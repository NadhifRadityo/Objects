package io.github.NadhifRadityo.Library.Providers

import io.github.NadhifRadityo.Library.CURL
import io.github.NadhifRadityo.Library.LibraryEntry
import io.github.NadhifRadityo.Library.Utils.CommonUtils.*
import io.github.NadhifRadityo.Library.Utils.FileUtils.mkfile
import io.github.NadhifRadityo.Library.Utils.JSONUtils.toJson
import io.github.NadhifRadityo.Library.Utils.JavascriptUtils.runJavascriptAsCallbackF
import io.github.NadhifRadityo.Library.Utils.LoggerUtils._ldebug
import io.github.NadhifRadityo.Library.Utils.LoggerUtils._linfo
import io.github.NadhifRadityo.Library.Utils.ProgressUtils.prog
import io.github.NadhifRadityo.Library.Utils.StringUtils.mostSafeString
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.nio.charset.StandardCharsets

typealias SearchURLCallback = (String?, String?, String?) -> String
typealias DownloadURLCallback = (String, String, String, String) -> String

class MavenProvider(
	val searchUrlCallback: SearchURLCallback,
	val downloadUrlCallback: DownloadURLCallback,
	val fileValidationCallback: FileValidationCallback
) {

	companion object {
		const val MAIN_PROPERTIES_MAVEN_HASHES = "mavenHashes"
		val defaultMavenProvider: Lazy<MavenProvider> = lazy {
			MavenProvider(DEFAULT_SEARCH_URL_CALLBACK.value, DEFAULT_DOWNLOAD_URL_CALLBACK.value, FILE_VALIDATION_CALLBACK.value)
		}
		val DEFAULT_VALIDATIONS: Lazy<Map<String, List<ValidationCallback>>> = lazy lambda@{
			val mainConfig = LibraryEntry.getMainConfig()
			return@lambda hashesAvailable(mainConfig.properties, mainConfig.properties.getProperty(MAIN_PROPERTIES_MAVEN_HASHES))
		}
		val DEFAULT_SEARCH_URL_CALLBACK: Lazy<SearchURLCallback> = lazy lambda@{
			val callback = runJavascriptAsCallbackF("(g, a, v) => `https://search.maven.org/solrsearch/select?q=\${[[\"g\", g], [\"a\", a], [\"v\", v]].map(([k, v]) => v != null ? `\${k}:\"\${v}\"`: null).filter(v => v != null).join(\"+AND+\")}&core=gav&wt=json`")
			return@lambda { group, artifact, version -> callback(arrayOf(group, artifact, version)) as String }
		}
		val DEFAULT_DOWNLOAD_URL_CALLBACK: Lazy<DownloadURLCallback> = lazy lambda@{
			val callback = runJavascriptAsCallbackF("(g, a, v, f) => `https://search.maven.org/remotecontent?filepath=\${g.replace(/\\./g, '/')}/\${a}/\${v}/\${f}`")
			return@lambda { group, artifact, version, file -> callback(arrayOf(group, artifact, version, file)) as String }
		}
		val FILE_VALIDATION_CALLBACK: Lazy<FileValidationCallback> = lazy lambda@{
			return@lambda { dependency, item, itemFile, itemFileCallback, type, download ->
				when(type) {
					State.VALIDATE_REQUEST_RUN -> validate(DEFAULT_VALIDATIONS.value) { extension ->
						if(extension.isEmpty()) itemFile
						else itemFileCallback(dependency, "$item.$extension")
					}
					State.VALIDATE_REQUEST_FETCH -> {
						for(extension in DEFAULT_VALIDATIONS.value.keys) {
							val itemName = "$item.$extension"
							download(dependency, itemName, itemFileCallback(dependency, itemName))
						}
						arrayOf()
					}
					else -> arrayOf()
				}
			}
		}
	}

	@Throws(Exception::class)
	fun search(group: String?, artifact: String?, version: String?): Array<MavenDependency> {
		prog(arrayOf(this, group, artifact, version, 0), javaClass, "Maven dependency", true, 2).use { prog0 ->
			val mavenSearch: JSONROOT_mavenSearch

			prog0.pdo(String.format("Listing %s.%s:%s", group ?: "*", artifact ?: "*", version ?: "*"))
			prog(arrayOf(this, group, artifact, version, 1), javaClass, "Getting dependency list", true).use { prog1 ->
				val curl = CURL()
				curl.url = formattedUrl(searchUrlCallback(group, artifact, version))
				curl.requestMethod = CURL.RequestMethod.GET
				curl.customHandler = { _, url, _, _ ->
					prog1.pdo(urlToUri(url).toASCIIString())
					_linfo("Searching repository \"%s\"... (%s)", "$group.$artifact", urlToUri(url).toASCIIString())
					null
				}
				curl.onConnect = { urlConnection, _, _, _ ->
					toJson(urlConnection.getInputStream(), JSONROOT_mavenSearch::class.java)
				}
				mavenSearch = curl.build(StandardCharsets.UTF_8) as JSONROOT_mavenSearch
			}

			val results = mutableListOf<MavenDependency>()
			val docs = mavenSearch.response.docs
			prog0.pdo(String.format("Getting infos %s.%s:%s", group ?: "*", artifact ?: "*", version ?: "*"))
			prog(arrayOf(this, group, artifact, version, 2), javaClass, "Getting dependency infos", true, docs.size).use { prog2 ->
				for(doc in docs) {
					val id = doc.id
					val g = doc.g
					val a = doc.a
					val v = doc.v
					val p = doc.p
					val timestamp = doc.timestamp
					val ec = doc.ec
					val tags = doc.tags
					prog2.pdo(String.format("Getting infos %s.%s:%s @%d", if(group == null) g else "*", if(artifact == null) a else "*", if(version == null) v else "*", timestamp))
					_ldebug("g=\"%s\" a=\"%s\" v=\"%s\" p=\"%s\" id=\"%s\" timestamp=\"%s\" ec=\"%s\" tags=\"%s\"", g, a, v, p, id, timestamp, java.lang.String.join(";", *ec), java.lang.String.join(";", *tags))

					if(group != null && group != g) continue
					if(artifact != null && artifact != a) continue
					if(version != null && version != v) continue
					val items = ec.map { "$a-$v$it" }.toTypedArray()
					val result = MavenDependency(id, g, a, v, p, timestamp, items, tags)
					results.add(result)
				}
			}
			results.sortWith { d1, d2 -> java.lang.Long.compare(d2.timestamp, d1.timestamp) }
			return results.toTypedArray()
		}
	}

	@JvmOverloads @Throws(Exception::class)
	fun download(dependency: MavenDependency, itemFileCallback: ItemFileCallback = defaultItemFileCallback(mostSafeString(dependency.id))): Map<String, File?> {
		return download0(dependency, { dep -> (dep as MavenDependency).items }, itemFileCallback, fileValidationCallback, { dep, item, itemFile ->
			val dependency_ = dep as MavenDependency
			mkfile(itemFile)
			FileOutputStream(itemFile).use { fos ->
				downloadFile(URL(downloadUrlCallback(dependency_.group, dependency_.artifact, dependency_.version, item)), fos)
			}
		})
	}

	class MavenDependency(
		val id: String,
		val group: String,
		val artifact: String,
		val version: String,
		val packaging: String,
		val timestamp: Long,
		val items: Array<String>,
		val tags: Array<String>
	) {
		val gav: String
			get() = String.format("%s.%s:%s", group, artifact, version)
		val ga: String
			get() = String.format("%s.%s", group, artifact)
	}

	data class JSONROOT_mavenSearch(
		var responseHeader: JSON_responseHeader,
		var response: JSON_response
	) {
		data class JSON_responseHeader(
			var status: Int,
			var QTime: Int,
			var params: JSON_params
		) {
			data class JSON_params(
				var q: String,
				var core: String,
				var indent: String,
				var fl: String,
				var start: String,
				var sort: String,
				var rows: String,
				var wt: String,
				var version: String
			)
		}
		data class JSON_response(
			var numFound: Int,
			var start: Int,
			var docs: Array<JSON_doc>
		) {
			data class JSON_doc(
				var id: String,
				var g: String,
				var a: String,
				var v: String,
				var p: String,
				var timestamp: Long,
				var ec: Array<String>,
				var tags: Array<String>
			)
		}
	}
}

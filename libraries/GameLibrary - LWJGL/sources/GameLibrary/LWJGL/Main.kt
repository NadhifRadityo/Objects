package GameLibrary.LWJGL

import io.github.NadhifRadityo.Library.LibraryModule
import io.github.NadhifRadityo.Library.Providers.MavenProvider
import io.github.NadhifRadityo.Library.Providers.MavenProvider.getDefaultMavenProvider
import io.github.NadhifRadityo.Library.Utils.FileUtils.*
import io.github.NadhifRadityo.Library.Utils.JSONUtils.createJSONFile
import io.github.NadhifRadityo.Library.Utils.JSONUtils.toJson
import io.github.NadhifRadityo.Library.Utils.ProgressUtils.progressEnd
import io.github.NadhifRadityo.Library.Utils.ProgressUtils.progressStart
import io.github.NadhifRadityo.Library.Utils.StringUtils.mostSafeString
import java.io.File

class Main : LibraryModule() {
	companion object {
		val GROUP = "org.lwjgl"
		val ARTIFACTS = arrayOf(
				"lwjgl", "lwjgl-assimp", "lwjgl-bgfx", "lwjgl-bom", "lwjgl-cuda", "lwjgl-egl", "lwjgl-glfw", "lwjgl-jawt", "lwjgl-jemalloc",
				"lwjgl-libdivide", "lwjgl-llvm", "lwjgl-lmdb", "lwjgl-lz4", "lwjgl-meow", "lwjgl-nanovg", "lwjgl-nfd", "lwjgl-nuklear", "lwjgl-odbc",
				"lwjgl-openal", "lwjgl-opencl", "lwjgl-opengl", "lwjgl-opengles", "lwjgl-openvr", "lwjgl-opus", "lwjgl-ovr", "lwjgl-par", "lwjgl-platform",
				"lwjgl-remotery", "lwjgl-rpmalloc", "lwjgl-shaderc", "lwjgl-sse", "lwjgl-stb", "lwjgl-tinyexr", "lwjgl-tinyfd", "lwjgl-tootle", "lwjgl-vma",
				"lwjgl-vulkan", "lwjgl-xxhash", "lwjgl-yoga", "lwjgl-zstd"
		)
	}

	override fun run() {
		val mavenProvider = getDefaultMavenProvider()
		val dependencyConfigsDir = file(staticDir, "dependencies")
		val dependencyFetchedDir = file(staticDir, "fetched")
		task(unique("clearDependencyCache")).apply {
			onlyIf { dependencyConfigsDir.exists() }
			doLast { delfile(dependencyConfigsDir) }
		}
		task(unique("searchDependency")).apply {
			dependsOn(unique("clearDependencyCache"))
			doLast {
				for(artifact in ARTIFACTS) {
					val dependency = mavenProvider.search(GROUP, artifact, null)[0]
					createJSONFile(dependency, file(dependencyConfigsDir, mostSafeString(dependency.ga) + ".json"))
				}
			}
		}
		task(unique("clearDependency")).apply {
			onlyIf { dependencyFetchedDir.exists() }
			doLast { delfile(dependencyFetchedDir) }
		}
		task(unique("fetchDependency")).apply {
//			dependsOn(unique("clearDependency"))
			onlyIf {
				dependencyConfigsDir.exists() && dependencyConfigsDir.listFiles()?.isNotEmpty() == true
			}
			doLast {
				val configFiles = dependencyConfigsDir.listFiles { file -> file.isFile }!!
				val nativesDir = mkdir(dependencyFetchedDir, "gamelib_natives")
				val itemFileCallback: (Array<Any>) -> File = itemFileCallback@ { args ->
					val dependency = args[0] as MavenProvider.MavenDependency
					val item = args[1] as String
					val mainJar: String = dependency.artifact + "-" + dependency.version + ".jar"
					val md5SplitIndex = item.lastIndexOf(".md5")
					val sha1SplitIndex = item.lastIndexOf(".sha1")
					val reflectingItem = if(md5SplitIndex != -1) item.substring(0, md5SplitIndex) else
						if(sha1SplitIndex != -1) item.substring(0, sha1SplitIndex) else item
					if(reflectingItem != mainJar && !reflectingItem.endsWith("-javadoc.jar") &&
							!reflectingItem.endsWith("-sources.jar") && !reflectingItem.endsWith(".pom"))
						return@itemFileCallback file(nativesDir, item)
					file(dependencyFetchedDir, item)
				}
				for(configFile in configFiles) {
					val dependency = toJson(configFile, MavenProvider.MavenDependency::class.java)
					if(dependency.group != GROUP || !ARTIFACTS.contains(dependency.artifact)) continue
					mavenProvider.download(dependency, itemFileCallback)
				}
			}
		}
	}
}

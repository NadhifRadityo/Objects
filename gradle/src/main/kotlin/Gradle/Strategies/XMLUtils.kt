package Gradle.Strategies

import Gradle.DynamicScripting.Scripting.addInjectScript
import Gradle.DynamicScripting.Scripting.removeInjectScript
import Gradle.GroovyKotlinInteroperability.ExportGradle
import Gradle.GroovyKotlinInteroperability.GroovyInteroperability.prepareGroovyKotlinCache
import Gradle.GroovyKotlinInteroperability.GroovyKotlinCache
import Gradle.Strategies.FileUtils.fileString
import Gradle.Strategies.FileUtils.mkfile
import Gradle.Strategies.FileUtils.writeFileString
import Gradle.Strategies.LoggerUtils.linfo
import Gradle.Strategies.StreamUtils.streamString
import Gradle.Strategies.Utils.__invalid_type
import org.w3c.dom.Document
import org.w3c.dom.Element
import org.xml.sax.InputSource
import java.io.File
import java.io.InputStream
import java.io.StringReader
import java.io.StringWriter
import java.nio.charset.StandardCharsets
import javax.xml.parsers.DocumentBuilderFactory
import javax.xml.transform.OutputKeys
import javax.xml.transform.TransformerFactory
import javax.xml.transform.dom.DOMSource
import javax.xml.transform.stream.StreamResult

object XMLUtils {
	@JvmStatic private var cache: GroovyKotlinCache<XMLUtils>? = null

	@JvmStatic
	fun construct() {
		cache = prepareGroovyKotlinCache(XMLUtils)
		addInjectScript(cache!!)
	}
	@JvmStatic
	fun destruct() {
		removeInjectScript(cache!!)
		cache = null
	}

	@ExportGradle @JvmStatic @Throws(Exception::class)
	fun newXMLDocument(obj0: Any?): Document {
		val documentBuilderFactory = DocumentBuilderFactory.newInstance()
		val documentBuilder = documentBuilderFactory.newDocumentBuilder()
		val obj: String? = when(obj0) {
			is InputStream -> streamString(obj0, StandardCharsets.UTF_8)
			is File -> fileString(obj0, StandardCharsets.UTF_8)
			null -> null
			else -> throw __invalid_type()
		}
		return if(obj == null) documentBuilder.newDocument()
			else documentBuilder.parse(InputSource(StringReader(obj)))
	}

	@ExportGradle @JvmStatic @Throws(Exception::class)
	fun XMLToString(obj: Any?): String {
		val transformerFactory = TransformerFactory.newInstance()
		val transformer = transformerFactory.newTransformer()
		val source = when(obj) {
			is Document -> {
				transformer.setOutputProperty(OutputKeys.METHOD, "xml")
				transformer.setOutputProperty(OutputKeys.INDENT, "yes")
				transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", Integer.toString(2))
				DOMSource(obj.documentElement)
			}
			is Element -> {
				transformer.setOutputProperty(OutputKeys.METHOD, "xml")
				transformer.setOutputProperty(OutputKeys.INDENT, "yes")
				transformer.setOutputProperty("omit-xml-declaration", "yes")
				transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", Integer.toString(2))
				DOMSource(obj as Element?)
			}
			else -> throw __invalid_type()
		}
		val stringWriter = StringWriter()
		val streamResult = StreamResult(stringWriter)
		transformer.transform(source, streamResult)
		return stringWriter.toString()
	}

	@ExportGradle @JvmStatic @Throws(Exception::class)
	fun createXMLFile(obj: Any?, target: File): String {
		val stringOut = XMLToString(obj)
		mkfile(target)
		writeFileString(target, stringOut, StandardCharsets.UTF_8)
		linfo("Configurations written to: ${target.path}")
		return stringOut
	}
}

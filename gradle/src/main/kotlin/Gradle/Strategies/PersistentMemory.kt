package Gradle.Strategies

import Gradle.Common.addOnConfigStarted
import Gradle.Common.groovyKotlinCaches
import Gradle.Common.lastContext
import Gradle.GroovyKotlinInteroperability.ExportGradle
import Gradle.GroovyKotlinInteroperability.GroovyInteroperability.attachAnyObject
import Gradle.GroovyKotlinInteroperability.GroovyInteroperability.prepareGroovyKotlinCache
import Gradle.GroovyKotlinInteroperability.GroovyKotlinCache
import Gradle.GroovyKotlinInteroperability.GroovyManipulation
import Gradle.Strategies.CommonUtils.bytesToHexString
import Gradle.Strategies.HashUtils.checksumJavaNative

object PersistentMemory {
	@JvmStatic private var cache: GroovyKotlinCache<PersistentMemory>? = null
	@JvmStatic private val memories = HashMap<String, Memory>()

	@JvmStatic
	fun construct() {
		cache = prepareGroovyKotlinCache(PersistentMemory)
		groovyKotlinCaches += cache!!
		addOnConfigStarted(0) {
			val deletes = memories.entries.filter { it.value.invalidate?.invoke() == true }.map { it.key }
			for(delete in deletes) memories.remove(delete)
			for(memory in memories.values) {
				memory.cache = prepareGroovyKotlinCache(memory)
				memory.__start__()
				attachAnyObject(memory, memory.cache!!)
				memory.__end__()
			}
		}
		addOnConfigStarted(0) {
			val deletes = memories.entries.filter { it.value.invalidate?.invoke() == true }.map { it.key }
			for(delete in deletes) memories.remove(delete)
			for(memory in memories.values) {
				memory.__clear__()
				memory.cache = null
			}
		}
	}
	@JvmStatic
	fun destruct() {
		groovyKotlinCaches -= cache!!
		cache = null
	}

	@ExportGradle @JvmStatic
	fun persistentMemory(id: String): Memory {
		var memory = memories[id]
		if(memory != null)
			return memory
		memory = Memory(id)
		memory.cache = prepareGroovyKotlinCache(memory)
		memory.__start__()
		attachAnyObject(memory, memory.cache!!)
		memory.__end__()
		memories[id] = memory
		return memory
	}
	@ExportGradle @JvmStatic
	fun persistentMemory(): Memory {
		val scriptFile = (lastContext().that as org.gradle.api.Script).buildscript.sourceFile!!
		val memory = persistentMemory(scriptFile.canonicalPath)
		// Need to check if the source have been changed. Any changes
		// to file hash might make the object structure changed
		val scriptFileLastHash = bytesToHexString(checksumJavaNative(scriptFile, "MD5"))
		memory.invalidate = {
			val currentHash = bytesToHexString(checksumJavaNative(scriptFile, "MD5"))
			currentHash != scriptFileLastHash
		}
		return memory
	}

	open class Memory(
		@ExportGradle val id: String
	): GroovyManipulation.DummyGroovyObject() {
		var cache: GroovyKotlinCache<Memory>? = null
			internal set
		@ExportGradle val data = HashMap<String, Any?>()
		@ExportGradle var invalidate: (() -> Boolean)? = null
			internal set

		override fun getProperty(property: String): Any? {
			return if(data.contains(property)) data[property] else super.getProperty(property)
		}
		override fun setProperty(property: String, value: Any?) {
			if(data.contains(property)) data[property] = value else super.getProperty(property)
		}
		@ExportGradle
		operator fun get(property: String): Any? {
			return getProperty(property)
		}
		@ExportGradle
		operator fun set(property: String, value: Any?) {
			setProperty(property, value)
		}

		@ExportGradle
		fun getOrCompute(name: String, callback: () -> Any?): Any? {
			val exists = data.containsKey(name)
			if(exists) return data[name]
			val result = callback()
			data[name] = result
			return result
		}
		@ExportGradle
		fun getOrDefault(name: String, default: Any?): Any? {
			val exists = data.containsKey(name)
			if(exists) return data[name]
			data[name] = default
			return default
		}
	}
}

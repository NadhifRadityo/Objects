import Common.groovyKotlinCaches
import DynamicScripting.__getLastImportScript
import Utils.__must_not_happen
import Utils.attachAnyObject
import Utils.checksumJava
import Utils.fileBytes
import Utils.prepareGroovyKotlinCache

object PersistentMemory {
	@JvmStatic
	private var cache: GroovyKotlinCache<*>? = null
	@JvmStatic
	private val memories = HashMap<String, Memory>()

	@JvmStatic
	fun construct() {
		cache = prepareGroovyKotlinCache(PersistentMemory)
		groovyKotlinCaches += cache!!
		val deletes = memories.entries.filter { it.value.invalidate?.invoke() == true }.map { it.key }
		for(delete in deletes) memories.remove(delete)
		for(memory in memories.values) {
			memory.cache = prepareGroovyKotlinCache(memory)
			memory.__start__()
			attachAnyObject(memory, memory.cache!!)
			memory.__end__()
		}
	}
	@JvmStatic
	fun destruct() {
		groovyKotlinCaches -= cache!!
		cache = null
		val deletes = memories.entries.filter { it.value.invalidate?.invoke() == true }.map { it.key }
		for(delete in deletes) memories.remove(delete)
		for(memory in memories.values) {
			memory.__clear__()
			memory.cache = null
		}
	}

	@ExportGradle
	@JvmStatic
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
	@ExportGradle
	@JvmStatic
	fun persistentMemory(): Memory {
		val lastImportScript = __getLastImportScript() ?: throw __must_not_happen()
		val scriptFile = lastImportScript.file
		val memory = persistentMemory(scriptFile.canonicalPath)
		// Need to check if the source have been changed. Any changes
		// to file hash might make the object structure changed
		val scriptFileLastHash = checksumJava("MD5", fileBytes(scriptFile))
		memory.invalidate = {
			val currentHash = checksumJava("MD5", fileBytes(scriptFile))
			currentHash != scriptFileLastHash
		}
		return memory
	}

	open class Memory(
		val id: String
	): GroovyInteroperability.DummyGroovyObject() {
		val data = HashMap<String, Any?>()
		var invalidate: (() -> Boolean)? = null
			internal set
		var cache: GroovyKotlinCache<Memory>? = null
			internal set

		@ExportGradle
		fun get(name: String): Any? {
			return data[name]
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
		@ExportGradle
		fun set(name: String, value: Any?) {
			data[name] = value
		}
	}
}

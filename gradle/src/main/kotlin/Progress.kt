import org.gradle.internal.logging.progress.ProgressLogger
import org.gradle.internal.logging.progress.ProgressLoggerFactory
import org.gradle.internal.operations.BuildOperationDescriptor

object Progress {
	@JvmStatic
	private var factory: ProgressLoggerFactory? = null
	@JvmStatic
	private var instances: MutableMap<Int, ProgressLogger>? = null

	@JvmStatic
	fun init() {
		Utils.pushKotlinToGradle(Progress)
		factory = Utils.asService(ProgressLoggerFactory::class.java)
		instances = HashMap()
	}
	@JvmStatic
	fun deinit() {
		Utils.pullKotlinFromGradle(Progress)
		factory = null
		instances = null
	}
	@ExportGradle
	@JvmStatic
	fun available(): Boolean {
		return factory != null
	}

	@ExportGradle
	@JvmStatic @JvmOverloads
	fun create(identifier: Any? = null, progressCategory: String?) {
		val instance = factory!!.newOperation(progressCategory)
		instances!![System.identityHashCode(identifier)] = instance
	}
	@ExportGradle
	@JvmStatic @JvmOverloads
	fun create(identifier: Any? = null, progressCategory: Class<*>?) {
		val instance = factory!!.newOperation(progressCategory)
		instances!![System.identityHashCode(identifier)] = instance
	}
	@ExportGradle
	@JvmStatic @JvmOverloads
	fun create(identifier: Any? = null, progressCategory: Class<*>?, buildOperationDescriptor: BuildOperationDescriptor?) {
		val instance = factory!!.newOperation(progressCategory, buildOperationDescriptor)
		instances!![System.identityHashCode(identifier)] = instance
	}
	@ExportGradle
	@JvmStatic @JvmOverloads
	fun create(identifier: Any? = null, progressCategory: Class<*>?, progressLogger: ProgressLogger?) {
		val instance = factory!!.newOperation(progressCategory, progressLogger)
		instances!![System.identityHashCode(identifier)] = instance
	}
	@ExportGradle
	@JvmStatic @JvmOverloads
	fun destroy(identifier: Any? = null) {
		instances!!.remove(System.identityHashCode(identifier))
	}
	@ExportGradle
	@JvmStatic @JvmOverloads
	fun instance(identifier: Any? = null): ProgressLogger? {
		return instances!![System.identityHashCode(identifier)]
	}

	@JvmStatic @JvmOverloads
	fun __on_start(identifier: Any? = null, callback: (ProgressLogger) -> Unit) {
		val instance = instance(identifier)
		if(instance != null) { callback(instance); return }
		create(identifier, identifier?.toString() ?: "default")
		callback(instance(identifier)!!)
	}
	@JvmStatic @JvmOverloads
	fun __on_do(identifier: Any? = null, callback: (ProgressLogger) -> Unit) {
		__on_start(identifier, callback)
	}
	@JvmStatic @JvmOverloads
	fun __on_end(identifier: Any? = null, callback: (ProgressLogger) -> Unit) {
		__on_start(identifier) {
			callback(it)
			destroy(identifier)
		}
	}
	@ExportGradle
	@JvmStatic @JvmOverloads
	fun pstart(identifier: Any? = null) {
		__on_start(identifier) { it.started() }
	}
	@ExportGradle
	@JvmStatic @JvmOverloads
	fun pstart(identifier: Any? = null, status: String?) {
		__on_start(identifier) { it.started(status) }
	}
	@ExportGradle
	@JvmStatic @JvmOverloads
	fun pstart(identifier: Any? = null, description: String?, status: String?) {
		__on_start(identifier) { it.start(description, status) }
	}
	@ExportGradle
	@JvmStatic @JvmOverloads
	fun pdo(identifier: Any? = null, status: String?) {
		__on_do(identifier) { it.progress(status) }
	}
	@ExportGradle
	@JvmStatic @JvmOverloads
	fun pdo(identifier: Any? = null, status: String?, failing: Boolean) {
		__on_do(identifier) { it.progress(status, failing) }
	}
	@ExportGradle
	@JvmStatic @JvmOverloads
	fun pend(identifier: Any? = null) {
		__on_end(identifier) { it.completed() }
	}
	@ExportGradle
	@JvmStatic @JvmOverloads
	fun pend(identifier: Any? = null, status: String?, failed: Boolean) {
		__on_end(identifier) { it.completed(status, failed) }
	}
}

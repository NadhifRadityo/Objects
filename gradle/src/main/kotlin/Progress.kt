import org.gradle.internal.logging.progress.ProgressLogger
import org.gradle.internal.logging.progress.ProgressLoggerFactory
import org.gradle.internal.operations.BuildOperationDescriptor

object Progress {
	private var factory: ProgressLoggerFactory? = null
	private var instances: MutableMap<Int, ProgressLogger>? = null

	fun init() {
		factory = Utils.asService(ProgressLoggerFactory::class.java)
		instances = HashMap()
	}
	fun deinit() {
		factory = null
		instances = null
	}
	fun available(): Boolean {
		return factory != null
	}

	fun create(identifier: Any? = null, progressCategory: String?) {
		val instance = factory!!.newOperation(progressCategory)
		instances!![System.identityHashCode(identifier)] = instance
	}
	fun create(identifier: Any? = null, progressCategory: Class<*>?) {
		val instance = factory!!.newOperation(progressCategory)
		instances!![System.identityHashCode(identifier)] = instance
	}
	fun create(identifier: Any? = null, progressCategory: Class<*>?, buildOperationDescriptor: BuildOperationDescriptor?) {
		val instance = factory!!.newOperation(progressCategory, buildOperationDescriptor)
		instances!![System.identityHashCode(identifier)] = instance
	}
	fun create(identifier: Any? = null, progressCategory: Class<*>?, progressLogger: ProgressLogger?) {
		val instance = factory!!.newOperation(progressCategory, progressLogger)
		instances!![System.identityHashCode(identifier)] = instance
	}
	fun destroy(identifier: Any? = null) {
		instances!!.remove(System.identityHashCode(identifier))
	}
	fun instance(identifier: Any? = null): ProgressLogger? {
		return instances!![System.identityHashCode(identifier)]
	}

	fun __on_start(identifier: Any? = null, callback: (ProgressLogger) -> Unit) {
		val instance = instance(identifier)
		if(instance != null) { callback(instance); return }
		create(identifier, identifier?.toString() ?: "default")
		callback(instance(identifier)!!)
	}
	fun __on_do(identifier: Any? = null, callback: (ProgressLogger) -> Unit) {
		__on_start(identifier, callback)
	}
	fun __on_end(identifier: Any? = null, callback: (ProgressLogger) -> Unit) {
		__on_start(identifier) {
			callback(it)
			destroy(identifier)
		}
	}
	fun pstart(identifier: Any? = null) {
		__on_start(identifier) { it.started() }
	}
	fun pstart(identifier: Any? = null, status: String?) {
		__on_start(identifier) { it.started(status) }
	}
	fun pstart(identifier: Any? = null, description: String?, status: String?) {
		__on_start(identifier) { it.start(description, status) }
	}
	fun pdo(identifier: Any? = null, status: String?) {
		__on_do(identifier) { it.progress(status) }
	}
	fun pdo(identifier: Any? = null, status: String?, failing: Boolean) {
		__on_do(identifier) { it.progress(status, failing) }
	}
	fun pend(identifier: Any? = null) {
		__on_end(identifier) { it.completed() }
	}
	fun pend(identifier: Any? = null, status: String?, failed: Boolean) {
		__on_end(identifier) { it.completed(status, failed) }
	}
}

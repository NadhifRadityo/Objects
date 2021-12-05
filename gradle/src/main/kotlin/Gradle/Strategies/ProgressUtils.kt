package Gradle.Strategies

import Gradle.Common.groovyKotlinCaches
import Gradle.GroovyKotlinInteroperability.ExportGradle
import Gradle.GroovyKotlinInteroperability.GroovyInteroperability
import Gradle.GroovyKotlinInteroperability.GroovyInteroperability.prepareGroovyKotlinCache
import Gradle.GroovyKotlinInteroperability.GroovyKotlinCache
import Gradle.GroovyKotlinInteroperability.GroovyManipulation
import Gradle.Strategies.ClassUtils.classForName0
import Gradle.Strategies.GradleUtils.asService
import Gradle.Strategies.UnsafeUtils.unsafe
import org.gradle.internal.logging.progress.ProgressLogger
import org.gradle.internal.logging.progress.ProgressLoggerFactory
import org.gradle.internal.operations.BuildOperationDescriptor
import java.util.*

object ProgressUtils {
	@JvmStatic private var cache: GroovyKotlinCache<ProgressUtils>? = null
	@JvmStatic private var factory: ProgressLoggerFactory? = null
	@JvmStatic private var instances: MutableMap<Int, ProgressLogger>? = null

	@JvmStatic
	fun construct() {
		cache = prepareGroovyKotlinCache(ProgressUtils)
		groovyKotlinCaches += cache!!
		factory = asService(ProgressLoggerFactory::class.java)
		instances = HashMap()
	}
	@JvmStatic
	fun destruct() {
		groovyKotlinCaches -= cache!!
		cache = null
		factory = null
		instances = null
	}

	@ExportGradle
	@JvmStatic @JvmOverloads
	fun progressCreate(identifier: Any? = null, progressCategory: String?) {
		val instance = factory!!.newOperation(progressCategory)
		instances!![System.identityHashCode(identifier)] = instance
	}
	@ExportGradle
	@JvmStatic @JvmOverloads
	fun progressCreate(identifier: Any? = null, progressCategory: Class<*>?) {
		val instance = factory!!.newOperation(progressCategory)
		instances!![System.identityHashCode(identifier)] = instance
	}
	@ExportGradle
	@JvmStatic @JvmOverloads
	fun progressCreate(identifier: Any? = null, progressCategory: Class<*>?, buildOperationDescriptor: BuildOperationDescriptor?) {
		val instance = factory!!.newOperation(progressCategory, buildOperationDescriptor)
		instances!![System.identityHashCode(identifier)] = instance
	}
	@ExportGradle
	@JvmStatic @JvmOverloads
	fun progressCreate(identifier: Any? = null, progressCategory: Class<*>?, progressLogger: ProgressLogger?) {
		val instance = factory!!.newOperation(progressCategory, progressLogger)
		instances!![System.identityHashCode(identifier)] = instance
	}
	@ExportGradle
	@JvmStatic @JvmOverloads
	fun progressDestroy(identifier: Any? = null) {
		instances!!.remove(System.identityHashCode(identifier))
	}
	@ExportGradle
	@JvmStatic @JvmOverloads
	fun progressInstance(identifier: Any? = null): ProgressLogger? {
		return instances!![System.identityHashCode(identifier)]
	}

	@JvmStatic @JvmOverloads
	fun __on_start(identifier: Any? = null, callback: (ProgressLogger) -> Unit) {
		val instance = progressInstance(identifier)
		if(instance != null) { callback(instance); return }
		progressCreate(identifier, identifier?.toString() ?: "default")
		callback(progressInstance(identifier)!!)
	}
	@JvmStatic @JvmOverloads
	fun __on_do(identifier: Any? = null, callback: (ProgressLogger) -> Unit) {
		__on_start(identifier, callback)
	}
	@JvmStatic @JvmOverloads
	fun __on_end(identifier: Any? = null, callback: (ProgressLogger) -> Unit) {
		__on_start(identifier) {
			callback(it)
			progressDestroy(identifier)
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

	@JvmStatic internal val stack = ThreadLocal<LinkedList<Any>>()
	@JvmStatic internal val AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_progressOperationId: Long
	@JvmStatic internal val AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_category: Long
	@JvmStatic internal val AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_listener: Long
	@JvmStatic internal val AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_clock: Long
	@JvmStatic internal val AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_buildOperationStart: Long
	@JvmStatic internal val AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_buildOperationId: Long
	@JvmStatic internal val AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_parentBuildOperationId: Long
	@JvmStatic internal val AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_buildOperationCategory: Long
	@JvmStatic internal val AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_previous: Long
	@JvmStatic internal val AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_parent: Long
	@JvmStatic internal val AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_description: Long
	@JvmStatic internal val AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_loggingHeader: Long
	@JvmStatic internal val AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_state: Long
	@JvmStatic internal val AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_totalProgress: Long
	init {
		val CLASS_DefaultProgressLoggerFactory_ProgressLoggerImpl = classForName0<Any>("org.gradle.internal.logging.progress.DefaultProgressLoggerFactory\$ProgressLoggerImpl")
		val FIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_progressOperationId = CLASS_DefaultProgressLoggerFactory_ProgressLoggerImpl.getDeclaredField("progressOperationId")
		val FIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_category = CLASS_DefaultProgressLoggerFactory_ProgressLoggerImpl.getDeclaredField("category")
		val FIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_listener = CLASS_DefaultProgressLoggerFactory_ProgressLoggerImpl.getDeclaredField("listener")
		val FIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_clock = CLASS_DefaultProgressLoggerFactory_ProgressLoggerImpl.getDeclaredField("clock")
		val FIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_buildOperationStart = CLASS_DefaultProgressLoggerFactory_ProgressLoggerImpl.getDeclaredField("buildOperationStart")
		val FIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_buildOperationId = CLASS_DefaultProgressLoggerFactory_ProgressLoggerImpl.getDeclaredField("buildOperationId")
		val FIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_parentBuildOperationId = CLASS_DefaultProgressLoggerFactory_ProgressLoggerImpl.getDeclaredField("parentBuildOperationId")
		val FIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_buildOperationCategory = CLASS_DefaultProgressLoggerFactory_ProgressLoggerImpl.getDeclaredField("buildOperationCategory")
		val FIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_previous = CLASS_DefaultProgressLoggerFactory_ProgressLoggerImpl.getDeclaredField("previous")
		val FIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_parent = CLASS_DefaultProgressLoggerFactory_ProgressLoggerImpl.getDeclaredField("parent")
		val FIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_description = CLASS_DefaultProgressLoggerFactory_ProgressLoggerImpl.getDeclaredField("description")
		val FIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_loggingHeader = CLASS_DefaultProgressLoggerFactory_ProgressLoggerImpl.getDeclaredField("loggingHeader")
		val FIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_state = CLASS_DefaultProgressLoggerFactory_ProgressLoggerImpl.getDeclaredField("state")
		val FIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_totalProgress = CLASS_DefaultProgressLoggerFactory_ProgressLoggerImpl.getDeclaredField("totalProgress")
		AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_progressOperationId = unsafe.objectFieldOffset(FIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_progressOperationId)
		AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_category = unsafe.objectFieldOffset(FIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_category)
		AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_listener = unsafe.objectFieldOffset(FIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_listener)
		AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_clock = unsafe.objectFieldOffset(FIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_clock)
		AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_buildOperationStart = unsafe.objectFieldOffset(FIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_buildOperationStart)
		AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_buildOperationId = unsafe.objectFieldOffset(FIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_buildOperationId)
		AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_parentBuildOperationId = unsafe.objectFieldOffset(FIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_parentBuildOperationId)
		AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_buildOperationCategory = unsafe.objectFieldOffset(FIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_buildOperationCategory)
		AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_previous = unsafe.objectFieldOffset(FIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_previous)
		AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_parent = unsafe.objectFieldOffset(FIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_parent)
		AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_description = unsafe.objectFieldOffset(FIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_description)
		AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_loggingHeader = unsafe.objectFieldOffset(FIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_loggingHeader)
		AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_state = unsafe.objectFieldOffset(FIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_state)
		AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_totalProgress = unsafe.objectFieldOffset(FIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_totalProgress)
	}

	@ExportGradle
	@JvmStatic
	fun progress(identifier: Any): ProgressWrapper {
		progressCreate(identifier, null as String?)
		val progressWrapper = ProgressWrapper(identifier)
		progressWrapper.cache = prepareGroovyKotlinCache(progressWrapper)
		progressWrapper.__start__()
		GroovyInteroperability.attachAnyObject(progressWrapper, progressWrapper.cache!!)
		progressWrapper.__end__()
		return progressWrapper
	}
	@ExportGradle
	@JvmStatic
	fun progress_id(vararg objects: Any?): String {
		return objects.joinToString(";") { System.identityHashCode(it).toString(16) }
	}

	class ProgressWrapper(val identifier: Any) : GroovyManipulation.DummyGroovyObject(), AutoCloseable {
		var cache: GroovyKotlinCache<ProgressWrapper>? = null
			internal set
		val instance: ProgressLogger?
			get() { return progressInstance(identifier) }
		val state: String
			get() { return (unsafe.getObject(instance, AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_state) as Enum<*>).name.uppercase() }
		var category: String
			get() { return unsafe.getObject(instance, AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_category) as String }
			set(v) { assertNotStarted(); unsafe.putObject(instance, AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_category, v) }
		var description: String
			get() { return unsafe.getObject(instance, AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_description) as String }
			set(v) { assertNotStarted(); unsafe.putObject(instance, AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_description, v) }
		var loggingHeader: String
			get() { return unsafe.getObject(instance, AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_loggingHeader) as String }
			set(v) { assertNotStarted(); unsafe.putObject(instance, AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_loggingHeader, v) }
		var totalProgress: Int
			get() { return unsafe.getInt(instance, AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_totalProgress) }
			set(v) { assertNotStarted(); unsafe.putInt(instance, AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_totalProgress, v) }

		internal fun assertNotStarted() {
			val instance = instance
			val state = state
			check(state != "STARTED") { "This operation ($instance) has already been started." }
			check(state != "COMPLETED") { "This operation ($instance) has already completed." }
		}
		internal fun assertRunning() {
			val instance = instance
			val state = state
			check(state != "IDLE") { "This operation ($instance) has not been started." }
			check(state != "COMPLETED") { "This operation ($instance) has already been completed." }
		}
		internal fun assertCanConfigure() {
			val instance = instance
			val state = state
			check(state != "IDLE") { "Cannot configure this operation ($instance) once it has started." }
		}

		internal var inherited = false
		fun inherit() {
			assertNotStarted()
			if(inherited) return
			var list = stack.get()
			if(list == null) {
				list = LinkedList()
				stack.set(list)
			}
			val last = if(list.size > 0) list.last else null
			val lastInstance = progressInstance(last)
			if(lastInstance != null) {
				val parentId: Any = unsafe.getObject(lastInstance, AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_progressOperationId)
				unsafe.putObject(instance, AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_parent, lastInstance)
				unsafe.putObject(instance, AFIELD_DefaultProgressLoggerFactory_ProgressLoggerImpl_buildOperationId, parentId)
			}
			list.addLast(identifier)
			inherited = true
		}

		fun pstart(description: String?, status: String?) { pstart(identifier, description, status) }
		fun pstart(status: String?) { pstart(identifier, status) }
		fun pstart() { pstart(identifier) }

		fun pdo(status: String?) { pdo(identifier, status) }
		fun pdo(status: String?, failing: Boolean) { pdo(identifier, status, failing) }

		internal var endCalled = false
		fun pend() { endCalled = true; pend(identifier) }
		fun pend(status: String?, failed: Boolean) { endCalled = true; pend(identifier, status, failed) }

		override fun close() {
			if(inherited) {
				val list = stack.get()
				val last = list.removeLast()
				check(!(identifier !== last)) { "Must not happen" }
			}
			if(state != "STARTED") return
			if(!endCalled) pend(identifier)
			__clear__()
			cache = null
		}
	}
}

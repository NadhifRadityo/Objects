import org.gradle.api.Project
import java.util.*

object Common {
	private val contextStack = ThreadLocal.withInitial<LinkedList<Project>> { LinkedList() }

	fun context(project: Project, callback: Runnable) {
		val stack = contextStack.get()
		stack.addLast(project)
		try {
			callback.run()
		} catch(e: Throwable) {
			throw Error(e)
		} finally {
			val last = stack.removeLast()
			if(project != last)
				throw IllegalStateException("Must not happen")
		}
	}
	fun lastContext(): Project {
		val stack = contextStack.get()
		if(stack.size == 0)
			throw IllegalStateException("context is not available")
		return stack.last
	}
}

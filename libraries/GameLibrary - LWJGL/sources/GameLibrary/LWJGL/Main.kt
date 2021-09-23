package GameLibrary.LWJGL

import io.github.NadhifRadityo.Library.LibraryModule

class Main : LibraryModule() {
	companion object {
		val TASK_GROUP = "library.game_library.lwjgl"
	}

	override fun run() { with(getProject()) {
		log("hello")
		task("libraryTest").apply {
			group = TASK_GROUP
			doFirst {
				log("from task lola")
			}
		}
	} }
}

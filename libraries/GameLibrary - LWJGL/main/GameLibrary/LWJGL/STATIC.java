package GameLibrary.LWJGL;

public interface STATIC {
	String NAME = "GameLibrary - LWJGL";
	String NATIVE_NAME = "GameLibrary - LWJGL - Natives";
	String LIBRARY_NAME = NAME.replaceAll("[^A-Za-z0-9]", "_");
	String LIBRARY_NATIVE_NAME = NATIVE_NAME.replaceAll("[^A-Za-z0-9]", "_");
	String JAR_NAME = "natives.jar";
	String NATIVE_DIR = "gamelib_natives";
	String GROUP = "org.lwjgl";
	String[] ARTIFACTS = new String[] {
			"lwjgl", "lwjgl-assimp", "lwjgl-bgfx", "lwjgl-bom", "lwjgl-cuda", "lwjgl-egl", "lwjgl-glfw", "lwjgl-jawt", "lwjgl-jemalloc",
			"lwjgl-libdivide", "lwjgl-llvm", "lwjgl-lmdb", "lwjgl-lz4", "lwjgl-meow", "lwjgl-nanovg", "lwjgl-nfd", "lwjgl-nuklear", "lwjgl-odbc",
			"lwjgl-openal", "lwjgl-opencl", "lwjgl-opengl", "lwjgl-opengles", "lwjgl-openvr", "lwjgl-opus", "lwjgl-ovr", "lwjgl-par", "lwjgl-platform",
			"lwjgl-remotery", "lwjgl-rpmalloc", "lwjgl-shaderc", "lwjgl-sse", "lwjgl-stb", "lwjgl-tinyexr", "lwjgl-tinyfd", "lwjgl-tootle", "lwjgl-vma",
			"lwjgl-vulkan", "lwjgl-xxhash", "lwjgl-yoga", "lwjgl-zstd"
	};
}

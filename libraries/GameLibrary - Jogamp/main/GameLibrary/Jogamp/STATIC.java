package GameLibrary.Jogamp;

public interface STATIC {
	String NAME = "GameLibrary - Jogamp";
	String NATIVE_NAME = "GameLibrary - Jogamp - Natives";
	String LIBRARY_NAME = NAME.replaceAll("[^A-Za-z0-9]", "_");
	String LIBRARY_NATIVE_NAME = NATIVE_NAME.replaceAll("[^A-Za-z0-9]", "_");
	String JAR_NAME = "natives.jar";
	String NATIVE_DIR = "gamelib_natives";
	String VERSION_LIST = "https://jogamp.org/deployment/archive/rc/";
	String AT_LEAST_VERSION = "v2.4.0-rc-20210111";
}

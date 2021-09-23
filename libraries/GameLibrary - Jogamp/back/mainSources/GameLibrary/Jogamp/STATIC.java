package GameLibrary.Jogamp;

import io.github.NadhifRadityo.Objects.Library.ReferencedCallback;

import java.util.Properties;

import static io.github.NadhifRadityo.Objects.Library.Providers.__shared__.DEFAULT_HASH_OPTIONS_ALL_SHA512;

public interface STATIC {
	String NAME = "GameLibrary - Jogamp";
	String NATIVE_NAME = "GameLibrary - Jogamp - Natives";
	String LIBRARY_NAME = NAME.replaceAll("[^A-Za-z0-9]", "_");
	String LIBRARY_NATIVE_NAME = NATIVE_NAME.replaceAll("[^A-Za-z0-9]", "_");
	String JAR_NAME = "natives.jar";
	String NATIVE_DIR = "gamelib_natives";
	String VERSION_LIST = "https://jogamp.org/deployment/archive/rc/";
	String AT_LEAST_VERSION = "v2.4.0-rc-20210111";
	String CHECKSUM_FILE = "sha512sum.txt";
	String CHECKSUM_EXTENSION = ".sha512";
	ReferencedCallback<String> CHECKSUM_TYPE = (args) -> String.join(",", DEFAULT_HASH_OPTIONS_ALL_SHA512((Properties) args[0]));
}

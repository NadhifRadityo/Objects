package io.github.NadhifRadityo.Library.Utils;

import io.github.NadhifRadityo.Library.ReferencedCallback;

import java.lang.management.ManagementFactory;
import java.nio.ByteOrder;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.github.NadhifRadityo.Library.Utils.UnsafeUtils.unsafe;
import static org.apache.commons.lang3.SystemUtils.IS_OS_LINUX;
import static org.apache.commons.lang3.SystemUtils.IS_OS_MAC_OSX;
import static org.apache.commons.lang3.SystemUtils.IS_OS_SOLARIS;
import static org.apache.commons.lang3.SystemUtils.IS_OS_WINDOWS;
import static org.apache.commons.lang3.SystemUtils.JAVA_VERSION;
import static org.apache.commons.lang3.SystemUtils.OS_ARCH;
import static org.apache.commons.lang3.SystemUtils.OS_NAME;

public class RuntimeUtils {
	public static final List<String> vmArguments = Collections.unmodifiableList(ManagementFactory.getRuntimeMXBean().getInputArguments());
	public static final String OS_WINDOWS = "windows";
	public static final String OS_OSX = "osx";
	public static final String OS_SOLARIS = "solaris";
	public static final String OS_LINUX = "linux";
	public static final String ARCH_X86_32 = "x86_32";
	public static final String ARCH_X86_64 = "x86_64";
	public static final String ARCH_PPC = "ppc";

	public static final String OS_DETECTION_NAME;
	public static final String OS_DETECTION_ARCH;
	public static final boolean IS_OS_32BIT;
	public static final boolean IS_OS_64BIT;
	public static final boolean IS_OS_BIG_ENDIAN;
	public static final boolean IS_OS_LITTLE_ENDIAN;
	public static final int JAVA_DETECTION_VERSION;
	public static final boolean IS_JAVA_32BIT;
	public static final boolean IS_JAVA_64BIT;

	static {
		OS_DETECTION_NAME = ((ReferencedCallback<String>) (args) -> {
			if(IS_OS_WINDOWS) return OS_WINDOWS;
			else if(IS_OS_MAC_OSX) return OS_OSX;
			else if(IS_OS_SOLARIS) return OS_SOLARIS;
			else if(IS_OS_LINUX) return OS_LINUX;
			else throw new IllegalArgumentException("Unknown operating system " + OS_NAME);
		}).get();

		OS_DETECTION_ARCH = ((ReferencedCallback<String>) (args) -> {
			switch(OS_ARCH) {
				case "x86":
				case "i386":
				case "i486":
				case "i586":
				case "i686": return ARCH_X86_32;
				case "x86_64":
				case "amd64": return ARCH_X86_64;
				case "powerpc": return ARCH_PPC;
				default: throw new IllegalArgumentException("Unknown architecture " + OS_ARCH);
			}
		}).get();

		IS_OS_32BIT = OS_DETECTION_ARCH.equals(ARCH_X86_32);
		IS_OS_64BIT = OS_DETECTION_ARCH.equals(ARCH_X86_64);
		IS_OS_BIG_ENDIAN = ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;
		IS_OS_LITTLE_ENDIAN = ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN;

		JAVA_DETECTION_VERSION = ((ReferencedCallback<Integer>) (args) -> {
			Matcher matcher = Pattern.compile("(1\\.|)([0-9]+)").matcher(JAVA_VERSION);
			if(!matcher.find()) throw new IllegalArgumentException("Unknown version " + JAVA_VERSION);
			return Integer.parseInt(matcher.group(2));
		}).get();

		IS_JAVA_32BIT = unsafe.addressSize() == 4;
		IS_JAVA_64BIT = unsafe.addressSize() == 8;
	}
}

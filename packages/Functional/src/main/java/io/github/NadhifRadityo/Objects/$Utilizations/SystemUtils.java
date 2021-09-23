package io.github.NadhifRadityo.Objects.$Utilizations;

import io.github.NadhifRadityo.Objects.$Interface.Functional.ReferencedCallback;

import java.lang.reflect.Field;
import java.nio.ByteOrder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SystemUtils extends org.apache.commons.lang3.SystemUtils {
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
		OS_DETECTION_NAME = ((ReferencedCallback.StringReferencedCallback) (args) -> {
			if(IS_OS_WINDOWS) return OS_WINDOWS;
			else if(IS_OS_MAC_OSX) return OS_OSX;
			else if(IS_OS_SOLARIS) return OS_SOLARIS;
			else if(IS_OS_LINUX) return OS_LINUX;
			else throw new IllegalArgumentException("Unknown operating system " + OS_NAME);
		}).get();

		OS_DETECTION_ARCH = ((ReferencedCallback.StringReferencedCallback) (args) -> {
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

		JAVA_DETECTION_VERSION = ((ReferencedCallback.PIntegerReferencedCallback) (args) -> {
			Matcher matcher = Pattern.compile("(1\\.|)([0-9]+)").matcher(JAVA_VERSION);
			if(!matcher.find()) throw new IllegalArgumentException("Unknown version " + JAVA_VERSION);
			return Integer.parseInt(matcher.group(2));
		}).get();

		sun.misc.Unsafe unsafe;
		try {
			Field field = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
			field.setAccessible(true);
			unsafe = (sun.misc.Unsafe) field.get(null);
		} catch(Exception e) { throw new Error(e); }
		IS_JAVA_32BIT = unsafe.addressSize() == 4;
		IS_JAVA_64BIT = unsafe.addressSize() == 8;
	}
}

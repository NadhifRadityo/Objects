package io.github.NadhifRadityo.Objects.Utilizations;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class FStringUtils extends StringUtils {

	static {
		Java9Utils.disableJava9SillyWarning();
	}

	public static FStringUtilsImplementation FImplgetInstance() {
		return new FStringUtilsImplementation() {
			@Override public long getStringValueOffset() throws Exception {
				Field FIELD_String_value = String.class.getDeclaredField("value");
				return UnsafeUtils.R_getUnsafe().objectFieldOffset(FIELD_String_value);
			}
			@Override public long getStringHashOffset() throws Exception {
				Field FIELD_String_hash = String.class.getDeclaredField("hash");
				return UnsafeUtils.R_getUnsafe().objectFieldOffset(FIELD_String_hash);
			}
			@Override public long getStringCoderOffset() throws Exception {
				Field FIELD_String_coder = String.class.getDeclaredField("coder");
				return UnsafeUtils.R_getUnsafe().objectFieldOffset(FIELD_String_coder);
			}
			@Override public boolean getStringCompactStrings() throws Exception {
				Unsafe unsafe = UnsafeUtils.R_getUnsafe();
				Field FIELD_String_COMPACT_STRINGS = String.class.getDeclaredField("COMPACT_STRINGS");
				return unsafe.getBoolean(unsafe.staticFieldBase(FIELD_String_COMPACT_STRINGS), unsafe.staticFieldOffset(FIELD_String_COMPACT_STRINGS));
			}
		};
	}
}

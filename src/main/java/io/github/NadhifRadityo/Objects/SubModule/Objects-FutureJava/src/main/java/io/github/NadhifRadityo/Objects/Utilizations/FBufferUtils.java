package io.github.NadhifRadityo.Objects.Utilizations;

import io.github.NadhifRadityo.Objects.ObjectUtils.Buffer.CustomBuffer.AbstractBuffer;

import java.lang.reflect.Field;
import java.nio.Buffer;

public class FBufferUtils extends BufferUtils {

	static {
		Java9Utils.disableJava9SillyWarning();
	}

//	private static final int MAGIC_POSITION = 0x07177135; // Man... I should really change this
//	private static final int MAGIC_CAPACITY = 0x0000DEAD; // Find for shorter one
//	protected static long getIntFieldOffset(ByteBuffer buffer, int target) {
//		Unsafe unsafe = UnsafeUtils.R_getUnsafe();
//		long offset = 4L;
//		while(true) {
//			if(unsafe.getInt(buffer, offset) == target)
//				return offset;
//			offset += 4L;
//		}
//	}

	// TODO FAR: Implement unsafe for the heck of Java > 8
	public static FBufferUtilsImplementation FImplgetInstance() {
		return new FBufferUtilsImplementation() {
			@Override public long getBufferMarkOffset() throws Exception {
				Field FIELD_Buffer_mark = Buffer.class.getDeclaredField("mark");
				return UnsafeUtils.R_getUnsafe().objectFieldOffset(FIELD_Buffer_mark);
			}
			@Override public long getBufferPositionOffset() throws Exception {
				Field FIELD_Buffer_position = Buffer.class.getDeclaredField("position");
				return UnsafeUtils.R_getUnsafe().objectFieldOffset(FIELD_Buffer_position);
			}
			@Override public long getBufferLimitOffset() throws Exception {
				Field FIELD_Buffer_limit = Buffer.class.getDeclaredField("limit");
				return UnsafeUtils.R_getUnsafe().objectFieldOffset(FIELD_Buffer_limit);
			}
			@Override public long getBufferCapacityOffset() throws Exception {
				Field FIELD_Buffer_capacity = Buffer.class.getDeclaredField("capacity");
				return UnsafeUtils.R_getUnsafe().objectFieldOffset(FIELD_Buffer_capacity);
			}
			@Override public long getBufferAddressOffset() throws Exception {
				Field FIELD_Buffer_address = Buffer.class.getDeclaredField("address");
				return UnsafeUtils.R_getUnsafe().objectFieldOffset(FIELD_Buffer_address);
			}
			@Override public long getAbstractBufferBufferOffset() throws Exception {
				Field FIELD_AbstractBuffer_address = AbstractBuffer.class.getDeclaredField("buffer");
				return UnsafeUtils.R_getUnsafe().objectFieldOffset(FIELD_AbstractBuffer_address);
			}
			@Override public long getAbstractBufferElementSizeOffset() throws Exception {
				Field FIELD_AbstractBuffer_elementSize = AbstractBuffer.class.getDeclaredField("elementSize");
				return UnsafeUtils.R_getUnsafe().objectFieldOffset(FIELD_AbstractBuffer_elementSize);
			}
			@Override public long getAbstractBufferCapacityOffset() throws Exception {
				Field FIELD_AbstractBuffer_capacity = AbstractBuffer.class.getDeclaredField("capacity");
				return UnsafeUtils.R_getUnsafe().objectFieldOffset(FIELD_AbstractBuffer_capacity);
			}
			@Override public long getAbstractBufferPositionOffset() throws Exception {
				Field FIELD_AbstractBuffer_position = AbstractBuffer.class.getDeclaredField("position");
				return UnsafeUtils.R_getUnsafe().objectFieldOffset(FIELD_AbstractBuffer_position);
			}
		};
	}
}

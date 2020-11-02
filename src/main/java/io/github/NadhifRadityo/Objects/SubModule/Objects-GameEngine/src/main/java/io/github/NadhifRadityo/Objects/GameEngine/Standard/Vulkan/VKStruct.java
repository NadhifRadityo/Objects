package io.github.NadhifRadityo.Objects.GameEngine.Standard.Vulkan;

import io.github.NadhifRadityo.Objects.Utilizations.ArrayUtils;
import io.github.NadhifRadityo.Objects.Utilizations.BufferUtils;
import io.github.NadhifRadityo.Objects.Utilizations.ByteUtils;
import io.github.NadhifRadityo.Objects.Utilizations.PrivilegedUtils;
import io.github.NadhifRadityo.Objects.Utilizations.SystemUtils;
import io.github.NadhifRadityo.Objects.Utilizations.UnsafeUtils;
import sun.misc.Unsafe;

import java.nio.Buffer;
import java.nio.ByteBuffer;

public abstract class VKStruct extends VulkanNativeHolder implements VulkanNativeHolder.Properties {
	protected static final Unsafe unsafe = PrivilegedUtils.doPrivileged(UnsafeUtils::getUnsafe);
	protected ByteBuffer buffer;

	public VKStruct(VKContext vk, ByteBuffer buffer) {
		super(vk);
		if(buffer != null)
			ArrayUtils.assertIndex(0, buffer.capacity(), SIZEOF());

		this.buffer = buffer;
	}
	public VKStruct(VKContext vk) { this(vk, (ByteBuffer) null); }
	public VKStruct(VKContext vk, BufferUtils.MemoryStack stack) { this(vk); this.buffer = stack.allocateByteBuffer(SIZEOF()); }

	public ByteBuffer getBuffer() { assertNotDead(); assertCreated(); return buffer; }

	@Value public abstract int SIZEOF();
	protected long getAddress() { return BufferUtils.__getAddress(buffer); }
	protected long getLong(int offset) { assertNotDead(); assertCreated(); return buffer.getLong(offset); }
	protected int getInt(int offset) { assertNotDead(); assertCreated(); return buffer.getInt(offset); }
	protected long getPointer(int offset) { assertNotDead(); assertCreated(); return SystemUtils.IS_JAVA_64BIT ? getLong(offset) : getInt(offset) & 0xFFFF_FFFFL; }
	protected String getString(int offset) { assertNotDead(); assertCreated(); return ByteUtils.toZeroTerminatedString(buffer, offset); }
	protected void putLong(int offset, long value) { assertNotDead(); assertCreated(); buffer.putLong(offset, value); }
	protected void putInt(int offset, int value) { assertNotDead(); assertCreated(); buffer.putInt(offset, value); }
	protected void putPointer(int offset, long value) { assertNotDead(); assertCreated(); if(SystemUtils.IS_JAVA_64BIT) putLong(offset, value); else putInt(offset, (int) value); }

	public void clear() { assertNotDead(); assertCreated(); BufferUtils.empty(buffer, 0, SIZEOF()); }
	public void copyFrom(Buffer buffer, int off) { assertNotDead(); assertCreated(); BufferUtils.copy(buffer, off, this.buffer, 0, SIZEOF()); }
	public void copyTo(Buffer buffer, int off) { assertNotDead(); assertCreated(); BufferUtils.copy(this.buffer, 0, buffer, off, SIZEOF()); }

	@Override protected void arrange() throws Exception {
		if(buffer == null) buffer = BufferUtils.createByteBuffer(SIZEOF());
		getInstance().setCreated();
	}
	@Override protected void disarrange() {
		buffer = null;
	}
}

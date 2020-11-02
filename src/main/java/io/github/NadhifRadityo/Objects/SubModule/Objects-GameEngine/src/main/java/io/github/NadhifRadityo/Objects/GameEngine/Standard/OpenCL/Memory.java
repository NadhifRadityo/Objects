package io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenCL;

import io.github.NadhifRadityo.Objects.Utilizations.BitwiseUtils;
import io.github.NadhifRadityo.Objects.Utilizations.BufferUtils;

import java.nio.Buffer;

public abstract class Memory<ID extends Number, BUFFER extends Buffer> extends OpenCLNativeHolder<ID> {
	protected final int flags;
	protected int size;
	protected BUFFER buffer;

	public Memory(CLContext<ID> cl, int size, BUFFER buffer, int flags) {
		super(cl);
		this.flags = flags;
		this.size = size * BufferUtils.getElementSize(buffer);
		this.buffer = buffer;
	}

	public int getFlags() { return flags; }
	public int[] getConfig() { return BitwiseUtils.extract(flags, getCL().CL_MEM_READ_WRITE(), getCL().CL_MEM_WRITE_ONLY(), getCL().CL_MEM_READ_ONLY(), getCL().CL_MEM_USE_HOST_PTR(), getCL().CL_MEM_ALLOC_HOST_PTR(), getCL().CL_MEM_COPY_HOST_PTR()); }
	public int getCLSize() { return size; }
	public int getCLCapacity() { return size / (buffer == null ? 1 : BufferUtils.getElementSize(buffer)); }
	public BUFFER getBuffer() { return buffer; }
	protected boolean isHostPointerFlag() { return BitwiseUtils.any(flags, getCL().CL_MEM_COPY_HOST_PTR(), getCL().CL_MEM_USE_HOST_PTR()); }
	public boolean isReadOnly() { return BitwiseUtils.is(flags, getCL().CL_MEM_READ_ONLY()); }
	public boolean isWriteOnly() { return BitwiseUtils.is(flags, getCL().CL_MEM_WRITE_ONLY()); }
	public boolean isReadWrite() { return BitwiseUtils.is(flags, getCL().CL_MEM_READ_WRITE()); }
	public int getMapCount() { assertNotDead(); assertCreated(); return (int) getCL().getUInt32Long(getCL().METHOD_clGetMemObjectInfo(), getCL().CL_MEM_MAP_COUNT(), getId()); }
	protected int getSizeImpl() { assertNotDead(); assertCreated(); return (int) getCL().getUInt32Long(getCL().METHOD_clGetMemObjectInfo(), getCL().CL_MEM_SIZE(), getId()); }

	public void addListener(MemoryListener listener, int priority) { assertNotDead(); assertCreated(); getCL().addListener(getId(), listener, priority); }
	public void addListener(MemoryListener listener) { addListener(listener, 0); }
	public void removeListener(MemoryListener listener) { assertNotDead(); assertCreated(); getCL().removeListener(getId(), listener); }

	public Memory<ID, BUFFER> use(BUFFER buffer) { assertNotDead(); assertCreated();
		if(this.buffer != null && buffer != null && this.buffer.getClass() != buffer.getClass())
			throw new IllegalArgumentException("expected a Buffer of class " + this.buffer.getClass() + " but got " + buffer.getClass());
		this.buffer = buffer; return this;
	}

	@Override protected void disarrange() {
		getCL().clReleaseMemObject(getId());
	}

	public interface MemoryListener {
		void memoryDeallocated(Memory<? extends Number, ? extends Buffer> mem);
	}
}

package io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenCL;

import io.github.NadhifRadityo.Objects.Utilizations.BufferUtils;

import java.nio.ByteBuffer;

public class Kernel<ID extends Number> extends OpenCLNativeHolder<ID> {
	protected final static boolean IS_32_BIT = com.jogamp.common.os.Platform.is32Bit();
	protected final static int DEFAULT_TEMP_BUFFER_CAP = 3 * (IS_32_BIT ? 4 : 8);

	protected final Program<ID> parent;
	protected final String name;
	protected final int argsLength;

	protected ByteBuffer tempBuffer;
	protected int argIndex;
	protected boolean force32BitArgs;

	protected Kernel(Program<ID> parent, ID id) {
		super(parent.getCL());
		getInstance().setId(id);
		this.parent = parent;
		this.name = getCL().getString(getCL().METHOD_clGetKernelInfo(), getCL().CL_KERNEL_FUNCTION_NAME(), getId());
		this.argsLength = (int) getCL().getUInt32Long(getCL().METHOD_clGetKernelInfo(), getCL().CL_KERNEL_NUM_ARGS(), getId());
		parent.attachKernel(this);
	}

	public Program<ID> getParent() { return parent; }
	public String getName() { return name; }
	public int getArgsLength() { return argsLength; }
	public int position() { return argIndex; }
	public boolean isForce32BitArgsEnabled() { return force32BitArgs; }
	public long getLocalMemorySize(Device<ID> device) { assertNotDead(); assertCreated(); return getCL().getLong(getCL().METHOD_clGetKernelWorkGroupInfo(), getCL().CL_KERNEL_LOCAL_MEM_SIZE(), getId(), device.getId()); }
	public long getWorkGroupSize(Device<ID> device) { assertNotDead(); assertCreated(); return getCL().getLong(getCL().METHOD_clGetKernelWorkGroupInfo(), getCL().CL_KERNEL_WORK_GROUP_SIZE(), getId(), device.getId()); }
	public long[] getCompileWorkGroupSize(Device<ID> device) { assertNotDead(); assertCreated(); return getCL().getLongs(getCL().METHOD_clGetKernelWorkGroupInfo(), getCL().CL_KERNEL_COMPILE_WORK_GROUP_SIZE(), getId(), device.getId(), 3); }

	public Kernel setForce32BitArgs(boolean force32BitArgs) { this.force32BitArgs = force32BitArgs; return this; }
	public Kernel rewind() { argIndex = 0; return this; }
	public Kernel putArg(Memory<ID, ?> value) { setArg(argIndex++, value); return this; }
	public Kernel putArg(int value) { setArg(argIndex++, value); return this; }
	public Kernel putArg(long value) { setArg(argIndex++, value); return this; }
	public Kernel putArg(short value) { setArg(argIndex++, value); return this; }
	public Kernel putArg(float value) { setArg(argIndex++, value); return this; }
	public Kernel putArg(double value) { setArg(argIndex++, value); return this; }
	public Kernel putNullArg(int size) { setNullArg(argIndex++, size); return this; }
	public Kernel putArgs(Object... values) { setArgs(argIndex, values); argIndex += values.length; return this; }
	public Kernel setArg(int argumentIndex, Memory<ID, ?> value) { setArgImpl(argumentIndex, IS_32_BIT ? 4 : 8, wrap(value.getId())); return this; }
	public Kernel setArg(int argumentIndex, int value) { setArgImpl(argumentIndex, 4, wrap(value)); return this; }
	public Kernel setArg(int argumentIndex, long value) { if(force32BitArgs) setArgImpl(argumentIndex, 4, wrap((int) value)); else setArgImpl(argumentIndex, 8, wrap(value)); return this; }
	public Kernel setArg(int argumentIndex, short value) { setArgImpl(argumentIndex, 2, wrap(value)); return this; }
	public Kernel setArg(int argumentIndex, float value) { setArgImpl(argumentIndex, 4, wrap(value)); return this; }
	public Kernel setArg(int argumentIndex, double value) { if(force32BitArgs) setArgImpl(argumentIndex, 4, wrap((float) value)); else setArgImpl(argumentIndex, 8, wrap(value)); return this; }
	public Kernel setNullArg(int argumentIndex, int size) { setArgImpl(argumentIndex, size, null); return this; }
	public Kernel setArgs(Object... values) { setArgs(argIndex, values); argIndex = values.length; return this; }

	private java.nio.Buffer wrap(ID value) { return wrap(value.longValue()); }
	private java.nio.Buffer wrap(int value) { prepareTempBuffer(4); return tempBuffer.putInt(0, value); }
	private java.nio.Buffer wrap(long value) { prepareTempBuffer(8); return tempBuffer.putLong(0, value); }
	private java.nio.Buffer wrap(short value) { prepareTempBuffer(2); return tempBuffer.putShort(0, value); }
	private java.nio.Buffer wrap(float value) { prepareTempBuffer(4); return tempBuffer.putFloat(0, value); }
	private java.nio.Buffer wrap(double value) { prepareTempBuffer(8); return tempBuffer.putDouble(0, value); }
	public Kernel setArgs(int argumentIndex, Object... values) {
		if(values == null || values.length == 0) throw new IllegalArgumentException("values array was empty or null.");
		for(int i = 0; i < values.length; i++) { Object value = values[i];
			if(value instanceof Memory<?, ?>) setArg(argumentIndex + i, (Memory<ID, ?>) value);
			else if(value instanceof Integer) setArg(argumentIndex + i, (Integer) value);
			else if(value instanceof Long) setArg(argumentIndex + i, (Long) value);
			else if(value instanceof Short) setArg(argumentIndex + i, (Short) value);
			else if(value instanceof Float) setArg(argumentIndex + i, (Float) value);
			else if(value instanceof Double) setArg(argumentIndex + i, (Double) value);
			else throw new IllegalArgumentException(value + " is not a valid argument.");
		} return this;
	}

	protected void prepareTempBuffer(int size) { size = Math.max(size, DEFAULT_TEMP_BUFFER_CAP); if(tempBuffer != null && tempBuffer.capacity() >= size) return; if(tempBuffer != null) destroyTempBuffer(); tempBuffer = BufferUtils.allocateByteBuffer(size); }
	protected void destroyTempBuffer() { if(tempBuffer == null) return; BufferUtils.deallocate(tempBuffer); tempBuffer = null; }
	protected void setArgImpl(int argumentIndex, int size, java.nio.Buffer buffer) {
		assertNotDead(); assertCreated(); if(buffer != null && tempBuffer == null) throw newException("buffer not accepted");
		if(argumentIndex >= argsLength || argumentIndex < 0) throw newException("kernel " + this + " has " + argsLength + " arguments, can not set argument with index " + argumentIndex);
		if(!parent.isExecutable()) throw new IllegalStateException("can not set program arguments for a not executable program. " + parent);
		getCL().clSetKernelArg(getId(), argumentIndex, size, buffer); if(argumentIndex == argsLength - 1) destroyTempBuffer();
	}

	@Override protected void arrange() throws Exception { throw new IllegalAccessException(); }
	@Override protected void disarrange() {
		boolean isParentCaller = false;
		String parentClassName = Program.class.getCanonicalName();
		StackTraceElement[] traces = Thread.currentThread().getStackTrace();
		for(int i = 1; i <= 5; i++) {
			if(!traces[i].getClassName().equals(parentClassName) || !traces[i].getMethodName().equals("disarrange")) continue;
			isParentCaller = true; break;
		} if(!isParentCaller) throw new IllegalStateException();
		parent.detachKernel(this);
		if(tempBuffer != null) destroyTempBuffer();
		getCL().clReleaseKernel(getId());
	}
}

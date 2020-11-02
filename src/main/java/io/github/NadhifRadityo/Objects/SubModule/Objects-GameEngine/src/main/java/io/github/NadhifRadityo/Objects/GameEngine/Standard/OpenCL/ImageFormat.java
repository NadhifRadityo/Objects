package io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenCL;

import io.github.NadhifRadityo.Objects.Utilizations.BufferUtils;

import java.nio.ByteBuffer;

public class ImageFormat<ID extends Number, SELF extends ImageFormat> extends OpenCLNativeHolder<ID> {
	public static final int CHANNEL_ORDER = 0;
	public static final int CHANNEL_DATA_TYPE = 1;

	protected final ByteBuffer buffer;
	protected final boolean deallocate;

	public ImageFormat(CLContext<ID> cl, ByteBuffer buffer, boolean deallocate) {
		super(cl);
		this.buffer = buffer;
		this.deallocate = deallocate;
		create();
	}
	public ImageFormat(CLContext<ID> cl, ByteBuffer buffer) { this(cl, buffer, true); }
	public ImageFormat(CLContext<ID> cl, int channelOrder, int channelDataType) { this(cl, wrap(channelOrder, channelDataType), true); }

	public ByteBuffer getBuffer() { return buffer; }
	public boolean isDeallocate() { return deallocate; }

	public int getImageChannelOrder() { return buffer.getInt(CHANNEL_ORDER); }
	public int getImageChannelDataType() { return buffer.getInt(CHANNEL_DATA_TYPE); }
	public void setImageChannelOrder(int channelOrder) { buffer.putInt(CHANNEL_ORDER, channelOrder); }
	public void setImageChannelDataType(int channelDataType) { buffer.putInt(CHANNEL_DATA_TYPE, channelDataType); }

	@Override protected void arrange() throws Exception {
		getInstance().setId((ID) (Integer) buffer.hashCode());
	}
	@Override protected void disarrange() {
		if(deallocate) BufferUtils.deallocate(buffer);
	}

	private static ByteBuffer wrap(int channelOrder, int channelDataType) {
		ByteBuffer buffer = BufferUtils.allocateByteBuffer(2 << 2);
		buffer.putInt(CHANNEL_ORDER, channelOrder).putInt(CHANNEL_DATA_TYPE, channelDataType);
		return buffer;
	}
}

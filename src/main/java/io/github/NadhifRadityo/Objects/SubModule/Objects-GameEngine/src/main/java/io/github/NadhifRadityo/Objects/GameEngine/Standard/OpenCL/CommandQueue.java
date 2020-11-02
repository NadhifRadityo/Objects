package io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenCL;

import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.LVec2;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.LVec3;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.LVecN;
import io.github.NadhifRadityo.Objects.ObjectUtils.Buffer.CustomBuffer.PointerBuffer;
import io.github.NadhifRadityo.Objects.Utilizations.BitwiseUtils;
import io.github.NadhifRadityo.Objects.Utilizations.BufferUtils;

import java.nio.ByteBuffer;

import static io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenCL.Buffer.ImageBuffer;
import static io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenCL.Buffer.ImageBuffer.Image2DBuffer;
import static io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenCL.Buffer.ImageBuffer.Image3DBuffer;

public class CommandQueue<ID extends Number, SELF extends CommandQueue> extends OpenCLNativeHolder<ID> {
	protected final Device<ID> device;
	protected final int properties;

	public CommandQueue(Device<ID> device, int properties) {
		super(device.getCL());
		this.device = device;
		this.properties = properties;
	}
	public CommandQueue(Device<ID> device, int... properties) {
		this(device, BitwiseUtils.or(properties));
	}

	public Device<ID> getDevice() { return device; }

	public SELF putWriteBuffer(Buffer<ID, ?> writeBuffer, boolean blockingWrite) { return putWriteBuffer(writeBuffer, blockingWrite, 0); }
	public SELF putWriteBuffer(Buffer<ID, ?> writeBuffer, boolean blockingWrite, int offset) { return putWriteBuffer(writeBuffer, blockingWrite, offset, null); }
	public SELF putWriteBuffer(Buffer<ID, ?> writeBuffer, boolean blockingWrite, int offset, PointerBuffer event) { return putWriteBuffer(writeBuffer, blockingWrite, offset, null, event); }
	public SELF putWriteBuffer(Buffer<ID, ?> writeBuffer, boolean blockingWrite, int offset, PointerBuffer eventWaitList, PointerBuffer event) { assertNotDead(); assertCreated(); getCL().clEnqueueWriteBuffer(getId(), writeBuffer, blockingWrite, offset, eventWaitList, event); return (SELF) this; }

	public SELF putReadBuffer(Buffer<ID, ?> readBuffer, boolean blockingRead) { return putReadBuffer(readBuffer, blockingRead, 0); }
	public SELF putReadBuffer(Buffer<ID, ?> readBuffer, boolean blockingRead, int offset) { return putReadBuffer(readBuffer, blockingRead, offset, null); }
	public SELF putReadBuffer(Buffer<ID, ?> readBuffer, boolean blockingRead, int offset, PointerBuffer event) { return putReadBuffer(readBuffer, blockingRead, offset, null, event); }
	public SELF putReadBuffer(Buffer<ID, ?> readBuffer, boolean blockingRead, int offset, PointerBuffer eventWaitList, PointerBuffer event) { assertNotDead(); assertCreated(); getCL().clEnqueueReadBuffer(getId(), readBuffer, blockingRead, offset, eventWaitList, event); return (SELF) this; }

	public SELF putCopyBuffer(Buffer<ID, ?> src, Buffer<ID, ?> dst) { return putCopyBuffer(src, dst, src.getCLSize()); }
	public SELF putCopyBuffer(Buffer<ID, ?> src, Buffer<ID, ?> dst, int size) { return putCopyBuffer(src, dst, 0, 0, size); }
	public SELF putCopyBuffer(Buffer<ID, ?> src, Buffer<ID, ?> dst, int srcOffset, int dstOffset, int size) { return putCopyBuffer(src, dst, srcOffset, dstOffset, size, null); }
	public SELF putCopyBuffer(Buffer<ID, ?> src, Buffer<ID, ?> dst, int srcOffset, int dstOffset, int size, PointerBuffer event) { return putCopyBuffer(src, dst, srcOffset, dstOffset, size, null, event); }
	public SELF putCopyBuffer(Buffer<ID, ?> src, Buffer<ID, ?> dst, int srcOffset, int dstOffset, int size, PointerBuffer eventWaitList, PointerBuffer event) { assertNotDead(); assertCreated(); getCL().clEnqueueCopyBuffer(getId(), src, dst, srcOffset, dstOffset, size, eventWaitList, event); return (SELF) this; }

	public SELF putWriteBufferRect(Buffer<ID, ?> writeBuffer, LVec2 origin, LVec2 host, LVec2 range, boolean blockingWrite) { return putWriteBufferRect(writeBuffer, origin, host, range, 0, 0, 0, 0, blockingWrite); }
	public SELF putWriteBufferRect(Buffer<ID, ?> writeBuffer, LVec2 origin, LVec2 host, LVec2 range, long rowPitch, long slicePitch, long hostRowPitch, long hostSlicePitch, boolean blockingWrite) { return putWriteBufferRect(writeBuffer, origin, host, range, rowPitch, slicePitch, hostRowPitch, hostSlicePitch, blockingWrite, null); }
	public SELF putWriteBufferRect(Buffer<ID, ?> writeBuffer, LVec2 origin, LVec2 host, LVec2 range, long rowPitch, long slicePitch, long hostRowPitch, long hostSlicePitch, boolean blockingWrite, PointerBuffer event) { return putWriteBufferRect(writeBuffer, origin, host, range, rowPitch, slicePitch, hostRowPitch, hostSlicePitch, blockingWrite, null, event); }
	public SELF putWriteBufferRect(Buffer<ID, ?> writeBuffer, LVec2 origin, LVec2 host, LVec2 range, long rowPitch, long slicePitch, long hostRowPitch, long hostSlicePitch, boolean blockingWrite, PointerBuffer eventWaitList, PointerBuffer event) { return putWriteBufferRect(writeBuffer, new LVec3(origin.x(), origin.y(), 0), new LVec3(host.x(), host.y(), 0), new LVec3(range.x(), range.y(), 1), rowPitch, slicePitch, hostRowPitch, hostSlicePitch, blockingWrite, eventWaitList, event); }

	public SELF putWriteBufferRect(Buffer<ID, ?> writeBuffer, LVec3 origin, LVec3 host, LVec3 range, boolean blockingWrite) { return putWriteBufferRect(writeBuffer, origin, host, range, 0, 0, 0, 0, blockingWrite); }
	public SELF putWriteBufferRect(Buffer<ID, ?> writeBuffer, LVec3 origin, LVec3 host, LVec3 range, long rowPitch, long slicePitch, long hostRowPitch, long hostSlicePitch, boolean blockingWrite) { return putWriteBufferRect(writeBuffer, origin, host, range, rowPitch, slicePitch, hostRowPitch, hostSlicePitch, blockingWrite, null); }
	public SELF putWriteBufferRect(Buffer<ID, ?> writeBuffer, LVec3 origin, LVec3 host, LVec3 range, long rowPitch, long slicePitch, long hostRowPitch, long hostSlicePitch, boolean blockingWrite, PointerBuffer event) { return putWriteBufferRect(writeBuffer, origin, host, range, rowPitch, slicePitch, hostRowPitch, hostSlicePitch, blockingWrite, null, event); }
	public SELF putWriteBufferRect(Buffer<ID, ?> writeBuffer, LVec3 origin, LVec3 host, LVec3 range, long rowPitch, long slicePitch, long hostRowPitch, long hostSlicePitch, boolean blockingWrite, PointerBuffer eventWaitList, PointerBuffer event) { assertNotDead(); assertCreated(); getCL().clEnqueueWriteBufferRect(getId(), writeBuffer, origin, host, range, rowPitch, slicePitch, hostRowPitch, hostSlicePitch, blockingWrite, eventWaitList, event); return (SELF) this; }

	public SELF putReadBufferRect(Buffer<ID, ?> readBuffer, LVec2 origin, LVec2 host, LVec2 range, boolean blockingRead) { return putReadBufferRect(readBuffer, origin, host, range, 0, 0, 0, 0, blockingRead); }
	public SELF putReadBufferRect(Buffer<ID, ?> readBuffer, LVec2 origin, LVec2 host, LVec2 range, long rowPitch, long slicePitch, long hostRowPitch, long hostSlicePitch, boolean blockingRead) { return putReadBufferRect(readBuffer, origin, host, range, rowPitch, slicePitch, hostRowPitch, hostSlicePitch, blockingRead, null); }
	public SELF putReadBufferRect(Buffer<ID, ?> readBuffer, LVec2 origin, LVec2 host, LVec2 range, long rowPitch, long slicePitch, long hostRowPitch, long hostSlicePitch, boolean blockingRead, PointerBuffer event) { return putReadBufferRect(readBuffer, origin, host, range, rowPitch, slicePitch, hostRowPitch, hostSlicePitch, blockingRead, null, event); }
	public SELF putReadBufferRect(Buffer<ID, ?> readBuffer, LVec2 origin, LVec2 host, LVec2 range, long rowPitch, long slicePitch, long hostRowPitch, long hostSlicePitch, boolean blockingRead, PointerBuffer eventWaitList, PointerBuffer event) { return putReadBufferRect(readBuffer, new LVec3(origin.x(), origin.y(), 0), new LVec3(host.x(), host.y(), 0), new LVec3(range.x(), range.y(), 1), rowPitch, slicePitch, hostRowPitch, hostSlicePitch, blockingRead, eventWaitList, event); }

	public SELF putReadBufferRect(Buffer<ID, ?> readBuffer, LVec3 origin, LVec3 host, LVec3 range, boolean blockingRead) { return putReadBufferRect(readBuffer, origin, host, range, 0, 0, 0, 0, blockingRead); }
	public SELF putReadBufferRect(Buffer<ID, ?> readBuffer, LVec3 origin, LVec3 host, LVec3 range, long rowPitch, long slicePitch, long hostRowPitch, long hostSlicePitch, boolean blockingRead) { return putReadBufferRect(readBuffer, origin, host, range, rowPitch, slicePitch, hostRowPitch, hostSlicePitch, blockingRead, null); }
	public SELF putReadBufferRect(Buffer<ID, ?> readBuffer, LVec3 origin, LVec3 host, LVec3 range, long rowPitch, long slicePitch, long hostRowPitch, long hostSlicePitch, boolean blockingRead, PointerBuffer event) { return putReadBufferRect(readBuffer, origin, host, range, rowPitch, slicePitch, hostRowPitch, hostSlicePitch, blockingRead, null, event); }
	public SELF putReadBufferRect(Buffer<ID, ?> readBuffer, LVec3 origin, LVec3 host, LVec3 range, long rowPitch, long slicePitch, long hostRowPitch, long hostSlicePitch, boolean blockingRead, PointerBuffer eventWaitList, PointerBuffer event) { assertNotDead(); assertCreated(); getCL().clEnqueueReadBufferRect(getId(), readBuffer, origin, host, range, rowPitch, slicePitch, hostRowPitch, hostSlicePitch, blockingRead, eventWaitList, event); return (SELF) this; }

	public SELF putCopyBufferRect(Buffer<ID, ?> src, Buffer<ID, ?> dst, LVec2 srcOrigin, LVec2 dstOrigin, LVec2 range) { return putCopyBufferRect(src, dst, srcOrigin, dstOrigin, range, 0, 0, 0, 0); }
	public SELF putCopyBufferRect(Buffer<ID, ?> src, Buffer<ID, ?> dst, LVec2 srcOrigin, LVec2 dstOrigin, LVec2 range, long srcRowPitch, long srcSlicePitch, long dstRowPitch, long dstSlicePitch) { return putCopyBufferRect(src, dst, srcOrigin, dstOrigin, range, srcRowPitch, srcSlicePitch, dstRowPitch, dstSlicePitch, null); }
	public SELF putCopyBufferRect(Buffer<ID, ?> src, Buffer<ID, ?> dst, LVec2 srcOrigin, LVec2 dstOrigin, LVec2 range, long srcRowPitch, long srcSlicePitch, long dstRowPitch, long dstSlicePitch, PointerBuffer event) { return putCopyBufferRect(src, dst, srcOrigin, dstOrigin, range, srcRowPitch, srcSlicePitch, dstRowPitch, dstSlicePitch, null, event); }
	public SELF putCopyBufferRect(Buffer<ID, ?> src, Buffer<ID, ?> dst, LVec2 srcOrigin, LVec2 dstOrigin, LVec2 range, long srcRowPitch, long srcSlicePitch, long dstRowPitch, long dstSlicePitch, PointerBuffer eventWaitList, PointerBuffer event) { return putCopyBufferRect(src, dst, new LVec3(srcOrigin.x(), srcOrigin.y(), 0), new LVec3(dstOrigin.x(), dstOrigin.y(), 0), new LVec3(range.x(), range.y(), 1), srcRowPitch, srcSlicePitch, dstRowPitch, dstSlicePitch, eventWaitList, event); }

	public SELF putCopyBufferRect(Buffer<ID, ?> src, Buffer<ID, ?> dst, LVec3 srcOrigin, LVec3 dstOrigin, LVec3 range) { return putCopyBufferRect(src, dst, srcOrigin, dstOrigin, range, 0, 0, 0, 0); }
	public SELF putCopyBufferRect(Buffer<ID, ?> src, Buffer<ID, ?> dst, LVec3 srcOrigin, LVec3 dstOrigin, LVec3 range, long srcRowPitch, long srcSlicePitch, long dstRowPitch, long dstSlicePitch) { return putCopyBufferRect(src, dst, srcOrigin, dstOrigin, range, srcRowPitch, srcSlicePitch, dstRowPitch, dstSlicePitch, null); }
	public SELF putCopyBufferRect(Buffer<ID, ?> src, Buffer<ID, ?> dst, LVec3 srcOrigin, LVec3 dstOrigin, LVec3 range, long srcRowPitch, long srcSlicePitch, long dstRowPitch, long dstSlicePitch, PointerBuffer event) { return putCopyBufferRect(src, dst, srcOrigin, dstOrigin, range, srcRowPitch, srcSlicePitch, dstRowPitch, dstSlicePitch, null, event); }
	public SELF putCopyBufferRect(Buffer<ID, ?> src, Buffer<ID, ?> dst, LVec3 srcOrigin, LVec3 dstOrigin, LVec3 range, long srcRowPitch, long srcSlicePitch, long dstRowPitch, long dstSlicePitch, PointerBuffer eventWaitList, PointerBuffer event) { assertNotDead(); assertCreated(); getCL().clEnqueueCopyBufferRect(getId(), src, dst, srcOrigin, dstOrigin, range, srcRowPitch, srcSlicePitch, dstRowPitch, dstSlicePitch, eventWaitList, event); return (SELF) this; }

	public SELF putWriteImage(Image2DBuffer<ID, ?> writeImage, boolean blockingWrite) { return putWriteImage(writeImage, 0, 0, new LVec2(0, 0), writeImage.getSize(), blockingWrite); }
	public SELF putWriteImage(Image2DBuffer<ID, ?> writeImage, int inputRowPitch, int inputSlicePitch, LVec2 origin, LVec2 range, boolean blockingWrite) { return putWriteImage(writeImage, inputRowPitch, inputSlicePitch, origin, range, blockingWrite, null); }
	public SELF putWriteImage(Image2DBuffer<ID, ?> writeImage, int inputRowPitch, int inputSlicePitch, LVec2 origin, LVec2 range, boolean blockingWrite, PointerBuffer event) { return putWriteImage(writeImage, inputRowPitch, inputSlicePitch, origin, range, blockingWrite, null, event); }
	public SELF putWriteImage(Image2DBuffer<ID, ?> writeImage, int inputRowPitch, int inputSlicePitch, LVec2 origin, LVec2 range, boolean blockingWrite, PointerBuffer eventWaitList, PointerBuffer event) { return putWriteImage((ImageBuffer<ID, ?>) writeImage, inputRowPitch, inputSlicePitch, new LVec3(origin.x(), origin.y(), 0), new LVec3(range.x(), range.y(), 1), blockingWrite, eventWaitList, event); }

	public SELF putWriteImage(Image3DBuffer<ID, ?> writeImage, boolean blockingWrite) { return putWriteImage(writeImage, 0, 0, new LVec3(0, 0, 0), new LVec3(writeImage.getSize().x(), writeImage.getSize().y(), writeImage.getDepth()), blockingWrite); }
	public SELF putWriteImage(Image3DBuffer<ID, ?> writeImage, int inputRowPitch, int inputSlicePitch, LVec3 origin, LVec3 range, boolean blockingWrite) { return putWriteImage(writeImage, inputRowPitch, inputSlicePitch, origin, range, blockingWrite, null); }
	public SELF putWriteImage(Image3DBuffer<ID, ?> writeImage, int inputRowPitch, int inputSlicePitch, LVec3 origin, LVec3 range, boolean blockingWrite, PointerBuffer event) { return putWriteImage(writeImage, inputRowPitch, inputSlicePitch, origin, range, blockingWrite, null, event); }
	public SELF putWriteImage(Image3DBuffer<ID, ?> writeImage, int inputRowPitch, int inputSlicePitch, LVec3 origin, LVec3 range, boolean blockingWrite, PointerBuffer eventWaitList, PointerBuffer event) { return putWriteImage((ImageBuffer<ID, ?>) writeImage, inputRowPitch, inputSlicePitch, origin, range, blockingWrite, eventWaitList, event); }
	public SELF putWriteImage(ImageBuffer<ID, ?> writeImage, int inputRowPitch, int inputSlicePitch, LVec3 origin, LVec3 range, boolean blockingWrite, PointerBuffer eventWaitList, PointerBuffer event) { assertNotDead(); assertCreated(); getCL().clEnqueueWriteImage(getId(), writeImage, inputRowPitch, inputSlicePitch, origin, range, blockingWrite, eventWaitList, event); return (SELF) this; }

	public SELF putReadImage(Image2DBuffer<ID, ?> readImage, boolean blockingRead) { return putReadImage(readImage, 0, 0, new LVec2(0, 0), readImage.getSize(), blockingRead); }
	public SELF putReadImage(Image2DBuffer<ID, ?> readImage, int rowPitch, int slicePitch, LVec2 origin, LVec2 range, boolean blockingRead) { return putReadImage(readImage, rowPitch, slicePitch, origin, range, blockingRead, null); }
	public SELF putReadImage(Image2DBuffer<ID, ?> readImage, int rowPitch, int slicePitch, LVec2 origin, LVec2 range, boolean blockingRead, PointerBuffer event) { return putReadImage(readImage, rowPitch, slicePitch, origin, range, blockingRead, null, event); }
	public SELF putReadImage(Image2DBuffer<ID, ?> readImage, int rowPitch, int slicePitch, LVec2 origin, LVec2 range, boolean blockingRead, PointerBuffer eventWaitList, PointerBuffer event) { return putReadImage((ImageBuffer<ID, ?>) readImage, rowPitch, slicePitch, new LVec3(origin.x(), origin.y(), 0), new LVec3(range.x(), range.y(), 1), blockingRead, eventWaitList, event); }

	public SELF putReadImage(Image3DBuffer<ID, ?> readImage, boolean blockingRead) { return putReadImage(readImage, 0, 0, new LVec3(0, 0, 0), new LVec3(readImage.getSize().x(), readImage.getSize().y(), readImage.getDepth()), blockingRead); }
	public SELF putReadImage(Image3DBuffer<ID, ?> readImage, int rowPitch, int slicePitch, LVec3 origin, LVec3 range, boolean blockingRead) { return putReadImage(readImage, rowPitch, slicePitch, origin, range, blockingRead, null); }
	public SELF putReadImage(Image3DBuffer<ID, ?> readImage, int rowPitch, int slicePitch, LVec3 origin, LVec3 range, boolean blockingRead, PointerBuffer event) { return putReadImage(readImage, rowPitch, slicePitch, origin, range, blockingRead, null, event); }
	public SELF putReadImage(Image3DBuffer<ID, ?> readImage, int rowPitch, int slicePitch, LVec3 origin, LVec3 range, boolean blockingRead, PointerBuffer eventWaitList, PointerBuffer event) { return putReadImage((ImageBuffer<ID, ?>) readImage, rowPitch, slicePitch, origin, range, blockingRead, eventWaitList, event); }
	public SELF putReadImage(ImageBuffer<ID, ?> readImage, int rowPitch, int slicePitch, LVec3 origin, LVec3 range, boolean blockingRead, PointerBuffer eventWaitList, PointerBuffer event) { assertNotDead(); assertCreated(); getCL().clEnqueueReadImage(getId(), readImage, rowPitch, slicePitch, origin, range, blockingRead, eventWaitList, event); return (SELF) this; }

	public SELF putCopyImage(Image2DBuffer<ID, ?> srcImage, Image2DBuffer<ID, ?> dstImage) { return putCopyImage(srcImage, dstImage, new LVec2(0, 0), new LVec2(0, 0), srcImage.getSize(), null); }
	public SELF putCopyImage(Image2DBuffer<ID, ?> srcImage, Image2DBuffer<ID, ?> dstImage, LVec2 srcOrigin, LVec2 dstOrigin, LVec2 range, PointerBuffer event) { return putCopyImage(srcImage, dstImage, srcOrigin, dstOrigin, range, null, event); }
	public SELF putCopyImage(Image2DBuffer<ID, ?> srcImage, Image2DBuffer<ID, ?> dstImage, LVec2 srcOrigin, LVec2 dstOrigin, LVec2 range, PointerBuffer eventWaitList, PointerBuffer event) { return putCopyImage((ImageBuffer<ID, ?>) srcImage, dstImage, new LVec3(srcOrigin.x(), srcOrigin.y(), 0), new LVec3(dstOrigin.x(), dstOrigin.y(), 0), new LVec3(range.x(), range.y(), 1), eventWaitList, event); }

	public SELF putCopyImage(Image3DBuffer<ID, ?> srcImage, Image3DBuffer<ID, ?> dstImage) { return putCopyImage(srcImage, dstImage, new LVec3(0, 0, 0), new LVec3(0, 0, 0), new LVec3(srcImage.getSize().x(), srcImage.getSize().y(), srcImage.getDepth()), null); }
	public SELF putCopyImage(Image3DBuffer<ID, ?> srcImage, Image3DBuffer<ID, ?> dstImage, LVec3 srcOrigin, LVec3 dstOrigin, LVec3 range, PointerBuffer event) { return putCopyImage(srcImage, dstImage, srcOrigin, dstOrigin, range, null, event); }
	public SELF putCopyImage(Image3DBuffer<ID, ?> srcImage, Image3DBuffer<ID, ?> dstImage, LVec3 srcOrigin, LVec3 dstOrigin, LVec3 range, PointerBuffer eventWaitList, PointerBuffer event) { return putCopyImage((ImageBuffer<ID, ?>) srcImage, dstImage, srcOrigin, dstOrigin, range, eventWaitList, event); }
	public SELF putCopyImage(ImageBuffer<ID, ?> srcImage, ImageBuffer<ID, ?> dstImage, LVec3 srcOrigin, LVec3 dstOrigin, LVec3 range, PointerBuffer eventWaitList, PointerBuffer event) { assertNotDead(); assertCreated(); getCL().clEnqueueCopyImage(getId(), srcImage, dstImage, srcOrigin, dstOrigin, range, eventWaitList, event); return (SELF) this; }

	public SELF putCopyBufferToImage(Buffer<ID, ?> srcBuffer, Image2DBuffer<ID, ?> dstImage) { return putCopyBufferToImage(srcBuffer, dstImage, 0, new LVec2(0, 0), new LVec2(dstImage.getSize().x(), dstImage.getSize().y())); }
	public SELF putCopyBufferToImage(Buffer<ID, ?> srcBuffer, Image2DBuffer<ID, ?> dstImage, int srcOffset, LVec2 dstOrigin, LVec2 range) { return putCopyBufferToImage(srcBuffer, dstImage, srcOffset, dstOrigin, range, null); }
	public SELF putCopyBufferToImage(Buffer<ID, ?> srcBuffer, Image2DBuffer<ID, ?> dstImage, int srcOffset, LVec2 dstOrigin, LVec2 range, PointerBuffer event) { return putCopyBufferToImage(srcBuffer, dstImage, srcOffset, dstOrigin, range, null, event); }
	public SELF putCopyBufferToImage(Buffer<ID, ?> srcBuffer, Image2DBuffer<ID, ?> dstImage, int srcOffset, LVec2 dstOrigin, LVec2 range, PointerBuffer eventWaitList, PointerBuffer event) { return putCopyBufferToImage(srcBuffer, (ImageBuffer<ID, ?>) dstImage, srcOffset, new LVec3(dstOrigin.x(), dstOrigin.y(), 0), new LVec3(range.x(), range.y(), 1), eventWaitList, event); }

	public SELF putCopyBufferToImage(Buffer<ID, ?> srcBuffer, Image3DBuffer<ID, ?> dstImage) { return putCopyBufferToImage(srcBuffer, dstImage, 0, new LVec3(0, 0, 0), new LVec3(dstImage.getSize().x(), dstImage.getSize().y(), dstImage.getDepth())); }
	public SELF putCopyBufferToImage(Buffer<ID, ?> srcBuffer, Image3DBuffer<ID, ?> dstImage, int srcOffset, LVec3 dstOrigin, LVec3 range) { return putCopyBufferToImage(srcBuffer, dstImage, srcOffset, dstOrigin, range, null); }
	public SELF putCopyBufferToImage(Buffer<ID, ?> srcBuffer, Image3DBuffer<ID, ?> dstImage, int srcOffset, LVec3 dstOrigin, LVec3 range, PointerBuffer event) { return putCopyBufferToImage(srcBuffer, dstImage, srcOffset, dstOrigin, range, null, event); }
	public SELF putCopyBufferToImage(Buffer<ID, ?> srcBuffer, Image3DBuffer<ID, ?> dstImage, int srcOffset, LVec3 dstOrigin, LVec3 range, PointerBuffer eventWaitList, PointerBuffer event) { return putCopyBufferToImage(srcBuffer, (ImageBuffer<ID, ?>) dstImage, srcOffset, dstOrigin, range, eventWaitList, event); }
	public SELF putCopyBufferToImage(Buffer<ID, ?> srcBuffer, ImageBuffer<ID, ?> dstImage, int srcOffset, LVec3 dstOrigin, LVec3 range, PointerBuffer eventWaitList, PointerBuffer event) { assertNotDead(); assertCreated(); getCL().clEnqueueCopyBufferToImage(getId(), srcBuffer, dstImage, srcOffset, dstOrigin, range, eventWaitList, event); return (SELF) this; }

	public SELF putCopyImageToBuffer(Image2DBuffer<ID, ?> srcImage, Buffer<ID, ?> dstBuffer) { return putCopyImageToBuffer(srcImage, dstBuffer, new LVec2(0, 0), srcImage.getSize(), 0); }
	public SELF putCopyImageToBuffer(Image2DBuffer<ID, ?> srcImage, Buffer<ID, ?> dstBuffer, LVec2 srcOrigin, LVec2 range, int dstOffset) { return putCopyImageToBuffer(srcImage, dstBuffer, srcOrigin, range, dstOffset, null); }
	public SELF putCopyImageToBuffer(Image2DBuffer<ID, ?> srcImage, Buffer<ID, ?> dstBuffer, LVec2 srcOrigin, LVec2 range, int dstOffset, PointerBuffer event) { return putCopyImageToBuffer(srcImage, dstBuffer, srcOrigin, range, dstOffset, null, event); }
	public SELF putCopyImageToBuffer(Image2DBuffer<ID, ?> srcImage, Buffer<ID, ?> dstBuffer, LVec2 srcOrigin, LVec2 range, int dstOffset, PointerBuffer eventWaitList, PointerBuffer event) { return putCopyImageToBuffer((ImageBuffer<ID, ?>) srcImage, dstBuffer, new LVec3(srcOrigin.x(), srcOrigin.y(), 0), new LVec3(range.x(), range.y(), 1), dstOffset, eventWaitList, event); }

	public SELF putCopyImageToBuffer(Image3DBuffer<ID, ?> srcImage, Buffer<ID, ?> dstBuffer) { return putCopyImageToBuffer(srcImage, dstBuffer, new LVec3(0, 0, 0), new LVec3(srcImage.getSize().x(), srcImage.getSize().y(), srcImage.getDepth()), 0); }
	public SELF putCopyImageToBuffer(Image3DBuffer<ID, ?> srcImage, Buffer<ID, ?> dstBuffer, LVec3 srcOrigin, LVec3 range, int dstOffset) { return putCopyImageToBuffer(srcImage, dstBuffer, srcOrigin, range, dstOffset, null); }
	public SELF putCopyImageToBuffer(Image3DBuffer<ID, ?> srcImage, Buffer<ID, ?> dstBuffer, LVec3 srcOrigin, LVec3 range, int dstOffset, PointerBuffer event) { return putCopyImageToBuffer(srcImage, dstBuffer, srcOrigin, range, dstOffset, null, event); }
	public SELF putCopyImageToBuffer(Image3DBuffer<ID, ?> srcImage, Buffer<ID, ?> dstBuffer, LVec3 srcOrigin, LVec3 range, int dstOffset, PointerBuffer eventWaitList, PointerBuffer event) { return putCopyImageToBuffer((ImageBuffer<ID, ?>) srcImage, dstBuffer, srcOrigin, range, dstOffset, eventWaitList, event); }
	public SELF putCopyImageToBuffer(ImageBuffer<ID, ?> srcImage, Buffer<ID, ?> dstBuffer, LVec3 srcOrigin, LVec3 range, int dstOffset, PointerBuffer eventWaitList, PointerBuffer event) { assertNotDead(); assertCreated(); getCL().clEnqueueCopyImageToBuffer(getId(), srcImage, dstBuffer, srcOrigin, range, dstOffset, eventWaitList, event); return (SELF) this; }

	public ByteBuffer putMapBuffer(Buffer<ID, ?> buffer, int flag, boolean blockingMap) { return putMapBuffer(buffer, flag, 0, buffer.getCLSize(), blockingMap); }
	public ByteBuffer putMapBuffer(Buffer<ID, ?> buffer, int flag, int offset, int size, boolean blockingMap) { return putMapBuffer(buffer, flag, offset, size, blockingMap, null); }
	public ByteBuffer putMapBuffer(Buffer<ID, ?> buffer, int flag, int offset, int size, boolean blockingMap, PointerBuffer event) { return putMapBuffer(buffer, flag, offset, size, blockingMap, null, event); }
	public ByteBuffer putMapBuffer(Buffer<ID, ?> buffer, int flag, int offset, int size, boolean blockingMap, PointerBuffer eventWaitList, PointerBuffer event) { assertNotDead(); assertCreated(); return getCL().clEnqueueMapBuffer(getId(), buffer, flag, offset, size, blockingMap, eventWaitList, event); }

	public ByteBuffer putMapImage(Image2DBuffer<ID, ?> image, int flag, boolean blockingMap) { return putMapImage(image, flag, new LVec2(0, 0), image.getSize(), blockingMap); }
	public ByteBuffer putMapImage(Image2DBuffer<ID, ?> image, int flag, LVec2 offset, LVec2 range, boolean blockingMap) { return putMapImage(image, flag, offset, range, blockingMap, null); }
	public ByteBuffer putMapImage(Image2DBuffer<ID, ?> image, int flag, LVec2 offset, LVec2 range, boolean blockingMap, PointerBuffer event) { return putMapImage(image, flag, offset, range, blockingMap, null, event); }
	public ByteBuffer putMapImage(Image2DBuffer<ID, ?> image, int flag, LVec2 offset, LVec2 range, boolean blockingMap, PointerBuffer eventWaitList, PointerBuffer event) { return putMapImage((ImageBuffer<ID, ?>) image, flag, new LVec3(offset.x(), offset.y(), 0), new LVec3(range.x(), range.y(), 1), blockingMap, eventWaitList, event); }

	public ByteBuffer putMapImage(Image3DBuffer<ID, ?> image, int flag, boolean blockingMap) { return putMapImage(image, flag, new LVec3(0, 0, 0), new LVec3(image.getSize().x(), image.getSize().y(), image.getDepth()), blockingMap); }
	public ByteBuffer putMapImage(Image3DBuffer<ID, ?> image, int flag, LVec3 offset, LVec3 range, boolean blockingMap) { return putMapImage(image, flag, offset, range, blockingMap, null); }
	public ByteBuffer putMapImage(Image3DBuffer<ID, ?> image, int flag, LVec3 offset, LVec3 range, boolean blockingMap, PointerBuffer event) { return putMapImage(image, flag, offset, range, blockingMap, null, event); }
	public ByteBuffer putMapImage(Image3DBuffer<ID, ?> image, int flag, LVec3 offset, LVec3 range, boolean blockingMap, PointerBuffer eventWaitList, PointerBuffer event) { return putMapImage((ImageBuffer<ID, ?>) image, flag, offset, range, blockingMap, eventWaitList, event); }
	public ByteBuffer putMapImage(ImageBuffer<ID, ?> image, int flag, LVec3 offset, LVec3 range, boolean blockingMap, PointerBuffer eventWaitList, PointerBuffer event) { assertNotDead(); assertCreated(); return getCL().clEnqueueMapImage(getId(), image, flag, offset, range, blockingMap, eventWaitList, event); }

	public SELF putUnmapMemory(Memory<ID, ?> memory, java.nio.Buffer mapped) { return putUnmapMemory(memory, mapped, null); }
	public SELF putUnmapMemory(Memory<ID, ?> memory, java.nio.Buffer mapped, PointerBuffer event) { return putUnmapMemory(memory, mapped, null, event); }
	public SELF putUnmapMemory(Memory<ID, ?> memory, java.nio.Buffer mapped, PointerBuffer eventWaitList, PointerBuffer event) { assertNotDead(); assertCreated(); getCL().clEnqueueUnmapMemObject(getId(), memory, mapped, eventWaitList, event); return (SELF) this; }

	public SELF putMarker(PointerBuffer event) { assertNotDead(); assertCreated(); getCL().clEnqueueMarker(getId(), event); return (SELF) this; }
	public SELF putWaitForEvent(PointerBuffer eventWaitList, int index, boolean blockingWait) { assertNotDead(); assertCreated();
		PointerBuffer p_eventWaitList = eventWaitList.slice().position(index);
		if(blockingWait) { getCL().clWaitForEvents(getId(), 1, p_eventWaitList); return (SELF) this; }
		getCL().clEnqueueWaitForEvents(getId(), 1, p_eventWaitList); return (SELF) this;
	} public SELF putWaitForEvents(PointerBuffer eventWaitList, boolean blockingWait) { assertNotDead(); assertCreated();
		PointerBuffer p_eventWaitList = eventWaitList.slice(); int length = p_eventWaitList.capacity() / p_eventWaitList.elementSize();
		if(blockingWait && length > 0) { getCL().clWaitForEvents(getId(), length, p_eventWaitList); return (SELF) this; }
		if(length <= 0) return (SELF) this; getCL().clEnqueueWaitForEvents(getId(), length, p_eventWaitList); return (SELF) this;
	}

	public SELF putBarrier() { assertNotDead(); assertCreated(); getCL().clEnqueueBarrier(getId()); return (SELF) this; }
	public SELF putTask(Kernel<ID> kernel) { return putTask(kernel, null); }
	public SELF putTask(Kernel<ID> kernel, PointerBuffer event) { return putTask(kernel, null, event); }
	public SELF putTask(Kernel<ID> kernel, PointerBuffer eventWaitList, PointerBuffer event) { assertNotDead(); assertCreated(); getCL().clEnqueueTask(getId(), kernel, eventWaitList, event); return (SELF) this; }

	public SELF put1DRangeKernel(Kernel<ID> kernel, int globalWorkOffset, int globalWorkSize, int localWorkSize) { return put1DRangeKernel(kernel, globalWorkOffset, globalWorkSize, localWorkSize, null); }
	public SELF put1DRangeKernel(Kernel<ID> kernel, int globalWorkOffset, int globalWorkSize, int localWorkSize, PointerBuffer event) { return put1DRangeKernel(kernel, globalWorkOffset, globalWorkSize, localWorkSize, null, event); }
	public SELF put1DRangeKernel(Kernel<ID> kernel, int globalWorkOffset, int globalWorkSize, int localWorkSize, PointerBuffer eventWaitList, PointerBuffer event) { return putNDRangeKernel(kernel, 1, new LVecN(1, globalWorkOffset), new LVecN(1, globalWorkSize), new LVecN(1, localWorkSize), eventWaitList, event); }
	public SELF put2DRangeKernel(Kernel<ID> kernel, LVec2 globalWorkOffset, LVec2 globalWorkSize, LVec2 localWorkSize) { return put2DRangeKernel(kernel, globalWorkOffset, globalWorkSize, localWorkSize, null); }
	public SELF put2DRangeKernel(Kernel<ID> kernel, LVec2 globalWorkOffset, LVec2 globalWorkSize, LVec2 localWorkSize, PointerBuffer event) { return put2DRangeKernel(kernel, globalWorkOffset, globalWorkSize, localWorkSize, null, event); }
	public SELF put2DRangeKernel(Kernel<ID> kernel, LVec2 globalWorkOffset, LVec2 globalWorkSize, LVec2 localWorkSize, PointerBuffer eventWaitList, PointerBuffer event) { return putNDRangeKernel(kernel, 2, globalWorkOffset, globalWorkSize, localWorkSize, eventWaitList, event); }
	public SELF put3DRangeKernel(Kernel<ID> kernel, LVec3 globalWorkOffset, LVec3 globalWorkSize, LVec3 localWorkSize) { return put3DRangeKernel(kernel, globalWorkOffset, globalWorkSize, localWorkSize, null); }
	public SELF put3DRangeKernel(Kernel<ID> kernel, LVec3 globalWorkOffset, LVec3 globalWorkSize, LVec3 localWorkSize, PointerBuffer event) { return put3DRangeKernel(kernel, globalWorkOffset, globalWorkSize, localWorkSize, null, event); }
	public SELF put3DRangeKernel(Kernel<ID> kernel, LVec3 globalWorkOffset, LVec3 globalWorkSize, LVec3 localWorkSize, PointerBuffer eventWaitList, PointerBuffer event) { return putNDRangeKernel(kernel, 3, globalWorkOffset, globalWorkSize, localWorkSize, eventWaitList, event); }
	public SELF putNDRangeKernel(Kernel<ID> kernel, int workDimension, LVecN globalWorkOffset, LVecN globalWorkSize, LVecN localWorkSize) { return putNDRangeKernel(kernel, workDimension, globalWorkOffset, globalWorkSize, localWorkSize, null); }
	public SELF putNDRangeKernel(Kernel<ID> kernel, int workDimension, LVecN globalWorkOffset, LVecN globalWorkSize, LVecN localWorkSize, PointerBuffer event) { return putNDRangeKernel(kernel, workDimension, globalWorkOffset, globalWorkSize, localWorkSize, null, event); }
	public SELF putNDRangeKernel(Kernel<ID> kernel, int workDimension, LVecN globalWorkOffset, LVecN globalWorkSize, LVecN localWorkSize, PointerBuffer eventWaitList, PointerBuffer event) { assertNotDead(); assertCreated(); getCL().clEnqueueNDRangeKernel(getId(), kernel, workDimension, globalWorkOffset, globalWorkSize, localWorkSize, eventWaitList, event); return (SELF) this; }

	public <CLObject extends OpenCLNativeHolder<ID> & CLGLObject> SELF putAcquireGLObjects(CLObject... clglObjects) { return putAcquireGLObjects(null, clglObjects); }
	public <CLObject extends OpenCLNativeHolder<ID> & CLGLObject> SELF putAcquireGLObjects(PointerBuffer event, CLObject... clglObjects) { return putAcquireGLObjects(null, event, clglObjects); }
	public <CLObject extends OpenCLNativeHolder<ID> & CLGLObject> SELF putAcquireGLObjects(PointerBuffer eventWaitList, PointerBuffer event, CLObject... clglObjects) { return putAcquireGLObjects(clglObjects, eventWaitList, event); }
	public <CLObject extends OpenCLNativeHolder<ID> & CLGLObject> SELF putAcquireGLObjects(CLObject[] clglObjects, PointerBuffer event) { return putAcquireGLObjects(clglObjects, null, event); }
	public <CLObject extends OpenCLNativeHolder<ID> & CLGLObject> SELF putAcquireGLObjects(CLObject[] clglObjects, PointerBuffer eventWaitList, PointerBuffer event) {
		PointerBuffer pointerBuffer = BufferUtils.allocatePointerBuffer(clglObjects.length);
		for(int i = 0; i < clglObjects.length; i++) pointerBuffer.put(i, clglObjects[i].getId().longValue());
		try { return putAcquireGLObjects(pointerBuffer, eventWaitList, event); } finally { BufferUtils.deallocate(pointerBuffer); }
	}
	public SELF putAcquireGLObjects(PointerBuffer clglObjects, PointerBuffer eventWaitList, PointerBuffer event) { assertNotDead(); assertCreated(); getCL().clEnqueueAcquireGLObjects(getId(), clglObjects, eventWaitList, event); return (SELF) this; }

	public <CLObject extends OpenCLNativeHolder<ID> & CLGLObject> SELF putReleaseGLObjects(CLObject... clglObjects) { return putReleaseGLObjects(null, clglObjects); }
	public <CLObject extends OpenCLNativeHolder<ID> & CLGLObject> SELF putReleaseGLObjects(PointerBuffer event, CLObject... clglObjects) { return putReleaseGLObjects(null, event, clglObjects); }
	public <CLObject extends OpenCLNativeHolder<ID> & CLGLObject> SELF putReleaseGLObjects(PointerBuffer eventWaitList, PointerBuffer event, CLObject... clglObjects) { return putReleaseGLObjects(clglObjects, eventWaitList, event); }
	public <CLObject extends OpenCLNativeHolder<ID> & CLGLObject> SELF putReleaseGLObjects(CLObject[] clglObjects, PointerBuffer event) { return putReleaseGLObjects(clglObjects, null, event); }
	public <CLObject extends OpenCLNativeHolder<ID> & CLGLObject> SELF putReleaseGLObjects(CLObject[] clglObjects, PointerBuffer eventWaitList, PointerBuffer event) {
		PointerBuffer pointerBuffer = BufferUtils.allocatePointerBuffer(clglObjects.length);
		for(int i = 0; i < clglObjects.length; i++) pointerBuffer.put(i, clglObjects[i].getId().longValue());
		try { return putReleaseGLObjects(pointerBuffer, eventWaitList, event); } finally { BufferUtils.deallocate(pointerBuffer); }
	}
	public SELF putReleaseGLObjects(PointerBuffer clglObjects, PointerBuffer eventWaitList, PointerBuffer event) { assertNotDead(); assertCreated(); getCL().clEnqueueReleaseGLObjects(getId(), clglObjects, eventWaitList, event); return (SELF) this; }
	
	public SELF finish() { assertNotDead(); assertCreated(); getCL().clFinish(getId()); return (SELF) this; }
	public SELF flush() { assertNotDead(); assertCreated(); getCL().clFlush(getId()); return (SELF) this; }
	public boolean isOutOfOrderModeEnabled() { assertNotDead(); assertCreated(); return BitwiseUtils.is(properties, getCL().CL_QUEUE_OUT_OF_ORDER_EXEC_MODE_ENABLE()); }
	public boolean isProfilingEnabled() { assertNotDead(); assertCreated(); return BitwiseUtils.is(properties, getCL().CL_QUEUE_PROFILING_ENABLE()); }
	
	@Override protected void arrange() throws Exception {
		getInstance().setId(getCL().createCommandQueue(device.getId(), properties));
	}
	@Override protected void disarrange() {
		getCL().clReleaseCommandQueue(getId());
	}
}

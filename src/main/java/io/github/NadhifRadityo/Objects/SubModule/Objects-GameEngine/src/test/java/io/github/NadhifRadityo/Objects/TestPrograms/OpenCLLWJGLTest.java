package io.github.NadhifRadityo.Objects.TestPrograms;

import io.github.NadhifRadityo.Objects.Console.Logger;
import io.github.NadhifRadityo.Objects.GameEngine.Jogamp.OpenGL.Objects.JOGLSandboxNative;
import io.github.NadhifRadityo.Objects.GameEngine.LWJGL.OpenCL.Buffer;
import io.github.NadhifRadityo.Objects.GameEngine.LWJGL.OpenCL.CLContext;
import io.github.NadhifRadityo.Objects.GameEngine.LWJGL.OpenCL.CommandQueue;
import io.github.NadhifRadityo.Objects.GameEngine.LWJGL.OpenCL.Device;
import io.github.NadhifRadityo.Objects.GameEngine.LWJGL.OpenCL.Kernel;
import io.github.NadhifRadityo.Objects.GameEngine.LWJGL.OpenCL.Platform;
import io.github.NadhifRadityo.Objects.GameEngine.LWJGL.OpenCL.Program;
import io.github.NadhifRadityo.Objects.Object.ReferencedCallback;
import io.github.NadhifRadityo.Objects.ObjectUtils.Buffer.BufferAlgorithm.BestFitAllocationAlgorithm;
import io.github.NadhifRadityo.Objects.ObjectUtils.Buffer.BufferManager;
import io.github.NadhifRadityo.Objects.ObjectUtils.Proxy.Proxy;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.TestProgram;
import io.github.NadhifRadityo.Objects.Tester;
import io.github.NadhifRadityo.Objects.Utilizations.BufferUtils;
import io.github.NadhifRadityo.Objects.Utilizations.PrivilegedUtils;
import io.github.NadhifRadityo.Objects.Utilizations.SecurityUtils;
import io.github.NadhifRadityo.Objects.Utilizations.UnsafeUtils;
import org.lwjgl.opencl.CL10;
import sun.misc.Unsafe;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.Map;
import java.util.Random;

@TestProgram(group = "GameEngine")
public class OpenCLLWJGLTest extends Tester {
	public OpenCLLWJGLTest() throws Exception {
		super("OpenCLLWJGLTest");
		BufferUtils.pointTo(0, 0);
		JOGLSandboxNative.extract();
	}

	static ReferencedCallback.PVoidReferencedCallback fillBuffer = (_args) -> {
		FloatBuffer buffer = (FloatBuffer) _args[0];
		int seed = (int) _args[1];
		int len = _args.length >= 3 ? (int) _args[2] : buffer.capacity();
		Random random = new Random(seed);
		int i = 0;
		int fit = (len / 8) * 8;
		for(; i < fit; i += 8) {
			buffer.put(random.nextFloat() * 100);
			buffer.put(random.nextFloat() * 100);
			buffer.put(random.nextFloat() * 100);
			buffer.put(random.nextFloat() * 100);
			buffer.put(random.nextFloat() * 100);
			buffer.put(random.nextFloat() * 100);
			buffer.put(random.nextFloat() * 100);
			buffer.put(random.nextFloat() * 100);
		}
		for(; i < len; i++)
			buffer.put(random.nextFloat() * 100);
		buffer.rewind();
	};
	static ReferencedCallback.PIntegerReferencedCallback roundUp = (args) -> {
		int groupSize = (int) args[0];
		int globalSize = (int) args[1];
		int r = globalSize % groupSize;
		return r == 0 ? globalSize : globalSize + groupSize - r;
	};
	// https://stackoverflow.com/questions/3758606/how-to-convert-byte-size-into-human-readable-format-in-java
	static CharacterIterator readableByteCount = new StringCharacterIterator("kMGTPE");
	static ReferencedCallback.StringReferencedCallback humanReadableByteCount = (args) -> {
		long bytes = (long) args[0];
		if(-1000 < bytes && bytes < 1000) return bytes + " B"; readableByteCount.setIndex(0);
		while(bytes <= -999_950 || bytes >= 999_950) { bytes /= 1000; readableByteCount.next(); }
		return String.format("%.1f %cB", bytes / 1000.0, readableByteCount.current());
	};

	@Override protected boolean doRun(Logger logger, ByteBuffer dump) throws Throwable {
		Platform[] platforms = CLContext.getPlatforms();
		boolean success = true;
		for(Platform platform : platforms) {
			logger.info("Using platform: " + platform.getName());
			Map<String, Object> platformProps = platform.getProperties();
			for(Map.Entry<String, Object> entry : platformProps.entrySet()) {
				byte[] key = entry.getKey().getBytes();
				byte[] val = entry.getValue().toString().getBytes();
				dump.putInt(key.length);
				dump.put(key);
				dump.putInt(val.length);
				dump.put(val);
			}

			CLContext context = CLContext.createContext(platform, platforms, platform.getDevices());
			Object[] devices = platform.getDevices();
			for(int i = 0; i < devices.length; i++) {
				Device device = (Device) devices[i];
				logger.info("Using #" + i + " device: " + device.getName());
				Map<String, Object> deviceProps = device.getProperties();
				for(Map.Entry<String, Object> entry : deviceProps.entrySet()) {
					byte[] key = entry.getKey().getBytes();
					byte[] val = entry.getValue().toString().getBytes();
					dump.putInt(key.length);
					dump.put(key);
					dump.putInt(val.length);
					dump.put(val);
				}

				boolean result = doRun0(logger, dump, context, device, 1024, 5);
				if(success) success = result;
			}
		} return success;
	}

	private boolean doRun0(Logger logger, ByteBuffer dump, CLContext context, Device device, int elementCount, int test) throws Exception {
		int localWorkSize = Math.min(device.getMaxWorkGroupSize(), 256);
		int globalWorkSize = roundUp.get(localWorkSize, elementCount);
		BufferManager bufferManager = new BufferManager(new BestFitAllocationAlgorithm(), globalWorkSize * 3 * Float.BYTES);
		String clSource = "E:\\Tio\\Eclipse\\WorkSpace\\Projects\\Objects\\src\\main\\java\\io\\github\\NadhifRadityo\\Objects\\SubModule\\Objects-GameEngine\\src\\main\\resources\\opencl\\add\\VectorAdd.cl";

		dump.putInt(elementCount);
		dump.putInt(localWorkSize);
		dump.putInt(globalWorkSize);

		Buffer<FloatBuffer> bufferA = new Buffer<>(context, globalWorkSize, BufferUtils.allocateFloatBuffer(globalWorkSize, bufferManager), CL10.CL_MEM_READ_ONLY);
		Buffer<FloatBuffer> bufferB = new Buffer<>(context, globalWorkSize, BufferUtils.allocateFloatBuffer(globalWorkSize, bufferManager), CL10.CL_MEM_READ_ONLY);
		Buffer<FloatBuffer> bufferC = new Buffer<>(context, globalWorkSize, BufferUtils.allocateFloatBuffer(globalWorkSize, bufferManager), CL10.CL_MEM_WRITE_ONLY);
		bufferA.create(); bufferB.create(); bufferC.create();
		byte[] resultTemp = new byte[bufferC.getBuffer().capacity() * Float.BYTES];
		logger.log("Used device memory: " + humanReadableByteCount.get((long) bufferA.getCLSize() + bufferB.getCLSize() + bufferC.getCLSize()));

		Program program = new Program(context, new File(clSource), Charset.defaultCharset());
		program.create();
		Kernel kernel = (Kernel) program.getKernel("VectorAdd");
		kernel.putArgs(bufferA, bufferB, bufferC).putArg(elementCount);
		CommandQueue queue = new CommandQueue(device, CL10.CL_QUEUE_PROFILING_ENABLE);
		queue.create();

		byte[] codeBytes = program.getWholeCode().getBytes(StandardCharsets.UTF_8);
		dump.putInt(codeBytes.length);
		dump.put(codeBytes);
		dump.putInt(program.getBinaries().get(device).length);
		dump.put(program.getBinaries().get(device));
		Proxy<Boolean> success = Pool.tryBorrow(Pool.getPool(Proxy.class));
		success.set(true);

		dump.putInt(test);
		try { for(int i = 0; i < test; i++) {
			fillBuffer.get(bufferA.getBuffer(), (int) System.nanoTime(), elementCount);
			fillBuffer.get(bufferB.getBuffer(), (int) System.nanoTime(), elementCount);

			BufferUtils.copy(bufferA.getBuffer(), 0, dump, dump.position(), bufferA.getBuffer().capacity() * Float.BYTES);
			dump.position(dump.position() + bufferA.getBuffer().capacity() * Float.BYTES);
			BufferUtils.copy(bufferB.getBuffer(), 0, dump, dump.position(), bufferB.getBuffer().capacity() * Float.BYTES);
			dump.position(dump.position() + bufferB.getBuffer().capacity() * Float.BYTES);

			long time = System.nanoTime();
			queue.putWriteBuffer(bufferA, false)
					.putWriteBuffer(bufferB, false)
					.put1DRangeKernel(kernel, 0, globalWorkSize, localWorkSize)
					.putReadBuffer(bufferC, true);
			time = System.nanoTime() - time;

			BufferUtils.copy(bufferC.getBuffer(), 0, dump, dump.position(), bufferC.getBuffer().capacity() * Float.BYTES);
			dump.position(dump.position() + bufferC.getBuffer().capacity() * Float.BYTES);
			dump.putLong(time);

			logger.log("a+b=c results snapshot: ");
			for(int j = 0; j < 10; j++)
				logger.log(bufferA.getBuffer().get(j) + " + " + bufferB.getBuffer().get(j) + " = " + bufferC.getBuffer().get(j));
			logger.log("...; " + (bufferC.getBuffer().capacity() - 10) + " more");
			logger.log("Computations took: " + ((float) time / 1000000) + "ms");

			PrivilegedUtils.doPrivileged(() -> {
				UnsafeUtils.copyMemory(BufferUtils.__getAddress(bufferC.getBuffer()), resultTemp, Unsafe.ARRAY_BYTE_BASE_OFFSET);
				String openclMd5 = SecurityUtils.MD5DigestString(resultTemp);

				FloatBuffer ba = bufferA.getBuffer();
				FloatBuffer bb = bufferB.getBuffer();
				FloatBuffer bc = bufferC.getBuffer();
				int j = 0;
				int fit = (elementCount / 8) * 8;
				for(; j < fit; j += 8) {
					bc.put(j + 0, ba.get(j + 0) + bb.get(j + 0));
					bc.put(j + 1, ba.get(j + 1) + bb.get(j + 1));
					bc.put(j + 2, ba.get(j + 2) + bb.get(j + 2));
					bc.put(j + 3, ba.get(j + 3) + bb.get(j + 3));
					bc.put(j + 4, ba.get(j + 4) + bb.get(j + 4));
					bc.put(j + 5, ba.get(j + 5) + bb.get(j + 5));
					bc.put(j + 6, ba.get(j + 6) + bb.get(j + 6));
					bc.put(j + 7, ba.get(j + 7) + bb.get(j + 7));
				}
				for(; j < elementCount; j++)
					bc.put(j, ba.get(j) + bb.get(j));
				UnsafeUtils.copyMemory(BufferUtils.__getAddress(bufferC.getBuffer()), resultTemp, Unsafe.ARRAY_BYTE_BASE_OFFSET);
				String cpuMd5 = SecurityUtils.MD5DigestString(resultTemp);

				byte[] md5Bytes = openclMd5.getBytes(StandardCharsets.UTF_8);
				dump.putInt(md5Bytes.length);
				dump.put(md5Bytes);
				md5Bytes = cpuMd5.getBytes(StandardCharsets.UTF_8);
				dump.putInt(md5Bytes.length);
				dump.put(md5Bytes);
				if(success.get()) success.set(openclMd5.equals(cpuMd5));
				if(!openclMd5.equals(cpuMd5))
					logger.error("Failed! Checksums not equal");
				else logger.debug("Success! Checksums passed");
				BufferUtils.empty(bc, 0, bc.capacity());
			});
		} return success.get(); } finally {
			bufferA.destroy();
			bufferB.destroy();
			bufferC.destroy();
			BufferUtils.deallocate(bufferManager.getBuffer());
			program.destroy();
			Pool.returnObject(Proxy.class, success);
		}
	}
}

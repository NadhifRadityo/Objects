package io.github.NadhifRadityo.Objects;

import io.github.NadhifRadityo.Objects.Console.Logger;
import io.github.NadhifRadityo.Objects.Utilizations.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public abstract class Tester {
	protected final String name;

	public Tester(String name) {
		this.name = name;
	}

	public String getName() { return name; }

	public boolean run(Logger logger, ByteBuffer _dump) throws Throwable {
		logger.warn("Performing test: " + this.name);
		long start = System.nanoTime();
		byte[] name = this.name.getBytes(StandardCharsets.UTF_8);
		_dump.putInt(name.length); _dump.put(name);
		int size = name.length + Integer.BYTES;
		ByteBuffer dump = BufferUtils.slice(_dump, size, _dump.capacity() - size);
		boolean result = false; try { result = doRun(logger, dump); } finally {
			_dump.position(size + dump.position());
			if(result) logger.warn("Successfully perform test: " + this.name + " -> Took: " + ((float) (System.nanoTime() - start) / 1000000) + "ms");
			else logger.error("Failed perform test: " + this.name + " -> Took: " + ((float) (System.nanoTime() - start) / 1000000) + "ms"); System.gc();
		} return result;
	}
	protected abstract boolean doRun(Logger logger, ByteBuffer dump) throws Throwable;
}

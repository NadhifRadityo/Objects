package io.github.NadhifRadityo.Objects.$Object.Buffer.BufferAlgorithm;

import io.github.NadhifRadityo.Objects.$Object.Buffer.BufferManager;

public interface BufferAllocationAlgorithm {
	int allocate(BufferManager bufferManager, int[] map, int size, int alignment, Object cache) throws OutOfMemoryError;
	void deallocate(BufferManager bufferManager, int[] map, int index, Object cache);
	boolean canAllocate(BufferManager bufferManager, int[] map, int size, int alignment, Object cache);
}

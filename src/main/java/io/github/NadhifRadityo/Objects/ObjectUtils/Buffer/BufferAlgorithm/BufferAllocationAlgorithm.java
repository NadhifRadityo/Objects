package io.github.NadhifRadityo.Objects.ObjectUtils.Buffer.BufferAlgorithm;

import io.github.NadhifRadityo.Objects.ObjectUtils.Buffer.BufferManager;

public interface BufferAllocationAlgorithm {
	int allocate(BufferManager bufferManager, int[] map, int size, Object cache) throws OutOfMemoryError;
	void deallocate(BufferManager bufferManager, int[] map, int index, Object cache);
	boolean canAllocate(BufferManager bufferManager, int[] map, int size, Object cache);
}

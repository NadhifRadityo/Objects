package io.github.NadhifRadityo.Objects.$Object.Buffer;

import io.github.NadhifRadityo.Objects.$Object.Buffer.BufferAlgorithm.BufferAllocationAlgorithm;
import io.github.NadhifRadityo.Objects.$Utilizations.BufferUtils;
import io.github.NadhifRadityo.Objects.$Utilizations.UnsafeUtils;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.NoSuchElementException;

public class BufferManager {
//	private static final ArrayList<BufferManager> bufferManagers = new ArrayList<>();

//	static {
//		ReferencedCallback<int[]> getIndex = (args) -> {
//			int[] map = (int[]) args[0];
//			int address = (int) args[1];
//			int fromLeft = 0; int index = -1;
//			for(int i = 0; i < map.length; i++) {
//				if(address > fromLeft) fromLeft += Math.abs(map[i]); else { index = i; break; }
//			} if(index == -1 && address <= fromLeft) index = map.length - 1;
//			if(index == -1) throw new ArrayIndexOutOfBoundsException();
//			return new int[] { fromLeft - map[index], index };
//		};
//		long delayFlip = 100;
//		if(Handler.getMain() != null)
//		Handler.getMain().postFixedRate(() -> {
//			for(BufferManager bufferManager : bufferManagers.toArray(new BufferManager[0])) {
//				int next = -1; boolean allocated = false;
//				int totalSize = bufferManager.totalSize;
//				int[] map = bufferManager.map.clone();
//				ByteBuffer buffer = BufferUtils.pointTo(BufferUtils.getAddress(bufferManager.buffer), totalSize);
//				if(buffer == null) throw new IllegalStateException("Not in privileged!");
//				if(totalSize != buffer.capacity()) throw new IllegalStateException("Capacity not same!");
//				for(int i = 0; i < totalSize; i++) {
//					if(i > next) {
//						int[] result = getIndex.get(map, i);
//						next = result[0]; allocated = map[result[1]] < 0;
//					}
//					byte value = buffer.get(i);
//					if(allocated || value == 0) continue;
//					buffer.put(i, (byte) 0);
//					try { Thread.sleep(delayFlip); } catch(InterruptedException e) { e.printStackTrace(); }
//					buffer.put(i, value);
//				}
//			}
//		}, 10);
//	}

	protected final BufferAllocationAlgorithm allocationAlgorithm;
	protected final ByteBuffer buffer;
	protected final long address;
	protected final int totalSize;
	protected int[] map;
	protected Object cache;

	public BufferManager(BufferAllocationAlgorithm allocationAlgorithm, ByteBuffer buffer) {
		this.allocationAlgorithm = allocationAlgorithm;
		this.buffer = buffer;
		this.address = BufferUtils.__address(buffer);
		this.totalSize = BufferUtils.getBytesRemaining(buffer);
		this.map = new int[] { totalSize };
//		bufferManagers.add(this);
	}
	public BufferManager(BufferAllocationAlgorithm allocationAlgorithm, int totalSize) {
		this(allocationAlgorithm, BufferUtils.createByteBuffer(totalSize));
	}

	public BufferAllocationAlgorithm getAllocationAlgorithm() { return allocationAlgorithm; }
	public ByteBuffer getBuffer() { return buffer; }
	public long getAddress() { return address; }
	public int getTotalSize() { return totalSize; }
	public void updateMap(int[] map) { this.map = map; }
	public void updateCache(Object cache) { this.cache = cache; }
	public boolean isPartOfBuffer(long address, int capacity) { return address >= this.address && address + capacity <= this.address + this.totalSize; }
	public boolean isPartOfBuffer(Buffer buffer) { return isPartOfBuffer(BufferUtils.__getAddress(buffer), BufferUtils.getBytesCapacity(buffer)); }

	public synchronized long _allocate(int size, int alignment) {
//		return BufferUtils.createByteBuffer(size);
		if(size <= 0) return UnsafeUtils.NULLPTR;
		int start = allocationAlgorithm.allocate(this, map, size, alignment, cache);
//		System.out.println(Thread.currentThread() + ": Allocate -> " + size + ", DUMP: " + ArrayUtils.deepToString(map));
//		int end = start + size;
//		for(int i = start; i < end; i++) if(buffer.get(i) != 0) {
//			System.out.println("BAD SECTOR (" + i + ")"); buffer.put(i, (byte) 0); }
		return address + start;
	}
	public synchronized long _allocate(int size) { return _allocate(size, 1); }
	public synchronized void _deallocate(long address) {
		int localAddress = (int) (address - this.address);
		if(!isPartOfBuffer(address, totalSize - localAddress)) throw new NoSuchElementException();

		long fromLeft = 0; int index = -1;
		for(int i = 0; i < map.length; i++) {
			if(localAddress > fromLeft) fromLeft += Math.abs(map[i]); else { index = i; break; }
		} if(index == -1 && localAddress <= fromLeft) index = map.length - 1;
		if(index == -1) throw new ArrayIndexOutOfBoundsException(); if(map[index] > 0) return;

		BufferUtils.empty(this.buffer, localAddress, Math.abs(map[index]));
		allocationAlgorithm.deallocate(this, map, index, cache);
//		System.out.println(Thread.currentThread() + ": Deallocate -> Address:" + address + ", local address:" + localAddress + ", DUMP: " + ArrayUtils.deepToString(map));
	}

	public synchronized ByteBuffer allocate(int size, int alignment) { return BufferUtils.__pointTo(_allocate(size, alignment), size); }
	public synchronized ByteBuffer allocate(int size) { return allocate(size, 1); }
	public synchronized void deallocate(Buffer buffer) { _deallocate(BufferUtils.__getAddress(buffer)); }
	public synchronized boolean canAllocate(int size, int alignment) { return allocationAlgorithm.canAllocate(this, map, size, alignment, cache); }
	public synchronized boolean canAllocate(int size) { return canAllocate(size, 1); }
}

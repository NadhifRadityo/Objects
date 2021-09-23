package io.github.NadhifRadityo.Objects.$Object.Buffer.BufferAlgorithm;

import io.github.NadhifRadityo.Objects.$Object.Buffer.BufferManager;

@Deprecated
public class BuddyAllocationAlgorithm implements BufferAllocationAlgorithm {
//	protected static final int CACHE_SIZE = 1;
//	protected static final int BUDDY_OBJECT = 0;
//
//	@Override public int allocate(BufferManager bufferManager, int[] map, int size, Object _cache) throws OutOfMemoryError {
//		Object[] cache = _cache == null ? null : (Object[]) _cache;
//		if(cache == null) {
//			cache = new Object[CACHE_SIZE];
//			cache[BUDDY_OBJECT] = new Buddy(findNearestLevel(sum(map)));
//			bufferManager.updateCache(cache);
//		}
//		return ((Buddy) cache[BUDDY_OBJECT]).allocate(size);
//	}
//
//	@Override public void deallocate(BufferManager bufferManager, int[] map, int index, Object _cache) {
//		Object[] cache = _cache == null ? null : (Object[]) _cache;
//		if(cache == null) throw new IllegalStateException();
//		((Buddy) cache[BUDDY_OBJECT]).deallocate(index);
//	}
//
//	private static int sum(int... x) {
//		int result = 0;
//		for(int value : x) result += Math.abs(value);
//		return result;
//	}
//	private static int findNearestLevel(int size) {
//		int level = 0;
//		while((1 << level) < size) level++;
//		return level - 1;
//	}
//
//	public static class Buddy {
//		protected static final int NODE_UNUSED = 0;
//		protected static final int NODE_USED = 1;
//		protected static final int NODE_SPLIT = 2;
//		protected static final int NODE_FULL = 3;
//
//		protected final int level;
//		protected final int[] tree;
//
//		public Buddy(int level) {
//			this.level = level;
//			this.tree = new int[(1 << level) * 2 - 2];
//		}
//
//		protected static boolean isPowOf2(long x) {
//			return x > 0 && (x & (x - 1)) == 0;
//		}
//		protected static long nextPowOf2(long x) {
//			if(isPowOf2(x)) return x;
//			x |= x >> 1;
//			x |= x >> 2;
//			x |= x >> 4;
//			x |= x >> 8;
//			x |= x >> 16;
//			return x + 1;
//		}
//		protected static int indexOffset(int index, int level, int maxLevel) {
//			return ((index + 1) - (1 << level)) << (maxLevel - level);
//		}
//		protected static void markParent(Buddy self, int index) {
//			while(true) {
//				int buddy = index - 1 + (index & 1) * 2;
//				if(buddy <= 0 || (self.tree[buddy] != NODE_USED && self.tree[buddy] != NODE_FULL)) return;
//				index = (index + 1) / 2 - 1;
//				self.tree[index] = NODE_FULL;
//			}
//		}
//
//		protected int allocate(int s) {
//			int size = s == 0 ? 1 : (int) nextPowOf2(s);
//			int length = 1 << level;
//			if(size > length) return -1;
//
//			int index = 0;
//			int level = 0;
//			while(index >= 0) {
//				if(size != length) switch(tree[index]) {
//					case NODE_USED:
//					case NODE_FULL:
//						break;
//					case NODE_UNUSED: {
//						tree[index] = NODE_SPLIT;
//						tree[index * 2 + 1] = NODE_UNUSED;
//						tree[index * 2 + 2] = NODE_UNUSED;
//					}
//					default: {
//						index = index * 2 + 1;
//						length /= 2;
//						level++;
//						continue;
//					}
//				} else if(tree[index] == NODE_UNUSED) {
//					tree[index] = NODE_USED;
//					markParent(this, index);
//					return indexOffset(index, level, this.level);
//				}
//				if((index & 1) != 0) { ++index; continue; }
//				while(true) {
//					level--;
//					length *= 2;
//					index = (index + 1) / 2 - 1;
//					if(index < 0) return -1;
//					if((index & 1) != 0) { index++; break; }
//				}
//			} return -1;
//		}
//
//		protected static void combine(Buddy self, int index) {
//			while(true) {
//				int buddy = index - 1 + (index & 1) * 2;
//				if(buddy >= 0 && self.tree[buddy] == NODE_UNUSED) { index = (index + 1) / 2 - 1; continue; }
//				self.tree[index] = NODE_UNUSED;
//				while(((index = (index + 1) / 2 - 1) >= 0) && self.tree[index] == NODE_FULL)
//					self.tree[index] = NODE_SPLIT;
//				return;
//
//			}
//		}
//
//		protected void deallocate(int offset) {
//			if(offset >= (1 << level)) throw new IllegalArgumentException();
//			int left = 0;
//			int length = 1 << level;
//
//			int index = 0;
//			while(true) {
//				switch(tree[index]) {
//					case NODE_USED: {
//						if(offset != left) throw new IllegalArgumentException();
//						combine(this, index);
//						return;
//					}
//					case NODE_UNUSED: return;
//					default: {
//						length /= 2;
//						if(offset < left + length) index = index * 2 + 1;
//						else { left += length; index = index * 2 + 2; }
//					}
//				}
//			}
//		}
//
//		protected int size(int offset) {
//			if(offset >= (1 << level)) throw new IllegalArgumentException();
//			int left = 0;
//			int length = 1 << level;
//
//			int index = 0;
//			while(true) {
//				switch(tree[index]) {
//					case NODE_USED: {
//						if(offset != left) throw new IllegalArgumentException();
//						return length;
//					}
//					case NODE_UNUSED: return length;
//					default: {
//						length /= 2;
//						if(offset < left + length) index = index * 2 + 1;
//						else { left += length; index = index * 2 + 2; }
//					}
//				}
//			}
//		}
//	}

	protected static final int DEFAULT_MIN_GROUP = (int) fastPow(2, 3);
	protected static final int CACHE_SIZE = 6;
	protected static final int ROOTS = 0;
	protected static final int D_ENABLED = 5;
	protected static final int D_START_LEN = 4;
	protected static final int D_INDEX = 1;
	protected static final int D_SIZE = 2;
	protected static final int D_REPEAT = 3;

	@Override public int allocate(BufferManager bufferManager, int[] map, int size, int alignment, Object _cache) throws OutOfMemoryError {
		Object[] cache = _cache == null ? null : (Object[]) _cache;
		if(cache == null) {
			cache = new Object[CACHE_SIZE];
			cache[ROOTS] = new BuddyAllocationAlgorithmRoot(null, 0, sum(map));

			bufferManager.updateCache(cache);
		}

		BuddyAllocationAlgorithmRoot root = ((BuddyAllocationAlgorithmRoot) cache[ROOTS]).allocate(size, map, cache);
		if(cache[D_ENABLED] != null && (boolean) cache[D_ENABLED]) {
			cache[D_ENABLED] = null;
			int d_start_len = (int) cache[D_START_LEN]; cache[D_START_LEN] = null;
			int d_index = (int) cache[D_INDEX]; cache[D_INDEX] = null;
			int d_size = (int) cache[D_SIZE]; cache[D_SIZE] = null;
			int d_repeat = (int) cache[D_REPEAT]; cache[D_REPEAT] = null;
			int[] newMap = new int[map.length + d_repeat];
			System.arraycopy(map, 0, newMap, 0, d_index);
			for(int i = -1; i < d_repeat; i++)
				newMap[d_index + i + 1] = d_start_len / (int) fastPow(2, d_repeat - Math.max(0, i));
			System.arraycopy(map, d_index + 1, newMap, d_index + d_size + 1, map.length - d_index - 1);
			bufferManager.updateMap(map = newMap);
		}
		map[root.index] *= -1;
		return root.index;
	}

	@Override public void deallocate(BufferManager bufferManager, int[] map, int index, Object cache) {

	}

	@Override public boolean canAllocate(BufferManager bufferManager, int[] map, int size, int alignment, Object cache) {
		return false;
	}

	protected static class BuddyAllocationAlgorithmRoot {
		protected final static int DIVIDE_SIZE = 2;
		protected final BuddyAllocationAlgorithmRoot parent;
		protected int index;
		protected final int length;
		protected boolean allocated;
		protected BuddyAllocationAlgorithmRoot[] roots;

		public BuddyAllocationAlgorithmRoot(BuddyAllocationAlgorithmRoot parent, int index, int length) {
			this.parent = parent;
			this.index = index;
			this.length = length;
			this.allocated = false;
		}

		protected void divide(int[] map, Object[] cache) {
			if(roots != null) return;
			if(length < DEFAULT_MIN_GROUP) throw new OutOfMemoryError();
			roots = new BuddyAllocationAlgorithmRoot[DIVIDE_SIZE];
			int size = length / roots.length;
			for(int i = 0; i < roots.length; i++)
				roots[i] = new BuddyAllocationAlgorithmRoot(this, DIVIDE_SIZE * index + i, size);
			if(parent != null) parent.shift(roots.length - 1, this);
			if(cache[D_ENABLED] == null) cache[D_ENABLED] = true;
			if(cache[D_START_LEN] == null) cache[D_START_LEN] = length;
			if(cache[D_INDEX] == null) cache[D_INDEX] = index;
			if(cache[D_SIZE] == null) cache[D_SIZE] = DIVIDE_SIZE;
			cache[D_REPEAT] = cache[D_REPEAT] == null ? 1 : ((int) cache[D_REPEAT]) + 1;
			index = -1;
		}
		protected void merge(int[] map, Object[] cache) {
			this.allocated = false;
			this.roots = null;
			this.index = 0;
		}
		protected void shift(int by, BuddyAllocationAlgorithmRoot callee) {
			if(roots == null) { index += by; return; }
			for(BuddyAllocationAlgorithmRoot root : roots)
				if(root != callee) root.shift(by, this);
			if(parent != null && parent != callee) parent.shift(by, this);
		}

		public BuddyAllocationAlgorithmRoot allocate(int size, int[] map, Object[] cache) {
			if(size > length) return null;
			if(size / DIVIDE_SIZE >= length / DIVIDE_SIZE) { this.allocated = true; return this; }
			if(roots == null) divide(map, cache); int i = 0;
			BuddyAllocationAlgorithmRoot result = null;
			for(; i < roots.length; i++) {
				if(roots[i].allocated) continue;
				if(result != null) break;
				result = roots[i].allocate(size, map, cache);
			} if(i == roots.length) this.allocated = true;
			return result;
		}
		public BuddyAllocationAlgorithmRoot deallocate(int index, int[] map, Object[] cache) {
			if(roots == null && index == this.index) { this.allocated = false; return this; }
			if(roots == null) return null; int i = 0; int d = 0;
			BuddyAllocationAlgorithmRoot result = null;
			for(; i < roots.length; i++) {
				if(!roots[i].allocated) { d++; continue; }
				if(result != null) break;
				if(roots[i].index < index) continue;
				result = roots[i].deallocate(index, map, cache); d++;
			} if(d != 0) { this.allocated = false; if(d == roots.length) merge(map, cache); }
			return result;
		}
	}

	private static int sum(int... x) {
		int result = 0;
		for(int value : x) result += Math.abs(value);
		return result;
	}
	private static int searchMin(int... x) {
		int result = Integer.MAX_VALUE;
		for(int value : x)
			if(value < result) result = value;
		return result;
	}
	private static int log(int base, int value) {
		return base == 2 ? fastLog2(value) : (int) (Math.log(value) / Math.log(base));
	}
	private static int fastLog2(int bits) {
		int log = 0;
		if((bits & 0xffff0000) != 0) { bits >>>= 16; log = 16; }
		if(bits >= 256) { bits >>>= 8; log += 8; }
		if(bits >= 16 ) { bits >>>= 4; log += 4; }
		if(bits >= 4  ) { bits >>>= 2; log += 2; }
		return log + (bits >>> 1);
	}
	private static long fastPow(int base, int exp) {
		long result = 1; while (exp > 0) {
			if ((exp & 1) == 0) { base *= base; exp >>>= 1;
			} else { result *= base; exp--; }
		} return result;
	}
}

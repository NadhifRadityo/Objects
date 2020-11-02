package io.github.NadhifRadityo.Objects.ObjectUtils.Buffer.BufferAlgorithm;

import io.github.NadhifRadityo.Objects.ObjectUtils.Buffer.BufferManager;

@Deprecated
public class BuddyAllocationAlgorithmPrimitive implements BufferAllocationAlgorithm {
	protected static final int DEFAULT_MIN_GROUP = (int) fastPow(2, 3);
	protected static final int CACHE_SIZE = 6;
	protected static final int ORIGINAL_SIZE = 0;
	protected static final int MAX_USABLE_SIZE = 1;
	protected static final int POSSIBLE_GROUPS = 2;
	protected static final int MAP_COUNT = 3;
	protected static final int LAST_DIVIDE_INDEX = 4;
	protected static final int MERGE_SIDE_COUNT = 5;

	// map ->> negative means used, positive means free.
	@Override
	public int allocate(BufferManager bufferManager, int[] map, int size, Object _cache) throws OutOfMemoryError {
		Object[] cache = _cache == null ? null : (Object[]) _cache;
		if(cache == null) {
			cache = new Object[CACHE_SIZE];
			cache[ORIGINAL_SIZE] = sum(map);
			cache[MAX_USABLE_SIZE] = (int) fastPow(2, fastLog2((int) cache[ORIGINAL_SIZE]));
			cache[POSSIBLE_GROUPS] = fastLog2((int) cache[MAX_USABLE_SIZE]) - fastLog2(DEFAULT_MIN_GROUP) + 1;
			cache[MAP_COUNT] = new int[(int) cache[POSSIBLE_GROUPS]];
			cache[MERGE_SIDE_COUNT] = new int[2][((int[]) cache[MAP_COUNT]).length + 1];

			map = new int[(int) cache[ORIGINAL_SIZE] > (int) cache[MAX_USABLE_SIZE] ? 2 : 1];
			map[0] = (int) cache[MAX_USABLE_SIZE];
			if(map.length == 2) map[1] = -((int) cache[ORIGINAL_SIZE]) - ((int) cache[MAX_USABLE_SIZE]);
			((int[]) cache[MAP_COUNT])[0] = 1;
			((int[][]) cache[MERGE_SIDE_COUNT])[0][0] = 1;
			((int[][]) cache[MERGE_SIDE_COUNT])[1][0] = 1;
			bufferManager.updateMap(map);
			bufferManager.updateCache(cache);
		}

		if(size <= 0) throw new IllegalArgumentException("Allocation size cannot be equal or less than zero");
		if(size > (int) cache[ORIGINAL_SIZE]) throw new OutOfMemoryError("Heap reached");
		if(size > (int) cache[MAX_USABLE_SIZE]) throw new IllegalArgumentException("Remaining buffer that are not the power of 2 is ignored");;

		size = Math.max(DEFAULT_MIN_GROUP, size);
		int logSize = fastLog2(size);
		logSize += size - fastPow(2, logSize) > 0 ? 1 : 0;
		size = (int) fastPow(2, logSize);
		int target = logSize - fastLog2(DEFAULT_MIN_GROUP) + 1;
		if(((int[]) cache[MAP_COUNT])[((int[]) cache[MAP_COUNT]).length - target] == 0) bufferManager.updateMap(map = divide(map, logSize, cache));
		if(cache[LAST_DIVIDE_INDEX] != null) {
			int i = (int) cache[LAST_DIVIDE_INDEX];
			cache[LAST_DIVIDE_INDEX] = null;
			((int[]) cache[MAP_COUNT])[((int[]) cache[MAP_COUNT]).length - target]--;
			map[i] *= -1;
			return i;
		}
		for(int i = 0; i < map.length; i++) { // TODO: start index shouldn't always be zero. make it dynamics!
			if(map[i] <= 0 || map[i] != size) continue;
			((int[]) cache[MAP_COUNT])[((int[]) cache[MAP_COUNT]).length - target]--;
			map[i] *= -1;
			return i;
		}
		throw new IllegalStateException("State might be changed unexpectedly");
	}

	private static int[] divide(int[] map, int target, Object[] cache) {
		int size = (int) fastPow(2, target);
		int divideCount = 0;
		int choosed = -1;
		for(int i = 0; i < map.length; i++) {
			if(map[i] <= 0 || map[i] < size || map[i] % DEFAULT_MIN_GROUP != 0) continue;
			if(choosed == -1 || map[i] < map[choosed]) choosed = i;
		}
		if(choosed == -1) throw new OutOfMemoryError("Heap reached");
		int expect = map[choosed];
		while(expect > size) {
			divideCount++;
			expect /= 2;
		}

		int[] newMap = new int[map.length + divideCount];
		System.arraycopy(map, 0, newMap, 0, choosed);
		((int[]) cache[MAP_COUNT])[((int[]) cache[MAP_COUNT]).length - (fastLog2(map[choosed]) - fastLog2(DEFAULT_MIN_GROUP) + 1)]--;
		for(int i = -1; i < divideCount; i++) {
			int lsize = map[choosed] / (int) fastPow(2, divideCount - Math.max(0, i));
			newMap[choosed + i + 1] = lsize;
			((int[]) cache[MAP_COUNT])[((int[]) cache[MAP_COUNT]).length - (fastLog2(lsize) - fastLog2(DEFAULT_MIN_GROUP) + 1)]++;
		}
		System.arraycopy(map, choosed + 1, newMap, choosed + divideCount + 1, map.length - choosed - 1);
		cache[LAST_DIVIDE_INDEX] = choosed;
		return newMap;
	}

	@Override public void deallocate(BufferManager bufferManager, int[] map, int index, Object _cache) {
		Object[] cache = _cache == null ? null : (Object[]) _cache;
		if(cache == null) throw new IllegalStateException("Cannot deallocate when nothing is allocated");
		int[] newMap = merge(map, index, cache);
		if(newMap != null) bufferManager.updateMap(newMap);
	}

	@Override public boolean canAllocate(BufferManager bufferManager, int[] map, int size, Object cache) {
		return false;
	}

//	private static int[] merge(int[] map, int index, Object[] cache) {
//		if(map[index] > 0 || map[index] % DEFAULT_MIN_GROUP != 0) return null;
//		int[] lookup = new int[map.length]; // 0 means grouped, otherwise -> size / DEFAULT_MIN_GROUP
//		for(int i = 0; i < map.length; i++) lookup[i] = map[i] > 0 ? 1 : -1;
//		int group = 0;
//		int lastIndex = index;
//		int lastSize = Math.abs(map[index]);
//		while(true) {
//			int toMerge = -1;
//			int offset = 0;
//			int iterations = 0;
//			int state = 0b000; // from left -> [0] = left edge reached, [1] = right edge reached, [2] = direction left(0) / right(1)
//			long sum = 0; // 4 bytes for left side and 4 bytes from right side
//			while((state & 0b110) != 0b110) { // while left edge is not yet reached and right edge is not yet reached
//				boolean toRight = (state & 0b001) == 1;
//				int localOffset = (toRight ? 1 : -1) * (++iterations);
//				if(lastIndex + localOffset < 0 || lastIndex + localOffset >= map.length) break;
//				if(map[lastIndex + localOffset] < 0 || map[lastIndex + localOffset] > lastSize) state = (state & (toRight ? 0b101 : 0b011)) + (1 << (toRight ? 1 : 2));
//				else if(lookup[lastIndex + localOffset] == 1) {
//				}
//				if((state & 0b100) == 1 && toRight) toRight = false; //note that in the last expression, right/left is flipped;
//				if((state & 0b010) == 1 && !toRight) toRight = true;
//				state = (state & 0b110) + (toRight ? 0 : 1);
//			}
//			if(offset == 0) break;
//			if(lastIndex > 0 && map[lastIndex - 1] == lastSize)
//				toMerge = lastIndex - 1;
//			if(lastIndex < map.length - 1 && map[lastIndex + 1] == lastSize)
//				toMerge = lastIndex + 1;
//			if(toMerge == -1) break;
//			lastIndex = toMerge;
//			lastSize += map[toMerge];
//			lookup[toMerge] = /*-1 * ++group*/0;
//		}
////		else if(index < map.length - 1 && map[index + 1] > 0 && map[index + 1] % DEFAULT_MIN_GROUP == 0) {
////			toMerge[mergeSize++] = index;
////		}
//	}

	private static int[] merge(int[] map, int index, Object[] cache) {
		if(map[index] > 0 || map[index] % DEFAULT_MIN_GROUP != 0) return null;
		int[][] mergeMapCount = (int[][]) cache[MERGE_SIDE_COUNT]; int[] mapCount = ((int[]) cache[MAP_COUNT]);
		mapCount[mapCount.length - (fastLog2(Math.abs(map[index])) - fastLog2(DEFAULT_MIN_GROUP) + 1)]++;
		System.arraycopy(mapCount, 0, mergeMapCount[0], 0, mapCount.length);
		System.arraycopy(mapCount, 0, mergeMapCount[1], 0, mapCount.length);
		long iterations = 1;
		int lastState = 0b000;
		int state = 0b000; // from left -> [0] = left edge reached, [1] = right edge reached, [2] = direction left(0) / right(1)
		int toDeallocateSize = Math.abs(map[index]);
		long sum = (((long) toDeallocateSize) << 32) | (toDeallocateSize & 0xffffffffL); // 4 bytes for the left side and 4 bytes for the right side
		while((state & 0b110) != 0b110) { // while left edge is not yet reached and right edge is not yet reached
			boolean toRight = (state & 0b001) == 1;
			int localOffset = ((int) iterations) + (((state & 0b110) != 0b000 && (lastState & 0b110) != 0b00) || ((state & 0b110) == 0b000 && (state & 0b001) != (lastState & 0b001)) ? 1 : 0);
			iterations = (((long) ((int) (iterations >> 32) + 1)) << 32) | (localOffset & 0xffffffffL);
			localOffset *= toRight ? 1 : -1; lastState = ((int) (iterations >> 32)) % 2 == 0 ? state : lastState;
			if(index + localOffset < 0 || index + localOffset >= map.length || map[index + localOffset] < 0 ||
					map[index + localOffset] > (toRight ? (int) sum : sum >> 32)) {
				state = (state & (toRight ? 0b101 : 0b011)) + (1 << (toRight ? 1 : 2));
				mergeMapCount[toRight ? 1 : 0][mergeMapCount[toRight ? 1 : 0].length - 1] = localOffset + (toRight ? -1 : 1);
			} else {
				// The main goal is I add the size of map to the sum whether it's on the left / right side;
				int size = map[index + localOffset];
				int[] localMapCount = mergeMapCount[toRight ? 1 : 0];
				int currentId = localMapCount.length - 1 - (fastLog2(size) - fastLog2(DEFAULT_MIN_GROUP) + 1);
				if(localMapCount[currentId] >= 2) {
					int add = localMapCount[currentId] / 2;
					localMapCount[currentId] %= 2;
					if(currentId - 1 >= 0) localMapCount[currentId - 1] += add;
				}
				sum = (((long) ((int) (sum >> 32) + (!toRight ? size : 0))) << 32) | (((int) sum + (toRight ? size : 0)) & 0xffffffffL);
				// TODO if the diff between the left and right sum is significant, then just return the biggest, or maybe not?
			}
			if((state & 0b100) == 0b100 && toRight) toRight = false; //note that in the last expression, right/left is flipped;
			if((state & 0b010) == 0b010 && !toRight) toRight = true;
			state = (state & 0b110) + (toRight ? 0 : 1);
		}

		long range;
		int sumTarget;
		// What a coincidence?
		int sumLeft = (int) (sum >> 32);
		int sumRight = (int) sum;
		int totalSum = sumLeft + sumRight - toDeallocateSize;
		if(fastPow(2, fastLog2(totalSum)) == totalSum) {
			range = (((long) (index - mergeMapCount[0][mergeMapCount[0].length - 1])) << 32) | ((index + mergeMapCount[1][mergeMapCount[1].length - 1]) & 0xffffffffL);
			sumTarget = totalSum;
			int[] mapCountSum = new int[((int[]) cache[MAP_COUNT]).length];
			for(int i = 0; i < mapCountSum.length; i++) mapCountSum[i] = mergeMapCount[0][i] + mergeMapCount[1][i] - ((int[]) cache[MAP_COUNT])[i];
			cache[MAP_COUNT] = mapCountSum;
		} else {
			boolean isRightBiggest = sumRight > sumLeft;
			range = (((long) (index - (isRightBiggest ? 0 : mergeMapCount[0][mergeMapCount[0].length - 1]))) << 32) | ((index + (isRightBiggest ? mergeMapCount[1][mergeMapCount[1].length - 1] : 0)) & 0xffffffffL);
			sumTarget = isRightBiggest ? sumRight : sumLeft;
			cache[MAP_COUNT] = mergeMapCount[isRightBiggest ? 1 : 0];
		}

		int x = (int) (range >> 32);
		int y = (int) range;
		int[] newMap = new int[map.length - (y - x)];
		System.arraycopy(map, 0, newMap, 0, x);
		newMap[index] = sumTarget;
		System.arraycopy(map, y + 1, newMap, index + 1, map.length - y - 1);
		return newMap;
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

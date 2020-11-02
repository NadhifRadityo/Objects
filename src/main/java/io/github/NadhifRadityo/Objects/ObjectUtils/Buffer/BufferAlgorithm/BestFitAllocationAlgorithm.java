package io.github.NadhifRadityo.Objects.ObjectUtils.Buffer.BufferAlgorithm;

import io.github.NadhifRadityo.Objects.ObjectUtils.Buffer.BufferManager;
import io.github.NadhifRadityo.Objects.Utilizations.NumberUtils;

public class BestFitAllocationAlgorithm implements BufferAllocationAlgorithm {
	public static final int DEF_ALLOC_SPLIT_DELTA = 32;
	public static final int CACHE_SIZE = 4;
	public static final int ALLOC_SPLIT_DELTA = 0;
	public static final int ORIGINAL_SIZE = 1;
	public static final int ADDITIONAL_CHECKS = 2;
	public static final int MAP_CACHE = 3;

	@Override public int allocate(BufferManager bufferManager, int[] map, int size, Object _cache) throws OutOfMemoryError {
		Object[] cache = _cache == null ? null : (Object[]) _cache;
		if(cache == null) {
			cache = new Object[CACHE_SIZE];
			cache[ALLOC_SPLIT_DELTA] = DEF_ALLOC_SPLIT_DELTA;
			cache[ORIGINAL_SIZE] = NumberUtils.add(map);
			cache[ADDITIONAL_CHECKS] = false;
			cache[MAP_CACHE] = new int[10][];
			bufferManager.updateCache(cache);
		}

		int index = getBestFitIndex(map, size);
		if(index == -1) throw new OutOfMemoryError();
		if(map[index] - size >= (int) cache[ALLOC_SPLIT_DELTA]) {
			int[][] mapCache = (int[][]) cache[MAP_CACHE];
			int[] newMap = null;
			for(int i = 0; i < mapCache.length; i++) {
				if(mapCache[i] == null || mapCache[i].length != map.length + 1) continue;
				newMap = mapCache[i]; mapCache[i] = null; break;
			} if(newMap == null) newMap = new int[map.length + 1];
			System.arraycopy(map, 0, newMap, 0, index);
			newMap[index] = size;
			newMap[index + 1] = map[index] - size;
			System.arraycopy(map, index + 1, newMap, index + 2, map.length - index - 1);
			for(int i = 0; i < mapCache.length; i++) {
				if(mapCache[i] != null) continue;
				mapCache[i] = map; break;
			} bufferManager.updateMap(map = newMap);
		} map[index] *= -1;

		int fromLeft = 0;
		for(int i = 0; i < index; i++)
			fromLeft += Math.abs(map[i]);
		return fromLeft;
	}
	@Override public void deallocate(BufferManager bufferManager, int[] map, int index, Object _cache) {
		Object[] cache = _cache == null ? null : (Object[]) _cache;
		if(cache == null) throw new IllegalStateException();

		int sum = 0;
		int rightEdge = 0;
		int leftEdge = 0;
		map[index] *= -1;
		for(int i = index; i < map.length; i++) {
			if(map[i] < 0) break;
			sum += map[i]; rightEdge = i;
		}
		for(int i = index; i >= 0; i--) {
			if(map[i] < 0) break;
			sum += map[i]; leftEdge = i;
		} sum -= map[index];

		int[][] mapCache = (int[][]) cache[MAP_CACHE];
		int[] newMap = null;
		for(int i = 0; i < mapCache.length; i++) {
			if(mapCache[i] == null || mapCache[i].length != map.length - (rightEdge - leftEdge)) continue;
			newMap = mapCache[i]; mapCache[i] = null; break;
		} if(newMap == null) newMap = new int[map.length - (rightEdge - leftEdge)];
		System.arraycopy(map, 0, newMap, 0, leftEdge);
		newMap[leftEdge] = sum;
		System.arraycopy(map, rightEdge + 1, newMap, leftEdge + 1, map.length - rightEdge - 1);
		if((boolean) cache[ADDITIONAL_CHECKS] && NumberUtils.add(newMap) != (int) cache[ORIGINAL_SIZE])
			throw new IllegalStateException("State might be changed unexpectedly");
		for(int i = 0; i < mapCache.length; i++) {
			if(mapCache[i] != null) continue;
			mapCache[i] = map; break;
		} bufferManager.updateMap(newMap);
	}
	@Override public boolean canAllocate(BufferManager bufferManager, int[] map, int size, Object cache) {
		return getBestFitIndex(map, size) != -1;
	}

	protected static int getBestFitIndex(int[] map, int size) {
		int index = -1;
		for(int i = 0; i < map.length; i++) {
			if(map[i] >= size && (index == -1 || map[i] < map[index])) index = i;
		} return index;
	}
}

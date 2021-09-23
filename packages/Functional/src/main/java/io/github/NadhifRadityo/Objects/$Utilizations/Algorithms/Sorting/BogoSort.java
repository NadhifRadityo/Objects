package io.github.NadhifRadityo.Objects.$Utilizations.Algorithms.Sorting;

import io.github.NadhifRadityo.Objects.$Interface.Functional.Comparator;
import io.github.NadhifRadityo.Objects.$Utilizations.PublicRandom;

public abstract class BogoSort implements Sorting {
	public static final BogoSort DEFAULT = new BogoSort() { };

	protected long seed;
	public BogoSort(long seed) { setSeed(seed); }
	public BogoSort() { this(PublicRandom.getRandom().nextLong()); }

	public long getSeed() { return seed; }
	public void setSeed(long seed) {
		this.seed = seed;
		seed ^= seed >>> 33;
		seed *= 0xff51afd7ed558ccdL;
		seed ^= seed >>> 33;
		seed *= 0xc4ceb9fe1a85ec53L;
		seed ^= seed >>> 33;

		seed += 0x9E3779B97F4A7C15L;
		seed = (seed ^ (seed >>> 30)) * 0xBF58476D1CE4E5B9L;
		seed = (seed ^ (seed >>> 27)) * 0x94D049BB133111EBL;
		s0 = seed ^ ( seed >>> 31 );
		seed += 0x9E3779B97F4A7C15L;
		seed = (seed ^ (seed >>> 30)) * 0xBF58476D1CE4E5B9L;
		seed = (seed ^ (seed >>> 27)) * 0x94D049BB133111EBL;
		s1 = seed ^ (seed >>> 31 );
	}

	@Override public <T> void sort(T[] array, Comparator<? super T> comparator, int start, int end) {
		T temp; boolean sorted; while(true) {
			sorted = true;
			for(int i = start + 1; i < end; i++) {
				if(comparator.compare(array[i], array[i - 1]) >= 0)
					continue;
				sorted = false; break;
			} if(sorted) break;
			for(int i = start + 1; i < end; i++) {
				int target = (int) (nextDouble() * i);
				temp = array[i];
				array[i] = array[target];
				array[target] = temp;
			}
		}
	}
	@Override public void sort(int[] array, Comparator.PIntegerComparator comparator, int start, int end) {
		int temp; boolean sorted; while(true) {
			sorted = true;
			for(int i = start + 1; i < end; i++) {
				if(comparator._compare(array[i], array[i - 1]) >= 0)
					continue;
				sorted = false; break;
			} if(sorted) break;
			for(int i = start + 1; i < end; i++) {
				int target = (int) (nextDouble() * i);
				temp = array[i];
				array[i] = array[target];
				array[target] = temp;
			}
		}
	}
	@Override public void sort(long[] array, Comparator.PLongComparator comparator, int start, int end) {
		long temp; boolean sorted; while(true) {
			sorted = true;
			for(int i = start + 1; i < end; i++) {
				if(comparator._compare(array[i], array[i - 1]) >= 0)
					continue;
				sorted = false; break;
			} if(sorted) break;
			for(int i = start + 1; i < end; i++) {
				int target = (int) (nextDouble() * i);
				temp = array[i];
				array[i] = array[target];
				array[target] = temp;
			}
		}
	}
	@Override public void sort(short[] array, Comparator.PShortComparator comparator, int start, int end) {
		short temp; boolean sorted; while(true) {
			sorted = true;
			for(int i = start + 1; i < end; i++) {
				if(comparator._compare(array[i], array[i - 1]) >= 0)
					continue;
				sorted = false; break;
			} if(sorted) break;
			for(int i = start + 1; i < end; i++) {
				int target = (int) (nextDouble() * i);
				temp = array[i];
				array[i] = array[target];
				array[target] = temp;
			}
		}
	}
	@Override public void sort(float[] array, Comparator.PFloatComparator comparator, int start, int end) {
		float temp; boolean sorted; while(true) {
			sorted = true;
			for(int i = start + 1; i < end; i++) {
				if(comparator._compare(array[i], array[i - 1]) >= 0)
					continue;
				sorted = false; break;
			} if(sorted) break;
			for(int i = start + 1; i < end; i++) {
				int target = (int) (nextDouble() * i);
				temp = array[i];
				array[i] = array[target];
				array[target] = temp;
			}
		}
	}
	@Override public void sort(double[] array, Comparator.PDoubleComparator comparator, int start, int end) {
		double temp; boolean sorted; while(true) {
			sorted = true;
			for(int i = start + 1; i < end; i++) {
				if(comparator._compare(array[i], array[i - 1]) >= 0)
					continue;
				sorted = false; break;
			} if(sorted) break;
			for(int i = start + 1; i < end; i++) {
				int target = (int) (nextDouble() * i);
				temp = array[i];
				array[i] = array[target];
				array[target] = temp;
			}
		}
	}
	@Override public void sort(char[] array, Comparator.PCharacterComparator comparator, int start, int end) {
		char temp; boolean sorted; while(true) {
			sorted = true;
			for(int i = start + 1; i < end; i++) {
				if(comparator._compare(array[i], array[i - 1]) >= 0)
					continue;
				sorted = false; break;
			} if(sorted) break;
			for(int i = start + 1; i < end; i++) {
				int target = (int) (nextDouble() * i);
				temp = array[i];
				array[i] = array[target];
				array[target] = temp;
			}
		}
	}

	protected long s0, s1;
	protected long nextLong() {
		long s0 = this.s0;
		long s1 = this.s1;
		long result = s0 + s1; s1 ^= s0;
		this.s0 = Long.rotateLeft(s0, 55) ^ s1 ^ s1 << 14;
		this.s1 = Long.rotateLeft(s1, 36);
		return result;
	}
	protected double nextDouble() {
		return Double.longBitsToDouble(nextLong() >>> 12 | 0x3FFL << 52) - 1.0;
	}
}

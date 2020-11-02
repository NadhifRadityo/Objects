package io.github.NadhifRadityo.Objects.TestPrograms;

import io.github.NadhifRadityo.Objects.Console.Logger;
import io.github.NadhifRadityo.Objects.Object.Comparator;
import io.github.NadhifRadityo.Objects.Object.ReferencedCallback;
import io.github.NadhifRadityo.Objects.TestProgram;
import io.github.NadhifRadityo.Objects.Tester;
import io.github.NadhifRadityo.Objects.Utilizations.Algorithms.Sorting.Sorting;
import io.github.NadhifRadityo.Objects.Utilizations.ArrayUtils;
import io.github.NadhifRadityo.Objects.Utilizations.BitwiseUtils;
import io.github.NadhifRadityo.Objects.Utilizations.BufferUtils;
import io.github.NadhifRadityo.Objects.Utilizations.NumberUtils;
import io.github.NadhifRadityo.Objects.Utilizations.PrivilegedUtils;
import io.github.NadhifRadityo.Objects.Utilizations.UnsafeUtils;
import sun.misc.Unsafe;

import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.util.HashMap;

@TestProgram(group = "Utilizations")
public class SortingAlgorithmsTest extends Tester {
	public SortingAlgorithmsTest() {
		super("SortingAlgorithmsTest");
	}

	@SuppressWarnings({"unchecked", "ConstantConditions"})
	@Override protected boolean doRun(Logger logger, ByteBuffer dump) throws Throwable {
		Sorting[] toBeTested = new Sorting[] {
				Sorting.BOGO_SORT, Sorting.BUBBLE_SORT, Sorting.BUCKET_SORT, Sorting.COUNTING_SORT, Sorting.HEAP_SORT, Sorting.INSERTION_SORT,
				Sorting.INTRO_SORT, Sorting.MERGE_SORT, Sorting.QUICK_SORT, Sorting.SELECTION_SORT, Sorting.SHELL_SORT
		};
		ReferencedCallback.BooleanReferencedCallback CHECK_DOUBLE = (args) -> {
			double[] array = (double[]) args[0];
			double last = array[0];
			for(int i = 1; i < array.length; i++)
//				if(array[i] > last) return false;
				if(array[i] < last) return false;
			return true;
		};
		ReferencedCallback.BooleanReferencedCallback CHECK_INTEGER = (args) -> {
			int[] array = (int[]) args[0];
			int last = array[0];
			for(int i = 1; i < array.length; i++)
//				if(array[i] > last) return false;
				if(array[i] < last) return false;
			return true;
		};
		ReferencedCallback<Object[]> generateArray = (args) -> { int len = (int) args[1]; String sort = ((String) args[0]).toLowerCase(); switch(sort) {
			case "bogosort": {
				int[] array = new int[len];
				for(int i = 0; i < array.length; i++)
//					array[i] = array.length - i;
					array[i] = i;
				return new Object[] { array, java.util.Comparator.comparingInt(o -> (int) o), CHECK_INTEGER };
			}
			case "bubblesort":
			case "bucketsort": if(sort.equalsIgnoreCase("bucketsort") && len > (int) Math.pow(2, 14)) { len = (int) Math.pow(2, 14); logger.warn("Bucket sort capped its max value!"); }
			case "heapsort":
			case "insertionsort":
			case "introsort":
			case "mergesort":
			case "quicksort":
			case "selectionsort":
			case "shellsort": {
				double[] array = new double[len];
				for(int i = 0; i < array.length; i++)
					array[i] = NumberUtils.rand(0d, 1d);
//				return new Object[] { array, (Comparator<Double>) (o1, o2) -> Double.compare(o2, o1), CHECK_DOUBLE };
				return new Object[] { array, java.util.Comparator.comparingDouble(o -> (double) o), CHECK_DOUBLE };
			}
			case "countingsort": {
				int[] array = new int[len];
				for(int i = 0; i < array.length; i++)
					array[i] = NumberUtils.rand(0, 100);
//				return new Object[] { array, (Comparator<Integer>) (o1, o2) -> Integer.compare(o2, o1), CHECK_INTEGER };
				return new Object[] { array, java.util.Comparator.comparingInt(o -> (int) o), CHECK_INTEGER };
			}
		} throw new IllegalArgumentException("Undefined sort"); };
		int session = 15;
		HashMap<Sorting, long[]> times = new HashMap<>();
		HashMap<Sorting, boolean[]> successes = new HashMap<>();
		for(Sorting sorting : toBeTested)
			times.put(sorting, new long[session]);
		for(Sorting sorting : toBeTested)
			successes.put(sorting, new boolean[session]);
		dump.putInt(session);
		dump.putInt(toBeTested.length);
		byte successesByte = 0; int j = 0;
		int successOffset = dump.position();
		dump.position(dump.position() + (toBeTested.length * session / 8 + ((toBeTested.length * session) % 8 > 0 ? 1 : 0)));
		OUTER: for(int i = 1; i <= session; i++) {
			logger.debug("");
			int size = (int) Math.pow(2, i);
			dump.putInt(size);
			logger.warn("Array size: " + size);
			for(Sorting sorting : toBeTested) {
				String className = sorting.getClass().getName();
				String packageName = sorting.getClass().getPackage().getName();
				String sortingName = className.substring(packageName.length() + 1, className.length() - 2);
				logger.info("Testing: " + sortingName);
				Object[] generatedArray = generateArray.get(sortingName, size);
				copyArray(generatedArray[0], dump);
//				logger.debug("Array: " + ArrayUtils.deepToString(generatedArray[0]));
				long start = System.nanoTime();
				sorting.sort(generatedArray[0], Comparator.natural((java.util.Comparator<Object>) generatedArray[1]));
				long took = System.nanoTime() - start;
				times.get(sorting)[i - 1] = took;
//				logger.debug("Sorted: " + ArrayUtils.deepToString(generatedArray[0]));
				logger.info("Took: " + ((float) took / 1000000) + "ms");
				copyArray(generatedArray[0], dump);
				dump.putLong(took);
				boolean succeeded = ((ReferencedCallback.BooleanReferencedCallback) (generatedArray[2])).get(generatedArray[0]);
				successes.get(sorting)[i - 1] = succeeded;
				if(succeeded) logger.info("Status: Succeeded");
				else { logger.error("Status: Failed"); break OUTER; }
				j++; successesByte = (byte) ((successesByte << 1) + (succeeded ? 1 : 0));
				if(j % Byte.SIZE == 0) { dump.put(successOffset++, successesByte); successesByte = 0; }
				logger.debug("");
			}
			logger.warn("GC Start");
			long start = System.nanoTime();
			System.gc();
			logger.warn("GC End, " + "Took: " + ((float) (System.nanoTime() - start) / 1000000) + "ms");
			logger.debug("");
		}
		logger.warn("Benchmark Results: (ns)");
		for(Sorting sorting : toBeTested) {
			String className = sorting.getClass().getName();
			String packageName = sorting.getClass().getPackage().getName();
			String sortingName = className.substring(packageName.length() + 1, className.length() - 2);
			long[] benchmark = times.get(sorting);
			logger.info(sortingName + ": " + ArrayUtils.deepToString(benchmark));
		}
		logger.debug("");
		logger.warn("Time scales:");
		float[] ratios = new float[session - 1];
		for(Sorting sorting : toBeTested) {
			String className = sorting.getClass().getName();
			String packageName = sorting.getClass().getPackage().getName();
			String sortingName = className.substring(packageName.length() + 1, className.length() - 2);
			long[] benchmark = times.get(sorting);
			for(int i = 1; i < benchmark.length; i++)
				ratios[i - 1] = (float) benchmark[i] / benchmark[i - 1];
			dump.putFloat(NumberUtils.add(ratios));
			logger.info(sortingName + ": " + (NumberUtils.add(ratios) / ratios.length) + "x");
		}
		for(Sorting sorting : toBeTested)
			if(BitwiseUtils.or(BitwiseUtils.compliment(successes.get(sorting)))) return false;
		return true;
	}

	protected static void copyArray(Object array, ByteBuffer dump) { PrivilegedUtils.doPrivileged(() -> {
		if(!array.getClass().isArray()) throw new IllegalArgumentException();
		long address = BufferUtils.__getAddress(dump) + dump.position();
		int offset = 0; int scale = 1;
		if(array instanceof int[]) { offset = Unsafe.ARRAY_INT_BASE_OFFSET; scale = Unsafe.ARRAY_INT_INDEX_SCALE; }
		else if(array instanceof double[]) { offset = Unsafe.ARRAY_DOUBLE_BASE_OFFSET; scale = Unsafe.ARRAY_DOUBLE_INDEX_SCALE; }
		dump.position(dump.position() + Array.getLength(array) * scale);
		UnsafeUtils.copyMemory(array, offset, null, address, Array.getLength(array) * scale);
	}); }
}

package io.github.NadhifRadityo.Objects.TestPrograms;

import io.github.NadhifRadityo.Objects.Console.Logger;
import io.github.NadhifRadityo.Objects.ObjectUtils.RandomString;
import io.github.NadhifRadityo.Objects.TestProgram;
import io.github.NadhifRadityo.Objects.Tester;
import io.github.NadhifRadityo.Objects.Utilizations.ArrayUtils;
import io.github.NadhifRadityo.Objects.Utilizations.PublicRandom;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

@TestProgram(group = "Utilizations")
public class ArraysDeepToStringTest extends Tester {
	public ArraysDeepToStringTest() {
		super("ArraysDeepToStringTest");
	}

	@Override protected boolean doRun(Logger logger, ByteBuffer dump) throws Throwable {
		Object[] object = new Object[] {
				new int[] {
						27, 17, 7, 3
				},
				new String[] {
						"twenty seven",
						"seven teen",
						"three",
						null
				},
				new long[][] {
						new long[] {
								System.currentTimeMillis(),
								System.currentTimeMillis() - PublicRandom.getRandom().nextLong()
						},
						new long[] {
								System.currentTimeMillis() - PublicRandom.getRandom().nextLong(),
								System.currentTimeMillis() - PublicRandom.getRandom().nextLong()
						},
						null
				},
				new short[][] {
						null,
						null,
						new short[] {
								((Integer) PublicRandom.getRandom().nextInt()).shortValue(),
								((Integer) PublicRandom.getRandom().nextInt()).shortValue()
						}
				},
				new boolean[][][] {
						new boolean[][] {
								new boolean[] {
										false, true
								},
								null,
								new boolean[] {
										false
								}
						},
						null
				},
				new Object[][] {
						new Object[] {
								null,
								"WOY",
								((Integer) PublicRandom.getRandom().nextInt()).shortValue(),
								System.currentTimeMillis() - PublicRandom.getRandom().nextLong(),
								new RandomString().toString()
						}
				},
				new StringBuilder("deep to string test")
		};

		String result = ArrayUtils.deepToString(object);
		byte[] bytes = result.getBytes(StandardCharsets.UTF_8);
		dump.putInt(bytes.length); dump.put(bytes);
		logger.log(result);

		long start = System.currentTimeMillis();
		for(int i = 0; i < 200; i++) ArrayUtils.deepToString(object);
		logger.log("200 Repetitions -> Took: " + (System.currentTimeMillis() - start) + "ms");

		start = System.currentTimeMillis();
		for(int i = 0; i < 2000; i++) ArrayUtils.deepToString(object);
		logger.log("2000 Repetitions -> Took: " + (System.currentTimeMillis() - start) + "ms");

		start = System.currentTimeMillis();
		for(int i = 0; i < 7000; i++) ArrayUtils.deepToString(object);
		logger.log("7000 Repetitions -> Took: " + (System.currentTimeMillis() - start) + "ms");
		return result.equals(ArrayUtils.deepToString(object));
	}
}

package io.github.NadhifRadityo.Objects.TestPrograms;

import io.github.NadhifRadityo.Objects.Console.Logger;
import io.github.NadhifRadityo.Objects.Object.ReferencedCallback;
import io.github.NadhifRadityo.Objects.Pool.Pool;
import io.github.NadhifRadityo.Objects.TestProgram;
import io.github.NadhifRadityo.Objects.Tester;
import io.github.NadhifRadityo.Objects.Utilizations.NumberUtils;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Set;

@TestProgram(group = "Pool")
public class PoolTest extends Tester {
	public PoolTest() {
		super("PoolTest");
	}

	@Override protected boolean doRun(Logger logger, ByteBuffer dump) throws Throwable {
		HashMap<Class<?>, long[]> benchmark = new HashMap<>();
		ReferencedCallback.PVoidReferencedCallback doPool = (args) -> {
			Class clazz = (Class) args[0]; int repetitions = (int) args[1];
			int session = (int) args[2]; int total = (int) args[3]; long start = System.nanoTime();
			for(int i = 0; i < repetitions; i++) Pool.returnObject(clazz, Pool.tryBorrow(Pool.getPool(clazz)));
			long delta = System.nanoTime() - start; benchmark.computeIfAbsent(clazz, k -> new long[total])[session] = delta; dump.putLong(delta);
			logger.info(clazz.getSimpleName() + ", " + repetitions + " repetitions -> Took: " + ((float) delta / 1000000) + "ms, every object took: " + ((float) delta / repetitions) + "ns");
		};

		int sessions = 7;
		int repetitions = 50000;
		Set<Class> pools = Pool.getPools().keySet();
		dump.putInt(sessions);
		dump.putInt(repetitions);
		dump.putInt(pools.size());
		for(int j = 0; j < sessions; j++) {
			logger.debug("------- Starting New Session -------");
			for(Class clazz : pools) doPool.get(clazz, repetitions, j, sessions);
			logger.debug("------------------------------------\n");
		}
		logger.info("Done!");
		logger.info("Average: (" + sessions + " sessions)");
		for(Class clazz : pools) {
			long[] deltas = benchmark.get(clazz); long total = NumberUtils.add(deltas);
			dump.putLong(total / deltas.length);
			logger.info(clazz.getSimpleName() + ": " + ((float) total / 1000000 / deltas.length) + "ms");
		}
		logger.info("");
		logger.info("Time scale: ");
		float[] ratios = new float[sessions - 1];
		for(Class clazz : pools) {
			long[] deltas = benchmark.get(clazz);
			for(int i = 1; i < deltas.length; i++)
				ratios[i - 1] = (float) deltas[i] / deltas[i - 1];
			dump.putFloat(NumberUtils.add(ratios));
			logger.info(clazz.getSimpleName() + ": " + (NumberUtils.add(ratios) / ratios.length) + "x");
		}
		return true;
	}
}

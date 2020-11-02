package io.github.NadhifRadityo.Objects.TestPrograms;

import io.github.NadhifRadityo.Objects.Console.Logger;
import io.github.NadhifRadityo.Objects.List.PriorityList;
import io.github.NadhifRadityo.Objects.TestProgram;
import io.github.NadhifRadityo.Objects.Tester;
import io.github.NadhifRadityo.Objects.Utilizations.NumberUtils;

import java.nio.ByteBuffer;

@TestProgram(group = "List")
public class PriorityListTest extends Tester {
	public PriorityListTest() {
		super("PriorityListTest");
	}

	@Override protected boolean doRun(Logger logger, ByteBuffer dump) throws Throwable {
		PriorityList<Integer> list = new PriorityList<>();
		list.clear();
		for(int i = 0; i < 120; i++)
			list.add(i, i);

		boolean listSuccess = true;
		int last = -1;
		for(Integer i : list.get(false)) {
			if(last > i) {
				listSuccess = false;
				break;
			}
			last = i;
		}

		if(listSuccess) {
			int sessions = 15;
			dump.putInt(sessions);
			for(int j = 0; j < sessions; j++) {
				long[] deltas = new long[(int) Math.pow(2, j)];
				dump.putInt(deltas.length);
				for(int i = 0; i < deltas.length; i++) {
					long start = System.nanoTime();
					list.get();
					deltas[i] = System.nanoTime() - start;
					dump.putLong(deltas[i]);
				}
				logger.info(deltas.length + " repetitions -> Took: " + (NumberUtils.add(deltas) / (float) 1000000) + "ms, every operation took: " + (NumberUtils.add(deltas) / (float) deltas.length) + "ns");
			}
		}

		return listSuccess;
	}
}

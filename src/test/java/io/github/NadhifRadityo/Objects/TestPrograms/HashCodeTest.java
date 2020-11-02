package io.github.NadhifRadityo.Objects.TestPrograms;

import io.github.NadhifRadityo.Objects.Console.Logger;
import io.github.NadhifRadityo.Objects.TestProgram;
import io.github.NadhifRadityo.Objects.Tester;

import java.nio.ByteBuffer;

@TestProgram(group = "Performance")
public class HashCodeTest extends Tester {
	public HashCodeTest() {
		super("HashCodeTest");
	}

	@Override protected boolean doRun(Logger logger, ByteBuffer dump) throws Throwable {
		Object[] targets = new Object[9999999];
		for(int i = 0; i < targets.length; i++)
			targets[i] = new Object();

		long start = System.nanoTime();
		for(int i = 0; i < targets.length; i++) System.identityHashCode(targets[i]);
		System.out.println("Done! Took: " + ((System.nanoTime() - start) / 1000000f) + "ms");

		start = System.nanoTime();
		for(int i = 0; i < targets.length; i++) System.identityHashCode(targets[0]);
		System.out.println("Done! Took: " + ((System.nanoTime() - start) / 1000000f) + "ms");

		return true;
	}
}

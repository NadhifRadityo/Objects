package io.github.NadhifRadityo.Objects;

import io.github.NadhifRadityo.Objects.Console.Logger;
import io.github.NadhifRadityo.Objects.List.PriorityList;
import io.github.NadhifRadityo.Objects.Utilizations.BitwiseUtils;
import io.github.NadhifRadityo.Objects.Utilizations.BufferUtils;

import java.nio.ByteBuffer;
import java.util.Map;

public class TesterGroup extends Tester {
	protected final PriorityList<Tester> testers;
	protected boolean exitWhenError;

	public TesterGroup(String name, boolean exitWhenError) {
		super(name);
		this.testers = new PriorityList<>();
		this.exitWhenError = exitWhenError;
	}
	public TesterGroup(String name) {
		this(name, false);
	}

	public void addTester(Tester tester, int priority) { testers.add(tester, priority); }
	public void addTester(Tester tester) { addTester(tester, 0); }
	public void removeTester(Tester tester) { testers.remove(tester); }
	public Map.Entry<Tester, Integer>[] getTesters() { return testers.getMap(); }
	public boolean isExitWhenError() { return exitWhenError; }
	public void setExitWhenError(boolean exitWhenError) { this.exitWhenError = exitWhenError; }

	@Override protected boolean doRun(Logger logger, ByteBuffer dump) throws Throwable {
		int position = testers.size() * Integer.BYTES; int i = 0; int successOffset = position;
		position += testers.size() / Byte.SIZE + (testers.size() % Byte.SIZE > 0 ? 1 : 0);
		boolean[] successes = new boolean[testers.size()]; byte successesByte = 0;
		for(Tester tester : testers) { dump.putInt(i, position);
			ByteBuffer testerDump = BufferUtils.slice(dump, position, dump.capacity() - position);
			boolean success = tester.run(logger, testerDump);
			if(!success && exitWhenError) break; position += testerDump.position();
			successes[i] = success; i++; successesByte = (byte) ((successesByte << 1) + (success ? 1 : 0));
			if(i % Byte.SIZE == 0) { dump.put(successOffset++, successesByte); successesByte = 0; }
		} dump.position(position); return !BitwiseUtils.or(BitwiseUtils.compliment(successes));
	}
}

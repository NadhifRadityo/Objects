package io.github.NadhifRadityo.Objects.TestPrograms;

import io.github.NadhifRadityo.Objects.Console.Logger;
import io.github.NadhifRadityo.Objects.Tester;

import java.nio.ByteBuffer;

public class InstanceOfTest extends Tester {
	public InstanceOfTest() {
		super("InstanceOfTest");
	}

	@Override protected boolean doRun(Logger logger, ByteBuffer dump) throws Throwable {
		int repeat = 20;
		int loops = 1000000;
		for(int r = 0; r < repeat; r++) {
			logger.debug("------- Starting New Session -------");
			Object a = r % 2 == 0 ? new B() : new C();
			Object b = r % 2 == 0 ? new C() : new B();
			logger._debug("Loops: %s", loops);
			long t1 = System.nanoTime();
			for(int i = 0; i < loops; i++) __(b instanceof A);
			long t2 = System.nanoTime();
			for(int i = 0; i < loops; i++) __(A.class.isInstance(b));
			long t3 = System.nanoTime();
			for(int i = 0; i < loops; i++) __(A.class.isAssignableFrom(b.getClass()));
			long t4 = System.nanoTime();
			for(int i = 0; i < loops; i++) __(a instanceof A);
			long t5 = System.nanoTime();
			for(int i = 0; i < loops; i++) __(A.class.isInstance(a));
			long t6 = System.nanoTime();
			for(int i = 0; i < loops; i++) __(A.class.isAssignableFrom(a.getClass()));
			long t7 = System.nanoTime();
			logger._info("A = instanceof: %sms (%sns/op), isInstance: %sms (%sns/op), isAssignableFrom: %sms (%sns/op)",
					(float) (t2 - t1) / 1000000, (float) (t2 - t1) / loops,
					(float) (t3 - t2) / 1000000, (float) (t3 - t2) / loops,
					(float) (t4 - t3) / 1000000, (float) (t4 - t3) / loops);
			logger._info("B = instanceof: %sms (%sns/op), isInstance: %sms (%sns/op), isAssignableFrom: %sms (%sns/op)",
					(float) (t5 - t4) / 1000000, (float) (t5 - t4) / loops,
					(float) (t6 - t5) / 1000000, (float) (t6 - t5) / loops,
					(float) (t7 - t6) / 1000000, (float) (t7 - t6) / loops);
			logger.debug("------------------------------------\n");
		}
		return true;
	}

	public boolean __;
	void __(boolean __) { this.__ = __; }

	static class A { }
	static class B extends A { }
	static class C { }
}

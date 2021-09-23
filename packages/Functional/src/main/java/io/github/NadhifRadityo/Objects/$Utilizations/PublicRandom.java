package io.github.NadhifRadityo.Objects.$Utilizations;

import java.security.SecureRandom;
import java.util.Random;

public class PublicRandom {
	private static final Random random = new SecureRandom();
	public static Random getRandom() { return random; }
}

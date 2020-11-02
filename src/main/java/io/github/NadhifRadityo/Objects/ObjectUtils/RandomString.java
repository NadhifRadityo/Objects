package io.github.NadhifRadityo.Objects.ObjectUtils;

import io.github.NadhifRadityo.Objects.Utilizations.PublicRandom;

import java.util.Locale;
import java.util.Objects;
import java.util.Random;

public class RandomString {

    /**
     * Generate a random string.
     */
    public String nextString() {
        for (int i = 0; i < buf.length; i++)
            buf[i] = symbols[random.nextInt(symbols.length)];
        return new String(buf);
    } public String toString() { return nextString(); }

    public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String lower = upper.toLowerCase(Locale.ROOT);
    public static final String digits = "0123456789";
    public static final String alphanum = upper + lower + digits;

    protected final Random random;
    protected final char[] symbols;
    protected final char[] buf;

    public RandomString(int length, Random random, String symbols) {
        if (length < 1) throw new IllegalArgumentException();
        if (symbols.length() < 2) throw new IllegalArgumentException();
        this.random = Objects.requireNonNull(random);
        this.symbols = symbols.toCharArray();
        this.buf = new char[length];
    }

    /**
     * Create an alphanumeric string generator.
     */
    public RandomString(int length, Random random) { this(length, random, alphanum); }

    /**
     * Create an alphanumeric strings from a secure generator.
     */
    public RandomString(int length) { this(length, PublicRandom.getRandom()); }

    /**
     * Create session identifiers.
     */
    public RandomString() { this(21); }

}

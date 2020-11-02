package io.github.NadhifRadityo.Objects.GameEngine.Standard.OpenCL;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Version implements Comparable<Version> {
	protected final static Pattern VERSION_PATTERN = Pattern.compile("OpenCL (?:C )?(\\d+)\\.(\\d+)(.*)");
	public final static Version CL_1_0 = new Version("OpenCL 1.0");
	public final static Version CL_1_1 = new Version("OpenCL 1.1");

	protected final String fullVersion;
	protected final String implVersion;
	protected final short major;
	protected final short minor;

	public Version(String version) {
		Matcher matcher = VERSION_PATTERN.matcher(version);
		if(!matcher.matches()) throw new IllegalArgumentException("Unidentified version");
		this.fullVersion = version;
		this.implVersion = matcher.groupCount() == 4 ? matcher.group(3).substring(1) : "";
		this.major = Short.parseShort(matcher.group(1));
		this.minor = Short.parseShort(matcher.group(2));
	}

	public int compareTo(Version other) { return compareTo(other.major, other.minor); }
	public int compareTo(int major, int minor)  { return this.major == major && this.minor == minor ? 0 : this.major > major || (this.major == major && this.minor > minor) ? 1 : -1; }

	public boolean isAtLeast(Version other) { return isAtLeast(other.major, other.minor); }
	public boolean isAtLeast(int major, int minor) { return compareTo(major, minor) >= 0; }
	public boolean isEqual(Version other) { return isEqual(other.major, other.minor); }
	public boolean isEqual(int major, int minor) { return compareTo(major, minor) == 0; }

	public String getSpecVersion() { return "OpenCL " + major + '.' + minor; }
	public String getFullVersion() { return fullVersion; }
	public String getImplVersion() { return implVersion; }
	public short getMajor() { return major; }
	public short getMinor() { return minor; }

	@Override public String toString() { return getFullVersion(); }
	@Override public int hashCode() { return fullVersion.hashCode(); }
	@Override public boolean equals(Object obj) { return obj != null && obj.getClass() == getClass() && fullVersion.equals(((Version) obj).fullVersion); }
}

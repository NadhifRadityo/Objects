package io.github.NadhifRadityo.Objects.Utilizations;

public class FNumberUtils extends NumberUtils {

	static {
		Java9Utils.disableJava9SillyWarning();
	}

	public static FNumberUtilsImplementation FImplgetInstance() {
		return new FNumberUtilsImplementation() {
			@Override public FMA_CALLBACK getFMAInstance() throws Exception {
				return Math::fma;
			}
			@Override public FastMathImplementation getFastMathImplementation() throws Exception {
				throw new UnsupportedOperationException("Not yet implemented");
			}
		};
	}
}

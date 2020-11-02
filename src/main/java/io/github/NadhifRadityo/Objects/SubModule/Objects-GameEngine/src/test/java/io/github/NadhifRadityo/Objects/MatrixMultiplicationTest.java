package io.github.NadhifRadityo.Objects;

import io.github.NadhifRadityo.Objects.Console.Logger;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Mat4;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.TempVec;
import io.github.NadhifRadityo.Objects.GameEngine.DataStructure.Vec4;
import io.github.NadhifRadityo.Objects.Object.ReferencedCallback;
import io.github.NadhifRadityo.Objects.Utilizations.BufferUtils;
import io.github.NadhifRadityo.Objects.Utilizations.OpenGLUtils;

import java.nio.ByteBuffer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatrixMultiplicationTest extends Tester {
	public MatrixMultiplicationTest() {
		super("MatrixMultiplicationTest");
	}

	@Override protected boolean doRun(Logger logger, ByteBuffer dump) throws Throwable {
		TempVec tempVec = new TempVec(16, null);
		Mat4 mat1 = new Mat4(0);
		OpenGLUtils.Matrix3D.loadIdentity(mat1);
		Vec4 mat2 = new Vec4(0);
		Vec4 result = OpenGLUtils.multiplyByMatrix(mat1, mat2);
		logger.log(result);
		String aa = "0 * 0 + 1 * 1 + 2 * 2 + 3 * 3 +  = 0\n" +
				"4 * 0 + 5 * 1 + 6 * 2 + 7 * 3 +  = 1\n" +
				"8 * 0 + 9 * 1 + 10 * 2 + 11 * 3 +  = 2\n" +
				"12 * 0 + 13 * 1 + 14 * 2 + 15 * 3 +  = 3";
		int[] ints = new int[9 * 4]; int i = 0;
		Pattern pattern = Pattern.compile("([0-9]+)");
		Matcher matcher = pattern.matcher(aa);
		while(matcher.find())
			ints[i++] = Integer.parseInt(matcher.group(1));
		ReferencedCallback.PVoidReferencedCallback mPrint = (args) -> {
			String mat = (String) args[0];
			StringBuilder matr = (StringBuilder) args[1];
			int j = (int) args[2];
			boolean temp = (boolean) args[3];
			matr.append(mat).append(temp ? ".getDoubleAt(" : "[").append(j).append(temp ? ")" : "]");
		};
		// double[] TempVec
		boolean m1Temp = true;
		boolean m2Temp = true;
		int m2Off = 16;
		int rOff = 20;
		for(i = 0; i < ints.length; i += 9) {
			StringBuilder builder = new StringBuilder();
			builder.append("temp.setVectorAt(").append(rOff + ints[i + 8]).append(", ");
//			builder.append("temp[").append(ints[i + 8]).append("] = ");
			mPrint.get("m1", builder, ints[i    ], m1Temp);
			builder.append(" * ");
			mPrint.get("m2", builder, ints[i + 1] + m2Off, m2Temp);
			builder.append(" + ");
			mPrint.get("m1", builder, ints[i + 2], m1Temp);
			builder.append(" * ");
			mPrint.get("m2", builder, ints[i + 3] + m2Off, m2Temp);
			builder.append(" + ");
			mPrint.get("m1", builder, ints[i + 4], m1Temp);
			builder.append(" * ");
			mPrint.get("m2", builder, ints[i + 5] + m2Off, m2Temp);
			builder.append(" + ");
			mPrint.get("m1", builder, ints[i + 6], m1Temp);
			builder.append(" * ");
			mPrint.get("m2", builder, ints[i + 7] + m2Off, m2Temp);
			builder.append(")");
			builder.append(";");
			System.out.println(builder);
		}
		return true;
	}

	public static void main(String... args) throws Throwable {
		Logger logger = MainTest.LoggerTestWrapper.start(args);
		Tester tester = new MatrixMultiplicationTest();
		tester.run(logger, BufferUtils.createByteBuffer(1024));
	}
}

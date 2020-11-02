package io.github.NadhifRadityo.Objects;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class CanvasTest {
	public static void main(String... args) throws Exception {
		EverythingIsTrue.main();
	}

	// https://stackoverflow.com/questions/3301635/change-private-static-final-field-using-java-reflection
	public static class EverythingIsTrue {
		static void setFinalStatic(Field field, Object newValue) throws Exception { setFinal(null, field, newValue); }
		static void setFinal(Object obj, Field field, Object newValue) throws Exception {
			field.setAccessible(true);
			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
			field.set(obj, newValue);
		}
		public static void main() throws Exception {
			setFinalStatic(Boolean.class.getField("FALSE"), true);
			System.out.format("Everything is %s", false); // "Everything is true"
			System.out.println();

			Double pi = new Double("" + Math.PI);
			System.out.println("PI " + pi);
			setFinal(pi, Double.class.getDeclaredField("value"), 0.5);
			System.out.println("PI " + pi);
		}
	}
}

package io.github.NadhifRadityo.Objects;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface TestProgram {
	boolean enabled() default true;
	String group() default "main";
	int priority() default 0;
}

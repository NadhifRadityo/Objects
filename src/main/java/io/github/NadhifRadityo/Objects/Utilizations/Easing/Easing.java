package io.github.NadhifRadityo.Objects.Utilizations.Easing;

public interface Easing {

	/**
	 * The basic function for easing.
	 * @param t the time (either frames or in seconds/milliseconds)
	 * @param b the beginning value
	 * @param c the value changed
	 * @param d the duration time
	 * @return the eased value
	 */
	double ease(double t, double b, double c, double d);

	/* LINEAR */
	Easing LINEAR = Linear.EASE_NONE;

	/* QUAD */
	Easing QUAD_IN = Quad.EASE_IN;
	Easing QUAD_OUT = Quad.EASE_OUT;
	Easing QUAD_IN_OUT = Quad.EASE_IN_OUT;

	/* QUART */
	Easing QUART_IN = Quart.EASE_IN;
	Easing QUART_OUT = Quart.EASE_OUT;
	Easing QUART_IN_OUT = Quart.EASE_IN_OUT;

	/* QUINT */
	Easing QUINT_IN = Quint.EASE_IN;
	Easing QUINT_OUT = Quint.EASE_OUT;
	Easing QUINT_IN_OUT = Quint.EASE_IN_OUT;

	/* CUBIC */
	Easing CUBIC_IN = Cubic.EASE_IN;
	Easing CUBIC_OUT = Cubic.EASE_OUT;
	Easing CUBIC_IN_OUT = Cubic.EASE_IN_OUT;

	/* SINE */
	Easing SINE_IN = Sine.EASE_IN;
	Easing SINE_OUT = Sine.EASE_OUT;
	Easing SINE_IN_OUT = Sine.EASE_IN_OUT;

	/* EXPO */
	Easing EXPO_IN = Expo.EASE_IN;
	Easing EXPO_OUT = Expo.EASE_OUT;
	Easing EXPO_IN_OUT = Expo.EASE_IN_OUT;

	/* CIRC */
	Easing CIRC_IN = Circ.EASE_IN;
	Easing CIRC_OUT = Circ.EASE_OUT;
	Easing CIRC_IN_OUT = Circ.EASE_IN_OUT;

	/* ELASTIC */
	Easing ELASTIC_IN = Elastic.EASE_IN;
	Easing ELASTIC_OUT = Elastic.EASE_OUT;
	Easing ELASTIC_IN_OUT = Elastic.EASE_IN_OUT;
	static Easing ELASTIC_IN(double amplitude, double period) { return new Elastic.EASE_IN(amplitude, period); }
	static Easing ELASTIC_OUT(double amplitude, double period) { return new Elastic.EASE_OUT(amplitude, period); }
	static Easing ELASTIC_IN_OUT(double amplitude, double period) { return new Elastic.EASE_IN_OUT(amplitude, period); }

	/* BACK */
	Easing BACK_IN = Back.EASE_IN;
	Easing BACK_OUT = Back.EASE_OUT;
	Easing BACK_IN_OUT = Back.EASE_IN_OUT;
	static Easing BACK_IN(double overshoot) { return new Back.EASE_IN(overshoot); }
	static Easing BACK_OUT(double overshoot) { return new Back.EASE_OUT(overshoot); }
	static Easing BACK_IN_OUT(double overshoot) { return new Back.EASE_IN_OUT(overshoot); }

	/* BOUNCE */
	Easing BOUNCE_IN = Bounce.EASE_IN;
	Easing BOUNCE_OUT = Bounce.EASE_OUT;
	Easing BOUNCE_IN_OUT = Bounce.EASE_IN_OUT;
}

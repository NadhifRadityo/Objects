package io.github.NadhifRadityo.Objects.Canvas.RenderHints.Easing;

public interface Easing {
    
    /**
     * The basic function for easing.
     * @param t the time (either frames or in seconds/milliseconds)
     * @param b the beginning value
     * @param c the value changed
     * @param d the duration time
     * @return the eased value
     */
    public float ease(float t, float b, float c, float d);
    
    /* LINEAR */
    public static Easing LINEAR = Linear.EASE_NONE;
    
    /* QUAD */
    public static Easing QUAD_IN = Quad.EASE_IN;
    public static Easing QUAD_OUT = Quad.EASE_OUT;
    public static Easing QUAD_IN_OUT = Quad.EASE_IN_OUT;
    
    /* QUART */
    public static Easing QUART_IN = Quart.EASE_IN;
    public static Easing QUART_OUT = Quart.EASE_OUT;
    public static Easing QUART_IN_OUT = Quart.EASE_IN_OUT;
    
    /* QUINT */
    public static Easing QUINT_IN = Quint.EASE_IN;
    public static Easing QUINT_OUT = Quint.EASE_OUT;
    public static Easing QUINT_IN_OUT = Quint.EASE_IN_OUT;
    
    /* CUBIC */
    public static Easing CUBIC_IN = Cubic.EASE_IN;
    public static Easing CUBIC_OUT = Cubic.EASE_OUT;
    public static Easing CUBIC_IN_OUT = Cubic.EASE_IN_OUT;
    
    /* SINE */
    public static Easing SINE_IN = Sine.EASE_IN;
    public static Easing SINE_OUT = Sine.EASE_OUT;
    public static Easing SINE_IN_OUT = Sine.EASE_IN_OUT;
    
    /* EXPO */
    public static Easing EXPO_IN = Expo.EASE_IN;
    public static Easing EXPO_OUT = Expo.EASE_OUT;
    public static Easing EXPO_IN_OUT = Expo.EASE_IN_OUT;
    
    /* CIRC */
    public static Easing CIRC_IN = Circ.EASE_IN;
    public static Easing CIRC_OUT = Circ.EASE_OUT;
    public static Easing CIRC_IN_OUT = Circ.EASE_IN_OUT;
    
    /* ELASTIC */
    public static Easing ELASTIC_IN = Elastic.def_EASE_IN;
    public static Easing ELASTIC_OUT = Elastic.def_EASE_OUT;
    public static Easing ELASTIC_IN_OUT = Elastic.def_EASE_IN_OUT;
    public static Easing ELASTIC_IN(float amplitude, float period) { return new Elastic.EASE_IN(amplitude, period); }
    public static Easing ELASTIC_OUT(float amplitude, float period) { return new Elastic.EASE_OUT(amplitude, period); }
    public static Easing ELASTIC_IN_OUT(float amplitude, float period) { return new Elastic.EASE_IN_OUT(amplitude, period); }
    
    /* BACK */
    public static Easing BACK_IN = Back.def_EASE_IN;
    public static Easing BACK_OUT = Back.def_EASE_OUT;
    public static Easing BACK_IN_OUT = Back.def_EASE_IN_OUT;
    public static Easing BACK_IN(float overshoot) { return new Back.EASE_IN(overshoot); }
    public static Easing BACK_OUT(float overshoot) { return new Back.EASE_OUT(overshoot); }
    public static Easing BACK_IN_OUT(float overshoot) { return new Back.EASE_IN_OUT(overshoot); }
    
    /* BOUNCE */
    public static Easing BOUNCE_IN = Bounce.EASE_IN;
    public static Easing BOUNCE_OUT = Bounce.EASE_OUT;
    public static Easing BOUNCE_IN_OUT = Bounce.EASE_IN_OUT;
}

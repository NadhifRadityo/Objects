package io.github.NadhifRadityo.Objects.Units.BuiltInUnit;

import io.github.NadhifRadityo.Objects.Units.Unit;

public class ScreenUnit extends Unit {
	public static final UnitTypesHolder UNIT_FIXED;
	public static final UnitTypesHolder UNIT_ABSOLUTE;

	static {
		UNIT_FIXED = addSupportedUnit(ScreenUnit.class, new UnitTypesHolder());
		UNIT_ABSOLUTE = addSupportedUnit(ScreenUnit.class, new UnitTypesHolder());

		UnitType pixelUnit = UNIT_FIXED.addUnitType(new UnitType("Pixel", "px"));
		UnitType pointUnit = UNIT_FIXED.addUnitType(new UnitType("Point", "pt"));
		UnitType picaUnit = UNIT_FIXED.addUnitType(new UnitType("Pica", "pc"));

		pixelUnit.addResolver(pixelUnit, args -> args[0]);
		pixelUnit.addResolver(pointUnit, args -> ((Number) args[0]).doubleValue() * 0.75);
		pixelUnit.addResolver(picaUnit, args -> ((Number) args[0]).doubleValue() * 0.0625);

		pointUnit.addResolver(pixelUnit, args -> ((Number) args[0]).doubleValue() / 0.75);
		pointUnit.addResolver(pointUnit, args -> args[0]);
		pointUnit.addResolver(picaUnit, args -> ((Number) args[0]).doubleValue() / 0.75 * 0.0625);

		picaUnit.addResolver(pixelUnit, args -> ((Number) args[0]).doubleValue() / 0.0625);
		picaUnit.addResolver(pointUnit, args -> ((Number) args[0]).doubleValue() / 0.0625 * 0.75);
		picaUnit.addResolver(picaUnit, args -> args[0]);

		UNIT_ABSOLUTE.addUnitType(new UnitType("Percent", "%"));
		UNIT_ABSOLUTE.addUnitType(new UnitType("ViewportWidth", "vw"));
		UNIT_ABSOLUTE.addUnitType(new UnitType("ViewportHeight", "vh"));
	}

	public ScreenUnit(UnitType unitType, Number value) { super(unitType, value); }
}

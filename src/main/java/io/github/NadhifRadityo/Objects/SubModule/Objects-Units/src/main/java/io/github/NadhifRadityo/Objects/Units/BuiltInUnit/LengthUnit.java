package io.github.NadhifRadityo.Objects.Units.BuiltInUnit;

import io.github.NadhifRadityo.Objects.Exception.ThrowsRunnable;
import io.github.NadhifRadityo.Objects.Units.Unit;
import io.github.NadhifRadityo.Objects.Utilizations.ExceptionUtils;
import io.github.NadhifRadityo.Objects.Utilizations.Unit.StairUnit;

public class LengthUnit extends Unit {
	public static final UnitTypesHolder UNIT_SI;
	public static final UnitTypesHolder UNIT_METRIC;

	static {
		UNIT_SI = addSupportedUnit(LengthUnit.class, new UnitTypesHolder());
		UNIT_METRIC = UNIT_SI;
		StairUnit SIStairUnit = StairUnit.newInstance(10);

		UnitType picoMeterUnit = UNIT_SI.addUnitType(new UnitType("Picometer", "pm")); // 10^(-12)
		UnitType nanoMeterUnit = UNIT_SI.addUnitType(new UnitType("Nanometer", "nm")); // 10^(-9)
		UnitType microMeterUnit = UNIT_SI.addUnitType(new UnitType("Micrometer", "μm")); // 10^(-6)
		UnitType milliMeterUnit = UNIT_SI.addUnitType(new UnitType("Millimeter", "mm")); // 10^(-3)
		UnitType centiMeterUnit = UNIT_SI.addUnitType(new UnitType("Centimeter", "cm")); // 10^(-2)
		UnitType deciMeterUnit = UNIT_SI.addUnitType(new UnitType("Decimeter", "dm")); // 10^(-1)
		UnitType meterUnit = UNIT_SI.addUnitType(new UnitType("Meter", "m")); // 10^(0)
		UnitType decaMeterUnit = UNIT_SI.addUnitType(new UnitType("Decameter", "dam")); // 10^(1)
		UnitType hectoMeterUnit = UNIT_SI.addUnitType(new UnitType("Hectometer", "hm")); // 10^(2)
		UnitType kiloMeterUnit = UNIT_SI.addUnitType(new UnitType("Kilometer", "km")); // 10^(3)
		UnitType megaMeterUnit = UNIT_SI.addUnitType(new UnitType("Megameter", "Mm")); // 10^(6)
		UnitType gigaMeterUnit = UNIT_SI.addUnitType(new UnitType("Gigameter", "Gm")); // 10^(9)
		UnitType teraMeterUnit = UNIT_SI.addUnitType(new UnitType("Terameter", "Tm")); // 10^(12)
		SIStairUnit.addUnitType(picoMeterUnit, -12);
		SIStairUnit.addUnitType(nanoMeterUnit, -9);
		SIStairUnit.addUnitType(microMeterUnit, -6);
		SIStairUnit.addUnitType(milliMeterUnit, -3);
		SIStairUnit.addUnitType(centiMeterUnit, -2);
		SIStairUnit.addUnitType(deciMeterUnit, -1);
		SIStairUnit.addUnitType(meterUnit, 0);
		SIStairUnit.addUnitType(decaMeterUnit, 1);
		SIStairUnit.addUnitType(hectoMeterUnit, 2);
		SIStairUnit.addUnitType(kiloMeterUnit, 3);
		SIStairUnit.addUnitType(megaMeterUnit, 6);
		SIStairUnit.addUnitType(gigaMeterUnit, 9);
		SIStairUnit.addUnitType(teraMeterUnit, 12);
		SIStairUnit.addResolvers();
	}

	public LengthUnit(UnitType unitType, Number value) { super(unitType, value); }
}

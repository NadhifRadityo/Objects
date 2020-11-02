package io.github.NadhifRadityo.Objects.Units.BuiltInUnit;

import io.github.NadhifRadityo.Objects.Units.Unit;
import io.github.NadhifRadityo.Objects.Utilizations.Unit.StairUnit;

public class MassUnit extends Unit {
	public static final UnitTypesHolder UNIT_SI;

	static {
		UNIT_SI = addSupportedUnit(MassUnit.class, new UnitTypesHolder());
		StairUnit SIStairUnit = StairUnit.newInstance(10);

		UnitType picoGramUnit = UNIT_SI.addUnitType(new UnitType("Picogram", "pg")); // 10^(-12)
		UnitType nanoGramUnit = UNIT_SI.addUnitType(new UnitType("Nanogram", "ng")); // 10^(-9)
		UnitType microGramUnit = UNIT_SI.addUnitType(new UnitType("Microgram", "μg")); // 10^(-6)
		UnitType milliGramUnit = UNIT_SI.addUnitType(new UnitType("Milligram", "mg")); // 10^(-3)
		UnitType centiGramUnit = UNIT_SI.addUnitType(new UnitType("Centigram", "cg")); // 10^(-2)
		UnitType deciGramUnit = UNIT_SI.addUnitType(new UnitType("Decigram", "dg")); // 10^(-1)
		UnitType gramUnit = UNIT_SI.addUnitType(new UnitType("Gram", "g")); // 10^(0)
		UnitType decaGramUnit = UNIT_SI.addUnitType(new UnitType("Decagram", "dag")); // 10^(1)
		UnitType hectoGramUnit = UNIT_SI.addUnitType(new UnitType("Hectogram", "hg")); // 10^(2)
		UnitType kiloGramUnit = UNIT_SI.addUnitType(new UnitType("Kilogram", "kg")); // 10^(3)
		UnitType megaGramUnit = UNIT_SI.addUnitType(new UnitType("Megagram", "Mg")); // 10^(6)
		UnitType gigaGramUnit = UNIT_SI.addUnitType(new UnitType("Gigagram", "Gg")); // 10^(9)
		UnitType teraGramUnit = UNIT_SI.addUnitType(new UnitType("Teragram", "Tg")); // 10^(12)
		SIStairUnit.addUnitType(picoGramUnit, -12);
		SIStairUnit.addUnitType(nanoGramUnit, -9);
		SIStairUnit.addUnitType(microGramUnit, -6);
		SIStairUnit.addUnitType(milliGramUnit, -3);
		SIStairUnit.addUnitType(centiGramUnit, -2);
		SIStairUnit.addUnitType(deciGramUnit, -1);
		SIStairUnit.addUnitType(gramUnit, 0);
		SIStairUnit.addUnitType(decaGramUnit, 1);
		SIStairUnit.addUnitType(hectoGramUnit, 2);
		SIStairUnit.addUnitType(kiloGramUnit, 3);
		SIStairUnit.addUnitType(megaGramUnit, 6);
		SIStairUnit.addUnitType(gigaGramUnit, 9);
		SIStairUnit.addUnitType(teraGramUnit, 12);
		SIStairUnit.addResolvers();
	}

	public MassUnit(UnitType unitType, Number value) { super(unitType, value); }
}

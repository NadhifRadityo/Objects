package Gradle.GroovyKotlinInteroperability

import Gradle.DynamicScripting.Imported
import groovy.lang.Closure
import kotlin.jvm.functions.FunctionN

fun construct() {
	GroovyManipulation.construct()
	GroovyInteroperability.construct()
	KotlinClosure.construct()
}
fun destruct() {
	KotlinClosure.destruct()
	GroovyInteroperability.destruct()
	GroovyManipulation.destruct()
}

// console.log(new Array(23).fill(null).map((v, i) => `typealias fn${i} = (${new Array(i).fill(null).map((_v, _i) => `Any?`).join(", ")}) -> Any?`).join("\n"))
typealias fn0 = () -> Any?
typealias fn1 = (Any?) -> Any?
typealias fn2 = (Any?, Any?) -> Any?
typealias fn3 = (Any?, Any?, Any?) -> Any?
typealias fn4 = (Any?, Any?, Any?, Any?) -> Any?
typealias fn5 = (Any?, Any?, Any?, Any?, Any?) -> Any?
typealias fn6 = (Any?, Any?, Any?, Any?, Any?, Any?) -> Any?
typealias fn7 = (Any?, Any?, Any?, Any?, Any?, Any?, Any?) -> Any?
typealias fn8 = (Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?) -> Any?
typealias fn9 = (Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?) -> Any?
typealias fn10 = (Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?) -> Any?
typealias fn11 = (Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?) -> Any?
typealias fn12 = (Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?) -> Any?
typealias fn13 = (Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?) -> Any?
typealias fn14 = (Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?) -> Any?
typealias fn15 = (Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?) -> Any?
typealias fn16 = (Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?) -> Any?
typealias fn17 = (Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?) -> Any?
typealias fn18 = (Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?) -> Any?
typealias fn19 = (Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?) -> Any?
typealias fn20 = (Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?) -> Any?
typealias fn21 = (Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?) -> Any?
typealias fn22 = (Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?, Any?) -> Any?
typealias fnn = FunctionN<Any?>

// console.log(new Array(23).fill(null).map((v, i) => `@JvmInline value class expandImport${i}<${new Array(i).fill(null).map((v1, i1) => `R${(i1 + 1)}`).join(", ")}>(val imported: Imported) {\n${new Array(i).fill(null).map((v1, i1) => `\toperator fun component${(i1 + 1)}(): Closure<R${(i1 + 1)}> { return imported["component${i1 + 1}"] as Closure<R${(i1 + 1)}> }`).join("\n")}\n}`).join("\n"))
@JvmInline value class expandImport0(val imported: Imported) {

}
@JvmInline value class expandImport1<R1>(val imported: Imported) {
	operator fun component1(): Closure<R1> { return imported["component1"] as Closure<R1> }
}
@JvmInline value class expandImport2<R1, R2>(val imported: Imported) {
	operator fun component1(): Closure<R1> { return imported["component1"] as Closure<R1> }
	operator fun component2(): Closure<R2> { return imported["component2"] as Closure<R2> }
}
@JvmInline value class expandImport3<R1, R2, R3>(val imported: Imported) {
	operator fun component1(): Closure<R1> { return imported["component1"] as Closure<R1> }
	operator fun component2(): Closure<R2> { return imported["component2"] as Closure<R2> }
	operator fun component3(): Closure<R3> { return imported["component3"] as Closure<R3> }
}
@JvmInline value class expandImport4<R1, R2, R3, R4>(val imported: Imported) {
	operator fun component1(): Closure<R1> { return imported["component1"] as Closure<R1> }
	operator fun component2(): Closure<R2> { return imported["component2"] as Closure<R2> }
	operator fun component3(): Closure<R3> { return imported["component3"] as Closure<R3> }
	operator fun component4(): Closure<R4> { return imported["component4"] as Closure<R4> }
}
@JvmInline value class expandImport5<R1, R2, R3, R4, R5>(val imported: Imported) {
	operator fun component1(): Closure<R1> { return imported["component1"] as Closure<R1> }
	operator fun component2(): Closure<R2> { return imported["component2"] as Closure<R2> }
	operator fun component3(): Closure<R3> { return imported["component3"] as Closure<R3> }
	operator fun component4(): Closure<R4> { return imported["component4"] as Closure<R4> }
	operator fun component5(): Closure<R5> { return imported["component5"] as Closure<R5> }
}
@JvmInline value class expandImport6<R1, R2, R3, R4, R5, R6>(val imported: Imported) {
	operator fun component1(): Closure<R1> { return imported["component1"] as Closure<R1> }
	operator fun component2(): Closure<R2> { return imported["component2"] as Closure<R2> }
	operator fun component3(): Closure<R3> { return imported["component3"] as Closure<R3> }
	operator fun component4(): Closure<R4> { return imported["component4"] as Closure<R4> }
	operator fun component5(): Closure<R5> { return imported["component5"] as Closure<R5> }
	operator fun component6(): Closure<R6> { return imported["component6"] as Closure<R6> }
}
@JvmInline value class expandImport7<R1, R2, R3, R4, R5, R6, R7>(val imported: Imported) {
	operator fun component1(): Closure<R1> { return imported["component1"] as Closure<R1> }
	operator fun component2(): Closure<R2> { return imported["component2"] as Closure<R2> }
	operator fun component3(): Closure<R3> { return imported["component3"] as Closure<R3> }
	operator fun component4(): Closure<R4> { return imported["component4"] as Closure<R4> }
	operator fun component5(): Closure<R5> { return imported["component5"] as Closure<R5> }
	operator fun component6(): Closure<R6> { return imported["component6"] as Closure<R6> }
	operator fun component7(): Closure<R7> { return imported["component7"] as Closure<R7> }
}
@JvmInline value class expandImport8<R1, R2, R3, R4, R5, R6, R7, R8>(val imported: Imported) {
	operator fun component1(): Closure<R1> { return imported["component1"] as Closure<R1> }
	operator fun component2(): Closure<R2> { return imported["component2"] as Closure<R2> }
	operator fun component3(): Closure<R3> { return imported["component3"] as Closure<R3> }
	operator fun component4(): Closure<R4> { return imported["component4"] as Closure<R4> }
	operator fun component5(): Closure<R5> { return imported["component5"] as Closure<R5> }
	operator fun component6(): Closure<R6> { return imported["component6"] as Closure<R6> }
	operator fun component7(): Closure<R7> { return imported["component7"] as Closure<R7> }
	operator fun component8(): Closure<R8> { return imported["component8"] as Closure<R8> }
}
@JvmInline value class expandImport9<R1, R2, R3, R4, R5, R6, R7, R8, R9>(val imported: Imported) {
	operator fun component1(): Closure<R1> { return imported["component1"] as Closure<R1> }
	operator fun component2(): Closure<R2> { return imported["component2"] as Closure<R2> }
	operator fun component3(): Closure<R3> { return imported["component3"] as Closure<R3> }
	operator fun component4(): Closure<R4> { return imported["component4"] as Closure<R4> }
	operator fun component5(): Closure<R5> { return imported["component5"] as Closure<R5> }
	operator fun component6(): Closure<R6> { return imported["component6"] as Closure<R6> }
	operator fun component7(): Closure<R7> { return imported["component7"] as Closure<R7> }
	operator fun component8(): Closure<R8> { return imported["component8"] as Closure<R8> }
	operator fun component9(): Closure<R9> { return imported["component9"] as Closure<R9> }
}
@JvmInline value class expandImport10<R1, R2, R3, R4, R5, R6, R7, R8, R9, R10>(val imported: Imported) {
	operator fun component1(): Closure<R1> { return imported["component1"] as Closure<R1> }
	operator fun component2(): Closure<R2> { return imported["component2"] as Closure<R2> }
	operator fun component3(): Closure<R3> { return imported["component3"] as Closure<R3> }
	operator fun component4(): Closure<R4> { return imported["component4"] as Closure<R4> }
	operator fun component5(): Closure<R5> { return imported["component5"] as Closure<R5> }
	operator fun component6(): Closure<R6> { return imported["component6"] as Closure<R6> }
	operator fun component7(): Closure<R7> { return imported["component7"] as Closure<R7> }
	operator fun component8(): Closure<R8> { return imported["component8"] as Closure<R8> }
	operator fun component9(): Closure<R9> { return imported["component9"] as Closure<R9> }
	operator fun component10(): Closure<R10> { return imported["component10"] as Closure<R10> }
}
@JvmInline value class expandImport11<R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11>(val imported: Imported) {
	operator fun component1(): Closure<R1> { return imported["component1"] as Closure<R1> }
	operator fun component2(): Closure<R2> { return imported["component2"] as Closure<R2> }
	operator fun component3(): Closure<R3> { return imported["component3"] as Closure<R3> }
	operator fun component4(): Closure<R4> { return imported["component4"] as Closure<R4> }
	operator fun component5(): Closure<R5> { return imported["component5"] as Closure<R5> }
	operator fun component6(): Closure<R6> { return imported["component6"] as Closure<R6> }
	operator fun component7(): Closure<R7> { return imported["component7"] as Closure<R7> }
	operator fun component8(): Closure<R8> { return imported["component8"] as Closure<R8> }
	operator fun component9(): Closure<R9> { return imported["component9"] as Closure<R9> }
	operator fun component10(): Closure<R10> { return imported["component10"] as Closure<R10> }
	operator fun component11(): Closure<R11> { return imported["component11"] as Closure<R11> }
}
@JvmInline value class expandImport12<R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, R12>(val imported: Imported) {
	operator fun component1(): Closure<R1> { return imported["component1"] as Closure<R1> }
	operator fun component2(): Closure<R2> { return imported["component2"] as Closure<R2> }
	operator fun component3(): Closure<R3> { return imported["component3"] as Closure<R3> }
	operator fun component4(): Closure<R4> { return imported["component4"] as Closure<R4> }
	operator fun component5(): Closure<R5> { return imported["component5"] as Closure<R5> }
	operator fun component6(): Closure<R6> { return imported["component6"] as Closure<R6> }
	operator fun component7(): Closure<R7> { return imported["component7"] as Closure<R7> }
	operator fun component8(): Closure<R8> { return imported["component8"] as Closure<R8> }
	operator fun component9(): Closure<R9> { return imported["component9"] as Closure<R9> }
	operator fun component10(): Closure<R10> { return imported["component10"] as Closure<R10> }
	operator fun component11(): Closure<R11> { return imported["component11"] as Closure<R11> }
	operator fun component12(): Closure<R12> { return imported["component12"] as Closure<R12> }
}
@JvmInline value class expandImport13<R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, R12, R13>(val imported: Imported) {
	operator fun component1(): Closure<R1> { return imported["component1"] as Closure<R1> }
	operator fun component2(): Closure<R2> { return imported["component2"] as Closure<R2> }
	operator fun component3(): Closure<R3> { return imported["component3"] as Closure<R3> }
	operator fun component4(): Closure<R4> { return imported["component4"] as Closure<R4> }
	operator fun component5(): Closure<R5> { return imported["component5"] as Closure<R5> }
	operator fun component6(): Closure<R6> { return imported["component6"] as Closure<R6> }
	operator fun component7(): Closure<R7> { return imported["component7"] as Closure<R7> }
	operator fun component8(): Closure<R8> { return imported["component8"] as Closure<R8> }
	operator fun component9(): Closure<R9> { return imported["component9"] as Closure<R9> }
	operator fun component10(): Closure<R10> { return imported["component10"] as Closure<R10> }
	operator fun component11(): Closure<R11> { return imported["component11"] as Closure<R11> }
	operator fun component12(): Closure<R12> { return imported["component12"] as Closure<R12> }
	operator fun component13(): Closure<R13> { return imported["component13"] as Closure<R13> }
}
@JvmInline value class expandImport14<R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, R12, R13, R14>(val imported: Imported) {
	operator fun component1(): Closure<R1> { return imported["component1"] as Closure<R1> }
	operator fun component2(): Closure<R2> { return imported["component2"] as Closure<R2> }
	operator fun component3(): Closure<R3> { return imported["component3"] as Closure<R3> }
	operator fun component4(): Closure<R4> { return imported["component4"] as Closure<R4> }
	operator fun component5(): Closure<R5> { return imported["component5"] as Closure<R5> }
	operator fun component6(): Closure<R6> { return imported["component6"] as Closure<R6> }
	operator fun component7(): Closure<R7> { return imported["component7"] as Closure<R7> }
	operator fun component8(): Closure<R8> { return imported["component8"] as Closure<R8> }
	operator fun component9(): Closure<R9> { return imported["component9"] as Closure<R9> }
	operator fun component10(): Closure<R10> { return imported["component10"] as Closure<R10> }
	operator fun component11(): Closure<R11> { return imported["component11"] as Closure<R11> }
	operator fun component12(): Closure<R12> { return imported["component12"] as Closure<R12> }
	operator fun component13(): Closure<R13> { return imported["component13"] as Closure<R13> }
	operator fun component14(): Closure<R14> { return imported["component14"] as Closure<R14> }
}
@JvmInline value class expandImport15<R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, R12, R13, R14, R15>(val imported: Imported) {
	operator fun component1(): Closure<R1> { return imported["component1"] as Closure<R1> }
	operator fun component2(): Closure<R2> { return imported["component2"] as Closure<R2> }
	operator fun component3(): Closure<R3> { return imported["component3"] as Closure<R3> }
	operator fun component4(): Closure<R4> { return imported["component4"] as Closure<R4> }
	operator fun component5(): Closure<R5> { return imported["component5"] as Closure<R5> }
	operator fun component6(): Closure<R6> { return imported["component6"] as Closure<R6> }
	operator fun component7(): Closure<R7> { return imported["component7"] as Closure<R7> }
	operator fun component8(): Closure<R8> { return imported["component8"] as Closure<R8> }
	operator fun component9(): Closure<R9> { return imported["component9"] as Closure<R9> }
	operator fun component10(): Closure<R10> { return imported["component10"] as Closure<R10> }
	operator fun component11(): Closure<R11> { return imported["component11"] as Closure<R11> }
	operator fun component12(): Closure<R12> { return imported["component12"] as Closure<R12> }
	operator fun component13(): Closure<R13> { return imported["component13"] as Closure<R13> }
	operator fun component14(): Closure<R14> { return imported["component14"] as Closure<R14> }
	operator fun component15(): Closure<R15> { return imported["component15"] as Closure<R15> }
}
@JvmInline value class expandImport16<R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, R12, R13, R14, R15, R16>(val imported: Imported) {
	operator fun component1(): Closure<R1> { return imported["component1"] as Closure<R1> }
	operator fun component2(): Closure<R2> { return imported["component2"] as Closure<R2> }
	operator fun component3(): Closure<R3> { return imported["component3"] as Closure<R3> }
	operator fun component4(): Closure<R4> { return imported["component4"] as Closure<R4> }
	operator fun component5(): Closure<R5> { return imported["component5"] as Closure<R5> }
	operator fun component6(): Closure<R6> { return imported["component6"] as Closure<R6> }
	operator fun component7(): Closure<R7> { return imported["component7"] as Closure<R7> }
	operator fun component8(): Closure<R8> { return imported["component8"] as Closure<R8> }
	operator fun component9(): Closure<R9> { return imported["component9"] as Closure<R9> }
	operator fun component10(): Closure<R10> { return imported["component10"] as Closure<R10> }
	operator fun component11(): Closure<R11> { return imported["component11"] as Closure<R11> }
	operator fun component12(): Closure<R12> { return imported["component12"] as Closure<R12> }
	operator fun component13(): Closure<R13> { return imported["component13"] as Closure<R13> }
	operator fun component14(): Closure<R14> { return imported["component14"] as Closure<R14> }
	operator fun component15(): Closure<R15> { return imported["component15"] as Closure<R15> }
	operator fun component16(): Closure<R16> { return imported["component16"] as Closure<R16> }
}
@JvmInline value class expandImport17<R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, R12, R13, R14, R15, R16, R17>(val imported: Imported) {
	operator fun component1(): Closure<R1> { return imported["component1"] as Closure<R1> }
	operator fun component2(): Closure<R2> { return imported["component2"] as Closure<R2> }
	operator fun component3(): Closure<R3> { return imported["component3"] as Closure<R3> }
	operator fun component4(): Closure<R4> { return imported["component4"] as Closure<R4> }
	operator fun component5(): Closure<R5> { return imported["component5"] as Closure<R5> }
	operator fun component6(): Closure<R6> { return imported["component6"] as Closure<R6> }
	operator fun component7(): Closure<R7> { return imported["component7"] as Closure<R7> }
	operator fun component8(): Closure<R8> { return imported["component8"] as Closure<R8> }
	operator fun component9(): Closure<R9> { return imported["component9"] as Closure<R9> }
	operator fun component10(): Closure<R10> { return imported["component10"] as Closure<R10> }
	operator fun component11(): Closure<R11> { return imported["component11"] as Closure<R11> }
	operator fun component12(): Closure<R12> { return imported["component12"] as Closure<R12> }
	operator fun component13(): Closure<R13> { return imported["component13"] as Closure<R13> }
	operator fun component14(): Closure<R14> { return imported["component14"] as Closure<R14> }
	operator fun component15(): Closure<R15> { return imported["component15"] as Closure<R15> }
	operator fun component16(): Closure<R16> { return imported["component16"] as Closure<R16> }
	operator fun component17(): Closure<R17> { return imported["component17"] as Closure<R17> }
}
@JvmInline value class expandImport18<R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, R12, R13, R14, R15, R16, R17, R18>(val imported: Imported) {
	operator fun component1(): Closure<R1> { return imported["component1"] as Closure<R1> }
	operator fun component2(): Closure<R2> { return imported["component2"] as Closure<R2> }
	operator fun component3(): Closure<R3> { return imported["component3"] as Closure<R3> }
	operator fun component4(): Closure<R4> { return imported["component4"] as Closure<R4> }
	operator fun component5(): Closure<R5> { return imported["component5"] as Closure<R5> }
	operator fun component6(): Closure<R6> { return imported["component6"] as Closure<R6> }
	operator fun component7(): Closure<R7> { return imported["component7"] as Closure<R7> }
	operator fun component8(): Closure<R8> { return imported["component8"] as Closure<R8> }
	operator fun component9(): Closure<R9> { return imported["component9"] as Closure<R9> }
	operator fun component10(): Closure<R10> { return imported["component10"] as Closure<R10> }
	operator fun component11(): Closure<R11> { return imported["component11"] as Closure<R11> }
	operator fun component12(): Closure<R12> { return imported["component12"] as Closure<R12> }
	operator fun component13(): Closure<R13> { return imported["component13"] as Closure<R13> }
	operator fun component14(): Closure<R14> { return imported["component14"] as Closure<R14> }
	operator fun component15(): Closure<R15> { return imported["component15"] as Closure<R15> }
	operator fun component16(): Closure<R16> { return imported["component16"] as Closure<R16> }
	operator fun component17(): Closure<R17> { return imported["component17"] as Closure<R17> }
	operator fun component18(): Closure<R18> { return imported["component18"] as Closure<R18> }
}
@JvmInline value class expandImport19<R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, R12, R13, R14, R15, R16, R17, R18, R19>(val imported: Imported) {
	operator fun component1(): Closure<R1> { return imported["component1"] as Closure<R1> }
	operator fun component2(): Closure<R2> { return imported["component2"] as Closure<R2> }
	operator fun component3(): Closure<R3> { return imported["component3"] as Closure<R3> }
	operator fun component4(): Closure<R4> { return imported["component4"] as Closure<R4> }
	operator fun component5(): Closure<R5> { return imported["component5"] as Closure<R5> }
	operator fun component6(): Closure<R6> { return imported["component6"] as Closure<R6> }
	operator fun component7(): Closure<R7> { return imported["component7"] as Closure<R7> }
	operator fun component8(): Closure<R8> { return imported["component8"] as Closure<R8> }
	operator fun component9(): Closure<R9> { return imported["component9"] as Closure<R9> }
	operator fun component10(): Closure<R10> { return imported["component10"] as Closure<R10> }
	operator fun component11(): Closure<R11> { return imported["component11"] as Closure<R11> }
	operator fun component12(): Closure<R12> { return imported["component12"] as Closure<R12> }
	operator fun component13(): Closure<R13> { return imported["component13"] as Closure<R13> }
	operator fun component14(): Closure<R14> { return imported["component14"] as Closure<R14> }
	operator fun component15(): Closure<R15> { return imported["component15"] as Closure<R15> }
	operator fun component16(): Closure<R16> { return imported["component16"] as Closure<R16> }
	operator fun component17(): Closure<R17> { return imported["component17"] as Closure<R17> }
	operator fun component18(): Closure<R18> { return imported["component18"] as Closure<R18> }
	operator fun component19(): Closure<R19> { return imported["component19"] as Closure<R19> }
}
@JvmInline value class expandImport20<R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, R12, R13, R14, R15, R16, R17, R18, R19, R20>(val imported: Imported) {
	operator fun component1(): Closure<R1> { return imported["component1"] as Closure<R1> }
	operator fun component2(): Closure<R2> { return imported["component2"] as Closure<R2> }
	operator fun component3(): Closure<R3> { return imported["component3"] as Closure<R3> }
	operator fun component4(): Closure<R4> { return imported["component4"] as Closure<R4> }
	operator fun component5(): Closure<R5> { return imported["component5"] as Closure<R5> }
	operator fun component6(): Closure<R6> { return imported["component6"] as Closure<R6> }
	operator fun component7(): Closure<R7> { return imported["component7"] as Closure<R7> }
	operator fun component8(): Closure<R8> { return imported["component8"] as Closure<R8> }
	operator fun component9(): Closure<R9> { return imported["component9"] as Closure<R9> }
	operator fun component10(): Closure<R10> { return imported["component10"] as Closure<R10> }
	operator fun component11(): Closure<R11> { return imported["component11"] as Closure<R11> }
	operator fun component12(): Closure<R12> { return imported["component12"] as Closure<R12> }
	operator fun component13(): Closure<R13> { return imported["component13"] as Closure<R13> }
	operator fun component14(): Closure<R14> { return imported["component14"] as Closure<R14> }
	operator fun component15(): Closure<R15> { return imported["component15"] as Closure<R15> }
	operator fun component16(): Closure<R16> { return imported["component16"] as Closure<R16> }
	operator fun component17(): Closure<R17> { return imported["component17"] as Closure<R17> }
	operator fun component18(): Closure<R18> { return imported["component18"] as Closure<R18> }
	operator fun component19(): Closure<R19> { return imported["component19"] as Closure<R19> }
	operator fun component20(): Closure<R20> { return imported["component20"] as Closure<R20> }
}
@JvmInline value class expandImport21<R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, R12, R13, R14, R15, R16, R17, R18, R19, R20, R21>(val imported: Imported) {
	operator fun component1(): Closure<R1> { return imported["component1"] as Closure<R1> }
	operator fun component2(): Closure<R2> { return imported["component2"] as Closure<R2> }
	operator fun component3(): Closure<R3> { return imported["component3"] as Closure<R3> }
	operator fun component4(): Closure<R4> { return imported["component4"] as Closure<R4> }
	operator fun component5(): Closure<R5> { return imported["component5"] as Closure<R5> }
	operator fun component6(): Closure<R6> { return imported["component6"] as Closure<R6> }
	operator fun component7(): Closure<R7> { return imported["component7"] as Closure<R7> }
	operator fun component8(): Closure<R8> { return imported["component8"] as Closure<R8> }
	operator fun component9(): Closure<R9> { return imported["component9"] as Closure<R9> }
	operator fun component10(): Closure<R10> { return imported["component10"] as Closure<R10> }
	operator fun component11(): Closure<R11> { return imported["component11"] as Closure<R11> }
	operator fun component12(): Closure<R12> { return imported["component12"] as Closure<R12> }
	operator fun component13(): Closure<R13> { return imported["component13"] as Closure<R13> }
	operator fun component14(): Closure<R14> { return imported["component14"] as Closure<R14> }
	operator fun component15(): Closure<R15> { return imported["component15"] as Closure<R15> }
	operator fun component16(): Closure<R16> { return imported["component16"] as Closure<R16> }
	operator fun component17(): Closure<R17> { return imported["component17"] as Closure<R17> }
	operator fun component18(): Closure<R18> { return imported["component18"] as Closure<R18> }
	operator fun component19(): Closure<R19> { return imported["component19"] as Closure<R19> }
	operator fun component20(): Closure<R20> { return imported["component20"] as Closure<R20> }
	operator fun component21(): Closure<R21> { return imported["component21"] as Closure<R21> }
}
@JvmInline value class expandImport22<R1, R2, R3, R4, R5, R6, R7, R8, R9, R10, R11, R12, R13, R14, R15, R16, R17, R18, R19, R20, R21, R22>(val imported: Imported) {
	operator fun component1(): Closure<R1> { return imported["component1"] as Closure<R1> }
	operator fun component2(): Closure<R2> { return imported["component2"] as Closure<R2> }
	operator fun component3(): Closure<R3> { return imported["component3"] as Closure<R3> }
	operator fun component4(): Closure<R4> { return imported["component4"] as Closure<R4> }
	operator fun component5(): Closure<R5> { return imported["component5"] as Closure<R5> }
	operator fun component6(): Closure<R6> { return imported["component6"] as Closure<R6> }
	operator fun component7(): Closure<R7> { return imported["component7"] as Closure<R7> }
	operator fun component8(): Closure<R8> { return imported["component8"] as Closure<R8> }
	operator fun component9(): Closure<R9> { return imported["component9"] as Closure<R9> }
	operator fun component10(): Closure<R10> { return imported["component10"] as Closure<R10> }
	operator fun component11(): Closure<R11> { return imported["component11"] as Closure<R11> }
	operator fun component12(): Closure<R12> { return imported["component12"] as Closure<R12> }
	operator fun component13(): Closure<R13> { return imported["component13"] as Closure<R13> }
	operator fun component14(): Closure<R14> { return imported["component14"] as Closure<R14> }
	operator fun component15(): Closure<R15> { return imported["component15"] as Closure<R15> }
	operator fun component16(): Closure<R16> { return imported["component16"] as Closure<R16> }
	operator fun component17(): Closure<R17> { return imported["component17"] as Closure<R17> }
	operator fun component18(): Closure<R18> { return imported["component18"] as Closure<R18> }
	operator fun component19(): Closure<R19> { return imported["component19"] as Closure<R19> }
	operator fun component20(): Closure<R20> { return imported["component20"] as Closure<R20> }
	operator fun component21(): Closure<R21> { return imported["component21"] as Closure<R21> }
	operator fun component22(): Closure<R22> { return imported["component22"] as Closure<R22> }
}

fun parseProperty(name: String): Triple<Boolean, Boolean, Boolean> {
	val isGetter = name.startsWith("get") && (!name[3].isLetter() || name[3].isUpperCase())
	val isGetterBoolean = name.startsWith("is") && (!name[2].isLetter() || name[2].isUpperCase())
	val isSetter = name.startsWith("set") && (!name[3].isLetter() || name[3].isUpperCase())
	return Triple(isGetter, isGetterBoolean, isSetter)
}

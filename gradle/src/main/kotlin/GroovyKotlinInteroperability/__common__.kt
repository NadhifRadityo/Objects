package GroovyKotlinInteroperability

import kotlin.jvm.functions.FunctionN

fun construct() {
	GroovyManipulation.construct()
	GroovyInteroperability.construct()
}
fun destruct() {
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

fun parseProperty(name: String): Triple<Boolean, Boolean, Boolean> {
	val isGetter = name.startsWith("get") && (!name[3].isLetter() || name[3].isUpperCase())
	val isGetterBoolean = name.startsWith("is") && (!name[2].isLetter() || name[2].isUpperCase())
	val isSetter = name.startsWith("set") && (!name[3].isLetter() || name[3].isUpperCase())
	return Triple(isGetter, isGetterBoolean, isSetter)
}

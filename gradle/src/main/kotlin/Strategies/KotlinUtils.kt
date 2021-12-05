package Strategies

import Common.groovyKotlinCaches
import GroovyKotlinInteroperability.ExportGradle
import GroovyKotlinInteroperability.GroovyInteroperability.prepareGroovyKotlinCache
import GroovyKotlinInteroperability.GroovyKotlinCache

object KotlinUtils {
	@JvmStatic private var cache: GroovyKotlinCache<KotlinUtils>? = null

	@JvmStatic
	fun construct() {
		cache = prepareGroovyKotlinCache(KotlinUtils)
		groovyKotlinCaches += cache!!
	}
	@JvmStatic
	fun destruct() {
		groovyKotlinCaches -= cache!!
		cache = null
	}

	@ExportGradle(names=["arrayOf"]) @JvmStatic fun _arrayOf(vararg elements: Any?): Array<Any?> { return arrayOf(*elements) }
	@ExportGradle(names=["arrayOfNulls"]) @JvmStatic fun _arrayOfNulls(size: Int): Array<Any?> { return arrayOfNulls(size) }
	@ExportGradle(names=["booleanArrayOf"]) @JvmStatic fun _booleanArrayOf(vararg elements: Boolean): BooleanArray { return booleanArrayOf(*elements) }
	@ExportGradle(names=["byteArrayOf"]) @JvmStatic fun _byteArrayOf(vararg elements: Byte): ByteArray { return byteArrayOf(*elements) }
	@ExportGradle(names=["charArrayOf"]) @JvmStatic fun _charArrayOf(vararg elements: Char): CharArray { return charArrayOf(*elements) }
	@ExportGradle(names=["doubleArrayOf"]) @JvmStatic fun _doubleArrayOf(vararg elements: Double): DoubleArray { return doubleArrayOf(*elements) }
	@ExportGradle(names=["floatArrayOf"]) @JvmStatic fun _floatArrayOf(vararg elements: Float): FloatArray { return floatArrayOf(*elements) }
	@ExportGradle(names=["intArrayOf"]) @JvmStatic fun _intArrayOf(vararg elements: Int): IntArray { return intArrayOf(*elements) }
	@ExportGradle(names=["longArrayOf"]) @JvmStatic fun _longArrayOf(vararg elements: Long): LongArray { return longArrayOf(*elements) }
	@ExportGradle(names=["shortArrayOf"]) @JvmStatic fun _shortArrayOf(vararg elements: Short): ShortArray { return shortArrayOf(*elements) }

	@ExportGradle(names=["emptyList"]) @JvmStatic fun _emptyList(): List<Any?> { return emptyList() }
	@ExportGradle(names=["listOf"]) @JvmStatic fun _listOf(vararg elements: Any?): List<Any?> { return listOf(*elements) }
	@ExportGradle(names=["mutableListOf"]) @JvmStatic fun _mutableListOf(vararg elements: Any?): MutableList<Any?> { return mutableListOf(*elements) }
	@ExportGradle(names=["arrayListOf"]) @JvmStatic fun _arrayListOf(vararg elements: Any?): ArrayList<Any?> { return arrayListOf(*elements) }
	@ExportGradle(names=["listOfNotNull"]) @JvmStatic fun _listOfNotNull(vararg elements: Any?): List<Any?> { return listOfNotNull(*elements) }

	@ExportGradle(names=["emptySet"]) @JvmStatic fun _emptySet(): Set<Any?> { return emptySet() }
	@ExportGradle(names=["setOf"]) @JvmStatic fun _setOf(vararg elements: Any?): Set<Any?> { return setOf(*elements) }
	@ExportGradle(names=["mutableSetOf"]) @JvmStatic fun _mutableSetOf(vararg elements: Any?): MutableSet<Any?> { return mutableSetOf(*elements) }
	@ExportGradle(names=["hashSetOf"]) @JvmStatic fun _hashSetOf(vararg elements: Any?): HashSet<Any?> { return hashSetOf(*elements) }
	@ExportGradle(names=["linkedSetOf"]) @JvmStatic fun _linkedSetOf(vararg elements: Any?): LinkedHashSet<Any?> { return linkedSetOf(*elements) }
	@ExportGradle(names=["setOfNotNull"]) @JvmStatic fun _setOfNotNull(vararg elements: Any?): Set<Any?> { return setOfNotNull(*elements) }

	@ExportGradle(names=["emptyMap"]) @JvmStatic fun _emptyMap(): Map<Any?, Any?> { return emptyMap() }
	@ExportGradle(names=["mapOf"]) @JvmStatic fun _mapOf(vararg pairs: Pair<Any?, Any?>): Map<Any?, Any?> { return mapOf(*pairs) }
	@ExportGradle(names=["mutableMapOf"]) @JvmStatic fun _mutableMapOf(vararg pairs: Pair<Any?, Any?>): MutableMap<Any?, Any?> { return mutableMapOf(*pairs) }
	@ExportGradle(names=["hashMapOf"]) @JvmStatic fun _hashMapOf(vararg pairs: Pair<Any?, Any?>): HashMap<Any?, Any?> { return hashMapOf(*pairs) }
	@ExportGradle(names=["linkedMapOf"]) @JvmStatic fun _linkedMapOf(vararg pairs: Pair<Any?, Any?>): LinkedHashMap<Any?, Any?> { return linkedMapOf(*pairs) }
}

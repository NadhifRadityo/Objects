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

	@ExportGradle(names=["arrayOf"]) @JvmStatic fun _arrayOf(vararg elements: Any): Array<Any> { return arrayOf(*elements) }
	@ExportGradle(names=["arrayOfNulls"]) @JvmStatic fun _arrayOfNulls(size: Int): Array<Any?> { return arrayOfNulls(size) }
	@ExportGradle(names=["booleanArrayOf"]) @JvmStatic fun _booleanArrayOf(vararg elements: Boolean): BooleanArray { return booleanArrayOf(*elements) }
	@ExportGradle(names=["byteArrayOf"]) @JvmStatic fun _byteArrayOf(vararg elements: Byte): ByteArray { return byteArrayOf(*elements) }
	@ExportGradle(names=["charArrayOf"]) @JvmStatic fun _charArrayOf(vararg elements: Char): CharArray { return charArrayOf(*elements) }
	@ExportGradle(names=["doubleArrayOf"]) @JvmStatic fun _doubleArrayOf(vararg elements: Double): DoubleArray { return doubleArrayOf(*elements) }
	@ExportGradle(names=["floatArrayOf"]) @JvmStatic fun _floatArrayOf(vararg elements: Float): FloatArray { return floatArrayOf(*elements) }
	@ExportGradle(names=["intArrayOf"]) @JvmStatic fun _intArrayOf(vararg elements: Int): IntArray { return intArrayOf(*elements) }
	@ExportGradle(names=["longArrayOf"]) @JvmStatic fun _longArrayOf(vararg elements: Long): LongArray { return longArrayOf(*elements) }
	@ExportGradle(names=["shortArrayOf"]) @JvmStatic fun _shortArrayOf(vararg elements: Short): ShortArray { return shortArrayOf(*elements) }

	@ExportGradle(names=["emptyList"]) @JvmStatic fun _emptyList(): List<Any> { return emptyList() }
	@ExportGradle(names=["listOf"]) @JvmStatic fun _listOf(vararg elements: Any): List<Any> { return listOf(*elements) }
	@ExportGradle(names=["mutableListOf"]) @JvmStatic fun _mutableListOf(vararg elements: Any): MutableList<Any> { return mutableListOf(*elements) }
	@ExportGradle(names=["arrayListOf"]) @JvmStatic fun _arrayListOf(vararg elements: Any): MutableList<Any> { return arrayListOf(*elements) }
	@ExportGradle(names=["listOfNotNull"]) @JvmStatic fun _listOfNotNull(vararg elements: Any): List<Any> { return listOfNotNull(*elements) }
}

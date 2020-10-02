package com.appacoustic.rt.data.filter

data class NumeratorDenominator(
    val b: DoubleArray,
    val a: DoubleArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as NumeratorDenominator

        if (!b.contentEquals(other.b)) return false
        if (!a.contentEquals(other.a)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = b.contentHashCode()
        result = 31 * result + a.contentHashCode()
        return result
    }
}

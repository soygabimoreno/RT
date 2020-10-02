package com.appacoustic.rt.data.filter

data class ButterworthCoefficientsOrder2(
    val b125: DoubleArray = doubleArrayOf(
        3.929676515700289e-05,
        0.0,
        -7.859353031400579e-05,
        0.0,
        3.929676515700289e-05
    ),
    val a125: DoubleArray = doubleArrayOf(
        1.0,
        -3.981559459679419e+00,
        5.945472787148730e+00,
        -3.946261397152608e+00,
        9.823481693904715e-01
    ),
    val b250: DoubleArray = doubleArrayOf(
        1.558059149882655e-04,
        0.0,
        -3.116118299765310e-04,
        0.0,
        1.558059149882655e-04,
    ),
    val a250: DoubleArray = doubleArrayOf(
        1.0,
        -3.961870007021670e+00,
        5.888793913787786e+00,
        -3.891930251948569e+00,
        9.650079262290459e-01,
    ),
    val b500: DoubleArray = doubleArrayOf(
        6.124167588371880e-04,
        0.0,
        -1.224833517674376e-03,
        0.0,
        6.124167588371880e-04,
    ),
    val a500: DoubleArray = doubleArrayOf(
        1.0,
        -3.918823751633717e+00,
        5.769261524124245e+00,
        -3.781653237866402e+00,
        9.312403077020502e-01,
    ),
    val b1000: DoubleArray = doubleArrayOf(
        2.366944651196807e-03,
        0.0,
        -4.733889302393613e-03,
        0.0,
        2.366944651196807e-03,
    ),
    val a1000: DoubleArray = doubleArrayOf(
        1.0,
        -3.818619281298950e+00,
        5.507523751882116e+00,
        -3.555730199154567e+00,
        8.672088097654537e-01,
    ),
    val b2000: DoubleArray = doubleArrayOf(
        8.861384842871868e-03,
        0.0,
        -1.772276968574374e-02,
        0.0,
        8.861384842871868e-03,
    ),
    val a2000: DoubleArray = doubleArrayOf(
        1.0,
        -3.566233905008885e+00,
        4.910270117609992e+00,
        -3.090416995530277e+00,
        7.520594824636435e-01,
    ),
    val b4000: DoubleArray = doubleArrayOf(
        3.135695184246452e-02,
        0.0,
        -6.271390368492903e-02,
        0.0,
        3.135695184246452e-02,
    ),
    val a4000: DoubleArray = doubleArrayOf(
        1.0,
        -2.889193822757614e+00,
        3.560473220464110e+00,
        -2.159920874581686e+00,
        5.658008519099556e-01,
    )
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ButterworthCoefficientsOrder2

        if (!b125.contentEquals(other.b125)) return false
        if (!a125.contentEquals(other.a125)) return false
        if (!b250.contentEquals(other.b250)) return false
        if (!a250.contentEquals(other.a250)) return false
        if (!b500.contentEquals(other.b500)) return false
        if (!a500.contentEquals(other.a500)) return false
        if (!b1000.contentEquals(other.b1000)) return false
        if (!a1000.contentEquals(other.a1000)) return false
        if (!b2000.contentEquals(other.b2000)) return false
        if (!a2000.contentEquals(other.a2000)) return false
        if (!b4000.contentEquals(other.b4000)) return false
        if (!a4000.contentEquals(other.a4000)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = b125.contentHashCode()
        result = 31 * result + a125.contentHashCode()
        result = 31 * result + b250.contentHashCode()
        result = 31 * result + a250.contentHashCode()
        result = 31 * result + b500.contentHashCode()
        result = 31 * result + a500.contentHashCode()
        result = 31 * result + b1000.contentHashCode()
        result = 31 * result + a1000.contentHashCode()
        result = 31 * result + b2000.contentHashCode()
        result = 31 * result + a2000.contentHashCode()
        result = 31 * result + b4000.contentHashCode()
        result = 31 * result + a4000.contentHashCode()
        return result
    }
}

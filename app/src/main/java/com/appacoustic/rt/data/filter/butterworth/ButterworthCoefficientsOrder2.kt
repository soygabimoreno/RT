package com.appacoustic.rt.data.filter.butterworth

import com.appacoustic.rt.data.filter.NumeratorDenominator

enum class ButterworthCoefficientsOrder2(
    override val numeratorDenominator: NumeratorDenominator,
    override val nOrder: Int = 2
) : ButterworthCoefficients {
    FREQUENCY_125(
        NumeratorDenominator(
            b = doubleArrayOf(
                3.929676515700289e-05,
                0.0,
                -7.859353031400579e-05,
                0.0,
                3.929676515700289e-05
            ),
            a = doubleArrayOf(
                1.0,
                -3.981559459679419e+00,
                5.945472787148730e+00,
                -3.946261397152608e+00,
                9.823481693904715e-01
            )
        )
    ),
    FREQUENCY_250(
        NumeratorDenominator(
            b = doubleArrayOf(
                1.558059149882655e-04,
                0.0,
                -3.116118299765310e-04,
                0.0,
                1.558059149882655e-04
            ),
            a = doubleArrayOf(
                1.0,
                -3.961870007021670e+00,
                5.888793913787786e+00,
                -3.891930251948569e+00,
                9.650079262290459e-01
            )
        )
    ),
    FREQUENCY_500(
        NumeratorDenominator(
            b = doubleArrayOf(
                6.124167588371880e-04,
                0.0,
                -1.224833517674376e-03,
                0.0,
                6.124167588371880e-04
            ),
            a = doubleArrayOf(
                1.0,
                -3.918823751633717e+00,
                5.769261524124245e+00,
                -3.781653237866402e+00,
                9.312403077020502e-01
            )
        )
    ),
    FREQUENCY_1000(
        NumeratorDenominator(
            b = doubleArrayOf(
                2.366944651196807e-03,
                0.0,
                -4.733889302393613e-03,
                0.0,
                2.366944651196807e-03
            ),
            a = doubleArrayOf(
                1.0,
                -3.818619281298950e+00,
                5.507523751882116e+00,
                -3.555730199154567e+00,
                8.672088097654537e-01
            )
        )
    ),
    FREQUENCY_2000(
        NumeratorDenominator(
            b = doubleArrayOf(
                8.861384842871868e-03,
                0.0,
                -1.772276968574374e-02,
                0.0,
                8.861384842871868e-03
            ),
            a = doubleArrayOf(
                1.0,
                -3.566233905008885e+00,
                4.910270117609992e+00,
                -3.090416995530277e+00,
                7.520594824636435e-01
            )
        )
    ),
    FREQUENCY_4000(
        NumeratorDenominator(
            b = doubleArrayOf(
                3.135695184246452e-02,
                0.0,
                -6.271390368492903e-02,
                0.0,
                3.135695184246452e-02
            ),
            a = doubleArrayOf(
                1.0,
                -2.889193822757614e+00,
                3.560473220464110e+00,
                -2.159920874581686e+00,
                5.658008519099556e-01
            )
        )
    )
}

package com.appacoustic.rt.data.filter.butterworth

import com.appacoustic.rt.data.filter.NumeratorDenominator

enum class ButterworthCoefficientsOrder1(
    override val numeratorDenominator: NumeratorDenominator,
    override val nOrder: Int = ButterworthOrder.N_1.nOrder
) : ButterworthCoefficients {
    FREQUENCY_125(
        NumeratorDenominator(
            b = doubleArrayOf(
                6.257285854711411e-03,
                0.0,
                -6.257285854711411e-03
            ),
            a = doubleArrayOf(
                1.0,
                -1.987170239472394e+00,
                9.874854282905769e-01
            )
        )
    ),
    FREQUENCY_250(
        NumeratorDenominator(
            b = doubleArrayOf(
                1.243723838285456e-02,
                0.0,
                -1.243723838285456e-02
            ),
            a = doubleArrayOf(
                1.0,
                -1.973872658103258e+00,
                9.751255232342908e-01
            )
        )
    ),
    FREQUENCY_500(
        NumeratorDenominator(
            b = doubleArrayOf(
                2.457270902253179e-02,
                0.0,
                -2.457270902253179e-02
            ),
            a = doubleArrayOf(
                1.0,
                -1.945905488731061e+00,
                9.508545819549364e-01
            )
        )
    ),
    FREQUENCY_1000(
        NumeratorDenominator(
            b = doubleArrayOf(
                4.799574337795706e-02,
                0.0,
                -4.799574337795706e-02
            ),
            a = doubleArrayOf(
                1.0,
                -1.884699766444328e+00,
                9.040085132440860e-01
            )
        )
    ),
    FREQUENCY_2000(
        NumeratorDenominator(
            b = doubleArrayOf(
                9.180727689490262e-02,
                0.0,
                -9.180727689490262e-02
            ),
            a = doubleArrayOf(
                1.0,
                -1.742891637932728e+00,
                8.163854462101952e-01
            )
        )
    ),
    FREQUENCY_4000(
        NumeratorDenominator(
            b = doubleArrayOf(
                1.696166553803913e-01,
                0.0,
                -1.696166553803913e-01
            ),
            a = doubleArrayOf(
                1.0,
                -1.394696678956508e+00,
                6.607666892392174e-01
            )
        )
    )
}

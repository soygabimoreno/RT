package com.appacoustic.rt.data.filter.butterworth

import com.appacoustic.rt.data.filter.NumeratorDenominator

enum class ButterworthCoefficientsOrder4(
    override val numeratorDenominator: NumeratorDenominator,
    override val nOrder: Int = ButterworthOrder.N_4.nOrder
) : ButterworthCoefficients {
    FREQUENCY_125(
        NumeratorDenominator(
            b = doubleArrayOf(
                1.546330403942967e-09,
                0.0,
                -6.185321615771869e-09,
                0.0,
                9.277982423657804e-09,
                0.0,
                -6.185321615771869e-09,
                0.0,
                1.546330403942967e-09
            ),
            a = doubleArrayOf(
                1.0,
                -7.965828998251020e+00,
                2.776263857419651e+01,
                -5.529338461026458e+01,
                6.883136036256866e+01,
                -5.484035188083344e+01,
                2.730956992024077e+01,
                -7.771631054353775e+00,
                9.676276866968969e-01
            )
        )
    ),
    FREQUENCY_250(
        NumeratorDenominator(
            b = doubleArrayOf(
                2.434136711306499e-08,
                0.0,
                -9.736546845225996e-08,
                0.0,
                1.460482026783899e-07,
                0.0,
                -9.736546845225996e-08,
                0.0,
                2.434136711306499e-08
            ),
            a = doubleArrayOf(
                1.0,
                -7.929152753057954e+00,
                2.751151646888077e+01,
                -5.455657735540087e+01,
                6.763050208430585e+01,
                -5.366621741060912e+01,
                2.662087407275280e+01,
                -7.547247015636179e+00,
                9.363019087672075e-01
            )
        )
    ),
    FREQUENCY_500(
        NumeratorDenominator(
            b = doubleArrayOf(
                3.770906769473942e-07,
                0.0,
                -1.508362707789577e-06,
                0.0,
                2.262544061684365e-06,
                0.0,
                -1.508362707789577e-06,
                0.0,
                3.770906769473942e-07
            ),
            a = doubleArrayOf(
                1.0,
                -7.848415054478742e+00,
                2.696956914195161e+01,
                -5.299802001496096e+01,
                6.514135280763709e+01,
                -5.128205373617932e+01,
                2.525142428525900e+01,
                -7.110507969499027e+00,
                8.766505408908472e-01
            )
        )
    ),
    FREQUENCY_1000(
        NumeratorDenominator(
            b = doubleArrayOf(
                5.663182598620790e-06,
                0.0,
                -2.265273039448316e-05,
                0.0,
                3.397909559172474e-05,
                0.0,
                -2.265273039448316e-05,
                0.0,
                5.663182598620790e-06
            ),
            a = doubleArrayOf(
                1.0,
                -7.658333133969251e+00,
                2.573851178898760e+01,
                -4.958196937683351e+01,
                5.987828341478472e+01,
                -4.642154872551343e+01,
                2.256200784274208e+01,
                -6.285392559582117e+00,
                7.684408977265627e-01
            )
        )
    ),
    FREQUENCY_2000(
        NumeratorDenominator(
            b = doubleArrayOf(
                8.021138626488560e-05,
                0.0,
                -3.208455450595424e-04,
                0.0,
                4.812683175893137e-04,
                0.0,
                -3.208455450595424e-04,
                0.0,
                8.021138626488560e-05
            ),
            a = doubleArrayOf(
                1.0,
                -7.171497257465158e+00,
                2.278811087460267e+01,
                -4.189273285613417e+01,
                4.872424333049440e+01,
                -3.671205318238091e+01,
                1.750108797790828e+01,
                -4.827163079326613e+00,
                5.900371328333353e-01
            )
        )
    ),
    FREQUENCY_4000(
        NumeratorDenominator(
            b = doubleArrayOf(
                1.023886978024911e-03,
                0.0,
                -4.095547912099646e-03,
                0.0,
                6.143321868149468e-03,
                0.0,
                -4.095547912099646e-03,
                0.0,
                1.023886978024911e-03
            ),
            a = doubleArrayOf(
                1.0,
                -5.836719368158203e+00,
                1.579851703479464e+01,
                -2.572308801747499e+01,
                2.748557351671896e+01,
                -1.971646334578914e+01,
                9.281471507521783e+00,
                -2.629020324986508e+00,
                3.459285698891766e-01
            )
        )
    )
}

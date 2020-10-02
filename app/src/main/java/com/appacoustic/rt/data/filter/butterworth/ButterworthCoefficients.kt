package com.appacoustic.rt.data.filter.butterworth

import com.appacoustic.rt.data.filter.NumeratorDenominator

interface ButterworthCoefficients {
    val numeratorDenominator: NumeratorDenominator
    val nOrder: Int
}

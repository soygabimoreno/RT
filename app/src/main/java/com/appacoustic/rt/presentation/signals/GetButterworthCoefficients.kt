package com.appacoustic.rt.presentation.signals

import com.appacoustic.rt.data.filter.butterworth.*

fun getButterworthCoefficients(
    butterworthFrequency: ButterworthFrequency,
    butterworthOrder: ButterworthOrder
): ButterworthCoefficients {
    return when (butterworthFrequency) {
        ButterworthFrequency.FREQUENCY_125 -> {
            when (butterworthOrder) {
                ButterworthOrder.N_1 -> ButterworthCoefficientsOrder1.FREQUENCY_125
                ButterworthOrder.N_2 -> ButterworthCoefficientsOrder2.FREQUENCY_125
                ButterworthOrder.N_4 -> ButterworthCoefficientsOrder4.FREQUENCY_125
                ButterworthOrder.N_8 -> ButterworthCoefficientsOrder8.FREQUENCY_125
            }
        }
        ButterworthFrequency.FREQUENCY_250 -> {
            when (butterworthOrder) {
                ButterworthOrder.N_1 -> ButterworthCoefficientsOrder1.FREQUENCY_250
                ButterworthOrder.N_2 -> ButterworthCoefficientsOrder2.FREQUENCY_250
                ButterworthOrder.N_4 -> ButterworthCoefficientsOrder4.FREQUENCY_250
                ButterworthOrder.N_8 -> ButterworthCoefficientsOrder8.FREQUENCY_250
            }
        }
        ButterworthFrequency.FREQUENCY_500 -> {
            when (butterworthOrder) {
                ButterworthOrder.N_1 -> ButterworthCoefficientsOrder1.FREQUENCY_500
                ButterworthOrder.N_2 -> ButterworthCoefficientsOrder2.FREQUENCY_500
                ButterworthOrder.N_4 -> ButterworthCoefficientsOrder4.FREQUENCY_500
                ButterworthOrder.N_8 -> ButterworthCoefficientsOrder8.FREQUENCY_500
            }
        }
        ButterworthFrequency.FREQUENCY_1000 -> {
            when (butterworthOrder) {
                ButterworthOrder.N_1 -> ButterworthCoefficientsOrder1.FREQUENCY_1000
                ButterworthOrder.N_2 -> ButterworthCoefficientsOrder2.FREQUENCY_1000
                ButterworthOrder.N_4 -> ButterworthCoefficientsOrder4.FREQUENCY_1000
                ButterworthOrder.N_8 -> ButterworthCoefficientsOrder8.FREQUENCY_1000
            }
        }
        ButterworthFrequency.FREQUENCY_2000 -> {
            when (butterworthOrder) {
                ButterworthOrder.N_1 -> ButterworthCoefficientsOrder1.FREQUENCY_2000
                ButterworthOrder.N_2 -> ButterworthCoefficientsOrder2.FREQUENCY_2000
                ButterworthOrder.N_4 -> ButterworthCoefficientsOrder4.FREQUENCY_2000
                ButterworthOrder.N_8 -> ButterworthCoefficientsOrder8.FREQUENCY_2000
            }
        }
        ButterworthFrequency.FREQUENCY_4000 -> {
            when (butterworthOrder) {
                ButterworthOrder.N_1 -> ButterworthCoefficientsOrder1.FREQUENCY_4000
                ButterworthOrder.N_2 -> ButterworthCoefficientsOrder2.FREQUENCY_4000
                ButterworthOrder.N_4 -> ButterworthCoefficientsOrder4.FREQUENCY_4000
                ButterworthOrder.N_8 -> ButterworthCoefficientsOrder8.FREQUENCY_4000
            }
        }
    }
}


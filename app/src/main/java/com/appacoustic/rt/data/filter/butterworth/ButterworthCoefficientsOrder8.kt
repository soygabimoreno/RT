package com.appacoustic.rt.data.filter.butterworth

import com.appacoustic.rt.data.filter.NumeratorDenominator

enum class ButterworthCoefficientsOrder8(
    override val numeratorDenominator: NumeratorDenominator,
    override val nOrder: Int = 8
) : ButterworthCoefficients {
    FREQUENCY_125(
        NumeratorDenominator(
            b = doubleArrayOf(
                1.514848635136415e-01,
                0.0,
                -1.211878908109132e+00,
                0.0,
                4.241576178381960e+00,
                0.0,
                -8.483152356763920e+00,
                0.0,
                1.060394044595490e+01,
                0.0,
                -8.483152356763920e+00,
                0.0,
                4.241576178381960e+00,
                0.0,
                -1.211878908109132e+00,
                0.0,
                1.514848635136415e-01
            ),
            a = doubleArrayOf(
                1.0,
                -1.593292225239295e+01,
                1.189985879791722e+02,
                -5.530231993716095e+02,
                1.789909718289628e+03,
                -4.278154218169149e+03,
                7.811268969816212e+03,
                -1.111365543295220e+04,
                1.245238328333992e+04,
                -1.102434107664026e+04,
                7.686223697529451e+03,
                -4.175837394818713e+03,
                1.733061550858418e+03,
                -5.311558055306670e+02,
                1.133746907341433e+02,
                -1.505793692943647e+01,
                9.374881174846405e-01
            )
        )
    ),
    FREQUENCY_250(
        NumeratorDenominator(
            b = doubleArrayOf(
                -1.279734745920687e-03,
                0.0,
                1.023787796736550e-02,
                0.0,
                -3.583257288577924e-02,
                0.0,
                7.166514577155848e-02,
                0.0,
                -8.958143221444812e-02,
                0.0,
                7.166514577155848e-02,
                0.0,
                -3.583257288577924e-02,
                0.0,
                1.023787796736550e-02,
                0.0,
                -1.279734745920687e-03
            ),
            a = doubleArrayOf(
                1.0,
                -1.586083182314282e+01,
                1.179320440177459e+02,
                -5.456596359665740e+02,
                1.758437869992929e+03,
                -4.185031410902124e+03,
                7.609203890367993e+03,
                -1.078149183508733e+04,
                1.203116468719138e+04,
                -1.060889268358413e+04,
                7.367525085282007e+03,
                -3.987239658140730e+03,
                1.648511121373961e+03,
                -5.033591199083927e+02,
                1.070481654421716e+02,
                -1.416656930714431e+01,
                8.788810513768383e-01
            )
        )
    ),
    FREQUENCY_500(
        NumeratorDenominator(
            b = doubleArrayOf(
                5.257752262837394e-06,
                0.0,
                -4.206201810269915e-05,
                0.0,
                1.472170633594471e-04,
                0.0,
                -2.944341267188942e-04,
                0.0,
                3.680426583986177e-04,
                0.0,
                -2.944341267188942e-04,
                0.0,
                1.472170633594471e-04,
                0.0,
                -4.206201810269915e-05,
                0.0,
                5.257752262837394e-06
            ),
            a = doubleArrayOf(
                1.0,
                -1.570186521440885e+01,
                1.156108314048082e+02,
                -5.298415004920995e+02,
                1.691704742716161e+03,
                -3.990116225134870e+03,
                7.191693628643308e+03,
                -1.010396352161485e+04,
                1.118297498794063e+04,
                -9.783006381405992e+03,
                6.742056084554707e+03,
                -3.621824836234090e+03,
                1.486782097905446e+03,
                -4.508683857457054e+02,
                9.525409140905494e+01,
                -1.252616010733851e+01,
                7.724113752401914e-01
            )
        )
    ),
    FREQUENCY_1000(
        NumeratorDenominator(
            b = doubleArrayOf(
                9.362828034051556e-08,
                0.0,
                -7.490262427241245e-07,
                0.0,
                2.621591849534436e-06,
                0.0,
                -5.243183699068872e-06,
                0.0,
                6.553979623836089e-06,
                0.0,
                -5.243183699068872e-06,
                0.0,
                2.621591849534436e-06,
                0.0,
                -7.490262427241245e-07,
                0.0,
                9.362828034051556e-08
            ),
            a = doubleArrayOf(
                1.0,
                -1.532659718597058e+01,
                1.102698008907005e+02,
                -4.943504256390925e+02,
                1.545646703808107e+03,
                -3.573819627335945e+03,
                6.321257941850472e+03,
                -8.724742523465431e+03,
                9.496634123504651e+03,
                -8.178980921874218e+03,
                5.555165926491976e+03,
                -2.944244328469204e+03,
                1.193713968348386e+03,
                -3.579112193343721e+02,
                7.484267340216148e+01,
                -9.751987408039948e+00,
                5.964924158194332e-01
            )
        )
    ),
    FREQUENCY_2000(
        NumeratorDenominator(
            b = doubleArrayOf(
                6.648799654869285e-09,
                0.0,
                -5.319039723895428e-08,
                0.0,
                1.861663903363400e-07,
                0.0,
                -3.723327806726800e-07,
                0.0,
                4.654159758408499e-07,
                0.0,
                -3.723327806726800e-07,
                0.0,
                1.861663903363400e-07,
                0.0,
                -5.319039723895428e-08,
                0.0,
                6.648799654869285e-09
            ),
            a = doubleArrayOf(
                1.0,
                -1.436177125418134e+01,
                9.726093725886194e+01,
                -4.122514101523289e+02,
                1.224004419012051e+03,
                -2.699173440474571e+03,
                4.572900489968592e+03,
                -6.071348286657170e+03,
                6.384029708872487e+03,
                -5.334136802016907e+03,
                3.529816651412181e+03,
                -1.830532742808144e+03,
                7.293294158359240e+02,
                -2.158280457760017e+02,
                4.474081886255839e+01,
                -5.805138538708931e+00,
                3.551964564534750e-01
            )
        )
    ),
    FREQUENCY_4000(
        NumeratorDenominator(
            b = doubleArrayOf(
                1.069028513720239e-06,
                0.0,
                -8.552228109761908e-06,
                0.0,
                2.993279838416667e-05,
                0.0,
                -5.986559676833335e-05,
                0.0,
                7.483199596041669e-05,
                0.0,
                -5.986559676833335e-05,
                0.0,
                2.993279838416667e-05,
                0.0,
                -8.552228109761908e-06,
                0.0,
                1.069028513720239e-06
            ),
            a = doubleArrayOf(
                1.0,
                -1.170317263386932e+01,
                6.600450414808721e+01,
                -2.377258186478554e+02,
                6.112487040089698e+02,
                -1.188687028105983e+03,
                1.807395796514192e+03,
                -2.190885757166393e+03,
                2.139186392124695e+03,
                -1.687906646389015e+03,
                1.072780326253592e+03,
                -5.435728864638604e+02,
                2.153556271431851e+02,
                -6.453600764414431e+01,
                1.380925925513872e+01,
                -1.887687673492426e+00,
                1.244344697721483e-01
            )
        )
    )
}
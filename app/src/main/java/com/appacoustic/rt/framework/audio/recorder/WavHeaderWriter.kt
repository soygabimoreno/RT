package com.appacoustic.rt.framework.audio.recorder

import java.io.FileOutputStream

class WavHeaderWriter {

    operator fun invoke(
        fos: FileOutputStream,
        totalAudioLength: Long,
        totalDataLength: Long,
        sampleRate: Int,
        nChannels: Int,
        byteRate: Int,
        bitPerSample: Int
    ) {
        val header = ByteArray(44)
        header[0] = 'R'.toByte()
        header[1] = 'I'.toByte()
        header[2] = 'F'.toByte()
        header[3] = 'F'.toByte()
        header[4] = (totalDataLength and 0xff).toByte()
        header[5] = (totalDataLength shr 8 and 0xff).toByte()
        header[6] = (totalDataLength shr 16 and 0xff).toByte()
        header[7] = (totalDataLength shr 24 and 0xff).toByte()
        header[8] = 'W'.toByte()
        header[9] = 'A'.toByte()
        header[10] = 'V'.toByte()
        header[11] = 'E'.toByte()
        header[12] = 'f'.toByte()
        header[13] = 'm'.toByte()
        header[14] = 't'.toByte()
        header[15] = ' '.toByte()
        header[16] = 16
        header[17] = 0
        header[18] = 0
        header[19] = 0
        header[20] = 1
        header[21] = 0
        header[22] = nChannels.toByte()
        header[23] = 0
        header[24] = (sampleRate and 0xff).toByte()
        header[25] = (sampleRate shr 8 and 0xff).toByte()
        header[26] = (sampleRate shr 16 and 0xff).toByte()
        header[27] = (sampleRate shr 24 and 0xff).toByte()
        header[28] = (byteRate and 0xff).toByte()
        header[29] = (byteRate shr 8 and 0xff).toByte()
        header[30] = (byteRate shr 16 and 0xff).toByte()
        header[31] = (byteRate shr 24 and 0xff).toByte()
        header[32] = (2 * 16 / 8).toByte()
        header[33] = 0
        header[34] = bitPerSample.toByte()
        header[35] = 0
        header[36] = 'd'.toByte()
        header[37] = 'a'.toByte()
        header[38] = 't'.toByte()
        header[39] = 'a'.toByte()
        header[40] = (totalAudioLength and 0xff).toByte()
        header[41] = (totalAudioLength shr 8 and 0xff).toByte()
        header[42] = (totalAudioLength shr 16 and 0xff).toByte()
        header[43] = (totalAudioLength shr 24 and 0xff).toByte()
        fos.write(header, 0, 44)
    }
}

package com.appacoustic.rt.framework.extension

import android.media.AudioRecord

fun AudioRecord.isRecording() = recordingState == AudioRecord.RECORDSTATE_RECORDING
fun AudioRecord.isStopped() = recordingState == AudioRecord.RECORDSTATE_STOPPED
fun AudioRecord.isInitialized() = recordingState == AudioRecord.STATE_INITIALIZED
fun AudioRecord.isUninitialized() = recordingState == AudioRecord.STATE_UNINITIALIZED

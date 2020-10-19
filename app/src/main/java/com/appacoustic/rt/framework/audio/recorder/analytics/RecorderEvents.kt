package com.appacoustic.rt.framework.audio.recorder.analytics

import com.appacoustic.rt.data.analytics.AnalyticsEvent

private const val DATA_TEMP_PATH = "DATA_TEMP_PATH"
private const val DATA_BUFFER_SIZE = "DATA_BUFFER_SIZE"
private const val DATA_FILE_OUTPUT_STREAM_CREATED = "DATA_FILE_OUTPUT_STREAM_CREATED"
private const val DATA_AUDIO_RECORDED = "DATA_AUDIO_RECORDED"
private const val DATA_FILE_OUTPUT_STREAM_CLOSED = "DATA_FILE_OUTPUT_STREAM_CLOSED"

private const val TEMP_PATH = "TEMP_PATH"
private const val BUFFER_SIZE = "BUFFER_SIZE"

sealed class RecorderEvents(
    override val name: String,
    override val parameters: Map<String, Any> = mapOf()
) : AnalyticsEvent {

    class DataTempPath(tempPath: String) : RecorderEvents(
        DATA_TEMP_PATH,
        mapOf(
            TEMP_PATH to tempPath
        )
    )

    class DataBufferSize(bufferSize: Int) : RecorderEvents(
        DATA_BUFFER_SIZE,
        mapOf(
            BUFFER_SIZE to bufferSize
        )
    )

    object DataFileOutputStreamCreated : RecorderEvents(DATA_FILE_OUTPUT_STREAM_CREATED)
    object DataAudioRecorded : RecorderEvents(DATA_AUDIO_RECORDED)
    object DataFileOutputStreamClosed : RecorderEvents(DATA_FILE_OUTPUT_STREAM_CLOSED)
}

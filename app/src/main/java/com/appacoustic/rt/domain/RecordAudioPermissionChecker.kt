package com.appacoustic.rt.domain

import android.Manifest
import arrow.core.Either
import com.appacoustic.rt.domainRecordAudioPermissionChecker.PermissionRequester

class RecordAudioPermissionChecker(
    private val permissionRequester: PermissionRequester
) {

    suspend operator fun invoke(): Either<Throwable, PermissionRequester.PermissionState> =
        permissionRequester(Manifest.permission.RECORD_AUDIO)
}

package com.appacoustic.rt.domain

import android.content.Context
import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.BasePermissionListener
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class PermissionRequester(private val context: Context) {

    enum class PermissionState {
        GRANTED,
        DENIED,
        SHOW_RATIONALE,
        SHOW_APP_SETTINGS
    }

    class PermissionRequestException : Exception("An exception happens requesting the permission.")

    suspend operator fun invoke(permission: String) =
        suspendCoroutine<Either<Throwable, PermissionState>> { continuation ->
            Dexter
                .withContext(context)
                .withPermission(permission)
                .withListener(object : BasePermissionListener() {
                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                        continuation.resume(PermissionState.GRANTED.right())
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                        continuation.resume(PermissionState.DENIED.right())
                    }

                    override fun onPermissionRationaleShouldBeShown(request: PermissionRequest?, token: PermissionToken?) {
                        continuation.resume(PermissionState.SHOW_RATIONALE.right())

                        // TODO: SHOW_APP_SETTINGS
//                    onPermissionRequested(PermissionState.SHOW_APP_SETTINGS.right())
                    }
                })
                .withErrorListener {
                    continuation.resume(PermissionRequestException().left())
                }
                .check()
        }
}

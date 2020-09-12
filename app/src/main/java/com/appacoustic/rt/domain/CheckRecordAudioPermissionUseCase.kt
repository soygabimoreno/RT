package com.appacoustic.rt.domain

import arrow.core.Either
import arrow.core.right

class CheckRecordAudioPermissionUseCase {

    operator fun invoke(): Either<Throwable, Boolean> {
        return true.right() // TODO
    }
}

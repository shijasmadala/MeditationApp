package com.shas.meditationapp.core.presentation

import com.shas.meditationapp.core.domain.DataError


fun DataError.toUiText(): String {
    val stringRes = when(this) {
        DataError.Local.DISK_FULL -> "error_disk_full"
        DataError.Local.UNKNOWN -> "error_unknown"
        DataError.Remote.REQUEST_TIMEOUT -> "error_request_timeout"
        DataError.Remote.TOO_MANY_REQUESTS -> "error_too_many_requests"
        DataError.Remote.NO_INTERNET -> "error_no_internet"
        DataError.Remote.SERVER -> "error_unknown"
        DataError.Remote.SERIALIZATION -> "error_serialization"
        DataError.Remote.UNKNOWN -> "error_unknown"
    }
    
    return stringRes
}
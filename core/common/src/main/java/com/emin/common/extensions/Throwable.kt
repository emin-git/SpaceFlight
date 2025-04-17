package com.emin.common.extensions

import com.emin.common.utils.OneTimeEvent

fun Throwable.asOneTimeEvent(): OneTimeEvent<Throwable?> {
    return OneTimeEvent(this)
}
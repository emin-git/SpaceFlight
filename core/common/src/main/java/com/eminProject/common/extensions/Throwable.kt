package com.eminProject.common.extensions

import com.eminProject.common.utils.OneTimeEvent

fun Throwable.asOneTimeEvent(): OneTimeEvent<Throwable?> {
    return OneTimeEvent(this)
}
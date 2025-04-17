package com.emin.common.utils

import java.util.concurrent.atomic.AtomicBoolean

class OneTimeEvent<T>(private val content: T) {
    private var hasBeenHandled = AtomicBoolean(false)
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled.compareAndSet(false, true)) content else null
    }
    fun peekContent(): T = content
}

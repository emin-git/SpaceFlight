package com.emin.ui

import com.emin.common.utils.OneTimeEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class UiState<T : Any>(
    val data: T?,
    val loading: Boolean = false,
    val error: OneTimeEvent<Throwable?> = OneTimeEvent(null),
    val warning: OneTimeEvent<String>? = null
)

inline fun <T : Any> MutableStateFlow<UiState<T>>.updateState(update: T.() -> T) {
    update { UiState(it.data?.let { it1 -> update(it1) }) }
}

inline fun <reified T : Any> MutableStateFlow<UiState<T>>.updateStateWith(
    scope: CoroutineScope,
    crossinline operation: suspend T.() -> Result<T>,
) {
    if (value.loading) return
    scope.launch {
        update { it.copy(loading = true, error = OneTimeEvent(null)) }

        val result = value.data?.operation()

        if (result?.isSuccess == true) {
            val data = result.getOrNull()
            if (data != null) {
                update { it.copy(data = data, loading = false) }
            } else {
                update {
                    it.copy(
                        loading = false,
                        error = OneTimeEvent(
                            IllegalStateException("Operation succeeded but returned no data"),
                        ),
                    )
                }
            }
        } else {
            update {
                it.copy(
                    error = OneTimeEvent(result?.exceptionOrNull()),
                    loading = false,
                )
            }
        }
    }
}

inline fun <T : Any> MutableStateFlow<UiState<T>>.updateWith(
    scope: CoroutineScope,
    crossinline operation: suspend T.() -> Result<Unit>,
) {
    if (value.loading) return
    scope.launch {
        update { it.copy(loading = true, error = OneTimeEvent(null)) }

        val result = value.data?.operation()

        if (result?.isSuccess == true) {
            update { it.copy(loading = false) }
        } else {
            update {
                it.copy(
                    error = OneTimeEvent(result?.exceptionOrNull()),
                    loading = false,
                )
            }
        }
    }
}
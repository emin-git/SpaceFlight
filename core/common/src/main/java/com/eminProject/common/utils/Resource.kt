package com.eminProject.common.utils

sealed class Resource<T> {
    class Loading<T> : Resource<T>()
    data class Success<T>(val data: T) : Resource<T>()
    data class Error<T>(val error: Throwable) : Resource<T>()
    data class Warning<T>(val data: T, val message: String) : Resource<T>()
}

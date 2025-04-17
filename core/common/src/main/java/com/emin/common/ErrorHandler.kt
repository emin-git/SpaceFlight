package com.emin.common

sealed class AppError(val message: String) {
    object NetworkError : AppError("İnternet bağlantınızı kontrol edin.")
    object UnknownError : AppError("Bilinmeyen bir hata oluştu.")
}
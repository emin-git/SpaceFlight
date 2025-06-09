package com.eminProject.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class RetryInterceptor(private val maxRetryCount: Int) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var attempt = 0
        var response: Response? = null
        var request = chain.request()

        while (attempt < maxRetryCount) {
            try {
                response = chain.proceed(request)

                if (response.isSuccessful) {
                    return response
                }

                if (response.code in 500..599) {
                    attempt++
                    continue
                } else {
                    return response
                }

            } catch (e: IOException) {
                attempt++
                if (attempt == maxRetryCount) {
                    throw e
                }
            }
        }

        return response ?: throw IOException("Bilinmeyen hata")
    }
}

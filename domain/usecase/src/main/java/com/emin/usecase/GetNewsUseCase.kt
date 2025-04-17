package com.emin.usecase

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresExtension
import com.emin.common.utils.Resource
import com.emin.database.EncryptedCacheManager
import com.emin.model.news.Result
import com.emin.model.news.dto.toNewsList
import com.emin.repository.NewsDataSource
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetNewsUseCase @Inject constructor(
    private val newsDataSource: NewsDataSource,
    private val encryptedCacheManager: EncryptedCacheManager,
    @ApplicationContext private val context: Context
) {
    @SuppressLint("SupportAnnotationUsage")
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun executeGetNews(
        limit: Int?,
        offset: Int?,
        isFeatured: Boolean?,
        published_at_gte: String?,
        published_at_lte: String?,
        ordering: List<String>?,
        search: String?
    ): Flow<Resource<List<Result>>> = flow {

        emit(Resource.Loading())

        val isInternetAvailable = isInternetAvailable()

        try {
            if (!isInternetAvailable) {
                val cachedNews = encryptedCacheManager.read() ?: emptyList()
                if (cachedNews.isEmpty()) {
                    emit(Resource.Error(Exception("No internet and no cached data")))
                } else {
                    emit(
                        Resource.Warning(
                            cachedNews,
                            "No internet connection. Showing cached data."
                        )
                    )
                }
                return@flow
            }

            val newsDto = newsDataSource.getNews(
                limit = limit,
                offset = offset,
                is_featured = isFeatured,
                published_at_gte = published_at_gte,
                published_at_lte = published_at_lte,
                ordering = ordering,
                search = search
            )
            val newsList = newsDto.toNewsList() ?: emptyList()

            if (newsList.isNotEmpty()) {
                encryptedCacheManager.save(newsList)
                emit(Resource.Success(newsList))
            } else {
                emit(Resource.Error(Exception("No news found")))
            }
        } catch (e: Exception) {
            val cachedNews = encryptedCacheManager.read() ?: emptyList()
            if (cachedNews.isEmpty()) {
                emit(Resource.Error(error = e))
            } else {
                emit(Resource.Warning(cachedNews, "Error occurred. Showing cached data."))
            }
        }
    }.flowOn(Dispatchers.IO)

    private fun isInternetAvailable(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
                ?: return false

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo
            networkInfo != null && networkInfo.isConnected
        }
    }

}

package com.emin.usecase.di

import android.content.Context
import com.emin.database.EncryptedCacheManager
import com.emin.repository.NewsDataSource
import com.emin.usecase.GetNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetNewsUseCase(
        newsDataSource: NewsDataSource,
        encryptedCacheManager: EncryptedCacheManager,
        @ApplicationContext context: Context
    ): GetNewsUseCase {
        return GetNewsUseCase(newsDataSource, encryptedCacheManager, context)
    }
}


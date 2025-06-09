package com.eminProject.usecase.di

import android.content.Context
import com.eminProject.database.EncryptedCacheManager
import com.eminProject.repository.NewsDataSource
import com.eminProject.usecase.GetNewsUseCase
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


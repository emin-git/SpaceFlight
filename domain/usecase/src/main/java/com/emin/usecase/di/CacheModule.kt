package com.emin.usecase.di

import android.content.Context
import com.emin.database.EncryptedCacheManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CacheModule {

    @Provides
    fun provideEncryptedCacheManager(@ApplicationContext context: Context): EncryptedCacheManager {
        return EncryptedCacheManager(context)
    }
}


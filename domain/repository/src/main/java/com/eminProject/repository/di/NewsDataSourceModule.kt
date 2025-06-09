package com.eminProject.repository.di

import com.eminProject.repository.NewsDataSource
import com.eminProject.repository.NewsDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class NewsDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindNewsDataSource(
        newsDataSourceImpl: NewsDataSourceImpl
    ): NewsDataSource
}


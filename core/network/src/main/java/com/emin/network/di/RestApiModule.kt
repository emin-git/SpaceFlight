package com.emin.network.di

import com.emin.network.api.ApiService
import com.emin.network.di.retrofit.RetrofitModule
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(
    includes = [
        RetrofitModule::class,
    ],
)
@InstallIn(SingletonComponent::class)
object RestApiModule {

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }

}
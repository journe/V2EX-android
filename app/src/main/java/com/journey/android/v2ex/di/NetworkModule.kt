package com.journey.android.v2ex.di

import com.journey.android.v2ex.net.RetrofitService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

  @Singleton
  @Provides
  fun provideUnsplashService(): RetrofitService {
    return RetrofitService.create()
  }
}
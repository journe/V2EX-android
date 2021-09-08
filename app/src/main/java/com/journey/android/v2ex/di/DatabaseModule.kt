package com.journey.android.v2ex.di

import android.content.Context
import com.journey.android.v2ex.room.AppDatabase
import com.journey.android.v2ex.room.dao.TopicListDao
import com.journey.android.v2ex.room.dao.TopicShowDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

  @Singleton
  @Provides
  fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
    return AppDatabase.getInstance(context)
  }

  @Provides
  fun provideTopicListDao(appDatabase: AppDatabase): TopicListDao {
    return appDatabase.topicListDao()
  }

  @Provides
  fun provideTopicShowDao(appDatabase: AppDatabase): TopicShowDao {
    return appDatabase.topicShowDao()
  }

}

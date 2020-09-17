package com.journey.android.v2ex.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.journey.android.v2ex.model.api.*
import com.journey.android.v2ex.room.dao.*
import com.journey.android.v2ex.utils.Utils

/**
 * Created by journey on 2020/5/18.
 */
@Database(
    entities = [TopicsListItemBean::class, TopicsShowBean::class, RepliesShowBean::class, MemberBean::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
  abstract fun meDao(): MeDao
  abstract fun userInfoDao(): UserInfoDao
  abstract fun topicListDao(): TopicListDao
  abstract fun topicDetailDao(): TopicDetailDao
  abstract fun topicRepliesDao(): TopicRepliesDao

  companion object {
    private const val DATABASE_NAME: String = "v2ex.db"

    // For Singleton instantiation
    @Volatile
    private var instance: AppDatabase? = null
    fun getInstance(context: Context = Utils.getContext()): AppDatabase {
      return instance ?: synchronized(this) {
        instance
            ?: buildDatabase(
                context
            )
                .also { instance = it }
      }
    }

    // Create and pre-populate the database. See this article for more details:
    // https://medium.com/google-developers/7-pro-tips-for-room-fbadea4bfbd1#4785
    private fun buildDatabase(context: Context): AppDatabase {
      return Room.databaseBuilder(
          context, AppDatabase::class.java,
          DATABASE_NAME
      )
          .fallbackToDestructiveMigration()
          .allowMainThreadQueries()
//          .addCallback(object : RoomDatabase.Callback() {
//            override fun onCreate(db: SupportSQLiteDatabase) {
//              super.onCreate(db)
//              val request = OneTimeWorkRequestBuilder<SeedDatabaseWorker>().build()
//              WorkManager.getInstance(context)
//                  .enqueue(request)
//            }
//          })
          .build()
    }
  }
}

package com.journey.android.v2ex.module.topic.list

import com.journey.android.v2ex.model.api.TopicsListItemBean
import com.journey.android.v2ex.net.RetrofitService
import com.journey.android.v2ex.room.AppDatabase
import com.journey.android.v2ex.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.invoke
import javax.inject.Inject

/**
 * Created by journey on 2020/5/19.
 *
 * Repository implementation that uses a database PagedList + a boundary callback to return a
 * listing that loads in pages.
 *
 */
class TopicListRepository @Inject constructor(
  private val db: AppDatabase,
  private val apiService: RetrofitService
) {

  /**
   * Inserts the response into the database while also assigning position indices to items.
   */
  private suspend fun insertResultIntoDb(
    tabName: String,
    body: List<TopicsListItemBean>
  ) = Dispatchers.IO {
    db.runInTransaction {
      val start = db.topicListDao()
          .getNextIndex()
      val items = body.mapIndexed { index, child ->
        child.indexInResponse = start + index
        child.tab = tabName
        child
      }
      db.topicListDao()
          .insert(items)
    }
  }

  /**
   * When refresh is called, we simply run a fresh network request and when it arrives, clear
   * the database table and insert all new items in a transaction.
   * <p>
   * Since the PagedList already uses a database bound data source, it will automatically be
   * updated after the database transaction is finished.
   */
  suspend fun refresh(tabName: String) = Dispatchers.IO {
    val result = apiService.getTopicsByNodeSuspend(Constants.TAB + tabName)
//    if (result.rows?.isEmpty() == true) {
////            Logger.d(result.size)
//    } else {
//      db.runInTransaction {
//        db.topicListDao()
//            .deleteAll()
//        val start = db.topicListDao()
//            .getNextIndex()
//        val items = result.rows?.mapIndexed { index, child ->
//          child.indexInResponse = start + index
//          child
//        }
//        items?.let {
//          db.topicListDao()
//              .insert(items)
//        }
//      }
//    }
  }

}
package com.journey.android.v2ex.module.topic.list

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.journey.android.v2ex.model.api.TopicsListItemBean
import com.journey.android.v2ex.net.RetrofitRequest
import com.journey.android.v2ex.room.AppDatabase
import com.journey.android.v2ex.utils.Constants
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.invoke

/**
 * Created by journey on 2020/5/19.
 *
 * Repository implementation that uses a database PagedList + a boundary callback to return a
 * listing that loads in pages.
 *
 */
class TopicListRepository(
  private val db: AppDatabase,
  private val lifecycleScope: LifecycleCoroutineScope,
  private val tabName: String
) {

  /**
   * Inserts the response into the database while also assigning position indices to items.
   */
  private suspend fun insertResultIntoDb(body: List<TopicsListItemBean>) = Dispatchers.IO {
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
  suspend fun refresh() = Dispatchers.IO {
    val result = RetrofitRequest.apiService.getTopicsByNodeSuspend(Constants.TAB + tabName)
    Logger.d(result.toString())
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

  @MainThread
  fun getFeeds(pageSize: Int): LiveData<PagedList<TopicsListItemBean>> {
    val boundaryCallback = TopicListBoundaryCallback(
        insertToDb = this::insertResultIntoDb,
        viewModelScope = lifecycleScope,
        tabName = tabName
    )
    return db.topicListDao()
        .dataSource(tabName)
        .toLiveData(
            pageSize = pageSize,
            boundaryCallback = boundaryCallback
        )
//        return Listing(
//            pagedList = livePagedList,
//            networkState = boundaryCallback.networkState,
//            retry = {
//                boundaryCallback.helper.retryAllFailed()
//            },
//            refresh = {
//                refreshTrigger.value = null
//            },
//            refreshState = refreshState
//        )
  }
}
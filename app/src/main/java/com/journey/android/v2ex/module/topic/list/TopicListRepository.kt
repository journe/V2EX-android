package com.journey.android.v2ex.module.topic.list

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.journey.android.v2ex.model.api.TopicsListItemBean
import com.journey.android.v2ex.net.RetrofitService
import com.journey.android.v2ex.net.parser.TopicListParser
import com.journey.android.v2ex.room.AppDatabase
import com.journey.android.v2ex.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.invoke
import org.jsoup.Jsoup
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by journey on 2020/5/19.
 *
 * Repository implementation that uses a database PagedList + a boundary callback to return a
 * listing that loads in pages.
 *
 */
@Singleton
class TopicListRepository @Inject constructor(
  private val db: AppDatabase,
  private val apiService: RetrofitService
) {

  suspend fun requsetData(tabName: String) {
    if (db.topicListDao().topicCount(tabName) > 0) {
//      getList(tabName)
    } else {
      insertResultIntoDb(request(tabName))
    }
  }

  fun getList(tabName: String) =
    Pager(PagingConfig(pageSize = 20)) {
      db.topicListDao().pagingSource(tabName)
    }.flow

  suspend fun request(nodeName: String): List<TopicsListItemBean> {
    val result = apiService.getTopicsByNodeSuspend(Constants.TAB + nodeName)
    val listItemBean = TopicListParser.parseTopicList(
      Jsoup.parse(result.string())
    ).map {
      it.apply { tab = nodeName }
    }

    return listItemBean
  }

  /**
   * Inserts the response into the database while also assigning position indices to items.
   */
  private suspend fun insertResultIntoDb(
    body: List<TopicsListItemBean>
  ) = Dispatchers.IO {
    db.runInTransaction {
      val start = db.topicListDao().getNextIndex()
      val items = body.mapIndexed { index, child ->
        child.indexInResponse = start + index
        child
      }
      db.topicListDao().insert(items)
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
    val result = request(tabName)
    if (result.isEmpty()) {
//            Logger.d(result.size)
    } else {
      db.runInTransaction {
        db.topicListDao().deleteAll()
        val start = db.topicListDao().getNextIndex()
        val items = result.mapIndexed { index, child ->
          child.indexInResponse = start + index
          child
        }
        items?.let {
          db.topicListDao().insert(items)
        }
      }
    }
  }

}
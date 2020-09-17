package com.journey.android.v2ex.module.topic.detail

import androidx.annotation.MainThread
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.journey.android.v2ex.model.api.RepliesShowBean
import com.journey.android.v2ex.model.api.TopicsListItemBean
import com.journey.android.v2ex.model.api.TopicsShowBean
import com.journey.android.v2ex.net.RetrofitRequest
import com.journey.android.v2ex.net.parser.TopicDetailParser
import com.journey.android.v2ex.room.AppDatabase
import com.journey.android.v2ex.utils.Constants
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.invoke
import org.jsoup.Jsoup

/**
 * Created by journey on 2020/5/19.
 *
 * Repository implementation that uses a database PagedList + a boundary callback to return a
 * listing that loads in pages.
 *
 */
class TopicDetailRepository(
  private val db: AppDatabase,
  private val topicId: Int
) {

  /**
   * Inserts the response into the database while also assigning position indices to items.
   */
  private suspend fun insertResultIntoDb(body: List<RepliesShowBean>) =
    Dispatchers.IO {
      db.runInTransaction {
        db.topicRepliesDao()
            .insert(body)
      }
    }

  /**
   * When refresh is called, we simply run a fresh network request and when it arrives, clear
   * the database table and insert all new items in a transaction.
   * <p>
   * Since the PagedList already uses a database bound data source, it will automatically be
   * updated after the database transaction is finished.
   */
//  suspend fun refresh() = Dispatchers.IO {
//    val result = RetrofitRequest.apiService.getTopicsByNodeSuspend(Constants.TAB + tabName)
//  }

  @MainThread
  fun getComments(pageSize: Int): LiveData<PagedList<RepliesShowBean>> {
    val boundaryCallback = TopicCommentsBoundaryCallback(
        insertToDb = this::insertResultIntoDb,
        topicId = topicId
    )
    return db.topicRepliesDao()
        .getTopicReplies(topicId)
        .toLiveData(
            pageSize = pageSize,
            boundaryCallback = boundaryCallback
        )
  }

  suspend fun getTopicDetail(): TopicsShowBean {
    val localTopic = db.topicDetailDao()
        .getTopicById(topicId = topicId)
    if (localTopic != null) {
      return localTopic
    } else {
      val response = RetrofitRequest.apiService.getTopicByIdSuspend(topicId)
      val doc = Jsoup.parse(response.string())
      val topicDetailBean = TopicDetailParser.parseTopicDetail(doc)
      topicDetailBean.id = topicId
      topicDetailBean.subtles?.forEach {
        it.id = topicId
      }
      val replies = TopicDetailParser.parseComments(doc)
      replies.forEach {
        it.topic_id = topicId
      }
      db.runInTransaction {
        db.topicRepliesDao()
            .insert(replies)
        db.topicDetailDao()
            .insert(topicDetailBean)
      }
      return topicDetailBean
    }
  }
}
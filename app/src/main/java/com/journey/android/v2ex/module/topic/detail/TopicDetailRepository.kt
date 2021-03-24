package com.journey.android.v2ex.module.topic.detail

import androidx.annotation.MainThread
import androidx.paging.PagingSource
import com.journey.android.v2ex.model.api.RepliesShowBean
import com.journey.android.v2ex.model.api.TopicsShowBean
import com.journey.android.v2ex.model.jsoup.TopicDetailBean
import com.journey.android.v2ex.net.RetrofitService
import com.journey.android.v2ex.net.parser.TopicDetailParser
import com.journey.android.v2ex.room.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.invoke
import org.jsoup.Jsoup
import javax.inject.Inject

/**
 * Created by journey on 2020/5/19.
 *
 * Repository implementation that uses a database PagedList + a boundary callback to return a
 * listing that loads in pages.
 *
 */
class TopicDetailRepository {

  @Inject
  lateinit var apiService: RetrofitService
  @Inject
  lateinit var db: AppDatabase

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

//  @MainThread
//  fun getComments(
//    pageSize: Int,
//    topicId: Int
//  ): PagingSource<Int, RepliesShowBean> {
//    return db.topicRepliesDao()
//        .getTopicReplies(topicId)
//  }

  suspend fun initTopicDetail(topicId: Int) {
    val localBean = db.topicDetailDao()
        .getTopicById(topicId)
    if (localBean == null) {
      val docString = apiService.getTopicByIdSuspend(topicId)
          .string()
      val doc = Jsoup.parse(docString)
      val topicsShowBean = TopicDetailParser.parseTopicDetail(doc)
      val replies = TopicDetailParser.parseComments(doc)

      topicsShowBean.id = topicId
      topicsShowBean.subtles?.forEach {
        it.id = topicId
      }
      replies.forEach {
        it.topic_id = topicId
      }
      db.topicDetailDao()
          .insert(TopicDetailBean(topicId, docString))
      db.topicRepliesDao()
          .insert(replies)
      db.topicShowDao()
          .insert(topicsShowBean)
    }
  }

  fun getTopicsShowBean(topicId: Int): Flow<TopicsShowBean> {
    return db.topicShowDao()
        .getTopicById(topicId = topicId)
  }
}
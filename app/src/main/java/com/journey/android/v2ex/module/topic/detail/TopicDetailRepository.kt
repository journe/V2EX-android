package com.journey.android.v2ex.module.topic.detail

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.withTransaction
import com.journey.android.v2ex.base.BaseRepository
import com.journey.android.v2ex.libs.extension.launchCoroutine
import com.journey.android.v2ex.model.api.RepliesShowBean
import com.journey.android.v2ex.model.api.TopicsShowBean
import com.journey.android.v2ex.net.RetrofitService
import com.journey.android.v2ex.net.parser.TopicDetailParser
import com.journey.android.v2ex.room.AppDatabase
import com.journey.android.v2ex.utils.Constants
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import org.jsoup.Jsoup
import java.util.*
import javax.inject.Inject

/**
 * Created by journey on 2020/5/19.
 *
 * Repository implementation that uses a database PagedList + a boundary callback to return a
 * listing that loads in pages.
 *
 */
class TopicDetailRepository @Inject constructor(
    private val db: AppDatabase,
    private val apiService: RetrofitService
) : BaseRepository() {

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

    suspend fun asd(): String {
        return apiService.getTopicByIdSuspend(1).string()
    }

    suspend fun initTopicDetail(topicId: Int) {
//    val localBean = db.topicDetailDao().getTopicById(topicId)
//    if (localBean == null) {

        launchCoroutine(context = Dispatchers.IO) {
            val a = it.async { apiService.getTopicByIdSuspend(topicId).string() }
            val apiBean = it.async { apiService.getTopicsById(topicId).first() }
        }

        GlobalScope.launch {
            val a = async { apiService.getTopicByIdSuspend(topicId).string() }
            val apiBean = async { apiService.getTopicsById(topicId).first() }
        }
        val docString = apiService.getTopicByIdSuspend(topicId).string()
        val apiBean = apiService.getTopicsById(topicId).first()
        val doc = Jsoup.parse(docString)
        val jsoupBean = TopicDetailParser.parseTopicDetail(doc)
        val replies = TopicDetailParser.parseComments(doc)

        jsoupBean.id = topicId
        jsoupBean.url = Constants.BASE_URL + "/t/$topicId"
        jsoupBean.subtles?.forEach { it.id = topicId }
        replies.forEach { it.topic_id = topicId }


        jsoupBean.created = apiBean.created
        jsoupBean.last_modified = apiBean.last_modified
        jsoupBean.last_touched = apiBean.last_touched

        jsoupBean.local_touched = Date().time

        db.withTransaction {
            val topicListItem = db.topicListDao().getTopicById(topicId)
            topicListItem.replies = jsoupBean.replies
            db.topicListDao().update(topicListItem)
//        db.topicDetailDao().insert(TopicDetailBean(topicId, docString))
            db.topicShowDao().insert(jsoupBean)
            db.topicRepliesDao().insert(replies)
        }
//    }
    }

    @OptIn(ExperimentalPagingApi::class)
    fun getReplyBeanPagerFlow(topicId: Int, pageSize: Int) = Pager(
        config = PagingConfig(pageSize),
        remoteMediator = PageKeyedRemoteMediator(db, apiService, topicId)
    ) {
        db.topicRepliesDao().pagingSource(topicId)
    }.flow

    fun getTopicsShowBean(topicId: Int): Flow<TopicsShowBean> {
        return db.topicShowDao().getTopicById(topicId = topicId)
    }
}
package com.journey.android.v2ex.module.topic.detail

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.LoadType.APPEND
import androidx.paging.LoadType.PREPEND
import androidx.paging.LoadType.REFRESH
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.journey.android.v2ex.model.api.RepliesShowBean
import com.journey.android.v2ex.net.RetrofitService
import com.journey.android.v2ex.net.parser.TopicDetailParser
import com.journey.android.v2ex.room.AppDatabase
import com.journey.android.v2ex.room.dao.TopicRepliesDao
import org.jsoup.Jsoup
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class PageKeyedRemoteMediator(
  private val db: AppDatabase,
  private val apiService: RetrofitService,
  private val topicId: Int
) : RemoteMediator<Int, RepliesShowBean>() {
  private val repliesDao: TopicRepliesDao = db.topicRepliesDao()
  private var page = 2

  override suspend fun initialize(): InitializeAction {
    // Require that remote REFRESH is launched on initial load and succeeds before launching
    // remote PREPEND / APPEND.
    return InitializeAction.LAUNCH_INITIAL_REFRESH
  }

  override suspend fun load(
    loadType: LoadType,
    state: PagingState<Int, RepliesShowBean>
  ): MediatorResult {
    try {
      // Get the closest item from PagingState that we want to load data around.
      val loadKey = when (loadType) {
        REFRESH -> page
        PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
        APPEND -> {
          state.lastItemOrNull() ?: return MediatorResult.Success(endOfPaginationReached = true)
          ++page
        }
      }

      val docString = apiService.getTopicByIdSuspend(topicId, page = loadKey).string()
      val doc = Jsoup.parse(docString)
      val replies = TopicDetailParser.parseComments(doc)
      replies.forEach { it.topic_id = topicId }

      db.withTransaction {
        if (loadType == REFRESH) {
          page = 1
        }
        repliesDao.insertListSuspend(replies)
      }

      return MediatorResult.Success(endOfPaginationReached = replies.size < 100)
    } catch (e: IOException) {
      return MediatorResult.Error(e)
    } catch (e: HttpException) {
      return MediatorResult.Error(e)
    }
  }
}

package com.journey.android.v2ex.module.topic.detail

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.journey.android.v2ex.model.api.RepliesShowBean
import com.journey.android.v2ex.room.AppDatabase

/**
 * Created by journey on 3/24/21.
 */

class TopicDetailReplyDataSource(
  private val db: AppDatabase,
  private val topicId: Int
) : PagingSource<Int, RepliesShowBean>() {

  override suspend fun load(
    params: LoadParams<Int>
  ): LoadResult<Int, RepliesShowBean> {
    try {
      // Start refresh at page 1 if undefined.
      val nextPageNumber = params.key ?: 1
      val result = db.topicRepliesDao()
          .getTopicRepliesSuspend(topicId)

      return LoadResult.Page(
          data = result,
          prevKey = null, // Only paging forward.
          nextKey = nextPageNumber
      )
    } catch (e: Exception) {
      // Handle errors in this block and return LoadResult.Error if it is an
      // expected error (such as a network failure).
      return LoadResult.Error(e)
    }
  }

  override fun getRefreshKey(state: PagingState<Int, RepliesShowBean>): Int? {
    return 0
  }
}
package com.journey.android.v2ex.module.topic.list

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.journey.android.v2ex.model.api.TopicsListItemBean
import com.journey.android.v2ex.net.RetrofitService
import com.journey.android.v2ex.net.parser.TopicListParser
import com.journey.android.v2ex.room.AppDatabase
import com.journey.android.v2ex.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.invoke
import org.jsoup.Jsoup
import javax.inject.Inject

/**
 * Created by journey on 3/24/21.
 */

class TopicListDataSource(private val tabName: String) : PagingSource<Int, TopicsListItemBean>() {

  @Inject
  lateinit var db: AppDatabase

  @Inject
  lateinit var apiService: RetrofitService

  override suspend fun load(
    params: LoadParams<Int>
  ): LoadResult<Int, TopicsListItemBean> {
    try {
      // Start refresh at page 1 if undefined.
      val nextPageNumber = params.key ?: 1

      val result = apiService.requestSuspend(Constants.TAB + tabName)
      val listItemBean = TopicListParser.parseTopicList(
          Jsoup.parse(result.string())
      )
      insertToDb(listItemBean.map { it.apply { tab = nodeName } })

      return LoadResult.Page(
          data = listItemBean,
          prevKey = null, // Only paging forward.
          nextKey = nextPageNumber
      )
    } catch (e: Exception) {
      // Handle errors in this block and return LoadResult.Error if it is an
      // expected error (such as a network failure).
      return LoadResult.Error(e)
    }
  }

  override fun getRefreshKey(state: PagingState<Int, TopicsListItemBean>): Int? {
    return 0
  }

  private suspend fun insertToDb(body: List<TopicsListItemBean>) = Dispatchers.IO {
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
}
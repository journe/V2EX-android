package com.journey.android.v2ex.module.topic.list

import androidx.paging.PagedList
import androidx.annotation.MainThread
import com.journey.android.v2ex.model.api.TopicsListItemBean
import com.journey.android.v2ex.net.RetrofitRequest
import com.journey.android.v2ex.net.parser.TopicListParser
import com.journey.android.v2ex.utils.Constants
import com.journey.android.v2ex.libs.extension.launchThrowException
import kotlinx.coroutines.CoroutineScope
import org.jsoup.Jsoup
import kotlin.reflect.KSuspendFunction1

/**
 * This boundary callback gets notified when user reaches to the edges of the list such that the
 * database cannot provide any more data.
 * <p>
 * The boundary callback might be called multiple times for the same direction so it does its own
 * rate limiting using the PagingRequestHelper class.
 */
class TopicListBoundaryCallback(
  private val insertToDb: KSuspendFunction1<List<TopicsListItemBean>, Unit>,
  private val viewModelScope: CoroutineScope,
  private val tabName: String
) : PagedList.BoundaryCallback<TopicsListItemBean>() {
  /**
   * Database returned 0 items. We should query the backend for more items.
   */
  @MainThread
  override fun onZeroItemsLoaded() {
//    Logger.d("onZeroItemsLoaded")
    requestFeeds()
  }

  /**
   * User reached to the end of the list.
   */
  @MainThread
  override fun onItemAtEndLoaded(itemAtEnd: TopicsListItemBean) {
//    Logger.d("onItemAtEndLoaded")
//        requestFeeds()
  }

  private fun requestFeeds(retry: Boolean = true) {
    viewModelScope.launchThrowException {
      val result = RetrofitRequest.apiService.getTopicsByNodeSuspend(Constants.TAB + tabName)
      val listItemBean = TopicListParser.parseTopicList(
          Jsoup.parse(result.string())
      )
      insertToDb(listItemBean.map { it.apply { tab = nodeName } })
    }
  }

  override fun onItemAtFrontLoaded(itemAtFront: TopicsListItemBean) {
    // ignored, since we only ever append to what's in the DB
  }

//    companion object {
//        var count = 0
//    }
}
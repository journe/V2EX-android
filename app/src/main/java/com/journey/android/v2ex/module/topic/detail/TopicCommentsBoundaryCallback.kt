package com.journey.android.v2ex.module.topic.detail

import androidx.paging.PagedList
import androidx.annotation.MainThread
import com.journey.android.v2ex.net.RetrofitRequest
import com.journey.android.v2ex.model.api.RepliesShowBean
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.reflect.KSuspendFunction1

/**
 * This boundary callback gets notified when user reaches to the edges of the list such that the
 * database cannot provide any more data.
 * <p>
 * The boundary callback might be called multiple times for the same direction so it does its own
 * rate limiting using the PagingRequestHelper class.
 */
class TopicCommentsBoundaryCallback(
  private val insertToDb: KSuspendFunction1<List<RepliesShowBean>, Unit>,
  private val topicId: Int
) : PagedList.BoundaryCallback<RepliesShowBean>() {

  private val uiDispatcher: CoroutineDispatcher = Dispatchers.Main
  private var job: Job = Job()
  private var page = 1

  /**
   * Database returned 0 items. We should query the backend for more items.
   */
  @MainThread
  override fun onZeroItemsLoaded() {
//    Logger.d("onZeroItemsLoaded")
    GlobalScope.launch(uiDispatcher + job) {
      requestComments()
    }
  }

  /**
   * User reached to the end of the list.
   */
  @MainThread
  override fun onItemAtEndLoaded(itemAtEnd: RepliesShowBean) {
//    Logger.d("onItemAtEndLoaded")
//    requestComments()
  }

  private suspend fun requestComments(retry: Boolean = true) {
    val replies = RetrofitRequest.apiService
        .getRepliesSuspend(topicId, page, 100)
    insertToDb(replies)
  }

  override fun onItemAtFrontLoaded(itemAtFront: RepliesShowBean) {
    // ignored, since we only ever append to what's in the DB
  }

}
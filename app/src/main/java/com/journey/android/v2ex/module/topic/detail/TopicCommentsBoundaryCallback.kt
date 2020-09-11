package com.journey.android.v2ex.module.topic.detail

import androidx.paging.PagedList
import androidx.annotation.MainThread
import com.journey.android.v2ex.model.api.TopicsListItemBean
import com.journey.android.v2ex.net.RetrofitRequest
import com.journey.android.v2ex.net.parser.TopicListParser
import com.journey.android.v2ex.utils.Constants
import com.journey.android.v2ex.libs.extension.launchThrowException
import com.journey.android.v2ex.model.api.RepliesShowBean
import com.journey.android.v2ex.net.parser.TopicDetailParser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
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
//        requestFeeds()
  }

  private suspend fun requestComments(retry: Boolean = true) {
    RetrofitRequest.apiService
        .getTopicById(topicId)
        .enqueue(object : Callback<ResponseBody> {
          override fun onFailure(
            call: Call<ResponseBody>,
            t: Throwable
          ) {
          }

          override fun onResponse(
            call: Call<ResponseBody>,
            response: Response<ResponseBody>
          ) {
            val doc = Jsoup.parse(
                response.body()!!
                    .string()
            )
            val topicDetailBean = TopicDetailParser.parseTopicDetail(doc)
            topicDetailBean.id = topicId
            topicDetailBean.subtles?.forEach {
              it.id = topicId
            }
//            topicDetailBean.save()
            val headView = addHeaderView(topicDetailBean)

            getReplyByNet(id, headView)
            //评论
//            getReplyByJsoup(doc, headView)
          }

        })
//    insertToDb(listItemBean.map { it.apply { tab = nodeName } })
  }

  override fun onItemAtFrontLoaded(itemAtFront: RepliesShowBean) {
    // ignored, since we only ever append to what's in the DB
  }

}
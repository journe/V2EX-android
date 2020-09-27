package com.journey.android.v2ex.net

import com.journey.android.v2ex.model.api.MemberBean
import com.journey.android.v2ex.model.api.NodeBean
import com.journey.android.v2ex.model.api.RepliesShowBean
import com.journey.android.v2ex.model.api.SiteInfoBean
import com.journey.android.v2ex.model.api.SiteStatsBean
import com.journey.android.v2ex.model.api.TopicsListItemBean
import com.journey.android.v2ex.model.api.TopicsShowBean
import com.journey.android.v2ex.utils.Constants
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Streaming
import retrofit2.http.Url

/**
 * Created by journey on 2017/12/29.
 */
interface RetrofitService {

  @GET(Constants.SITE_INFO)
  fun getSiteInfo(): Call<SiteInfoBean>

  @GET(Constants.SITE_STATS)
  fun getSiteStats(): Call<SiteStatsBean>

  @GET(Constants.NODES_ALL)
  fun getNodesAll(): Call<ArrayList<NodeBean>>

  @GET(Constants.NODES_SHOW)
  fun getNodesShow(@Query("id") id: Int): Call<NodeBean>

  @GET(Constants.TOPICS_HOT)
  fun listHotTopics(): Call<ArrayList<TopicsListItemBean>>

  @GET(Constants.TOPICS_LATEST)
  fun listLatestTopics(): Call<ArrayList<TopicsListItemBean>>

  @GET(Constants.TOPICS_SHOW)
  suspend fun getTopicsById(@Query("id") id: Int): List<TopicsShowBean>

  @GET(Constants.TOPICS_SHOW)
  fun getTopicsByUser(@Query("username") username: String): Call<ArrayList<TopicsShowBean>>

  @GET(Constants.TOPICS_SHOW)
  fun getTopicsByNode(@Query("node_id") nodeId: Int): Call<ArrayList<TopicsListItemBean>>

  @GET(Constants.REPLIES)
  suspend fun getRepliesSuspend(
    @Query("topic_id") id: Int,
    @Query("page") page: Int,
    @Query("page_size") pageSize: Int
  ): List<RepliesShowBean>

  @GET(Constants.MEMBERS)
  fun getMemberInfoByID(@Query("id") id: Int): Call<MemberBean>

  @GET(Constants.MEMBERS)
  fun getMemberInfo(@Query("username") name: String): Call<MemberBean>

  @GET(Constants.SIGNIN)
  fun getLogin(): Call<ResponseBody>

  @GET(Constants.SIGNIN)
  suspend fun getLoginSuspend(): ResponseBody

  @FormUrlEncoded
  @Headers("Referer: https://www.v2ex.com/signin")
  @POST(Constants.SIGNIN)
  fun postSignin(@FieldMap hashMap: HashMap<String, String>): Call<ResponseBody>

  @FormUrlEncoded
  @Headers("Referer: https://www.v2ex.com/signin")
  @POST(Constants.SIGNIN)
  suspend fun postSigninSuspend(@FieldMap hashMap: HashMap<String, String>): ResponseBody

  @Streaming
  @GET
  fun getCaptcha(@Url url: String): Call<ResponseBody>

  @Streaming
  @GET
  suspend fun getCaptchaSuspend(@Url url: String): ResponseBody

  @GET(Constants.MORE)
  fun getMore(): Call<ResponseBody>

  @GET(Constants.MORE)
  suspend fun getMoreSuspend(): ResponseBody

  @GET(Constants.BALANCE)
  fun getBalance(): Call<ResponseBody>

  @GET
  fun getTopicsByNode(@Url url: String): Call<ResponseBody>

  @GET
  suspend fun getTopicsByNodeSuspend(@Url url: String): ResponseBody

  @GET("/t/{id}")
  suspend fun getTopicByIdSuspend(
    @Path("id") id: Int,
    @Query("p") page: Int = 1
  ): ResponseBody

}
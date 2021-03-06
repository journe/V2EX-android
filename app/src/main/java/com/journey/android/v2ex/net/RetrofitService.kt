package com.journey.android.v2ex.net

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.journey.android.v2ex.V2exApplication
import com.journey.android.v2ex.bean.api.MemberBean
import com.journey.android.v2ex.bean.api.NodeBean
import com.journey.android.v2ex.bean.api.RepliesShowBean
import com.journey.android.v2ex.bean.api.SiteInfoBean
import com.journey.android.v2ex.bean.api.SiteStatsBean
import com.journey.android.v2ex.bean.api.TopicsListItemBean
import com.journey.android.v2ex.bean.api.TopicsShowBean
import com.journey.android.v2ex.utils.Constants
import io.realm.RealmList
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Streaming
import retrofit2.http.Url
import java.io.File
import java.util.concurrent.TimeUnit

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
  fun getTopicsById(@Query("id") id: Int): Call<ArrayList<TopicsShowBean>>

  @GET(Constants.TOPICS_SHOW)
  fun getTopicsByUser(@Query("username") username: String): Call<ArrayList<TopicsShowBean>>

  @GET(Constants.TOPICS_SHOW)
  fun getTopicsByNode(@Query("node_id") nodeId: Int): Call<ArrayList<TopicsListItemBean>>

  @GET(Constants.REPLIES)
  fun getReplies(
    @Query("topic_id") id: Int,
    @Query("page") page: Int,
    @Query("page_size") pageSize: Int
  ): Call<RealmList<RepliesShowBean>>

  @GET(Constants.MEMBERS)
  fun getMemberInfoByID(@Query("id") id: Int): Call<MemberBean>

  @GET(Constants.MEMBERS)
  fun getMemberInfo(@Query("username") name: String): Call<MemberBean>

  @GET(Constants.SIGNIN)
  fun getLogin(): Call<ResponseBody>

  @FormUrlEncoded
  @Headers("Referer: https://www.v2ex.com/signin")
  @POST(Constants.SIGNIN)
  fun postSignin(@FieldMap hashMap: HashMap<String, String>): Call<ResponseBody>

  @Streaming
  @GET
  fun getCaptcha(@Url url: String): Call<ResponseBody>

  @GET(Constants.MORE)
  fun getMore(): Call<ResponseBody>

  @GET(Constants.BALANCE)
  fun getBalance(): Call<ResponseBody>

  @GET
  fun getTopicsByNode(@Url url: String): Call<ResponseBody>

  @GET("/t/{id}")
  fun getTopicById(
    @Path("id") id: Int,
    @Query("p") page: Int
  ): Call<ResponseBody>

  @GET("/t/{id}")
  fun getTopicById(
    @Path("id") id: Int
  ): Call<ResponseBody>

}
package com.journey.android.v2ex.net

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.journey.android.v2ex.V2exApplication
import com.journey.android.v2ex.bean.api.MembersShowBean
import com.journey.android.v2ex.bean.api.NodesAllBean
import com.journey.android.v2ex.bean.api.NodesShowBean
import com.journey.android.v2ex.bean.api.RepliesShowBean
import com.journey.android.v2ex.bean.api.SiteInfoBean
import com.journey.android.v2ex.bean.api.SiteStatsBean
import com.journey.android.v2ex.bean.api.TopicsListItemBean
import com.journey.android.v2ex.bean.api.TopicsShowBean
import com.journey.android.v2ex.utils.Constants
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
import retrofit2.http.Query
import retrofit2.http.Streaming
import retrofit2.http.Url
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by journey on 2017/12/29.
 */
interface GetAPIService {

  companion object {
    private val cookieJar =
      PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(V2exApplication.instance))
    val okHttpClient = OkHttpClient.Builder()
        .apply {
          cache(buildCache())
          connectTimeout(10, TimeUnit.SECONDS)
          writeTimeout(10, TimeUnit.SECONDS)
          readTimeout(30, TimeUnit.SECONDS)
          followRedirects(false)
          cookieJar(cookieJar)
        }
        .addInterceptor { chain ->
          val request = chain.request()
              .newBuilder()
              .addHeader("User-Agent", Constants.USER_AGENT_ANDROID)
              .build()
          val response = chain.proceed(request)
          response
        }
        .build()

    private fun buildCache(): Cache? {
      val cacheDir = File(V2exApplication.instance.cacheDir, "webCache")
      val cacheSize = 16 * 1024 * 1024
      return Cache(cacheDir, cacheSize.toLong())
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(GetAPIService::class.java)
    fun getInstance(): GetAPIService {
      return service
    }
  }

  @GET(Constants.SITE_INFO)
  fun getSiteInfo(): Call<SiteInfoBean>

  @GET(Constants.SITE_STATS)
  fun getSiteStats(): Call<SiteStatsBean>

  @GET(Constants.NODES_ALL)
  fun getNodesAll(): Call<ArrayList<NodesAllBean>>

  @GET(Constants.NODES_SHOW)
  fun getNodesShow(@Query("id") id: Int): Call<NodesShowBean>

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
  ): Call<ArrayList<RepliesShowBean>>

  @GET(Constants.MEMBERS)
  fun getMemberInfo(@Query("id") id: Int): Call<MembersShowBean>

  @GET(Constants.SIGNIN)
  fun getLogin(): Call<ResponseBody>

  @FormUrlEncoded
  @Headers("Referer: https://www.v2ex.com/signin")
  @POST(Constants.SIGNIN)
  fun postLogin(@FieldMap hashMap: HashMap<String, String>): Call<ResponseBody>

  @Streaming
  @GET
  fun getCaptcha(@Url url: String): Call<ResponseBody>

  @GET(Constants.MORE)
  fun getMore(): Call<ResponseBody>

  //"https://www.v2ex.com/?tab="
  @GET
  fun getTopicsByNode(@Url url: String): Call<ResponseBody>

}
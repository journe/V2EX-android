package com.journey.android.v2ex.net

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.journey.android.v2ex.V2exApplication
import com.journey.android.v2ex.bean.api.*
import com.journey.android.v2ex.utils.Constants
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query



/**
 * Created by journey on 2017/12/29.
 */
interface GetAPIService {

    companion object {
        private var cookieJar: PersistentCookieJar = PersistentCookieJar(SetCookieCache(),
                SharedPrefsCookiePersistor(V2exApplication.instance))
        private var okHttpClient = OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .build()
        private val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()!!

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
    fun getReplies(@Query("topic_id") id: Int,
                   @Query("page") page: Int,
                   @Query("page_size") pageSize: Int): Call<ArrayList<RepliesShowBean>>

    @GET(Constants.MEMBERS)
    fun getMemberInfo(@Query("id") id: Int): Call<MembersShowBean>

    @GET(Constants.SIGNIN)
    fun login(): Call<String>

    @FormUrlEncoded
    @POST(Constants.SIGNIN)
    fun postLogin(@Query("id") id: Int): Call<MembersShowBean>

}
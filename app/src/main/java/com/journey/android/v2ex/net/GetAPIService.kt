package com.journey.android.v2ex.net

import com.journey.android.v2ex.bean.api.*
import com.journey.android.v2ex.utils.Constants
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by journey on 2017/12/29.
 */
interface GetAPIService {

    companion object {
        private val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
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
    fun getTopicsByNode(@Query("node_id") nodeId: Int): Call<ArrayList<TopicsShowBean>>

    @GET(Constants.REPLIES)
    fun getReplies(@Query("topic_id") id: Int,
                   @Query("page") page: Int,
                   @Query("page_size") pageSize: Int): Call<ArrayList<RepliesShowBean>>

    @GET(Constants.MEMBERS)
    fun getMemberInfo(@Query("id") id: Int): Call<MembersShowBean>

}
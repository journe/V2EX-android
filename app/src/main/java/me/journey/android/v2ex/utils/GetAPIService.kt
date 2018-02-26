package me.journey.android.v2ex.utils

import me.journey.android.v2ex.bean.TopicListBean
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by journey on 2017/12/29.
 */
interface GetAPIService {
    @GET("topics/{user}.json")
    fun listRepos(@Path("user") user: String): Call<List<TopicListBean>>

    @GET(Constants.HOT)
    fun listHotTopics(): Call<ArrayList<TopicListBean>>

    @GET(Constants.LASTEST)
    fun listLastestTopics(): Call<ArrayList<TopicListBean>>

    @GET("topics/hot.json")
    fun repo(): Call<TopicListBean>
}
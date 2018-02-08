package me.journey.android.v2ex.utils

import me.journey.android.v2ex.bean.TopicList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by journey on 2017/12/29.
 */
interface GetAPIService {
    @GET("topics/{user}.json")
    fun listRepos(@Path("user") user: String): Call<List<TopicList>>

    @GET(Constants.HOT)
    fun listRepos(): Call<ArrayList<TopicList>>

    @GET(Constants.LASTEST)
    fun listLastest(): Call<ArrayList<TopicList>>

    @GET("topics/hot.json")
    fun repo(): Call<TopicList>
}
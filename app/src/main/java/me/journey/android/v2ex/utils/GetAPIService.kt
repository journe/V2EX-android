package me.journey.android.v2ex.utils

import me.journey.android.v2ex.bean.MemberInfoBean
import me.journey.android.v2ex.bean.TopicListBean
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

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

    @GET(Constants.MEMBERS)
    fun getMemberInfo(@Query("id") id: Int): Call<MemberInfoBean>

    //https://www.v2ex.com/api/members/show.json?id=1
}
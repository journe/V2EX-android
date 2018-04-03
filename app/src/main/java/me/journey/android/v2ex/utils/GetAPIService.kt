package me.journey.android.v2ex.utils

import me.journey.android.v2ex.bean.MemberInfoDetailBean
import me.journey.android.v2ex.bean.TopicListBean
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
        val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = retrofit.create(GetAPIService::class.java)
        fun getInstance(): GetAPIService {
            return service
        }
    }

    @GET(Constants.HOT)
    fun listHotTopics(): Call<ArrayList<TopicListBean>>

    @GET(Constants.LASTEST)
    fun listLastestTopics(): Call<ArrayList<TopicListBean>>

    @GET(Constants.MEMBERS)
    fun getMemberInfo(@Query("id") id: Int): Call<MemberInfoDetailBean>

}
package com.journey.android.v2ex.net

import com.journey.android.v2ex.bean.MemberInfoDetailBean
import com.journey.android.v2ex.bean.TopicListBean
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
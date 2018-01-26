package me.journey.android.v2ex.utils

import me.journey.android.v2ex.bean.Hot
import me.journey.android.v2ex.bean.Lastest
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by journey on 2017/12/29.
 */
interface GetAPIService {
    @GET("topics/{user}.json")
    fun listRepos(@Path("user") user: String): Call<List<Hot>>

    @GET(Constants.HOT)
    fun listRepos(): Call<ArrayList<Hot>>

    @GET(Constants.LASTEST)
    fun listLastest(): Call<ArrayList<Lastest>>

    @GET("topics/hot.json")
    fun repo(): Call<Hot>
}
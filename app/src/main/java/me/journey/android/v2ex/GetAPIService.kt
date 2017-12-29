package me.journey.android.v2ex

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by journey on 2017/12/29.
 */
interface GetAPIService {
    @GET("topics/{user}.json")
    fun listRepos(@Path("user") user: String): Call<List<Hot>>
    @GET("topics/hot.json")
    fun listRepos(): Call<ArrayList<Hot>>
    @GET("topics/hot.json")
    fun repo(): Call<Hot>
}
package me.journey.android.v2ex.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import me.journey.android.v2ex.bean.TopicListBean
import me.journey.android.v2ex.utils.Constants
import me.journey.android.v2ex.utils.GetAPIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by journey on 2018/1/26.
 */

open class BaseActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.addLogAdapter(AndroidLogAdapter())
    }

    fun getHotTopics() {
        //https://www.v2ex.com/api/topics/hot.json
        val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = retrofit.create(GetAPIService::class.java)

        val call = service.listLastest()

        call.enqueue(object : Callback<ArrayList<TopicListBean>> {
            override fun onResponse(call: Call<ArrayList<TopicListBean>>, response: Response<ArrayList<TopicListBean>>) {
                Logger.d(call.toString())
                for (item in response.body()!!) {
                    Logger.d(item)
                }
            }

            override fun onFailure(call: Call<ArrayList<TopicListBean>>, t: Throwable) {
                print(t.message)
            }
        })

        Logger.d(call)
    }
}


package com.journey.android.v2ex.net

import android.os.AsyncTask
import com.journey.android.v2ex.bean.js.LoginBean
import com.journey.android.v2ex.utils.Constants
import com.journey.android.v2ex.utils.LoginParser
import com.orhanobut.logger.Logger
import okhttp3.Request
import org.jsoup.Jsoup


abstract class GetLoginTask : AsyncTask<Any, Any, LoginBean>() {
    override fun onPreExecute() {
        super.onPreExecute()
        onStart()
    }

    abstract fun onStart()

    override fun doInBackground(vararg params: Any?): LoginBean {
        val url = Constants.BASE_URL + Constants.SIGNIN

//        var doc = Jsoup.connect(url)
//                .userAgent(Constants.USER_AGENT_ANDROID)
//                .get()

        val request = Request.Builder()
                .url(url)
                .header("User-Agent", Constants.USER_AGENT_ANDROID)
                .build()
        val call = GetAPIService.okHttpClient.newCall(request)
        var doc = Jsoup.parse(call.execute().body()!!.string())
                Logger.d(doc.toString())

//        call.enqueue(object : Callback {
//            override fun onFailure(call: Call, e: IOException) {
//                Log.d(TAG, "onFailure: ")
//            }
//
//            @Throws(IOException::class)
//            override fun onResponse(call: Call, response: Response) {
//                doc = Jsoup.parse(response.body()!!.string())
//            }
//        })

        return LoginParser.parseLoginBean(doc)
    }

    override fun onPostExecute(loginBean: LoginBean) {
        super.onPostExecute(loginBean)
        onFinish(loginBean)
    }

    abstract fun onFinish(loginBean: LoginBean)

}
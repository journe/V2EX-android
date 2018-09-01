package com.journey.android.v2ex.net

import android.os.AsyncTask
import com.journey.android.v2ex.bean.js.LoginBean
import com.journey.android.v2ex.utils.Constants
import com.journey.android.v2ex.utils.LoginParser
import com.orhanobut.logger.Logger
import org.jsoup.Jsoup

abstract class GetLoginTask : AsyncTask<Any, Any, LoginBean>() {
    override fun onPreExecute() {
        super.onPreExecute()
        onStart()
    }

    abstract fun onStart()

    override fun doInBackground(vararg params: Any?): LoginBean {
        val url = Constants.BASE_URL + Constants.SIGNIN
        val doc = Jsoup.connect(url)
                .header("User-Agent", Constants.USER_AGENT_ANDROID).get()
        Logger.d(doc.toString())
        return LoginParser.parseLoginBean(doc)
    }

    override fun onPostExecute(loginBean: LoginBean) {
        super.onPostExecute(loginBean)
        onFinish(loginBean)
    }

    abstract fun onFinish(loginBean: LoginBean)

}
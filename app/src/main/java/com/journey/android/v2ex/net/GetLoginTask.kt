package com.journey.android.v2ex.net

import android.os.AsyncTask
import com.journey.android.v2ex.utils.Constants
import org.jsoup.Jsoup

abstract class GetLoginTask : AsyncTask<Any, Any, String>() {
    override fun onPreExecute() {
        super.onPreExecute()
        onStart()
    }

    abstract fun onStart()

    override fun doInBackground(vararg params: Any?): String? {
        val url = Constants.BASE_URL + Constants.SIGNIN
        val doc = Jsoup.connect(url).get()
        return doc.html()
    }

    override fun onPostExecute(topicDetails: String) {
        super.onPostExecute(topicDetails)
        onFinish(topicDetails)
    }

    abstract fun onFinish(html: String)

}
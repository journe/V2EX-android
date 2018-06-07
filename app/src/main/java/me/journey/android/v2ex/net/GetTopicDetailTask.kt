package me.journey.android.v2ex.net

import android.os.AsyncTask
import me.journey.android.v2ex.bean.JsoupTopicDetailBean
import me.journey.android.v2ex.utils.TopicDetailParser
import org.jsoup.Jsoup

abstract class GetTopicDetailTask : AsyncTask<String, Any, JsoupTopicDetailBean>() {
    override fun onPreExecute() {
        super.onPreExecute()
        onStart()
    }

    abstract fun onStart()

    override fun doInBackground(vararg strings: String): JsoupTopicDetailBean {
        val url = "https://www.v2ex.com/t/" + strings[0]
        val doc = Jsoup.connect(url).get()
        return TopicDetailParser.parseTopicDetail(doc)
    }

    override fun onPostExecute(topicDetails: JsoupTopicDetailBean) {
        super.onPostExecute(topicDetails)
        onFinish(topicDetails)
    }

    abstract fun onFinish(topicList: JsoupTopicDetailBean)

}
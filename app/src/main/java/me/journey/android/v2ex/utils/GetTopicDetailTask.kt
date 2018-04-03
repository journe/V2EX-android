package me.journey.android.v2ex.utils

import android.os.AsyncTask
import me.journey.android.v2ex.bean.JsoupTopicDetailBean
import org.jsoup.Jsoup

abstract class GetTopicDetailTask : AsyncTask<String, Any, ArrayList<JsoupTopicDetailBean>>() {
    override fun onPreExecute() {
        super.onPreExecute()
        onStart()
    }

    abstract fun onStart()

    override fun doInBackground(vararg strings: String): ArrayList<JsoupTopicDetailBean> {
        val url = "https://www.v2ex.com/t/" + strings[0]
        val doc = Jsoup.connect(url).get()
        TopicDetailParser.parseTopicContent(doc)
        val topicDetails: ArrayList<JsoupTopicDetailBean> = ArrayList<JsoupTopicDetailBean>()
        return topicDetails
    }

    override fun onPostExecute(topicDetails: ArrayList<JsoupTopicDetailBean>) {
        super.onPostExecute(topicDetails)
        onFinish(topicDetails)
    }

    abstract fun onFinish(topicList: ArrayList<JsoupTopicDetailBean>)

}
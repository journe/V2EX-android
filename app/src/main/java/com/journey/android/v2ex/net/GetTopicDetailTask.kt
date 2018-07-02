package com.journey.android.v2ex.net

import android.os.AsyncTask
import com.journey.android.v2ex.bean.TopicDetailBean
import com.journey.android.v2ex.utils.TopicDetailParser
import org.jsoup.Jsoup

abstract class GetTopicDetailTask : AsyncTask<String, Any, TopicDetailBean>() {
    override fun onPreExecute() {
        super.onPreExecute()
        onStart()
    }

    abstract fun onStart()

    override fun doInBackground(vararg strings: String): TopicDetailBean {
        val url = "https://www.v2ex.com/t/" + strings[0]
        val doc = Jsoup.connect(url).get()
        return TopicDetailParser.parseTopicDetail(doc)
    }

    override fun onPostExecute(topicDetails: TopicDetailBean) {
        super.onPostExecute(topicDetails)
        onFinish(topicDetails)
    }

    abstract fun onFinish(topicList: TopicDetailBean)

}
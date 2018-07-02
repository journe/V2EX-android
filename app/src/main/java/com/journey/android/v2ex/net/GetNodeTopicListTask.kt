package com.journey.android.v2ex.net

import android.os.AsyncTask
import com.journey.android.v2ex.bean.TopicListBean
import com.journey.android.v2ex.utils.TopicListParser
import org.jsoup.Jsoup


abstract class GetNodeTopicListTask : AsyncTask<String, Any, ArrayList<TopicListBean>>() {
    override fun onPreExecute() {
        super.onPreExecute()
        onStart()
    }

    abstract fun onStart()

    override fun doInBackground(vararg strings: String): ArrayList<TopicListBean> {
        val doc = Jsoup.connect("https://www.v2ex.com/?tab=" + strings[0]).get()
        return TopicListParser.parseTopicList(doc)
    }

    override fun onPostExecute(topicList: ArrayList<TopicListBean>) {
        super.onPostExecute(topicList)
        onFinish(topicList)
    }

    abstract fun onFinish(topicList: ArrayList<TopicListBean>)

}

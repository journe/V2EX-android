package com.journey.android.v2ex.net

import android.os.AsyncTask
import com.journey.android.v2ex.bean.api.TopicsListItemBean
import com.journey.android.v2ex.utils.parser.TopicListParser
import org.jsoup.Jsoup


abstract class GetNodeTopicListTask : AsyncTask<String, Any, ArrayList<TopicsListItemBean>>() {
    override fun onPreExecute() {
        super.onPreExecute()
        onStart()
    }

    abstract fun onStart()

    override fun doInBackground(vararg strings: String): ArrayList<TopicsListItemBean> {
        val doc = Jsoup.connect("https://www.v2ex.com/?tab=" + strings[0]).get()
        return TopicListParser.parseTopicList(doc)
    }

    override fun onPostExecute(topicListItem: ArrayList<TopicsListItemBean>) {
        super.onPostExecute(topicListItem)
        onFinish(topicListItem)
    }

    abstract fun onFinish(topicListItem: ArrayList<TopicsListItemBean>)

}

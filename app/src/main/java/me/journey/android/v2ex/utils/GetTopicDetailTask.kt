package me.journey.android.v2ex.utils

import android.os.AsyncTask
import com.orhanobut.logger.Logger
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
        Logger.d(doc!!.body().toString())
        val topicDetails: ArrayList<JsoupTopicDetailBean> = ArrayList<JsoupTopicDetailBean>()

        return topicDetails
    }

    override fun onPostExecute(topicDetails: ArrayList<JsoupTopicDetailBean>) {
        super.onPostExecute(topicDetails)

//        val content = document!!.body().selectFirst("#Wrapper")
//                .selectFirst(".content")
//                .selectFirst("#Main")
//                .select(".box")
//                .select(".item")
//                .select("table")
//                .select("tbody")
//                .select("tr")
//        val topicList: ArrayList<JsoupTopicListBean> = ArrayList<JsoupTopicListBean>()
//        for (element in content) {
//            val td = element.select("td")
//            val jsTopicListBean = JsoupTopicListBean()
//            jsTopicListBean.member_name = td[0].select("a").attr("href").substringAfterLast("/")
//            jsTopicListBean.member_avatar = td[0].select("a").select("img").attr("src")
//            jsTopicListBean.url = td[2].select(".item_title").select("a").attr("href")
//            jsTopicListBean.title = td[2].select(".item_title").select("a").text()
//            jsTopicListBean.node = td[2].select(".fade").select(".node").text()
//            jsTopicListBean.replies = td[3].select("a").text()
//            var strList: List<String> = td[2].select(".fade").text().split(" • ")
//            if (strList.size > 2) {
//                jsTopicListBean.last_modified = strList[2]
//            } else {
//                jsTopicListBean.last_modified = ""
//            }
//
//            topicList.add(jsTopicListBean)
//        }
        onFinish(topicDetails)
    }

    abstract fun onFinish(topicList: ArrayList<JsoupTopicDetailBean>)

}
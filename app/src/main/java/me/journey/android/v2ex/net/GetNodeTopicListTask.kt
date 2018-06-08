package me.journey.android.v2ex.net

import android.os.AsyncTask
import me.journey.android.v2ex.bean.JsoupTopicListBean
import me.journey.android.v2ex.bean.TopicListBean
import org.jsoup.Jsoup


abstract class GetNodeTopicListTask : AsyncTask<String, Any, ArrayList<JsoupTopicListBean>>() {
    override fun onPreExecute() {
        super.onPreExecute()
        onStart()
    }

    abstract fun onStart()

    override fun doInBackground(vararg strings: String): ArrayList<JsoupTopicListBean> {
        val doc = Jsoup.connect("https://www.v2ex.com/?tab=" + strings[0]).get()
        val content = doc.body().selectFirst("#Wrapper")
                .selectFirst(".content")
                .selectFirst("#Main")
                .select(".box")
                .select(".item")
                .select("table")
                .select("tbody")
                .select("tr")
        val topicList: ArrayList<JsoupTopicListBean> = ArrayList<JsoupTopicListBean>()
        for (element in content) {
            val td = element.select("td")
            val topicListBean = TopicListBean()
            topicListBean.member!!.username = td[0].select("a").attr("href").substringAfterLast("/")
            topicListBean.member!!.avatar_large = td[0].select("a").select("img").attr("src")
            topicListBean.url = td[2].select(".item_title").select("a").attr("href")
            topicListBean.title = td[2].select(".item_title").select("a").text()
            topicListBean.node!!.title = td[2].select(".fade").select(".node").text()
            val replies = td[3].select("a").text()
            if (replies.isEmpty()){
                topicListBean.replies = 0
            }else{
                topicListBean.replies = td[3].select("a").text().toInt()
            }
            val jsTopicListBean = JsoupTopicListBean()
            jsTopicListBean.member_name = td[0].select("a").attr("href").substringAfterLast("/")
            jsTopicListBean.member_avatar = td[0].select("a").select("img").attr("src")
            jsTopicListBean.url = td[2].select(".item_title").select("a").attr("href")
            jsTopicListBean.title = td[2].select(".item_title").select("a").text()
            jsTopicListBean.node = td[2].select(".fade").select(".node").text()
            jsTopicListBean.replies = td[3].select("a").text()
            var strList: List<String> = td[2].select(".fade").text().split(" • ")
            if (strList.size > 2) {
                jsTopicListBean.last_modified = strList[2]
            } else {
                jsTopicListBean.last_modified = ""
            }
            topicList.add(jsTopicListBean)
//            Logger.d(jsTopicListBean.member_name + "\n" +
//                    jsTopicListBean.member_avatar + "\n" +
//                    jsTopicListBean.title + "\n" +
//                    jsTopicListBean.node + "\n" +
//                    jsTopicListBean.last_modified + "\n" +
//                    jsTopicListBean.replies + "\n" +
//                    jsTopicListBean.url)
        }
        return topicList
    }

    override fun onPostExecute(topicList: ArrayList<JsoupTopicListBean>) {
        super.onPostExecute(topicList)
        onFinish(topicList)
    }

    abstract fun onFinish(topicList: ArrayList<JsoupTopicListBean>)

}

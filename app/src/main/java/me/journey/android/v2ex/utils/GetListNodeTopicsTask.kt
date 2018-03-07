package me.journey.android.v2ex.utils

import android.os.AsyncTask
import com.orhanobut.logger.Logger
import me.journey.android.v2ex.bean.JsoupTopicListBean
import org.jsoup.Jsoup
import org.jsoup.nodes.Document


class GetListNodeTopicsTask : AsyncTask<String, Any, Document>() {
    override fun doInBackground(vararg strings: String): Document? {
        val doc = Jsoup.connect("https://www.v2ex.com/?tab=deals").get()
//        val title = doc.title()
//        Logger.d(title)
//        Logger.d(doc.body())
        return doc
    }

    override fun onPostExecute(document: Document?) {
        super.onPostExecute(document)
//        Logger.d(document?.body().toString())
        val content = document!!.body().selectFirst("#Wrapper")
                .selectFirst(".content")
                .selectFirst("#Main")
                .select(".box")
                .select(".item")
                .select("table")
                .select("tbody")
                .select("tr")
        val topicList: ArrayList<JsoupTopicListBean> = ArrayList<JsoupTopicListBean>()
        for (element in content) {
//            Logger.d(element.toString())
            val td = element.select("td")
            val jsTopicListBean: JsoupTopicListBean = JsoupTopicListBean()
            jsTopicListBean.member_name = td[0].select("a").attr("href")
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
            Logger.d(jsTopicListBean.member_name + "\n" +
                    jsTopicListBean.member_avatar + "\n" +
                    jsTopicListBean.title + "\n" +
                    jsTopicListBean.node + "\n" +
                    jsTopicListBean.last_modified + "\n" +
                    jsTopicListBean.replies + "\n" +
                    jsTopicListBean.url)
        }

    }

}

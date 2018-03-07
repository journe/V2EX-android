package me.journey.android.v2ex.utils

import android.os.AsyncTask
import com.orhanobut.logger.Logger
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
        val content = document?.body()?.selectFirst("#Wrapper")
                ?.selectFirst(".content")
                ?.selectFirst("#Main")
                ?.select(".box")
        val topicList = content!!.select(".item")
                .select("table")
                .select("tbody")
                .select("tr")
        for (element in topicList) {
            Logger.d(element.toString())
        }

    }

//    fun parseDocForTab(Document doc){
//        val elements = doc.select("#Main > div:nth-child(2) > .item  tr")
//        val result:ArrayList = ArrayList()
//        for (item in elements) {
//            result.add(parseItemForTab(item))
//        }
//        return result
//    }

}

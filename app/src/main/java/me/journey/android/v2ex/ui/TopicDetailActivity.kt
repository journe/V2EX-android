package me.journey.android.v2ex.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import com.zzhoujay.richtext.RichText
import kotlinx.android.synthetic.main.activity_topic_detail.*
import me.journey.android.v2ex.R
import me.journey.android.v2ex.bean.JsoupTopicDetailBean
import me.journey.android.v2ex.utils.GetTopicDetailTask
import me.journey.android.v2ex.utils.ImageLoader

class TopicDetailActivity : AppCompatActivity() {

    private var topicId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = this.layoutInflater.inflate(R.layout.activity_topic_detail, null as ViewGroup?, false)
        setContentView(R.layout.activity_topic_detail)
        topicId = intent.extras[TOPIC_ID] as Int
        initView(view)
    }

    private fun initView(view: View) {
        object : GetTopicDetailTask() {
            override fun onStart() {
            }

            override fun onFinish(topicDetail: JsoupTopicDetailBean) {
                topic_detail_title_tv.text = topicDetail.title
                RichText.fromHtml(topicDetail.content)
//                .errorImage(object : DrawableGetter {
//                    override fun getDrawable(holder: ImageHolder?, config: RichTextConfig?, textView: TextView?): Drawable {
//                        return getDrawable(R.drawable.ic_image_error)
//                    }
//                })
                        .into(topic_detail_content_tv)
                topic_detail_menber_name_tv.text = topicDetail.memberBean.username
                ImageLoader.displayImage(view, topicDetail.memberBean.avatar,
                        topic_detail_avatar, R.mipmap.ic_launcher_round, 4)
            }

        }.execute(topicId.toString())
    }

    companion object {
        val TOPIC_ID = "topic_id"
        fun start(id: Int, context: Context) {
            val intent = Intent(context, TopicDetailActivity::class.java)
            intent.putExtra(TOPIC_ID, id)
            context.startActivity(intent)
        }
    }
}

package me.journey.android.v2ex.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import com.zzhoujay.richtext.RichText
import me.journey.android.v2ex.R
import me.journey.android.v2ex.utils.TopicCommentItemAdapter
import me.journey.android.v2ex.bean.JsoupTopicDetailBean
import me.journey.android.v2ex.net.GetTopicDetailTask
import me.journey.android.v2ex.utils.ImageLoader
import kotlinx.android.synthetic.main.activity_topic_detail.*
import me.journey.android.v2ex.R.id.*


class TopicDetailActivity : AppCompatActivity() {

    private var topicId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = this.layoutInflater.inflate(R.layout.activity_topic_detail, null as ViewGroup?, false)
        setContentView(R.layout.activity_topic_detail)
        setSupportActionBar(topic_detail_toolbar)
        topicId = intent.extras[TOPIC_ID] as Int
        initView(view)
    }

    private fun initView(view: View) {
        topic_detail_comments_list.layoutManager = LinearLayoutManager(this)
        topic_detail_comments_list.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        object : GetTopicDetailTask() {
            override fun onStart() {
            }

            override fun onFinish(jsoupTopicDetailBean: JsoupTopicDetailBean) {
                topic_detail_title_tv.text = jsoupTopicDetailBean.title
                RichText.fromHtml(jsoupTopicDetailBean.content)
//                .errorImage(object : DrawableGetter {
//                    override fun getDrawable(holder: ImageHolder?, config: RichTextConfig?, textView: TextView?): Drawable {
//                        return getDrawable(R.drawable.ic_image_error)
//                    }
//                })
                        .into(topic_detail_content_tv)
                topic_detail_menber_name_tv.text = jsoupTopicDetailBean.memberBean.username
                ImageLoader.displayImage(view, jsoupTopicDetailBean.memberBean.avatar,
                        topic_detail_avatar, R.mipmap.ic_launcher_round, 4)

                topic_detail_comments_list.adapter = TopicCommentItemAdapter(jsoupTopicDetailBean.comments)
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

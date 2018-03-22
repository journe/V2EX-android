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
import me.journey.android.v2ex.bean.TopicListBean
import me.journey.android.v2ex.utils.ImageLoader

class TopicDetailActivity : AppCompatActivity() {

    private lateinit var topicListBean: TopicListBean
    private lateinit var memberBean: TopicListBean.MemberBean

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = this.layoutInflater.inflate(R.layout.activity_topic_detail, null as ViewGroup?, false)
        setContentView(R.layout.activity_topic_detail)
        topicListBean = intent.extras[TOPICLISTBEAN] as TopicListBean
        memberBean = intent.extras[MEMBERBEAN] as TopicListBean.MemberBean
        initView(view)
    }

    private fun initView(view: View) {
        topic_detail_title_tv.text = topicListBean.title
        RichText.fromMarkdown(topicListBean.content)
//                .errorImage(object : DrawableGetter {
//                    override fun getDrawable(holder: ImageHolder?, config: RichTextConfig?, textView: TextView?): Drawable {
//                        return getDrawable(R.drawable.ic_image_error)
//                    }
//                })
                .into(topic_detail_content_tv)
        topic_detail_menber_name_tv.text = memberBean.username
        ImageLoader.displayImage(view, memberBean?.avatar_large,
                topic_detail_avatar, R.mipmap.ic_launcher_round, 4)
    }

    companion object {
        val TOPICLISTBEAN = "topiclistbean"
        val MEMBERBEAN = "memberbean"
        fun start(topicListBean: TopicListBean, member: TopicListBean.MemberBean, context: Context) {
            val intent = Intent(context, TopicDetailActivity::class.java)
            intent.putExtra(TOPICLISTBEAN, topicListBean)
            intent.putExtra(MEMBERBEAN, member)
            context.startActivity(intent)
        }
    }
}

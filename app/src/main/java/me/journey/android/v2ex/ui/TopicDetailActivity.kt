package me.journey.android.v2ex.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.ViewGroup
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder
import com.zzhoujay.richtext.RichText
import kotlinx.android.synthetic.main.activity_topic_detail.*
import me.journey.android.v2ex.R
import me.journey.android.v2ex.bean.TopicCommentBean
import me.journey.android.v2ex.bean.TopicDetailBean
import me.journey.android.v2ex.net.GetTopicDetailTask
import me.journey.android.v2ex.utils.ImageLoader


class TopicDetailActivity : BaseActivity() {

    private var topicId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = this.layoutInflater.inflate(R.layout.activity_topic_detail,
                null as ViewGroup?, false)
        setContentView(R.layout.activity_topic_detail)
        setSupportActionBar(topic_detail_toolbar)
        topicId = intent.extras[TOPIC_ID] as Int
        initView(view)
    }

    private fun initView(view: View) {
        topic_detail_comments_list.layoutManager = LinearLayoutManager(this)
        topic_detail_comments_list.addItemDecoration(DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL))

        object : GetTopicDetailTask() {
            override fun onStart() {
            }

            override fun onFinish(topicDetailBean: TopicDetailBean) {
                topic_detail_title_tv.text = topicDetailBean.title
                RichText.fromHtml(topicDetailBean.content)
                        .into(topic_detail_content_tv)
                topic_detail_menber_name_tv.text = topicDetailBean.memberBean.username
                ImageLoader.displayImage(view, topicDetailBean.memberBean.avatar,
                        topic_detail_avatar, R.mipmap.ic_launcher_round, 4)

                topic_detail_comments_list.adapter = topicDetailBean.topicComments?.let { getTopicCommentItemAdapter(it) }
            }

        }.execute(topicId.toString())
    }

    private fun getTopicCommentItemAdapter(topicComments: ArrayList<TopicCommentBean>): CommonAdapter<TopicCommentBean> {
        return object : CommonAdapter<TopicCommentBean>(this, R.layout.activity_topic_comment_item,
                topicComments) {
            override fun convert(holder: ViewHolder, t: TopicCommentBean, position: Int) {
                RichText.fromHtml(t.content)
                        .into(holder.getView(R.id.topic_comment_item_content_tv))
                holder.setText(R.id.topic_comment_item_username_tv, t.member.username)
                holder.setText(R.id.topic_comment_item_floor_tv, t.floor.toString())
                holder.setText(R.id.topic_comment_item_reply_time_tv, t.replyTime)

                ImageLoader.displayImage(holder.convertView, t.member.avatar,
                        holder.getView(R.id.topic_comment_item_useravatar_iv), R.mipmap.ic_launcher_round, 4)
                holder.convertView.setOnClickListener {
                    //            mListener?.onListFragmentInteraction(holder.mItem!!.id)
                }
            }
        }

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

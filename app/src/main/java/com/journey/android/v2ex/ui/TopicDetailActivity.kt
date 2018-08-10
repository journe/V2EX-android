package com.journey.android.v2ex.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.journey.android.v2ex.R
import com.journey.android.v2ex.bean.api.RepliesShowBean
import com.journey.android.v2ex.bean.api.TopicsShowBean
import com.journey.android.v2ex.net.GetAPIService
import com.journey.android.v2ex.utils.ImageLoader
import com.journey.android.v2ex.utils.TimeUtil.calculateTime
import com.orhanobut.logger.Logger
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper
import com.zzhoujay.richtext.RichText
import kotlinx.android.synthetic.main.activity_topic_detail.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TopicDetailActivity : BaseActivity() {

    private var topicId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val view = this.layoutInflater.inflate(R.layout.activity_topic_detail,
                null as ViewGroup?, false)
        setContentView(R.layout.activity_topic_detail)
        setSupportActionBar(topic_detail_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        topic_detail_toolbar.setNavigationOnClickListener({ finish() })
        topicId = intent.extras[TOPIC_ID] as Int
        Logger.d(topicId)
        initView(view)
    }

    private fun initView(view: View) {
        topic_detail_comments_list.layoutManager = LinearLayoutManager(this)
        topic_detail_comments_list.addItemDecoration(DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL))

        getTopicDetais()
    }

    private fun getTopicDetais() {
        GetAPIService.getInstance().getTopicsById(topicId)
                .enqueue(object : Callback<ArrayList<TopicsShowBean>> {
                    override fun onResponse(call: Call<ArrayList<TopicsShowBean>>?,
                                            response: Response<ArrayList<TopicsShowBean>>?) {
                        val headView = layoutInflater.inflate(R.layout.activity_topic_detail_head,
                                null as ViewGroup?, false)
                        val topicDetailBean = response?.body()?.get(0)
                        headView.findViewById<TextView>(R.id.topic_detail_title_tv).text = topicDetailBean?.title
                        headView.findViewById<TextView>(R.id.topic_detail_node_tv).text = topicDetailBean?.node?.title
                        headView.findViewById<TextView>(R.id.topic_detail_create_time_tv).text = topicDetailBean?.created?.toLong()?.let { calculateTime(it) }
                        topicDetailBean?.content_rendered?.let {
                            RichText.fromHtml(it)
                                    .into(headView.findViewById(R.id.topic_detail_content_tv))
                        }
                        ImageLoader.displayImage(headView, topicDetailBean?.member?.avatar_large,
                                headView.findViewById(R.id.topic_detail_avatar), R.mipmap.ic_launcher_round, 4)

                        getTopicReplies(headView)
                    }

                    override fun onFailure(call: Call<ArrayList<TopicsShowBean>>?, t: Throwable?) {
                        print(t?.message)
                    }
                })
    }

    private fun getTopicReplies(headView: View) {
        GetAPIService.getInstance().getReplies(topicId, 1, 100)
                .enqueue(object : Callback<ArrayList<RepliesShowBean>> {
                    override fun onFailure(call: Call<ArrayList<RepliesShowBean>>?, t: Throwable?) {
                    }

                    override fun onResponse(call: Call<ArrayList<RepliesShowBean>>?, response: Response<ArrayList<RepliesShowBean>>?) {
                        val replies = response?.body()
                        replies?.let {
                            topic_detail_comments_list.visibility = View.VISIBLE
                            val mHeaderAndFooterWrapper = HeaderAndFooterWrapper<RecyclerView.Adapter<RecyclerView.ViewHolder>>(getTopicReplyItemAdapter(it))
                            mHeaderAndFooterWrapper.addHeaderView(headView)
                            topic_detail_comments_list.adapter = mHeaderAndFooterWrapper
                            mHeaderAndFooterWrapper.notifyDataSetChanged()
                        }
                    }

                })
    }


    private fun getTopicReplyItemAdapter(topicComments: ArrayList<RepliesShowBean>): CommonAdapter<RepliesShowBean> {
        return object : CommonAdapter<RepliesShowBean>(this, R.layout.activity_topic_comment_item,
                topicComments) {
            override fun convert(holder: ViewHolder, t: RepliesShowBean, position: Int) {
                RichText.fromHtml(t.content_rendered)
                        .into(holder.getView(R.id.topic_comment_item_content_tv))
                holder.setText(R.id.topic_comment_item_username_tv, t.member?.username)
                holder.setText(R.id.topic_comment_item_floor_tv, position.toString())
                holder.setText(R.id.topic_comment_item_reply_time_tv, calculateTime(t.created.toLong()))

                ImageLoader.displayImage(holder.convertView, t.member?.avatar_large,
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

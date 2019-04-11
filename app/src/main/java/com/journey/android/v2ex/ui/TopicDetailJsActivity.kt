package com.journey.android.v2ex.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import com.journey.android.v2ex.R
import com.journey.android.v2ex.bean.api.RepliesShowBean
import com.journey.android.v2ex.bean.jsoup.parser.TopicDetailParser
import com.journey.android.v2ex.net.RetrofitService
import com.journey.android.v2ex.utils.ImageLoader
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper
import com.zzhoujay.richtext.RichText
import kotlinx.android.synthetic.main.activity_topic_detail.topic_detail_comments_list
import kotlinx.android.synthetic.main.activity_topic_detail.topic_detail_toolbar
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopicDetailJsActivity : BaseActivity() {

  private var topicId: Int = 0

  private lateinit var view: View

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    view = this.layoutInflater.inflate(
        R.layout.activity_topic_detail,
        null as ViewGroup?, false
    )
    setContentView(R.layout.activity_topic_detail)
    setSupportActionBar(topic_detail_toolbar)
    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    topic_detail_toolbar.setNavigationOnClickListener { finish() }
    topicId = intent.extras[TOPIC_ID] as Int

    topic_detail_comments_list.layoutManager = LinearLayoutManager(this)
    topic_detail_comments_list.addItemDecoration(
        DividerItemDecoration(
            this,
            DividerItemDecoration.VERTICAL
        )
    )

    getJsTopicById(topicId)
  }

  private fun getJsTopicById(id: Int) {
    RetrofitService.getInstance()
        .getTopicById(id, 1)
        .enqueue(object : Callback<ResponseBody> {
          override fun onFailure(
            call: Call<ResponseBody>,
            t: Throwable
          ) {
          }

          override fun onResponse(
            call: Call<ResponseBody>,
            response: Response<ResponseBody>
          ) {
            val doc = Jsoup.parse(response.body()!!.string())
            val topicDetailBean = TopicDetailParser.parseTopicDetail(doc)

            val headView = layoutInflater.inflate(
                R.layout.activity_topic_detail_head,
                view as ViewGroup, false
            )
            headView.findViewById<TextView>(R.id.topic_detail_title_tv)
                .text = topicDetailBean.title
            headView.findViewById<TextView>(R.id.topic_detail_node_tv)
                .text = topicDetailBean.node.name
            headView.findViewById<TextView>(R.id.topic_detail_create_time_tv)
                .text = topicDetailBean.created_str
            topicDetailBean.content?.let {
              RichText.fromHtml(it)
                  .clickable(true)
                  .imageClick { imageUrls, position ->
                    TopicImageActivity.start(imageUrls[position], this@TopicDetailJsActivity)
                  }
                  .into(headView.findViewById(R.id.topic_detail_content_tv))
            }
            ImageLoader.displayImage(
                view, topicDetailBean.member.avatar_large,
                headView.findViewById(R.id.topic_detail_avatar), R.mipmap.ic_launcher_round, 4
            )

            //附言
            val subtlesView = headView.findViewById<LinearLayout>(R.id.topic_subtles_ll)
            topicDetailBean.subtles?.forEach {
              val subtleItemView = layoutInflater.inflate(
                  R.layout.activity_topic_detail_subtle_item,
                  view as ViewGroup, false
              )
              subtleItemView.findViewById<TextView>(R.id.topic_subtle_title_tv)
                  .text = it.title
              RichText.fromHtml(it.content)
                  .imageClick { imageUrls, position ->
                    TopicImageActivity.start(imageUrls[position], this@TopicDetailJsActivity)
                  }
                  .into(subtleItemView.findViewById(R.id.topic_subtle_content_tv))
              subtlesView.addView(subtleItemView)
            }
            //取得评论数据并添加head view
            TopicDetailParser.parseComments(doc)
                ?.let {
                  topic_detail_comments_list.visibility = View.VISIBLE
                  setTopicHeadView(getTopicCommentItemAdapter(it), headView)
                }
          }

        })
  }

  private fun setTopicHeadView(
    topicCommentItemAdapter: CommonAdapter<RepliesShowBean>,
    headView: View
  ) {
    val mHeaderAndFooterWrapper =
      HeaderAndFooterWrapper<RecyclerView.Adapter<RecyclerView.ViewHolder>>(topicCommentItemAdapter)
    mHeaderAndFooterWrapper.addHeaderView(headView)
    topic_detail_comments_list.adapter = mHeaderAndFooterWrapper
    mHeaderAndFooterWrapper.notifyDataSetChanged()
  }

  private fun getTopicCommentItemAdapter(topicComments: ArrayList<RepliesShowBean>): CommonAdapter<RepliesShowBean> {
    return object : CommonAdapter<RepliesShowBean>(
        this, R.layout.activity_topic_comment_item,
        topicComments
    ) {
      override fun convert(
        holder: ViewHolder,
        t: RepliesShowBean,
        position: Int
      ) {
        RichText.fromHtml(t.content)
            .into(holder.getView(R.id.topic_comment_item_content_tv))
        holder.setText(R.id.topic_comment_item_username_tv, t.member.username)
        holder.setText(R.id.topic_comment_item_floor_tv, t.floor.toString())
        holder.setText(R.id.topic_comment_item_reply_time_tv, t.created_str)

        ImageLoader.displayImage(
            holder.convertView, t.member.avatar_large,
            holder.getView(R.id.topic_comment_item_useravatar_iv), R.mipmap.ic_launcher_round, 4
        )
        holder.convertView.setOnClickListener {
          //            mListener?.onListFragmentInteraction(holder.mItem!!.id)
        }
      }
    }
  }

  companion object {
    const val TOPIC_ID = "topic_id"
    fun start(
      id: Int,
      context: Context
    ) {
      val intent = Intent(context, TopicDetailJsActivity::class.java)
      intent.putExtra(TOPIC_ID, id)
      context.startActivity(intent)
    }
  }
}

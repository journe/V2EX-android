package com.journey.android.v2ex.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.journey.android.v2ex.R
import com.journey.android.v2ex.bean.api.RepliesShowBean
import com.journey.android.v2ex.bean.api.TopicsShowBean
import com.journey.android.v2ex.bean.jsoup.parser.TopicDetailParser
import com.journey.android.v2ex.net.RetrofitRequest
import com.journey.android.v2ex.utils.ImageLoader
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.wrapper.HeaderAndFooterWrapper
import com.zzhoujay.richtext.ImageHolder
import com.zzhoujay.richtext.RichText
import kotlinx.android.synthetic.main.activity_topic_detail.topic_detail_comments_list
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopicDetailFragment : BaseFragment() {
  private var topicId: Int = 0

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.activity_topic_detail, container, false)
  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)
    topic_detail_comments_list.layoutManager = LinearLayoutManager(context)
    topic_detail_comments_list.addItemDecoration(
        DividerItemDecoration(
            context,
            DividerItemDecoration.VERTICAL
        )
    )
    val safeArgs: TopicDetailFragmentArgs by navArgs()
    topicId = safeArgs.topicId
    getData(topicId)
  }

  private fun getData(id: Int) {
//    val result = TopicsShowBean().queryFirst { equalTo("id", id) }
//    if (result != null) {
//      val headView = addHeaderView(result)
//      val replies = RepliesShowBean().query { equalTo("topic_id", id) }
//      topic_detail_comments_list.visibility = View.VISIBLE
//      setTopicHeadView(genTopicCommentItemAdapter(replies), headView)
//    } else {
//      getDataByNet(id)
//    }
    getDataByNet(id)
  }

  private fun getDataByNet(id: Int) {
    RetrofitRequest.retrofit
        .getTopicById(id)
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
            val doc = Jsoup.parse(
                response.body()!!
                    .string()
            )
            val topicDetailBean = TopicDetailParser.parseTopicDetail(doc)
            topicDetailBean.id = id
            topicDetailBean.subtles?.forEach {
              it.id = id
            }
//            topicDetailBean.save()
            val headView = addHeaderView(topicDetailBean)

            getReplyByNet(id, headView)
            //评论
//            getReplyByJsoup(doc, headView)
          }

        })
  }

  private fun getReplyByJsoup(
    doc: Document,
    headView: View
  ) {
    TopicDetailParser.parseComments(doc)
        .let {
          topic_detail_comments_list.visibility = View.VISIBLE
          setTopicHeadView(genTopicCommentItemAdapter(it), headView)
        }
  }

  private fun getReplyByNet(
    id: Int,
    headView: View
  ) {
    RetrofitRequest.retrofit
        .getReplies(id, 1, 100)
        .enqueue(object : Callback<List<RepliesShowBean>> {
          override fun onFailure(
            call: Call<List<RepliesShowBean>>,
            t: Throwable
          ) {

          }

          override fun onResponse(
            call: Call<List<RepliesShowBean>>,
            response: Response<List<RepliesShowBean>>
          ) {
            val replies = response.body()
            if (replies!!.isNotEmpty()) {
//              replies.saveAll()
              topic_detail_comments_list.visibility = View.VISIBLE
              setTopicHeadView(genTopicCommentItemAdapter(replies), headView)
            }
          }

        })
  }

  private fun addHeaderView(topicDetailBean: TopicsShowBean): View {
    //取得评论数据并添加head view
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
            showImage(imageUrls[position])
          }
          .into(headView.findViewById(R.id.topic_detail_content_tv))
    }
    ImageLoader.displayImage(
        view as ViewGroup, topicDetailBean.member.avatar_large,
        headView.findViewById(R.id.topic_detail_avatar), R.mipmap.ic_launcher_round,
        R.dimen.avatar_radius
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
          .clickable(true)
          .imageClick { imageUrls, position ->
            showImage(imageUrls[position])
          }
          .into(subtleItemView.findViewById(R.id.topic_subtle_content_tv))
      subtlesView.addView(subtleItemView)
    }
    return headView
  }

  private fun showImage(url: String?) {
    val action =
      TopicDetailFragmentDirections.nextAction(url ?: "")
    findNavController().navigate(action)
  }

  private fun setTopicHeadView(
    topicCommentItemAdapter: CommonAdapter<RepliesShowBean>,
    headView: View
  ) {
    val mHeaderAndFooterWrapper =
      HeaderAndFooterWrapper<Adapter<ViewHolder>>(topicCommentItemAdapter)
    mHeaderAndFooterWrapper.addHeaderView(headView)
    mHeaderAndFooterWrapper.addFootView(
        layoutInflater.inflate(
            R.layout.activity_topic_detail_foot,
            view as ViewGroup, false
        )
    )
    topic_detail_comments_list.adapter = mHeaderAndFooterWrapper
    mHeaderAndFooterWrapper.notifyDataSetChanged()
  }

  private fun genTopicCommentItemAdapter(topicComments: List<RepliesShowBean>): CommonAdapter<RepliesShowBean> {
    return object : CommonAdapter<RepliesShowBean>(
        context, R.layout.activity_topic_comment_item,
        topicComments
    ) {
      override fun convert(
        holder: com.zhy.adapter.recyclerview.base.ViewHolder,
        repliesShowBean: RepliesShowBean,
        position: Int
      ) {
        RichText.fromHtml(repliesShowBean.content_rendered)
            .clickable(true)
            .scaleType(ImageHolder.ScaleType.none) // 图片缩放方式
            .size(ImageHolder.WRAP_CONTENT, ImageHolder.WRAP_CONTENT)
            .imageClick { imageUrls, position ->
              showImage(imageUrls[position])
            }
            .into(holder.getView(R.id.topic_comment_item_content_tv))
        holder.setText(R.id.topic_comment_item_username_tv, repliesShowBean.member.username)
        holder.setText(R.id.topic_comment_item_floor_tv, position.toString())
        holder.setText(R.id.topic_comment_item_reply_time_tv, repliesShowBean.created_str)

        ImageLoader.displayImage(
            holder.convertView, repliesShowBean.member.avatar_large,
            holder.getView(R.id.topic_comment_item_useravatar_iv), R.mipmap.ic_launcher_round,
            R.dimen.avatar_radius
        )
        holder.convertView.setOnClickListener {
          //            mListener?.onListFragmentInteraction(holder.mItem!!.id)
        }
      }
    }
  }
}

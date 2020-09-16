package com.journey.android.v2ex.module.topic.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.journey.android.v2ex.R
import com.journey.android.v2ex.libs.imageEngine.ImageLoader
import com.journey.android.v2ex.model.api.TopicsShowBean
import com.zzhoujay.richtext.RichText
import kotlinx.android.synthetic.main.fragment_topic_detail_head.view.topic_detail_title_tv

class TopicHeaderAdapter(var topicDetailBean: TopicsShowBean) : RecyclerView.Adapter<TopicHeaderAdapter.ViewHolder>() {
  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): ViewHolder {
    val view: View =
      LayoutInflater.from(parent.context)
          .inflate(R.layout.fragment_topic_detail_head, parent, false)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(
    holder: ViewHolder,
    position: Int
  ) {
    holder.bind()
  }

  override fun getItemCount(): Int {
    return 1
  }

  inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val title: TextView = itemView.topic_detail_title_tv
    fun bind() {
      val headView = itemView
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
//              showImage(imageUrls[position])
            }
            .into(headView.findViewById(R.id.topic_detail_content_tv))
      }
      ImageLoader.loadImage(
          headView.findViewById(R.id.topic_detail_avatar), topicDetailBean.member.avatar_large
      )

      //附言
      val subtlesView = headView.findViewById<LinearLayout>(R.id.topic_subtles_ll)
      topicDetailBean.subtles?.forEach {
        val subtleItemView = LayoutInflater.from(headView.context)
            .inflate(
                R.layout.fragment_topic_detail_subtle_item,
                headView as ViewGroup, true
            )
        subtleItemView.findViewById<TextView>(R.id.topic_subtle_title_tv)
            .text = it.title
        RichText.fromHtml(it.content)
            .clickable(true)
            .imageClick { imageUrls, position ->
//              showImage(imageUrls[position])
            }
            .into(subtleItemView.findViewById(R.id.topic_subtle_content_tv))
        subtlesView.addView(subtleItemView)
      }
    }
  }
}


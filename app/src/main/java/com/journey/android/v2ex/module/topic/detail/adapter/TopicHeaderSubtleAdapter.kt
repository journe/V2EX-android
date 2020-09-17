package com.journey.android.v2ex.module.topic.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.journey.android.v2ex.R
import com.journey.android.v2ex.model.api.TopicsShowBean
import com.journey.android.v2ex.module.topic.detail.adapter.TopicHeaderSubtleAdapter.ViewHolder
import com.journey.android.v2ex.router.Router
import com.zzhoujay.richtext.RichText

class TopicHeaderSubtleAdapter(var topicDetailBean: TopicsShowBean) : RecyclerView.Adapter<ViewHolder>() {
  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): ViewHolder {
    val view: View =
      LayoutInflater.from(parent.context)
          .inflate(R.layout.fragment_topic_detail_subtle_item, parent, false)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(
    holder: ViewHolder,
    position: Int
  ) {
    holder.bind(position)
  }

  override fun getItemCount(): Int {
    return topicDetailBean.subtles?.size ?: 0
  }

  inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(position: Int) {
      //附言
      if (topicDetailBean.subtles.isNullOrEmpty()
              .not()
      ) {
        topicDetailBean.subtles!![position].let {
          itemView.findViewById<TextView>(R.id.topic_subtle_title_tv)
              .text = it.title
          RichText.fromHtml(it.content)
              .clickable(true)
              .imageClick { imageUrls, position ->
                Router.imageDetail(imageUrls[position] ?: "")
              }
              .into(itemView.findViewById(R.id.topic_subtle_content_tv))
        }
      }
    }
  }
}


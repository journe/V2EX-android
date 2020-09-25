package com.journey.android.v2ex.module.topic.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.journey.android.v2ex.R
import com.journey.android.v2ex.model.api.TopicsShowBean
import com.journey.android.v2ex.module.topic.detail.TopicDetailFragmentDirections
import com.journey.android.v2ex.module.topic.detail.adapter.TopicHeaderAdapter.ViewHolder
import com.journey.android.v2ex.router.Router
import com.zzhoujay.richtext.RichText

class TopicHeaderAdapter(var topicDetailBean: TopicsShowBean) : RecyclerView.Adapter<ViewHolder>() {
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
    fun bind() {
      val headView = itemView
      topicDetailBean.content?.let {
        RichText.fromHtml(it)
            .clickable(true)
            .imageClick { imageUrls, position ->
              val action =
                TopicDetailFragmentDirections.topicDetailImage(imageUrls[position] ?: "")
              Router.navigate(action)
            }
            .into(headView.findViewById(R.id.topic_detail_content_tv))
      }
    }
  }
}


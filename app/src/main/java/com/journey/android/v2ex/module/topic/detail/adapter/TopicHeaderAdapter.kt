package com.journey.android.v2ex.module.topic.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.journey.android.v2ex.R
import com.journey.android.v2ex.model.api.TopicsShowBean
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
              Router.imageDetail(imageUrls[position] ?: "")
            }
            .into(headView.findViewById(R.id.topic_detail_content_tv))
      }
      //附言
//      val subtlesView = headView.findViewById<LinearLayout>(R.id.topic_subtles_ll)
//      topicDetailBean.subtles?.forEach {
//        val subtleItemView = LayoutInflater.from(headView.context)
//            .inflate(
//                R.layout.fragment_topic_detail_subtle_item,
//                headView as ViewGroup, true
//            )
//        subtleItemView.findViewById<TextView>(R.id.topic_subtle_title_tv)
//            .text = it.title
//        RichText.fromHtml(it.content)
//            .clickable(true)
//            .imageClick { imageUrls, position ->
////              showImage(imageUrls[position])
//            }
//            .into(subtleItemView.findViewById(R.id.topic_subtle_content_tv))
//        subtlesView.addView(subtleItemView)
//      }
    }
  }
}


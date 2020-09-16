package com.journey.android.v2ex.module.topic.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView
import com.journey.android.v2ex.R
import com.journey.android.v2ex.libs.imageEngine.ImageLoader
import com.journey.android.v2ex.model.api.RepliesShowBean
import com.journey.android.v2ex.router.V2EXRouter
import com.zzhoujay.richtext.ImageHolder
import com.zzhoujay.richtext.RichText
import kotlinx.android.synthetic.main.fragment_topic_detail_comment_item.view.topic_comment_item_content_tv
import kotlinx.android.synthetic.main.fragment_topic_detail_comment_item.view.topic_comment_item_floor_tv
import kotlinx.android.synthetic.main.fragment_topic_detail_comment_item.view.topic_comment_item_reply_time_tv
import kotlinx.android.synthetic.main.fragment_topic_detail_comment_item.view.topic_comment_item_useravatar_iv
import kotlinx.android.synthetic.main.fragment_topic_detail_comment_item.view.topic_comment_item_username_tv

class TopicCommentAdapter :
    PagedListAdapter<RepliesShowBean, TopicCommentAdapter.ViewHolder>(
        DIFF_CALLBACK
    ) {

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): ViewHolder {
    val view = LayoutInflater.from(parent.context)
        .inflate(R.layout.fragment_topic_detail_comment_item, parent, false)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(
    holder: ViewHolder,
    position: Int
  ) {
    val item = getItem(position)!!
    holder.bind(item)

    with(holder.mView) {
      tag = item
    }
  }

  inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
    val floor: TextView = mView.topic_comment_item_floor_tv
    val username: TextView = mView.topic_comment_item_username_tv
    val replyTime: TextView = mView.topic_comment_item_reply_time_tv
    val memberAvatar = mView.topic_comment_item_useravatar_iv
    fun bind(repliesShowBean: RepliesShowBean) {

      RichText.fromHtml(repliesShowBean.content_rendered)
          .clickable(true)
          .scaleType(ImageHolder.ScaleType.none) // 图片缩放方式
          .size(ImageHolder.WRAP_CONTENT, ImageHolder.WRAP_CONTENT)
          .imageClick { imageUrls, position ->
            showImage(imageUrls[position])
          }
          .into(mView.topic_comment_item_content_tv)
      floor.text = repliesShowBean.floor.toString()
      username.text = repliesShowBean.member.username
      replyTime.text = repliesShowBean.created_str

      ImageLoader.loadImage(
          memberAvatar,
          repliesShowBean.member.avatar_large
      )

//      holder.setOnClickListener(R.id.topic_useravatar_item_iv, View.OnClickListener {
//        MemberInfoActivity.start(
//            itemBean.memberName, holder.convertView.context
//        )
//      })

      mView.setOnClickListener {
//        navInterface.navigate(itemBean.id)
      }
    }

    private fun showImage(url: String?) {
      val action =
        TopicDetailFragmentDirections.topicDetailImage(url ?: "")
      V2EXRouter.navigate(action)
    }
  }

  companion object {
    private val DIFF_CALLBACK = object : ItemCallback<RepliesShowBean>() {
      override fun areItemsTheSame(
        oldItem: RepliesShowBean,
        newItem: RepliesShowBean
      ): Boolean {
        return oldItem.id == newItem.id
      }

      override fun areContentsTheSame(
        oldItem: RepliesShowBean,
        newItem: RepliesShowBean
      ): Boolean {
        return oldItem.id == newItem.id
      }
    }
  }
}
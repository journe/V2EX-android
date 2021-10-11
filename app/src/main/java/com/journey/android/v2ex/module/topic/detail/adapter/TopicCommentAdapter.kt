package com.journey.android.v2ex.module.topic.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.journey.android.v2ex.R
import com.journey.android.v2ex.libs.extension.invisible
import com.journey.android.v2ex.libs.extension.largeAvatar
import com.journey.android.v2ex.libs.extension.visible
import com.journey.android.v2ex.model.api.RepliesShowBean
import com.journey.android.v2ex.module.topic.detail.adapter.TopicCommentAdapter.ViewHolder
import com.journey.android.v2ex.router.Router
import com.zzhoujay.richtext.ImageHolder
import com.zzhoujay.richtext.RichText
import kotlinx.android.synthetic.main.fragment_topic_detail_comment_item.view.*

class TopicCommentAdapter :
    PagingDataAdapter<RepliesShowBean, ViewHolder>(
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

        with(holder.mView) { tag = item }
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        private val floor: TextView = mView.topic_comment_item_floor_tv
        private val username: TextView = mView.topic_comment_item_username_tv
        private val replyTime: TextView = mView.topic_comment_item_reply_time_tv
        private val heartCount: TextView = mView.topic_comment_item_reply_heart_tv
        private val memberAvatar = mView.topic_comment_item_useravatar_iv

        fun bind(repliesShowBean: RepliesShowBean) {
            RichText.fromHtml(repliesShowBean.content_rendered)
                .clickable(true)
                .scaleType(ImageHolder.ScaleType.none) // 图片缩放方式
                .size(ImageHolder.WRAP_CONTENT, ImageHolder.WRAP_CONTENT)
                .imageClick { imageUrls, position ->
                    Router.imageDetail(imageUrls[position] ?: "")
                }
                .into(mView.topic_comment_item_content_tv)

            if (repliesShowBean.floor != 0) {
                floor.text = repliesShowBean.floor.toString()
            }
            username.text = repliesShowBean.member.username
            replyTime.text = repliesShowBean.created_str
            if (repliesShowBean.heart != 0) {
                heartCount.visible()
                heartCount.text = "❤ ️${repliesShowBean.heart}"
            } else {
                heartCount.invisible()
            }

            memberAvatar.load(repliesShowBean.member.avatar_large.largeAvatar())
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
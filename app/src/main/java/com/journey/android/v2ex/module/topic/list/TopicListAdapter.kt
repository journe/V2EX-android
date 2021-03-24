package com.journey.android.v2ex.module.topic.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.PagedListAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView
import com.journey.android.v2ex.R
import com.journey.android.v2ex.libs.TimeUtil
import com.journey.android.v2ex.libs.extension.invisible
import com.journey.android.v2ex.libs.extension.largeAvatar
import com.journey.android.v2ex.libs.extension.onClick
import com.journey.android.v2ex.libs.extension.visible
import com.journey.android.v2ex.libs.imageEngine.ImageLoader
import com.journey.android.v2ex.model.api.TopicsListItemBean
import com.journey.android.v2ex.module.topic.MainFragmentDirections
import com.journey.android.v2ex.module.topic.detail.TopicDetailFragment
import com.journey.android.v2ex.router.Router
import kotlinx.android.synthetic.main.fragment_topic_list_item.view.topic_corner_star_iv
import kotlinx.android.synthetic.main.fragment_topic_list_item.view.topic_node_item_tv
import kotlinx.android.synthetic.main.fragment_topic_list_item.view.topic_replies_item_tv
import kotlinx.android.synthetic.main.fragment_topic_list_item.view.topic_reply_time_item_tv
import kotlinx.android.synthetic.main.fragment_topic_list_item.view.topic_title_item_tv
import kotlinx.android.synthetic.main.fragment_topic_list_item.view.topic_useravatar_item_iv
import kotlinx.android.synthetic.main.fragment_topic_list_item.view.topic_username_item_tv

internal class TopicListAdapter :
    PagingDataAdapter<TopicsListItemBean, TopicListViewHolder>(
        DIFF_CALLBACK
    ) {

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): TopicListViewHolder {
    return TopicListViewHolder(parent).apply {
      itemView.onClick {
        val itemBean = getItem(bindingAdapterPosition)!!
//        TopicDetailRepository(topicId = topicId).initTopicDetail()
        val action = MainFragmentDirections.topicDetail(
            itemBean.id, topicTitle = itemBean.title ?: "",
            avatar = itemBean.memberAvatar.largeAvatar() ?: ""
        )
        val extras = FragmentNavigatorExtras(
            title to TopicDetailFragment.TRANSITION_HEADER_TITLE,
            itemView to TopicDetailFragment.TRANSITION_BACKGROUND,
            avatar to TopicDetailFragment.TRANSITION_HEADER_AVATAR
        )
        Router.navigate(action, extras)
      }
    }
  }

  override fun onBindViewHolder(
    holder: TopicListViewHolder,
    position: Int
  ) {
    val itemBean = getItem(position)!!
//    holder.bind(item)

    ViewCompat.setTransitionName(holder.title, "header_title-${itemBean.id}")
    ViewCompat.setTransitionName(holder.avatar, "header_avatar-${itemBean.id}")
    ViewCompat.setTransitionName(holder.itemView, "header_item-${itemBean.id}")

    holder.title.text = itemBean.title
    holder.node.text = itemBean.nodeName
    holder.user.text = itemBean.memberName
    holder.replies.text = itemBean.replies.toString()
    if (itemBean.last_modified != 0) {
      itemBean.last_modified_str = TimeUtil.calculateTime(itemBean.last_modified.toLong())
    }

    holder.replyTime.text = itemBean.last_modified_str ?: ""
    if (itemBean.last_modified_str.equals("置顶")) {
      holder.starIv.visible()
    } else {
      holder.starIv.invisible()
    }
    ImageLoader.loadImage(holder.avatar, itemBean.memberAvatar.largeAvatar())
  }

  companion object {
    private val DIFF_CALLBACK = object : ItemCallback<TopicsListItemBean>() {
      override fun areItemsTheSame(
        oldItem: TopicsListItemBean,
        newItem: TopicsListItemBean
      ): Boolean {
        return oldItem.id == newItem.id
      }

      override fun areContentsTheSame(
        oldItem: TopicsListItemBean,
        newItem: TopicsListItemBean
      ): Boolean {
        return oldItem.id == newItem.id
      }
    }
  }
}

internal class TopicListViewHolder(
  parent: ViewGroup
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context)
        .inflate(R.layout.fragment_topic_list_item, parent, false)
) {

  val title: TextView = itemView.topic_title_item_tv
  val node: TextView = itemView.topic_node_item_tv
  val user: TextView = itemView.topic_username_item_tv
  val replies: TextView = itemView.topic_replies_item_tv
  val replyTime: TextView = itemView.topic_reply_time_item_tv
  val starIv = itemView.topic_corner_star_iv
  val avatar = itemView.topic_useravatar_item_iv

}
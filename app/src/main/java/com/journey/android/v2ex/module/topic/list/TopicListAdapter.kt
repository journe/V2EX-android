package com.journey.android.v2ex.module.topic.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView
import com.journey.android.v2ex.R
import com.journey.android.v2ex.model.api.TopicsListItemBean
import com.journey.android.v2ex.module.topic.list.TopicListAdapter.ViewHolder
import com.journey.android.v2ex.libs.TimeUtil
import com.journey.android.v2ex.libs.extension.invisible
import com.journey.android.v2ex.libs.extension.visible
import com.journey.android.v2ex.libs.imageEngine.ImageLoader
import com.journey.android.v2ex.module.topic.MainFragmentDirections
import com.journey.android.v2ex.module.topic.list.TopicListFragment.NavInterface
import com.journey.android.v2ex.router.V2EXRouter
import kotlinx.android.synthetic.main.fragment_topic_list_item.view.*

class TopicListAdapter(val navInterface: NavInterface) :
    PagedListAdapter<TopicsListItemBean, ViewHolder>(
        DIFF_CALLBACK
    ) {

  override fun onCreateViewHolder(
    parent: ViewGroup,
    viewType: Int
  ): ViewHolder {
    val view = LayoutInflater.from(parent.context)
        .inflate(R.layout.fragment_topic_list_item, parent, false)
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
    val title: TextView = mView.topic_title_item_tv
    val node: TextView = mView.topic_node_item_tv
    val user: TextView = mView.topic_username_item_tv
    val replies: TextView = mView.topic_replies_item_tv
    val replyTime: TextView = mView.topic_reply_time_item_tv
    val starIv = mView.topic_corner_star_iv
    val memberAvatar = mView.topic_useravatar_item_iv
    fun bind(itemBean: TopicsListItemBean) {
      title.text = itemBean.title
      node.text = itemBean.nodeName
      user.text = itemBean.memberName
      replies.text = itemBean.replies.toString()
      if (itemBean.last_modified != 0) {
        itemBean.last_modified_str = TimeUtil.calculateTime(itemBean.last_modified.toLong())
      }

      replyTime.text = itemBean.last_modified_str ?: ""
      if (itemBean.last_modified_str.equals("置顶")) {
        starIv.visible()
      } else {
        starIv.invisible()
      }
      ImageLoader.loadImage(memberAvatar, itemBean.memberAvatar)

//      holder.setOnClickListener(R.id.topic_useravatar_item_iv, View.OnClickListener {
//        MemberInfoActivity.start(
//            itemBean.memberName, holder.convertView.context
//        )
//      })

      mView.setOnClickListener {
        val action = MainFragmentDirections.topicDetail(itemBean.id)

        val extras = FragmentNavigatorExtras(
            title to "header_title",
            memberAvatar to "header_avatar"
        )

        //        navInterface.navigate(itemBean.id)
        V2EXRouter.navigate(action, extras)
      }
    }
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
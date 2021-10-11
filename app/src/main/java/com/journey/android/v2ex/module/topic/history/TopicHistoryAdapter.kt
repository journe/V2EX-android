package com.journey.android.v2ex.module.topic.history

/**
 * Created by journey on 2021/10/11.
 */
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.journey.android.v2ex.databinding.FragmentTopicListItemBinding
import com.journey.android.v2ex.libs.string
import com.journey.android.v2ex.model.api.TopicsShowBean
import com.journey.android.v2ex.module.topic.detail.TopicDetailFragment
import com.journey.android.v2ex.router.Router
import java.util.*

class TopicHistoryAdapter :
    PagingDataAdapter<TopicsShowBean, TopicHistoryAdapter.ViewHolder>(
        DIFF_CALLBACK
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            FragmentTopicListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)!!
        holder.bind(item)
        with(holder.itemView) {
            tag = item
        }
    }


    inner class ViewHolder(private val binding: FragmentTopicListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(itemBean: TopicsShowBean) {
            binding.topicTitleItemTv.text = itemBean.title
            binding.topicNodeItemTv.text = itemBean.node.name
            binding.topicUsernameItemTv.text = itemBean.member.username
            binding.topicRepliesItemTv.text = itemBean.replies.toString()
            if (itemBean.local_touched != 0L) {
                binding.topicReplyTimeItemTv.text = Date(itemBean.local_touched).string()
            }
            binding.topicUseravatarItemIv.load(itemBean.member.avatar_large)

            ViewCompat.setTransitionName(binding.topicTitleItemTv, "header_title-${itemBean.id}")
            ViewCompat.setTransitionName(
                binding.topicUseravatarItemIv,
                "header_avatar-${itemBean.id}"
            )
            ViewCompat.setTransitionName(itemView, "header_item-${itemBean.id}")

            binding.topicTitleItemTv.setOnClickListener {
                val action = TopicHistoryFragmentDirections.actionHistoryDestToTopicDetailDest(
                    topicId = itemBean.id,
                    topicTitle = itemBean.title ?: "",
                    avatar = itemBean.member.avatar_large ?: ""
                )
                val extras = FragmentNavigatorExtras(
                    binding.topicTitleItemTv to TopicDetailFragment.TRANSITION_HEADER_TITLE,
                    itemView to TopicDetailFragment.TRANSITION_BACKGROUND,
                    binding.topicUseravatarItemIv to TopicDetailFragment.TRANSITION_HEADER_AVATAR
                )
                Router.navigate(action, extras)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : ItemCallback<TopicsShowBean>() {
            override fun areItemsTheSame(
                oldItem: TopicsShowBean,
                newItem: TopicsShowBean
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: TopicsShowBean,
                newItem: TopicsShowBean
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}

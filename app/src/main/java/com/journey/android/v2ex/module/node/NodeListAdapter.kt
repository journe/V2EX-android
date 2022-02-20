package com.journey.android.v2ex.module.node

/**
 * Created by journey on 2022/2/21.
 */
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.journey.android.v2ex.databinding.FragmentNodeListItemBinding
import com.journey.android.v2ex.model.api.NodeBean

class NodeListAdapter :
	PagingDataAdapter<NodeBean, NodeListAdapter.ViewHolder>(
		DIFF_CALLBACK
	) {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		return ViewHolder(
			FragmentNodeListItemBinding.inflate(
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


	inner class ViewHolder(private val binding: FragmentNodeListItemBinding) :
		RecyclerView.ViewHolder(binding.root) {
		fun bind(item: NodeBean) {
			binding.nodeItemTv.text = item.title
			binding.nodeItemIv.load(item.avatar_large)
		}
	}

	companion object {
		private val DIFF_CALLBACK = object : ItemCallback<NodeBean>() {
			override fun areItemsTheSame(
				oldItem: NodeBean,
				newItem: NodeBean
			): Boolean {
				return oldItem.id == newItem.id
			}

			override fun areContentsTheSame(
				oldItem: NodeBean,
				newItem: NodeBean
			): Boolean {
				return oldItem.id == newItem.id
			}
		}
	}
}
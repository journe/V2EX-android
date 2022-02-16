package com.journey.android.v2ex.module.topic.detail.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.journey.android.v2ex.R
import com.journey.android.v2ex.libs.extension.gone
import com.journey.android.v2ex.libs.extension.visible
import com.journey.android.v2ex.model.api.TopicShowTag
import org.jetbrains.anko.textColorResource

class TopicHeaderTagAdapter(var list: List<TopicShowTag>?) :
	RecyclerView.Adapter<TopicHeaderTagAdapter.ViewHolder>() {
	override fun onCreateViewHolder(
		parent: ViewGroup,
		viewType: Int
	): ViewHolder {
		val view: View =
			LayoutInflater.from(parent.context)
				.inflate(R.layout.fragment_topic_detail_tag_item, parent, false)
		return ViewHolder(view)
	}

	override fun onBindViewHolder(
		holder: ViewHolder,
		position: Int
	) {
		holder.bind(position)
	}

	override fun getItemCount(): Int {
		return if (list.isNullOrEmpty()) {
			0
		} else {
			1
		}
	}

	inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
		fun bind(position: Int) {
			//附言
			if (list.isNullOrEmpty().not()) {
				itemView.visible()
				list!!.forEach {
					itemView.findViewById<ChipGroup>(R.id.topic_tag_chip_group)
						.addView(Chip(itemView.context).apply {
							text = it.title
							textColorResource = R.color.topic_tags
						})
				}
			} else {
				itemView.gone()
			}
		}
	}
}


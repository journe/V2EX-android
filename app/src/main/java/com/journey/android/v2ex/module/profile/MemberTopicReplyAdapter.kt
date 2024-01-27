package com.journey.android.v2ex.module.profile

/**
 * Created by fdx on 2017/7/15.
 * fdx will maintain it
 * 在用户信息的回复页面
 */

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.journey.android.v2ex.R
import com.journey.android.v2ex.libs.TimeUtil

//class MemberTopicReplyAdapter(val activity: Activity,
//                              var list: MutableList<MemberReplyModel> = mutableListOf())
//  : androidx.recyclerview.widget.RecyclerView.Adapter<MemberTopicReplyAdapter.ReplyViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
//            ReplyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_reply_member, parent, false))
//
//    override fun onBindViewHolder(holder: ReplyViewHolder, position: Int) {
//
//        val reply = list[position]
//        holder.tvTitle.text = reply.topic.title
//        holder.tvContent.setGoodText(reply.content, true)
//        holder.tvTime.text = TimeUtil.getRelativeTime(reply.create)
//
//        holder.itemView.setOnClickListener {
//            activity.startActivity<TopicActivity>(Keys.KEY_TOPIC_ID to reply.topic.id)
//        }
//    }
//
//
//    fun updateItem(newItems: List<MemberReplyModel>) {
////        val diffResult = DiffUtil.calculateDiff(DiffReply(list, newItems))
//        list.clear()
//        list.addAll(newItems)
//        notifyDataSetChanged()
////        diffResult.dispatchUpdatesTo(this)
//
//    }
//
//    fun addItems(newItems: List<MemberReplyModel>) {
//        val old = list.toList()
//        list.addAll(newItems)
//        val diffResult = DiffUtil.calculateDiff(DiffReply(old, list))
//        diffResult.dispatchUpdatesTo(this)
//    }
//
//    override fun getItemCount() = list.size
//
//  inner class ReplyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val tvTitle: TextView = itemView.findViewById(R.id.tv_title)
//        val tvContent: GoodTextView = itemView.findViewById(R.id.tv_content_reply)
//        val tvTime: TextView = itemView.findViewById(R.id.tv_create)
//    }
//}
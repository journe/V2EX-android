package me.journey.android.v2ex.utils

import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import me.journey.android.v2ex.R
import me.journey.android.v2ex.bean.JsoupTopicListBean
import me.journey.android.v2ex.ui.JsoupTopicListFragment

class JsoupTopicItemAdapter(private val mValues: List<JsoupTopicListBean>,
                            private val mListener: JsoupTopicListFragment.OnListFragmentInteractionListener?) : RecyclerView.Adapter<JsoupTopicItemAdapter.ViewHolder>() {

    lateinit var view: View;
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_topic_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues[position]
        holder.mTitleView.text = mValues[position].title
        holder.mTopicNodeView.text = mValues[position].node
        holder.mUsernameView.text = mValues[position].member_name
        holder.mCommentCountView.text = mValues[position].replies
        if (holder.mCommentCountView.text.isEmpty()) {
            holder.mCommentCountView.text = "0"
        }
        holder.mTopicReplyTimeView.text = mValues[position].last_modified

//        Glide.with(view).load(mValues[position].member?.avatar_normal ?: "").into(holder.mUserAvatarNormalView)
        ImageLoader.displayImage(view, mValues[position].member_avatar,
                holder.mUserAvatarNormalView, R.mipmap.ic_launcher_round, 4)
        holder.mView.setOnClickListener {
//            mListener?.onListFragmentInteraction(holder.mItem!!)
        }
        holder.mUserAvatarNormalView.setOnClickListener {
//            MemberInfoActivity.start(holder.mItem!!.member!!.id, holder.mView.context)
        }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    fun caculateTime(ts: Long): String {
        val created = ts * 1000
        val now = System.currentTimeMillis()
        val difference = now - created
        val text = if (difference >= 0 && difference <= DateUtils.MINUTE_IN_MILLIS)
            "刚刚"
        else
            DateUtils.getRelativeTimeSpanString(created, now, DateUtils.MINUTE_IN_MILLIS,
                    DateUtils.FORMAT_ABBREV_RELATIVE)
        return text.toString()
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mUsernameView: TextView
        val mUserAvatarNormalView: ImageView
        val mTitleView: TextView
        val mCommentCountView: TextView
        val mTopicNodeView: TextView
        val mTopicReplyTimeView: TextView
        var mItem: JsoupTopicListBean? = null

        init {
            mUsernameView = mView.findViewById(R.id.topic_username_item_tv)
            mUserAvatarNormalView = mView.findViewById(R.id.topic_useravatar_item_iv)
            mTitleView = mView.findViewById(R.id.topic_title_item_tv)
            mCommentCountView = mView.findViewById(R.id.topic_replies_item_tv)
            mTopicNodeView = mView.findViewById(R.id.topic_node_item_tv)
            mTopicReplyTimeView = mView.findViewById(R.id.topic_reply_time_item_tv)
        }

        override fun toString(): String {
            return super.toString() + " '" + mTitleView.text + "'"
        }
    }
}
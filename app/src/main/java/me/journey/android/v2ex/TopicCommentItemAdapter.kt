package me.journey.android.v2ex

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.zzhoujay.richtext.RichText
import me.journey.android.v2ex.bean.CommentBean
import me.journey.android.v2ex.utils.ImageLoader


class TopicCommentItemAdapter(private val mValues: ArrayList<CommentBean>?) : RecyclerView.Adapter<TopicCommentItemAdapter.ViewHolder>() {

    lateinit var view: View
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        view = LayoutInflater.from(parent.context)
                .inflate(R.layout.activity_topic_comment_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues?.get(position)
        RichText.fromHtml(mValues?.get(position)?.content)
                .into(holder.mTopicCommentContentView)
        holder.mUsernameView.text = mValues?.get(position)?.member?.username ?: ""
        holder.mCommentFloorView.text = mValues?.get(position)?.floor.toString()
        holder.mTopicReplyTimeView.text = mValues?.get(position)?.replyTime

        ImageLoader.displayImage(view, mValues?.get(position)?.member?.avatar,
                holder.mUserAvatarNormalView, R.mipmap.ic_launcher_round, 4)
        holder.mView.setOnClickListener {
            //            mListener?.onListFragmentInteraction(holder.mItem!!.id)
        }
//        holder.mUserAvatarNormalView.setOnClickListener {
//            MemberInfoActivity.start(holder.mItem!!.member!!.id , holder.mView.context)
//        }
    }

    override fun getItemCount(): Int {
        return mValues?.size ?: 0
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mUsernameView: TextView
        val mUserAvatarNormalView: ImageView
        val mCommentFloorView: TextView
        val mTopicCommentContentView: TextView
        val mTopicReplyTimeView: TextView
        var mItem: CommentBean? = null

        init {
            mUsernameView = mView.findViewById(R.id.topic_comment_item_username_tv)
            mUserAvatarNormalView = mView.findViewById(R.id.topic_comment_item_useravatar_iv)
            mCommentFloorView = mView.findViewById(R.id.topic_comment_item_floor_tv)
            mTopicCommentContentView = mView.findViewById(R.id.topic_comment_item_content_tv)
            mTopicReplyTimeView = mView.findViewById(R.id.topic_comment_item_reply_time_tv)
        }

    }
}

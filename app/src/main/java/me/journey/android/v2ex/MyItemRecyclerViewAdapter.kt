package me.journey.android.v2ex

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import me.journey.android.v2ex.TopicListFragment.OnListFragmentInteractionListener
import me.journey.android.v2ex.bean.TopicList
import me.journey.android.v2ex.utils.ImageLoader

class MyItemRecyclerViewAdapter(private val mValues: List<TopicList>,
                                private val mListener: OnListFragmentInteractionListener?) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    lateinit var view: View;
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_topic_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues[position]
        holder.mTitleView.text = mValues[position].title
        holder.mTopicTagView.text = mValues[position].node?.name ?: ""
        holder.mUsernameView.text = mValues[position].member?.username ?: ""
        holder.mCommentCountView.text = mValues[position].replies.toString()
//        Glide.with(view).load(mValues[position].member?.avatar_normal ?: "").into(holder.mUserAvatarNormalView)
        ImageLoader.displayImage(view, mValues[position].member?.avatar_normal ?: "", holder.mUserAvatarNormalView)
        holder.mView.setOnClickListener {
            mListener?.onListFragmentInteraction(holder.mItem!!)
        }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mUsernameView: TextView
        val mUserAvatarNormalView: ImageView
        val mTitleView: TextView
        val mCommentCountView: TextView
        val mTopicTagView: TextView
        var mItem: TopicList? = null

        init {
            mTitleView = mView.findViewById(R.id.topic_title_item_tv)
            mUsernameView = mView.findViewById(R.id.topic_username_item_tv)
            mCommentCountView = mView.findViewById(R.id.topic_replies_item_tv)
            mTopicTagView = mView.findViewById(R.id.topic_node_item_tv)
            mUserAvatarNormalView = mView.findViewById(R.id.topic_useravatar_item_iv)
        }

        override fun toString(): String {
            return super.toString() + " '" + mTitleView.text + "'"
        }
    }
}

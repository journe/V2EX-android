package me.journey.android.v2ex

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import me.journey.android.v2ex.TopicListFragment.OnListFragmentInteractionListener
import me.journey.android.v2ex.bean.Lastest

/**
 * [RecyclerView.Adapter] that can display a [DummyItem] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 * TODO: Replace the implementation with code for your data type.
 */
class MyItemRecyclerViewAdapter(private val mValues: List<Lastest>,
                                private val mListener: OnListFragmentInteractionListener?) : RecyclerView.Adapter<MyItemRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.fragment_topic_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mItem = mValues[position]
        holder.mTitleView.text = mValues[position].title
        holder.mTopicTagView.text = mValues[position].node?.name ?: ""
        holder.mUsernameView.text = mValues[position].member?.username ?: ""
        holder.mCommentCountView.text = mValues[position].replies.toString()

        holder.mView.setOnClickListener {
            mListener?.onListFragmentInteraction(holder.mItem!!)
        }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val mUsernameView: TextView
        val mTitleView: TextView
        val mCommentCountView: TextView
        val mTopicTagView: TextView
        var mItem: Lastest? = null

        init {
            mTitleView = mView.findViewById(R.id.topic_title_item_tv)
            mUsernameView = mView.findViewById(R.id.topic_username_item_tv)
            mCommentCountView = mView.findViewById(R.id.topic_replies_item_tv)
            mTopicTagView = mView.findViewById(R.id.topic_node_item_tv)
        }

        override fun toString(): String {
            return super.toString() + " '" + mTitleView.text + "'"
        }
    }
}

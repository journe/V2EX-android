package me.journey.android.v2ex.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import me.journey.android.v2ex.R
import me.journey.android.v2ex.bean.TopicListBean
import me.journey.android.v2ex.utils.Constants
import me.journey.android.v2ex.net.GetAPIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.android.synthetic.main.fragment_topic_item_list.*
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder
import me.journey.android.v2ex.utils.ImageLoader


/**
 * A fragment representing a list of Items.
 *
 *
 * Activities containing this fragment MUST implement the [OnListFragmentInteractionListener]
 * interface.
 */
/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
class TopicListFragment : Fragment() {
    private var mListener: OnListFragmentInteractionListener? = null

    private var mTopicType: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mTopicType = arguments!!.getInt(TOPIC_TYPE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_topic_item_list, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        topic_list_recycleview.layoutManager = LinearLayoutManager(context)
        topic_list_refreshview.isRefreshing = true
        getTopics()
        topic_list_refreshview.setOnRefreshListener {
            getTopics()
        }
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnListFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    fun getTopics() {
        val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = retrofit.create(GetAPIService::class.java)

        val call = when (mTopicType) {
            0 -> service.listLastestTopics()
            1 -> service.listHotTopics()
            else -> service.listLastestTopics()
        }
        call.enqueue(object : Callback<ArrayList<TopicListBean>> {
            override fun onResponse(call: Call<ArrayList<TopicListBean>>,
                                    response: Response<ArrayList<TopicListBean>>) {
                topic_list_recycleview.adapter = genTopicListAdapter(response)
                topic_list_recycleview.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
                topic_list_refreshview.isRefreshing = false
            }

            override fun onFailure(call: Call<ArrayList<TopicListBean>>, t: Throwable) {
                print(t.message)
                topic_list_refreshview.isRefreshing = false
            }
        })
    }

    private fun genTopicListAdapter(response: Response<ArrayList<TopicListBean>>): CommonAdapter<TopicListBean> {
        return object : CommonAdapter<TopicListBean>(activity,
                R.layout.fragment_topic_item, response.body()!!) {
            override fun convert(holder: ViewHolder?, t: TopicListBean?, position: Int) {
                holder!!.setText(R.id.topic_title_item_tv, t?.title)
                holder!!.setText(R.id.topic_node_item_tv, t?.node?.title ?: "")
                holder!!.setText(R.id.topic_username_item_tv, t?.member?.username ?: "")
                holder!!.setText(R.id.topic_replies_item_tv, t?.replies.toString())
                holder!!.setText(R.id.topic_reply_time_item_tv, caculateTime(t?.last_modified!!.toLong()))

                ImageLoader.displayImage(holder!!.convertView, t?.member?.avatar_large,
                        holder.getView(R.id.topic_useravatar_item_iv), R.mipmap.ic_launcher_round, 4)

                holder.setOnClickListener(R.id.topic_useravatar_item_iv, View.OnClickListener {
                    MemberInfoActivity.start(t!!.member!!.id, holder.convertView.context)
                })

                holder.convertView.setOnClickListener {
                    mListener?.onListFragmentInteraction(t!!.id)
                }
            }
        }
    }
    
    private fun caculateTime(ts: Long): String {
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(id: Int)
    }

    companion object {

        private val TOPIC_TYPE = "TOPIC_TYPE"

        fun newInstance(topicType: Int): TopicListFragment {
            val fragment = TopicListFragment()
            val args = Bundle()
            args.putInt(TOPIC_TYPE, topicType)
            fragment.arguments = args
            return fragment
        }
    }
}

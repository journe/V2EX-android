package me.journey.android.v2ex.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_topic_item_list.*
import me.journey.android.v2ex.JsoupTopicItemAdapter
import me.journey.android.v2ex.R
import me.journey.android.v2ex.bean.JsoupTopicListBean
import me.journey.android.v2ex.bean.TopicListBean
import me.journey.android.v2ex.utils.GetListNodeTopicsTask

/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
class JsoupTopicListFragment : Fragment() {
    private var mListener: OnListFragmentInteractionListener? = null

    private var mTopicType: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mTopicType = arguments!!.getInt(TOPIC_TYPE)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_topic_item_list, container, false)
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
//        if (context is OnListFragmentInteractionListener) {
//            mListener = context
//        } else {
//            throw RuntimeException(context!!.toString() + " must implement OnListFragmentInteractionListener")
//        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    fun getTopics() {
        val getListNodeTopicsTask = object : GetListNodeTopicsTask() {
            override fun onStart() {
            }

            override fun onFinish(topicList: ArrayList<JsoupTopicListBean>) {
                topic_list_recycleview.adapter = JsoupTopicItemAdapter(topicList!!, mListener)
                topic_list_recycleview.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
                topic_list_refreshview.isRefreshing = false
            }

        }
        getListNodeTopicsTask.execute("apple")
//        val retrofit = Retrofit.Builder()
//                .baseUrl(Constants.BASE_URL)
//                .addConverterFactory(GsonConverterFactory.create())
//                .build()
//        val service = retrofit.create(GetAPIService::class.java)
//
//        val call = when (mTopicType) {
//            0 -> service.listLastestTopics()
//            1 -> service.listHotTopics()
//            else -> service.listLastestTopics()
//        }
//        call.enqueue(object : Callback<ArrayList<TopicListBean>> {
//            override fun onResponse(call: Call<ArrayList<TopicListBean>>, response: Response<ArrayList<TopicListBean>>) {
//                topic_list_recycleview.adapter = JsoupTopicItemAdapter(response.body()!!, mListener)
//                topic_list_recycleview.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
//                topic_list_refreshview.isRefreshing = false
//            }
//
//            override fun onFailure(call: Call<ArrayList<TopicListBean>>, t: Throwable) {
//                print(t.message)
//                topic_list_refreshview.isRefreshing = false
//            }
//        })
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
        fun onListFragmentInteraction(item: TopicListBean)
    }

    companion object {

        private val TOPIC_TYPE = "TOPIC_TYPE"

        fun newInstance(topicType: Int): JsoupTopicListFragment {
            val fragment = JsoupTopicListFragment()
            val args = Bundle()
            args.putInt(TOPIC_TYPE, topicType)
            fragment.arguments = args
            return fragment
        }
    }
}
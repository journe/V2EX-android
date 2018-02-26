package me.journey.android.v2ex.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.orhanobut.logger.Logger
import me.journey.android.v2ex.R
import me.journey.android.v2ex.bean.TopicListBean
import me.journey.android.v2ex.utils.Constants
import me.journey.android.v2ex.utils.GetAPIService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
class TopicFragment : Fragment() {
    private var mListener: OnTopFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
//            mColumnCount = arguments.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_topic, container, false)
        // Set the adapter
        if (view is RecyclerView) {
            val context = view.getContext()
            view.layoutManager = LinearLayoutManager(context)
            getHotTopics(view)
        }
        return view
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnTopFragmentInteractionListener) {
            mListener = context
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    fun getHotTopics(view: RecyclerView) {
        //https://www.v2ex.com/api/topics/hot.json
        val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val service = retrofit.create(GetAPIService::class.java)
        val call = service.listLastest()
        call.enqueue(object : Callback<ArrayList<TopicListBean>> {
            override fun onResponse(call: Call<ArrayList<TopicListBean>>, response: Response<ArrayList<TopicListBean>>) {
//                view.adapter = MyItemRecyclerViewAdapter(response.body()!!, mListener)
//                view.addItemDecoration(DividerItemDecoration(activity, DividerItemDecoration.VERTICAL))
            }

            override fun onFailure(call: Call<ArrayList<TopicListBean>>, t: Throwable) {
                print(t.message)
            }
        })
        Logger.d(call)
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
    interface OnTopFragmentInteractionListener {
        // TODO: Update argument type and name
        fun OnTopFragmentInteractionListener(item: TopicListBean)
    }

    companion object {

        // TODO: Customize parameter initialization
        fun newInstance(columnCount: Int): TopicFragment {
            val fragment = TopicFragment()
            val args = Bundle()
//            args.putInt(ARG_COLUMN_COUNT, columnCount)
            fragment.arguments = args
            return fragment
        }
    }
}
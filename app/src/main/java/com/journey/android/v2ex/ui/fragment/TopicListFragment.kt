package com.journey.android.v2ex.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.journey.android.v2ex.R
import com.journey.android.v2ex.bean.api.TopicsListItemBean
import com.journey.android.v2ex.bean.jsoup.parser.TopicListParser
import com.journey.android.v2ex.net.RetrofitService
import com.journey.android.v2ex.ui.MemberInfoActivity
import com.journey.android.v2ex.utils.Constants
import com.journey.android.v2ex.utils.ImageLoader
import com.journey.android.v2ex.utils.TimeUtil.calculateTime
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder
import kotlinx.android.synthetic.main.fragment_topic_list.topic_list_recycleview
import kotlinx.android.synthetic.main.fragment_topic_list.topic_list_refreshview
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TopicListFragment : BaseFragment() {

  private var mNodeName: String = "all"

  interface NavInterface {
    fun navigate(id: Int)
  }

  lateinit var navInterface: NavInterface

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    if (arguments != null) {
      mNodeName = arguments!!.getString(
          TOPIC_NODE
      )
    }
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_topic_list, container, false)
  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)
    topic_list_recycleview.layoutManager = LinearLayoutManager(context)
    topic_list_recycleview.addItemDecoration(
        DividerItemDecoration(
            activity,
            DividerItemDecoration.VERTICAL
        )
    )
    topic_list_refreshview.isRefreshing = true
    getJsTopicsByNodeName(mNodeName)
    topic_list_refreshview.setOnRefreshListener {
      getJsTopicsByNodeName(mNodeName)
    }
  }

  private fun getJsTopicsByNodeName(node: String) {
    RetrofitService.getInstance()
        .getTopicsByNode(Constants.TAB + node)
        .enqueue(object : Callback<ResponseBody> {
          override fun onFailure(
            call: Call<ResponseBody>,
            t: Throwable
          ) {
          }

          override fun onResponse(
            call: Call<ResponseBody>,
            response: Response<ResponseBody>
          ) {
            val topicListItem =
              TopicListParser.parseTopicList(Jsoup.parse(response.body()!!.string()))

            topic_list_recycleview.adapter = genTopicListAdapter(topicListItem)
            topic_list_refreshview.isRefreshing = false
          }

        })
  }

  private fun genTopicListAdapter(topicListItem: ArrayList<TopicsListItemBean>): CommonAdapter<TopicsListItemBean> {
    return object : CommonAdapter<TopicsListItemBean>(
        activity,
        R.layout.fragment_topic_list_item, topicListItem
    ) {
      override fun convert(
        holder: ViewHolder?,
        t: TopicsListItemBean?,
        position: Int
      ) {
        holder?.setText(R.id.topic_title_item_tv, t?.title)
        holder?.setText(R.id.topic_node_item_tv, t?.node?.title ?: "")
        holder?.setText(R.id.topic_username_item_tv, t?.member?.username ?: "")
        holder?.setText(R.id.topic_replies_item_tv, t?.replies.toString())
        if (t?.last_modified != 0) {
          t?.last_modified_str = calculateTime(t?.last_modified!!.toLong())
        }

        holder?.setText(R.id.topic_reply_time_item_tv, t.last_modified_str ?: "")
        holder?.setVisible(R.id.topic_corner_star_iv, t.last_modified_str.equals("置顶"))

        ImageLoader.displayImage(
            holder!!.convertView, t.member?.avatar_large,
            holder.getView(R.id.topic_useravatar_item_iv), R.mipmap.ic_launcher_round, 4
        )

        holder.setOnClickListener(R.id.topic_useravatar_item_iv, View.OnClickListener {
          MemberInfoActivity.start(
              t.member!!.id, holder.convertView.context
          )
        })

        holder.convertView.setOnClickListener {
          navInterface.navigate(t.id)
        }
      }
    }
  }

  companion object {
    private val TAG = TopicListFragment::class.java.simpleName
    private const val TOPIC_NODE = "TOPIC_NODE"

    fun newInstance(
      topicType: String,
      navInterface: NavInterface
    ): TopicListFragment {
      val fragment = TopicListFragment()
      val args = Bundle()
      args.putString(TOPIC_NODE, topicType)
      fragment.arguments = args
      fragment.navInterface = navInterface
      return fragment
    }
  }
}

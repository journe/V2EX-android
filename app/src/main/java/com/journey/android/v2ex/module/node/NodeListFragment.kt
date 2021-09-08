package com.journey.android.v2ex.module.node

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.journey.android.v2ex.R
import com.journey.android.v2ex.base.BaseFragment
import com.journey.android.v2ex.model.api.NodeBean
import com.journey.android.v2ex.net.RetrofitService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class NodeListFragment : BaseFragment() {

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.activity_node_list, container, false)

  }

  @Inject
  lateinit var apiService: RetrofitService

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)
    doGetNodes()
  }

  private fun doGetNodes() {
    apiService
        .getNodesAll()
        .enqueue(object : Callback<ArrayList<NodeBean>> {
          override fun onFailure(
            call: Call<ArrayList<NodeBean>>,
            t: Throwable
          ) {
            t.printStackTrace()
          }

          override fun onResponse(
            call: Call<ArrayList<NodeBean>>,
            response: Response<ArrayList<NodeBean>>
          ) {
//            node_list_recycle.adapter = genAdapter(response.body()!!)
          }
        })
  }

//  private fun genAdapter(list: ArrayList<NodeBean>): CommonAdapter<NodeBean> {
//    return object : CommonAdapter<NodeBean>(context, R.layout.activity_node_list_item, list) {
//      override fun convert(
//        holder: ViewHolder?,
//        bean: NodeBean?,
//        position: Int
//      ) {
//        holder?.setText(R.id.node_item_tv, bean?.title)
////        ImageLoader.displayImage(
////            this@NodeListActivity, bean.url, holder.getView(R.id.node_item_iv) as ImageView
////        )
//      }
//    }
//  }
}

package com.journey.android.v2ex.ui

import android.os.Bundle
import com.journey.android.v2ex.R
import com.journey.android.v2ex.bean.api.NodeBean
import com.journey.android.v2ex.net.RetrofitService
import com.zhy.adapter.recyclerview.CommonAdapter
import com.zhy.adapter.recyclerview.base.ViewHolder
import kotlinx.android.synthetic.main.activity_node_list.node_list_recycle
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NodeListActivity : BaseActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_node_list)
    doGetNodes()
  }

  private fun doGetNodes() {
    RetrofitService.getInstance()
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
            node_list_recycle.adapter = genAdapter(response.body()!!)
          }
        })
  }

  private fun genAdapter(list: ArrayList<NodeBean>): CommonAdapter<NodeBean> {
    return object : CommonAdapter<NodeBean>(this, R.layout.activity_node_list_item, list) {
      override fun convert(
        holder: ViewHolder?,
        bean: NodeBean?,
        position: Int
      ) {
        holder?.setText(R.id.node_item_tv, bean?.title)
//        ImageLoader.displayImage(
//            this@NodeListActivity, bean.url, holder.getView(R.id.node_item_iv) as ImageView
//        )
      }
    }
  }
}

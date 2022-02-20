package com.journey.android.v2ex.module.node

import androidx.fragment.app.viewModels
import androidx.transition.TransitionManager
import com.journey.android.v2ex.base.BaseFragment
import com.journey.android.v2ex.databinding.FragmentNodeListBinding
import com.journey.android.v2ex.libs.extension.launch
import com.journey.android.v2ex.libs.transition.Stagger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class NodeListFragment : BaseFragment<FragmentNodeListBinding, NodeListViewModel>() {
	override val mViewModel: NodeListViewModel by viewModels()
	override fun FragmentNodeListBinding.initView() {
		mBinding.nodeListRefresh.setOnRefreshListener {
			mViewModel.refresh()
		}

		val adapter = NodeListAdapter()
		mBinding.nodeListRecycle.adapter = adapter
		val stagger = Stagger()
		launch({
			mViewModel.request()
				.collectLatest {
					mBinding.nodeListRefresh.isRefreshing = false
					TransitionManager.beginDelayedTransition(mBinding.nodeListRecycle, stagger)
					adapter.submitData(it)
				}
		})

	}

	override fun initObserve() {
	}

	override fun initRequestData() {
//		mViewModel.refresh()
	}

	private fun doGetNodes() {
//    apiService
//      .getNodesAll()
//      .enqueue(object : Callback<ArrayList<NodeBean>> {
//        override fun onFailure(
//          call: Call<ArrayList<NodeBean>>,
//          t: Throwable
//        ) {
//          t.printStackTrace()
//        }
//
//        override fun onResponse(
//          call: Call<ArrayList<NodeBean>>,
//          response: Response<ArrayList<NodeBean>>
//        ) {
//            node_list_recycle.adapter = genAdapter(response.body()!!)
//        }
//      })
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

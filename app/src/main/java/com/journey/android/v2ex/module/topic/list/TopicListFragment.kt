package com.journey.android.v2ex.module.topic.list

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import com.journey.android.v2ex.base.BaseFragment
import com.journey.android.v2ex.databinding.FragmentTopicListBinding
import com.journey.android.v2ex.libs.extension.launch
import com.journey.android.v2ex.libs.transition.Stagger
import com.journey.android.v2ex.net.RetrofitService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class TopicListFragment(private val nodeName: String) :
  BaseFragment<FragmentTopicListBinding, TopicListViewModel>() {

  override val mViewModel: TopicListViewModel by viewModels()

  val apiService: RetrofitService = RetrofitService.create()

  companion object {
    fun newInstance(
      topicType: String,
    ): TopicListFragment {
      return TopicListFragment(topicType)
    }
  }

  override fun FragmentTopicListBinding.initView() {
    //    exitTransition = Fade(Fade.OUT).apply {
//      duration = LARGE_EXPAND_DURATION / 2
//      interpolator = FAST_OUT_LINEAR_IN
//    }
//    reenterTransition = Fade(Fade.IN).apply {
//      duration = LARGE_COLLAPSE_DURATION / 2
//      startDelay = LARGE_COLLAPSE_DURATION / 2
//      interpolator = LINEAR_OUT_SLOW_IN
//    }

    mBinding.topicListRecycleview
      .addItemDecoration(
        DividerItemDecoration(
          activity,
          DividerItemDecoration.VERTICAL
        )
      )
    val adapter = TopicListAdapter()
    mBinding.topicListRefreshview.setOnRefreshListener {
      mViewModel.refresh(nodeName)
    }
    mBinding.topicListRecycleview.adapter = adapter
    // We animate item additions on our side, so disable it in RecyclerView.
    mBinding.topicListRecycleview.itemAnimator = object : DefaultItemAnimator() {
      override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
        dispatchAddFinished(holder)
        dispatchAddStarting(holder)
        return false
      }
    }
    val stagger = Stagger()
    launch({
      mViewModel.getTopicListBean(nodeName)
        .collectLatest {
          mBinding.topicListRefreshview.isRefreshing = false
          TransitionManager.beginDelayedTransition(mBinding.topicListRefreshview, stagger)
          adapter.submitData(it)
        }
    })

  }

  override fun initObserve() {
  }

  override fun initRequestData() {
    mViewModel.request(nodeName)
  }
}

package com.journey.android.v2ex.module.profile

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import com.journey.android.v2ex.base.BaseFragment
import com.journey.android.v2ex.databinding.FragmentTopicListBinding
import com.journey.android.v2ex.libs.extension.launch
import com.journey.android.v2ex.libs.transition.Stagger
import com.journey.android.v2ex.module.topic.list.TopicListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MemberTopicReplyFragment(private val username: String) :
	BaseFragment<FragmentTopicListBinding, MemberTopicReplyViewModel>() {

	override val mViewModel: MemberTopicReplyViewModel by viewModels()

	companion object {
		fun newInstance(
			topicType: String,
		): MemberTopicReplyFragment {
			return MemberTopicReplyFragment(topicType)
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
//		mBinding.topicListRefreshview.setOnRefreshListener {
//			mViewModel.refreshByNode(username)
//		}
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
//		launch({
//			mViewModel.getTopicListBeanByNode(username)
//				.collectLatest {
//					mBinding.topicListRefreshview.isRefreshing = false
//					TransitionManager.beginDelayedTransition(mBinding.topicListRefreshview, stagger)
//					adapter.submitData(it)
//				}
//		})

	}

	override fun initObserve() {
	}

	override fun initRequestData() {
//		mViewModel.requestByNode(username)
	}
}

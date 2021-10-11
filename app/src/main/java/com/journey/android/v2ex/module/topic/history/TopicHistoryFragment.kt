package com.journey.android.v2ex.module.topic.history

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import com.journey.android.v2ex.base.BaseFragment
import com.journey.android.v2ex.databinding.FragmentTopicListBinding
import com.journey.android.v2ex.libs.transition.Stagger
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TopicHistoryFragment : BaseFragment<FragmentTopicListBinding, TopicHistoryViewModel>() {

    override val mViewModel: TopicHistoryViewModel by viewModels()

    private val listAdapter = TopicHistoryAdapter()

    override fun FragmentTopicListBinding.initView() {

        mBinding.topicListRefreshview.apply {
            setOnRefreshListener {
                isRefreshing = false
            }

        }
        mBinding.topicListRecycleview.apply {
            adapter = listAdapter
            addItemDecoration(
                DividerItemDecoration(
                    activity,
                    DividerItemDecoration.VERTICAL
                )
            )

            // We animate item additions on our side, so disable it in RecyclerView.
            itemAnimator = object : DefaultItemAnimator() {
                override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
                    dispatchAddFinished(holder)
                    dispatchAddStarting(holder)
                    return false
                }
            }
        }
    }

    override fun initObserve() {
        val stagger = Stagger()
        lifecycleScope.launch {
            mViewModel.getListBeans()
                .collectLatest {
//                    TransitionManager.beginDelayedTransition(mBinding.topicListRefreshview, stagger)
                    listAdapter.submitData(it)
                }

        }
    }

    override fun initRequestData() {
    }

}

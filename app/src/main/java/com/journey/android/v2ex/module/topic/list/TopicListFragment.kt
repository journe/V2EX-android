package com.journey.android.v2ex.module.topic.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import com.journey.android.v2ex.R
import com.journey.android.v2ex.base.BaseFragment
import com.journey.android.v2ex.libs.transition.Stagger
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_topic_list.topic_list_recycleview
import kotlinx.android.synthetic.main.fragment_topic_list.topic_list_refreshview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TopicListFragment(private val topicType: String) : BaseFragment() {

  private val viewModel: TopicListViewModel by viewModels()
//  private val viewModel: TopicListViewModel by viewModels {
//    object : ViewModelProvider.Factory {
//      override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        return TopicListViewModel(
//            TopicListRepository(
//                AppDatabase.getInstance(),
//                lifecycleScope,
//                topicType
//            )
//        ) as T
//      }
//    }
//  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
//    exitTransition = Fade(Fade.OUT).apply {
//      duration = LARGE_EXPAND_DURATION / 2
//      interpolator = FAST_OUT_LINEAR_IN
//    }
//    reenterTransition = Fade(Fade.IN).apply {
//      duration = LARGE_COLLAPSE_DURATION / 2
//      startDelay = LARGE_COLLAPSE_DURATION / 2
//      interpolator = LINEAR_OUT_SLOW_IN
//    }
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
    topic_list_recycleview.addItemDecoration(
        DividerItemDecoration(
            activity,
            DividerItemDecoration.VERTICAL
        )
    )
    val adapter = TopicListAdapter()
    topic_list_recycleview.adapter = adapter
    // We animate item additions on our side, so disable it in RecyclerView.
    topic_list_recycleview.itemAnimator = object : DefaultItemAnimator() {
      override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
        dispatchAddFinished(holder)
        dispatchAddStarting(holder)
        return false
      }
    }
    val stagger = Stagger()
//    viewModel.getTopicListBean(topicType)
//        .observe(viewLifecycleOwner) {
//          topic_list_refreshview.isRefreshing = false
//          TransitionManager.beginDelayedTransition(topic_list_refreshview, stagger)
//          lifecycleScope.launch {
//            adapter.submitData(pagingData = it)
//          }
//        }
    lifecycleScope.launch {
      viewModel.getTopicListBean(topicType)
          .collectLatest {
            topic_list_refreshview.isRefreshing = false
            TransitionManager.beginDelayedTransition(topic_list_refreshview, stagger)
            adapter.submitData(it)
          }
    }
//    viewModel.itemPagedList.observe(viewLifecycleOwner, {
//      topic_list_refreshview.isRefreshing = false
//      TransitionManager.beginDelayedTransition(topic_list_refreshview, stagger)
//      adapter.submitData(it)
//    })
  }

  companion object {
    fun newInstance(
      topicType: String,
    ): TopicListFragment {
      return TopicListFragment(topicType)
    }
  }
}

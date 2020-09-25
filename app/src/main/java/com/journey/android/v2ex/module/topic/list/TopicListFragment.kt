package com.journey.android.v2ex.module.topic.list

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.Explode
import androidx.transition.Fade
import androidx.transition.Slide
import androidx.transition.TransitionManager
import com.google.android.material.appbar.AppBarLayout
import com.journey.android.v2ex.R
import com.journey.android.v2ex.base.BaseFragment
import com.journey.android.v2ex.room.AppDatabase
import com.journey.android.v2ex.libs.transition.FAST_OUT_LINEAR_IN
import com.journey.android.v2ex.libs.transition.LARGE_COLLAPSE_DURATION
import com.journey.android.v2ex.libs.transition.LARGE_EXPAND_DURATION
import com.journey.android.v2ex.libs.transition.LINEAR_OUT_SLOW_IN
import com.journey.android.v2ex.libs.transition.Stagger
import com.journey.android.v2ex.libs.transition.plusAssign
import com.journey.android.v2ex.libs.transition.transitionTogether
import kotlinx.android.synthetic.main.fragment_topic_list.topic_list_recycleview
import kotlinx.android.synthetic.main.fragment_topic_list.topic_list_refreshview

class TopicListFragment(val topicType: String) : BaseFragment() {

  private val viewModel: TopicListViewModel by viewModels {
    object : ViewModelProvider.Factory {
      override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TopicListViewModel(
            TopicListRepository(
                AppDatabase.getInstance(),
                lifecycleScope,
                topicType
            )
        ) as T
      }
    }
  }

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
    viewModel.itemPagedList.observe(viewLifecycleOwner, Observer {
      topic_list_refreshview.isRefreshing = false
      TransitionManager.beginDelayedTransition(topic_list_refreshview, stagger)
      adapter.submitList(it)
    })
  }

  companion object {
    fun newInstance(
      topicType: String,
    ): TopicListFragment {
      return TopicListFragment(topicType)
    }
  }
}

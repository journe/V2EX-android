package com.journey.android.v2ex.module.topic.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionManager
import com.journey.android.v2ex.base.BaseFragment
import com.journey.android.v2ex.databinding.FragmentTopicListBinding
import com.journey.android.v2ex.libs.extension.launch
import com.journey.android.v2ex.libs.transition.Stagger
import com.journey.android.v2ex.model.api.TopicsListItemBean
import com.journey.android.v2ex.net.RetrofitService
import com.journey.android.v2ex.net.parser.TopicListParser
import com.journey.android.v2ex.room.AppDatabase
import com.journey.android.v2ex.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.invoke
import org.jsoup.Jsoup

@AndroidEntryPoint
class TopicListFragment(private val nodeName: String) : BaseFragment() {

  override val binding get() = _binding!! as FragmentTopicListBinding

  private val viewModel: TopicListViewModel by viewModels()

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
  ): View {
    _binding = FragmentTopicListBinding.inflate(inflater, container, false)
    return binding.root
  }

  val apiService: RetrofitService = RetrofitService.create()

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)
    binding.topicListRecycleview
        .addItemDecoration(
            DividerItemDecoration(
                activity,
                DividerItemDecoration.VERTICAL
            )
        )
    val adapter = TopicListAdapter()
    binding.topicListRecycleview.adapter = adapter
    // We animate item additions on our side, so disable it in RecyclerView.
    binding.topicListRecycleview.itemAnimator = object : DefaultItemAnimator() {
      override fun animateAdd(holder: RecyclerView.ViewHolder?): Boolean {
        dispatchAddFinished(holder)
        dispatchAddStarting(holder)
        return false
      }
    }
    val stagger = Stagger()

    launch({
//      val result = apiService.getTopicsByNodeSuspend(Constants.TAB + nodeName)
//      val listItemBean = TopicListParser.parseTopicList(
//          Jsoup.parse(result.string())
//      )
//      insertToDb(listItemBean.map { it.apply { tab = nodeName } })

      viewModel.getTopicListBean(nodeName)
          .collectLatest {
            binding.topicListRefreshview.isRefreshing = false
            TransitionManager.beginDelayedTransition(binding.topicListRefreshview, stagger)
            adapter.submitData(it)
          }
    })
  }

  private suspend fun insertToDb(body: List<TopicsListItemBean>) = Dispatchers.IO {
    val db = AppDatabase.getInstance()
    db.runInTransaction {
      val start = db.topicListDao()
          .getNextIndex()
      val items = body.mapIndexed { index, child ->
        child.indexInResponse = start + index
        child.tab = nodeName
        child
      }
      db.topicListDao()
          .insert(items)
    }
  }

  companion object {
    fun newInstance(
      topicType: String,
    ): TopicListFragment {
      return TopicListFragment(topicType)
    }
  }
}

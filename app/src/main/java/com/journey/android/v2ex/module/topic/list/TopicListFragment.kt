package com.journey.android.v2ex.module.topic.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import com.journey.android.v2ex.R
import com.journey.android.v2ex.base.BaseFragment
import com.journey.android.v2ex.room.AppDatabase
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

  interface NavInterface {
    fun navigate(id: Int)
  }

  lateinit var navInterface: NavInterface

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
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
    val adapter = TopicListAdapter(navInterface)
    topic_list_recycleview.adapter = adapter
    viewModel.itemPagedList.observe(viewLifecycleOwner, Observer {
      topic_list_refreshview.isRefreshing = false
      adapter.submitList(it)
    })
  }

  companion object {
    fun newInstance(
      topicType: String,
      navInterface: NavInterface
    ): TopicListFragment {
      val fragment = TopicListFragment(topicType)
      fragment.navInterface = navInterface
      return fragment
    }
  }
}

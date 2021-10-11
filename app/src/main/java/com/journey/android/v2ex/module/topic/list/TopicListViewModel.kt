package com.journey.android.v2ex.module.topic.list

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.journey.android.v2ex.base.BaseViewModel
import com.journey.android.v2ex.libs.extension.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopicListViewModel @Inject constructor(
  private val repository: TopicListRepository
) : BaseViewModel() {

  fun request(tabName: String) {
    launch({
      repository.requestData(tabName)
    })

  }

  fun getTopicListBean(tabName: String) = repository.getList(tabName).cachedIn(viewModelScope)

  fun refresh(tabName: String) {
    launch({
      repository.refresh(tabName)
    })

  }
}
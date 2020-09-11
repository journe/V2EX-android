package com.journey.android.v2ex.module.topic.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.PagedList
import com.journey.android.v2ex.base.BaseViewModel
import com.journey.android.v2ex.model.api.RepliesShowBean

class TopicDetailViewModel(private val repository: TopicDetailRepository) : BaseViewModel() {

  val topicDetailBean = liveData {
    emit(repository.getTopicDetail())
  }
  val commentPagedList: LiveData<PagedList<RepliesShowBean>> = repository.getComments(100)

//  fun refresh() {
////        itemPagedList.value?.dataSource?.invalidate()
//    launch({
//      repository.refresh()
//    }, {
//      Logger.d(it.message)
//    })
//  }
}
package com.journey.android.v2ex.module.topic.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.PagedList
import com.journey.android.v2ex.base.BaseViewModel
import com.journey.android.v2ex.libs.extension.launch
import com.journey.android.v2ex.model.api.RepliesShowBean
import com.journey.android.v2ex.room.AppDatabase
import com.orhanobut.logger.Logger

class TopicDetailViewModel(private val repository: TopicDetailRepository) : BaseViewModel() {

  init {
    launch({
      repository.initTopicDetail()
    })
  }

  val topicShowBean = liveData {
    emitSource(repository.getTopicsShowBean())
  }
  val repliesShowBean: LiveData<PagedList<RepliesShowBean>> = repository.getComments(100)

  fun initTopicDetail() {
    launch({
      repository.initTopicDetail()
    })
  }
//  fun refresh() {
////        itemPagedList.value?.dataSource?.invalidate()
//    launch({
//      repository.refresh()
//    }, {
//      Logger.d(it.message)
//    })
//  }
}
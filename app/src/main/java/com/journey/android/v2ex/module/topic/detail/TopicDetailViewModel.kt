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

  val detailBean = liveData {
    emitSource(repository.getTopicDetailBean())
  }

  val topicShowBean = liveData {
    emitSource(repository.getTopicsShowBean())
  }
  val repliesShowBean: LiveData<PagedList<RepliesShowBean>> = repository.getComments(100)

  fun getreply(topicId: Int) {
    launch({
      val a = AppDatabase.getInstance()
          .topicRepliesDao()
          .getTopicRepliesSuspend(topicId)
      Logger.d(a.size)
    }, {})
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
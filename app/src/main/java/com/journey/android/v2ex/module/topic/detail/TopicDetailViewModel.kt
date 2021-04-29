package com.journey.android.v2ex.module.topic.detail

import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.journey.android.v2ex.base.BaseViewModel
import com.journey.android.v2ex.libs.extension.launch
import com.journey.android.v2ex.room.AppDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class TopicDetailViewModel @Inject constructor(private val repository: TopicDetailRepository) :
  BaseViewModel() {
//  val topicShowBean = liveData {
//    emitSource(repository.getTopicsShowBean())
//  }

//  var topicShowBean: LiveData<TopicsShowBean>
//  val repliesShowBean: LiveData<PagingData<RepliesShowBean>> = repository.getComments(100)

  fun initTopicDetail(topicId: Int) {
    launch({
      repository.initTopicDetail(topicId)
    })

  }

  fun getTopicReplyBean(topicId: Int) = Pager(
    PagingConfig(pageSize = 20)
  ) {
    TopicDetailReplyDataSource(AppDatabase.getInstance(), topicId)
  }.flow
    .cachedIn(viewModelScope)

  fun getTopicsShowBean(topicId: Int) = liveData {
    repository.getTopicsShowBean(topicId)
      .collectLatest {
        emit(it)
      }
  }

}
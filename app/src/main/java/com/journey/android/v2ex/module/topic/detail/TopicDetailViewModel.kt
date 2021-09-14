package com.journey.android.v2ex.module.topic.detail

import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.journey.android.v2ex.base.BaseViewModel
import com.journey.android.v2ex.libs.extension.launch
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

  fun getTopicReplyPager(topicId: Int) =
    repository.getReplyBeanPagerFlow(topicId, pageSize = 20)
      .cachedIn(viewModelScope)

  fun getTopicsShowBean(topicId: Int) = liveData {
    repository.getTopicsShowBean(topicId)
      .collectLatest {
        emit(it)
      }
  }

}
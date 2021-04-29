package com.journey.android.v2ex.module.topic.list

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.journey.android.v2ex.base.BaseViewModel
import com.journey.android.v2ex.room.AppDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopicListViewModel @Inject constructor(val db: AppDatabase) : BaseViewModel() {

  fun getTopicListBean(tabName: String) =
    Pager(PagingConfig(pageSize = 20)) {
//      TopicListDataSource(tabName)
      db.topicListDao()
          .pagingSource(tabName)
    }.flow.cachedIn(viewModelScope)

}
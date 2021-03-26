package com.journey.android.v2ex.module.topic.list

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.journey.android.v2ex.base.BaseViewModel
import com.journey.android.v2ex.room.AppDatabase

class TopicListViewModel : BaseViewModel() {

  val db: AppDatabase = AppDatabase.getInstance()
//  val itemPagedList: LiveData<PagingData<TopicsListItemBean>> = repository.getFeeds(20)

//  fun refresh() {
////        itemPagedList.value?.dataSource?.invalidate()
//    launch({
//      repository.refresh()
//    }, {
//      Logger.d(it.message)
//    })
//  }

//  fun getTopicListBean(tabName: String) =
//    Pager(PagingConfig(pageSize = 20)) {
//      TopicListDataSource(tabName)
//    }.flow.cachedIn(viewModelScope).asLiveData()

  fun getTopicListBean(tabName: String) =
    Pager(PagingConfig(pageSize = 20)) {
//      TopicListDataSource(tabName)
      db.topicListDao()
          .pagingSource(tabName)
    }.flow.cachedIn(viewModelScope)

}
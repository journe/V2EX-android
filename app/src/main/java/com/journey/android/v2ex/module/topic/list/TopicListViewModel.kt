package com.journey.android.v2ex.module.topic.list

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.journey.android.v2ex.base.BaseViewModel
import com.journey.android.v2ex.model.api.TopicsListItemBean
import com.journey.android.v2ex.libs.extension.launch
import com.orhanobut.logger.Logger

class TopicListViewModel(private val repository: TopicListRepository) : BaseViewModel() {

  val itemPagedList: LiveData<PagedList<TopicsListItemBean>> = repository.getFeeds(20)

  fun refresh() {
//        itemPagedList.value?.dataSource?.invalidate()
    launch({
        repository.refresh()
    }, {
        Logger.d(it.message)
    })

  }
}
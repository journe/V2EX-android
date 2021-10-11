package com.journey.android.v2ex.module.topic.history

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.journey.android.v2ex.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TopicHistoryViewModel @Inject constructor(
    private val repository: TopicHistoryRepository
) : BaseViewModel() {

    fun getListBeans() = repository.getList().cachedIn(viewModelScope)

}
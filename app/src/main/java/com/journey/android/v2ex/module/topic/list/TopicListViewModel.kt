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

	fun requestByNode(tabName: String) {
		launch({
			repository.requestByNode(tabName)
		})

	}

	fun getTopicListBeanByNode(tabName: String) =
		repository.getDataSourceByTab(tabName).cachedIn(viewModelScope)

	fun refreshByNode(tabName: String) {
		launch({
			repository.refreshByNode(tabName)
		})
	}

	fun requestByUser(username: String) {
		launch({
			repository.requestByUser(username)
		})

	}

	fun getTopicListBeanByUser(username: String) =
		repository.getDataSourceByUser(username).cachedIn(viewModelScope)

	fun refreshByUser(username: String) {
		launch({
			repository.refreshByNode(username)
		})
	}


}
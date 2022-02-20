package com.journey.android.v2ex.module.node

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.journey.android.v2ex.base.BaseViewModel
import com.journey.android.v2ex.libs.extension.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NodeListViewModel @Inject constructor(
	private val repository: NodeListRepository
) : BaseViewModel() {

	fun request() {
		launch({
			repository.requestDataLocal()
		})
	}

	fun refresh() {
		launch({
			repository.refresh()
		})
	}

}
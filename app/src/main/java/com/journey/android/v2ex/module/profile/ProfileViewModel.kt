package com.journey.android.v2ex.module.profile

import androidx.lifecycle.viewModelScope
import com.journey.android.v2ex.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
	private val repository: ProfileRepository
) : BaseViewModel() {

	fun requestMemberBean(name: String) = repository.requestMemberBean(name)

	fun refresh(name: String) {
		viewModelScope.launch {
			repository.refresh(name)
		}
	}
}
package com.journey.android.v2ex.module.debug

import com.journey.android.v2ex.base.BaseViewModel
import com.journey.android.v2ex.libs.extension.launch
import com.journey.android.v2ex.module.login.LoginRepository
import com.journey.android.v2ex.module.node.NodeListRepository
import com.journey.android.v2ex.room.AppDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by journey on 2020/9/17.
 */
@HiltViewModel
class TestViewModel @Inject constructor(
	private val loginRepository: LoginRepository,
	private val nodeListRepository: NodeListRepository,
	val db: AppDatabase
) : BaseViewModel() {
	fun deleteCache() {
		db.topicListDao().deleteAll()
	}

	fun getProfile() {
		launch({
			loginRepository.getProfile()
		})
	}

	fun getAllNodes() {
		launch({
			nodeListRepository.requestDataLocal()
		})

	}
}
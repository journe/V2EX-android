package com.journey.android.v2ex

import com.journey.android.v2ex.base.BaseViewModel
import com.journey.android.v2ex.module.login.LoginRepository
import com.journey.android.v2ex.room.AppDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by journey on 2020/9/17.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
  val loginRepository: LoginRepository,
  val db: AppDatabase
) :
  BaseViewModel() {
  fun deleteCache() {
    db.topicListDao().deleteAll()
  }

}
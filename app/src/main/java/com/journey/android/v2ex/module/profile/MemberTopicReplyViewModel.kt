package com.journey.android.v2ex.module.profile

import com.journey.android.v2ex.base.BaseViewModel
import com.journey.android.v2ex.model.jsoup.MemBerReplyItemBean
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MemberTopicReplyViewModel @Inject constructor(
	private val repository: ProfileRepository
) : BaseViewModel() {

//	fun getReply(name: String): List<MemBerReplyItemBean> =
//		repository.requestMemberReply(name = name)
}
package com.journey.android.v2ex.module.login.data

import com.journey.android.v2ex.model.Avatar

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoginResult(
	val username: String = "",
	val avatar: Avatar = Avatar(""),
	var problem: String = ""
)

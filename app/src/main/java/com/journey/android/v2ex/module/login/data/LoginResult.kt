package com.journey.android.v2ex.module.login.data

/**
 * Authentication result : success (user details) or error message.
 */
data class LoginResult(
  val success: LoggedInUser? = null,
  val error: Int? = null
)

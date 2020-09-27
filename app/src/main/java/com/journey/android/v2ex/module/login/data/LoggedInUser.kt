package com.journey.android.v2ex.module.login.data

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
data class LoggedInUser(
  val avatar: String = "",
  val displayName: String = ""
)

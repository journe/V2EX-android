package com.journey.android.v2ex.module.login.data

/**
 * User details post authentication that is exposed to the UI
 */
data class LoginForm(
  val name: String,
  val pass: String,
  val captcha: String
)

package com.journey.android.v2ex.module.login

import android.graphics.Bitmap
import com.journey.android.v2ex.model.jsoup.SignInFormData
import com.journey.android.v2ex.module.login.Result.Success
import com.journey.android.v2ex.module.login.data.LoggedInUser
import com.journey.android.v2ex.module.login.data.LoginForm
import com.orhanobut.logger.Logger
import javax.inject.Inject

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository @Inject constructor(private val dataSource: LoginDataSource) {
  // in-memory cache of the loggedInUser object


  fun logout() {
    dataSource.logout()
  }

  fun getLoginUserInfo() {
    dataSource.getUserInfoByName()
  }

  suspend fun loadSignPage(): SignInFormData {
    return dataSource.loadSignPage()
  }

  suspend fun getCaptcha(captchaUrl: String): Bitmap? {
    return dataSource.getCaptcha(captchaUrl)
  }

  suspend fun login(
    signInFormData: SignInFormData,
    loginForm: LoginForm,
  ): Result<LoggedInUser> {
    // handle login
    val result = dataSource.login(signInFormData, loginForm)
    if (result is Success) {
      setLoggedInUser(result.data)
    }
    return result
  }

  private fun setLoggedInUser(loggedInUser: LoggedInUser) {
    Logger.d(loggedInUser.displayName)
  }
}

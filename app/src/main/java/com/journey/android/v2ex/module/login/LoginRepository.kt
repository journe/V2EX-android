package com.journey.android.v2ex.module.login

import com.journey.android.v2ex.model.jsoup.SignInFormData
import com.journey.android.v2ex.module.login.Result.Success
import com.journey.android.v2ex.module.login.data.LoggedInUser
import com.journey.android.v2ex.module.login.data.LoginForm
import com.orhanobut.logger.Logger
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class LoginRepository(private val dataSource: LoginDataSource) {
  // in-memory cache of the loggedInUser object
  var user: LoggedInUser? = null
    private set

  val isLoggedIn: Boolean
    get() = user != null

  init {
    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    user = null
  }

  fun logout() {
    user = null
    dataSource.logout()
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
    this.user = loggedInUser
    Logger.d(loggedInUser.displayName)
    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
  }
}

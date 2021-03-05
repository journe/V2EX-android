package com.journey.android.v2ex.module.login

import com.journey.android.v2ex.model.jsoup.SignInFormData
import com.journey.android.v2ex.module.login.Result.Error
import com.journey.android.v2ex.module.login.Result.Success
import com.journey.android.v2ex.module.login.data.LoggedInUser
import com.journey.android.v2ex.module.login.data.LoginForm
import com.journey.android.v2ex.net.RetrofitRequest
import com.journey.android.v2ex.net.parser.MoreParser
import com.journey.android.v2ex.utils.PrefStore
import org.jsoup.Jsoup
import java.io.IOException
import java.util.HashMap

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {
  suspend fun login(
    signInFormData: SignInFormData,
    loginForm: LoginForm
  ): Result<LoggedInUser> {
    return try {
      val fakeUser = doLogin(signInFormData, loginForm.name, loginForm.pass, loginForm.captcha)
      Success(fakeUser)
    } catch (e: Throwable) {
      Error(IOException("Error logging in", e))
    }
  }

  private suspend fun doLogin(
    signInFormData: SignInFormData,
    emailStr: String,
    passwordStr: String,
    captcha: String
  ): LoggedInUser {
    PrefStore.instance.userName = emailStr
    PrefStore.instance.userPass = passwordStr
    val map = HashMap<String, String>()
    map[signInFormData.account] = emailStr
    map[signInFormData.password] = passwordStr
    map[signInFormData.captcha] = captcha
    map["once"] = signInFormData.once.toString()
    map["next"] = "/mission"
    val signIn = RetrofitRequest.apiService.postSigninSuspend(map)
    val more = RetrofitRequest.apiService.getMoreSuspend()
    return if (MoreParser.isLogin(Jsoup.parse(more.string()))) {
      MoreParser.getLoggedInUser(Jsoup.parse(more.string()))
    } else {
      LoggedInUser()
    }
  }

  fun logout() {
    RetrofitRequest.cleanCookies()
  }
}


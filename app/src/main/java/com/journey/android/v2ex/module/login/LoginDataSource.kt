package com.journey.android.v2ex.module.login

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.journey.android.v2ex.model.Result
import com.journey.android.v2ex.model.jsoup.SignInFormData
import com.journey.android.v2ex.model.Result.Error
import com.journey.android.v2ex.model.Result.Success
import com.journey.android.v2ex.module.login.data.LoggedInUser
import com.journey.android.v2ex.module.login.data.LoginForm
import com.journey.android.v2ex.net.RetrofitService
import com.journey.android.v2ex.net.parser.LoginParser
import com.journey.android.v2ex.net.parser.MoreParser
import com.journey.android.v2ex.utils.PrefStore
import org.jsoup.Jsoup
import java.io.IOException
import java.util.HashMap
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
@Singleton
class LoginDataSource @Inject constructor(private val apiService: RetrofitService) {
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
    map["next"] = "/more"
//    val signIn = apiService.postSigninSuspend(map)
    val more = apiService.getMoreSuspend()
    return if (MoreParser.isLogin(Jsoup.parse(more.string()))) {
      MoreParser.getLoggedInUser(Jsoup.parse(more.string()))
    } else {
      LoggedInUser()
    }
  }

  suspend fun loadSignPage(): SignInFormData {
      val response = apiService.getLoginSuspend()
      val doc = Jsoup.parse(response.string())
      return LoginParser.parseSignInData(doc)
  }

  suspend fun getCaptcha(captchaUrl: String): Bitmap? {
      val response = apiService.getCaptchaSuspend(captchaUrl)
    return BitmapFactory.decodeStream(
        response.byteStream()
    )
  }

  fun getUserInfoByName(){

  }

  fun logout() {
    RetrofitService.cleanCookies()
  }
}


package com.journey.android.v2ex.module.login

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.journey.android.v2ex.R
import com.journey.android.v2ex.base.BaseViewModel
import com.journey.android.v2ex.libs.ToastUtils
import com.journey.android.v2ex.libs.extension.launch
import com.journey.android.v2ex.model.jsoup.SignInFormData
import com.journey.android.v2ex.module.login.data.LoginForm
import com.journey.android.v2ex.module.login.data.LoginFormState
import com.journey.android.v2ex.module.login.data.LoginResult
import com.journey.android.v2ex.net.RetrofitRequest
import com.journey.android.v2ex.net.parser.LoginParser
import org.jsoup.Jsoup

/**
 * Created by journey on 2020/9/25.
 */
class LoginViewModel(private val loginRepository: LoginRepository) : BaseViewModel() {

  private val _loginForm = MutableLiveData<LoginFormState>()
  val loginFormState: LiveData<LoginFormState> = _loginForm

  private val _loginResult = MutableLiveData<LoginResult>()
  val loginResult: LiveData<LoginResult> = _loginResult

  private val signInFormData = liveData<SignInFormData> {
    val response = RetrofitRequest.apiService.getLoginSuspend()
    val doc = Jsoup.parse(response.string())
    emit(LoginParser.parseSignInData(doc))
  }

  val captchaBitmap = signInFormData.switchMap {
    var bitmap: LiveData<Bitmap> = MutableLiveData()
    launch({ bitmap = getCaptcha(it.genCaptcha()) })
    bitmap
  }

  private suspend fun getCaptcha(captchaUrl: String): LiveData<Bitmap> {
    val response = RetrofitRequest.apiService.getCaptchaSuspend(captchaUrl)
    val bitmap = BitmapFactory.decodeStream(
        response.byteStream()
    )
    if (bitmap == null) {
      RetrofitRequest.cleanCookies()
      ToastUtils.showShortToast(R.string.toast_load_captcha_failed)
    }
    return MutableLiveData(bitmap)

//    launch({
//      val response = RetrofitRequest.apiService.getCaptchaSuspend(captchaUrl)
//      val bitmap = BitmapFactory.decodeStream(
//          response.byteStream()
//      )
//      if (bitmap == null) {
//        RetrofitRequest.cleanCookies()
//        ToastUtils.showShortToast(R.string.toast_load_captcha_failed)
//      }else{
//        captchaBitmap.value = bitmap
//      }
//    }, {
//      ToastUtils.showShortToast(R.string.toast_load_captcha_failed)
//    })
  }

  fun login(
    username: String,
    password: String,
    captcha: String
  ) {
    launch({
      val result = loginRepository.login(
          signInFormData.value!!,
          LoginForm(name = username, pass = password, captcha = captcha)
      )
      if (result is Result.Success) {
        _loginResult.value =
          LoginResult(success = result.data)
      } else {
        _loginResult.value = LoginResult(error = R.string.error_incorrect_password)
      }
    })

  }

  fun loginDataChanged(
    username: String,
    password: String
  ) {
    if (!isUserNameValid(username)) {
      _loginForm.value = LoginFormState(usernameError = R.string.error_field_required)
    } else if (!isPasswordValid(password)) {
      _loginForm.value = LoginFormState(passwordError = R.string.error_invalid_password)
    } else {
      _loginForm.value = LoginFormState(isDataValid = true)
    }
  }

  // A placeholder username validation check
  private fun isUserNameValid(username: String): Boolean {
    return if (username.contains('@')) {
      Patterns.EMAIL_ADDRESS.matcher(username)
          .matches()
    } else {
      username.isNotBlank()
    }
  }

  // A placeholder password validation check
  private fun isPasswordValid(password: String): Boolean {
    return password.length > 5
  }

}
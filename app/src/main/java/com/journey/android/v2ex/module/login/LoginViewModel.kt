package com.journey.android.v2ex.module.login

import android.graphics.Bitmap
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.journey.android.v2ex.R
import com.journey.android.v2ex.base.BaseViewModel
import com.journey.android.v2ex.libs.ToastUtils
import com.journey.android.v2ex.libs.extension.launch
import com.journey.android.v2ex.model.Result
import com.journey.android.v2ex.model.jsoup.SignInFormData
import com.journey.android.v2ex.module.login.data.LoggedInUser
import com.journey.android.v2ex.module.login.data.LoginForm
import com.journey.android.v2ex.module.login.data.LoginFormState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by journey on 2020/9/25.
 */
@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) :
    BaseViewModel() {

  private val _loginForm = MutableLiveData<LoginFormState>()
  val loginFormState: LiveData<LoginFormState> = _loginForm

  private val _loginResult = MutableLiveData<Result<LoggedInUser>>()
  val loginResult: LiveData<Result<LoggedInUser>> = _loginResult

  val signInFormData = MutableLiveData<SignInFormData>()

  val captchaBitmap = MutableLiveData<Bitmap>()

  val loadingState = MutableLiveData(true)

  fun loadSignPage() {
    launch({
      signInFormData.postValue(loginRepository.loadSignPage())
    })
  }

  fun getCaptcha(captchaUrl: String) {
    launch({
      captchaBitmap.postValue(loginRepository.getCaptcha(captchaUrl))
      loadingState.postValue(false)
    }, {
      ToastUtils.showShortToast(R.string.toast_load_captcha_failed)
      loadingState.postValue(false)
    })

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
      _loginResult.postValue(result)
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
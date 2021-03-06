package com.journey.android.v2ex.ui.fragment

import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.DrawableCompat.setTint
import com.bumptech.glide.Glide
import com.journey.android.v2ex.R
import com.journey.android.v2ex.bean.jsoup.LoginBean
import com.journey.android.v2ex.bean.jsoup.parser.LoginParser
import com.journey.android.v2ex.bean.jsoup.parser.MoreParser
import com.journey.android.v2ex.net.HttpStatus
import com.journey.android.v2ex.net.RetrofitRequest
import com.journey.android.v2ex.net.RetrofitService
import com.journey.android.v2ex.utils.ImageLoader
import com.journey.android.v2ex.utils.PrefStore
import com.journey.android.v2ex.utils.ToastUtils
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_login.login_account
import kotlinx.android.synthetic.main.fragment_login.login_captcha
import kotlinx.android.synthetic.main.fragment_login.login_captcha_iv
import kotlinx.android.synthetic.main.fragment_login.login_password
import kotlinx.android.synthetic.main.fragment_login.login_refresh
import kotlinx.android.synthetic.main.fragment_login.sign_in_button
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.HashMap

class LoginFragment : BaseFragment() {

  private lateinit var mLoginBean: LoginBean

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_login, container, false)

  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)
    sign_in_button.setOnClickListener {
      attemptLogin()
    }
    login_captcha_iv.setOnClickListener {
      getCaptcha(mLoginBean.genCaptcha())
    }
    login_refresh.setOnRefreshListener {
      doGetLoginTask()
    }
    doGetLoginTask()
    login_account.setText(PrefStore.instance.userName)
    login_password.setText(PrefStore.instance.userPass)
  }

  private fun doGetLoginTask() {
    RetrofitRequest.retrofit
        .getLogin()
        .enqueue(object : Callback<ResponseBody> {
          override fun onFailure(
            call: Call<ResponseBody>,
            t: Throwable
          ) {
            Logger.d(t.stackTrace)
            showProgress(false)
          }

          override fun onResponse(
            call: Call<ResponseBody>,
            response: Response<ResponseBody>
          ) {
            showProgress(false)
            val doc = Jsoup.parse(response.body()!!.string())
            mLoginBean = LoginParser.parseLoginBean(doc)
            getCaptcha(mLoginBean.genCaptcha())
          }
        })
  }

  private fun getCaptcha(captchaUrl: String) {

    RetrofitRequest.retrofit
        .getCaptcha(captchaUrl)
        .enqueue(object : Callback<ResponseBody> {
          override fun onFailure(
            call: Call<ResponseBody>,
            t: Throwable
          ) {
            Logger.d(t.message)
          }

          override fun onResponse(
            call: Call<ResponseBody>,
            response: Response<ResponseBody>
          ) {
            if (response.code() == 200) {
              val bitmap = BitmapFactory.decodeStream(response.body()!!.byteStream())
              if (bitmap == null) {
                RetrofitRequest.cleanCookies()
                ToastUtils.showShortToast(R.string.toast_load_captcha_failed)
              }
              Glide.with(this@LoginFragment)
                  .load(bitmap)
                  .placeholder(R.drawable.ic_sync_white_24dp)
                  .error(R.drawable.ic_sync_problem_white_24dp)
                  .into(login_captcha_iv)
            }
          }
        })
  }

  private fun attemptLogin() {
    // Reset errors.
    login_account.error = null
    login_password.error = null

    // Store values at the time of the login attempt.
    val emailStr = login_account.text.toString()
    val passwordStr = login_password.text.toString()
    val captcha = login_captcha.text.toString()

    var cancel = false
    var focusView: View? = null

    // Check for a valid password, if the user entered one.
    if (!TextUtils.isEmpty(passwordStr) && !isPasswordValid(passwordStr)) {
      login_password.error = getString(R.string.error_invalid_password)
      focusView = login_password
      cancel = true
    }

    // Check for a valid email address.
    if (TextUtils.isEmpty(emailStr)) {
      login_account.error = getString(R.string.error_field_required)
      focusView = login_account
      cancel = true
    } else if (!isEmailValid(emailStr)) {
      login_account.error = getString(R.string.error_invalid_email)
      focusView = login_account
      cancel = true
    }

    if (cancel) {
      // There was an error; don't attempt login and focus the first
      // form field with an error.
      focusView?.requestFocus()
    } else {
      // Show a progress spinner, and kick off a background task to
      // perform the user login attempt.
      showProgress(true)
      doLogin(emailStr, passwordStr, captcha)
    }
  }

  private fun doLogin(
    emailStr: String,
    passwordStr: String,
    captcha: String
  ) {
    showProgress(true)
    PrefStore.instance.userName = emailStr
    PrefStore.instance.userPass = passwordStr
    val map = HashMap<String, String>()
    map[mLoginBean.account] = emailStr
    map[mLoginBean.password] = passwordStr
    map[mLoginBean.captcha] = captcha
    map["once"] = mLoginBean.once.toString()
    map["next"] = "/mission"
    RetrofitRequest.retrofit
        .postSignin(map)
        .enqueue(object : Callback<ResponseBody> {
          override fun onFailure(
            call: Call<ResponseBody>,
            t: Throwable
          ) {
            showProgress(false)
            login_password.error = getString(R.string.error_incorrect_password)
            login_password.requestFocus()
          }

          override fun onResponse(
            call: Call<ResponseBody>,
            response: Response<ResponseBody>
          ) {
            showProgress(false)
            if (response.code() != HttpStatus.SC_MOVED_TEMPORARILY) {
              Logger.d("code should not be " + response.code(), response)
            }
            doGetMore()
          }
        })
  }

  private fun doGetMore() {
    RetrofitRequest.retrofit
        .getMore()
        .enqueue(object : Callback<ResponseBody> {
          override fun onFailure(
            call: Call<ResponseBody>,
            t: Throwable
          ) {
            showProgress(false)
            Logger.d(t.stackTrace)
          }

          override fun onResponse(
            call: Call<ResponseBody>,
            response: Response<ResponseBody>
          ) {
            showProgress(false)
            if (MoreParser.isLogin(Jsoup.parse(response.body()!!.string()))) {
              Logger.d("success")
//              finish()
            }
          }
        })

  }

  private fun isEmailValid(email: String): Boolean {
    return email.isNotEmpty()
  }

  private fun isPasswordValid(password: String): Boolean {
    return password.length > 4
  }

  /**
   * Shows the progress UI and hides the login form.
   */
  private fun showProgress(show: Boolean) {
    login_refresh.isRefreshing = show
  }

}

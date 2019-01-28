package com.journey.android.v2ex.ui

import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import com.journey.android.v2ex.R
import com.journey.android.v2ex.bean.js.LoginBean
import com.journey.android.v2ex.net.GetAPIService
import com.journey.android.v2ex.net.HttpStatus
import com.journey.android.v2ex.utils.UserPreferenceUtil
import com.journey.android.v2ex.utils.parser.LoginParser
import com.journey.android.v2ex.utils.parser.MoreParser
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_login.login_account
import kotlinx.android.synthetic.main.activity_login.login_captcha
import kotlinx.android.synthetic.main.activity_login.login_captcha_iv
import kotlinx.android.synthetic.main.activity_login.login_password
import kotlinx.android.synthetic.main.activity_login.login_refresh
import kotlinx.android.synthetic.main.activity_login.sign_in_button
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.nio.charset.Charset
import java.nio.charset.UnsupportedCharsetException
import java.util.HashMap

class LoginActivity : BaseActivity() {

  private lateinit var mLoginBean: LoginBean

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)

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
    login_account.setText(UserPreferenceUtil.getValue(UserPreferenceUtil.NAME, ""), false)
    login_password.setText(UserPreferenceUtil.getValue(UserPreferenceUtil.PASS, ""))
  }

  private fun doGetLoginTask() {
    showProgress(true)
    GetAPIService.getInstance()
        .getLogin()
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
            val source = response.body()
                ?.source()
            source?.request(java.lang.Long.MAX_VALUE) // Buffer the entire body.
            val buffer = source?.buffer()
            var charset = Charset.defaultCharset()
            val contentType = response.body()
                ?.contentType()
            if (contentType != null) {
              try {
                charset = contentType.charset(charset)
              } catch (e: UnsupportedCharsetException) {
                e.printStackTrace()
              }

            }
            val rBody = buffer?.clone()
                ?.readString(charset)
            val doc = Jsoup.parse(rBody)
            mLoginBean = LoginParser.parseLoginBean(doc)
            getCaptcha(mLoginBean.genCaptcha())
          }
        })
  }

  private fun getCaptcha(captchaUrl: String) {
    GetAPIService.getInstance()
        .getCaptcha(captchaUrl)
        .enqueue(object : Callback<ResponseBody> {
          override fun onFailure(
            call: Call<ResponseBody>,
            t: Throwable
          ) {

          }

          override fun onResponse(
            call: Call<ResponseBody>,
            response: Response<ResponseBody>
          ) {
            if (response.code() == 200) {
              login_captcha_iv.setImageBitmap(
                  BitmapFactory.decodeStream(response.body()!!.byteStream())
              )
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
    UserPreferenceUtil.setValue(UserPreferenceUtil.NAME, emailStr)
    UserPreferenceUtil.setValue(UserPreferenceUtil.PASS, passwordStr)
    val map = HashMap<String, String>()
    map[mLoginBean.account] = emailStr
    map[mLoginBean.password] = passwordStr
    map[mLoginBean.captcha] = captcha
    map["once"] = mLoginBean.once.toString()
    map["next"] = "/mission"
    GetAPIService.getInstance()
        .postLogin(map)
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
    GetAPIService.getInstance()
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
            val source = response.body()
                ?.source()
            source?.request(java.lang.Long.MAX_VALUE) // Buffer the entire body.
            val buffer = source?.buffer()
            var charset = Charset.defaultCharset()
            val contentType = response.body()
                ?.contentType()
            if (contentType != null) {
              try {
                charset = contentType.charset(charset)
              } catch (e: UnsupportedCharsetException) {
                e.printStackTrace()
              }

            }
            val rBody = buffer?.clone()
                ?.readString(charset)
            if (MoreParser.isLogin(Jsoup.parse(rBody))) {
              finish()
            }
          }
        })

  }

  private fun isEmailValid(email: String): Boolean {
    //TODO: Replace this with your own logic
    return email.isNotEmpty()
  }

  private fun isPasswordValid(password: String): Boolean {
    //TODO: Replace this with your own logic
    return password.length > 4
  }

  /**
   * Shows the progress UI and hides the login form.
   */
  private fun showProgress(show: Boolean) {
    login_refresh.isRefreshing = show
  }

}

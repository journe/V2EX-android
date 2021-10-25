package com.journey.android.v2ex.module.login

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.google.common.net.HttpHeaders
import com.journey.android.v2ex.libs.extension.toJsoup
import com.journey.android.v2ex.model.Result
import com.journey.android.v2ex.model.Result.Error
import com.journey.android.v2ex.model.jsoup.SignInFormData
import com.journey.android.v2ex.module.login.data.LoginForm
import com.journey.android.v2ex.module.login.data.LoginResult
import com.journey.android.v2ex.net.HttpStatus
import com.journey.android.v2ex.net.RetrofitService
import com.journey.android.v2ex.net.parser.CommonParser
import com.journey.android.v2ex.net.parser.LoginParser
import com.journey.android.v2ex.utils.Constants
import com.journey.android.v2ex.utils.PrefStore
import com.journey.android.v2ex.utils.RequestException
import org.jsoup.Jsoup
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
@Singleton
class LoginNetDataSource @Inject constructor(private val apiService: RetrofitService) {
	suspend fun login(
		signInFormData: SignInFormData,
		loginForm: LoginForm
	): Result<LoginResult> {
		try {

//			val nextUrl = "/more"
			val nextUrl = "/mission"
			PrefStore.instance.userName = loginForm.name
			PrefStore.instance.userPass = loginForm.pass
			val map = HashMap<String, String>()
			map[signInFormData.account] = loginForm.name
			map[signInFormData.password] = loginForm.pass
			map[signInFormData.captcha] = loginForm.captcha
			map["once"] = signInFormData.once.toString()
			map["next"] = nextUrl

			val response = apiService.postLoginSuspend(map)

			// v2ex will redirect if login success
			if (response.code() != HttpStatus.SC_MOVED_TEMPORARILY) {
				if (response.code() == HttpStatus.SC_OK) {
					val loginDoc = response.body()!!.toJsoup()
					val errorResult = LoginParser.parseLoginError(loginDoc)
					return Error(Exception(errorResult.problem, null))
				} else {
					throw RequestException("code should not be " + response.code(), response.raw())
				}
			}

			val location = checkNotNull(response.headers()["location"]) {
				"Redirect response missing location"
			}
			if (location != nextUrl) {
				throw RequestException("location should not be $location", response.raw())
			}

			val redirectDoc = apiService.requestSuspend(Constants.BASE_URL + location).toJsoup()

			return if (CommonParser.isLogin(redirectDoc)) {
				val successResult = CommonParser.loginResult(redirectDoc)
				Result.Success(successResult)
			} else {
				val loginDoc = response.body()!!.toJsoup()
				val errorResult = LoginParser.parseLoginError(loginDoc)
				Error(Exception(errorResult.problem, null))
			}

		} catch (e: Throwable) {
			return Error(Exception(e.message, e))
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

	fun getUserInfoByName() {

	}

}


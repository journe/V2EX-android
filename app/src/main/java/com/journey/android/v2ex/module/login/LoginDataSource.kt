package com.journey.android.v2ex.module.login

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.journey.android.v2ex.libs.SpKey
import com.journey.android.v2ex.libs.SpUtils
import com.journey.android.v2ex.libs.extension.toJsoup
import com.journey.android.v2ex.model.Result
import com.journey.android.v2ex.model.Result.Error
import com.journey.android.v2ex.model.Result.Success
import com.journey.android.v2ex.model.jsoup.SignInFormData
import com.journey.android.v2ex.module.login.data.LoggedInUser
import com.journey.android.v2ex.module.login.data.LoginForm
import com.journey.android.v2ex.net.RetrofitService
import com.journey.android.v2ex.net.parser.LoginParser
import com.journey.android.v2ex.net.parser.MoreParser
import com.journey.android.v2ex.utils.PrefStore
import org.jsoup.Jsoup
import java.io.IOException
import java.util.*
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
        try {

            PrefStore.instance.userName = loginForm.name
            PrefStore.instance.userPass = loginForm.pass
            val map = HashMap<String, String>()
            map[signInFormData.account] = loginForm.name
            map[signInFormData.password] = loginForm.pass
            map[signInFormData.captcha] = loginForm.captcha
            map["once"] = signInFormData.once.toString()
            map["next"] = "/more"
            val loginDoc = apiService.postLoginSuspend(map).toJsoup()
            val moreDoc = apiService.getMoreSuspend().toJsoup()

            return if (MoreParser.isLogin(moreDoc)) {
                SpUtils.put(SpKey.IS_LOGIN, true)
                val loggedInUser = MoreParser.getLoggedInUser(moreDoc)
                Success(loggedInUser)
            } else {
                val signInResultData = LoginParser.parseSignInResult(loginDoc)
                Error(IOException(signInResultData.problem, null))
            }

        } catch (e: Throwable) {
            return Error(IOException(e.message, e))
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

    fun logout() {
        SpUtils.put(SpKey.IS_LOGIN, false)
        RetrofitService.cleanCookies()
    }
}


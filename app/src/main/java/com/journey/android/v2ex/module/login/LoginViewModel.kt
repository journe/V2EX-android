package com.journey.android.v2ex.module.login

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.bumptech.glide.Glide
import com.journey.android.v2ex.R
import com.journey.android.v2ex.base.BaseViewModel
import com.journey.android.v2ex.libs.ToastUtils
import com.journey.android.v2ex.libs.extension.launch
import com.journey.android.v2ex.model.jsoup.LoginBean
import com.journey.android.v2ex.net.RetrofitRequest
import com.journey.android.v2ex.net.parser.LoginParser
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_login.login_captcha_iv
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by journey on 2020/9/25.
 */
class LoginViewModel : BaseViewModel() {

  private val loginBean = liveData<LoginBean> {
    val response = RetrofitRequest.apiService.getLoginSuspend()
    val doc = Jsoup.parse(response.string())
    emit(LoginParser.parseLoginBean(doc))
  }

  val captchaBitmap = loginBean.switchMap {
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

}
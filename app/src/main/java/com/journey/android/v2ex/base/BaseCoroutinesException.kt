package com.journey.android.v2ex.base

import com.journey.android.v2ex.libs.ToastUtils
import kotlinx.coroutines.CoroutineExceptionHandler
import retrofit2.HttpException
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

/**
 * Create by Crazy on 2020/12/7
 * BaseCoroutinesException : 协程错误捕捉
 */
class BaseCoroutinesException(
  private val handler: (Throwable) -> Unit = {},
  private var httpError: ((Throwable) -> Unit)? = null
) : AbstractCoroutineContextElement(CoroutineExceptionHandler),
    CoroutineExceptionHandler {

  override fun handleException(
    context: CoroutineContext,
    exception: Throwable
  ) {
    when (exception) {
      is HttpException -> netError(exception)
      else -> {
        handler.invoke(exception)
        exception.printStackTrace()
      }
    }
  }

  private fun netError(exception: HttpException) {
    if (httpError != null) {
      httpError?.invoke(exception)
    } else {
      exception.printStackTrace()
      ToastUtils.showLongToastSafe(
          "网络错误：${exception.code()}，" +
              "原因：${exception.message()}," +
              "url:${
                exception.response()
                    ?.raw()?.request?.url
              }"
      )
    }
  }
}

open class CommonCoroutinesException(
  val handler: (Throwable) -> Unit,
  private var httpError: ((Throwable) -> Unit)? = null
) : AbstractCoroutineContextElement(CoroutineExceptionHandler), CoroutineExceptionHandler {
  override fun handleException(
    context: CoroutineContext,
    exception: Throwable
  ) {
    when (exception) {
      is HttpException -> {
        if (httpError != null) {
          httpError?.invoke(exception)
        } else {
          exception.printStackTrace()
          ToastUtils.showLongToastSafe(
              "网络错误：${exception.code()}，" +
                  "原因：${exception.message()}," +
                  "url:${
                    exception.response()
                        ?.raw()?.request?.url
                  }"
          )
        }
      }
      else -> {
        handler.invoke(exception)
        exception.printStackTrace()
      }
    }
  }
}
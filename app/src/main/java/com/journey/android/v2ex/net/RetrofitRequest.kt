package com.journey.android.v2ex.net

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.journey.android.v2ex.V2exApplication
import com.journey.android.v2ex.utils.Constants
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by journey on 2019-10-17.
 */
object RetrofitRequest {
  val retrofit: RetrofitService
  val client: OkHttpClient
  private var cookieJar: PersistentCookieJar =
    PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(V2exApplication.instance))

  init {
    client = OkHttpClient.Builder()
        .apply {
          cache(buildCache())
          connectTimeout(10, TimeUnit.SECONDS)
          writeTimeout(10, TimeUnit.SECONDS)
          readTimeout(30, TimeUnit.SECONDS)
          followRedirects(false)
          cookieJar(cookieJar)
        }
        .addInterceptor { chain ->
          val request = chain.request()
              .newBuilder()
              .addHeader("User-Agent", Constants.USER_AGENT_ANDROID)
              .build()
          val response = chain.proceed(request)
          response
        }
        .build()

    retrofit = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RetrofitService::class.java)
  }

  private fun buildCache(): Cache? {
    val cacheDir = File(V2exApplication.instance.cacheDir, "webCache")
    val cacheSize = 16 * 1024 * 1024
    return Cache(cacheDir, cacheSize.toLong())
  }
}
package com.journey.android.v2ex.net

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.journey.android.v2ex.BuildConfig
import com.journey.android.v2ex.model.api.*
import com.journey.android.v2ex.utils.Constants
import com.journey.android.v2ex.utils.Utils
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.io.File
import java.util.concurrent.TimeUnit

/**
 * Created by journey on 2017/12/29.
 */
interface RetrofitService {

	companion object {

		private val cookieJar =
			PersistentCookieJar(SetCookieCache(), SharedPrefsCookiePersistor(Utils.getContext()))
		private val logInterceptor = HttpLoggingInterceptor(HttpLogger()).apply {
			level = HttpLoggingInterceptor.Level.BODY
		}
		private val client: OkHttpClient = OkHttpClient.Builder()
			.apply {
				cache(buildCache())
				connectTimeout(10, TimeUnit.SECONDS)
				writeTimeout(10, TimeUnit.SECONDS)
				readTimeout(30, TimeUnit.SECONDS)
				followRedirects(false)
				cookieJar(cookieJar)
				addInterceptor { chain ->
					val request = chain.request()
						.newBuilder()
						.addHeader("User-Agent", Constants.USER_AGENT_ANDROID)
						.build()
					val response = chain.proceed(request)
					response
				}
				if (BuildConfig.DEBUG) {
					addNetworkInterceptor(logInterceptor)
				}
			}
			.build()

		fun create(): RetrofitService {
			return Retrofit.Builder()
				.baseUrl(Constants.BASE_URL)
				.client(client)
				.addConverterFactory(GsonConverterFactory.create())
				.build()
				.create(RetrofitService::class.java)
		}

		fun cleanCookies() {
			cookieJar.clear()
		}

		private fun buildCache(): Cache? {
			val cacheDir = File(Utils.getContext().cacheDir, "webCache")
			val cacheSize = 16 * 1024 * 1024
			return Cache(cacheDir, cacheSize.toLong())
		}
	}

	@GET(Constants.SITE_INFO)
	fun getSiteInfo(): Call<SiteInfoBean>

	@GET(Constants.SITE_STATS)
	fun getSiteStats(): Call<SiteStatsBean>

	@GET(Constants.NODES_ALL)
	fun getNodesAll(): Call<ArrayList<NodeBean>>

	@GET(Constants.NODES_SHOW)
	fun getNodesShow(@Query("id") id: Int): Call<NodeBean>

	@GET(Constants.TOPICS_HOT)
	fun listHotTopics(): Call<ArrayList<TopicsListItemBean>>

	@GET(Constants.TOPICS_LATEST)
	fun listLatestTopics(): Call<ArrayList<TopicsListItemBean>>

	@GET(Constants.TOPICS_SHOW)
	suspend fun getTopicsById(@Query("id") id: Int): List<TopicsShowBean>

	@GET(Constants.TOPICS_SHOW)
	fun getTopicsByUser(@Query("username") username: String): Call<ArrayList<TopicsShowBean>>

	@GET(Constants.TOPICS_SHOW)
	fun getTopicsByNode(@Query("node_id") nodeId: Int): Call<ArrayList<TopicsListItemBean>>

	@GET(Constants.REPLIES)
	suspend fun getRepliesSuspend(
		@Query("topic_id") id: Int,
		@Query("page") page: Int,
		@Query("page_size") pageSize: Int
	): List<RepliesShowBean>

	@GET(Constants.MEMBERS)
	fun getMemberInfoByID(@Query("id") id: Int): Call<MemberBean>

	@GET(Constants.MEMBERS)
	fun getMemberInfo(@Query("username") name: String): Call<MemberBean>

	@GET(Constants.SIGNIN)
	fun getLogin(): Call<ResponseBody>

	@GET(Constants.SIGNIN)
	suspend fun getLoginSuspend(): ResponseBody

	@FormUrlEncoded
	@Headers("Referer: https://www.v2ex.com/signin")
	@POST(Constants.SIGNIN)
	suspend fun postLoginSuspend(@FieldMap hashMap: HashMap<String, String>): Response<ResponseBody>

	@Streaming
	@GET
	suspend fun getCaptchaSuspend(@Url url: String): ResponseBody

	@GET(Constants.MORE)
	suspend fun getMoreSuspend(): ResponseBody

	@GET(Constants.BALANCE)
	fun getBalance(): Call<ResponseBody>

	@GET
	fun getTopicsByNode(@Url url: String): Call<ResponseBody>

	@GET
	suspend fun requestSuspend(@Url url: String): ResponseBody

	@GET("/t/{id}")
	suspend fun getTopicByIdSuspend(
		@Path("id") id: Int,
		@Query("p") page: Int = 1
	): ResponseBody

}
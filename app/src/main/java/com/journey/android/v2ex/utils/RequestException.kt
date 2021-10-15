package com.journey.android.v2ex.utils

import com.google.common.base.Strings
import com.google.common.net.HttpHeaders
import okhttp3.Response

class RequestException @JvmOverloads constructor(
	message: String?,
	response: Response,
	tr: Throwable? = null
) : RuntimeException(message, tr) {
	val response: Response

	/**
	 * error info in html
	 */
	var errorHtml: String? = null
	var isShouldLogged = false

	constructor(response: Response) : this(null, response) {}
	constructor(response: Response, tr: Throwable?) : this(null, response, tr) {}

	val code: Int
		get() = response.code
	override val message: String
		get() {
			val message = Strings.nullToEmpty(super.message)
			val sb = StringBuilder(message)
			sb.append(", url: ").append(response.request.url)
			sb.append(", code: ").append(response.code)
			if (response.isRedirect) {
				sb.append(", location: ")
				sb.append(response.header(HttpHeaders.LOCATION))
			}
			return sb.toString()
		}

	init {
		isShouldLogged = true
		this.response = response
	}
}
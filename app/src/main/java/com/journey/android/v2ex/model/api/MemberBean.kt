package com.journey.android.v2ex.model.api

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * status : found
 * id : 1
 * url : http://www.v2ex.com/member/Livid
 * username : Livid
 * website :
 * twitter :
 * psn :
 * github :
 * btc :
 * location :
 * tagline : Gravitated and spellbound
 * bio : Remember the bigger green
 * avatar_large : //cdn.v2ex.com/avatar/c4ca/4238/1_large.png?m=1466415272
 * created : 1272203146
 */
@Entity
data class MemberBean(
  @PrimaryKey
  var id: Int = 0,
  var status: String? = null,
  var username: String = "",
  var url: String? = null,
  var website: String? = null,
  var twitter: String? = null,
  var psn: String? = null,
  var github: String? = null,
  var btc: String? = null,
  var location: String? = null,
  var tagline: String? = null,
  var bio: String? = null,
  var avatar_large: String? = null,
  var created: Int = 0
)


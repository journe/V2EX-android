package me.journey.android.v2ex.bean

import android.os.Parcel
import android.os.Parcelable

class MemberInfoBean() :Parcelable{

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
     * avatar_mini : //cdn.v2ex.com/avatar/c4ca/4238/1_mini.png?m=1466415272
     * avatar_normal : //cdn.v2ex.com/avatar/c4ca/4238/1_normal.png?m=1466415272
     * avatar_large : //cdn.v2ex.com/avatar/c4ca/4238/1_large.png?m=1466415272
     * created : 1272203146
     */

    var status: String? = null
    var id: Int = 0
    var url: String? = null
    var username: String? = null
    var website: String? = null
    var twitter: String? = null
    var psn: String? = null
    var github: String? = null
    var btc: String? = null
    var location: String? = null
    var tagline: String? = null
    var bio: String? = null
    var avatar_mini: String? = null
    var avatar_normal: String? = null
    var avatar_large: String? = null
    var created: Int = 0

    constructor(parcel: Parcel) : this() {
        status = parcel.readString()
        id = parcel.readInt()
        url = parcel.readString()
        username = parcel.readString()
        website = parcel.readString()
        twitter = parcel.readString()
        psn = parcel.readString()
        github = parcel.readString()
        btc = parcel.readString()
        location = parcel.readString()
        tagline = parcel.readString()
        bio = parcel.readString()
        avatar_mini = parcel.readString()
        avatar_normal = parcel.readString()
        avatar_large = parcel.readString()
        created = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(status)
        parcel.writeInt(id)
        parcel.writeString(url)
        parcel.writeString(username)
        parcel.writeString(website)
        parcel.writeString(twitter)
        parcel.writeString(psn)
        parcel.writeString(github)
        parcel.writeString(btc)
        parcel.writeString(location)
        parcel.writeString(tagline)
        parcel.writeString(bio)
        parcel.writeString(avatar_mini)
        parcel.writeString(avatar_normal)
        parcel.writeString(avatar_large)
        parcel.writeInt(created)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MemberInfoBean> {
        override fun createFromParcel(parcel: Parcel): MemberInfoBean {
            return MemberInfoBean(parcel)
        }

        override fun newArray(size: Int): Array<MemberInfoBean?> {
            return arrayOfNulls(size)
        }
    }
}

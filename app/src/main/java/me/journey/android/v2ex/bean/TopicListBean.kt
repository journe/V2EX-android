package me.journey.android.v2ex.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by journey on 2018/1/23.
 */

class TopicListBean() : Parcelable {

    /**
     * id : 425341
     * title : Python 培训需要多少钱？老男孩学 Python
     * url : http://www.v2ex.com/t/425341
     * content : 文章来源：www.oldboyedu.com 10000+，开启逆袭模式，冲击年薪 40 万，做一名新时代不被淘汰的运维工程师！
     * replies : 0
     * member : {"id":276089,"username":"lnh2017","tagline":"","avatar_mini":"
     * node : {"id":90,"name":"python","title":"Python","title_alternative":"Python","url"
     * created : 1516704999
     * last_modified : 1516704999
     * last_touched : 1516690419
     */

    var id: Int = 0
    var title: String? = null
    var url: String? = null
    var content: String? = null
    var replies: Int = 0
    var member: MemberBean? = null
    var node: NodeBean? = null
    var created: Int = 0
    var last_modified: Int = 0
    var last_touched: Int = 0

    constructor(parcel: Parcel) : this() {
        id = parcel.readInt()
        title = parcel.readString()
        url = parcel.readString()
        content = parcel.readString()
        replies = parcel.readInt()
        created = parcel.readInt()
        last_modified = parcel.readInt()
        last_touched = parcel.readInt()
    }

    class MemberBean() :Parcelable{
        /**
         * id : 276089
         * username : lnh2017
         * tagline :
         * avatar_mini : //v2ex.assets.uxengine.net/gravatar/7c01d6df4eee5548fff82b33051e2d40?s=24&d=retro
         * avatar_normal : //v2ex.assets.uxengine.net/gravatar/7c01d6df4eee5548fff82b33051e2d40?s=48&d=retro
         * avatar_large : //v2ex.assets.uxengine.net/gravatar/7c01d6df4eee5548fff82b33051e2d40?s=73&d=retro
         */

        var id: Int = 0
        var username: String? = null
        var tagline: String? = null
        var avatar_mini: String? = null
        var avatar_normal: String? = null
        var avatar_large: String? = null

        constructor(parcel: Parcel) : this() {
            id = parcel.readInt()
            username = parcel.readString()
            tagline = parcel.readString()
            avatar_mini = parcel.readString()
            avatar_normal = parcel.readString()
            avatar_large = parcel.readString()
        }

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeInt(id)
            parcel.writeString(username)
            parcel.writeString(tagline)
            parcel.writeString(avatar_mini)
            parcel.writeString(avatar_normal)
            parcel.writeString(avatar_large)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<MemberBean> {
            override fun createFromParcel(parcel: Parcel): MemberBean {
                return MemberBean(parcel)
            }

            override fun newArray(size: Int): Array<MemberBean?> {
                return arrayOfNulls(size)
            }
        }
    }

    class NodeBean {
        /**
         * id : 90
         * name : python
         * title : Python
         * title_alternative : Python
         * url : http://www.v2ex.com/go/python
         * topics : 8521
         * avatar_mini : //v2ex.assets.uxengine.net/navatar/8613/985e/90_mini.png?m=1516102119
         * avatar_normal : //v2ex.assets.uxengine.net/navatar/8613/985e/90_normal.png?m=1516102119
         * avatar_large : //v2ex.assets.uxengine.net/navatar/8613/985e/90_large.png?m=1516102119
         */

        var id: Int = 0
        var name: String? = null
        var title: String? = null
        var title_alternative: String? = null
        var url: String? = null
        var topics: Int = 0
        var avatar_mini: String? = null
        var avatar_normal: String? = null
        var avatar_large: String? = null
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(url)
        parcel.writeString(content)
        parcel.writeInt(replies)
        parcel.writeInt(created)
        parcel.writeInt(last_modified)
        parcel.writeInt(last_touched)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TopicListBean> {
        override fun createFromParcel(parcel: Parcel): TopicListBean {
            return TopicListBean(parcel)
        }

        override fun newArray(size: Int): Array<TopicListBean?> {
            return arrayOfNulls(size)
        }
    }
}

package com.journey.android.v2ex.bean.api

/**
 * Created by journey on 2018/8/8.
 */
class TopicsShowBean {

    /**
     * id : 1000
     * title : Google App Engine x MobileMe
     * url : http://www.v2ex.com/t/1000
     * content : 从现在开始，新上传到 V2EX 的头像将存储在 MobileMe iDisk 中。这是 V2EX 到目前为之所用到的第三个云。
     *
     * 得益于这个架构升级，现在头像上传之后，将立刻在全站的所有页面更新。
     * content_rendered : 从现在开始，新上传到 V2EX 的头像将存储在 MobileMe iDisk 中。这是 V2EX 到目前为之所用到的第三个云。
     * <br></br>
     * <br></br>得益于这个架构升级，现在头像上传之后，将立刻在全站的所有页面更新。
     * replies : 14
     * member : {"id":1,"username":"Livid","tagline":"Beautifully Advance","avatar_mini":"//cdn.v2ex.com/avatar/c4ca/4238/1_mini.png?m=1401650222","avatar_normal":"//cdn.v2ex.com/avatar/c4ca/4238/1_normal.png?m=1401650222","avatar_large":"//cdn.v2ex.com/avatar/c4ca/4238/1_large.png?m=1401650222"}
     * node : {"id":1,"name":"babel","title":"Project Babel","url":"http://www.v2ex.com/go/babel","topics":1102,"avatar_mini":"//cdn.v2ex.com/navatar/c4ca/4238/1_mini.png?m=1370299418","avatar_normal":"//cdn.v2ex.com/navatar/c4ca/4238/1_normal.png?m=1370299418","avatar_large":"//cdn.v2ex.com/navatar/c4ca/4238/1_large.png?m=1370299418"}
     * created : 1280192329
     * last_modified : 1335004238
     * last_touched : 1280285385
     */

    var id: Int = 0
    var title: String? = null
    var url: String? = null
    var content: String? = null
    var content_rendered: String? = null
    var replies: Int = 0
    var member: MemberBean? = null
    var node: NodeBean? = null
    var created: Int = 0
    var last_modified: Int = 0
    var last_touched: Int = 0

    class MemberBean {
        /**
         * id : 1
         * username : Livid
         * tagline : Beautifully Advance
         * avatar_mini : //cdn.v2ex.com/avatar/c4ca/4238/1_mini.png?m=1401650222
         * avatar_normal : //cdn.v2ex.com/avatar/c4ca/4238/1_normal.png?m=1401650222
         * avatar_large : //cdn.v2ex.com/avatar/c4ca/4238/1_large.png?m=1401650222
         */

        var id: Int = 0
        var username: String? = null
        var tagline: String? = null
        var avatar_mini: String? = null
        var avatar_normal: String? = null
        var avatar_large: String? = null
    }

    class NodeBean {
        /**
         * id : 1
         * name : babel
         * title : Project Babel
         * url : http://www.v2ex.com/go/babel
         * topics : 1102
         * avatar_mini : //cdn.v2ex.com/navatar/c4ca/4238/1_mini.png?m=1370299418
         * avatar_normal : //cdn.v2ex.com/navatar/c4ca/4238/1_normal.png?m=1370299418
         * avatar_large : //cdn.v2ex.com/navatar/c4ca/4238/1_large.png?m=1370299418
         */

        var id: Int = 0
        var name: String? = null
        var title: String? = null
        var url: String? = null
        var topics: Int = 0
        var avatar_mini: String? = null
        var avatar_normal: String? = null
        var avatar_large: String? = null
    }
}

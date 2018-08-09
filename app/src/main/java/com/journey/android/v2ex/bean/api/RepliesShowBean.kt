package com.journey.android.v2ex.bean.api

/**
 * Created by journey on 2018/8/8.
 */
class RepliesShowBean {

    /**
     * id : 1
     * thanks : 5
     * content : 很高兴看到 v2ex 又回来了，等了你半天发第一贴了，憋死我了。
     *
     *
     * nice work~
     * content_rendered : 很高兴看到 v2ex 又回来了，等了你半天发第一贴了，憋死我了。<br></br><br></br>nice work~
     * member : {"id":4,"username":"Jay","tagline":"","avatar_mini":"//cdn.v2ex.com/avatar/a87f/f679/4_mini.png?m=1325831331","avatar_normal":"//cdn.v2ex.com/avatar/a87f/f679/4_normal.png?m=1325831331","avatar_large":"//cdn.v2ex.com/avatar/a87f/f679/4_large.png?m=1325831331"}
     * created : 1272207477
     * last_modified : 1335092176
     */

    var id: Int = 0
    var thanks: Int = 0
    var content: String? = null
    var content_rendered: String? = null
    var member: MemberBean? = null
    var created: Int = 0
    var last_modified: Int = 0

    class MemberBean {
        /**
         * id : 4
         * username : Jay
         * tagline :
         * avatar_mini : //cdn.v2ex.com/avatar/a87f/f679/4_mini.png?m=1325831331
         * avatar_normal : //cdn.v2ex.com/avatar/a87f/f679/4_normal.png?m=1325831331
         * avatar_large : //cdn.v2ex.com/avatar/a87f/f679/4_large.png?m=1325831331
         */

        var id: Int = 0
        var username: String? = null
        var tagline: String? = null
        var avatar_mini: String? = null
        var avatar_normal: String? = null
        var avatar_large: String? = null
    }
}

package me.journey.android.v2ex

/**
 * Created by journey on 2017/12/29.
 */

class Hot {
    /**
     * id : 418471
     * title : 苹果对降频门发布了声明 20171228
     * url : http://www.v2ex.com/t/418471
     * content : https://www.apple.com/iphone-battery-and-performance/
     *
     * 辩解和之前一样，当然是为了你好啦。两个 2018 新措施：
     *
     * - 换电池价格从$79 降到$29。
     * - 会在系统里告诉用户电池状况和降频状态
     * content_rendered :
     *
     *[https://www.apple.com/iphone-battery-and-performance/](https://www.apple.com/iphone-battery-and-performance/)
     *
     * 辩解和之前一样，当然是为了你好啦。两个 2018 新措施：
     *
     *  * 换电池价格从$79 降到$29。
     *  * 会在系统里告诉用户电池状况和降频状态
     *
     *
     * replies : 173
     * member : {"id":274586,"username":"zachguo","tagline":"","avatar_mini":"//v2ex.assets.uxengine.net/avatar/54ee/af5b/274586_mini.png?m=1513487764","avatar_normal":"//v2ex.assets.uxengine.net/avatar/54ee/af5b/274586_normal.png?m=1513487764","avatar_large":"//v2ex.assets.uxengine.net/avatar/54ee/af5b/274586_large.png?m=1513487764"}
     * node : {"id":184,"name":"apple","title":"Apple","title_alternative":"Apple","url":"http://www.v2ex.com/go/apple","topics":4772,"avatar_mini":"//v2ex.assets.uxengine.net/navatar/6cdd/60ea/184_mini.png?m=1513582489","avatar_normal":"//v2ex.assets.uxengine.net/navatar/6cdd/60ea/184_normal.png?m=1513582489","avatar_large":"//v2ex.assets.uxengine.net/navatar/6cdd/60ea/184_large.png?m=1513582489"}
     * created : 1514501161
     * last_modified : 1514501161
     * last_touched : 1514539364
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
         * id : 274586
         * username : zachguo
         * tagline :
         * avatar_mini : //v2ex.assets.uxengine.net/avatar/54ee/af5b/274586_mini.png?m=1513487764
         * avatar_normal : //v2ex.assets.uxengine.net/avatar/54ee/af5b/274586_normal.png?m=1513487764
         * avatar_large : //v2ex.assets.uxengine.net/avatar/54ee/af5b/274586_large.png?m=1513487764
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
         * id : 184
         * name : apple
         * title : Apple
         * title_alternative : Apple
         * url : http://www.v2ex.com/go/apple
         * topics : 4772
         * avatar_mini : //v2ex.assets.uxengine.net/navatar/6cdd/60ea/184_mini.png?m=1513582489
         * avatar_normal : //v2ex.assets.uxengine.net/navatar/6cdd/60ea/184_normal.png?m=1513582489
         * avatar_large : //v2ex.assets.uxengine.net/navatar/6cdd/60ea/184_large.png?m=1513582489
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
}

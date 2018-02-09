package me.journey.android.v2ex.bean

/**
 * Created by journey on 2018/1/23.
 */

class TopicListBean {

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

    class MemberBean {
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
}

package com.journey.android.v2ex.utils

import com.journey.android.v2ex.bean.js.LoginBean
import org.jsoup.nodes.Document

/**
 * Selector选择器概述
 *  tagname: 通过标签查找元素，比如：a
 *  ns|tag: 通过标签在命名空间查找元素，比如：可以用 fb|name 语法来查找 <fb:name> 元素
 *  #id: 通过ID查找元素，比如：#logo
 *  .class: 通过class名称查找元素，比如：.masthead
 *  [attribute]: 利用属性查找元素，比如：[href]
 *  [^attr]: 利用属性名前缀来查找元素，比如：可以用[^data-] 来查找带有HTML5 Dataset属性的元素
 *  [attr=value]: 利用属性值来查找元素，比如：[width=500]
 *  [attr^=value], [attr$=value], [attr*=value]: 利用匹配属性值开头、结尾或包含属性值来查找元素，比如：[href*=/path/]
 *  [attr~=regex]: 利用属性值匹配正则表达式来查找元素，比如： img[src~=(?i)\.(png|jpe?g)]
 *   *: 这个符号将匹配所有元素
 */
object LoginParser {
    private val PATTERN_NUMBERS = "\\d+".toRegex()

    /**
     * <form method="post" action="/signin">
     * <table cellpadding="5" cellspacing="0" border="0" width="100%">
     *     <tr>
     *         <td width="120" align="right">用户名</td>
     *         <td width="auto" align="left">
     *             <input type="text" class="sl" name="88cd8688b6ab46e55a73c00a9c82bd8e5391fe98389e1765755a825d0f56a342" value="" autofocus="autofocus" autocorrect="off" spellcheck="false" autocapitalize="off" placeholder="用户名或电子邮箱地址" />
     *             </td>
     *     </tr>
     *     <tr>
     *         <td width="120" align="right">密码</td>
     *         <td width="auto" align="left">
     *             <input type="password" class="sl" name="a251edf575022f246636e7db2aeb3ef58a78d2841823e5d720b4df1ab67f6662" value="" autocorrect="off" spellcheck="false" autocapitalize="off" />
     *             </td>
     *     </tr>
     *     <tr>
     *         <td width="120" align="right">你是机器人么？</td>
     *         <td width="auto" align="left">
     *             <div style="background-image: url('/_captcha?once=57631'); background-repeat: no-repeat; width: 320px; height: 80px; border-radius: 3px; border: 1px solid #ccc;">
     *             </div>
     *             <div class="sep10"></div>
     *             <input type="text" class="sl" name="2337265c56d6ee14b3c75cf229b2855a0e1606d5e0d7d4e482a227e107e09b43" value="" autocorrect="off" spellcheck="false" autocapitalize="off" placeholder="请输入上图中的验证码" />
     *             </td>
     *     </tr>
     *     <tr>
     *         <td width="120" align="right"></td>
     *         <td width="auto" align="left"><input type="hidden" value="57631" name="once" /><input type="submit" class="super normal button" value="登录" /></td>
     *     </tr>
     *     <tr>
     *         <td width="120" align="right"></td>
     *         <td width="auto" align="left"><a href="/forgot">我忘记密码了</a></td>
     *     </tr>
     * </table>
     * <input type="hidden" value="/" name="next" />
     * </form>
     */

    @JvmStatic
    fun parseLoginBean(doc: Document): LoginBean {
        val content = doc.body().selectFirst("#Wrapper")
                .selectFirst(".content")
                .selectFirst(".box")
                .selectFirst(".cell")
                .selectFirst("form")
        val loginBean = LoginBean()
        loginBean.next = content.select("input[name=next]").attr("value")
        loginBean.once = content.select("input[name=once]").attr("value").toInt()
        loginBean.captcha = content.select("input.sl").first().attr("name")
        loginBean.account = content.select("input[type=text]").attr("name")
        loginBean.password = content.select("input[type=password]").attr("name")
        return loginBean
    }
}
package com.journey.android.v2ex.net.parser

import com.journey.android.v2ex.model.jsoup.SignInFormData
import com.journey.android.v2ex.model.jsoup.SignInResultData
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
     *<div id="Wrapper">
     * <div class="content">
     *  <div class="box">
     *   <div class="header">
     *    <a href="/">V2EX</a>
     *    <span class="chevron">&nbsp;›&nbsp;</span> 登录 &nbsp;
     *    <li class="fa fa-lock"></li>
     *   </div>
     *   <div class="cell">
     *    <form method="post" action="/signin">
     *     <input type="hidden" name="next" value="/">
     *     <table cellpadding="5" cellspacing="0" border="0" width="100%">
     *      <tbody>
     *       <tr>
     *        <td width="60" align="right">用户名</td>
     *        <td width="auto" align="left">
     *          <input type="text" class="sl" name="dedfb2ab7c721d6bff57da883204ee663fa327fb59780bd1fca503ed8d7d853e" value="" autocorrect="off" spellcheck="false" autocapitalize="off">
     *        </td>
     *       </tr>
     *       <tr>
     *        <td width="60" align="right">密码</td>
     *        <td width="auto" align="left">
     *          <input type="hidden" value="87092" name="once">
     *           <input type="password" class="sl" name="edeafdaf750137780da4cb650269058bb646e23e904127f5d496a0523c554430" value="" autocorrect="off" spellcheck="false" autocapitalize="off">
     *        </td>
     *       </tr>
     *       <tr>
     *        <td colspan="2" width="auto" align="left">
     *         <div style="background-image: url('/_captcha?once=87092'); background-repeat: no-repeat; width: 280px; height: 80px; border-radius: 3px; border: 1px solid #ccc;"></div> </td>
     *       </tr>
     *       <tr>
     *        <td width="60" align="right">机器？</td>
     *        <td width="auto" align="left">
     *          <input type="text" class="sl" name="2961290093a747f27f05f739d567851bb77a45f8e0ffd6974e034970911afb2b" value="" autocorrect="off" spellcheck="false" autocapitalize="off" placeholder="请输入上图中的验证码"> </td>
     *       </tr>
     *      </tbody>
     *     </table>
     *     <input type="submit" class="super normal button" value="登录" style="width: 100%; line-height: 20px; box-sizing: border-box;">
     *    </form>
     *   </div>
     *  </div>
     *  <div class="sep5"></div>
     *  <div class="box">
     *   <div class="header">
     *    其他登录方式
     *   </div>
     *   <div class="cell" style="text-align: center;">
     *    <a onclick="location.href = '/auth/google?once=87092';" href="#" class="google-signin"></a>
     *   </div>
     *  </div>
     * </div>
     *</div>
     */

    @JvmStatic
    fun parseSignInData(doc: Document): SignInFormData {
        val content = doc.body()
            .selectFirst("#Wrapper")
            .selectFirst(".content")
            .selectFirst(".box")
            .selectFirst(".cell")
            .selectFirst("form")
        val loginBean = SignInFormData()
        loginBean.next = content.select("input[name=next]")
            .attr("value")
        loginBean.once = content.select("input[name=once]")
            .attr("value")
            .toInt()
        loginBean.account = content.select("input[type=text]")[0]
            .attr("name")
        loginBean.captcha = content.select("input[type=text]")[1]
            .attr("name")
        loginBean.password = content.select("input[type=password]")
            .attr("name")
        return loginBean
    }

    /**
     * <div class="box">
     *  <div class="message" onclick="$(this).slideUp('fast');"><li class="fa fa-exclamation-triangle"></li>&nbsp; 登录有点问题，请重试一次</div>
     *  <div class="header"><div class="fr"><a href="/forgot">我忘记密码了</a></div><a href="/">V2EX</a> <span class="chevron">&nbsp;›&nbsp;</span> 登录 &nbsp;<li class="fa fa-lock"></li></div>
     *  <div class="problem">登录过程中遇到一些问题：<ul><li>输入的验证码不正确</li></ul></div>
     *  <div class="cell">
     *  </div>
     * </div>
     */

    @JvmStatic
    fun parseSignInResult(doc: Document): SignInResultData {
        val content = doc.body()
            .selectFirst("#Wrapper")
            .selectFirst(".content")
            .selectFirst(".box")
        val loginBean = SignInResultData()
//        loginBean.message = content.selectFirst(".message").text()
        loginBean.problem = content.selectFirst(".problem").text()
        return loginBean
    }
}
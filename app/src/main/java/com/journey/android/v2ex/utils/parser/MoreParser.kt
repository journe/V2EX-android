package com.journey.android.v2ex.utils.parser

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
object MoreParser {

  /**
   * <div id="Top">
   *  <div class="content">
   *  <div style="padding-top: 6px;">
   *  <table cellpadding="0" cellspacing="0" border="0" width="100%">
   *    <tbody>
   *      <tr>
   *    <td width="5" align="left"></td>
   *    <td width="80" align="left" style="padding-top: 4px;"><a href="/" name="top"><div id="LogoMobile"></div></a></td>
   *    <td width="auto" align="right" style="padding-top: 2px;">
   *      <a href="/member/xxxxxx" class="top"><img src="//cdn.v2ex.com/avatar/d3dd/4434/44444_normal.png?m=444440" style="border-radius: 24px;" width="24" height="24" align="absmiddle"></a>
   *      &nbsp;&nbsp;&nbsp;
   *      <a href="/notes" class="top"><img src="/static/img/neue_notepad.png" width="24" border="0" align="absmiddle"></a>
   *      &nbsp;&nbsp;&nbsp;
   *      <a href="/t" class="top"><img src="/static/img/neue_comment.png" width="24" border="0" align="absmiddle"></a>
   *      &nbsp;&nbsp;&nbsp;
   *      <a href="/settings" class="top"><img src="/static/img/neue_config.png" width="24" border="0" align="absmiddle"></a>
   *      &nbsp;&nbsp;&nbsp;
   *      <a href="/more" class="top"><img src="/static/img/flat_more.png" width="24" border="0" align="absmiddle"></a>
   *     </td>
   *    <td width="10" align="left"></td>
   *      </tr>
   *    </tbody>
   *  </table>
   *
   *  </div>
   *  </div>
   * </div>
   *
   * <div id="Top">
   *  <div class="content">
   *  <div style="padding-top: 6px;">
   *  <table cellpadding="0" cellspacing="0" border="0" width="100%">
   *  <tbody>
   *    <tr>
   *      <td width="5" align="left"></td>
   *      <td width="80" align="left" style="padding-top: 4px;"><a href="/" name="top"><div id="LogoMobile"></div></a></td>
   *      <td width="auto" align="right" style="padding-top: 2px;">
   *        <a href="/" class="top">首页</a>&nbsp;&nbsp;&nbsp;
   *        <a href="/signup" class="top">注册</a>&nbsp;&nbsp;&nbsp;
   *        <a href="/signin" class="top">登录</a></td>
   *      <td width="10" align="left"></td>
   *    </tr>
   *  </tbody>
   *  </table>
   *  </div>
   *  </div>
   * </div>
   *
   *<div id="Wrapper">
   *  <div class="content">
   *
   *  <div class="box">
   *  <div class="inner">搜索全站主题
   *  <div class="sep5"></div>
   *  <form onsubmit="dispatch();">
   *  <input type="text" class="sl" name="q" id="q" style="width: 280px; display: block; margin-left: 2px; border: 1px solid #ddd; box-shadow: none;" data-cip-id="q">
   *  <div class="sep5"></div>
   *  <input type="submit" value="提交" class="super normal button">
   *  </form>
   *  </div>
   *  </div>
   *
   *  <div class="sep5"></div>
   *
   *  <div class="box">
   *  <div class="inner">
   *  &nbsp; <li class="fa fa-angle-right"></li> &nbsp; <a href="/ip">IP 地址查询工具</a>
   *  <div class="sep5"></div>
   *  &nbsp; <li class="fa fa-angle-right"></li> &nbsp; <a href="#;" onclick="if (confirm('确定要从 V2EX 登出？')) { location.href= '/signout?once=82080'; }">登出</a>
   *  </div>
   *  </div>
   *
   *  </div>
   *</div>
   */

  @JvmStatic
  fun isLogin(doc: Document): Boolean {
    val content = doc.body()
        .selectFirst("#Top")
        .selectFirst(".content")
    val a = content.select("tbody")
        .select("tr")
        .select("td[width=auto]")
        .select("a")
    return a.size >= 5
  }
}
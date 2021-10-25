# V2EX API list

## Site

### 取网站信息

**/api/site/info.json**

```
{
    "title" : "V2EX",
    "slogan" : "way to explore",
    "description" : "创意工作者们的社区",
    "domain" : "www.v2ex.com"
}
```

### 取网站状态

**/api/site/stats.json**

```
{
    "topic_max" : 126172,
    "member_max" : 71033
}
```

## Node

### 取所有节点

**/api/nodes/all.json**

```
[

  {
      "id" : 1,
      "name" : "babel",
      "url" : "http://www.v2ex.com/go/babel",
      "title" : "Project Babel",
      "title_alternative" : "Project Babel",
      "topics" : 1102,
      "header" : "Project Babel \u002D 帮助你在云平台上搭建自己的社区",
      "footer" : "V2EX 基于 Project Babel 驱动。Project Babel 是用 Python 语言写成的，运行于 Google App Engine 云计算平台上的社区软件。Project Babel 当前开发分支 2.5。最新版本可以从 \u003Ca href\u003D\u0022http://github.com/livid/v2ex\u0022 target\u003D\u0022_blank\u0022\u003EGitHub\u003C/a\u003E 获取。",
      "created" : 1272206882
  }
]
```

### 取节点信息

**/api/nodes/show.json**

| 参数（选其一）  |  |
| ------------- | ------------- |
| id | 节点id   |
| name | 节点名 |


```
{
    "id" : 2,
    "name" : "v2ex",
    "url" : "http://www.v2ex.com/go/v2ex",
    "title" : "V2EX",
    "title_alternative" : "V2EX",
    "topics" : 2000,
    
        "header" : "这里讨论和发布关于 V2EX 站点发展。",
    
    
        "footer" : null,
    
    "created" : 1272207021
}
```

## Topic

### 取最新主题

**/api/topics/latest.json**

```
[
    
    {
        "id" : 128177,
        "title" : "vim\u002Dtranslator",
        "url" : "http://www.v2ex.com/t/128177",
        "content" : "一个轻巧的vim下的翻译和发音插件，依赖于 google\u002Dtranslator\u002Dcli，或者其他的命令行翻译，查询工具。发音取自google...\u000D\u000A\u000D\u000Ahttps://github.com/farseer90718/vim\u002Dtranslator\u000D\u000A\u000D\u000A功能比较简单。暂时只是实现了个人的需求...",
        "content_rendered" : "一个轻巧的vim下的翻译和发音插件，依赖于 google\u002Dtranslator\u002Dcli，或者其他的命令行翻译，查询工具。发音取自google...\u003Cbr /\u003E\u003Cbr /\u003E\u003Ca target\u003D\u0022_blank\u0022 href\u003D\u0022https://github.com/farseer90718/vim\u002Dtranslator\u0022 rel\u003D\u0022nofollow\u0022\u003Ehttps://github.com/farseer90718/vim\u002Dtranslator\u003C/a\u003E\u003Cbr /\u003E\u003Cbr /\u003E功能比较简单。暂时只是实现了个人的需求...",
        "replies" : 0,
        "member" : {
            "id" : 67060,
            "username" : "farseer2014",
            "tagline" : "",
            "avatar_mini" : "//cdn.v2ex.com/avatar/6766/2b3d/67060_mini.png?m=1408121347",
            "avatar_normal" : "//cdn.v2ex.com/avatar/6766/2b3d/67060_normal.png?m=1408121347",
            "avatar_large" : "//cdn.v2ex.com/avatar/6766/2b3d/67060_large.png?m=1408121347"
        },
        "node" : {
            "id" : 17,
            "name" : "create",
            "title" : "分享创造",
            "title_alternative" : "Create",
            "url" : "http://www.v2ex.com/go/create",
            "topics" : 2621,
            "avatar_mini" : "//cdn.v2ex.com/navatar/70ef/df2e/17_mini.png?m=1388448923",
            "avatar_normal" : "//cdn.v2ex.com/navatar/70ef/df2e/17_normal.png?m=1388448923",
            "avatar_large" : "//cdn.v2ex.com/navatar/70ef/df2e/17_large.png?m=1388448923"
        },
        "created" : 1408122614,
        "last_modified" : 1408122614,
        "last_touched" : 1408122434
    }
]
```

### 取热议主题

**/api/topics/hot.json**
```
[
    
    {
        "id" : 130248,
        "title" : "今晚罗永浩和王自如优酷对质，大家预测谁会赢？",
        "url" : "http://www.v2ex.com/t/130248",
        "content" : "世界杯后遗症……想预测结果\u000D\u000A\u000D\u000A附图，不知道能不能看到\u000D\u000Ahttp://ww2.sinaimg.cn/bmiddle/61c921e5jw1ejrbvfjdvej20ri1fmahf.jpg",
        "content_rendered" : "世界杯后遗症……想预测结果\u003Cbr /\u003E\u003Cbr /\u003E附图，不知道能不能看到\u003Cbr /\u003E\u003Ca target\u003D\u0022_blank\u0022 href\u003D\u0022http://ww2.sinaimg.cn/bmiddle/61c921e5jw1ejrbvfjdvej20ri1fmahf.jpg\u0022 target\u003D\u0022_blank\u0022\u003E\u003Cimg src\u003D\u0022http://ww2.sinaimg.cn/bmiddle/61c921e5jw1ejrbvfjdvej20ri1fmahf.jpg\u0022 class\u003D\u0022imgly\u0022 style\u003D\u0022max\u002Dwidth: 660px\u003B\u0022 border\u003D\u00220\u0022 /\u003E\u003C/a\u003E",
        "replies" : 218,
        "member" : {
            "id" : 52028,
            "username" : "sniper1211",
            "tagline" : "",
            "avatar_mini" : "//cdn.v2ex.com/avatar/1574/5f4c/52028_mini.png?m=1396973137",
            "avatar_normal" : "//cdn.v2ex.com/avatar/1574/5f4c/52028_normal.png?m=1396973137",
            "avatar_large" : "//cdn.v2ex.com/avatar/1574/5f4c/52028_large.png?m=1396973137"
        },
        "node" : {
            "id" : 687,
            "name" : "smartisanos",
            "title" : "Smartisan OS",
            "title_alternative" : "Smartisan OS",
            "url" : "http://www.v2ex.com/go/smartisanos",
            "topics" : 97,
            "avatar_mini" : "//cdn.v2ex.com/navatar/7f5d/04d1/687_mini.png?m=1364402617",
            "avatar_normal" : "//cdn.v2ex.com/navatar/7f5d/04d1/687_normal.png?m=1364402617",
            "avatar_large" : "//cdn.v2ex.com/navatar/7f5d/04d1/687_large.png?m=1364402617"
        },
        "created" : 1409134584,
        "last_modified" : 1409149779,
        "last_touched" : 1409199522
    }
]
```

### 取主题信息

**/api/topics/show.json**

| 参数  |  |
| ------------- | ------------- |
| id | 主题id（必选）   |

```
[{
    
    "id" : 1000,
    "title" : "Google App Engine x MobileMe",
    "url" : "http://www.v2ex.com/t/1000",
    "content" : "从现在开始，新上传到 V2EX 的头像将存储在 MobileMe iDisk 中。这是 V2EX 到目前为之所用到的第三个云。\u000D\u000A\u000D\u000A得益于这个架构升级，现在头像上传之后，将立刻在全站的所有页面更新。",
    "content_rendered" : "从现在开始，新上传到 V2EX 的头像将存储在 MobileMe iDisk 中。这是 V2EX 到目前为之所用到的第三个云。\u000D\u000A\u003Cbr /\u003E\u000D\u000A\u003Cbr /\u003E得益于这个架构升级，现在头像上传之后，将立刻在全站的所有页面更新。",
    "replies" : 14,
    "member" : {
        "id" : 1,
        "username" : "Livid",
        "tagline" : "Beautifully Advance",
        "avatar_mini" : "//cdn.v2ex.com/avatar/c4ca/4238/1_mini.png?m=1401650222",
        "avatar_normal" : "//cdn.v2ex.com/avatar/c4ca/4238/1_normal.png?m=1401650222",
        "avatar_large" : "//cdn.v2ex.com/avatar/c4ca/4238/1_large.png?m=1401650222"
    },
    "node" : {
        "id" : 1,
        "name" : "babel",
        "title" : "Project Babel",
        "url" : "http://www.v2ex.com/go/babel",
        "topics" : 1102,
        "avatar_mini" : "//cdn.v2ex.com/navatar/c4ca/4238/1_mini.png?m=1370299418",
        "avatar_normal" : "//cdn.v2ex.com/navatar/c4ca/4238/1_normal.png?m=1370299418",
        "avatar_large" : "//cdn.v2ex.com/navatar/c4ca/4238/1_large.png?m=1370299418"
    },
    "created" : 1280192329,
    "last_modified" : 1335004238,
    "last_touched" : 1280285385
    
}]
```

### 根据提供信息取主题

**/api/topics/show.json**

| 参数（选其一）  |  |
| ------------- | ------------- |
| username  | 根据用户名取该用户所发表主题  |
| node_id  | 根据节点id取该节点下所有主题  |
| node_name | 根据节点名取该节点下所有主题 |


## Replies

### 取主题回复

**/api/replies/show.json**

| 参数  |   |
| ------------- | ------------- |
| topic_id  | 主题id（必选）  |
| page |  |
| page_size |  |

```
[
    
    {
        "id" : 1,
        "thanks" : 5,
        "content" : "很高兴看到 v2ex 又回来了，等了你半天发第一贴了，憋死我了。\u000D\u000A\u000D\u000Anice work~",
        "content_rendered" : "很高兴看到 v2ex 又回来了，等了你半天发第一贴了，憋死我了。\u003Cbr /\u003E\u003Cbr /\u003Enice work~",
        "member" : {
            "id" : 4,
            "username" : "Jay",
            "tagline" : "",
            "avatar_mini" : "//cdn.v2ex.com/avatar/a87f/f679/4_mini.png?m=1325831331",
            "avatar_normal" : "//cdn.v2ex.com/avatar/a87f/f679/4_normal.png?m=1325831331",
            "avatar_large" : "//cdn.v2ex.com/avatar/a87f/f679/4_large.png?m=1325831331"
        },
        "created" : 1272207477,
        "last_modified" : 1335092176
    }
]
```

## Members

### 取用户信息

**/api/members/show.json**

| 参数  |  |
| ------------- | ------------- |
| username  | 用户名（必选）  |

```
{
    "status" : "found",
    "id" : 16147,
    "url" : "http://www.v2ex.com/member/djyde",
    "username" : "djyde",
    "website" : "https://djyde.github.io",
    "twitter" : "",
    "location" : "",
    "tagline" : "",
    "bio" : "喜欢摄影和写作的程序员。",
    "avatar_mini" : "//cdn.v2ex.com/avatar/ed4c/1b66/16147_mini.png?m=1329639748",
    "avatar_normal" : "//cdn.v2ex.com/avatar/ed4c/1b66/16147_normal.png?m=1329639748",
    "avatar_large" : "//cdn.v2ex.com/avatar/ed4c/1b66/16147_large.png?m=1329639748",
    "created" : 1328075793
}
```

# API 2.0 Beta

API 2.0 Beta 是我们正在持续更新中的新接口，会提供一系列通过 [Personal Access Token](https://www.v2ex.com/help/personal-access-token) 访问 V2EX 功能的新方式。

## Authentication / 认证方式

Personal Access Token 可以在 Authorization header 中使用，例子如下：

> ```
> Authorization: Bearer bd1f2c67-cc7f-48e3-a48a-e5b88b427146
> ```

## Endpoints

所有 2.0 的 API 接口都位于下面的这个前缀下：

> ```
> https://www.v2ex.com/api/v2
> ```

推荐你可以在 VS Code 中安装和使用 [REST Client](https://marketplace.visualstudio.com/items?itemName=humao.rest-client) 来测试这些 API。

下面是具体的接口访问信息。

## 获取最新的提醒

> ```
> GET /notifications
> ```

可选参数：

- `p` - 分页页码，默认为 1

完整例子：

> ```
> GET https://www.v2ex.com/api/v2/notifications?p=2 Authorization: Bearer bd1f2c67-cc7f-48e3-a48a-e5b88b427146
> ```

## 获取自己的 Profile

> ```
> GET /member
> ```

完整例子：

> ```
> GET https://www.v2ex.com/api/v2/member Authorization: Bearer bd1f2c67-cc7f-48e3-a48a-e5b88b427146
> ```

## 查看当前正在使用的令牌的信息

> ```
> GET /token
> ```

完整例子：

> ```
> GET https://www.v2ex.com/api/v2/token Authorization: Bearer bd1f2c67-cc7f-48e3-a48a-e5b88b427146
> ```

## 创建新的令牌

> ```
> POST /tokens
> ```

你可以在系统中最多创建 10 个 Personal Access Token。

输入参数：

- scope - 可选 everything 或者 regular，如果是 regular 类型的 Token 将不能用于进一步创建新的 token
- expiration - 可支持的值：259200，5184000 或者 15552000，即 30 天，60 天或者 180 天的秒数

完整例子：

> ```
> GET https://www.v2ex.com/api/v2/tokens Authorization: Bearer bd1f2c67-cc7f-48e3-a48a-e5b88b427146 {"scope": "everything", "expiration": 259200} 
> ```

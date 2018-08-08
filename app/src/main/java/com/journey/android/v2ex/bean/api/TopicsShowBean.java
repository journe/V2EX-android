package com.journey.android.v2ex.bean.api;

/**
 * Created by journey on 2018/8/8.
 */
public class TopicsShowBean {

	/**
	 * id : 1000
	 * title : Google App Engine x MobileMe
	 * url : http://www.v2ex.com/t/1000
	 * content : 从现在开始，新上传到 V2EX 的头像将存储在 MobileMe iDisk 中。这是 V2EX 到目前为之所用到的第三个云。

	 得益于这个架构升级，现在头像上传之后，将立刻在全站的所有页面更新。
	 * content_rendered : 从现在开始，新上传到 V2EX 的头像将存储在 MobileMe iDisk 中。这是 V2EX 到目前为之所用到的第三个云。
	 <br />
	 <br />得益于这个架构升级，现在头像上传之后，将立刻在全站的所有页面更新。
	 * replies : 14
	 * member : {"id":1,"username":"Livid","tagline":"Beautifully Advance","avatar_mini":"//cdn.v2ex.com/avatar/c4ca/4238/1_mini.png?m=1401650222","avatar_normal":"//cdn.v2ex.com/avatar/c4ca/4238/1_normal.png?m=1401650222","avatar_large":"//cdn.v2ex.com/avatar/c4ca/4238/1_large.png?m=1401650222"}
	 * node : {"id":1,"name":"babel","title":"Project Babel","url":"http://www.v2ex.com/go/babel","topics":1102,"avatar_mini":"//cdn.v2ex.com/navatar/c4ca/4238/1_mini.png?m=1370299418","avatar_normal":"//cdn.v2ex.com/navatar/c4ca/4238/1_normal.png?m=1370299418","avatar_large":"//cdn.v2ex.com/navatar/c4ca/4238/1_large.png?m=1370299418"}
	 * created : 1280192329
	 * last_modified : 1335004238
	 * last_touched : 1280285385
	 */

	private int id;
	private String title;
	private String url;
	private String content;
	private String content_rendered;
	private int replies;
	private MemberBean member;
	private NodeBean node;
	private int created;
	private int last_modified;
	private int last_touched;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent_rendered() {
		return content_rendered;
	}

	public void setContent_rendered(String content_rendered) {
		this.content_rendered = content_rendered;
	}

	public int getReplies() {
		return replies;
	}

	public void setReplies(int replies) {
		this.replies = replies;
	}

	public MemberBean getMember() {
		return member;
	}

	public void setMember(MemberBean member) {
		this.member = member;
	}

	public NodeBean getNode() {
		return node;
	}

	public void setNode(NodeBean node) {
		this.node = node;
	}

	public int getCreated() {
		return created;
	}

	public void setCreated(int created) {
		this.created = created;
	}

	public int getLast_modified() {
		return last_modified;
	}

	public void setLast_modified(int last_modified) {
		this.last_modified = last_modified;
	}

	public int getLast_touched() {
		return last_touched;
	}

	public void setLast_touched(int last_touched) {
		this.last_touched = last_touched;
	}

	public static class MemberBean {
		/**
		 * id : 1
		 * username : Livid
		 * tagline : Beautifully Advance
		 * avatar_mini : //cdn.v2ex.com/avatar/c4ca/4238/1_mini.png?m=1401650222
		 * avatar_normal : //cdn.v2ex.com/avatar/c4ca/4238/1_normal.png?m=1401650222
		 * avatar_large : //cdn.v2ex.com/avatar/c4ca/4238/1_large.png?m=1401650222
		 */

		private int id;
		private String username;
		private String tagline;
		private String avatar_mini;
		private String avatar_normal;
		private String avatar_large;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getTagline() {
			return tagline;
		}

		public void setTagline(String tagline) {
			this.tagline = tagline;
		}

		public String getAvatar_mini() {
			return avatar_mini;
		}

		public void setAvatar_mini(String avatar_mini) {
			this.avatar_mini = avatar_mini;
		}

		public String getAvatar_normal() {
			return avatar_normal;
		}

		public void setAvatar_normal(String avatar_normal) {
			this.avatar_normal = avatar_normal;
		}

		public String getAvatar_large() {
			return avatar_large;
		}

		public void setAvatar_large(String avatar_large) {
			this.avatar_large = avatar_large;
		}
	}

	public static class NodeBean {
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

		private int id;
		private String name;
		private String title;
		private String url;
		private int topics;
		private String avatar_mini;
		private String avatar_normal;
		private String avatar_large;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public int getTopics() {
			return topics;
		}

		public void setTopics(int topics) {
			this.topics = topics;
		}

		public String getAvatar_mini() {
			return avatar_mini;
		}

		public void setAvatar_mini(String avatar_mini) {
			this.avatar_mini = avatar_mini;
		}

		public String getAvatar_normal() {
			return avatar_normal;
		}

		public void setAvatar_normal(String avatar_normal) {
			this.avatar_normal = avatar_normal;
		}

		public String getAvatar_large() {
			return avatar_large;
		}

		public void setAvatar_large(String avatar_large) {
			this.avatar_large = avatar_large;
		}
	}
}

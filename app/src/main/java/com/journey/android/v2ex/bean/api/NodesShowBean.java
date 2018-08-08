package com.journey.android.v2ex.bean.api;

/**
 * Created by journey on 2018/8/8.
 */
public class NodesShowBean {

	/**
	 * id : 2
	 * name : v2ex
	 * url : http://www.v2ex.com/go/v2ex
	 * title : V2EX
	 * title_alternative : V2EX
	 * topics : 2000
	 * header : 这里讨论和发布关于 V2EX 站点发展。
	 * footer : null
	 * created : 1272207021
	 */

	private int id;
	private String name;
	private String url;
	private String title;
	private String title_alternative;
	private int topics;
	private String header;
	private Object footer;
	private int created;

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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle_alternative() {
		return title_alternative;
	}

	public void setTitle_alternative(String title_alternative) {
		this.title_alternative = title_alternative;
	}

	public int getTopics() {
		return topics;
	}

	public void setTopics(int topics) {
		this.topics = topics;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	public Object getFooter() {
		return footer;
	}

	public void setFooter(Object footer) {
		this.footer = footer;
	}

	public int getCreated() {
		return created;
	}

	public void setCreated(int created) {
		this.created = created;
	}
}

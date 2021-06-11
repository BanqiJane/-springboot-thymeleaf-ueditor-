package xyz.acproject.blogs.entity;

public class Ip {
	private String ip;
	private String time;
	private String url;
	
	public Ip() {
		super();
		// TODO 自动生成的构造函数存根
	}

	public Ip(String ip, String time, String url) {
		super();
		this.ip = ip;
		this.time = time;
		this.url = url;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "Ip [ip=" + ip + ", time=" + time + ", url=" + url + "]";
	}
	
	
}

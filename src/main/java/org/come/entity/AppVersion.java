package org.come.entity;

/**
 * 三端
 * @author zz
 * @time 2019年8月6日11:28:19
 * 
 */
public class AppVersion {

//	 版本号
	private String ver_id;
	// 下载路径
	private String ver_url;
	// 标识(1,pc 2,app)
	private String ver_sign;

	public AppVersion() {
		// TODO Auto-generated constructor stub
	}

	public String getVer_id() {
		return ver_id;
	}

	public void setVer_id(String ver_id) {
		this.ver_id = ver_id;
	}

	public String getVer_url() {
		return ver_url;
	}

	public void setVer_url(String ver_url) {
		this.ver_url = ver_url;
	}

	public String getVer_sign() {
		return ver_sign;
	}

	public void setVer_sign(String ver_sign) {
		this.ver_sign = ver_sign;
	}

}

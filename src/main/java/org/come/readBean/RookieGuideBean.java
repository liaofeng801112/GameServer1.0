package org.come.readBean;

/**
 * 新手引导Bean类
 */
public class RookieGuideBean {
	
	private int Gid;//表ID
	private String Guidename; //小项名称
	private int Fid; //所属大项ID
	private String Bootcontent;//引导内容
	public int getGid() {
		return Gid;
	}
	public void setGid(int gid) {
		Gid = gid;
	}
	public String getGuidename() {
		return Guidename;
	}
	public void setGuidename(String guidename) {
		Guidename = guidename;
	}
	public int getFid() {
		return Fid;
	}
	public void setFid(int fid) {
		Fid = fid;
	}
	public String getBootcontent() {
		return Bootcontent;
	}
	public void setBootcontent(String bootcontent) {
		Bootcontent = bootcontent;
	}
	
	
}

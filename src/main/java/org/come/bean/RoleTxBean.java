package org.come.bean;

/**
 * 饰品表格xls数据字段存储
 * @author Administrator
 *
 */
public class RoleTxBean {
	/**特效id*/
	private int Gid;
	/**素材ID*/
	private int RdId;
	/**名称*/
	private String RdName;
	/**图层（0表示先画9表示后画）*/
	private int RdStatues;
	/**类型（1特效2装饰品3足迹）*/
	private int RdType;
	/**说明（特效展示说明描述）*/
	private String RdAsk;
	
	public int getGid() {
		return Gid;
	}
	public void setGid(int gid) {
		Gid = gid;
	}
	public int getRdId() {
		return RdId;
	}
	public void setRdId(int rdId) {
		RdId = rdId;
	}
	public String getRdName() {
		return RdName;
	}
	public void setRdName(String rdName) {
		RdName = rdName;
	}
	public int getRdStatues() {
		return RdStatues;
	}
	public void setRdStatues(int rdStatues) {
		RdStatues = rdStatues;
	}
	public int getRdType() {
		return RdType;
	}
	public void setRdType(int rdType) {
		RdType = rdType;
	}
	public String getRdAsk() {
		return RdAsk;
	}
	public void setRdAsk(String rdAsk) {
		RdAsk = rdAsk;
	}
	
	
	
	
}

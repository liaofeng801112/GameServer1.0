package org.come.model;
/**
 * 打造装备
 * @author 叶豪芳
 * @date 2018年1月3日 下午6:46:28
 * 
 */ 
public class Alchemy {
	
	//物品id
	private String alchemyid;	
	//装备类型
	private String alchemytype;
	//属性键
	private String alchemykey;
	//最小值
	private String alchemysv;
	//最大值
	private String alchemymv;
	public Alchemy() {
		// TODO Auto-generated constructor stub
	}
	public Alchemy(String alchemyid, String alchemytype, String alchemykey,
			String alchemysv, String alchemymv) {
		super();
		this.alchemyid = alchemyid;
		this.alchemytype = alchemytype;
		this.alchemykey = alchemykey;
		this.alchemysv = alchemysv;
		this.alchemymv = alchemymv;
	}
	public String getAlchemyid() {
		return alchemyid;
	}
	public void setAlchemyid(String alchemyid) {
		this.alchemyid = alchemyid;
	}
	public String getAlchemytype() {
		return alchemytype;
	}
	public void setAlchemytype(String alchemytype) {
		this.alchemytype = alchemytype;
	}
	public String getAlchemykey() {
		return alchemykey;
	}
	public void setAlchemykey(String alchemykey) {
		this.alchemykey = alchemykey;
	}
	public String getAlchemysv() {
		return alchemysv;
	}
	public void setAlchemysv(String alchemysv) {
		this.alchemysv = alchemysv;
	}
	public String getAlchemymv() {
		return alchemymv;
	}
	public void setAlchemymv(String alchemymv) {
		this.alchemymv = alchemymv;
	}

}

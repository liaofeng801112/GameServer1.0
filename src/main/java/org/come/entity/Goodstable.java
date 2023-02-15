package org.come.entity;

import java.math.BigDecimal;

import org.come.tool.JmSum;

import come.tool.Calculation.BaseEquip;
/**
 * 角色背包物品bean
 * @author 叶豪芳
 * @date : 2017年11月27日 上午10:06:42
 */
public class Goodstable implements Cloneable{
    // 物品IDgoodsid
    private BigDecimal goodsid;
    // 物品名称
    private String goodsname;
    // 皮肤
    private String skin;
    // 物品类型
    private long type;
    // 贵重
    private Long quality;
	// 物品功能
    private String value;
    // 物品说明
    private String instruction;
    // 物品状态（0：未使用   1：使用   2:典当）
    private Integer status;
    // 使用次数
    private Integer usetime;
	// 表ID
	private BigDecimal rgid;
	// 角色ID
    private BigDecimal role_id;
    //物品是否有加锁
  	private int goodlock;
  	private Integer qhv;//强化值
  	private Integer qht;//强化总数
  	private Integer qhb;//强化消耗是否未绑定标记
  	
  	private transient BaseEquip baseEquip;
	/**物品使用消耗*/
	public void goodxh(int i) {
		setUsetime(getUsetime()-i);
	}
    public BigDecimal getRgid() {
		return rgid;
	}
	public void setRgid(BigDecimal rgid) {
		this.rgid = rgid;
	}
	public BigDecimal getRole_id() {
		return role_id;
	}
	public void setRole_id(BigDecimal role_id) {
		this.role_id = role_id;
	}
	public BigDecimal getGoodsid() {
        return goodsid;
    }
    public void setGoodsid(BigDecimal goodsid) {
        this.goodsid = goodsid;
    }
    public String getGoodsname() {
        return goodsname;
    }
    public void setGoodsname(String goodsname) {
        this.goodsname = goodsname == null ? null : goodsname.trim();
    }
    public String getSkin() {
        return skin;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }

    public long getType() {
        return type;
    }
    public void setType(Long type) {
        this.type = type;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
    	this.value = value == null ? null : value.trim();
    	this.baseEquip=null;
    }
    public BaseEquip getEquip() {
		if (baseEquip==null&&value!=null&&!value.equals("")) {
			baseEquip=new BaseEquip(value, type);	
		}
		return baseEquip;
	}
    
    public String getInstruction() {
        return instruction;
    }
    public void setInstruction(String instruction) {
        this.instruction = instruction == null ? null : instruction.trim();
    }
    public Long getQuality() {
		return quality;
	}
	public void setQuality(Long quality) {
		this.quality = quality;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public Integer getUsetime() {
		if (usetime == null)setUsetime(1);
		return (int) JmSum.MZ(usetime);
	}
	public void setUsetime(Integer usetime) {
		if (goodsid!=null&&goodsid.longValue()==80050) {
			if (usetime>20) {
				usetime=20;
			}
		}
		this.usetime = (int) JmSum.ZM(usetime);
	}  
	public Integer getQhv() {
		return qhv;
	}
	public void setQhv(Integer qhv) {
		this.qhv = qhv;
	}
	public Integer getQht() {
		return qht;
	}
	public void setQht(Integer qht) {
		this.qht = qht;
	}
	public Integer getQhb() {
		return qhb;
	}
	public void setQhb(Integer qhb) {
		this.qhb = qhb;
	}
	public boolean isQh(Goodstable good) {
		if (qhv==null&&qht==null&&qhb==null&&good.qhv==null&&good.qht==null&&good.qhb==null) {
			return true;
		}
		int v1=qhv!=null?qhv:0;
		int v2=good.qhv!=null?good.qhv:0;
		if (v1!=v2) {return false;}			
		
		v1=qht!=null?qht:0;
		v2=good.qht!=null?good.qht:0;
		if (v1!=v2) {return false;}			
		
		v1=qhb!=null?qhb:0;
		v2=good.qhb!=null?good.qhb:0;
		if (v1!=v2) {return false;}			
		
		return true;
	}
	@Override
	public Goodstable clone(){
		try {
			return (Goodstable) super.clone();		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	public int getGoodlock() {
		return goodlock;
	}

	public void setGoodlock(int goodlock) {
		this.goodlock = goodlock;
	}

	public void setType(long type) {
		this.type = type;
	}
	
	static String[] vs=new String[]{"  ","rgid:","goodsid:","goodsname:","role_id:","usetime:","value:","type:","quality:","status:","DEFINEPRICE:"};
	public static String toStringTwo(BigDecimal roleID,BigDecimal id,int type,String name,String value){
		StringBuffer buffer=new StringBuffer();
		buffer.append(vs[1]);
		buffer.append(id);
		buffer.append(vs[0]);
		buffer.append(vs[2]);
		buffer.append("999999");
		buffer.append(vs[0]);
		buffer.append(vs[3]);
		buffer.append(name);
		buffer.append(vs[0]);
		buffer.append(vs[4]);
		buffer.append(roleID);
		buffer.append(vs[0]);
		buffer.append(vs[5]);
		buffer.append("1");
		buffer.append(vs[0]);
		buffer.append(vs[6]);
		buffer.append(value);
		buffer.append(vs[0]);
		buffer.append(vs[7]);
		buffer.append(type);
		buffer.append(vs[0]);
		buffer.append(vs[8]);
		buffer.append("0");
		buffer.append(vs[0]);
		buffer.append(vs[9]);
		buffer.append("0");
		return buffer.toString();	
	}
	@Override
	public String toString(){
		StringBuffer buffer=new StringBuffer();
		buffer.append(vs[1]);
		buffer.append(rgid);
		buffer.append(vs[0]);
		buffer.append(vs[2]);
		buffer.append(goodsid);
		buffer.append(vs[0]);
		buffer.append(vs[3]);
		buffer.append(goodsname);
		buffer.append(vs[0]);
		buffer.append(vs[4]);
		buffer.append(role_id);
		buffer.append(vs[0]);
		buffer.append(vs[5]);
		buffer.append(getUsetime());
		buffer.append(vs[0]);
		buffer.append(vs[6]);
		buffer.append(value);
		buffer.append(vs[0]);
		buffer.append(vs[7]);
		buffer.append(type);
		buffer.append(vs[0]);
		buffer.append(vs[8]);
		buffer.append(quality);
		buffer.append(vs[0]);
		buffer.append(vs[9]);
		buffer.append(status);
		return buffer.toString();		
	}
}
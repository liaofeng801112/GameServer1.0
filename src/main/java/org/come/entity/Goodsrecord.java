package org.come.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Goodsrecord {
    /**
     * 表ID
     */
    private Integer grid;

    /**
     * 记录类型（0：商店或商城购买、1:摆摊购买、2、给与、3、礼包获得、4、其他获得、5:交易,6、合成消耗,7、合成符石，8、合成修改，9、使用,10:典当，11：取回典当,12:炼妖消耗）
     */
    private Integer recordtype;

    /**
     * 角色ID
     */
    private BigDecimal roleid;

    /**
     * 对方ID
     */
    private BigDecimal otherrole;

    /**
     * 操作物品
     */
    private String goods;

    /**记录时间*/
    private Date recordtime;

    /**
     * 物品数量
     */
    private Integer goodsnum;
    
    /**
     * 角色名
     */
    private String rolename;
    
    /**
     * 对方角色名
     */
    private String othername;

    /**
     * 表ID
     * @return GRID 表ID
     */
    public Integer getGrid() {
        return grid;
    }

    /**
     * 表ID
     * @param grid 表ID
     */
    public void setGrid(Integer grid) {
        this.grid = grid;
    }

    /**
     * 记录类型（0：商店购买、1：商城购买、2:摆摊购买、3、给与获得、4、礼包获得、5、其他获得、6:丢弃或使用）
     * @return RECORDTYPE 记录类型（0：商店购买、1：商城购买、2:摆摊购买、3、给与获得、4、礼包获得、5、其他获得、6:丢弃或使用）
     */
    public Integer getRecordtype() {
        return recordtype;
    }

    /**
     * 记录类型（0：商店购买、1：商城购买、2:摆摊购买、3、给与获得、4、礼包获得、5、其他获得、6:丢弃或使用）
     * @param recordtype 记录类型（0：商店购买、1：商城购买、2:摆摊购买、3、给与获得、4、礼包获得、5、其他获得、6:丢弃或使用）
     */
    public void setRecordtype(Integer recordtype) {
        this.recordtype = recordtype;
    }

    /**
     * 角色ID
     * @return ROLEID 角色ID
     */
    public BigDecimal getRoleid() {
        return roleid;
    }

    /**
     * 角色ID
     * @param roleid 角色ID
     */
    public void setRoleid(BigDecimal roleid) {
        this.roleid = roleid;
    }

    /**
     * 对方ID
     * @return OTHERROLE 对方ID
     */
    public BigDecimal getOtherrole() {
        return otherrole;
    }

    /**
     * 对方ID
     * @param otherrole 对方ID
     */
    public void setOtherrole(BigDecimal otherrole) {
        this.otherrole = otherrole;
    }

    /**
     * 操作物品
     * @return GOODS 操作物品
     */
    public String getGoods() {
        return goods;
    }

    /**
     * 操作物品
     * @param goods 操作物品
     */
    public void setGoods(String goods) {
        this.goods = goods == null ? null : goods.trim();
    }

    /**
     * 记录时间
     * @return RECORDTIME 记录时间
     */
    public Date getRecordtime() {
        return recordtime;
    }

    /**
     * 记录时间
     * @param recordtime 记录时间
     */
    public void setRecordtime(Date recordtime) {
        this.recordtime = recordtime;
    }

    /**
     * 物品数量
     * @return GOODSNUM 物品数量
     */
    public Integer getGoodsnum() {
        return goodsnum;
    }

    /**
     * 物品数量
     * @param goodsnum 物品数量
     */
    public void setGoodsnum(Integer goodsnum) {
        this.goodsnum = goodsnum;
    }

	public String getRolename() {
		return rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public String getOthername() {
		return othername;
	}

	public void setOthername(String othername) {
		this.othername = othername;
	}
    
}
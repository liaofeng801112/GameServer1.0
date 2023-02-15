package org.come.extInterBean;

import java.math.BigDecimal;

public class Goodsrecord2 {
	
	/**
	 * 记录ID
	 */
	private Long grid = 0L;

	/**
	 * 记录类型
	 */
	private Integer recordtype = 0;

	/**
	 * 角色ID
	 */
	private BigDecimal roleid = new BigDecimal(0);

	/**
	 * 对方角色ID
	 */
	private BigDecimal otherrole = new BigDecimal(0);

	/**
	 * 物品信息
	 */
	private String goods="0";

	/**
	 * 记录时间
	 */
	private String recordtime;

	/**
	 * 物品数量
	 */
	private Integer goodsnum = 0;

	/**
	 * 区域ID
	 */
	private Integer sid = 0;

	/**
	 * 物品名称
	 */
	private String goodsname="0";

	/**
	 * 物品加成
	 */
	private String value = "0";

	/**
	 * 使用次数
	 */
	private String usetime = "0";

	/**
	 * 物品标识
	 */
	private BigDecimal goodsid = new BigDecimal(0);

	/**
	 * 皮肤
	 */
	private String skin = "0";

	/**
	 * 物品类型
	 */
	private Long type = 0L;

	/**
	 * 物品贵重
	 */
	private Long quality = 0L;

	/**
	 * 物品说明
	 */
	private String instruction;

	/**
	 * 物品ID
	 */
	private BigDecimal rgid = new BigDecimal(0);

	/**
	 * 物品状态
	 */
	private Integer status = 0;

	/**
	 * 自定义价格/仙玉/大话币/积分
	 */
	private BigDecimal defineprice = new BigDecimal(0);

	/**
	 * 加锁
	 */
	private Integer goodlock = 0;

	private String roleName = "0";
	private String otherRole = "0";

	/**
	 * 记录ID
	 * 
	 * @return GRID 记录ID
	 */
	public Long getGrid() {
		if(grid == null){
			grid = 0L;
		}
		return grid;
	}

	/**
	 * 记录ID
	 * 
	 * @param grid
	 *            记录ID
	 */
	public void setGrid(Long grid) {
		this.grid = grid;
	}

	/**
	 * 记录类型
	 * 
	 * @return RECORDTYPE 记录类型
	 */
	public Integer getRecordtype() {
		if(recordtype == null){
			recordtype = 0;
		}
		return recordtype;
	}

	/**
	 * 记录类型
	 * 
	 * @param recordtype
	 *            记录类型
	 */
	public void setRecordtype(Integer recordtype) {
		this.recordtype = recordtype;
	}

	/**
	 * 角色ID
	 * 
	 * @return ROLEID 角色ID
	 */
	public BigDecimal getRoleid() {
		if(roleid == null){
			roleid = new BigDecimal(0);
		}
//		if (roleid==null) { roleid=zero; }
		return roleid;
	}

	/**
	 * 角色ID
	 * 
	 * @param roleid
	 *            角色ID
	 */
	public void setRoleid(BigDecimal roleid) {
		this.roleid = roleid;
	}
//	static BigDecimal zero=new BigDecimal(0);
	/**
	 * 对方角色ID
	 * 
	 * @return OTHERROLE 对方角色ID
	 */
	public BigDecimal getOtherrole() {
		if(otherrole == null){
			otherrole = new BigDecimal(0);
		}
//		if (otherrole==null) { otherrole=zero; }
		return otherrole;
	}

	/**
	 * 对方角色ID
	 * 
	 * @param otherrole
	 *            对方角色ID
	 */
	public void setOtherrole(BigDecimal otherrole) {
		this.otherrole = otherrole;
	}

	/**
	 * 物品信息
	 * 
	 * @return GOODS 物品信息
	 */
	public String getGoods() {
		return goods;
	}

	/**
	 * 物品信息
	 * 
	 * @param goods
	 *            物品信息
	 */
	public void setGoods(String goods) {
		this.goods = goods == null ? null : goods.trim();
	}

	/**
	 * 记录时间
	 * 
	 * @return RECORDTIME 记录时间
	 */
	public String getRecordtime() {
		return recordtime;
	}

	/**
	 * 记录时间
	 * 
	 * @param recordtime
	 *            记录时间
	 */
	public void setRecordtime(String recordtime) {
		this.recordtime = recordtime;
	}

	/**
	 * 物品数量
	 * 
	 * @return GOODSNUM 物品数量
	 */
	public Integer getGoodsnum() {
		if(goodsnum == null){
			goodsnum = 0;
		}
		return goodsnum;
	}

	/**
	 * 物品数量
	 * 
	 * @param goodsnum
	 *            物品数量
	 */
	public void setGoodsnum(Integer goodsnum) {
		this.goodsnum = goodsnum;
	}

	/**
	 * 区域ID
	 * 
	 * @return SID 区域ID
	 */
	public Integer getSid() {
		if(sid == null){
			sid = 0;
		}
		return sid;
	}

	/**
	 * 区域ID
	 * 
	 * @param sid
	 *            区域ID
	 */
	public void setSid(Integer sid) {
		this.sid = sid;
	}

	/**
	 * 物品名称
	 * 
	 * @return GOODSNAME 物品名称
	 */
	public String getGoodsname() {
		if(goodsname == null){
			goodsname = "0";
		}
		return goodsname;
	}

	/**
	 * 物品名称
	 * 
	 * @param goodsname
	 *            物品名称
	 */
	public void setGoodsname(String goodsname) {
		this.goodsname = goodsname == null ? null : goodsname.trim();
	}

	/**
	 * 物品加成
	 * 
	 * @return VALUE 物品加成
	 */
	public String getValue() {
		if(value == null){
			value = "0";
		}
		return value;
	}

	/**
	 * 物品加成
	 * 
	 * @param value
	 *            物品加成
	 */
	public void setValue(String value) {
		this.value = value == null ? null : value.trim();
	}

	/**
	 * 使用次数
	 * 
	 * @return USETIME 使用次数
	 */
	public String getUsetime() {
		if(usetime == null){
			usetime = "0";
		}
		return usetime;
	}

	/**
	 * 使用次数
	 * 
	 * @param usetime
	 *            使用次数
	 */
	public void setUsetime(String usetime) {
		this.usetime = usetime == null ? null : usetime.trim();
	}

	/**
	 * 物品标识
	 * 
	 * @return GOODSID 物品标识
	 */
	public BigDecimal getGoodsid() {
		if(goodsid == null){
			goodsid = new BigDecimal(0);
		}
		return goodsid;
	}

	/**
	 * 物品标识
	 * 
	 * @param goodsid
	 *            物品标识
	 */
	public void setGoodsid(BigDecimal goodsid) {
		this.goodsid = goodsid;
	}

	/**
	 * 皮肤
	 * 
	 * @return SKIN 皮肤
	 */
	public String getSkin() {
		return skin;
	}

	/**
	 * 皮肤
	 * 
	 * @param skin
	 *            皮肤
	 */
	public void setSkin(String skin) {
		this.skin = skin == null ? null : skin.trim();
	}

	/**
	 * 物品类型
	 * 
	 * @return TYPE 物品类型
	 */
	public Long getType() {
		if(type == null){
			type = 0L;
		}
		return type;
	}

	/**
	 * 物品类型
	 * 
	 * @param type
	 *            物品类型
	 */
	public void setType(Long type) {
		this.type = type;
	}

	/**
	 * 物品贵重
	 * 
	 * @return QUALITY 物品贵重
	 */
	public Long getQuality() {
		if(quality == null){
			quality = 0L;
		}
		return quality;
	}

	/**
	 * 物品贵重
	 * 
	 * @param quality
	 *            物品贵重
	 */
	public void setQuality(Long quality) {
		this.quality = quality;
	}

	/**
	 * 物品说明
	 * 
	 * @return INSTRUCTION 物品说明
	 */
	public String getInstruction() {
		return instruction;
	}

	/**
	 * 物品说明
	 * 
	 * @param instruction
	 *            物品说明
	 */
	public void setInstruction(String instruction) {
		this.instruction = instruction == null ? null : instruction.trim();
	}

	/**
	 * 物品ID
	 * 
	 * @return RGID 物品ID
	 */
	public BigDecimal getRgid() {
		if(rgid == null){
			rgid = new BigDecimal(0);
		}
		return rgid;
	}

	/**
	 * 物品ID
	 * 
	 * @param rgid
	 *            物品ID
	 */
	public void setRgid(BigDecimal rgid) {
		this.rgid = rgid;
	}

	/**
	 * 物品状态
	 * 
	 * @return STATUS 物品状态
	 */
	public Integer getStatus() {
		if(status == null){
			status = 0;
		}
		return status;
	}

	/**
	 * 物品状态
	 * 
	 * @param status
	 *            物品状态
	 */
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	 * 自定义价格/仙玉/大话币/积分
	 * 
	 * @return DEFINEPRICE 自定义价格/仙玉/大话币/积分
	 */
	public BigDecimal getDefineprice() {
		return defineprice;
	}

	/**
	 * 自定义价格/仙玉/大话币/积分
	 * 
	 * @param defineprice
	 *            自定义价格/仙玉/大话币/积分
	 */
	public void setDefineprice(BigDecimal defineprice) {
		this.defineprice = defineprice;
	}

	/**
	 * 加锁
	 * 
	 * @return GOODLOCK 加锁
	 */
	public Integer getGoodlock() {
		if(goodlock == null){
			goodlock = 0;
		}
		return goodlock;
	}

	/**
	 * 加锁
	 * 
	 * @param goodlock
	 *            加锁
	 */
	public void setGoodlock(Integer goodlock) {
		this.goodlock = goodlock;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getOtherRole() {
		return otherRole;
	}

	public void setOtherRole(String otherRole) {
		this.otherRole = otherRole;
	}

}
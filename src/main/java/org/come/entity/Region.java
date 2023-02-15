package org.come.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 三端
 * 区域表bean
 * 
 * @author zz
 * @time 2019年7月15日16:19:04
 * 
 */
public class Region {

	// 大区域id
	private BigDecimal regionId;
	// 大区域名称
	private String regionName;
	// 大区域创建时间
	private Date regionCreTime;
	// 大区域修改时间
	private Date regionModTime;
	// 区id
	private BigDecimal regionAllId;
	// 区名称
	private String regionAllName;

	public Region() {
		// TODO Auto-generated constructor stub
	}

	public BigDecimal getRegionId() {
		return regionId;
	}

	public void setRegionId(BigDecimal regionId) {
		this.regionId = regionId;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public Date getRegionCreTime() {
		return regionCreTime;
	}

	public void setRegionCreTime(Date regionCreTime) {
		this.regionCreTime = regionCreTime;
	}

	public Date getRegionModTime() {
		return regionModTime;
	}

	public void setRegionModTime(Date regionModTime) {
		this.regionModTime = regionModTime;
	}

	public BigDecimal getRegionAllId() {
		return regionAllId;
	}

	public void setRegionAllId(BigDecimal regionAllId) {
		this.regionAllId = regionAllId;
	}

	public String getRegionAllName() {
		return regionAllName;
	}

	public void setRegionAllName(String regionAllName) {
		this.regionAllName = regionAllName;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Region [regionId=");
		builder.append(regionId);
		builder.append(", regionName=");
		builder.append(regionName);
		builder.append(", regionCreTime=");
		builder.append(regionCreTime);
		builder.append(", regionModTime=");
		builder.append(regionModTime);
		builder.append(", regionAllId=");
		builder.append(regionAllId);
		builder.append(", regionAllName=");
		builder.append(regionAllName);
		builder.append("]");
		return builder.toString();
	}

}

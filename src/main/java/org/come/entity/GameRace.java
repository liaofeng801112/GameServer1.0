package org.come.entity;

import java.math.BigDecimal;
/**
 * 种族实体类
 * 
 * @author 叶豪芳
 * @date : 2017年11月23日 下午2:58:04
 */
public class GameRace {
	// 种族ID
    private BigDecimal race_id;

    // 种族名称
    private String race_name;


    public BigDecimal getRace_id() {
		return race_id;
	}

	public void setRace_id(BigDecimal race_id) {
		this.race_id = race_id;
	}

	public String getRace_name() {
		return race_name;
	}

	public void setRace_name(String race_name) {
		this.race_name = race_name;
	}

}
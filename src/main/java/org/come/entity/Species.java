package org.come.entity;

import java.math.BigDecimal;
/**
 * 种类bean
 * @author 叶豪芳
 * @date : 2017年11月27日 上午10:07:54
 */
public class Species {
    private BigDecimal species_id;

    private BigDecimal race_id;

    private String sex;

    private String localname;

    private BigDecimal hp;

    private BigDecimal mp;

    private BigDecimal ap;

    private BigDecimal sp;

    private BigDecimal mpv;

    private BigDecimal apv;

    private BigDecimal spv;
    
    private BigDecimal hpv;

    public BigDecimal getHpv() {
		return hpv;
	}

	public void setHpv(BigDecimal hpv) {
		this.hpv = hpv;
	}

	private String rolepath;


    public BigDecimal getSpecies_id() {
		return species_id;
	}

	public void setSpecies_id(BigDecimal species_id) {
		this.species_id = species_id;
	}

	public BigDecimal getRace_id() {
		return race_id;
	}

	public void setRace_id(BigDecimal race_id) {
		this.race_id = race_id;
	}

	public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getLocalname() {
        return localname;
    }

    public void setLocalname(String localname) {
        this.localname = localname == null ? null : localname.trim();
    }

    public BigDecimal getHp() {
        return hp;
    }

    public void setHp(BigDecimal hp) {
        this.hp = hp;
    }

    public BigDecimal getMp() {
        return mp;
    }

    public void setMp(BigDecimal mp) {
        this.mp = mp;
    }

    public BigDecimal getAp() {
        return ap;
    }

    public void setAp(BigDecimal ap) {
        this.ap = ap;
    }

    public BigDecimal getSp() {
        return sp;
    }

    public void setSp(BigDecimal sp) {
        this.sp = sp;
    }

    public BigDecimal getMpv() {
        return mpv;
    }

    public void setMpv(BigDecimal mpv) {
        this.mpv = mpv;
    }

    public BigDecimal getApv() {
        return apv;
    }

    public void setApv(BigDecimal apv) {
        this.apv = apv;
    }

    public BigDecimal getSpv() {
        return spv;
    }

    public void setSpv(BigDecimal spv) {
        this.spv = spv;
    }

    public String getRolepath() {
        return rolepath;
    }

    public void setRolepath(String rolepath) {
        this.rolepath = rolepath == null ? null : rolepath.trim();
    }
}
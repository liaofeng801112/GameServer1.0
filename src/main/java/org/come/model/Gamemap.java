package org.come.model;

import java.util.List;

import come.tool.Good.DropModel;


public class Gamemap {
    private String mapid;

    private String mapname;

    private String police;

    private String maptype;

    private String width;

    private String height;

    private String maplvl;

    private String touch;

    private String mapflag;

    private String reward;

    private String exp;

    private String mapnpc;

    private String monster;

    private String mapway;

    private String music;

    private String smallmap;

	private String background;

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

    private transient DropModel dropModel;
    private transient List<String> npcs;
    
	public String getMapid() {
		return mapid;
	}

	public void setMapid(String mapid) {
		this.mapid = mapid;
	}

	public String getMapname() {
		return mapname;
	}

	public void setMapname(String mapname) {
		this.mapname = mapname;
	}

	public String getPolice() {
		return police;
	}

	public void setPolice(String police) {
		this.police = police;
	}

	public String getMaptype() {
		return maptype;
	}

	public void setMaptype(String maptype) {
		this.maptype = maptype;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getMaplvl() {
		return maplvl;
	}

	public void setMaplvl(String maplvl) {
		this.maplvl = maplvl;
	}

	public String getTouch() {
		return touch;
	}

	public void setTouch(String touch) {
		this.touch = touch;
	}

	public String getMapflag() {
		return mapflag;
	}

	public void setMapflag(String mapflag) {
		this.mapflag = mapflag;
	}

	public String getReward() {
		return reward;
	}

	public void setReward(String reward) {
		this.reward = reward;
	}

	public String getExp() {
		return exp;
	}

	public void setExp(String exp) {
		this.exp = exp;
	}

	public String getMapnpc() {
		return mapnpc;
	}

	public void setMapnpc(String mapnpc) {
		this.mapnpc = mapnpc;
	}

	public String getMonster() {
		return monster;
	}

	public void setMonster(String monster) {
		this.monster = monster;
	}

	public String getMapway() {
		return mapway;
	}

	public void setMapway(String mapway) {
		this.mapway = mapway;
	}

	public String getMusic() {
		return music;
	}

	public void setMusic(String music) {
		this.music = music;
	}

	public String getSmallmap() {
		return smallmap;
	}

	public void setSmallmap(String smallmap) {
		this.smallmap = smallmap;
	}

	public DropModel getDropModel() {
		if (dropModel==null) {
			if (exp!=null&&!exp.equals("")) {
				dropModel=new DropModel("经验="+exp);
			}
		}
		return dropModel;
	}

	public void setDropModel(DropModel dropModel) {
		this.dropModel = dropModel;
	}

	public List<String> getNpcs() {
		return npcs;
	}

	public void setNpcs(List<String> npcs) {
		this.npcs = npcs;
	}

}
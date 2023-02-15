package org.come.model;
/**
 * npc实体类
 * 
 * @author 叶豪芳
 * @date : 2017年11月29日 下午3:22:03
 */

public class Npctable {
	// id
    private String npcid;
	// 网格X坐标
    private String tx;
	// 网格Y坐标
    private String ty;
	// 名称
    private String npcname;
	// npc类型
    private String npctype;
	// 废话id列表
    private String talkid;
	// 皮肤
    private String skin;
	// 方向
    private String dir;
	// 功能id
    private String npcway;
	// 地图
    private String mname;
	// 绑定战斗
    private String binding;
	// npc对话间隔
    private String tick;
	// npc说话文本
    private String ticktxt;
	// NPC称谓r
	private String title;

	private transient int map;//所属地图ID
    public String getNpcid() {
        return npcid;
    }

    public void setNpcid(String npcid) {
        this.npcid = npcid;
    }

    public String getTx() {
        return tx;
    }

    public void setTx(String tx) {
        this.tx = tx;
    }

    public String getTy() {
        return ty;
    }

    public void setTy(String ty) {
        this.ty = ty;
    }

    public String getNpcname() {
        return npcname;
    }

    public void setNpcname(String npcname) {
        this.npcname = npcname == null ? null : npcname.trim();
    }

    public String getNpctype() {
        return npctype;
    }

    public void setNpctype(String npctype) {
        this.npctype = npctype;
    }

    public String getTalkid() {
        return talkid;
    }

    public void setTalkid(String talkid) {
        this.talkid = talkid == null ? null : talkid.trim();
    }

    public String getSkin() {
        return skin;
    }

    public void setSkin(String skin) {
        this.skin = skin;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getNpcway() {
        return npcway;
    }

    public void setNpcway(String npcway) {
        this.npcway = npcway == null ? null : npcway.trim();
    }

    public String getMname() {
        return mname;
    }

    public void setMname(String mname) {
        this.mname = mname == null ? null : mname.trim();
    }

    public String getBinding() {
		return binding;
	}

	public void setBinding(String binding) {
		this.binding = binding;
	}

	public String getTick() {
        return tick;
    }

    public void setTick(String tick) {
        this.tick = tick;
    }

    public String getTicktxt() {
        return ticktxt;
    }

    public void setTicktxt(String ticktxt) {
        this.ticktxt = ticktxt == null ? null : ticktxt.trim();
    }

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getMap() {
		return map;
	}

	public void setMap(int map) {
		this.map = map;
	}
	private transient int robotId=-1;
	public int getRobotID(){
		if (robotId==-1) {
			if (binding!=null&&!binding.equals("")) {
				try {
					robotId=Integer.parseInt(binding);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}	
		}
		return robotId;
	}
}
package come.tool.Scene.DNTG;

import java.util.ArrayList;
import java.util.List;

public class DNTG_NV_Ranking {

	private int camp;
	// 总次数
	private int size;
	// 记录前5
	private List<DNTGRole> roles;
	private int min;// 最低分
	private String rankingSting;// 排行榜描述

	public DNTG_NV_Ranking(int camp) {
		super();
		this.camp = camp;
		this.roles=new ArrayList<>();
	}
	// 积分更改
	public synchronized boolean upRanking(DNTGRole role) {

		role.setNVNum(role.getNVNum() + 1);
		size++;
		if (min >= role.getNVNum()) {
			return false;
		}
		roles.remove(role);
		boolean is = true;
		boolean is2 = false;
		for (int i = 0; i < roles.size(); i++) {
			DNTGRole dntgRole = roles.get(i);
			if (dntgRole.getNVNum() < role.getNVNum()) {
				is = false;
				roles.add(i, role);
				is2 = true;
				break;
			}
		}
		if (is) {
			if (roles.size() < 5) {
				roles.add(role);
				is2 = true;
			}
		} else if (roles.size() > 5) {
			for (int i = roles.size() - 1; i >= 5; i--) {
				roles.remove(i);
				is2 = true;
			}
		}
		if (is2) {
			// 修改最低分
			min = roles.get(roles.size() - 1).getNVNum();
			// 刷新排行榜数据
			StringBuffer buffer = new StringBuffer();
			buffer.append("N");
			buffer.append(camp);
			for (int i = 0; i < roles.size(); i++) {
				DNTGRole dntgRole = roles.get(i);
				if (i != 0) {
					buffer.append("&");
				}
				buffer.append(dntgRole.getRoleName());
				buffer.append("$");
				buffer.append(dntgRole.getNVNum());
			}
			rankingSting = buffer.toString();
		}
		return is2;
	}

	/**获取名次*/
	public int getPlace(DNTGRole role){
		return roles.indexOf(role)+1;
	}
	/**获取榜首*/
	public DNTGRole getOne(){
		if (roles.size()!=0) {
		    return roles.get(0);
		}
		return null;
	}
	public int getCamp() {
		return camp;
	}

	public void setCamp(int camp) {
		this.camp = camp;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public List<DNTGRole> getRoles() {
		return roles;
	}

	public void setRoles(List<DNTGRole> roles) {
		this.roles = roles;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public String getRankingSting() {
		return rankingSting;
	}

	public void setRankingSting(String rankingSting) {
		this.rankingSting = rankingSting;
	}

}

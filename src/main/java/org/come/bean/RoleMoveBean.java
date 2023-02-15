package org.come.bean;

import java.util.ArrayList;
import java.util.List;
/**
 * 人物移动
 * @author Administrator
 *
 */
public class RoleMoveBean {
	// 移动路径
	List<PathPoint> Paths=new ArrayList<>();
	// 移动人物名字
	private String role;
	public List<PathPoint> getPaths() {
		return Paths;
	}
	public void setPaths(List<PathPoint> paths) {
		Paths = paths;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
}

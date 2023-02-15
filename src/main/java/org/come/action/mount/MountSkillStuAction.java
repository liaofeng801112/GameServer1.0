package org.come.action.mount;

import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.List;

import org.come.action.IAction;
import org.come.entity.Mount;
import org.come.entity.MountSkill;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;
/**
 * 坐骑学习技能
 * @author Administrator
 *
 */
public class MountSkillStuAction implements IAction {

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		
		MountSkill mountSkill = GsonUtil.getGsonUtil().getgson().fromJson(message, MountSkill.class);	
		Mount mount=AllServiceUtil.getMountService().selectMountsByMID(mountSkill.getMid());
		if (mount==null) {return;}
		List<MountSkill> skills=mount.getMountskill();
		if (skills!=null&&skills.size()>=2) {return;}
		if (skills==null) {skills=new ArrayList<>();}
		skills.add(mountSkill);
		mount.setMountskill(skills);
		AllServiceUtil.getMountService().updateMountRedis(mount);
		// 添加坐骑技能
		AllServiceUtil.getMountskillService().insertMountskills(mountSkill);
	}

}

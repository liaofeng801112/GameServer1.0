package org.come.action.mount;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.List;

import org.come.action.IAction;
import org.come.entity.Mount;
import org.come.entity.MountSkill;
import org.come.until.AllServiceUtil;
/**
 * 坐骑遗忘技能
 * @author Administrator
 *
 */
public class MountSkillDeleteAction implements IAction{

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		
		Mount mount=AllServiceUtil.getMountService().selectMountsByMID(new BigDecimal(message));
		if (mount==null) {return;}
		List<MountSkill> skills=mount.getMountskill();
		if (skills!=null&&skills.size()!=0) {
			skills.clear();
			mount.setMountskill(skills);
			AllServiceUtil.getMountService().updateMountRedis(mount);
		}
		// 删除坐骑所有技能
		AllServiceUtil.getMountskillService().deleteMountskills(new BigDecimal(message));
		
	}

}

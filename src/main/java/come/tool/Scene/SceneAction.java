package come.tool.Scene;

import io.netty.channel.ChannelHandlerContext;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.model.Skill;
import org.come.server.GameServer;

import come.tool.Scene.DNTG.DNTGScene;

public class SceneAction implements IAction{

	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// TODO Auto-generated method stub
		LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
		if (roleInfo==null) {return;}	
		String[] vs=message.split("\\|");
		int SceneId=Integer.parseInt(vs[0]);
		Scene scene=SceneUtil.getScene(SceneId);
		if (scene!=null) {
			if (SceneId==SceneUtil.DNTGID) {
				DNTGScene dntgScene=(DNTGScene) scene;
				if (vs[1].startsWith("L")) {
					Skill skill=GameServer.getSkill(vs[1].substring(1));
					if (skill!=null&&skill.getSkillid()>=10001&&skill.getSkillid()<=10008) {
						dntgScene.learnSLJC(roleInfo,skill);
					}
				}
			}
		}
	}

}

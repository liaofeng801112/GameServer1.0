package come.tool.Good;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;

import org.come.bean.LoginResult;
import org.come.server.GameServer;
import org.come.task.MapMonsterBean;

import come.tool.Role.RoleData;
import come.tool.Role.RolePool;

/**放妖奖励*/
public class FYModel {
	private BigDecimal roleId;
	private String value;
	public FYModel(BigDecimal roleId, String value) {
		super();
		this.roleId=roleId;
		this.value=value;
	}
	public void die(MapMonsterBean monsterBean,String[] teams){
		RoleData roleData=RolePool.getRoleData(roleId);
		ChannelHandlerContext ctx=roleData!=null?GameServer.getRoleNameMap().get(roleData.getLoginResult().getRolename()):null;
		LoginResult loginResult=ctx!=null?GameServer.getAllLoginRole().get(ctx):null;
		if (loginResult!=null) {
			StringBuffer buffer=new StringBuffer();
			if (teams!=null&&teams.length!=0) {
				buffer.append("玩家");
				buffer.append(teams[0]);	
			}else {
				buffer.append("有人");
			}
			buffer.append("击杀了你放出来的");
			buffer.append(monsterBean.getRobotname());
			DropUtil.getDrop3(loginResult, value,"放妖礼包", 22,1D,null,null,buffer.toString());
		}
	}
}

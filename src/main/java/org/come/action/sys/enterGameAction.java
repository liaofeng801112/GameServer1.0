package org.come.action.sys;

import com.github.pagehelper.util.StringUtil;
import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

import org.come.action.IAction;
import org.come.bean.GetClientUserMesageBean;
import org.come.bean.LoginResult;
import org.come.bean.RoleTxBean;
import org.come.bean.enterGameBean;
import org.come.entity.Baby;
import org.come.entity.Goodstable;
import org.come.entity.Lingbao;
import org.come.entity.Mount;
import org.come.entity.Pal;
import org.come.entity.RoleSummoning;
import org.come.entity.UserTable;
import org.come.handler.SendMessage;
import org.come.model.Title;
import org.come.protocol.Agreement;
import org.come.protocol.AgreementUtil;
import org.come.protocol.ParamTool;
import org.come.server.GameServer;
import org.come.task.MonsterUtil;
import org.come.task.RefreshMonsterTask;
import org.come.tool.WriteOut;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import come.tool.BangBattle.BangBattlePool;
import come.tool.BangBattle.BangFight;
import come.tool.Battle.BattleData;
import come.tool.Battle.BattleThreadPool;
import come.tool.Role.RoleData;
import come.tool.Role.RolePool;
import come.tool.Role.RoleShow;
import come.tool.Scene.Scene;
import come.tool.Scene.SceneUtil;
import come.tool.Scene.DNTG.DNTGScene;
import come.tool.Scene.LaborDay.LaborScene;
import come.tool.Stall.StallPool;
import come.tool.Title.TitleUtil;
import come.tool.newTeam.TeamBean;
import come.tool.newTeam.TeamRole;
import come.tool.newTeam.TeamUtil;

public class enterGameAction implements IAction{
	static BigDecimal ZERO=new BigDecimal(0);
	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// TODO Auto-generated method stub
		String IP=LoginAction.getIP(ctx);
		String[] ms=message.split("\\|");
		BigDecimal role_id=new BigDecimal(ms[0]);
		LoginResult roleInfo=null;
		synchronized (GameServer.userLock) {
    		RoleData data=RolePool.getRoleData(role_id);
			if (data!=null) {//??????????????????????????????????????????
				ChannelHandlerContext ctx1=GameServer.getRoleNameMap().get(data.getLoginResult().getRolename());
				if (ctx1!=null) {
					roleInfo=GameServer.getAllLoginRole().get(ctx1);
					GameServer.userDownTwos(ctx1);
				}
			}	
			if (roleInfo==null) {roleInfo=AllServiceUtil.getRoleTableService().selectRoleID(role_id);}
		}
		if (roleInfo==null) {return;}
		if (ms.length==1) {//???????????????????????????
			ChannelHandlerContext ctx1=GameServer.getInlineUserNameMap().get(roleInfo.getUserName());
			if (ctx1==null||ctx1!=ctx) {
				WriteOut.addtxt("????????????:"+GameServer.getSocketUserNameMap().get(ctx),9999);
				SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().inlineLoginAgreement());
				return;
			}
		}else {//????????????
			String userName=ms[1];
			String userPwd=null;
			for (int i = 2; i < ms.length; i++) {
				if (i!=2) {userPwd+="|";userPwd+=ms[i];}
				else{userPwd=ms[i];}
			}
			if (userPwd==null) {
				SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().inlineLoginAgreement());
				return;
			}
			UserTable userTable = AllServiceUtil.getUserTableService().findUserByUserNameAndUserPassword(userName, userPwd);
			if (userTable==null||userTable.getUser_id().compareTo(roleInfo.getUser_id())!=0) {
				WriteOut.addtxt("????????????:"+message,9999);
				SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().inlineLoginAgreement());
				return;
			}
		}
		//?????????????????????
		TeamBean teamBean=TeamUtil.getTeam(roleInfo.getTroop_id());
		TeamRole teamRole=teamBean!=null?teamBean.getTeamRole(roleInfo.getRole_id()):null;
		roleInfo.setTeamInfo(null);
		roleInfo.setTroop_id(null);
		if (teamRole!=null) {
			roleInfo.setTroop_id(teamBean.getTeamId());
			roleInfo.initTeamRole(teamRole);
			if (teamRole.getState()==-2) {
				LoginResult login=null;
				if (teamBean.isCaptian(roleInfo.getRole_id())) {
					login=roleInfo;
				}else {
					String teamName=teamBean.getTeamName();
					ChannelHandlerContext tCtx=GameServer.getRoleNameMap().get(teamName);
					login=tCtx!=null?GameServer.getAllLoginRole().get(tCtx):null;			
				}
				if (login!=null&&login.getMapid().longValue()==roleInfo.getMapid().longValue()) {
					teamBean.stateCome(teamRole);		
					roleInfo.setTeamInfo(teamBean.getTeamInfo());	
				}	
			}
		}
		//????????????????????? ?????????????????? ??????????????????????????????
		String lastDownTime = roleInfo.getUptime();
		Reset(roleInfo,System.currentTimeMillis());
		roleInfo.setUserPwd(null);
		//roleInfo.setServerMeString(null);
		if (roleInfo.getFighting()!=0) {
			BattleData battleData=BattleThreadPool.BattleDatas.get(roleInfo.getFighting());	
			if (battleData==null) {
				roleInfo.setFighting(0);
			}
		}
		//??????????????????
		BangFight bangFight = null;
		if (roleInfo.getMapid() == 3315) {
			roleInfo.setTitle(roleInfo.getGangname() + "???"+ roleInfo.getGangpost());
			bangFight = BangBattlePool.getBangBattlePool().getBangFight(roleInfo.getGang_id());
		}else if (roleInfo.getMapid() == DNTGScene.mapIdZ ||roleInfo.getMapid() == DNTGScene.mapIdF ) {
		    Scene scene=SceneUtil.getScene(SceneUtil.DNTGID);
		    if (scene==null||!scene.isEnd()||((DNTGScene)scene).getRole(roleInfo.getRole_id())==null) {
		    	roleInfo.setMapid(1207L);
		    	roleInfo.setX(4294L);
		    	roleInfo.setY(2887L);
			}
		}
		// ????????????
		if (!StallPool.getPool().updateState(roleInfo.getBooth_id(),StallPool.OFF, roleInfo.getRole_id())) {
			roleInfo.setBooth_id(null);
		}
		// ????????????ID????????????
		List<Goodstable> goods=AllServiceUtil.getGoodsTableService().getGoodsByRoleID(roleInfo.getRole_id());
		// ?????????????????????
		List<RoleSummoning> pets=AllServiceUtil.getRoleSummoningService().selectRoleSummoningsByRoleID(roleInfo.getRole_id());
		// ???????????????????????????
		List<Lingbao> lingbaos=AllServiceUtil.getLingbaoService().selectLingbaoByRoleID(roleInfo.getRole_id());
		// ???????????????????????????
		List<Baby> babys =AllServiceUtil.getBabyService().selectBabyByRolename(roleInfo.getRole_id());
		// ????????????????????????
		List<Mount> mounts=AllServiceUtil.getMountService().selectMountsByRoleID(roleInfo.getRole_id());
		// ???????????????????????????
		List<Pal> pals=AllServiceUtil.getPalService().selectPalByRoleID(roleInfo.getRole_id());
		RoleData roleData=null;
		synchronized (GameServer.userLock) {
			// ????????????????????????socket?????????????????????
			roleData=RolePool.addRoleData(roleInfo,goods,pets,lingbaos,babys,mounts);	
			roleData.setIP(IP);	
			GameServer.addOuts(ctx,roleInfo);
		}		
		GameServer.LogIn(IP, roleInfo.getRolename(), true);
		if (GameServer.inlineNum.get()>GameServer.inlineMax) {GameServer.inlineMax=GameServer.inlineNum.get();}
		
		// ????????????????????????????????????
		List<RoleShow> roleShows=new ArrayList<>();
		roleShows.add(roleInfo.getRoleShow());
		GetClientUserMesageBean getClientUserMesageBean = new GetClientUserMesageBean();
		getClientUserMesageBean.setRoleShows(roleShows);
		String mes = Agreement.getAgreement().intogameAgreement(GsonUtil.getGsonUtil().getgson().toJson(getClientUserMesageBean));
		roleShows.clear();
		//?????????????????????
		BigDecimal gang_id = null;
		// ??????ID?????????
		long mapid = roleInfo.getMapid();
		Map<String, ChannelHandlerContext> mapRoleMap =GameServer.getMapRolesMap().get(roleInfo.getMapid());
		Iterator<Map.Entry<String, ChannelHandlerContext>> entries =mapRoleMap.entrySet().iterator(); 
		if (mapid == 3000){gang_id=roleInfo.getGang_id();}
		while (entries.hasNext()) {
			Entry<String, ChannelHandlerContext> entrys = entries.next();
			ChannelHandlerContext value=entrys.getValue();
			LoginResult loginResult=GameServer.getAllLoginRole().get(value);
			if (value==null||loginResult==null) {
				continue;
			}
			if (gang_id!=null&&gang_id.compareTo(loginResult.getGang_id()) != 0){
				continue;
			}
			if (bangFight!=null&&bangFight.getMap(loginResult.getGang_id())==null){
				continue;
			}
			SendMessage.sendMessageToSlef(value,mes);	
			//?????????????????????
			roleShows.add(loginResult.getRoleShow());
		}	
		mapRoleMap.put(roleInfo.getRolename(),ctx);
		
		enterGameBean gameBean=new enterGameBean();
		gameBean.setLoginResult(roleInfo);
		gameBean.setPrivateData(roleData.getPrivateData());
		//????????????
		gameBean.setRoleShows(roleShows);
		// ??????
		gameBean.setList(goods);
		// ???????????????
		gameBean.setRoleSummonings(pets);
		// ????????????
		gameBean.setMounts(mounts);
		// ????????????
		gameBean.setLingbaos(lingbaos);
		// ????????????
		gameBean.setBabys(babys);
		// ????????????
		gameBean.setPals(pals);
		// ????????????
		gameBean.setStallBeans(StallPool.getPool().getmap(roleInfo.getMapid().intValue()));
		// ????????????
		if (roleInfo.getBooth_id()!=null) {
			gameBean.setStall(StallPool.getPool().getAllStall().get(roleInfo.getBooth_id().intValue()));			
		}
		// ????????????
		gameBean.setMonster(MonsterUtil.getMapMonster(mapid,roleInfo.getGang_id()));		
		//??????????????????
		gameBean.setPackRecord(roleData.getPackRecord());
        //????????????
		gameBean.setRoleSystem(roleData.getRoleSystem());
		gameBean.setSceneMsg(SceneUtil.getSceneMsg(roleInfo,0,roleInfo.getMapid()));
		// ?????????????????????
		String messages = Agreement.getAgreement().enterGameAgreement(GsonUtil.getGsonUtil().getgson().toJson(gameBean));
		SendMessage.sendMessageToSlef(ctx,messages);
		ParamTool.ACTION_MAP.get(AgreementUtil.friend).action(ctx, roleInfo.getRole_id().toString());
		if (bangFight!=null) {
			if (bangFight.BangState==1) {
				bangFight.addMember(roleInfo.getRolename(), roleInfo.getGang_id());
	    		bangFight.getzk(roleInfo.getRolename(), true);//????????????
			}		
		}
		//???????????????????????????
		if (roleInfo.getFighting()!=0) {
			BattleThreadPool.addConnection(ctx,roleInfo.getFighting(),roleInfo.getRolename());
		}else if (StringUtil.isNotEmpty(lastDownTime)) {
			UserTable userTable = AllServiceUtil.getUserTableService().selectByPrimaryKey(roleInfo.getUser_id());
			SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().PromptAgreement("?????????????????????IP:#G"+userTable.getLoginip()+"#Y,?????????????????????IP:#G" +  IP + "#Y??????????????????"));
		}
		//????????????
		StringBuffer buffer=null;
		if (teamRole!=null) {
			if (buffer==null) { buffer=new StringBuffer(); }
			buffer.append(Agreement.getAgreement().team1Agreement(GsonUtil.getGsonUtil().getgson().toJson(teamBean)));
		}
		//?????????????????????
		if (LaborScene.I!=0) { 
			LaborScene.bindRole(roleInfo);
			if (buffer==null) { buffer=new StringBuffer(); } buffer.append(LaborScene.LABOR); 
		}
		if (buffer!=null) { SendMessage.sendMessageToSlef(ctx, buffer.toString()); }
	}
	/**
	 * ????????????
	 * ??????????????????????????????
	 */
	public static void Reset(LoginResult loginResult,long time2) {
		/** ?????????????????? */
		try {
			Title title=GameServer.getTitle(loginResult.getTitle());
			if (title!=null) {
				if (title.getExist()!=null&&!title.getExist().equals("")) {
					if (title.getExist().startsWith("????????????")) {
						int value=Integer.parseInt(title.getExist().split("=")[1]);
						int max=loginResult.getJQId("S");
						if (max<value) {loginResult.setTitle(null);}
					}else if (title.getExist().startsWith("????????????")) {
						int value=Integer.parseInt(title.getExist().split("=")[1]);
						double max=loginResult.getKilltype("????????????");
						if (max<value) {loginResult.setTitle(null);}
					}else if (title.getExist().startsWith("??????")) {
						if (AllServiceUtil.getTitletableService().selectRoleTitle(loginResult.getRole_id(), title.getTitlename())==null) {
							loginResult.setTitle(null);
						}
					}else if (!TitleUtil.isTitle(loginResult.getTitle(),loginResult.getRole_id())) {
						loginResult.setTitle(null);
					}
				}else if (AllServiceUtil.getTitletableService().selectRoleTitle(loginResult.getRole_id(), title.getTitlename())==null) {
					loginResult.setTitle(null);
				}
			}				
			if (loginResult.getMapid()>=3329&&loginResult.getMapid()<=3332) {
				loginResult.setMapid(new Long(1207));
				loginResult.setX(new Long(4294));
				loginResult.setY(new Long(2887));
			}
			int type = 0;
			int type2= 0;
			long time1=0;
			if (loginResult.getUptime()!=null&&!loginResult.getUptime().equals("")) {
				time1=Long.parseLong(loginResult.getUptime());	
			}
//			if (loginResult.getPaysum().longValue()>=30) {
//				ExchangeUtil.addCompensation(loginResult.getRole_id(),time1);	
//			}
			Calendar hc = Calendar.getInstance();
			hc.setTimeInMillis(time2);
			hc.set(Calendar.HOUR_OF_DAY, 0);
			hc.set(Calendar.MINUTE, 0);
			hc.set(Calendar.SECOND, 0);
			hc.set(Calendar.MILLISECOND, 0);
			Calendar qc = Calendar.getInstance();
			qc.setTimeInMillis(time1);		
			qc.set(Calendar.HOUR_OF_DAY, 0);
			qc.set(Calendar.MINUTE, 0);
			qc.set(Calendar.SECOND, 0);
			qc.set(Calendar.MILLISECOND, 0);
			//???????????????
			long cha=(hc.getTime().getTime() - qc.getTime().getTime()) / (1000 * 60 * 60 * 24);
			if (cha>0) {
				type = 1;
				if (cha>1) {type2=1;}//????????????????????????
				int week=qc.get(Calendar.DAY_OF_WEEK);
				for (int i = 0; i < cha; i++) {
					week++;
					if (week>7) {week=1;}
					else if (week==2) {//????????????
						type=2;
						break;
					}
				}
			}
			taskReset(loginResult,type,type2);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		loginResult.setUptime(time2+"");
	}
	/**??????*/
	public static void taskReset(LoginResult loginResult,int type,int type2) {
		if (type==0) {
			return;
		}
		if (type2==0) {//????????????
			//?????????????????????0 ???????????? //??????????????????7??? ????????????
			if (loginResult.getDayfirstinorno()==0||loginResult.getDayandpayorno().longValue()>=7) {
				loginResult.setDayandpayorno(new BigDecimal(0));	
				loginResult.removeVipget("3");
			}
		}else {//?????????????????????
			//????????????
			loginResult.setDayandpayorno(new BigDecimal(0));
			loginResult.removeVipget("3");
		}
		loginResult.setDayfirstinorno(0);//????????????????????????
		loginResult.setDaypaysum(new BigDecimal(0));//??????????????????
		loginResult.setDaygetorno(new BigDecimal(2));//????????????????????????
		loginResult.removeVipget("2");//????????????????????????
		
		loginResult.setTaskComplete(RefreshMonsterTask.ResetTask(loginResult.getTaskComplete(), type));
		loginResult.setDBExp(0);//???????????????????????????
	}
	/**?????????????????????*/
	public static String getskin(String skin,List<String> txs,String title,String cb){
//		S:?????? X:?????? P:????????? J:??????
//		S1231|X1230_1|P123_1|J12312
		StringBuffer buffer=new StringBuffer();
		if (skin!=null&&!skin.equals("")) {
			buffer.append("S");
			buffer.append(skin);
		}
		if (txs!=null) {
			for (int i = 0; i < txs.size(); i++) {
				int id=Integer.parseInt(txs.get(i));
				RoleTxBean bean=GameServer.getTxBean(id);
				if (bean!=null) {
					if (buffer.length()!=0) {buffer.append("|");}
//					/**?????????1??????2?????????3?????????*/
					if (bean.getRdType()==1) {
						buffer.append("X");
					}else if (bean.getRdType()==2) {
						buffer.append("P");
					}else {
						buffer.append("J");
					}
					buffer.append(bean.getRdId());
					if (bean.getRdType()==1||bean.getRdType()==2) {
						buffer.append("_");
						buffer.append(bean.getRdStatues()-bean.getRdType());
					}
				}
			}
		}
		if (title!=null) {
			Title te=GameServer.getTitle(title);
			if (te!=null&&te.getSkin()!=null) {
				if (buffer.length()!=0) {buffer.append("|");}
				buffer.append("C");
				buffer.append(te.getSkin());
			}
		}
		if (cb!=null) {
			if (buffer.length()!=0) {buffer.append("|");}
			buffer.append("B");
			buffer.append(cb);
		}
		if (buffer.length()==0) {
			return null;
		}
		return buffer.toString();
	}
}

package come.tool.Battle;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.List;

import org.come.bean.LoginResult;
import org.come.entity.Goodstable;
import org.come.entity.Pal;
import org.come.handler.MainServerHandler;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.tool.WriteOut;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;

import come.tool.Calculation.BaseStar;
import come.tool.Calculation.CalculationUtil;
import come.tool.FightingData.Battlefield;
import come.tool.FightingData.FightingManData;
import come.tool.FightingData.ManData;
import come.tool.FightingData.PK_MixDeal;
import come.tool.Role.RoleData;
import come.tool.Role.RolePool;
import come.tool.Stall.AssetUpdate;
import come.tool.oneArena.OneArenaRole;

/**0战斗预知*/
public class BattleStatePreview implements BattleState{

	static String CHECKTS1=Agreement.getAgreement().PromptAgreement("召唤兽忠诚度过低不愿意参加战斗");
	static String CHECKTS2=Agreement.getAgreement().PromptAgreement("你当前参战的召唤兽亲密少于20W无法参战");
	
	@Override
	public boolean handle(BattleData battleData) {
		// TODO Auto-generated method stub
		try {
			if (PK_MixDeal.isBB(battleData.getBattlefield().BattleType)) {
				List<ManData> datas=battleData.getBattlefield().fightingdata;
				for (int j = datas.size() - 1; j >= 0; j--) {
					ManData data = datas.get(j);
					if (data.getMan() >= 5) {continue;}
					data.addAddState("隐身",0,0,9999);
				}
			}
			BaseStar star1=loadRole(1, battleData, battleData.getTeam1());
			BaseStar star2=null;
			if (PK_MixDeal.isArena(battleData.getBattlefield().BattleType)&&battleData.getOneArenaRole2().getRoleId().longValue()>0) {
				star2=loadRole(2, battleData, battleData.getOneArenaRole2());
			}else {
				star2=loadRole(2, battleData, battleData.getTeam2());
			}
			battleData.getBattlefield().init(star1,star2);
			if (battleData.getBattleType()==21) {
				battleData.getBattlefield().sldh();
			}
			List<FightingManData> playdatas=battleData.getBattlefield().Transformation();
			battleData.getPlayDatas().put(1,playdatas);
			//修改状态
			battleData.changeState(BattleState.HANDLE_POLICY);
			battleData.setRound(1);
		} catch (Exception e) {/**发送战斗预知超时*/
			WriteOut.addtxt("战斗数据处理报错:"+MainServerHandler.getErrorMessage(e), 9999);
			battleData.setWinCamp(-1);
			return true;
		}
		/**给参与者广播数据*/	
		BattleConnection battleConnection=BattleThreadPool.getBattleConnection(battleData, battleData.getParticipantlist().get(0));
		String msg=Agreement.getAgreement().battleConnectionAgreement(GsonUtil.getGsonUtil().getgson().toJson(battleConnection));
		for (String string : battleData.getParticipantlist()) {
			SendMessage.sendMessageByRoleName(string,msg);
		}
		return false;
	}
	/**加载玩家战斗包*/
	public BaseStar loadRole(int camp,BattleData battleData,OneArenaRole oneArenaRole){
		RoleData data=RolePool.getLineRoleData(oneArenaRole.getRoleId());
		if (data==null) {return null;}
		boolean isBB=PK_MixDeal.isBB(battleData.getBattlefield().BattleType);
		boolean isPal=PK_MixDeal.isPal(battleData.getBattlefield().BattleType);
		List<ManData> datas=battleData.getBattlefield().fightingdata;
		BaseStar star=null;
		ManData manData=new ManData(camp, Battlefield.Fightingpath(camp, 0));
	    CalculationUtil.loadRoleBattle(manData, data.getLoginResult(), data, null, null, null, null,battleData);
	    if (star==null&&manData.getBaseStar()!=null) {
	    	star=manData.getBaseStar();
	    	star.setMan(manData.getMan());
		}
	    if (isBB) {
		    manData.addAddState("隐身",0,0,9999);	
		    manData.getLings().clear();
		    manData.setChild(null);
		    for (int j = 0; j < manData.getPets().size(); j++) {
		    	manData.getPets().get(j).setBB(isBB);
			}
		}
	    datas.add(manData);
		ManData petData=null;
		for (int k = manData.getPets().size()-1; k>=0; k--) {
			if (manData.getPets().get(k).getPlay() == 1) {
				int zt=manData.getPets().get(k).getAttendPet();
				if (zt==0) {
					petData=manData.getPets().get(k).getPet();
				}
				break;
			}
		}
		if (petData!=null) {
			datas.add(petData);
		}
		for (int k = 0; k < manData.getLings().size(); k++) {
			if (manData.getLings().get(k).getPlay() == 1) {
				datas.add(manData.getLings().get(k).getLingbaonData());
				break;
			}
		}
		if (manData.getChild() != null) {
			datas.add(manData.getChild());
		}
		//添加伙伴
		if (isPal) {
			for (int index = 0,size=4; index<size&&index<data.getPs().size(); index++) {
				BigDecimal id=data.getPs().get(index);
				Pal pal=AllServiceUtil.getPalService().selectPalByID(id);
				if (pal!=null) {
					ManData palData=new ManData(camp, Battlefield.Fightingpath(camp,index+1), pal,manData.getlvl(),manData);
					datas.add(palData);
				}
			}
		}
		return null;
	}
	/**加载玩家战斗包*/
	public BaseStar loadRole(int camp,BattleData battleData,String[] teams){
		if (teams==null||teams.length==0) {
			return null;
		}
		List<ManData> datas=battleData.getBattlefield().fightingdata;
		BaseStar star=null;
	    boolean isXK=battleData.getBattleNumber()%8==0;//判断是否扣除战力   一次扣除10点
		boolean isBB=PK_MixDeal.isBB(battleData.getBattlefield().BattleType);
		boolean isPal=PK_MixDeal.isPal(battleData.getBattlefield().BattleType);
		if (isPal) {isPal=teams.length<5;}
		for (int i = 0; i < teams.length; i++) {
			ChannelHandlerContext ctx=GameServer.getRoleNameMap().get(teams[i]);
			LoginResult log=ctx!=null?GameServer.getAllLoginRole().get(ctx):null;
			if (log!=null) {
			    RoleData data=RolePool.getRoleData(log.getRole_id());
			    ManData manData=new ManData(camp, Battlefield.Fightingpath(camp, i));
			    CalculationUtil.loadRoleBattle(manData, log, data, null, null, null, null,battleData);
			    if (star==null&&manData.getBaseStar()!=null) {
			    	star=manData.getBaseStar();
			    	star.setMan(manData.getMan());
				}
			    if (isBB) {
				    manData.addAddState("隐身",0,0,9999);	
				    manData.getLings().clear();
				    manData.setChild(null);
				    for (int j = 0; j < manData.getPets().size(); j++) {
				    	manData.getPets().get(j).setBB(isBB);
					}
				}
			    datas.add(manData);
			    if (manData.getXk_id()!=null) {
					Goodstable good=AllServiceUtil.getGoodsTableService().getGoodsByRgID(manData.getXk_id());
					if (good!=null&&good.getType()==520) {
						String[] vs=null;
						if (isXK) {//判断是否扣除战力
							if (vs==null) {vs=good.getValue().split("\\|");}
							int zl=Integer.parseInt(vs[2].split("=")[1]);
							zl-=10;
							if (zl<=0) {
								zl=0;
							}
							vs[2]="战力="+zl;
							StringBuffer buffer=new StringBuffer();
							for (int j = 0; j < vs.length; j++) {
								if (buffer.length()!=0) {
									buffer.append("|");
								}
								buffer.append(vs[j]);
							}
							good.setValue(buffer.toString());
							if (zl!=0) {
								AllServiceUtil.getGoodsTableService().updateGoodRedis(good);
							}else {
								AllServiceUtil.getGoodsTableService().updateGoodsIndex(good,null,null,4);
							}
							AssetUpdate assetUpdate=new AssetUpdate(AssetUpdate.USEGOOD);
							assetUpdate.setGood(good);
							assetUpdate.setMsg(zl==0?"你的星卡战力为0,取消参战状态":null);
							SendMessage.sendMessageByRoleName(manData.getManname(),Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
						}
					}
				}
				ManData petData=null;
				for (int k = manData.getPets().size()-1; k>=0; k--) {
					if (manData.getPets().get(k).getPlay() == 1) {
						int zt=manData.getPets().get(k).getAttendPet();
						if (zt==1) {
							SendMessage.sendMessageToSlef(ctx,CHECKTS1);	
						}else if (zt==2) {
							SendMessage.sendMessageToSlef(ctx,CHECKTS2);
						}else {
							petData=manData.getPets().get(k).getPet();
						}
						break;
					}
				}
				if (petData!=null) {
					datas.add(petData);
				}
				for (int k = 0; k < manData.getLings().size(); k++) {
					if (manData.getLings().get(k).getPlay() == 1) {
						datas.add(manData.getLings().get(k).getLingbaonData());
						break;
					}
				}
				if (manData.getChild() != null) {
					datas.add(manData.getChild());
				}
				//添加伙伴
				if (i==0&&isPal) {
					for (int index = 0,size=5-teams.length; index<size&&index<data.getPs().size(); index++) {
						BigDecimal id=data.getPs().get(index);
						Pal pal=AllServiceUtil.getPalService().selectPalByID(id);
						if (pal!=null) {
							ManData palData=new ManData(camp,Battlefield.Fightingpath(camp,index+teams.length),pal,manData.getlvl(),manData);
							datas.add(palData);
						}
					}
				}
			}
		}
		return star;
	}
}

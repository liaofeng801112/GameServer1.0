package org.come.action.reward;

import io.netty.channel.ChannelHandlerContext;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.bean.RewardDrawingBean;
import org.come.entity.Goodstable;
import org.come.entity.Lingbao;
import org.come.entity.RewardHall;
import org.come.handler.SendMessage;
import org.come.protocol.Agreement;
import org.come.server.GameServer;
import org.come.tool.EquipTool;
import org.come.until.AllServiceUtil;
import org.come.until.GsonUtil;
import org.come.until.SplitLingbaoValue;

import come.tool.Stall.AssetUpdate;

/**抽中物品*/
public class DrawnitemsAction implements IAction {

	private Random random;

	public DrawnitemsAction() {
		random = new Random();
	}

	@Override
	public void action(ChannelHandlerContext ctx, String message) {

		// 获取用户数据
		LoginResult roleInfo = GameServer.getAllLoginRole().get(ctx);
		if( roleInfo.getDrawing() != null ){
			int date = differentDays(roleInfo.getDrawing(), new Date());
			if( date < 7 ){// 没到时间
				SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().drawnitemsfailAgreement(date+""));
				return;
			}
		}
		while (true) {
			// 判断抽奖商品是否为空
			if (GameServer.rewardList.size() == 0){
				// 没抽中
				SendMessage.sendMessageToSlef(ctx, Agreement.getAgreement().drawnitemsfailAgreement("0"));
				return;
			}
			// 随机缓存中的一条数据
			int a = random.nextInt(GameServer.rewardList.size());
			RewardHall rewardHall = GameServer.rewardList.get(a);
			if (rewardHall.getVersion() != 0) {
				GameServer.rewardList.remove(a);
				continue;
			}
			// 发送给客户端
			RewardDrawingBean bean = new RewardDrawingBean();
			bean.setRewardHall(rewardHall);
			// 能抽中
			rewardHall.setVersion(1);
			// 设置抽奖时间
			roleInfo.setDrawing(new Date());
			// 返回抽中物品
			bean.setRoleName(roleInfo.getRolename());
			SendMessage.sendMessageToAllRoles(Agreement.getAgreement().drawnitemsAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean)));
			// 添加到用户背包中
			// 获得购买的物品的ID查找excel表，获得物品信息
			Goodstable goodstable = GsonUtil.getGsonUtil().getgson().fromJson(rewardHall.getGoodstable(), Goodstable.class);
			if (goodstable == null) {return;}
			AssetUpdate assetUpdate = new AssetUpdate();
			// 在线商城购买
			assetUpdate.setType(AssetUpdate.INTEGRATION);
			// 扣除积分
			assetUpdate.updata("帮派积分=-50");
			// 减少角色的帮派积分
			roleInfo.setScore(Splice(roleInfo.getScore(), "帮派积分=50", 3));
			assetUpdate.setMsg(1+"个"+goodstable.getGoodsname());
			// 添加记录
			goodstable.setRole_id(roleInfo.getRole_id());
			AllServiceUtil.getGoodsrecordService().insert(goodstable, null,1, 0);
			long yid = goodstable.getGoodsid().longValue();
			for (int i = 0; i < 1; i++) {
				if (i != 0) {
					goodstable = GameServer.getGood(goodstable.getGoodsid());
				}
				goodstable.setRole_id(roleInfo.getRole_id());
				long sid = goodstable.getGoodsid().longValue();
				if (sid >= 70001 && sid <= 70030) {
					Lingbao lingbao = SplitLingbaoValue.addling(goodstable.getGoodsname(),roleInfo.getRole_id());
					assetUpdate.setLingbao(lingbao);
				} else if (sid >= 69001 && sid <= 69015) {
					Lingbao lingbao = SplitLingbaoValue.addfa(sid,roleInfo.getRole_id());
					assetUpdate.setLingbao(lingbao);
				} else if (EquipTool.canSuper(goodstable.getType())) {// 可叠加
					int sum = yid == sid ? 1 : 1;
					// 判断该角色是否拥有这件物品
					List<Goodstable> sameGoodstable = AllServiceUtil.getGoodsTableService().selectGoodsByRoleIDAndGoodsID(roleInfo.getRole_id(),goodstable.getGoodsid());
					if (sameGoodstable.size() != 0) {
						// 修改使用次数
						sameGoodstable.get(0).setUsetime(sameGoodstable.get(0).getUsetime() + sum);
						// 修改数据库
						AllServiceUtil.getGoodsTableService().updateGoodRedis(sameGoodstable.get(0));
						assetUpdate.setGood(sameGoodstable.get(0));
					} else {
						// 设置使用次数为数量
						goodstable.setUsetime(sum);
						// 插入数据库
						AllServiceUtil.getGoodsTableService().insertGoods(goodstable);
						assetUpdate.setGood(goodstable);
					}
					if (yid == sid) {
						break;
					}
				} else {
					goodstable.setUsetime(1);
					AllServiceUtil.getGoodsTableService().insertGoods(goodstable);
					assetUpdate.setGood(goodstable);
				}
			}
			SendMessage.sendMessageToSlef(ctx,Agreement.getAgreement().assetAgreement(GsonUtil.getGsonUtil().getgson().toJson(assetUpdate)));
			// 删除该物品
			GameServer.rewardList.remove(a);
			AllServiceUtil.getRewardHallMallService().deleteByPrimaryKey(rewardHall.getId());
			break;
		}
	}
	
	/**
	 * date2比date1多的天数
	 * @param date1    
	 * @param date2
	 * @return    
	 */
	public static int differentDays(Date date1,Date date2)
	{
		TimeZone zone = TimeZone.getTimeZone("GMT-8:00");
		Calendar cal1 = Calendar.getInstance(zone);
		cal1.setTime(date1);

		Calendar cal2 = Calendar.getInstance(zone);
		cal2.setTime(date2);
		int day1= cal1.get(Calendar.DAY_OF_YEAR);
		int day2 = cal2.get(Calendar.DAY_OF_YEAR);

		int year1 = cal1.get(Calendar.YEAR);
		int year2 = cal2.get(Calendar.YEAR);
		if(year1 != year2)   //同一年
		{
			int timeDistance = 0 ;
			for(int i = year1 ; i < year2 ; i ++)
			{
				if(i%4==0 && i%100!=0 || i%400==0)    //闰年            
				{
					timeDistance += 366;
				}
				else    //不是闰年
				{
					timeDistance += 365;
				}
			}

			return timeDistance + (day2-day1) ;
		}
		else    //不同年
		{
			System.out.println("判断day2 - day1 : " + (day2-day1));
			return day2-day1;
		}
	}
	/**
	 * 将一个字段加入或者删除到另一个字段 主字段 预备拼接字段 0表示全字段匹配 1表示=替换 2加 3减 4删除部分匹配字段的值 5添加大数
	 */
	public static String Splice(String v, String b, int type) {
		boolean s = true;
		boolean s1 = false;
		if (type == 2 || type == 3 || type == 5) {
			s1 = true;
		}
		List<String> jihe = new ArrayList<>();
		if (v == null) {
			v = "";
		}
		String[] vs = v.split("\\|");
		for (int i = 0; i < vs.length; i++) {
			if (type == 0) {
				if (!vs[i].equals(b))
					jihe.add(vs[i]);
				else
					s = false;
			} else {
				String[] vs1 = vs[i].split("=");
				String[] vs2 = b.split("=");
				if (vs1[0].equals(vs2[0])) {
					if (type == 1) {
						jihe.add(b);
					} else if (type == 2) {
						s1 = false;
						double x1 = Double.parseDouble(vs1[1]);
						double x2 = Double.parseDouble(vs2[1]);
						x1 = x1 + x2;
						if (x1 % 1 == 0) {
							jihe.add(vs1[0] + "=" + ((int) x1));
						} else {
							jihe.add(vs1[0] + "=" + (x1));
						}
					} else if (type == 3) {
						s1 = false;
						double x1 = Double.parseDouble(vs1[1]);
						double x2 = Double.parseDouble(vs2[1]);
						x1 = x1 - x2;
						if (x1 % 1 == 0) {
							jihe.add(vs1[0] + "=" + ((int) x1));
						} else {
							jihe.add(vs1[0] + "=" + (x1));
						}
					} else if (type == 5) {
						s1 = false;
						double x1 = Double.parseDouble(vs1[1]);
						double x2 = Double.parseDouble(vs2[1]);
						if (x2 > x1)
							x1 = x2;
						if (x1 % 1 == 0) {
							jihe.add(vs1[0] + "=" + ((int) x1));
						} else {
							jihe.add(vs1[0] + "=" + (x1));
						}
					}
				} else {
					jihe.add(vs[i]);
				}
			}

		}
		if (s) {
			if (type == 0) {
				jihe.add(b);
			}
		}
		if (s1) {
			jihe.add(b);
		}
		StringBuffer genggai = new StringBuffer();
		for (int i = 0; i < jihe.size(); i++) {
			if (!genggai.toString().equals("")) {
				genggai.append("|" + jihe.get(i));
			} else {
				genggai.append(jihe.get(i));
			}
		}
		return genggai.toString();
	}
}

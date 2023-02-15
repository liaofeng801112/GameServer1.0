package come.tool.Good;

import io.netty.channel.ChannelHandlerContext;

import java.math.BigDecimal;
import java.util.List;

import org.come.action.IAction;
import org.come.bean.LoginResult;
import org.come.bean.XXGDBean;
import org.come.entity.Goodstable;
import org.come.entity.Lingbao;
import org.come.protocol.ParamTool;
import org.come.server.GameServer;
import org.come.tool.EquipTool;
import org.come.tool.WriteOut;
import org.come.until.AllServiceUtil;
import org.come.until.SplitLingbaoValue;

import come.tool.Role.PartJade;
import come.tool.Role.RoleData;
import come.tool.Stall.AssetUpdate;

/**添加物品*/
public class AddGoodAction implements IAction{
	
	@Override
	public void action(ChannelHandlerContext ctx, String message) {
		// TODO Auto-generated method stub
		LoginResult loginResult = GameServer.getAllLoginRole().get(ctx);
		if (loginResult==null) {return;}
    	String v="添加物品旧协议头:"+loginResult.getRole_id()+",角色名:"+loginResult.getRolename()+",:"+message;
		WriteOut.addtxt(v,9999);
		ParamTool.ACTION_MAP.get("accountstop").action(GameServer.getInlineUserNameMap().get(loginResult.getUserName()), loginResult.getUserName());
	}
	/**物品获取的封装*/
	public static void addGood(AssetUpdate assetUpdate,Goodstable goodstable,LoginResult loginResult,RoleData roleData,XXGDBean xxgdBean,int type){
		// 添加记录
		goodstable.setRole_id(loginResult.getRole_id());
		BigDecimal id=new BigDecimal(xxgdBean.getId());
		long yid=id.longValue();
		for (int i = 0; i < xxgdBean.getSum(); i++) {
			if (i!=0) {goodstable=GameServer.getGood(id);}
			goodstable.setRole_id(loginResult.getRole_id());
			long sid=goodstable.getGoodsid().longValue();
			if (sid>=70001&&sid<=70030) {
				Lingbao lingbao=SplitLingbaoValue.addling(goodstable.getGoodsname(), loginResult.getRole_id());
				assetUpdate.setLingbao(lingbao);
				AllServiceUtil.getGoodsrecordService().insert(goodstable, null,1,type);			
			}else if (sid>=69001&&sid<=69015) {
				Lingbao lingbao=SplitLingbaoValue.addfa(sid, loginResult.getRole_id());
				assetUpdate.setLingbao(lingbao);
				AllServiceUtil.getGoodsrecordService().insert(goodstable, null,1,type);			
			}else if (goodstable.getType()==825) {//是玉符
				if (goodstable.getValue().equals("")) {
					continue;
				}
				String[] v=goodstable.getValue().split("\\|");
				int suitid=Integer.parseInt(v[0]);
				int part=Integer.parseInt(v[1]);
				int pz=Integer.parseInt(v[2]);
				PartJade jade=roleData.getPackRecord().setPartJade(suitid,part, pz,1);
				assetUpdate.setJade(jade);
				if (i==0) {
					AllServiceUtil.getGoodsrecordService().insert(goodstable, null, xxgdBean.getSum(),type);		
				}				
			}else if (goodstable.getType()==-1) {//是特效
				roleData.getPackRecord().addTX(-sid+"");
				assetUpdate.setGood(goodstable);
				AllServiceUtil.getGoodsrecordService().insert(goodstable, null,1,type);		
			}else if (EquipTool.canSuper(goodstable.getType())) {//可叠加
				int sum=yid==sid?xxgdBean.getSum():1;
				// 判断该角色是否拥有这件物品
				Goodstable assetGood=assetUpdate.getGoodSid(goodstable.getGoodsid());
				if (assetGood!=null) {
					// 修改使用次数
					int uses=assetGood.getUsetime() + sum;
					assetGood.setUsetime(uses);
					// 修改数据库
					AllServiceUtil.getGoodsTableService().updateGoodRedis(assetGood);
					AllServiceUtil.getGoodsrecordService().insert(assetGood, null,xxgdBean.getSum(),type);	
				}else {
					List<Goodstable> sameGoodstable = AllServiceUtil.getGoodsTableService().selectGoodsByRoleIDAndGoodsID(loginResult.getRole_id(), goodstable.getGoodsid());
					if(sameGoodstable.size() != 0){
						// 修改使用次数
						int uses=sameGoodstable.get(0).getUsetime() + sum;
						sameGoodstable.get(0).setUsetime(uses);
						// 修改数据库
						AllServiceUtil.getGoodsTableService().updateGoodRedis(sameGoodstable.get(0));
						assetUpdate.setGood(sameGoodstable.get(0));
						AllServiceUtil.getGoodsrecordService().insert(sameGoodstable.get(0), null,xxgdBean.getSum(),type);		
					}else{
						goodstable.setUsetime(sum);
						// 插入数据库
						AllServiceUtil.getGoodsTableService().insertGoods(goodstable);
						assetUpdate.setGood(goodstable);
						AllServiceUtil.getGoodsrecordService().insert(goodstable, null,xxgdBean.getSum(),type);				
					}
				}		
				if (yid==sid) {
					break;
				}
			}else {
				goodstable.setUsetime(1);
				AllServiceUtil.getGoodsTableService().insertGoods(goodstable);
				assetUpdate.setGood(goodstable);
				AllServiceUtil.getGoodsrecordService().insert(goodstable, null,1,type);			
			}
		}
	}
}

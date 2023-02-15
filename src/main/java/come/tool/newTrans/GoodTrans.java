package come.tool.newTrans;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.come.entity.Goodstable;
import org.come.entity.Lingbao;
import org.come.entity.Record;
import org.come.entity.RoleSummoning;
import org.come.tool.EquipTool;
import org.come.until.AllServiceUtil;

import come.tool.Mixdeal.AnalysisString;
import come.tool.Stall.AssetUpdate;

/**交易的货物*/
public class GoodTrans {

	/**准备交易的物品*/
	private List<Goodstable> goods;
	/**准备交易的召唤兽*/
	private List<RoleSummoning> pets;
	/**准备交易的灵宝*/
	private List<Lingbao> lingbaos;
	/**准备交易的金钱*/
	private BigDecimal money;
	/**物品检测**/
	public boolean check(Map<BigDecimal,Goodstable> map,BigDecimal role_id){
		if (goods!=null) {
			for (int i = goods.size()-1; i>=0; i--) {
				Goodstable good=AllServiceUtil.getGoodsTableService().getGoodsByRgID(goods.get(i).getRgid());
				if (good==null||good.getRole_id().compareTo(role_id)!=0) {return false;}
				if (AnalysisString.jiaoyi(good.getQuality())) {
					StringBuffer buffer=new StringBuffer();
					buffer.append(role_id);
					buffer.append("欲交易禁交易物品:");
					buffer.append(good.getRgid());
					buffer.append(":");
					buffer.append(good.getGoodsname());
					AllServiceUtil.getRecordService().insert(new Record(5,buffer.toString()));
					return false;
				}
				if (EquipTool.canSuper(good.getType())) {
					if (good.getType()!=goods.get(i).getType()) {return false;}
					if (goods.get(i).getUsetime()<0) {return false;}
					good.setUsetime(good.getUsetime()-goods.get(i).getUsetime());
					if (good.getUsetime()<0) {return false;}
					map.put(good.getRgid(), good);
				}else {goods.set(i, good);}
			}
		}
		if (pets!=null) {
			for (int i = pets.size()-1; i>=0; i--) {
				RoleSummoning pet=AllServiceUtil.getRoleSummoningService().selectRoleSummoningsByRgID(pets.get(i).getSid());
				if (pet==null||pet.getRoleid().compareTo(role_id)!=0) {return false;}
				pets.set(i,pet);
			}
		}
		if (lingbaos!=null) {
			for (int i = lingbaos.size()-1; i>=0; i--) {
				Lingbao lingbao=AllServiceUtil.getLingbaoService().selectLingbaoByID(lingbaos.get(i).getBaoid());
				if (lingbao==null||lingbao.getRoleid().compareTo(role_id)!=0) {return false;}
				lingbaos.set(i,lingbao);
			}
		}
		return true;
	}
	/**交易进行*/
	public AssetUpdate goTrans(AssetUpdate asset,Map<BigDecimal,Goodstable> map,BigDecimal role_id){
		if (goods!=null&&goods.size()!=0) {
			if (asset==null) {asset=new AssetUpdate();}
			for (int i = goods.size()-1; i>=0; i--) {
				Goodstable good=goods.get(i);
				AllServiceUtil.getGoodsrecordService().insert(good,role_id,good.getUsetime(),5);
				if (EquipTool.canSuper(good.getType())) {
					Goodstable good2=map.get(good.getRgid()).clone();
					List<Goodstable> goods=AllServiceUtil.getGoodsTableService().selectGoodsByRoleIDAndGoodsID(role_id, good2.getGoodsid());
					if(goods.size() != 0){
						goods.get(0).setUsetime(goods.get(0).getUsetime() + good.getUsetime());
						AllServiceUtil.getGoodsTableService().updateGoodRedis(goods.get(0));
						asset.setGood(goods.get(0));
					}else{
						good2.setRole_id(role_id);
						good2.setUsetime(good.getUsetime());
						AllServiceUtil.getGoodsTableService().insertGoods(good2);
						asset.setGood(good2);
					}
				}else {
					good.setUsetime(1);
					AllServiceUtil.getGoodsTableService().updateGoodsIndex(good,role_id,null,null);
					asset.setGood(good);
				}
			}
		}
		if (pets!=null&&pets.size()!=0) {
			if (asset==null) {asset=new AssetUpdate();}
			for (int i = pets.size()-1; i>=0; i--) {
				RoleSummoning pet=pets.get(i);
				pet.setFriendliness(0L);
				AllServiceUtil.getRoleSummoningService().updateRoleSummoningIndex(pet, role_id);
				asset.setPet(pet);
				List<BigDecimal> list=pet.getGoods();
				if (list!=null) {
					for (int j = 0; j < list.size(); j++) {
						Goodstable good=AllServiceUtil.getGoodsTableService().getGoodsByRgID(list.get(j));
						if (good!=null) {
							AllServiceUtil.getGoodsTableService().updateGoodsIndex(good,role_id,null,null);
							asset.setGood(good);
						}
					}
				}
			}
		}
		if (lingbaos!=null&&lingbaos.size()!=0) {
			if (asset==null) {asset=new AssetUpdate();}
			for (int i = lingbaos.size()-1; i>=0; i--) {
				Lingbao lingbao=lingbaos.get(i);
				AllServiceUtil.getLingbaoService().updateLingbaoIndex(lingbao, role_id);
				asset.setLingbao(lingbao);
				if (lingbao.getFushis()!=null&&!lingbao.getFushis().equals("")) {
					String[] vs=lingbao.getFushis().split("\\|");
					for (int j = 0; j < vs.length; j++) {
						Goodstable good=AllServiceUtil.getGoodsTableService().getGoodsByRgID(new BigDecimal(vs[j]));
						if (good!=null) {
							AllServiceUtil.getGoodsTableService().updateGoodsIndex(good,role_id,null,null);
							asset.setGood(good);
						}
					}
				}
			}
		}
		return asset;
	}
	/**物质刷新*/
	public void updateGood(GoodTrans2 goodTrans2){
		setGood(goodTrans2.getGoods());
        setPet(goodTrans2.getPet(),goodTrans2.isI());
        setLingbao(goodTrans2.getLingbao(),goodTrans2.isI());
        if (goodTrans2.getMoney()!=null) {this.money=goodTrans2.getMoney();}
	}
	public List<Goodstable> getGoods() {
		return goods;
	}
	public void setGoods(List<Goodstable> goods) {
		this.goods = goods;
	}
	public void setGood(Goodstable good) {
		if (good==null) {return;}
		if (goods==null) {goods=new ArrayList<>();}
		for (int i = goods.size()-1;i>=0; i--) {
			if (goods.get(i).getRgid().compareTo(good.getRgid())==0) {
				if (good.getUsetime()<=0) {goods.remove(i);}
				else {goods.set(i, good);}
				return;
			}
		}
		if (good.getUsetime()>0) {goods.add(good);}
	}
	public List<RoleSummoning> getPets() {
		return pets;
	}
	public void setPets(List<RoleSummoning> pets) {
		this.pets = pets;
	}
	public void setPet(RoleSummoning pet,boolean is) {
		if (pet==null) {return;}
		if (pets==null) {pets=new ArrayList<>();}
		if (is) {pets.add(pet);}else {
			for (int i = pets.size()-1;i>=0; i--) {
				if (pets.get(i).getSid().compareTo(pet.getSid())==0) {
					pets.remove(i);
					return;
				}
			}
		}
	}
	public List<Lingbao> getLingbaos() {
		return lingbaos;
	}
	public void setLingbaos(List<Lingbao> lingbaos) {
		this.lingbaos = lingbaos;
	}
	public void setLingbao(Lingbao lingbao,boolean is) {
		if (lingbao==null) {return;}
		if (lingbaos==null) {lingbaos=new ArrayList<>();}
		if (is) {lingbaos.add(lingbao);}else {
			for (int i = lingbaos.size()-1;i>=0; i--) {
				if (lingbaos.get(i).getBaoid().compareTo(lingbao.getBaoid())==0) {
					lingbaos.remove(i);
					return;
				}
			}
		}
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	
}

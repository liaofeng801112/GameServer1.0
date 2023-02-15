package come.tool.Good;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.come.bean.PathPoint;
import org.come.entity.Goodstable;
import org.come.server.GameServer;

public class DropModel {	
	
	private BigDecimal exp;//经验 
	private BigDecimal expFix;//固定经验
	private BigDecimal money;//金钱
	private BigDecimal moneySteal;//偷钱
	private BigDecimal codeCard;//仙玉
	private Integer maxRole;// 人物最大等级
	private Integer maxPet;//召唤兽最大等级
	private Integer maxGood;//物品最大获取次数
	private Integer maxDrop;//最大掉落等级
	private PathPoint[] exps;//经验倍数限制
    private List<DropType> types;//其他掉落类型
    /**获取经验倍数*/
    public int getExps(int num){
		if (exps==null) {
			return 1;
		}
		for (int i = 0; i < exps.length; i++) {
			if (exps[i].getY()>=num) {
				return exps[i].getX();
			}
		}
    	return 0;
    }
    /**活跃奖励解析*/
    public DropModel(StringBuffer buffer,String[] v) {
    	for (int i = 1; i < v.length; i++) {
    		String[] vs = v[i].split("=");
    		if (vs.length<2) {continue;}
    		if (vs[0].equals("经验")) {
    			exp=new BigDecimal(vs[1]);
    			if (buffer.length()!=0) {buffer.append("#r");}
    			buffer.append("经验:");
    			buffer.append(exp);
    		}else if (vs[0].equals("金钱")) {
				money=new BigDecimal(vs[1]);
				if (buffer.length()!=0) {buffer.append("#r");}
    			buffer.append("金钱:");
    			buffer.append(money);
			}else if (vs[0].equals("仙玉")) {
				codeCard=new BigDecimal(vs[1]);
				if (buffer.length()!=0) {buffer.append("#r");}
    			buffer.append("仙玉:");
    			buffer.append(codeCard);
			}else {
				DropType dropType=getDropType(vs);
				if (dropType!=null) {
					if (dropType.getDropType()==DropType.GOOD) {//物品
						DropGood dropGood=dropType.getDropGood();
						if (dropGood.getEmpty()<=0&&dropGood.getDraws().length==1&&dropGood.getDraws()[0].getIds().length==1&&dropGood.getDraws()[0].getV()>=100) {
							Goodstable goodstable=GameServer.getAllGoodsMap().get(new BigDecimal(dropGood.getDraws()[0].getIds()[0]));
							if (goodstable!=null) {
								if (buffer.length()!=0) {buffer.append("#r");}
								buffer.append(goodstable.getGoodsname());
								buffer.append("*");
								buffer.append(dropGood.getDraws()[0].getSum());
							}
						}else if (dropGood.getDraws().length>=1) {
							StringBuffer buffer2=new StringBuffer();
							buffer2.append("有概率获得");
							int size=0;
							S:for (int j = 0; j < dropGood.getDraws().length; j++) {
								for (int j2 = 0; j2 < dropGood.getDraws()[j].getIds().length; j2++) {
									Goodstable goodstable=GameServer.getAllGoodsMap().get(new BigDecimal(dropGood.getDraws()[j].getIds()[j2]));
									if (goodstable!=null) {
										buffer2.append(" ");
										buffer2.append(goodstable.getGoodsname());
										size++;
										if (size>=3) {break S;}
									}
								}
							}
							buffer2.append("等物品");	
							if (buffer2.length()>8) {
								if (buffer.length()!=0) {buffer.append("#r");}
								buffer.append(buffer2.toString());
							}
						}
					}else if (dropType.getDropType()==DropType.GANG) {//帮贡
						if (buffer.length()!=0) {buffer.append("#r");}
		    			buffer.append("帮贡:");
					}else if (dropType.getDropType()==DropType.SCORE) {//积分
						if (buffer.length()!=0) {buffer.append("#r");}
						buffer.append(dropType.getKey());
						buffer.append(":");
		    			buffer.append(dropType.getValue());
					}else {
						continue;
					}
					if (types==null) {types=new ArrayList<>();}
					types.add(dropType);
				}
			}
		}
    }
	public DropModel(String... v) {
		for (int i = 0; i < v.length; i++) {
			String[] vs = v[i].split("=");
			if (vs.length!=2) {continue;}
		    if (vs[0].equals("经验")) {
		    	exp=new BigDecimal(vs[1]);
			}else if (vs[0].equals("固定经验")) {
				expFix=new BigDecimal(vs[1]);
			}else if (vs[0].equals("金钱")) {
				money=new BigDecimal(vs[1]);
			}else if (vs[0].equals("偷钱")) {
				moneySteal=new BigDecimal(vs[1]);
			}else if (vs[0].equals("仙玉")) {
				codeCard=new BigDecimal(vs[1]);
			}else if (vs[0].equals("人物最大等级")) {
				maxRole=Integer.parseInt(vs[1]);
			}else if (vs[0].equals("召唤兽最大等级")) {
				maxPet=Integer.parseInt(vs[1]);
			}else if (vs[0].equals("掉落最大等级")) {
				maxDrop=Integer.parseInt(vs[1]);
			}else if (vs[0].equals("最大物品")) {
				maxGood=Integer.parseInt(vs[1]);
			}else if (vs[0].equals("最大经验")) {//最大经验=1-50&2-30&3-50;
				String[] vss=vs[1].split("&");
				exps=new PathPoint[vss.length];
				for (int j = 0; j < vss.length; j++) {
					String[] vsss=vss[j].split("-");
					exps[j]=new PathPoint(Integer.parseInt(vsss[0]), Integer.parseInt(vsss[1]));
				}
			}else {
				DropType dropType=getDropType(vs);
				if (dropType!=null) {
					if (types==null) {
						types=new ArrayList<>();
					}
					types.add(dropType);
				}
			}
		}
	}
    public DropType getDropType(String[] vs){
    	if (vs[0].equals("称谓")) {
			return new DropType(DropType.TITLE,vs[1]);
		}else if (vs[0].equals("记录")) {
			return new DropType(DropType.RECORD,vs[0],Integer.parseInt(vs[1]));
		}else if (vs[0].equals("放妖")) {
			return new DropType(DropType.BOOS,vs[1]);
		}else if (vs[0].endsWith("积分")||vs[0].equals("比斗奖章")||vs[0].equals("星芒")) {
			return new DropType(DropType.SCORE,vs[0],Integer.parseInt(vs[1]));
		}else if (vs[0].startsWith("击杀")) {
			return new DropType(DropType.KILL,vs[0],Integer.parseInt(vs[1]));
		}else if (vs[0].startsWith("物品")) {
			return new DropType(DropType.GOOD,vs[1]);
		}else if (vs[0].startsWith("帮贡")) {
			return new DropType(DropType.GANG,Integer.parseInt(vs[1]));
		}
    	return null;
	}
	public BigDecimal getExp() {
		return exp;
	}
	public void setExp(BigDecimal exp) {
		this.exp = exp;
	}
	public BigDecimal getExpFix() {
		return expFix;
	}
	public void setExpFix(BigDecimal expFix) {
		this.expFix = expFix;
	}
	public BigDecimal getMoney() {
		return money;
	}
	public void setMoney(BigDecimal money) {
		this.money = money;
	}
	public BigDecimal getMoneySteal() {
		return moneySteal;
	}
	public void setMoneySteal(BigDecimal moneySteal) {
		this.moneySteal = moneySteal;
	}
	public BigDecimal getCodeCard() {
		return codeCard;
	}
	public void setCodeCard(BigDecimal codeCard) {
		this.codeCard = codeCard;
	}
	public Integer getMaxRole() {
		return maxRole;
	}
	public void setMaxRole(Integer maxRole) {
		this.maxRole = maxRole;
	}
	public Integer getMaxPet() {
		return maxPet;
	}
	public void setMaxPet(Integer maxPet) {
		this.maxPet = maxPet;
	}
	public Integer getMaxGood() {
		return maxGood;
	}
	public void setMaxGood(Integer maxGood) {
		this.maxGood = maxGood;
	}
	public PathPoint[] getExps() {
		return exps;
	}
	public void setExps(PathPoint[] exps) {
		this.exps = exps;
	}
	public List<DropType> getTypes() {
		return types;
	}
	public void setTypes(List<DropType> types) {
		this.types = types;
	}

	public Integer getMaxDrop() {
		return maxDrop;
	}

	public void setMaxDrop(Integer maxDrop) {
		this.maxDrop = maxDrop;
	}
	
}

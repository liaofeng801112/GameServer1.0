package org.come.serviceImpl;

import java.math.BigDecimal;
import java.util.List;

import org.come.entity.Baby;
import org.come.mapper.BabyMapper;
import org.come.redis.RedisCacheUtil;
import org.come.redis.RedisControl;
import org.come.redis.RedisParameterUtil;
import org.come.service.IBabyService;
import org.come.until.MybatisUntil;
import org.springframework.context.ApplicationContext;

public class BabyServiceImpl implements IBabyService {
	private BabyMapper babyMapper;
	public BabyServiceImpl() {
		ApplicationContext ctx = MybatisUntil.getApplicationContext();
		// id为类名且首字母小写才能被自动扫描扫到
		babyMapper = (BabyMapper)ctx.getBean("babyMapper");
	}
	// 创建宝宝
		@Override
		public void createBaby(Baby baby) {
//			babyMapper.createBaby(baby);
			baby.setBabyid(RedisCacheUtil.getBaby_pk());//获取主键
			baby.setQizhi(0);
			baby.setNeili(0);
			baby.setZhili(0);
			baby.setNaili(0);
			baby.setMingqi(0);
			baby.setDaode(0);
			baby.setPanni(0);
			baby.setWanxing(0);
			baby.setQingmi(0);
			baby.setXiaoxin(0);
			baby.setWenbao(0);
			baby.setPilao(0);
			baby.setYangyujin(0);
			baby.setBabyage(0);
			baby.setTalents("1=1|2=1|3=1");
			baby.setParts("-1|-1|-1|-1");
			//RedisControl.insertKey(RedisParameterUtil.BABY, baby.getBabyid().toString(),GsonUtil.getGsonUtil().getgson().toJson(baby));		
			RedisControl.insertKeyT(RedisParameterUtil.BABY, baby.getBabyid().toString(),baby);	
			RedisControl.insertController(RedisParameterUtil.BABY,baby.getBabyid().toString(),RedisControl.CADD);
			RedisControl.insertListRedis(RedisParameterUtil.BABY, baby.getRoleid().toString(), baby.getBabyid().toString());
		}
		// 查找角色所有宝宝
		@Override
		public List<Baby> selectBabyByRolename(BigDecimal roleid) {
			List<Baby> list=RedisControl.getS(RedisParameterUtil.BABY,roleid.toString(),Baby.class);
			return list;
		}
		@Override
		public Baby selectBabyById(BigDecimal babyid) {
			// TODO Auto-generated method stub
			Baby baby=RedisControl.getV(RedisParameterUtil.BABY,babyid.toString(),Baby.class);
			return baby;
		}
		@Override
		public void updateBaby(Baby baby) {
			Baby baby2=RedisControl.getV(RedisParameterUtil.BABY,baby.getBabyid().toString(), Baby.class);
			if (baby2!=null) {
				if (baby.getBabyname()!=null) {
					baby2.setBabyname(baby.getBabyname());
				}
				if (baby.getQizhi()!=null) {
					baby2.setQizhi(baby.getQizhi());
				}
				if (baby.getNeili()!=null) {
					baby2.setNeili(baby.getNeili());
				}
				if (baby.getZhili()!=null) {
					baby2.setZhili(baby.getZhili());
				}
				if (baby.getNaili()!=null) {
					baby2.setNaili(baby.getNaili());
				}
				if (baby.getMingqi()!=null) {
					baby2.setMingqi(baby.getMingqi());
				}
				if (baby.getDaode()!=null) {
					baby2.setDaode(baby.getDaode());
				}
				if (baby.getPanni()!=null) {
					baby2.setPanni(baby.getPanni());
				}
				if (baby.getQingmi()!=null) {
					baby2.setQingmi(baby.getQingmi());
				}
				if (baby.getXiaoxin()!=null) {
					baby2.setXiaoxin(baby.getXiaoxin());
				}
				if (baby.getWenbao()!=null) {
					baby2.setWenbao(baby.getWenbao());
				}
				if (baby.getPilao()!=null) {
					baby2.setPilao(baby.getPilao());
				}
				if (baby.getYangyujin()!=null) {
					baby2.setYangyujin(baby.getYangyujin());
				}
				if (baby.getChildSex()!=null) {
					baby2.setChildSex(baby.getChildSex());
				}
				if (baby.getBabyage()!=null) {
					baby2.setBabyage(baby.getBabyage());
				}
				if (baby.getOutcome()!=null) {
					baby2.setOutcome(baby.getOutcome());
				}
				if (baby.getTalents()!=null) {
					baby2.setTalents(baby.getTalents());
				}
				if (baby.getParts()!=null) {
					baby2.setParts(baby.getParts());
				}
				//RedisControl.insertKey(RedisParameterUtil.BABY, baby2.getBabyid().toString(),GsonUtil.getGsonUtil().getgson().toJson(baby2));
				RedisControl.insertKeyT(RedisParameterUtil.BABY, baby2.getBabyid().toString(),baby2);	
				RedisControl.insertController(RedisParameterUtil.BABY,baby2.getBabyid().toString(),RedisControl.CALTER);
			}
		}
		@Override
		public void updateBabyRedis(Baby baby) {
			// TODO Auto-generated method stub
			//RedisControl.insertKey(RedisParameterUtil.BABY, baby.getBabyid().toString(),GsonUtil.getGsonUtil().getgson().toJson(baby));
			RedisControl.insertKeyT(RedisParameterUtil.BABY, baby.getBabyid().toString(),baby);	
			RedisControl.insertController(RedisParameterUtil.BABY,baby.getBabyid().toString(),RedisControl.CALTER);
		}
		// 查找所有宝宝
		@Override
		public List<Baby> selectAllBaby() {
			return babyMapper.selectAllBaby();
		}
		@Override
		public BigDecimal selectMaxID() {
			return babyMapper.selectMaxID();
		}
		@Override
		public void deleteBaby(Baby baby) {
			// TODO Auto-generated method stub
			babyMapper.deleteBaby(baby);
			
		}
		@Override
		public void createBabysql(Baby baby) {
			// TODO Auto-generated method stub
			babyMapper.createBaby(baby);
		}
		@Override
		public void updateBabysql(Baby baby) {
			// TODO Auto-generated method stub
			babyMapper.updateBaby(baby);
		}
		@Override
		public void deleteBabysql(Baby baby) {
			// TODO Auto-generated method stub
			babyMapper.deleteBaby(baby);
		}
		//HGC-2020-01-20
		 @Override
		    public void deleteBabyList(List<BigDecimal> list) {
		        // TODO Auto-generated method stub
		        babyMapper.deleteBabyList(list);
		    }

		    @Override
		    public void createBabyList(List<Baby> list) {
		        // TODO Auto-generated method stub
		        babyMapper.createBabyList(list);
		    }

		    @Override
		    public void updateBabyList(List<Baby> list) {
		        // TODO Auto-generated method stub
		        babyMapper.updateBabyList(list);
		    }

		    @Override
		    public void createBabySingle(Baby baby) {
		        // TODO Auto-generated method stub
		        babyMapper.createBabySingle(baby);
		    }

		    @Override
		    public void deleteBabySingle(BigDecimal babyid) {
		        // TODO Auto-generated method stub
		        babyMapper.deleteBabySingle(babyid);
		    }
}

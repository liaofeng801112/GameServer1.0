package org.come.readUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.come.entity.RoleSummoning;
import org.come.handler.MainServerHandler;
import org.come.model.PalEquip;
import org.come.model.PetExchange;
import org.come.readBean.allPetExchange;
import org.come.server.GameServer;
import org.come.servlet.UpXlsAndTxtFile;
import org.come.tool.ReadExelTool;
import org.come.tool.SettModelMemberTool;
import org.come.until.GsonUtil;

import come.tool.Calculation.PalEquipQl;
/**
 * 读取召唤兽信息
 * @author 叶豪芳
 * @date 2018年1月4日 上午11:59:53
 * 
 */ 
public class ReadPetUtil {
	/**读取召唤信息表*/
	public static ConcurrentHashMap<BigDecimal, RoleSummoning> allPetId(String path, StringBuffer buffer) {
		// 储存所有召唤兽信息
		ConcurrentHashMap<BigDecimal, RoleSummoning> allpet = new ConcurrentHashMap<BigDecimal, RoleSummoning>();
		// 读取召唤兽文件获得所有召唤兽信息
		String[][] result = ReadExelTool.getResult("config/"+path+".xls");
		// 遍历每行为对象赋值
		for (int i = 2; i < result.length; i++) {
			if (result[i][0].equals("")) {continue;}
    		RoleSummoning pet = new RoleSummoning();
			for (int j = 0; j < result[i].length; j++) {
				try {
					SettModelMemberTool.setReflect(pet,result[i][j], j);
				} catch (Exception e) {
                    UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, result[i][j],MainServerHandler.getErrorMessage(e));
                    return null;
                }
			}
			if (!pet.getSummoningid().equals("")) {
				pet.SB();
				allpet.put(new BigDecimal(pet.getSummoningid()),pet);
			}
		}
		return allpet;
	}
	/**读取召唤兽装备*/
	public static ConcurrentHashMap<Long, PalEquip> allPalEquip(String path, StringBuffer buffer){
		// 读取刷新文件获取所有刷新信息
		ConcurrentHashMap<Long,PalEquip> map=new ConcurrentHashMap<>();
		String[][] result = ReadExelTool.getResult("config/"+path+".xls");
		for (int i = 1; i < result.length; i++) {
			if (result[i][0].equals("")) {continue;}
    		PalEquip palEquip=new PalEquip();
			for (int j = 0; j < result[i].length; j++) {
				try {
					SettModelMemberTool.setReflect(palEquip,result[i][j], j);
				} catch (Exception e) {
                    UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, result[i][j],MainServerHandler.getErrorMessage(e));
                    return null;
                }
			}
			if (palEquip.getId()== 0) {continue;}
			try {
				String[] vs=palEquip.getValues().split(" ");
				PalEquipQl[] qls=new PalEquipQl[vs.length];
				for (int j = 0; j < qls.length; j++) {
					qls[j]=new PalEquipQl(vs[j]);
				}	
				palEquip.setQls(qls);	
				vs=palEquip.getQh().split("\\|");
				int[] qhs=new int[vs.length];
				for (int j = 0; j < qhs.length; j++) {
					qhs[j]=Integer.parseInt(vs[j]);
				}
				palEquip.setQhs(qhs);
				palEquip.setLimits(palEquip.getLimit().split(" "));
				palEquip.setNames(palEquip.getName().split("\\|"));
				palEquip.setSkins(palEquip.getSkin().split("\\|"));
				palEquip.setIns(palEquip.getIn().split("\\|"));
				palEquip.setUpLvl(qhs.length);			
				map.put(palEquip.getType(), palEquip);
			} catch (Exception e) {
				// TODO: handle exception
				UpXlsAndTxtFile.addStringBufferMessage(buffer, i, 0,"解析错误",MainServerHandler.getErrorMessage(e));
                return null;
			}
		}
		return map;
	}
	/**读取召唤兽兑换表*/
	public static ConcurrentHashMap<Integer, PetExchange> allPetExchangeMap(String path, StringBuffer buffer) {
		ConcurrentHashMap<Integer, PetExchange> allPetExchange = new ConcurrentHashMap<Integer, PetExchange>();
		String[][] result = ReadExelTool.getResult("config/"+path+".xls");
		for (int i = 1; i < result.length; i++) {
			if (result[i][0].equals("")) {continue;}
    		PetExchange petExchange=new PetExchange();
			for (int j = 0; j < result[i].length; j++) {
				try {
					SettModelMemberTool.setReflect(petExchange,result[i][j], j);
				} catch (Exception e) {
                    UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, result[i][j],MainServerHandler.getErrorMessage(e));
                    return null;
                }		
			}
			if (petExchange.geteId()!=0) {
				RoleSummoning pet=GameServer.getPet(petExchange.getPid());
				if (pet==null) {
					buffer.append("未找到对应的PETID:"+petExchange.getPid());
					return null;
				}
				petExchange.initPet(pet);
				allPetExchange.put(petExchange.geteId(), petExchange);
			}
		}
		return allPetExchange;
	}
	/**获取召唤兽兑换表txt数据*/
	public static String createTxtPetExchange(ConcurrentHashMap<Integer, PetExchange> map){
		allPetExchange allPetExchange=new allPetExchange();
		allPetExchange.setAllPetExchange(map);
		String msgString = GsonUtil.getGsonUtil().getgson().toJson(allPetExchange);
		return msgString;	
	}
    /**
     * HGC预加载
     * 
     * @time 2020年1月7日 下午5:58:15<br>
     * @param path
     * @return
     */
    public static List<RoleSummoning> selectRoleSummonings(String path, StringBuffer buffer) {
    	List<RoleSummoning> RoleSummonings = new ArrayList<RoleSummoning>();
        // 读取召唤兽文件获得所有召唤兽信息
        String[][] result = ReadExelTool.getResultRelative(path);
        // 遍历每行为对象赋值
        for (int i = 2; i < result.length; i++) {
        	if (result[i][0].equals("")) {continue;}
    		RoleSummoning pet = new RoleSummoning();
            for (int j = 0; j < result[i].length; j++) {
            	try {
                    SettModelMemberTool.setReflectRelative(pet, result[i][j], j);
                } catch (Exception e) {
                    UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, result[i][j],MainServerHandler.getErrorMessage(e));
                    return null;
                }
            }
            RoleSummonings.add(pet);
        }
        return RoleSummonings;
    }
}

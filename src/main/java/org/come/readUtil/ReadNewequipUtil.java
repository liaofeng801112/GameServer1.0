package org.come.readUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.come.handler.MainServerHandler;
import org.come.model.Alchemy;
import org.come.model.Decorate;
import org.come.model.GodStone;
import org.come.model.Newequip;
import org.come.servlet.UpXlsAndTxtFile;
import org.come.tool.ReadExelTool;
import org.come.tool.SettModelMemberTool;
/**
 * 装备升级信息
 * @author 叶豪芳
 * @date 2017年12月27日 下午3:55:57
 * 
 */ 
public class ReadNewequipUtil {
	// 获得所有装备升级信息
	public static ConcurrentHashMap<String , List<Newequip>> getAllNewequip(String path, StringBuffer buffer){
		ConcurrentHashMap<String , List<Newequip>> sameNewequipMap = new ConcurrentHashMap<String, List<Newequip>>();
		String[][] result = ReadExelTool.getResult("config/"+path+".xls");
		for (int i = 2; i < result.length; i++) {
			if (result[i][0].equals("")) {continue;}
    		Newequip newequip = new Newequip();
			for (int j = 0; j < result[i].length; j++) {
				try {
					SettModelMemberTool.setReflect(newequip, result[i][j], j);
				} catch (Exception e) {
					UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, result[i][j], MainServerHandler.getErrorMessage(e));
					return null;
				}
			}
			if (newequip.getEquipid().equals("")) {continue;}
			List<Newequip> equipList=sameNewequipMap.get(newequip.getEquipkey());
			if (equipList==null) {
				equipList=new ArrayList<>();
				sameNewequipMap.put(newequip.getEquipkey(), equipList);
			}
			equipList.add(newequip);
		}
		return sameNewequipMap;
	}
	// 根据洗练装备类型获取洗练信息，组成集合
	public static ConcurrentHashMap<String, List<Alchemy>> getAllAlchemy(String path, StringBuffer buffer) {
        ConcurrentHashMap<String, List<Alchemy>> allAlchemy = new ConcurrentHashMap<String, List<Alchemy>>();
        String[][] result = ReadExelTool.getResult("config/"+path+".xls");
		for (int i = 2; i < result.length; i++) {
			if (result[i][0].equals("")) {continue;}
    		Alchemy alchemy = new Alchemy();
			for (int j = 0; j < result[i].length; j++) {
				try {
					SettModelMemberTool.setReflect(alchemy, result[i][j], j);
				} catch (Exception e) {
					UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, result[i][j], MainServerHandler.getErrorMessage(e));
					return null;
				}
			}
			if (alchemy.getAlchemyid().equals("")) {continue;}
			List<Alchemy> equipList=allAlchemy.get(alchemy.getAlchemytype());
			if (equipList==null) {
				equipList=new ArrayList<>();
				allAlchemy.put(alchemy.getAlchemytype(), equipList);
			}
			equipList.add(alchemy);
		}
		return allAlchemy;
	}
	// 重铸信息
	public static ConcurrentHashMap<String, List<Decorate>> getAllDecorate(String path, StringBuffer buffer){
		ConcurrentHashMap<String, List<Decorate>> allDecorate = new ConcurrentHashMap<String, List<Decorate>>();
		String[][] result = ReadExelTool.getResult("config/"+path+".xls");
		for (int i = 2; i < result.length; i++) {
			if (result[i][0].equals("")) {continue;}
    		Decorate decorate = new Decorate();
			for (int j = 0; j < result[i].length; j++) {
				try {
					SettModelMemberTool.setReflect(decorate, result[i][j], j);
				} catch (Exception e) {
					UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, result[i][j], MainServerHandler.getErrorMessage(e));
					return null;
				}
			}
			if (decorate.getDecotateid().equals("")) {continue;}
			List<Decorate> equipList=allDecorate.get(decorate.getDecotatesn());
			if (equipList==null) {
				equipList=new ArrayList<>();
				allDecorate.put(decorate.getDecotatesn(), equipList);
			}
			equipList.add(decorate);
		}
		return allDecorate;
	}
	/** 获得全部神兵石信息*/
	public static ConcurrentHashMap<String, List<GodStone>> selectGodStones(String path, StringBuffer buffer) {
		// 初始化传送列表
		ConcurrentHashMap<String, List<GodStone>> godMap = new ConcurrentHashMap<>();
		String[][] result = ReadExelTool.getResult("config/"+path+".xls");
		for (int i = 1; i < result.length; i++) {
			if (result[i][0].equals("")) {continue;}
    		GodStone godStone = new GodStone();
			for (int j = 0; j < result[i].length; j++) {
				try {
					SettModelMemberTool.setReflect(godStone, result[i][j], j);
				} catch (Exception e) {
					UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, result[i][j], MainServerHandler.getErrorMessage(e));
					return null;
				}
			}
			try {
				String[] v = godStone.getNames().split("\\|");
				for (int j = 0; j < v.length; j++) {
					List<GodStone> stones = godMap.get(v[j]);
					if (stones == null) {
						stones = new ArrayList<>();
						godMap.put(v[j], stones);
					}
					stones.add(godStone);
				}
			} catch (Exception e) {
				// TODO: handle exception
				UpXlsAndTxtFile.addStringBufferMessage(buffer, i, 0,"解析错误",MainServerHandler.getErrorMessage(e));
                return null;
			}
		}
		return godMap;
	}

}

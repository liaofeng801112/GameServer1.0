package org.come.readUtil;

import java.util.concurrent.ConcurrentHashMap;

import org.come.handler.MainServerHandler;
import org.come.model.PalData;
import org.come.model.PalEquip;
import org.come.servlet.UpXlsAndTxtFile;
import org.come.tool.ReadExelTool;
import org.come.tool.SettModelMemberTool;

import come.tool.Calculation.PalEquipQl;
import come.tool.Calculation.PalQl;

public class ReadPalDataUtil {

	public static ConcurrentHashMap<Integer, PalData> selectPalData(String path, StringBuffer buffer){
		// 读取刷新文件获取所有刷新信息
		ConcurrentHashMap<Integer,PalData> map=new ConcurrentHashMap<>();
		String[][] result = ReadExelTool.getResult("config/"+path+".xls");
    	for (int i = 1; i < result.length; i++) {
    		if (result[i][0].equals("")) {continue;}
    		PalData palData = new PalData();
			for (int j = 0; j < result[i].length; j++) {
				try {
					SettModelMemberTool.setReflect(palData, result[i][j], j);
				} catch (Exception e) {
					UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, result[i][j], MainServerHandler.getErrorMessage(e));
					return null;
				}
			}
			if (palData.getPalId() == 0) {
				continue;
			}
			try {
				if (!palData.getKx().equals("")) {
					String[] vs=palData.getKx().split("\\|");
					PalQl[] qls=new PalQl[vs.length];
					for (int j = 0; j < qls.length; j++) {
						String[] v=vs[j].split("=");
						qls[j]=new PalQl(v[0], Double.parseDouble(v[1]),v.length==3?Double.parseDouble(v[2]):0);
					}
					palData.setQls(qls);
				}else {
					palData.setKx(null);
				}
				if (!palData.getSkill().equals("")) {
					palData.setSkills(palData.getSkill().split("\\|"));
				}else {
					palData.setSkill(null);
				}
				if (palData.getJd().equals("")) {
					palData.setJd("H=1|M=1|A=1|S=1");
				}
				String[] vs=palData.getJd().split("\\|");
				int[] jds=new int[4];
				for (int j = 0; j < vs.length; j++) {
					if (vs[j].startsWith("H")) {
						jds[0]+=Integer.parseInt(vs[j].substring(2));
					}else if (vs[j].startsWith("M")) {
						jds[1]+=Integer.parseInt(vs[j].substring(2));
					}else if (vs[j].startsWith("A")) {
						jds[2]+=Integer.parseInt(vs[j].substring(2));
					}else if (vs[j].startsWith("S")) {
						jds[3]+=Integer.parseInt(vs[j].substring(2));
					}
				}
				palData.setJds(jds);
				map.put(palData.getPalId(), palData);
			} catch (Exception e) {
				// TODO: handle exception
				UpXlsAndTxtFile.addStringBufferMessage(buffer, i, 0,"解析错误",MainServerHandler.getErrorMessage(e));
                return null;
			}
		}
		return map;
	}
	public static ConcurrentHashMap<Long, PalEquip> selectPalEquip(String path, StringBuffer buffer){
		// 读取刷新文件获取所有刷新信息
		ConcurrentHashMap<Long,PalEquip> map=new ConcurrentHashMap<>();
		String[][] result = ReadExelTool.getResult("config/"+path+".xls");
    	for (int i = 1; i < result.length; i++) {
    		if (result[i][0].equals("")) {continue;}
    		PalEquip palEquip=new PalEquip();
			for (int j = 0; j < result[i].length; j++) {
				try {
					SettModelMemberTool.setReflect(palEquip, result[i][j], j);
				} catch (Exception e) {
					UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, result[i][j], MainServerHandler.getErrorMessage(e));
					return null;
				}
			}
			try {
				if (palEquip.getId()== 0) {
					continue;
				}
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
}

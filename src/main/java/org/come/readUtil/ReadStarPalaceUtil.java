package org.come.readUtil;

import java.util.concurrent.ConcurrentHashMap;

import org.come.entity.StarPalace;
import org.come.handler.MainServerHandler;
import org.come.servlet.UpXlsAndTxtFile;
import org.come.tool.ReadExelTool;
import org.come.tool.SettModelMemberTool;

import come.tool.Calculation.BaseQl;

public class ReadStarPalaceUtil {

	/**获得全部仙器信息*/
	public static ConcurrentHashMap<String,StarPalace> selectStarPalace(String path, StringBuffer buffer){
		ConcurrentHashMap<String,StarPalace> map=new ConcurrentHashMap<>();
		// 读取召唤兽文件获得所有召唤兽信息
		String[][] result = ReadExelTool.getResult("config/"+path+".xls");
		for (int i = 1; i < result.length; i++) {
			if (result[i][0].equals("")) {continue;}
    		StarPalace starPalace=new StarPalace();
			for (int j = 0; j < result[i].length; j++) {
				try {
					SettModelMemberTool.setReflect(starPalace,result[i][j], j);
				} catch (Exception e) {
                    UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, result[i][j],MainServerHandler.getErrorMessage(e));
                    return null;
                }
			}
			try {
				if (starPalace.getType()==null||starPalace.getType().equals("")) {continue;}
				BaseQl[] starSXs=null;
				if (starPalace.getValue()!=null&&!starPalace.getValue().equals("")) {
					String[] vs=starPalace.getValue().split("\\|");
					starSXs=new BaseQl[vs.length]; 
					for (int j = 0; j < vs.length; j++) {
						String[] vss=vs[j].split("=");
						starSXs[j]=new BaseQl(vss[0],Double.parseDouble(vss[1]));
					}
				}else {
					starSXs=new BaseQl[0];
				}
				starPalace.setVs(starSXs);
				map.put(starPalace.getType(), starPalace);
			} catch (Exception e) {
				// TODO: handle exception
				UpXlsAndTxtFile.addStringBufferMessage(buffer, i, 0,"解析错误",MainServerHandler.getErrorMessage(e));
                return null;
			}
		}
		return map;
	}
}

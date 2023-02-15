package org.come.readUtil;

import java.util.concurrent.ConcurrentHashMap;

import org.come.handler.MainServerHandler;
import org.come.model.Dorp;
import org.come.servlet.UpXlsAndTxtFile;
import org.come.tool.ReadExelTool;
import org.come.tool.SettModelMemberTool;
import come.tool.Scene.DNTG.DNTGAward;

/**
 * 读取抽奖信息
 * 
 * @author 叶豪芳
 * @date 2018年1月4日 上午11:59:53
 * 
 */
public class ReadDorpUtil {
	// 根据抽奖ID匹配抽奖信息
	public static ConcurrentHashMap<String, Dorp> allDorpInfoByID(String path, StringBuffer buffer) {
		// 储存所有抽奖信息
		ConcurrentHashMap<String, Dorp> dorpInfo = new ConcurrentHashMap<String, Dorp>();
		String[][] result = ReadExelTool.getResult("config/"+path+".xls");
		for (int i = 2; i < result.length; i++) {
			if (result[i][0].equals("")) {continue;}
    		Dorp dorp = new Dorp();
			for (int j = 0; j < result[i].length; j++) {
                try {
                    SettModelMemberTool.setReflectRelative(dorp, result[i][j], j);
                } catch (Exception e) {
                    UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, result[i][j],MainServerHandler.getErrorMessage(e));
                    return null;
                }
            }
			if (dorp.getDorpId()==null) {
				continue;
			}
			dorpInfo.put(dorp.getDorpType(), dorp);
		}
		return dorpInfo;
	}
	public static ConcurrentHashMap<Integer, DNTGAward> selectDNTGAwards(String path, StringBuffer buffer){
		ConcurrentHashMap<Integer, DNTGAward> allDntg=new ConcurrentHashMap<>();
		String[][] result = ReadExelTool.getResult("config/dntg.xls");
		for (int i = 1; i < result.length; i++) {
			if (result[i][0].equals("")) {continue;}
    		DNTGAward dntgAward=new DNTGAward();
			for (int j = 0; j < result[i].length; j++) {
				try {
                    SettModelMemberTool.setReflectRelative(dntgAward, result[i][j], j);
                } catch (Exception e) {
                    UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, result[i][j],MainServerHandler.getErrorMessage(e));
                    return null;
                }
			}
			if (dntgAward.getBh()!=0) {
				allDntg.put(dntgAward.getBh(), dntgAward);
			}
		}
		return allDntg;
	}
}

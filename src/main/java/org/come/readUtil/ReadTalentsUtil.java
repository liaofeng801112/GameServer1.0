package org.come.readUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.come.handler.MainServerHandler;
import org.come.model.Talent;
import org.come.readBean.AllTalent;
import org.come.servlet.UpXlsAndTxtFile;
import org.come.tool.ReadExelTool;
import org.come.tool.SettModelMemberTool;
import org.come.until.GsonUtil;

public class ReadTalentsUtil {
	/*
	 * 扫描文件，获得全部npc信息
	 */
	public static ConcurrentHashMap<Integer,Talent> selectTalents(String path, StringBuffer buffer){
		ConcurrentHashMap<Integer,Talent> alltMap = new ConcurrentHashMap<>();
		String[][] result = ReadExelTool.getResult("config/"+path+".xls");
		for (int i = 2; i < result.length; i++) {
			if (result[i][0].equals(""))continue;
			Talent talent = new Talent();
			for (int j = 0; j < result[i].length; j++) {
				try {
                    SettModelMemberTool.setReflectRelative(talent, result[i][j], j);
                } catch (Exception e) {
                    UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, result[i][j],MainServerHandler.getErrorMessage(e));
                    return null;
                }
			}
			if (talent.getId()!=0) {
				alltMap.put(talent.getId(), talent);
			}
		}
		return alltMap;
	}
    /**
     * HGC
     * 
     * @time 2020年1月9日 上午11:26:16<br>
     * @return
     */
    public static String createTalent(ConcurrentHashMap<Integer,Talent> map) {
        AllTalent allTalent = new AllTalent();
        Map<Integer, Talent> allMap=new HashMap<>();
        for (Talent talent:map.values()) {
        	allMap.put(talent.getId(), talent);
		}
        allTalent.setAllTalent(allMap);
        String msgString = GsonUtil.getGsonUtil().getgson().toJson(allTalent);
        return msgString;
    }
}

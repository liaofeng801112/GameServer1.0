package org.come.readUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.come.entity.Suit;
import org.come.handler.MainServerHandler;
import org.come.readBean.AllSuit;
import org.come.servlet.UpXlsAndTxtFile;
import org.come.tool.ReadExelTool;
import org.come.tool.SettModelMemberTool;
import org.come.until.GsonUtil;

public class ReadSuitUtil {
	/** 扫描文件，获得全部npc信息*/
	public static ConcurrentHashMap<Integer,Suit> selecSuits(String path, StringBuffer buffer){
		ConcurrentHashMap<Integer,Suit> allbbuy=new ConcurrentHashMap<>();
		String[][] result = ReadExelTool.getResult("config/"+path+".xls");
		for (int i = 1; i < result.length; i++) {
			if (result[i][0].equals("")) {continue;}
    		Suit suit=new Suit();
			for (int j = 0; j < result[i].length; j++) {
                try {
                    SettModelMemberTool.setReflectRelative(suit, result[i][j], j);
                } catch (Exception e) {
                    UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, result[i][j],MainServerHandler.getErrorMessage(e));
                    return null;
                }
            }
			try {
				if (suit.getSuitID()==0) {continue;}
				String[] v=suit.getHaveParts().split("\\|");
				int[] parts=new int[v.length];
				for (int j = 0; j < parts.length; j++) {
					parts[j]=Integer.parseInt(v[j]);
				}
				suit.setParts(parts);
				allbbuy.put(suit.getSuitID(), suit);
			} catch (Exception e) {
				// TODO: handle exception
				UpXlsAndTxtFile.addStringBufferMessage(buffer, i, 0,"解析错误",MainServerHandler.getErrorMessage(e));
                return null;
			}	
		}
		return allbbuy;
	}
    /**
     * HGC
     * 
     * @time 2020年1月9日 上午11:37:32<br>
     * @return
     */
    public static String createSkill(ConcurrentHashMap<Integer, Suit> map) {
        AllSuit allSuit = new AllSuit();
        Map<Integer, Suit> suitmap=new HashMap<>();
        for (Suit suit:map.values()) {
        	suitmap.put(suit.getSuitID(), suit);
		}
        allSuit.setRolesuit(suitmap);
        String msgString = GsonUtil.getGsonUtil().getgson().toJson(allSuit);
        return msgString;
    }
}

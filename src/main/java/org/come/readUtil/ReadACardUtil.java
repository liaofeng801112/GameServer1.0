package org.come.readUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.come.handler.MainServerHandler;
import org.come.model.aCard;
import org.come.readBean.AllACard;
import org.come.servlet.UpXlsAndTxtFile;
import org.come.tool.ReadExelTool;
import org.come.tool.SettModelMemberTool;
import org.come.until.GsonUtil;

public class ReadACardUtil {

	/** 扫描怪兽文件，获得全部怪兽信息*/
	public static ConcurrentHashMap<Integer,aCard> selectACards(String path, StringBuffer buffer){
		ConcurrentHashMap<Integer,aCard> allACard=new ConcurrentHashMap<>();
		String[][] result = ReadExelTool.getResult("config/"+path+".xls");
		for (int i = 2; i < result.length; i++) {
			if (result[i][0].equals("")) {continue;}
    		aCard aCard=new aCard();
			for (int j = 0; j < result[i].length; j++) {
				try {
                    SettModelMemberTool.setReflectRelative(aCard, result[i][j], j);
                } catch (Exception e) {
                    UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, result[i][j],MainServerHandler.getErrorMessage(e));
                    return null;
                }
			}
			if (aCard.getId()!=0) {
				allACard.put(aCard.getId(), aCard);
			}
		}
		return allACard;
	}
    public static String createACards(ConcurrentHashMap<Integer, aCard> map) {
        AllACard aCard = new AllACard();
        Map<Integer, aCard> aMap=new HashMap<>();
        for (aCard card:map.values()) {
        	aMap.put(card.getId(), card);
		}
        aCard.setaMap(aMap);
        String msgString = GsonUtil.getGsonUtil().getgson().toJson(aCard);
        return msgString;
    }

	
}

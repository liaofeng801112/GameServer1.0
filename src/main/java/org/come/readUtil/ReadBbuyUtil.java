package org.come.readUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.come.bean.Bbuy;
import org.come.handler.MainServerHandler;
import org.come.readBean.AllBbuy;
import org.come.servlet.UpXlsAndTxtFile;
import org.come.tool.ReadExelTool;
import org.come.tool.SettModelMemberTool;
import org.come.until.GsonUtil;

public class ReadBbuyUtil {
	
	/** 扫描文件，获得全部npc信息*/
	public static ConcurrentHashMap<Integer,Bbuy> selecBbuys(String path, StringBuffer buffer){
		ConcurrentHashMap<Integer,Bbuy> allbbuy=new ConcurrentHashMap<>();
		String[][] result = ReadExelTool.getResult("config/"+path+".xls");
		for (int i = 2; i < result.length; i++) {
			if (result[i][0].equals(""))continue;
			Bbuy bbuy = new Bbuy();
			for (int j = 0; j < result[i].length; j++) {
                try {
                    SettModelMemberTool.setReflectRelative(bbuy, result[i][j], j);
                } catch (Exception e) {
                    UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, result[i][j],MainServerHandler.getErrorMessage(e));
                    return null;
                }
            }
			allbbuy.put(bbuy.getGid(), bbuy);
		}
		return allbbuy;
	}
    /**
     * HGC
     * 
     * @time 2020年1月9日 上午11:24:11<br>
     * @return
     */
    public static String createBbuy(ConcurrentHashMap<Integer,Bbuy> map) {
        AllBbuy allBbuy = new AllBbuy();
        Map<Integer,Bbuy> allbbuy=new HashMap<>();
        for (Bbuy bbuy:map.values()) {
        	allbbuy.put(bbuy.getGid(), bbuy);
		}
        allBbuy.setAllbbuy(allbbuy);
        String msgString = GsonUtil.getGsonUtil().getgson().toJson(allBbuy);
        return msgString;
    }
}

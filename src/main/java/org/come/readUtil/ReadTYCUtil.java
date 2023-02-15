package org.come.readUtil;

import java.util.HashMap;
import java.util.Map;

import org.come.handler.MainServerHandler;
import org.come.readBean.TYCbean;
import org.come.servlet.UpXlsAndTxtFile;
import org.come.tool.ReadExelTool;
import org.come.tool.SettModelMemberTool;
import org.come.until.GsonUtil;

/**
 * HGC天演策初始化
 * 2020-01-11
 * @author HGC
 *
 */
public class ReadTYCUtil {
    public static Map<String, String> selectDecoration(String path, StringBuffer buffer) {
        // 初始化刷新列表
        Map<String, String> allTXs = new HashMap<>();
    	String[][] result = ReadExelTool.getResult("config/"+path+".xls");
        for (int i = 1; i < result.length; i++) {
        	if (result[i][0].equals("")) {continue;}
    		TYCbean tyCbean = new TYCbean();
            for (int j = 0; j < result[i].length; j++) {
                // 遍历实体类成员变量赋值
                try {
                    SettModelMemberTool.setReflectRelative(tyCbean, result[i][j], j);
                } catch (Exception e) {
                    UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, result[i][j], MainServerHandler.getErrorMessage(e));
                    return null;
                }
            }
            allTXs.put(tyCbean.getType(), tyCbean.getValue()); 
        }
        return allTXs;
    }
    public static String createTX(Map<String, String> map) {
        String msgString = GsonUtil.getGsonUtil().getgson().toJson(map);
        return msgString;
    }

}

package org.come.readUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.come.handler.MainServerHandler;
import org.come.model.ColorScheme;
import org.come.readBean.AllColorScheme;
import org.come.servlet.UpXlsAndTxtFile;
import org.come.tool.ReadExelTool;
import org.come.tool.SettModelMemberTool;
import org.come.until.GsonUtil;

public class ReadColorUtil {
	/**扫描文件，获得全部npc信息*/
	public static ConcurrentHashMap<String,ColorScheme> selectcolors(String path, StringBuffer buffer){
		ConcurrentHashMap<String,ColorScheme> allCMap = new ConcurrentHashMap<>();
		String[][] result = ReadExelTool.getResult("config/"+path+".xls");
		for (int i = 2; i < result.length; i++) {
			if (result[i][0].equals(""))continue;
			ColorScheme scheme=new ColorScheme();
			for (int j = 0; j < result[i].length; j++) {
				try {
                    SettModelMemberTool.setReflectRelative(scheme, result[i][j], j);
                } catch (Exception e) {
                    UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, result[i][j],MainServerHandler.getErrorMessage(e));
                    return null;
                }
			}
			allCMap.put(scheme.getId()+"", scheme);
			allCMap.put(scheme.getName(), scheme);
		}
		return allCMap;
	}
    public static String createcolor(ConcurrentHashMap<String, ColorScheme> map) {
        AllColorScheme allScheme = new AllColorScheme();
        Map<Integer, ColorScheme> allMap = new HashMap<Integer, ColorScheme>();
        Set<Entry<String, ColorScheme>> entrySet = map.entrySet();
        for (Entry<String, ColorScheme> entry : entrySet) {
        	allMap.put(entry.getValue().getId(), entry.getValue());
        }
        allScheme.setAllMap(allMap);
        String msgString = GsonUtil.getGsonUtil().getgson().toJson(allScheme);
        return msgString;
    }
}

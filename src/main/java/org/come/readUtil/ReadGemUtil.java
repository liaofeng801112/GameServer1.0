package org.come.readUtil;

import java.util.concurrent.ConcurrentHashMap;

import org.come.handler.MainServerHandler;
import org.come.model.Gem;
import org.come.servlet.UpXlsAndTxtFile;
import org.come.tool.ReadExelTool;
import org.come.tool.SettModelMemberTool;

public class ReadGemUtil {
	
    public static ConcurrentHashMap<String, Gem> selectGem(String path, StringBuffer buffer) {
        ConcurrentHashMap<String, Gem> gems = new ConcurrentHashMap<>();
        String[][] result = ReadExelTool.getResult("config/"+path+".xls");
        for (int i = 1; i < result.length; i++) {
        	if (result[i][0].equals("")) {continue;}
    		Gem bean = new Gem();
            for (int j = 0; j < result[i].length; j++) {
                try {
                    SettModelMemberTool.setReflectRelative(bean, result[i][j], j);
                } catch (Exception e) {
                    UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, result[i][j],MainServerHandler.getErrorMessage(e));
                    return null;
                }
            }
            try {
            	 bean.initGemBase();
                 gems.put(bean.getBname(), bean);	
			} catch (Exception e) {
				// TODO: handle exception
				UpXlsAndTxtFile.addStringBufferMessage(buffer, i, 0,"解析错误",MainServerHandler.getErrorMessage(e));
                return null;
			}
        }
        return gems;
    }
}

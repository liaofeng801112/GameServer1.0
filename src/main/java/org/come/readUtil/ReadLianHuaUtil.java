package org.come.readUtil;

import org.come.handler.MainServerHandler;
import org.come.model.LianHua;
import org.come.readBean.AllLianHua;
import org.come.servlet.UpXlsAndTxtFile;
import org.come.tool.ReadExelTool;
import org.come.tool.SettModelMemberTool;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ReadLianHuaUtil {

    /** 炼化 */
    public static AllLianHua selectLianHuas(String path, StringBuffer buffer) {
        String[][] result = ReadExelTool.getResult("config/" + path + ".xls");
        Map<Integer, LianHua> list = new ConcurrentHashMap();
        for (int i = 1; i < result.length; i++) {
            if (result[i][0].equals("")) {
                continue;
            }
            LianHua obj = new LianHua();
            for (int j = 0; j < result[i].length; j++) {
                try {
                    SettModelMemberTool.setReflectRelative(obj, result[i][j], j);
                    list.put(obj.getId(), obj);// (obj);
                } catch (Exception e) {
                    UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, result[i][j],
                            MainServerHandler.getErrorMessage(e));
                    return null;
                }
            }
        }
        AllLianHua all = new AllLianHua(list);
        return all;
    }
}

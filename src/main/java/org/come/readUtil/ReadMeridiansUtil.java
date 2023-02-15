package org.come.readUtil;

import org.come.bean.Meridians;
import org.come.handler.MainServerHandler;
import org.come.readBean.AllMeridians;
import org.come.servlet.UpXlsAndTxtFile;
import org.come.tool.ReadExelTool;
import org.come.tool.SettModelMemberTool;

import java.util.ArrayList;
import java.util.List;

public class ReadMeridiansUtil {

    /** 经脉 */
    public static AllMeridians selectMeridians(String path, StringBuffer buffer) {
        String[][] result = ReadExelTool.getResult("config/" + path + ".xls");
        AllMeridians all = new AllMeridians();
        List<Meridians> list = new ArrayList();
        for (int i = 1; i < result.length; i++) {
            if (result[i][0].equals("")) {
                continue;
            }
            Meridians obj = new Meridians();
            for (int j = 0; j < result[i].length; j++) {
                try {
                    SettModelMemberTool.setReflectRelative(obj, result[i][j], j);

                } catch (Exception e) {
                    UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, result[i][j],
                            MainServerHandler.getErrorMessage(e));
                    return null;
                }
            }
            list.add(obj);
        }
        all.setMeridians(list);

        return all;
    }
}

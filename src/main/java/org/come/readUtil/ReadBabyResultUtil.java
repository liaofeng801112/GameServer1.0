package org.come.readUtil;

import java.util.ArrayList;
import java.util.List;

import org.come.handler.MainServerHandler;
import org.come.readBean.AllBabyResult;
import org.come.readBean.BabyResult;
import org.come.servlet.UpXlsAndTxtFile;
import org.come.tool.ReadExelTool;
import org.come.tool.SettModelMemberTool;
import org.come.until.GsonUtil;

/**
 * @author HGC<br>
 * @time 2020年1月9日 上午11:47:21<br>
 * @class 类名:ReadBabyResultUtil<br>
 */
public class ReadBabyResultUtil {

    private static List<BabyResult> allBabyResults;

    /*
     * 扫描文件，获得全部npc信息
     */
    public static List<BabyResult> selectBabyResult(String path, StringBuffer buffer) {
        allBabyResults = new ArrayList<>();
    	String[][] result = ReadExelTool.getResult("config/"+path+".xls");
        // 遍历每行为对象赋值
        for (int i = 1; i < result.length; i++) {
        	if (result[i][0].equals("")) {continue;}
    		BabyResult babyResult = new BabyResult();
            for (int j = 0; j < result[i].length; j++) {
            	try {
                    SettModelMemberTool.setReflectRelative(babyResult, result[i][j], j);
                } catch (Exception e) {
                    UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, result[i][j],  MainServerHandler.getErrorMessage(e));
                    return null;
                }
            }
            allBabyResults.add(babyResult);
        }
        return allBabyResults;
    }
    public static String creatbabyresult(List<BabyResult> list) {
        AllBabyResult allBabyResult = new AllBabyResult();
        allBabyResult.setAllBabyResults(list);
        String msgString = GsonUtil.getGsonUtil().getgson().toJson(allBabyResult);
        return msgString;
    }

}

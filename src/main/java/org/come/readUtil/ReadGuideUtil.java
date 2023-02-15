package org.come.readUtil;

import java.util.HashMap;
import java.util.Map;

import org.come.handler.MainServerHandler;
import org.come.readBean.AllGuide;
import org.come.readBean.RookieGuideBean;
import org.come.servlet.UpXlsAndTxtFile;
import org.come.tool.ReadExelTool;
import org.come.tool.SettModelMemberTool;
import org.come.until.GsonUtil;

/**
 * @author HGC<br>
 * @time 2020年1月9日 上午11:47:14<br>
 * @class 类名:ReadGuideUtil<br>
 */
public class ReadGuideUtil {
    public static Map<Integer, RookieGuideBean> selectSkills(String path, StringBuffer buffer) {
        Map<Integer, RookieGuideBean> map = new HashMap<>();
        String[][] result = ReadExelTool.getResult("config/"+path+".xls");
        for (int i = 1; i < result.length; i++) {
        	if (result[i][0].equals("")) {continue;}
    		RookieGuideBean bean = new RookieGuideBean();
            for (int j = 0; j < result[i].length; j++) {
            	try {
                    SettModelMemberTool.setReflectRelative(bean, result[i][j], j);
                } catch (Exception e) {
                    UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, result[i][j],MainServerHandler.getErrorMessage(e));
                    return null;
                }
            }
            map.put(bean.getGid(), bean);
        }
        return map;
    }

    public static String createSkill(Map<Integer, RookieGuideBean> map) {
        AllGuide allSuit = new AllGuide();
        allSuit.setRookieguide(map);
        String msgString = GsonUtil.getGsonUtil().getgson().toJson(allSuit);
        return msgString;
    }

}

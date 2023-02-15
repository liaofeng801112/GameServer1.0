package org.come.readUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.come.handler.MainServerHandler;
import org.come.model.Title;
import org.come.readBean.AllTitleBean;
import org.come.server.GameServer;
import org.come.servlet.UpXlsAndTxtFile;
import org.come.tool.ReadExelTool;
import org.come.tool.SettModelMemberTool;
import org.come.until.GsonUtil;
/**
 * 读取称谓文件
 * @author 叶豪芳
 * @date : 2017年11月30日 下午4:22:51
 */
public class ReadTitleUtil {
	/*
	 * 扫描文件，获得全部称谓信息
	 */
	public static List<Title> selectTitles(String path, StringBuffer buffer){
		List<Title> titles = new ArrayList<Title>();
		String[][] result = ReadExelTool.getResult("config/"+path+".xls");
		for (int i = 2; i < result.length; i++) {
			if (result[i][0].equals("")) {continue;}
    		Title title = new Title();
			for (int j = 0; j < result[i].length; j++) {
				try {
                    SettModelMemberTool.setReflectRelative(title, result[i][j], j);
                } catch (Exception e) {
                    UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, result[i][j],MainServerHandler.getErrorMessage(e));
                    return null;
                }
			}
			if (title.getSkin().equals("")) {
				title.setSkin(null);
			}
			if (title.getText().equals("")) {
				title.setText(null);
			}
			if (title.getValue().equals("")) {
				title.setValue(null);
			}
			titles.add(title);
		}
		return titles;
	}
	/**根据获得称谓*/
	public static ConcurrentHashMap<String,Title> getTitle2(List<Title> titles){
		ConcurrentHashMap<String,Title> getTitle = new ConcurrentHashMap<String,Title>();
		for (int i = titles.size()-1; i>=0; i--) {
			Title title=titles.get(i);
			getTitle.put(title.getTitlename(), title);
			getTitle.put(title.getId()+"", title);
			if ( title.getExist().indexOf("充值") == -1 ) {
				titles.remove(i);
			}
		}
		GameServer.setMoneyTitles(titles);
		return getTitle;
	}
	/**
     * @time 2020年1月9日 上午11:59:11<br>
     * @return
     */
    public static String createTitle(List<Title> titles) {
        AllTitleBean allTitleBean = new AllTitleBean();
        Map<String, Title> map = new HashMap<>();
        for (int i = 0; i < titles.size(); i++) {
            Title title = titles.get(i);
            map.put(title.getTitlename(), title);
        }
        allTitleBean.setTitleMap(map);
        String msgString = GsonUtil.getGsonUtil().getgson().toJson(allTitleBean);
        return msgString;
    }
}

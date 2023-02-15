package org.come.readUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.come.action.lottery.EventRanking;
import org.come.handler.MainServerHandler;
import org.come.model.EventModel;
import org.come.readBean.AllEventModelBean;
import org.come.servlet.UpXlsAndTxtFile;
import org.come.tool.ReadExelTool;
import org.come.tool.SettModelMemberTool;
import org.come.until.GsonUtil;
import org.come.until.ReadTxtUtil;

import come.tool.Role.RoleCard;

/** 读取活动表 */
public class ReadEventUtil {
	/*
	 * 扫描文件，获得全部商城物品信息
	 */
	public static ConcurrentHashMap<Integer, EventModel> selectEvents(String path, StringBuffer buffer) {
		String text = ReadTxtUtil.readFile1(ReadExelTool.class.getResource("/").getPath() + "event.txt");
		Map<Integer, RoleCard[]> map = null;
		if (text == null || text.equals("")) {
			map = new HashMap<>();
		} else {
			EventRanking eventRanking = GsonUtil.getGsonUtil().getgson().fromJson(text, EventRanking.class);
			map = eventRanking.getMap();
		}
		ConcurrentHashMap<Integer, EventModel> eMap = new ConcurrentHashMap<>();
		String[][] result = ReadExelTool.getResult("config/"+path+".xls");
		for (int i = 2; i < result.length; i++) {
			if (result[i][0].equals("")) {continue;}
    		EventModel eventModel = new EventModel();
			for (int j = 0; j < result[i].length; j++) {
				try {
                    SettModelMemberTool.setReflectRelative(eventModel, result[i][j], j);
                } catch (Exception e) {
                    UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, result[i][j],MainServerHandler.getErrorMessage(e));
                    return null;
                }
			}
			if (eventModel.getgId() != 0) {
				eventModel.setRoles(map.get(eventModel.getgId()));
				eventModel.resetRanking(null, null);
				eMap.put(eventModel.getgId(), eventModel);
			}
		}
		return eMap;
	}
	public static String createEvent(ConcurrentHashMap<Integer, EventModel> map) {
		AllEventModelBean allEventModelBean = new AllEventModelBean();
		Map<Integer, EventModel> allEventModelMap=new HashMap<>();
		for (EventModel model:map.values()) {
			allEventModelMap.put(model.getgId(), model);
		}
		allEventModelBean.setAllEventModelMap(allEventModelMap);
		String msgString = GsonUtil.getGsonUtil().getgson().toJson(allEventModelBean);
		return msgString;
	}
}

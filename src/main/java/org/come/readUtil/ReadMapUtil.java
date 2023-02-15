package org.come.readUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.come.bean.AllNpcBean;
import org.come.bean.NpcInfoBean;
import org.come.handler.MainServerHandler;
import org.come.model.Door;
import org.come.model.Gamemap;
import org.come.model.Npctable;
import org.come.model.Talk;
import org.come.readBean.AllDoorBean;
import org.come.readBean.AllMapBean;
import org.come.server.GameServer;
import org.come.servlet.UpXlsAndTxtFile;
import org.come.tool.ReadExelTool;
import org.come.tool.SettModelMemberTool;
import org.come.tool.SplitStringTool;
import org.come.until.GsonUtil;

/**
 * 读取地图表
 * 
 * @author 叶豪芳
 * @date : 2017年11月28日 下午10:14:08
 */
public class ReadMapUtil {
	
	public static ConcurrentHashMap<String, Gamemap> selectAllMap(String path, StringBuffer buffer) {
		ConcurrentHashMap<String, Gamemap> map=new ConcurrentHashMap<>();
		String[][] result = ReadExelTool.getResult("config/"+path+".xls");
		for (int i = 2; i < result.length; i++) {
			if (result[i][0].equals("")) {continue;}
    		Gamemap gamemap = new Gamemap();
			for (int j = 0; j < result[i].length; j++) {
				try {
					SettModelMemberTool.setReflect(gamemap,result[i][j], j);
				} catch (Exception e) {
                    UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, result[i][j],MainServerHandler.getErrorMessage(e));
                    return null;
                }
			}
			if (gamemap.getMapid().equals("")) {continue;}
			try {
				Long.parseLong(gamemap.getMapid());
				if (gamemap.getMapnpc() != null && !gamemap.getMapnpc().equals("")) {
					List<String> npcs = SplitStringTool.splitString(gamemap.getMapnpc());
					gamemap.setNpcs(npcs);
				}
				map.put(gamemap.getMapid(), gamemap);
				map.put(gamemap.getMapname(), gamemap);
			} catch (Exception e) {
				// TODO: handle exception
				UpXlsAndTxtFile.addStringBufferMessage(buffer, i, 0,"解析错误",MainServerHandler.getErrorMessage(e));
                return null;
			}
		}
		return map;		
	}
	/**扫描文件，获得全部npc信息*/
	public static ConcurrentHashMap<String,Npctable> selectallNpc(String path, StringBuffer buffer){
		ConcurrentHashMap<String,Npctable> npctables=new ConcurrentHashMap<>();
		String[][] result = ReadExelTool.getResult("config/"+path+".xls");
		for (int i = 2; i < result.length; i++) {
			if (result[i][0].equals("")) {continue;}
    		Npctable npctable = new Npctable();
			for (int j = 0; j < result[i].length; j++) {
				try {
					SettModelMemberTool.setReflect(npctable,result[i][j], j);
				} catch (Exception e) {
                    UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, result[i][j],MainServerHandler.getErrorMessage(e));
                    return null;
                }
			}
			if (!npctable.getNpcid().equals("")) {
				npctables.put(npctable.getNpcid(), npctable);	
			}	
			if (npctable.getNpcid()!=null&&!npctable.getNpcid().equals("")) {
				npctable.setMap(GameServer.getMapNpc(npctable.getNpcid()));
				npctables.put(npctable.getNpcid(), npctable);	
			}
		}
		return npctables;
	}
	/**获得全部传送信息*/
	public static ConcurrentHashMap<Integer,Door> selectDoors(String path, StringBuffer buffer){
		ConcurrentHashMap<Integer,Door> map=new ConcurrentHashMap<>();
		String[][] result = ReadExelTool.getResult("config/"+path+".xls");
		for (int i = 2; i < result.length; i++) {
			if (result[i][0].equals("")) {continue;}
    		Door door = new Door();
			for (int j = 0; j < result[i].length; j++) {
				try {
					SettModelMemberTool.setReflect(door,result[i][j], j);
				} catch (Exception e) {
                    UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, result[i][j],MainServerHandler.getErrorMessage(e));
                    return null;
                }
			}
			if (door.getDoorid()==null||door.getDoorid().equals("")) {continue;}
			try {
				map.put(Integer.parseInt(door.getDoorid()), door);
			} catch (Exception e) {
				// TODO: handle exception
				UpXlsAndTxtFile.addStringBufferMessage(buffer, i, 0,"解析错误",MainServerHandler.getErrorMessage(e));
                return null;
			}
		}
		return map;
	}
	/**扫描文件，获得全部廢話表信息*/
	public static Map<String,Talk> selectTalks(){
		Map<String,Talk> talks =new HashMap<>();
		String[][] result = ReadExelTool.getResult("config/talk.xls");
		for (int i = 2; i < result.length; i++) {
			if (result[i][0].equals("")) {continue;}
    		Talk talk = new Talk();
			for (int j = 0; j < result[i].length; j++) {
				try {
					SettModelMemberTool.setReflect(talk, result[i][j], j);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			talks.put(talk.getTalkid(), talk);
		}
		return talks;
	}
	/**获取map表txt数据*/
	public static String createTxtMap(ConcurrentHashMap<String, Gamemap> map){
		AllMapBean allMapBean=new AllMapBean();
		Map<String, Gamemap> map2=new HashMap<>();
		for (Gamemap gamemap:map.values()) {
			map2.put(gamemap.getMapid(), gamemap);
		}
		allMapBean.setAllMap(map2);
		return GsonUtil.getGsonUtil().getgson().toJson(allMapBean);	
	}
	/**获取npc表txt数据*/
	public static String createTxtNpc(ConcurrentHashMap<String,Npctable> map){
		Map<String,Talk> talks=selectTalks();
		Map<String, NpcInfoBean> Npcs=new HashMap<>();
		Iterator<String> iter1 = map.keySet().iterator();
    	while (iter1.hasNext()) {
    		String key = iter1.next();
    		Npctable npctable=map.get(key);
    		NpcInfoBean npcInfoBean=new NpcInfoBean();
    		npcInfoBean.setNpctable(npctable);
    		List<Talk> talkss = new ArrayList<>();
			if(npctable.getTalkid() != null&&!npctable.getTalkid().equals("") ){
				List<String> talkList = SplitStringTool.splitString(npctable.getTalkid());
				for (String string : talkList) {
					Talk talk=talks.get(string);
					if (talk!=null) {
						talkss.add(talk);	
					}
				}
			}
    		npcInfoBean.setTalks(talkss);
    		Npcs.put(key, npcInfoBean);
    	}
    	AllNpcBean allNpcBean=new AllNpcBean();
    	allNpcBean.setAllNpcInfo(Npcs);
		String msgString = GsonUtil.getGsonUtil().getgson().toJson(allNpcBean);
		return msgString;
	}
	public static String createTxtDoor(ConcurrentHashMap<Integer,Door> map) {
		AllDoorBean allDoorBean=new AllDoorBean();
		Map<String,Door> alldoorMap=new HashMap<>();
		for (Door door:map.values()) {
			alldoorMap.put(door.getDoorid(), door);
		}
		allDoorBean.setAlldoor(alldoorMap);
		String msgString = GsonUtil.getGsonUtil().getgson().toJson(allDoorBean);
		return msgString;
	}
}

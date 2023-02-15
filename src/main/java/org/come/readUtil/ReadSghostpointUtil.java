package org.come.readUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.come.bean.PathPoint;
import org.come.handler.MainServerHandler;
import org.come.model.Sghostpoint;
import org.come.servlet.UpXlsAndTxtFile;
import org.come.tool.ReadExelTool;
import org.come.tool.SettModelMemberTool;

/**
 * 怪物刷新坐标
 * @author 叶豪芳
 * @date 2017年12月27日 下午3:49:38
 * 
 */ 
public class ReadSghostpointUtil {
	// 领取刷怪任务
	public static ConcurrentHashMap<String, List<Sghostpoint>> getMonsterTask(String path, StringBuffer buffer){
		ConcurrentHashMap<String, List<Sghostpoint>> monsterMap = new ConcurrentHashMap<String, List<Sghostpoint>>();
		String[][] result = ReadExelTool.getResult("config/"+path+".xls");
		for (int i = 2; i < result.length; i++) {
			if (result[i][0].equals("")) {continue;}
    		Sghostpoint sghostpoint = new Sghostpoint();
			for (int j = 0; j < result[i].length; j++) {
				try {
					SettModelMemberTool.setReflect(sghostpoint, result[i][j], j);
				} catch (Exception e) {
					UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, result[i][j], MainServerHandler.getErrorMessage(e));
					return null;
				}
			}
			try {
				if (sghostpoint.getPointidString()==0) {continue;}
				List<Sghostpoint> sghostpoints2=monsterMap.get(sghostpoint.getPointtype());
				if (sghostpoints2==null) {
					sghostpoints2 = new ArrayList<Sghostpoint>();
					monsterMap.put(sghostpoint.getPointtype(), sghostpoints2);
				}
				String[] v=sghostpoint.getPointpoint().split("\\|");
				PathPoint[] points=new PathPoint[v.length];
				for (int k = 0; k < v.length; k++) {
					String[] v2=v[k].split(",");
					points[k]=new PathPoint(Integer.parseInt(v2[0]),Integer.parseInt(v2[1]));
				}
				sghostpoint.setPoints(points);
				sghostpoints2.add(sghostpoint);
			} catch (Exception e) {
				// TODO: handle exception
				UpXlsAndTxtFile.addStringBufferMessage(buffer, i, 0,"解析错误",MainServerHandler.getErrorMessage(e));
                return null;
			}
		}
		return monsterMap;
	}
}

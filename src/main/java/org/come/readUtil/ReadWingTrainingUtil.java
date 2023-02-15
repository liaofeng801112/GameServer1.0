package org.come.readUtil;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.come.entity.WingTraining;
import org.come.handler.MainServerHandler;
import org.come.servlet.UpXlsAndTxtFile;
import org.come.tool.ReadExelTool;
import org.come.tool.SettModelMemberTool;
import org.come.tool.SplitStringTool;

public class ReadWingTrainingUtil {

	/**获得全部仙器信息*/
	public static ConcurrentHashMap<Long,WingTraining> selectWingTraining(String path, StringBuffer buffer){
		ConcurrentHashMap<Long,WingTraining> map=new ConcurrentHashMap<>();
		// 读取召唤兽文件获得所有召唤兽信息
		String[][] result = ReadExelTool.getResult("config/"+path+".xls");
		// 遍历每行为对象赋值
		for (int i = 1; i < result.length; i++) {
			if (result[i][0].equals("")) {continue;}
    		WingTraining wingTraining=new WingTraining();
			for (int j = 0; j < result[i].length; j++) {
				try {
					SettModelMemberTool.setReflect(wingTraining,result[i][j], j);
				} catch (Exception e) {
                    UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, result[i][j],MainServerHandler.getErrorMessage(e));
                    return null;
                }
			}
			try {
				if (wingTraining.getId()==0) {continue;}
				String[] vs=wingTraining.getBase().split("\\|");
				double[] bases=new double[vs.length];
				for (int j = 0; j < bases.length; j++) {
					bases[j]=Double.parseDouble(vs[j]);
				}
				wingTraining.setBases(bases);
				List<String> list=SplitStringTool.splitString(wingTraining.getType());
				for (int j = list.size()-1; j >=0; j--) {
					map.put(new Long(list.get(j)), wingTraining);
				}
			} catch (Exception e) {
				// TODO: handle exception
				UpXlsAndTxtFile.addStringBufferMessage(buffer, i, 0,"解析错误",MainServerHandler.getErrorMessage(e));
                return null;
			}
		}
		return map;
	}
}

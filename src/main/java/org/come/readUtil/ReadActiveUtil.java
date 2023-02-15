package org.come.readUtil;

import java.util.ArrayList;
import java.util.List;

import org.come.handler.MainServerHandler;
import org.come.model.ActiveAward;
import org.come.model.ActiveBase;
import org.come.readBean.AllActive;
import org.come.servlet.UpXlsAndTxtFile;
import org.come.tool.ReadExelTool;
import org.come.tool.SettModelMemberTool;

import come.tool.Good.DropModel;

/**活跃表*/
public class ReadActiveUtil {
	
	/** 扫描怪兽文件，获得全部怪兽信息*/
	public static AllActive selectActives(String path, StringBuffer buffer){
		String[][] result = ReadExelTool.getResult("config/"+path+".xls");
		if (result.length<=1||!result[1][0].equals("活跃奖励设置")) {
			UpXlsAndTxtFile.addStringBufferMessage(buffer, 1, 0,"未设置活跃奖励", "");
			return null;
		}
		//活跃=xxx
		List<ActiveAward> awardList=new ArrayList<>();
		for (int i = 1; i < result[1].length; i++) {
			if (result[1][i].equals("")) {continue;}
			try {
				String[] vs=result[1][i].split("\\|");
				ActiveAward activeAward=new ActiveAward(Integer.parseInt(vs[0].split("=")[1]));
				StringBuffer activeBuffer=new StringBuffer();
				activeAward.setDropModel(new DropModel(activeBuffer,vs));
				activeAward.setValue(activeBuffer.toString());
				awardList.add(activeAward);
			} catch (Exception e) {
				// TODO: handle exception
				UpXlsAndTxtFile.addStringBufferMessage(buffer, 1, i,result[1][i], MainServerHandler.getErrorMessage(e));
				return null;
			}			
		}
		List<ActiveBase> baseList=new ArrayList<>();
		for (int i = 2; i < result.length; i++) {
			if (result[i][0].equals("")) {continue;}
			ActiveBase activeBase=new ActiveBase();
			for (int j = 0; j < result[i].length; j++) {
				try {
					SettModelMemberTool.setReflectRelative(activeBase, result[i][j],j);
				} catch (Exception e) {
					UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j,result[i][j], MainServerHandler.getErrorMessage(e));
					return null;
				}
			}
			if (activeBase.getId()!=0) {baseList.add(activeBase);}
		}
		AllActive allActive=new AllActive(awardList,baseList);
		return allActive;
	}
}

package org.come.readUtil;

import java.util.ArrayList;
import java.util.List;

import org.come.entity.Present;
import org.come.handler.MainServerHandler;
import org.come.servlet.UpXlsAndTxtFile;
import org.come.tool.ReadExelTool;
import org.come.tool.SettModelMemberTool;

public class ReadPresentUtil {

	public static List<Present> selectPresents(String path, StringBuffer buffer) {
		List<Present> presents = new ArrayList<>();
		String[][] result = ReadExelTool.getResult("config/"+path+".xls");
		for (int i = 1; i < result.length; i++) {
			if (result[i][0].equals("")) {continue;}
    		Present present = new Present();
			for (int j = 0; j < result[i].length; j++) {
				try {
                    SettModelMemberTool.setReflectRelative(present, result[i][j], j);
                } catch (Exception e) {
                    UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, result[i][j],MainServerHandler.getErrorMessage(e));
                    return null;
                }
			}
			presents.add(present);
		}
		return presents;
	}

}

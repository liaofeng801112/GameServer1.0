package org.come.readUtil;

import java.math.BigDecimal;
import java.util.concurrent.ConcurrentHashMap;

import org.come.entity.Goodstable;
import org.come.entity.Lingbao;
import org.come.handler.MainServerHandler;
import org.come.servlet.UpXlsAndTxtFile;
import org.come.tool.ReadExelTool;
import org.come.tool.SettModelMemberTool;
/**
 * 读取灵宝表
 * @author 叶豪芳
 * @date : 2017年11月29日 下午5:10:07
 */
public class ReadLingbaoUtil {
	public static ConcurrentHashMap<String, Lingbao> getAllLingbao(String path, StringBuffer buffer){
		ConcurrentHashMap<String, Lingbao> getAllLingbao = new ConcurrentHashMap<>();
		String[][] result = ReadExelTool.getResult("config/"+path+".xls");
		for (int i = 1; i < result.length; i++) {
			if (result[i][0].equals("")) {continue;}
    		Lingbao lingbao = new Lingbao();
			for (int j = 0; j < result[i].length; j++) {
				try {
					SettModelMemberTool.setReflect(lingbao, result[i][j], j);
				} catch (Exception e) {
					UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, result[i][j], MainServerHandler.getErrorMessage(e));
					return null;
				}
			}
			if (lingbao.getBaoname()!=null&&!lingbao.getBaoname().equals("")) {
				getAllLingbao.put(lingbao.getBaoname(), lingbao);
			}
		}
		return getAllLingbao;
	}
	public static ConcurrentHashMap<BigDecimal, Goodstable> getAllLingbaoFushi(String path, StringBuffer buffer){
		ConcurrentHashMap<BigDecimal, Goodstable> getAllGoodstable = new ConcurrentHashMap<>();
		String[][] result = ReadExelTool.getResult("config/"+path+".xls");
		for (int i = 0; i < result.length; i++) {
			if (result[i][0].equals("")) {continue;}
    		Goodstable Goodstable = new Goodstable();
			for (int j = 0; j < result[i].length; j++) {
				try {
					SettModelMemberTool.setReflect(Goodstable, result[i][j], j);
				} catch (Exception e) {
					UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, result[i][j], MainServerHandler.getErrorMessage(e));
					return null;
				}
			}
			getAllGoodstable.put(Goodstable.getGoodsid(), Goodstable);
		}
		return getAllGoodstable;
	}
}

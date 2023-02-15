package org.come.readUtil;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

import org.come.bean.RoleExpBean;
import org.come.handler.MainServerHandler;
import org.come.model.Exp;
import org.come.servlet.UpXlsAndTxtFile;
import org.come.tool.ReadExelTool;
import org.come.tool.SettModelMemberTool;
import org.come.until.GsonUtil;

/**
 * 读取等级经验表
 * 
 * @author 叶豪芳
 * @date : 2017年11月29日 下午5:10:07
 */
public class ReadExpUtil {
	/** 根据等级经验ID查询等级经验*/
	public static ConcurrentHashMap<Integer, Long> getExp(String path, StringBuffer buffer) {
		ConcurrentHashMap<Integer, Long> roleExpMap = new ConcurrentHashMap<Integer, Long>();
		String[][] result = ReadExelTool.getResult("config/"+path+".xls");
		for (int i = 2; i < result.length; i++) {
			if (result[i][0].equals("")) {continue;}
    		Exp exp = new Exp();
			for (int j = 0; j < result[i].length; j++) {
				try {
                    SettModelMemberTool.setReflectRelative(exp, result[i][j], j);
                } catch (Exception e) {
                    UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, result[i][j],MainServerHandler.getErrorMessage(e));
                    return null;
                }
			}
			try {
				if (!exp.getExpid().equals("")) {
					roleExpMap.put(Integer.parseInt(exp.getExpid()),Long.parseLong(exp.getExpvalues()));
				}	
			} catch (Exception e) {
				// TODO: handle exception
				UpXlsAndTxtFile.addStringBufferMessage(buffer, i, 0,"解析错误",MainServerHandler.getErrorMessage(e));
                return null;
			}
			
		}
		return roleExpMap;
	}
	/**生成经验表*/
	public static String createExp(ConcurrentHashMap<Integer, Long> map) {
		RoleExpBean roleExpBean = new RoleExpBean();
		Map<Integer, BigDecimal> rolePetExpMap=new HashMap<>();
		Iterator<Entry<Integer, Long>> entries = map.entrySet().iterator();
		while (entries.hasNext()) {
			Entry<Integer, Long> entrys = entries.next();
			rolePetExpMap.put(entrys.getKey(), new BigDecimal(entrys.getValue()));
		}
		roleExpBean.setRolePetExpMap(rolePetExpMap);
		String msgString = GsonUtil.getGsonUtil().getgson().toJson(roleExpBean);
		return msgString;
	}
}

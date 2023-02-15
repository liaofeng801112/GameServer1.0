package org.come.readUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.come.bean.RoleTxBean;
import org.come.handler.MainServerHandler;
import org.come.readBean.AllTx;
import org.come.servlet.UpXlsAndTxtFile;
import org.come.tool.ReadExelTool;
import org.come.tool.SettModelMemberTool;
import org.come.until.GsonUtil;

public class ReadTxUtil {
	/**获得全部套装信息*/
	public static ConcurrentHashMap<Integer,RoleTxBean> selectDecoration(String path, StringBuffer buffer){
		ConcurrentHashMap<Integer,RoleTxBean> allTXs = new ConcurrentHashMap<>();
		String[][] result = ReadExelTool.getResult("config/"+path+".xls");
		for (int i = 2; i < result.length; i++) {
			if (result[i][0].equals("")) {continue;}
    		RoleTxBean bean=new RoleTxBean();
			for (int j = 0; j < result[i].length; j++) {
				try {
                    SettModelMemberTool.setReflectRelative(bean, result[i][j], j);
                } catch (Exception e) {
                    UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, result[i][j],MainServerHandler.getErrorMessage(e));
                    return null;
                }
			}
			allTXs.put(bean.getGid(), bean);
		}
		return allTXs;
	}
    /**
     * HGC
     * 
     * @time 2020年1月9日 上午11:36:26<br>
     * @return
     */
    public static String createTX(ConcurrentHashMap<Integer, RoleTxBean> map) {
        AllTx allTx = new AllTx();
        Map<Integer, RoleTxBean> txmap=new HashMap<>();
        for (RoleTxBean bean:map.values()) {
        	txmap.put(bean.getGid(), bean);
		}
        allTx.setTxmap(txmap);
        String msgString = GsonUtil.getGsonUtil().getgson().toJson(allTx);
        return msgString;
    }
}

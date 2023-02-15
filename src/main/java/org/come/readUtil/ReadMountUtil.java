package org.come.readUtil;

import java.util.concurrent.ConcurrentHashMap;

import org.come.entity.Mount;
import org.come.handler.MainServerHandler;
import org.come.model.InitMount;
import org.come.servlet.UpXlsAndTxtFile;
import org.come.tool.ReadExelTool;
import org.come.tool.SettModelMemberTool;
/**
 * 读取坐骑表内容
 * @author 叶豪芳
 * @date 2017年12月27日 下午3:46:12
 * 
 */ 
public class ReadMountUtil {
	// 匹配所有种族坐骑
	public static ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, Mount>> getAllMount(String path, StringBuffer buffer){
		ConcurrentHashMap<Integer, ConcurrentHashMap<Integer, Mount>> getAllMount = new ConcurrentHashMap<Integer, ConcurrentHashMap<Integer,Mount>>();
		String[][] result = ReadExelTool.getResult("config/"+path+".xls");
		for (int i = 1; i < result.length; i++) {
			if (result[i][0].equals("")) {continue;}
    		InitMount mount = new InitMount();
			for (int j = 0; j < result[i].length; j++) {
				try {
                    SettModelMemberTool.setReflectRelative(mount, result[i][j], j);
                } catch (Exception e) {
                    UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, result[i][j],MainServerHandler.getErrorMessage(e));
                    return null;
                }
			}
			try {
				if (mount.getRaceid().equals("")) {continue;}
				ConcurrentHashMap<Integer, Mount> hashMap=getAllMount.get(Integer.parseInt(mount.getRaceid()));
				if (hashMap==null) {
					hashMap=new ConcurrentHashMap<>();
					getAllMount.put(Integer.parseInt(mount.getRaceid()), hashMap);
				}
				Mount mountBean = new Mount();
				mountBean.setMountname(mount.getMountname());
				mountBean.setMountid(Integer.parseInt(mount.getMountid()));
				mountBean.setMountlvl(Integer.parseInt(mount.getMountlvl()));
				mountBean.setLive(Integer.parseInt(mount.getLive()));
				mountBean.setSpri(Integer.parseInt(mount.getSpri()));
				mountBean.setPower(Integer.parseInt(mount.getPower()));
				mountBean.setBone(Integer.parseInt(mount.getBone()));
				mountBean.setGradeexp(Integer.parseInt(mount.getExp()));
				hashMap.put(Integer.parseInt(mount.getMountid()),mountBean);
			} catch (Exception e) {
				// TODO: handle exception
				UpXlsAndTxtFile.addStringBufferMessage(buffer, i, 0,"解析错误",MainServerHandler.getErrorMessage(e));
                return null;
			}
		}
		return getAllMount;
	}
}

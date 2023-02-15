package org.come.readUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import org.come.action.buy.BuyShopAction;
import org.come.action.lottery.Draw;
import org.come.action.lottery.DrawBase;
import org.come.bean.QuackGameBean;
import org.come.handler.MainServerHandler;
import org.come.protocol.Agreement;
import org.come.servlet.UpXlsAndTxtFile;
import org.come.tool.ReadExelTool;
import org.come.tool.SettModelMemberTool;
import org.come.until.GsonUtil;

public class ReadDrawUtil {

	/** 扫描文件，获得全部商店物品信息 */
	public static ConcurrentHashMap<Integer, Draw> selectDraw(String path, StringBuffer buffer) {
		List<Draw> list = new ArrayList<>();
		ConcurrentHashMap<Integer, Draw> draws = new ConcurrentHashMap<>();
		String[][] result = ReadExelTool.getResult("config/"+path+".xls");
		for (int i = 2; i < result.length; i++) {
			if (result[i][0].equals("")) {continue;}
    		Draw draw = new Draw();
			for (int j = 0; j < result[i].length; j++) {
				try {
                    SettModelMemberTool.setReflectRelative(draw, result[i][j], j);
                } catch (Exception e) {
                    UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, result[i][j],MainServerHandler.getErrorMessage(e));
                    return null;
                }
			}
			
			if (draw.getDid() == 0) {
				continue;
			}
			try {
				String[] goods = result[i][5].split("&");
				DrawBase[] drawBases = new DrawBase[goods.length];
				for (int j = 0; j < goods.length; j++) {
					String[] canGetGoods = goods[j].split("\\$");
					drawBases[j] = new DrawBase();
					drawBases[j].setV(Double.parseDouble(canGetGoods[2]));
					drawBases[j].setSum(Integer.parseInt(canGetGoods[1]));
					String[] getGoods = canGetGoods[0].split("-");
					int[] ids = new int[getGoods.length];
					for (int k = 0; k < ids.length; k++) {
						ids[k] = Integer.parseInt(getGoods[k]);
					}
					drawBases[j].setIds(ids);
				}
				draw.setDrawBases(drawBases);
				list.add(draw);
			} catch (Exception e) {
				// TODO: handle exception
				UpXlsAndTxtFile.addStringBufferMessage(buffer, i, 0,"解析错误",MainServerHandler.getErrorMessage(e));
                return null;
			}
		}
		try {
			String[] CJS=new String[list.size()];
			for (int i = 0; i < list.size(); i++) {
				Draw draw = list.get(i);
				CJS[i]=draw.getName()+"积分";
				StringBuffer bufferTwo = new StringBuffer();
				for (int j = 0; j < list.size(); j++) {
					Draw draw2 = list.get(j);
					if (bufferTwo.length()!=0) {bufferTwo.append("|");}
					bufferTwo.append(i==j?"Y":"N");
					bufferTwo.append(draw2.getDid());
					bufferTwo.append("-");
					bufferTwo.append(draw2.getName());
				}
				bufferTwo.append("|");
				bufferTwo.append(draw.getGoods());
				QuackGameBean bean = new QuackGameBean();
				bean.setType(3);
				bean.setMoney(draw.getMoney());
				bean.setPetmsg(bufferTwo.toString());
				bean.setPetmsg2(draw.getMoneyType()+"|"+draw.getGoodid());
				draw.setText(Agreement.getAgreement().getfivemsgAgreement(GsonUtil.getGsonUtil().getgson().toJson(bean)));
				draws.put(draw.getDid(), draw);
			}
			BuyShopAction.CJS=CJS;
		} catch (Exception e) {
			// TODO: handle exception
			UpXlsAndTxtFile.addStringBufferMessage(buffer, 0, 0,"解析错误",MainServerHandler.getErrorMessage(e));
            return null;
		}
		return draws;
	}
}

package org.come.nettyClient;

import java.io.IOException;

import org.come.protocol.AgreementUtil;

/**
 * netty 客户端 处理 服务器 返回的信息
 * 
 * @author zengr
 * 
 */
public class ServerToClientMesControl implements FromServerAction {
	@Override
	public void controlMessFromServer(String mes) throws IOException {
		String ab = mes.substring(0, 4);
		String ReceiveMes = null;
		FromServerAction action = ClientMapAction.serverAction.get(ab);
		if (action != null) {
			return;
		}
		mes = Clinet_NewAESUtil.AESJDKDncode(mes);
		if (mes == null) {
			System.out.println("服务器返回空数据");
			return;
		}
		int wz = mes.indexOf("//");
		if (wz == -1) {
			return;
		}
		ReceiveMes = mes.substring(wz + 2);
		ab = mes.substring(0, wz);
		// System.out.println("协议头: " + ab + " 服务器发来信息: " + ReceiveMes);

		// 接收协议头并处理
		switch (ab) {
		/** 账号服务器新增接口 zrikka 2020 0414 */
		case "LOGINVERSION":// 确定版本号
			ClientMapAction.serverAction.get(AgreementUtil.LOGINVERSION).controlMessFromServer(ReceiveMes);
			break;

		}
	}
}

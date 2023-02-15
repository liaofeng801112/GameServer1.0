package org.come.nettyClient;

/**
 * 
 * @author 黄建彬
 * @date 确定版本号
 */
public class VersionControl implements FromServerAction {
	@Override
	public void controlMessFromServer(String mes) {
		System.out.println("与账号服务器连接成功!!--信息: " + mes);
	}

}

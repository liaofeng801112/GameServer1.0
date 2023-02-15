package org.come.tool;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.come.until.GsonUtil;

/**
 * 三端 AES加密解密算法
 * 
 * @author 叶豪芳
 * @date 2018年1月29日 上午11:33:38
 * 
 */
public class NewAESUtil {
	static String Instance="AES/CBC/PKCS5Padding";
	public static String sKey = "BOLIN861808ADMIN";
	//public static String sKey = "1234567890198809";
	static String AES = "AES";
	static String UTF_8 = "UTF-8";
	static String GB2312 = "GBK";
	
	//加密运用
	static SecretKeySpec JMskeySpec;
	static IvParameterSpec JMiv;// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
	//解密运用
	static SecretKeySpec skeySpec;
	static IvParameterSpec iv;
	static{
		try {
			byte[] JMraw = sKey.getBytes();
			JMskeySpec = new SecretKeySpec(JMraw, "AES");
			JMiv = new IvParameterSpec("0102030405060708".getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	static{
		try {
			byte[] raw = sKey.getBytes("ASCII");
			skeySpec = new SecretKeySpec(raw, AES);
			iv = new IvParameterSpec("0102030405060708".getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	/**AES加密算法*/
	public static String AESJDKEncode(String content) {
		return GsonUtil.getGsonUtil().getgson().toJson(content)+ "\n";
//		try {
//			byte[] encrypted = GZip.gZip(content.getBytes());
//
//			Cipher Encode = Cipher.getInstance(Instance);
//			Encode.init(Cipher.ENCRYPT_MODE, JMskeySpec, JMiv);
//			encrypted = Encode.doFinal(encrypted);
//			return GsonUtil.getGsonUtil().getgson().toJson(new BASE64Encoder().encode(encrypted))+ "\n";// 此处使用BASE64做转码功能，同时能起到2次加密的作用。
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
	}
	/**AES解密算法*/
	public static String AESJDKDncode(String content) {

			return GsonUtil.getGsonUtil().getgson().fromJson(content, String.class);
//			content = GsonUtil.getGsonUtil().getgson().fromJson(content, String.class);
//			byte[] encrypted1 = new BASE64Decoder().decodeBuffer(content);// 先用base64解密
//
//			Cipher Dncode = Cipher.getInstance(Instance);
//			Dncode.init(Cipher.DECRYPT_MODE, skeySpec, iv);
//			byte[] original = Dncode.doFinal(encrypted1);
//			original = GZip.unGZip(original);
//			String originalString = new String(original, UTF_8);
//			return originalString;

	}





}


package org.come.nettyClient;

import java.io.IOException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.come.tool.GZip;
import org.come.until.GsonUtil;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * AES加密解密算法(客户端使用)
 * 
 * @author
 * 
 */
public class Clinet_NewAESUtil {
	public static String sKey = "BOLIN861808ADMIN";
	public static String AES = "AES";
	public static String UTF_8 = "UTF-8";
	public static String GB2312 = "GBK";
	static String modelType = "AES/CBC/PKCS5Padding";
	public static Cipher Encode;// 加密
	public static Cipher Dncode;// 解密
	static BASE64Encoder base64Encodr;
	static BASE64Decoder base64Decoder;

	static {
		try {
			byte[] raw = sKey.getBytes(UTF_8);
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Encode = Cipher.getInstance(modelType);
			IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes(UTF_8));// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
			Encode.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			base64Encodr = new BASE64Encoder();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	static {
		try {
			byte[] raw = sKey.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, AES);
			Dncode = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
			Dncode.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			base64Decoder = new BASE64Decoder();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/** AES加密算法 */
	public static synchronized String AESJDKEncode(String content) {

		try {
			content = getTime() + content;
			byte[] encrypted = GZip.gZip(content.getBytes(UTF_8));
			encrypted = Encode.doFinal(encrypted);
			return GsonUtil.getGsonUtil().getgson().toJson(base64Encodr.encode(encrypted));// 此处使用BASE64做转码功能，同时能起到2次加密的作用。
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * AES解密算法
	 */
	public static synchronized String AESJDKDncode(String content) {
		try {
			content = GsonUtil.getGsonUtil().getgson().fromJson(content, String.class);
			byte[] encrypted1 = base64Decoder.decodeBuffer(content);// 先用base64解密
			byte[] original = Dncode.doFinal(encrypted1);
			original = GZip.unGZip(original);
			String originalString = new String(original, GB2312);
			return originalString;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * AES解密算法
	 */
	public static synchronized String AESJDKDncode_utf8(String content) throws IOException {
		try {
			// 判断Key是否正确
			content = GsonUtil.getGsonUtil().getgson().fromJson(content, String.class);
			byte[] encrypted1 = base64Decoder.decodeBuffer(content);// 先用base64解密
			byte[] original = Dncode.doFinal(encrypted1);
			original = GZip.unGZip(original);
			String originalString = new String(original, UTF_8);
			return originalString;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public static String getTime() {
		if (UrlUntil.time == System.currentTimeMillis()) {
			UrlUntil.time = UrlUntil.time + 1;
		} else {
			UrlUntil.time = System.currentTimeMillis();
		}
		return UrlUntil.time + "";
	}
}

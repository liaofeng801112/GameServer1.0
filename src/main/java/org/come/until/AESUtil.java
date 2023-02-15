package org.come.until;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * AES加密解密算法
 * 
 * @author 叶豪芳
 * @date 2018年1月29日 上午11:33:38
 * 
 */ 
public class AESUtil {
	static String AES="AES";
	static String UTF_8="utf-8";
	
	/**
	 * AES加密算法
	 */
	public static byte[] AESJDKEncode ( String message, String password ){
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
			keyGenerator.init(128,new SecureRandom(password.getBytes()));
			SecretKey secretKey = keyGenerator.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, AES);
			Cipher cipher = Cipher.getInstance(AES);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] result = cipher.doFinal(message.getBytes(UTF_8));
			return result;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	/**
	 * AES解密算法
	 */
	public static String AESJDKDncode ( byte[] msg, String password ){
		try {
			byte[] message = msg;
			KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
			keyGenerator.init(128,new SecureRandom(password.getBytes()));
			SecretKey secretKey = keyGenerator.generateKey();
			byte[] enCodeFormat = secretKey.getEncoded();
			SecretKeySpec key = new SecretKeySpec(enCodeFormat, AES);
			Cipher cipher = Cipher.getInstance(AES);
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] result = cipher.doFinal(message);
			String info = new String(result,UTF_8);
			return info;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	/**
	 * 16转byte[]
	 * @param hex
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static final byte[] hex2byte(String hex) throws IllegalArgumentException {
		if (hex.length() % 2 != 0) {
			throw new IllegalArgumentException();
		}
		char[] arr = hex.toCharArray();
		byte[] b = new byte[hex.length() / 2];
		for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++) {
			String swap = "" + arr[i++] + arr[i];
			int byteint = Integer.parseInt(swap, 16) & 0xFF;
			b[j] = new Integer(byteint).byteValue();
		}
		return b;
	}
	
	/**
	 * byte[]转16
	 * @param b
	 * @return
	 */
	public static final String byte2hex(byte b[]) {
		if (b == null) {
			throw new IllegalArgumentException("Argument b ( byte array ) is null! ");
		}
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0xff);
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
		}
		return hs.toUpperCase();
	}

	/** 
	 * 将byte[]转为各种进制的字符串 
	 * @param bytes byte[] 
	 * @param radix 基数可以转换进制的范围，从Character.MIN_RADIX到Character.MAX_RADIX，超出范围后变为10进制 
	 * @return 转换后的字符串 
	 */  
	public static String binary(byte[] bytes, int radix){
		return new BigInteger(1, bytes).toString(radix);// 这里的1代表正数  
	}
	
	 /**
     * 对字符串md5加密(小写+字母)
     *
     * @param str 传入要加密的字符串
     * @return  MD5加密后的字符串
     */
    public static String getMD5(String str) {
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            // 计算md5函数
            md.update(str.getBytes());
            // digest()最后确定返回md5 hash值，返回值为8为字符串。因为md5 hash值是16位的hex值，实际上就是8位的字符
            // BigInteger函数则将8位的字符串转换成16位hex值，用字符串来表示；得到字符串形式的hash值
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
           e.printStackTrace();
           return null;
        }
    }
	
}

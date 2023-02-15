package org.come.until;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class PayMd5 {
	/**
	 * MD5 32位小写加密
	 * @param encryptStr
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static String encryption(String str) throws UnsupportedEncodingException {
	
		String re_md5 = new String();
		try {
		MessageDigest md = MessageDigest.getInstance("MD5");
		md.update(str.getBytes("utf-8"));
		byte b[] = md.digest();

		int i;

		StringBuffer buf = new StringBuffer("");
		for (int offset = 0; offset < b.length; offset++) {
		i = b[offset];
		if (i < 0)
		i += 256;
		if (i < 16)
		buf.append("0");
		buf.append(Integer.toHexString(i));
		}

		re_md5 = buf.toString();

		} catch (NoSuchAlgorithmException e) {
		e.printStackTrace();
		}
		return re_md5;
		}

}

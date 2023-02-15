package org.come.until;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import javax.crypto.Cipher;


/**
 * 客户端加密数据包
 * @author 黄建彬
 *
 */
public class ClientPrivateInSercateUntil {
	
	//公钥
	private static  RSAPublicKey pubKey;
	
	private static  Cipher cipher;
	
	static  byte[] cipherText;
    
	//私钥
	private static  RSAPrivateKey privKey;
	
	//产生公钥加密数据
	public static byte[] crateSercate(String mes) throws Exception{
	
	     cipher = Cipher.getInstance("RSA");    
		 pubKey = (RSAPublicKey) SecreateUntil.getPublicKey(SecreateUntil.PUBLIC_PRIVATE_SERCATE);
		 cipher.init(Cipher.ENCRYPT_MODE, pubKey); 
         cipherText = cipher.doFinal(mes.getBytes()); 

		
		return cipherText;
		
	}
	
	
	//解密数据
	private static String getSendMes(byte[] process) throws Exception{
		pubKey = (RSAPublicKey) SecreateUntil.getPublicKey(SecreateUntil.PUBLIC_PRIVATE_SERCATE);
		 cipher = Cipher.getInstance("RSA");  
		cipher.init(Cipher.DECRYPT_MODE, pubKey);  
        byte[] plainText = cipher.doFinal(process); 
		
		return new String(plainText);
		
	}
	

}

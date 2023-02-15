package org.come.until;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 密码包，产生密码，获取公钥和私钥必要类
 * @author 黄建彬
 *
 */
public class SecreateUntil {
	
	//公钥路径
	static String PUBLIC_PRIVATE_SERCATE="mypubkey.dat";
	//私钥路径
	static String PRIVATE_PRIVATE_SERCATE=SecreateUntil.class.getResource("/").getPath() + "secreate/myprikey.dat";
	
	/**
     *生成私钥  公钥
     */
//    public static void geration(){
//        KeyPairGenerator keyPairGenerator;
//        try {
//            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
//            SecureRandom secureRandom = new SecureRandom(new Date().toString().getBytes()); 
//            keyPairGenerator.initialize(1024, secureRandom); 
//            KeyPair keyPair = keyPairGenerator.genKeyPair(); 
//            byte[] publicKeyBytes = keyPair.getPublic().getEncoded(); 
//            FileOutputStream fos = new FileOutputStream(PUBLIC_PRIVATE_SERCATE);  
//            fos.write(publicKeyBytes);  
//            fos.close(); 
//            byte[] privateKeyBytes = keyPair.getPrivate().getEncoded(); 
//            fos = new FileOutputStream(PRIVATE_PRIVATE_SERCATE);  
//            fos.write(privateKeyBytes);  
//            fos.close(); 
//        } catch (Exception e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }  
//    }
    
    
    /**
     * 获取公钥
     * @param filename
     * @return
     * @throws Exception
     */
    public static PublicKey getPublicKey(String filename) throws Exception { 
        File f = new File(filename); 
        FileInputStream fis = new FileInputStream(f);  
        DataInputStream dis = new DataInputStream(fis); 
        byte[] keyBytes = new byte[(int)f.length()];
        dis.readFully(keyBytes);  
        dis.close();
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes); 
        KeyFactory kf = KeyFactory.getInstance("RSA");  
        return kf.generatePublic(spec); 
    } 
    
    /**
     * 获取私钥
     * @param filename
     * @return
     * @throws Exception
     */
    public static PrivateKey getPrivateKey(String filename)throws Exception { 
        File f = new File(filename); 
        FileInputStream fis = new FileInputStream(f); 
        DataInputStream dis = new DataInputStream(fis); 
        byte[] keyBytes = new byte[(int)f.length()]; 
        dis.readFully(keyBytes); 
        dis.close(); 
        PKCS8EncodedKeySpec spec =new PKCS8EncodedKeySpec(keyBytes); 
        KeyFactory kf = KeyFactory.getInstance("RSA"); 
        return kf.generatePrivate(spec); 
      } 

}

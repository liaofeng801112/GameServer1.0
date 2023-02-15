package org.come.tool;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Map;
import java.util.Map.Entry;

import org.come.action.monitor.TBean;
import org.come.redis.RedisParameterUtil;
import org.come.redis.RedisPoolUntil;
import org.come.server.GameServer;
import org.come.until.GsonUtil;

import redis.clients.jedis.Jedis;

public class WriteOut {
	/**
	 * 创建
	 */
	public static String PATH=GameServer.getTXTPATH()+"/log";
	public static String PATHTwo=GameServer.getTXTPATH()+"/RZ";
	public static String PATHThere=GameServer.getTXTPATH()+"/Task";
	static int a;
	static int b;
	static int c;
	public static StringBuffer buffer;
	static StringBuffer buffer2;
	public static synchronized void addtxt(String newStr,long time){
		if (time<10) {return;}
		if (buffer==null) {buffer=new StringBuffer();}
		a++;
		buffer.append("\r\n");
		buffer.append(newStr);   	
		buffer.append(":");   	
		buffer.append(time);   		
		if (a>=100) {
			writeTxtFile(buffer.toString());
			buffer=null;
			a=0;
		}
	}
	public static synchronized void addtxt(){
		if (buffer!=null) {
			writeTxtFile(buffer.toString());
			buffer=null;
			a=0;	
		}	
	}
	/**替换文件*/
	public static void writeTxtFile(String txt){
		try {
			 //先读取原有文件内容，然后进行写入操作
			 try{
			  b++;
			  OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(new File(PATH+b+".txt")),"UTF-8");
			  BufferedWriter writer = new BufferedWriter(write);
			  writer.write(txt);
			  writer.close();
			  write.close();
			 }catch(Exception e){
			     e.printStackTrace();
			 }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	} 
	/**替换文件*/
	public static void writeTxtFile(String txt,String path){
		//先读取原有文件内容，然后进行写入操作
		 try{
		  OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(new File(PATH+path+".txt")),"UTF-8");
		  BufferedWriter writer = new BufferedWriter(write);
		  writer.write(txt);
		  writer.close();
		  write.close();
		 }catch(Exception e){
		     e.printStackTrace();
		 }
	}
	/**替换文件
	 * @param <T>*/
	public static <T> void writeTxtTBean(TBean<T> t){
		try {
			 //先读取原有文件内容，然后进行写入操作
			 try{
			  c++;
			  OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(new File(PATHThere+c+".txt")),"UTF-8");
			  BufferedWriter writer = new BufferedWriter(write);
			  writer.write(GsonUtil.getGsonUtil().getgson().toJson(t));
			  writer.close();
			  write.close();
			 }catch(Exception e){
			     e.printStackTrace();
			 }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	} 
	/**打印一份现在的同步日志*/
	public static void TB(){
		Jedis jedis=RedisPoolUntil.getJedis();//获取对应表格的所有数据
		Map<String, String> redisChangeMap=jedis.hgetAll(RedisParameterUtil.ROLE_CONTROL);
		RedisPoolUntil.returnResource(jedis);
		StringBuffer buffer=new StringBuffer();
		int size=0;
		int a=0;
		for (Entry<String, String> entry : redisChangeMap.entrySet()) {
			String v=entry.getKey()+":"+entry.getValue();
			buffer.append(v);
			buffer.append("  ");
			if (size%10==9) {buffer.append("\r\n");}
			size++;
			if (size>=1000) {
				size=0;
				a++;
				TBFile(buffer.toString(), a);
				buffer=new StringBuffer();
			}
		}
		if (size!=0) {
			a++;
			TBFile(buffer.toString(), a);
		}
	}
	/**
	 * 替换文件
	 */
	public static void TBFile(String txt,int a){
		try {
			 //先读取原有文件内容，然后进行写入操作
			 try{
			  OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(new File(PATH+"TB"+a+".txt")),"UTF-8");
			  BufferedWriter writer = new BufferedWriter(write);
			  writer.write(txt);
			  writer.close();
			  write.close();
			 }catch(Exception e){
			     e.printStackTrace();
			 }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	} 
}

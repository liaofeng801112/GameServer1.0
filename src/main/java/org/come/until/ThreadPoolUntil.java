package org.come.until;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * @author 黄建彬
 * @date : 2017年11月21日 上午10:48:43
 */
public class ThreadPoolUntil {
	
	private  static  ExecutorService fixedThreadPool;//创建固定线程池
	//单例实例化线程池
	public static ExecutorService getThreadPoolUntil() {
	   if(fixedThreadPool==null){
		   
		   fixedThreadPool=Executors.newFixedThreadPool(100); //设置做多执行线程50条
		   return  fixedThreadPool;
		   
	   }
	  return fixedThreadPool;
	}
	/**
	 * 提交任务
	 * @param command
	 */
	 public static void execute(Runnable command){
	        // do something
	        // 提交任务
		 fixedThreadPool.execute(command); 
	 }
 
public static ExecutorService getFixedThreadPool() {
	return fixedThreadPool;
}
public static void setFixedThreadPool(ExecutorService fixedThreadPool) {
	ThreadPoolUntil.fixedThreadPool = fixedThreadPool;
}
   
public static void closeFixedThreadPool(ExecutorService fixedThreadPool) {
	if(fixedThreadPool.isTerminated()){
		
		fixedThreadPool.shutdown();
		
	}
}  
   
   
}

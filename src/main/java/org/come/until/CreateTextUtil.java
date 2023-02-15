package org.come.until;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 生成txt文件
 * @author 叶豪芳
 * @date 2017年12月27日 下午3:01:02
 * 
 */ 
public class CreateTextUtil {

	/**
	 * 创建文件的操作，创建什么文件
	 * 
	 */
//	public static void createTextControl( String handle ){
//		long startTime = System.currentTimeMillis();
//		String textName = null;
//		String text = null;
//		switch ( handle ) {
//		// 生成地图信息
//		case "1":
//			textName = "mapinfo";
//			System.out.println("正在生成"+textName+"表");
//			text = MapNpcUntil.createMpaNpcText();
//			break;
//			// 生成在线商城信息
//		case "2":
//			textName = "eshop";
//			System.out.println("正在生成"+textName+"表");
//			text = ReadEshopUtil.createEshop();
//			break;
//			// 生成任务宠物经验文件
//		case "3":
//			textName = "exp";
//			System.out.println("正在生成"+textName+"表");
//			text = ReadExpUtil.createExp();
//			break;
//			// 生成灵宝技能
//		case "4":
//			textName = "lingbaoskill";
//			System.out.println("正在生成"+textName+"表");
//			text = ReadLinbaoskillUtil.createLingbaoSkill();
//			break;
//			// 生成灵宝
//		case "5":
//			textName = "lingbao";
//			System.out.println("正在生成"+textName+"表");
//			text = ReadLingbaoUtil.createLingbao();
//			break;
//			// 怪物
//		case "6":
//			textName = "monster";
//			System.out.println("正在生成"+textName+"表");
//			text = ReadMonsterUtil.createMonster();
//			break;
//			// 坐骑经验
//		case "7":
//			textName = "mountexp";
//			System.out.println("正在生成"+textName+"表");
//			text = ReadMountexpUtil.createMountExp();
//			break;
//			// 坐骑技能
//		case "8":
//			textName = "mountskill";
//			System.out.println("正在生成"+textName+"表");
//			text = ReadMountskillUtil.createMountSkill();
//			break;
//			// 坐骑
//		case "9":
//			textName = "mount";
//			System.out.println("正在生成"+textName+"表");
//			text = ReadMountUtil.createMount();
//			break;
//			// 召唤兽
//		case "10":
//			textName = "RoleSummoning";
//			System.out.println("正在生成"+textName+"表");
//			text = ReadPetUtil.createPet();
//			break;
//			// npc商店
//		case "11":
////			textName = "npcshop";
////			System.out.println("正在生成"+textName+"表");
////			text = ReadShopUtil.createShop();
////			break;
//			// 技能
//		case "12":
//			textName = "skill";
//			System.out.println("正在生成"+textName+"表");
//			text = ReadSkillUtil.createSkill();
//			break;
//			// 灵宝符石
//		case "13":
//			textName = "lingbaofushi";
//			System.out.println("正在生成"+textName+"表");
//			text = ReadLingbaoFushiUtil.createLingbaofushi();
//			break;
//			// 洗练
//		case "14":
//			textName = "alchemy";
//			System.out.println("正在生成"+textName+"表");
//			text = ReadAlchemyUtil.createAlchemy();
//			break;
//			// 物品
//		case "15":
//			textName = "goods";
//			System.out.println("正在生成"+textName+"表");
//			text = ReadGoodsUtil.createGoods();
//			break;
//		case "16":
//			textName = "drop";
//			System.out.println("正在生成"+textName+"表");
//			text = ReadDorpUtil.createDorp();
//			break;
//		case "17":
//			textName = "robots";
//			System.out.println("正在生成"+textName+"表");
//			text = ReadRobotUtil.createRobot();
//			break;
//		case "18":
//			textName = "sgpoint";
//			System.out.println("正在生成"+textName+"表");
//			text = ReadSghostpointUtil.createSgpoint();
//			break;
//		case "19":
//			textName = "save";
//			System.out.println("正在生成"+textName+"表");
//			text = ReadSaveUtil.createSave();
//			break;
//		case "20":
//			textName = "neidanexp";
//			System.out.println("正在生成"+textName+"表");
//			text = ReadNeidanExpUtil.createNeidanExp();
//			break;
//		default:
//			System.out.println("输入错误请重新输入!");
//			break;
//		}
////		byte[] scriteTxt = AESUtil.AESJDKEncode(text, "huangjianbingshidashabi");
//		try {
//			createFile("C:\\Users\\Administrator\\Desktop\\"+textName+".txt",text.getBytes());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println("生成完毕");
//		long endTime = System.currentTimeMillis();
//		System.out.println("进入游戏时间："+(endTime-startTime)+"ms");
//	}
	//第一种获取文件内容方式
	@SuppressWarnings("resource")
	public static byte[] getContent(String filePath) throws IOException {  
		File file = new File(filePath);  

		long fileSize = file.length();  
		if (fileSize > Integer.MAX_VALUE) {  
			System.out.println("file too big...");  
			return null;  
		}  

		FileInputStream fi = new FileInputStream(file);  

		byte[] buffer = new byte[(int) fileSize];  

		int offset = 0;  

		int numRead = 0;  

		while (offset < buffer.length  

				&& (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {  

			offset += numRead;  

		}  

		// 确保所有数据均被读取  

		if (offset != buffer.length) {  

			throw new IOException("Could not completely read file "  
					+ file.getName());  

		}  

		fi.close();  

		return buffer;  
	}  

	//第二种获取文件内容方式  
	public byte[] getContent2(String filePath) throws IOException  
	{  
		FileInputStream in=new FileInputStream(filePath);  

		ByteArrayOutputStream out=new ByteArrayOutputStream(1024);  

		System.out.println("bytes available:"+in.available());  

		byte[] temp=new byte[1024];  

		int size=0;  

		while((size=in.read(temp))!=-1)  
		{  
			out.write(temp,0,size);  
		}  

		in.close();  
		byte[] bytes=out.toByteArray();  
		System.out.println("bytes size got is:"+bytes.length);  

		return bytes;  
	}
	
	//将byte数组写入文件  
	public static void createFile(String path, byte[] content) throws IOException {  

		FileOutputStream fos = new FileOutputStream(path);  

		fos.write(content);  
		fos.close();  
	}

}

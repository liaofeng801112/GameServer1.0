package org.come.servlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.come.readUtil.ReadPoolUtil;
import org.come.tool.NewAESUtil;
import org.come.tool.ReadExelTool;
import org.come.until.GsonUtil;

public class UpXlsAndTxtFile extends HttpServlet {

    /**
     * Constructor of the object.
     */
    public UpXlsAndTxtFile() {
        super();
    }

    /**
     * Destruction of the servlet. <br>
     */
    @Override
	public void destroy() {
        super.destroy(); // Just puts "destroy" string in log
        // Put your code here
    }

    /**
     * The doGet method of the servlet. <br>
     * 
     * This method is called when a form has its tag value method equals to get.
     * 
     * @param request
     *            the request send by the client to the server
     * @param response
     *            the response send by the server to the client
     * @throws ServletException
     *             if an error occurred
     * @throws IOException
     *             if an error occurred
     */
    @Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
        out.println("<HTML>");
        out.println("  <HEAD><TITLE>A Servlet</TITLE></HEAD>");
        out.println("  <BODY>");
        out.print("    This is ");
        out.print(this.getClass());
        out.println(", using the GET method");
        out.println("  </BODY>");
        out.println("</HTML>");
        out.flush();
        out.close();
    }

    public static String pwdUp = "X123456";
    public static String lineFeed = "\r\n";
    public static Map<String, Integer> XLSmap;
    static{
    	XLSmap=new HashMap<>();
    	XLSmap.put("pet.xls",0);
    	XLSmap.put("palEquip.xls",1);
    	XLSmap.put("petExchange.xls",2);
    	XLSmap.put("map.xls",3);
    	XLSmap.put("npc.xls",4);
    	XLSmap.put("door.xls",5);
    	XLSmap.put("taskSet.xls",6);
    	XLSmap.put("taskData.xls",6);
    	XLSmap.put("palData.xls",7);
    	XLSmap.put("boos.xls",8);
    	XLSmap.put("monster.xls",9);
    	XLSmap.put("robots.xls",10);
    	XLSmap.put("item.xls",11);
    	XLSmap.put("newequip.xls",12);
    	XLSmap.put("alchemy.xls",13);
    	XLSmap.put("decorate.xls",14);
    	XLSmap.put("godstone.xls",15);
    	XLSmap.put("palEquip.xls",16);
    	XLSmap.put("shop.xls",17);
    	XLSmap.put("eshop.xls",18);
    	XLSmap.put("lShop.xls",19);
    	XLSmap.put("sghostpoint.xls",20);
    	XLSmap.put("xianqi.xls",21);
    	XLSmap.put("lingbao.xls",22);
    	XLSmap.put("lingbaofushi.xls",23);
    	XLSmap.put("gem.xls",24);
    	XLSmap.put("skill.xls",25);
    	XLSmap.put("drop.xls",26);
    	XLSmap.put("dntg.xls",27);
    	XLSmap.put("bbuy.xls",28);
    	XLSmap.put("suit.xls",29);
    	XLSmap.put("tx.xls",30);
    	XLSmap.put("present.xls",31);
    	XLSmap.put("exp.xls",32);
    	XLSmap.put("mount.xls",33);
    	XLSmap.put("color.xls",34);
    	XLSmap.put("child.xls",35);
    	XLSmap.put("draw.xls",36);
    	XLSmap.put("acard.xls",37);
    	XLSmap.put("title.xls",38);
    	XLSmap.put("event.xls",39);
    	XLSmap.put("wingTraining.xls",40);
    	XLSmap.put("starPalace.xls",41);
    	XLSmap.put("tyc.xls",42);
    	XLSmap.put("babyresult.xls",43);
    	XLSmap.put("guide.xls",44);
    	XLSmap.put("active.xls",45);
    	XLSmap.put("achieve.xls",46);
    }
    /**
     * The doPost method of the servlet. <br>
     * 
     * This method is called when a form has its tag value method equals to
     * post.
     * 
     * @param request
     *            the request send by the client to the server
     * @param response
     *            the response send by the server to the client
     * @throws ServletException
     *             if an error occurred
     * @throws IOException
     *             if an error occurred
     */
    @Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "ISO-8859-1"));
        List<String> strings = new ArrayList<>();
        String line = null;
        boolean is = true;
        while ((line = bufferedReader.readLine()) != null) {
            if (is) {
                is = false;
                continue;
            }
            strings.add(line);
            // System.out.println("line:" + line);
        }
        Map<String, String> map = new HashMap<>();
        // 获取参数
        for (int i = 0; i < strings.size() / 6; i++) {
            String key = strings.get(i * 6);
            String[] keyArr = key.split("\"");
            map.put(keyArr[1], strings.get(6 * i + 4));
        }
        String params = map.get("params");
        params = NewAESUtil.AESJDKDncode(params);
        params = params.substring(13);
        StringBuffer buffer = new StringBuffer();
        // 判断验证码是否存在
        if (params != null && !"".equals(params) && params.equals(pwdUp)) {// 判断验证码是否匹配
            // 匹配时删除params
            map.remove("params");
            // 验证文件是否正确
            File directory = new File(ReadExelTool.class.getResource("/").getPath()+"config");
            if (!directory.exists())
                directory.mkdir();
            Set<Entry<String, String>> entrySet = map.entrySet();
            for (Entry<String, String> entry : entrySet) {
            	String key = entry.getKey();
            	Integer I=XLSmap.get(key);
            	if (I==null) {
            		buffer.append(key);
                    buffer.append("没有读取该文件的方法.");
                    buffer.append(lineFeed);
                    continue;
				}
            	File oldFile = new File(directory.getAbsolutePath() + File.separatorChar + key);
            	if (oldFile.exists()) {
            		File newfile=new File(directory.getAbsolutePath() + File.separatorChar +"OLD"+ key);
            		if (newfile.exists()) {
            			newfile.delete();
					}
            		oldFile.renameTo(newfile);
            	}
            	String value = entry.getValue();
            	String aesjdkEncode = NewAESUtil.AESJDKDncode(value);
            	aesjdkEncode = aesjdkEncode.substring(13);
            	byte[] bsArr = GsonUtil.getGsonUtil().getgson().fromJson(aesjdkEncode, byte[].class);
            	FileOutputStream fos = new FileOutputStream(directory.getAbsolutePath() + File.separatorChar + key);
                fos.write(bsArr);
                try {// 关闭资源
                	if (fos != null){fos.close();}
                } catch (IOException e) {
                	e.printStackTrace();
                }
            	if (ReadPoolUtil.readTypeTwo(buffer, I)) {//替换成功
            		oldFile=new File(directory.getAbsolutePath() + File.separatorChar +"OLD"+ key);
                    if (oldFile.exists()) {
            			oldFile.delete();
                	}
            		buffer.append(key);
                    buffer.append("替换旧文件替换成功.");
                    buffer.append(lineFeed);
				}else {
                    File newFile = new File(directory.getAbsolutePath() + File.separatorChar + key);
                    newFile.delete();
                    oldFile=new File(directory.getAbsolutePath() + File.separatorChar +"OLD"+ key);
                    oldFile.renameTo(newFile);
                    buffer.append(key);
                    buffer.append("读取错误");
                    buffer.append(lineFeed);
				}
            }
        } else {// 不存在
            buffer.append("验证码不正确");
            buffer.append(lineFeed);
        }
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(NewAESUtil.AESJDKEncode(buffer.toString()).getBytes("UTF-8"));
        outputStream.flush();
        outputStream.close();
    }

    /**
     * 文件数据获取
     * 
     * @param result
     * @param fileName
     * @return
     * @throws Exception
     */

    public static Object getReadFileClass(StringBuffer buffer, String fileName, String path) {
//        if ("acard.xls".equals(fileName)) {
//            return ReadACardUtil.selectACards(path, buffer);
//        } else if ("alchemy.xls".equals(fileName)) {
//            return ReadAlchemyUtil.selectAlchemies(path, buffer);
//        } else if ("babyresult.xls".equals(fileName)) {
//            return ReadBabyResultUtil.selectBabyResult(path, buffer);
//        } else if ("bbuy.xls".equals(fileName)) {
//            return ReadBbuyUtil.selecBbuys(path, buffer);
//        } else if ("boos.xls".equals(fileName)) {
//            return ReadBoosUtil.selectBoos(path, buffer);
//        } else if ("child.xls".equals(fileName)) {
//            return ReadTalentsUtil.selectTalents(path, buffer);
//        } else if ("color.xls".equals(fileName)) {
//            return ReadColorUtil.selectcolors(path, buffer);
//        } else if ("decorate.xls".equals(fileName)) {
//            return ReadDecorateUtil.selectDecorate(path, buffer);
//        } else if ("dntg.xls".equals(fileName)) {
//            return ReadDNTGUtil.selectDNTGAwards(path, buffer);
//        } else if ("door.xls".equals(fileName)) {
//            return ReadDoorUtil.selectDoors(path, buffer);
//        } else if ("draw.xls".equals(fileName)) {
//            return ReadDrawUtil.selectDraw(path, buffer);
//        } else if ("drop.xls".equals(fileName)) {
//            return ReadDorpUtil.selectDorps(path, buffer);
//        } else if ("eshop.xls".equals(fileName)) {
//            return ReadEshopUtil.selectEshops(path, buffer);
//        } else if ("event.xls".equals(fileName)) {
//            return ReadEventUtil.selectEvents(path, buffer);
//        } else if ("exp.xls".equals(fileName)) {
//            return ReadExpUtil.selectExps(path, buffer);
//        } else if ("gem.xls".equals(fileName)) {
//            return ReadGemUtil.selectGem(path, buffer);
//        } else if ("godstone.xls".equals(fileName)) {
//            return ReadGodUtil.selectGodStones(path, buffer);
//        } else if ("guide.xls".equals(fileName)) {
//            return ReadGuideUtil.selectSkills(path, buffer);
//        } else if ("item.xls".equals(fileName)) {
//            return ReadGoodsUtil.selectAllGoods(path, buffer);
//        } else if ("lingbao.xls".equals(fileName)) {
//            return ReadLingbaoUtil.selectLingbaos(path, buffer);
//        } else if ("lingbaofushi.xls".equals(fileName)) {
//            return ReadLingbaoFushiUtil.selectGoodstables(path, buffer);
//        } else if ("lingbaoskill.xls".equals(fileName)) {
//            return "-1";
//        } else if ("lShop.xls".equals(fileName)) {
//            return ReadLShopUtil.selectLShops(path, buffer);
//        } else if ("map.xls".equals(fileName)) {
//            return ReadMapUtil.selectMap(path, buffer);
//        } else if ("monster.xls".equals(fileName)) {
//            return ReadMonsterUtil.selectMonsters(path, buffer);
//        } else if ("mount.xls".equals(fileName)) {
//            return ReadMountUtil.selectMount(path, buffer);
//        } else if ("mountexp.xls".equals(fileName)) {
//            return ReadMountexpUtil.selectMountexps(path, buffer);
//        } else if ("mountskill.xls".equals(fileName)) {
//            return ReadMountskillUtil.selectMountSkill(path, buffer);
//        } else if ("neidanexp.xls".equals(fileName)) {
//            return ReadNeidanExpUtil.selectNeidanExps(path, buffer);
//        } else if ("newequip.xls".equals(fileName)) {
//            return ReadNewequipUtil.selectNewequip(path, buffer);
//        } else if ("npc.xls".equals(fileName)) {
//            return ReadNpcUtil.selectNpctables(path, buffer);
//        } else if ("palData.xls".equals(fileName)) {
//            return ReadPalDataUtil.selectPalData(path, buffer);
//        } else if ("palEquip.xls".equals(fileName)) {
//            return ReadPalEquipUtil.selectPalEquip(path, buffer);
//        } else if ("pet.xls".equals(fileName)) {
//            return ReadPetUtil.selectRoleSummonings(path, buffer);
//        } else if ("present.xls".equals(fileName)) {
//            return ReadPresentUtil.selectPresents(path, buffer);
//        } else if ("robots.xls".equals(fileName)) {
//            return ReadRobotUtil.selectRobots(path, buffer);
//        } else if ("save.xls".equals(fileName)) {
//            return ReadSaveUtil.selectSave(path, buffer);
//        } else if ("sghostpoint.xls".equals(fileName)) {
//            return ReadSghostpointUtil.selectSghostpoint(path, buffer);
//        } else if ("shop.xls".equals(fileName)) {
//            return ReadShopUtil.getAllShop(path, buffer);
//        } else if ("skill.xls".equals(fileName)) {
//            return ReadSkillUtil.selectSkills(path, buffer);
//        } else if ("starPalace.xls".equals(fileName)) {
//            return ReadStarPalaceUtil.selectStarPalace(path, buffer);
//        } else if ("suit.xls".equals(fileName)) {
//            return ReadSuitUtil.selecSuits(path, buffer);
//        } else if ("talk.xls".equals(fileName)) {
//            return ReadTalkUtil.selectTalks(path, buffer);
//        } else if ("task.xls".equals(fileName)) {
//            return "-1";
//        } else if ("taskData.xls".equals(fileName)) {
//            return ReadTaskDataUtil.selectTaskData(path, buffer);
//        } else if ("taskSet.xls".equals(fileName)) {
//            return ReadTaskSetUtil.selectTaskSet(path, buffer);
//        } else if ("title.xls".equals(fileName)) {
//            return ReadTitleUtil.selectTitles(path, buffer);
//        } else if ("tx.xls".equals(fileName)) {
//            return ReadTxUtil.selectDecoration(path, buffer);
//        } else if ("tyc.xls".equals(fileName)) {
//            return ReadTYCUtil.selectDecoration(path, buffer);
//        } else if ("wingTraining.xls".equals(fileName)) {
//            return ReadWingTrainingUtil.selectWingTraining(path, buffer);
//        } else if ("xianqi.xls".equals(fileName)) {
//            return ReadXianqiUtil.selectXianqi(path, buffer);
//        }
        return null;
    }

    public static void addStringBufferMessage(StringBuffer buffer, int i, int j, String value, String errorMsg) {
        buffer.append("第");
        buffer.append(i);
        buffer.append("列");
        buffer.append("第");
        buffer.append(j);
        buffer.append("行,数据");
        buffer.append(value);
        buffer.append("错误,错误信息:");
        buffer.append(errorMsg);
        buffer.append(lineFeed);
    }

    /**
     * 生成txt失败
     * 
     * @time 2020年1月9日 下午3:07:34<br>
     * @param buffer
     * @param fileName
     * @param errorMsg
     */
    public static void addStringBufferMessageTxtFailure(StringBuffer buffer, String fileName, String errorMsg) {
        buffer.append(fileName);
        buffer.append("文件生成txt失败,错误信息:");
        buffer.append(errorMsg);
        buffer.append(lineFeed);
    }

    /**
     * 生成txt 成功
     * 
     * @time 2020年1月9日 下午3:08:09<br>
     * @param buffer
     * @param fileName
     * @param errorMsg
     */
    public static void addStringBufferMessageTxtSuccess(StringBuffer buffer, String fileName, String textName) {
        buffer.append(fileName);
        buffer.append("文件生成");
        buffer.append(textName);
        buffer.append(".txt成功");
        buffer.append(lineFeed);
    }

    /**
     * 没有生成txt文件的方法
     * 
     * @time 2020年1月9日 下午3:11:27<br>
     * @param buffer
     * @param fileName
     */
    public static void addStringBufferMessageTxtUnfound(StringBuffer buffer, String fileName) {
        buffer.append(fileName);
        buffer.append("没有生成txt文件的方法");
        buffer.append(lineFeed);
    }

    /**
     * 依据不同名字进行分类
     */
    public void deviceClassForMes(String fileName, StringBuffer buffer) {
//        String textName = null;
//        String text = null;
//        try {
//            switch (fileName) {
//            case "map.xls":// 地图信息
//                textName = "map";
//                text = ReadMapUtil.createMap();
//                break;
//            case "shop.xls":// npc商店信息
//                ReadPoolUtil.readType(2);
//                addStringBufferMessageTxtSuccess(buffer, fileName, textName);
//                return;
//            case "exp.xls":// 召唤兽和人物升级经验信息
//                textName = "exp";
//                text = ReadExpUtil.createExp();
//                break;
//            case "eshop.xls":// 商城信息
//                textName = "eshop";
//                text = ReadEshopUtil.createEshop();
//                break;
//            case "skill.xls":// 技能表
//                textName = "skill";
//                text = ReadSkillUtil.createSkill();
//                break;
//            case "babyresult.xls":// 孩子结局
//                textName = "babyresult";
//                text = ReadBabyResultUtil.creatbabyresult();
//                break;
//            case "item.xls":// 物品表
//                textName = "goods";
//                text = ReadGoodsUtil.createGoods();
//                break;
//            case "robots.xls"://
//                textName = "robots";
//                text = ReadRobotUtil.createRobot();
//                break;
//            case "npc.xls":
//                textName = "npc";
//                text = ReadNpcUtil.createNpcText();
//                break;
//            case "door.xls":// 传送
//                textName = "door";
//                text = ReadDoorUtil.createDoor();
//                break;
//            case "taskSet.xls":// 任务
//            case "taskData.xls":// 任务
//                ReadPoolUtil.readType(0);
//                addStringBufferMessageTxtSuccess(buffer, fileName, textName);
//                return;
//            case "bbuy.xls":// 回收表
//                textName = "bbuy";
//                text = ReadBbuyUtil.createBbuy();
//                break;
//            case "child.xls":// 天资
//                textName = "talent";
//                text = ReadTalentsUtil.createTalent();
//                break;
//            case "color.xls":// 颜色方案
//                textName = "color";
//                text = ReadColorUtil.createcolor();
//                break;
//            case "tx.xls":// 特效配置
//                textName = "tx";
//                text = ReadTxUtil.createTX();
//                break;
//            case "suit.xls":// 套装表
//                textName = "suit";
//                text = ReadSuitUtil.createSkill();
//                break;
//            case "guide.xls":// 引导
//                textName = "guide";
//                text = ReadGuideUtil.createSkill();
//                break;
//            case "tyc.xls":// 天演策表
//                textName = "tyc";
//                text = ReadTYCUtil.createTX();
//                break;
//            case "acard.xls":// 七十二变表
//                textName = "acard";
//                text = ReadACardUtil.createACards();
//                break;
//            case "title.xls":// 称谓
//                textName = "title";
//                text = ReadTitleUtil.createTitle();
//                break;
//            case "event.xls":// 活动表
//                textName = "event";
//                text = ReadEventUtil.createEvent();
//                break;
//            case "palData.xls":// 伙伴表
//                ReadPoolUtil.readType(1);
//                addStringBufferMessageTxtSuccess(buffer, fileName, textName);
//                return;
//            default:
//                addStringBufferMessageTxtUnfound(buffer, fileName);
//                return;
//            }
//            byte[] vvv = MessageGZIP.compressToByte(text);
//            vvv = NewAESUtil.Encode.doFinal(vvv);
//            if (vvv.length > 10) {
//                byte a = vvv[vvv.length - 4];
//                vvv[vvv.length - 4] = vvv[4];
//                vvv[4] = a;
//            }
//            String path = GameServer.class.getResource("/").getPath().replaceAll("WEB-INF/classes/", "") + "GetTXT\\"  + textName + ".txt";
//            CreateTextUtil.createFile(path, vvv);
//            addStringBufferMessageTxtSuccess(buffer, fileName, textName);
//        } catch (Exception e) {
//            addStringBufferMessageTxtFailure(buffer, fileName, MainServerHandler.getErrorMessage(e));
//            return;
//        }
    }

    /**
     * Initialization of the servlet. <br>
     * 
     * @throws ServletException
     *             if an error occurs
     */
    @Override
	public void init() throws ServletException {
        // Put your code here
    }

}

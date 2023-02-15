package org.come.readUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.come.bean.SkillBean;
import org.come.handler.MainServerHandler;
import org.come.model.Skill;
import org.come.model.SkillModel;
import org.come.servlet.UpXlsAndTxtFile;
import org.come.tool.ReadExelTool;
import org.come.tool.SettModelMemberTool;
import org.come.until.GsonUtil;
/**
 * 读取传送表
 * @author 叶豪芳
 * @date : 2017年11月30日 下午3:44:05
 */
public class ReadSkillUtil {
	// 根据技能ID获取技能信息集合
	public static ConcurrentHashMap<String, Skill> getSkill(String path, StringBuffer buffer){
		// 存储每个技能ID与技能信息的集合
		ConcurrentHashMap<String, Skill> skillMap = new ConcurrentHashMap<String, Skill>();
		String[][] result = ReadExelTool.getResult("config/"+path+".xls");
		for (int i = 2; i < result.length; i++) {
			if (result[i][0].equals("")) {continue;}
    		Skill skill = new Skill();
			for (int j = 0; j < result[i].length; j++) {
                try {
                    SettModelMemberTool.setReflectRelative(skill, result[i][j], j);
                } catch (Exception e) {
                    UpXlsAndTxtFile.addStringBufferMessage(buffer, i, j, result[i][j],MainServerHandler.getErrorMessage(e));
                    return null;
                }
            }
			if (skill.getSkillid()!=0) {
				skillMap.put(skill.getSkillid()+"",skill);
				skillMap.put(skill.getSkillname() ,skill);	
			}
		}
		return skillMap;
	}
    /**
     * HGC生成SkillBean
     * 
     * @time 2020年1月9日 上午10:24:26<br>
     * @return
     */
    public static String createSkill(ConcurrentHashMap<String, Skill> map) {
        SkillBean skillBean = new SkillBean();
        Map<String, SkillModel> skillMap=new HashMap<>();
        for (Skill skill:map.values()) {
        	SkillModel skillModel=new SkillModel(skill);
        	skillMap.put(skillModel.getSkillid(), skillModel);
		}
        skillBean.setSkillMap(skillMap);
        String msgString = GsonUtil.getGsonUtil().getgson().toJson(skillBean);
        return msgString;
    }
}

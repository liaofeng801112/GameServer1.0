package org.come.bean;

//经脉
public class Meridians {
    //
    private int id;// 经脉编号
    private String idName;// 经脉编号
    private int allExp;// 进度
    private int quality;// 品质
    private String qualityName;// 品质
    private String attrs;// 属性
    private double min;// 最小值
    private double max;// 最大值
    private int gold;// 金币需求
    private int money;// 仙玉需求
    private int exp;// 经验需求
    private int goldExp;// 金币修炼进度
    private int moneyExp;// 仙玉修炼进度
    private int resetGold;// 重洗金币需求
    private int lockQuality;// 仙玉锁定品质
    private int lockAttr;// 仙玉锁定属性
    private String stagesName;// 境界，阶段
    private String stages;// 境界，阶段
    private String level;// 等级限制
    private int probability;// 品质几率

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public String getAttrs() {
        return attrs;
    }

    public String getIdName() {
        return idName;
    }

    public void setIdName(String idName) {
        this.idName = idName;
    }

    public String getQualityName() {
        return qualityName;
    }

    public void setQualityName(String qualityName) {
        this.qualityName = qualityName;
    }

    public void setAttrs(String attrs) {
        this.attrs = attrs;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAllExp() {
        return allExp;
    }

    public void setAllExp(int allExp) {
        this.allExp = allExp;
    }

    public int getQuality() {
        return quality;
    }

    public void setQuality(int quality) {
        this.quality = quality;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getGoldExp() {
        return goldExp;
    }

    public void setGoldExp(int goldExp) {
        this.goldExp = goldExp;
    }

    public int getMoneyExp() {
        return moneyExp;
    }

    public void setMoneyExp(int moneyExp) {
        this.moneyExp = moneyExp;
    }

    public int getResetGold() {
        return resetGold;
    }

    public void setResetGold(int resetGold) {
        this.resetGold = resetGold;
    }

    public int getLockQuality() {
        return lockQuality;
    }

    public void setLockQuality(int lockQuality) {
        this.lockQuality = lockQuality;
    }

    public int getLockAttr() {
        return lockAttr;
    }

    public void setLockAttr(int lockAttr) {
        this.lockAttr = lockAttr;
    }

    public int getProbability() {
        return probability;
    }

    public void setProbability(int probability) {
        this.probability = probability;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public String getStages() {
        return stages;
    }

    public void setStages(String stages) {
        this.stages = stages;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getStagesName() {
        return stagesName;
    }

    public void setStagesName(String stagesName) {
        this.stagesName = stagesName;
    }

}

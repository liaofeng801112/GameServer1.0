package org.come.model;

import org.come.entity.Goodstable;

import java.math.BigDecimal;
import java.util.List;

public class GoodsExchange {

    private int eId;//兑换id
    private int type;//分类
    private String consume;//消耗
    private BigDecimal gid;//物品ID
    private String goodssname;//物品名称
    private String instruction;//说明
    private String[] instructions;//说明
    private String[] value;//功能
    public void initGood(Goodstable goodstable, List<Goodstable> goodstables){
        goodssname = goodstable.getGoodsname();
        instruction = goodstable.getInstruction();
        value=new String[goodstables.size()];
        instructions = new String[goodstables.size()];
        for (int i = 0; i < goodstables.size(); i++) {
            instructions[i] = goodstables.get(i).getInstruction();
        }for (int i = 0; i < goodstables.size(); i++) {
            value[i] = goodstables.get(i).getValue();
        }
    }
    public int geteId() {
        return eId;
    }
    public void seteId(int eId) {
        this.eId = eId;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public String getConsume() {
        return consume;
    }
    public void setConsume(String consume) {
        this.consume = consume;
    }
    public BigDecimal getGid() {
        return gid;
    }
    public void setGid(BigDecimal gid) {
        this.gid = gid;
    }
    public String getGoodssname() {
        return goodssname;
    }
    public void setGoodssname(String goodssname) {
        this.goodssname = goodssname;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    public String[] getInstructions() {
        return instructions;
    }

    public void setInstructions(String[] instructions) {
        this.instructions = instructions;
    }

    public String[] getValue(){return value;}
    public void setValue(String[] value){
        this.value=value;
    }
}

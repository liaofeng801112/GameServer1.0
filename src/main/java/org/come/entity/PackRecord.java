package org.come.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import come.tool.Role.PartJade;

public class PackRecord {
    /**角色ID*/
    private BigDecimal roleId;
    /**背包数据*/
    private String record;
    /**记录召唤兽支援数据*/
    private String helpBb;
    /**记录灵宝支援数据*/
    private String helpLing;
    /**特效*/
    private String tx;
    /**记录套装最大收录数量*/
    private int suitNum;
    /**收录*/
    private String collect;
    /**套装1 1-10*/
    private String suit1;
    /**套装2 11-20*/
    private String suit2;
    /**套装3 21-30*/
    private String suit3;
    /**套装4 31-40*/
    private String suit4;
    /**套装5 41-50*/
    private String suit5;
    /**套装6 51-60*/
    private String suit6;
    /**套装7 61-70*/
    private String suit7;
    /**套装8 71-80*/
    private String suit8;
    /**套装9 81-90*/
    private String suit9;
    /**套装10 91-100*/
    private String suit10;
    /**套装11 101-110*/
    private String suit11;
    /**常用记录*/
    private String lCard;
    /**副本记录*/
    private String other;
    /**获取已装备的特效集合*/
    public List<String> getPutTX(){
    	if (tx==null||tx.equals("")) {return null;}
    	List<String> list=null;
    	String[] txs=tx.split("\\|");
    	for (int i = 0; i < txs.length; i++) {
    		if (txs[i].endsWith("#")) {
    			if (list==null) {list=new ArrayList<>();}
    			list.add(txs[i].substring(0,txs[i].length()-1));
    		}
		}
    	return list;
    }
    /**穿戴特效*/
    public String putTX(String... ps){
    	if (tx==null||tx.equals("")) {return tx;}
    	String[] txs=tx.split("\\|");
    	StringBuffer buffer=new StringBuffer();
    	for (int i = 0; i < txs.length; i++) {
    		String tid=txs[i];
    		if (tid.endsWith("#")) {
    			tid=tid.substring(0, tid.length()-1);
    		}
    		for (int j = 0; j < ps.length; j++) {
    			if (ps[j]!=null&&tid.equals(ps[j])) {
					tid+="#";
					break;
				}
			}		
    		if (buffer.length()!=0) {buffer.append("|");}
    		buffer.append(tid);
		}
    	tx=buffer.toString();
		return tx;
    }
    /**添加特效*/
    public String addTX(String id){
    	if (isTX(id)) {
    		return tx;
		}	
    	if (tx==null||tx.equals("")) {tx=id;}
    	else {tx+="|"+id;}
		return tx;
    }
    /**移除特效*/
    public String removeTX(String id){
    	if (tx==null||tx.equals("")) {return tx;}
    	String[] txs=tx.split("\\|");
    	StringBuffer buffer=new StringBuffer();
    	for (int i = 0; i < txs.length; i++) {
    		String tid=txs[i];
    		if (tid.endsWith("#")) {
    			tid=tid.substring(0, tid.length()-1);
    		}
    		if (id.equals(tid)) {
				continue;
			}			
    		if (buffer.length()!=0) {buffer.append("|");}
    		buffer.append(txs[i]);
		}
    	tx=buffer.toString();
		return tx;
    }
    /**判断是否拥有该特效*/
    public boolean isTX(String id){
    	if (tx==null||tx.equals("")) {return false;}
    	String[] txs=tx.split("\\|");
    	for (int i = 0; i < txs.length; i++) {
    		if (txs[i].endsWith("#")) {
    			txs[i]=txs[i].substring(0, txs[i].length()-1);
    		}
    		if (id.equals(txs[i])) {
				return true;
			}			
		}
		return false;
    }
    /**
     * 查询
     * 1-部件1-部件2-部件3|2-部件1-部件2-部件3
     * 部件id_数量1_数量1_数量1_数量1_数量1
     */
    public PartJade getPartJade(int suitid,int partId){
    	PartJade partJade=new PartJade(suitid, partId);
    	String suit=getSuit(suitid);
    	if (suit==null||suit.equals("")) {
    		return partJade;
		}
    	String[] suits=suit.split("\\|");
    	for (int i = 0; i < suits.length; i++) {
			String[] suits2=suits[i].split("-");
		    int sid=Integer.parseInt(suits2[0]);
		    if (suitid!=sid){continue;}
		    for (int j = 1; j < suits2.length; j++) {
		    	String[] suits3=suits2[j].split("_");
		    	int pid=Integer.parseInt(suits3[0]);
		    	if (partId!=pid){continue;}
		    	partJade.initJade(suits3);
				return partJade;
			}
		}
		return partJade;
    }
    /**修改1 单个修改*/ 
    public PartJade setPartJade(int suitid,int partId,int pz,int sum){
    	String suit=getSuit(suitid);
    	if (suit==null||suit.equals("")) {
    	     StringBuffer buffer=new StringBuffer();
    	     buffer.append(suitid);
    	     buffer.append("-");
    	     buffer.append(partId);
    	     for (int i = 1; i < 6; i++) {
    	    	buffer.append("_");
        	    if (i==pz) {
        	      buffer.append(sum);
				}else {
				  buffer.append(0);	
				}
    	    	
			 }
    	     setSuit(suitid,buffer.toString());
    	     PartJade partJade=new PartJade(suitid, partId);
    	     partJade.setJade(pz, sum);
    	     return partJade;
		}
    	PartJade partJade=new PartJade(suitid, partId);
        StringBuffer buffer=new StringBuffer();
    	String[] suits=suit.split("\\|");
    	boolean is1=true;
    	for (int i = 0; i < suits.length; i++) {
			String[] suits2=suits[i].split("-");
		    int sid=Integer.parseInt(suits2[0]);
		    if (suitid!=sid){
		    	if (i!=0) {buffer.append("|");}
		    	buffer.append(suits[i]);
		    	continue;
		    }
		    if (i!=0) {buffer.append("|");}
		    is1=false;
		  	boolean is2=true;
		    buffer.append(suits2[0]);
		    for (int j = 1; j < suits2.length; j++) {
		    	String[] suits3=suits2[j].split("_");
		    	int pid=Integer.parseInt(suits3[0]);
		    	if (partId!=pid){
		    		buffer.append("-");
			    	buffer.append(suits2[j]);
		    		continue;
		    	}
		    	is2=false;
		    	partJade.initJade(suits3);
		    	partJade.setJade(pz, sum);
		    	if (!partJade.isJade()) {
		    		buffer.append("-");
			    	buffer.append(partJade.toString());
				}
			}
		    if (is2) {
		    	partJade.setJade(pz, sum);
			    if (!partJade.isJade()) {
		    		buffer.append("-");
			    	buffer.append(partJade.toString());
				}	
			}
		}
    	if (is1) {
    		buffer.append("|");
    		buffer.append(suitid);
    		partJade.setJade(pz,sum);
    		if (!partJade.isJade()) {
	    		buffer.append("-");
		    	buffer.append(partJade.toString());
			}	
		}
    	setSuit(suitid,buffer.toString());
	    return partJade;
    }
    /**修改2 覆盖修改*/ 
    public void setPartJade(PartJade partJade){
    	String suit=getSuit(partJade.getSuitid());
    	if (suit==null||suit.equals("")) {
    	     StringBuffer buffer=new StringBuffer();
    	     buffer.append(partJade.getSuitid());
    	     buffer.append("-");
    	     buffer.append(partJade.getPartId());
    	     for (int i = 1; i < 6; i++) {
    	    	buffer.append("_");
    	    	buffer.append(partJade.getJade(i));	
			 }
    	     setSuit(partJade.getSuitid(),buffer.toString());
    	     return;
		}
        StringBuffer buffer=new StringBuffer();
    	String[] suits=suit.split("\\|");
    	boolean is1=true;
    	for (int i = 0; i < suits.length; i++) {
			String[] suits2=suits[i].split("-");
		    int sid=Integer.parseInt(suits2[0]);
		    if (partJade.getSuitid()!=sid){
		    	if (i!=0) {buffer.append("|");}
		    	buffer.append(suits[i]);
		    	continue;
		    }
		    if (i!=0) {buffer.append("|");}
		    is1=false;
		  	boolean is2=true;
		    buffer.append(suits2[0]);
		    for (int j = 1; j < suits2.length; j++) {
		    	String[] suits3=suits2[j].split("_");
		    	int pid=Integer.parseInt(suits3[0]);
		    	if (partJade.getPartId()!=pid){
		    		buffer.append("-");
			    	buffer.append(suits2[j]);
		    		continue;
		    	}
		    	is2=false;
		    	if (!partJade.isJade()) {
		    		buffer.append("-");
			    	buffer.append(partJade.toString());
				}
			}
		    if (is2) {
			    if (!partJade.isJade()) {
		    		buffer.append("-");
			    	buffer.append(partJade.toString());
				}	
			}
		}
    	if (is1) {
    		buffer.append("|");
    		buffer.append(partJade.getSuitid());
    		if (!partJade.isJade()) {
	    		buffer.append("-");
		    	buffer.append(partJade.toString());
			}	
		}
    	setSuit(partJade.getSuitid(),buffer.toString());
    }
    /**添加收录 套装id-部件id-部件id-部件id*/
    public void setCollect(int suitid,int partId){
       	if (collect==null||collect.equals("")) {
       		collect=suitid+"-"+partId;
       		return;
       	}
        StringBuffer buffer=new StringBuffer();
       	String[] collects=collect.split("\\|");
       	boolean is1=true;
       	for (int i = 0; i < collects.length; i++) {
       		String[] collects2=collects[i].split("-");
		    int sid=Integer.parseInt(collects2[0]);
		    if (suitid!=sid){
		    	if (i!=0) {buffer.append("|");}
		    	buffer.append(collects[i]);
		    	continue;
		    }
		    if (i!=0) {buffer.append("|");}
		    is1=false;
		  	boolean is2=true;
		    buffer.append(collects2[0]);
		    for (int j = 1; j < collects2.length; j++) {
		    	int pid=Integer.parseInt(collects2[j]);
		    	if (partId!=pid){
		    		buffer.append("-");
			    	buffer.append(collects2[j]);
		    		continue;
		    	}
		    	is2=false;
			}
		    if (is2) {
		    	buffer.append("-");
		    	buffer.append(partId);
			}
		}
       	if (is1) {
       		buffer.append("|");
    		buffer.append(suitid);
    		buffer.append("-");
    		buffer.append(partId);
		}
       	collect=buffer.toString();
    }
    /**删除制定套装id的收录*/
    public void setCollect(int suitid){
    	if (collect==null||collect.equals("")) {
       		return;
       	}
        StringBuffer buffer=new StringBuffer();
       	String[] collects=collect.split("\\|");
       	for (int i = 0; i < collects.length; i++) {
       		String[] collects2=collects[i].split("-");
		    int sid=Integer.parseInt(collects2[0]);
		    if (suitid!=sid){
		    	if (buffer.length()!=0) {buffer.append("|");}
		    	buffer.append(collects[i]);
		    }
		}
       	collect=buffer.toString();
    }
    /**获取收录 套装id-部件id-部件id-部件id*/
    public String[] getCollect(int suitid){
    	if (collect==null||collect.equals("")) {
       		return null;
       	}
    	String[] collects=collect.split("\\|");
       	for (int i = 0; i < collects.length; i++) {
       		String[] collects2=collects[i].split("-");
		    int sid=Integer.parseInt(collects2[0]);
		    if (suitid!=sid){
		    	continue;
		    }
		    String[] v=new String[collects2.length-1];
		    for (int j = 1; j < collects2.length; j++) {
		    	v[j-1]=collects2[j];
			}
		    return v;
		}
		return null;
    }
    /**获取*/
    public String getSuit(int id){
    	if (id<=0||id>110) {return null;}
		id=(id-1)/10;
		switch (id) {
		case 0:return suit1;
		case 1:return suit2;
		case 2:return suit3;
		case 3:return suit4;
		case 4:return suit5;
		case 5:return suit6;
		case 6:return suit7;
		case 7:return suit8;
		case 8:return suit9;
		case 9:return suit10;
		case 10:return suit11;
		}
		return null;
    }
    /**保存*/
    public void setSuit(int id,String suit){
    	if (id<=0||id>110) {return;}
		id=(id-1)/10;
		switch (id) {
		case 0:suit1=suit;break;
		case 1:suit2=suit;break;
		case 2:suit3=suit;break;
		case 3:suit4=suit;break;
		case 4:suit5=suit;break;
		case 5:suit6=suit;break;
		case 6:suit7=suit;break;
		case 7:suit8=suit;break;
		case 8:suit9=suit;break;
		case 9:suit10=suit;break;
		case 10:suit11=suit;break;
		}
    }
    /**判断是否常用*/
    public String isCard(String id){
    	if (lCard==null||lCard.equals("")) {
    		lCard=id;
    		return lCard;
		}
    	String[] vs=lCard.split("\\|");
    	for (int i = 0; i < vs.length; i++) {
			if (vs[i].equals(id)) {return null;}
		}
    	if (vs.length<=2) {
    		lCard+="|"+id;
    		return lCard;
		}
    	lCard=id+"|"+vs[0]+"|"+vs[1];
		return lCard;
    }
    /**添加副本通光记录*/
    public boolean addOther(String id){
    	if (other==null||other.equals("")) {
			other=id+"S";
			return true;
		}
    	StringBuffer buffer=new StringBuffer();
    	String[] vs=other.split("\\|");
    	for (int i = 0; i < vs.length; i++) {
    		if (vs[i].substring(0, vs[i].length()-1).equals(id)) {
				return false;
			}
    		if (buffer.length()!=0) {buffer.append("|");}
    		buffer.append(vs[i]);
		}
    	if (buffer.length()!=0) {buffer.append("|");}
		buffer.append(id);
		buffer.append("S");
		return true;
    }
    /**判断领取资格   未领取修改未已领取  0没资格1未领取 2是已领取*/
    public int isOther(String id){
    	if (other==null||other.equals("")) {return 0;}
    	StringBuffer buffer=new StringBuffer();
    	String[] vs=other.split("\\|");
    	boolean is=true;
    	for (int i = 0; i < vs.length; i++) {
    		if (vs[i].substring(0, vs[i].length()-1).equals(id)) {
			    if (vs[i].endsWith("E")) {return 2;}
			    is=false;
			    continue;
			}
    		if (buffer.length()!=0) {buffer.append("|");}
    		buffer.append(vs[i]);
		}
    	if (is) {return 0;}
    	if (buffer.length()!=0) {buffer.append("|");}
		buffer.append(id);
		buffer.append("E");
		other=buffer.toString();
		return 1;
    }
    /**
     * 角色ID
     * @return ROLE_ID 角色ID
     */
    public BigDecimal getRoleId() {
        return roleId;
    }
    /**
     * 角色ID
     * @param roleId 角色ID
     */
    public void setRoleId(BigDecimal roleId) {
        this.roleId = roleId;
    }
    /**
     * 背包数据
     * @return RECORD 背包数据
     */
    public String getRecord() {
        return record;
    }
    /**
     * 背包数据
     * @param record 背包数据
     */
    public void setRecord(String record) {
        this.record = record == null ? null : record.trim();
    }
	public String getHelpBb() {
		return helpBb;
	}
	public void setHelpBb(String helpBb) {
		this.helpBb = helpBb;
	}
	public String getHelpLing() {
		return helpLing;
	}
	public void setHelpLing(String helpLing) {
		this.helpLing = helpLing;
	}
	public String getSuit1() {
		return suit1;
	}
	public void setSuit1(String suit1) {
		this.suit1 = suit1;
	}
	public String getSuit2() {
		return suit2;
	}
	public void setSuit2(String suit2) {
		this.suit2 = suit2;
	}
	public String getSuit3() {
		return suit3;
	}
	public void setSuit3(String suit3) {
		this.suit3 = suit3;
	}
	public String getSuit4() {
		return suit4;
	}
	public void setSuit4(String suit4) {
		this.suit4 = suit4;
	}
	public String getSuit5() {
		return suit5;
	}
	public void setSuit5(String suit5) {
		this.suit5 = suit5;
	}
	public String getSuit6() {
		return suit6;
	}
	public void setSuit6(String suit6) {
		this.suit6 = suit6;
	}
	public String getSuit7() {
		return suit7;
	}
	public void setSuit7(String suit7) {
		this.suit7 = suit7;
	}
	public String getSuit8() {
		return suit8;
	}
	public void setSuit8(String suit8) {
		this.suit8 = suit8;
	}
	public String getSuit9() {
		return suit9;
	}
	public void setSuit9(String suit9) {
		this.suit9 = suit9;
	}
	public String getSuit10() {
		return suit10;
	}
	public void setSuit10(String suit10) {
		this.suit10 = suit10;
	}
	public String getSuit11() {
		return suit11;
	}
	public void setSuit11(String suit11) {
		this.suit11 = suit11;
	}
	public String getCollect() {
		return collect;
	}
	public void setCollect(String collect) {
		this.collect = collect;
	}
	public int getSuitNum() {
		return suitNum;
	}
	public void setSuitNum(int suitNum) {
		this.suitNum = suitNum;
	}
	public String getTx() {
		return tx;
	}
	public void setTx(String tx) {
		this.tx = tx;
	}
	
}
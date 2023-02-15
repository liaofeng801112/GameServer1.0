package org.come.entity;

public class Rune {
    private Integer runeid;

    private String runename;

    private Integer runeskin;

    private Integer runetype;

    private Short runequality;

    private String runevalue;

    private String runetext;

    public Integer getRuneid() {
        return runeid;
    }

    public void setRuneid(Integer runeid) {
        this.runeid = runeid;
    }

    public String getRunename() {
        return runename;
    }

    public void setRunename(String runename) {
        this.runename = runename == null ? null : runename.trim();
    }

    public Integer getRuneskin() {
        return runeskin;
    }

    public void setRuneskin(Integer runeskin) {
        this.runeskin = runeskin;
    }

    public Integer getRunetype() {
        return runetype;
    }

    public void setRunetype(Integer runetype) {
        this.runetype = runetype;
    }

    public Short getRunequality() {
        return runequality;
    }

    public void setRunequality(Short runequality) {
        this.runequality = runequality;
    }

    public String getRunevalue() {
        return runevalue;
    }

    public void setRunevalue(String runevalue) {
        this.runevalue = runevalue == null ? null : runevalue.trim();
    }

    public String getRunetext() {
        return runetext;
    }

    public void setRunetext(String runetext) {
        this.runetext = runetext == null ? null : runetext.trim();
    }
}
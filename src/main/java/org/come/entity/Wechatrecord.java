package org.come.entity;

import java.math.BigDecimal;

public class Wechatrecord {
    /**
     * null
     */
    private BigDecimal chatId;

    /**
     * null
     */
    private String chatMes;

    /**
     * null
     */
    private BigDecimal chatSendid;

    /**
     * null
     */
    private BigDecimal chatGetid;

    /**
     * null
     */
    private String time;

    /**
     * null
     * @return CHAT_ID null
     */
    public BigDecimal getChatId() {
        return chatId;
    }

    /**
     * null
     * @param chatId null
     */
    public void setChatId(BigDecimal chatId) {
        this.chatId = chatId;
    }

    /**
     * null
     * @return CHAT_MES null
     */
    public String getChatMes() {
        return chatMes;
    }

    /**
     * null
     * @param chatMes null
     */
    public void setChatMes(String chatMes) {
        this.chatMes = chatMes == null ? null : chatMes.trim();
    }

    /**
     * null
     * @return CHAT_SENDID null
     */
    public BigDecimal getChatSendid() {
        return chatSendid;
    }

    /**
     * null
     * @param chatSendid null
     */
    public void setChatSendid(BigDecimal chatSendid) {
        this.chatSendid = chatSendid;
    }

    /**
     * null
     * @return CHAT_GETID null
     */
    public BigDecimal getChatGetid() {
        return chatGetid;
    }

    /**
     * null
     * @param chatGetid null
     */
    public void setChatGetid(BigDecimal chatGetid) {
        this.chatGetid = chatGetid;
    }

    /**
     * null
     * @return TIME null
     */
    public String getTime() {
        return time;
    }

    /**
     * null
     * @param time null
     */
    public void setTime(String time) {
        this.time = time == null ? null : time.trim();
    }
}
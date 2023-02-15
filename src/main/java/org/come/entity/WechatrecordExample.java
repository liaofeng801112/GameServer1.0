package org.come.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class WechatrecordExample {
    /**
     * WECHATRECORD
     */
    protected String orderByClause;

    /**
     * WECHATRECORD
     */
    protected boolean distinct;

    /**
     * WECHATRECORD
     */
    protected List<Criteria> oredCriteria;

    /**
     *
     * @mbggenerated 2019-02-25
     */
    public WechatrecordExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     *
     * @mbggenerated 2019-02-25
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     *
     * @mbggenerated 2019-02-25
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     *
     * @mbggenerated 2019-02-25
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     *
     * @mbggenerated 2019-02-25
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     *
     * @mbggenerated 2019-02-25
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     *
     * @mbggenerated 2019-02-25
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     *
     * @mbggenerated 2019-02-25
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     *
     * @mbggenerated 2019-02-25
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     *
     * @mbggenerated 2019-02-25
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     *
     * @mbggenerated 2019-02-25
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * WECHATRECORD 2019-02-25
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andChatIdIsNull() {
            addCriterion("CHAT_ID is null");
            return (Criteria) this;
        }

        public Criteria andChatIdIsNotNull() {
            addCriterion("CHAT_ID is not null");
            return (Criteria) this;
        }

        public Criteria andChatIdEqualTo(BigDecimal value) {
            addCriterion("CHAT_ID =", value, "chatId");
            return (Criteria) this;
        }

        public Criteria andChatIdNotEqualTo(BigDecimal value) {
            addCriterion("CHAT_ID <>", value, "chatId");
            return (Criteria) this;
        }

        public Criteria andChatIdGreaterThan(BigDecimal value) {
            addCriterion("CHAT_ID >", value, "chatId");
            return (Criteria) this;
        }

        public Criteria andChatIdGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CHAT_ID >=", value, "chatId");
            return (Criteria) this;
        }

        public Criteria andChatIdLessThan(BigDecimal value) {
            addCriterion("CHAT_ID <", value, "chatId");
            return (Criteria) this;
        }

        public Criteria andChatIdLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CHAT_ID <=", value, "chatId");
            return (Criteria) this;
        }

        public Criteria andChatIdIn(List<BigDecimal> values) {
            addCriterion("CHAT_ID in", values, "chatId");
            return (Criteria) this;
        }

        public Criteria andChatIdNotIn(List<BigDecimal> values) {
            addCriterion("CHAT_ID not in", values, "chatId");
            return (Criteria) this;
        }

        public Criteria andChatIdBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CHAT_ID between", value1, value2, "chatId");
            return (Criteria) this;
        }

        public Criteria andChatIdNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CHAT_ID not between", value1, value2, "chatId");
            return (Criteria) this;
        }

        public Criteria andChatMesIsNull() {
            addCriterion("CHAT_MES is null");
            return (Criteria) this;
        }

        public Criteria andChatMesIsNotNull() {
            addCriterion("CHAT_MES is not null");
            return (Criteria) this;
        }

        public Criteria andChatMesEqualTo(String value) {
            addCriterion("CHAT_MES =", value, "chatMes");
            return (Criteria) this;
        }

        public Criteria andChatMesNotEqualTo(String value) {
            addCriterion("CHAT_MES <>", value, "chatMes");
            return (Criteria) this;
        }

        public Criteria andChatMesGreaterThan(String value) {
            addCriterion("CHAT_MES >", value, "chatMes");
            return (Criteria) this;
        }

        public Criteria andChatMesGreaterThanOrEqualTo(String value) {
            addCriterion("CHAT_MES >=", value, "chatMes");
            return (Criteria) this;
        }

        public Criteria andChatMesLessThan(String value) {
            addCriterion("CHAT_MES <", value, "chatMes");
            return (Criteria) this;
        }

        public Criteria andChatMesLessThanOrEqualTo(String value) {
            addCriterion("CHAT_MES <=", value, "chatMes");
            return (Criteria) this;
        }

        public Criteria andChatMesLike(String value) {
            addCriterion("CHAT_MES like", value, "chatMes");
            return (Criteria) this;
        }

        public Criteria andChatMesNotLike(String value) {
            addCriterion("CHAT_MES not like", value, "chatMes");
            return (Criteria) this;
        }

        public Criteria andChatMesIn(List<String> values) {
            addCriterion("CHAT_MES in", values, "chatMes");
            return (Criteria) this;
        }

        public Criteria andChatMesNotIn(List<String> values) {
            addCriterion("CHAT_MES not in", values, "chatMes");
            return (Criteria) this;
        }

        public Criteria andChatMesBetween(String value1, String value2) {
            addCriterion("CHAT_MES between", value1, value2, "chatMes");
            return (Criteria) this;
        }

        public Criteria andChatMesNotBetween(String value1, String value2) {
            addCriterion("CHAT_MES not between", value1, value2, "chatMes");
            return (Criteria) this;
        }

        public Criteria andChatSendidIsNull() {
            addCriterion("CHAT_SENDID is null");
            return (Criteria) this;
        }

        public Criteria andChatSendidIsNotNull() {
            addCriterion("CHAT_SENDID is not null");
            return (Criteria) this;
        }

        public Criteria andChatSendidEqualTo(BigDecimal value) {
            addCriterion("CHAT_SENDID =", value, "chatSendid");
            return (Criteria) this;
        }

        public Criteria andChatSendidNotEqualTo(BigDecimal value) {
            addCriterion("CHAT_SENDID <>", value, "chatSendid");
            return (Criteria) this;
        }

        public Criteria andChatSendidGreaterThan(BigDecimal value) {
            addCriterion("CHAT_SENDID >", value, "chatSendid");
            return (Criteria) this;
        }

        public Criteria andChatSendidGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CHAT_SENDID >=", value, "chatSendid");
            return (Criteria) this;
        }

        public Criteria andChatSendidLessThan(BigDecimal value) {
            addCriterion("CHAT_SENDID <", value, "chatSendid");
            return (Criteria) this;
        }

        public Criteria andChatSendidLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CHAT_SENDID <=", value, "chatSendid");
            return (Criteria) this;
        }

        public Criteria andChatSendidIn(List<BigDecimal> values) {
            addCriterion("CHAT_SENDID in", values, "chatSendid");
            return (Criteria) this;
        }

        public Criteria andChatSendidNotIn(List<BigDecimal> values) {
            addCriterion("CHAT_SENDID not in", values, "chatSendid");
            return (Criteria) this;
        }

        public Criteria andChatSendidBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CHAT_SENDID between", value1, value2, "chatSendid");
            return (Criteria) this;
        }

        public Criteria andChatSendidNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CHAT_SENDID not between", value1, value2, "chatSendid");
            return (Criteria) this;
        }

        public Criteria andChatGetidIsNull() {
            addCriterion("CHAT_GETID is null");
            return (Criteria) this;
        }

        public Criteria andChatGetidIsNotNull() {
            addCriterion("CHAT_GETID is not null");
            return (Criteria) this;
        }

        public Criteria andChatGetidEqualTo(BigDecimal value) {
            addCriterion("CHAT_GETID =", value, "chatGetid");
            return (Criteria) this;
        }

        public Criteria andChatGetidNotEqualTo(BigDecimal value) {
            addCriterion("CHAT_GETID <>", value, "chatGetid");
            return (Criteria) this;
        }

        public Criteria andChatGetidGreaterThan(BigDecimal value) {
            addCriterion("CHAT_GETID >", value, "chatGetid");
            return (Criteria) this;
        }

        public Criteria andChatGetidGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("CHAT_GETID >=", value, "chatGetid");
            return (Criteria) this;
        }

        public Criteria andChatGetidLessThan(BigDecimal value) {
            addCriterion("CHAT_GETID <", value, "chatGetid");
            return (Criteria) this;
        }

        public Criteria andChatGetidLessThanOrEqualTo(BigDecimal value) {
            addCriterion("CHAT_GETID <=", value, "chatGetid");
            return (Criteria) this;
        }

        public Criteria andChatGetidIn(List<BigDecimal> values) {
            addCriterion("CHAT_GETID in", values, "chatGetid");
            return (Criteria) this;
        }

        public Criteria andChatGetidNotIn(List<BigDecimal> values) {
            addCriterion("CHAT_GETID not in", values, "chatGetid");
            return (Criteria) this;
        }

        public Criteria andChatGetidBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CHAT_GETID between", value1, value2, "chatGetid");
            return (Criteria) this;
        }

        public Criteria andChatGetidNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("CHAT_GETID not between", value1, value2, "chatGetid");
            return (Criteria) this;
        }

        public Criteria andTimeIsNull() {
            addCriterion("TIME is null");
            return (Criteria) this;
        }

        public Criteria andTimeIsNotNull() {
            addCriterion("TIME is not null");
            return (Criteria) this;
        }

        public Criteria andTimeEqualTo(String value) {
            addCriterion("TIME =", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotEqualTo(String value) {
            addCriterion("TIME <>", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeGreaterThan(String value) {
            addCriterion("TIME >", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeGreaterThanOrEqualTo(String value) {
            addCriterion("TIME >=", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeLessThan(String value) {
            addCriterion("TIME <", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeLessThanOrEqualTo(String value) {
            addCriterion("TIME <=", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeLike(String value) {
            addCriterion("TIME like", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotLike(String value) {
            addCriterion("TIME not like", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeIn(List<String> values) {
            addCriterion("TIME in", values, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotIn(List<String> values) {
            addCriterion("TIME not in", values, "time");
            return (Criteria) this;
        }

        public Criteria andTimeBetween(String value1, String value2) {
            addCriterion("TIME between", value1, value2, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotBetween(String value1, String value2) {
            addCriterion("TIME not between", value1, value2, "time");
            return (Criteria) this;
        }
    }

    /**
     * WECHATRECORD
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * WECHATRECORD 2019-02-25
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}
package org.come.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MessageExample {
    /**
     * MESSAGE
     */
    protected String orderByClause;

    /**
     * MESSAGE
     */
    protected boolean distinct;

    /**
     * MESSAGE
     */
    protected List<Criteria> oredCriteria;

    /**
     *
     * @mbggenerated 2019-03-05
     */
    public MessageExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     *
     * @mbggenerated 2019-03-05
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     *
     * @mbggenerated 2019-03-05
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     *
     * @mbggenerated 2019-03-05
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     *
     * @mbggenerated 2019-03-05
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     *
     * @mbggenerated 2019-03-05
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     *
     * @mbggenerated 2019-03-05
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     *
     * @mbggenerated 2019-03-05
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     *
     * @mbggenerated 2019-03-05
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
     * @mbggenerated 2019-03-05
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     *
     * @mbggenerated 2019-03-05
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * MESSAGE 2019-03-05
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

        public Criteria andMesidIsNull() {
            addCriterion("MESID is null");
            return (Criteria) this;
        }

        public Criteria andMesidIsNotNull() {
            addCriterion("MESID is not null");
            return (Criteria) this;
        }

        public Criteria andMesidEqualTo(BigDecimal value) {
            addCriterion("MESID =", value, "mesid");
            return (Criteria) this;
        }

        public Criteria andMesidNotEqualTo(BigDecimal value) {
            addCriterion("MESID <>", value, "mesid");
            return (Criteria) this;
        }

        public Criteria andMesidGreaterThan(BigDecimal value) {
            addCriterion("MESID >", value, "mesid");
            return (Criteria) this;
        }

        public Criteria andMesidGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("MESID >=", value, "mesid");
            return (Criteria) this;
        }

        public Criteria andMesidLessThan(BigDecimal value) {
            addCriterion("MESID <", value, "mesid");
            return (Criteria) this;
        }

        public Criteria andMesidLessThanOrEqualTo(BigDecimal value) {
            addCriterion("MESID <=", value, "mesid");
            return (Criteria) this;
        }

        public Criteria andMesidIn(List<BigDecimal> values) {
            addCriterion("MESID in", values, "mesid");
            return (Criteria) this;
        }

        public Criteria andMesidNotIn(List<BigDecimal> values) {
            addCriterion("MESID not in", values, "mesid");
            return (Criteria) this;
        }

        public Criteria andMesidBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MESID between", value1, value2, "mesid");
            return (Criteria) this;
        }

        public Criteria andMesidNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("MESID not between", value1, value2, "mesid");
            return (Criteria) this;
        }

        public Criteria andRoleidIsNull() {
            addCriterion("ROLEID is null");
            return (Criteria) this;
        }

        public Criteria andRoleidIsNotNull() {
            addCriterion("ROLEID is not null");
            return (Criteria) this;
        }

        public Criteria andRoleidEqualTo(BigDecimal value) {
            addCriterion("ROLEID =", value, "roleid");
            return (Criteria) this;
        }

        public Criteria andRoleidNotEqualTo(BigDecimal value) {
            addCriterion("ROLEID <>", value, "roleid");
            return (Criteria) this;
        }

        public Criteria andRoleidGreaterThan(BigDecimal value) {
            addCriterion("ROLEID >", value, "roleid");
            return (Criteria) this;
        }

        public Criteria andRoleidGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ROLEID >=", value, "roleid");
            return (Criteria) this;
        }

        public Criteria andRoleidLessThan(BigDecimal value) {
            addCriterion("ROLEID <", value, "roleid");
            return (Criteria) this;
        }

        public Criteria andRoleidLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ROLEID <=", value, "roleid");
            return (Criteria) this;
        }

        public Criteria andRoleidIn(List<BigDecimal> values) {
            addCriterion("ROLEID in", values, "roleid");
            return (Criteria) this;
        }

        public Criteria andRoleidNotIn(List<BigDecimal> values) {
            addCriterion("ROLEID not in", values, "roleid");
            return (Criteria) this;
        }

        public Criteria andRoleidBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ROLEID between", value1, value2, "roleid");
            return (Criteria) this;
        }

        public Criteria andRoleidNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ROLEID not between", value1, value2, "roleid");
            return (Criteria) this;
        }

        public Criteria andSaleidIsNull() {
            addCriterion("SALEID is null");
            return (Criteria) this;
        }

        public Criteria andSaleidIsNotNull() {
            addCriterion("SALEID is not null");
            return (Criteria) this;
        }

        public Criteria andSaleidEqualTo(BigDecimal value) {
            addCriterion("SALEID =", value, "saleid");
            return (Criteria) this;
        }

        public Criteria andSaleidNotEqualTo(BigDecimal value) {
            addCriterion("SALEID <>", value, "saleid");
            return (Criteria) this;
        }

        public Criteria andSaleidGreaterThan(BigDecimal value) {
            addCriterion("SALEID >", value, "saleid");
            return (Criteria) this;
        }

        public Criteria andSaleidGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SALEID >=", value, "saleid");
            return (Criteria) this;
        }

        public Criteria andSaleidLessThan(BigDecimal value) {
            addCriterion("SALEID <", value, "saleid");
            return (Criteria) this;
        }

        public Criteria andSaleidLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SALEID <=", value, "saleid");
            return (Criteria) this;
        }

        public Criteria andSaleidIn(List<BigDecimal> values) {
            addCriterion("SALEID in", values, "saleid");
            return (Criteria) this;
        }

        public Criteria andSaleidNotIn(List<BigDecimal> values) {
            addCriterion("SALEID not in", values, "saleid");
            return (Criteria) this;
        }

        public Criteria andSaleidBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SALEID between", value1, value2, "saleid");
            return (Criteria) this;
        }

        public Criteria andSaleidNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SALEID not between", value1, value2, "saleid");
            return (Criteria) this;
        }

        public Criteria andMescontentIsNull() {
            addCriterion("MESCONTENT is null");
            return (Criteria) this;
        }

        public Criteria andMescontentIsNotNull() {
            addCriterion("MESCONTENT is not null");
            return (Criteria) this;
        }

        public Criteria andMescontentEqualTo(String value) {
            addCriterion("MESCONTENT =", value, "mescontent");
            return (Criteria) this;
        }

        public Criteria andMescontentNotEqualTo(String value) {
            addCriterion("MESCONTENT <>", value, "mescontent");
            return (Criteria) this;
        }

        public Criteria andMescontentGreaterThan(String value) {
            addCriterion("MESCONTENT >", value, "mescontent");
            return (Criteria) this;
        }

        public Criteria andMescontentGreaterThanOrEqualTo(String value) {
            addCriterion("MESCONTENT >=", value, "mescontent");
            return (Criteria) this;
        }

        public Criteria andMescontentLessThan(String value) {
            addCriterion("MESCONTENT <", value, "mescontent");
            return (Criteria) this;
        }

        public Criteria andMescontentLessThanOrEqualTo(String value) {
            addCriterion("MESCONTENT <=", value, "mescontent");
            return (Criteria) this;
        }

        public Criteria andMescontentLike(String value) {
            addCriterion("MESCONTENT like", value, "mescontent");
            return (Criteria) this;
        }

        public Criteria andMescontentNotLike(String value) {
            addCriterion("MESCONTENT not like", value, "mescontent");
            return (Criteria) this;
        }

        public Criteria andMescontentIn(List<String> values) {
            addCriterion("MESCONTENT in", values, "mescontent");
            return (Criteria) this;
        }

        public Criteria andMescontentNotIn(List<String> values) {
            addCriterion("MESCONTENT not in", values, "mescontent");
            return (Criteria) this;
        }

        public Criteria andMescontentBetween(String value1, String value2) {
            addCriterion("MESCONTENT between", value1, value2, "mescontent");
            return (Criteria) this;
        }

        public Criteria andMescontentNotBetween(String value1, String value2) {
            addCriterion("MESCONTENT not between", value1, value2, "mescontent");
            return (Criteria) this;
        }

        public Criteria andGettimeIsNull() {
            addCriterion("GETTIME is null");
            return (Criteria) this;
        }

        public Criteria andGettimeIsNotNull() {
            addCriterion("GETTIME is not null");
            return (Criteria) this;
        }

        public Criteria andGettimeEqualTo(Date value) {
            addCriterion("GETTIME =", value, "gettime");
            return (Criteria) this;
        }

        public Criteria andGettimeNotEqualTo(Date value) {
            addCriterion("GETTIME <>", value, "gettime");
            return (Criteria) this;
        }

        public Criteria andGettimeGreaterThan(Date value) {
            addCriterion("GETTIME >", value, "gettime");
            return (Criteria) this;
        }

        public Criteria andGettimeGreaterThanOrEqualTo(Date value) {
            addCriterion("GETTIME >=", value, "gettime");
            return (Criteria) this;
        }

        public Criteria andGettimeLessThan(Date value) {
            addCriterion("GETTIME <", value, "gettime");
            return (Criteria) this;
        }

        public Criteria andGettimeLessThanOrEqualTo(Date value) {
            addCriterion("GETTIME <=", value, "gettime");
            return (Criteria) this;
        }

        public Criteria andGettimeIn(List<Date> values) {
            addCriterion("GETTIME in", values, "gettime");
            return (Criteria) this;
        }

        public Criteria andGettimeNotIn(List<Date> values) {
            addCriterion("GETTIME not in", values, "gettime");
            return (Criteria) this;
        }

        public Criteria andGettimeBetween(Date value1, Date value2) {
            addCriterion("GETTIME between", value1, value2, "gettime");
            return (Criteria) this;
        }

        public Criteria andGettimeNotBetween(Date value1, Date value2) {
            addCriterion("GETTIME not between", value1, value2, "gettime");
            return (Criteria) this;
        }
    }

    /**
     * MESSAGE
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * MESSAGE 2019-03-05
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
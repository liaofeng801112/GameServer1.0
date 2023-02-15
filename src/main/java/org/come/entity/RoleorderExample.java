package org.come.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RoleorderExample {
    /**
     * ROLEORDER
     */
    protected String orderByClause;

    /**
     * ROLEORDER
     */
    protected boolean distinct;

    /**
     * ROLEORDER
     */
    protected List<Criteria> oredCriteria;

    /**
     *
     * @mbggenerated 2019-03-06
     */
    public RoleorderExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     *
     * @mbggenerated 2019-03-06
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     *
     * @mbggenerated 2019-03-06
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     *
     * @mbggenerated 2019-03-06
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     *
     * @mbggenerated 2019-03-06
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     *
     * @mbggenerated 2019-03-06
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     *
     * @mbggenerated 2019-03-06
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     *
     * @mbggenerated 2019-03-06
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     *
     * @mbggenerated 2019-03-06
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
     * @mbggenerated 2019-03-06
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     *
     * @mbggenerated 2019-03-06
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * ROLEORDER 2019-03-06
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

        public Criteria andOrderidIsNull() {
            addCriterion("ORDERID is null");
            return (Criteria) this;
        }

        public Criteria andOrderidIsNotNull() {
            addCriterion("ORDERID is not null");
            return (Criteria) this;
        }

        public Criteria andOrderidEqualTo(BigDecimal value) {
            addCriterion("ORDERID =", value, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidNotEqualTo(BigDecimal value) {
            addCriterion("ORDERID <>", value, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidGreaterThan(BigDecimal value) {
            addCriterion("ORDERID >", value, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ORDERID >=", value, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidLessThan(BigDecimal value) {
            addCriterion("ORDERID <", value, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ORDERID <=", value, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidIn(List<BigDecimal> values) {
            addCriterion("ORDERID in", values, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidNotIn(List<BigDecimal> values) {
            addCriterion("ORDERID not in", values, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ORDERID between", value1, value2, "orderid");
            return (Criteria) this;
        }

        public Criteria andOrderidNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ORDERID not between", value1, value2, "orderid");
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

        public Criteria andBuytimeIsNull() {
            addCriterion("BUYTIME is null");
            return (Criteria) this;
        }

        public Criteria andBuytimeIsNotNull() {
            addCriterion("BUYTIME is not null");
            return (Criteria) this;
        }

        public Criteria andBuytimeEqualTo(Date value) {
            addCriterion("BUYTIME =", value, "buytime");
            return (Criteria) this;
        }

        public Criteria andBuytimeNotEqualTo(Date value) {
            addCriterion("BUYTIME <>", value, "buytime");
            return (Criteria) this;
        }

        public Criteria andBuytimeGreaterThan(Date value) {
            addCriterion("BUYTIME >", value, "buytime");
            return (Criteria) this;
        }

        public Criteria andBuytimeGreaterThanOrEqualTo(Date value) {
            addCriterion("BUYTIME >=", value, "buytime");
            return (Criteria) this;
        }

        public Criteria andBuytimeLessThan(Date value) {
            addCriterion("BUYTIME <", value, "buytime");
            return (Criteria) this;
        }

        public Criteria andBuytimeLessThanOrEqualTo(Date value) {
            addCriterion("BUYTIME <=", value, "buytime");
            return (Criteria) this;
        }

        public Criteria andBuytimeIn(List<Date> values) {
            addCriterion("BUYTIME in", values, "buytime");
            return (Criteria) this;
        }

        public Criteria andBuytimeNotIn(List<Date> values) {
            addCriterion("BUYTIME not in", values, "buytime");
            return (Criteria) this;
        }

        public Criteria andBuytimeBetween(Date value1, Date value2) {
            addCriterion("BUYTIME between", value1, value2, "buytime");
            return (Criteria) this;
        }

        public Criteria andBuytimeNotBetween(Date value1, Date value2) {
            addCriterion("BUYTIME not between", value1, value2, "buytime");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("STATUS is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("STATUS is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("STATUS =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("STATUS <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("STATUS >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("STATUS >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("STATUS <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("STATUS <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("STATUS in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("STATUS not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("STATUS between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("STATUS not between", value1, value2, "status");
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

        public Criteria andOrdernumberIsNull() {
            addCriterion("ORDERNUMBER is null");
            return (Criteria) this;
        }

        public Criteria andOrdernumberIsNotNull() {
            addCriterion("ORDERNUMBER is not null");
            return (Criteria) this;
        }

        public Criteria andOrdernumberEqualTo(String value) {
            addCriterion("ORDERNUMBER =", value, "ordernumber");
            return (Criteria) this;
        }

        public Criteria andOrdernumberNotEqualTo(String value) {
            addCriterion("ORDERNUMBER <>", value, "ordernumber");
            return (Criteria) this;
        }

        public Criteria andOrdernumberGreaterThan(String value) {
            addCriterion("ORDERNUMBER >", value, "ordernumber");
            return (Criteria) this;
        }

        public Criteria andOrdernumberGreaterThanOrEqualTo(String value) {
            addCriterion("ORDERNUMBER >=", value, "ordernumber");
            return (Criteria) this;
        }

        public Criteria andOrdernumberLessThan(String value) {
            addCriterion("ORDERNUMBER <", value, "ordernumber");
            return (Criteria) this;
        }

        public Criteria andOrdernumberLessThanOrEqualTo(String value) {
            addCriterion("ORDERNUMBER <=", value, "ordernumber");
            return (Criteria) this;
        }

        public Criteria andOrdernumberLike(String value) {
            addCriterion("ORDERNUMBER like", value, "ordernumber");
            return (Criteria) this;
        }

        public Criteria andOrdernumberNotLike(String value) {
            addCriterion("ORDERNUMBER not like", value, "ordernumber");
            return (Criteria) this;
        }

        public Criteria andOrdernumberIn(List<String> values) {
            addCriterion("ORDERNUMBER in", values, "ordernumber");
            return (Criteria) this;
        }

        public Criteria andOrdernumberNotIn(List<String> values) {
            addCriterion("ORDERNUMBER not in", values, "ordernumber");
            return (Criteria) this;
        }

        public Criteria andOrdernumberBetween(String value1, String value2) {
            addCriterion("ORDERNUMBER between", value1, value2, "ordernumber");
            return (Criteria) this;
        }

        public Criteria andOrdernumberNotBetween(String value1, String value2) {
            addCriterion("ORDERNUMBER not between", value1, value2, "ordernumber");
            return (Criteria) this;
        }
    }

    /**
     * ROLEORDER
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * ROLEORDER 2019-03-06
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
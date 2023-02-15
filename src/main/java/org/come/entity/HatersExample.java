package org.come.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class HatersExample {
    /**
     * HATERS
     */
    protected String orderByClause;

    /**
     * HATERS
     */
    protected boolean distinct;

    /**
     * HATERS
     */
    protected List<Criteria> oredCriteria;

    /**
     *
     * @mbggenerated 2019-01-16
     */
    public HatersExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     *
     * @mbggenerated 2019-01-16
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     *
     * @mbggenerated 2019-01-16
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     *
     * @mbggenerated 2019-01-16
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     *
     * @mbggenerated 2019-01-16
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     *
     * @mbggenerated 2019-01-16
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     *
     * @mbggenerated 2019-01-16
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     *
     * @mbggenerated 2019-01-16
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     *
     * @mbggenerated 2019-01-16
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
     * @mbggenerated 2019-01-16
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     *
     * @mbggenerated 2019-01-16
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * HATERS 2019-01-16
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

        public Criteria andUnknownIsNull() {
            addCriterion("UNKNOWN is null");
            return (Criteria) this;
        }

        public Criteria andUnknownIsNotNull() {
            addCriterion("UNKNOWN is not null");
            return (Criteria) this;
        }

        public Criteria andUnknownEqualTo(String value) {
            addCriterion("UNKNOWN =", value, "unknown");
            return (Criteria) this;
        }

        public Criteria andUnknownNotEqualTo(String value) {
            addCriterion("UNKNOWN <>", value, "unknown");
            return (Criteria) this;
        }

        public Criteria andUnknownGreaterThan(String value) {
            addCriterion("UNKNOWN >", value, "unknown");
            return (Criteria) this;
        }

        public Criteria andUnknownGreaterThanOrEqualTo(String value) {
            addCriterion("UNKNOWN >=", value, "unknown");
            return (Criteria) this;
        }

        public Criteria andUnknownLessThan(String value) {
            addCriterion("UNKNOWN <", value, "unknown");
            return (Criteria) this;
        }

        public Criteria andUnknownLessThanOrEqualTo(String value) {
            addCriterion("UNKNOWN <=", value, "unknown");
            return (Criteria) this;
        }

        public Criteria andUnknownLike(String value) {
            addCriterion("UNKNOWN like", value, "unknown");
            return (Criteria) this;
        }

        public Criteria andUnknownNotLike(String value) {
            addCriterion("UNKNOWN not like", value, "unknown");
            return (Criteria) this;
        }

        public Criteria andUnknownIn(List<String> values) {
            addCriterion("UNKNOWN in", values, "unknown");
            return (Criteria) this;
        }

        public Criteria andUnknownNotIn(List<String> values) {
            addCriterion("UNKNOWN not in", values, "unknown");
            return (Criteria) this;
        }

        public Criteria andUnknownBetween(String value1, String value2) {
            addCriterion("UNKNOWN between", value1, value2, "unknown");
            return (Criteria) this;
        }

        public Criteria andUnknownNotBetween(String value1, String value2) {
            addCriterion("UNKNOWN not between", value1, value2, "unknown");
            return (Criteria) this;
        }
    }

    /**
     * HATERS
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * HATERS 2019-01-16
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
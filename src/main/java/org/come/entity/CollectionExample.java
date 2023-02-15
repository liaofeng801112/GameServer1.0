package org.come.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CollectionExample {
    /**
     * COLLECTION
     */
    protected String orderByClause;

    /**
     * COLLECTION
     */
    protected boolean distinct;

    /**
     * COLLECTION
     */
    protected List<Criteria> oredCriteria;

    /**
     *
     * @mbggenerated 2019-03-05
     */
    public CollectionExample() {
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
     * COLLECTION 2019-03-05
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

        public Criteria andColidIsNull() {
            addCriterion("COLID is null");
            return (Criteria) this;
        }

        public Criteria andColidIsNotNull() {
            addCriterion("COLID is not null");
            return (Criteria) this;
        }

        public Criteria andColidEqualTo(BigDecimal value) {
            addCriterion("COLID =", value, "colid");
            return (Criteria) this;
        }

        public Criteria andColidNotEqualTo(BigDecimal value) {
            addCriterion("COLID <>", value, "colid");
            return (Criteria) this;
        }

        public Criteria andColidGreaterThan(BigDecimal value) {
            addCriterion("COLID >", value, "colid");
            return (Criteria) this;
        }

        public Criteria andColidGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("COLID >=", value, "colid");
            return (Criteria) this;
        }

        public Criteria andColidLessThan(BigDecimal value) {
            addCriterion("COLID <", value, "colid");
            return (Criteria) this;
        }

        public Criteria andColidLessThanOrEqualTo(BigDecimal value) {
            addCriterion("COLID <=", value, "colid");
            return (Criteria) this;
        }

        public Criteria andColidIn(List<BigDecimal> values) {
            addCriterion("COLID in", values, "colid");
            return (Criteria) this;
        }

        public Criteria andColidNotIn(List<BigDecimal> values) {
            addCriterion("COLID not in", values, "colid");
            return (Criteria) this;
        }

        public Criteria andColidBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COLID between", value1, value2, "colid");
            return (Criteria) this;
        }

        public Criteria andColidNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("COLID not between", value1, value2, "colid");
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
    }

    /**
     * COLLECTION
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * COLLECTION 2019-03-05
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
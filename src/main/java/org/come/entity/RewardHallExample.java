package org.come.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RewardHallExample {
    /**
     * REWARD_HALL
     */
    protected String orderByClause;

    /**
     * REWARD_HALL
     */
    protected boolean distinct;

    /**
     * REWARD_HALL
     */
    protected List<Criteria> oredCriteria;

    /**
     *
     * @mbggenerated 2018-10-13
     */
    public RewardHallExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     *
     * @mbggenerated 2018-10-13
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     *
     * @mbggenerated 2018-10-13
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     *
     * @mbggenerated 2018-10-13
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     *
     * @mbggenerated 2018-10-13
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     *
     * @mbggenerated 2018-10-13
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     *
     * @mbggenerated 2018-10-13
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     *
     * @mbggenerated 2018-10-13
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     *
     * @mbggenerated 2018-10-13
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
     * @mbggenerated 2018-10-13
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     *
     * @mbggenerated 2018-10-13
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * REWARD_HALL 2018-10-13
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

        public Criteria andIdIsNull() {
            addCriterion("ID is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("ID is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(BigDecimal value) {
            addCriterion("ID =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(BigDecimal value) {
            addCriterion("ID <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(BigDecimal value) {
            addCriterion("ID >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("ID >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(BigDecimal value) {
            addCriterion("ID <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(BigDecimal value) {
            addCriterion("ID <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<BigDecimal> values) {
            addCriterion("ID in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<BigDecimal> values) {
            addCriterion("ID not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ID between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("ID not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andGoodstableIsNull() {
            addCriterion("Goodstable is null");
            return (Criteria) this;
        }

        public Criteria andGoodstableIsNotNull() {
            addCriterion("Goodstable is not null");
            return (Criteria) this;
        }

        public Criteria andGoodstableEqualTo(String value) {
            addCriterion("Goodstable =", value, "goodstable");
            return (Criteria) this;
        }

        public Criteria andGoodstableNotEqualTo(String value) {
            addCriterion("Goodstable <>", value, "goodstable");
            return (Criteria) this;
        }

        public Criteria andGoodstableGreaterThan(String value) {
            addCriterion("Goodstable >", value, "goodstable");
            return (Criteria) this;
        }

        public Criteria andGoodstableGreaterThanOrEqualTo(String value) {
            addCriterion("Goodstable >=", value, "goodstable");
            return (Criteria) this;
        }

        public Criteria andGoodstableLessThan(String value) {
            addCriterion("Goodstable <", value, "goodstable");
            return (Criteria) this;
        }

        public Criteria andGoodstableLessThanOrEqualTo(String value) {
            addCriterion("Goodstable <=", value, "goodstable");
            return (Criteria) this;
        }

        public Criteria andGoodstableLike(String value) {
            addCriterion("Goodstable like", value, "goodstable");
            return (Criteria) this;
        }

        public Criteria andGoodstableNotLike(String value) {
            addCriterion("Goodstable not like", value, "goodstable");
            return (Criteria) this;
        }

        public Criteria andGoodstableIn(List<String> values) {
            addCriterion("Goodstable in", values, "goodstable");
            return (Criteria) this;
        }

        public Criteria andGoodstableNotIn(List<String> values) {
            addCriterion("Goodstable not in", values, "goodstable");
            return (Criteria) this;
        }

        public Criteria andGoodstableBetween(String value1, String value2) {
            addCriterion("Goodstable between", value1, value2, "goodstable");
            return (Criteria) this;
        }

        public Criteria andGoodstableNotBetween(String value1, String value2) {
            addCriterion("Goodstable not between", value1, value2, "goodstable");
            return (Criteria) this;
        }

        public Criteria andGoodnumIsNull() {
            addCriterion("Goodnum is null");
            return (Criteria) this;
        }

        public Criteria andGoodnumIsNotNull() {
            addCriterion("Goodnum is not null");
            return (Criteria) this;
        }

        public Criteria andGoodnumEqualTo(BigDecimal value) {
            addCriterion("Goodnum =", value, "goodnum");
            return (Criteria) this;
        }

        public Criteria andGoodnumNotEqualTo(BigDecimal value) {
            addCriterion("Goodnum <>", value, "goodnum");
            return (Criteria) this;
        }

        public Criteria andGoodnumGreaterThan(BigDecimal value) {
            addCriterion("Goodnum >", value, "goodnum");
            return (Criteria) this;
        }

        public Criteria andGoodnumGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Goodnum >=", value, "goodnum");
            return (Criteria) this;
        }

        public Criteria andGoodnumLessThan(BigDecimal value) {
            addCriterion("Goodnum <", value, "goodnum");
            return (Criteria) this;
        }

        public Criteria andGoodnumLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Goodnum <=", value, "goodnum");
            return (Criteria) this;
        }

        public Criteria andGoodnumIn(List<BigDecimal> values) {
            addCriterion("Goodnum in", values, "goodnum");
            return (Criteria) this;
        }

        public Criteria andGoodnumNotIn(List<BigDecimal> values) {
            addCriterion("Goodnum not in", values, "goodnum");
            return (Criteria) this;
        }

        public Criteria andGoodnumBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Goodnum between", value1, value2, "goodnum");
            return (Criteria) this;
        }

        public Criteria andGoodnumNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Goodnum not between", value1, value2, "goodnum");
            return (Criteria) this;
        }

        public Criteria andGoodpriceIsNull() {
            addCriterion("Goodprice is null");
            return (Criteria) this;
        }

        public Criteria andGoodpriceIsNotNull() {
            addCriterion("Goodprice is not null");
            return (Criteria) this;
        }

        public Criteria andGoodpriceEqualTo(BigDecimal value) {
            addCriterion("Goodprice =", value, "goodprice");
            return (Criteria) this;
        }

        public Criteria andGoodpriceNotEqualTo(BigDecimal value) {
            addCriterion("Goodprice <>", value, "goodprice");
            return (Criteria) this;
        }

        public Criteria andGoodpriceGreaterThan(BigDecimal value) {
            addCriterion("Goodprice >", value, "goodprice");
            return (Criteria) this;
        }

        public Criteria andGoodpriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Goodprice >=", value, "goodprice");
            return (Criteria) this;
        }

        public Criteria andGoodpriceLessThan(BigDecimal value) {
            addCriterion("Goodprice <", value, "goodprice");
            return (Criteria) this;
        }

        public Criteria andGoodpriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Goodprice <=", value, "goodprice");
            return (Criteria) this;
        }

        public Criteria andGoodpriceIn(List<BigDecimal> values) {
            addCriterion("Goodprice in", values, "goodprice");
            return (Criteria) this;
        }

        public Criteria andGoodpriceNotIn(List<BigDecimal> values) {
            addCriterion("Goodprice not in", values, "goodprice");
            return (Criteria) this;
        }

        public Criteria andGoodpriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Goodprice between", value1, value2, "goodprice");
            return (Criteria) this;
        }

        public Criteria andGoodpriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Goodprice not between", value1, value2, "goodprice");
            return (Criteria) this;
        }

        public Criteria andRoleIdIsNull() {
            addCriterion("Role_ID is null");
            return (Criteria) this;
        }

        public Criteria andRoleIdIsNotNull() {
            addCriterion("Role_ID is not null");
            return (Criteria) this;
        }

        public Criteria andRoleIdEqualTo(BigDecimal value) {
            addCriterion("Role_ID =", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdNotEqualTo(BigDecimal value) {
            addCriterion("Role_ID <>", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdGreaterThan(BigDecimal value) {
            addCriterion("Role_ID >", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Role_ID >=", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdLessThan(BigDecimal value) {
            addCriterion("Role_ID <", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Role_ID <=", value, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdIn(List<BigDecimal> values) {
            addCriterion("Role_ID in", values, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdNotIn(List<BigDecimal> values) {
            addCriterion("Role_ID not in", values, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Role_ID between", value1, value2, "roleId");
            return (Criteria) this;
        }

        public Criteria andRoleIdNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Role_ID not between", value1, value2, "roleId");
            return (Criteria) this;
        }

        public Criteria andThrowtimeIsNull() {
            addCriterion("ThrowTime is null");
            return (Criteria) this;
        }

        public Criteria andThrowtimeIsNotNull() {
            addCriterion("ThrowTime is not null");
            return (Criteria) this;
        }

        public Criteria andThrowtimeEqualTo(Date value) {
            addCriterion("ThrowTime =", value, "throwtime");
            return (Criteria) this;
        }

        public Criteria andThrowtimeNotEqualTo(Date value) {
            addCriterion("ThrowTime <>", value, "throwtime");
            return (Criteria) this;
        }

        public Criteria andThrowtimeGreaterThan(Date value) {
            addCriterion("ThrowTime >", value, "throwtime");
            return (Criteria) this;
        }

        public Criteria andThrowtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("ThrowTime >=", value, "throwtime");
            return (Criteria) this;
        }

        public Criteria andThrowtimeLessThan(Date value) {
            addCriterion("ThrowTime <", value, "throwtime");
            return (Criteria) this;
        }

        public Criteria andThrowtimeLessThanOrEqualTo(Date value) {
            addCriterion("ThrowTime <=", value, "throwtime");
            return (Criteria) this;
        }

        public Criteria andThrowtimeIn(List<Date> values) {
            addCriterion("ThrowTime in", values, "throwtime");
            return (Criteria) this;
        }

        public Criteria andThrowtimeNotIn(List<Date> values) {
            addCriterion("ThrowTime not in", values, "throwtime");
            return (Criteria) this;
        }

        public Criteria andThrowtimeBetween(Date value1, Date value2) {
            addCriterion("ThrowTime between", value1, value2, "throwtime");
            return (Criteria) this;
        }

        public Criteria andThrowtimeNotBetween(Date value1, Date value2) {
            addCriterion("ThrowTime not between", value1, value2, "throwtime");
            return (Criteria) this;
        }
    }

    /**
     * REWARD_HALL
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * REWARD_HALL 2018-10-13
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
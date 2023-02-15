package org.come.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GoodsrecordExample {
    /**
     * GOODSRECORD
     */
    protected String orderByClause;

    /**
     * GOODSRECORD
     */
    protected boolean distinct;

    /**
     * GOODSRECORD
     */
    protected List<Criteria> oredCriteria;

    /**
     *
     * @mbggenerated 2018-09-14
     */
    public GoodsrecordExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     *
     * @mbggenerated 2018-09-14
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     *
     * @mbggenerated 2018-09-14
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     *
     * @mbggenerated 2018-09-14
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     *
     * @mbggenerated 2018-09-14
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     *
     * @mbggenerated 2018-09-14
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     *
     * @mbggenerated 2018-09-14
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     *
     * @mbggenerated 2018-09-14
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     *
     * @mbggenerated 2018-09-14
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
     * @mbggenerated 2018-09-14
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     *
     * @mbggenerated 2018-09-14
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * GOODSRECORD 2018-09-14
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

        public Criteria andGridIsNull() {
            addCriterion("GRID is null");
            return (Criteria) this;
        }

        public Criteria andGridIsNotNull() {
            addCriterion("GRID is not null");
            return (Criteria) this;
        }

        public Criteria andGridEqualTo(Integer value) {
            addCriterion("GRID =", value, "grid");
            return (Criteria) this;
        }

        public Criteria andGridNotEqualTo(Integer value) {
            addCriterion("GRID <>", value, "grid");
            return (Criteria) this;
        }

        public Criteria andGridGreaterThan(Integer value) {
            addCriterion("GRID >", value, "grid");
            return (Criteria) this;
        }

        public Criteria andGridGreaterThanOrEqualTo(Integer value) {
            addCriterion("GRID >=", value, "grid");
            return (Criteria) this;
        }

        public Criteria andGridLessThan(Integer value) {
            addCriterion("GRID <", value, "grid");
            return (Criteria) this;
        }

        public Criteria andGridLessThanOrEqualTo(Integer value) {
            addCriterion("GRID <=", value, "grid");
            return (Criteria) this;
        }

        public Criteria andGridIn(List<Integer> values) {
            addCriterion("GRID in", values, "grid");
            return (Criteria) this;
        }

        public Criteria andGridNotIn(List<Integer> values) {
            addCriterion("GRID not in", values, "grid");
            return (Criteria) this;
        }

        public Criteria andGridBetween(Integer value1, Integer value2) {
            addCriterion("GRID between", value1, value2, "grid");
            return (Criteria) this;
        }

        public Criteria andGridNotBetween(Integer value1, Integer value2) {
            addCriterion("GRID not between", value1, value2, "grid");
            return (Criteria) this;
        }

        public Criteria andRecordtypeIsNull() {
            addCriterion("RECORDTYPE is null");
            return (Criteria) this;
        }

        public Criteria andRecordtypeIsNotNull() {
            addCriterion("RECORDTYPE is not null");
            return (Criteria) this;
        }

        public Criteria andRecordtypeEqualTo(Integer value) {
            addCriterion("RECORDTYPE =", value, "recordtype");
            return (Criteria) this;
        }

        public Criteria andRecordtypeNotEqualTo(Integer value) {
            addCriterion("RECORDTYPE <>", value, "recordtype");
            return (Criteria) this;
        }

        public Criteria andRecordtypeGreaterThan(Integer value) {
            addCriterion("RECORDTYPE >", value, "recordtype");
            return (Criteria) this;
        }

        public Criteria andRecordtypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("RECORDTYPE >=", value, "recordtype");
            return (Criteria) this;
        }

        public Criteria andRecordtypeLessThan(Integer value) {
            addCriterion("RECORDTYPE <", value, "recordtype");
            return (Criteria) this;
        }

        public Criteria andRecordtypeLessThanOrEqualTo(Integer value) {
            addCriterion("RECORDTYPE <=", value, "recordtype");
            return (Criteria) this;
        }

        public Criteria andRecordtypeIn(List<Integer> values) {
            addCriterion("RECORDTYPE in", values, "recordtype");
            return (Criteria) this;
        }

        public Criteria andRecordtypeNotIn(List<Integer> values) {
            addCriterion("RECORDTYPE not in", values, "recordtype");
            return (Criteria) this;
        }

        public Criteria andRecordtypeBetween(Integer value1, Integer value2) {
            addCriterion("RECORDTYPE between", value1, value2, "recordtype");
            return (Criteria) this;
        }

        public Criteria andRecordtypeNotBetween(Integer value1, Integer value2) {
            addCriterion("RECORDTYPE not between", value1, value2, "recordtype");
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

        public Criteria andOtherroleIsNull() {
            addCriterion("OTHERROLE is null");
            return (Criteria) this;
        }

        public Criteria andOtherroleIsNotNull() {
            addCriterion("OTHERROLE is not null");
            return (Criteria) this;
        }

        public Criteria andOtherroleEqualTo(BigDecimal value) {
            addCriterion("OTHERROLE =", value, "otherrole");
            return (Criteria) this;
        }

        public Criteria andOtherroleNotEqualTo(BigDecimal value) {
            addCriterion("OTHERROLE <>", value, "otherrole");
            return (Criteria) this;
        }

        public Criteria andOtherroleGreaterThan(BigDecimal value) {
            addCriterion("OTHERROLE >", value, "otherrole");
            return (Criteria) this;
        }

        public Criteria andOtherroleGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("OTHERROLE >=", value, "otherrole");
            return (Criteria) this;
        }

        public Criteria andOtherroleLessThan(BigDecimal value) {
            addCriterion("OTHERROLE <", value, "otherrole");
            return (Criteria) this;
        }

        public Criteria andOtherroleLessThanOrEqualTo(BigDecimal value) {
            addCriterion("OTHERROLE <=", value, "otherrole");
            return (Criteria) this;
        }

        public Criteria andOtherroleIn(List<BigDecimal> values) {
            addCriterion("OTHERROLE in", values, "otherrole");
            return (Criteria) this;
        }

        public Criteria andOtherroleNotIn(List<BigDecimal> values) {
            addCriterion("OTHERROLE not in", values, "otherrole");
            return (Criteria) this;
        }

        public Criteria andOtherroleBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("OTHERROLE between", value1, value2, "otherrole");
            return (Criteria) this;
        }

        public Criteria andOtherroleNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("OTHERROLE not between", value1, value2, "otherrole");
            return (Criteria) this;
        }

        public Criteria andGoodsIsNull() {
            addCriterion("GOODS is null");
            return (Criteria) this;
        }

        public Criteria andGoodsIsNotNull() {
            addCriterion("GOODS is not null");
            return (Criteria) this;
        }

        public Criteria andGoodsEqualTo(String value) {
            addCriterion("GOODS =", value, "goods");
            return (Criteria) this;
        }

        public Criteria andGoodsNotEqualTo(String value) {
            addCriterion("GOODS <>", value, "goods");
            return (Criteria) this;
        }

        public Criteria andGoodsGreaterThan(String value) {
            addCriterion("GOODS >", value, "goods");
            return (Criteria) this;
        }

        public Criteria andGoodsGreaterThanOrEqualTo(String value) {
            addCriterion("GOODS >=", value, "goods");
            return (Criteria) this;
        }

        public Criteria andGoodsLessThan(String value) {
            addCriterion("GOODS <", value, "goods");
            return (Criteria) this;
        }

        public Criteria andGoodsLessThanOrEqualTo(String value) {
            addCriterion("GOODS <=", value, "goods");
            return (Criteria) this;
        }

        public Criteria andGoodsLike(String value) {
            addCriterion("GOODS like", value, "goods");
            return (Criteria) this;
        }

        public Criteria andGoodsNotLike(String value) {
            addCriterion("GOODS not like", value, "goods");
            return (Criteria) this;
        }

        public Criteria andGoodsIn(List<String> values) {
            addCriterion("GOODS in", values, "goods");
            return (Criteria) this;
        }

        public Criteria andGoodsNotIn(List<String> values) {
            addCriterion("GOODS not in", values, "goods");
            return (Criteria) this;
        }

        public Criteria andGoodsBetween(String value1, String value2) {
            addCriterion("GOODS between", value1, value2, "goods");
            return (Criteria) this;
        }

        public Criteria andGoodsNotBetween(String value1, String value2) {
            addCriterion("GOODS not between", value1, value2, "goods");
            return (Criteria) this;
        }

        public Criteria andRecordtimeIsNull() {
            addCriterion("RECORDTIME is null");
            return (Criteria) this;
        }

        public Criteria andRecordtimeIsNotNull() {
            addCriterion("RECORDTIME is not null");
            return (Criteria) this;
        }

        public Criteria andRecordtimeEqualTo(Date value) {
            addCriterion("RECORDTIME =", value, "recordtime");
            return (Criteria) this;
        }

        public Criteria andRecordtimeNotEqualTo(Date value) {
            addCriterion("RECORDTIME <>", value, "recordtime");
            return (Criteria) this;
        }

        public Criteria andRecordtimeGreaterThan(Date value) {
            addCriterion("RECORDTIME >", value, "recordtime");
            return (Criteria) this;
        }

        public Criteria andRecordtimeGreaterThanOrEqualTo(Date value) {
            addCriterion("RECORDTIME >=", value, "recordtime");
            return (Criteria) this;
        }

        public Criteria andRecordtimeLessThan(Date value) {
            addCriterion("RECORDTIME <", value, "recordtime");
            return (Criteria) this;
        }

        public Criteria andRecordtimeLessThanOrEqualTo(Date value) {
            addCriterion("RECORDTIME <=", value, "recordtime");
            return (Criteria) this;
        }

        public Criteria andRecordtimeIn(List<Date> values) {
            addCriterion("RECORDTIME in", values, "recordtime");
            return (Criteria) this;
        }

        public Criteria andRecordtimeNotIn(List<Date> values) {
            addCriterion("RECORDTIME not in", values, "recordtime");
            return (Criteria) this;
        }

        public Criteria andRecordtimeBetween(Date value1, Date value2) {
            addCriterion("RECORDTIME between", value1, value2, "recordtime");
            return (Criteria) this;
        }

        public Criteria andRecordtimeNotBetween(Date value1, Date value2) {
            addCriterion("RECORDTIME not between", value1, value2, "recordtime");
            return (Criteria) this;
        }

        public Criteria andGoodsnumIsNull() {
            addCriterion("GOODSNUM is null");
            return (Criteria) this;
        }

        public Criteria andGoodsnumIsNotNull() {
            addCriterion("GOODSNUM is not null");
            return (Criteria) this;
        }

        public Criteria andGoodsnumEqualTo(Integer value) {
            addCriterion("GOODSNUM =", value, "goodsnum");
            return (Criteria) this;
        }

        public Criteria andGoodsnumNotEqualTo(Integer value) {
            addCriterion("GOODSNUM <>", value, "goodsnum");
            return (Criteria) this;
        }

        public Criteria andGoodsnumGreaterThan(Integer value) {
            addCriterion("GOODSNUM >", value, "goodsnum");
            return (Criteria) this;
        }

        public Criteria andGoodsnumGreaterThanOrEqualTo(Integer value) {
            addCriterion("GOODSNUM >=", value, "goodsnum");
            return (Criteria) this;
        }

        public Criteria andGoodsnumLessThan(Integer value) {
            addCriterion("GOODSNUM <", value, "goodsnum");
            return (Criteria) this;
        }

        public Criteria andGoodsnumLessThanOrEqualTo(Integer value) {
            addCriterion("GOODSNUM <=", value, "goodsnum");
            return (Criteria) this;
        }

        public Criteria andGoodsnumIn(List<Integer> values) {
            addCriterion("GOODSNUM in", values, "goodsnum");
            return (Criteria) this;
        }

        public Criteria andGoodsnumNotIn(List<Integer> values) {
            addCriterion("GOODSNUM not in", values, "goodsnum");
            return (Criteria) this;
        }

        public Criteria andGoodsnumBetween(Integer value1, Integer value2) {
            addCriterion("GOODSNUM between", value1, value2, "goodsnum");
            return (Criteria) this;
        }

        public Criteria andGoodsnumNotBetween(Integer value1, Integer value2) {
            addCriterion("GOODSNUM not between", value1, value2, "goodsnum");
            return (Criteria) this;
        }
    }

    /**
     * GOODSRECORD
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * GOODSRECORD 2018-09-14
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
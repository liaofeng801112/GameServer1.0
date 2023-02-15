package org.come.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SalegoodsExample {
    /**
     * SALEGOODS
     */
    protected String orderByClause;

    /**
     * SALEGOODS
     */
    protected boolean distinct;

    /**
     * SALEGOODS
     */
    protected List<Criteria> oredCriteria;

    /**
     *
     * @mbggenerated 2019-03-18
     */
    public SalegoodsExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     *
     * @mbggenerated 2019-03-18
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     *
     * @mbggenerated 2019-03-18
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     *
     * @mbggenerated 2019-03-18
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     *
     * @mbggenerated 2019-03-18
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     *
     * @mbggenerated 2019-03-18
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     *
     * @mbggenerated 2019-03-18
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     *
     * @mbggenerated 2019-03-18
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     *
     * @mbggenerated 2019-03-18
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
     * @mbggenerated 2019-03-18
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     *
     * @mbggenerated 2019-03-18
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * SALEGOODS 2019-03-18
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

        public Criteria andSalenameIsNull() {
            addCriterion("SALENAME is null");
            return (Criteria) this;
        }

        public Criteria andSalenameIsNotNull() {
            addCriterion("SALENAME is not null");
            return (Criteria) this;
        }

        public Criteria andSalenameEqualTo(String value) {
            addCriterion("SALENAME =", value, "salename");
            return (Criteria) this;
        }

        public Criteria andSalenameNotEqualTo(String value) {
            addCriterion("SALENAME <>", value, "salename");
            return (Criteria) this;
        }

        public Criteria andSalenameGreaterThan(String value) {
            addCriterion("SALENAME >", value, "salename");
            return (Criteria) this;
        }

        public Criteria andSalenameGreaterThanOrEqualTo(String value) {
            addCriterion("SALENAME >=", value, "salename");
            return (Criteria) this;
        }

        public Criteria andSalenameLessThan(String value) {
            addCriterion("SALENAME <", value, "salename");
            return (Criteria) this;
        }

        public Criteria andSalenameLessThanOrEqualTo(String value) {
            addCriterion("SALENAME <=", value, "salename");
            return (Criteria) this;
        }

        public Criteria andSalenameLike(String value) {
            addCriterion("SALENAME like", value, "salename");
            return (Criteria) this;
        }

        public Criteria andSalenameNotLike(String value) {
            addCriterion("SALENAME not like", value, "salename");
            return (Criteria) this;
        }

        public Criteria andSalenameIn(List<String> values) {
            addCriterion("SALENAME in", values, "salename");
            return (Criteria) this;
        }

        public Criteria andSalenameNotIn(List<String> values) {
            addCriterion("SALENAME not in", values, "salename");
            return (Criteria) this;
        }

        public Criteria andSalenameBetween(String value1, String value2) {
            addCriterion("SALENAME between", value1, value2, "salename");
            return (Criteria) this;
        }

        public Criteria andSalenameNotBetween(String value1, String value2) {
            addCriterion("SALENAME not between", value1, value2, "salename");
            return (Criteria) this;
        }

        public Criteria andSaletypeIsNull() {
            addCriterion("SALETYPE is null");
            return (Criteria) this;
        }

        public Criteria andSaletypeIsNotNull() {
            addCriterion("SALETYPE is not null");
            return (Criteria) this;
        }

        public Criteria andSaletypeEqualTo(Integer value) {
            addCriterion("SALETYPE =", value, "saletype");
            return (Criteria) this;
        }

        public Criteria andSaletypeNotEqualTo(Integer value) {
            addCriterion("SALETYPE <>", value, "saletype");
            return (Criteria) this;
        }

        public Criteria andSaletypeGreaterThan(Integer value) {
            addCriterion("SALETYPE >", value, "saletype");
            return (Criteria) this;
        }

        public Criteria andSaletypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("SALETYPE >=", value, "saletype");
            return (Criteria) this;
        }

        public Criteria andSaletypeLessThan(Integer value) {
            addCriterion("SALETYPE <", value, "saletype");
            return (Criteria) this;
        }

        public Criteria andSaletypeLessThanOrEqualTo(Integer value) {
            addCriterion("SALETYPE <=", value, "saletype");
            return (Criteria) this;
        }

        public Criteria andSaletypeIn(List<Integer> values) {
            addCriterion("SALETYPE in", values, "saletype");
            return (Criteria) this;
        }

        public Criteria andSaletypeNotIn(List<Integer> values) {
            addCriterion("SALETYPE not in", values, "saletype");
            return (Criteria) this;
        }

        public Criteria andSaletypeBetween(Integer value1, Integer value2) {
            addCriterion("SALETYPE between", value1, value2, "saletype");
            return (Criteria) this;
        }

        public Criteria andSaletypeNotBetween(Integer value1, Integer value2) {
            addCriterion("SALETYPE not between", value1, value2, "saletype");
            return (Criteria) this;
        }

        public Criteria andOtheridIsNull() {
            addCriterion("OTHERID is null");
            return (Criteria) this;
        }

        public Criteria andOtheridIsNotNull() {
            addCriterion("OTHERID is not null");
            return (Criteria) this;
        }

        public Criteria andOtheridEqualTo(BigDecimal value) {
            addCriterion("OTHERID =", value, "otherid");
            return (Criteria) this;
        }

        public Criteria andOtheridNotEqualTo(BigDecimal value) {
            addCriterion("OTHERID <>", value, "otherid");
            return (Criteria) this;
        }

        public Criteria andOtheridGreaterThan(BigDecimal value) {
            addCriterion("OTHERID >", value, "otherid");
            return (Criteria) this;
        }

        public Criteria andOtheridGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("OTHERID >=", value, "otherid");
            return (Criteria) this;
        }

        public Criteria andOtheridLessThan(BigDecimal value) {
            addCriterion("OTHERID <", value, "otherid");
            return (Criteria) this;
        }

        public Criteria andOtheridLessThanOrEqualTo(BigDecimal value) {
            addCriterion("OTHERID <=", value, "otherid");
            return (Criteria) this;
        }

        public Criteria andOtheridIn(List<BigDecimal> values) {
            addCriterion("OTHERID in", values, "otherid");
            return (Criteria) this;
        }

        public Criteria andOtheridNotIn(List<BigDecimal> values) {
            addCriterion("OTHERID not in", values, "otherid");
            return (Criteria) this;
        }

        public Criteria andOtheridBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("OTHERID between", value1, value2, "otherid");
            return (Criteria) this;
        }

        public Criteria andOtheridNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("OTHERID not between", value1, value2, "otherid");
            return (Criteria) this;
        }

        public Criteria andContiontypeIsNull() {
            addCriterion("CONTIONTYPE is null");
            return (Criteria) this;
        }

        public Criteria andContiontypeIsNotNull() {
            addCriterion("CONTIONTYPE is not null");
            return (Criteria) this;
        }

        public Criteria andContiontypeEqualTo(String value) {
            addCriterion("CONTIONTYPE =", value, "contiontype");
            return (Criteria) this;
        }

        public Criteria andContiontypeNotEqualTo(String value) {
            addCriterion("CONTIONTYPE <>", value, "contiontype");
            return (Criteria) this;
        }

        public Criteria andContiontypeGreaterThan(String value) {
            addCriterion("CONTIONTYPE >", value, "contiontype");
            return (Criteria) this;
        }

        public Criteria andContiontypeGreaterThanOrEqualTo(String value) {
            addCriterion("CONTIONTYPE >=", value, "contiontype");
            return (Criteria) this;
        }

        public Criteria andContiontypeLessThan(String value) {
            addCriterion("CONTIONTYPE <", value, "contiontype");
            return (Criteria) this;
        }

        public Criteria andContiontypeLessThanOrEqualTo(String value) {
            addCriterion("CONTIONTYPE <=", value, "contiontype");
            return (Criteria) this;
        }

        public Criteria andContiontypeLike(String value) {
            addCriterion("CONTIONTYPE like", value, "contiontype");
            return (Criteria) this;
        }

        public Criteria andContiontypeNotLike(String value) {
            addCriterion("CONTIONTYPE not like", value, "contiontype");
            return (Criteria) this;
        }

        public Criteria andContiontypeIn(List<String> values) {
            addCriterion("CONTIONTYPE in", values, "contiontype");
            return (Criteria) this;
        }

        public Criteria andContiontypeNotIn(List<String> values) {
            addCriterion("CONTIONTYPE not in", values, "contiontype");
            return (Criteria) this;
        }

        public Criteria andContiontypeBetween(String value1, String value2) {
            addCriterion("CONTIONTYPE between", value1, value2, "contiontype");
            return (Criteria) this;
        }

        public Criteria andContiontypeNotBetween(String value1, String value2) {
            addCriterion("CONTIONTYPE not between", value1, value2, "contiontype");
            return (Criteria) this;
        }

        public Criteria andFlagIsNull() {
            addCriterion("FLAG is null");
            return (Criteria) this;
        }

        public Criteria andFlagIsNotNull() {
            addCriterion("FLAG is not null");
            return (Criteria) this;
        }

        public Criteria andFlagEqualTo(Integer value) {
            addCriterion("FLAG =", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotEqualTo(Integer value) {
            addCriterion("FLAG <>", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagGreaterThan(Integer value) {
            addCriterion("FLAG >", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagGreaterThanOrEqualTo(Integer value) {
            addCriterion("FLAG >=", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagLessThan(Integer value) {
            addCriterion("FLAG <", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagLessThanOrEqualTo(Integer value) {
            addCriterion("FLAG <=", value, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagIn(List<Integer> values) {
            addCriterion("FLAG in", values, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotIn(List<Integer> values) {
            addCriterion("FLAG not in", values, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagBetween(Integer value1, Integer value2) {
            addCriterion("FLAG between", value1, value2, "flag");
            return (Criteria) this;
        }

        public Criteria andFlagNotBetween(Integer value1, Integer value2) {
            addCriterion("FLAG not between", value1, value2, "flag");
            return (Criteria) this;
        }

        public Criteria andUptimeIsNull() {
            addCriterion("UPTIME is null");
            return (Criteria) this;
        }

        public Criteria andUptimeIsNotNull() {
            addCriterion("UPTIME is not null");
            return (Criteria) this;
        }

        public Criteria andUptimeEqualTo(Date value) {
            addCriterion("UPTIME =", value, "uptime");
            return (Criteria) this;
        }

        public Criteria andUptimeNotEqualTo(Date value) {
            addCriterion("UPTIME <>", value, "uptime");
            return (Criteria) this;
        }

        public Criteria andUptimeGreaterThan(Date value) {
            addCriterion("UPTIME >", value, "uptime");
            return (Criteria) this;
        }

        public Criteria andUptimeGreaterThanOrEqualTo(Date value) {
            addCriterion("UPTIME >=", value, "uptime");
            return (Criteria) this;
        }

        public Criteria andUptimeLessThan(Date value) {
            addCriterion("UPTIME <", value, "uptime");
            return (Criteria) this;
        }

        public Criteria andUptimeLessThanOrEqualTo(Date value) {
            addCriterion("UPTIME <=", value, "uptime");
            return (Criteria) this;
        }

        public Criteria andUptimeIn(List<Date> values) {
            addCriterion("UPTIME in", values, "uptime");
            return (Criteria) this;
        }

        public Criteria andUptimeNotIn(List<Date> values) {
            addCriterion("UPTIME not in", values, "uptime");
            return (Criteria) this;
        }

        public Criteria andUptimeBetween(Date value1, Date value2) {
            addCriterion("UPTIME between", value1, value2, "uptime");
            return (Criteria) this;
        }

        public Criteria andUptimeNotBetween(Date value1, Date value2) {
            addCriterion("UPTIME not between", value1, value2, "uptime");
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

        public Criteria andBuyroleIsNull() {
            addCriterion("BUYROLE is null");
            return (Criteria) this;
        }

        public Criteria andBuyroleIsNotNull() {
            addCriterion("BUYROLE is not null");
            return (Criteria) this;
        }

        public Criteria andBuyroleEqualTo(BigDecimal value) {
            addCriterion("BUYROLE =", value, "buyrole");
            return (Criteria) this;
        }

        public Criteria andBuyroleNotEqualTo(BigDecimal value) {
            addCriterion("BUYROLE <>", value, "buyrole");
            return (Criteria) this;
        }

        public Criteria andBuyroleGreaterThan(BigDecimal value) {
            addCriterion("BUYROLE >", value, "buyrole");
            return (Criteria) this;
        }

        public Criteria andBuyroleGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("BUYROLE >=", value, "buyrole");
            return (Criteria) this;
        }

        public Criteria andBuyroleLessThan(BigDecimal value) {
            addCriterion("BUYROLE <", value, "buyrole");
            return (Criteria) this;
        }

        public Criteria andBuyroleLessThanOrEqualTo(BigDecimal value) {
            addCriterion("BUYROLE <=", value, "buyrole");
            return (Criteria) this;
        }

        public Criteria andBuyroleIn(List<BigDecimal> values) {
            addCriterion("BUYROLE in", values, "buyrole");
            return (Criteria) this;
        }

        public Criteria andBuyroleNotIn(List<BigDecimal> values) {
            addCriterion("BUYROLE not in", values, "buyrole");
            return (Criteria) this;
        }

        public Criteria andBuyroleBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BUYROLE between", value1, value2, "buyrole");
            return (Criteria) this;
        }

        public Criteria andBuyroleNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("BUYROLE not between", value1, value2, "buyrole");
            return (Criteria) this;
        }

        public Criteria andSalepriceIsNull() {
            addCriterion("SALEPRICE is null");
            return (Criteria) this;
        }

        public Criteria andSalepriceIsNotNull() {
            addCriterion("SALEPRICE is not null");
            return (Criteria) this;
        }

        public Criteria andSalepriceEqualTo(BigDecimal value) {
            addCriterion("SALEPRICE =", value, "saleprice");
            return (Criteria) this;
        }

        public Criteria andSalepriceNotEqualTo(BigDecimal value) {
            addCriterion("SALEPRICE <>", value, "saleprice");
            return (Criteria) this;
        }

        public Criteria andSalepriceGreaterThan(BigDecimal value) {
            addCriterion("SALEPRICE >", value, "saleprice");
            return (Criteria) this;
        }

        public Criteria andSalepriceGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("SALEPRICE >=", value, "saleprice");
            return (Criteria) this;
        }

        public Criteria andSalepriceLessThan(BigDecimal value) {
            addCriterion("SALEPRICE <", value, "saleprice");
            return (Criteria) this;
        }

        public Criteria andSalepriceLessThanOrEqualTo(BigDecimal value) {
            addCriterion("SALEPRICE <=", value, "saleprice");
            return (Criteria) this;
        }

        public Criteria andSalepriceIn(List<BigDecimal> values) {
            addCriterion("SALEPRICE in", values, "saleprice");
            return (Criteria) this;
        }

        public Criteria andSalepriceNotIn(List<BigDecimal> values) {
            addCriterion("SALEPRICE not in", values, "saleprice");
            return (Criteria) this;
        }

        public Criteria andSalepriceBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SALEPRICE between", value1, value2, "saleprice");
            return (Criteria) this;
        }

        public Criteria andSalepriceNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("SALEPRICE not between", value1, value2, "saleprice");
            return (Criteria) this;
        }

        public Criteria andSaleskinIsNull() {
            addCriterion("SALESKIN is null");
            return (Criteria) this;
        }

        public Criteria andSaleskinIsNotNull() {
            addCriterion("SALESKIN is not null");
            return (Criteria) this;
        }

        public Criteria andSaleskinEqualTo(String value) {
            addCriterion("SALESKIN =", value, "saleskin");
            return (Criteria) this;
        }

        public Criteria andSaleskinNotEqualTo(String value) {
            addCriterion("SALESKIN <>", value, "saleskin");
            return (Criteria) this;
        }

        public Criteria andSaleskinGreaterThan(String value) {
            addCriterion("SALESKIN >", value, "saleskin");
            return (Criteria) this;
        }

        public Criteria andSaleskinGreaterThanOrEqualTo(String value) {
            addCriterion("SALESKIN >=", value, "saleskin");
            return (Criteria) this;
        }

        public Criteria andSaleskinLessThan(String value) {
            addCriterion("SALESKIN <", value, "saleskin");
            return (Criteria) this;
        }

        public Criteria andSaleskinLessThanOrEqualTo(String value) {
            addCriterion("SALESKIN <=", value, "saleskin");
            return (Criteria) this;
        }

        public Criteria andSaleskinLike(String value) {
            addCriterion("SALESKIN like", value, "saleskin");
            return (Criteria) this;
        }

        public Criteria andSaleskinNotLike(String value) {
            addCriterion("SALESKIN not like", value, "saleskin");
            return (Criteria) this;
        }

        public Criteria andSaleskinIn(List<String> values) {
            addCriterion("SALESKIN in", values, "saleskin");
            return (Criteria) this;
        }

        public Criteria andSaleskinNotIn(List<String> values) {
            addCriterion("SALESKIN not in", values, "saleskin");
            return (Criteria) this;
        }

        public Criteria andSaleskinBetween(String value1, String value2) {
            addCriterion("SALESKIN between", value1, value2, "saleskin");
            return (Criteria) this;
        }

        public Criteria andSaleskinNotBetween(String value1, String value2) {
            addCriterion("SALESKIN not between", value1, value2, "saleskin");
            return (Criteria) this;
        }
    }

    /**
     * SALEGOODS
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * SALEGOODS 2019-03-18
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
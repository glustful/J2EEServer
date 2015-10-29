package com.dunbian.jujiabao.appobj.generator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FoodWeekExample {
	/*生成分页所需元素开始*/
	private int startRecord = -1;

	private int pageCount = -1;

	public void setStartRecord(int startRecord){
	   this.startRecord = startRecord;
	}

	public int getStartRecord(){
	   return startRecord;
	}

	public void setPageCount(int pageCount){
	   this.pageCount = pageCount;
	}

	public int getPageCount(){
	   return pageCount;
	}

	/*生成分页所需元素结束*/
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_food_week
     *
     * @mbggenerated Tue Mar 31 10:43:03 CST 2015
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_food_week
     *
     * @mbggenerated Tue Mar 31 10:43:03 CST 2015
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_food_week
     *
     * @mbggenerated Tue Mar 31 10:43:03 CST 2015
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_food_week
     *
     * @mbggenerated Tue Mar 31 10:43:03 CST 2015
     */
    public FoodWeekExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_food_week
     *
     * @mbggenerated Tue Mar 31 10:43:03 CST 2015
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_food_week
     *
     * @mbggenerated Tue Mar 31 10:43:03 CST 2015
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_food_week
     *
     * @mbggenerated Tue Mar 31 10:43:03 CST 2015
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_food_week
     *
     * @mbggenerated Tue Mar 31 10:43:03 CST 2015
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_food_week
     *
     * @mbggenerated Tue Mar 31 10:43:03 CST 2015
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_food_week
     *
     * @mbggenerated Tue Mar 31 10:43:03 CST 2015
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_food_week
     *
     * @mbggenerated Tue Mar 31 10:43:03 CST 2015
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_food_week
     *
     * @mbggenerated Tue Mar 31 10:43:03 CST 2015
     */
    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_food_week
     *
     * @mbggenerated Tue Mar 31 10:43:03 CST 2015
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_food_week
     *
     * @mbggenerated Tue Mar 31 10:43:03 CST 2015
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_food_week
     *
     * @mbggenerated Tue Mar 31 10:43:03 CST 2015
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
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andDayIsNull() {
            addCriterion("day is null");
            return (Criteria) this;
        }

        public Criteria andDayIsNotNull() {
            addCriterion("day is not null");
            return (Criteria) this;
        }

        public Criteria andDayEqualTo(String value) {
            addCriterion("day =", value, "day");
            return (Criteria) this;
        }

        public Criteria andDayNotEqualTo(String value) {
            addCriterion("day <>", value, "day");
            return (Criteria) this;
        }

        public Criteria andDayGreaterThan(String value) {
            addCriterion("day >", value, "day");
            return (Criteria) this;
        }

        public Criteria andDayGreaterThanOrEqualTo(String value) {
            addCriterion("day >=", value, "day");
            return (Criteria) this;
        }

        public Criteria andDayLessThan(String value) {
            addCriterion("day <", value, "day");
            return (Criteria) this;
        }

        public Criteria andDayLessThanOrEqualTo(String value) {
            addCriterion("day <=", value, "day");
            return (Criteria) this;
        }

        public Criteria andDayLike(String value) {
            addCriterion("day like", value, "day");
            return (Criteria) this;
        }

        public Criteria andDayNotLike(String value) {
            addCriterion("day not like", value, "day");
            return (Criteria) this;
        }

        public Criteria andDayIn(List<String> values) {
            addCriterion("day in", values, "day");
            return (Criteria) this;
        }

        public Criteria andDayNotIn(List<String> values) {
            addCriterion("day not in", values, "day");
            return (Criteria) this;
        }

        public Criteria andDayBetween(String value1, String value2) {
            addCriterion("day between", value1, value2, "day");
            return (Criteria) this;
        }

        public Criteria andDayNotBetween(String value1, String value2) {
            addCriterion("day not between", value1, value2, "day");
            return (Criteria) this;
        }

        public Criteria andSetIdIsNull() {
            addCriterion("set_id is null");
            return (Criteria) this;
        }

        public Criteria andSetIdIsNotNull() {
            addCriterion("set_id is not null");
            return (Criteria) this;
        }

        public Criteria andSetIdEqualTo(String value) {
            addCriterion("set_id =", value, "setId");
            return (Criteria) this;
        }

        public Criteria andSetIdNotEqualTo(String value) {
            addCriterion("set_id <>", value, "setId");
            return (Criteria) this;
        }

        public Criteria andSetIdGreaterThan(String value) {
            addCriterion("set_id >", value, "setId");
            return (Criteria) this;
        }

        public Criteria andSetIdGreaterThanOrEqualTo(String value) {
            addCriterion("set_id >=", value, "setId");
            return (Criteria) this;
        }

        public Criteria andSetIdLessThan(String value) {
            addCriterion("set_id <", value, "setId");
            return (Criteria) this;
        }

        public Criteria andSetIdLessThanOrEqualTo(String value) {
            addCriterion("set_id <=", value, "setId");
            return (Criteria) this;
        }

        public Criteria andSetIdIn(List<String> values) {
            addCriterion("set_id in", values, "setId");
            return (Criteria) this;
        }

        public Criteria andSetIdNotIn(List<String> values) {
            addCriterion("set_id not in", values, "setId");
            return (Criteria) this;
        }

        public Criteria andSetIdBetween(String value1, String value2) {
            addCriterion("set_id between", value1, value2, "setId");
            return (Criteria) this;
        }

        public Criteria andSetIdNotBetween(String value1, String value2) {
            addCriterion("set_id not between", value1, value2, "setId");
            return (Criteria) this;
        }

        public Criteria andSetNameIsNull() {
            addCriterion("set_name is null");
            return (Criteria) this;
        }

        public Criteria andSetNameIsNotNull() {
            addCriterion("set_name is not null");
            return (Criteria) this;
        }

        public Criteria andSetNameEqualTo(String value) {
            addCriterion("set_name =", value, "setName");
            return (Criteria) this;
        }

        public Criteria andSetNameNotEqualTo(String value) {
            addCriterion("set_name <>", value, "setName");
            return (Criteria) this;
        }

        public Criteria andSetNameGreaterThan(String value) {
            addCriterion("set_name >", value, "setName");
            return (Criteria) this;
        }

        public Criteria andSetNameGreaterThanOrEqualTo(String value) {
            addCriterion("set_name >=", value, "setName");
            return (Criteria) this;
        }

        public Criteria andSetNameLessThan(String value) {
            addCriterion("set_name <", value, "setName");
            return (Criteria) this;
        }

        public Criteria andSetNameLessThanOrEqualTo(String value) {
            addCriterion("set_name <=", value, "setName");
            return (Criteria) this;
        }

        public Criteria andSetNameLike(String value) {
            addCriterion("set_name like", value, "setName");
            return (Criteria) this;
        }

        public Criteria andSetNameNotLike(String value) {
            addCriterion("set_name not like", value, "setName");
            return (Criteria) this;
        }

        public Criteria andSetNameIn(List<String> values) {
            addCriterion("set_name in", values, "setName");
            return (Criteria) this;
        }

        public Criteria andSetNameNotIn(List<String> values) {
            addCriterion("set_name not in", values, "setName");
            return (Criteria) this;
        }

        public Criteria andSetNameBetween(String value1, String value2) {
            addCriterion("set_name between", value1, value2, "setName");
            return (Criteria) this;
        }

        public Criteria andSetNameNotBetween(String value1, String value2) {
            addCriterion("set_name not between", value1, value2, "setName");
            return (Criteria) this;
        }

        public Criteria andTypeIsNull() {
            addCriterion("type is null");
            return (Criteria) this;
        }

        public Criteria andTypeIsNotNull() {
            addCriterion("type is not null");
            return (Criteria) this;
        }

        public Criteria andTypeEqualTo(String value) {
            addCriterion("type =", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotEqualTo(String value) {
            addCriterion("type <>", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThan(String value) {
            addCriterion("type >", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeGreaterThanOrEqualTo(String value) {
            addCriterion("type >=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThan(String value) {
            addCriterion("type <", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeLessThanOrEqualTo(String value) {
            addCriterion("type <=", value, "type");
            return (Criteria) this;
        }

        public Criteria andTypeIn(List<String> values) {
            addCriterion("type in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotIn(List<String> values) {
            addCriterion("type not in", values, "type");
            return (Criteria) this;
        }

        public Criteria andTypeBetween(String value1, String value2) {
            addCriterion("type between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andTypeNotBetween(String value1, String value2) {
            addCriterion("type not between", value1, value2, "type");
            return (Criteria) this;
        }

        public Criteria andSortIsNull() {
            addCriterion("sort is null");
            return (Criteria) this;
        }

        public Criteria andSortIsNotNull() {
            addCriterion("sort is not null");
            return (Criteria) this;
        }

        public Criteria andSortEqualTo(Integer value) {
            addCriterion("sort =", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotEqualTo(Integer value) {
            addCriterion("sort <>", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThan(Integer value) {
            addCriterion("sort >", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortGreaterThanOrEqualTo(Integer value) {
            addCriterion("sort >=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThan(Integer value) {
            addCriterion("sort <", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortLessThanOrEqualTo(Integer value) {
            addCriterion("sort <=", value, "sort");
            return (Criteria) this;
        }

        public Criteria andSortIn(List<Integer> values) {
            addCriterion("sort in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotIn(List<Integer> values) {
            addCriterion("sort not in", values, "sort");
            return (Criteria) this;
        }

        public Criteria andSortBetween(Integer value1, Integer value2) {
            addCriterion("sort between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andSortNotBetween(Integer value1, Integer value2) {
            addCriterion("sort not between", value1, value2, "sort");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andDayCountIsNull() {
            addCriterion("day_count is null");
            return (Criteria) this;
        }

        public Criteria andDayCountIsNotNull() {
            addCriterion("day_count is not null");
            return (Criteria) this;
        }

        public Criteria andDayCountEqualTo(Integer value) {
            addCriterion("day_count =", value, "dayCount");
            return (Criteria) this;
        }

        public Criteria andDayCountNotEqualTo(Integer value) {
            addCriterion("day_count <>", value, "dayCount");
            return (Criteria) this;
        }

        public Criteria andDayCountGreaterThan(Integer value) {
            addCriterion("day_count >", value, "dayCount");
            return (Criteria) this;
        }

        public Criteria andDayCountGreaterThanOrEqualTo(Integer value) {
            addCriterion("day_count >=", value, "dayCount");
            return (Criteria) this;
        }

        public Criteria andDayCountLessThan(Integer value) {
            addCriterion("day_count <", value, "dayCount");
            return (Criteria) this;
        }

        public Criteria andDayCountLessThanOrEqualTo(Integer value) {
            addCriterion("day_count <=", value, "dayCount");
            return (Criteria) this;
        }

        public Criteria andDayCountIn(List<Integer> values) {
            addCriterion("day_count in", values, "dayCount");
            return (Criteria) this;
        }

        public Criteria andDayCountNotIn(List<Integer> values) {
            addCriterion("day_count not in", values, "dayCount");
            return (Criteria) this;
        }

        public Criteria andDayCountBetween(Integer value1, Integer value2) {
            addCriterion("day_count between", value1, value2, "dayCount");
            return (Criteria) this;
        }

        public Criteria andDayCountNotBetween(Integer value1, Integer value2) {
            addCriterion("day_count not between", value1, value2, "dayCount");
            return (Criteria) this;
        }

        public Criteria andDaySaleIsNull() {
            addCriterion("day_sale is null");
            return (Criteria) this;
        }

        public Criteria andDaySaleIsNotNull() {
            addCriterion("day_sale is not null");
            return (Criteria) this;
        }

        public Criteria andDaySaleEqualTo(Integer value) {
            addCriterion("day_sale =", value, "daySale");
            return (Criteria) this;
        }

        public Criteria andDaySaleNotEqualTo(Integer value) {
            addCriterion("day_sale <>", value, "daySale");
            return (Criteria) this;
        }

        public Criteria andDaySaleGreaterThan(Integer value) {
            addCriterion("day_sale >", value, "daySale");
            return (Criteria) this;
        }

        public Criteria andDaySaleGreaterThanOrEqualTo(Integer value) {
            addCriterion("day_sale >=", value, "daySale");
            return (Criteria) this;
        }

        public Criteria andDaySaleLessThan(Integer value) {
            addCriterion("day_sale <", value, "daySale");
            return (Criteria) this;
        }

        public Criteria andDaySaleLessThanOrEqualTo(Integer value) {
            addCriterion("day_sale <=", value, "daySale");
            return (Criteria) this;
        }

        public Criteria andDaySaleIn(List<Integer> values) {
            addCriterion("day_sale in", values, "daySale");
            return (Criteria) this;
        }

        public Criteria andDaySaleNotIn(List<Integer> values) {
            addCriterion("day_sale not in", values, "daySale");
            return (Criteria) this;
        }

        public Criteria andDaySaleBetween(Integer value1, Integer value2) {
            addCriterion("day_sale between", value1, value2, "daySale");
            return (Criteria) this;
        }

        public Criteria andDaySaleNotBetween(Integer value1, Integer value2) {
            addCriterion("day_sale not between", value1, value2, "daySale");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_food_week
     *
     * @mbggenerated do_not_delete_during_merge Tue Mar 31 10:43:03 CST 2015
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_food_week
     *
     * @mbggenerated Tue Mar 31 10:43:03 CST 2015
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
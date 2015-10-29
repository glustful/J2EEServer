package com.dunbian.jujiabao.appobj.generator;

import java.util.ArrayList;
import java.util.List;

public class SeckillInstanceExample {
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
     * This field corresponds to the database table t_seckill_instance
     *
     * @mbggenerated Wed Mar 25 21:56:14 CST 2015
     */
    protected String orderByClause;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_seckill_instance
     *
     * @mbggenerated Wed Mar 25 21:56:14 CST 2015
     */
    protected boolean distinct;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table t_seckill_instance
     *
     * @mbggenerated Wed Mar 25 21:56:14 CST 2015
     */
    protected List<Criteria> oredCriteria;

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_seckill_instance
     *
     * @mbggenerated Wed Mar 25 21:56:14 CST 2015
     */
    public SeckillInstanceExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_seckill_instance
     *
     * @mbggenerated Wed Mar 25 21:56:14 CST 2015
     */
    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_seckill_instance
     *
     * @mbggenerated Wed Mar 25 21:56:14 CST 2015
     */
    public String getOrderByClause() {
        return orderByClause;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_seckill_instance
     *
     * @mbggenerated Wed Mar 25 21:56:14 CST 2015
     */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_seckill_instance
     *
     * @mbggenerated Wed Mar 25 21:56:14 CST 2015
     */
    public boolean isDistinct() {
        return distinct;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_seckill_instance
     *
     * @mbggenerated Wed Mar 25 21:56:14 CST 2015
     */
    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_seckill_instance
     *
     * @mbggenerated Wed Mar 25 21:56:14 CST 2015
     */
    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_seckill_instance
     *
     * @mbggenerated Wed Mar 25 21:56:14 CST 2015
     */
    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_seckill_instance
     *
     * @mbggenerated Wed Mar 25 21:56:14 CST 2015
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
     * This method corresponds to the database table t_seckill_instance
     *
     * @mbggenerated Wed Mar 25 21:56:14 CST 2015
     */
    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_seckill_instance
     *
     * @mbggenerated Wed Mar 25 21:56:14 CST 2015
     */
    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_seckill_instance
     *
     * @mbggenerated Wed Mar 25 21:56:14 CST 2015
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

        public Criteria andSeckillIdIsNull() {
            addCriterion("seckill_id is null");
            return (Criteria) this;
        }

        public Criteria andSeckillIdIsNotNull() {
            addCriterion("seckill_id is not null");
            return (Criteria) this;
        }

        public Criteria andSeckillIdEqualTo(String value) {
            addCriterion("seckill_id =", value, "seckillId");
            return (Criteria) this;
        }

        public Criteria andSeckillIdNotEqualTo(String value) {
            addCriterion("seckill_id <>", value, "seckillId");
            return (Criteria) this;
        }

        public Criteria andSeckillIdGreaterThan(String value) {
            addCriterion("seckill_id >", value, "seckillId");
            return (Criteria) this;
        }

        public Criteria andSeckillIdGreaterThanOrEqualTo(String value) {
            addCriterion("seckill_id >=", value, "seckillId");
            return (Criteria) this;
        }

        public Criteria andSeckillIdLessThan(String value) {
            addCriterion("seckill_id <", value, "seckillId");
            return (Criteria) this;
        }

        public Criteria andSeckillIdLessThanOrEqualTo(String value) {
            addCriterion("seckill_id <=", value, "seckillId");
            return (Criteria) this;
        }

        public Criteria andSeckillIdIn(List<String> values) {
            addCriterion("seckill_id in", values, "seckillId");
            return (Criteria) this;
        }

        public Criteria andSeckillIdNotIn(List<String> values) {
            addCriterion("seckill_id not in", values, "seckillId");
            return (Criteria) this;
        }

        public Criteria andSeckillIdBetween(String value1, String value2) {
            addCriterion("seckill_id between", value1, value2, "seckillId");
            return (Criteria) this;
        }

        public Criteria andSeckillIdNotBetween(String value1, String value2) {
            addCriterion("seckill_id not between", value1, value2, "seckillId");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeIsNull() {
            addCriterion("inventory_type is null");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeIsNotNull() {
            addCriterion("inventory_type is not null");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeEqualTo(String value) {
            addCriterion("inventory_type =", value, "inventoryType");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeNotEqualTo(String value) {
            addCriterion("inventory_type <>", value, "inventoryType");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeGreaterThan(String value) {
            addCriterion("inventory_type >", value, "inventoryType");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeGreaterThanOrEqualTo(String value) {
            addCriterion("inventory_type >=", value, "inventoryType");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeLessThan(String value) {
            addCriterion("inventory_type <", value, "inventoryType");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeLessThanOrEqualTo(String value) {
            addCriterion("inventory_type <=", value, "inventoryType");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeLike(String value) {
            addCriterion("inventory_type like", value, "inventoryType");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeNotLike(String value) {
            addCriterion("inventory_type not like", value, "inventoryType");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeIn(List<String> values) {
            addCriterion("inventory_type in", values, "inventoryType");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeNotIn(List<String> values) {
            addCriterion("inventory_type not in", values, "inventoryType");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeBetween(String value1, String value2) {
            addCriterion("inventory_type between", value1, value2, "inventoryType");
            return (Criteria) this;
        }

        public Criteria andInventoryTypeNotBetween(String value1, String value2) {
            addCriterion("inventory_type not between", value1, value2, "inventoryType");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(String value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(String value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(String value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(String value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(String value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(String value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLike(String value) {
            addCriterion("status like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotLike(String value) {
            addCriterion("status not like", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<String> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<String> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(String value1, String value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(String value1, String value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_seckill_instance
     *
     * @mbggenerated do_not_delete_during_merge Wed Mar 25 21:56:14 CST 2015
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table t_seckill_instance
     *
     * @mbggenerated Wed Mar 25 21:56:14 CST 2015
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

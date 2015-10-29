package com.dunbian.jujiabao.appobj.generator;

import java.math.BigDecimal;
import java.util.Date;

public class RechargeRecord {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_recharge_record.id
     *
     * @mbggenerated Tue Apr 14 16:21:49 CST 2015
     */
    private String id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_recharge_record.recharge_id
     *
     * @mbggenerated Tue Apr 14 16:21:49 CST 2015
     */
    private String rechargeId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_recharge_record.user_id
     *
     * @mbggenerated Tue Apr 14 16:21:49 CST 2015
     */
    private String userId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_recharge_record.amount
     *
     * @mbggenerated Tue Apr 14 16:21:49 CST 2015
     */
    private BigDecimal amount;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_recharge_record.gift
     *
     * @mbggenerated Tue Apr 14 16:21:49 CST 2015
     */
    private BigDecimal gift;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_recharge_record.status
     *
     * @mbggenerated Tue Apr 14 16:21:49 CST 2015
     */
    private String status;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_recharge_record.trade_no
     *
     * @mbggenerated Tue Apr 14 16:21:49 CST 2015
     */
    private String tradeNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column t_recharge_record.trade_time
     *
     * @mbggenerated Tue Apr 14 16:21:49 CST 2015
     */
    private Date tradeTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_recharge_record.id
     *
     * @return the value of t_recharge_record.id
     *
     * @mbggenerated Tue Apr 14 16:21:49 CST 2015
     */
    public String getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_recharge_record.id
     *
     * @param id the value for t_recharge_record.id
     *
     * @mbggenerated Tue Apr 14 16:21:49 CST 2015
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_recharge_record.recharge_id
     *
     * @return the value of t_recharge_record.recharge_id
     *
     * @mbggenerated Tue Apr 14 16:21:49 CST 2015
     */
    public String getRechargeId() {
        return rechargeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_recharge_record.recharge_id
     *
     * @param rechargeId the value for t_recharge_record.recharge_id
     *
     * @mbggenerated Tue Apr 14 16:21:49 CST 2015
     */
    public void setRechargeId(String rechargeId) {
        this.rechargeId = rechargeId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_recharge_record.user_id
     *
     * @return the value of t_recharge_record.user_id
     *
     * @mbggenerated Tue Apr 14 16:21:49 CST 2015
     */
    public String getUserId() {
        return userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_recharge_record.user_id
     *
     * @param userId the value for t_recharge_record.user_id
     *
     * @mbggenerated Tue Apr 14 16:21:49 CST 2015
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_recharge_record.amount
     *
     * @return the value of t_recharge_record.amount
     *
     * @mbggenerated Tue Apr 14 16:21:49 CST 2015
     */
    public BigDecimal getAmount() {
        return amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_recharge_record.amount
     *
     * @param amount the value for t_recharge_record.amount
     *
     * @mbggenerated Tue Apr 14 16:21:49 CST 2015
     */
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_recharge_record.gift
     *
     * @return the value of t_recharge_record.gift
     *
     * @mbggenerated Tue Apr 14 16:21:49 CST 2015
     */
    public BigDecimal getGift() {
        return gift;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_recharge_record.gift
     *
     * @param gift the value for t_recharge_record.gift
     *
     * @mbggenerated Tue Apr 14 16:21:49 CST 2015
     */
    public void setGift(BigDecimal gift) {
        this.gift = gift;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_recharge_record.status
     *
     * @return the value of t_recharge_record.status
     *
     * @mbggenerated Tue Apr 14 16:21:49 CST 2015
     */
    public String getStatus() {
        return status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_recharge_record.status
     *
     * @param status the value for t_recharge_record.status
     *
     * @mbggenerated Tue Apr 14 16:21:49 CST 2015
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_recharge_record.trade_no
     *
     * @return the value of t_recharge_record.trade_no
     *
     * @mbggenerated Tue Apr 14 16:21:49 CST 2015
     */
    public String getTradeNo() {
        return tradeNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_recharge_record.trade_no
     *
     * @param tradeNo the value for t_recharge_record.trade_no
     *
     * @mbggenerated Tue Apr 14 16:21:49 CST 2015
     */
    public void setTradeNo(String tradeNo) {
        this.tradeNo = tradeNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column t_recharge_record.trade_time
     *
     * @return the value of t_recharge_record.trade_time
     *
     * @mbggenerated Tue Apr 14 16:21:49 CST 2015
     */
    public Date getTradeTime() {
        return tradeTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column t_recharge_record.trade_time
     *
     * @param tradeTime the value for t_recharge_record.trade_time
     *
     * @mbggenerated Tue Apr 14 16:21:49 CST 2015
     */
    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
    }
}
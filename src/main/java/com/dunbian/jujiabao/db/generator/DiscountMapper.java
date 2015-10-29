package com.dunbian.jujiabao.db.generator;

import com.dunbian.jujiabao.appobj.generator.Discount;
import com.dunbian.jujiabao.appobj.extend.DiscountAO;
import com.dunbian.jujiabao.appobj.generator.DiscountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface DiscountMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_discount
     *
     * @mbggenerated Wed Jun 10 11:13:53 CST 2015
     */
    int countByExample(DiscountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_discount
     *
     * @mbggenerated Wed Jun 10 11:13:53 CST 2015
     */
    int deleteByExample(DiscountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_discount
     *
     * @mbggenerated Wed Jun 10 11:13:53 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_discount
     *
     * @mbggenerated Wed Jun 10 11:13:53 CST 2015
     */
    int insert(Discount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_discount
     *
     * @mbggenerated Wed Jun 10 11:13:53 CST 2015
     */
    int insertSelective(Discount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_discount
     *
     * @mbggenerated Wed Jun 10 11:13:53 CST 2015
     */
    List<DiscountAO> selectByExample(DiscountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_discount
     *
     * @mbggenerated Wed Jun 10 11:13:53 CST 2015
     */
	DiscountAO selectByPrimaryKey(String id);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_discount
     *
     * @mbggenerated Wed Jun 10 11:13:53 CST 2015
     */
    int updateByExampleSelective(@Param("record") Discount record, @Param("example") DiscountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_discount
     *
     * @mbggenerated Wed Jun 10 11:13:53 CST 2015
     */
    int updateByExample(@Param("record") Discount record, @Param("example") DiscountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_discount
     *
     * @mbggenerated Wed Jun 10 11:13:53 CST 2015
     */
    int updateByPrimaryKeySelective(Discount record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_discount
     *
     * @mbggenerated Wed Jun 10 11:13:53 CST 2015
     */
    int updateByPrimaryKey(Discount record);
}

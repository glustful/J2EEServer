package com.dunbian.jujiabao.db.generator;

import com.dunbian.jujiabao.appobj.generator.SeckillInstance;
import com.dunbian.jujiabao.appobj.extend.SeckillInstanceAO;
import com.dunbian.jujiabao.appobj.generator.SeckillInstanceExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SeckillInstanceMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_seckill_instance
     *
     * @mbggenerated Wed Mar 25 21:56:14 CST 2015
     */
    int countByExample(SeckillInstanceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_seckill_instance
     *
     * @mbggenerated Wed Mar 25 21:56:14 CST 2015
     */
    int deleteByExample(SeckillInstanceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_seckill_instance
     *
     * @mbggenerated Wed Mar 25 21:56:14 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_seckill_instance
     *
     * @mbggenerated Wed Mar 25 21:56:14 CST 2015
     */
    int insert(SeckillInstance record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_seckill_instance
     *
     * @mbggenerated Wed Mar 25 21:56:14 CST 2015
     */
    int insertSelective(SeckillInstance record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_seckill_instance
     *
     * @mbggenerated Wed Mar 25 21:56:14 CST 2015
     */
    List<SeckillInstanceAO> selectByExample(SeckillInstanceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_seckill_instance
     *
     * @mbggenerated Wed Mar 25 21:56:14 CST 2015
     */
	SeckillInstanceAO selectByPrimaryKey(String id);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_seckill_instance
     *
     * @mbggenerated Wed Mar 25 21:56:14 CST 2015
     */
    int updateByExampleSelective(@Param("record") SeckillInstance record, @Param("example") SeckillInstanceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_seckill_instance
     *
     * @mbggenerated Wed Mar 25 21:56:14 CST 2015
     */
    int updateByExample(@Param("record") SeckillInstance record, @Param("example") SeckillInstanceExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_seckill_instance
     *
     * @mbggenerated Wed Mar 25 21:56:14 CST 2015
     */
    int updateByPrimaryKeySelective(SeckillInstance record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_seckill_instance
     *
     * @mbggenerated Wed Mar 25 21:56:14 CST 2015
     */
    int updateByPrimaryKey(SeckillInstance record);
}
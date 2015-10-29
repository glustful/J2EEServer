package com.dunbian.jujiabao.db.generator;

import com.dunbian.jujiabao.appobj.generator.RegionTime;
import com.dunbian.jujiabao.appobj.extend.RegionTimeAO;
import com.dunbian.jujiabao.appobj.generator.RegionTimeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface RegionTimeMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_region_time
     *
     * @mbggenerated Sat Mar 07 17:52:47 CST 2015
     */
    int countByExample(RegionTimeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_region_time
     *
     * @mbggenerated Sat Mar 07 17:52:47 CST 2015
     */
    int deleteByExample(RegionTimeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_region_time
     *
     * @mbggenerated Sat Mar 07 17:52:47 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_region_time
     *
     * @mbggenerated Sat Mar 07 17:52:47 CST 2015
     */
    int insert(RegionTime record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_region_time
     *
     * @mbggenerated Sat Mar 07 17:52:47 CST 2015
     */
    int insertSelective(RegionTime record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_region_time
     *
     * @mbggenerated Sat Mar 07 17:52:47 CST 2015
     */
    List<RegionTimeAO> selectByExample(RegionTimeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_region_time
     *
     * @mbggenerated Sat Mar 07 17:52:47 CST 2015
     */
	RegionTimeAO selectByPrimaryKey(String id);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_region_time
     *
     * @mbggenerated Sat Mar 07 17:52:47 CST 2015
     */
    int updateByExampleSelective(@Param("record") RegionTime record, @Param("example") RegionTimeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_region_time
     *
     * @mbggenerated Sat Mar 07 17:52:47 CST 2015
     */
    int updateByExample(@Param("record") RegionTime record, @Param("example") RegionTimeExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_region_time
     *
     * @mbggenerated Sat Mar 07 17:52:47 CST 2015
     */
    int updateByPrimaryKeySelective(RegionTime record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_region_time
     *
     * @mbggenerated Sat Mar 07 17:52:47 CST 2015
     */
    int updateByPrimaryKey(RegionTime record);
}

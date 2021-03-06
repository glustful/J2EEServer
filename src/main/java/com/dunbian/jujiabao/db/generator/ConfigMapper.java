package com.dunbian.jujiabao.db.generator;

import com.dunbian.jujiabao.appobj.generator.Config;
import com.dunbian.jujiabao.appobj.extend.ConfigAO;
import com.dunbian.jujiabao.appobj.generator.ConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ConfigMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_config
     *
     * @mbggenerated Sun Mar 15 20:19:44 CST 2015
     */
    int countByExample(ConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_config
     *
     * @mbggenerated Sun Mar 15 20:19:44 CST 2015
     */
    int deleteByExample(ConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_config
     *
     * @mbggenerated Sun Mar 15 20:19:44 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_config
     *
     * @mbggenerated Sun Mar 15 20:19:44 CST 2015
     */
    int insert(Config record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_config
     *
     * @mbggenerated Sun Mar 15 20:19:44 CST 2015
     */
    int insertSelective(Config record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_config
     *
     * @mbggenerated Sun Mar 15 20:19:44 CST 2015
     */
    List<ConfigAO> selectByExample(ConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_config
     *
     * @mbggenerated Sun Mar 15 20:19:44 CST 2015
     */
	ConfigAO selectByPrimaryKey(String id);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_config
     *
     * @mbggenerated Sun Mar 15 20:19:44 CST 2015
     */
    int updateByExampleSelective(@Param("record") Config record, @Param("example") ConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_config
     *
     * @mbggenerated Sun Mar 15 20:19:44 CST 2015
     */
    int updateByExample(@Param("record") Config record, @Param("example") ConfigExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_config
     *
     * @mbggenerated Sun Mar 15 20:19:44 CST 2015
     */
    int updateByPrimaryKeySelective(Config record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_config
     *
     * @mbggenerated Sun Mar 15 20:19:44 CST 2015
     */
    int updateByPrimaryKey(Config record);
}

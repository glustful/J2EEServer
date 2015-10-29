package com.dunbian.jujiabao.db.generator;

import com.dunbian.jujiabao.appobj.generator.UserAddress;
import com.dunbian.jujiabao.appobj.extend.UserAddressAO;
import com.dunbian.jujiabao.appobj.generator.UserAddressExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserAddressMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_user_address
     *
     * @mbggenerated Sun Feb 08 11:44:19 CST 2015
     */
    int countByExample(UserAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_user_address
     *
     * @mbggenerated Sun Feb 08 11:44:19 CST 2015
     */
    int deleteByExample(UserAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_user_address
     *
     * @mbggenerated Sun Feb 08 11:44:19 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_user_address
     *
     * @mbggenerated Sun Feb 08 11:44:19 CST 2015
     */
    int insert(UserAddress record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_user_address
     *
     * @mbggenerated Sun Feb 08 11:44:19 CST 2015
     */
    int insertSelective(UserAddress record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_user_address
     *
     * @mbggenerated Sun Feb 08 11:44:19 CST 2015
     */
    List<UserAddressAO> selectByExample(UserAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_user_address
     *
     * @mbggenerated Sun Feb 08 11:44:19 CST 2015
     */
	UserAddressAO selectByPrimaryKey(String id);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_user_address
     *
     * @mbggenerated Sun Feb 08 11:44:19 CST 2015
     */
    int updateByExampleSelective(@Param("record") UserAddress record, @Param("example") UserAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_user_address
     *
     * @mbggenerated Sun Feb 08 11:44:19 CST 2015
     */
    int updateByExample(@Param("record") UserAddress record, @Param("example") UserAddressExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_user_address
     *
     * @mbggenerated Sun Feb 08 11:44:19 CST 2015
     */
    int updateByPrimaryKeySelective(UserAddress record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_user_address
     *
     * @mbggenerated Sun Feb 08 11:44:19 CST 2015
     */
    int updateByPrimaryKey(UserAddress record);
}

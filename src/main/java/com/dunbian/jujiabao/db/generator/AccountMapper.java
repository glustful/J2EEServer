package com.dunbian.jujiabao.db.generator;

import com.dunbian.jujiabao.appobj.generator.Account;
import com.dunbian.jujiabao.appobj.extend.AccountAO;
import com.dunbian.jujiabao.appobj.generator.AccountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AccountMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_account
     *
     * @mbggenerated Sat Apr 11 10:24:16 CST 2015
     */
    int countByExample(AccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_account
     *
     * @mbggenerated Sat Apr 11 10:24:16 CST 2015
     */
    int deleteByExample(AccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_account
     *
     * @mbggenerated Sat Apr 11 10:24:16 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_account
     *
     * @mbggenerated Sat Apr 11 10:24:16 CST 2015
     */
    int insert(Account record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_account
     *
     * @mbggenerated Sat Apr 11 10:24:16 CST 2015
     */
    int insertSelective(Account record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_account
     *
     * @mbggenerated Sat Apr 11 10:24:16 CST 2015
     */
    List<AccountAO> selectByExample(AccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_account
     *
     * @mbggenerated Sat Apr 11 10:24:16 CST 2015
     */
	AccountAO selectByPrimaryKey(String id);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_account
     *
     * @mbggenerated Sat Apr 11 10:24:16 CST 2015
     */
    int updateByExampleSelective(@Param("record") Account record, @Param("example") AccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_account
     *
     * @mbggenerated Sat Apr 11 10:24:16 CST 2015
     */
    int updateByExample(@Param("record") Account record, @Param("example") AccountExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_account
     *
     * @mbggenerated Sat Apr 11 10:24:16 CST 2015
     */
    int updateByPrimaryKeySelective(Account record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_account
     *
     * @mbggenerated Sat Apr 11 10:24:16 CST 2015
     */
    int updateByPrimaryKey(Account record);
}

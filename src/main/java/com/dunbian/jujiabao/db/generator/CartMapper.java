package com.dunbian.jujiabao.db.generator;

import com.dunbian.jujiabao.appobj.generator.Cart;
import com.dunbian.jujiabao.appobj.extend.CartAO;
import com.dunbian.jujiabao.appobj.generator.CartExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CartMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_cart
     *
     * @mbggenerated Fri Apr 03 10:54:58 CST 2015
     */
    int countByExample(CartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_cart
     *
     * @mbggenerated Fri Apr 03 10:54:58 CST 2015
     */
    int deleteByExample(CartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_cart
     *
     * @mbggenerated Fri Apr 03 10:54:58 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_cart
     *
     * @mbggenerated Fri Apr 03 10:54:58 CST 2015
     */
    int insert(Cart record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_cart
     *
     * @mbggenerated Fri Apr 03 10:54:58 CST 2015
     */
    int insertSelective(Cart record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_cart
     *
     * @mbggenerated Fri Apr 03 10:54:58 CST 2015
     */
    List<CartAO> selectByExample(CartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_cart
     *
     * @mbggenerated Fri Apr 03 10:54:58 CST 2015
     */
	CartAO selectByPrimaryKey(String id);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_cart
     *
     * @mbggenerated Fri Apr 03 10:54:58 CST 2015
     */
    int updateByExampleSelective(@Param("record") Cart record, @Param("example") CartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_cart
     *
     * @mbggenerated Fri Apr 03 10:54:58 CST 2015
     */
    int updateByExample(@Param("record") Cart record, @Param("example") CartExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_cart
     *
     * @mbggenerated Fri Apr 03 10:54:58 CST 2015
     */
    int updateByPrimaryKeySelective(Cart record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_cart
     *
     * @mbggenerated Fri Apr 03 10:54:58 CST 2015
     */
    int updateByPrimaryKey(Cart record);
}

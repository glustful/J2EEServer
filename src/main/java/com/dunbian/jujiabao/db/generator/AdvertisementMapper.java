package com.dunbian.jujiabao.db.generator;

import com.dunbian.jujiabao.appobj.generator.Advertisement;
import com.dunbian.jujiabao.appobj.extend.AdvertisementAO;
import com.dunbian.jujiabao.appobj.generator.AdvertisementExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdvertisementMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_advertisement
     *
     * @mbggenerated Sat Mar 21 15:46:54 CST 2015
     */
    int countByExample(AdvertisementExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_advertisement
     *
     * @mbggenerated Sat Mar 21 15:46:54 CST 2015
     */
    int deleteByExample(AdvertisementExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_advertisement
     *
     * @mbggenerated Sat Mar 21 15:46:54 CST 2015
     */
    int deleteByPrimaryKey(String id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_advertisement
     *
     * @mbggenerated Sat Mar 21 15:46:54 CST 2015
     */
    int insert(Advertisement record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_advertisement
     *
     * @mbggenerated Sat Mar 21 15:46:54 CST 2015
     */
    int insertSelective(Advertisement record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_advertisement
     *
     * @mbggenerated Sat Mar 21 15:46:54 CST 2015
     */
    List<AdvertisementAO> selectByExampleWithBLOBs(AdvertisementExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_advertisement
     *
     * @mbggenerated Sat Mar 21 15:46:54 CST 2015
     */
    List<AdvertisementAO> selectByExample(AdvertisementExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_advertisement
     *
     * @mbggenerated Sat Mar 21 15:46:54 CST 2015
     */
	AdvertisementAO selectByPrimaryKey(String id);
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_advertisement
     *
     * @mbggenerated Sat Mar 21 15:46:54 CST 2015
     */
    int updateByExampleSelective(@Param("record") Advertisement record, @Param("example") AdvertisementExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_advertisement
     *
     * @mbggenerated Sat Mar 21 15:46:54 CST 2015
     */
    int updateByExampleWithBLOBs(@Param("record") Advertisement record, @Param("example") AdvertisementExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_advertisement
     *
     * @mbggenerated Sat Mar 21 15:46:54 CST 2015
     */
    int updateByExample(@Param("record") Advertisement record, @Param("example") AdvertisementExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_advertisement
     *
     * @mbggenerated Sat Mar 21 15:46:54 CST 2015
     */
    int updateByPrimaryKeySelective(Advertisement record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_advertisement
     *
     * @mbggenerated Sat Mar 21 15:46:54 CST 2015
     */
    int updateByPrimaryKeyWithBLOBs(Advertisement record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_advertisement
     *
     * @mbggenerated Sat Mar 21 15:46:54 CST 2015
     */
    int updateByPrimaryKey(Advertisement record);
}
package com.dunbian.jujiabao.db.customer;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.dunbian.jujiabao.appobj.extend.ResourceAO;

public interface ResourceCustomMapper {
	
	/**
	 * 批量添加文件资源管理信息
	 * @param resources 文件管理信息集合
	 * @return
	 */
    int addResources(@Param("resources") List<ResourceAO> resources);
    
    /**
     * 根据数据id和数据类型查询文件路径集合
     * @param dataId 数据id
     * @param dataType 数据类型
     * @return
     */
    List<String> getFilePathsByDataId(@Param("dataId") String dataId, @Param("dataType") String dataType);
}

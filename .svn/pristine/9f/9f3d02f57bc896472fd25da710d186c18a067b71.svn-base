package com.dunbian.jujiabao.service.good;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;

import com.dunbian.jujiabao.appobj.extend.FoodTypeAO;
import com.dunbian.jujiabao.framework.jdbc.QueryReactor;
import com.dunbian.jujiabao.framework.jdbc.SimpleJDBCTemplate;

@Service
public class GoodTypeService implements IGoodTypeService {

	@Resource
	private DataSource dataSource;
	
	private List<FoodTypeAO> foodTypeListCache;
	
	private Map<String, FoodTypeAO> foodTypeMapCache;
	
	@Override
	public void reload() {
		synchronized (this) {
			foodTypeListCache = null;
			foodTypeMapCache = null;
		}
		loadFoodType();
	}

	@Override
	public void loadFoodType() {
		Connection con = null;
		
		synchronized (this) {
			if(foodTypeListCache != null) {
				return;
			}
		}
		
		try {
			con = DataSourceUtils.getConnection(dataSource);
			SimpleJDBCTemplate jdbcTool = new SimpleJDBCTemplate(con);
			List<FoodTypeAO> typeList = jdbcTool.selectList("select id, name, status from t_food_type order by sort", new QueryReactor<FoodTypeAO>() {

				@Override
				public void setParam(PreparedStatement ps, Object... params)
						throws SQLException {
				}

				@Override
				public FoodTypeAO pack(ResultSet rs) throws SQLException {
					FoodTypeAO type = new FoodTypeAO();
					type.setId(rs.getString("id"));
					type.setName(rs.getString("name"));
					type.setStatus(rs.getString("status"));
					
					return type;
				}
			});
			
			if(typeList == null) {
				return;
			}
			
			Map<String, FoodTypeAO> typeMap = new HashMap<>();
			for(FoodTypeAO type : typeList) {
				typeMap.put(type.getId(), type);
			}
			
			synchronized (this) {
				foodTypeListCache = typeList;
				foodTypeMapCache = typeMap;
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			DataSourceUtils.releaseConnection(con, dataSource);
		}
	}

	@Override
	public void loadGoodType() {
		// TODO Auto-generated method stub

	}

	@Override
	public FoodTypeAO getFoodTypeCache(String key) {
		if(foodTypeMapCache == null) {
			synchronized (this) {
				if(foodTypeMapCache == null) {
					loadFoodType();
				}
			}
		}
		
		return foodTypeMapCache.get(key);
	}

	@Override
	public List<FoodTypeAO> getFoodTypeList() {
		if(foodTypeListCache == null) {
			synchronized (this) {
				if(foodTypeListCache == null) {
					loadFoodType();
				}
			}
		}
		
		return foodTypeListCache;
	}

}

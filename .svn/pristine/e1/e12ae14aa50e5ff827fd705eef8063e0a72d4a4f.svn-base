package com.dunbian.jujiabao.service.discount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dunbian.jujiabao.appobj.extend.AdvertisementShowAO;
import com.dunbian.jujiabao.appobj.extend.DiscountAO;
import com.dunbian.jujiabao.appobj.extend.UserAO;
import com.dunbian.jujiabao.framework.jdbc.QueryReactor;
import com.dunbian.jujiabao.framework.jdbc.SimpleJDBCTemplate;

@Service
public class DiscountSerivce implements IDiscountSerivce {

	@Resource
	private DataSource dataSource;
	
	@Override
	@Transactional(readOnly=true)
	public List<DiscountAO> getDiscountInfo(String town, UserAO user) {
		Connection con = null;
		try {
			if(town == null || "".equals(town)) {
				town = AdvertisementShowAO.REGION_ALL;
			}
			
			con = DataSourceUtils.getConnection(dataSource);
			SimpleJDBCTemplate jdbcTool = new SimpleJDBCTemplate(con);
			List<DiscountAO> disList = jdbcTool.selectList("select id, name, type, for_new, discount_amount, `limit`, `join`, user_limit from t_discount where (region=? or region=?) and start_date<=? and end_date>=? and status=?", new QueryReactor<DiscountAO>() {

				@Override
				public void setParam(PreparedStatement ps, Object... params)
						throws SQLException {
					String town = (String)params[0];
					java.sql.Date now = new java.sql.Date(System.currentTimeMillis());
					
					ps.setString(1, town);
					ps.setString(2, AdvertisementShowAO.REGION_ALL);
					ps.setDate(3, now);
					ps.setDate(4, now);
					ps.setString(5, DiscountAO.STATUS_NOMARL);
				}

				@Override
				public DiscountAO pack(ResultSet rs) throws SQLException {
					DiscountAO discount = new DiscountAO();
					discount.setId(rs.getString(1));
					discount.setName(rs.getString(2));
					discount.setType(rs.getString(3));
					discount.setForNew(rs.getString(4));
					discount.setDiscountAmount(rs.getBigDecimal(5));
					discount.setLimit(rs.getInt(6));
					discount.setJoin(rs.getInt(7));
					discount.setUserLimit(rs.getInt(8));
					return discount;
				}
			}, town);
			
			if(disList == null || disList.isEmpty()) {
				return null;
			}
			
			
			for(Iterator<DiscountAO> itr = disList.iterator(); itr.hasNext(); ) {
				DiscountAO discount = itr.next();
				if(discount.getLimit() != null && discount.getJoin() != null && discount.getJoin() >= discount.getLimit()) {
					itr.remove();
					continue;
				}
				
				Integer cs = jdbcTool.selectObject("select count(*) from t_discount_record where user_id=? and discount_id=?", new QueryReactor<Integer>() {

					@Override
					public void setParam(PreparedStatement ps, Object... params)
							throws SQLException {
						ps.setString(1, (String)params[0]);
						ps.setString(2, (String)params[1]);
					}

					@Override
					public Integer pack(ResultSet rs) throws SQLException {
						return rs.getInt(1);
					}
				}, user.getId(), discount.getId());
				
				if(cs == null) {
					cs = 0;
				}
			
				
				Integer gradeCredit = jdbcTool.selectObject("select  grade_credit from t_user where id=?", new QueryReactor<Integer>() {

					@Override
					public void setParam(PreparedStatement ps, Object... params)
							throws SQLException {
						ps.setString(1, (String)params[0]);
					}

					@Override
					public Integer pack(ResultSet rs) throws SQLException {
						return rs.getInt(1);
					}
				}, user.getId());
				
				if(gradeCredit == null) {
					gradeCredit = 0;
				}
			
				
				//如果用户参与次数大于限制次数或者是活动是针对新注册用户而当前用户不是新注册用户是就移除活动
				if((discount.getUserLimit() != null && cs >= discount.getUserLimit()) 
						|| (discount.getForNew() != null && discount.getForNew().equals(DiscountAO.FOR_NEW_YES) && gradeCredit > 0)) {
					itr.remove();
					continue;
				}
			}
			
			return disList;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			DataSourceUtils.releaseConnection(con, dataSource);
		}
	}

}

package com.dunbian.jujiabao.service.advertisement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dunbian.jujiabao.appobj.extend.AdvertisementAO;
import com.dunbian.jujiabao.appobj.extend.AdvertisementShowAO;
import com.dunbian.jujiabao.framework.jdbc.QueryReactor;
import com.dunbian.jujiabao.framework.jdbc.SimpleJDBCTemplate;

@Service
public class AdvertisementService implements IAdvertisementService {

	@Resource
	private DataSource dataSource;
	
	@Override
	@Transactional(readOnly=true)
	public List<AdvertisementAO> getAdvertisement(String townId, Date now) {
		Connection con = null;
		try {
			if(townId == null || "".equals(townId)) {
				townId = AdvertisementShowAO.REGION_ALL;
			}
			
			con = DataSourceUtils.getConnection(dataSource);
			SimpleJDBCTemplate jdbcTool = new SimpleJDBCTemplate(con);
			return jdbcTool.selectList("select id, name, img_web, img_phone, type, background from t_advertisement where status='00' and exists (select 1 from t_advertisement_show where advertisement_id=t_advertisement.id and (region_id=? or region_id=?) and start_date<=? and end_date>=?) order by create_time desc", new QueryReactor<AdvertisementAO>() {

				@Override
				public void setParam(PreparedStatement ps, Object... params)
						throws SQLException {
					String townId = (String)params[0];
					Date now = (Date)params[1];
					ps.setString(1, townId);
					ps.setString(2, AdvertisementShowAO.REGION_ALL);
					ps.setDate(3, new java.sql.Date(now.getTime()));
					ps.setDate(4, new java.sql.Date(now.getTime()));
				}

				@Override
				public AdvertisementAO pack(ResultSet rs) throws SQLException {
					AdvertisementAO adv = new AdvertisementAO();
					adv.setId(rs.getString("id"));
					adv.setName(rs.getString("name"));
					adv.setImgWeb(rs.getString("img_web"));
					adv.setImgPhone(rs.getString("img_phone"));
					adv.setType(rs.getString("type"));
					adv.setBackground(rs.getString("background"));
					return adv;
				}
			}, townId, now);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			DataSourceUtils.releaseConnection(con, dataSource);
		}
	}

	@Override
	@Transactional(readOnly=true)
	public AdvertisementAO getAdvertisement(String id) {
		Connection con = null;
		try {
			if(id == null || "".equals(id)) {
				return null;
			}
			
			con = DataSourceUtils.getConnection(dataSource);
			SimpleJDBCTemplate jdbcTool = new SimpleJDBCTemplate(con);
			return jdbcTool.selectObject("select content, actions, support_version, unsupport_msg,name from t_advertisement where id=?", new QueryReactor<AdvertisementAO>() {

				@Override
				public void setParam(PreparedStatement ps, Object... params)
						throws SQLException {
					String id = (String)params[0];
					ps.setString(1, id);
				}

				@Override
				public AdvertisementAO pack(ResultSet rs) throws SQLException {
					AdvertisementAO adv = new AdvertisementAO();
					adv.setContent(rs.getString("content"));
					adv.setActions(rs.getString("actions"));
					adv.setSupportVersion(rs.getInt("support_version"));
					adv.setUnsupportMsg(rs.getString("unsupport_msg"));
					adv.setName(rs.getString("name"));
					return adv;
				}
			}, id);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			DataSourceUtils.releaseConnection(con, dataSource);
		}
	}

}

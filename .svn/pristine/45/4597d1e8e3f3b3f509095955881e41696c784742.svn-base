package com.dunbian.jujiabao.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import com.dunbian.jujiabao.appobj.extend.UserAO;
import com.dunbian.jujiabao.framework.exception.NoLoginException;
import com.dunbian.jujiabao.framework.jdbc.ParamReactor;
import com.dunbian.jujiabao.framework.jdbc.SimpleJDBCTemplate;
import com.dunbian.jujiabao.framework.json.JsonUtil;
import com.dunbian.jujiabao.framework.session.SessionUtil;
import com.puddingnet.mvc.servlet.SpringContext;

public class UserUtil {
	
	public static final String SESSION_USER = "session_user";

	public static UserAO getCurrentLoginUser(HttpServletRequest request) {
		UserAO user = SessionUtil.getAttribute(SESSION_USER, UserAO.class, request);
		if(user == null) {
			throw new NoLoginException("当前会话未登录");
		}
		
		return user;
	}
	
	
	public static void resetUserInfo(UserAO user) {
		if(user == null) {
			return;
		}
		
		DataSource ds = null;
		Connection con = null;
		try {
			ds = SpringContext.getBean(DataSource.class);
			con = ds.getConnection();
			con.setAutoCommit(false);
			
		    SimpleJDBCTemplate template = new SimpleJDBCTemplate(con);
		    template.executeUpdate("update t_session_data set session_data=? where session_key=? and exists (select 1 from t_session where session_id=t_session_data.session_id and user_id=?)", new ParamReactor() {
				
				@Override
				public void setParam(PreparedStatement ps, Object... params)
						throws SQLException {
					UserAO user = (UserAO)params[0];
					JsonUtil jsonUtil = JsonUtil.buildNonDefaultMapper();
					String v = jsonUtil.toJson(user);
					byte[] data = null;
					if(v != null) {
						data = v.getBytes();
					}
					
					ps.setBytes(1, data);
					ps.setString(2, UserUtil.SESSION_USER);
					ps.setString(3, user.getId());
				}
			}, user);
		    
			con.commit();
		} catch (Exception e) {
			try {
				if(con != null) {
					con.rollback();
				}
			} catch (Exception e2) {}
		} finally {
			try {
				if(con != null) {
					con.close();
				}
			} catch (Exception e2) {}
		}
	}
//	public static boolean updateCurrentLoginUser(HttpServletRequest request, UserAO user) {
//		if(user == null) {
//			return false;
//		}
//		SessionUtil.setAttribute(SESSION_USER, SESSION_USER, request);
//		return true;
//	}
}

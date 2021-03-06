package com.dunbian.jujiabao.service.seckill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dunbian.jujiabao.appobj.extend.AdvertisementShowAO;
import com.dunbian.jujiabao.appobj.extend.FoodSetAO;
import com.dunbian.jujiabao.appobj.extend.FoodWeekAO;
import com.dunbian.jujiabao.appobj.extend.OrderAO;
import com.dunbian.jujiabao.appobj.extend.OrderExtraAO;
import com.dunbian.jujiabao.appobj.extend.SeckillAO;
import com.dunbian.jujiabao.appobj.extend.SeckillInstanceAO;
import com.dunbian.jujiabao.appobj.extend.SeckillResultAO;
import com.dunbian.jujiabao.appobj.extend.UserAO;
import com.dunbian.jujiabao.appobj.extend.UserAddressAO;
import com.dunbian.jujiabao.framework.jdbc.ParamReactor;
import com.dunbian.jujiabao.framework.jdbc.QueryReactor;
import com.dunbian.jujiabao.framework.jdbc.SimpleJDBCTemplate;
import com.dunbian.jujiabao.framework.obj.Result;
import com.dunbian.jujiabao.service.good.IGoodTypeService;
import com.dunbian.jujiabao.service.order.IOrderService;

@Service
public class SecKillService implements ISeckillService {

	@Resource
	private DataSource dataSource;
	
	@Resource
	private IOrderService orderService;
	
	@Resource
	private IGoodTypeService goodTypeService;
	
	@Override
	@Transactional(readOnly=true)
	public List<SeckillAO> getSeckillList(String town) {
		Connection con = null;
		try {
			if(town == null || "".equals(town.trim())) {
				town = AdvertisementShowAO.REGION_ALL;
			}
			con = DataSourceUtils.getConnection(dataSource);
			SimpleJDBCTemplate jdbcTool = new SimpleJDBCTemplate(con);
			List<SeckillAO> secList = jdbcTool.selectList("select id, name, memo, price, market_price, start_time, end_time, logo,type, inventory_web, inventory_phone, inventory_share  from t_seckill ", new QueryReactor<SeckillAO>() {

				@Override
				public void setParam(PreparedStatement ps, Object... params)
						throws SQLException {
					String region = (String)params[0];
					
//					ps.setTimestamp(1, new java.sql.Timestamp(System.currentTimeMillis()));
//					ps.setTimestamp(2, new java.sql.Timestamp(System.currentTimeMillis()));
//					ps.setString(3, SeckillAO.STATUS_NOMARL);
//					ps.setString(4, AdvertisementShowAO.REGION_ALL);
//					ps.setString(5, region);
				}

				@Override
				public SeckillAO pack(ResultSet rs) throws SQLException {
					SeckillAO sec = new SeckillAO();
					sec.setId(rs.getString("id"));
					sec.setName(rs.getString("name"));
					sec.setMemo(rs.getString("memo"));
					sec.setPrice(rs.getBigDecimal("price"));
					sec.setType(rs.getString("type"));
					sec.setStartTime(rs.getTimestamp("start_time"));
					sec.setEndTime(rs.getTimestamp("end_time"));
					sec.setLogo(rs.getString("logo"));
					sec.setMarketPrice(rs.getBigDecimal("market_price"));
					int inventory = rs.getInt("inventory_phone") + rs.getInt("inventory_share") + rs.getInt("inventory_web");
					sec.setInventory(inventory);
					
					return sec;
				}
			}, town);
			
			return secList;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			DataSourceUtils.releaseConnection(con, dataSource);
		}
	}

	@Override
	@Transactional(readOnly=true)
	public SeckillAO getSeckill(String id) {
		Connection con = null;
		try {
			con = DataSourceUtils.getConnection(dataSource);
			SimpleJDBCTemplate jdbcTool = new SimpleJDBCTemplate(con);
			SeckillAO sec = jdbcTool.selectObject("select id, name, memo, price, start_time, end_time, logo, type, question_web, answer_web, question_phone, answer_phone, md  from t_seckill where status=? and id=?", new QueryReactor<SeckillAO>() {

				@Override
				public void setParam(PreparedStatement ps, Object... params)
						throws SQLException {
					String id = (String)params[0];
					
					ps.setString(1, SeckillAO.STATUS_NOMARL);
					ps.setString(2, id);
				}

				@Override
				public SeckillAO pack(ResultSet rs) throws SQLException {
					SeckillAO sec = new SeckillAO();
					sec.setId(rs.getString("id"));
					sec.setName(rs.getString("name"));
					sec.setMemo(rs.getString("memo"));
					sec.setPrice(rs.getBigDecimal("price"));
					sec.setStartTime(rs.getTimestamp("start_time"));
					sec.setEndTime(rs.getTimestamp("end_time"));
					sec.setLogo(rs.getString("logo"));
					sec.setType(rs.getString("type"));
					sec.setQuestionWeb(rs.getString("question_web"));
					sec.setQuestionPhone(rs.getString("question_phone"));
					sec.setAnswerWeb(rs.getString("answer_web"));
					sec.setAnswerPhone(rs.getString("answer_phone"));
					sec.setMd(rs.getString("md"));
					
					return sec;
				}
			}, id);
			
			return sec;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			DataSourceUtils.releaseConnection(con, dataSource);
		}
	}

	@Override
	@Transactional(rollbackFor=Throwable.class)
	public Result<OrderAO> kill(String inventoryType, UserAO user, SeckillAO sec, OrderExtraAO orderExtra) {
		Connection con = null;
		try {
			con = DataSourceUtils.getConnection(dataSource);
			SimpleJDBCTemplate jdbcTool = new SimpleJDBCTemplate(con);
			
			String id = jdbcTool.selectObject("select id from t_seckill_instance where seckill_id=? and inventory_type in (?,?) and status=? order by RAND() limit 1", new QueryReactor<String>() {

				@Override
				public void setParam(PreparedStatement ps, Object... params)
						throws SQLException {
					String id = (String)params[0];
					String inventoryType = (String)params[1];
					
					ps.setString(1, id);
					ps.setString(2, inventoryType);
					ps.setString(3, SeckillInstanceAO.INVENTORY_TYPE_SHARE);
					ps.setString(4, SeckillInstanceAO.STATUS_INIT);
				}

				@Override
				public String pack(ResultSet rs) throws SQLException {
					return rs.getString(1);
				}
			}, sec.getId(), inventoryType);
			
			if(id == null) {
				return new Result<>(false, "秒杀失败:已秒完", 400);
			}
			
			UserAddressAO userAddress = jdbcTool.selectObject("select * from t_user_address where user_id=? and status=?", new QueryReactor<UserAddressAO>() {

				@Override
				public void setParam(PreparedStatement ps, Object... params)
						throws SQLException {
					ps.setString(1, (String)params[0]);
					ps.setString(2, UserAddressAO.STATUS_DEFAULT);
				}

				@Override
				public UserAddressAO pack(ResultSet rs) throws SQLException {
					UserAddressAO address = new UserAddressAO();
					address.setBlockId(rs.getString("block_id"));
					address.setBlockName(rs.getString("block_name"));
					address.setBuildingId(rs.getString("building_id"));
					address.setBuildingName(rs.getString("building_name"));
					address.setCellId(rs.getString("cell_id"));
					address.setCellName(rs.getString("cell_name"));
					address.setCityId(rs.getString("city_id"));
					address.setCityName(rs.getString("city_name"));
					address.setCountyId(rs.getString("county_id"));
					address.setCountyName(rs.getString("county_name"));
					address.setFloor(rs.getInt("floor"));
					address.setManualAddress(rs.getString("manual_address"));
					address.setMobile(rs.getString("mobile"));
					address.setName(rs.getString("name"));
					address.setTownId(rs.getString("town_id"));
					address.setTownName(rs.getString("town_name"));
					address.setUserId(rs.getString("user_id"));
					address.setId(rs.getString("id"));
					
					return address;
				}
			}, user.getId());
			
			if(userAddress == null) {
				return new Result<>(false, "您尚未设置默认地址，请设置默认地址后再 参与秒杀", 500);
			}
			
			Result<Boolean> chk = orderService.validateAddress(userAddress);
			if(chk != null && !chk.isSuccess()) {
				return new Result<>(false, "对不起，您设置的默认地址超出我们的派送范围", 500);
			}
			
			/*锁定当前用户*/
			jdbcTool.selectObject("select id from t_user where id=? for update", new QueryReactor<String>() {

				@Override
				public void setParam(PreparedStatement ps, Object... params)
						throws SQLException {
					String id = (String)params[0];
					
					ps.setString(1, id);
				}

				@Override
				public String pack(ResultSet rs) throws SQLException {
					return null;
				}
			}, user.getId());
			
			String existsId = jdbcTool.selectObject("select id from t_seckill_result where user_id=? and sec_id=?", new QueryReactor<String>() {

				@Override
				public void setParam(PreparedStatement ps, Object... params)
						throws SQLException {
					String id = (String)params[0];
					String secId = (String)params[1];
					
					ps.setString(1, id);
					ps.setString(2, secId);
				}

				@Override
				public String pack(ResultSet rs) throws SQLException {
					return rs.getString(1);
				}
			}, user.getId(), sec.getId());
			
			if(existsId != null && !"".equals(existsId.trim())) {
				return new Result<>(false, "秒杀失败，您已参与过本次秒杀", 600);
			}
			
			id = jdbcTool.selectObject("select id from t_seckill_instance where id=? and status=? for update", new QueryReactor<String>() {

				@Override
				public void setParam(PreparedStatement ps, Object... params)
						throws SQLException {
					String id = (String)params[0];
					
					ps.setString(1, id);
					ps.setString(2, SeckillInstanceAO.STATUS_INIT);
				}

				@Override
				public String pack(ResultSet rs) throws SQLException {
					return rs.getString(1);
				}
			}, id);
			
			if(id == null) {
				return new Result<OrderAO>(false, "秒杀失败");
			}
			
			if(goodTypeService.getFoodTypeCache(sec.getType()) != null) {
				return kill(id, user, sec, userAddress, orderExtra);
			}
			
			return new Result<OrderAO>(false, "秒杀失败");
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			DataSourceUtils.releaseConnection(con, dataSource);
		}
	}
	
	private String getDay(String time) {
		String ret = null;
		Calendar cl = Calendar.getInstance();
		if(FoodSetAO.TYPE_TOMORROW.equals(time)) {//明天
			cl.add(Calendar.DAY_OF_MONTH, 1);
		}
		int tmp = cl.get(Calendar.DAY_OF_WEEK);
		switch (tmp) {
		case Calendar.MONDAY:
			ret = FoodWeekAO.DAY_MONDAY;
			break;
		case Calendar.TUESDAY:
			ret = FoodWeekAO.DAY_TUESDAY;
			break;
		case Calendar.WEDNESDAY:
			ret = FoodWeekAO.DAY_WEDNESDAY;
			break;
		case Calendar.THURSDAY:
			ret = FoodWeekAO.DAY_THURSDAY;
			break;
		case Calendar.FRIDAY:
			ret = FoodWeekAO.DAY_FRIDAY;
			break;
		case Calendar.SATURDAY:
			ret = FoodWeekAO.DAY_SATURDAY;
			break;
		case Calendar.SUNDAY:
			ret = FoodWeekAO.DAY_SUNDAY;
			break;
		default:
			break;
		}
		return ret;
	}
	
	@Transactional(rollbackFor=Throwable.class)
	private Result<OrderAO> kill(String id, UserAO user, SeckillAO sec, UserAddressAO userAddress, OrderExtraAO orderExtra) {
		Connection con = null;
		try {
			con = DataSourceUtils.getConnection(dataSource);
			SimpleJDBCTemplate jdbcTool = new SimpleJDBCTemplate(con);
			
			FoodSetAO set = jdbcTool.selectObject("select a.id, a.sku_id, a.name, a.logo  from t_food_set a, t_food_week b where a.id=b.set_id and b.day=? and b.type=?", new QueryReactor<FoodSetAO>() {

				@Override
				public void setParam(PreparedStatement ps, Object... params)
						throws SQLException {
					String day = (String)params[0];
					String type = (String)params[1];
					
					ps.setString(1, day);
					ps.setString(2, type);
				}

				@Override
				public FoodSetAO pack(ResultSet rs) throws SQLException {
					FoodSetAO set = new FoodSetAO();
					set.setId(rs.getString("id"));
					set.setSkuId(rs.getLong("sku_id"));
					set.setName(rs.getString("name"));
					set.setLogo(rs.getString("logo"));
					
					return set;
				}
			}, getDay(FoodSetAO.TYPE_TODAY), sec.getType());
			
			Result<OrderAO> ret = orderService.submitOrder4Seckill(user, set, sec, userAddress, orderExtra);
			
			if(ret == null || ret.getData() == null) {
				return new Result<OrderAO>(false, "秒杀失败");
			}
			
			String resultId = jdbcTool.selectObject("select uuid_short()", new QueryReactor<String>() {

				@Override
				public void setParam(PreparedStatement ps, Object... params)
						throws SQLException {
				}

				@Override
				public String pack(ResultSet rs) throws SQLException {
					return rs.getString(1);
				}
			});
			
			SeckillResultAO secResult = new SeckillResultAO();
			secResult.setId(resultId);
			secResult.setOrderId(ret.getData().getId());
			secResult.setSecDate(new Date());
			secResult.setSecId(sec.getId());
			secResult.setSecInstanceId(id);
			secResult.setSecTime(new Date());
			secResult.setUserId(user.getId());
			
			jdbcTool.executeUpdate("insert into t_seckill_result(id, order_id, sec_date, sec_id, sec_instance_id, sec_time, user_id) values(?, ?, ?, ?, ?, ?, ?)", new ParamReactor() {
				
				@Override
				public void setParam(PreparedStatement ps, Object... params)
						throws SQLException {
					SeckillResultAO secResult = (SeckillResultAO)params[0];
					ps.setString(1, secResult.getId());
					ps.setString(2, secResult.getOrderId());
					ps.setDate(3, new java.sql.Date(secResult.getSecDate().getTime()));
					ps.setString(4, secResult.getSecId());
					ps.setString(5, secResult.getSecInstanceId());
					ps.setTimestamp(6, new java.sql.Timestamp(secResult.getSecTime().getTime()));
					ps.setString(7, secResult.getUserId());
				}
			}, secResult);
			
			jdbcTool.executeUpdate("update t_seckill_instance set status=? where id=?", new ParamReactor() {
				
				@Override
				public void setParam(PreparedStatement ps, Object... params)
						throws SQLException {
					ps.setString(1, SeckillInstanceAO.STATUS_KILLED);
					ps.setString(2, (String)params[0]);
				}
			}, id);
			
			ret.setCode(100);
			return ret;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			DataSourceUtils.releaseConnection(con, dataSource);
		}
	}

}

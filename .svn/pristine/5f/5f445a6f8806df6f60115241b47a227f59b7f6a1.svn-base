package com.dunbian.jujiabao.service.order;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dunbian.jujiabao.appobj.extend.CartAO;
import com.dunbian.jujiabao.appobj.extend.DiscountAO;
import com.dunbian.jujiabao.appobj.extend.DiscountRecordAO;
import com.dunbian.jujiabao.appobj.extend.FoodSetAO;
import com.dunbian.jujiabao.appobj.extend.FoodWeekAO;
import com.dunbian.jujiabao.appobj.extend.OrderAO;
import com.dunbian.jujiabao.appobj.extend.OrderDetailAO;
import com.dunbian.jujiabao.appobj.extend.OrderExtraAO;
import com.dunbian.jujiabao.appobj.extend.PayLogAO;
import com.dunbian.jujiabao.appobj.extend.RegionAO;
import com.dunbian.jujiabao.appobj.extend.RegionStatusAO;
import com.dunbian.jujiabao.appobj.extend.RegionTimeAO;
import com.dunbian.jujiabao.appobj.extend.SeckillAO;
import com.dunbian.jujiabao.appobj.extend.UserAO;
import com.dunbian.jujiabao.appobj.extend.UserAddressAO;
import com.dunbian.jujiabao.appobj.generator.CartExample;
import com.dunbian.jujiabao.appobj.generator.DiscountRecordExample;
import com.dunbian.jujiabao.appobj.generator.FoodSetExample;
import com.dunbian.jujiabao.appobj.generator.FoodWeekExample;
import com.dunbian.jujiabao.appobj.generator.OrderDetailExample;
import com.dunbian.jujiabao.appobj.generator.OrderExample;
import com.dunbian.jujiabao.appobj.generator.RegionExample;
import com.dunbian.jujiabao.appobj.generator.RegionStatusExample;
import com.dunbian.jujiabao.appobj.generator.RegionTimeExample;
import com.dunbian.jujiabao.db.customer.FoodSetCustomMapper;
import com.dunbian.jujiabao.db.customer.FoodWeekCustomMapper;
import com.dunbian.jujiabao.db.customer.OrderCustomMapper;
import com.dunbian.jujiabao.db.generator.CartMapper;
import com.dunbian.jujiabao.db.generator.DiscountRecordMapper;
import com.dunbian.jujiabao.db.generator.FoodSetMapper;
import com.dunbian.jujiabao.db.generator.FoodWeekMapper;
import com.dunbian.jujiabao.db.generator.OrderDetailMapper;
import com.dunbian.jujiabao.db.generator.OrderExtraMapper;
import com.dunbian.jujiabao.db.generator.OrderMapper;
import com.dunbian.jujiabao.db.generator.RegionMapper;
import com.dunbian.jujiabao.db.generator.RegionStatusMapper;
import com.dunbian.jujiabao.db.generator.RegionTimeMapper;
import com.dunbian.jujiabao.db.generator.UserAddressMapper;
import com.dunbian.jujiabao.db.generator.UserMapper;
import com.dunbian.jujiabao.framework.jdbc.ParamReactor;
import com.dunbian.jujiabao.framework.jdbc.QueryReactor;
import com.dunbian.jujiabao.framework.jdbc.SimpleJDBCTemplate;
import com.dunbian.jujiabao.framework.obj.Page;
import com.dunbian.jujiabao.framework.obj.Result;
import com.dunbian.jujiabao.framework.util.DateTimeUtil;
import com.dunbian.jujiabao.framework.util.SequenceUtil;
import com.dunbian.jujiabao.service.pay.IPayLogService;
import com.dunbian.jujiabao.service.recharge.IRechargeService;

@Service
public class OrderService implements IOrderService {

	@Resource
	private UserAddressMapper userAddressMapper;
	
	@Resource
	private CartMapper cartMapper;
	
	@Resource
	private OrderExtraMapper orderExtraMapper;
	
	@Resource
	private FoodSetMapper foodSetMapper;
	
	@Resource
	private FoodSetCustomMapper foodSetCustomMapper;
	
	@Resource
	private FoodWeekCustomMapper foodWeekCustomMapper;
	
	@Resource
	private OrderMapper orderMapper;
	
	@Resource
	private OrderDetailMapper orderDetailMapper;
	
	@Resource
	private OrderCustomMapper orderCustomMapper;
	
	@Resource
	private RegionTimeMapper regionTimeMapper;
	
	@Resource
	private RegionMapper regionMapper;
	
	@Resource
	private UserMapper userMapper;
	
	@Resource
	private FoodWeekMapper foodWeekMapper;
	
	@Resource
	RegionStatusMapper regionStatusMapper;
	
	
	/*
	@Resource
	private ConfigUtil configUtil;
	
	@Resource
	private ISMSService smsService;
	*/
	
	@Resource
	private DiscountRecordMapper discountRecordMapper;
	
	@Resource
	private IRechargeService rechargeService;
	
	@Resource
	private IPayLogService payLogService;
	
	@Override
	@Transactional(rollbackFor=Throwable.class)
	public Result<OrderAO> submitOrder(String addressId, String[] cartArr,
			UserAO user, OrderExtraAO orderExtra, List<String> discountList) {
		List<CartAO> cartList = getCartList(cartArr);
		if(cartList == null || cartList.isEmpty()) {
			return new Result<>(false, "购物车中无商品，请刷新后重新提交订单", 100);
		}
		
		if(!validateCart(cartList)) {
			clearOverdueCart(user.getId());
			return new Result<>(false, "购物车中商品已过期，请刷新后重新提交订单", 200);
		}
		
		if(!checkCartUser(user.getId(), cartList)) {
			return new Result<>(false, "购物车信息错误，请刷新后重新提交订单", 300);
		}
		
		if(!findFoodSet(cartList)) {
			return new Result<>(false, "您要购买的套餐不存在，请刷新后重新提交订单", 400);
		}
		
		if(addressId == null) {
			return new Result<>(false, "下单地址不存在，请刷新后重新提交订单", 500);
		}
		
		UserAddressAO address = getUserAddress(addressId);
		if(address == null) {
			return new Result<>(false, "下单地址不存在，请刷新后重新提交订单", 500);
		}
		
		Result<Boolean> ack = checkOrderTime(address);
		if(ack != null && !ack.isSuccess()) {
			return new Result<OrderAO>(false, ack.getMsg(), 600);
		}
		
		Result<Boolean> tmp = checkOrderForCommuty(address, user, cartList);
		if(ack != null && !tmp.isSuccess()) {
			return new Result<OrderAO>(false, tmp.getMsg(), 600);
		}
		
		ack = validateAddress(address);
		if(ack != null && !ack.isSuccess()) {
			return new Result<OrderAO>(false, ack.getMsg(), 700);
		}
		
		Result<Boolean> crs = checkRegionStatus(address);
		if(crs != null && !crs.isSuccess()) {
			return new Result<OrderAO>(false, crs.getMsg(), 1000);
		}
		
		//TODO 这里检查库存量，目前的代码逻辑只支持今天订今日的餐品，如果后续头一天订第二天的餐品，则需要修改这里的逻辑
		ack = checkDayCount(cartList);
		if(ack != null && !ack.isSuccess()) {
			return new Result<OrderAO>(false, ack.getMsg(), 800);
		}
		
//		ack = checkActivity(user.getId(), cartList);
//		if(ack != null && !ack.isSuccess()) {
//			return new Result<OrderAO>(false, ack.getMsg(), 800);
//		}
		
		List<DiscountAO> disList = null;
		if(discountList != null) {
			Result<List<DiscountAO>> disAck = checkDiscount(user, discountList);
			if(disAck != null && !disAck.isSuccess()) {
				return new Result<OrderAO>(false, disAck.getMsg(), 900);
			} else {
				disList = disAck.getData();
			}
		}
		
		OrderAO order = genOrder(address, cartList, user, orderExtra, disList);
		saveExtra(orderExtra);
		return new Result<>(order);
	}
	
	
	private DiscountAO getDiscountById(String id) {
		Connection con = null;
		try {
			con = DataSourceUtils.getConnection(dataSource);
			SimpleJDBCTemplate jdbcTool = new SimpleJDBCTemplate(con);
			DiscountAO data = jdbcTool.selectObject("select * from t_discount where id=?", new QueryReactor<DiscountAO>() {

				@Override
				public void setParam(PreparedStatement ps, Object... params)
						throws SQLException {
					String id = (String)params[0];
                    
					ps.setString(1, id);
				}

				@Override
				public DiscountAO pack(ResultSet rs) throws SQLException {
					DiscountAO discount = new DiscountAO();
					discount.setId(rs.getString("id"));
					discount.setName(rs.getString("name"));
					discount.setType(rs.getString("type"));
					discount.setDiscountAmount(rs.getBigDecimal("discount_amount"));
					discount.setRegion(rs.getString("region"));
					discount.setStartDate(rs.getDate("start_date"));
					discount.setEndDate(rs.getDate("end_date"));
					discount.setStatus(rs.getString("status"));
					discount.setLimit(rs.getInt("limit"));
					discount.setUserLimit(rs.getInt("user_limit"));
					discount.setJoin(rs.getInt("join"));
					
					return discount;
				}
			}, id);

			return data;
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			DataSourceUtils.releaseConnection(con, dataSource);
		}
	}
	
	private Result<List<DiscountAO>> checkDiscount(UserAO user, List<String> disList) {
		List<DiscountAO> ret = new ArrayList<>();
		for(String disId : disList){
			DiscountAO dis = getDiscountById(disId);
			
			if(dis == null || dis.getJoin() >= dis.getLimit()) {
				return new Result<>(false, "对不起，您购物车中的优惠活动已经结束，请刷新购物车后重新提交");
			}
			
			DiscountRecordExample dre = new DiscountRecordExample();
			dre.createCriteria().andDiscountIdEqualTo(dis.getId()).andUserIdEqualTo(user.getId());
			int cs = discountRecordMapper.countByExample(dre);
			if(cs >= dis.getUserLimit()) {
				return new Result<>(false, "对不起，您购物车中的优惠信息已经发生变化，请刷新购物车后重新提交");
			}
			
			ret.add(dis);
		}
		
		return new Result<>(ret);
	}
	
	private Result<Boolean> checkDayCount(List<CartAO> cartList) {
		if(cartList.isEmpty()) {
			return new Result<Boolean>(false, "购物车中无商品！");
		}
		
		Map<String, Integer> todayRemainCount = getTodayRemainCount();
		boolean canOrder = true;
		String msg = "您订购的餐品数量不足：";
		for(CartAO cart : cartList) {
			if(cart != null && cart.getSetId() != null && todayRemainCount.get(cart.getSetId()) != null) {
				int remainCount = 999;
				if(todayRemainCount.get(cart.getSetId()) != null) {
					remainCount = todayRemainCount.get(cart.getSetId());
				}
				if(cart.getCount() > remainCount) {
					canOrder = false;
					msg += cart.getTypeName() + "剩余" + remainCount + "份；";
				}
			}
		}
		
		if(!canOrder) {
			return new Result<Boolean>(false, msg);
		}
		return new Result<Boolean>(true);
	}
	
	private Map<String, Integer> getTodayRemainCount() {
		String weekDay = getTodayWeekDay();
		FoodWeekExample example = new FoodWeekExample();
		example.createCriteria().andDayEqualTo(weekDay);
		List<FoodWeekAO> foodWeeks = foodWeekMapper.selectByExample(example);
		
		Map<String, Integer> ret = new HashMap<String, Integer>();
		for(FoodWeekAO tmpFoodWeekAO : foodWeeks) {
			if(tmpFoodWeekAO != null && tmpFoodWeekAO.getId() != null) {
				int remainCount = 999;
				if(tmpFoodWeekAO.getDayCount() != null) {
					remainCount = tmpFoodWeekAO.getDayCount();
				}
				if(tmpFoodWeekAO.getDaySale() != null) {
					remainCount -= tmpFoodWeekAO.getDaySale();
				}
				ret.put(tmpFoodWeekAO.getSetId(), remainCount);
			}
		}
		return ret;
	}
	
	private String getTodayWeekDay() {
		String weekDay = null;
		Calendar cl = Calendar.getInstance();
		int tmp = cl.get(Calendar.DAY_OF_WEEK);
		switch (tmp) {
		case Calendar.MONDAY:
			weekDay = FoodWeekAO.DAY_MONDAY;
			break;
		case Calendar.TUESDAY:
			weekDay = FoodWeekAO.DAY_TUESDAY;
			break;
		case Calendar.WEDNESDAY:
			weekDay = FoodWeekAO.DAY_WEDNESDAY;
			break;
		case Calendar.THURSDAY:
			weekDay = FoodWeekAO.DAY_THURSDAY;
			break;
		case Calendar.FRIDAY:
			weekDay = FoodWeekAO.DAY_FRIDAY;
			break;
		case Calendar.SATURDAY:
			weekDay = FoodWeekAO.DAY_SATURDAY;
			break;
		case Calendar.SUNDAY:
			weekDay = FoodWeekAO.DAY_SUNDAY;
			break;
		default:
			break;
		}
		return weekDay;
	}
	
	/*
	private Result<Boolean> checkActivity(String userId, List<CartAO> cartList) {
		boolean isActivity = false;
		int orderLimit = configUtil.getOrderLimtCount();
		
		for(CartAO cart : cartList) {
			if(cart.getFoodSet().getPrice().compareTo(new BigDecimal(3)) < 0) {
				isActivity = true;
				break;
			}
		}
		
		if(!isActivity){
			return new Result<Boolean>(true);
		}
		
		if(cartList.size() != 1) {
			return new Result<Boolean>(false, "对不起，1元订餐活动期间，您只能订购一份，如果您身边的人有订餐需要，建议注册居家宝账号进行订购");
		}
		
		if(cartList.get(0).getCount() > 1) {
			return new Result<Boolean>(false, "对不起，1元订餐活动期间，您只能订购一份，如果您身边的人有订餐需要，建议注册居家宝账号进行订购");
		}
		
		OrderExample oe = new OrderExample();
		oe.createCriteria().andUserIdEqualTo(userId).andCreateDateEqualTo(new Date()).andStatusNotEqualTo(OrderAO.STATUS_DELETED).andStatusNotEqualTo(OrderAO.STATUS_CANCELED);
		int cnt = orderMapper.countByExample(oe);
		
		if(cnt > 0) {
			return new Result<Boolean>(false, "对不起，您今天已经订购过1元优惠套餐，不能重复订购，欢迎您明天订购");
		}
		
		oe = new OrderExample();
		oe.createCriteria().andCreateDateEqualTo(new Date()).andStatusNotEqualTo(OrderAO.STATUS_DELETED).andStatusNotEqualTo(OrderAO.STATUS_CANCELED);
		cnt = orderMapper.countByExample(oe);
		
		if(cnt >= orderLimit) {
			return new Result<Boolean>(false, "对不起，您晚来一步，今天的套餐已经订购一空，欢迎您明天订购");
		}
		
		return new Result<Boolean>(true);
	}
	*/
	
	public Result<Boolean> validateAddress(UserAddressAO address) {
		String county = address.getCountyId();
		String town = address.getTownId();
		String block = address.getBlockId();
		String building = address.getBuildingId();
		String cell = address.getCellId();
		
		List<String> ids = new ArrayList<>();
		add2List(county, ids);
		add2List(town, ids);
		add2List(block, ids);
		add2List(building, ids);
		add2List(cell, ids);
		
		RegionExample re = new RegionExample();
		re.setOrderByClause("location");
		re.createCriteria().andIdIn(ids);
		List<RegionAO> regList = regionMapper.selectByExample(re);
		if(regList != null) {
			for(RegionAO region : regList) {
				if(!RegionAO.STATUS_NORMAL.equals(region.getStatus())) {
					return new Result<Boolean>(false, "对不起,您所选择的订餐区域[" + region.getName() + "]我们暂未覆盖，我们正加紧覆盖中，欢迎下次订购");
				}
			}
		}
		
		return new Result<Boolean>(true);
	}
	
	private void add2List(String id, List<String> ids) {
		if(id != null && !"".equals(id.trim())) {
			ids.add(id);
		}
	}
	
	private Result<Boolean> checkOrderTime(UserAddressAO address) {
		RegionTimeExample rte = new RegionTimeExample();
		rte.createCriteria().andRegionIdEqualTo(address.getTownId());
		
		List<RegionTimeAO> timeList = regionTimeMapper.selectByExample(rte);
		if(timeList == null || timeList.isEmpty()) {
			return new Result<Boolean>(true);
		}
		
		String now = DateTimeUtil.format(new Date(), "HH:mm:ss");
		RegionTimeAO region = timeList.get(0);
		String start = null;
		String end = null;
		if(region.getStartTime() != null) {
			start = DateTimeUtil.format(region.getStartTime(), "HH:mm:ss");
		}
		
		if(region.getEndTime() != null) {
			end = DateTimeUtil.format(region.getEndTime(), "HH:mm:ss");
		}
		
		if(start != null && start.compareTo(now) > 0) {
			return new Result<Boolean>(false, "尚未开始订餐");
		}
		
		if(end != null && end.compareTo(now) < 0) {
			return new Result<Boolean>(false, "很遗憾，今日订餐时间已过，欢迎您下次订购");
		}
		
		return new Result<Boolean>(true);
	}
	
	//检查小区订单，小区地址不允许预订15元以下餐品
	private Result<Boolean> checkOrderForCommuty(UserAddressAO address, UserAO user, List<CartAO> cartList) {
	   
		RegionAO region = regionMapper.selectByPrimaryKey(address.getBlockId());
		
		if(RegionAO.TYPE_COMMUNITY.equals(region.getType())) { //小区地址，检查购物车的商品有无15元以内的
			for(CartAO cart : cartList) {
				if(cart.getFoodSet().getPrice().doubleValue() <=15) {
					return new Result<Boolean>(false, "小区目前不支持订购15元以下的餐品，请重新选择餐品！");
				}
			}
		}
		
		return new Result<Boolean>(true);
	}
	
	public RegionStatusAO getRegionStatus(String townId) {
		RegionStatusExample example = new RegionStatusExample();
		example.createCriteria().andRegionIdEqualTo(townId);
		List<RegionStatusAO> dataList = regionStatusMapper.selectByExample(example);
		if(dataList != null && !dataList.isEmpty()) { //如果存在该地区对应配置，直接返回
			return dataList.get(0);
		}
		return null;//找不到任何配置则返回空
	}
	
	private Result<Boolean> checkRegionStatus(UserAddressAO address) {
		//首先判断全部地区的配置
		RegionStatusAO regionStatus = getRegionStatus("0");
		if(regionStatus != null && RegionStatusAO.STATUS_DISABLE.equals(regionStatus.getStatus())) {
			return new Result<Boolean>(false, regionStatus.getComment());
		} else {//如果全部地区没有配置或者是全部启用状态，则看具体片区
			regionStatus = getRegionStatus(address.getTownId());
			if(regionStatus != null && RegionStatusAO.STATUS_DISABLE.equals(regionStatus.getStatus())) {
				return new Result<Boolean>(false, regionStatus.getComment());
			}
		}
		return new Result<Boolean>(true);
	}
	
	private boolean findFoodSet(List<CartAO> cartList) {
		List<String> setList = new ArrayList<>();
		for(CartAO cart : cartList) {
			setList.add(cart.getSetId());
		}
		
		FoodSetExample fse = new FoodSetExample();
		fse.createCriteria().andIdIn(setList);
		List<FoodSetAO> foodSetList = foodSetMapper.selectByExample(fse);
		Map<String, FoodSetAO> tmp = null;
		if(foodSetList != null) {
			tmp = new TreeMap<>();
			for(FoodSetAO set : foodSetList) {
				tmp.put(set.getId(), set);
			}
		}
		
		boolean flag = true;
		for(CartAO cart : cartList) {
			if(tmp != null) {
				cart.setFoodSet(tmp.get(cart.getSetId()));
			}
			
			if(cart.getFoodSet() == null) {
				clearInvalidCart(cart.getId());
				flag &= false;
			}
		}
		
		return flag;
	}
	
	private void clearInvalidCart(String id) {
		cartMapper.deleteByPrimaryKey(id);
	}
	
	private String genOrderNo() {
		int day = Integer.parseInt(DateTimeUtil.format(new Date(), "yyyyMMdd"));
		long seq = SequenceUtil.getSequenceNo(SequenceUtil.SEQUENCE_TYPE_ORDER, day);
		String str = String.valueOf(seq);
		
		StringBuffer sb = new StringBuffer();
		sb.append(day);
		
		int rand = ((int)(100000*Math.random()))%1000;
		String sr = String.valueOf(rand);
		for(int i=0; i < 3 - sr.length(); i++) {
			sb.append("0");
		}
		sb.append(sr);
		
		for(int i=0; i < 6 - str.length(); i++) {
			sb.append("0");
		}
		
		sb.append(str);

		return sb.toString();
	}
	
//	private boolean isFirstOrder(String userId) {
//		OrderExample oe = new OrderExample();
//		oe.createCriteria().andUserIdEqualTo(userId).andStatusNotEqualTo(OrderAO.STATUS_CANCELED).andStatusNotEqualTo(OrderAO.STATUS_DELETED);
//		
//		return orderMapper.countByExample(oe) == 0;
//	}
	
	private BigDecimal caculateAmount4Discount(BigDecimal amount, List<DiscountAO> discountList) {
		if(discountList == null || discountList.isEmpty()) {
			return amount;
		}
		
		for(DiscountAO dis : discountList) {
			amount = amount.subtract(dis.getDiscountAmount());
		}
		
		if(amount.compareTo(new BigDecimal(1)) < 0) {
			amount = new BigDecimal(1);
		}
		
		return amount;
	}
	
	private void recordDiscount(String orderId, UserAO user, List<DiscountAO> discountList) {
		if(discountList != null && !discountList.isEmpty()) {
			for(DiscountAO dis : discountList) {
				DiscountRecordAO rec = new DiscountRecordAO();
				rec.setCreateTime(new Date());
				rec.setDiscountAmount(dis.getDiscountAmount());
				rec.setDiscountId(dis.getId());
				rec.setOrderId(orderId);
				rec.setUserId(user.getId());
				
				discountRecordMapper.insert(rec);
				
				updateDiscountJoin(dis.getId());
			}
		}
	}
	
	private void updateDiscountJoin(String id) {
		Connection con = null;
		try {
			con = DataSourceUtils.getConnection(dataSource);
			SimpleJDBCTemplate jdbcTool = new SimpleJDBCTemplate(con);
			jdbcTool.executeUpdate("update t_discount set `join` = `join` + 1 where id=?", new ParamReactor() {
				
				@Override
				public void setParam(PreparedStatement ps, Object... params)
						throws SQLException {
					ps.setString(1, (String)params[0]);
				}
			}, id);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			DataSourceUtils.releaseConnection(con, dataSource);
		}
	}
	
	private OrderAO genOrder(UserAddressAO address, List<CartAO> cartList, UserAO user, OrderExtraAO orderExtra, List<DiscountAO> discountList) {
		BigDecimal amount = new BigDecimal(0);
		int count = 0;
		for(CartAO cart : cartList) {
			if(cart.getCount() <= 0) {
				continue;
			}
			
			count += cart.getCount();
			amount = amount.add(cart.getFoodSet().getPrice().multiply(new BigDecimal(cart.getCount())));
		}
		
		if(discountList != null && !discountList.isEmpty()) {
			amount = caculateAmount4Discount(amount, discountList);
		}
//		else {
//			if(isFirstOrder(user.getId())) {
//				amount = amount.subtract(new BigDecimal(8.8));
//				if(amount.compareTo(new BigDecimal(1)) < 0) {
//					amount = new BigDecimal(1);
//				}
//			}
//		}

		OrderAO order = new OrderAO();
		order.setAddressId(address.getId());
		order.setAddress(address.getAddress());
		order.setAmount(amount);
		order.setCityId(address.getCityId());
		order.setCityName(address.getCityName());
		order.setCountyId(address.getCountyId());
		order.setCountyName(address.getCountyName());
		order.setTownId(address.getTownId());
		order.setTownName(address.getTownName());
		order.setBlockId(address.getBlockId());
		order.setBlockName(address.getBlockName());
		order.setBuildingId(address.getBuildingId());
		order.setBuildingName(address.getBuildingName());
		order.setCellId(address.getCellId());
		order.setCellName(address.getCellName());
		order.setCount(count);
		order.setCreateDate(new Date());
		order.setCreateTime(new Date());
		order.setFloor(address.getFloor());
		order.setManualAddress(address.getManualAddress());
		order.setMobile(address.getMobile());
		order.setName(address.getName());
		order.setToPay(amount);
		
		//处理location
		String retionId = "";
		if(address.getCellId() != null && !address.getCellId().isEmpty()) {
			retionId = address.getCellId();
		} else if(address.getBuildingId() != null && !address.getBuildingId().isEmpty()) {
			retionId = address.getBuildingId();
		} else if(address.getBlockId() != null && !address.getBlockId().isEmpty()) {
			retionId = address.getBlockId();
		} else if(address.getTownId() != null && !address.getTownId().isEmpty()) {
			retionId = address.getTownId();
		}
		RegionAO region = regionMapper.selectByPrimaryKey(retionId);
		if(region != null) {
			order.setLocation(region.getLocation());
		}
		
		order.setOrderFrom(orderExtra.getOrderFrom());
		order.setOrderNo(genOrderNo());
		order.setPaymentTime(null);
		order.setStatus(OrderAO.STATUS_ORDERED);
		order.setSetType(OrderAO.SET_TYPE_LUNCH);
		order.setTownId(address.getTownId());
		order.setUserId(user.getId());
		orderMapper.insert(order);
		
		order.setAddressInfo(address);
		orderExtra.setOrderId(order.getId());
		
		for(CartAO cart : cartList) {
			if(cart.getCount() <= 0) {
				continue;
			}
			OrderDetailAO detail = new OrderDetailAO();
			detail.setAmount(cart.getFoodSet().getPrice().multiply(new BigDecimal(cart.getCount())));
			detail.setCount(cart.getCount());
			detail.setCreateTime(new Date());
			detail.setLogo(cart.getFoodSet().getLogo());
			detail.setOrderId(order.getId());
			detail.setPrice(cart.getFoodSet().getPrice());
			detail.setSetId(cart.getSetId());
			detail.setSetTitle(cart.getFoodSet().getName());
			detail.setSkuId(cart.getFoodSet().getSkuId());
			detail.setType(cart.getType());
			
			orderDetailMapper.insert(detail);
			cartMapper.deleteByPrimaryKey(cart.getId());
			order.addDetail(detail);
		}
		
		recordDiscount(order.getId(), user, discountList);
		
		return order;
	}
	
	
	
	private void saveExtra(OrderExtraAO orderExtra) {
		orderExtraMapper.insert(orderExtra);
	}
	
	private void clearOverdueCart(String userId) {
		CartExample ce = new CartExample();
		ce.createCriteria().andUserIdEqualTo(userId).andCartDateNotEqualTo(new Date());
		cartMapper.deleteByExample(ce);
	}
	
	private boolean checkCartUser(String userId, List<CartAO> cartList) {
		for(CartAO cart : cartList) {
			if(cart.getUserId() == null || !cart.getUserId().equals(userId)) {
				return false;
			}
			
			if(cart.getCount() < 0) {
				return false;
			}
		}
		
		return true;
	}
	
	private boolean validateCart(List<CartAO> cartList) {
		String today = DateTimeUtil.format(new Date(), "yyyyMMdd");
		Calendar now = Calendar.getInstance();
		int day = now.get(Calendar.DAY_OF_WEEK);
		day--;
		if(day == 0) {
			day = 7;
		}
		
		day = day*10;
		
		boolean flag = true;
		for(CartAO cart : cartList) {
			String t = DateTimeUtil.format(cart.getCartDate(), "yyyyMMdd");
			if(!today.equals(t)) {
				flag = false;
			}
			
			FoodWeekExample fwe = new FoodWeekExample();
			fwe.createCriteria().andDayEqualTo(String.valueOf(day)).andSetIdEqualTo(cart.getSetId());
			int i = foodWeekMapper.countByExample(fwe);
			if(i == 0) {
				cartMapper.deleteByPrimaryKey(cart.getId());
				flag = false;
			}
		}
		
		return flag;
	}
	
	private List<CartAO> getCartList(String[] cartArr) {
		if(cartArr == null || cartArr.length == 0) {
			return null;
		}
		
		List<String> cartIds = new ArrayList<>();
		for(String id : cartArr) {
			cartIds.add(id);
		}
		
		CartExample ce = new CartExample();
		ce.createCriteria().andIdIn(cartIds);
		return cartMapper.selectByExample(ce);
	}
	
	private UserAddressAO getUserAddress(String addressId) {
		return userAddressMapper.selectByPrimaryKey(addressId);
	}

	@Override
	@Transactional(readOnly=true)
	public List<OrderAO> getOrderList(String userId, String status, Page page) {
		page.setPageSize(page.getPageSize() + 1);
		return orderCustomMapper.getOrderList(userId, status, page);
	}

	@Override
	@Transactional(readOnly=true)
	public int countOrder(String userId, String status) {
		OrderExample oe = new OrderExample();
		if(status != null) {
			if(OrderAO.STATUS_SEARCH_PAID.equals(status)) {
				List<String> statusList = new ArrayList<>();
				statusList.add(OrderAO.STATUS_CANCELED);
				statusList.add(OrderAO.STATUS_PACKED);
				statusList.add(OrderAO.STATUS_PAID);
				statusList.add(OrderAO.STATUS_RECEIVED);
				statusList.add(OrderAO.STATUS_REFUND);
				oe.createCriteria().andUserIdEqualTo(userId).andStatusIn(statusList);
			} else {
				oe.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(status);
			}
		} else {
			oe.createCriteria().andUserIdEqualTo(userId).andStatusNotEqualTo(OrderAO.STATUS_DELETED);
		}
		
		
		return orderMapper.countByExample(oe);
	}

	@Override
	@Transactional(rollbackFor=Throwable.class)
	public boolean payOrder(OrderAO order, BigDecimal payAmount) {
		//判断该笔订单是否在商户网站中已经做过处理
		//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
		//如果有做过处理，不执行商户的业务程序
		if(order == null || order.getOrderNo() == null || order.getOrderNo().isEmpty()) {
			return false;
		}
		 
		OrderAO oldOrder = orderCustomMapper.getOrderByOrderNo(order.getOrderNo());
		if(oldOrder == null) {
			return false;
		}
		if(oldOrder == null || OrderAO.STATUS_PAID.equals(oldOrder.getStatus())) {//状态已经是付款的，表示付过款的，就直接返回
			return false;
		} else if(payAmount != null && payAmount.compareTo(oldOrder.getToPay()) != 0) { //如果支付的金额和应付金额不等，则记录异常状态并返回，不改变订单状态
			PayLogAO log = new PayLogAO();
			log.setAmount(oldOrder.getToPay());
			log.setPayNo(oldOrder.getOrderNo());
			log.setCreateTime(new Date());
			log.setTitle("支付宝回调失败");
			log.setContent("支付宝回调过程中，支付宝付款的金额和应付金额不等！支付宝金额：" + payAmount + " topay:" + oldOrder.getToPay());
			payLogService.log(log);
			return false;
		}
		
//		//首先判断一下是否需要钱包支付
//		if(oldOrder.getToPay().compareTo(oldOrder.getAmount()) < 0) {//需要钱包支付的情况
			Boolean walletPay = rechargeService.callback4Pay(oldOrder).getData();
			if(!walletPay) {//如果钱包扣款失败，则记录log，并返回
				PayLogAO log = new PayLogAO();
				log.setAmount(oldOrder.getToPay());
				log.setPayNo(oldOrder.getOrderNo());
				log.setCreateTime(new Date());
				log.setTitle("支付宝回调失败");
				log.setContent("支付宝回调过程中，调用钱包支付方法callback4Pay返回失败 支付宝金额：" + payAmount + " topay:" + oldOrder.getToPay());
				payLogService.log(log);
				return false;
			}
//		}
		
		OrderExample example = new OrderExample();
		example.createCriteria().andOrderNoEqualTo(order.getOrderNo());
		int count = orderMapper.updateByExampleSelective(order, example);
		//修改销售量
		OrderDetailExample detailExample = new OrderDetailExample();
		detailExample.createCriteria().andOrderIdEqualTo(oldOrder.getId());
		List<OrderDetailAO> detailAOs = orderDetailMapper.selectByExample(detailExample);
		for(OrderDetailAO tmpAo : detailAOs) {
			if(tmpAo != null && tmpAo.getCount() > 0) {
				foodSetCustomMapper.updateSaleCount(tmpAo.getCount(), tmpAo.getFoodSetId());
				//下面把每日的销量更改一下
				String day = getTodayWeekDay();
				foodWeekCustomMapper.updateSaleCount(tmpAo.getCount(), tmpAo.getFoodSetId(), day);
			}
		}
		//修改积分
		if(oldOrder.getAmount() != null) {
			int increment = oldOrder.getAmount().intValue();
			UserAO user = userMapper.selectByPrimaryKey(oldOrder.getUserId());
			if(user != null && increment != 0) {
				Integer credit = user.getCredit();
				Integer gradeCredit = user.getGradeCredit();
				UserAO tmpUser = new UserAO();
				tmpUser.setId(user.getId());
				tmpUser.setCredit(credit +increment);
				tmpUser.setGradeCredit(gradeCredit+increment);
				userMapper.updateByPrimaryKeySelective(tmpUser);
			} 
		}
		
		//判断一下付款时间，如果当前时间已经超过了用户下单地址的截止时间，就发短信提示一下（暂时注释掉，如果后续单独申请一个短信平台通道即账号，则再考虑放开这个功能）
//		RegionTimeExample regionTimeExample = new RegionTimeExample();
//		regionTimeExample.createCriteria().andRegionIdEqualTo(oldOrder.getTownId());
//		List<RegionTimeAO> regionTimes = regionTimeMapper.selectByExample(regionTimeExample);
//		if(regionTimes != null && !regionTimes.isEmpty()) {
//			RegionTimeAO regionTime = regionTimes.get(0);
//			// 首先把当前的时间的年月日设置到订购限制时间上
//			Date endTime = regionTime.getEndTime();
//			Date now = new Date();
//			String realEndTimeStr = DateTimeUtil.format(now,
//					DateTimeUtil.FORMAT_YMD)
//					+ " "
//					+ DateTimeUtil.format(endTime, DateTimeUtil.FORMAT_HM);
//			endTime = DateTimeUtil.parse(realEndTimeStr,
//					DateTimeUtil.FORMAT_YMD_HM);
//			long between = (endTime.getTime() - now.getTime()) / 1000;// 除以1000是为了转换成秒
//			if (between < 0) { // 已经超过下单截止时间，发个短信提醒
//				String phone = configUtil.getConfigValue(ConfigUtil.ORDER_NOTICE_PHONE);
//				smsService.send(phone, "系统通知：有用户在订单截止日期后付款，订单编号为" + oldOrder.getOrderNo() + "，请及时检查并确保已经在派送单上！", null, null, null);
//			}
//		}
		return count > 0;
	}

	@Override
	@Transactional(readOnly=true)
	public OrderAO getOrderByOrderNo(String orderNo) {
		if(orderNo == null || orderNo.isEmpty()) {
			return null;
		}
		OrderExample example = new OrderExample();
		example.createCriteria().andOrderNoEqualTo(orderNo);
		List<OrderAO> datas = orderMapper.selectByExample(example);
		if(datas != null && !datas.isEmpty()) {
			return datas.get(0);
		}
		return null;
	}

	@Override
	@Transactional(readOnly=true)
	public OrderAO getOrder(String userId, String id) {
		OrderAO order = orderMapper.selectByPrimaryKey(id);
		if(order == null) {
			return null;
		}
		
		if(userId == null || !userId.equals(order.getUserId())) {
			return null;
		}
		OrderDetailExample ode = new OrderDetailExample();
		ode.createCriteria().andOrderIdEqualTo(id);
		
		List<OrderDetailAO> odList = orderDetailMapper.selectByExample(ode);
		order.setDetailList(odList);
		return order;
	}

	@Override
	@Transactional(rollbackFor=Throwable.class)
	public Result<Boolean> cancel(String userId, String id) {
		OrderAO order = orderCustomMapper.getOrderByOrderId(id);
		if(!order.getUserId().equals(userId)) {
			return new Result<Boolean>(false, "对不起，您无权取消");
		} else if(!OrderAO.STATUS_ORDERED.equals(order.getStatus())) {
			return new Result<Boolean>(false, "对不起，只能取消未付款订单");
		}
		
		rechargeService.cancelOrder(userId, id);
		
		OrderAO upd = new OrderAO();
		upd.setId(id);
		upd.setStatus(OrderAO.STATUS_CANCELED);
		orderMapper.updateByPrimaryKeySelective(upd);
		
		return new Result<Boolean>(true);
	}

	
	@Resource
	private DataSource dataSource;
	
	private boolean checkIfTestUser4Apple(String userId) {
		Connection con = DataSourceUtils.getConnection(dataSource);
		
		try {
			SimpleJDBCTemplate template = new SimpleJDBCTemplate(con);
			Boolean ret = template.selectObject("select 1 from t_user_test where user_id=?", 
					new QueryReactor<Boolean>() {

						@Override
						public void setParam(PreparedStatement ps,
								Object... params) throws SQLException {
							ps.setString(1, (String)params[0]);
						}

						@Override
						public Boolean pack(ResultSet rs) throws SQLException {
							return true;
						}
					}, userId);
			
			return ret != null && ret;
		} catch (Exception e) {
			return false;
		} finally {
			DataSourceUtils.releaseConnection(con, dataSource);
		}
	}
	
	@Override
	@Transactional(rollbackFor=Throwable.class)
	public Result<Boolean> deal4AppleTest(OrderAO order) {
		 
		if(order == null) {
			return new Result<>(false);
		}
		String userId = order.getUserId();
		if(!checkIfTestUser4Apple(userId)) {
			return new Result<Boolean>(false);
		}
	
		OrderAO tmpOrder=new OrderAO();
		tmpOrder.setOrderNo(order.getOrderNo());
		tmpOrder.setStatus(OrderAO.STATUS_PAID);
		tmpOrder.setToPay(new BigDecimal(0));
		tmpOrder.setPaymentTime(new Date());
		tmpOrder.setCommentStatus(OrderAO.COMMENT_STATUS_WAITFOR);
		tmpOrder.setTradeNo("PAY4APPLETEST");
		payOrder(tmpOrder, null);
		return new Result<Boolean>(true);
	}

	@Override
	@Transactional(readOnly=true)
	public OrderAO getOrderByOrderNoWithDetail(String orderNo) {
		OrderAO order = getOrderByOrderNo(orderNo);
		if(order != null) {
			OrderDetailExample ode = new OrderDetailExample();
			ode.createCriteria().andOrderIdEqualTo(order.getId());
			
			order.setDetailList(orderDetailMapper.selectByExample(ode));
		}
		return order;
	}


	@Override
	@Transactional(rollbackFor=Throwable.class)
	public Result<OrderAO> submitOrder4Seckill(UserAO user, FoodSetAO foodSet,
			SeckillAO sec, UserAddressAO userAddress, OrderExtraAO orderExtra) {
		OrderAO order = new OrderAO();
		order.setAddressId(userAddress.getId());
		order.setAddress(userAddress.getAddress());
		order.setAmount(sec.getPrice());
		order.setCityId(userAddress.getCityId());
		order.setCityName(userAddress.getCityName());
		order.setCountyId(userAddress.getCountyId());
		order.setCountyName(userAddress.getCountyName());
		order.setTownId(userAddress.getTownId());
		order.setTownName(userAddress.getTownName());
		order.setBlockId(userAddress.getBlockId());
		order.setBlockName(userAddress.getBlockName());
		order.setBuildingId(userAddress.getBuildingId());
		order.setBuildingName(userAddress.getBuildingName());
		order.setCellId(userAddress.getCellId());
		order.setCellName(userAddress.getCellName());
		order.setCount(1);
		order.setCreateDate(new Date());
		order.setCreateTime(new Date());
		order.setFloor(userAddress.getFloor());
		order.setManualAddress(userAddress.getManualAddress());
		order.setMobile(userAddress.getMobile());
		order.setName(userAddress.getName());
		order.setToPay(sec.getPrice());
		
		//处理location
		String retionId = "";
		if(userAddress.getCellId() != null && !userAddress.getCellId().isEmpty()) {
			retionId = userAddress.getCellId();
		} else if(userAddress.getBuildingId() != null && !userAddress.getBuildingId().isEmpty()) {
			retionId = userAddress.getBuildingId();
		} else if(userAddress.getBlockId() != null && !userAddress.getBlockId().isEmpty()) {
			retionId = userAddress.getBlockId();
		} else if(userAddress.getTownId() != null && !userAddress.getTownId().isEmpty()) {
			retionId = userAddress.getTownId();
		}
		RegionAO region = regionMapper.selectByPrimaryKey(retionId);
		if(region != null) {
			order.setLocation(region.getLocation());
		}
		
		order.setOrderFrom(orderExtra.getOrderFrom());
		order.setOrderNo(genOrderNo());
		order.setPaymentTime(null);
		order.setStatus(OrderAO.STATUS_ORDERED);
		order.setSetType(OrderAO.SET_TYPE_LUNCH);
		order.setTownId(userAddress.getTownId());
		order.setUserId(user.getId());
		orderMapper.insert(order);
		
		order.setAddressInfo(userAddress);
		orderExtra.setOrderId(order.getId());
		
		OrderDetailAO detail = new OrderDetailAO();
		detail.setAmount(sec.getPrice());
		detail.setCount(1);
		detail.setCreateTime(new Date());
		detail.setLogo(foodSet.getLogo());
		detail.setOrderId(order.getId());
		detail.setPrice(sec.getPrice());
		detail.setSetId(foodSet.getId());
		detail.setSetTitle(foodSet.getName());
		detail.setSkuId(foodSet.getSkuId());
		detail.setType(sec.getType());
		order.addDetail(detail);
		
		orderDetailMapper.insert(detail);
		
		saveExtra(orderExtra);
		
		return new Result<OrderAO>(order);
	}



	
}

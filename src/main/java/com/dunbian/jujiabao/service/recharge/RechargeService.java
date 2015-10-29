package com.dunbian.jujiabao.service.recharge;

import java.math.BigDecimal;
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

import com.dunbian.jujiabao.appobj.extend.AccountAO;
import com.dunbian.jujiabao.appobj.extend.AccountDetailAO;
import com.dunbian.jujiabao.appobj.extend.OrderAO;
import com.dunbian.jujiabao.appobj.extend.RechargeRecordAO;
import com.dunbian.jujiabao.appobj.extend.RechargeSetAO;
import com.dunbian.jujiabao.appobj.extend.RegionTimeAO;
import com.dunbian.jujiabao.appobj.extend.UserWalletAO;
import com.dunbian.jujiabao.appobj.generator.AccountDetailExample;
import com.dunbian.jujiabao.appobj.generator.RechargeSetExample;
import com.dunbian.jujiabao.appobj.generator.UserWalletExample;
import com.dunbian.jujiabao.db.customer.OrderCustomMapper;
import com.dunbian.jujiabao.db.generator.AccountDetailMapper;
import com.dunbian.jujiabao.db.generator.AccountMapper;
import com.dunbian.jujiabao.db.generator.OrderMapper;
import com.dunbian.jujiabao.db.generator.RechargeRecordMapper;
import com.dunbian.jujiabao.db.generator.RechargeSetMapper;
import com.dunbian.jujiabao.db.generator.UserWalletMapper;
import com.dunbian.jujiabao.framework.jdbc.QueryReactor;
import com.dunbian.jujiabao.framework.jdbc.SimpleJDBCTemplate;
import com.dunbian.jujiabao.framework.obj.Page;
import com.dunbian.jujiabao.framework.obj.Result;
import com.dunbian.jujiabao.framework.util.DateTimeUtil;
import com.dunbian.jujiabao.framework.util.EncryptUtil;
import com.dunbian.jujiabao.service.address.IAddressService;

@Service
public class RechargeService implements IRechargeService {

	@Resource
	private RechargeRecordMapper rechargeRecordMapper;
	
	@Resource
	private DataSource dataSource;
	
	@Resource
	private UserWalletMapper userWalletMapper;
	
	@Resource
	private AccountDetailMapper accountDetailMapper;
	
	@Resource
	private AccountMapper accountMapper;
	
	@Resource
	private RechargeSetMapper rechargeSetMapper;
	
	@Resource
	private OrderMapper orderMapper;
	
	@Resource
	private OrderCustomMapper orderCustomMapper;
	
	@Resource
	private IAddressService addressService;
	
	@Transactional(rollbackFor=Throwable.class)
	private UserWalletAO lockWallet(String userId) {
		Connection con = null;
		try {
			con = DataSourceUtils.getConnection(dataSource);
			SimpleJDBCTemplate jdbcTool = new SimpleJDBCTemplate(con);
			return jdbcTool.selectObject("select id, user_id, amount, balance, gift_balance, frozen_balance, frozen_gift, pay_pass, error_cnt, error_time, error_cnt, error_time from t_user_wallet where user_id=? for update", new QueryReactor<UserWalletAO>() {

				@Override
				public void setParam(PreparedStatement ps, Object... params)
						throws SQLException {
					ps.setString(1, (String)params[0]);
				}

				@Override
				public UserWalletAO pack(ResultSet rs) throws SQLException {
					UserWalletAO wallet = new UserWalletAO();
					wallet.setId(rs.getString("id"));
					wallet.setAmount(rs.getBigDecimal("amount"));
					wallet.setBalance(rs.getBigDecimal("balance"));
					wallet.setGiftBalance(rs.getBigDecimal("gift_balance"));
					wallet.setFrozenBalance(rs.getBigDecimal("frozen_balance"));
					wallet.setFrozenGift(rs.getBigDecimal("frozen_gift"));
					wallet.setPayPass(rs.getString("pay_pass"));
					wallet.setErrorCnt(rs.getInt("error_cnt"));
					wallet.setErrorTime(rs.getTimestamp("error_time"));
					wallet.setUserId(rs.getString("user_id"));
					return wallet;
				}
			}, userId);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			DataSourceUtils.releaseConnection(con, dataSource);
		}
	}

	@Transactional(rollbackFor=Throwable.class)
	private AccountAO lockAccount() {
		Connection con = null;
		try {
			con = DataSourceUtils.getConnection(dataSource);
			SimpleJDBCTemplate jdbcTool = new SimpleJDBCTemplate(con);
			return jdbcTool.selectObject("select * from t_account limit 1 for update", new QueryReactor<AccountAO>() {

				@Override
				public void setParam(PreparedStatement ps, Object... params)
						throws SQLException {
				}

				@Override
				public AccountAO pack(ResultSet rs) throws SQLException {
					AccountAO account = new AccountAO();
					account.setBalance(rs.getBigDecimal("balance"));
					account.setGiftBalance(rs.getBigDecimal("gift_balance"));
					account.setId(rs.getString("id"));
					account.setTradeTime(rs.getTimestamp("trade_time"));
					
					return account;
				}
			});
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		} finally {
			DataSourceUtils.releaseConnection(con, dataSource);
		}
	}
	
	@Override
	@Transactional(rollbackFor=Throwable.class)
	public Result<Boolean> recharge(String rechargeId) {
		if(rechargeId == null) {
			return new Result<Boolean>(false, "充值id不能为空");
		}

		RechargeRecordAO record = rechargeRecordMapper.selectByPrimaryKey(rechargeId);
		
		if(record == null) {
			return new Result<Boolean>(false, "无此充值记录");
		}
		
		if(RechargeRecordAO.STATUS_PAID.equals(record.getStatus())) {
			return new Result<Boolean>(true);
		}
		
		if(!RechargeRecordAO.STATUS_TOPAY.equals(record.getStatus())) {
			return new Result<Boolean>(false, "状态异常");
		}
		
		if(record.getAmount().compareTo(new BigDecimal(0)) <= 0) {
			return new Result<Boolean>(false, "数据异常");
		}
		
		if(record.getGift()!=null && record.getGift().compareTo(new BigDecimal(0)) < 0) {
			return new Result<Boolean>(false, "数据异常");
		}

		UserWalletAO wallet = lockWallet(record.getUserId());
		if(wallet == null) {
			return new Result<Boolean>(false, "锁定用户钱包失败");
		}
		
		wallet.setAmount(wallet.getAmount().add(record.getAmount()));
		wallet.setBalance(wallet.getBalance().add(record.getAmount()));
		if(record.getGift() != null) {
			wallet.setGiftBalance(wallet.getGiftBalance().add(record.getGift()));
		}
		wallet.setTradeTime(new Date());
		
		userWalletMapper.updateByPrimaryKeySelective(wallet);
		recordUserWalletDetail(wallet, record);
		
		
		AccountAO account = lockAccount();
		if(account == null) {
			return new Result<Boolean>(false, "获取总账户失败");
		}
		
		account.setBalance(account.getBalance().add(record.getAmount()));
		if(record.getGift() != null) {
			account.setGiftBalance(account.getGiftBalance().add(record.getGift()));
		}
		account.setTradeTime(new Date());
		
		accountMapper.updateByPrimaryKeySelective(account);
		recordInternalDetail(account.getId(), record);
		
		RechargeRecordAO upd = new RechargeRecordAO();
		upd.setId(record.getId());
		upd.setStatus(RechargeRecordAO.STATUS_PAID);
		upd.setTradeTime(new Date());
		rechargeRecordMapper.updateByPrimaryKeySelective(upd);
		
		return new Result<Boolean>(true);
	}
	
	@Transactional(rollbackFor=Throwable.class)
	private void recordUserWalletDetail(UserWalletAO wallet, RechargeRecordAO record) {
		AccountDetailAO detail = new AccountDetailAO();
		detail.setAccountId(wallet.getId());
		detail.setAmount(record.getAmount());
		detail.setBalanceType(AccountDetailAO.BALANCE_TYPE_AMOUNT);
		detail.setCreditFlag(AccountDetailAO.CREDIT_FLAG_INCOME);
		detail.setDataId(record.getId());
		detail.setHead(AccountDetailAO.HEAD_USER);
		detail.setTradeTime(new Date());
		detail.setTradeType(AccountDetailAO.TRADE_TYPE_RECHARGE);
		detail.setValidType(AccountDetailAO.VALID_TYPE_SHEETS);
		detail.setUserId(record.getUserId());
		accountDetailMapper.insert(detail);
		
		if(record.getGift() != null && record.getGift().compareTo(new BigDecimal(0)) > 0) {
			detail = new AccountDetailAO();
			detail.setAccountId(wallet.getId());
			detail.setAmount(record.getGift());
			detail.setBalanceType(AccountDetailAO.BALANCE_TYPE_GIFT);
			detail.setCreditFlag(AccountDetailAO.CREDIT_FLAG_INCOME);
			detail.setDataId(record.getId());
			detail.setHead(AccountDetailAO.HEAD_USER);
			detail.setTradeTime(new Date());
			detail.setTradeType(AccountDetailAO.TRADE_TYPE_RECHARGE);
			detail.setValidType(AccountDetailAO.VALID_TYPE_SHEETS);
			detail.setUserId(record.getUserId());
			accountDetailMapper.insert(detail);
		}
	}
	
	@Transactional(rollbackFor=Throwable.class)
	private void recordInternalDetail(String accountId, RechargeRecordAO record) {
		AccountDetailAO detail = new AccountDetailAO();
		detail.setAccountId(accountId);
		detail.setAmount(record.getAmount());
		detail.setBalanceType(AccountDetailAO.BALANCE_TYPE_AMOUNT);
		detail.setCreditFlag(AccountDetailAO.CREDIT_FLAG_INCOME);
		detail.setDataId(record.getId());
		detail.setHead(AccountDetailAO.HEAD_INTERNAL);
		detail.setTradeTime(new Date());
		detail.setTradeType(AccountDetailAO.TRADE_TYPE_RECHARGE);
		detail.setValidType(AccountDetailAO.VALID_TYPE_SHEETS);
		detail.setUserId(record.getUserId());
		accountDetailMapper.insert(detail);
		
		if(record.getGift() != null && record.getGift().compareTo(new BigDecimal(0)) > 0) {
			detail = new AccountDetailAO();
			detail.setAccountId(accountId);
			detail.setAmount(record.getGift());
			detail.setBalanceType(AccountDetailAO.BALANCE_TYPE_GIFT);
			detail.setCreditFlag(AccountDetailAO.CREDIT_FLAG_INCOME);
			detail.setDataId(record.getId());
			detail.setHead(AccountDetailAO.HEAD_INTERNAL);
			detail.setTradeTime(new Date());
			detail.setTradeType(AccountDetailAO.TRADE_TYPE_RECHARGE);
			detail.setValidType(AccountDetailAO.VALID_TYPE_SHEETS);
			detail.setUserId(record.getUserId());
			accountDetailMapper.insert(detail);
		}
	}

	@Override
	@Transactional(rollbackFor=Throwable.class)
	public List<RechargeSetAO> getRechargeSetList() {
		RechargeSetExample rse = new RechargeSetExample();
		rse.createCriteria().andStatusEqualTo(RechargeSetAO.STATUS_VALID);
		rse.setOrderByClause("sort");
		
		return rechargeSetMapper.selectByExample(rse);
	}

	@Override
	@Transactional(rollbackFor=Throwable.class)
	public Result<RechargeRecordAO> genRecharge(String setId, String userId) {
		UserWalletExample uwe = new UserWalletExample();
		uwe.createCriteria().andUserIdEqualTo(userId);
		
		List<UserWalletAO> userWallets = userWalletMapper.selectByExample(uwe);
		if(userWallets == null || userWallets.isEmpty()) {
			return new Result<>(false, "程序出现异常，钱包数据未生成");
		}
		
		RechargeSetAO set = rechargeSetMapper.selectByPrimaryKey(setId);
		if(set == null) {
			return new Result<>(false, "无此充值套餐，请刷新后重新充值");
		}
		
		RechargeRecordAO record = new RechargeRecordAO();
		record.setAmount(set.getAmount());
		record.setGift(set.getGift());
		record.setRechargeId(set.getId());
		record.setStatus(RechargeRecordAO.STATUS_TOPAY);
		record.setTradeTime(new Date());
		record.setUserId(userId);
	    rechargeRecordMapper.insert(record);
		
		return new Result<>(record);
	}

	@Override
	@Transactional(rollbackFor=Throwable.class)
	public Result<OrderAO> prepare4Pay(String userId, String orderId) {
		OrderAO order = orderCustomMapper.getOrderByOrderId(orderId);
		if(order == null) {
			return new Result<OrderAO>(false, "订单信息出错");
		}
		
		if(userId == null || !userId.equals(order.getUserId())) {
			return new Result<OrderAO>(false, "订单信息出错");
		}
		
		UserWalletExample uwe = new UserWalletExample();
		uwe.createCriteria().andUserIdEqualTo(userId);
		
		List<UserWalletAO> userWallets = userWalletMapper.selectByExample(uwe);
		if(userWallets == null || userWallets.isEmpty()) {
			order.setWalletPayType(OrderAO.WALLET_PAY_TYPE_NONE);
			return new Result<>(order);
		}
		
		UserWalletAO wallet = userWallets.get(0);
		wallet.setPayPass(null);
		order.setWallet(wallet);
		if(wallet.getValidAmount().compareTo(new BigDecimal(0.001)) < 0) {
			order.setWalletPayType(OrderAO.WALLET_PAY_TYPE_NONE);
			return new Result<>(order);
		} else if(wallet.getValidAmount().compareTo(order.getToPay()) >= 0) {
			order.setWalletPayType(OrderAO.WALLET_PAY_TYPE_ALL);
			order.setWalletMoney(order.getToPay());
			return new Result<>(order);
		} else {
			order.setWalletPayType(OrderAO.WALLET_PAY_TYPE_PART);
			order.setWalletMoney(wallet.getValidAmount());
			return new Result<>(order);
		}
	}

	@Transactional(rollbackFor=Throwable.class)
	private BigDecimal comsume4Wallet(String orderId, String orderNo, BigDecimal walletMoney, BigDecimal toPay, UserWalletAO wallet) {
		boolean payAll = wallet.getValidAmount().compareTo(toPay) >= 0;
		boolean billPayOnly = wallet.getBalance().compareTo(walletMoney) >= 0;
		BigDecimal giftPayMoney = walletMoney;
		
		if(wallet.getBalance().compareTo(new BigDecimal(0.001)) > 0) {
			AccountDetailAO detail = new AccountDetailAO();
			detail.setAccountId(wallet.getId());
			if(billPayOnly) {
				giftPayMoney = null;
				detail.setAmount(walletMoney);
				if(!payAll) {
					wallet.setFrozenBalance(wallet.getFrozenBalance().add(walletMoney));
				}
				wallet.setBalance(wallet.getBalance().subtract(walletMoney));
			} else {
				giftPayMoney = walletMoney.subtract(wallet.getBalance());
				detail.setAmount(wallet.getBalance());
				if(!payAll) {
					wallet.setFrozenBalance(wallet.getFrozenBalance().add(wallet.getBalance()));
				}
				wallet.setBalance(new BigDecimal(0));
			}
			
			detail.setBalanceType(AccountDetailAO.BALANCE_TYPE_AMOUNT);
			detail.setCreditFlag(AccountDetailAO.CREDIT_FLAG_SPENDING);
			detail.setDataId(orderId);
			detail.setHead(AccountDetailAO.HEAD_USER);
			detail.setTradeTime(new Date());
			detail.setTradeType(AccountDetailAO.TRADE_TYPE_COMSUME);
			detail.setValidType(payAll ? AccountDetailAO.VALID_TYPE_SHEETS : AccountDetailAO.VALID_TYPE_OFF_SHEETS);
			detail.setDataNo(orderNo);
			detail.setUserId(wallet.getUserId());
			accountDetailMapper.insert(detail);
		}
		
		if(!billPayOnly) {
			AccountDetailAO detail = new AccountDetailAO();

			detail = new AccountDetailAO();
			detail.setAccountId(wallet.getId());
			detail.setAmount(giftPayMoney);
			wallet.setGiftBalance(wallet.getGiftBalance().subtract(giftPayMoney));
			if(!payAll) {
				wallet.setFrozenGift(wallet.getFrozenGift().add(giftPayMoney));
			}
			detail.setBalanceType(AccountDetailAO.BALANCE_TYPE_GIFT);
			detail.setCreditFlag(AccountDetailAO.CREDIT_FLAG_SPENDING);
			detail.setDataId(orderId);
			detail.setHead(AccountDetailAO.HEAD_USER);
			detail.setTradeTime(new Date());
			detail.setTradeType(AccountDetailAO.TRADE_TYPE_COMSUME);
			detail.setValidType(payAll ? AccountDetailAO.VALID_TYPE_SHEETS : AccountDetailAO.VALID_TYPE_OFF_SHEETS);
			detail.setDataNo(orderNo);
			detail.setUserId(wallet.getUserId());
			accountDetailMapper.insert(detail);
		}
		
		wallet.setTradeTime(new Date());
		userWalletMapper.updateByPrimaryKeySelective(wallet);
		
		return giftPayMoney;
	}
	
	private void recordPassError(UserWalletAO wallet) {
		int errorCnt = wallet.getErrorCnt() == null ? 0 : wallet.getErrorCnt();
		if(wallet.getErrorTime() == null || (System.currentTimeMillis() - wallet.getErrorTime().getTime() <= 3*3600*1000)) {
			errorCnt++;
		} else {
			errorCnt = 1;
		}
		
		UserWalletAO upd = new UserWalletAO();
		upd.setId(wallet.getId());
		upd.setErrorCnt(errorCnt);
		upd.setErrorTime(new Date());
		userWalletMapper.updateByPrimaryKeySelective(upd);
	}
	
	@Transactional(rollbackFor=Throwable.class)
	private void comsume4Internal(OrderAO order, BigDecimal giftMoney) {
			AccountAO account = lockAccount();
			AccountDetailAO detail = new AccountDetailAO();
			detail.setAccountId(account.getId());
			detail.setAmount(giftMoney);
			detail.setBalanceType(AccountDetailAO.BALANCE_TYPE_GIFT);
			detail.setCreditFlag(AccountDetailAO.CREDIT_FLAG_SPENDING);
			detail.setDataId(order.getId());
			detail.setHead(AccountDetailAO.HEAD_INTERNAL);
			detail.setTradeTime(new Date());
			detail.setTradeType(AccountDetailAO.TRADE_TYPE_COMSUME);
			detail.setValidType(AccountDetailAO.VALID_TYPE_SHEETS);
			detail.setDataNo(order.getOrderNo());
			detail.setUserId(order.getUserId());
			accountDetailMapper.insert(detail);
			
			account.setGiftBalance(account.getGiftBalance().subtract(giftMoney));
			accountMapper.updateByPrimaryKeySelective(account);
	}
	
	@Override
	@Transactional(rollbackFor=Throwable.class)
	public Result<OrderAO> walletPay(String userId, String orderId,
			String payPass, BigDecimal walletMoney) {
		if(userId == null || orderId == null || payPass == null || walletMoney == null) {
			return new Result<>(false, "输入数据错误");
		}
		
		if(walletMoney.compareTo(new BigDecimal(0.001)) < 0) {
			return new Result<>(false, "支付金额出错");
		}
		
		OrderAO order = orderCustomMapper.getOrderByOrderId(orderId);
		if(!userId.equals(order.getUserId())) {
			return new Result<>(false, "订单信息出错");
		}
		
		if(!OrderAO.STATUS_ORDERED.equals(order.getStatus())) {
			return new Result<OrderAO>(false, "该订单状态不允许支付");
		}
		
		RegionTimeAO regionTimeAO = addressService.getRegionTime(order.getTownId());
		if(regionTimeAO != null && regionTimeAO.getEndTime() != null) {
			Date endTime = regionTimeAO.getEndTime();
			//首先把当前的时间的年月日设置到订购限制时间上
			Date now = new Date();
			String realEndTimeStr = DateTimeUtil.format(now, DateTimeUtil.FORMAT_YMD) + " " 
					+ DateTimeUtil.format(endTime, DateTimeUtil.FORMAT_HM);
			endTime = DateTimeUtil.parse(realEndTimeStr, DateTimeUtil.FORMAT_YMD_HM);
			long between=endTime.getTime()-now.getTime();
			if(between < 0) { //已经超过下单时间
				 return new Result<OrderAO>(false, "当前已超过本地区下单时间，不允许支付");
			}
		}
		
		UserWalletAO wallet = lockWallet(userId);
		
		int errorCnt = wallet.getErrorCnt() == null ? 0 : wallet.getErrorCnt();
		if(errorCnt >= 3 && System.currentTimeMillis() - wallet.getErrorTime().getTime() <= 3*3600*1000) {
			return new Result<>(false, "对不起，您的支付密码输入错误次数超过限制，账户被锁定，请联系我们进行账户解锁");
		}
		
		String encryptPwd = EncryptUtil.encrypt(wallet.getId() + EncryptUtil.encrypt(payPass));
		if(!encryptPwd.equals(wallet.getPayPass())) {
			recordPassError(wallet);
			return new Result<>(false, "您的支付密码输入有误，请重新输入(连续3次错误将锁定账户)");
		}
		
		if(wallet.getValidAmount().compareTo(walletMoney) < 0){
			return new Result<>(false, "您的钱包余额不足，请刷新后重新支付");
		}
		
		if(walletMoney.compareTo(order.getToPay()) > 0) {
			return new Result<>(false, "您的订单信息发生变化，请刷新后重新支付");
		}
		
		if(walletMoney.compareTo(order.getToPay()) < 0 && wallet.getValidAmount().compareTo(walletMoney) > 0) {
			return new Result<>(false, "您的钱包信息发生变化，请刷新后重新支付");
		}
		
		boolean payAll = wallet.getValidAmount().compareTo(order.getToPay()) >= 0;
		boolean billPayOnly = wallet.getBalance().compareTo(walletMoney) >= 0;
		
		BigDecimal giftMoney = comsume4Wallet(orderId, order.getOrderNo(), walletMoney, order.getToPay(), wallet);
		
		if(payAll && !billPayOnly && order.getToPay().compareTo(order.getAmount()) >= 0) {
			comsume4Internal(order, giftMoney);
		} else if(payAll && order.getToPay().compareTo(order.getAmount()) < 0) {
		    giftMoney = callback4walletPay(orderId, wallet, giftMoney);
		    if(giftMoney != null && giftMoney.compareTo(new BigDecimal(0)) > 0) {
		    	comsume4Internal(order, giftMoney);
		    }
		}
		
		order.setToPay(order.getToPay().subtract(walletMoney));
		if(order.getToPay().compareTo(new BigDecimal(0.001)) < 0) {
			order.setStatus(OrderAO.STATUS_PAID);
			order.setPaymentTime(new Date());
			order.setCommentStatus(OrderAO.COMMENT_STATUS_WAITFOR);
		}
		
		orderMapper.updateByPrimaryKeySelective(order);
		return new Result<>(order);
	}
	
	@Transactional(rollbackFor=Throwable.class)
	private BigDecimal callback4walletPay(String orderId, UserWalletAO wallet, BigDecimal giftMoney) {
		AccountDetailExample ade = new AccountDetailExample();
		ade.createCriteria().andTradeTypeEqualTo(AccountDetailAO.TRADE_TYPE_COMSUME).andDataIdEqualTo(orderId).andValidTypeEqualTo(AccountDetailAO.VALID_TYPE_OFF_SHEETS);
		
		List<AccountDetailAO> lst = accountDetailMapper.selectByExample(ade);
		if(lst != null && !lst.isEmpty()) {
			if(giftMoney == null) {
				giftMoney = new BigDecimal(0);
			}
			for(AccountDetailAO detail : lst) {
				if(AccountDetailAO.BALANCE_TYPE_AMOUNT.equals(detail.getBalanceType())) {
					wallet.setFrozenBalance(wallet.getFrozenBalance().subtract(detail.getAmount()));
				} else if(AccountDetailAO.BALANCE_TYPE_GIFT.equals(detail.getBalanceType())) {
					wallet.setFrozenGift(wallet.getFrozenGift().subtract(detail.getAmount()));
					giftMoney = giftMoney.add(detail.getAmount());
				}
			}
		}
		
		userWalletMapper.updateByPrimaryKeySelective(wallet);
		
		AccountDetailAO upd = new AccountDetailAO();
		upd.setValidType(AccountDetailAO.VALID_TYPE_SHEETS);
		accountDetailMapper.updateByExampleSelective(upd, ade);
		
		return giftMoney;
	}

	@Override
	public RechargeRecordAO getRechargeById(String recordId) {
		return rechargeRecordMapper.selectByPrimaryKey(recordId);
	}

	@Transactional(rollbackFor=Throwable.class)
	private void callback4PayInternal(BigDecimal giftMoney, OrderAO order, AccountAO account) {
		if(giftMoney.compareTo(new BigDecimal(0.001)) > 0) {
			AccountDetailAO detail = new AccountDetailAO();
			detail.setAccountId(account.getId());
			detail.setAmount(giftMoney);
			detail.setBalanceType(AccountDetailAO.BALANCE_TYPE_GIFT);
			detail.setCreditFlag(AccountDetailAO.CREDIT_FLAG_SPENDING);
			detail.setDataId(order.getId());
			detail.setHead(AccountDetailAO.HEAD_INTERNAL);
			detail.setTradeTime(new Date());
			detail.setTradeType(AccountDetailAO.TRADE_TYPE_COMSUME);
			detail.setValidType(AccountDetailAO.VALID_TYPE_SHEETS);
			detail.setDataNo(order.getOrderNo());
			detail.setUserId(order.getUserId());
			accountDetailMapper.insert(detail);
			
			account.setGiftBalance(account.getGiftBalance().subtract(giftMoney));
		}
		
		AccountDetailAO detail = new AccountDetailAO();
		detail.setAccountId(account.getId());
		detail.setAmount(order.getToPay());
		detail.setBalanceType(AccountDetailAO.BALANCE_TYPE_AMOUNT);
		detail.setCreditFlag(AccountDetailAO.CREDIT_FLAG_INCOME);
		detail.setDataId(order.getId());
		detail.setHead(AccountDetailAO.HEAD_INTERNAL);
		detail.setTradeTime(new Date());
		detail.setTradeType(AccountDetailAO.TRADE_TYPE_ALIPAY);
		detail.setValidType(AccountDetailAO.VALID_TYPE_SHEETS);
		detail.setDataNo(order.getOrderNo());
		detail.setUserId(order.getUserId());
		accountDetailMapper.insert(detail);
		account.setBalance(account.getBalance().add(order.getToPay()));
		
		accountMapper.updateByPrimaryKeySelective(account);
	}
	
	@Override
	@Transactional(rollbackFor=Throwable.class)
	public Result<Boolean> callback4Pay(OrderAO order) {
		BigDecimal giftMoney = new BigDecimal(0);
		if(order.getToPay().compareTo(order.getAmount()) < 0) {
			if(OrderAO.STATUS_ORDERED.equals(order.getStatus())) {
				AccountDetailExample ade = new AccountDetailExample();
				ade.createCriteria().andDataIdEqualTo(order.getId()).andTradeTypeEqualTo(AccountDetailAO.TRADE_TYPE_COMSUME).andValidTypeEqualTo(AccountDetailAO.VALID_TYPE_OFF_SHEETS);
				List<AccountDetailAO> detailList = accountDetailMapper.selectByExample(ade);
				
				if(detailList == null || detailList.isEmpty()) {
					return new Result<>(false, "未发现钱包支付记录");
				}
				
				UserWalletAO wallet = lockWallet(order.getUserId());
				for(AccountDetailAO ad : detailList) {
					if(AccountDetailAO.BALANCE_TYPE_AMOUNT.equals(ad.getBalanceType())) {
						wallet.setFrozenBalance(wallet.getFrozenBalance().subtract(ad.getAmount()));
					} else if(AccountDetailAO.BALANCE_TYPE_GIFT.equals(ad.getBalanceType())) {
						wallet.setFrozenGift(wallet.getFrozenGift().subtract(ad.getAmount()));
						giftMoney = giftMoney.add(ad.getAmount());
					}
				}
				
				userWalletMapper.updateByPrimaryKeySelective(wallet);
				
				AccountDetailAO upd = new AccountDetailAO();
				upd.setValidType(AccountDetailAO.VALID_TYPE_SHEETS);
				
				accountDetailMapper.updateByExampleSelective(upd, ade);
			} else if(OrderAO.STATUS_CANCELED.equals(order.getStatus())) {
				AccountDetailExample ade = new AccountDetailExample();
				ade.createCriteria().andDataIdEqualTo(order.getId()).andTradeTypeEqualTo(AccountDetailAO.TRADE_TYPE_COMSUME).andValidTypeEqualTo(AccountDetailAO.VALID_TYPE_OFF_SHEETS).andCreditFlagEqualTo(AccountDetailAO.CREDIT_FLAG_SPENDING);
				List<AccountDetailAO> detailList = accountDetailMapper.selectByExample(ade);
				
				if(detailList == null || detailList.isEmpty()) {
					return new Result<>(false, "未发现钱包支付记录");
				}
				
				UserWalletAO wallet = lockWallet(order.getUserId());
				for(AccountDetailAO ad : detailList) {
					if(AccountDetailAO.BALANCE_TYPE_AMOUNT.equals(ad.getBalanceType())) {
						wallet.setBalance(wallet.getBalance().subtract(ad.getAmount()));
					} else if(AccountDetailAO.BALANCE_TYPE_GIFT.equals(ad.getBalanceType())) {
						wallet.setGiftBalance(wallet.getGiftBalance().subtract(ad.getAmount()));
						giftMoney = giftMoney.add(ad.getAmount());
					}
					
					ad.setId(null);
					ad.setValidType(AccountDetailAO.VALID_TYPE_SHEETS);
					ad.setTradeTime(new Date());
					accountDetailMapper.insert(ad);
				}
				
				userWalletMapper.updateByPrimaryKeySelective(wallet);
			}
		}
		
		AccountAO account = lockAccount();
		callback4PayInternal(giftMoney, order, account);
		return new Result<>(true);
	}

	@Override
	public Result<Boolean> isExistsWallet(String userId) {
		UserWalletAO wallet = lockWallet(userId);
		boolean ret = wallet != null;
		Result<Boolean> result = new Result<>(true);
		result.setData(ret);
		return result;
	}

	@Override
	@Transactional(rollbackFor=Throwable.class)
	public Result<Boolean> setPass(String userId, String pass) {
		if(pass == null || pass.isEmpty()) {
			return new Result<Boolean>(false, "密码不能为空");
		}
		
		UserWalletAO wallet = lockWallet(userId);
		if(wallet != null) {
			return new Result<>(false, "您已设置过支付密码");
		}
		
		wallet = new UserWalletAO();
		wallet.setAmount(new BigDecimal(0));
		wallet.setBalance(new BigDecimal(0));
		wallet.setCash(new BigDecimal(0));
		wallet.setErrorCnt(0);
		wallet.setFrozenBalance(new BigDecimal(0));
		wallet.setFrozenGift(new BigDecimal(0));
		wallet.setGiftBalance(new BigDecimal(0));
		wallet.setPayPass("no password");
		wallet.setUserId(userId);
		wallet.setTradeTime(new Date());
		userWalletMapper.insert(wallet);
		
		pass = EncryptUtil.encrypt(wallet.getId() + EncryptUtil.encrypt(pass));
		
		UserWalletAO upd = new UserWalletAO();
		upd.setId(wallet.getId());
		upd.setPayPass(pass);
		
		userWalletMapper.updateByPrimaryKeySelective(upd);
		return new Result<Boolean>(true);
	}
	
	@Override
	public Result<Boolean> resetPass(String walletId, String oldPass,
			String newPass) {
		//判断一下旧密码是否正确
		String encryptOldPwd = EncryptUtil.encrypt(walletId + EncryptUtil.encrypt(oldPass));
		UserWalletExample example = new UserWalletExample();
		example.createCriteria().andPayPassEqualTo(encryptOldPwd).andIdEqualTo(walletId);
		int count = userWalletMapper.countByExample(example);
		if(count <= 0) {
			return new Result<>(false,"旧密码不正确！");
		}
		
		String encryptNewPwd = EncryptUtil.encrypt(walletId + EncryptUtil.encrypt(newPass));
		UserWalletAO userWalletAO = new UserWalletAO();
		userWalletAO.setId(walletId);
		userWalletAO.setPayPass(encryptNewPwd);
		count = userWalletMapper.updateByPrimaryKeySelective(userWalletAO);
		return new Result<>(count > 0);
	}

	

	@Override
	public List<AccountDetailAO> getDetailList(String userId, Page page) {
		UserWalletExample uwe = new UserWalletExample();
		uwe.createCriteria().andUserIdEqualTo(userId);
		
		List<UserWalletAO> userWalletList = userWalletMapper.selectByExample(uwe);
		if(userWalletList == null || userWalletList.isEmpty()) {
			return null;
		}
		
		String accountId = userWalletList.get(0).getId();
		
		AccountDetailExample ade = new AccountDetailExample();
		ade.createCriteria().andAccountIdEqualTo(accountId);
		ade.setOrderByClause("trade_time desc, id desc");
		ade.setStartRecord(page.getStart());
		ade.setPageCount(page.getPageSize());
		
		return accountDetailMapper.selectByExample(ade);
	}

	@Override
	public UserWalletAO getUserWallet(String userId) {
		UserWalletExample uwe = new UserWalletExample();
		uwe.createCriteria().andUserIdEqualTo(userId);
		
		List<UserWalletAO> userWalletList = userWalletMapper.selectByExample(uwe);
		if(userWalletList == null || userWalletList.isEmpty()) {
			return null;
		}
		
		return userWalletList.get(0);
	}

	@Override
	@Transactional(rollbackFor=Throwable.class)
	public void cancelOrder(String userId, String orderId) {
		AccountDetailExample ade = new AccountDetailExample();
		ade.createCriteria().andDataIdEqualTo(orderId).andTradeTypeEqualTo(AccountDetailAO.TRADE_TYPE_COMSUME).andValidTypeEqualTo(AccountDetailAO.VALID_TYPE_OFF_SHEETS);
		List<AccountDetailAO> detailList = accountDetailMapper.selectByExample(ade);
		
		if(detailList == null || detailList.isEmpty()) {
			return;
		}
		
		UserWalletAO wallet = lockWallet(userId);
		for(AccountDetailAO ad : detailList) {
			if(AccountDetailAO.CREDIT_FLAG_INCOME.equals(ad.getCreditFlag())) {
				throw new RuntimeException("数据出现异常，请重试");
			}
			if(AccountDetailAO.BALANCE_TYPE_AMOUNT.equals(ad.getBalanceType())) {
				wallet.setFrozenBalance(wallet.getFrozenBalance().subtract(ad.getAmount()));
				wallet.setBalance(wallet.getBalance().add(ad.getAmount()));
			} else if(AccountDetailAO.BALANCE_TYPE_GIFT.equals(ad.getBalanceType())) {
				wallet.setFrozenGift(wallet.getFrozenGift().subtract(ad.getAmount()));
				wallet.setGiftBalance(wallet.getGiftBalance().add(ad.getAmount()));
			}
			
			AccountDetailAO copy = ad.clone();
			copy.setId(null);
			copy.setTradeTime(new Date());
			copy.setCreditFlag(AccountDetailAO.CREDIT_FLAG_INCOME);
			copy.setUserId(userId);
			accountDetailMapper.insert(copy);
		}
		
		userWalletMapper.updateByPrimaryKeySelective(wallet);	
	}

	@Override
	@Transactional(rollbackFor=Throwable.class)
	public Result<Boolean> resetPass(String userId, String pass) {
		if(pass == null || pass.isEmpty()) {
			return new Result<Boolean>(false, "密码不能为空");
		}
		
		UserWalletAO wallet = lockWallet(userId);
		if(wallet == null) {
			return new Result<>(false, "您未设置过初始密码");
		}
		
		pass = EncryptUtil.encrypt(wallet.getId() + EncryptUtil.encrypt(pass));
		
		UserWalletAO upd = new UserWalletAO();
		upd.setId(wallet.getId());
		upd.setPayPass(pass);
		
		userWalletMapper.updateByPrimaryKeySelective(upd);
		return new Result<Boolean>(true);
	}

	
}

package com.dunbian.jujiabao.service.cart;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dunbian.jujiabao.appobj.extend.CartAO;
import com.dunbian.jujiabao.appobj.extend.CartInfo;
import com.dunbian.jujiabao.appobj.extend.FoodSetAO;
import com.dunbian.jujiabao.appobj.extend.FoodWeekAO;
import com.dunbian.jujiabao.appobj.generator.CartExample;
import com.dunbian.jujiabao.appobj.generator.FoodWeekExample;
import com.dunbian.jujiabao.db.customer.CartCustomMapper;
import com.dunbian.jujiabao.db.generator.CartMapper;
import com.dunbian.jujiabao.db.generator.FoodSetMapper;
import com.dunbian.jujiabao.db.generator.FoodWeekMapper;
import com.dunbian.jujiabao.framework.util.DateTimeUtil;

@Service
public class CartService implements ICartService {

	@Resource
	private CartMapper cartMapper;
	
	@Resource
	private CartCustomMapper cartCustomMapper;
	
	@Resource
	private FoodSetMapper foodSetMapper;
	
	@Resource
	private FoodWeekMapper foodWeekMapper;
	
	private CartAO getCart(String foodSetId, String userId) {
		CartExample ce = new CartExample();
		ce.createCriteria().andUserIdEqualTo(userId).andSetIdEqualTo(foodSetId).andCartDateEqualTo(new Date());
		List<CartAO> cartList = cartMapper.selectByExample(ce);
		if(cartList == null || cartList.isEmpty()) {
			return null;
		}
		
		return cartList.get(0);
	}
	
	private String checkFoodWeek(String foodSetId) {
		Calendar today = Calendar.getInstance();
		int day = today.get(Calendar.DAY_OF_WEEK);
		day--;
		if(day == 0) {
			day = 7;
		}
		
		day = day*10;
		
		
		FoodWeekExample fwe = new FoodWeekExample();
		fwe.createCriteria().andDayEqualTo(String.valueOf(day));
		List<FoodWeekAO> fwList = foodWeekMapper.selectByExample(fwe);
		if(fwList == null || fwList.isEmpty()) {
			return null;
		}
		
		for(FoodWeekAO fw : fwList) {
			if(foodSetId != null && foodSetId.equals(fw.getSetId())) {
				return fw.getType();
			}
		}
		
		return null;
	}
	
	@Override
	@Transactional(rollbackFor=Throwable.class)
	public void addCart(int pcount, String foodSetId, String userId) {
		if(pcount <= 0) {
			pcount = 1;
		}
		CartAO cart = getCart(foodSetId, userId);
		if(cart == null) {
			String type = checkFoodWeek(foodSetId);
			if(type == null) {
				return;
			}
			cart = new CartAO();
			cart.setCount(0);
			cart.setSetId(foodSetId);
			cart.setUserId(userId);
			cart.setCartDate(new Date());
			cart.setType(type);
			
			int newCount = cart.getCount() + pcount;
			cart.setCount(newCount);
			
			if(cart.getCount() > CartAO.CART_MAX_COUNT) {
				cart.setCount(CartAO.CART_MAX_COUNT);
			}
			cartMapper.insertSelective(cart);
		} else {
			int newCount = cart.getCount() + pcount;
			cart.setCount(newCount);
			
			if(cart.getCount() > CartAO.CART_MAX_COUNT) {
				cart.setCount(CartAO.CART_MAX_COUNT);
			}
			
			cartMapper.updateByPrimaryKeySelective(cart);
		}
	}

	@Override
	@Transactional(rollbackFor=Throwable.class)
	public void setCart(int pcount, String foodSetId, String cartId, String userId) {
		if(pcount <= 0) {
			pcount = 0;
		}
		CartAO cart = getCart(foodSetId, userId);
		if(cart == null) {
			String type = checkFoodWeek(foodSetId);
			if(type == null) {
				return;
			}
			cart = new CartAO();
			cart.setCount(pcount);
			cart.setSetId(foodSetId);
			cart.setUserId(userId);
			cart.setCartDate(new Date());
			cart.setType(type);
			cart.setId(cartId);
			
			if(cart.getCount() > CartAO.CART_MAX_COUNT) {
				cart.setCount(CartAO.CART_MAX_COUNT);
			} else if(cart.getCount() <= 0) {
				return;
			}
			
			cartCustomMapper.insertWithId(cart);
		} else {
			cart.setCount(pcount);
			
			if(cart.getCount() > CartAO.CART_MAX_COUNT) {
				cart.setCount(CartAO.CART_MAX_COUNT);
			} else if(cart.getCount() <= 0) {
				cartMapper.deleteByPrimaryKey(cart.getId());
				return;
			}
			
			cartMapper.updateByPrimaryKeySelective(cart);
		}
	}

	@Override
	@Transactional(readOnly=true)
	public List<CartAO> getCartList(String userId) {
		CartExample ce = new CartExample();
		ce.createCriteria().andUserIdEqualTo(userId).andCartDateEqualTo(new Date());
		
		List<CartAO> cartList = cartMapper.selectByExample(ce);
		if(cartList != null) {
			for(CartAO cart : cartList) {
				FoodSetAO set = foodSetMapper.selectByPrimaryKey(cart.getSetId());
				cart.setFoodSet(set);
			}
		}
		
		return cartList;
	}

	@Override
	@Transactional(rollbackFor=Throwable.class)
	public void removeCart(String foodSetId, String userId) {
		CartExample ce = new CartExample();
		ce.createCriteria().andSetIdEqualTo(foodSetId).andUserIdEqualTo(userId);
		
		cartMapper.deleteByExample(ce);
	}

	@Override
	@Transactional(rollbackFor=Throwable.class)
	public void clearCart(String userId) {
		CartExample ce = new CartExample();
		ce.createCriteria().andUserIdEqualTo(userId);
		
		cartMapper.deleteByExample(ce);
	}

	@Override
	@Transactional(readOnly=true)
	public int getCartCount(String userId) {
		CartExample ce = new CartExample();
		ce.createCriteria().andUserIdEqualTo(userId).andCartDateEqualTo(new Date());
		
		List<CartAO> cartList = cartMapper.selectByExample(ce);
		int total = 0;
		if(cartList != null) {
			for(CartAO cart : cartList) {
				total += cart.getCount();
			}
		}
		return total;
	}

	@Override
	@Transactional(readOnly=true)
	public CartAO getCart(String cartId) {
		CartAO cart = cartMapper.selectByPrimaryKey(cartId);
		if(cart != null) {
			String today = DateTimeUtil.format(new Date(), DateTimeUtil.FORMAT_YMD);
			String cartDay = DateTimeUtil.format(cart.getCartDate(), DateTimeUtil.FORMAT_YMD);
			if(!today.equals(cartDay)) {
				return null;
			}
			
			FoodSetAO set = foodSetMapper.selectByPrimaryKey(cart.getSetId());
			cart.setFoodSet(set);
		}
		
		return cart;
	}

	@Override
	@Transactional(readOnly=true)
	public CartInfo getCartInfo(String userId) {
		return cartCustomMapper.getCartInfo(userId, new Date());
	}

}

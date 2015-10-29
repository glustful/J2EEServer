package com.dunbian.jujiabao.service.cart;

import java.util.List;

import com.dunbian.jujiabao.appobj.extend.CartAO;
import com.dunbian.jujiabao.appobj.extend.CartInfo;

public interface ICartService {

	void addCart(int pcount, String foodSetId, String userId);
	
	void setCart(int pcount, String foodSetId, String cartId, String userId);
	
	List<CartAO> getCartList(String userId);
	
	CartAO getCart(String cartId);
	
	void removeCart(String foodSetId, String userId);
	
	void clearCart(String userId);
	
	int getCartCount(String userId);
	
	CartInfo getCartInfo(String userId);
}

package com.dunbian.jujiabao.db.customer;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.dunbian.jujiabao.appobj.extend.CartInfo;
import com.dunbian.jujiabao.appobj.generator.Cart;

public interface CartCustomMapper {
    int insertWithId(Cart record);
    
    CartInfo getCartInfo(@Param("userId") String userId, @Param("today") Date today);
}

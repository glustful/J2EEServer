package com.dunbian.jujiabao.mvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.dunbian.jujiabao.appobj.extend.CartAO;
import com.dunbian.jujiabao.appobj.extend.DiscountAO;
import com.dunbian.jujiabao.appobj.extend.UserAO;
import com.dunbian.jujiabao.appobj.extend.UserAddressAO;
import com.dunbian.jujiabao.framework.json.JsonUtil;
import com.dunbian.jujiabao.framework.obj.Result;
import com.dunbian.jujiabao.service.address.IAddressService;
import com.dunbian.jujiabao.service.cart.ICartService;
import com.dunbian.jujiabao.service.discount.IDiscountSerivce;
import com.dunbian.jujiabao.util.UserUtil;
import com.puddingnet.mvc.servlet.CookieUtil;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Resource
    private ICartService cartService;

    @Resource
    private IAddressService addressService;

    @Resource
    private IDiscountSerivce discountSerivce;

    @RequestMapping(value="")
    public ModelAndView cart(HttpServletRequest request, Model model, HttpServletResponse response) {
         UserAO user = UserUtil.getCurrentLoginUser(request);
         List<CartAO> cartList = cartService.getCartList(user.getId());
         BigDecimal cartCount = new BigDecimal(0);
         BigDecimal sum = new BigDecimal(0);
         if(cartList != null) {
             for(CartAO cart : cartList) {
                 cartCount = cartCount.add(new BigDecimal(cart.getCount()));
                 sum = sum.add(cart.getFoodSet().getPrice().multiply(new BigDecimal(cart.getCount())));
             }
         }

         model.addAttribute("currentMenu", "cart");
         model.addAttribute("currentUser", user);
         model.addAttribute("cartCount", cartCount);

         if(cartCount.compareTo(new BigDecimal(0)) <= 0) {
        	 return MVCViewName.INDEX_CART_EMPTY.view();
         }

         model.addAttribute("dataList", cartList);
         model.addAttribute("sum", sum);

         UserAddressAO address = addressService.getDefaultAddress(user.getId());
         model.addAttribute("defaultAddress", address);

         BigDecimal disAmt = new BigDecimal(0);
         if(address != null) {
         	Map<String, List<DiscountAO>> discount = getDiscountPc(address.getTownId(), request, response);
             if(discount != null && discount.get("discount") != null) {
            	 model.addAttribute("discount", discount);
            	 for(DiscountAO dis : discount.get("discount")) {
            		 disAmt = disAmt.add(dis.getDiscountAmount());
            	 }
             }
         }

         BigDecimal realSum = new BigDecimal(0);
         if(disAmt.compareTo(new BigDecimal(0)) > 0) {
        	 realSum = sum.subtract(disAmt);
        	 model.addAttribute("disAmt", disAmt);
         } else {
        	 realSum = realSum.add(sum);
         }

         model.addAttribute("realSum", realSum);

        return MVCViewName.INDEX_CART.view();
    }

    @RequestMapping(value="/empty")
    public ModelAndView cartEmpty(HttpServletRequest request, Model model) {
    	  return MVCViewName.INDEX_CART_EMPTY.view();
    }

    @RequestMapping(value="/order/ok")
    public ModelAndView orderSuccess(HttpServletRequest request, Model model) {
    	return MVCViewName.INDEX_ORDER_SUCCESS.view();
    }

    @RequestMapping(value="/pay/ok")
    public ModelAndView paySuccess(HttpServletRequest request, Model model) {
    	return MVCViewName.INDEX_PAY_SUCCESS.view();
    }

    @ResponseBody
    @RequestMapping(value="/addcart")
    public Object addCartPc(int pcount, String set, String town, HttpServletRequest request, Model model, HttpServletResponse response) {
        UserAO user = UserUtil.getCurrentLoginUser(request);
        cartService.addCart(pcount, set, user.getId());
        int cartCount = cartService.getCartCount(user.getId());
        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put("success", true);
        ret.put("cartCount", cartCount);

        if(town != null) {
        	Map<String, List<DiscountAO>> discount = getDiscountPc(town, request, response);
            if(discount != null && discount.get("discount") != null) {
            	ret.put("discount", discount);
            }
        }
        return ret;
    }

    @RequestMapping(value="/refresh")
    public ModelAndView refreshCart(String address, UserAO user, HttpServletRequest request, Model model, HttpServletResponse response) {
    	if(user == null || user.getId() == null || user.getId().trim().isEmpty()) {
    		user = UserUtil.getCurrentLoginUser(request);
    	}
    	List<CartAO> cartList = cartService.getCartList(user.getId());
        BigDecimal cartCount = new BigDecimal(0);
        BigDecimal sum = new BigDecimal(0);
        if(cartList != null) {
            for(CartAO cart : cartList) {
                cartCount = cartCount.add(new BigDecimal(cart.getCount()));
                sum = sum.add(cart.getFoodSet().getPrice().multiply(new BigDecimal(cart.getCount())));
            }
        }

        model.addAttribute("cartCount", cartCount);
        model.addAttribute("dataList", cartList);
        model.addAttribute("sum", sum);

        BigDecimal disAmt = new BigDecimal(0);
        UserAddressAO userAddress = address == null ? null : addressService.getAddress(address);
        String town = userAddress != null ? userAddress.getTownId() : null;
        if(town != null) {
        	Map<String, List<DiscountAO>> discount = getDiscountPc(town, request, response);
            if(discount != null && discount.get("discount") != null) {
            	model.addAttribute("discount", discount);
            	for(DiscountAO dis : discount.get("discount")) {
	           		 disAmt = disAmt.add(dis.getDiscountAmount());
	           	 }
            }
        }

        BigDecimal realSum = new BigDecimal(0);
        if(disAmt.compareTo(new BigDecimal(0)) > 0) {
       	 realSum = sum.subtract(disAmt);
       	 model.addAttribute("disAmt", disAmt);
        } else {
       	 realSum = realSum.add(sum);
        }

        model.addAttribute("realSum", realSum);

        return MVCViewName.INDEX_CART_CONTENT.view();
    }

    @RequestMapping(value="/setcart")
    public ModelAndView setCartPc(int pcount, String set, String cartId, String address, HttpServletRequest request, Model model, HttpServletResponse response) {
        UserAO user = UserUtil.getCurrentLoginUser(request);
        cartService.setCart(pcount, set, cartId, user.getId());

//        int cnt = cartService.getCartCount(user.getId());
//        return new Result<>(true, cnt);
//        CartAO cart = cartService.getCart(cartId);
//        if(cart != null) {
//        	model.addAttribute("cart", cart);
//        }

        return refreshCart(address, user, request, model, response);
//        return "";
    }

    @ResponseBody
    @RequestMapping(value="/cartinfo")
    public Object getCartInfo(HttpServletRequest request) {
    	UserAO user = UserUtil.getCurrentLoginUser(request);
    	return new Result<>(cartService.getCartInfo(user.getId()));
    }

    @ResponseBody
    @RequestMapping(value="/phone/addcart")
    public Object addCart(int pcount, String set, String town, HttpServletRequest request, HttpServletResponse response) {
        UserAO user = UserUtil.getCurrentLoginUser(request);
        cartService.addCart(pcount, set, user.getId());

        Result<String> ret = new Result<>("成功加入购物车");
        if(town != null) {
        	Map<String, List<DiscountAO>> discount = getDiscount(town, request, response);
            if(discount != null && discount.get("discount") != null) {
            	ret.setExtend(discount);
            }
        }
        return ret;
    }

    @ResponseBody
    @RequestMapping(value="/phone/setcart")
    public Object setCart(int pcount, String set, String cartId, String town, HttpServletRequest request, HttpServletResponse response) {
        UserAO user = UserUtil.getCurrentLoginUser(request);
        cartService.setCart(pcount, set, cartId, user.getId());

        int cnt = cartService.getCartCount(user.getId());
        Result<Integer> ret = new Result<>(true, cnt);
        if(town != null) {
        	Map<String, List<DiscountAO>> discount = getDiscount(town, request, response);
            if(discount != null && discount.get("discount") != null) {
            	ret.setExtend(discount);
            }
        }
        return ret;
    }

    @ResponseBody
    @RequestMapping(value="/phone/removecart")
    public Object removeCart(String town, String set, HttpServletRequest request, HttpServletResponse response) {
        UserAO user = UserUtil.getCurrentLoginUser(request);
        cartService.removeCart(set, user.getId());

        int cnt = cartService.getCartCount(user.getId());

        Result<Integer> ret = new Result<>(true, cnt);
        if(town != null) {
        	Map<String, List<DiscountAO>> discount = getDiscount(town, request, response);
            if(discount != null && discount.get("discount") != null) {
            	ret.setExtend(discount);
            }
        }

        return ret;
    }

    @ResponseBody
    @RequestMapping(value="/phone/cartcount")
    public int getCartCount(HttpServletRequest request) {
        try {
            UserAO user = UserUtil.getCurrentLoginUser(request);
            return cartService.getCartCount(user.getId());
        } catch (Exception e) {
            return 0;
        }
    }

    @ResponseBody
    @RequestMapping(value="/phone/getcart")
    public Object getCart(HttpServletRequest request, HttpServletResponse response) {
        UserAO user = UserUtil.getCurrentLoginUser(request);

        List<CartAO> cartList = cartService.getCartList(user.getId());
        Map<String, Object> ret = new HashMap<>();
        ret.put("datalist", cartList);
        BigDecimal cnt = new BigDecimal(0);
        BigDecimal sum = new BigDecimal(0);
        if(cartList != null) {
            for(CartAO cart : cartList) {
                cnt = cnt.add(new BigDecimal(cart.getCount()));
                sum = sum.add(cart.getFoodSet().getPrice().multiply(new BigDecimal(cart.getCount())));
            }
        }

        ret.put("cnt", cnt);
        ret.put("sum", sum);
        UserAddressAO address = addressService.getDefaultAddress(user.getId());
        ret.put("defaultAddress", address);
        if(address != null) {
            Map<String, List<DiscountAO>> discount = getDiscount(address.getTownId(), request, response);
            if(discount != null && discount.get("discount") != null) {
            	ret.put("extend", discount);
            }
        }

        return ret;
    }

    @ResponseBody
    @RequestMapping(value="/getdiscount")
    public Map<String, List<DiscountAO>> getDiscountPc(String town, HttpServletRequest request, HttpServletResponse response) {
    		request.setAttribute("AppVersion", 3);
    		return getDiscount(town, request, response);
    }

    @ResponseBody
    @RequestMapping(value="/phone/getdiscount")
    public Map<String, List<DiscountAO>> getDiscount(String town, HttpServletRequest request, HttpServletResponse response) {
    	UserAO user = UserUtil.getCurrentLoginUser(request);
    	List<DiscountAO> discountList = discountSerivce.getDiscountInfo(town, user);

    	if(discountList != null && !discountList.isEmpty()) {
    		JsonUtil jsonUtil = JsonUtil.buildNonNullMapper();

        	List<String> ids = new ArrayList<>();
        	for(DiscountAO dis : discountList) {
        		ids.add(dis.getId());
        	}

        	String jsonStr = jsonUtil.toJson(ids);

        	String appVersion = request.getHeader("AppVersion");
        	if((appVersion == null || Integer.parseInt(appVersion.trim()) <= 2) && request.getAttribute("AppVersion") == null) {
        		CookieUtil.setCookie("discount", "[]", "/order", CookieUtil.MAXAGE_BROWSER, response);
        	} else {
        		CookieUtil.setCookie("discount", jsonStr, "/order", CookieUtil.MAXAGE_BROWSER, response);
        	}
    	} else {
    		CookieUtil.setCookie("discount", "[]", "/order", CookieUtil.MAXAGE_BROWSER, response);
    	}

    	Map<String, List<DiscountAO>> discountInfo = new TreeMap<String, List<DiscountAO>>();
    	if(discountList != null && discountList.isEmpty()) {
    		discountList = null;
    	}

    	discountInfo.put("discount", discountList);
    	return discountInfo;
    }

    @RequestMapping(value = "/paybank")
    public ModelAndView paybank(HttpServletRequest request, HttpServletResponse response, Model model) {
        return MVCViewName.INDEX_PAYBANK.view();
    }

    @RequestMapping(value = "/paypassword")
    public ModelAndView paypassword(HttpServletRequest request, HttpServletResponse response, Model model) {
        return MVCViewName.INDEX_PAYPASSWORD.view();
    }
}

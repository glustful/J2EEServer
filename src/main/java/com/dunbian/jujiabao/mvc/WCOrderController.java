package com.dunbian.jujiabao.mvc;

/**
 * Created by Administrator on 2015/6/14 0014.
 */

import com.dunbian.jujiabao.appobj.extend.*;
import com.dunbian.jujiabao.framework.json.JsonUtil;
import com.dunbian.jujiabao.framework.obj.Page;
import com.dunbian.jujiabao.framework.obj.Result;
import com.dunbian.jujiabao.service.address.IAddressService;
import com.dunbian.jujiabao.service.order.IOrderService;
import com.dunbian.jujiabao.util.UserUtil;
import com.puddingnet.mvc.servlet.CookieUtil;
import org.apache.log4j.Logger;
import org.codehaus.jackson.type.JavaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/wcorder")
public class WCOrderController {

    private static final Logger LOG = Logger.getLogger(OrderController.class.getName());

    @Resource
    private IOrderService orderService;

    @Resource
    private IAddressService addressService;

    @ResponseBody
    @RequestMapping(value="/submit")
    public Object submit(String address, String[] cartIds,
                                       HttpServletRequest request, HttpServletResponse response) {
        UserAO user = UserUtil.getCurrentLoginUser(request);
        OrderExtraAO orderExtra=new OrderExtraAO();
        orderExtra.setOrderFrom(OrderAO.ORDER_FROM_WEIXIN);
        orderExtra.setPlatform("weixin");
        Cookie ck = CookieUtil.getCookie("discount", request);
        String disJson = null;
        if(ck != null) {
            disJson = ck.getValue();
        }
        List<String> disList = null;
        if(disJson != null) {
            JsonUtil jsonUtil = JsonUtil.buildNonDefaultMapper();
            JavaType javaType = jsonUtil.constructParametricType(List.class, String.class);
            disList = jsonUtil.fromJson(disJson, javaType);
        }

        Result<OrderAO> ret = orderService.submitOrder(address, cartIds, user, orderExtra, disList);

        if(ret != null && ret.getData() != null) {
            CookieUtil.setCookie("town", ret.getData().getTownId(), "/", CookieUtil.MAXAGE_EVER, response);

            String deliveryTime = "12:00 - 13:00";
            if(ret.getData().getTownId() != null && !ret.getData().getTownId().isEmpty()) {
                RegionTimeAO regionTimeAO = addressService.getRegionTime(ret.getData().getTownId());
                if(regionTimeAO != null && regionTimeAO.getDeliveryTime() != null) {
                    deliveryTime = regionTimeAO.getDeliveryTime();
                }
            }

            String orderTip = "我们将在今天" + deliveryTime + "为您送达，请耐心等待.";
            ret.getData().setOrderTip(orderTip);
        } else {
            UserAddressAO userAddress = addressService.getAddress(address);
            CookieUtil.setCookie("town", userAddress.getTownId(), "/", CookieUtil.MAXAGE_EVER, response);
        }
        return ret;
    }

    @ResponseBody
    @RequestMapping(value="/show")
    public OrderAO show(String orderNo, HttpServletRequest request) {
        UserAO user = UserUtil.getCurrentLoginUser(request);
        //OrderAO order = orderService.getOrder(user.getId(), OrderId);
        OrderAO orderBuffer =  orderService.getOrderByOrderNo(orderNo);
        OrderAO order=orderService.getOrder(user.getId(),orderBuffer.getId());
        if(order == null) {
            return null;
        }

        UserAddressAO userAddressAO = addressService.getDefaultAddress(order.getAddressId());
        String deliveryTime = "12:00 - 13:00";
        if(userAddressAO != null && !userAddressAO.getTownId().isEmpty()) {
            RegionTimeAO regionTimeAO = addressService.getRegionTime(order.getAddressInfo().getTownId());
            if(regionTimeAO != null && regionTimeAO.getDeliveryTime() != null) {
                deliveryTime = regionTimeAO.getDeliveryTime();
            }
        }
        //orderDetailService
      //  order.setDetailList();
        String orderTip = "我们将在今天" + deliveryTime + "为您送达，请耐心等待.";

        order.setOrderTip(orderTip);
        return order;
    }



    @ResponseBody
    @RequestMapping(value="/list")
    public Object list(Integer pageNo, String status, HttpServletRequest request) {
        UserAO user = UserUtil.getCurrentLoginUser(request);
        Map<String, Object> ret = new HashMap<>();
        if(pageNo == null) {
            pageNo = 1;
        }
        Page page = new Page(pageNo, 5);
        int unpaidCount = orderService.countOrder(user.getId(), OrderAO.STATUS_ORDERED);
        List<OrderAO> orderList=new ArrayList<OrderAO>();
        orderList = orderService.getOrderList(user.getId(), status, page);
        ret.put("unpaidCount", unpaidCount);
        ret.put("orderList", orderList);
        return ret;
    }
}

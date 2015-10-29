package com.dunbian.jujiabao.mvc;


import com.dunbian.jujiabao.appobj.extend.RegionAO;
import com.dunbian.jujiabao.appobj.extend.UserAO;
import com.dunbian.jujiabao.appobj.extend.UserAddressAO;
import com.dunbian.jujiabao.service.address.IAddressService;
import com.dunbian.jujiabao.util.UserUtil;
import com.puddingnet.mvc.servlet.CookieUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/wcaddress")
public class WCAddressController {

    @Resource
    private IAddressService addressService;

    @ResponseBody
    @RequestMapping(value="/list")
    public Object get(HttpServletRequest request) {
        UserAO user = UserUtil.getCurrentLoginUser(request);
        return addressService.getAddressList(user.getId());
    }

    @ResponseBody
    @RequestMapping(value="/getRegionList")
    public Object getRegionList(String parent) {
        if(parent == null) {
            parent= RegionAO.CITY_ID_KUNMING;
        }
        List<RegionAO> ret = addressService.getRegionByParent(parent);
        List<Map<String, String>> lst = new ArrayList<>();
        if(ret != null) {
            for(RegionAO rg : ret) {
                Map<String, String> rec = new HashMap<>();
                rec.put("id", rg.getId());
                rec.put("name", rg.getName());
                rec.put("type", rg.getType());
                rec.put("hasChild", rg.getHasChild());
                lst.add(rec);
            }
        }
        return lst;
    }


    @ResponseBody
    @RequestMapping(value="/set")
    public Object saveAddress(UserAddressAO userAddress, HttpServletRequest request) {
        UserAO user = UserUtil.getCurrentLoginUser(request);
        userAddress.setUserId(user.getId());
        return addressService.save(userAddress);
    }


    @ResponseBody
    @RequestMapping(value="/remove")
    public Object removeAddress(String id, HttpServletRequest request) {
        Map<String, Object> retMap = new HashMap<String, Object>();
        try{
            UserAO user = UserUtil.getCurrentLoginUser(request);
            addressService.remove(user.getId(), id);
            retMap.put("res",false);
            retMap.put("msg","删除成功");
            return retMap;
        }catch(Exception error){
            retMap.put("res",false);
            retMap.put("msg","删除失败");
            return retMap;
        }
    }

    @ResponseBody
    @RequestMapping(value="/setdefault")
    public Object setDefault(String id, HttpServletRequest request, HttpServletResponse response) {
        UserAO user = UserUtil.getCurrentLoginUser(request);
        UserAddressAO userAddress = addressService.getAddress(id);
        if(userAddress != null) {
            CookieUtil.setCookie("town", userAddress.getTownId(), "/", CookieUtil.MAXAGE_EVER, response);
        }
        return addressService.setDefaultAddress(user.getId(), id);
    }

}


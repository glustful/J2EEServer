package com.dunbian.jujiabao.mvc;

import com.dunbian.jujiabao.appobj.extend.AdvertisementAO;
import com.dunbian.jujiabao.service.advertisement.IAdvertisementService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/6/14 0014.
 */
@Controller
@RequestMapping("/wcadvertisement")
public class WCAdvertisementController {

    @Resource
    private IAdvertisementService advertisementService;


    @ResponseBody
    @RequestMapping(value="/list")
    public Object list(String town,HttpServletRequest request) {
        if (town.trim().isEmpty()||town.trim()==null) {
            town="23877403403092068";
        }
        List<AdvertisementAO> data = advertisementService.getAdvertisement(town, new Date());
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("datalist", data);
        return retMap;
    }


    @ResponseBody
    @RequestMapping(value="/detail")
    public Object detail(String id) {
        return advertisementService.getAdvertisement(id);
    }

}

package com.dunbian.jujiabao.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2015/6/15 0015.
 */

@Controller
@RequestMapping(value="/wcregion")
public class WCRegionController {
    //获取所有区域店铺
    @ResponseBody
    @RequestMapping("/getregion")
    public Object comment(Model model, HttpServletRequest request) {
    return null;
    }
}

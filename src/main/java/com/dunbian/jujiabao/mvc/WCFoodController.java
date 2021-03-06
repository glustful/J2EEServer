package com.dunbian.jujiabao.mvc;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dunbian.jujiabao.appobj.extend.CommentAO;
import com.dunbian.jujiabao.appobj.extend.FoodSetAO;
import com.dunbian.jujiabao.appobj.extend.FoodWeekAO;
import com.dunbian.jujiabao.appobj.extend.RegionAO;
import com.dunbian.jujiabao.appobj.extend.RegionTimeAO;
import com.dunbian.jujiabao.appobj.extend.SeckillAO;
import com.dunbian.jujiabao.appobj.extend.UserAO;
import com.dunbian.jujiabao.appobj.extend.UserAddressAO;
import com.dunbian.jujiabao.food.IFoodService;
import com.dunbian.jujiabao.framework.obj.DataList;
import com.dunbian.jujiabao.framework.obj.Page;
import com.dunbian.jujiabao.framework.util.DateTimeUtil;
import com.dunbian.jujiabao.service.address.IAddressService;
import com.dunbian.jujiabao.service.cart.ICartService;
import com.dunbian.jujiabao.service.comment.ICommentService;
import com.dunbian.jujiabao.service.seckill.ISeckillService;
import com.dunbian.jujiabao.util.UserUtil;

@Controller
@RequestMapping("/wcfood")
public class WCFoodController {
    @Resource
    private IFoodService foodService;

    @Resource
    private ICartService cartService;

    @Resource
    private IAddressService addressService;

    @Resource
    private ICommentService commentService;

    @Resource
    private ISeckillService seckillService;



    public void setCurrentUser(Model model, HttpServletRequest request) {
        try {
            model.addAttribute("currentMenu", "home");
            UserAO user = (UserAO) UserUtil.getCurrentLoginUser(request);
            model.addAttribute("currentUser", user);
            int cartCount = cartService.getCartCount(user.getId());
            model.addAttribute("cartCount", cartCount);
        } catch (Exception e) {
        }
    }

    //获取商品评论列表；
    @ResponseBody
    @RequestMapping("/comment")
    public Object comment(String setId, Integer pageNo, Model model, HttpServletRequest request) {
        if (setId == null || setId.isEmpty()) {
            return "no msg";
        } else {
            if (pageNo == null) {
                pageNo = 1;
            }
            Page page = new Page(pageNo, 5);
            Map<String, Object> retData = new HashMap<String, Object>();
            DataList<CommentAO> ret = commentService.getCommentListBySet(setId, page);
            if (ret.getData() != null) {
                retData.put("dataList", ret.getData());
            }
            System.out.print("dataList");
            return retData;
        }
    }

    @ResponseBody
    @RequestMapping("/getDefaultAddress")
    public Object getDefaultAddress(HttpServletRequest request) {
        Map<String, Object> retMap = new HashMap<String, Object>();
        try{
            UserAO user = UserUtil.getCurrentLoginUser(request);
            UserAddressAO userAddressAO=addressService.getDefaultAddress(user.getId());
            retMap.put("res", true);
            retMap.put("data", userAddressAO);
            return retMap;
        }catch(Exception error){
            retMap.put("res", false);
            retMap.put("data", null);
            return retMap;
        }
    }

    //获取商品列表(time:10(今天),20(明天))；
    @ResponseBody
    @RequestMapping("/setlist")
    public Object setlist(String time, String town, Model model, HttpServletRequest request) {
//        DataList<FoodSetAO> dataList = foodService.getFoodSetList(time);
//        List<FoodSetAO> listBuffer= dataList.getData();
//        List<FoodSetAO> data=new ArrayList<>();
//        for (int i = 0; i <listBuffer.size() ; i++) {
//            FoodSetAO fao=listBuffer.get(i);
//            if (fao.getPrice().compareTo(new BigDecimal(15))>=0) {//筛选大于15元的套餐
//                data.add(listBuffer.get(i));
//            }
//        }
        List<FoodSetAO> data = foodService.getFoodSetList(time).getData();

        UserAO user = null;
        try {
            user = (UserAO) UserUtil.getCurrentLoginUser(request);
        } catch (Exception e) {
        }
        if(user != null) {
            filterFoodSetByAddressType(data, user);
        }
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("dataList", data);
        String startTimeStr = "8:00";
        String endTimeStr = "11:00";
        String deliveryTimeStr = "12:00 - 13:00";

        String leftTime = "";
        boolean canOrder = true;
        Date endTime = null;
        if (!town.trim().isEmpty()) {
            RegionTimeAO regionTimeAO = addressService.getRegionTime(town);
            if (regionTimeAO != null && regionTimeAO.getEndTime() != null) {
                endTime = regionTimeAO.getEndTime();
                if(regionTimeAO.getStartTime() != null) {
                    startTimeStr = DateTimeUtil.format(regionTimeAO.getStartTime(),
                            DateTimeUtil.FORMAT_HM);
                }
                if(regionTimeAO.getEndTime() != null) {
                    endTimeStr = DateTimeUtil.format(regionTimeAO.getEndTime(),
                            DateTimeUtil.FORMAT_HM);
                }
                if(regionTimeAO.getDeliveryTime() != null && !regionTimeAO.getDeliveryTime().isEmpty()) {
                    deliveryTimeStr = regionTimeAO.getDeliveryTime();
                }
            }else {//找不到配置对象，暂时给个默认值
                endTime = DateTimeUtil.parse("11:00", DateTimeUtil.FORMAT_HM);
            }
        }

        if((time == null || time.isEmpty()
                || FoodSetAO.TYPE_TODAY.equals(time))) {//今天菜谱
            if (!town.trim().isEmpty()) {
                // 首先把当前的时间的年月日设置到订购限制时间上
                Date now = new Date();
                String realEndTimeStr = DateTimeUtil.format(now,
                        DateTimeUtil.FORMAT_YMD)
                        + " "
                        + DateTimeUtil.format(endTime, DateTimeUtil.FORMAT_HM);
                endTime = DateTimeUtil.parse(realEndTimeStr,
                        DateTimeUtil.FORMAT_YMD_HM);
                long between = (endTime.getTime() - now.getTime()) / 1000;// 除以1000是为了转换成秒
                if (between < 0) { // 已经超过下单时间
                    leftTime = "订餐已结束";
                    canOrder = false;
                } else {
                    long hour = between % (24 * 3600) / 3600;
                    long minute = between % 3600 / 60 + 1;
                    if (hour > 0) {
                        leftTime += "离订餐结束：" + hour + "小时";
                    } else if (minute > 0) {
                        leftTime += "离订餐结束：" + minute + "分";
                    }
                }
            }

            List<SeckillAO> seckillList = seckillService.getSeckillList(town);
            if(seckillList != null && !seckillList.isEmpty()) {
                retMap.put("miaosha", seckillList);
            }
        } else {//明天
            leftTime = "欢迎明天订购";
            canOrder = false;
        }

        retMap.put("leftTime", leftTime);
        retMap.put("canOrder", canOrder);
        retMap.put("bookStr", startTimeStr + " - " + endTimeStr + "预定");
        retMap.put("deliveryStr", deliveryTimeStr + "送达");
        return retMap;
    }

    private void filterFoodSetByAddressType(List<FoodSetAO> data, UserAO user) {
        if(data == null || user == null) {
            return;
        }

        UserAddressAO address = addressService.getDefaultAddress(user.getId());
        if(address==null){
            return ;
        }
        RegionAO region = addressService.getRegionById(address.getBlockId());
        if(RegionAO.TYPE_COMMUNITY.equals(region.getType())) {
            Iterator<FoodSetAO> it = data.iterator();
            while(it.hasNext()) {
                FoodSetAO food = it.next();
                if(food.getPrice() != null && food.getPrice().doubleValue() <=15 && food.getPrice().doubleValue() > 2) {
                    it.remove();
                }
            }
        }
    }


    @ResponseBody
    @RequestMapping(value="/commentOrder")
    public Object commentOrder(String[] orderDetail, String[] comment, HttpServletRequest request) {
        UserAO user = UserUtil.getCurrentLoginUser(request);
        return commentService.comment(user, orderDetail, comment);
    }
    @ResponseBody
    @RequestMapping("/setdetail")
    public Object setDetail(String setid, Model model) {
        FoodSetAO data = foodService.getFoodSet(setid);
        Map<String, Object> retMap = new HashMap<String, Object>();
        retMap.put("data", data.getFoodDetailList());
        return retMap;
    }

    @ResponseBody
    @RequestMapping("/weekdetail")
    public Object weekDetail(@CookieValue(value="town",defaultValue="")String town, String weekId, Model model) {
        FoodSetAO data = foodService.getFoodSetByWeekId(weekId);
        Map<String, Object> retMap = new HashMap<String, Object>();
        if (town.trim().isEmpty()||town.trim()==null) {
            town="23877403403092068";
        }
        String time = null;
        if (isToday(weekId)) {
            time = FoodSetAO.TYPE_TODAY;
        } else {
            time = FoodSetAO.TYPE_TOMORROW;
        }
        boolean canOrder = true;
        String leftTime = "";
        if ((time == null || time.isEmpty()
                || FoodSetAO.TYPE_TODAY.equals(time))) {//今天菜谱
            if (!town.trim().isEmpty()) {
                RegionTimeAO regionTimeAO = addressService.getRegionTime(town);
                if (regionTimeAO != null && regionTimeAO.getEndTime() != null) {
                    Date endTime = regionTimeAO.getEndTime();
                    //首先把当前的时间的年月日设置到订购限制时间上
                    Date now = new Date();
                    String realEndTimeStr = DateTimeUtil.format(now, DateTimeUtil.FORMAT_YMD) + " "
                            + DateTimeUtil.format(endTime, DateTimeUtil.FORMAT_HM);
                    endTime = DateTimeUtil.parse(realEndTimeStr, DateTimeUtil.FORMAT_YMD_HM);
                    long between = (endTime.getTime() - now.getTime()) / 1000;//除以1000是为了转换成秒
                    if (between < 0) { //已经超过下单时间
                        leftTime = "订餐已结束";
                        canOrder = false;
                    } else {
                        long hour = between % (24 * 3600) / 3600;
                        long minute = between % 3600 / 60 + 1;
                        if (hour > 0) {
                            leftTime += "离订餐结束：" + hour + "小时";
                        } else if (minute > 0) {
                            leftTime += "离订餐结束：" + minute + "分";
                        }
                    }
                }
            }
        } else {//明天
            canOrder = false;
            leftTime = "欢迎明天订购";
        }
        retMap.put("canOrder", canOrder);
        retMap.put("leftTime", leftTime);
        retMap.put("data", data);
        return retMap;
    }



    private boolean isToday(String weekId) {
        if (weekId == null || weekId.isEmpty()) {
            return true;
        }
        FoodWeekAO foodWeekAO = foodService.getFoodWeekByWeekId(weekId);
        Calendar cl = Calendar.getInstance();
        int day = cl.get(Calendar.DAY_OF_WEEK);
        day--;
        if (day == 0) {
            day = 7;
        }
        String todayWeek = day * 10 + "";
        if (foodWeekAO.getDay().equals(todayWeek)) {
            return true;
        }
        return false;
    }
}

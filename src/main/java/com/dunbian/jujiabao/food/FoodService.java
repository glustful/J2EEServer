package com.dunbian.jujiabao.food;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dunbian.jujiabao.appobj.extend.FoodDetailAO;
import com.dunbian.jujiabao.appobj.extend.FoodSetAO;
import com.dunbian.jujiabao.appobj.extend.FoodWeekAO;
import com.dunbian.jujiabao.appobj.generator.FoodWeekExample;
import com.dunbian.jujiabao.db.customer.FoodDetailCustomMapper;
import com.dunbian.jujiabao.db.customer.FoodSetCustomMapper;
import com.dunbian.jujiabao.db.customer.ResourceCustomMapper;
import com.dunbian.jujiabao.db.generator.FoodSetMapper;
import com.dunbian.jujiabao.db.generator.FoodWeekMapper;
import com.dunbian.jujiabao.framework.obj.DataList;

@Service
public class FoodService implements IFoodService {
	
	@Resource
	private FoodSetMapper foodSetMapper;
	
	@Resource
	private FoodSetCustomMapper foodSetCustomMapper;
      
	@Resource
	private ResourceCustomMapper resourceCustomMapper;
	
	@Resource
	private FoodDetailCustomMapper foodDetailCustomMapper;
	
	@Resource
	private FoodWeekMapper foodWeekMapper;
	
	@Override
	public FoodSetAO getFoodSet(String id) {
		if(id == null) {
			return null;
		}
		//首先拿到套餐主对象
		FoodSetAO foodSetAO = foodSetMapper.selectByPrimaryKey(id);
		//查询构成套餐的单品对象集合
		if(foodSetAO != null) {
			//查询轮番图路径
			List<String> multiImgPaths = resourceCustomMapper.getFilePathsByDataId(id, null);
			foodSetAO.setMultiImgPaths(multiImgPaths);
			
			List<FoodDetailAO> foodDetailAOs = foodDetailCustomMapper.selectBySetId(foodSetAO.getId());
			foodSetAO.setFoodDetailList(foodDetailAOs);
		}
		
		return foodSetAO;
	}
	

	@Override
	public DataList<FoodSetAO> getFoodSetList(String time) {
		//首先处理时间
		if(time == null) {
			time = FoodSetAO.TYPE_TODAY;
		}
		String weekDay = getDay(time);
		List<FoodSetAO> dataList = foodSetCustomMapper.getFoodSets(weekDay);
		
		Map<String, Integer> todayRemainCount = getDayRemainCount(weekDay);
		for(FoodSetAO foodSetAO : dataList) {
			int remainCount = 999;
			if(todayRemainCount.get(foodSetAO.getId()) != null) {
				remainCount = todayRemainCount.get(foodSetAO.getId());
				if(remainCount < 0) {
					remainCount = 0;
				}
			}
			foodSetAO.setRemainMsg("剩余" + remainCount + "份");
			if(remainCount <= 0) {
				foodSetAO.setSellOut(true);
			} else {
				foodSetAO.setSellOut(false);
			}
		}
		return new DataList<FoodSetAO>(dataList, -1);
	}
	
	private Map<String, Integer> getDayRemainCount(String weekDay) {
		FoodWeekExample example = new FoodWeekExample();
		example.createCriteria().andDayEqualTo(weekDay);
		List<FoodWeekAO> foodWeeks = foodWeekMapper.selectByExample(example);
		
		Map<String, Integer> ret = new HashMap<String, Integer>();
		for(FoodWeekAO tmpFoodWeekAO : foodWeeks) {
			if(tmpFoodWeekAO != null && tmpFoodWeekAO.getId() != null) {
				Integer remainCount = 999;
				if(tmpFoodWeekAO.getDayCount() != null) {
					remainCount = tmpFoodWeekAO.getDayCount();
				}
				if(tmpFoodWeekAO.getDaySale() != null) {
					remainCount -= tmpFoodWeekAO.getDaySale();
				}
				ret.put(tmpFoodWeekAO.getSetId(), remainCount);
			}
		}
		return ret;
	}
	
	private String getDay(String time) {
		String ret = null;
		Calendar cl = Calendar.getInstance();
		if(FoodSetAO.TYPE_TOMORROW.equals(time)) {//明天
			cl.add(Calendar.DAY_OF_MONTH, 1);
		}
		int tmp = cl.get(Calendar.DAY_OF_WEEK);
		switch (tmp) {
		case Calendar.MONDAY:
			ret = FoodWeekAO.DAY_MONDAY;
			break;
		case Calendar.TUESDAY:
			ret = FoodWeekAO.DAY_TUESDAY;
			break;
		case Calendar.WEDNESDAY:
			ret = FoodWeekAO.DAY_WEDNESDAY;
			break;
		case Calendar.THURSDAY:
			ret = FoodWeekAO.DAY_THURSDAY;
			break;
		case Calendar.FRIDAY:
			ret = FoodWeekAO.DAY_FRIDAY;
			break;
		case Calendar.SATURDAY:
			ret = FoodWeekAO.DAY_SATURDAY;
			break;
		case Calendar.SUNDAY:
			ret = FoodWeekAO.DAY_SUNDAY;
			break;
		default:
			break;
		}
		return ret;
	}


	@Override
	public FoodSetAO getFoodSetByWeekId(String weekId) {
		FoodSetAO ret = null;
		List<FoodSetAO> dataList = foodSetCustomMapper.getFoodSetByWeekId(weekId);
		if(dataList != null && !dataList.isEmpty()) {
			ret = dataList.get(0);
			//查询构成套餐的单品对象集合
			if(ret != null) {
				FoodWeekAO foodWeek = foodWeekMapper.selectByPrimaryKey(weekId);
				Map<String, Integer> dayRemainCount = this.getDayRemainCount(foodWeek.getDay());
				int remainCount = dayRemainCount.get(ret.getId());
				ret.setRemainMsg("剩余" + remainCount + "份");
				if(remainCount <= 0) {
					ret.setSellOut(true);
				} else {
					ret.setSellOut(false);
				}
				//查询轮番图路径
				List<String> multiImgPaths = resourceCustomMapper.getFilePathsByDataId(ret.getId(), null);
				ret.setMultiImgPaths(multiImgPaths);
				
				List<FoodDetailAO> foodDetailAOs = foodDetailCustomMapper.selectBySetId(ret.getId());
				ret.setFoodDetailList(foodDetailAOs);
			}
		}
		return ret;
	}


	@Override
	public FoodWeekAO getFoodWeekByWeekId(String weekId) {
		return foodWeekMapper.selectByPrimaryKey(weekId);
	}
	  
}

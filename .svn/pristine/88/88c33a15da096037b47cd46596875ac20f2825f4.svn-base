package com.dunbian.jujiabao.service.pay;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.dunbian.jujiabao.appobj.extend.PayLogAO;
import com.dunbian.jujiabao.db.generator.PayLogMapper;
import com.dunbian.jujiabao.framework.obj.Result;

@Service
public class PayLogService implements IPayLogService {

	@Resource
	PayLogMapper payLogMapper;
	
	@Override
	public Result<Boolean> log(PayLogAO log) {
		if(log == null) {
			return new Result<>(false, "参数为空！");
		}
		int count = payLogMapper.insert(log);
		return new Result<>(count > 0);
	}

}

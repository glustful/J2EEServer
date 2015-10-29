package com.dunbian.jujiabao.service.user;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dunbian.jujiabao.appobj.extend.FileAO;
import com.dunbian.jujiabao.appobj.extend.ResourceAO;
import com.dunbian.jujiabao.appobj.extend.UserAO;
import com.dunbian.jujiabao.appobj.generator.ResourceExample;
import com.dunbian.jujiabao.appobj.generator.UserExample;
import com.dunbian.jujiabao.db.generator.ResourceMapper;
import com.dunbian.jujiabao.db.generator.UserMapper;
import com.dunbian.jujiabao.framework.obj.Result;
import com.dunbian.jujiabao.framework.util.EncryptUtil;
import com.dunbian.jujiabao.service.file.IFileService;


@Service
public class UserService implements IUserService {

	@Resource
	private UserMapper userMapper;
	
	@Resource
	private ResourceMapper resourceMapper;
	
	@Resource
	private IFileService fileService;
	
	@Transactional(readOnly=true)
	public Result<UserAO> check4Login(String userName, String pass) {
		if(userName == null || userName.isEmpty() || pass == null || pass.isEmpty()) {
			return new Result<UserAO>(false, "用户名和密码都不能为空！");
		}
		UserExample ue = new UserExample();
		ue.createCriteria().andUserNameEqualTo(userName).andStatusEqualTo(UserAO.STATUS_NORMAL);
		List<UserAO> userList = userMapper.selectByExample(ue);
		if(userList == null || userList.isEmpty()) {
			return new Result<UserAO>(false, "用户不存在或密码错误");
		}
		
		UserAO user = userList.get(0);
		String encryptPwd = EncryptUtil.encrypt(user.getId() + EncryptUtil.encrypt(pass));
		if(encryptPwd == null || !encryptPwd.equals(user.getPassword())) {
			return new Result<UserAO>(false, "用户不存在或密码错误");
		}
		
		user.setPassword(null);
		return new Result<UserAO>(user);
	}

	@Override
	@Transactional(rollbackFor=Throwable.class)
	public Result<UserAO> register(UserAO user) {
		if(user == null){
            return new Result<>(false, "参数user不能为空");
        } else if(user.getMobile() == null || "".equals(user.getMobile())){
        	return new Result<>(false, "手机不能为空");
        }  else if(user.getPassword() == null || "".equals(user.getPassword())){
        	return new Result<>(false, "用户的密码不能为空");
        }
        
        //首先检查一下手机号是否已经存在
    	UserExample ue = new UserExample();
        ue.createCriteria().andMobileEqualTo(user.getMobile());
        int count = userMapper.countByExample(ue);
        if(count > 0) {
        	return new Result<>(false, "手机已存在");
        }
        //接着检查一下昵称是否已经存在
        ue = new UserExample();
        ue.createCriteria().andNicknameEqualTo(user.getNickname());
        count = userMapper.countByExample(ue);
        if(count > 0) {
        	return new Result<>(false, "昵称已存在");
        }
        
        userMapper.insert(user);
        String encryptPwd = EncryptUtil.encrypt(user.getId() + EncryptUtil.encrypt(user.getPassword()));
        UserAO upd = new UserAO();
        upd.setId(user.getId());
        upd.setPassword(encryptPwd);
        userMapper.updateByPrimaryKeySelective(upd);
        return new Result<>(user);
	}

	@Override
	@Transactional(rollbackFor=Throwable.class)
	public Result<UserAO> update(UserAO user) {
		if(user == null || user.getId() == null || user.getId().isEmpty()) {
			return new Result<UserAO>(false,"用户信息不完善！");
		}
		
		if(user.getNickname() != null) {
			UserExample ue = new UserExample();
			ue.createCriteria().andNicknameEqualTo(user.getNickname()).andIdNotEqualTo(user.getId());
			int i = userMapper.countByExample(ue);
			if(i > 0) {
				return new Result<UserAO>(false, "昵称已被他人占用，请修改后再提交");
			}
		}
		userMapper.updateByPrimaryKeySelective(user);
		return new Result<UserAO>(user);
	}

	@Override
	@Transactional(rollbackFor=Throwable.class)
	public Result<UserAO> resetPwd(String userId, String oldPwd, String newPwd) {
		//判断一下旧密码是否正确
		String encryptOldPwd = EncryptUtil.encrypt(userId + EncryptUtil.encrypt(oldPwd));
		UserExample example = new UserExample();
		example.createCriteria().andPasswordEqualTo(encryptOldPwd).andIdEqualTo(userId);
		int count = userMapper.countByExample(example);
		if(count <= 0) {
			return new Result<UserAO>(false,"旧密码不正确！");
		}
		
		String encryptNewPwd = EncryptUtil.encrypt(userId + EncryptUtil.encrypt(newPwd));
		UserAO tmpUserAO = new UserAO();
		tmpUserAO.setId(userId);
		tmpUserAO.setPassword(encryptNewPwd);
		userMapper.updateByPrimaryKeySelective(tmpUserAO);
		return new Result<UserAO>(tmpUserAO);
	}
	
	private void resetFileResource(String userId, FileAO file) {
		ResourceExample re = new ResourceExample();
		re.createCriteria().andDataIdEqualTo(userId).andDataTypeEqualTo(ResourceAO.DATA_TYPE_DETAIL_USERHEAD);
		resourceMapper.deleteByExample(re);
		
		ResourceAO resource = new ResourceAO();
		resource.setCreateTime(new Date());
		resource.setDataId(userId);
		resource.setDataType(ResourceAO.DATA_TYPE_DETAIL_USERHEAD);
		resource.setFileId(file.getId());
		resource.setFileName(resource.getFileName());
		resource.setFilePath(resource.getFilePath());
		resource.setResourceType(ResourceAO.RESOURCE_TYPE_IMG);
		resource.setSort(0);
		resource.setStatus(ResourceAO.STATUS_NOMAL);
		resource.setUserId(userId);
		
		resourceMapper.insert(resource);
	}

	@Override
	@Transactional(rollbackFor=Throwable.class)
	public void setHead(String userId, FileAO file) {
		UserAO user = new UserAO();
		user.setId(userId);
		user.setLogo(file.getFilePath());
		userMapper.updateByPrimaryKeySelective(user);
		
		resetFileResource(userId, file);
		fileService.afterSaveFile(file);
	}

	@Override
	@Transactional(rollbackFor=Throwable.class)
	public boolean updateCredit(String userId, Integer increment) {
		UserAO user = userMapper.selectByPrimaryKey(userId);
		if(user != null && increment != null) {
			Integer credit = user.getCredit();
			if(credit == null) {
				credit = 0;
			}
			Integer gradeCredit = user.getGradeCredit();
			if(gradeCredit == null) {
				gradeCredit = 0;
			}
			UserAO tmpUser = new UserAO();
			tmpUser.setId(user.getId());
			tmpUser.setCredit(credit +increment);
			tmpUser.setGradeCredit(gradeCredit+increment);
			userMapper.updateByPrimaryKeySelective(tmpUser);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean checkExists(String userName) {
		UserExample ue = new UserExample();
		ue.createCriteria().andUserNameEqualTo(userName);
		
		return userMapper.countByExample(ue) > 0;
	}

	@Override
	public Result<Boolean> resetPwd(String userName, String newPwd) {
		UserExample example = new UserExample();
		example.createCriteria().andUserNameEqualTo(userName);
		List<UserAO> userList = userMapper.selectByExample(example);
		if(userList == null || userList.isEmpty()) {
			return new Result<Boolean>(false,"用户不存在！");
		}
		
		String encryptNewPwd = EncryptUtil.encrypt(userList.get(0).getId() + EncryptUtil.encrypt(newPwd));
		UserAO tmpUserAO = new UserAO();
		tmpUserAO.setId(userList.get(0).getId());
		tmpUserAO.setPassword(encryptNewPwd);
		userMapper.updateByPrimaryKeySelective(tmpUserAO);
		return new Result<>(true);
	}

}

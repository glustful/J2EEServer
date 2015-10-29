package com.dunbian.jujiabao.service.user;

import com.dunbian.jujiabao.appobj.extend.FileAO;
import com.dunbian.jujiabao.appobj.extend.UserAO;
import com.dunbian.jujiabao.framework.obj.Result;

public interface IUserService {

	Result<UserAO> check4Login(String user, String pass);
	
	  /**
     * 注册
     * @param user 用户对象
     * @return
     */
    Result<UserAO> register(UserAO user);
    
    
    /**
     * 修改用户信息（昵称、电话等）
     * @param user 用户对象
     * @return
     */
    Result<UserAO> update(UserAO user);
    
    /**
     * 修改用户积分
     * @param userId 用户对象
     * @param increment 用户对象
     * @return
     */
    boolean updateCredit(String userId, Integer increment);
    
    /**
     * 修改用户密码
     * @param user 用户对象
     * @return
     */
    Result<UserAO> resetPwd(String userId, String oldPwd, String newPwd);
    
    Result<Boolean> resetPwd(String userName, String newPwd);
    
    void setHead(String userId, FileAO file);
    
    boolean checkExists(String userName);
}

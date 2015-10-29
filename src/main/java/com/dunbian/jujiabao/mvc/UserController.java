package com.dunbian.jujiabao.mvc;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.dunbian.jujiabao.appobj.extend.FileAO;
import com.dunbian.jujiabao.appobj.extend.UserAO;
import com.dunbian.jujiabao.framework.obj.Result;
import com.dunbian.jujiabao.framework.util.EncryptUtil;
import com.dunbian.jujiabao.framework.util.ImageUtil;
import com.dunbian.jujiabao.service.address.IAddressService;
import com.dunbian.jujiabao.service.cart.ICartService;
import com.dunbian.jujiabao.service.file.IFileService;
import com.dunbian.jujiabao.service.user.IUserService;
import com.dunbian.jujiabao.util.UserUtil;

@Controller
@RequestMapping(value="/user")
public class UserController {

    @Resource
    private IUserService userService;

    @Resource
    private IFileService fileService;

    @Resource
    private IAddressService addressService;

    @Resource
    private ICartService cartService;

    @RequestMapping(value="/profile")
    public ModelAndView userProfile(HttpServletRequest request, Model model) {
    	setCurrentUser(model, request);
    	model.addAttribute("activetap", "profile");
        return MVCViewName.PROFILE_PROFILE.view();
    }

    public void setCurrentUser(Model model, HttpServletRequest request) {
		try {
			model.addAttribute("currentMenu", "user");
			UserAO user = UserUtil.getCurrentLoginUser(request);
			model.addAttribute("currentUser", user);
			int cartCount = cartService.getCartCount(user.getId());
			model.addAttribute("cartCount", cartCount);
		} catch (Exception e) {}
	}

    @ResponseBody
    @RequestMapping(value="/phone/update")
    public Object phoneUpdate(String nickname, String mobile, HttpServletRequest request) {
        UserAO user = UserUtil.getCurrentLoginUser(request);
        user.setNickname(nickname);;
        user.setMobile(mobile);
        user.setPassword(null);
        Result<UserAO> ret = userService.update(user);
        if(ret.isSuccess() && ret.getData() != null) {
            UserUtil.resetUserInfo(user);
        }
        return ret;
    }

    @ResponseBody
    @RequestMapping(value="/update")
    public Object update(String nickname, String mobile, HttpServletRequest request) {
    	UserAO user = UserUtil.getCurrentLoginUser(request);
    	user.setNickname(nickname);;
    	user.setMobile(mobile);
    	user.setPassword(null);
    	Result<UserAO> ret = userService.update(user);
    	if(ret.isSuccess() && ret.getData() != null) {
    		UserUtil.resetUserInfo(user);
    	}
    	return ret;
    }

    @ResponseBody
    @RequestMapping(value="/phone/getuser")
    public Object getuser(HttpServletRequest request) {
        UserAO user = UserUtil.getCurrentLoginUser(request);
        return new Result<UserAO>(user);
    }

    @ResponseBody
    @RequestMapping(value="/phone/resetpwd")
    public Object resetpwd(String oldPwd, String newPwd, HttpServletRequest request) {
        UserAO user = UserUtil.getCurrentLoginUser(request);
        UserAO tmpUserAO = new UserAO();
        tmpUserAO.setId(user.getId());
        if(oldPwd == null || oldPwd.isEmpty() || newPwd == null || newPwd.isEmpty()) {
            return new Result<UserAO>(false, "新旧密码都不能为空！");
        }
        Result<UserAO> ret = userService.resetPwd(user.getId(), oldPwd, newPwd);
        return ret;
    }

    @ResponseBody
    @RequestMapping(value="/resetpwd")
    public Object resetpwdPc(String oldPwd, String newPwd, HttpServletRequest request) {
    	return resetpwd(oldPwd, newPwd, request);
    }

    private Pattern pattern = Pattern.compile("(jpg|jpeg|gif|png|bmp).*");

    @RequestMapping(value = "/phone/head", method = RequestMethod.POST)
    public ModelAndView upload(HttpServletRequest request, HttpServletResponse response, Model model) {
        UserAO user = UserUtil.getCurrentLoginUser(request);

        if(request.getContentLength() > 1024*1024*10.1) {
            model.addAttribute("ret", false);
            model.addAttribute("msg", "单张图片需小于10M");
            return MVCViewName.UPLOAD_RES.view();
        }
        MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
        File localFile = null;
        if(multiRequest.getFileMap() != null) {
            for(Entry<String, MultipartFile> entry : multiRequest.getFileMap().entrySet()) {
                MultipartFile file = entry.getValue();
                OutputStream os = null;
                InputStream is = null;

                try {
                    if(file.getOriginalFilename() == null || file.getOriginalFilename().trim().equals("")) {
                        continue;
                    }
                    String fileName = file.getOriginalFilename();
                    fileName = new String(fileName.getBytes("iso-8859-1"), "utf-8");
                    String myFileName = EncryptUtil.getUuid();
                    String fileType = "";
                    fileType = fileName.lastIndexOf(".") > -1  && fileName.lastIndexOf(".") != fileName.length() - 1? fileName.substring(fileName.lastIndexOf(".") + 1) : "unknown";
                    Matcher matcher = pattern.matcher(fileType);
                    if(!matcher.matches()) {
                        model.addAttribute("ret", false);
                        model.addAttribute("msg", "只允许上传[jpg、jpeg、gif、png、bmp]等格式的图片");
                        return MVCViewName.UPLOAD_RES.view();
                    } else {
                        fileType = matcher.group(1);
                    }
                    int fpartition = (int)(((long)(System.currentTimeMillis() * Math.random())) & (fileService.getTotalPartition() - 1));
                    localFile = new File(URLDecoder.decode(URLEncoder.encode(fileService.getUploadDir() + File.separator + fpartition + File.separator + myFileName + "." + fileType, fileService.getFileNameEncoding()), fileService.getFileNameEncoding()));
                    if(!localFile.getParentFile().exists()) {
                        localFile.getParentFile().mkdirs();
                    }

                    FileAO ao = new FileAO();
                    ao.setExtension(fileType);
                    ao.setFileName(fileName);
                    ao.setFilePath("/" + fpartition + "/" + myFileName + "." + fileType);
                    ao.setSize(0L);
                    ao.setStorePartition(fpartition);
                    ao.setCreateTime(new Date());
                    ao.setUserId(user.getId());
                    ao.setStatus(FileAO.STATUS_BEFORE_SAVE);
                    Result<FileAO> result = fileService.beforeSaveFile(ao);
                    if(result == null || !result.isSuccess()) {
                        model.addAttribute("ret", false);
                        model.addAttribute("msg", "上传失败");
                        return MVCViewName.UPLOAD_RES.view();
                    }

                    ao = result.getData();

                    os = new BufferedOutputStream(new FileOutputStream(localFile));
                    is = new BufferedInputStream(file.getInputStream());
                    ImageUtil imgUtil = new ImageUtil(is, os);
                    imgUtil.setWidth(1280);
                    imgUtil.compress(fileType);

                    os.flush();
                    os.close();
                    ao.setStatus(FileAO.STATUS_NOMARL);
                    ao.setSize(localFile.length());

                    userService.setHead(user.getId(), ao);
                    user.setLogo(ao.getFilePath());
                    UserUtil.resetUserInfo(user);
//                  SessionUtil.setAttribute(UserUtil.SESSION_USER, user, request);
//                  result = fileService.afterSaveFile(ao);
//                  if(result != null && !result.isSuccess()){
//                      model.addAttribute("ret", false);
//                      model.addAttribute("msg", "上传失败");
//                      return MVCViewName.UPLOAD_RES.view();
//                  }
                    model.addAttribute("ret", true);
                    model.addAttribute("msg", "上传成功");
                    model.addAttribute("file", result.getData());
                    model.addAttribute("width", imgUtil.getImgRealWeight());
                    model.addAttribute("height", imgUtil.getImgRealHeight());
                } catch (Exception e) {
                    e.printStackTrace();
                    model.addAttribute("ret", false);
                    model.addAttribute("msg", "上传失败");
                    return MVCViewName.UPLOAD_RES.view();
                } finally {
                    try {
                        if(is != null) is.close();
                    } catch (Exception e2) {}
                    try {
                        if(os != null) os.close();
                    } catch (Exception e2) {}
                }
                break;
            }
        }

        return MVCViewName.UPLOAD_RES.view();
    }

    @RequestMapping(value = "/head", method = RequestMethod.POST)
    public ModelAndView uploadpC(HttpServletRequest request, HttpServletResponse response, Model model) {
    	return upload(request, response, model);
    }

    @RequestMapping(value = "/bankpdset")
    public ModelAndView bankpdset(HttpServletRequest request, HttpServletResponse response, Model model) {
        return MVCViewName.PROFILE_BANKPDSET.view();
    }

    @RequestMapping(value = "/bankindex")
    public ModelAndView bankindex(HttpServletRequest request, HttpServletResponse response, Model model) {
        return MVCViewName.PROFILE_BANKINDEX.view();
    }

    @RequestMapping(value = "/banksuccess")
    public ModelAndView banksuccess(HttpServletRequest request, HttpServletResponse response, Model model) {
        return MVCViewName.PROFILE_BANKSUCCESS.view();
    }

//    @RequestMapping(value = "/bankbilling")
//    public ModelAndView bankbilling(HttpServletRequest request, HttpServletResponse response, Model model) {
//    	setCurrentUser(model, request);
//    	model.addAttribute("activetap", "bankbilling");
//        return MVCViewName.PROFILE_BANKBILLING.view();
//    }

    @RequestMapping(value = "/bankresetpd")
    public ModelAndView bankresetpd(HttpServletRequest request, HttpServletResponse response, Model model) {
    	setCurrentUser(model, request);
    	model.addAttribute("activetap", "bankindex");
    	model.addAttribute("activeheadertap", "bankresetpd");
        return MVCViewName.PROFILE_BANKRESETPD.view();
    }
}

package com.dunbian.jujiabao.mvc;

import com.dunbian.jujiabao.appobj.extend.FileAO;
import com.dunbian.jujiabao.appobj.extend.SMSValidationError;
import com.dunbian.jujiabao.appobj.extend.UserAO;
import com.dunbian.jujiabao.appobj.extend.UserAddressAO;
import com.dunbian.jujiabao.framework.obj.Result;
import com.dunbian.jujiabao.framework.session.SessionUtil;
import com.dunbian.jujiabao.framework.util.EncryptUtil;
import com.dunbian.jujiabao.framework.util.IPUtil;
import com.dunbian.jujiabao.framework.util.ImageUtil;
import com.dunbian.jujiabao.framework.util.VerifyUtil;
import com.dunbian.jujiabao.message.ISMSService;
import com.dunbian.jujiabao.service.address.IAddressService;
import com.dunbian.jujiabao.service.file.IFileService;
import com.dunbian.jujiabao.service.user.IUserService;
import com.dunbian.jujiabao.util.UserUtil;
import com.puddingnet.mvc.servlet.Constraint;
import com.puddingnet.mvc.servlet.CookieUtil;
import com.puddingnet.mvc.servlet.SessionServiceFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/wcuser")
public class WCUserController {

    @Resource
    private IUserService userService;

    @Resource
    private ISMSService smsService;
    @Resource
    private IAddressService addressService;

    @Resource
    private IFileService fileService;

    @ResponseBody
    @RequestMapping("/login")
    public Object login(String user, String pass, HttpServletRequest request, HttpServletResponse response) {
        Result<UserAO> ret = userService.check4Login(user, pass);
        if (ret == null || !ret.isSuccess()) {
            return ret;
        }

        CookieUtil.setCookie(Constraint.COOKIE_SESSION_ID, request.getSession().getId(), null, CookieUtil.MAXAGE_REMEMBER, response);
        SessionServiceFactory.getInstance().getService().login4Remember(ret.getData().getId(), request, response);
        SessionUtil.setAttribute(UserUtil.SESSION_USER, ret.getData(), request);
        return ret;
    }

    @ResponseBody
    @RequestMapping("/logoff")
    public Object logoff(HttpServletRequest request) {
        Map<String, Object> retMap = new HashMap<String, Object>();
        try{
            SessionUtil.removeAttribute(UserUtil.SESSION_USER, request);
            retMap.put("res",true);
            retMap.put("msg","注销成功");
        }catch(Exception error){
            retMap.put("res",false);
            retMap.put("msg","注销失败");
        }
        return  retMap;
    }

    @ResponseBody
    @RequestMapping(value = "/register", method = {RequestMethod.POST, RequestMethod.GET})
    public Object register(String verifyCode, UserAO user, UserAddressAO userAddress, HttpServletRequest request, HttpServletResponse response) {
        boolean verified = false;
        user.setMobile(user.getUserName());
        verified = VerifyUtil.isSmsVerified(verifyCode, user.getMobile(), request);
        if (!verified) {
            if (!checkSMSError(user.getUserName(), request)) {
                VerifyUtil.clearSmsVerify(request);
            }
            return new Result<>(false, "短信验证码输入错误");
        }

        user.setStatus(UserAO.STATUS_NORMAL);
        user.setCredit(0);
        user.setGradeCredit(0);
        user.setCreateTime(new Date());
        user.setIp(IPUtil.getRealIp(request));
        user.setLogo(UserAO.DEFAULT_USER_LOGO);
        Result<UserAO> ret = userService.register(user);
        Map<String, Object> retMap = new HashMap<String, Object>();
        if (ret == null) {
            retMap.put("res", true);
            retMap.put("msg", "注册失败");
        } else if (!ret.isSuccess()) {
            retMap.put("res", true);
            retMap.put("msg", "注册失败");
        } else {
            VerifyUtil.clearSmsVerify(request);

            if (userAddress.getRegionJson() != null && !"".equals(userAddress.getRegionJson().trim())) {
                userAddress.setUserId(ret.getData().getId());
                userAddress.setStatus(UserAddressAO.STATUS_DEFAULT);
                addressService.save(userAddress);

                if (userAddress.getTownId() != null) {
                    CookieUtil.setCookie("town", userAddress.getTownId(), "/", CookieUtil.MAXAGE_EVER, response);
                }
            }
            retMap.put("res", true);
            retMap.put("msg", "注册成功");
        }
        return retMap;
    }

    @ResponseBody
    @RequestMapping(value="/GetCurrentUser")
    public Object GetCurrentUser(HttpServletRequest request) {
        Map<String, Object> retMap = new HashMap<String, Object>();
        try{
            UserAO user = UserUtil.getCurrentLoginUser(request);
            if(user!=null){
                retMap.put("Status", true);
                retMap.put("UserName",user.getUserName());
            }else{
                retMap.put("Status", false);
            }
        }catch(Exception error){
            retMap.put("Status", false);
        }

        return retMap;
    }

    @ResponseBody
    @RequestMapping(value="/getuser")
    public Object getuser(HttpServletRequest request) {
        UserAO user = UserUtil.getCurrentLoginUser(request);
        return new Result<UserAO>(user);
    }

    @ResponseBody
    @RequestMapping(value="/resetpwd")
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
    @RequestMapping(value="/resetname")
    public Object resetname(String name, HttpServletRequest request) {
        UserAO user = UserUtil.getCurrentLoginUser(request);
        user.setNickname(name);
        Result<UserAO> ret = userService.update(user);
        if(ret.isSuccess() && ret.getData() != null) {
            UserUtil.resetUserInfo(user);
        }
        return ret;
    }

    private Pattern pattern = Pattern.compile("(jpg|jpeg|gif|png|bmp).*");
    @ResponseBody
    @RequestMapping(value = "/uploadimg", method = RequestMethod.POST)
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
            for(Map.Entry<String, MultipartFile> entry : multiRequest.getFileMap().entrySet()) {
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

    private boolean checkSMSError(String mobile, HttpServletRequest request) {
        SMSValidationError error = SessionUtil.getAttribute(SMSValidationError.SMS_VALIDATAION_ERROR, SMSValidationError.class, request);
        if (error == null) {
            error = new SMSValidationError();
            error.setKeepTime(System.currentTimeMillis());
            error.setLastTime(System.currentTimeMillis());
            error.setCount(1);
            error.setMobile(mobile);
            SessionUtil.setAttribute(SMSValidationError.SMS_VALIDATAION_ERROR, error, request);
        } else {
            if (System.currentTimeMillis() - error.getKeepTime() > SMSValidationError.CLEAN_TIME) {
                error.setKeepTime(System.currentTimeMillis());
                error.setLastTime(System.currentTimeMillis());
                error.setCount(1);
                error.setMobile(mobile);
                SessionUtil.setAttribute(SMSValidationError.SMS_VALIDATAION_ERROR, error, request);
            } else {
                if (error.getCount() + 1 >= SMSValidationError.MAX_COUNT) {
                    return false;
                } else {
                    error.setLastTime(System.currentTimeMillis());
                    error.setCount(error.getCount() + 1);
                    error.setMobile(mobile);
                    SessionUtil.setAttribute(SMSValidationError.SMS_VALIDATAION_ERROR, error, request);
                }
            }
        }

        return true;
    }

    @ResponseBody
    @RequestMapping(value = "/sendSMS", method = {RequestMethod.POST, RequestMethod.GET})
    public Object sendSMS(String mobile, HttpServletRequest request,HttpServletResponse response) {
        Map<String, Object> retMap = new HashMap<String, Object>();
        if (mobile == null || mobile.length() != 11 || !mobile.startsWith("1")) {
            retMap.put("res", false);
            retMap.put("msg", "请输入正确的手机号");
            return retMap;
        }
        if (userService.checkExists(mobile)) {
            retMap.put("res", false);
            retMap.put("msg",  "当前用户[" + mobile + "]在系统中已存在，请尝试使用其他手机号注册");
            return retMap;
        }
        String yzm = VerifyUtil.genSmsWords(mobile, request);
//        Cookie cookie = new Cookie("test","test");
//        cookie.setPath("/wx/");
//        response.addCookie(cookie);
        Result<Boolean> res=  smsService.send(mobile, "您的验证码是：" + yzm + "。请不要把验证码泄露给其他人。 ", IPUtil.getRealIp(request), request.getSession().getId(), null);
        if( res.isSuccess()){
            retMap.put("res", res.isSuccess());
            retMap.put("msg",  "注册成功");
            retMap.put("JSESSIONID",  request.getSession().getId());
        }else{
            retMap.put("res", false);
            retMap.put("msg", res.getMsg());
        }

        return retMap;
    }
}

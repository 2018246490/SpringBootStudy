package com.study.app.sys.apiController;

import com.study.app.sys.model.SysUser;
import com.study.app.sys.service.SysOperatingLogService;
import com.study.app.sys.service.SysUserService;
import com.study.tool.login.LoginCacheManage;
import com.study.tool.model.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("user")
@CrossOrigin
public class UserAction {

    @Autowired
    private SysUserService userService;
    @Autowired
    private SysOperatingLogService sysOperatingLogService;


    @PostMapping(value = "/login")
    @CrossOrigin
    public HttpResult login(SysUser user, String loginType, String code) throws Exception {
        if(StringUtils.isEmpty(loginType) || "0".equals(loginType)){//账号登录
            Map map = userService.accLogin(user);
            sysOperatingLogService.saveLog(10 , "系统管理", "账号："+user.getUserCode()+"登录系统", user.getUserCode());
            return HttpResult.ok(map);
        }else{//手机号登录
//            return HttpResult.ok(userService.mobileLogin(user.getTelephone(), code));
            return HttpResult.ok();
        }
    }




    @PostMapping(value = "/logout")
    @CrossOrigin
    public HttpResult logout(Long userId, String token) {
        if (LoginCacheManage.clearLogin(userId, token)) {
            SysUser user = userService.findById(userId);
            sysOperatingLogService.saveLog(11 , "系统管理", "账户："+user.getUserCode()+"退出系统", user.getUserCode());
            return HttpResult.ok();
        } else {
            return HttpResult.error();
        }
    }


    /**
     * @Auther  吕伟林
     * @Des 忘记密码流程  修改密码
     * @Date 2021/7/7 10:08
     */
    @PostMapping(value = "/updatePassword")
    @CrossOrigin
    public HttpResult updatePassword(String updateUuid, String password) throws Exception {
        userService.updatePasswordByUpdateUuid(updateUuid, password);
        return HttpResult.ok();
    }




    //    @RoleCompetence(value = "1")
    @PostMapping(value = "/demo")
    @CrossOrigin
    public HttpResult demo(String a) throws Exception {
//        userService.sendSms("15986819639");
        return HttpResult.ok(a);
    }

    @PostMapping(value = "/demo1")
    @CrossOrigin
    public HttpResult demo1(@RequestBody String a) throws Exception {
//        userService.sendSms("15986819639");
        return HttpResult.ok(a);
    }


}

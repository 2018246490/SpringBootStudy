package com.study.app.sys.service;

import com.study.app.sys.model.SysUser;
import com.study.system.exception.MsgException;
import com.study.tool.model.PageModel;

import java.util.Map;

public interface SysUserService {


    Map accLogin(SysUser user) throws Exception;

    PageModel findByPage(Map map, PageModel page);

    void save(SysUser user) throws MsgException;

    /**
     * @Auther  吕伟林
     * @Des 登录后修改密码
     * @Date 2021/7/7 10:08
     */
    void updatePassword(Long userId, String oldPassword, String newPassword) throws MsgException;

    SysUser findById(Long userId);

    /**
     * @Auther  吕伟林
     * @Des 忘记密码流程 修改密码
     * @Date 2021/7/7 10:09
     */
    void updatePasswordByUpdateUuid(String updateUuid, String password) throws MsgException;
}

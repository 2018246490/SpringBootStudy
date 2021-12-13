package com.study.tool.login;

import com.study.app.sys.model.SysRole;
import com.study.app.sys.model.SysRoleButton;
import com.study.app.sys.model.SysUser;

import java.util.List;

/**
 * @Auther  吕伟林
 * @Des 登录信息类
 * @Date 2021/6/22 15:16
 */
public class LoginInfo {
    private SysUser user;
    private SysRole role;
    private List<SysRoleButton> roleButtonList; //角色功能权限
    private Long time;
    private String token;

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    public SysRole getRole() {
        return role;
    }

    public void setRole(SysRole role) {
        this.role = role;
    }

    public List<SysRoleButton> getRoleButtonList() {
        return roleButtonList;
    }

    public void setRoleButtonList(List<SysRoleButton> roleButtonList) {
        this.roleButtonList = roleButtonList;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}

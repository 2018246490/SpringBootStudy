package com.study.tool.login;

import java.util.Date;

/**
 * 错误登录信息
 * 吕伟林
 * 2021/7/1
 */
public class ErrorLoingInfo {
    private String code;//账号
    private Integer errorNum;//错误次数
    private Date lastLogin;//最后一次错误登录时间

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getErrorNum() {
        return errorNum;
    }

    public void setErrorNum(Integer errorNum) {
        this.errorNum = errorNum;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }
}

package com.study.tool.login.serverCache;

import com.study.tool.login.LoginCache;
import com.study.tool.login.LoginInfo;

import java.util.Date;
import java.util.HashMap;

/**
 * @Auther  吕伟林
 * @Des 服务器缓存登录信息 处理类
 * @Date 2021/6/22 15:16
 */
public class HashMapLoginCache implements LoginCache<LoginInfo> {

    protected Long outTime = 10*60*1000l;
    private final HashMap<String, LoginInfo> loginUserMap = new HashMap();
    private static LoginCache manager = null;

    /**
     * @Auther 吕伟林
     * @Des
     * @Date 2021/6/22 15:19
     */
    private HashMapLoginCache() {
    }

    @Override
    public LoginInfo getLoginUser(Long id, String token) {
        LoginInfo user = loginUserMap.get(id.toString());
        if (user != null) {
            if (checkExpired(user) && user.getToken().equals(token)) {
                return user;
            } else {
                loginUserMap.remove(id.toString());
            }
        }
        return null;
    }


    @Override
    public void setLoginUser(LoginInfo loginUserInfo) {
        loginUserMap.put(loginUserInfo.getUser().getId().toString(), loginUserInfo);
    }

    @Override
    public boolean clearLogin(Long id, String token) {
        LoginInfo user = loginUserMap.get(id.toString());
        if (user != null) {
            if (user.getToken().equals(token)) {//checkExpired(user) &&
                loginUserMap.remove(id.toString());
                return true;
            }
        }
        return false;
    }

    public static LoginCache getInstance() {
        if (manager == null) {
            manager = new HashMapLoginCache();
        }
        return manager;
    }

//    public void setOutTime(Long outTime) {
//        this.outTime = outTime;
//    }

    private boolean checkExpired(LoginInfo user) {
        if (outTime.compareTo(new Long(0)) == 0) {//
            return true;
        }
        if (new Date().getTime() - user.getTime() > outTime) {
            return false;
        }
        return true;
    }
}

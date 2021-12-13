package com.study.tool.login;

/**
 * @Auther  吕伟林
 * @Des 登录信息处理接口
 * @Date 2021/6/22 15:16
 */
public interface LoginCache<T> {
    /**
     * @Auther  吕伟林
     * @Des 获取登录用户信息
     * @Date 2021/6/22 15:18
     */
    T getLoginUser(Long id,String token);

    /**
     * @Auther  吕伟林
     * @Des 保存登录用户信息
     * @Date 2021/6/22 15:18
     */
    void setLoginUser(T t);

    /**
     * @Auther  吕伟林
     * @Des 清除登录用户信息
     * @Date 2021/6/22 15:38
     */
    boolean clearLogin(Long id,String token);
}

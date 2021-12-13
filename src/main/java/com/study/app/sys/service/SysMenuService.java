package com.study.app.sys.service;

import java.util.List;

public interface SysMenuService {

    /**
     * @Auther  吕伟林
     * @Des 查询树形菜单
     * @Date 2021/6/29 14:02
     */
    List findTree();

    /**
     * @Auther  吕伟林
     * @Des 查询登录用户权限菜单
     * @Date 2021/6/29 14:14
     */
    List findTreeByUser();

}

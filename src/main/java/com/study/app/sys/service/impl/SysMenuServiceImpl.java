package com.study.app.sys.service.impl;

import com.study.app.sys.dao.SysMenuDao;
import com.study.app.sys.model.SysMenu;
import com.study.app.sys.service.SysMenuService;
import com.study.tool.login.LoginCacheManage;
import com.study.tool.login.LoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Transactional(rollbackFor = Exception.class)
@Service("sysMenuService")
public class SysMenuServiceImpl implements SysMenuService {
    @Autowired
    private SysMenuDao sysMenuDao;

    /**
     * @Auther 吕伟林
     * @Des 查询树形菜单
     * @Date 2021/6/29 14:02
     */
    @Override
    public List findTree() {
        List<SysMenu> list = sysMenuDao.findAllByBdeleteOrderByOrderNum(false);
        return children(new Long(0), list);
    }

    /**
     * @Auther 吕伟林
     * @Des 查询登录用户菜单
     * @Date 2021/6/29 14:15
     */
    @Override
    public List findTreeByUser() {
        LoginInfo info = LoginCacheManage.getLoginUser();
        if (info != null) {
            List l = sysMenuDao.findUserMenu(info.getRole().getId());
            return children(new Long(0), l);
        }
        return new ArrayList();
    }

    /**
     * @Auther 吕伟林
     * @Des 递归树形结构
     * @Date 2021/6/29 14:15
     */
    private List children(Long parent, List<SysMenu> list) {
        List result = new ArrayList();
        for (SysMenu m : list) {
            if (parent.compareTo(m.getParentId()) == 0) {
                Map<String, Object> map = new HashMap<>();
                map.put("id", m.getId());
                map.put("name", m.getName());
                map.put("code", m.getCode());
                map.put("path", m.getRoute());
                map.put("children", children(m.getId(), list));
                result.add(map);
            }
        }
        return result;
    }
}

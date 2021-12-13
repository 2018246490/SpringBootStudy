package com.study.app.sys.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.study.app.sys.dao.SysRoleButtonDao;
import com.study.app.sys.dao.SysRoleDao;
import com.study.app.sys.model.SysRole;
import com.study.app.sys.model.SysRoleButton;
import com.study.app.sys.service.SysRoleService;
import com.study.system.exception.MsgException;
import com.study.tool.login.LoginCacheManage;
import com.study.tool.login.LoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Transactional(rollbackFor = Exception.class)
@Service("sysRoleService")
public class SysRoleServiceImpl implements SysRoleService {
    @Autowired
    private SysRoleDao sysRoleDao;
    @Autowired
    private SysRoleButtonDao sysRoleButtonDao;

    @Override
    public List findAll() {
        return sysRoleDao.findAll();
    }

    @Override
    public List findRoleButtons(Long roleId) {
        List<Map<String, Object>> list = sysRoleButtonDao.queryRoleButtonByRoleId(roleId);
        //数据处理
        List<Map<String, Object>> result = new ArrayList<>();
        for (Map<String, Object> m : list) {
            Map<String, Object> map = new HashMap<>(m);
            if (map.get("roleMenuId") == null) {
                map.put("menuCheck", 0);
            } else {
                map.put("menuCheck", 1);
            }
//            map.remove("roleMenuId");

            //新增权限
            if (map.get("createFlag") == null || "0".equals(map.get("createFlag"))) {
                map.put("createCheck", 0);
            } else {
                map.put("createCheck", 1);
            }
            map.remove("createFlag");

            //修改权限
            if (map.get("updateFlag") == null || "0".equals(map.get("updateFlag"))) {
                map.put("updateCheck", 0);
            } else {
                map.put("updateCheck", 1);
            }
            map.remove("updateFlag");

            //删除权限
            if (map.get("delFlag") == null || "0".equals(map.get("delFlag"))) {
                map.put("delCheck", 0);
            } else {
                map.put("delCheck", 1);
            }
            map.remove("delFlag");

            //查询权限
            if (map.get("queryFlag") == null || "0".equals(map.get("queryFlag"))) {
                map.put("queryCheck", 0);
            } else {
                map.put("queryCheck", 1);
            }
            map.remove("queryFlag");

            //上传权限
            if (map.get("uploadFlag") == null || "0".equals(map.get("uploadFlag"))) {
                map.put("uploadCheck", 0);
            } else {
                map.put("uploadCheck", 1);
            }
            map.remove("uploadFlag");

            //下载权限
            if (map.get("downloadFlag") == null || "0".equals(map.get("downloadFlag"))) {
                map.put("downloadCheck", 0);
            } else {
                map.put("downloadCheck", 1);
            }
            map.remove("downloadFlag");

            result.add(map);
        }

        //转化树形
        return children(new Integer(0), result);
    }

    /**
     * @Auther 吕伟林
     * @Des 递归树形结构
     * @Date 2021/6/29 14:15
     */
    private List children(Integer id, List<Map<String, Object>> list) {
        List result = new ArrayList();
        for (Map<String, Object> m : list) {
            Integer parentId = (Integer) m.get("parentId");
            if (id.compareTo(parentId) == 0) {
                Integer mid = (Integer) m.get("id");
                m.put("children", children(mid, list));
                result.add(m);
            }
        }
        return result;
    }

    /**
     * @Auther 吕伟林
     * @Des 角色权限更新
     * @Date 2021/7/2 9:43
     */
    @Override
    public void saveOrUpdate(Long roleId, String roleButtonsJson) throws MsgException {
        SysRole sr = sysRoleDao.getOne(roleId);
        if (1 == sr.getLevel()) {
            throw new MsgException("管理员权限不能修改！");
        }

        //还原json
        List<SysRoleButton> list = new Gson().fromJson(roleButtonsJson, new TypeToken<List<SysRoleButton>>() {
        }.getType());

        LoginInfo user = LoginCacheManage.getLoginUser();
        List a = new ArrayList();
        for (SysRoleButton srb : list) {
            if ("0".equals(srb.getMenuCheck())) {
                if (srb.getId() != null) {
                    sysRoleButtonDao.deleteById(srb.getId());
                }
            } else {
                srb.setBdelete(false);
                srb.setRoleId(roleId);
                if (srb.getId() != null) {
                    srb.setUpdateTime(new Date());
                    srb.setUpdateUser(user.getUser().getId());
                } else {
                    srb.setCreateTime(new Date());
                    srb.setCreateUser(user.getUser().getId());
                }
                a.add(srb);
            }
        }

        //批量保存、更新
        sysRoleButtonDao.saveAll(a);
    }
}

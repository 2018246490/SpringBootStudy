package com.study.app.sys.service.impl;

import com.study.app.sys.dao.SysRoleButtonDao;
import com.study.app.sys.dao.SysRoleDao;
import com.study.app.sys.dao.SysUserDao;
import com.study.app.sys.model.SysRole;
import com.study.app.sys.model.SysUser;
import com.study.app.sys.service.SysOperatingLogService;
import com.study.app.sys.service.SysUserService;
import com.study.system.exception.MsgException;
import com.study.tool.login.LoginCacheManage;
import com.study.tool.login.LoginInfo;
import com.study.tool.model.PageModel;
import com.study.util.EsUtil;
import com.study.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * @Auther 吕伟林
 * @Des 登录处理服务
 * @Date 2021/6/29 16:03
 */
@Transactional(rollbackFor = Exception.class)
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {
    //忘记密码流程  保存检查通过后的随机ID
    private static HashMap<String, Long> checkUuidMap = new HashMap<>();

    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private SysRoleDao sysRoleDao;
    @Autowired
    private SysRoleButtonDao sysRoleButtonDao;
    @Autowired
    private SysOperatingLogService sysOperatingLogService;

    /**
     * @Auther 吕伟林
     * @Des 账号登录
     * @Date 2021/6/29 16:01
     */
    @Override
    public Map accLogin(SysUser user) throws Exception {
        if (!LoginCacheManage.checkAllowLogin(user.getUserCode())) {
            throw new MsgException("账号：" + user.getUserCode() + "多次错误登录导致被锁定.请过段时间再尝试登录.");
        }
        if (user == null || StringUtils.isEmpty(user.getUserCode()) || StringUtils.isEmpty(user.getPassword())) {
            throw new MsgException("用户名密码不能为空");
        }
        SysUser u = sysUserDao.findSysUserByUserCodeAndPassword(user.getUserCode(), StringUtil.saltMd5(user.getPassword()));
        if (u != null) {
            LoginInfo info = new LoginInfo();
            if (u.getRoleId() != null) {
                SysRole role = sysRoleDao.getOne(u.getRoleId());
                info.setRole(role);

                //查询角色功能权限
                List roleButton = sysRoleButtonDao.findAllByRoleIdAndBdelete(u.getRoleId(), false);
                info.setRoleButtonList(roleButton);
            }
            info.setUser(u);
            info.setTime(new Date().getTime());
            String token = UUID.randomUUID().toString();
            info.setToken(token);
            LoginCacheManage.setLoginCache(info);
            return LoginCacheManage.resultLoginCacheInfo(info);
        } else {
            LoginCacheManage.addErrorLogin(user.getUserCode());
            throw new MsgException("您的用户名或者密码不对");
        }
    }

    @Override
    public SysUser findById(Long userId) {
        return sysUserDao.getOne(userId);
    }

    @Override
    public PageModel<SysUser> findByPage(Map map, PageModel page) {
        //页码：前端从1开始，jpa从0开始，做个转换
        Pageable pageable = PageRequest.of(page.getPageNum() - 1, page.getPageSize(), Sort.by(Sort.Direction.DESC, "id"));
        String userName = null;
        if (map.get("userName") != null) {
            userName = (String) map.get("userName");
        }
        Page<Map> hpPage = sysUserDao.queryPage(userName, pageable);
        page.setData(hpPage.getContent());
        page.setTatalPageNum(hpPage.getTotalPages());
        page.setTotalNum(hpPage.getTotalElements());
        return page;
    }

    @Override
    public void save(SysUser user) throws MsgException {
        if (!StringUtil.isEmpty(user.getPassword())) {
            user.setPassword(StringUtil.saltMd5(user.getPassword()));
        }

        //验证手机号码是否重复
        if (!StringUtils.isEmpty(user.getTelephone())) {
            List<SysUser> list = sysUserDao.findSysUserByTelephone(user.getTelephone());
            if (list != null && list.size() > 0) {
                if (user.getId() == null) {
                    throw new MsgException(user.getTelephone() + " 已被使用");
                } else {
                    for (SysUser su : list) {
                        if (su.getId().compareTo(user.getId()) != 0) {
                            throw new MsgException(user.getTelephone() + " 已被使用");
                        }
                    }
                }
            }
        }

        SysUser la = null;
        if (user.getId() != null) {
            la = sysUserDao.findSysUserById(user.getId());
            EsUtil.mergeObject(user, la);
            sysOperatingLogService.saveLog(0, "系统管理", "账户：" + la.getUserCode() + "修改信息");
        } else {
            la = user;
            la.setBdelete(false);
            sysOperatingLogService.saveLog(1, "系统管理", "新增账户：" + la.getUserCode());
        }
        sysUserDao.save(la);

    }

    @Override
    public void updatePassword(Long userId, String oldPassword, String newPassword) throws MsgException {
        if (StringUtils.isEmpty(userId)) {
            throw new MsgException("账号信息为空");
        }
        if (StringUtils.isEmpty(oldPassword)) {
            throw new MsgException("旧密码不能为空");
        }
        if (StringUtils.isEmpty(newPassword)) {
            throw new MsgException("新密码不能为空");
        }
        if (newPassword.length() < 6) {
            throw new MsgException("新密码长度不能小于六位数");
        }

        SysUser u = sysUserDao.getOne(userId);//sysUserDao.findSysUserByUserCodeAndPassword(userCode, StringUtil.saltMd5(oldPassword));
        if (!StringUtil.saltMd5(oldPassword).equals(u.getPassword())) {
            throw new MsgException("账号密码不匹配！");
        }

        u.setPassword(StringUtil.saltMd5(newPassword));
        sysUserDao.save(u);
        sysOperatingLogService.saveLog(1, "系统管理", "账号:" + u.getUserCode() + "修改密码");
    }

    @Override
    public void updatePasswordByUpdateUuid(String updateUuid, String password) throws MsgException {
        if(checkUuidMap.containsKey(updateUuid)){
            Long userId = checkUuidMap.get(updateUuid);
            SysUser sysUser = sysUserDao.getOne(userId);
            sysUser.setPassword(StringUtil.saltMd5(password));
            sysUserDao.save(sysUser);
            sysOperatingLogService.saveLog(1, "系统管理", "账号:" + sysUser.getUserCode() + "修改密码", sysUser.getUserCode());
        }else{
            throw new MsgException("未获取到相关的验证信息！");
        }
    }

}

package com.study.app.sys.service;


import com.study.system.exception.MsgException;

import java.util.List;

public interface SysRoleService {

    List findAll();

    List findRoleButtons(Long roleId);

    void saveOrUpdate(Long roleId, String roleButtonsJson) throws MsgException;
}

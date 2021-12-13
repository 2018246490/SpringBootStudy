package com.study.app.sys.service;

import com.study.tool.model.PageModel;

import java.util.List;
import java.util.Map;

public interface SysOperatingLogService {

    PageModel findByPage(Map<String, Object> map, PageModel page);

    List findAllType();

    void saveLog(Integer type, String module, String rmk);

    void saveLog(Integer type, String module, String rmk, String userCode);
}

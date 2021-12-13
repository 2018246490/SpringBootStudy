package com.study.app.sys.service;

import com.study.system.exception.MsgException;

import java.util.List;

public interface SysParamService {

    List findAll();

    void updateValue(String paramJson) throws MsgException;

}

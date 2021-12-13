package com.study.app.sys.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.study.app.sys.dao.SysParamDao;
import com.study.app.sys.model.SysParam;
import com.study.app.sys.service.SysParamService;
import com.study.system.exception.MsgException;
import com.study.tool.ParamManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(rollbackFor = Exception.class)
@Service("sysParamService")
public class SysParamServiceImpl implements SysParamService {
    @Autowired
    private SysParamDao sysParamDao;

    @Override
    public List findAll() {
        return sysParamDao.findAllByBdelete(false);
    }

    @Override
    public void updateValue(String paramJson) throws MsgException {
        if (paramJson != null) {
            List<SysParam> list = new Gson().fromJson(paramJson, new TypeToken<List<SysParam>>() {
            }.getType());

            //数据入库
            for (SysParam sp : list) {
                sysParamDao.updateValueByCode(sp.getCode(), sp.getValue());
            }

            //更新内存中的信息
            for (SysParam sp : list) {
                ParamManager.SysParamMap.put(sp.getCode(), sp.getValue());
            }
        }

    }
}

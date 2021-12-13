package com.study.system.scheduling;

import com.study.app.sys.model.SysParam;
import com.study.app.sys.service.SysParamService;
import com.study.tool.ParamManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 定时器任务  暂时停用, 目前在系统参数更新后 进行了内存数据更新
 * 吕伟林
 * 2021/7/1
 */
//@Component
@Slf4j
public class ScheduingTasks {

    @Autowired
    private SysParamService sysParamService;

    /**
     * @Auther  吕伟林
     * @Des 刷新系统参数设置
     * @Date 2021/7/1 14:21
     */
//    @Scheduled(cron="0 0/5 * * * ?")
    public void refreshSysParam() {
        List<SysParam> list = sysParamService.findAll();
        for(SysParam sp : list){
            ParamManager.SysParamMap.put(sp.getCode(), sp.getValue());
        }
        log.info("重新加载系统参数完成");
    }
}

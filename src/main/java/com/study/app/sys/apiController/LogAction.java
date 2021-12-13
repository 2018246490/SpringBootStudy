package com.study.app.sys.apiController;

import com.study.app.sys.service.SysOperatingLogService;
import com.study.system.annotation.ButtonAnnotation;
import com.study.system.annotation.ButtonAnnotationType;
import com.study.tool.model.HttpResult;
import com.study.tool.model.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @Auther 吕伟林
 * @Des 系统日志
 * @Date 2021/6/29 14:11
 */
@RestController
@RequestMapping("log")
@CrossOrigin
public class LogAction {

    @Autowired
    private SysOperatingLogService sysOperatingLogService;

    /**
     * @Auther 吕伟林
     * @Des 日志分页列表
     * @Date 2021/6/29 14:12
     */
    //    @RoleAnnotation(value = "1")
    @ButtonAnnotation(menuCode = "log", buttonType = ButtonAnnotationType.QUERY)
    @PostMapping(value = "/findByPage")
    @CrossOrigin
    public HttpResult findByPage(PageModel page,
                                 @RequestParam Map<String, Object> map) {

        return HttpResult.ok(sysOperatingLogService.findByPage(map, page));
    }

    /**
     * @Auther 吕伟林
     * @Des 查询全部日志类型
     * @Date 2021/6/29 14:12
     */
    @ButtonAnnotation(menuCode = "log", buttonType = ButtonAnnotationType.QUERY)
    @PostMapping(value = "/findAllType")
    @CrossOrigin
    public HttpResult findAllType() {
        return HttpResult.ok(sysOperatingLogService.findAllType());
    }
}

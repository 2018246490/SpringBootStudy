package com.study.app.sys.apiController;

import com.study.app.sys.service.SysParamService;
import com.study.system.annotation.ButtonAnnotation;
import com.study.system.annotation.ButtonAnnotationType;
import com.study.tool.model.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther  吕伟林
 * @Des 系统角色
 * @Date 2021/6/29 14:13
 */
@RestController
@RequestMapping("param")
@CrossOrigin
public class ParamAction {

    @Autowired
    private SysParamService paramService;

//    @RoleAnnotation(value = "1")
    @ButtonAnnotation(menuCode = "security", buttonType = ButtonAnnotationType.QUERY)
    @PostMapping(value = "/findAll")
    @CrossOrigin
    public HttpResult findByPage() {
        return HttpResult.ok(paramService.findAll());
    }

    @ButtonAnnotation(menuCode = "security", buttonType = {ButtonAnnotationType.UPDATE})
    @PostMapping(value = "/updateValue")
    @CrossOrigin
    public HttpResult updateValue(String paramJson) throws Exception {
        paramService.updateValue(paramJson);
        return HttpResult.ok();
    }

}

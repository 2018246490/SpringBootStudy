package com.study.app.sys.apiController;

import com.study.app.sys.service.SysRoleService;
import com.study.system.annotation.ButtonAnnotation;
import com.study.system.annotation.ButtonAnnotationType;
import com.study.system.exception.MsgException;
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
@RequestMapping("role")
@CrossOrigin
public class RoleAction {

    @Autowired
    private SysRoleService sysRoleService;

//    @RoleAnnotation(value = "1")
    @ButtonAnnotation(menuCode = "permission", buttonType = ButtonAnnotationType.QUERY)
    @PostMapping(value = "/findAll")
    @CrossOrigin
    public HttpResult findByPage() {
        return HttpResult.ok(sysRoleService.findAll());
    }

    /**
     * @Auther  吕伟林
     * @Des 查询角色的 菜单、功能权限
     * @Date 2021/7/1 17:11
     */
    @ButtonAnnotation(menuCode = "permission", buttonType = ButtonAnnotationType.QUERY)
    @PostMapping(value = "/findRoleButtons")
    @CrossOrigin
    public HttpResult findRoleButtons(Long roleId) {
        return HttpResult.ok(sysRoleService.findRoleButtons(roleId));
    }

    /**
     * @Auther  吕伟林
     * @Des 角色权限编辑
     * @Date 2021/7/2 9:35
     */
    @ButtonAnnotation(menuCode = "permission", buttonType = {ButtonAnnotationType.CREATE, ButtonAnnotationType.UPDATE})
    @PostMapping(value = "/saveOrUpdate")
    @CrossOrigin
    public HttpResult saveOrUpdate(Long roleId, String roleButtonsJson) throws MsgException {
        sysRoleService.saveOrUpdate(roleId, roleButtonsJson);
        return HttpResult.ok();
    }

}

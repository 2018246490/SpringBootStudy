package com.study.app.sys.apiController;

import com.study.app.sys.service.SysMenuService;
import com.study.tool.model.HttpResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther  吕伟林
 * @Des 系统菜单
 * @Date 2021/6/29 14:13
 */
@RestController
@RequestMapping("menu")
@CrossOrigin
public class MenuAction {

    @Autowired
    private SysMenuService sysMenuService;

    /**
     * @Auther  吕伟林
     * @Des 查询全部菜单
     * @Date 2021/6/29 14:06
     */
//    @RoleAnnotation(value = "1") //管理员权限
//    @ButtonAnnotation(menuCode = "menu", buttonType = ButtonAnnotationType.QUERY)
    @PostMapping(value = "/findTree")
    @CrossOrigin
    public HttpResult findTree() {
        return HttpResult.ok(sysMenuService.findTree());
    }


    /**
     * @Auther  吕伟林
     * @Des 获取登录用户权限菜单
     * @Date 2021/6/29 14:06
     */
    @PostMapping(value = "/findTreeByUser")
    @CrossOrigin
    public HttpResult findTreeByUser() {
        return HttpResult.ok(sysMenuService.findTreeByUser());
    }
}

package com.study.app.sys.apiController;

import com.study.app.sys.model.SysUser;
import com.study.app.sys.service.SysUserService;
import com.study.system.annotation.ButtonAnnotation;
import com.study.system.annotation.ButtonAnnotationType;
import com.study.tool.model.HttpResult;
import com.study.tool.model.PageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("userManager")
@CrossOrigin
public class UserMangerAction {

    @Autowired
    private SysUserService userService;


//    @RoleCompetence(value = "1")
    @ButtonAnnotation(menuCode = "user", buttonType = ButtonAnnotationType.QUERY)
    @PostMapping(value = "/findByPage")
    @CrossOrigin
    public HttpResult findByPage(PageModel page,
                                 @RequestParam Map<String, Object> map) {
        return HttpResult.ok(userService.findByPage(map, page));
    }

//    @RoleAnnotation(value = "1")
    @ButtonAnnotation(menuCode = "user", buttonType = {ButtonAnnotationType.CREATE,ButtonAnnotationType.UPDATE})
    @PostMapping(value = "/saveOrUpdate")
    @CrossOrigin
    public HttpResult saveOrUpdate(SysUser user) throws Exception {
        userService.save(user);
        return HttpResult.ok();
    }

    @PostMapping(value = "/updatePassword")
    @CrossOrigin
    public HttpResult updatePassword(Long userId, String oldPassword, String newPassword) throws Exception {
        userService.updatePassword(userId, oldPassword, newPassword);
        return HttpResult.ok();
    }

    /**
     * @Auther  吕伟林
     * @Des
     * @Date 2021/6/30 18:21
     */
    @PostMapping(value = "/isLogin")
    @CrossOrigin
    public HttpResult isLogin() {

        return HttpResult.ok();
    }

}

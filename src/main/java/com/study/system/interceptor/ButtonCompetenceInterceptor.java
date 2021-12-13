package com.study.system.interceptor;

import com.google.gson.GsonBuilder;
import com.study.app.sys.model.SysRoleButton;
import com.study.system.annotation.ButtonAnnotation;
import com.study.system.annotation.ButtonAnnotationType;
import com.study.tool.login.LoginCacheManage;
import com.study.tool.login.LoginInfo;
import com.study.tool.model.HttpResult;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.util.List;

/**
 * @Auther 吕伟林
 * @Des 角色功能权限拦截器
 * @Date 2021/6/25 11:58
 */
public class ButtonCompetenceInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 验证权限
        if (this.hasPermission(handler)) {
            return true;
        }
        //  null == request.getHeader("x-requested-with") TODO 暂时用这个来判断是否为ajax请求

        HttpResult hr = HttpResult.roleError();
        String result = new GsonBuilder().create().toJson(hr);
        OutputStream outputStream = response.getOutputStream();//获取OutputStream输出流
        response.setHeader("content-type", "application/json;charset=UTF-8;");
        byte[] dataByteArr = result.getBytes("UTF-8");//将字符转换成字节数组，指定以UTF-8编码进行转换
        outputStream.write(dataByteArr);//使用OutputStream流向客户端输出字节数组
        return false;
    }

    /**
     * 是否有权限
     *
     * @param handler
     * @return
     */
    private boolean hasPermission(Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // 获取方法上的注解
            ButtonAnnotation buttonAnnotation = handlerMethod.getMethod().getAnnotation(ButtonAnnotation.class);
            // 如果标记了注解，则判断权限
            if (buttonAnnotation != null) {
                //获取参数
                String[] menus = buttonAnnotation.menuCode();
                String[] buttons = buttonAnnotation.buttonType();
                if (menus != null && buttons != null && menus.length > 0 && buttons.length > 0) {
                    // 并判断是否有权限
                    List<SysRoleButton> srbList = null;
                    LoginInfo info = LoginCacheManage.getLoginUser();
                    if (info != null && info.getRoleButtonList() != null && info.getRoleButtonList().size() > 0) {
                        srbList = info.getRoleButtonList();
                    }

                    if (srbList == null) {
                        return false;
                    }

                    //检测权限
                    for (SysRoleButton srb : srbList) {
                        //比对菜单权限
                        for (String code : menus) {
                            if (code.equalsIgnoreCase(srb.getMenuCode())) {
                                //比对功能权限
                                for (String btn : buttons) {
                                    if(ButtonAnnotationType.CREATE.equalsIgnoreCase(btn) && "1".equalsIgnoreCase(srb.getCreateFlag())){
                                        return true;
                                    }
                                    if(ButtonAnnotationType.UPDATE.equalsIgnoreCase(btn) && "1".equalsIgnoreCase(srb.getUpdateFlag())){
                                        return true;
                                    }
                                    if(ButtonAnnotationType.AUTH.equalsIgnoreCase(btn) && "1".equalsIgnoreCase(srb.getAuthFlag())){
                                        return true;
                                    }
                                    if(ButtonAnnotationType.DEL.equalsIgnoreCase(btn) && "1".equalsIgnoreCase(srb.getDelFlag())){
                                        return true;
                                    }
                                    if(ButtonAnnotationType.QUERY.equalsIgnoreCase(btn) && "1".equalsIgnoreCase(srb.getQueryFlag())){
                                        return true;
                                    }
                                    // 自定义权限暂时未处理
                                    if(ButtonAnnotationType.CUSTOM.equalsIgnoreCase(btn) && "1".equalsIgnoreCase(srb.getCustom())){
                                        return true;
                                    }
                                    if(ButtonAnnotationType.PRINT.equalsIgnoreCase(btn) && "1".equalsIgnoreCase(srb.getPrintFlag())){
                                        return true;
                                    }
                                    if(ButtonAnnotationType.UPLOAD.equalsIgnoreCase(btn) && "1".equalsIgnoreCase(srb.getUploadFlag())){
                                        return true;
                                    }
                                    if(ButtonAnnotationType.DOWNLOAD.equalsIgnoreCase(btn) && "1".equalsIgnoreCase(srb.getDownloadFlag())){
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // TODO
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // TODO
    }

}

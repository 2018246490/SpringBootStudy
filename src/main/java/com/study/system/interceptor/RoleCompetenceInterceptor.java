package com.study.system.interceptor;

import com.google.gson.GsonBuilder;
import com.study.system.annotation.RoleAnnotation;
import com.study.system.annotation.RoleAnnotationType;
import com.study.tool.login.LoginCacheManage;
import com.study.tool.login.LoginInfo;
import com.study.tool.model.HttpResult;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * @Auther  吕伟林
 * @Des 角色权限拦截器
 * @Date 2021/6/25 11:58
 */
@Deprecated
public class RoleCompetenceInterceptor implements HandlerInterceptor {

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
            RoleAnnotation roleCompetence = handlerMethod.getMethod().getAnnotation(RoleAnnotation.class);
            // 如果方法上的注解为空 则获取类的注解
            if (roleCompetence == null) {
                roleCompetence = handlerMethod.getMethod().getDeclaringClass().getAnnotation(RoleAnnotation.class);
            }
            // 如果标记了注解，则判断权限
            if (roleCompetence != null && !StringUtils.isEmpty(roleCompetence.value())) {
                // 并判断是否有权限
                Integer level = null;
                LoginInfo info = LoginCacheManage.getLoginUser();
                if(info != null && info.getRole() != null){
                    level = info.getRole().getLevel();
                }

                if(level == null){
                    return false;
                }

                boolean result = false;
                String[] levelList = roleCompetence.value().split(",");
                String compare = roleCompetence.compare();
                String relation = roleCompetence.relation();
                for(int i = 0; i< levelList.length; i++){
                    String _level = levelList[i];
                    boolean pd = _level.equals(level.toString());
                    if(RoleAnnotationType.NOT_EQUALS.equals(compare)){
                        pd = !pd;
                    }

                    if(RoleAnnotationType.AND.equals(relation)){
                        if(!pd){
                            return false;
                        }
                    }else{
                        if(pd){
                            return true;
                        }
                    }
                    result = pd;
                }
                return result;
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

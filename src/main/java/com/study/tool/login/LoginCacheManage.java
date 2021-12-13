package com.study.tool.login;

import com.study.app.sys.model.SysRoleButton;
import com.study.tool.ParamManager;
import com.study.tool.login.serverCache.HashMapLoginCache;
import com.study.util.CookiesUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @Auther  吕伟林 TODO 待优化
 * @Des 登录信息缓存管理类
 * @Date 2021/6/22 15:16
 */
public class LoginCacheManage  {

    //使用服务器缓存登录信息
    private static LoginCache<LoginInfo> loginCache = HashMapLoginCache.getInstance();

    //登录频次与锁定
    private static Map<String, ErrorLoingInfo> errorLoginMap = new HashMap<>();

    /**
     * @Auther  吕伟林
     * @Des 获取登录用户信息
     * @Date 2021/7/1 15:21
     */
    public static LoginInfo getLoginUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        Map<String, String> cooklist = CookiesUtil.readCookieMap(request);
        String token = request.getParameter("token");
        String userid = request.getParameter("userId");
        if (token == null && userid == null) {
            token = cooklist.get("token");;
            userid = cooklist.get("userId");
        }
        if (!StringUtils.isEmpty(token) && !StringUtils.isEmpty(userid)) {
            return loginCache.getLoginUser(Long.parseLong(userid), token);
        }
        return null;
    }

    /**
     * @Auther  吕伟林
     * @Des 设置登录用户信息
     * @Date 2021/7/1 15:21
     */
    public static void setLoginCache(LoginInfo info) {
        loginCache.setLoginUser(info);

        //清除错误登录信息
        if(errorLoginMap.containsKey(info.getUser().getUserCode())){
            errorLoginMap.remove(info.getUser().getUserCode());
        }
    }

    /**
     * @Auther  吕伟林
     * @Des 清除登录用户信息
     * @Date 2021/7/1 15:21
     */
    public static boolean clearLogin(Long id, String token){
        return loginCache.clearLogin(id, token);
    }

    /**
     * @Auther  吕伟林
     * @Des 反回给前端 的登录用户信息
     * @Date 2021/7/1 15:22
     */
    public static Map resultLoginCacheInfo(LoginInfo info){
        Map result = new HashMap();
        result.put("userId", info.getUser().getId());
        result.put("userName", info.getUser().getNickName());
        result.put("userAvatar", info.getUser().getAvatar());
        result.put("telephone", info.getUser().getTelephone());
        result.put("rmk", info.getUser().getRmk());
        result.put("token", info.getToken());
        if(info != null && info.getRole() != null){
            result.put("level", info.getRole().getLevel());
        }else{
            result.put("level", -1);
        }
        result.put("roleButton", info.getRoleButtonList());
        return result;
    }

    /**
     * @Auther  吕伟林
     * @Des 用户功能权限简化
     * @Date 2021/6/29 18:34
     */
    private static List roleButtonSimplify(List<SysRoleButton> list){
        List result = new ArrayList();
        for(SysRoleButton srb : list){
            HashMap<String, Object> map = new HashMap();
            map.put("menuCode", srb.getMenuCode());
            result.add(map);
        }
        return result;
    }

    /**
     * @Auther  吕伟林
     * @Des 检查是否允许登录
     * @Date 2021/7/1 15:23
     */
    public static boolean checkAllowLogin(String userCode){
        if(!errorLoginMap.containsKey(userCode)){
            return true;
        }

        int lockTime = 5;//锁定时间 分钟
        String ellt = ParamManager.SysParamMap.get("error_login_lock_time");//错误登录锁定时间
        if(ellt != null){
            try {
                lockTime = Integer.parseInt(ellt);
            }catch (Exception e){
            }
        }

        ErrorLoingInfo eli = errorLoginMap.get(userCode);
        if(eli.getLastLogin() == null || new Date().getTime() - eli.getLastLogin().getTime() > lockTime*60*1000){//最后登录时间为空或 已过限制时间
            return true;
        }

        return false;
    }

    /**
     * @Auther  吕伟林
     * @Des 新增错误登录
     * @Date 2021/7/1 15:58
     */
    public static void addErrorLogin(String userCode){
        ErrorLoingInfo eli = null;
        if(!errorLoginMap.containsKey(userCode)){
            eli = new ErrorLoingInfo();
            eli.setCode(userCode);
            eli.setErrorNum(0);
            errorLoginMap.put(userCode,eli);
        }else {
            eli = errorLoginMap.get(userCode);
        }

        int errorLogin = 5;//默认错误登录五次
        String el = ParamManager.SysParamMap.get("error_login");//错误登录次数
        if(el != null){
            try {
                errorLogin = Integer.parseInt(el);
            }catch (Exception e){
            }
        }
        eli.setErrorNum(eli.getErrorNum() + 1);

        //错误登录次数到达指定值
        if(eli.getErrorNum() >= errorLogin){
            eli.setLastLogin(new Date()); //设置最后登录时间
        }
    }
}

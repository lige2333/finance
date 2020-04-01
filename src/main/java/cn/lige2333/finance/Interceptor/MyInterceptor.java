package cn.lige2333.finance.Interceptor;

import cn.lige2333.finance.Utils.JwtUtils;
import cn.lige2333.finance.controller.BaseController;
import cn.lige2333.finance.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MyInterceptor extends BaseController implements HandlerInterceptor {
    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {//
    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
            throws Exception {//
    }

    @Override
    public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
        String mgr_getToken = "http://login.lige2333.cn/user/token";
        String mgr_login = "http://login.lige2333.cn/login";
        // 查询本系统token
        String token_u = arg0.getParameter("token") == null ? "" : arg0.getParameter("token").trim();
        String token_c = super.getCookieVal(arg0, "token");
        String token = token_u.equals("") ? token_c : token_u;
        // 本系统无token时
        if (token.equals("")) {
            arg1.sendRedirect(mgr_getToken + "?callback=" + arg0.getRequestURL().toString());
            return false;
        }
        // 本系统有token时
        User sysUser = JwtUtils.getObject(token, User.class);
        if (sysUser == null) {
            arg1.sendRedirect(mgr_login);
            return false;
        }
        // success
        super.setCookieVal(arg1, "token", token);
        arg0.setAttribute("user", sysUser);
        return true;
    }

    //
}

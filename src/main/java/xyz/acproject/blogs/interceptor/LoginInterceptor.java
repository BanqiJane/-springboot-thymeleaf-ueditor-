package xyz.acproject.blogs.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import xyz.acproject.blogs.entity.Admin;

public class LoginInterceptor implements HandlerInterceptor {
	@Override
    public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
		Admin admin = (Admin) req.getSession().getAttribute("admin");
        if (admin!=null) {
            return true;
        } else {
            resp.sendRedirect("/manager/login");
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest req, HttpServletResponse resp, Object handler, ModelAndView modelAndView) throws Exception {
//求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后
    	
    }

    @Override
    public void afterCompletion(HttpServletRequest req, HttpServletResponse resp, Object handler, Exception ex) throws Exception {
//在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作
    }
}

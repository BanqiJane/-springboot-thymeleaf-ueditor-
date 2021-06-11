package xyz.acproject.blogs.interceptor;

import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;


import xyz.acproject.blogs.entity.Ip;
import xyz.acproject.blogs.tools.FileTools;
import xyz.acproject.blogs.tools.returnJson.FastjsonConfig.FastJsonUtil;
import xyz.acproject.blogs.tools.returnJson.FastjsonConfig.Response;

public class MainInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse resp, Object handler) throws Exception {
		// TODO 自动生成的方法存根
		// 获取ip
		PrintWriter printWriter = resp.getWriter();
		String ip = req.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = req.getRemoteAddr();
		}
		// 获取时间
		Calendar calendar = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timeString = dateFormat.format(calendar.getTime());
		// iplist
		List<Ip> ips = new ArrayList<Ip>();
		ips.add(new Ip(ip, timeString.toString(), req.getRequestURI().toString()));
		FileTools.write("E:/banqi/blogs/record", ips, FileTools.read("E:/banqi/blogs/record", "ip"), "ip");
		// 404代码本体
		String contentTypeHeader = req.getHeader("Content-Type");
		String acceptHeader = req.getHeader("Accept");
		String xRequestedWith = req.getHeader("X-Requested-With");
		int status = resp.getStatus();
		if ((contentTypeHeader != null && contentTypeHeader.contains("application/json"))
				|| (acceptHeader != null && acceptHeader.contains("application/json"))
				|| "XMLHttpRequest".equalsIgnoreCase(xRequestedWith)) {
			if (status == 404) {
				String jsonString = FastJsonUtil.toJson(Response.error(req));
				resp.setContentType("application/json;charset=UTF-8");
				printWriter.write(jsonString);
				printWriter.flush();
				printWriter.close();
				return false;
			} else if (status == 500) {
				String jsonString = FastJsonUtil.toJson(Response.error(req));
				resp.setContentType("application/json;charset=UTF-8");
				printWriter.write(jsonString);
				printWriter.flush();
				printWriter.close();
				return false;
			} else {
				return true;
			}
		} else {
			if (status == 404) {
				resp.sendRedirect("/404");
				return false;
			} else if (status == 500) {
				resp.sendRedirect("/404");
				return false;
			} else {
				return true;
			}
		}
	}

	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse resp, Object handler, ModelAndView modelAndView)
			throws Exception {
		// 求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后
	}

	@Override
	public void afterCompletion(HttpServletRequest req, HttpServletResponse resp, Object handler, Exception ex)
			throws Exception {
		// 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作
	}
}

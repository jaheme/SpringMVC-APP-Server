package com.tony.logic.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 请求的拦截器，前置处理和后置处理。<br>
 * 拦截的URI和实例化见spring的配置文件。
 * @author Tony.lai
 *
 */
public class ExecuteInterceptor extends HandlerInterceptorAdapter {
	
	private static Logger log = LoggerFactory.getLogger(ExecuteInterceptor.class);
	private static final String T_ATTRI = "ei.st_time"; // 开始时间

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		request.setAttribute(T_ATTRI, System.currentTimeMillis());
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		long t = System.currentTimeMillis() - Long.valueOf(request.getAttribute(T_ATTRI).toString());
		if (t > 50) {
			log.warn("{} used: {} ms", request.getRequestURI(), t);
		}
		request.removeAttribute(T_ATTRI);
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}
	
	

}

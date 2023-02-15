package com.gl.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.gl.service.UserService;

public class LoginHandler implements HandlerInterceptor {
   @Override
   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
           throws Exception {
      // 获取请求的URL
      String url = request.getRequestURI();
      // 登录请求放行，不拦截
      if (url.indexOf("/api/login") >= 0) {
         return true;
      }
      // 获取 session
      HttpSession session = request.getSession();
      Object obj = session.getAttribute(UserService.USERNAME);
      if (obj != null) {
         return true;
      }
      return false;
   }

   @Override
   public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
           throws Exception {
      // TODO Auto-generated method stub
   }

   @Override
   public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
           throws Exception {
      // TODO Auto-generated method stub
   }
}
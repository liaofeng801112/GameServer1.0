
package com.gl.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.github.pagehelper.util.StringUtil;
import com.gl.model.Result;
import com.gl.model.User;
import com.gl.service.ResultFactory;
import com.gl.service.TokenService;
import com.gl.service.UserService;
import com.gl.token.UserToken;
import com.gl.util.RandomValidateCode;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.URL;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.come.tool.ReadExelTool;
import org.come.until.ReadTxtUtil;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.BASE64Decoder;

@RestController
public class UserController {


   @PostMapping({"/api/login"})
   public Result login(User user, HttpSession session) {
      String up = ReadTxtUtil.readFile1(ReadExelTool.class.getResource("/").getPath() + "administrator.db");
      String[] nameAndPwd = up.split("\\|&\\|");
      if (nameAndPwd[0].equals(user.getUserName()) && nameAndPwd[1].equals(user.getPassword())) {
         TokenService tokenService = new TokenService();
         String token = tokenService.getToken(user);
         session.setAttribute("xy2o", user.getUserName());
         session.setAttribute(UserService.USERNAME, user);
         return ResultFactory.success(token);
      } else {
         return ResultFactory.fail("用户名或密码错误，请重新登录！ ");
      }
   }

   @RequestMapping(value = "/api/checkcode")
   public void checkCode(HttpServletRequest request, HttpServletResponse response)
           throws ServletException, IOException {
      // 设置相应类型,告诉浏览器输出的内容为图片
      response.setContentType("image/jpeg");
      // 设置响应头信息，告诉浏览器不要缓存此内容
      response.setHeader("pragma", "no-cache");
      response.setHeader("Cache-Control", "no-cache");
      response.setDateHeader("Expire", 0);

      RandomValidateCode randomValidateCode = new RandomValidateCode();
      try {
         randomValidateCode.getRandcode(request, response);
      } catch (Exception e) {
         e.printStackTrace();
      }
   }

   /**
    * 处理注册功能
    */
   @PostMapping(value = "/api/register")
   public Result register(User user, HttpServletRequest request, HttpServletResponse res) {
      String code = user.getCode();
      if (StringUtils.isEmpty(code)) {
         return ResultFactory.fail("验证码不可为空");
      }

      HttpSession sesion = request.getSession();

      if (!code.equals(sesion.getAttribute(RandomValidateCode.RANDOMCODEKEY))) {
         // 避免一个验证码被多次使用暴力破解验证一次自动删除
         sesion.removeAttribute(RandomValidateCode.RANDOMCODEKEY);
         return ResultFactory.fail("验证码不正确");
      }

      // 验证通过也要马上删除掉验证码
      sesion.removeAttribute(RandomValidateCode.RANDOMCODEKEY);

      String ip = request.getHeader("x-forwarded-for");
      if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
         ip = request.getHeader("Proxy-Client-IP");
      }
      if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
         ip = request.getHeader("WL-Proxy-Client-IP");
      }
      if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
         ip = request.getRemoteAddr();
      }

      UserService service = new UserService();
      String msg = service.register(user, ip);
      if (StringUtil.isEmpty(msg)) {
         return ResultFactory.success("注册成功");
      }
      return ResultFactory.fail(msg);
   }

   /**
    * 退出登录
    */
   @CrossOrigin(origins = "*", maxAge = 3600)
   @RequestMapping("/api/logout")
   public Result logout(HttpSession session) {
      // 清除 session
      session.invalidate();
      return ResultFactory.success(null);
   }
   /**
    * 首页基础统计
    */
   @UserToken
   @CrossOrigin(origins = "*", maxAge = 3600)
   @GetMapping("/api/stat")
   public Result stat(HttpSession session) {
      UserService service = new UserService();
      return ResultFactory.success(service.getData());
   }
}

package com.gl.service;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.gl.model.User;

import java.util.Date;

public class TokenService {

   public String getToken(User user) {
      Date start = new Date();
      long currentTime = System.currentTimeMillis() + 60* 60 * 1000;//一小时有效时间
      Date end = new Date(currentTime);
      String token = "";

      token = JWT.create().withAudience(String.valueOf(user.getUserName())).withIssuedAt(start).withExpiresAt(end)
              .sign(Algorithm.HMAC256(user.getPassword()));
      return token;
   }
}
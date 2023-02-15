package com.gl.model;

public class User {

   private String userName;

   private String password;

   private String security;

   private String recommend;

   private String code;

   public String getUserName() {
      return userName;
   }

   public void setUserName(String userName) {
      this.userName = userName;
   }

   public String getPassword() {
      return password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   public String getSecurity() {
      return security;
   }

   public void setSecurity(String security) {
      this.security = security;
   }

   public String getRecommend() {
      return recommend;
   }

   public void setRecommend(String recommend) {
      this.recommend = recommend;
   }

   public String getCode() {
      return code;
   }

   public void setCode(String code) {
      this.code = code;
   }

}
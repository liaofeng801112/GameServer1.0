package com.gl.service;

import com.gl.enumpack.ResultCode;
import com.gl.model.Result;

public class ResultFactory {

   public static Result success(Object data) {
      return message(ResultCode.SUCCESS, "成功", data);
   }

   public static Result fail(String message) {
      return message(ResultCode.FAIL, message, null);
   }

   public static Result message(ResultCode resultCode, String message, Object data) {
      return message(resultCode.code, message, data);
   }

   public static Result message(int resultCode, String message, Object data) {
      return new Result(resultCode, message, data);
   }
}
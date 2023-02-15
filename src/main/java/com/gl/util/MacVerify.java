package com.gl.util;

import java.util.Calendar;
import java.util.Date;

public class MacVerify {
   public static void main(String[] args) {
      System.out.println(System.currentTimeMillis());
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(new Date());
      calendar.add(5, 100);
      Date date = calendar.getTime();
      System.out.println(date);
      System.out.println(date.getTime());
   }
}

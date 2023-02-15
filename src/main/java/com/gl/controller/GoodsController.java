

package com.gl.controller;

import com.gl.model.Param;
import com.gl.model.Result;
import com.gl.service.GoodsService;
import com.gl.service.ResultFactory;
import com.gl.token.UserToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoodsController {
   public GoodsController() {
   }

   @UserToken
   @GetMapping({"/api/goods"})
   public Result getGoods() {
      GoodsService service = new GoodsService();
      return ResultFactory.success(service.goodsMap());
   }

   @UserToken
   @PostMapping({"/api/exchange"})
   public Result exchange(Param param) {
      GoodsService service = new GoodsService();
      return service.createExchange(param) ? ResultFactory.success(null) : ResultFactory.fail("推广码生成失败");
   }

   @UserToken
   @RequestMapping({"/api/exchanges"})
   public Result getExchange(Param param) {
      GoodsService service = new GoodsService();
      return ResultFactory.success(service.getExchange(param));
   }

   @UserToken
   @PostMapping({"/api/sendgoods"})
   public Result sendgoods(Param param) {
      GoodsService service = new GoodsService();
      return service.sendGoods(param) ? ResultFactory.success(null) : ResultFactory.fail("物品发送失败，请确认玩家或物品是否存在");
   }
}

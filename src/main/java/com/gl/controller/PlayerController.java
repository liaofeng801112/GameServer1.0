
package com.gl.controller;

import com.gl.model.Param;
import com.gl.model.Result;
import com.gl.service.PlayerService;
import com.gl.service.ResultFactory;
import com.gl.token.UserToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerController {
   public PlayerController() {
   }

   @UserToken
   @PostMapping({"/api/role"})
   public Result roles(Param param) {
      PlayerService service = new PlayerService();
      return ResultFactory.success(service.getRole(param));
   }

   @UserToken
   @PostMapping({"/api/lockpwd"})
   public Result lockpwd(Param param) {
      PlayerService service = new PlayerService();
      return service.editLockPassword(param) ? ResultFactory.success(null) : ResultFactory.fail("操作失败");
   }

   @UserToken
   @PostMapping({"/api/roleoperation"})
   public Result operation(Param param) {
      PlayerService service = new PlayerService();
      return service.operation(param) ? ResultFactory.success(null) : ResultFactory.fail("操作失败，请确认该玩家是否存在");
   }

   @UserToken
   @PostMapping({"/api/recharge"})
   public Result recharge(Param param) {
      PlayerService service = new PlayerService();
      return service.rechargeCallBack(param) ? ResultFactory.success(null) : ResultFactory.fail("操作失败");
   }

   @UserToken
   @PostMapping({"/api/rechargeinfo"})
   public Result rechargeinfo(Param param) {
      PlayerService service = new PlayerService();
      return ResultFactory.success(service.getReceipts(param));
   }
}

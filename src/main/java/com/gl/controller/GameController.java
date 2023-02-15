
package com.gl.controller;

import com.alibaba.druid.util.StringUtils;
import com.gl.model.Param;
import com.gl.model.Result;
import com.gl.service.GameService;
import com.gl.service.ResultFactory;
import com.gl.token.UserToken;
import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class GameController {
   public GameController() {
   }

   @UserToken
   @GetMapping({"/api/gg"})
   public Result read() {
      GameService service = new GameService();
      return ResultFactory.success(service.readGG());
   }

   @UserToken
   @PostMapping({"/api/gg"})
   public Result write(Param param) {
      GameService service = new GameService();
      return service.writeGG(param) ? ResultFactory.success(null) : ResultFactory.fail("写入游戏公告错误");
   }

   @UserToken
   @PostMapping({"/api/sendmsg"})
   public Result sendmsg(Param param) {
      GameService service = new GameService();
      return service.sendMsg(param) ? ResultFactory.success(null) : ResultFactory.fail("发布系统消息错误");
   }

   @UserToken
   @GetMapping({"/api/lhpz"})
   public Result readLHPZ() {
      GameService service = new GameService();
      return ResultFactory.success(service.readConfig());
   }

   @UserToken
   @PostMapping({"/api/lhpz"})
   public Result saveLHPZ(@RequestBody Map<String, String> map) {
      if (map.size() > 0) {
         GameService service = new GameService();
         return service.saveConfig(map) ? ResultFactory.success(null) : ResultFactory.fail("保存失败，配置参数不匹配");
      } else {
         return ResultFactory.fail("没有接收到参数");
      }
   }

   @UserToken
   @PostMapping({"/api/uploadconfig"})
   public Result uploadConfig(@RequestParam(value = "file",required = false) MultipartFile file) {
      if (file != null) {
         GameService service = new GameService();

         try {
            String msg = service.updateXls(file);
            if (StringUtils.isEmpty(msg)) {
               return ResultFactory.success(null);
            }

            return ResultFactory.fail(msg);
         } catch (IllegalStateException var5) {
            var5.printStackTrace();
         } catch (IOException var6) {
            var6.printStackTrace();
         }

         return ResultFactory.fail("配置文件解析错误");
      } else {
         return ResultFactory.fail("没有接收到参数");
      }
   }

   @UserToken
   @GetMapping({"/api/readconfig"})
   public Result readConfig() {
      GameService service = new GameService();
      return ResultFactory.success(service.readXls());
   }

   @UserToken
   @GetMapping({"/api/downconfig"})
   public Result downConfig(HttpServletResponse res, @RequestParam("fileName") String fileName) throws IOException {
      res.setHeader("Content-type", "application/vnd.ms-excel");
      res.setCharacterEncoding("UTF-8");
      res.setHeader("Content-Disposition", "attachment;filename=" + fileName);
      GameService service = new GameService();
      String msg = service.downloadXls(fileName, res.getOutputStream());
      return StringUtils.isEmpty(msg) ? ResultFactory.success(null) : ResultFactory.fail(msg);
   }

   @UserToken
   @DeleteMapping({"/api/delconfig"})
   public Result deleteConfig(@RequestParam("fileName") String fileName) {
      GameService service = new GameService();
      String msg = service.deleteXls(fileName);
      return StringUtils.isEmpty(msg) ? ResultFactory.success(null) : ResultFactory.fail(msg);
   }
}

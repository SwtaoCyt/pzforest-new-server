package com.song.pzforestserver.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.song.pzforestserver.service.UserService;
import com.song.pzforestserver.service.WeappService;
import com.song.pzforestserver.util.Result;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Tag(name="UserController")
@RestController
@RequestMapping("/user")
public class UserController {

        @Autowired
        WeappService wxService;

        // 测试登录，浏览器访问： http://localhost:8081/user/doLogin?username=zhang&password=123456
        @PostMapping("/login")
        public Result login(@RequestBody Map<String, String> params) throws IOException {
                String code = params.get("code");
                String encryptedData = params.get("encryptedData");
                String iv = params.get("iv");

                Map map = wxService.login(code);
                return Result.success(map);
        }

        // 查询登录状态，浏览器访问： http://localhost:8081/user/isLogin
        @RequestMapping("isLogin")
        public String isLogin() {
                return "当前会话是否登录：" + StpUtil.isLogin();
        }
        }

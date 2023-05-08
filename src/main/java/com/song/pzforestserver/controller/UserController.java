package com.song.pzforestserver.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONObject;
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
import java.util.HashMap;
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
        public Result<Map> login(@RequestBody Map<String, String> params) throws IOException {
                String code = params.get("code");
                String encryptedData = params.get("encryptedData");
                String iv = params.get("iv");

                // 调用微信接口获取 session_key 和 openid
                JSONObject sessionInfo = wxService.getSessionInfo(code);
                Map map= new HashMap<>();

                String openid = sessionInfo.getStr("openid");
                String sessionkey = sessionInfo.getStr("session_key");
                map.put("openid",openid);
                map.put("session_key",sessionkey);
                // 使用 openid 进行用户登录
                StpUtil.login(openid);
                // ... 进行业务处理，例如绑定用户信息等

                return Result.success(map);
        }

        // 查询登录状态，浏览器访问： http://localhost:8081/user/isLogin
        @RequestMapping("isLogin")
        public String isLogin() {
                return "当前会话是否登录：" + StpUtil.isLogin();
        }
        }

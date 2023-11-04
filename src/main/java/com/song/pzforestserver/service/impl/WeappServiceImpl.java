package com.song.pzforestserver.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.song.pzforestserver.entity.User;
import com.song.pzforestserver.service.UserService;
import com.song.pzforestserver.service.WeappService;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
public class WeappServiceImpl implements WeappService {
    public static String BASE_URL="https://api.weixin.qq.com/";
    public static String AppId ="wxb5fcfb7fd31951f9";
    public static String Secret = "85b681204f4058a099102c7d3ff04520";
    OkHttpClient client = new OkHttpClient();



    @Autowired
    UserService userService;
    @Override
    public JSONObject getSessionInfo(String code) throws IOException {
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(BASE_URL + "sns/jscode2session")).newBuilder();
        urlBuilder.addQueryParameter("appid",AppId)
                .addQueryParameter("secret",Secret)
                .addQueryParameter("js_code",code)
                .addQueryParameter("grant_type","authorization_code");

        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();


        String json =response.body().string();
        return JSONUtil.parseObj(json);


    }

    @Override
    public Map login(String code) throws IOException {
        // 调用微信接口获取 session_key 和 openid
        JSONObject sessionInfo = this.getSessionInfo(code);
        Map map= new HashMap<>();

        String openid = sessionInfo.getStr("openid");
        User temp  = userService.selectUserByUserId(openid);
        if (ObjectUtils.isEmpty(temp))
        {
            temp = new
                    User();
            UUID uuid =UUID.randomUUID();
            temp.setId(String.valueOf(uuid));
            temp.setOpenid(openid);
            temp.setCreateTime(DateTime.now());
            temp.setNikename("培正学生");
            log.info(temp.toString());
            userService.saveOrUpdate(temp);
        }

        String sessionkey = sessionInfo.getStr("session_key");
        map.put("openid",openid);
        map.put("session_key",sessionkey);
        // 使用 openid 进行用户登录
        StpUtil.login(openid);
        // ... 进行业务处理，例如绑定用户信息等
        return map;
    }
}

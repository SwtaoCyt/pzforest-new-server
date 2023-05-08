package com.song.pzforestserver.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.song.pzforestserver.service.WeappService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import okhttp3.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class WeappServiceImpl implements WeappService {
    public static String BASE_URL="https://api.weixin.qq.com/";
    public static String AppId ="wxb5fcfb7fd31951f9";
    public static String Secret = "85b681204f4058a099102c7d3ff04520";
    OkHttpClient client = new OkHttpClient();

    @Override
    public JSONObject getSessionInfo(String code) throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL+"sns/jscode2session").newBuilder();
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
        log.info("当前数据如下"+json);
        JSONObject jsonObject = JSONUtil.parseObj(json);
        return jsonObject;


    }
}

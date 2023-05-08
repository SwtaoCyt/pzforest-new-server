package com.song.pzforestserver.controller;


import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.song.pzforestserver.entity.WeChatPhoneNumberRequest;
import com.song.pzforestserver.util.WeChatDecryptUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Base64;

@Slf4j
@Tag(name="WeappController")
@RestController
@RequestMapping("/weapp")
public class WeappController {

    @PostMapping("/phone")
    public String getPhoneNumber(@RequestBody WeChatPhoneNumberRequest request) {
        String sessionKey = request.getSessionKey();
        String encryptedData = request.getEncryptedData();
        String iv = request.getIv();


// 解密数据
        try {
            String result = WeChatDecryptUtil.decrypt(encryptedData, sessionKey, iv);
            // 在这里解析出用户手机号码
            JSONObject jsonObject = JSONUtil.parseObj(result);
            log.info(result);
            String phoneNumber = jsonObject.getStr("phoneNumber");
            return phoneNumber;
        } catch (Exception e) {
            // 处理解密失败的情况
            e.printStackTrace();
            return "";
        }

    }
}

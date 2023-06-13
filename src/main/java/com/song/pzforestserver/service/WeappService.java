package com.song.pzforestserver.service;

import cn.hutool.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public interface WeappService {
    /**
     * 根据code调用微信api获取微信的openid和sessionid
     * @param code
     * @return openid,sessionid
     * @throws IOException
     */
    public JSONObject getSessionInfo(String code) throws IOException;

    /**
     * 根据前端的code进行登录
     * @param code
     * @return
     * @throws IOException
     */
    public Map login(String code) throws IOException;
}

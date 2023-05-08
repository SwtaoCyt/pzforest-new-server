package com.song.pzforestserver.service;

import cn.hutool.json.JSONObject;

import java.io.IOException;

public interface WeappService {
    public JSONObject getSessionInfo(String code) throws IOException;
}

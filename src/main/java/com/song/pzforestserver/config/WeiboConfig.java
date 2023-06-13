package com.song.pzforestserver.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "pzforest")
public class WeiboConfig {
    private static List<String> accessToken;
    public static int count = 0;
    public static String getAccessToken() {
        String token = accessToken.get(count);
        count++;
        if(count==accessToken.size()) count=0;
        return token;
    }

    public void setAccessToken(List<String> accessToken) {
        this.accessToken = accessToken;
    }
}

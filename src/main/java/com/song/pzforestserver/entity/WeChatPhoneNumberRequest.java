package com.song.pzforestserver.entity;

import lombok.Data;

@Data
public class WeChatPhoneNumberRequest {
    private String encryptedData;

    private String iv;

    private String sessionKey;


}

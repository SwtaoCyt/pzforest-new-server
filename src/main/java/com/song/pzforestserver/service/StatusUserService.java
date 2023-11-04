package com.song.pzforestserver.service;

import cn.hutool.core.date.DateTime;
import com.song.pzforestserver.entity.StatusUser;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Swtao
* @description 针对表【status_user】的数据库操作Service
* @createDate 2023-05-14 23:11:22
*/
public interface StatusUserService extends IService<StatusUser> {
    /**
     * 添加用户与Status表的关联记录
     * @param openId 用户的微信id
     * @param contributeType 投稿类型，0为微信投稿，1为微博@投稿
     * @param statusId 用户的微博id
     */
    void AddStatusUser(String openId,String contributeType,String statusId);

}

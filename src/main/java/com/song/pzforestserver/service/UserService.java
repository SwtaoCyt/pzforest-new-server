package com.song.pzforestserver.service;

import com.song.pzforestserver.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author Swtao
* @description 针对表【user】的数据库操作Service
* @createDate 2023-06-15 09:58:52
*/
public interface UserService extends IService<User> {
    User selectUserByUserId(String openid);

    /**
     *
     * @param openid
     * @param nikename
     * @return 412没有这个用户,408冷却期不够,200成功
     */
    Integer changeMyName(String openid, String nikename);

    String selectNicknameByOpenId(String openid);
}

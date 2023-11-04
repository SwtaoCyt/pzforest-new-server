package com.song.pzforestserver.mapper;

import com.song.pzforestserver.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Swtao
* @description 针对表【user】的数据库操作Mapper
* @createDate 2023-06-15 09:58:52
* @Entity com.song.pzforestserver.entity.User
*/
@Mapper
public interface UserMapper extends BaseMapper<User> {

    User selectUserByUserId (String openid);

    String selectNicknameByOpenId(String openid);
}





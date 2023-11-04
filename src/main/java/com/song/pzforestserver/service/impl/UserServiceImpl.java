package com.song.pzforestserver.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.song.pzforestserver.entity.User;
import com.song.pzforestserver.service.UserService;
import com.song.pzforestserver.mapper.UserMapper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

/**
* @author Swtao
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-06-15 09:58:52
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Resource
    UserMapper userMapper;
    @Override
    public User selectUserByUserId(String openid) {
        return userMapper.selectUserByUserId(openid);
    }

    @Override
    public Integer changeMyName(String openid, String nikename) {
         final Integer SUCCESS = 200;
         final Integer NOUSER  = 412;
         final Integer NOCD =408;
        User user  = userMapper.selectUserByUserId(openid);
        if(ObjectUtils.isEmpty(user))
        {
            return NOUSER;
        }
        else
        {
            if(ObjectUtils.isEmpty(user.getUpdateTime()))
            {
                user.setUpdateTime(DateTime.now());
                user.setNikename(nikename);
            }
            else {

               long betweenDay= DateUtil.between(user.getUpdateTime(), DateTime.now(), DateUnit.DAY);
               if(betweenDay>30)
               {
                   user.setUpdateTime(DateTime.now());
                   user.setNikename(nikename);
               }
               else
               {
                   return NOCD;
               }
            }



        }
        this.saveOrUpdate(user);
        return SUCCESS;
    }

    @Override
    public String selectNicknameByOpenId(String openid) {
        return userMapper.selectNicknameByOpenId(openid);
    }
}





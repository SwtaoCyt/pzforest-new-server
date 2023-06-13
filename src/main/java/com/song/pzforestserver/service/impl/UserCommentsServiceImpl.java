package com.song.pzforestserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.song.pzforestserver.entity.UserComments;
import com.song.pzforestserver.service.UserCommentsService;
import com.song.pzforestserver.mapper.UserCommentsMapper;
import org.springframework.stereotype.Service;

/**
* @author Swtao
* @description 针对表【user_comments】的数据库操作Service实现
* @createDate 2023-05-10 12:57:13
*/
@Service
public class UserCommentsServiceImpl extends ServiceImpl<UserCommentsMapper, UserComments>
    implements UserCommentsService{

}





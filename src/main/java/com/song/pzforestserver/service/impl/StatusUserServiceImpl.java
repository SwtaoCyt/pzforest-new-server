package com.song.pzforestserver.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.song.pzforestserver.entity.StatusUser;
import com.song.pzforestserver.service.StatusUserService;
import com.song.pzforestserver.mapper.StatusUserMapper;
import org.springframework.stereotype.Service;

/**
* @author Swtao
* @description 针对表【status_user】的数据库操作Service实现
* @createDate 2023-05-14 23:11:22
*/
@Service
public class StatusUserServiceImpl extends ServiceImpl<StatusUserMapper, StatusUser>
    implements StatusUserService{


    @Override
    public void AddStatusUser( String openId, String contributeType, String statusId) {
        StatusUser statusUser = new StatusUser();
        statusUser.setCreateTime(DateTime.now());
        statusUser.setUserid(openId);
        statusUser.setStatus(statusId);
        statusUser.setContributeType(contributeType);
        this.saveOrUpdate(statusUser);
    }
}








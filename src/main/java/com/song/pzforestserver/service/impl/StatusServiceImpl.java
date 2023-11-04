package com.song.pzforestserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.song.pzforestserver.config.WeiboConfig;
import com.song.pzforestserver.entity.Status;
import com.song.pzforestserver.entity.StatusCounts;
import com.song.pzforestserver.service.StatusService;
import com.song.pzforestserver.mapper.StatusMapper;
import com.song.pzforestserver.service.WeiboService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
* @author Swtao
* @description 针对表【weibos_status(微博信息表)】的数据库操作Service实现
* @createDate 2023-05-21 22:43:05
*/
@Slf4j
@Service
public class StatusServiceImpl extends ServiceImpl<StatusMapper, Status>
    implements StatusService{

    @Autowired
    StatusMapper statusMapper;
    @Autowired
    WeiboConfig weiboConfig;


    @Override
    public Status selectByWeiboId(String id) {
        return statusMapper.selectByWeiboId(id);
    }

    @Override
    public Integer getTotalCount(String text) {
        return statusMapper.getTotalCount(text);
    }


}





package com.song.pzforestserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.song.pzforestserver.entity.Status;
import com.song.pzforestserver.service.StatusService;
import com.song.pzforestserver.mapper.StatusMapper;
import org.springframework.stereotype.Service;

/**
* @author Swtao
* @description 针对表【weibos_status(微博信息表)】的数据库操作Service实现
* @createDate 2023-03-19 18:28:46
*/
@Service
public class StatusServiceImpl extends ServiceImpl<StatusMapper, Status>
    implements StatusService{

}





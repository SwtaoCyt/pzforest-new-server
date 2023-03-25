package com.song.pzforestserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.song.pzforestserver.entity.StatusCounts;
import com.song.pzforestserver.service.StatusCountsService;
import com.song.pzforestserver.mapper.StatusCountsMapper;
import org.springframework.stereotype.Service;

/**
* @author Swtao
* @description 针对表【weibos_status_counts】的数据库操作Service实现
* @createDate 2023-03-25 17:04:34
*/
@Service
public class StatusCountsServiceImpl extends ServiceImpl<StatusCountsMapper, StatusCounts>
    implements StatusCountsService{

}





package com.song.pzforestserver.service;

import com.song.pzforestserver.entity.Status;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.io.IOException;
import java.util.List;

/**
* @author Swtao
* @description 针对表【weibos_status(微博信息表)】的数据库操作Service
* @createDate 2023-05-21 22:43:05
*/
public interface StatusService extends IService<Status> {


    Status selectByWeiboId(String id);
}

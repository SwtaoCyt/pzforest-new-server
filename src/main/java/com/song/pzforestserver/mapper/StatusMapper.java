package com.song.pzforestserver.mapper;

import com.song.pzforestserver.entity.Status;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Swtao
* @description 针对表【weibos_status(微博信息表)】的数据库操作Mapper
* @createDate 2023-03-19 18:28:46
* @Entity com.song.pzforestserver.entity.Status
*/
@Mapper
public interface StatusMapper extends BaseMapper<Status> {

}





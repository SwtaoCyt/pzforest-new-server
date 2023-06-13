package com.song.pzforestserver.mapper;

import com.song.pzforestserver.entity.Status;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author Swtao
* @description 针对表【weibos_status(微博信息表)】的数据库操作Mapper
* @createDate 2023-05-21 22:43:05
* @Entity com.song.pzforestserver.entity.Status
*/
@Mapper
public interface StatusMapper extends BaseMapper<Status> {

    List<Status> SelectStatusList(@Param("text") String text, @Param("offset") int offset, @Param("limit") int limit);
    List<Status> SelectStatusImageList(@Param("text") String text, @Param("offset") int offset, @Param("limit") int limit);

    Status selectByWeiboId(String id);

}





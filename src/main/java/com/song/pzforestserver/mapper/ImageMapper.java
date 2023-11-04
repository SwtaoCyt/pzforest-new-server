package com.song.pzforestserver.mapper;

import com.song.pzforestserver.entity.Image;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Swtao
* @description 针对表【image】的数据库操作Mapper
* @createDate 2023-06-08 21:28:27
* @Entity com.song.pzforestserver.entity.Image
*/
@Mapper
public interface ImageMapper extends BaseMapper<Image> {

}





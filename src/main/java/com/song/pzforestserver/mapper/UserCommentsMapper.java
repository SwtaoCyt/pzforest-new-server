package com.song.pzforestserver.mapper;

import com.song.pzforestserver.entity.UserComments;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Swtao
* @description 针对表【user_comments】的数据库操作Mapper
* @createDate 2023-05-10 12:57:13
* @Entity com.song.pzforestserver.entity.UserComments
*/
@Mapper
public interface UserCommentsMapper extends BaseMapper<UserComments> {

}





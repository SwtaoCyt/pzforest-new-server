package com.song.pzforestserver.mapper;

import com.song.pzforestserver.entity.Comments;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author Swtao
* @description 针对表【weibos_comments(微博评论表)】的数据库操作Mapper
* @createDate 2023-03-19 18:28:39
* @Entity com.song.pzforestserver.entity.Comments
*/
@Mapper
public interface CommentsMapper extends BaseMapper<Comments> {

}





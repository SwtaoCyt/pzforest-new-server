package com.song.pzforestserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.song.pzforestserver.entity.Comments;
import com.song.pzforestserver.service.CommentsService;
import com.song.pzforestserver.mapper.CommentsMapper;
import org.springframework.stereotype.Service;

/**
* @author Swtao
* @description 针对表【weibos_comments(微博评论表)】的数据库操作Service实现
* @createDate 2023-03-19 18:28:39
*/
@Service
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments>
    implements CommentsService{

}





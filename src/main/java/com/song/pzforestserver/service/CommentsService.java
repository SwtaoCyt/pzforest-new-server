package com.song.pzforestserver.service;

import cn.hutool.core.date.DateTime;
import com.song.pzforestserver.entity.Comments;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;

/**
* @author Swtao
* @description 针对表【weibos_comments(微博评论表)】的数据库操作Service
* @createDate 2023-06-14 16:33:19
*/
public interface CommentsService extends IService<Comments> {

    public boolean canComment(String userId);
    public boolean canComment(String userId,String status);
    void createComment(String cid, String id, String openid, String comment, DateTime dateTime);


}

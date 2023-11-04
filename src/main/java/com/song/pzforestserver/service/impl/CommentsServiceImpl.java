package com.song.pzforestserver.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.song.pzforestserver.entity.Comments;
import com.song.pzforestserver.entity.UserComments;
import com.song.pzforestserver.service.CommentsService;
import com.song.pzforestserver.mapper.CommentsMapper;
import com.song.pzforestserver.service.UserCommentsService;
import com.song.pzforestserver.service.UserService;
import com.song.pzforestserver.util.CacheUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
* @author Swtao
* @description 针对表【weibos_comments(微博评论表)】的数据库操作Service实现
* @createDate 2023-06-14 16:33:19
*/
@Service
@Slf4j
public class CommentsServiceImpl extends ServiceImpl<CommentsMapper, Comments>
    implements CommentsService{
    private static final String COMMENT_PREFIX = "comment:";
    @Autowired
    CacheUtils cacheService;
    @Resource
    UserCommentsService userCommentsService;
    @Autowired
    UserService userService;
    @Override
    public boolean canComment(String userId) {
        String commentKey = COMMENT_PREFIX + userId;
        String lastCommentTime = cacheService.get(commentKey);
        if (StringUtils.isEmpty(lastCommentTime)||lastCommentTime.equals("{}") ) {
            // 用户没有评论过，可以继续评论
            cacheService.add(commentKey, String.valueOf(System.currentTimeMillis()), 1, TimeUnit.HOURS);
            return true;
        } else {
            // 用户已经评论过，检查评论时间是否超过1小时
            long lastTime = Long.parseLong(lastCommentTime);
            long currentTime = System.currentTimeMillis();
            long elapsedTime = currentTime - lastTime;
            if (elapsedTime >= TimeUnit.HOURS.toMillis(1)) {
                // 超过1小时，可以继续评论
                cacheService.add(commentKey, System.currentTimeMillis(), 1, TimeUnit.HOURS);
                return true;
            } else {
                // 未超过1小时，不允许评论
                return false;
            }
        }
    }

    @Override
    public boolean canComment(String userId, String status) {
        return false;
    }

    @Override
    public void createComment(String cid, String id, String openid, String comment, DateTime dateTime) {
        Comments comments  = new Comments();
        comments.setRootid(cid);
        comments.setName(cid);
        comments.setCreatedAt(dateTime);
        comments.setUserId(openid);
        String nickname = userService.selectNicknameByOpenId(openid);
        if(StringUtils.isNotEmpty(nickname))
        {
            comments.setName(nickname);
        }

        this.saveOrUpdate(comments);
        UserComments userComments = new UserComments();
        userComments.setId(cid);
        userComments.setCommentId(Integer.valueOf(id));
        userComments.setUserId(openid);
        userComments.setCreateTime(dateTime);
        userComments.setText(comment);
        userCommentsService.saveOrUpdate(userComments);

    }
}





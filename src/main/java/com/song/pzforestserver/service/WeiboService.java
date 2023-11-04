package com.song.pzforestserver.service;

import com.song.pzforestserver.entity.Comments;
import com.song.pzforestserver.entity.Status;
import com.song.pzforestserver.entity.StatusCounts;
import com.song.pzforestserver.entity.WeiboException;
import com.song.pzforestserver.util.Result;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

public interface WeiboService {
    /**
     * 发送微博,该方法不带图片。
     * @param status 文字内容
     */
    public void sendStatus(String access_token, String status, Integer mode, Consumer<String> callback);

    /**
     * 带图片的微博发送方法
     * @param access_token 用户授权的唯一票据，用于调用微博的开放接口，同时也是第三方应用验证微博用户登录的唯一票据，第三方应用应该对该票据进行校验，校验方法为调用 oauth2/get_token_info 接口，对比返回的授权信息中的APPKEY是否正确一致，然后用 access_token 与自己应用内的用户建立唯一影射关系，来识别登录状态，不能只是简单的使用本返回值里的UID字段来做登录识别。
     * @param status
     * @param image
     * @param mode
     * @param callback
     * @throws IOException
     */
    public void sendStatus(String access_token, String status, MultipartFile image, Integer mode,String fromStatus, Consumer<String> callback) throws IOException;
    /**
     * 发送纯文字的微博
     * @param access_token 用户授权的唯一票据，用于调用微博的开放接口，同时也是第三方应用验证微博用户登录的唯一票据，第三方应用应该对该票据进行校验，校验方法为调用 oauth2/get_token_info 接口，对比返回的授权信息中的APPKEY是否正确一致，然后用 access_token 与自己应用内的用户建立唯一影射关系，来识别登录状态，不能只是简单的使用本返回值里的UID字段来做登录识别。
     * @param status
     * @param callback
     */
    public void sendStatus(String access_token, String status,Integer mode,String fromStatus, Consumer<String> callback);

    /**
     * 根据参数获取AccessToken,数据获取请参考OAUTH2.0
     * @param clientId
     * @param client_secret
     * @param code
     * @param redirect_uri
     */
    public void  getAccessToken(String clientId,String client_secret,String code,String redirect_uri);
    public Result home_timeline(String accessToken, String sinceId, String maxId, int count, int page, int baseApp, int feature, int trimUser);
    public  Result user_timeline(String accessToken,int page,int count,int feature);

    /**
     * https://api.weibo.com/2/statuses/show.json
     * 根据微博ID获取单条微博内容
     * @param id
     * @param access_token
     * @return
     * @throws IOException
     * @throws WeiboException
     */
    public Status show(String id, String access_token) throws IOException, WeiboException;

    /**
     *批量获取指定微博的转发数评论数
     * @param accessToken
     * @param ids
     * @return
     * @throws IOException
     */
    public List<StatusCounts> getStatusCounts(String accessToken, String... ids)throws IOException;
    /**
     *
     * @param accessToken
     * @param id
     * @param sinceId
     * @param maxId
     * @param count
     * @param page
     * @return 根据微博ID返回某条微博的评论列表
     * @throws IOException
     * @throws WeiboException
     */
    public  List<Comments> getComments(String accessToken, String id, String sinceId, String maxId, int count, int page) throws IOException,WeiboException;

    /**
     * 根据微博ID评论
     * @param access_token
     * @param id
     * @param comment
     * @return
     * @throws IOException
     */
    public DeferredResult<String> createComment(String access_token, String id, String comment) throws IOException;

    List<Status> SelectStatusList(String text, int offset, int limit) throws IOException;

    /**
     * 回复评论
     * @param access_token
     * @param cid
     * @param id
     * @param comment
     * @return
     * @throws IOException
     */
    public DeferredResult<String> reply(String access_token,String cid,  String id,String comment) throws IOException;
    public Mono<String> reply(String cid,String id,String openid,String accessToken,String comment);
    /**
     * 投稿并保存
     * @param access_token
     * @param status
     * @param image
     * @param openId
     * @return
     * @throws IOException
     */
    Mono<String> contributeAndSave(String access_token, String status, MultipartFile image, String openId, Integer mode) throws IOException;

    public  List<Comments> getComments(String accessToken, String id) throws IOException,WeiboException;
    /**
     * 投稿并且带图片的
     * @param access_token
     * @param status
     * @param image
     * @param callback
     * @throws IOException
     */
    public void sendStatus(String access_token, String status, MultipartFile image, Integer mode, Consumer<String> callback) throws IOException;

    public Mono<Object> createComment(String access_token ,String comment,String weiboId,String openid);

    void handleAt();
}

package com.song.pzforestserver.controller;

import com.song.pzforestserver.config.WeiboConfig;
import com.song.pzforestserver.entity.Comments;
import com.song.pzforestserver.entity.Status;
import com.song.pzforestserver.entity.StatusCounts;
import com.song.pzforestserver.entity.WeiboException;
import com.song.pzforestserver.service.StatusService;
import com.song.pzforestserver.service.WeiboService;
import com.song.pzforestserver.util.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Tag(name="WeiboController")
@RestController
@RequestMapping("/weibo")
public class WeiboController {
    private final OkHttpClient httpClient = new OkHttpClient();


    @Autowired
    WeiboConfig weiboConfig;
    @Autowired
    StatusService statusService;
    @Autowired
    WeiboService weiboResponse ;
    @Operation(summary = "test")
    @RequestMapping("send")
    public List<Status> test(String text,int offset,int limit) throws IOException {

        return weiboResponse.SelectStatusList(text,offset,limit);
    }

    @Operation(summary = "test2")
    @RequestMapping("send2")
    public List<StatusCounts> test2(String... id) throws IOException {

        return weiboResponse.getStatusCounts(weiboConfig.getAccessToken(),id);
    }


    @Scheduled(fixedRate = 1000*60*5)
    @Operation(summary = "handleAt")
    @RequestMapping("handleAt")
    public  void handleAt()
    {
        weiboResponse.handleAt();
    }


    @RequestMapping(value = "/getStatusList")
    @Operation(summary = "getStatusList")
    public Result getStatusList(@RequestParam(value = "text", required = false) String text,
                                @RequestParam("page") int offset,
                                @RequestParam(value = "limit", required = false) int limit) throws IOException {
        List<Status> statuses=new ArrayList<>();
        if(text.equals("null"))
        {
            statuses  = weiboResponse.SelectStatusList(null, offset, limit);
        }
        else {
            statuses = weiboResponse.SelectStatusList(text, offset, limit);
        }


        return new Result(200, "查询成功", statuses);
    }

    @RequestMapping(value = "/sendStatusForText")
    @Operation(summary = "sendStatusOnlyText")
    public Result send_status_onlyText(@RequestBody Map<String, Object> requestData

    ) throws IOException {
        String accessToken = (String) requestData.get("accessToken");
        String sessionId = (String) requestData.get("sessionId");
        String openid = (String) requestData.get("openid");
        String text = (String) requestData.get("text");
        Integer mode = (Integer) requestData.get("mode");
//        log.info(accessToken);
//        log.info(String.valueOf(StringUtils.isEmpty(accessToken)));
        String token = StringUtils.isEmpty(accessToken) ? weiboConfig.getAccessToken():accessToken;
        weiboResponse.contributeAndSave(token,text,null,openid,mode);
        return new Result(200,"投稿成功！",null);
    }

    /**
     * 发送微博,带图片的api
     * @param file
     * @return
     */
    @PostMapping(value = "/sendStatus", consumes = "multipart/form-data")
    @Operation(summary = "sendStatus")
    public Result send_status(@RequestParam("accessToken") String accessToken,
            @RequestParam("file") MultipartFile file,
                              @RequestParam("sessionId") String sessionId,
                              @RequestParam("openid") String openid,
                              @RequestParam("text") String text,
    @RequestParam("mode") Integer mode) throws IOException {
    log.info("accessToken:" +accessToken);

        String token = accessToken.equals("[object Null]") ? weiboConfig.getAccessToken():accessToken;

        weiboResponse.contributeAndSave(token,text,file,openid,mode);

        return new Result(200,"ok",null);
    }

    @Operation(summary = "hometimeline")
    @RequestMapping("hometimeline")
    public Result getHome_timeline(String accessToken, String sinceId, String maxId, int count, int page, int baseApp, int feature, int trimUser)
    {
        weiboResponse.home_timeline(accessToken,sinceId,maxId,count,page,baseApp,feature,trimUser);
        return  new Result(200,"ok",null);
    }

    /**
     * 获取该用户的前5条微博，貌似没用
     * @param accessToken
     * @param count
     * @param page
     * @param feature
     * @return
     */
    @Operation(summary = "usertimeline")
    @RequestMapping("usertimeline")
    public Result getUser_timeline(String accessToken, int count, int page, int feature)
    {
        weiboResponse.user_timeline(accessToken,page,count,feature);
        return  new Result(200,"ok",null);
    }

    /**
     * 显示微博
     * @param accessToken
     * @param id
     * @return
     * @throws WeiboException
     * @throws IOException
     */
    @Operation(summary = "show")
    @RequestMapping("show")
    public Result show(String accessToken, String id) throws WeiboException, IOException {
        weiboResponse.show(id,accessToken);
        return  new Result(200,"ok",weiboResponse.show(id,accessToken));
    }

    @Operation(summary = "create")
    @RequestMapping("create")
    public Result create_status()
    {
        return  new Result(200,"ok",weiboResponse);
    }

    /**
     *创建评论
     * @param accessToken
     * @param id
     * @param comment
     * @throws IOException
     */
    @Operation(summary="createComment")
    @RequestMapping("createComment")
    public DeferredResult<String> createComment(@RequestParam("access_token") String accessToken,
                                                @RequestParam("id") long id,
                                                @RequestParam("comment") String comment) throws IOException {




        return weiboResponse.createComment(accessToken, String.valueOf(id),comment);
    }

    /**
     * 获取一条微博的评论内容
     * @param accessToken
     * @param id 微博id
     * @return
     * @throws WeiboException
     * @throws IOException
     */
    @Operation(summary="getComments")
    @RequestMapping("getComments")
    public Result getComments(@RequestParam(value = "access_token",required = false) String accessToken,
                              @RequestParam(value = "id") String id) throws WeiboException, IOException {
        String token = accessToken.equals("undefined") ? weiboConfig.getAccessToken():accessToken;
        List<Comments> comments =weiboResponse.getComments(token,id);
        return new Result(200,"ok",comments);
    }

    /**
     * 评论评论
     * @param accessToken
     * @param cid
     * @param id
     * @param comment
     * @return
     * @throws IOException
     */
    @Operation(summary="reply")
    @RequestMapping("reply")
    public DeferredResult<String> reply(@RequestParam("access_token") String accessToken,
                                        @RequestParam("cid") int cid,
                                        @RequestParam("id") int id,
                                        @RequestParam("comment") String comment
    ) throws IOException {

        return weiboResponse.reply(accessToken,cid,id,comment);
    }

}






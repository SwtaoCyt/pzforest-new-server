package com.song.pzforestserver.controller;

import com.song.pzforestserver.entity.WeiboException;
import com.song.pzforestserver.http.WeiboResponse;
import com.song.pzforestserver.util.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@Tag(name="WeiboController")
@RestController
@RequestMapping("/weibo")
public class WeiboController {
    private final OkHttpClient httpClient = new OkHttpClient();
    WeiboResponse weiboResponse = new WeiboResponse();
    @Operation(summary = "test")
    @RequestMapping("send")
    public String test(String s)
    {
        System.out.println(s);
        return s;
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
                              @RequestParam("text") String text) throws IOException {
        log.info(sessionId);
        log.info(openid);
        MultipartFile multipartFile = file;
        File finallyFile =  File.createTempFile(multipartFile.getOriginalFilename(),null);
        multipartFile.transferTo(finallyFile);
        weiboResponse.sendStatus(accessToken,text,finallyFile);

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






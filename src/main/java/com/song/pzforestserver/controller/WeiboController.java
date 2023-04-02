package com.song.pzforestserver.controller;

import com.song.pzforestserver.entity.WeiboException;
import com.song.pzforestserver.http.WeiboResponse;
import com.song.pzforestserver.util.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.servlet.http.HttpServletResponse;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.io.IOException;
import java.io.PrintWriter;

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
    @Operation(summary = "hometimeline")
    @RequestMapping("hometimeline")
    public Result getHome_timeline(String accessToken, String sinceId, String maxId, int count, int page, int baseApp, int feature, int trimUser)
    {
        weiboResponse.home_timeline(accessToken,sinceId,maxId,count,page,baseApp,feature,trimUser);
        return  new Result(200,"ok",null);
    }

    @Operation(summary = "usertimeline")
    @RequestMapping("usertimeline")
    public Result getUser_timeline(String accessToken, int count, int page, int feature)
    {
        weiboResponse.user_timeline(accessToken,page,count,feature);
        return  new Result(200,"ok",null);
    }

    @Operation(summary = "show")
    @RequestMapping("show")
    public Result show(String accessToken, String id) throws WeiboException, IOException {
        weiboResponse.show(id,accessToken);
        return  new Result(200,"ok",weiboResponse.show(id,accessToken));
    }


    /**
     *
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






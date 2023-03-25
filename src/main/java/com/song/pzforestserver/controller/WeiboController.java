package com.song.pzforestserver.controller;

import com.song.pzforestserver.entity.WeiboException;
import com.song.pzforestserver.http.WeiboResponse;
import com.song.pzforestserver.util.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Tag(name="WeiboController")
@RestController
@RequestMapping("/weibo")
public class WeiboController {

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
     * @param res
     * @throws IOException
     */
    @Operation(summary="createComment")
    @RequestMapping("createComment")
    public void createComment(@RequestParam("accessToken") String accessToken,
                              @RequestParam("id") String id,
                              @RequestParam("comment") String comment,
                              HttpServletResponse res)throws IOException{
        weiboResponse.createComment(accessToken, id, comment, new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
              e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                res.setCharacterEncoding("UTF-8");
                res.setContentType("application/json;charset=UTF-8");
                res.getWriter().write(response.body().string());

            }
        });
    }


}

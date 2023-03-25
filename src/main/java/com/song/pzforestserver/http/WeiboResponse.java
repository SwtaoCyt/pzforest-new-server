package com.song.pzforestserver.http;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.song.pzforestserver.entity.Comments;
import com.song.pzforestserver.entity.Status;
import com.song.pzforestserver.entity.StatusCounts;
import com.song.pzforestserver.entity.WeiboException;
import com.song.pzforestserver.util.Result;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class WeiboResponse {
    public static String BASE_URL="https://api.weibo.com/";
    public static  String accessToken ="2.00Cr7AhCFlne1D50224d9dadjIQdBE";
    OkHttpClient client = new OkHttpClient();


/**OAuth2 授权第二步 access_token 接口，用 code 换取授权 access_token，该步需在服务端完成**/
    public void  getAccessToken(String clientId,String client_secret,String code,String redirect_uri)
    {
        val requestBody = new FormBody.Builder()
                .add("client_id", clientId)
                .add("client_secret", client_secret)
                .add("grant_type","authorization_code")
                .add("code",code)
                .add("redirect_uri",redirect_uri)
                .build();
        val request = new Request.Builder()
                .url(BASE_URL+"oauth2/access_token")
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(@NotNull Call call, @NotNull okhttp3.Response response) throws IOException {
                String str = response.body().string();
                log.info("str");
            }
        });

    }
/**获取当前登录用户及其所关注（授权）用户的最新微博**/
    public  Result home_timeline(String accessToken,String sinceId,String maxId,int count,int page,int baseApp,int feature,int trimUser){

        String url = BASE_URL+"2/statuses/home_timeline.json"
                +"?&access_token="+accessToken
                +"&since_id="+sinceId
                +"&max_id="+maxId
                +"&count="+count
                +"&page="+page
                +"&base_app="+baseApp
                +"&feature="+feature
                +"&trim_user="+trimUser;

        val request = new Request.Builder()
                .url(url)
                .build();

        try (Response response =client.newCall(request).execute()){
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            log.info(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result(200,"ok",null);
    }
    /**
     * 获取某个用户最新发表的微博列表
     * GET
     * **/
    public  Result user_timeline(String accessToken,int page,int count,int feature) {
        String url = BASE_URL+"2/statuses/user_timeline.json"
                +"?&access_token="+accessToken
                +"&count="+count
                +"&page="+page
                +"&base_app=0"
                +"&feature="+feature
                +"&trim_user=1";

        val request = new Request.Builder()
                .url(url)
                .build();

        try (Response response =client.newCall(request).execute()){
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            log.info(response.body().string());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  new Result(200,"ok",null);
    }

    /**
     * https://api.weibo.com/2/statuses/show.json
     * 根据微博ID获取单条微博内容
     */
    public Status show(String id, String access_token) throws IOException, WeiboException {
        OkHttpClient client = new OkHttpClient();
        String baseURL = "https://api.weibo.com/2/statuses/show.json";

        // Build the URL
        String url = baseURL + "?id=" + id + "&access_token=" + access_token;

        // Create the request object
        Request request = new Request.Builder()
                .url(url)
                .build();

        // Execute the request
        Response response = client.newCall(request).execute();

        // Check for errors and parse the response into a Status object
        Status status = null;
        if (!response.isSuccessful()) {
            throw new IOException("Unexpected code " + response);

        }
        String json = response.body().string();
        status =JSONUtil.toBean(json,Status.class);
        return status;
    }


    /**
     *
     * @param accessToken
     * @param ids
     * @return  批量获取指定微博的转发数评论数
     * @throws IOException
     */
    public List<StatusCounts> getStatusCounts(String accessToken,String... ids)throws IOException{
        String joinedIds =String.join(",",ids);
        String url = BASE_URL +"2/statuses/count.json"
                +"?access_token="+accessToken
                +"&ids="+joinedIds;
        val request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        String json = response.body().string();
        JSONArray jsonArray = JSONUtil.parseArray(json);
        List<StatusCounts> statusCounts = new ArrayList<>();
        for (int i=0;i<jsonArray.size();i++){
            StatusCounts statusCount = JSONUtil.toBean(jsonArray.getJSONObject(i),StatusCounts.class);
            statusCounts.add(statusCount);
        }
        return statusCounts;
    }

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
    public  List<Comments> getComments(String accessToken,String id,String sinceId,String maxId,int count,int page) throws IOException,WeiboException
    {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL+"2/comments/show.json").newBuilder();
        urlBuilder.addQueryParameter("access_token",accessToken);
        urlBuilder.addQueryParameter("id",id);
        urlBuilder.addQueryParameter("since_id",sinceId);
        urlBuilder.addQueryParameter("max_id",maxId);
        urlBuilder.addQueryParameter("count", String.valueOf(count));
        urlBuilder.addQueryParameter("page", String.valueOf(page));
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();

        String json = response.body().string();
        JSONObject jsonObject = JSONUtil.parseObj(json);
        JSONArray jsonArray = jsonObject.getJSONArray("comments");
        List<Comments> commentsLIst= jsonArray.toList(Comments.class);

        return  commentsLIst;
    }

     public void createComment(String access_token,String comment,String id,Callback callback) throws IOException
     {
         val requestBody = new FormBody.Builder()
                 .add("access_token", access_token)
                 .add("comment",comment)
                 .add("id",id)
                 .add("rip","106.75.52.202")

                 .build();
         val request = new Request.Builder()
                 .url(BASE_URL+"2/comments/create.json")
                 .post(requestBody)
                 .build();

        client.newCall(request).enqueue(callback);

     }


}

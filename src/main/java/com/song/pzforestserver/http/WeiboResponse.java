package com.song.pzforestserver.http;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.song.pzforestserver.entity.Comments;
import com.song.pzforestserver.entity.Status;
import com.song.pzforestserver.entity.StatusCounts;
import com.song.pzforestserver.entity.WeiboException;
import com.song.pzforestserver.util.Result;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import okhttp3.*;

import okio.ByteString;
import org.apache.ibatis.javassist.bytecode.ByteArray;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.context.request.async.DeferredResult;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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

    public DeferredResult<String> createComment(String access_token,  String id,String comment) throws IOException
    {
        String url = "https://api.weibo.com/2/comments/create.json";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        urlBuilder.addQueryParameter("access_token", accessToken);
        urlBuilder.addQueryParameter("id", String.valueOf(id));
        urlBuilder.addQueryParameter("comment", comment);
        urlBuilder.addQueryParameter("rip","119.129.228.246");
        String fullUrl = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(fullUrl)
                .post(RequestBody.create("", MediaType.get("application/x-www-form-urlencoded")))
                .build();
        DeferredResult<String> deferredResult = new DeferredResult<>();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                deferredResult.setErrorResult(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody = response.body().string();
                if (response.isSuccessful()) {
                    deferredResult.setResult(responseBody);
                } else {
                    deferredResult.setErrorResult(responseBody);
                }
            }
        });
        return  deferredResult;
    }


    public DeferredResult<String> sendStatus(String access_token, String status, File image) throws IOException {
        String url = "https://api.weibo.com/2/statuses/share.json";
        MultipartBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("access_token", access_token)
                .addFormDataPart("status", status + " http://pzforest.com")
                .addFormDataPart("rip", "106.52.75.202")
                .addFormDataPart("pic", image.getPath(), RequestBody.create(MediaType.parse("image/*"), image))
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        DeferredResult<String> deferredResult = new DeferredResult<>();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                deferredResult.setErrorResult(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody = response.body().string();
                if (response.isSuccessful()) {
                    log.info(responseBody);
                    deferredResult.setResult(responseBody);
                } else {
                    deferredResult.setErrorResult(responseBody);
                }
            }
        });
        return deferredResult;
    }


    public DeferredResult<String> reply(String access_token,int cid,  int id,String comment) throws IOException
    {
        String url = "https://api.weibo.com/2/comments/reply.json";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(url).newBuilder();
        urlBuilder.addQueryParameter("access_token", accessToken);
        urlBuilder.addQueryParameter("cid",String.valueOf(cid));
        urlBuilder.addQueryParameter("id", String.valueOf(id));
        urlBuilder.addQueryParameter("comment", comment);
        urlBuilder.addQueryParameter("rip","119.129.228.246");
        String fullUrl = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(fullUrl)
                .post(RequestBody.create("", MediaType.get("application/x-www-form-urlencoded")))
                .build();
        DeferredResult<String> deferredResult = new DeferredResult<>();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                deferredResult.setErrorResult(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody = response.body().string();
                if (response.isSuccessful()) {
                    deferredResult.setResult(responseBody);
                } else {
                    deferredResult.setErrorResult(responseBody);
                }
            }
        });
        return  deferredResult;
    }
}







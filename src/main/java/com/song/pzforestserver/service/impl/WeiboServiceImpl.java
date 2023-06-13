package com.song.pzforestserver.service.impl;
import cn.hutool.core.date.DateTime;
import cn.hutool.json.JSONArray;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URL;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.song.pzforestserver.config.WeiboConfig;
import com.song.pzforestserver.entity.*;
import com.song.pzforestserver.mapper.StatusMapper;
import com.song.pzforestserver.service.*;

import com.song.pzforestserver.util.MinioUploader;
import com.song.pzforestserver.util.MultipartFileDto;
import com.song.pzforestserver.util.Result;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
@Service
public class WeiboServiceImpl implements WeiboService {
    public static String BASE_URL="https://api.weibo.com/";
    public static  String accessToken ="2.00Cr7AhCFlne1D50224d9dadjIQdBE";
    OkHttpClient client = new OkHttpClient();


    @Resource
    CommentsService commentsService;

    @Resource
    StatusMapper statusMapper;
    @Resource
    StatusService statusService;
    @Resource
    public ImageService imageService;
    @Resource
    public MinioUploader minioUploader;

    private  final WeiboConfig weiboConfig;



    @Autowired
    UserService userService;

    @Autowired
    StatusUserService statusUserService;

    @Autowired
    public WeiboServiceImpl(WeiboConfig weiboConfig) {
        this.weiboConfig = weiboConfig;
    }


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

            }
        });

    }
    /**获取当前登录用户及其所关注（授权）用户的最新微博**/
    public Result home_timeline(String accessToken, String sinceId, String maxId, int count, int page, int baseApp, int feature, int trimUser){

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
        status = JSONUtil.toBean(json,Status.class);
        return status;
    }


    /**
     *
     * @param accessToken 用户授权的唯一票据，用于调用微博的开放接口，同时也是第三方应用验证微博用户登录的唯一票据，第三方应用应该对该票据进行校验，校验方法为调用 oauth2/get_token_info 接口，对比返回的授权信息中的APPKEY是否正确一致，然后用 access_token 与自己应用内的用户建立唯一影射关系，来识别登录状态，不能只是简单的使用本返回值里的UID字段来做登录识别。
     * @param ids
     * @return  批量获取指定微博的转发数评论数
     * @throws IOException
     */
    public List<StatusCounts> getStatusCounts(String accessToken, String... ids)throws IOException{
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
     * @param accessToken 用户授权的唯一票据，用于调用微博的开放接口，同时也是第三方应用验证微博用户登录的唯一票据，第三方应用应该对该票据进行校验，校验方法为调用 oauth2/get_token_info 接口，对比返回的授权信息中的APPKEY是否正确一致，然后用 access_token 与自己应用内的用户建立唯一影射关系，来识别登录状态，不能只是简单的使用本返回值里的UID字段来做登录识别。
     * @param id
     * @param sinceId
     * @param maxId
     * @param count
     * @param page
     * @return 根据微博ID返回某条微博的评论列表
     * @throws IOException
     * @throws WeiboException
     */
    public  List<Comments> getComments(String accessToken, String id, String sinceId, String maxId, int count, int page) throws IOException,WeiboException
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

    /**
     *
     * @param accessToken 用户授权的唯一票据，用于调用微博的开放接口，同时也是第三方应用验证微博用户登录的唯一票据，第三方应用应该对该票据进行校验，校验方法为调用 oauth2/get_token_info 接口，对比返回的授权信息中的APPKEY是否正确一致，然后用 access_token 与自己应用内的用户建立唯一影射关系，来识别登录状态，不能只是简单的使用本返回值里的UID字段来做登录识别。
     * @param id 微博Id
     * @return 根据微博ID返回某条微博的评论列表
     * @throws IOException
     * @throws WeiboException
     */
    public  List<Comments> getComments(String accessToken, String id) throws IOException,WeiboException
    {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(BASE_URL+"2/comments/show.json").newBuilder();
        urlBuilder.addQueryParameter("access_token",accessToken);
        urlBuilder.addQueryParameter("id",id);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
//        log.info(response.body().string());
        String json = response.body().string();
        log.info(json);
        JSONObject jsonObject = JSONUtil.parseObj(json);
        JSONArray jsonArray = jsonObject.getJSONArray("comments");
        List<Comments> commentsLIst= jsonArray.toList(Comments.class);
        for (Comments temp:commentsLIst
             ) {
            temp.setStatusId(Long.valueOf(id));
            commentsService.saveOrUpdate(temp);
        }
        return  commentsLIst;
    }

    public DeferredResult<String> createComment(String access_token, String id, String comment) throws IOException
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
                log.info(responseBody);
                if (response.isSuccessful()) {
                    deferredResult.setResult(responseBody);
                } else {
                    deferredResult.setErrorResult(responseBody);
                }
            }
        });
        return  deferredResult;
    }

    /**
     * 发送纯文字的微博
     * @param access_token 用户授权的唯一票据，用于调用微博的开放接口，同时也是第三方应用验证微博用户登录的唯一票据，第三方应用应该对该票据进行校验，校验方法为调用 oauth2/get_token_info 接口，对比返回的授权信息中的APPKEY是否正确一致，然后用 access_token 与自己应用内的用户建立唯一影射关系，来识别登录状态，不能只是简单的使用本返回值里的UID字段来做登录识别。
     * @param status
     * @param callback
     */
    public void sendStatus(String access_token, String status,Integer mode, Consumer<String> callback) {
        String url = "https://api.weibo.com/2/statuses/share.json";
        FormBody requestBody = new FormBody.Builder()
                .add("status", status + " http://pzforest.com")
                .add("access_token", access_token)
                .add("rip", "106.52.75.202")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.accept(null); // 失败时传递 null
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody = response.body().string();
                if (response.isSuccessful()) {
                    log.info(responseBody);

                    // 处理成功的逻辑
                    Status temp = JSONUtil.toBean(responseBody, Status.class);
                    JSONObject res = JSONUtil.parseObj(responseBody);
                    String userStr = res.getStr("user");
                    JSONObject userObj = JSONUtil.parseObj(userStr);
                    log.info("id在这" + userObj.get("id").toString());
                    temp.setUserId(userObj.get("id").toString());
                    temp.setCreatedTime(DateTime.now());
                    statusService.saveOrUpdate(temp);
                } else {
                    // 处理失败的逻辑
                    log.error(responseBody);
                }

                callback.accept(responseBody); // 将结果传递给回调函数
            }
        });
    }

    /**
     * 发送纯文字的微博
     * @param access_token 用户授权的唯一票据，用于调用微博的开放接口，同时也是第三方应用验证微博用户登录的唯一票据，第三方应用应该对该票据进行校验，校验方法为调用 oauth2/get_token_info 接口，对比返回的授权信息中的APPKEY是否正确一致，然后用 access_token 与自己应用内的用户建立唯一影射关系，来识别登录状态，不能只是简单的使用本返回值里的UID字段来做登录识别。
     * @param status
     * @param callback
     */
    public void sendStatus(String access_token, String status,Integer mode,String fromStatus, Consumer<String> callback) {
        log.info("纯文字");
        String url = "https://api.weibo.com/2/statuses/share.json";
        FormBody requestBody = new FormBody.Builder()
                .add("status", status + " http://pzforest.com")
                .add("access_token", access_token)
                .add("rip", "106.52.75.202")
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.accept(null); // 失败时传递 null
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody = response.body().string();
                if (response.isSuccessful()) {

                    // 处理成功的逻辑
                    Status temp = JSONUtil.toBean(responseBody, Status.class);
                    JSONObject res = JSONUtil.parseObj(responseBody);
                    String userStr = res.getStr("user");
                    JSONObject userObj = JSONUtil.parseObj(userStr);
                    log.info("id在这" + userObj.get("id").toString());
                    temp.setUserId(userObj.get("id").toString());
                    temp.setMid(fromStatus);
                    temp.setCreatedTime(DateTime.now());

                    statusService.saveOrUpdate(temp);
                } else {
                    // 处理失败的逻辑
                    log.error(responseBody);
                }

                callback.accept(responseBody); // 将结果传递给回调函数
            }
        });
    }



    /**
     * 带图片的微博发送方法
     * @param access_token 用户授权的唯一票据，用于调用微博的开放接口，同时也是第三方应用验证微博用户登录的唯一票据，第三方应用应该对该票据进行校验，校验方法为调用 oauth2/get_token_info 接口，对比返回的授权信息中的APPKEY是否正确一致，然后用 access_token 与自己应用内的用户建立唯一影射关系，来识别登录状态，不能只是简单的使用本返回值里的UID字段来做登录识别。
     * @param status
     * @param image
     * @param mode
     * @param callback
     * @throws IOException
     */
    public void sendStatus(String access_token, String status, MultipartFile image, Integer mode,String fromStatus, Consumer<String> callback) throws IOException {
        String url = "https://api.weibo.com/2/statuses/share.json";
//        log.info("上传图片成功！url如下"+image.getResource());
        Image   tempImage = minioUploader.uploadImage(image);
        log.info("上传成功！url:"+tempImage.getUrl());
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("access_token", access_token)
                .addFormDataPart("rip", "106.52.75.202")
                .addFormDataPart("pic", image.getOriginalFilename(), RequestBody.create(MediaType.parse("image/*"), image.getBytes()));

        if (mode == 1) {
            log.info("mode=1,不发送文字");
            builder.addFormDataPart("status", "http://pzforest.com");
        } else {
            log.info("mode=0,发送文字");
            builder.addFormDataPart("status", status + " http://pzforest.com");
        }

        MultipartBody requestBody = builder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.accept(null); // 失败时传递 null
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody = response.body().string();
                if (response.isSuccessful()) {
                    log.info(responseBody);

                    // 处理成功的逻辑
                    Status temp = JSONUtil.toBean(responseBody, Status.class);
                    JSONObject res = JSONUtil.parseObj(responseBody);
                    String userStr = res.getStr("user");
                    JSONObject userObj = JSONUtil.parseObj(userStr);
                    temp.setUserId(userObj.get("id").toString());
                    temp.setCreatedTime(DateTime.now());
                    temp.setText(status);
                    tempImage.setStatus(temp.getIdstr());
                    temp.setCreatedTime(DateTime.now());
                    imageService.saveOrUpdate(tempImage);
                    temp.setMid(fromStatus);
                    statusService.saveOrUpdate(temp);
                } else {
                    // 处理失败的逻辑
                    log.error(responseBody);
                }
                callback.accept(responseBody); // 将结果传递给回调函数
            }
        });
    }


    /**
     * 带图片的微博发送方法
     * @param access_token 用户授权的唯一票据，用于调用微博的开放接口，同时也是第三方应用验证微博用户登录的唯一票据，第三方应用应该对该票据进行校验，校验方法为调用 oauth2/get_token_info 接口，对比返回的授权信息中的APPKEY是否正确一致，然后用 access_token 与自己应用内的用户建立唯一影射关系，来识别登录状态，不能只是简单的使用本返回值里的UID字段来做登录识别。
     * @param status
     * @param image
     * @param mode
     * @param callback
     * @throws IOException
     */
    public void sendStatus(String access_token, String status, MultipartFile image, Integer mode, Consumer<String> callback) throws IOException {
        String url = "https://api.weibo.com/2/statuses/share.json";
//        log.info("上传图片成功！url如下"+image.getResource());
     Image   tempImage = minioUploader.uploadImage(image);
        log.info("上传成功！url:"+tempImage.getUrl());
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("access_token", access_token)
                .addFormDataPart("rip", "106.52.75.202")
                .addFormDataPart("pic", image.getOriginalFilename(), RequestBody.create(MediaType.parse("image/*"), image.getBytes()));

        if (mode == 1) {
            builder.addFormDataPart("status", "http://pzforest.com");
        } else {
            builder.addFormDataPart("status", status + " http://pzforest.com");
        }

        MultipartBody requestBody = builder.build();

        Request request = new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.accept(null); // 失败时传递 null
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseBody = response.body().string();
                if (response.isSuccessful()) {
                    log.info(responseBody);

                    // 处理成功的逻辑
                    Status temp = JSONUtil.toBean(responseBody, Status.class);
                    JSONObject res = JSONUtil.parseObj(responseBody);
                    String userStr = res.getStr("user");
                    JSONObject userObj = JSONUtil.parseObj(userStr);
                    temp.setUserId(userObj.get("id").toString());
                    temp.setCreatedTime(DateTime.now());
                    temp.setText(status);
                    tempImage.setStatus(temp.getIdstr());
                    temp.setCreatedTime(DateTime.now());
                    imageService.saveOrUpdate(tempImage);

                    statusService.saveOrUpdate(temp);
                } else {
                    // 处理失败的逻辑
                    log.error(responseBody);
                }
                callback.accept(responseBody); // 将结果传递给回调函数
            }
        });
    }


    @Override
    public void handleAt() {
        String accessTokens = WeiboConfig.getAccessToken();

        // 使用accessTokens进行其他操作

         String url = "https://api.weibo.com/2/statuses/mentions.json?access_token="+accessTokens;
        val request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Call call =client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
               log.debug(e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                JSONObject res = JSONUtil.parseObj(response.body().string());

               val statuses = res.getJSONArray("statuses");
                val size= statuses.size();
                ArrayList<JSONObject>  status = new   ArrayList<JSONObject>() ;

                for(int i =0;i<size-1;i++)
                {
                    File file = null;
                    var temp = statuses.getJSONObject(i);
                    var text = temp.getStr("text");
                    if(text.contains("转发微博"))
                    {
                        continue;
                    }
                    text =text.replace("培正森林","").replace("http://pzforest.com","").replace("@","");
                    var weiboid =temp.get("idstr").toString();
                    var user = temp.getJSONObject("user");
                    var userid = user.getStr("id");
                    if(ObjectUtils.isEmpty(statusService.selectByWeiboId(weiboid)) && text.length()<140)
                    {
                        val pic =temp.getStr("original_pic");
                        //如果有图片的话获取图片
                        if(!ObjectUtils.isEmpty(pic))
                        {
                            log.info("has Image"+temp.getStr("original_pic")) ;
                            val originalFileName=temp.getStr("original_pic");
                           val imageStream = new URL(originalFileName).openStream();
                            // 读取图片数据
                            val imageData = imageStream.readAllBytes();
                            // 获取文件类型，以最后一个`.`为标识
                            val type = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
                            // 创建临时文件
                            val tempFile = File.createTempFile("temp", "." + type);
                            tempFile.deleteOnExit();
                            // 创建临时文件
                            val tFile = File.createTempFile("temp", "." + type);
                            tFile.deleteOnExit();
                            // 将图片数据写入临时文件
                            FileOutputStream fileOutputStream = new FileOutputStream(tempFile);
                            fileOutputStream.write(imageData);
                            fileOutputStream.close();
                            //获取文件类型，以最后一个`.`为标识
                            val contentType = originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
                           val  finalType =  "image/"+contentType;
                            // 将临时文件转换为 MultipartFile 对象
                            MultipartFileDto multipartFile = new MultipartFileDto(originalFileName, originalFileName, finalType, new FileInputStream(tempFile));
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            String finalText = text;
                            sendStatus(WeiboConfig.getAccessToken(),text,multipartFile,0,weiboid, result->{
                                String id = JSONUtil.parseObj(result).getStr("id");
                                statusUserService.AddStatusUser(weiboid,"0",id);
                                log.info("新at信息，内容为:"+ finalText +"用户微博id:"+userid+",微博id:"+weiboid);
                            });

                        }
                        else
                        {
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            String finalText1 = text;
                            sendStatus(WeiboConfig.getAccessToken(),text,1,weiboid, result->{
                                String id = JSONUtil.parseObj(result).getStr("id");
                                statusUserService.AddStatusUser(weiboid,"0",id);
                                log.info("新at信息，内容为:"+ finalText1 +"用户微博id:"+userid+",微博id:"+weiboid);
                            });
                        }

                    }

                }
            }
        });
    }


    @Override
    public List<Status> SelectStatusList(String text, int offset, int limit) throws IOException {
        List<Status> statuses = statusMapper.SelectStatusImageList(text,offset,limit);
        List<String> ids = new ArrayList<String>();
        for(int i=0;i<statuses.size();i++)
        {
            Status temp =  statuses.get(i);
            ids.add(temp.getId());
        }
        String[] id= ids.toArray(new String[ids.size()]);
        List<StatusCounts> statusCounts = this.getStatusCounts(weiboConfig.getAccessToken(),id);
        for(int i=0;i<statuses.size();i++)
        {
            Status temp =  statuses.get(i);
            temp.setCommentsCount(statusCounts.get(i).getComments());
            statusService.saveOrUpdate(temp);
        }
        return statuses;
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



    /**
     * 投稿并且保存内容到数据库
     * @param access_token 用户授权的唯一票据，用于调用微博的开放接口，同时也是第三方应用验证微博用户登录的唯一票据，第三方应用应该对该票据进行校验，校验方法为调用 oauth2/get_token_info 接口，对比返回的授权信息中的APPKEY是否正确一致，然后用 access_token 与自己应用内的用户建立唯一影射关系，来识别登录状态，不能只是简单的使用本返回值里的UID字段来做登录识别。
     * @param status 文本内容
     * @param image 图片
     * @param openId 投稿人的微信唯一标识
     * @param mode 当mode为1的时候，意味着文本内容已经并入图片或者没有文本内容，此时投稿不会投稿文字。仅投稿图片，但是仍然会将文字保存到数据库。
     * @return DeferredResult<String>
     * @throws IOException
     */
    public  DeferredResult<String> contributeAndSave(String access_token,String status,MultipartFile image,String openId,Integer mode) throws IOException {
        final  Integer TEXTINIMG =1;
        final Integer INEEDTEXT = 0;
        User curUser=   userService.selectUserByUserId(openId);
        DeferredResult<String> deferredResult = new DeferredResult<>();
        //该用户没有注册
        if (curUser==null)
        {
            return  deferredResult;
        }
        //如果有图片存在的话，执行如下逻辑
        if (!ObjectUtils.isEmpty(image)) {
            this.sendStatus(access_token, status, image,mode, result -> {
                //获取这条微博的id
                String id = JSONUtil.parseObj(result).getStr("id");

                statusUserService.AddStatusUser(openId,"1",id);


                deferredResult.setResult(result);
            });
        }
        //如果没有图片的话执行下面的逻辑
        else {
            this.sendStatus(access_token, status,mode, result -> {
                String id = JSONUtil.parseObj(result).getStr("id");
                // 保存用户发送微博的信息！！
                statusUserService.AddStatusUser(openId,"1",id);
                deferredResult.setResult(result);
            });
        }

        return  deferredResult;
    }

}

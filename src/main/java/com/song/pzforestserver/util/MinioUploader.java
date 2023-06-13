package com.song.pzforestserver.util;

import cn.hutool.core.date.DateTime;
import com.song.pzforestserver.entity.Image;
import com.song.pzforestserver.service.ImageService;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Slf4j
@Component
@ConfigurationProperties(prefix = "minio")
public class MinioUploader {

    @Autowired
ImageService imageService;
    private String url;
    private String accessKey;
    private String secretKey;
    private String api;
    private String path;
    private String bucketName;

    public String getBucketName() {
        return bucketName;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }
// 添加setter方法用于注入属性值

    public void setUrl(String url) {
        this.url = url;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public String getApi() {
        return api;
    }

    public String getPath() {
        return path;
    }

    public Image uploadImage(MultipartFile file) {
        try {
            log.info(String.valueOf(file.getSize()));
            // 创建 Minio 客户端
            MinioClient minioClient = MinioClient.builder()
                    .endpoint(url)
                    .credentials(accessKey, secretKey)
                    .build();
//            log.info(file.getInputStream().toString());
            // 生成上传的对象名称
            String objectName = generateObjectName(file.getOriginalFilename());

            // 使用 PutObjectArgs 将文件上传到 Minio
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build());

            // 拼接文件的访问 URL
            String imageUrl = url + "/" + bucketName + "/" + objectName;
            Image image = new Image();
            image.setUrl(imageUrl);
            image.setUploadTime(DateTime.now());
            image.setFilename(objectName);
            return imageService.insertImageInfo(objectName,imageUrl,DateTime.now());
        } catch (Exception e) {
            // 处理上传异常
            e.printStackTrace();
            return null;
        }
    }

    private String generateObjectName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String timestamp = now.format(formatter);
        return "image_" + timestamp + "_" + uuid + ".jpg";
    }

    private String buildImageUrl(String objectName) {
        return url + "/" + path + "/" + objectName;
    }

    private void insertImageInfo(String objectName, String filePath, String imageUrl) {
        // 在这里执行插入图片信息到数据库的操作
        // ...
    }
}

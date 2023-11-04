package com.song.pzforestserver.service;

import com.song.pzforestserver.entity.Image;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;

/**
* @author Swtao
* @description 针对表【image】的数据库操作Service
* @createDate 2023-06-08 21:28:27
*/
public interface ImageService extends IService<Image> {

    /**
     * 往数据库中添加图片信息
     * @param objectName 文件名
     * @param uploadTime 上传时间
     * @param imageUrl 文件链接
     */
    public Image insertImageInfo(String objectName, String imageUrl, Date uploadTime);

}

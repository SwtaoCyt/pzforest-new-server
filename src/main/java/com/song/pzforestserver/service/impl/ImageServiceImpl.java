package com.song.pzforestserver.service.impl;

import cn.hutool.core.date.DateTime;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.song.pzforestserver.entity.Image;
import com.song.pzforestserver.service.ImageService;
import com.song.pzforestserver.mapper.ImageMapper;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
* @author Swtao
* @description 针对表【image】的数据库操作Service实现
* @createDate 2023-06-08 21:28:27
*/
@Service
public class ImageServiceImpl extends ServiceImpl<ImageMapper, Image>
    implements ImageService{


    @Override
    public Image insertImageInfo(String objectName, String imageUrl, Date uploadTime) {
        Image image =new Image();
        image.setFilename(objectName);
        image.setUrl(imageUrl);
        image.setUploadTime(DateTime.now());
        return  image;

    }
}





package com.song.pzforestserver.util;

import com.song.pzforestserver.util.MultipartFileDto;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;
import org.im4java.process.ProcessStarter;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;

public class ImageProcessor {



    public static MultipartFile compressImage(MultipartFile sourceImage) throws IOException, InterruptedException, IM4JavaException {
        double maxSizeMB = 5.0; // 目标最大大小（MB）
        double quality = 90; // 初始图像质量

        // 1. 创建临时文件来保存源图像内容
        File sourceTempFile = File.createTempFile("source", ".jpg");
        sourceImage.transferTo(sourceTempFile);

        // 2. 计算源图像大小
        long sourceSize = sourceTempFile.length();

        if (sourceSize <= maxSizeMB * 1024 * 1024) {
            // 源图像大小已在目标范围内，不进行进一步压缩，直接返回
            return sourceImage;
        }

        while (true) {
            // 3. 创建临时文件来保存压缩后的图像内容
            File compressedTempFile = File.createTempFile("compressed", ".jpg");

            // 4. 设置 GraphicsMagick 压缩参数
            IMOperation operation = new IMOperation();
            operation.addImage(sourceTempFile.getAbsolutePath());
            operation.quality(quality); // 设置图像质量
            operation.addImage(compressedTempFile.getAbsolutePath());

            // 5. 使用 ConvertCmd 执行图像压缩
            ConvertCmd cmd = new ConvertCmd();
            cmd.run(operation);

            // 6. 计算压缩后的图像大小
            long compressedSize = compressedTempFile.length();

            if (compressedSize <= maxSizeMB * 1024 * 1024) {
                // 7. 图像大小在目标范围内，创建压缩后的 MultipartFile 并返回
                byte[] compressedBytes = java.nio.file.Files.readAllBytes(compressedTempFile.toPath());
                return new MultipartFileDto(
                        sourceImage.getName(),
                        sourceImage.getOriginalFilename(),
                        sourceImage.getContentType(),
                        compressedBytes
                );
            }

            // 8. 图像大小超出目标范围，降低图像质量继续压缩
            quality -= 10;
            if (quality <= 0) {
                // 如果质量已降至最低，无法满足目标大小，返回空值或抛出异常
                throw new RuntimeException("无法满足目标大小要求");
            }
        }
    }
}

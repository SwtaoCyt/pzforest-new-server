package com.song.pzforestserver.service;

import com.song.pzforestserver.entity.SensitiveWord;
import com.baomidou.mybatisplus.extension.service.IService;
import com.song.pzforestserver.util.SensitiveWordFilter;

import java.util.List;

/**
* @author Swtao
* @description 针对表【sensitive_word】的数据库操作Service
* @createDate 2023-06-17 14:38:05
*/
public interface SensitiveWordService extends IService<SensitiveWord> {
    public List<SensitiveWord> getAllWord();

    public SensitiveWordFilter check ();
}

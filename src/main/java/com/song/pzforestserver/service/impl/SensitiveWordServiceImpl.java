package com.song.pzforestserver.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.song.pzforestserver.entity.SensitiveWord;
import com.song.pzforestserver.service.SensitiveWordService;
import com.song.pzforestserver.mapper.SensitiveWordMapper;
import com.song.pzforestserver.util.CacheUtils;
import com.song.pzforestserver.util.SensitiveWordFilter;
import com.song.pzforestserver.util.SensitiveWordsParser;
import jakarta.annotation.Resource;
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author Swtao
* @description 针对表【sensitive_word】的数据库操作Service实现
* @createDate 2023-06-17 14:38:05
*/
@Service
public class SensitiveWordServiceImpl extends ServiceImpl<SensitiveWordMapper, SensitiveWord>
    implements SensitiveWordService{

    @Resource
    SensitiveWordMapper sensitiveWordMapper;

    @Resource
    CacheUtils cacheUtils;
    private static final String SENSITIVE_WORDS_KEY = "sensitive_words";
    @Override
    public List<SensitiveWord> getAllWord() {
        return sensitiveWordMapper.getAllSenWord();
    }

    @Override
    public SensitiveWordFilter check() {


        String sensitiveWordsJson = cacheUtils.get(SENSITIVE_WORDS_KEY);
        List<SensitiveWord> sensitiveWords = null;
        if (sensitiveWordsJson != null) {
            // 如果 Redis 中存在敏感词列表，则解析并返回
            sensitiveWords = SensitiveWordsParser.parseSensitiveWordsJson(sensitiveWordsJson);
        }
        else {
            sensitiveWords =getAllWord();
            cacheUtils.add(SENSITIVE_WORDS_KEY,SensitiveWordsParser.convertToSensitiveWordsJson(sensitiveWords));
        }

        SensitiveWordFilter sensitiveWordFilter = new SensitiveWordFilter(sensitiveWords);
    return sensitiveWordFilter;
    }
}





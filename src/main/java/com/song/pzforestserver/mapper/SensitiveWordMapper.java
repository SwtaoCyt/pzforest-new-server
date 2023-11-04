package com.song.pzforestserver.mapper;

import com.song.pzforestserver.entity.SensitiveWord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
* @author Swtao
* @description 针对表【sensitive_word】的数据库操作Mapper
* @createDate 2023-06-17 14:38:05
* @Entity com.song.pzforestserver.entity.SensitiveWord
*/
@Mapper
public interface SensitiveWordMapper extends BaseMapper<SensitiveWord> {
    public List<SensitiveWord> getAllSenWord();
}





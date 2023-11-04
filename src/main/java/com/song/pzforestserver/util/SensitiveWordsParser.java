package com.song.pzforestserver.util;

import com.song.pzforestserver.entity.SensitiveWord;
import com.song.pzforestserver.util.CacheUtils;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.util.ArrayList;
import java.util.List;

public class SensitiveWordsParser {
    public static List<SensitiveWord> parseSensitiveWordsJson(String json) {
        List<SensitiveWord> sensitiveWords = new ArrayList<>();

        JSONArray jsonArray = JSONUtil.parseArray(json);
        for (Object obj : jsonArray) {
            if (obj instanceof JSONObject) {
                JSONObject jsonObj = (JSONObject) obj;
                SensitiveWord sensitiveWord = new SensitiveWord();

                sensitiveWord.setId(jsonObj.getInt("id"));
                sensitiveWord.setWord(jsonObj.getStr("word"));
                sensitiveWord.setLevel(jsonObj.getInt("level"));
                sensitiveWord.setReplace(jsonObj.getStr("replace"));

                sensitiveWords.add(sensitiveWord);
            }
        }

        return sensitiveWords;
    }
    public static String convertToSensitiveWordsJson(List<SensitiveWord> sensitiveWords) {
        JSONArray jsonArray = JSONUtil.createArray();

        for (SensitiveWord sensitiveWord : sensitiveWords) {
            jsonArray.add(JSONUtil.parseObj(sensitiveWord));
        }

        return jsonArray.toString();
    }
}

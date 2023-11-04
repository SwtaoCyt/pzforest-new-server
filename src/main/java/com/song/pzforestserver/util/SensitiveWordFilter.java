package com.song.pzforestserver.util;

import com.song.pzforestserver.entity.SensitiveWord;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class SensitiveWordFilter {
    private TrieNode root;
    private  String word = null;
    List<SensitiveWord> sensitiveWords=null;
    public SensitiveWordFilter(List<SensitiveWord> sensitiveWordsList) {
        root = new TrieNode();
        sensitiveWords =sensitiveWordsList;
        buildTrie(sensitiveWordsList);
    }

    private static class TrieNode {
        private Map<Character, TrieNode> children;
        private boolean isEnd;
        private String replaceContent;
        private int level;
        private int wordId;
        private String word;
        public TrieNode() {
            children = new HashMap<>();
            isEnd = false;
            replaceContent = null;
            level = 0;
            wordId = -1;

        }

        public Map<Character, TrieNode> getChildren() {
            return children;
        }

        public boolean isEnd() {
            return isEnd;
        }

        public void setEnd(boolean end) {
            isEnd = end;
        }

        public String getReplaceContent() {
            return replaceContent;
        }

        public void setReplaceContent(String replaceContent) {
            this.replaceContent = replaceContent;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }

        public int getWordId() {
            return wordId;
        }

        public void setWordId(int wordId) {
            this.wordId = wordId;
        }

        public String getWord() {
            return word;
        }

        public void setWord(String word) {
            this.word = word;
        }

        @Override
        public String toString() {
            return "TrieNode{" +
                    "children=" + children +
                    ", isEnd=" + isEnd +
                    ", replaceContent='" + replaceContent + '\'' +
                    ", level=" + level +
                    ", wordId=" + wordId +
                    ", word='" + word + '\'' +
                    '}';
        }
    }

    private void buildTrie(List<SensitiveWord> sensitiveWordsList) {
        for (SensitiveWord sensitiveWord : sensitiveWordsList) {
            String word = sensitiveWord.getWord();
            String replaceContent = sensitiveWord.getReplace();
            int level = sensitiveWord.getLevel();
            int wordId = sensitiveWord.getId();

            TrieNode currentNode = root;
            for (char c : word.toCharArray()) {
                currentNode.getChildren().computeIfAbsent(c, key -> new TrieNode());
                currentNode = currentNode.getChildren().get(c);
            }
            currentNode.setEnd(true);
            currentNode.setReplaceContent(replaceContent);
            currentNode.setLevel(level);
            currentNode.setWordId(wordId);
            currentNode.setWord(word);
        }
    }

    public boolean containsSensitiveWords(String content) {
        int currentPosition = 0;
        TrieNode currentNode = root;

        while (currentPosition < content.length()) {
            char c = content.charAt(currentPosition);

            if (currentNode.getChildren().containsKey(c)) {
                currentNode = currentNode.getChildren().get(c);

                if (currentNode.isEnd()) {
                    // 发现敏感词
                    return true;
                }
            } else {
                currentNode = root;
            }

            currentPosition++;
        }

        return false;
    }

    public Map<Integer, Integer> getTriggeredWordsAndLevels(String content) {
        Map<Integer, Integer> triggeredWordsAndLevels = new HashMap<>();
        int currentPosition = 0;
        TrieNode currentNode = root;

        while (currentPosition < content.length()) {
            char c = content.charAt(currentPosition);

            if (currentNode.getChildren().containsKey(c)) {
                currentNode = currentNode.getChildren().get(c);

                if (currentNode.isEnd()) {
                    // 发现敏感词，记录敏感词ID和等级
                    int wordId = currentNode.getWordId();
                    int level = currentNode.getLevel();
                    triggeredWordsAndLevels.put(wordId, level);
                }
            } else {
                currentNode = root;
            }

            currentPosition++;
        }

        return triggeredWordsAndLevels;
    }

    public String filterSensitiveWords(String content) {
        StringBuilder filteredContent = new StringBuilder(content);

        // 处理等级2的敏感词替换
        for (SensitiveWord sensitiveWord : sensitiveWords) {
            if (sensitiveWord.getLevel() == 2) {
                String word = sensitiveWord.getWord();
                String replaceContent = sensitiveWord.getReplace();
                int index = filteredContent.indexOf(word);
                while (index != -1) {
                    filteredContent.replace(index, index + word.length(), replaceContent);
                    index = filteredContent.indexOf(word, index + replaceContent.length());
                }
            }
        }

        // 处理等级1的敏感词检查
        TrieNode currentNode = root;
        int currentPosition = 0;
        int start = 0;
        while (currentPosition < filteredContent.length()) {
            char c = filteredContent.charAt(currentPosition);

            if (currentNode.getChildren().containsKey(c)) {
                currentNode = currentNode.getChildren().get(c);

                if (currentNode.isEnd()) {
                    // 发现等级1的敏感词
                    filteredContent.replace(start, currentPosition + 1, StringUtils.repeat("*", currentPosition - start + 1));
                    currentNode = root;
                    currentPosition = start;
                }
            } else {
                currentNode = root;
                start = currentPosition + 1;
            }

            currentPosition++;
        }

        return filteredContent.toString();
    }



}

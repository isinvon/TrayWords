package com.sinvon.traywords.utils;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClient;

import java.text.MessageFormat;

/**
 * 百词斩API工具类
 * @author sinvon
 * @since 2024年8月15日01:00:17
 */
@Slf4j
public class BaiCiZhanAPIUtils {
    /**
     * 获取随机单词
     *
     * @return
     */
    public static String getRandomWords() {
        String url = "https://cdn.jsdelivr.net/gh/lyc8503/baicizhan-word-meaning-API/data/list.json";
        WebClient webClient = WebClient.create();
        String response = webClient.get().uri(url).retrieve().bodyToMono(String.class).block();

        // 解析 JSON 并获取 list 数组
        JSONObject jsonObject = new JSONObject(response);
        // 单词列表
        JSONArray list = jsonObject.getJSONArray("list");
        // 单词总数
        Integer total = jsonObject.getInt("total");
        // 随机获取一个单词
        int randomIndex = RandomUtil.randomInt(total);
        String words = list.get(randomIndex).toString().replace("[", "").replace("]", "");
        // 返回随机单词的相关信息
        return words;
    }

    /**
     * 通过索引获取单词
     *
     * @param index
     * @return
     */
    public static String getWordsByIndex(String index) {
        String url = "https://cdn.jsdelivr.net/gh/lyc8503/baicizhan-word-meaning-API/data/list.json";
        WebClient webClient = WebClient.create();
        String response = webClient.get().uri(url).retrieve().bodyToMono(String.class).block();

        // String 转化为 int
        int indexInt = Integer.parseInt(index);

        // 解析 JSON 并获取 list 数组
        JSONObject jsonObject = new JSONObject(response);
        // 单词列表
        JSONArray list = jsonObject.getJSONArray("list");
        // 返回一个指定下标的单词
        return list.get(indexInt).toString().replace("[", "").replace("]", "");
    }

    /**
     * 通过单词获取单词解释
     *
     * @param words
     * @return
     */
    public static String getWordsMeaning(String words) {
        // 使用MessageFormat处理占位
        String url = MessageFormat.format("https://cdn.jsdelivr.net/gh/lyc8503/baicizhan-word-meaning-API/data/words/{0}.json", words);
        WebClient webClient = WebClient.create();
        String response = webClient.get().uri(url).retrieve().bodyToMono(String.class).block();

        // 解析 JSON 并获取 list 数组
        JSONObject jsonObject = new JSONObject(response);

        // 获取单词中文意思
        String meanCn = jsonObject.getStr("mean_cn");
        // 获取英文解释
        String meanEn = jsonObject.getStr("mean_en");
        // 获取单词造句
        String sentence = jsonObject.getStr("sentence");
        // 获取单词造句的中文
        String sentenceTrans = jsonObject.getStr("sentence_trans");
        String sentencePhrase = jsonObject.getStr("sentence_phrase");
        // 获取词源
        String wordEtyma = jsonObject.getStr("word_etyma");
        // 获取词根
        JSONObject clozeData = jsonObject.getJSONObject("cloze_data");

        // 返回单词中文意思
        return meanCn;
    }

    /**
     * 获取随机 单词+释义
     * @return
     */
    public static String getWords() {
        String randomWords = getRandomWords().replace(" ", "");
        String wordsMeaning = getWordsMeaning(randomWords).replace(" ", "");
        // log.info("随机单词: {}, 释义: {}", randomWords, wordsMeaning);
        return MessageFormat.format("{0}：{1}", randomWords, wordsMeaning);
    }
}

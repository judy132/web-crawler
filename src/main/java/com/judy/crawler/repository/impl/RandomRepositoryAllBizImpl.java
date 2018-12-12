package com.judy.crawler.repository.impl;

import com.judy.crawler.repository.IUrlRepositoryBiz;
import com.judy.crawler.utils.CrawlerUtils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Description: 全网爬虫单机版 <br/>
 * Copyright (c),2018,judyxia<br/>
 * This program is protected by copyright laws,<br/>
 * Date:2018-12-12
 *
 * @author judy
 * @version:1.0
 */
public class RandomRepositoryAllBizImpl implements IUrlRepositoryBiz {
    //url 仓库
    private Map<String, Map<String, ConcurrentLinkedQueue<String>>> repository;
    private Random random;


    public RandomRepositoryAllBizImpl() {
        repository=new LinkedHashMap<>();
        random=new Random();
    }

    @Override
    public void pushHigher(String url) {
        commonPush(url, "higher");
    }

    @Override
    public void pushLower(String url) {

    }

    @Override
    public void pushOther(String url) {

    }

    @Override
    public String poll() {
        return null;
    }

    /**
     * 共通push处理
     * @param url
     * @param key lower，higher，other
     */
    private void commonPush(String url, String key) {
        String topDomain = CrawlerUtils.getTopDomain(url);

//        Map<String, ConcurrentLinkedQueue<String>> nowPlatfromAllUrls = repository.getOrDefault(topDomain, new LinkedHashMap<>());
//        repository.put(topDomain,nowPlatfromAllUrls);

    }


}

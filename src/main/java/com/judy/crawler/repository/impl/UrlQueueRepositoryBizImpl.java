package com.judy.crawler.repository.impl;

import com.judy.crawler.repository.IUrlRepositoryBiz;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Description: url仓库模块业务逻辑层接口实现类,单机版爬虫使用链表来存储url <br/>
 * Copyright (c),2018,judyxia<br/>
 * This program is protected by copyright laws,<br/>
 * Date:2018-12-08
 *
 * @author judy
 * @version:1.0
 */
public class UrlQueueRepositoryBizImpl implements IUrlRepositoryBiz {
    private ConcurrentLinkedQueue<String> higherLevel;
    private ConcurrentLinkedQueue<String> lowerLevel;

    /**
     *
     */
    private ConcurrentLinkedQueue<String> other;

    public UrlQueueRepositoryBizImpl() {
        higherLevel = new ConcurrentLinkedQueue<>();
        lowerLevel = new ConcurrentLinkedQueue<>();
        //
        other = new ConcurrentLinkedQueue<>();
    }

    @Override

    public void pushHigher(String url) {
        higherLevel.add(url);
    }

    @Override
    public void pushLower(String url) {
        lowerLevel.add(url);
    }

    @Override
    public void pushOther(String url) {
        other.add(url);
    }

    @Override
    public String poll() {
        //先从高优先级url仓库中获取一个url
        String url = higherLevel.poll();
        //若不存在，就从低优先级的容器中取出一个url
        if (url == null) {
            url = lowerLevel.poll();
        }
        return url;
    }
}

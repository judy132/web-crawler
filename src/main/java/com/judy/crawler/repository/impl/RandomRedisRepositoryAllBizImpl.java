package com.judy.crawler.repository.impl;

import com.judy.crawler.constants.CommonConstants;
import com.judy.crawler.repository.IUrlRepositoryBiz;
import com.judy.crawler.utils.CrawlerUtils;
import com.judy.crawler.utils.JedisUtil;
import com.judy.crawler.utils.PropertiesManagerUtil;
import redis.clients.jedis.Jedis;

import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

/**
 * Description: 全网爬虫分布式版<br/>
 * Copyright (c),2018,judyxia<br/>
 * This program is protected by copyright laws,<br/>
 * Date:2018-12-12
 *
 * @author judy
 * @version:1.0
 */
public class RandomRedisRepositoryAllBizImpl implements IUrlRepositoryBiz {
    private Random random;
    //各平台电商网站 顶级域名集合
    private Set<String> allTopDomain;

    public RandomRedisRepositoryAllBizImpl() {
        random = new Random();
        allTopDomain = new LinkedHashSet<>();

    }

    @Override
    public void pushHigher(String url) {
        commonPush(url, PropertiesManagerUtil.getPropertyValue(CommonConstants.CRAWLER_URL_REDIS_REPOSITORY_HIGHER_KEY));
    }

    @Override
    public void pushLower(String url) {
        commonPush(url, PropertiesManagerUtil.getPropertyValue(CommonConstants.CRAWLER_URL_REDIS_REPOSITORY_LOWER_KEY));
    }

    @Override
    public void pushOther(String url) {
        commonPush(url, PropertiesManagerUtil.getPropertyValue(CommonConstants.CRAWLER_URL_REDIS_REPOSITORY_OTHER_KEY));

    }

    /**
     * 取
     *
     * @return
     */
    @Override
    public String poll() {
        //思路：
        Jedis jedis = JedisUtil.getJedis();
        //随机从set集合中获取一个顶级域名 ，先转化成数组
        String[] topDomainArray = allTopDomain.toArray(new String[allTopDomain.size()]);
        //从数组长度中随机获取域名
        String randomTopDomain = topDomainArray[random.nextInt(topDomainArray.length)];
        //先从高优先级的key中获取url
        String key = randomTopDomain + "." + PropertiesManagerUtil.getPropertyValue(CommonConstants.CRAWLER_URL_REDIS_REPOSITORY_HIGHER_KEY);
        String url = jedis.spop(key);
        //若高优先级的key取完了，从低优先级的key中获取（other:本期暂时不考虑）
        if (url == null) {
            key = randomTopDomain + "." + PropertiesManagerUtil.getPropertyValue(CommonConstants.CRAWLER_URL_REDIS_REPOSITORY_LOWER_KEY);
            url = jedis.spop(key);
        }

        JedisUtil.close(jedis);
        return url;
    }

    /**
     * 存储url共通处理,将各平台网站组织key，
     * 如 tmall.com.higer-level
     * tmall.com.lower-level
     * tmall.com.other-level
     *
     * @param url
     * @param nowUrlLevel
     */
    private void commonPush(String url, String nowUrlLevel) {
        //思路：
        //①获得顶级域名
        String topDomain = CrawlerUtils.getTopDomain(url);
        //将当前处理的dopDomain 加到容器中
        allTopDomain.add(topDomain);
        //②获得Jedis的实例
        Jedis jedis = JedisUtil.getJedis();

        //③组织key,如：电商平台的顶级域名.higer-level
        String key = topDomain + "." + nowUrlLevel;
        //④将该url存入redis组织的key中
        jedis.sadd(key, url);
        //关闭资源
        JedisUtil.close(jedis);
    }
}

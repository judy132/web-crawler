package com.judy.crawler.repository.impl;

import com.judy.crawler.constants.CommonConstants;
import com.judy.crawler.repository.IUrlRepositoryBiz;
import com.judy.crawler.utils.JedisUtil;
import com.judy.crawler.utils.PropertiesManagerUtil;
import redis.clients.jedis.Jedis;

/**
 * Description: url仓库模块业务逻辑层接口实现类,分布式版爬虫使用redis内存db来存储url<br/>
 * Copyright (c),2018,judyxia<br/>
 * This program is protected by copyright laws,<br/>
 * Date:2018-12-08
 *
 * @author judy
 * @version:1.0
 */
public class UrlRedisRepositoryBizImpl implements IUrlRepositoryBiz {
    @Override
    public void pushHigher(String url) {
        Jedis jedis = JedisUtil.getJedis();
        jedis.sadd(PropertiesManagerUtil.getPropertyValue(CommonConstants.CRAWLER_URL_REDIS_REPOSITORY_HIGHER_KEY),url);
        JedisUtil.close(jedis);
    }

    @Override
    public void pushLower(String url) {
        Jedis jedis = JedisUtil.getJedis();
        jedis.sadd(PropertiesManagerUtil.getPropertyValue(CommonConstants.CRAWLER_URL_REDIS_REPOSITORY_LOWER_KEY),url);
        JedisUtil.close(jedis);
    }

    @Override
    public void pushOther(String url) {
        Jedis jedis = JedisUtil.getJedis();
        jedis.sadd(PropertiesManagerUtil.getPropertyValue(CommonConstants.CRAWLER_URL_REDIS_REPOSITORY_OTHER_KEY),url);
        JedisUtil.close(jedis);
    }

    @Override
    public String poll() {
        Jedis jedis = JedisUtil.getJedis();
        try {
            String url = jedis.spop(PropertiesManagerUtil.getPropertyValue(CommonConstants.CRAWLER_URL_REDIS_REPOSITORY_HIGHER_KEY));
            if (url == null) {
                url = jedis.spop(PropertiesManagerUtil.getPropertyValue(CommonConstants.CRAWLER_URL_REDIS_REPOSITORY_LOWER_KEY));
            }
            return url;
        }finally {
            JedisUtil.close(jedis);
        }

    }
}

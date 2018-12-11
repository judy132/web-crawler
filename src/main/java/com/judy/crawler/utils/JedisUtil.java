package com.judy.crawler.utils;

import com.judy.crawler.constants.CommonConstants;
import com.judy.crawler.constants.DeployMode;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * Description: 获得jedis实例和关闭jedis连接 <br/>
 * Copyright (c),2018,judyxia<br/>
 * This program is protected by copyright laws,<br/>
 * Date:2018-12-10
 *
 * @author judy
 * @version:1.0
 */
public class JedisUtil {
    private static JedisPool jedisPool;

    public JedisUtil() {
    }

    //加载redis资源文件
    private static Properties properties;

    static {
        properties = new Properties();
        //取部署模式
        DeployMode deployMode = PropertiesManagerUtil.mode;
        //枚举变字符串小写
        String mode = deployMode.toString().toLowerCase();
        try {
            //加载资源文件
            properties.load(JedisUtil.class.getClassLoader().getResourceAsStream(mode+File.separator+"redis.properties"));
            //将prop实例中封装的jedis的参数都取出来，都添加到共通的资源文件(conf.)中去
            PropertiesManagerUtil.loadOtherProperties(properties);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取jedis的实例  单例模式获取
     *
     * @return
     */
    public static Jedis getJedis() {
        if (jedisPool == null) {
            synchronized (JedisUtil.class) {
                if (jedisPool == null) {
                    JedisPoolConfig poolConfig = new JedisPoolConfig();
                    poolConfig.setMaxIdle(Integer.valueOf(PropertiesManagerUtil.getPropertyValue(CommonConstants.CRAWLER_REDIS_MAX_IDLE)));
                    poolConfig.setMaxTotal(Integer.valueOf(PropertiesManagerUtil.getPropertyValue(CommonConstants.CRAWLER_REDIS_MAX_TOTAL)));
                    poolConfig.setMaxWaitMillis(Long.valueOf(PropertiesManagerUtil.getPropertyValue(CommonConstants.CRAWLER_REDIS_MAX_WAIT_MILLIS)));

                    jedisPool = new JedisPool(poolConfig,
                            PropertiesManagerUtil.getPropertyValue(CommonConstants.CRAWLER_REDIS_HOST),
                            Integer.valueOf(PropertiesManagerUtil.getPropertyValue(CommonConstants.CRAWLER_REDIS_PORT)),
                            Integer.valueOf(PropertiesManagerUtil.getPropertyValue(CommonConstants.CRAWLER_REDIS_TIMEOUT)));
                }
            }
        }

        return jedisPool.getResource();
    }

    /**
     * 资源释放
     *
     * @param jedis
     */
    public static void close(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }
}

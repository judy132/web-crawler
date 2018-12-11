package com.judy;

import com.judy.crawler.utils.JedisUtil;
import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * Description：xxx<br/>
 * Copyright (c) ， 2018， Jansonxu <br/>
 * This program is protected by copyright laws. <br/>
 * Date：2018年12月08日
 *
 * @author 徐文波
 * @version : 1.0
 */
public class JedisUtilTest {
    @Test
    public void testJedis() {
        Jedis jedis = JedisUtil.getJedis();
        System.out.println(jedis);
    }
}

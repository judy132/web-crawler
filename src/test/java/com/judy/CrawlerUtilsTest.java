package com.judy;

import com.judy.crawler.utils.CrawlerUtils;
import org.junit.Test;

/**
 * Description: CrawlerUtils共通工具类测试<br/>
 * Copyright (c),2018,judyxia<br/>
 * This program is protected by copyright laws,<br/>
 * Date:2018-12-04
 *
 * @author judy
 * @version:1.0
 */
public class CrawlerUtilsTest {
    @Test
    public void getUUIDTest(){
        System.out.println(CrawlerUtils.getUUID());
    }

    //---------------------------------
    @Test
    public void getGoodsIdTest(){
        String url = "https://item.jd.com/8735304.html";
        System.out.println(CrawlerUtils.getGoodsId(url));
    }

    /**
     * 测试获取顶级域名
     */
    @Test
    public void testGetTopDomain(){
        System.out.println(CrawlerUtils.getTopDomain("https://re.jd.com/cps/item/7437786.html"));
        System.out.println(CrawlerUtils.getTopDomain("http://news.baidu.com/"));
    }
}

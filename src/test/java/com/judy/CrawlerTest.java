package com.judy;

import com.judy.crawler.Crawler;
import com.judy.crawler.domian.Page;
import org.junit.Test;

/**
 * Description: 网络爬虫测试类f<br/>
 * Copyright (c),2018,judyxia<br/>
 * This program is protected by copyright laws,<br/>
 * Date:2018-12-04
 *
 * @author judy
 * @version:1.0
 */
public class CrawlerTest {
    @Test
    public void crawlerBeginDoingTest() {
        //步骤：
        //1，准备要给爬虫类的实例
        Crawler crawler = new Crawler();

        //2，调用爬虫实例的方法
        String url = "https://item.jd.com/8735304.html";

        //1）下载
        Page page=crawler.download(url);
//        System.out.print(page.getContent());

        //2）解析
        crawler.parse(page);

        //3) 存储
        crawler.store(page);
    }
}
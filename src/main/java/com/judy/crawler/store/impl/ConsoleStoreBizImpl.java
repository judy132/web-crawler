package com.judy.crawler.store.impl;

import com.judy.crawler.domian.Page;
import com.judy.crawler.store.IStoreBiz;

import java.util.List;

/**
 * Description: thinking by myself<br/>
 * Copyright (c),2018,judyxia<br/>
 * This program is protected by copyright laws,<br/>
 * Date:2018-12-07
 *
 * @author judy
 * @version:1.0
 */
public class ConsoleStoreBizImpl implements IStoreBiz {
    @Override
    public void store(Page page) {

        System.out.println(page.getUrl() + " <~~~> " + page.getTitle());

//        System.out.println("线程名："+Thread.currentThread().getName()+",     "+page.getUrl() + " <~~~> " + page.getPrice());

/*        System.out.println("当前页面的url:" + page.getUrl());

        //若当前页面是商品列表页面或者是所有品类的列表页面，显示页面中每个元素的url
          List<String> urls = page.getUrls();
          if (urls != null && urls.size() > 0) {
              //Jdk >= 1.8 函数式编程
              for (String url : urls) {
                  System.out.println(url);
              }
          }*/
    }
}

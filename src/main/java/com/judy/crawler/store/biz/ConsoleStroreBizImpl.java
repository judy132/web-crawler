package com.judy.crawler.store.biz;

import com.judy.crawler.domian.Page;
import com.judy.crawler.store.IStoreBiz;

/**
 * Description: thinking by myself<br/>
 * Copyright (c),2018,judyxia<br/>
 * This program is protected by copyright laws,<br/>
 * Date:2018-12-07
 *
 * @author judy
 * @version:1.0
 */
public class ConsoleStroreBizImpl implements IStoreBiz {
    @Override
    public void store(Page page) {
        for (int i = 0; i < page.getUrls().size(); i++) {
            System.out.println("--------------"+page.getUrls().get(i));

        }
    }
}

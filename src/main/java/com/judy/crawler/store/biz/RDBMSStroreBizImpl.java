package com.judy.crawler.store.biz;

import com.judy.crawler.dao.impl.DefaultPageDaoImpl;
import com.judy.crawler.domian.Page;
import com.judy.crawler.store.IStoreBiz;

/**
 * Description: thinking by myself<br/>
 * Copyright (c),2018,judyxia<br/>
 * This program is protected by copyright laws,<br/>
 * Date:2018-12-04
 *
 * @author judy
 * @version:1.0
 */
public class RDBMSStroreBizImpl implements IStoreBiz{
    @Override
    public void store(Page page) {
        DefaultPageDaoImpl defaultPageDao = new DefaultPageDaoImpl();
        defaultPageDao.save(page);
    }
}

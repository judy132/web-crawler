package com.judy.crawler.store.impl;

import com.google.common.collect.Table;
import com.judy.crawler.domian.Page;
import com.judy.crawler.store.IStoreBiz;
import org.apache.commons.dbcp.ConnectionFactory;

import java.sql.Connection;
import java.util.List;


/**
 * Description: 存到hbase <br/>
 * Copyright (c),2018,judyxia<br/>
 * This program is protected by copyright laws,<br/>
 * Date:2018-12-07
 *
 * @author judy
 * @version:1.0
 */
public class HbaseStoreImpl implements IStoreBiz {
    private Table table;

   /* public HbaseStoreImpl() {
        Connection connection = ConnectionFactory.createConnection();
        this.table = connection.getTable();
    }*/

    @Override
    public void store(Page page) {
        //拦截页表页面
        List<String> urls = page.getUrls();
        if (urls != null && urls.size() > 0) {
            return;
        }
/*
        //步骤
        //1查询
        Filter filter=Single
        Get get = new Get();
        */
    }
}

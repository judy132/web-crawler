package com.judy.crawler.dao.impl;

import com.judy.crawler.dao.IPageDao;

/**
 * Description: thinking by myself<br/>
 * Copyright (c),2018,judyxia<br/>
 * This program is protected by copyright laws,<br/>
 * Date:2018-12-04
 *
 * @author judy
 * @version:1.0
 */
public class DefaultPageDaoImpl implements IPageDao {
    @Override
    public void save() {
        String sql="insert into tb_product_info values(?,?,?,?,?,?,?,?,?,?)";

    }
}

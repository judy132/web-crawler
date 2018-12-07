package com.judy.crawler.dao;

import com.judy.crawler.domian.Page;

/**
 * Description: 将解析后的数据<br/>
 * Copyright (c),2018,judyxia<br/>
 * This program is protected by copyright laws,<br/>
 * Date:2018-12-04
 *
 * @author judy
 * @version:1.0
 */
public interface IPageDao {
    /**
     * 将解析后的产品信息保存到db（Mysql, HBase，ES）中
     *
     * @param page
     */
    void save(Page page);
}

package com.judy.crawler.repository;

/**
 * Description: url仓库模块业务逻辑层接口<br/>
 * Copyright (c),2018,judyxia<br/>
 * This program is protected by copyright laws,<br/>
 * Date:2018-12-08
 *
 * @author judy
 * @version:1.0
 */
public interface IUrlRepositoryBiz {
    /**
     * 向高优先级的容器中放url，存放商品列表页面的url
     * @param url
     */
    void pushHigher(String url);

    /**
     *向低优先级的容器中存放url，存放商品详情页面的url
     * @param url
     */
    void pushLower(String url);

    /**
     * 将其他的url存入到仓库中，后续完善的阶段需要将other中存储的url再进行细分
     *
     * @param url
     */
    void pushOther(String url);

    /**
     * 从容器中获取url
     */
    String poll();
}

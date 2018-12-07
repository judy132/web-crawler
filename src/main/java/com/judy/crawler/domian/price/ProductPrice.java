package com.judy.crawler.domian.price;

import com.judy.crawler.domian.price.PriceBean;

import java.util.List;

/**
 * Description: thinking by myself<br/>
 * Copyright (c),2018,judyxia<br/>
 * This program is protected by copyright laws,<br/>
 * Date:2018-12-05
 *
 * @author judy
 * @version:1.0
 */
public class ProductPrice {
    //
   private List<PriceBean> beans;

    public ProductPrice(List<PriceBean> beans) {
        this.beans = beans;

    }

    public List<PriceBean> getBeans() {
        return beans;
    }

    public void setBeans(List<PriceBean> beans) {
        this.beans = beans;
    }

    @Override
    public String toString() {
        return "ProductPrice{" +
                "beans=" + beans +
                '}';
    }
}


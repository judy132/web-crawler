package com.judy;

import com.alibaba.fastjson.JSON;
import com.judy.crawler.domian.price.PriceBean;
import com.judy.crawler.domian.price.ProductPrice;
import org.junit.Test;

import java.util.Date;

/**
 * Description: thinking by myself<br/>
 * Copyright (c),2018,judyxia<br/>
 * This program is protected by copyright laws,<br/>
 * Date:2018-12-05
 *
 * @author judy
 * @version:1.0
 */
public class CommonTest {
    @Test
    public void testNowTime(){
        System.out.println(new Date().getTime());
    }



    /*@Test
    public void parse(){
        String str="{\"beans\":[{\"p\":\"1199.00\",\"op\":\"1399.00\",\"id\":\"J_7479820\",\"m\":\"9999.00\"}]}";
        ProductPrice productPrice = JSON.parseObject(str, ProductPrice.class);
        System.out.println(productPrice+"---------------------------");
        PriceBean bean=productPrice.getBeans().get(0);
        System.out.println(bean);
        System.out.println("价格是："+bean.getP());
    }*/
}

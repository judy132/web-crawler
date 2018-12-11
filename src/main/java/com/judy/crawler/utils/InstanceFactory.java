package com.judy.crawler.utils;


/**
 * Description: thinking by myself<br/>
 * Copyright (c),2018,judyxia<br/>
 * This program is protected by copyright laws,<br/>
 * Date:2018-12-08
 *
 * @author judy
 * @version:1.0
 */
public class InstanceFactory {
    /**
     * 动态根据获得的接口实现类的权限定名，然后使用反射的机制获取实现类的实例
     *
     * @param <T>
     * @return
     */
    public static <T> T getInstance(String key) {
        try {
            return (T) Class.forName(PropertiesManagerUtil
                    .getPropertyValue(key))
                    .newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("获得实现类发生了异常哦！异常信息是：" + e.getMessage());
        }
    }
}

package com.judy.crawler.utils;

import com.judy.crawler.constants.CommonConstants;
import com.judy.crawler.constants.DeployMode;

import java.io.IOException;
import java.util.Properties;

/**
 * Description: 资源文件操作工具类<br/>
 * Copyright (c),2018,judyxia<br/>
 * This program is protected by copyright laws,<br/>
 * Date:2018-12-04
 *
 * @author judy
 * @version:1.0
 */
public class PropertiesManagerUtil {
    /**
     * 资源文件操作的容器
     */
    private static Properties properties;

    /**
     * 部署模式
     */
    public static DeployMode mode;

    //获取项目部署模式 赋值给 枚举类型的部署模式
    static {
        //把属性文件中的部署模式 取出来 赋值给 枚举类型存放
        properties = new Properties();
        //加载属性文件
        try {
            properties.load(PropertiesManagerUtil.class.getClassLoader().getResourceAsStream(CommonConstants.COMMON_CONFIG_FILE_NAME));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // k-v存放方式在properties中，获取对应key的value  变大写
        mode = DeployMode.valueOf(getPropertyValue(CommonConstants.CRAWLER_JOB_RUN_MODE).toString().toUpperCase());
    }

    /**
     * 获得资源文件中指定key的值
     *
     * @param key
     * @return
     */
    public static String getPropertyValue(String key) {
        return properties.getProperty(key);
    }

}

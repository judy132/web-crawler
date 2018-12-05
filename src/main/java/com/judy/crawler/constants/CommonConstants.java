package com.judy.crawler.constants;

/**
 * Description: 共通的常量<br/>
 * Copyright (c),2018,judyxia<br/>
 * This program is protected by copyright laws,<br/>
 * Date:2018-12-04
 *
 * @author judy
 * @version:1.0
 */
public interface CommonConstants {
    /**
     *运行模式
     */
    String CRAWLER_JOB_RUN_MODE="crawler.job.run.mode";

    /**
     * dbcp连接池共用属性文件
     */
    String DBCP_COMMON_FILE_NAME="dbcp-config.properties";

    /**
     * 共通配置信息资源文件名
     */
    String COMMON_CONFIG_FILE_NAME = "conf.properties";

    /**
     * 连接的实例获取失败时提示信息
     */
    String CONNECTION_FAILURE_MSG = "connection.failure.msg";


}

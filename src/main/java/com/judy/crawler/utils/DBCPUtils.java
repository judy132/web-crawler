package com.judy.crawler.utils;

import com.judy.crawler.constants.CommonConstants;
import com.judy.crawler.constants.DeployMode;
import org.apache.commons.dbcp.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Description: 对DBCP连接池操作的工具类<br/>
 * Copyright (c),2018,judyxia<br/>
 * This program is protected by copyright laws,<br/>
 * Date:2018-12-04
 *
 * @author judy
 * @version:1.0
 */
public class DBCPUtils {
    /**
     * 数据源类型的属性（连接池）
     */
    private static DataSource ds;
    /**
     * 操作资源文件的Map集合
     */
    private static Properties properties;

    static {
        properties = new Properties();

        //获得模式名（通过共通的资源文件管理器工具类来获取）
        DeployMode deployMode = PropertiesManagerUtil.mode;
        //获取某种运行目录下的 连接池属性文件
        String resourceName = deployMode.toString().toLowerCase() + File.separator + CommonConstants.DBCP_COMMON_FILE_NAME;

        try {
            properties.load(DBCPUtils.class.getClassLoader().getResourceAsStream(resourceName));
            //初始化连接池
            ds = BasicDataSourceFactory.createDataSource(properties);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获得连接池的实例
     *
     * @return
     */
    public static DataSource getDataSource() {
        return ds;
    }

    /**
     * 获得连接的实例
     *
     * @return
     */
    public static Connection getConnection() {
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(PropertiesManagerUtil.getPropertyValue(CommonConstants.CONNECTION_FAILURE_MSG));
        }
    }
}

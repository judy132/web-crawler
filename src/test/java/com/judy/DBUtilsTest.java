package com.judy;

import com.judy.crawler.utils.DBCPUtils;
import com.judy.crawler.utils.PropertiesManagerUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import java.sql.SQLException;

/**
 * Description: 工具类测试<br/>
 * Copyright (c),2018,judyxia<br/>
 * This program is protected by copyright laws,<br/>
 * Date:2018-12-04
 *
 * @author judy
 * @version:1.0
 */
public class DBUtilsTest {
    @Test
    public void testPropertiesManagerUtil(){
        System.out.println(PropertiesManagerUtil.mode);
    }

    @Test
    public void testDB(){
        System.out.println(DBCPUtils.getConnection());
    }

   /* @Test
    public void testDBCPUtil() throws SQLException {
        System.out.println(DBCPUtils.getConnection());
        //_____________________________________________
        //DBUtils框架，对JDBC轻量级的封装
        //需求：要获得demo数据库中的表user_中所有记录的条数
        QueryRunner qr = new QueryRunner(DBCPUtils.getDataSource());
        //BeanListHandler ~> 将表中每一条记录都封装到实例中，且将实例置于List容器中
        //BeanHandler ~> 将表中指定的唯一一条记录封装成实例
        //ScalarHandler ~>求表记录的条数
        Long cnt = qr.query("select count(*) cnt from user_", new ScalarHandler<>("cnt"));
        System.out.println("cnt = "+cnt);
    }*/
}

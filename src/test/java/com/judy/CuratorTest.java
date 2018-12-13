package com.judy;

import com.judy.crawler.utils.CrawlerUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.junit.Test;

import java.net.InetAddress;

/**
 * Description: zookeeper助手框架curator测试 <br/>
 * Copyright (c),2018,judyxia<br/>
 * This program is protected by copyright laws,<br/>
 * Date:2018-12-13
 *
 * @author judy
 * @version:1.0
 */
public class CuratorTest {
    @Test
    public void testCurator() throws Exception {
        String zookeeperConnectionString = "cent01:2181,cent03:2181,cent03:2181";

        /**
         * 参数1：每次重试等待的时间
         * 参数2：重试次数
         */
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.newClient(zookeeperConnectionString, retryPolicy);
        client.start();

        //默认创建的zNode是持久节点
//        client.create().forPath("/curators", "爬虫监控父zNode".getBytes());

        //创建临时节点
        for(int i=1;i<=3;i++){
            String ipAddr= InetAddress.getLocalHost().getHostAddress()+"-"+System.currentTimeMillis();
            client.create().withMode(CreateMode.EPHEMERAL).forPath("/curators/"+ipAddr, "爬虫进程对应的zNode".getBytes());
        }


        CrawlerUtils.sleep(20);


        //资源释放
        client.close();
    }
}

package com.judy.crawler.curator;

import com.judy.crawler.utils.MailUtil;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.List;

/**
 * Description: 爬虫监控服务类 <br/>
 * Copyright (c),2018,judyxia<br/>
 * This program is protected by copyright laws,<br/>
 * Date:2018-12-13
 *
 * @author judy
 * @version:1.0
 */
public class CuratorServer implements Watcher {
    //客户端 操作curator事件
    private CuratorFramework client;

    /**
     * 容器，用于存储指定zNode下初始所有子zNode的名字
     */
    private List<String> initAllZnodes;

    public CuratorServer() {
        //zookeeper连接参数
        String zookeeperConnectionString = "cent01:2181,cent03:2181,cent03:2181";
        /**
         * 参数1：每次重试等待的时间
         * 参数2：重试次数
         */
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        client = CuratorFrameworkFactory.newClient(zookeeperConnectionString, retryPolicy);
        //注意：在start方法之后书写具体的操作
        client.start();
        //curators 节点下所有的znode
        try {
            initAllZnodes = client.getChildren().usingWatcher(this).forPath("/curators");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 当前所监控的父的zNode下若是子zNode发生了变化：新增，删除，修改
     * <p>
     * 下述方法都会触发执行
     * <p>
     * 概述：根据初始话容器的长度与最新的容器的长度进行比对，就可以推导出当前爬虫集群的状态：新增，宕机，变更...
     * 思想：哪个容器中元素多，就循环遍历哪个容器。
     *
     * @param watchedEvent
     */
    @Override
    public void process(WatchedEvent watchedEvent) {
        List<String> newZodeInfos = null;
        try {
            //当前监控父节点写的子节点名字
            newZodeInfos = client.getChildren().usingWatcher(this).forPath("/curators");
            //新增
            if (newZodeInfos.size() > initAllZnodes.size()) {
                for (String newZodeInfo : newZodeInfos) {
                    //原来节点集合里不包含的节点是新的
                    if (!initAllZnodes.contains(newZodeInfo)) {
                        System.out.println("新增节点---" + newZodeInfo);
                    }
                }
                //宕机
            } else if (initAllZnodes.size() > newZodeInfos.size()) {
                for (String initAllZnode : initAllZnodes) {
                    if (!newZodeInfos.contains(initAllZnode)) {
//                        System.out.printf("爬虫节点【%s】宕机了哦！要赶紧向运维人员发送E-mail啊！....%n", initAllZnode);
                        MailUtil.sendWarningEmail("爬虫宕机警告！", "爬虫节点【" + initAllZnode + "】宕机了哦！请您赶紧采取应对措施！...", "judy132@126.com", "独孤求败");

                    }
                }
            } else {
                //容器中爬虫的个数未发生变化（不用处理）
                //①爬虫集群正常运行
                //②宕机了，当时马上重启了，总的爬虫未发生变化

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //要达到每次都是与上一次比较的效果，需要动态替换：initAllZnodes
        initAllZnodes = newZodeInfos;
    }

    /**
     * 保证监控服务一直开启
     */
    private void start() {
        while (true) {

        }
    }

    public static void main(String[] args) {
        //监控服务启动
        new CuratorServer().start();
    }
}

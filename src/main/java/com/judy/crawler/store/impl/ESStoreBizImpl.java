package com.judy.crawler.store.impl;

import com.alibaba.fastjson.JSON;
import com.judy.crawler.constants.CommonConstants;
import com.judy.crawler.domian.Page;
import com.judy.crawler.store.IStoreBiz;
import com.judy.crawler.utils.PropertiesManagerUtil;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * Description: 存储到ElasticSearch <br/>
 * Copyright (c),2018,judyxia<br/>
 * This program is protected by copyright laws,<br/>
 * Date:2018-12-15
 *
 * @author judy
 * @version:1.0
 */
public class ESStoreBizImpl implements IStoreBiz {
   /* private TransportClient client;
    private static Properties properties;

    public ESStoreBizImpl() {
        try {
            properties.load(ESStoreBizImpl.class.getClassLoader().getResourceAsStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }*/

    @Override
    public void store(Page page) {
        //连接属性设置
        Settings settings = Settings.builder()
                //这点必须要注意，不然会出现异常
                .put("cluster.name", PropertiesManagerUtil.getPropertyValue(CommonConstants.CLUSTER_NAME))
                .build();
        //创建连接客户端
        TransportClient client = TransportClient.builder().settings(settings).build();
        //获取es节点
        String nodeStr = PropertiesManagerUtil.getPropertyValue(CommonConstants.CLUSTER_NODES);
        String[] split = nodeStr.split(",");
        for (String field : split) {
            String[] hp = field.split(":");
            String hostname = hp[0];
            int port = Integer.valueOf(hp[1]);
            //连接每个节点
            TransportAddress ta = new InetSocketTransportAddress(new InetSocketAddress(hostname, port));
            client.addTransportAddresses(ta);
        }

        List<String> urls = page.getUrls();
        if (urls != null&&urls.size()>0) {
            return;
        }

        //直接存储到es，前提 先建立好索引库
        //content不需要保存
        page.setContent(null);
        try {
            client.prepareIndex("web-crawler","tb_product_info",page.getId()).setSource(JSON.toJSONString(page));

        }catch (Exception e){
            System.out.println("save to es is failed...");
        }

    }

}
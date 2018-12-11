package com.judy.crawler.utils;

import com.judy.crawler.domian.Page;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Description: 对html页面请求的操作工具类<br/>
 * Copyright (c),2018,judyxia<br/>
 * This program is protected by copyright laws,<br/>
 * Date:2018-12-05
 *
 * @author judy
 * @version:1.0
 */
public class HtmlUtils {
    public static String downloadPageContent(String url){

        //使用技术：HttpClient
        //HttpClient:类比作browser
        HttpClient client= HttpClients.createDefault();

        //请求方式：GET,POST,PUT,DELETE等等
        HttpUriRequest request=new HttpGet(url);
        try {
            //类比：在浏览器键入url，敲回车
            HttpResponse response = client.execute(request);

            //判断状态码是200 :服务器端已经将反馈的结果正常传送给客户端了
            if (response.getStatusLine().getStatusCode()== HttpStatus.SC_OK){
                String content = EntityUtils.toString(response.getEntity());
                return content;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}

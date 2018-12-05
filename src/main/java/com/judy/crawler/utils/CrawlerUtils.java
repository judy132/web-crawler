package com.judy.crawler.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.judy.crawler.domian.Page;
import com.judy.crawler.domian.PriceBean;
import com.judy.crawler.domian.ProductPrice;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Description: 爬虫项目其他共通操作工具类<br/>
 * Copyright (c),2018,judyxia<br/>
 * This program is protected by copyright laws,<br/>
 * Date:2018-12-04
 *
 * @author judy
 * @version:1.0
 */
public class CrawlerUtils {
    /**
     * 获得全球唯一的一个随机字符串
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }


    //--------------------------------------------------没
    /**
     * 获得产品id
     *
     * @param url ,如：https://item.jd.com/8735304.html
     * @return
     */
    public static String getGoodsId(String url) {
        int beginIndex = url.lastIndexOf('/') + 1;
        int endIndex = url.lastIndexOf('.');
        return url.substring(beginIndex, endIndex);
    }

    /**
     * 获取指定url的顶级域名
     *
     * @return
     */
    public static String getTopDomain(String url) {
        try {
            String host = new URL(url).getHost().toLowerCase();// 此处获取值转换为小写
            Pattern pattern = Pattern.compile("[^\\.]+(\\.com\\.cn|\\.net\\.cn|\\.org\\.cn|\\.gov\\.cn|\\.com|\\.net|\\.cn|\\.org|\\.cc|\\.me|\\.tel|\\.mobi|\\.asia|\\.biz|\\.info|\\.name|\\.tv|\\.hk|\\.公司|\\.中国|\\.网络)");
            Matcher matcher = pattern.matcher(host);
            while (matcher.find()) {
                return matcher.group();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据指定的xPath，获取对应标签标签体的值
     *
     * @param cleaner
     * @param page
     * @param xPath
     * @return
     */
    public static String getTagTextValueByAttr(HtmlCleaner cleaner, Page page, String xPath) {
        TagNode tagNode = cleaner.clean(page.getContent());
        try {
            Object[] objs = tagNode.evaluateXPath(xPath);
            if (objs != null && objs.length > 0) {
                TagNode node = (TagNode) objs[0];
                String value = node.getText().toString().trim();
                return value;
            }
        } catch (XPatherException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 根据指定的xPath，获取对应标签的属性值
     *
     * @param cleaner
     * @param page
     * @param xPath
     * @return
     */
    public static String getTagAttrValueByAttr(HtmlCleaner cleaner,Page page, String xPath,String arrName) {
        TagNode tagNode = cleaner.clean(page.getContent());
        try {
            Object[] objs = tagNode.evaluateXPath(xPath);
            if (objs != null && objs.length > 0) {
                TagNode node = (TagNode) objs[0];
                String value = node.getAttributeByName(arrName);
                return value;
            }
        } catch (XPatherException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static double parseProductPrice(String pageURL){
        String content = HtmlUtils.downloadPageContent(pageURL);
        String jsonArray= JSON.parseArray(content).toJSONString();
        JSONObject obj=new JSONObject();
        obj.put("beans",jsonArray); //
        //
        ProductPrice productPrice=JSON.parseObject(obj.toJSONString().replace("\\",""), ProductPrice.class);
        PriceBean bean = productPrice.getBeans().get(0);
//        System.out.println(bean.getP());
        return Double.valueOf(bean.getP());

    }
}

package com.judy.crawler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.judy.crawler.domian.Page;
import com.judy.crawler.domian.PriceBean;
import com.judy.crawler.domian.ProductPrice;
import com.judy.crawler.utils.CrawlerUtils;
import com.judy.crawler.utils.HtmlUtils;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.htmlcleaner.HtmlCleaner;

import java.io.IOException;

/**
 * Description: 项目入口类<br/>
 * Copyright (c),2018,judyxia<br/>
 * This program is protected by copyright laws,<br/>
 * Date:2018-12-04
 *
 * @author judy
 * @version:1.0
 */
public class Crawler {
    /**
     *  根据指定的url下载网页资源到内存中，并将页面信息封装到Page实体类中
     * @param url 产品页面的url （uri:统一资源标识符；url是uri的子集  http://xxx,ftp://xx）
     * @return Page 实例
     */
    public Page download(String url){

    Page page = new Page();

   String content= HtmlUtils.downloadPageContent(url);
   page.setContent(content);
   return page;
}


    /**
     * 对页面资源进行解析 （爬虫中的重难点！ Cleaner+xPath）
     *
     * 指定获取页面中我们需要的东西
     * @param page
     */
    //----------------------------------------------
    public void parse(Page page){
        //产品的唯一标识 ~>String，UUID ，全球唯一的字符串
        page.setId(CrawlerUtils.getUUID());

        //产品编号 ~>String
        page.setGoodsId(CrawlerUtils.getGoodsId(page.getUrl()));

        //来源  ~>String （商家的网站的顶级域名，如：jd.com，taobao.com）
        page.setSource(CrawlerUtils.getTopDomain(page.getUrl()));

        //url~>String
        //页面内容 ~>String （产品url对应的页面的详细内容，别的属性都是来自于该属性）

        //标题~>String
        HtmlCleaner cleaner = new HtmlCleaner();
        page.setTitle(CrawlerUtils.getTagTextValueByAttr(cleaner,page,"//div[@class='sku-name']"));



        //图片url ~> String
        String url1= CrawlerUtils.getTagAttrValueByAttr(cleaner,page,"//img[@id='spec-img']","data-origin");

        String url2= CrawlerUtils.getTagAttrValueByAttr(cleaner,page,"//img[@id='spec-img']","src");
        String url3= CrawlerUtils.getTagAttrValueByAttr(cleaner,page,"//img[@id='spec-img']","jqimg");

        System.out.println(url1);
        System.out.println(url2);
        System.out.println(url3);


        //售价 ~>Double
        double price = CrawlerUtils.parseProductPrice("");
        page.setPrice(price);

        //评论数~>int
        //好评率 ~>Double
        //参数 ~>String



    }

    public void store(Page page) {

    }


}

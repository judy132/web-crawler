package com.judy.crawler.parse.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.judy.crawler.domian.Page;
import com.judy.crawler.parse.IParseBiz;
import com.judy.crawler.utils.CrawlerUtils;
import org.htmlcleaner.HtmlCleaner;

/**
 * Description: thinking by myself<br/>
 * Copyright (c),2018,judyxia<br/>
 * This program is protected by copyright laws,<br/>
 * Date:2018-12-04
 *
 * @author judy
 * @version:1.0
 */
public class JDParseBizImpl implements IParseBiz{

    @Override
    public void parse(Page page) {
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
        page.setTitle(CrawlerUtils.getTagTextValueByAttr(cleaner, page, "//div[@class='sku-name']"));


        //图片url ~> String
        String url = CrawlerUtils.getTagAttrValueByAttr(cleaner, page, "//img[@id='spec-img']", "data-origin");

        if (url == null || url.trim().isEmpty()) {
            url = CrawlerUtils.getTagAttrValueByAttr(cleaner, page, "//img[@id='spec-img']", "src");
            if (url == null || url.trim().isEmpty()) {
                url = CrawlerUtils.getTagAttrValueByAttr(cleaner, page, "//img[@id='spec-img']", "jqimg");
            }
        }
        page.setImageUrl("https:" + url);


        //售价 ~>Double
        String pageURL = "https://p.3.cn/prices/mgets?skuIds=J_" + page.getGoodsId();
        double price = CrawlerUtils.parseProductPrice(pageURL);
        page.setPrice(price);

        String commentURL = "https://club.jd.com/comment/productCommentSummaries.action?referenceIds=" + page.getGoodsId() + "&_=" + CrawlerUtils.getNowTimeMillions();

        CrawlerUtils.parseProductCommentCountAndGoodRate(page, commentURL);
        //评论数~>int
        //好评率 ~>Double


        //参数 ~>String
        //步骤：
        //①准备一个JSONObject的实例,和JSONArray的实例
        JSONObject object = new JSONObject();
        JSONArray jsonArray = new JSONArray();

        //②解析出【商品介绍】
        JSONObject goodsIntroDetail = CrawlerUtils.parseGoodsIntroduction(cleaner, page);

        //③解析出【规格与包装】
        JSONObject goodsPkg = CrawlerUtils.parseGoodsPkg(cleaner, page);


        //④将解析后的结果封装到步骤①的JSONObject的实例中
        jsonArray.add(goodsIntroDetail);
        jsonArray.add(goodsPkg);
        object.put("产品参数", jsonArray);


        //⑤将最终的结果设置为Page实例的属性params的值
        page.setParams(object.toJSONString());

    }
}

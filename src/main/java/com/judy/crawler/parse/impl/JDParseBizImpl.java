package com.judy.crawler.parse.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.judy.crawler.constants.CommonConstants;
import com.judy.crawler.domian.Page;
import com.judy.crawler.parse.IParseBiz;
import com.judy.crawler.utils.CrawlerUtils;
import com.judy.crawler.utils.PropertiesManagerUtil;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

/**
 * Description: thinking by myself<br/>
 * Copyright (c),2018,judyxia<br/>
 * This program is protected by copyright laws,<br/>
 * Date:2018-12-04
 *
 * @author judy
 * @version:1.0
 */
public class JDParseBizImpl implements IParseBiz {

    @Override
    public void parse(Page page) {
        //判断page url的类型
        String pageUrl = page.getUrl();
        //html共通解析需要的HtmlCleaner实例
        HtmlCleaner cleaner = new HtmlCleaner();

        if (pageUrl.startsWith(PropertiesManagerUtil.getPropertyValue(CommonConstants.CRAWLER_GOODS_URL_PREFIX))) {
            parseSingleGood(cleaner, page);
        } else if (pageUrl.startsWith(PropertiesManagerUtil.getPropertyValue(CommonConstants.CRAWLER_GOODS_LIST_URL_PREFIX))) {
            //如果是商品列表，解析列表中的每个商品url
            parseGoodsList(cleaner, page);
        } else if (pageUrl.startsWith(PropertiesManagerUtil.getPropertyValue(CommonConstants.CRAWLER_JD_GOODS_ALL_URL_PREFIX))){
            //若是品类列表页面，解析品类列表
            parseAllCategroies(cleaner, page);
        }//。。。。


    }

    /**
     * 若是全部品类列表页面，解析品类列表
     *
     * @param page
     * @param cleaner
     */
    private void parseAllCategroies(HtmlCleaner cleaner, Page page) {
        TagNode htmlNode = cleaner.clean(page.getContent());
        try {
            // 所有的 dl
            Object[] objects = htmlNode.evaluateXPath("//dl[@class='clearfix']");
            if (objects != null && objects.length > 0) {
                for (Object object : objects) {
                    TagNode dlNode = (TagNode) object;
                    Object[] ddaObjs = dlNode.evaluateXPath("//dd/a");
                    if (ddaObjs != null && ddaObjs.length > 0) {
                        for (Object ddaObj : ddaObjs) {
                            TagNode ddaNode = (TagNode) ddaObj;
                            String href = "https:" + ddaNode.getAttributeByName("href");
                            page.getUrls().add(href);
                        }
                    }
                }
            }
        } catch (XPatherException e) {
            e.printStackTrace();
        }
    }

    private void parseGoodsList(HtmlCleaner cleaner, Page page) {
        TagNode htmlNode = cleaner.clean(page.getContent());
        try {
            //当前列表 包含手机url的a 标签
            Object[] objects = htmlNode.evaluateXPath("//*[@id=\"plist\"]/ul/li[*]/div/div[1]/a");
            if (objects != null && objects.length > 0) {
                for (Object object : objects) {
                    //变成可用的a 节点
                    TagNode aNode = (TagNode) object;
                    String href = aNode.getAttributeByName("href");
                    //拿到当前列表所有的手机url加到 page 设定的列表中
                    page.getUrls().add("https:" + href);
                }
            }

            //把当前列表页面指向下一页的url也get到，同时放入page列表中
            String nextPageListUrl = CrawlerUtils.getTagAttrValueByAttr(cleaner, page, "//a[@class='fp-next']", "href");
            //不是最后一页
            if (nextPageListUrl != null && !nextPageListUrl.trim().isEmpty()) {
                page.getUrls().add("https://list.jd.com" + nextPageListUrl);
            }
        } catch (XPatherException e) {
            e.printStackTrace();
        }
    }

    private void parseSingleGood(HtmlCleaner cleaner, Page page) {
        //产品的唯一标识 ~>String，UUID ，全球唯一的字符串
        page.setId(CrawlerUtils.getUUID());

        //产品编号 ~>String
        page.setGoodsId(CrawlerUtils.getGoodsId(page.getUrl()));

        //来源  ~>String （商家的网站的顶级域名，如：jd.com，taobao.com）
        page.setSource(CrawlerUtils.getTopDomain(page.getUrl()));

        //url~>String
        //页面内容 ~>String （产品url对应的页面的详细内容，别的属性都是来自于该属性）

        //标题~>String
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

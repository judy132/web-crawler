package com.judy.crawler.domian;


import lombok.Data;

import java.util.LinkedList;
import java.util.List;

/**
 * Description: Page实体类 （对产品页面解析后的结果的封装）<br/>
 * Copyright (c),2018,judyxia<br/>
 * This program is protected by copyright laws,<br/>
 * Date:2018-12-04
 *
 * @author judy
 * @version:1.0
 */
//用一个框架自动为实体生成getter，setter @Data
@Data
public class Page {
    /**
     * 产品的唯一标识 ~>String，UUID ，全球唯一的字符串
     */
    private String id;

    /**
     * 产品编号 ~>String
     */
    private String goodsId;

    /**
     * 来源  ~>String （商家的网站的顶级域名，如：jd.com，taobao.com）
     */
    private String source;


    /**
     * url~>String
     */
    private String url;

    /**
     * 页面内容 ~>String （产品url对应的页面的详细内容，别的属性都是来自于该属性）
     *
     * 注意：建表时，该属性不需要映射为相应的字段。为了辅助计算其他的属性值。
     */
    private String content;

    /**
     * 标题~>String
     */
    private String title;

    /**
     * 图片url ~>String
     */
    private String imageUrl;


    /**
     * 售价 ~>Double
     */
    private double price;

    /**
     * 评论数~>int
     */
    private int commentCnt;

    /**
     * 好评率 ~>Double
     */
    private double goodRate;

    /**
     * 参数 ~>String, 向表中相应的字段处存入一个json对象格式的数据
     */
    private String params;

    /**
     *
     */
//    private List<String> urls= new LinkedList<>();

    public Page() {
    }

    public Page(String id, String goodsId, String source, String url, String title, String imageUrl, double price, int commentCnt, double goodRate, String params) {
        this.id = id;
        this.goodsId = goodsId;
        this.source = source;
        this.url = url;
        this.title = title;
        this.imageUrl = imageUrl;
        this.price = price;
        this.commentCnt = commentCnt;
        this.goodRate = goodRate;
        this.params = params;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCommentCnt() {
        return commentCnt;
    }

    public void setCommentCnt(int commentCnt) {
        this.commentCnt = commentCnt;
    }

    public double getGoodRate() {
        return goodRate;
    }

    public void setGoodRate(double goodRate) {
        this.goodRate = goodRate;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }


    @Override
    public String toString() {
        return "Page{" +
                "id='" + id + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", source='" + source + '\'' +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", price=" + price +
                ", commentCnt=" + commentCnt +
                ", goodRate=" + goodRate +
                ", params='" + params + '\'' +
                '}';
    }
}

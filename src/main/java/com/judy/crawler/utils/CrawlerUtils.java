package com.judy.crawler.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.judy.crawler.domian.Page;
import com.judy.crawler.domian.comment.CommentBean;
import com.judy.crawler.domian.price.PriceBean;
import com.judy.crawler.domian.price.ProductPrice;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.LinkedList;
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
    public static String getTagAttrValueByAttr(HtmlCleaner cleaner, Page page, String xPath, String arrName) {
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

    /**
     * 解析商品的价格
     *
     * @return
     */
    public static double parseProductPrice(String pageURL) {
        //①根据url，获得内容
        String content = HtmlUtils.downloadPageContent(pageURL);
        if (content.contains("error")) {
            return -1;
        }
        //②解析内容
        JSONArray jsonArray = JSON.parseArray(content);
        JSONObject obj = new JSONObject();
        obj.put("beans", jsonArray); //
        // String str=obj.toJSONString().replace("\"[","[").replace("]\"","]").replace("\\","");
        //str: {"beans",[{"op":"1399.00","m":"9999.00","id":"J_7479820","p":"1199.00"}]}

        ProductPrice productPrice = JSON.parseObject(obj.toJSONString(), ProductPrice.class);
        PriceBean bean = productPrice.getBeans().get(0);
        return Double.valueOf(bean.getP());

    }

    /**
     * 解析商品的的评论数和好评率
     *
     * @param page
     * @param commentURL
     */
    public static void parseProductCommentCountAndGoodRate(Page page, String commentURL) {
        String content = HtmlUtils.downloadPageContent(commentURL);

       /*
       这个方法不行，空指针找不到错误原因
       CommentBean commentBean = null;
        try {

            commentBean = JSON.parseObject(content, ProductComment.class).getCommentBeans().get(0);

        } catch (Exception e) {
            commentBean = new CommentBean();
        }*/

        JSONObject json = (JSONObject) JSON.parse(content);
        CommentBean commentBean = new CommentBean();
        commentBean.setGoodRate(((BigDecimal) ((JSONObject) json.getJSONArray("CommentsCount").get(0)).get("GoodRate")).doubleValue());
        commentBean.setCommentCount((int) (((JSONObject) json.getJSONArray("CommentsCount").get(0)).get("CommentCount"))
        );

        page.setCommentCnt(commentBean.getCommentCount());
        page.setGoodRate(commentBean.getGoodRate());
    }

    /**
     * 获得系统当前时间的毫秒值
     *
     * @return
     */
    public static long getNowTimeMillions() {
        return new Date().getTime();
    }

    /**
     * 解析商品介绍的参数
     *
     * @param cleaner
     * @param page
     * @return
     */
    public static JSONObject parseGoodsIntroduction(HtmlCleaner cleaner, Page page) {
        //json对象商品介绍 大的json对象包括商品介绍和里面的详细参数
        JSONObject goodsIntroduce = new JSONObject();
        //获得商品介绍 这几个字的文本
        String goodsInstroduction = CrawlerUtils.getTagTextValueByAttr(cleaner, page, "//li[@clstag='shangpin|keycount|product|shangpinjieshao_1']");
        //商品介绍中又有json对象  商品介绍中的详细参数
        JSONObject goodsIntroduceDetail = new JSONObject();
        //获取页面主节点 html
        TagNode tagNode = cleaner.clean(page.getContent());
        try {
            //获取页面中id =detail的所有li 下的div 返回是对象数组还不是节点

            Object[] objects = tagNode.evaluateXPath("//*[@id=\"detail\"]/div[2]/div[1]/div[1]/ul[1]/li[*]/div");
            if (objects != null && objects.length > 0) {
                //对象变节点获取到 div
                for (Object objTmp : objects) {
                    TagNode node = (TagNode) objTmp;
                    //再取div中的子元素 p （p有多个）
                    for (TagNode childNode : node.getChildTags()) {
                        //取p元素中的 text 文本
                        String body = childNode.getText().toString();  //分辨率：2340*1080
                        //要切分
                        String[] arr = body.split("：");

                        //放到 设定的放商品介绍详参数的json对象中
                        goodsIntroduceDetail.put(arr[0].trim(), arr[1].trim());
                    }
                }
            }

            //get商品介绍中的品牌  放到商品详细介绍json对象中
            String brand = CrawlerUtils.getTagTextValueByAttr(cleaner, page, "//ul[@id=\"parameter-brand\"]/li");
            if (brand==null){
                return null;
            }
            String[] split = brand.split("：");
            goodsIntroduceDetail.put(split[0].trim(), split[1].trim());

            //get品牌 下面的其他参数 ，
            objects = tagNode.evaluateXPath("//ul[@class='parameter2 p-parameter-list']");
            if (objects != null && objects.length > 0) {
                for (Object objTmp : objects) {
                    //get ul 节点
                    TagNode node = (TagNode) objTmp;
                    //ul 下面的li
                    for (TagNode childNode : node.getChildTags()) {
                        //get li 中的文本 并切分
                        String[] details = childNode.getText().toString().trim().split("：");
                        goodsIntroduceDetail.put(details[0].trim(), details[1].trim());
                    }

                }
            }

            //最后把商品介绍标题和里面的详细内容放到大的json对象中
            goodsIntroduce.put(goodsInstroduction, goodsIntroduceDetail);
            return goodsIntroduce;

        } catch (XPatherException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 解析产品的规格与包装
     *
     * @param cleaner
     * @return
     */
    public static JSONObject parseGoodsPkg(HtmlCleaner cleaner, Page page) {
        //get产品与包装 标题的文本 ul中的文本
        String pkgtitle = CrawlerUtils.getTagTextValueByAttr(cleaner, page, "//li[@clstag=\"shangpin|keycount|product|pcanshutab\"]");
        //大的json对象   外层， 放 标题为key，参数通通为value
        JSONObject pkgObj = new JSONObject();
        //value是个jsonArray
        JSONArray jsonArray = new JSONArray();
        //建立联系
        pkgObj.put(pkgtitle, jsonArray);        //最后的封装到大的json对象中


        //get html
        TagNode tagNode = cleaner.clean(page.getContent());
        try {
            //get 规格的 所有参数  所有名为 Ptable-item 的div 这里是对象
            Object[] objects = tagNode.evaluateXPath("//div[@class=\"Ptable-item\"]");
            if (objects != null && objects.length > 0) {

                for (Object objTmp : objects) {
                    //每个规格是一个json对象，所以每循坏一次，构建一个json对象
                    JSONObject perLineObj = new JSONObject();
                    //每个规格里的参数又是一个json对象
                    JSONObject perLineDetail = new JSONObject();
                    //get 所有的div= Patable-item节点
                    TagNode node = (TagNode) objTmp;
                    //node.getChildTags()[0] = h3  每个规格名
                    String key = node.getChildTags()[0].getText().toString(); //主体 ,基本信息....

                    //get每个规格的详细信息  拿到每个div的dl
                    Object[] dlObjs = node.evaluateXPath("//dl[@class=\"clearfix\"]");
                    for (Object dlObj : dlObjs) {
                        TagNode dlNode = (TagNode) dlObj;
                        //dl 的子标签
                        TagNode[] dlChildTags = dlNode.getChildTags();

                        //详细参数用集合存放
                        LinkedList<String> linkedList = new LinkedList<>();
                        //每次清空集合
                        linkedList.clear();
                        //循环dl 的子标签
                        for (int i = 0; i < dlChildTags.length; i++) {
                            //dt dd ......
                            TagNode dlChildTag = dlChildTags[i];
                            //dt dl 下没有子节点才获取其文本值
                            if (dlChildTag.getChildTags().length == 0) {
                                String value = dlChildTag.getText().toString();
                                linkedList.add(value);
                            }

                        }
                        //把每个规格下的消息信息放到json对象中
                        perLineDetail.put(linkedList.getFirst(), linkedList.getLast());
                    }
                    //把每个规格名和详细信息放到一个当前的json对象中
                    perLineObj.put(key, perLineDetail);
                    //将每一个规格放到数组中
                    jsonArray.add(perLineObj);

                }

                return pkgObj;
            }
        } catch (XPatherException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 线程休眠间隔  随机几秒之内
     * @param inputSeconds 输入需要休眠的最大秒数
     */
    public static void sleep(int inputSeconds) {
        int randomSenconds = (int) (Math.random() * inputSeconds + 1);
        try {
            Thread.sleep(randomSenconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

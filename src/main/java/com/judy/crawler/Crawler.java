package com.judy.crawler;

import com.judy.crawler.domian.Page;
import com.judy.crawler.download.IDownloadBiz;
import com.judy.crawler.download.impl.DownloadBizImpl;
import com.judy.crawler.parse.IParseBiz;
import com.judy.crawler.parse.impl.JDParseBizImpl;
import com.judy.crawler.store.IStoreBiz;
import com.judy.crawler.store.biz.RDBMSStroreBizImpl;

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
    private IDownloadBiz downloadBiz;
    private IParseBiz parseBiz;
    private IStoreBiz storeBiz;

    public Crawler() {
    }

    public Crawler(IDownloadBiz downloadBiz, IParseBiz parseBiz, IStoreBiz storeBiz) {
        this.downloadBiz = downloadBiz;
        this.parseBiz = parseBiz;
        this.storeBiz = storeBiz;
    }

    /**
     * 根据指定的url下载网页资源到内存中，并将页面信息封装到Page实体类中
     *
     * @param url 产品页面的url （uri:统一资源标识符；url是uri的子集  http://xxx,ftp://xx）
     * @return Page 实例
     */
    public Page download(String url) {
        return downloadBiz.download(url);

    }


    /**
     * 对页面资源进行解析 （爬虫中的重难点！ Cleaner+xPath）
     * <p>
     * 指定获取页面中我们需要的东西
     *
     * @param page
     */
    //----------------------------------------------
    public void parse(Page page) {
        parseBiz.parse(page);

    }


    /**
     * 将解析后的结果存储到数据库（mysql，redis，hbase）
     *
     * @param page
     */
    public void store(Page page) {
        storeBiz.store(page);

    }

    /**
     * 开始爬虫
     */
    private void start() {
        String url = "https://item.jd.com/8735304.html";

        //1）下载
        Page page = download(url);

        //2）解析
        parse(page);

        //3) 存储
        store(page);
    }

    /**
     * 入口方法
     *
     * @param args
     */
    public static void main(String[] args) {
        new Crawler(new DownloadBizImpl(),
                    new JDParseBizImpl(),
                    new RDBMSStroreBizImpl()).start();
    }
}

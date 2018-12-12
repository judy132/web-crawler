package com.judy.crawler;

import com.judy.crawler.constants.CommonConstants;
import com.judy.crawler.domian.Page;
import com.judy.crawler.download.IDownloadBiz;
import com.judy.crawler.parse.IParseBiz;
import com.judy.crawler.repository.IUrlRepositoryBiz;
import com.judy.crawler.store.IStoreBiz;
import com.judy.crawler.utils.CrawlerUtils;
import com.judy.crawler.utils.InstanceFactory;
import com.judy.crawler.utils.PropertiesManagerUtil;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    /**
     * url仓库
     */
    private IUrlRepositoryBiz urlRepository;

    public Crawler() {
    }

    public Crawler(IDownloadBiz downloadBiz, IParseBiz parseBiz, IStoreBiz storeBiz) {
        this.downloadBiz = downloadBiz;
        this.parseBiz = parseBiz;
        this.storeBiz = storeBiz;

        //初始化容器（集合）  拿仓库实现类 存在redis 或单机
        urlRepository = InstanceFactory.getInstance(CommonConstants.IURLREPOSITORYBIZ);
        //添加种子url   全网爬 用商品种类 总页面 https://www.jd.com/allSort.aspx
        String seedUrl = PropertiesManagerUtil.getPropertyValue(CommonConstants.CRAWLER_SEED_URL);
        //向高优先级的容器中存入种子url
        urlRepository.pushHigher(seedUrl);

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
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        while (true) {
            //0）从url仓库中获取一个url
            final String url = urlRepository.poll();
            if (url != null) {
                threadPool.execute(new Runnable() {
                    @Override
                    public void run() {
                        crawling(url);

                    }
                });

            } else {
                System.out.println("暂时没有新的url可供爬取哦！稍等。。。。");
                //动态休息1~2秒钟
                CrawlerUtils.sleep(2);
            }

        }
    }
    /**
     * 爬虫正在爬取操作
     *
     * @param url
     */
    private void crawling(String url) {
        //判断该url是否被处理过
        if (url != null && !url.trim().isEmpty()) {
            if (CrawlerUtils.judgeUrlExists(url)) {
                return;
            } else {
                CrawlerUtils.saveNowUrl(url);
            }
        }

        //1）下载
        Page page = download(url);

        //2）解析
        parse(page);
        List<String> urls = page.getUrls();
        if (urls != null && urls.size() > 0) {
            //通过循环判断容器中每个url的类型，然后添加到不同的容器中
            for (String urlTmp : urls) {
                //若是高优先级的url添加到高优先级的容器中
                if (urlTmp.startsWith(PropertiesManagerUtil.getPropertyValue(CommonConstants.CRAWLER_GOODS_LIST_URL_PREFIX))) {
                    urlRepository.pushHigher(urlTmp);
                    //若是低优先级的url添加到低先级的容器中
                } else if (urlTmp.startsWith(PropertiesManagerUtil.getPropertyValue(CommonConstants.CRAWLER_GOODS_URL_PREFIX))) {
                    urlRepository.pushLower(urlTmp);
                } else {
                    urlRepository.pushOther(urlTmp);
                }
            }
        }

        //3) 存储
        store(page);
        //动态休息1~2秒钟
        CrawlerUtils.sleep(2);
    }

    /**
     * 入口方法
     *
     * @param args
     */
    public static void main(String[] args) {
        //清空common-url
        //若标志值是0，进行清空操作
        if (args != null && PropertiesManagerUtil.getPropertyValue(CommonConstants.CRAWLER_URL_CLEAR_FIRST_FLG).equalsIgnoreCase(args[0].trim())) {
            CrawlerUtils.clearCommonUrl();
        }

        IDownloadBiz downloadBiz = InstanceFactory.getInstance(CommonConstants.IDOWNLOADBIZ);
        IParseBiz parseBiz = InstanceFactory.getInstance(CommonConstants.IPARSEBIZ);
        IStoreBiz storeBiz = InstanceFactory.getInstance(CommonConstants.ISTOREBIZ);

        new Crawler(downloadBiz, parseBiz, storeBiz).start();
    }
}

package com.judy.crawler.scheduler.job;

import com.judy.crawler.constants.CommonConstants;
import com.judy.crawler.repository.IUrlRepositoryBiz;
import com.judy.crawler.utils.CrawlerUtils;
import com.judy.crawler.utils.InstanceFactory;
import com.judy.crawler.utils.PropertiesManagerUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.Set;

/**
 * Description: 爬虫定时器具体任务类 <br/>
 * Copyright (c),2018,judyxia<br/>
 * This program is protected by copyright laws,<br/>
 * Date:2018-12-13
 *
 * @author judy
 * @version:1.0
 */
public class  CrawlerJob implements Job {
    //url仓库
    private IUrlRepositoryBiz urlRepository;

    public CrawlerJob() {
        //get 仓库实现类
        urlRepository = InstanceFactory.getInstance(CommonConstants.IURLREPOSITORYBIZ);
    }

    /**
     * 定时器到了指定的时点，下述方法会自动触发执行
     *
     * @param context
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        //前提：
        //只要定时器启动了，证明所有的爬虫将上一次的任务都已经执行完毕了，正在空转，此时，需要清空
        //redis中的key common-url，否则，就不能爬取相同的商品了 （否则：即使价格不同，也不能爬取）
        CrawlerUtils.clearCommonUrl(CommonConstants.CRAWLER_URL_REDIS_REPOSITORY_COMMON_KEY);

        //步骤：
        //①获得运维人员新增的种子url
        //②将所有新增的种子url添加到url仓库中
        //③需要清空本次运维人员添加的新的url （否则，爬虫一直会处理相同的类似的url）

        Set<String> allSeedUrls = CrawlerUtils.getAdminNewAddSeedUrls();
        for (String url : allSeedUrls) {
            urlRepository.pushHigher(url);
        }

        CrawlerUtils.clearCommonUrl(CommonConstants.CRAWLER_ADMIN_NEW_ADD_SEED_KEY);

    }
}

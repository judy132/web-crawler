package com.judy.crawler.scheduler;

import com.judy.crawler.scheduler.job.CrawlerJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.ParseException;

/**
 * Description: 爬虫定时器服务<br/>
 * Copyright (c),2018,judyxia<br/>
 * This program is protected by copyright laws,<br/>
 * Date:2018-12-13
 *
 * @author judy
 * @version:1.0
 */
public class CrawlerScheduleService {
    public static void main(String[] args) {

        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            //
            scheduler.start();
            String jobName = CrawlerJob.class.getSimpleName();
            String groupName = "crawlerGroup";
            //需要执行的任务 描述信息
            JobDetail jobDetail = new JobDetail(jobName, groupName, CrawlerJob.class);

            //触发器 17点开始，每一分钟执行一次
            Trigger trigger = new CronTrigger(jobName, groupName, "0 0/1 17 * * ?");
            //启动定时任务
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}

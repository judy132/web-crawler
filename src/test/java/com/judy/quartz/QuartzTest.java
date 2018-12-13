package com.judy.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.ParseException;

/**
 * Description: thinking by myself<br/>
 * Copyright (c),2018,judyxia<br/>
 * This program is protected by copyright laws,<br/>
 * Date:2018-12-13
 *
 * @author judy
 * @version:1.0
 */
public class QuartzTest {
    public static void main(String[] args) throws SchedulerException, ParseException {
        //
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        //
        scheduler.start();
        String jobName ="MyJob";
        String groupName ="组1";
        //需要执行的任务 描述信息
        JobDetail jobDetail = new JobDetail(jobName,groupName,MyJob.class);

        //触发器 17点开始，每一分钟执行一次
        Trigger trigger = new CronTrigger(jobName,groupName,"0 0/1 17 * * ?");
        //启动定时任务
        scheduler.scheduleJob(jobDetail,trigger);
    }
}

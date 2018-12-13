package com.judy.crawler.utils;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Description: 邮件发送工具类 <br/>
 * Copyright (c),2018,judyxia<br/>
 * This program is protected by copyright laws,<br/>
 * Date:2018-12-13
 *
 * @author judy
 * @version:1.0
 */
public class MailUtil {
    /**
     * 发送邮件
     * @param subject
     * @param content
     * @param recipientPersonEmail
     * @param recipientPersonName
     */
    public static void sendWarningEmail(String subject, String content, String recipientPersonEmail, String recipientPersonName) {
        // 会话需要的相关信息
        Properties prop = new Properties();
        prop.setProperty("mail.transport.protocol", "smtp");// 发送邮件的协议
        prop.setProperty("mail.smtp.host", "smtp.163.com");// 使用的邮箱服务器
        prop.setProperty("mail.smtp.auth", "true");
        Session session = Session.getInstance(prop);

        try {
            // 创建邮件
            MimeMessage message = new MimeMessage(session);

            //发送邮件共通的测试账户 （注意：E-mai:xuwenbo_test@163.com； 密码：xuwenbo888）
            InternetAddress fromAddr = new InternetAddress("xuwenbo_test@163.com", "janson");// 发件人的信息
            InternetAddress toAddr = new InternetAddress(recipientPersonEmail, recipientPersonName);// 收件人的信息

            message.setFrom(fromAddr);// 在信封上写上
            message.setRecipient(Message.RecipientType.TO, toAddr);

            message.setSubject(subject);
            message.setContent(content, "text/html;charset=UTF-8");

            // 发送邮件
            Transport t = session.getTransport();
            t.connect("xuwenbo_test", "xwb881114");
            t.sendMessage(message, message.getAllRecipients());
            t.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

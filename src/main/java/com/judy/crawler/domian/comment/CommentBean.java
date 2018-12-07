package com.judy.crawler.domian.comment;

/**
 * Description: thinking by myself<br/>
 * Copyright (c),2018,judyxia<br/>
 * This program is protected by copyright laws,<br/>
 * Date:2018-12-05
 *
 * @author judy
 * @version:1.0
 */
public class CommentBean {
    private int CommentCount;
    private double GoodRate;

    public CommentBean() {
    }

    public CommentBean(int commentCount, double goodRate) {
        CommentCount = commentCount;
        GoodRate = goodRate;
    }

    public int getCommentCount() {
        return CommentCount;
    }

    public void setCommentCount(int commentCount) {
        CommentCount = commentCount;
    }

    public double getGoodRate() {
        return GoodRate;
    }

    public void setGoodRate(double goodRate) {
        GoodRate = goodRate;
    }

    @Override
    public String toString() {
        return "CommentBean{" +
                "CommentCount=" + CommentCount +
                ", GoodRate=" + GoodRate +
                '}';
    }
}

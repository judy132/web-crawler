package com.judy.crawler.domian.comment;

import java.util.List;

/**
 * Description: thinking by myself<br/>
 * Copyright (c),2018,judyxia<br/>
 * This program is protected by copyright laws,<br/>
 * Date:2018-12-05
 *
 * @author judy
 * @version:1.0
 */
public class ProductComment {
    private List<CommentBean> commentBeans;

    public ProductComment() {
    }

    public ProductComment(List<CommentBean> commentBeans) {
        this.commentBeans = commentBeans;
    }

    public List<CommentBean> getCommentBeans() {
        return commentBeans;
    }

    public void setCommentBeans(List<CommentBean> commentBeans) {
        this.commentBeans = commentBeans;
    }
}

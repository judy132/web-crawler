package com.judy.crawler.download.impl;

import com.judy.crawler.domian.Page;
import com.judy.crawler.download.IDownloadBiz;
import com.judy.crawler.utils.HtmlUtils;

/**
 * Description: thinking by myself<br/>
 * Copyright (c),2018,judyxia<br/>
 * This program is protected by copyright laws,<br/>
 * Date:2018-12-04
 *
 * @author judy
 * @version:1.0
 */
public class DownloadBizImpl implements IDownloadBiz{
    @Override
    public Page download(String url) {

        Page page = new Page();
        String content = HtmlUtils.downloadPageContent(url);
        page.setContent(content);
        page.setUrl(url);
        return page;
    }
}

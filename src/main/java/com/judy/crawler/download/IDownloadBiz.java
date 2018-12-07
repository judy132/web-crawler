package com.judy.crawler.download;

import com.judy.crawler.domian.Page;

/**
 * Description: 下载页面接口 <br/>
 * Copyright (c),2018,judyxia<br/>
 * This program is protected by copyright laws,<br/>
 * Date:2018-12-04
 *
 * @author judy
 * @version:1.0
 */
public interface IDownloadBiz {
    Page download(String url);
}

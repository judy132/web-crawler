package com.judy.crawler.dao.impl;

import com.judy.crawler.dao.IPageDao;
import com.judy.crawler.domian.Page;
import com.judy.crawler.utils.DBCPUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.SQLException;

/**
 * Description: 将解析后的页面数据保存到db中数据访问层接口实现类<br/>
 * Copyright (c),2018,judyxia<br/>
 * This program is protected by copyright laws,<br/>
 * Date:2018-12-04
 *
 * @author judy
 * @version:1.0
 */
public class DefaultPageDaoImpl implements IPageDao {
    private QueryRunner queryRunner = new QueryRunner(DBCPUtils.getDataSource());

    @Override
    public void save(Page page) {
        //先查询表中相同的数据是否存在
        try {
            Page queryBean = queryRunner.query("select * from tb_product_info where goodsId=? and source=?",
                    new BeanHandler<>(Page.class), page.getGoodsId(), page.getSource());
            //如果存在则更新
            if (queryBean == null) {
                //不存在则添加
                saveToDB(page);
            }else {

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void saveToDB(Page page) {
        String sql="insert into tb_product_info values(?,?,?,?,?,?,?,?,?,?)";
        try {
            queryRunner.update(sql,page.getId(),
                    page.getGoodsId(),
                    page.getSource(),
                    page.getUrl(),
                    page.getTitle(),
                    page.getImageUrl(),
                    page.getPrice(),
                    page.getCommentCnt(),
                    page.getGoodRate(),
                    page.getParams());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新数据库中相同的商品的参数（因为商品有些参数是在不断变化的）
     * @param page
     */
    private void updateToDB(Page page){
        String sql = "update  tb_product_info  set title=?," +
                "imageUrl=?," +
                "price=?," +
                "commentCnt=?," +
                "goodRate=?," +
                "params=? where id=?";
        try {
            queryRunner.update(sql,
                    page.getTitle(),
                    page.getImageUrl(),
                    page.getPrice(),
                    page.getCommentCnt(),
                    page.getGoodRate(),
                    page.getParams(),
                    page.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

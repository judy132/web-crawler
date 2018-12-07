-- 创建数据库
create database if not exists  `web-crawler-dev`;

-- 新建表
USE `web-crawler-dev`;
DROP TABLE IF EXISTS tb_product_info;

CREATE TABLE  IF NOT EXISTS tb_product_info (
  id VARCHAR(50) PRIMARY  KEY,
  goodsId VARCHAR(100) NOT NULL,
  source VARCHAR(30) ,
  url VARCHAR(200) ,
  title VARCHAR (180),
  imageUrl VARCHAR(280),
  price DOUBLE(8,2),
  commentCnt INT,
  goodRate DOUBLE(5,2),
  params VARCHAR(1000)
);

select * from `web-crawler-dev`.tb_product_info;


-- 修改字段params的类型
alter table `tb_product_info` modify params text ;
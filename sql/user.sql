create database dreams;

use dreams;

drop table if exists user;
create table user(
    id bigint primary key  comment 'id',
    password varchar(100) not null comment '密码',
    volunteer_id bigint comment '对应的志愿者ID,如果该用户类型为志愿者，该字段就为空',
    phone varchar(20) not null unique comment '电话',
    name varchar(20) not null comment '昵称',
    user_type tinyint(1) not null comment '用户类型枚举| 儿童为1 志愿者为 2',
    score int  default 0 comment '积分'
)
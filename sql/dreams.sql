create database dreams;

use dreams;

#用户表
drop table if exists user;
create table user
(
    id           bigint primary key comment 'id',
    password     varchar(100) not null comment '密码',
    volunteer_id bigint comment '对应的志愿者ID,如果该用户类型为志愿者，该字段就为空',
    phone        varchar(20)  not null unique comment '电话',
    name         varchar(20)  not null comment '昵称',
    user_type    tinyint(1)   not null comment '用户类型枚举| 儿童为1 志愿者为 2',
    score        int default 0 comment '积分'
);

use dreams;
#任务表
DROP TABLE IF EXISTS `task`;
CREATE TABLE `task`
(
    `id`           bigint(0)                                             NOT NULL COMMENT '主键',
    `name`         text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '任务名字',
    `volunteer_id` bigint(0)                                             NOT NULL COMMENT '志愿者ID',
    `description`  text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '任务描述',
    `total_score`  int(0)                                                NOT NULL COMMENT '任务总分',
    `deadline`     datetime(0)                                           NULL DEFAULT NULL COMMENT '任务截止时间',
    `type`         tinyint(1)                                            NOT NULL COMMENT '任务类型（枚举类型暂时还没有确定）',
    `isdelete`       tinyint(1)                                            NOT NULL COMMENT '表示任务是否删除，0未删除，1删除',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_0900_ai_ci
  ROW_FORMAT = Dynamic;

#任务作答表
DROP TABLE IF EXISTS answer;
CREATE TABLE answer
(
    id             BIGINT PRIMARY KEY COMMENT '主键',
    child_user_id  BIGINT     NOT NULL COMMENT '儿童用户ID',
    task_id        BIGINT     NOT NULL COMMENT '任务ID',
    answer_status  TINYINT(1) NOT NULL COMMENT '回答状态 (枚举类型暂时还没有确定)',
    answer_content TEXT COMMENT '回答内容',
    image_url      VARCHAR(255) COMMENT '图片URL地址',
    score          INT COMMENT '分数',
    comments       TEXT COMMENT '评语'
);



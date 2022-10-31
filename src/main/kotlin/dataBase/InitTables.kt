package dataBase

object InitTables {
    val init_user_table = """
        create table user
        (
            account    char(255) not null comment '账号'
                primary key,
            password   tinytext  not null comment '密码',
            token      char(32)  null comment '登录凭证',
            last_login timestamp null
        );"""

    val init_log_table="""
create table log
(
    account char(255)                                                  not null comment '账号',
    reward  int unsigned                                               not null comment '获得的奖励',
    coin    int unsigned                                               not null comment '已有的酷币',
    type    enum ('sign', 'study', 'spell', 'review', 'share','other') not null comment '日志类型',
    message text                                                       null comment '备注',
    time    timestamp default current_timestamp                        not null comment '记录时间',
    constraint log_user_null_fk
        foreign key (account) references user (account)
)
    comment '签到日志';

create index log_account_index
    on log (account);

    """.trimIndent()
}
create table user_center.tb_user
(
    id          varchar(50)                            not null comment '主键'
        primary key,
    account     varchar(18)                            not null comment '用户账号(登录使用)',
    name        varchar(50)  default '默认用户'        not null comment '用户名',
    password    varchar(128)                           not null comment '用户密码(登录使用)',
    email       varchar(128)                           null comment '邮箱',
    phone       varchar(11)                            null comment '手机号',
    status      varchar(10)  default 'ACTIVE'          not null comment '账号状态 ACTIVE,BAN',
    roles       varchar(255) default '["user"]'        not null comment '角色',
    create_by   varchar(50)                            null comment '创建人',
    create_time datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    update_by   varchar(50)                            null comment '更新人',
    update_time datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    is_delete   tinyint      default 0                 not null comment '逻辑删除 1-删除',
    constraint tb_user_account_unique
        unique (account)
)
    comment '用户表' collate = utf8mb4_unicode_ci;

create index tb_user__index_name
    on user_center.tb_user (name)
    comment '用户名索引';

create table tb_permission
(
    id             varchar(50)  not null comment '主键',
    name           varchar(50)  not null comment '权限名称',
    description    varchar(255) null comment '权限描述',
    permission_key varchar(50)  not null comment '权限key,适配sa-token权限认证格式',
    create_by   varchar(50)                            null comment '创建人',
    create_time datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    update_by   varchar(50)                            null comment '更新人',
    update_time datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    is_delete   tinyint      default 0                 not null comment '逻辑删除 1-删除',
    constraint tb_permission_pk
        primary key (id)
)
    comment '权限表' collate = utf8mb4_unicode_ci;

create index name
    on tb_permission (name)
    comment '名称索引';

create unique index tb_permission__uindex_key
    on tb_permission (permission_key)
    comment '权限key的唯一约束';

create table tb_role
(
    id          varchar(50)  not null comment '主键',
    name        varchar(50)  not null comment '角色名称',
    description varchar(255) null comment '角色描述',
    role_key    varchar(50)  not null comment '角色key,与user表的roles列对应',
    create_by   varchar(50)                            null comment '创建人',
    create_time datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    update_by   varchar(50)                            null comment '更新人',
    update_time datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    is_delete   tinyint      default 0                 not null comment '逻辑删除 1-删除',
    constraint tb_role_pk
        primary key (id)
)
    comment '角色表' collate = utf8mb4_unicode_ci;

create index tb_role__index_name
    on tb_role (name)
    comment '角色名称索引';

create unique index tb_role__uindex_key
    on tb_role (role_key)
    comment '角色key索引';

create table tb_role_permission
(
    id            varchar(50)  not null comment '主键',
    role_id       varchar(255) not null comment '角色id',
    permission_id varchar(50)  not null comment '权限id',
    create_by   varchar(50)                            null comment '创建人',
    create_time datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    update_by   varchar(50)                            null comment '更新人',
    update_time datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    is_delete   tinyint      default 0                 not null comment '逻辑删除 1-删除',
    constraint tb_role_permission_pk
        primary key (id),
    constraint tb_role_permission___fk_permission
        foreign key (permission_id) references tb_permission (id),
    constraint tb_role_permission___fk_role
        foreign key (role_id) references tb_role (id)
)
    comment '角色权限对照表' collate = utf8mb4_unicode_ci;





--数据库信息表
create table SYS_DATA_CONNECT
(
  db_link_no     NVARCHAR2(32) not null,
  db_link_name   NVARCHAR2(32),
  db_link_desc   NVARCHAR2(1024),
  db_link_type   NVARCHAR2(32) not null,
  db_ip          NVARCHAR2(32) not null,
  db_name        NVARCHAR2(32) not null,
  db_user        NVARCHAR2(32) not null,
  db_pw          NVARCHAR2(32) not null,
  sequ_num       NUMBER(16) not null,
  db_state       NVARCHAR2(4) not null,
  create_user_id VARCHAR2(32),
  create_date    DATE,
  server_port    VARCHAR2(10) not null,
  db_ver         VARCHAR2(256),
  issid          NVARCHAR2(1),
  other          NVARCHAR2(100)
);
comment on table SYS_DATA_CONNECT
  is '数据库信息表';
comment on column SYS_DATA_CONNECT.db_link_no
  is '数据库链接编号';
comment on column SYS_DATA_CONNECT.db_link_name
  is '数据库链接名称';
comment on column SYS_DATA_CONNECT.db_link_desc
  is '数据库链接描述';
comment on column SYS_DATA_CONNECT.db_link_type
  is '数据库链接类型,oracle,mySql,sqlServer';
comment on column SYS_DATA_CONNECT.db_ip
  is '数据库IP';
comment on column SYS_DATA_CONNECT.db_name
  is '数据库名称';
comment on column SYS_DATA_CONNECT.db_user
  is '数据库用户';
comment on column SYS_DATA_CONNECT.db_pw
  is '数据库口令';
comment on column SYS_DATA_CONNECT.sequ_num
  is '显示顺序';
comment on column SYS_DATA_CONNECT.db_state
  is '数据库状态 显示停用或启用';
comment on column SYS_DATA_CONNECT.create_user_id
  is '添加人姓名';
comment on column SYS_DATA_CONNECT.create_date
  is '添加时间';
comment on column SYS_DATA_CONNECT.server_port
  is '端口';
comment on column SYS_DATA_CONNECT.db_ver
  is '数据库版本';
comment on column SYS_DATA_CONNECT.issid
  is '是否是SID，针对oracle数据库，Y：代表SID，N：代表SERVICE_NAME';
comment on column SYS_DATA_CONNECT.other
  is '备用字段';
alter table SYS_DATA_CONNECT add constraint PK_SYS_DATA_CONNECT primary key (DB_LINK_NO);

--数据源信息表
create table SYS_DATA_SOURCE
(
  ds_id      NVARCHAR2(32) not null,
  ds_name    NVARCHAR2(100),
  ds_type    NVARCHAR2(10) not null,
  ds         CLOB not null,
  remarks    NVARCHAR2(200),
  db_link_no NVARCHAR2(32) not null,
  other      NVARCHAR2(100),
  create_date DATE
);
comment on table SYS_DATA_SOURCE
  is '数据源信息表';
comment on column SYS_DATA_SOURCE.ds_id
  is '数据源ID';
comment on column SYS_DATA_SOURCE.ds_name
  is '数据源名称';
comment on column SYS_DATA_SOURCE.ds_type
  is '数据源类型：SQL/存储过程';
comment on column SYS_DATA_SOURCE.ds
  is '数据源：SQL语句';
comment on column SYS_DATA_SOURCE.remarks
  is '备注';
comment on column SYS_DATA_SOURCE.db_link_no
  is '数据库链接编号,SYS_DATA_CONNECT:DB_LINK_NO';
comment on column SYS_DATA_SOURCE.other
  is '备用字段';
alter table SYS_DATA_SOURCE
  add constraint PK_SYS_DATA_SOURCE primary key (DS_ID);
alter table SYS_DATA_SOURCE
  add constraint FK_SYS_DATA_SOURCE1 foreign key (DB_LINK_NO)
  references SYS_DATA_CONNECT (DB_LINK_NO);

--数据源参数表
create table SYS_DATA_PARM
(
  parm_id        NVARCHAR2(32) not null,
  ds_id          NVARCHAR2(32) not null,
  parm_code      NVARCHAR2(40) not null,
  parm_name      NVARCHAR2(80),
  parm_type      NVARCHAR2(20) not null,
  parm_num       NUMBER not null,
  def_value      NVARCHAR2(200),
  parm_desc      NVARCHAR2(200),
  parm_data_type NVARCHAR2(30) not null,
  other          NVARCHAR2(100),
  parm_query_type CHAR(1)
);
comment on table SYS_DATA_PARM
  is '数据源参数表';
comment on column SYS_DATA_PARM.parm_id
  is '参数ID';
comment on column SYS_DATA_PARM.ds_id
  is '数据源ID';
comment on column SYS_DATA_PARM.parm_code
  is '参数代码';
comment on column SYS_DATA_PARM.parm_name
  is '参数名称';
comment on column SYS_DATA_PARM.parm_type
  is '参数类型：IN/OUT';
comment on column SYS_DATA_PARM.parm_num
  is '序号';
comment on column SYS_DATA_PARM.def_value
  is '默认值';
comment on column SYS_DATA_PARM.parm_desc
  is '描述';
comment on column SYS_DATA_PARM.parm_data_type
  is '参数值类型,String,Integer.Long,Double,Float,Data,DataTime,结果集(OUT)';
comment on column SYS_DATA_PARM.other
  is '备用字段';
comment on column SYS_DATA_PARM.parm_query_type
  is '查询方式,0:非模糊查询,1:模糊查询(包含),2:模糊查询(开头),3:模糊查询(结尾)';
alter table SYS_DATA_PARM
  add constraint PK_SYS_DATA_PARM primary key (PARM_ID);
alter table SYS_DATA_PARM
  add constraint FK_SYS_DATA_PARM1 foreign key (DS_ID)
  references SYS_DATA_SOURCE (DS_ID);

--数据源字段表
create table SYS_DATA_COLUMN
(
  column_id      NVARCHAR2(32) not null, --字段ID
  column_title   NVARCHAR2(100),--字段标题
  column_name    NVARCHAR2(100) not null, --字段名
  ds_id          NVARCHAR2(32) not null,--数据源ID
  column_num     NUMBER not null,--序号
  column_length   NUMBER(19) not null,--字段长度
  column_primary_key   NUMBER(1) not null,--是否主键  0:非主键 1:主键
  column_unique   NUMBER(1) not null,--可唯一 0:不唯一 1:唯一
  column_type   NVARCHAR2(20) not null,--字段类型
  column_isnull  NUMBER(1) not null--可非空 0:可为空 1:不可为空
);

comment on table SYS_DATA_COLUMN
  is '数据源字段表';
comment on column SYS_DATA_COLUMN.column_id
  is '字段ID';
comment on column SYS_DATA_COLUMN.column_title
  is '字段标题';
comment on column SYS_DATA_COLUMN.column_name
  is '字段名';
comment on column SYS_DATA_COLUMN.ds_id
  is '数据源ID';
comment on column SYS_DATA_COLUMN.column_num
  is '序号';
comment on column SYS_DATA_COLUMN.column_length
  is '字段长度';
comment on column SYS_DATA_COLUMN.column_primary_key
  is '是否主键  0:非主键 1:主键';
comment on column SYS_DATA_COLUMN.column_unique
  is '可唯一 0:不唯一 1:唯一';
comment on column SYS_DATA_COLUMN.column_type
  is '字段类型';
comment on column SYS_DATA_COLUMN.column_isnull
  is '可非空 0:可为空 1:空';

alter table SYS_DATA_COLUMN
  add constraint PK_SYS_DATA_COLUMN primary key (column_id);
alter table SYS_DATA_COLUMN
  add constraint FK_SYS_DATA_COLUMN foreign key (ds_id)
  references SYS_DATA_SOURCE (ds_id);

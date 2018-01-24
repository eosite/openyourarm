insert into sys_tree (id, parent, name, nodedesc, sort, code, discriminator) values (15001, 4, '流程根节点', '流程根节点', 4, 'process_root_node','P');
insert into sys_tree (id, parent, name, nodedesc, sort, code, discriminator) values (15002, 15001, 'LeaveProcess', '请假流程', 5, 'LeaveProcess','P');
insert into sys_tree (id, parent, name, nodedesc, sort, code, discriminator) values (15003, 15001, 'task1', '请假流程-部门经理审核', 6, 'LeaveProcess_task1','P');
insert into sys_tree (id, parent, name, nodedesc, sort, code, discriminator) values (15004, 15001, 'task2', '请假流程-项目经理审核', 7, 'LeaveProcess_task2','P');
insert into sys_tree (id, parent, name, nodedesc, sort, code, discriminator) values (15005, 15001, 'task3', '请假流程-人事经理审核', 8, 'LeaveProcess_task3','P');
insert into sys_tree (id, parent, name, nodedesc, sort, code, discriminator) values (15006, 15001, 'task4', '请假流程-公司领导审批', 9, 'LeaveProcess_task4','P');


insert into SYS_SQL (ID_, CACHEFLAG_, COUNTSQL_, CREATEBY_, CREATETIME_, NAME_, OPERATION_, PARENTID_, SCHEDULEEXPRESSION_, SCHEDULEFLAG_, SQL_, TITLE_, TYPE_, UPDATEBY_, UPDATETIME_, LOCKED_, ROWKEY_, SHAREFLAG_, SAVEFLAG_, CODE_, PUBLICFLAG_, DELETEFETCH_, FETCHFLAG_, ORDINAL_, TARGETTABLENAME_, EXPORTFLAG_, EXPORTTABLENAME_, UUID_, AGGREGATIONFLAG_) values (1, null, 'select count(*) from proj_inspection ', 'admin', '2015-02-16 11:01:31', '报审数量', 'select', 0, '', 'Y', 'select * from proj_inspection ', '查看形成报审文件数量', 'SYS', 'admin', '2015-03-17 14:25:05', 0, 'id', 'Y', 'Y', 'sql_2', null, null, null, null, null, null, null, null, 'C');
insert into SYS_SQL (ID_, CACHEFLAG_, COUNTSQL_, CREATEBY_, CREATETIME_, NAME_, OPERATION_, PARENTID_, SCHEDULEEXPRESSION_, SCHEDULEFLAG_, SQL_, TITLE_, TYPE_, UPDATEBY_, UPDATETIME_, LOCKED_, ROWKEY_, SHAREFLAG_, SAVEFLAG_, CODE_, PUBLICFLAG_, DELETEFETCH_, FETCHFLAG_, ORDINAL_, TARGETTABLENAME_, EXPORTFLAG_, EXPORTTABLENAME_, UUID_, AGGREGATIONFLAG_) values (2, null, 'select count(*) from cell_fillform where name not like ''%设计指标%''', 'admin', '2015-02-16 11:09:36', '已填资料', 'select', 0, '', 'Y', 'select * from cell_fillform where name not like ''%设计指标%''', '查看已经填写表格', 'SYS', 'admin', '2015-03-17 14:24:46', 0, 'id', 'Y', 'Y', 'sql_1', null, null, null, null, null, null, null, null, 'C');
insert into SYS_SQL (ID_, CACHEFLAG_, COUNTSQL_, CREATEBY_, CREATETIME_, NAME_, OPERATION_, PARENTID_, SCHEDULEEXPRESSION_, SCHEDULEFLAG_, SQL_, TITLE_, TYPE_, UPDATEBY_, UPDATETIME_, LOCKED_, ROWKEY_, SHAREFLAG_, SAVEFLAG_, CODE_, PUBLICFLAG_, DELETEFETCH_, FETCHFLAG_, ORDINAL_, TARGETTABLENAME_, EXPORTFLAG_, EXPORTTABLENAME_, UUID_, AGGREGATIONFLAG_) values (3, null, 'select count(distinct index_id) from cell_fillform where name like ''%评定%''  ', 'admin', '2015-02-16 11:24:55', '评定份数', 'select', 0, '', 'Y', 'select * from cell_fillform where name like ''%评定%''  ', '查看中间交工证书数量', 'SYS', 'admin', '2015-03-17 14:24:29', 0, 'id', 'Y', 'Y', 'sql_3', null, null, null, null, null, null, null, null, 'C');
insert into SYS_SQL (ID_, CACHEFLAG_, COUNTSQL_, CREATEBY_, CREATETIME_, NAME_, OPERATION_, PARENTID_, SCHEDULEEXPRESSION_, SCHEDULEFLAG_, SQL_, TITLE_, TYPE_, UPDATEBY_, UPDATETIME_, LOCKED_, ROWKEY_, SHAREFLAG_, SAVEFLAG_, CODE_, PUBLICFLAG_, DELETEFETCH_, FETCHFLAG_, ORDINAL_, TARGETTABLENAME_, EXPORTFLAG_, EXPORTTABLENAME_, UUID_, AGGREGATIONFLAG_) values (4, null, 'select count(*) from cell_fillform where index_id not in(select index_id from treepinfo) and name not like ''%设计指标%''', 'admin', '2015-02-16 11:25:30', '树外表格', 'select', 0, '', 'Y', 'select * from cell_fillform where index_id not in(select index_id from treepinfo) and name not like ''%设计指标%''', '不在结构树中的表格(除设计值表)', 'SYS', 'admin', '2015-03-17 14:24:06', 0, 'id', 'Y', 'Y', 'sql_4', null, null, null, null, null, null, null, null, 'C');
insert into SYS_SQL (ID_, CACHEFLAG_, COUNTSQL_, CREATEBY_, CREATETIME_, NAME_, OPERATION_, PARENTID_, SCHEDULEEXPRESSION_, SCHEDULEFLAG_, SQL_, TITLE_, TYPE_, UPDATEBY_, UPDATETIME_, LOCKED_, ROWKEY_, SHAREFLAG_, SAVEFLAG_, CODE_, PUBLICFLAG_, DELETEFETCH_, FETCHFLAG_, ORDINAL_, TARGETTABLENAME_, EXPORTFLAG_, EXPORTTABLENAME_, UUID_, AGGREGATIONFLAG_) values (5, null, '', 'admin', '2015-02-16 11:26:09', '查询具体驻地办ID', 'select', 0, '', 'Y', 'select * from treepinfo where sys_id is not null and parent_id in(select index_id from treepinfo where index_name like ''%抽检%'')', '查询具体驻地办ID', 'SYS', 'admin', '2015-03-04 08:20:11', 1, 'index_id', 'Y', 'Y', null, null, null, null, null, null, null, null, null, 'C');
insert into SYS_SQL (ID_, CACHEFLAG_, COUNTSQL_, CREATEBY_, CREATETIME_, NAME_, OPERATION_, PARENTID_, SCHEDULEEXPRESSION_, SCHEDULEFLAG_, SQL_, TITLE_, TYPE_, UPDATEBY_, UPDATETIME_, LOCKED_, ROWKEY_, SHAREFLAG_, SAVEFLAG_, CODE_, PUBLICFLAG_, DELETEFETCH_, FETCHFLAG_, ORDINAL_, TARGETTABLENAME_, EXPORTFLAG_, EXPORTTABLENAME_, UUID_, AGGREGATIONFLAG_) values (6, null, '', 'admin', '2015-02-16 11:27:37', ' 分别查询总监中的驻地要填的数量', 'select', 5, '', 'N', 'select sum(cell1) from treepinfo where id like #{idLike}', '分别查询总监中的驻地要填的数量', 'SYS', 'admin', '2015-03-04 08:20:37', 1, '', 'Y', null, null, null, null, null, null, null, null, null, null, 'C');
insert into SYS_SQL (ID_, CACHEFLAG_, COUNTSQL_, CREATEBY_, CREATETIME_, NAME_, OPERATION_, PARENTID_, SCHEDULEEXPRESSION_, SCHEDULEFLAG_, SQL_, TITLE_, TYPE_, UPDATEBY_, UPDATETIME_, LOCKED_, ROWKEY_, SHAREFLAG_, SAVEFLAG_, CODE_, PUBLICFLAG_, DELETEFETCH_, FETCHFLAG_, ORDINAL_, TARGETTABLENAME_, EXPORTFLAG_, EXPORTTABLENAME_, UUID_, AGGREGATIONFLAG_) values (7, null, '', 'admin', '2015-02-16 11:28:12', '分别查询总监中的驻地已填的数量', 'select', 5, '', 'N', 'select sum(cell2) from treepinfo where id like #{idLike}', '分别查询总监中的驻地已填的数量', 'SYS', 'admin', '2015-03-04 10:19:41', 1, '', 'Y', null, null, null, null, null, null, null, null, null, null, 'C');
insert into SYS_SQL (ID_, CACHEFLAG_, COUNTSQL_, CREATEBY_, CREATETIME_, NAME_, OPERATION_, PARENTID_, SCHEDULEEXPRESSION_, SCHEDULEFLAG_, SQL_, TITLE_, TYPE_, UPDATEBY_, UPDATETIME_, LOCKED_, ROWKEY_, SHAREFLAG_, SAVEFLAG_, CODE_, PUBLICFLAG_, DELETEFETCH_, FETCHFLAG_, ORDINAL_, TARGETTABLENAME_, EXPORTFLAG_, EXPORTTABLENAME_, UUID_, AGGREGATIONFLAG_) values (8, null, '', 'admin', '2015-02-16 11:29:58', '修改任务执行状态', 'update', 0, '', 'Y', 'update treepinfo set finishint=0 where cell1>0 and cell2=0
and index_id not in(select index_id from cell_mytaskmain where intisflow=1) and finishint=1 and
nodetype=0 and finishint=1   ', '对监理结构树中，实施状态的节点执行情况却全部角色都是未开始，待办找不到任务。将这些节点的状态变成计划状态', 'SYS', 'admin', null, 0, null, 'Y', null, null, null, null, null, null, null, null, null, null, 'C');
insert into SYS_SQL (ID_, CACHEFLAG_, COUNTSQL_, CREATEBY_, CREATETIME_, NAME_, OPERATION_, PARENTID_, SCHEDULEEXPRESSION_, SCHEDULEFLAG_, SQL_, TITLE_, TYPE_, UPDATEBY_, UPDATETIME_, LOCKED_, ROWKEY_, SHAREFLAG_, SAVEFLAG_, CODE_, PUBLICFLAG_, DELETEFETCH_, FETCHFLAG_, ORDINAL_, TARGETTABLENAME_, EXPORTFLAG_, EXPORTTABLENAME_, UUID_, AGGREGATIONFLAG_) values (9, null, 'select count(*) from treepinfo where finishint=-1 ', 'admin', '2015-02-16 11:30:21', '取消节点', 'select', 0, '', 'Y', 'select * from treepinfo where finishint=-1 ', '查看取消的节点', 'SYS', 'admin', '2015-03-17 14:23:14', 0, 'index_id', 'Y', 'Y', 'sql_6', null, null, null, null, null, null, null, null, 'C');
insert into SYS_SQL (ID_, CACHEFLAG_, COUNTSQL_, CREATEBY_, CREATETIME_, NAME_, OPERATION_, PARENTID_, SCHEDULEEXPRESSION_, SCHEDULEFLAG_, SQL_, TITLE_, TYPE_, UPDATEBY_, UPDATETIME_, LOCKED_, ROWKEY_, SHAREFLAG_, SAVEFLAG_, CODE_, PUBLICFLAG_, DELETEFETCH_, FETCHFLAG_, ORDINAL_, TARGETTABLENAME_, EXPORTFLAG_, EXPORTTABLENAME_, UUID_, AGGREGATIONFLAG_) values (10, null, '', 'admin', '2015-02-16 11:30:40', '把任务取消状态变成计划', 'update', 0, '', 'Y', 'update treepinfo set finishint=0 where finishint=-1 ', '把任务取消状态变成计划', 'SYS', 'admin', null, 0, null, 'Y', null, null, null, null, null, null, null, null, null, null, 'C');
insert into SYS_SQL (ID_, CACHEFLAG_, COUNTSQL_, CREATEBY_, CREATETIME_, NAME_, OPERATION_, PARENTID_, SCHEDULEEXPRESSION_, SCHEDULEFLAG_, SQL_, TITLE_, TYPE_, UPDATEBY_, UPDATETIME_, LOCKED_, ROWKEY_, SHAREFLAG_, SAVEFLAG_, CODE_, PUBLICFLAG_, DELETEFETCH_, FETCHFLAG_, ORDINAL_, TARGETTABLENAME_, EXPORTFLAG_, EXPORTTABLENAME_, UUID_, AGGREGATIONFLAG_) values (11, null, '', 'admin', '2015-02-16 11:31:32', '树外节点', 'select', 0, '', 'Y', 'select * from treepinfo where parent_id not in(select index_id from treepinfo) and nodetype=0 and parent_id<>-1', '查询正确结构树之外的结构树', 'SYS', 'admin', '2015-03-17 14:22:51', 0, 'index_id', 'Y', 'Y', 'sql_7', null, null, null, null, null, null, null, null, 'C');
insert into SYS_SQL (ID_, CACHEFLAG_, COUNTSQL_, CREATEBY_, CREATETIME_, NAME_, OPERATION_, PARENTID_, SCHEDULEEXPRESSION_, SCHEDULEFLAG_, SQL_, TITLE_, TYPE_, UPDATEBY_, UPDATETIME_, LOCKED_, ROWKEY_, SHAREFLAG_, SAVEFLAG_, CODE_, PUBLICFLAG_, DELETEFETCH_, FETCHFLAG_, ORDINAL_, TARGETTABLENAME_, EXPORTFLAG_, EXPORTTABLENAME_, UUID_, AGGREGATIONFLAG_) values (18, null, 'select count( *) from SYS_SQL_RESULT where DATABASEID_=58 and SQLDEFID_=2', 'admin', '2015-03-03 11:48:49', '查看SQL汇总结果', 'select', 0, null, 'N', 'select * from SYS_SQL_RESULT where DATABASEID_=58 and SQLDEFID_=2', '查看SQL汇总结果', null, 'admin', '2015-03-04 14:20:59', 1, '', 'N', null, 'sql_10002', null, null, null, null, null, null, null, null, 'C');
insert into SYS_SQL (ID_, CACHEFLAG_, COUNTSQL_, CREATEBY_, CREATETIME_, NAME_, OPERATION_, PARENTID_, SCHEDULEEXPRESSION_, SCHEDULEFLAG_, SQL_, TITLE_, TYPE_, UPDATEBY_, UPDATETIME_, LOCKED_, ROWKEY_, SHAREFLAG_, SAVEFLAG_, CODE_, PUBLICFLAG_, DELETEFETCH_, FETCHFLAG_, ORDINAL_, TARGETTABLENAME_, EXPORTFLAG_, EXPORTTABLENAME_, UUID_, AGGREGATIONFLAG_) values (19, null, '', 'zhangg', '2015-03-03 13:56:18', '节点数量', 'select', 0, null, 'Y', 'select * from treepinfo', '查询结构树节点数量', null, 'admin', '2015-03-17 14:15:15', 0, '', 'Y', null, 'sql_5', null, null, null, null, null, null, null, null, 'C');
insert into SYS_SQL (ID_, CACHEFLAG_, COUNTSQL_, CREATEBY_, CREATETIME_, NAME_, OPERATION_, PARENTID_, SCHEDULEEXPRESSION_, SCHEDULEFLAG_, SQL_, TITLE_, TYPE_, UPDATEBY_, UPDATETIME_, LOCKED_, ROWKEY_, SHAREFLAG_, SAVEFLAG_, CODE_, PUBLICFLAG_, DELETEFETCH_, FETCHFLAG_, ORDINAL_, TARGETTABLENAME_, EXPORTFLAG_, EXPORTTABLENAME_, UUID_, AGGREGATIONFLAG_) values (22, null, '', 'admin', '2015-03-24 14:21:44', '查询未处理收件', 'select', 0, null, null, 'select * from email_rec where intoperat is null ', '查询未处理收件', null, 'admin', '2015-09-08 11:25:17', 0, '', null, null, '', null, null, null, null, null, null, null, null, 'C');
insert into SYS_SQL (ID_, CACHEFLAG_, COUNTSQL_, CREATEBY_, CREATETIME_, NAME_, OPERATION_, PARENTID_, SCHEDULEEXPRESSION_, SCHEDULEFLAG_, SQL_, TITLE_, TYPE_, UPDATEBY_, UPDATETIME_, LOCKED_, ROWKEY_, SHAREFLAG_, SAVEFLAG_, CODE_, PUBLICFLAG_, DELETEFETCH_, FETCHFLAG_, ORDINAL_, TARGETTABLENAME_, EXPORTFLAG_, EXPORTTABLENAME_, UUID_, AGGREGATIONFLAG_) values (23, null, '', 'admin', '2015-09-08 11:23:55', '节点无wbs模板', 'select', 0, null, null, 'select * from treepinfo where wbsindex is null and nodetype=0 and index_name not like ''%施工放样%'' and index_name not like ''%开工报告%''  and index_name not like ''%申请%''', '节点无wbs模板', null, 'admin', '2015-09-09 15:06:57', 0, '', null, null, '', null, null, null, null, null, null, null, null, 'C');
insert into SYS_SQL (ID_, CACHEFLAG_, COUNTSQL_, CREATEBY_, CREATETIME_, NAME_, OPERATION_, PARENTID_, SCHEDULEEXPRESSION_, SCHEDULEFLAG_, SQL_, TITLE_, TYPE_, UPDATEBY_, UPDATETIME_, LOCKED_, ROWKEY_, SHAREFLAG_, SAVEFLAG_, CODE_, PUBLICFLAG_, DELETEFETCH_, FETCHFLAG_, ORDINAL_, TARGETTABLENAME_, EXPORTFLAG_, EXPORTTABLENAME_, UUID_, AGGREGATIONFLAG_) values (24, null, 'select count(*) from cell_fillform where name not like ''%设计指标%'' and name like ''%试验%'' or name like ''%材料报验%'' or name like ''%试件%'' or name like ''%曲线图%''', 'admin', '2015-11-09 17:15:56', '试验数量', 'select', 0, null, null, 'select * from cell_fillform where name not like ''%设计指标%'' and name like ''%试验%'' or name like ''%材料报验%'' or name like ''%试件%'' or name like ''%曲线图%''', '试验数量', null, 'admin', null, 0, 'id', null, 'N', 'sql_20151109', null, null, null, null, null, null, null, null, 'C');
insert into SYS_SQL (ID_, CACHEFLAG_, COUNTSQL_, CREATEBY_, CREATETIME_, NAME_, OPERATION_, PARENTID_, SCHEDULEEXPRESSION_, SCHEDULEFLAG_, SQL_, TITLE_, TYPE_, UPDATEBY_, UPDATETIME_, LOCKED_, ROWKEY_, SHAREFLAG_, SAVEFLAG_, CODE_, PUBLICFLAG_, DELETEFETCH_, FETCHFLAG_, ORDINAL_, TARGETTABLENAME_, EXPORTFLAG_, EXPORTTABLENAME_, UUID_, AGGREGATIONFLAG_) values (25, null, 'select sum(cell3) from treepinfo ', 'admin', '2016-03-22 16:09:51', '未填表格', 'select', 0, null, 'Y', 'select sum(cell3) from treepinfo ', '未填表格', null, 'admin', '2016-03-22 16:17:18', 0, 'id', 'Y', 'Y', 'sql_20160322', '', '', '', 0, '', '', '', null, 'C');
insert into SYS_SQL (ID_, CACHEFLAG_, COUNTSQL_, CREATEBY_, CREATETIME_, NAME_, OPERATION_, PARENTID_, SCHEDULEEXPRESSION_, SCHEDULEFLAG_, SQL_, TITLE_, TYPE_, UPDATEBY_, UPDATETIME_, LOCKED_, ROWKEY_, SHAREFLAG_, SAVEFLAG_, CODE_, PUBLICFLAG_, DELETEFETCH_, FETCHFLAG_, ORDINAL_, TARGETTABLENAME_, EXPORTFLAG_, EXPORTTABLENAME_, UUID_, AGGREGATIONFLAG_) values (26, null, 'select sum(cell3) from treepinfo  where  projtype<>''a''', 'admin', '2016-04-01 10:05:52', '未填自检表格', 'select', 0, null, 'Y', 'select sum(cell3) from treepinfo  where  projtype<>''a''', '未填自检表格', null, 'admin', '2016-04-01 10:09:27', 0, 'id', 'Y', 'Y', 'sql_20160401', '', '', 'N', 0, '', 'N', '', null, 'C');
insert into SYS_SQL (ID_, CACHEFLAG_, COUNTSQL_, CREATEBY_, CREATETIME_, NAME_, OPERATION_, PARENTID_, SCHEDULEEXPRESSION_, SCHEDULEFLAG_, SQL_, TITLE_, TYPE_, UPDATEBY_, UPDATETIME_, LOCKED_, ROWKEY_, SHAREFLAG_, SAVEFLAG_, CODE_, PUBLICFLAG_, DELETEFETCH_, FETCHFLAG_, ORDINAL_, TARGETTABLENAME_, EXPORTFLAG_, EXPORTTABLENAME_, UUID_, AGGREGATIONFLAG_) values (125, null, 'select count(*) from treepinfo where nodetype=0 and cell1<>0 and cell3=0 and index_name not like ''%分项%'' and index_name not like ''%分部%'' and index_name not like ''%单位%''  and index_name not like ''%交验%''  and index_name not like ''%评定%'' 
', 'admin', '2016-05-11 08:32:26', '已完工序', 'select', 0, null, 'Y', 'select * from treepinfo where nodetype=0 and cell1<>0 and cell3=0 and index_name not like ''%分项%'' and index_name not like ''%分部%'' and index_name not like ''%单位%''  and index_name not like ''%交验%''  and index_name not like ''%评定%'' 
', '已完工序', null, 'admin', '2016-05-11 09:40:35', 0, 'index_id', 'Y', 'Y', 'sql_20160511', '', '', 'Y', 0, '', 'Y', '', null, 'C');
insert into SYS_SQL (ID_, CACHEFLAG_, COUNTSQL_, CREATEBY_, CREATETIME_, NAME_, OPERATION_, PARENTID_, SCHEDULEEXPRESSION_, SCHEDULEFLAG_, SQL_, TITLE_, TYPE_, UPDATEBY_, UPDATETIME_, LOCKED_, ROWKEY_, SHAREFLAG_, SAVEFLAG_, CODE_, PUBLICFLAG_, DELETEFETCH_, FETCHFLAG_, ORDINAL_, TARGETTABLENAME_, EXPORTFLAG_, EXPORTTABLENAME_, UUID_, AGGREGATIONFLAG_) values (128, null, 'select count(*) from treepinfo where nodetype=0 and cell1<>0 and cell3=0 and index_name like ''%分部%''  and  projtype<>''a''', 'admin', '2016-05-11 08:49:42', '已完分部', 'select', 0, null, 'Y', 'select * from treepinfo where nodetype=0 and cell1<>0 and cell3=0 and index_name like ''%分部%''   and  projtype<>''a''', '已完分部', null, 'admin', '2016-05-11 10:27:17', 0, 'index_id', 'Y', 'Y', 'sql_20160511_2', '', 'Y', 'Y', 0, '', 'Y', '', null, 'C');
insert into SYS_SQL (ID_, CACHEFLAG_, COUNTSQL_, CREATEBY_, CREATETIME_, NAME_, OPERATION_, PARENTID_, SCHEDULEEXPRESSION_, SCHEDULEFLAG_, SQL_, TITLE_, TYPE_, UPDATEBY_, UPDATETIME_, LOCKED_, ROWKEY_, SHAREFLAG_, SAVEFLAG_, CODE_, PUBLICFLAG_, DELETEFETCH_, FETCHFLAG_, ORDINAL_, TARGETTABLENAME_, EXPORTFLAG_, EXPORTTABLENAME_, UUID_, AGGREGATIONFLAG_) values (129, null, 'select count(*) from treepinfo where nodetype=0 and cell1<>0 and cell3=0 and index_name like ''%单位%''  and  projtype<>''a''', 'admin', '2016-05-11 08:50:30', '已完单位工程', 'select', 0, null, 'Y', 'select * from treepinfo where nodetype=0 and cell1<>0 and cell3=0 and index_name like ''%单位%''   and  projtype<>''a''', '已完单位工程', null, 'admin', '2016-05-11 10:28:06', 0, 'index_id', 'Y', 'Y', 'sql_20160511_3', '', 'Y', 'Y', 0, '', 'Y', '', null, 'C');
insert into SYS_SQL (ID_, CACHEFLAG_, COUNTSQL_, CREATEBY_, CREATETIME_, NAME_, OPERATION_, PARENTID_, SCHEDULEEXPRESSION_, SCHEDULEFLAG_, SQL_, TITLE_, TYPE_, UPDATEBY_, UPDATETIME_, LOCKED_, ROWKEY_, SHAREFLAG_, SAVEFLAG_, CODE_, PUBLICFLAG_, DELETEFETCH_, FETCHFLAG_, ORDINAL_, TARGETTABLENAME_, EXPORTFLAG_, EXPORTTABLENAME_, UUID_, AGGREGATIONFLAG_) values (225, null, 'select count(*) from email_rec where intoperat is null  ', 'admin', '2016-06-22 16:51:02', '未处理邮件', 'select', 0, null, 'Y', 'select * from email_rec where intoperat is null ', '未处理邮件', null, 'admin', '2016-06-22 16:51:33', 0, 'id', 'Y', 'Y', 'sql_20160622', '', '', 'Y', 0, '', 'Y', '', null, 'C');

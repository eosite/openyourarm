alter table net_role add deleteflag int default 0;

alter table userinfo add deleteflag int default 0;

alter table sys_application add deleteflag int default 0;

alter table sys_department add deleteflag int default 0;

alter table sys_tree add deleteflag int default 0;


alter table net_role add deletetime datetime null;

alter table userinfo add deletetime datetime null;

alter table sys_application add deletetime datetime null;

alter table sys_department add deletetime datetime null;

alter table sys_tree add deletetime datetime null;


update net_role set deleteflag = 0 where deleteflag is null;

update sys_application set deleteflag = 0 where deleteflag is null;

update sys_department set deleteflag = 0 where deleteflag is null;

update sys_tree set deleteflag = 0 where deleteflag is null;

update userinfo set deleteflag = 0 where deleteflag is null;


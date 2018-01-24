alter table SYS_TREETABLE_SYNTHETIC_RULE add MAPPINGTOTARGETALIAS_ nvarchar(500);

alter table SYS_SERVER add SECRETALGORITHM_ nvarchar(50) null;

alter table SYS_SERVER add SECRETKEY_ nvarchar(max) null;

alter table SYS_SERVER add SECRETIV_ nvarchar(200) null;


alter table LOGIN_MODULE add PUBLICKEY_ nvarchar(max) null;

alter table LOGIN_MODULE add PRIVATEKEY_ nvarchar(max) null;

alter table LOGIN_MODULE add PEERPUBLICKEY_ nvarchar(max) null;



alter table SYS_LOGIN_TOKEN add SIGNATURE_ nvarchar(500) null;

alter table SYS_LOGIN_TOKEN add NONCE_ nvarchar(50) null;

alter table SYS_LOGIN_TOKEN add TIMEMILLIS_ bigint null;


update userinfo set deleteFlag=0 where deleteFlag is null;

update sys_application set deleteFlag=0 where deleteFlag is null;

update sys_department set deleteFlag=0 where deleteFlag is null;

update sys_tree set deleteFlag=0 where deleteFlag is null;

update net_role set deleteFlag=0 where deleteFlag is null;




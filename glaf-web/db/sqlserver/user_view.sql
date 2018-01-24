drop view user_view;
delete from interface where frmtype = 'user_view';
delete from cell_data_field where tableid = 'user_view';
delete from cell_data_table where id = 'user_view';

CREATE VIEW user_view
AS 
select u.UserId, u.UserName, u.ISSYSTEM, u.STATUS, u.DEPID, u.EMAIL, u.MOBILE, u.ACCOUNTTYPE, d.NAME 
from userinfo u
inner join sys_department d
on u.DEPID = d.id;

insert into cell_data_table (id,tablename,index_id,name,addtype,userid,ctime,content,issubtable) values('user_view','user_view',1,'用户视图',3,'system',GETDATE(),'用户视图',0);

insert into interface(frmtype,listId,issystem,fname,dname,dtype,strlen,intype,listno,isListShow) 
values('user_view','user_view_1',1,'用户编号','UserId','string',-1,'edt',1,1);
insert into interface(frmtype,listId,issystem,fname,dname,dtype,strlen,intype,listno,isListShow) 
values('user_view','user_view_2',1,'用户姓名','UserName','string',-1,'edt',1,1);
insert into interface(frmtype,listId,issystem,fname,dname,dtype,strlen,intype,listno,isListShow) 
values('user_view','user_view_3',1,'是否系统管理员','ISSYSTEM','string',-1,'edt',1,1);
insert into interface(frmtype,listId,issystem,fname,dname,dtype,strlen,intype,listno,isListShow) 
values('user_view','user_view_4',1,'状态','STATUS','string',-1,'edt',1,1);
insert into interface(frmtype,listId,issystem,fname,dname,dtype,strlen,intype,listno,isListShow) 
values('user_view','user_view_5',1,'部门编号','DEPID','int',-1,'edt',1,1);
insert into interface(frmtype,listId,issystem,fname,dname,dtype,strlen,intype,listno,isListShow) 
values('user_view','user_view_6',1,'Email','EMAIL','string',-1,'edt',1,1);
insert into interface(frmtype,listId,issystem,fname,dname,dtype,strlen,intype,listno,isListShow) 
values('user_view','user_view_7',1,'手机号码','MOBILE','string',-1,'edt',1,1);
insert into interface(frmtype,listId,issystem,fname,dname,dtype,strlen,intype,listno,isListShow) 
values('user_view','user_view_8',1,'部门名称','NAME','int',-1,'edt',1,1);
insert into interface(frmtype,listId,issystem,fname,dname,dtype,strlen,intype,listno,isListShow) 
values('user_view','user_view_9',1,'账户类型','ACCOUNTTYPE','int',-1,'edt',1,1);

insert into cell_data_field(id,tableid,fieldname,userid,maxuser,ctime)
values('user_view_1','user_view','UserId','admin',1,GETDATE());
insert into cell_data_field(id,tableid,fieldname,userid,maxuser,ctime)
values('user_view_2','user_view','UserName','admin',1,GETDATE());
insert into cell_data_field(id,tableid,fieldname,userid,maxuser,ctime)
values('user_view_3','user_view','ISSYSTEM','admin',1,GETDATE());
insert into cell_data_field(id,tableid,fieldname,userid,maxuser,ctime)
values('user_view_4','user_view','STATUS','admin',1,GETDATE());
insert into cell_data_field(id,tableid,fieldname,userid,maxuser,ctime)
values('user_view_5','user_view','DEPID','admin',1,GETDATE());
insert into cell_data_field(id,tableid,fieldname,userid,maxuser,ctime)
values('user_view_6','user_view','EMAIL','admin',1,GETDATE());
insert into cell_data_field(id,tableid,fieldname,userid,maxuser,ctime)
values('user_view_7','user_view','MOBILE','admin',1,GETDATE());
insert into cell_data_field(id,tableid,fieldname,userid,maxuser,ctime)
values('user_view_8','user_view','NAME','admin',1,GETDATE());
insert into cell_data_field(id,tableid,fieldname,userid,maxuser,ctime)
values('user_view_9','user_view','ACCOUNTTYPE','admin',1,GETDATE());
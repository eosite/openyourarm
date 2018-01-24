insert into cell_data_table (id,tablename,index_id,name,addtype,userid,ctime,content,issubtable) values('sys_database_access','sys_database_access',1,'标段权限',3,'system',GETDATE(),'标段权限配置',0);

insert into interface(frmtype,listId,issystem,fname,dname,dtype,strlen,intype,listno,isListShow) 
values('sys_database_access','sys_database_access_1',1,'ID','ID_','int',-1,'edt',1,1);
insert into interface(frmtype,listId,issystem,fname,dname,dtype,strlen,intype,listno,isListShow) 
values('sys_database_access','sys_database_access_2',1,'用户','ACTORID_','string',-1,'edt',1,1);
insert into interface(frmtype,listId,issystem,fname,dname,dtype,strlen,intype,listno,isListShow) 
values('sys_database_access','sys_database_access_3',1,'标段ID','DATABASEID_','int',-1,'edt',1,1);

insert into cell_data_field(id,tableid,fieldname,userid,maxuser,ctime)
values('sys_database_access_1','sys_database_access','ID_','admin',1,GETDATE());
insert into cell_data_field(id,tableid,fieldname,userid,maxuser,ctime)
values('sys_database_access_2','sys_database_access','ACTORID_','admin',1,GETDATE());
insert into cell_data_field(id,tableid,fieldname,userid,maxuser,ctime)
values('sys_database_access_3','sys_database_access','DATABASEID_','admin',1,GETDATE());
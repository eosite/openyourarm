
insert into cell_data_table (id,tablename,index_id,name,addtype,userid,ctime,content,issubtable) values('sys_database','sys_database',1,'�����Ϣ',3,'system',GETDATE(),'������ݿ�����',0);

insert into interface(frmtype,listId,issystem,fname,dname,dtype,strlen,intype,listno,isListShow) 
values('sys_database','sys_database_1',1,'ID','ID_','int',-1,'edt',1,1);
insert into interface(frmtype,listId,issystem,fname,dname,dtype,strlen,intype,listno,isListShow) 
values('sys_database','sys_database_2',1,'�������','TITLE_','string',-1,'edt',1,1);
insert into interface(frmtype,listId,issystem,fname,dname,dtype,strlen,intype,listno,isListShow) 
values('sys_database','sys_database_3',1,'��α��','SECTION_','string',-1,'edt',1,1);
insert into interface(frmtype,listId,issystem,fname,dname,dtype,strlen,intype,listno,isListShow) 
values('sys_database','sys_database_4',1,'����','HOST_','string',-1,'edt',1,1);
insert into interface(frmtype,listId,issystem,fname,dname,dtype,strlen,intype,listno,isListShow) 
values('sys_database','sys_database_5',1,'���ݿ�����','DBNAME_','string',-1,'edt',1,1);

insert into cell_data_field(id,tableid,fieldname,userid,maxuser,ctime)
values('sys_database_1','sys_database','ID_','admin',1,GETDATE());
insert into cell_data_field(id,tableid,fieldname,userid,maxuser,ctime)
values('sys_database_2','sys_database','TITLE_','admin',2,GETDATE());
insert into cell_data_field(id,tableid,fieldname,userid,maxuser,ctime)
values('sys_database_3','sys_database','SECTION_','admin',3,GETDATE());
insert into cell_data_field(id,tableid,fieldname,userid,maxuser,ctime)
values('sys_database_4','sys_database','HOST_','admin',4,GETDATE());
insert into cell_data_field(id,tableid,fieldname,userid,maxuser,ctime)
values('sys_database_5','sys_database','DBNAME_','admin',5,GETDATE());

insert into cell_data_table (id,tablename,name,addtype,userid,ctime,content,issubtable) values('project_query','project_query','��Ŀ��Ϣ',3,'system',GETDATE(),'��Ŀ��Ϣ',0);

insert into interface(frmtype,listId,issystem,fname,dname,dtype,strlen,intype,listno,isListShow) 
values('project_query','project_query_1',1,'ID','ID_','int',-1,'edt',1,1);
insert into interface(frmtype,listId,issystem,fname,dname,dtype,strlen,intype,listno,isListShow) 
values('project_query','project_query_2',1,'��Ŀ����','NAME_','string',-1,'edt',1,1);
insert into interface(frmtype,listId,issystem,fname,dname,dtype,strlen,intype,listno,isListShow) 
values('project_query','project_query_3',1,'��Ŀ����','CODE_','string',-1,'edt',1,1);
insert into interface(frmtype,listId,issystem,fname,dname,dtype,strlen,intype,listno,isListShow) 
values('project_query','project_query_4',1,'����','TITLE_','string',-1,'edt',1,1);

insert into cell_data_field(id,tableid,fieldname,userid,maxuser,ctime)
values('project_query_1','project_query','ID_','admin',1,GETDATE());
insert into cell_data_field(id,tableid,fieldname,userid,maxuser,ctime)
values('project_query_2','project_query','NAME_','admin',2,GETDATE());
insert into cell_data_field(id,tableid,fieldname,userid,maxuser,ctime)
values('project_query_3','project_query','CODE_','admin',3,GETDATE());
insert into cell_data_field(id,tableid,fieldname,userid,maxuser,ctime)
values('project_query_4','project_query','TITLE_','admin',4,GETDATE());
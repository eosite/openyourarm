 create table sys_permission ( 
    roleid NUMBER(19,0)  not null,
    funcid NUMBER(19,0)  not null,
    primary key (roleid, funcid) 
);

 create table sys_access ( 
    roleid NUMBER(19,0)  not null,
    appid NUMBER(19,0)  not null,
    primary key (roleid, appid) 
);


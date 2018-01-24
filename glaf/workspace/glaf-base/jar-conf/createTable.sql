 create table sys_permission ( 
    roleid bigint  not null,
    funcid bigint  not null,
    primary key (roleid, funcid) 
);

 create table sys_access ( 
    roleid bigint  not null,
    appid bigint  not null,
    primary key (roleid, appid) 
);


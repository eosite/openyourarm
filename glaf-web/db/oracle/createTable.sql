CREATE TABLE NET_ROLE (
        ID NUMBER(19) NOT NULL,
        CODE VARCHAR2(50),
        CONTENT VARCHAR2(500),
        CREATEBY VARCHAR2(50),
        CREATEDATE TIMESTAMP(6),
        DELETEFLAG NUMBER(10),
        DELETETIME TIMESTAMP(6),
        INDEXURL VARCHAR2(500),
        ISUSEBRANCH VARCHAR2(10),
        ISUSEDEPT VARCHAR2(10),
        ROLENAME VARCHAR2(100),
        TASKSORT NUMBER(10),
        TYPE VARCHAR2(50),
        UPDATEBY VARCHAR2(50),
        UPDATEDATE TIMESTAMP(6),
        PRIMARY KEY (ID)
);
 
 
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

CREATE TABLE FORM_TEMPLATE(
        ID_ NUMBER(10) NOT NULL,
        COMPONENTID_ NUMBER(10),
        CREATEBY_ VARCHAR2(50),
        CREATEDATE_ TIMESTAMP(6),
        DELETEFLAG_ NUMBER(10),
        IMAGE_ BLOB,
        IMAGE_EXISTS NUMBER(19),
        NAME_ VARCHAR2(250),
        TEMPLATE_ CLOB,
        TYPE_ VARCHAR2(250),
        PRIMARY KEY (ID_)
);
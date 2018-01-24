
 create table NET_ROLE ( 
    ID bigint not null ,
    LISTNO integer ,
    ROLENAME varchar  (100) ,
    ROLEUSE integer ,
    CONTENT varchar  (250) ,
    ROLETYPE integer ,
    BUSIESS_ID varchar  (50) ,
    TASKSORT integer ,
    DOMAIN_INDEX integer ,
    CODE varchar  (50) ,
    CREATEBY varchar  (50) ,
    CREATEDATE timestamp ,
    UPDATEBY varchar  (50) ,
    UPDATEDATE timestamp ,
    ISUSEBRANCH varchar  (10) ,
    TYPE varchar  (50) ,
    INDEXURL varchar  (500) ,
    DELETEFLAG integer ,
    DELETETIME timestamp ,
    primary key (ID) 
);


 create table SYS_TREE ( 
    ID bigint  not null ,
    CODE varchar  (50) ,
    CREATEBY varchar  (50) ,
    CREATEDATE timestamp ,
    NODEDESC varchar  (500) ,
    DISCRIMINATOR varchar  (10) ,
    ICON varchar  (50) ,
    ICONCLS varchar  (50) ,
    LOCKED integer ,
    MOVEABLE varchar  (10) ,
    NAME varchar  (100) ,
    PARENT bigint ,
    SORT integer ,
    TREEID varchar  (500) ,
    UPDATEBY varchar  (50) ,
    UPDATEDATE timestamp ,
    URL varchar  (500) ,
    DELETEFLAG integer ,
    ALLOWEDFILEEXTS varchar  (200) ,
    ALLOWEDFIZESIZE integer ,
    DELETETIME timestamp ,
    PROVIDERCLASS varchar  (100) ,
    primary key (ID) 
);


 create table SYS_DEPARTMENT ( 
    ID bigint  not null ,
    CODE varchar  (250) ,
    CODE2 varchar  (250) ,
    CREATEBY varchar  (50) ,
    CREATETIME timestamp ,
    DEPTDESC varchar  (500) ,
    FINCODE varchar  (250) ,
    DEPTLEVEL integer ,
    NAME varchar  (200) ,
    DEPTNO varchar  (255) ,
    NODEID bigint ,
    SORT integer ,
    STATUS integer ,
    UPDATEBY varchar  (50) ,
    UPDATEDATE timestamp ,
    DELETEFLAG integer ,
    DELETETIME timestamp ,
    primary key (ID) 
);


 create table USERINFO ( 
    USERID varchar  (20)  not null ,
    USERNAME varchar  (20) ,
    PASSWORD varchar  (10) ,
    CTIME timestamp ,
    ETIME timestamp ,
    STATUS varchar  (1) ,
    ISSYSTEM varchar  (1) ,
    SETPRUV varchar  (1) ,
    NOTE varchar  (50) ,
    SLEVEL integer ,
    DEPID integer ,
    ISBIND varchar  (1) ,
    COMPUTERID varchar  (500) ,
    EMAIL varchar  (100) ,
    EMAIL_USER varchar  (100) ,
    EMAIL_PSW varchar  (50) ,
    MOBILE varchar  (15) ,
    INTREMOTE integer ,
    DOMAIN_INDEX integer ,
    INTALLWBS integer ,
    INTVIRTUAL integer ,
    ACCOUNTTYPE integer ,
    CREATEBY varchar  (50) ,
    DUMPFLAG integer ,
    EVECTION integer ,
    FAX varchar  (50) ,
    GENDER integer ,
    HEADSHIP varchar  (50) ,
    LASTLOGINIP varchar  (80) ,
    LASTLOGINTIME timestamp ,
    SUPERIORIDS varchar  (250) ,
    TELEPHONE varchar  (50) ,
    UPDATEBY varchar  (50) ,
    UPDATEDATE timestamp ,
    USERTYPE integer ,
    LASTLOGINDATE timestamp ,
    LOGINIP varchar  (255) ,
    LOGINRETRY integer ,
    TOKEN varchar  (250) ,
    LOCKLOGINTIME timestamp ,
    APPID varchar  (200) ,
    APPSECRET varchar  (200) ,
    PASSWORD_HASH varchar  (500) ,
    TOKENTIME timestamp ,
    DELETEFLAG integer ,
    DELETETIME timestamp ,
    LOGINSECRET_ varchar  (200) ,
    LOGINSECRETUPDATETIME_ timestamp ,
    MQLOGINFLAG_ varchar  (1) ,
    SECRETLOGINFLAG_ varchar  (1) ,
    SYNCFLAG integer ,
    SYNCOPERATORTYPE varchar  (50) ,
    SYNCTIME timestamp ,
    primary key (USERID) 
);


 create table SYS_APPLICATION ( 
    ID bigint  not null ,
    CODE varchar  (50) ,
    CREATEBY varchar  (50) ,
    CREATEDATE timestamp ,
    APPDESC varchar  (500) ,
    LOCKED integer ,
    NAME varchar  (250) ,
    NODEID bigint ,
    SHOWMENU integer ,
    SORT integer ,
    UPDATEBY varchar  (50) ,
    UPDATEDATE timestamp ,
    URL varchar  (500) ,
    IMAGEPATH varchar  (200) ,
    LINKFILENAME varchar  (200) ,
    LINKTYPE varchar  (50) ,
    LINKFILEID varchar  (200) ,
    REFID1 integer ,
    REFID2 integer ,
    REFID3 integer ,
    REFID4 integer ,
    REFID5 integer ,
    REFNAME1 varchar  (255) ,
    REFNAME2 varchar  (255) ,
    REFNAME3 varchar  (255) ,
    REFNAME4 varchar  (255) ,
    REFNAME5 varchar  (255) ,
    LINKPARAM varchar  (500) ,
    PRINTFILEID varchar  (200) ,
    PRINTFILENAME varchar  (200) ,
    PRINTPARAM varchar  (500) ,
    PRINTTYPE varchar  (50) ,
    DELETEFLAG integer ,
    CELLTREEDOTINDEX_ varchar  (50) ,
    DATABASEID_ bigint ,
    DELETETIME timestamp ,
    FLOWID_ varchar  (200) ,
    PAGEID_ varchar  (200) ,
    POSITION_ varchar  (50) ,
    SHOWTYPE varchar  (255) ,
    SYSTEMFLAG_ varchar  (10) ,
    UID_ varchar  (200) ,
    primary key (ID) 
);


 create table USERROLE ( 
    ID varchar  (50)  not null ,
    ROLEID varchar  (255) ,
    USERID varchar  (255) ,
    RSYSID varchar  (50) ,
    RUSERID varchar  (20) ,
    AUTHORIZEFROM varchar  (255) ,
    AUTHORIZED integer ,
    AVAILDATEEND timestamp ,
    AVAILDATESTART timestamp ,
    CREATEBY varchar  (50) ,
    CREATEDATE timestamp ,
    PROCESSDESCRIPTION varchar  (500) ,
    primary key (ID) 
);


 create table SYS_ACCESS ( 
    ROLEID bigint  not null ,
    APPID bigint  not null,
    primary key (ROLEID, APPID) 
);

 create table sys_permission ( 
    roleid bigint  not null ,
    funcid bigint  not null ,
    primary key (roleid, funcid) 
);

 create table SYS_FUNCTION ( 
    ID bigint  not null ,
    APPID bigint ,
    CODE varchar  (50) ,
    FUNCDESC varchar  (500) ,
    FUNCMETHOD varchar  (250) ,
    NAME varchar  (100) ,
    SORT integer ,
    primary key (ID) 
);


 create table SYS_DICTORY ( 
    ID bigint  not null ,
    BLOCKED integer ,
    CODE varchar  (50) ,
    CREATEBY varchar  (50) ,
    CREATEDATE timestamp ,
    DICTDESC varchar  (500) ,
    EXT1 varchar  (255) ,
    EXT10 timestamp ,
    EXT11 bigint ,
    EXT12 bigint ,
    EXT13 bigint ,
    EXT14 bigint ,
    EXT15 bigint ,
    EXT16 double precision ,
    EXT17 double precision ,
    EXT18 double precision ,
    EXT19 double precision ,
    EXT2 varchar  (255) ,
    EXT20 double precision ,
    EXT3 varchar  (255) ,
    EXT4 varchar  (255) ,
    EXT5 timestamp ,
    EXT6 timestamp ,
    EXT7 timestamp ,
    EXT8 timestamp ,
    EXT9 timestamp ,
    NAME varchar  (255) ,
    TYPEID bigint ,
    SORT integer ,
    UPDATEBY varchar  (255) ,
    UPDATEDATE timestamp ,
    VALUE_ varchar  (255) ,
    VALUE varchar  (2000) ,
    CATEGORY varchar  (50) ,
    primary key (ID) 
);


 create table SYS_DICTORY_DEF ( 
    ID bigint  not null ,
    COLUMNNAME varchar  (50) ,
    CREATEBY varchar  (50) ,
    CREATEDATE timestamp ,
    LENGTH integer ,
    NAME varchar  (50) ,
    NODEID bigint ,
    REQUIRED integer ,
    SORT integer ,
    TARGET varchar  (100) ,
    TITLE varchar  (250) ,
    TYPE varchar  (50) ,
    UPDATEBY varchar  (50) ,
    UPDATEDATE timestamp ,
    primary key (ID) 
);


 create table SYS_DBID ( 
    NAME_ varchar  (50)  not null ,
    TITLE_ varchar  (200) ,
    VALUE_ varchar  (500) ,
    VERSION_ integer ,
    primary key (NAME_) 
);


 create table SYS_PROPERTY ( 
    ID_ varchar  (50)  not null ,
    CATEGORY_ varchar  (200) ,
    DESCRIPTION_ varchar  (500) ,
    INITVALUE_ varchar  (1000) ,
    LOCKED_ integer ,
    NAME_ varchar  (50) ,
    TITLE_ varchar  (200) ,
    TYPE_ varchar  (50) ,
    VALUE_ varchar  (1000) ,
    INPUTTYPE_ varchar  (50) ,
    MAXVALUE_ double precision ,
    MINVALUE_ double precision ,
    primary key (ID_) 
);


 create table SYS_WORKCALENDAR ( 
    ID bigint  not null ,
    FREEDAY integer ,
    FREEMONTH integer ,
    FREEYEAR integer ,
    primary key (ID) 
);


 create table SYS_PARAMS ( 
    ID varchar  (50)  not null ,
    BUSINESS_KEY varchar  (200) ,
    DATE_VAL timestamp ,
    DOUBLE_VAL double precision ,
    INT_VAL integer ,
    JAVA_TYPE varchar  (20) ,
    KEY_NAME varchar  (50) ,
    LONG_VAL bigint ,
    SERVICE_KEY varchar  (50) ,
    STRING_VAL varchar  (2000) ,
    TEXT_VAL text ,
    TITLE varchar  (200) ,
    TYPE_CD varchar  (20) ,
    primary key (ID) 
);


 create table SYS_INPUT_DEF ( 
    ID varchar  (50)  not null ,
    INIT_VALUE varchar  (500) ,
    INPUT_TYPE varchar  (50) ,
    JAVA_TYPE varchar  (20) ,
    KEY_NAME varchar  (50) ,
    REQUIRED varchar  (10) ,
    SERVICE_KEY varchar  (50) ,
    TEXT_FIELD varchar  (50) ,
    TITLE varchar  (200) ,
    TYPE_CD varchar  (20) ,
    TYPE_TITLE varchar  (200) ,
    URL varchar  (250) ,
    VALID_TYPE varchar  (50) ,
    VALUE_FIELD varchar  (50) ,
    primary key (ID) 
);


CREATE TABLE sys_key (
        id_ varchar(100) NOT NULL,
        createby_ varchar(50),
        createdate_ TIMESTAMP,
        data_ bytea,
        name_ varchar(50),
        path_ varchar(200),
        title_ varchar(200),
        type_ varchar(50),
        PRIMARY KEY (id_)
);

CREATE TABLE sys_lob (
        id_ varchar(50) NOT NULL,
        businesskey_ varchar(50),
        contenttype_ varchar(50),
        createby_ varchar(50),
        createdate_ TIMESTAMP,
        data_ bytea,
        deleteflag_ INTEGER,
        deviceid_ varchar(20),
        fileid_ varchar(50),
        filename_ varchar(500),
        lastmodified_ BIGINT,
        locked_ INTEGER,
        name_ varchar(50),
        objectid_ varchar(255),
        objectvalue_ varchar(255),
        path_ varchar(500),
        servicekey_ varchar(50),
        size_ BIGINT,
        status_ INTEGER,
        type_ varchar(50),
        PRIMARY KEY (id_)
 );

CREATE TABLE file_history (
        fileid_ varchar(50) NOT NULL,
        createby_ varchar(50),
        createtime_ TIMESTAMP,
        deleteflag_ INTEGER,
        filecontent_ bytea,
        filename_ varchar(200),
        filesize_ INTEGER,
        lastmodified_ BIGINT,
        md5_ varchar(200),
        path_ varchar(500),
        pkgstatus_ varchar(20),
        pkgupdatetime_ TIMESTAMP,
        referid_ varchar(50),
        type_ varchar(50),
        version_ INTEGER,
        PRIMARY KEY (fileid_)
);

CREATE TABLE cell_treedot (
        index_id INTEGER NOT NULL,
        content varchar(255),
        customdata varchar(50),
        file_content bytea,
        file_name varchar(255),
        gid varchar(50),
        id varchar(100),
        imagepath varchar(200),
        index_name varchar(255),
        intmuifrm INTEGER,
        intdel INTEGER,
        intnoshow INTEGER,
        intoperation INTEGER,
        intsystemselect INTEGER,
        intused INTEGER,
        ismode varchar(1),
        issystem INTEGER,
        link_file_content bytea,
        link_file_name varchar(255),
        linktype varchar(50),
        listno INTEGER,
        modetable_id varchar(50),
        nlevel INTEGER,
        nodeico INTEGER,
        num varchar(50),
        parent_id INTEGER,
        picfile varchar(250),
        sindex_name varchar(255),
        type_basetable varchar(50),
        type_index INTEGER,
        type_tablename varchar(50),
        url varchar(500),
        viewtype INTEGER,
        PRIMARY KEY (index_id)
);

CREATE TABLE dotuse (
        fileid varchar(255) NOT NULL,
        cman varchar(20),
        ctime TIMESTAMP,
        dotid varchar(50),
        dwid INTEGER,
        fbid INTEGER,
        file_content bytea,
        file_name varchar(255),
        filesize INTEGER,
        flid varchar(50),
        fname varchar(250),
        fxid INTEGER,
        index_id INTEGER,
        ischeck INTEGER,
        isink varchar(1),
        jid varchar(50),
        name varchar(255),
        num varchar(10),
        old_id varchar(50),
        pid INTEGER,
        projid INTEGER,
        remark varchar(200),
        savetime varchar(100),
        task_id varchar(50),
        topid varchar(50),
        topnode varchar(250),
        type INTEGER,
        vision varchar(100),
        PRIMARY KEY (fileid)
);

CREATE TABLE filedot (
        fileid varchar(255) NOT NULL,
        cman varchar(50),
        content varchar(250),
        ctime TIMESTAMP,
        ctimedname varchar(50),
        dotname varchar(255),
        dottype INTEGER,
        dtime TIMESTAMP,
        dwid INTEGER,
        fbelong varchar(250),
        fbid INTEGER,
        file_content BYTEA,
        file_name varchar(255),
        filesize INTEGER,
        flid varchar(50),
        fname varchar(255),
        fnum varchar(50),
        fxid INTEGER,
        index_id INTEGER,
        intsysform INTEGER,
        isink varchar(1),
        isquan varchar(1),
        jid varchar(50),
        listflag varchar(1),
        listid varchar(50),
        listno INTEGER,
        num varchar(50),
        pbelong varchar(200),
        tofile INTEGER,
        topnode varchar(250),
        type INTEGER,
        utree_index INTEGER,
        PRIMARY KEY (fileid)
);

create index IDX_TREE_NAME on SYS_TREE (NAME);

create index IDX_TREE_CODE on SYS_TREE (CODE);

create index SYS_DEPT_NAME on SYS_DEPARTMENT (NAME);

create index SYS_DEPT_CODE on SYS_DEPARTMENT (CODE);

create index SYS_DEPT_NODE on SYS_DEPARTMENT (NODEID);

create index SYS_APP_NAME on SYS_APPLICATION (NAME);

create index SYS_APP_CODE on SYS_APPLICATION (CODE);

create index SYS_APP_NODE on SYS_APPLICATION (NODEID);

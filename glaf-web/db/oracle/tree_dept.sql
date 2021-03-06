CREATE TABLE
    SYS_TREE
    (
        ID NUMBER(19) NOT NULL,
        ALLOWEDFILEEXTS VARCHAR2(200 CHAR),
        ALLOWEDFIZESIZE NUMBER(10),
        CODE VARCHAR2(50 CHAR),
        CREATEBY VARCHAR2(50 CHAR),
        CREATEDATE TIMESTAMP(6),
        DELETEFLAG NUMBER(10),
        DELETETIME TIMESTAMP(6),
        NODEDESC VARCHAR2(500 CHAR),
        DISCRIMINATOR VARCHAR2(10 CHAR),
        ICON VARCHAR2(50 CHAR),
        ICONCLS VARCHAR2(50 CHAR),
        LOCKED NUMBER(10),
        MOVEABLE VARCHAR2(10 CHAR),
        NAME VARCHAR2(100 CHAR),
        PARENT NUMBER(19),
        PROVIDERCLASS VARCHAR2(100 CHAR),
        SORT NUMBER(10),
        TREEID VARCHAR2(500 CHAR),
        UPDATEBY VARCHAR2(50 CHAR),
        UPDATEDATE TIMESTAMP(6),
        URL VARCHAR2(500 CHAR),
        PRIMARY KEY (ID)
    );

CREATE TABLE
    SYS_DEPARTMENT
    (
        ID NUMBER(19) NOT NULL,
        ADDRESS VARCHAR2(250 CHAR),
        ANOTHERNAME VARCHAR2(200 CHAR),
        CODE VARCHAR2(250 CHAR),
        CODE2 VARCHAR2(250 CHAR),
        CREATEBY VARCHAR2(50 CHAR),
        CREATETIME TIMESTAMP(6),
        DELETEFLAG NUMBER(10),
        DELETETIME TIMESTAMP(6),
        DEPTDESC VARCHAR2(500 CHAR),
        FINCODE VARCHAR2(250 CHAR),
        FORMALFLAG VARCHAR2(1 CHAR),
        DEPTLEVEL NUMBER(10),
        NAME VARCHAR2(200 CHAR),
        DEPTNO VARCHAR2(255 CHAR),
        NODEID NUMBER(19),
        PRINCIPAL VARCHAR2(200 CHAR),
        SHORTNAME VARCHAR2(200 CHAR),
        SORT NUMBER(10),
        STATUS NUMBER(10),
        TELPHONE VARCHAR2(100 CHAR),
        UPDATEBY VARCHAR2(50 CHAR),
        UPDATEDATE TIMESTAMP(6),
        PRIMARY KEY (ID)
    );


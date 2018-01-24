    alter table UserInfo add  ACCOUNTTYPE int;

    alter table UserInfo add  USERTYPE int;

    alter table UserInfo add  LASTLOGINIP nvarchar(200) null;

    alter table UserInfo add  LASTLOGINTIME datetime null;

    alter table UserInfo add  LOCKLOGINTIME datetime null;

    alter table UserInfo add  LOGINRETRY int;

    alter table UserInfo add  UPDATEBY nvarchar(50) null;

    alter table UserInfo add  UPDATEDATE datetime null;

    alter table UserInfo add  APPID nvarchar(200) null;

    alter table UserInfo add  APPSECRET nvarchar(200) null;

    alter table UserInfo add  PASSWORD_HASH nvarchar(250) null;

    alter table UserInfo add  TOKEN nvarchar(250) null;

    alter table UserInfo add  TOKENTIME datetime null;

    alter table UserInfo add  PROJECTID bigint default 0;

    alter table UserInfo add  PWDUPDATEFLAG nvarchar(10) null;

    alter table net_role add  CODE nvarchar(200) null;

    alter table net_role add  INDEXURL nvarchar(500) null;

    alter table net_role add  ISUSEBRANCH nvarchar(10) null;

    alter table net_role add  ISUSEDEPT nvarchar(10) null;

    alter table userrole add AUTHORIZED int;

    alter table userrole add AUTHORIZEFROM nvarchar(200) null;

    alter table userrole add AVAILDATESTART datetime null;

    alter table userrole add AVAILDATEEND datetime null;

    alter table userinfo alter column password nvarchar(50);

    
    IF COL_LENGTH('cell_data_field','formula') IS NULL
    ALTER TABLE cell_data_field ADD formula VARCHAR(1000);

    IF COL_LENGTH('cell_data_field','lgcexpress') IS NULL
    ALTER TABLE cell_data_field ADD lgcexpress VARCHAR(1000);


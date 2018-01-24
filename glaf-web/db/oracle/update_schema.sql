alter table userinfo add INTSENDSMS int default 0;

alter table userinfo add LOGINIP varchar2(100) null;

alter table userinfo add LASTLOGINDATE timestamp null;

alter table userinfo add DUMPFLAG varchar2(10) null;

alter table userinfo add INTVIRTUAL int default 0;

alter table userinfo add INTALLWBS  int default 0;

alter table userinfo add DOMAIN_INDEX int default 0;

alter table userinfo add INTREMOTE int default 0;

alter table userinfo add slevel int default 0;

alter table userinfo add EMAIL_USER varchar2(100) null;

alter table userinfo add EMAIL_PSW varchar2(100) null;

alter table userinfo add computerid varchar2(200) null;

alter table userinfo add isbind varchar2(1) null;

alter table userinfo add note varchar2(50) null;

alter table userinfo add setpruv varchar2(1) null;

alter table userinfo add etime timestamp null;

alter table userinfo add Password varchar2(200) null;
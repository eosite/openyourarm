
    alter table sys_access 
        drop 
        foreign key FK_ACCESS_APP;

    alter table sys_access 
        drop 
        foreign key FKC8152316B6CDCDC6;

    alter table sys_application 
        drop 
        foreign key FK_APP_TREE;

    alter table sys_department 
        drop 
        foreign key FK_DEPT_TREE;

    alter table sys_dept_role 
        drop 
        foreign key FK_DEPTROLE_DEPT;

    alter table sys_dept_role 
        drop 
        foreign key FK_DEPTROLE_ROLE;

    alter table sys_function 
        drop 
        foreign key FK_FUN_APP;

    alter table sys_permission 
        drop 
        foreign key FKC9610A81B6CDCDC6;

    alter table sys_permission 
        drop 
        foreign key FK_PERM_FUN;

    alter table sys_user 
        drop 
        foreign key FK_USER_DEPT;

    alter table sys_user_role 
        drop 
        foreign key FK_USERROLE_ROLE;

    alter table sys_user_role 
        drop 
        foreign key FK_USERROLE_USER;

    drop table if exists sys_access;

    drop table if exists sys_application;

    drop table if exists sys_department;

    drop table if exists sys_dept_role;

    drop table if exists sys_function;

    drop table if exists sys_permission;

    drop table if exists sys_role;

    drop table if exists sys_tree;

    drop table if exists sys_user;

    drop table if exists sys_user_role;

    create table sys_access (
        roleId bigint not null,
        appId bigint not null,
        primary key (roleId, appId)
    );

    create table sys_application (
        id bigint not null auto_increment,
        name varchar(255),
        appDesc varchar(255),
        url varchar(255),
        code varchar(255),
        sort integer,
        showMenu integer,
        nodeId bigint,
        primary key (id)
    );

    create table sys_department (
        id bigint not null auto_increment,
        name varchar(255),
        deptDesc varchar(255),
        createTime datetime,
        sort integer,
        deptNo varchar(255),
        code varchar(255),
        code2 varchar(255),
        status integer,
        fincode varchar(255),
        nodeId bigint,
        primary key (id)
    );

    create table sys_dept_role (
        id bigint not null auto_increment,
        grade integer,
        code varchar(255),
        sort integer,
        deptId bigint,
        sysRoleId bigint not null,
        primary key (id)
    );

    create table sys_function (
        id bigint not null auto_increment,
        name varchar(255),
        funcDesc varchar(255),
        funcMethod varchar(255),
        sort integer,
        appId bigint,
        primary key (id)
    );

    create table sys_permission (
        roleId bigint not null,
        funcId bigint not null,
        primary key (roleId, funcId)
    );

    create table sys_role (
        id bigint not null auto_increment,
        name varchar(255),
        roleDesc varchar(255),
        code varchar(255),
        sort integer,
        primary key (id)
    );

    create table sys_tree (
        id bigint not null auto_increment,
        parent bigint,
        name varchar(255),
        nodeDesc varchar(255),
        discriminator varchar(255),
        cacheFlag varchar(255),
        moveable varchar(255),
        treeId varchar(255),
        sort integer,
        code varchar(255),
        icon varchar(255),
        iconCls varchar(255),
        url varchar(255),
        locked integer,
        primary key (id)
    );

    create table sys_user (
        id bigint not null auto_increment,
        account varchar(255),
        password varchar(255),
        code varchar(255),
        name varchar(255),
        blocked integer,
        createTime datetime,
        lastLoginTime datetime,
        lastLoginIP varchar(255),
        evection integer,
        mobile varchar(255),
        email varchar(255),
        telephone varchar(255),
        gender integer,
        headship varchar(255),
        userType integer,
        fax varchar(255),
        accountType integer,
        dumpFlag integer,
        adminFlag varchar(255),
        superiorIds varchar(255),
        deptId bigint,
        primary key (id)
    );

    create table sys_user_role (
        id bigint not null,
        availDateStart datetime,
        availDateEnd datetime,
        userId bigint not null auto_increment,
        roleId bigint,
        authorized integer,
        authorizeFrom bigint,
        processDescription varchar(255),
        primary key (userId, roleId)
    );

    alter table sys_access 
        add index FK_ACCESS_APP (appId), 
        add constraint FK_ACCESS_APP 
        foreign key (appId) 
        references sys_application (id);

    alter table sys_access 
        add index FKC8152316B6CDCDC6 (roleId), 
        add constraint FKC8152316B6CDCDC6 
        foreign key (roleId) 
        references sys_dept_role (id);

    alter table sys_application 
        add index FK_APP_TREE (nodeId), 
        add constraint FK_APP_TREE 
        foreign key (nodeId) 
        references sys_tree (id);

    alter table sys_department 
        add index FK_DEPT_TREE (nodeId), 
        add constraint FK_DEPT_TREE 
        foreign key (nodeId) 
        references sys_tree (id);

    alter table sys_dept_role 
        add index FK_DEPTROLE_DEPT (deptId), 
        add constraint FK_DEPTROLE_DEPT 
        foreign key (deptId) 
        references sys_department (id);

    alter table sys_dept_role 
        add index FK_DEPTROLE_ROLE (sysRoleId), 
        add constraint FK_DEPTROLE_ROLE 
        foreign key (sysRoleId) 
        references sys_role (id);

    alter table sys_function 
        add index FK_FUN_APP (appId), 
        add constraint FK_FUN_APP 
        foreign key (appId) 
        references sys_application (id);

    alter table sys_permission 
        add index FKC9610A81B6CDCDC6 (roleId), 
        add constraint FKC9610A81B6CDCDC6 
        foreign key (roleId) 
        references sys_dept_role (id);

    alter table sys_permission 
        add index FK_PERM_FUN (funcId), 
        add constraint FK_PERM_FUN 
        foreign key (funcId) 
        references sys_function (id);

    alter table sys_user 
        add index FK_USER_DEPT (deptId), 
        add constraint FK_USER_DEPT 
        foreign key (deptId) 
        references sys_department (id);

    alter table sys_user_role 
        add index FK_USERROLE_ROLE (roleId), 
        add constraint FK_USERROLE_ROLE 
        foreign key (roleId) 
        references sys_dept_role (id);

    alter table sys_user_role 
        add index FK_USERROLE_USER (userId), 
        add constraint FK_USERROLE_USER 
        foreign key (userId) 
        references sys_user (id);

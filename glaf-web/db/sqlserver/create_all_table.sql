/*==============================================================*/
/* Table: proj_BaseInfo                                         */
/*==============================================================*/
if object_id(N'proj_BaseInfo',N'U') is null
create table proj_BaseInfo(
   id 				int				not null,	--主键
   index_id 		int				null,		--treepinfo表主键
   unit_index_id 	int				null,		--add数据库treepinfo主键（单位工程indexid)
   file_name 		varchar(50)		null,		--文件名称
   file_content 	varbinary(max)		 	null,		--文件内容（附件）
   file_content2 	text			null,		--文件内容（文字）
   sType 			varchar(1)		null,		--类型
   constraint PK_PROJ_BASEINFO primary key (id)
);

/*==============================================================*/
/* Table: treepinfoSum                                          */
/*==============================================================*/
if object_id(N'treepinfoSum',N'U') is null
create table treepinfoSum(
   id 						int			not null,	--主键
   index_id 				int			null,		--treepinfo表主键，（如果是分项明细统计，则该键为分项id）
   unit_id 					int			null,		--单位工程id（用于单位工程统计）
   parent_id 				int			null,		--分部id（分项明细统计时用）
   month_cell1_sum 			int			null,		--本月累计启动份数
   month_cell1_page_sum 	int			null,		--本月累计启动页数
   month_cell2_sum 			int			null,		--本月已填写份数
   month_cell2_page_sum 	int			null,		--本月已填写页数
   month_cell3_sum 			int			null,		--本月已报验份数
   month_cell3_page_sum 	int			null,		--本月已报验页数
   month_intpFile1_sum 		int			null,		--本月形成文件份数
   month_intpFile1_page_sum int			null,		--本月形成文件页数
   cell1_sum 				int			null,		--累计启动计划份数
   cell1_page_sum 			int			null,		--累计启动计划页数
   cell2_sum 				int			null,		--累计已填写份数
   cell2_page_sum 			int			null,		--累计已填写页数
   cell3_sum 				int			null,		--累计已报验页数
   cell3_page_sum 			int			null,		--累计已报验份数
   intpFile1_sum 			int			null,		--累计形成文件份数
   intpFile1_page_sum 		int			null,		--累计形成文件页数
   sType 					varchar(1)	null,		--统计类型
   startDate 				datetime	null,		--统计开始时间
   endDate 					datetime	null,		--统计结束时间
   countMonth 				varchar(8)	null,		--统计年月
   constraint PK_TREEPINFOSUM primary key (id)
);


/*==============================================================*/
/* Table: report_base_data                                      */
/*==============================================================*/
if object_id(N'Report_Base_Data',N'U') is null
create table Report_Base_Data (
   id                   int                  not null,
   treepinfo_Id         varchar(150)         null,		--treepinfo表id
   treepinfo_Index_Id   int                  null,		--treepinfo表index_id
   treepinfo_Index_Name varchar(255)         null,		--treepinfo表index_name
   parent_Id			int					 null,		--父节点
   unit_Id              int                  null,		--单位工程id
   subSection_Id        int                  null,		--分部id
   subItem_Id           int                  null,		--分项id
   intpFile1	        int                  null,		--份数
   intpFile2			int					 null,		--已有份数
   intpFile3			int					 null,		--未填份数
   cell1                int                  null,		--页数
   cell2				int					 null,		--已有页数
   cell3				int					 null,		--未填页数
   inspecTime           datetime             null,		--报验时间
   inspecHandleTime     datetime             null,		--处理报验时间
   inspecStatus         varchar(50)          null,		--报验状态
   inspecUser           varchar(20)          null,		--报验责任人
   approveTime          datetime             null,		--审批时间
   finishTime           datetime             null,		--完成时间
   finishStatus			varchar(50)			 null,		--完成状态
   createBy				varchar(50)			 null,
   createDate			datetime			 null,
   updateDate			datetime			 null,
   updateBy				varchar(50)			 null,
   constraint PK_REPORT_BASE_DATA primary key (id)
);

/*==============================================================*/
/* Table: report_flow_info                                      */
/*==============================================================*/
if object_id(N'Report_Flow_Info',N'U') is null
create table Report_Flow_Info (
   id                   int                 not null,
   report_Base_Data_Id  int                 null,			--基础报表id
   flow_Name            varchar(255)        null,			--工作流名称
   taskName				varchar(255)		null,			--任务名称
   flow_Status          varchar(50)         null,			--工作流状态
   flow_Type            int                 null,			--工作流类型
   netroleid			varchar(50)			null,			--当前流程角色id
   userid				varchar(50)			null,			--当前流程用户id
   flow_pre_username	varchar(50)			null,			--上一流程用户名称
   flow_pre_userid		varchar(50)			null,			--上一流程用户id
   flow_pre_netroleid	varchar(50)			null,			--上一流程角色id
   ctime_start			datetime			null,			--流程开启时间/上一流程结束时间
   createBy 			varchar(255)		null,
   createDate 			datetime			null,
   updateDate 			datetime			null,
   updateBy 			varchar(255)		null,
   constraint PK_REPORT_FLOW_INFO primary key (id)
);

if object_id(N'UI_LAYOUT',N'U') is null
CREATE TABLE UI_LAYOUT(
        ID_ nvarchar(50) NOT NULL,
        ACTORID_ nvarchar(255),
        COLUMNSTYLE_ nvarchar(255),
        COLUMNS_ int,
        CREATEDATE_ datetime,
        DATAINDEX_ int,
        NAME_ nvarchar(50) NOT NULL,
        PANELS_ nvarchar(255),
        SPACESTYLE_ nvarchar(50),
        TEMPLATEID_ nvarchar(50),
        TITLE_ nvarchar(255),
        PRIMARY KEY (ID_)
 );

if object_id(N'UI_PANEL',N'U') is null
 CREATE TABLE UI_PANEL (
        ID_ nvarchar(50) NOT NULL,
        ACTORID_ nvarchar(255),
        CLOSE_ int,
        COLLAPSIBLE_ int,
        COLOR_ nvarchar(50),
        COLUMNINDEX_ int,
        CONTENT_ text,
        CREATEDATE_ datetime,
        HEIGHT_ int,
        ICON_ nvarchar(255),
        LINK_ nvarchar(200),
        LOCKED_ int DEFAULT 0,
        MODULEID_ nvarchar(255),
        MODULENAME_ nvarchar(255),
        MORELINK_ nvarchar(200),
        NAME_ nvarchar(50) NOT NULL,
        QUERYID_ nvarchar(200),
        RESIZE_ int,
        STYLE_ nvarchar(200),
        TITLE_ nvarchar(255),
        TYPE_ nvarchar(20),
        WIDTH_ int,
        PRIMARY KEY (ID_)
);

if object_id(N'UI_PANELINSTANCE',N'U') is null
CREATE TABLE UI_PANELINSTANCE (
        ID_ nvarchar(50) NOT NULL,
        NAME_ nvarchar(50),
        PANEL_ nvarchar(50),
        USERPANEL_ nvarchar(50),
        PRIMARY KEY (ID_)
);

if object_id(N'UI_SKIN',N'U') is null
CREATE TABLE UI_SKIN (
        ID_ nvarchar(50) NOT NULL,
        CREATEDATE_ datetime,
        DESCRIPTION_ nvarchar(500),
        IMAGE_ nvarchar(255),
        LOCKED_ int DEFAULT 0,
        NAME_ nvarchar(50) NOT NULL,
        STYLECLASS_ nvarchar(255),
        TITLE_ nvarchar(255),
        PRIMARY KEY (ID_)
);

if object_id(N'UI_SKININSTANCE',N'U') is null
CREATE TABLE UI_SKININSTANCE (
        ID_ nvarchar(50) NOT NULL,
        ACTORID_ nvarchar(255),
        SKIN_ nvarchar(50),
        PRIMARY KEY (ID_)
);

if object_id(N'UI_USERPANEL',N'U') is null
CREATE TABLE UI_USERPANEL (
        ID_ nvarchar(50) NOT NULL,
        ACTORID_ nvarchar(50),
        CREATEDATE_ datetime,
        LAYOUTNAME_ nvarchar(20),
        REFRESHSECONDS_ int,
        LAYOUT_ nvarchar(50),
        PRIMARY KEY (ID_)
);

if object_id(N'UI_USERPORTAL',N'U') is null
CREATE TABLE UI_USERPORTAL (
        ID_ nvarchar(50) NOT NULL,
        ACTORID_ nvarchar(50),
        COLUMNINDEX_ int,
        CREATEDATE_ datetime,
        PANELID_ nvarchar(50),
        POSITION_ int,
        PRIMARY KEY (ID_)
);

if object_id(N'sys_access',N'U') is null
CREATE TABLE sys_access(
        roleid bigint  NOT NULL,
        appid bigint NOT NULL,
        PRIMARY KEY (roleid, appid)
);

if object_id(N'sys_permission',N'U') is null
CREATE TABLE sys_permission (
        roleid bigint NOT NULL,
        funcid bigint NOT NULL,
        PRIMARY KEY (roleid, funcid)
 );

 if object_id(N'sys_application',N'U') is null
CREATE TABLE sys_application(
	id BIGINT NOT NULL,
        name NVARCHAR(255) ,
        code NVARCHAR(255) ,
        appdesc NVARCHAR(255) ,
        url NVARCHAR(2000) ,
        imagepath NVARCHAR(200) ,
        sort INT,
        showmenu INT,
        nodeid BIGINT NOT NULL,
        locked INT DEFAULT 0,
	DELETEFLAG INT DEFAULT 0,
        CREATEBY NVARCHAR(50) ,
        CREATEDATE DATETIME,
        DATABASEID_ BIGINT,
        LINKFILEID NVARCHAR(200) ,
        LINKFILENAME NVARCHAR(200) ,
        LINKPARAM NVARCHAR(500) ,
        LINKTYPE NVARCHAR(50) ,
        PRINTFILEID NVARCHAR(200) ,
        PRINTFILENAME NVARCHAR(200) ,
        PRINTPARAM NVARCHAR(500) ,
        PRINTTYPE NVARCHAR(50) ,
        REFID1 INT,
        REFID2 INT,
        REFID3 INT,
        REFID4 INT,
        REFID5 INT,
        REFNAME1 NVARCHAR(255) ,
        REFNAME2 NVARCHAR(255) ,
        REFNAME3 NVARCHAR(255) ,
        REFNAME4 NVARCHAR(255) ,
        REFNAME5 NVARCHAR(255) ,
        SHOWTYPE NVARCHAR(255) ,
        SYSTEMFLAG_ NVARCHAR(10) ,
        UID_ NVARCHAR(200) ,
        UPDATEBY NVARCHAR(50) ,
        UPDATEDATE DATETIME,
        PAGEID_ NVARCHAR(200) ,
        CELLTREEDOTINDEX_ NVARCHAR(50) ,
        FLOWID_ NVARCHAR(200) ,
        POSITION_ NVARCHAR(50) ,
        PRIMARY KEY (id)
);

 if object_id(N'sys_department',N'U') is null
CREATE TABLE sys_department(
        id bigint not null,
        name nvarchar(255),
        deptdesc nvarchar(255),
        createtime datetime,
        sort int,
        deptno nvarchar(255),
        code nvarchar(255),
        code2 nvarchar(255),
        status int,
	deleteflag int default 0,
        fincode nvarchar(255),
        nodeid bigint not null,
	createby nvarchar(50),
        deptlevel int,
        updateby nvarchar(50),
        updatedate datetime,
        PRIMARY KEY (id)
);

 if object_id(N'sys_function',N'U') is null
CREATE TABLE sys_function(
        id bigint not null,
        name nvarchar(255),
	code nvarchar(50),
        funcdesc nvarchar(255),
        funcmethod nvarchar(255),
        sort int,
        appid bigint NOT NULL,
        PRIMARY KEY (id)
);
 if object_id(N'sys_log',N'U') is null
CREATE TABLE sys_log(
        id bigint not null,
        account nvarchar(255),
        ip nvarchar(255),
        createtime datetime,
        operate nvarchar(255),
        flag int,
        PRIMARY KEY (id)
);

 if object_id(N'sys_tree',N'U') is null
CREATE TABLE sys_tree (
        id bigint not null,
        parent bigint,
        name nvarchar(255),
        nodedesc nvarchar(255),
	cacheFlag nvarchar(1),
	discriminator nvarchar(10),
	moveable nvarchar(1),
	treeId nvarchar(200),
        sort int,
        code nvarchar(255),
	category nvarchar(50),
	icon nvarchar(255),
	iconCls nvarchar(255),
	url nvarchar(2000),
	locked int default 0,
	deleteflag int default 0,
	createby nvarchar(50),
        createdate datetime,
        updateby nvarchar(50),
        updatedate datetime,
        allowedfileexts nvarchar(200),
        allowedfizesize int,
        providerclass nvarchar(100),
        PRIMARY KEY (id)
);

 if object_id(N'sys_dictory',N'U') is null
CREATE TABLE sys_dictory (
        id bigint not null,
        typeId bigint,
        name nvarchar(50),
        dictDesc nvarchar(200),
        code nvarchar(50),
	value_ nvarchar(2000),
        sort int,
        blocked int,
        ext1 nvarchar(200),
        ext2 nvarchar(200),
        ext3 nvarchar(200),
        ext4 nvarchar(200),
        ext5 datetime,
        ext6 datetime,
        ext7 datetime,
        ext8 datetime,
        ext9 datetime,
        ext10 datetime,
        ext11 bigint,
        ext12 bigint,
        ext13 bigint,
        ext14 bigint,
        ext15 bigint,
        ext16 double precision,
        ext17 double precision,
        ext18 double precision,
        ext19 double precision,
        ext20 double precision,
	category nvarchar(50),
        createby nvarchar(50),
        createdate datetime,
        updateby nvarchar(50),
        updatedate datetime,
        PRIMARY KEY (id)
);
 if object_id(N'sys_dictory_def',N'U') is null
create table sys_dictory_def (
        id bigint NOT NULL,
        nodeId bigint,
        name nvarchar(50),
        columnName nvarchar(50),
        title nvarchar(50),
        type nvarchar(50),
        length int,
        sort int,
        required int,
        target nvarchar(50),
	createby nvarchar(50),
        createdate datetime,
        updateby nvarchar(50),
        updatedate datetime,
        PRIMARY KEY (id)
);
 if object_id(N'sys_workcalendar',N'U') is null
CREATE TABLE sys_workcalendar (
        id bigint not null,
        freeday int,
        freemonth int,
        freeyear int,
        PRIMARY KEY (id)
);

 if object_id(N'sys_todo',N'U') is null
CREATE TABLE sys_todo(
        id bigint not null,
        code nvarchar(255),
        content nvarchar(255),
        deptid bigint,
        deptname nvarchar(255),
        enableflag int,
        limitday int,
        xa int,
        xb int,
        link nvarchar(255),
        listlink nvarchar(255),
	allListLink nvarchar(255),
        linktype nvarchar(255),
        appid bigint,
        moduleid bigint,
        modulename nvarchar(255),
        objectid nvarchar(255),
        objectvalue nvarchar(255),
        processname nvarchar(255),
        rolecode nvarchar(255),
        roleid bigint,
        taskname nvarchar(255),
        title nvarchar(255),
        type nvarchar(50),
	provider varchar(50),
        sql_ text,
	sortno int,
        versionno bigint,
        PRIMARY KEY (id)
    );
    
 if object_id(N'sys_todo_instance',N'U') is null
CREATE TABLE sys_todo_instance(
        id bigint not null,
        actorid nvarchar(255),
        actorname nvarchar(255),
        title nvarchar(255),
        content nvarchar(255),
        provider nvarchar(255),
        link_ nvarchar(255),
        linktype nvarchar(255),
        createdate datetime,
        startdate datetime,
        enddate datetime,
        alarmdate datetime,
        pastduedate datetime,
        taskinstanceid nvarchar(255),
        processinstanceid nvarchar(255),
        deptid bigint,
        deptname nvarchar(255),
        roleid bigint,
        rolecode nvarchar(255),
        rowid_ nvarchar(255),
        todoid bigint,
        appid bigint,
        moduleid bigint,
        objectid nvarchar(255),
        objectvalue nvarchar(255),
        versionno bigint,
        PRIMARY KEY (id)
    );
 if object_id(N'sys_scheduler',N'U') is null
  CREATE TABLE sys_scheduler (
    ID nvarchar  (50)  not null ,
    ATTRIBUTE_ nvarchar  (500) ,
    AUTOSTARTUP integer ,
    CONTENT nvarchar  (255) ,
    CREATEBY nvarchar  (50) ,
    CREATEDATE datetime ,
    ENDDATE datetime ,
    EXPRESSION_ nvarchar  (255) ,
    INTERVALTIME nvarchar  (255) ,
    INTERVALTYPE nvarchar  (255) ,
    INTERVALVALUE nvarchar  (255) ,
    JOBCLASS nvarchar  (255) ,
    LOCKED_ integer DEFAULT 0,
    PRIORITY_ integer ,
    REPEATCOUNT integer ,
    REPEATINTERVAL integer ,
    STARTDATE datetime ,
    STARTDELAY integer ,
    STARTUP_ integer ,
    TASKNAME nvarchar  (255) ,
    TASKTYPE nvarchar  (255) ,
    THREADSIZE integer ,
    TITLE nvarchar  (255) ,
    JOBRUNTIME bigint ,
    NEXTFIRETIME datetime ,
    PREVIOUSFIRETIME datetime ,
    RUNSTATUS integer ,
    RUNTYPE integer ,
    METHODNAME nvarchar  (100) ,
    SPRINGBEANID nvarchar  (100) ,
    SPRINGCLASS nvarchar  (255) ,
    primary key (ID) 
    );

 
 if object_id(N'sys_scheduler_execution',N'U') is null
 create table sys_scheduler_execution (
        ID_ bigint,
        SCHEDULERID_ nvarchar(50),
        BUSINESSKEY_ nvarchar(500),
        COUNT_ int,
        VALUE_ double precision,
        RUNYEAR_ int,
        RUNMONTH_ int,
        RUNWEEK_ int,
        RUNQUARTER_ int,
        RUNDAY_ int,
        RUNTIME_ int,
        JOBNO_ nvarchar(50),
        STATUS_ int,
        CREATEBY_ nvarchar(50),
        CREATETIME_ datetime,
        primary key (ID_)
) ;

 if object_id(N'SYS_ACCESS_URI',N'U') is null
create table SYS_ACCESS_URI (
        ID_ bigint  not null,
        URI_ nvarchar(250)  not null,
        LIMIT_ int,
        TOTAL_ int,
        TITLE_ nvarchar(200),
        DESCRIPTION_ nvarchar(500),
        primary key (ID_)
) ;

 if object_id(N'SYS_ACCESS_LOG',N'U') is null
create table SYS_ACCESS_LOG (
        ID_ bigint  not null,
        IP_ nvarchar(250)  not null,
        METHOD_ nvarchar(50),
        URI_ nvarchar(500)  not null,
        URIREFID_ bigint  not null,
        STATUS_ int,
	DAY_ int,
        HOUR_ int,
        MINUTE_ int,
        TIMEMILLIS_ int,
        TYPE_ nvarchar(50),
	USERID_ nvarchar(50),
        ACCESSTIME_ datetime,
        primary key (ID_)
) ;

 if object_id(N'sys_dbid',N'U') is null
create table sys_dbid(
        name_ nvarchar(50)  not null,
	title_ varchar(255),
        value_ nvarchar(255) not null,
        version_ int not null,
        primary key (name_)
);

 if object_id(N'sys_agent',N'U') is null
create table sys_agent (
        ID_ nvarchar(50)  not null,
        AGENTTYPE_ int,
        ASSIGNFROM_ nvarchar(255) ,
        ASSIGNTO_ nvarchar(255) ,
        CREATEDATE_ datetime,
        ENDDATE_ datetime,
        LOCKED_ int DEFAULT 0,
        OBJECTID_ nvarchar(255) ,
        OBJECTVALUE_ nvarchar(255) ,
        PROCESSNAME_ nvarchar(255) ,
        SERVICEKEY_ nvarchar(50) ,
        STARTDATE_ datetime,
        TASKNAME_ nvarchar(255) ,
        PRIMARY KEY (ID_)
 );
 if object_id(N'sys_property',N'U') is null
CREATE TABLE sys_property (
        id_ nvarchar(50) NOT NULL,
        category_ nvarchar(200),
        description_ nvarchar(500),
        initvalue_ nvarchar(1000),
        locked_ int DEFAULT 0,
        name_ nvarchar(50),
        title_ nvarchar(200),
        type_ nvarchar(50),
        value_ nvarchar(1000),
        inputtype_ nvarchar(50),
        maxvalue_ float,
        minvalue_ float,
        PRIMARY KEY (id_)
);

if object_id(N'SYS_MODULE_PROPERTY',N'U') is null
CREATE TABLE SYS_MODULE_PROPERTY (
        ID_ NVARCHAR(250) NOT NULL,
        CATEGORY_ NVARCHAR(50)  NOT NULL,
        CREATEBY_ NVARCHAR(50) ,
        CREATETIME_ DATETIME,
        LOCALE_ NVARCHAR(50) ,
        LOCKED_ INT DEFAULT 0,
        NAME_ NVARCHAR(200)  NOT NULL,
        TITLE_ NVARCHAR(200) ,
        TYPE_ NVARCHAR(50) ,
        UPDATEBY_ NVARCHAR(50) ,
        UPDATETIME_ DATETIME,
        VALUE_ NVARCHAR(2000) ,
        PRIMARY KEY (ID_)
);

 if object_id(N'sys_params',N'U') is null
create table sys_params(
        id nvarchar(50) not null,
        business_key nvarchar(200) not null,
        date_val datetime,
        double_val double precision,
        int_val int,
        java_type nvarchar(20) not null,
        key_name nvarchar(50) not null,
        long_val bigint,
        service_key nvarchar(50) not null,
        string_val nvarchar(2000),
        text_val text,
        title nvarchar(200),
        type_cd nvarchar(20) not null,
        primary key (id)
);

 if object_id(N'sys_input_def',N'U') is null
create table sys_input_def (
        id nvarchar(50) not null,
        init_value nvarchar(500),
        input_type nvarchar(50),
        java_type nvarchar(20) not null,
        key_name nvarchar(50) not null,
        required nvarchar(10),
        service_key nvarchar(50) not null,
        text_field nvarchar(50),
        title nvarchar(200) not null,
        type_cd nvarchar(20) not null,
        type_title nvarchar(200),
        url nvarchar(250),
        valid_type nvarchar(50),
        value_field nvarchar(50),
        primary key (id)
);

 if object_id(N'sys_table',N'U') is null
    create table sys_table (
        tablename_ varchar(50) not null,
        parenttablename_ varchar(50),
        packagename_ varchar(200),
        entityname_ varchar(50),
        classname_ varchar(250),
        title_ varchar(255),
        englishtitle_ varchar(255),
        columnqty_ int,
        addtype_ int,
        sysnum_ varchar(100),
        issubtable_ varchar(2),
        topid_ varchar(50),
        aggregationkeys_ varchar(500),
        queryids_ varchar(500),
        temporaryflag_ varchar(1),
        deletefetch_ varchar(1),
        createtime_ datetime,
        createby_ varchar(50),
        description_ varchar(500),
        type_ varchar(50),
        displaytype_ varchar(50),
        insertcascade_ int,
        updatecascade_ int,
        deletecascade_ int,
        locked_ int DEFAULT 0,
        deleteflag_ int DEFAULT 0,
        systemflag_ varchar(2),
        revision_ int,
        sortno_ int,
        primary key (tablename_)
    );

 if object_id(N'sys_column',N'U') is null
    create table sys_column (
        id_ varchar(100) not null,
        queryid_ varchar(50),
        tablename_ varchar(50),
        targetid_ varchar(50),
        alias_ varchar(50),
        columnname_ varchar(50),
        columnlabel_ varchar(50),
        name_ varchar(50),
        title_ varchar(100),
        englishtitle_ varchar(100),
        length_ int,
        scale_ int,
        precision_ int,
        primarykey_ varchar(10),
        null_ varchar(10),
        frozen_ varchar(10),
        unique_ varchar(10),
        searchable_ varchar(10),
        editable_ varchar(10),
        updateable_ varchar(10),
        resizable_ varchar(10),
        hidden_ varchar(10),
        tooltip_ varchar(100),
        ordinal_ int,
        javatype_ varchar(20),
        inputtype_ varchar(50),
        valuefield_ varchar(50),
        textfield_ varchar(50),
        url_ varchar(250),
        validtype_ varchar(50),
        required varchar(10),
        regex_ varchar(100),
        defaultvalue_ varchar(200),
        discriminator_ varchar(10),
        formula_ varchar(200),
        mask_ varchar(100),
        datacode_ varchar(50),
        rendertype_ varchar(50),
        translator_ varchar(100),
        summarytype_ varchar(50),
        summaryexpr_ varchar(200),
        displaytype_ int,
        sortable_ varchar(10),
        sorttype_ varchar(50),
        systemflag_ varchar(2),
        formatter_ varchar(200),
        align_ varchar(50),
        height_ varchar(50),
        width_ varchar(50),
        link_ varchar(200),
        iscollection_ varchar(10),
        valueexpression_ varchar(200),
        renderer_ varchar(100),
        primary key (id_)
    );

  if object_id(N'sys_key',N'U') is null
    create table sys_key (
        ID_ nvarchar(50),
        TITLE_ nvarchar(200),
        NAME_ nvarchar(50),
        TYPE_ nvarchar(50),
        PATH_ nvarchar(200),
	DATA_ varbinary(max),
        CREATEBY_ nvarchar(50),
        CREATEDATE_ datetime,
        primary key (ID_)
  ) ;

 if object_id(N'SYS_DATABASE',N'U') is null
  CREATE TABLE SYS_DATABASE (
        ID_ BIGINT NOT NULL,
        ACTIVE_ NVARCHAR(10) ,
        CODE_ NVARCHAR(50) ,
        CREATEBY_ NVARCHAR(50) ,
        CREATETIME_ DATETIME,
        DBNAME_ NVARCHAR(50) ,
        HOST_ NVARCHAR(100) ,
        INITFLAG_ NVARCHAR(10) ,
        KEY_ NVARCHAR(1024) ,
        LEVEL_ INT,
        MAPPING_ NVARCHAR(50) ,
        NAME_ NVARCHAR(200) ,
        NODEID_ BIGINT,
        OPERATION_ INT,
        PASSWORD_ NVARCHAR(100) ,
        PORT_ INT,
        PRIORITY_ INT,
        PROVIDERCLASS_ NVARCHAR(100) ,
        REMOTEURL_ NVARCHAR(500) ,
        TITLE_ NVARCHAR(100) ,
        TYPE_ NVARCHAR(50) ,
        UPDATEBY_ NVARCHAR(50) ,
        UPDATETIME_ DATETIME,
        USER_ NVARCHAR(50) ,
        VERIFY_ NVARCHAR(10) ,
        SECTION_ NVARCHAR(50) ,
        DISCRIMINATOR_ NVARCHAR(10) ,
        RUNTYPE_ NVARCHAR(50) ,
        BUCKET_ NVARCHAR(50) ,
        USEYPE_ NVARCHAR(50) ,
        CATALOG_ NVARCHAR(50) ,
        INFOSERVER_ NVARCHAR(50) ,
	SORT_ INT,
        PRIMARY KEY (ID_)
 );

if object_id(N'SYS_GROUP',N'U') is null
 create table SYS_GROUP ( 
    GROUPID nvarchar  (50)  not null ,
    NAME nvarchar  (200) ,
    GROUPDESC nvarchar  (500) ,
    SORT int ,
    TYPE nvarchar  (50) ,
    MANAGEUSERS  nvarchar  (2000) ,
    CREATEBY nvarchar  (50) ,
    CREATEDATE datetime ,
    UPDATEBY nvarchar  (50) ,
    UPDATEDATE datetime ,
    primary key (GROUPID) 
);

if object_id(N'SYS_GROUP_LEADER',N'U') is null
 create table SYS_GROUP_LEADER ( 
    USERID nvarchar  (50)  not null ,
    GROUPID nvarchar  (50) not null ,
    primary key (USERID,GROUPID) 
);

if object_id(N'SYS_GROUP_USER',N'U') is null
 create table SYS_GROUP_USER ( 
    USERID nvarchar  (50)  not null ,
    GROUPID nvarchar  (50) not null ,
    primary key (USERID,GROUPID) 
);

 if object_id(N'SYS_DATA_ITEM_DEF',N'U') is null
    create table SYS_DATA_ITEM_DEF (
        ID_ bigint,
        NODEID_ bigint,
        CATEGORY_ nvarchar(50),
        TITLE_ nvarchar(200),
        CODE_ nvarchar(50),
        TYPE_ nvarchar(50),
        KEYCOLUMN_ nvarchar(100),
        VALUECOLUMN_ nvarchar(100),
        SQL_ nvarchar(4000),
        CREATEBY_ nvarchar(50),
        CREATETIME_ datetime,
        primary key (ID_)
);

 if object_id(N'T_IMAGE',N'U') is null
    CREATE TABLE T_IMAGE (
        ID_ BIGINT NOT NULL,
        CREATEBY_ VARCHAR(50),
        CREATEDATE_ DATETIME,
        DELETEFLAG_ INT DEFAULT 0,
        DESC_ VARCHAR(100) ,
        FILENAME_ VARCHAR(200),
        HEIGHT_ INT,
        IMAGEPATH_ VARCHAR(200),
        LOCKED_ INT DEFAULT 0,
        NAME_ VARCHAR(200),
        PARENTID_ BIGINT,
        TYPE_ VARCHAR(50),
        UPDATEBY_ VARCHAR(50),
        UPDATEDATE_ DATETIME,
        WIDTH_ INT,
        PRIMARY KEY (ID_)
);

if object_id(N'SYS_EXECUTION_LOG',N'U') is null
create table SYS_EXECUTION_LOG (
        ID_ bigint,
        TYPE_ nvarchar(50),
        BUSINESSKEY_ nvarchar(200),
        JOBNO_ nvarchar(250),
        TITLE_ nvarchar(200),
        CONTENT_ nvarchar(500),
        STARTTIME_ datetime,
        ENDTIME_ datetime,
        RUNDAY_ int,
        RUNHOUR_ int,
        RUNTIME_ bigint,
        STATUS_ int,
        EXITCODE_ nvarchar(200),
        EXITMESSAGE_ nvarchar(max),
        CREATEBY_ nvarchar(50),
        CREATETIME_ datetime,
        primary key (ID_)
) ;


/*==============================================================*/
/* Table: UI_THEME                                    */
/*==============================================================*/
if object_id(N'UI_THEME',N'U') is null
create table UI_THEME (
        ID_ int,
        THEMESTYLE_ nvarchar(20),
        LAYOUTMODEL_ nvarchar(20),
        BACKGROUND_ nvarchar(100),
        BACKGROUNDTYPE_ nvarchar(20),
        LOCKED_ int DEFAULT 0,
        CREATEBY_ nvarchar(20),
        CREATEDATE_ datetime,
        primary key (ID_)
) ;

/*==============================================================*/
/* Table: UI_THEME                                    */
/*==============================================================*/
if object_id(N'UI_USER_THEME',N'U') is null
create table UI_USER_THEME (
        ID_ int,
        ACTORID_ nvarchar(20),
        THEMESTYLE_ nvarchar(20),
        LAYOUTMODEL_ nvarchar(20),
        BACKGROUND_ nvarchar(100),
        BACKGROUNDTYPE_ nvarchar(20),
        COURSE_ int,
        CREATEBY_ nvarchar(20),
        CREATEDATE_ datetime,
        primary key (ID_)
) ;

    
    create index IDX_TREE_NAME on SYS_TREE (NAME);

    create index IDX_TREE_CODE on SYS_TREE (CODE);

    create index SYS_DEPT_NAME on SYS_DEPARTMENT (NAME);

    create index SYS_DEPT_CODE on SYS_DEPARTMENT (CODE);

    create index SYS_DEPT_NODE on SYS_DEPARTMENT (NODEID);

    create index SYS_APP_NAME on SYS_APPLICATION (NAME);

    create index SYS_APP_CODE on SYS_APPLICATION (CODE);

    create index SYS_APP_NODE on SYS_APPLICATION (NODEID);

    alter table sys_access 
        add constraint FK_ACCESS_APP 
        foreign key (appId) 
        references sys_application;

    alter table sys_application 
        add constraint FK_APP_TREE 
        foreign key (nodeId) 
        references sys_tree;

    alter table sys_department 
        add constraint FK_DEPT_TREE 
        foreign key (nodeId) 
        references sys_tree;

    alter table sys_function 
        add constraint FK_FUN_APP 
        foreign key (appId) 
        references sys_application;

    alter table sys_permission 
        add constraint FK_PERM_FUN
        foreign key (funcId) 
        references sys_function;

    alter table SYS_ACCESS_URI
          add constraint SYS_ACCESS_URI_UNIQ
          unique (URI_);
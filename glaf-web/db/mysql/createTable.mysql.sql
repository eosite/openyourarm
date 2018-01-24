

 create table NET_ROLE ( 
    ID integer  not null auto_increment ,
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
    CREATEDATE datetime ,
    UPDATEBY varchar  (50) ,
    UPDATEDATE datetime ,
    ISUSEBRANCH varchar  (10) ,
    TYPE varchar  (50) ,
    INDEXURL varchar  (500) ,
    DELETEFLAG integer ,
    DELETETIME datetime ,
  primary key (ID) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


 create table net_roleuse ( 
    id integer  not null ,
    roleid integer ,
    celltreedot_index integer ,
    intuserper integer ,
    primary key (id) 
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


 create table net_roleuse2 ( 
    id integer  not null ,
    roleid integer ,
    celltreedot_index integer ,
    filedot_fileid varchar  (50) ,
    dname varchar  (50) ,
    inttypeofauth integer ,
    intreadorwrite integer ,
    primary key (id) 
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


 create table sys_access ( 
    roleid bigint  not null ,
    appid bigint  not null,
    primary key (roleid, appid) 
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


 create table sys_accessentry ( 
    id_ varchar  (50)  not null ,
    applicationname_ varchar  (255) ,
    createby_ varchar  (255) ,
    createdate_ datetime ,
    editfile_ varchar  (255) ,
    entrytype_ integer ,
    formname_ varchar  (255) ,
    objectid_ varchar  (255) ,
    objectvalue_ varchar  (255) ,
    processdefinitionid_ varchar  (255) ,
    processname_ varchar  (255) ,
    roleid_ bigint ,
    taskname_ varchar  (255) ,
    primary key (id_) 
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


 create table sys_accesspoint ( 
    id_ varchar  (50)  not null ,
    accessentry_ varchar  (255) ,
    accesslevel_ integer ,
    name_ varchar  (255) ,
    primary key (id_) 
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


 create table sys_agent ( 
    id_ varchar  (50)  not null ,
    agenttype_ integer ,
    assignfrom_ varchar  (255) ,
    assignto_ varchar  (255) ,
    createdate_ datetime ,
    enddate_ datetime ,
    locked_ integer ,
    objectid_ varchar  (255) ,
    objectvalue_ varchar  (255) ,
    processname_ varchar  (255) ,
    servicekey_ varchar  (50) ,
    startdate_ datetime ,
    taskname_ varchar  (255) ,
    primary key (id_) 
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


 create table SYS_APPLICATION ( 
    ID bigint  not null ,
    CODE varchar  (50) ,
    CREATEBY varchar  (50) ,
    CREATEDATE datetime ,
    APPDESC varchar  (500) ,
    LOCKED integer ,
    NAME varchar  (250) ,
    NODEID bigint ,
    SHOWMENU integer ,
    SORT integer ,
    UPDATEBY varchar  (50) ,
    UPDATEDATE datetime ,
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
    DELETETIME datetime ,
    FLOWID_ varchar  (200) ,
    PAGEID_ varchar  (200) ,
    POSITION_ varchar  (50) ,
    SHOWTYPE varchar  (255) ,
    SYSTEMFLAG_ varchar  (10) ,
    UID_ varchar  (200) ,
  primary key (ID) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


 create table sys_calendar ( 
    id_ bigint  not null ,
    createby_ varchar  (50) ,
    createdate_ datetime ,
    day_ integer ,
    dutya_ varchar  (50) ,
    dutyb_ varchar  (50) ,
    groupa_ varchar  (50) ,
    groupb_ varchar  (50) ,
    isfreeday_ integer ,
    month_ integer ,
    productionline_ varchar  (50) ,
    week_ integer ,
    workdate_ datetime ,
    year_ integer ,
    primary key (id_) 
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


 create table sys_column ( 
    id_ varchar  (100)  not null ,
    alias_ varchar  (50) ,
    align_ varchar  (50) ,
    columnlabel_ varchar  (50) ,
    columnname_ varchar  (50) ,
    datacode_ varchar  (50) ,
    defaultvalue_ varchar  (200) ,
    discriminator_ varchar  (10) ,
    displaytype_ integer ,
    editable_ varchar  (10) ,
    englishtitle_ varchar  (100) ,
    formatter_ varchar  (200) ,
    formula_ varchar  (200) ,
    frozen_ varchar  (10) ,
    height_ varchar  (50) ,
    hidden_ varchar  (10) ,
    inputtype_ varchar  (50) ,
    iscollection_ varchar  (10) ,
    javatype_ varchar  (20) ,
    length_ integer ,
    link_ varchar  (200) ,
    mask_ varchar  (100) ,
    name_ varchar  (50) ,
    null_ varchar  (10) ,
    ordinal_ integer ,
    precision_ integer ,
    primarykey_ varchar  (10) ,
    queryid_ varchar  (50) ,
    regex_ varchar  (100) ,
    rendertype_ varchar  (50) ,
    renderer_ longtext ,
    required varchar  (10) ,
    resizable_ varchar  (10) ,
    scale_ integer ,
    searchable_ varchar  (10) ,
    sorttype_ varchar  (50) ,
    sortable_ varchar  (10) ,
    summaryexpr_ varchar  (200) ,
    summarytype_ varchar  (50) ,
    systemflag_ varchar  (2) ,
    tablename_ varchar  (50) ,
    targetid_ varchar  (50) ,
    textfield_ varchar  (50) ,
    title_ varchar  (100) ,
    tooltip_ varchar  (100) ,
    translator_ varchar  (100) ,
    unique_ varchar  (10) ,
    updateable_ varchar  (10) ,
    url_ varchar  (250) ,
    validtype_ varchar  (50) ,
    valueexpression_ varchar  (200) ,
    valuefield_ varchar  (50) ,
    width_ varchar  (50) ,
    position_ integer ,
    primary key (id_) 
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


 create table sys_data_field ( 
    id_ varchar  (80)  not null ,
    columnname_ varchar  (50) ,
    createby_ varchar  (50) ,
    createtime_ datetime ,
    datatype_ varchar  (20) ,
    defaultvalue_ varchar  (100) ,
    displaytype_ integer ,
    editable_ varchar  (5) ,
    formatter_ varchar  (50) ,
    formula_ varchar  (100) ,
    frmtype_ varchar  (50) ,
    importtype_ integer ,
    initvalue_ varchar  (200) ,
    inputtype_ varchar  (50) ,
    length_ integer ,
    listweigth_ integer ,
    mask_ varchar  (100) ,
    name_ varchar  (50) ,
    ordinal_ integer ,
    primarykey_ varchar  (5) ,
    queryid_ varchar  (50) ,
    required_ varchar  (5) ,
    searchable_ varchar  (5) ,
    servicekey_ varchar  (50) ,
    sortable_ varchar  (5) ,
    systemflag_ varchar  (5) ,
    tablename_ varchar  (50) ,
    textfield_ varchar  (50) ,
    title_ varchar  (100) ,
    updateable_ varchar  (5) ,
    validtype_ varchar  (50) ,
    valueexpression_ varchar  (100) ,
    valuefield_ varchar  (50) ,
    updateby_ varchar  (50) ,
    updatetime_ datetime ,
    maxvalue_ double ,
    minvalue_ double ,
    placeholder_ varchar  (200) ,
    stepvalue_ double ,
    dataitemid_ bigint ,
    primary key (id_) 
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


 create table sys_data_item ( 
    id_ bigint  not null ,
    createby_ varchar  (50) ,
    createtime_ datetime ,
    name_ varchar  (50) ,
    parameter_ longtext ,
    queryid_ varchar  (100) ,
    querysql_ longtext ,
    textfield_ varchar  (50) ,
    title_ varchar  (100) ,
    treeidfield_ varchar  (50) ,
    treelistnofield_ varchar  (50) ,
    treenamefield_ varchar  (50) ,
    treeparentidfield_ varchar  (50) ,
    treetreeidfield_ varchar  (50) ,
    updateby_ varchar  (50) ,
    updatetime_ datetime ,
    url_ varchar  (500) ,
    valuefield_ varchar  (50) ,
    locked_ integer ,
    cacheflag_ varchar  (5) ,
    type_ varchar  (50) ,
    primary key (id_) 
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;
 
 
 create table SYS_DBID ( 
    NAME_ varchar  (50)  not null ,
    TITLE_ varchar  (200) ,
    VALUE_ varchar  (500) ,
    VERSION_ integer ,
    primary key (NAME_) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


 create table SYS_DEPARTMENT ( 
    ID bigint  not null ,
    CODE varchar  (250) ,
    CODE2 varchar  (250) ,
    CREATEBY varchar  (50) ,
    CREATETIME datetime ,
    DEPTDESC varchar  (500) ,
    FINCODE varchar  (250) ,
    DEPTLEVEL integer ,
    NAME varchar  (200) ,
    DEPTNO varchar  (255) ,
    NODEID bigint ,
    SORT integer ,
    STATUS integer ,
    UPDATEBY varchar  (50) ,
    UPDATEDATE datetime ,
    DELETEFLAG integer ,
    DELETETIME datetime ,
    primary key (ID) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


 create table sys_dictory ( 
    id bigint  not null ,
    blocked integer ,
    category varchar  (50) ,
    code varchar  (50) ,
    createby varchar  (50) ,
    createdate datetime ,
    dictdesc varchar  (500) ,
    ext1 varchar  (255) ,
    ext10 datetime ,
    ext11 bigint ,
    ext12 bigint ,
    ext13 bigint ,
    ext14 bigint ,
    ext15 bigint ,
    ext16 double ,
    ext17 double ,
    ext18 double ,
    ext19 double ,
    ext2 varchar  (255) ,
    ext20 double ,
    ext3 varchar  (255) ,
    ext4 varchar  (255) ,
    ext5 datetime ,
    ext6 datetime ,
    ext7 datetime ,
    ext8 datetime ,
    ext9 datetime ,
    name varchar  (255) ,
    typeid bigint ,
    sort integer ,
    updateby varchar  (255) ,
    updatedate datetime ,
    value_ varchar  (255) ,
    value varchar  (2000) ,
    primary key (id) 
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


 create table SYS_DICTORY_DEF ( 
    ID bigint  not null ,
    COLUMNNAME varchar  (50) ,
    CREATEBY varchar  (50) ,
    CREATEDATE datetime ,
    LENGTH integer ,
    NAME varchar  (50) ,
    NODEID bigint ,
    REQUIRED integer ,
    SORT integer ,
    TARGET varchar  (100) ,
    TITLE varchar  (250) ,
    TYPE varchar  (50) ,
    UPDATEBY varchar  (50) ,
    UPDATEDATE datetime ,
  primary key (ID) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


 create table sys_district ( 
    id_ bigint  not null ,
    code_ varchar  (50) ,
    createby_ varchar  (50) ,
    level_ integer ,
    locked_ integer ,
    name_ varchar  (200) ,
    parentid_ bigint ,
    sortno_ integer ,
    treeid_ varchar  (500) ,
    usetype_ varchar  (50) ,
    primary key (id_) 
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

 
 create table sys_dynamicaccess ( 
    id_ varchar  (50)  not null ,
    entitytype_ varchar  (10) ,
    filtersql_ varchar  (500) ,
    objectid_ varchar  (255) ,
    objectvalue_ varchar  (255) ,
    operation_ varchar  (50) ,
    servicekey_ varchar  (50) ,
    target_ varchar  (50) ,
    targettype_ integer ,
    primary key (id_) 
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;

 
 create table sys_extension ( 
    id bigint  not null ,
    business_key varchar  (200) ,
    date_val datetime ,
    double_val double ,
    int_val integer ,
    key_name varchar  (100) ,
    long_val bigint ,
    service_key varchar  (50) ,
    string_val varchar  (2000) ,
    text_val longtext ,
    type_cd varchar  (6) ,
    primary key (id) 
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


 create table sys_function ( 
    id bigint  not null ,
    appid bigint ,
    code varchar  (50) ,
    funcdesc varchar  (500) ,
    funcmethod varchar  (250) ,
    name varchar  (100) ,
    sort integer ,
    primary key (id) 
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


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
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


 create table sys_lob ( 
    id_ varchar  (50)  not null ,
    businesskey_ varchar  (50) ,
    contenttype_ varchar  (50) ,
    createby_ varchar  (50) ,
    createdate_ datetime ,
    data_ longblob ,
    deleteflag_ integer ,
    deviceid_ varchar  (20) ,
    fileid_ varchar  (50) ,
    filename_ varchar  (500) ,
    lastmodified_ bigint ,
    locked_ integer ,
    name_ varchar  (50) ,
    objectid_ varchar  (255) ,
    objectvalue_ varchar  (255) ,
    path_ varchar  (500) ,
    servicekey_ varchar  (50) ,
    size_ bigint ,
    status_ integer ,
    type_ varchar  (50) ,
    primary key (id_) 
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


 create table sys_log ( 
    id bigint  not null ,
    account varchar  (50) ,
    content varchar  (500) ,
    createtime datetime ,
    flag integer ,
    ip varchar  (100) ,
    moduleid varchar  (50) ,
    operate varchar  (50) ,
    timems integer ,
    primary key (id) 
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;
 

 create table sys_membership ( 
    id_ bigint  not null ,
    actorid_ varchar  (255) ,
    attribute_ varchar  (255) ,
    modifyby_ varchar  (255) ,
    modifydate_ datetime ,
    nodeid_ bigint ,
    objectid_ varchar  (255) ,
    objectvalue_ varchar  (255) ,
    roleid_ bigint ,
    superiorid_ varchar  (255) ,
    type_ varchar  (255) ,
    primary key (id_) 
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


 create table sys_moduleaccess ( 
    id_ varchar  (50)  not null ,
    basecode_ varchar  (100) ,
    objectid_ varchar  (255) ,
    objectvalue_ varchar  (255) ,
    operation_ varchar  (100) ,
    servicekey_ varchar  (50) ,
    target_ varchar  (50) ,
    targettype_ integer ,
    primary key (id_) 
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


 create table SYS_PARAMS ( 
    ID varchar  (50)  not null ,
    BUSINESS_KEY varchar  (200) ,
    DATE_VAL datetime ,
    DOUBLE_VAL double ,
    INT_VAL integer ,
    JAVA_TYPE varchar  (20) ,
    KEY_NAME varchar  (50) ,
    LONG_VAL bigint ,
    SERVICE_KEY varchar  (50) ,
    STRING_VAL varchar  (2000) ,
    TEXT_VAL longtext ,
    TITLE varchar  (200) ,
    TYPE_CD varchar  (20) ,
    primary key (ID) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


 create table sys_permission ( 
    roleid bigint  not null ,
    funcid bigint  not null ,
    primary key (roleid, funcid) 
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


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
    MAXVALUE_ double ,
    MINVALUE_ double ,
  primary key (ID_) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


 create table sys_query ( 
    id_ varchar  (50)  not null ,
    countsql_ longtext ,
    countstatementid_ varchar  (100) ,
    createby_ varchar  (255) ,
    createtime_ datetime ,
    deleteflag_ integer ,
    description_ varchar  (500) ,
    detailurl_ varchar  (250) ,
    dsname_ varchar  (255) ,
    idfield_ varchar  (50) ,
    listurl_ varchar  (250) ,
    locked_ integer ,
    mapping_ varchar  (50) ,
    name_ varchar  (50) ,
    parametertype_ varchar  (200) ,
    parentid_ varchar  (50) ,
    resulttype_ varchar  (200) ,
    revision_ integer ,
    servicekey_ varchar  (50) ,
    sql_ longtext ,
    statementid_ varchar  (100) ,
    targettablename_ varchar  (50) ,
    title_ varchar  (255) ,
    type_ varchar  (50) ,
    nodeid_ bigint ,
    primary key (id_) 
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


 create table sys_repository ( 
    id_ bigint  not null ,
    active_ varchar  (2) ,
    bucket_ varchar  (50) ,
    code_ varchar  (50) ,
    dbname_ varchar  (50) ,
    host_ varchar  (100) ,
    key_ varchar  (1024) ,
    name_ varchar  (200) ,
    nodeid_ bigint ,
    password_ varchar  (100) ,
    port_ integer ,
    providerclass_ varchar  (100) ,
    title_ varchar  (100) ,
    type_ varchar  (50) ,
    user_ varchar  (50) ,
    level_ integer ,
    initflag_ varchar  (2) ,
    verify_ varchar  (2) ,
    priority_ integer ,
    operation_ integer ,
    primary key (id_) 
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


 create table sys_scheduler ( 
    id varchar  (50)  not null ,
    attribute_ varchar  (500) ,
    autostartup integer ,
    content varchar  (255) ,
    createby varchar  (255) ,
    createdate datetime ,
    enddate datetime ,
    expression_ varchar  (255) ,
    intervaltime varchar  (255) ,
    intervaltype varchar  (255) ,
    intervalvalue varchar  (255) ,
    jobclass varchar  (255) ,
    locked_ integer ,
    priority_ integer ,
    repeatcount integer ,
    repeatinterval integer ,
    startdate datetime ,
    startdelay integer ,
    startup_ integer ,
    taskname varchar  (255) ,
    tasktype varchar  (255) ,
    threadsize integer ,
    title varchar  (255) ,
    jobruntime bigint ,
    nextfiretime datetime ,
    previousfiretime datetime ,
    runstatus integer ,
    runtype integer ,
    methodname varchar  (100) ,
    springbeanid varchar  (100) ,
    springclass varchar  (255) ,
    primary key (id) 
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


 create table sys_scheduler_log ( 
    id_ varchar  (50)  not null ,
    content_ varchar  (500) ,
    createby_ varchar  (50) ,
    createdate_ datetime ,
    enddate_ datetime ,
    exitcode_ varchar  (255) ,
    exitmessage_ longtext ,
    jobruntime bigint ,
    startdate_ datetime ,
    status_ integer ,
    taskid_ varchar  (50) ,
    taskname_ varchar  (255) ,
    title_ varchar  (255) ,
    primary key (id_) 
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


 create table sys_scheduler_params ( 
    id varchar  (50)  not null ,
    date_val datetime ,
    double_val double ,
    int_val integer ,
    key_name varchar  (50) ,
    long_val bigint ,
    string_val varchar  (2000) ,
    taskid varchar  (50) ,
    text_val longtext ,
    title varchar  (200) ,
    type_cd varchar  (20) ,
    primary key (id) 
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


 create table sys_step_execution ( 
    step_execution_id integer  not null ,
    commit_count integer ,
    end_time datetime ,
    exit_code varchar  (100) ,
    exit_message varchar  (2500) ,
    filter_count integer ,
    job_class varchar  (200) ,
    job_execution_id integer ,
    job_instance_id integer ,
    job_step_key varchar  (200) ,
    last_updated datetime ,
    listno integer ,
    process_skip_count integer ,
    read_count integer ,
    read_skip_count integer ,
    rollback_count integer ,
    start_time datetime ,
    status varchar  (10) ,
    step_key varchar  (50) ,
    step_name varchar  (100) ,
    version integer ,
    write_count integer ,
    write_skip_count integer ,
    primary key (step_execution_id) 
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


 create table sys_step_execution_context ( 
    step_execution_id integer  not null ,
    serialized_context longtext ,
    short_context varchar  (2500) ,
    primary key (step_execution_id) 
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


 create table sys_table ( 
    tablename_ varchar  (50)  not null ,
    addtype_ integer ,
    aggregationkeys_ varchar  (500) ,
    classname_ varchar  (250) ,
    columnqty_ integer ,
    createby_ varchar  (50) ,
    createtime_ datetime ,
    deletecascade_ integer ,
    deletefetch_ varchar  (1) ,
    deleteflag_ integer ,
    description_ varchar  (500) ,
    displaytype_ varchar  (50) ,
    englishtitle_ varchar  (255) ,
    entityname_ varchar  (50) ,
    insertcascade_ integer ,
    issubtable_ varchar  (2) ,
    locked_ integer ,
    packagename_ varchar  (200) ,
    parenttablename_ varchar  (50) ,
    queryids_ varchar  (500) ,
    revision_ integer ,
    sortno_ integer ,
    sysnum_ varchar  (100) ,
    systemflag_ varchar  (2) ,
    temporaryflag_ varchar  (1) ,
    title_ varchar  (255) ,
    topid_ varchar  (50) ,
    type_ varchar  (50) ,
    updatecascade_ integer ,
    nodeid_ bigint ,
    primary key (tablename_) 
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;




create table SYS_TREE ( 
    ID bigint  not null ,
    CODE varchar  (50) ,
    CREATEBY varchar  (50) ,
    CREATEDATE datetime ,
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
    UPDATEDATE datetime ,
    URL varchar  (500) ,
    DELETEFLAG integer ,
    ALLOWEDFILEEXTS varchar  (200) ,
    ALLOWEDFIZESIZE integer ,
    DELETETIME datetime ,
    PROVIDERCLASS varchar  (100) ,
  primary key (ID) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


 create table sys_userlogin ( 
    id varchar  (50)  not null ,
    remoteaddress varchar  (200) ,
    remotehost varchar  (200) ,
    userid varchar  (20) ,
    logintime datetime ,
    primary key (id) 
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


 create table SYS_WORKCALENDAR ( 
    ID bigint not null ,
    FREEDAY integer ,
    FREEMONTH integer ,
    FREEYEAR integer ,
    primary key (ID) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


create table sys_key (
		id_ varchar(50),
		title_ varchar(200),
		name_ varchar(50),
		type_ varchar(50),
		path_ varchar(200),
		data_ longblob,
		createby_ varchar(50),
		createdate_ datetime,
		primary key (id_)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


 create table ui_layout ( 
    id_ varchar  (50)  not null ,
    actorid_ varchar  (255) ,
    columnstyle_ varchar  (255) ,
    columns_ integer ,
    createdate_ datetime ,
    dataindex_ integer ,
    name_ varchar  (50) ,
    panels_ varchar  (255) ,
    spacestyle_ varchar  (50) ,
    templateid_ varchar  (50) ,
    title_ varchar  (255) ,
    primary key (id_) 
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


 create table ui_panel ( 
    id_ varchar  (50)  not null ,
    actorid_ varchar  (255) ,
    close_ integer ,
    collapsible_ integer ,
    color_ varchar  (50) ,
    columnindex_ integer ,
    content_ varchar  (2000) ,
    createdate_ datetime ,
    height_ integer ,
    icon_ varchar  (255) ,
    link_ varchar  (200) ,
    locked_ integer ,
    moduleid_ varchar  (255) ,
    modulename_ varchar  (255) ,
    morelink_ varchar  (200) ,
    name_ varchar  (50) ,
    queryid_ varchar  (200) ,
    resize_ integer ,
    style_ varchar  (200) ,
    title_ varchar  (255) ,
    type_ varchar  (20) ,
    width_ integer ,
    primary key (id_) 
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


 create table ui_panelinstance ( 
    id_ varchar  (50)  not null ,
    name_ varchar  (50) ,
    panel_ varchar  (50) ,
    userpanel_ varchar  (50) ,
    primary key (id_) 
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


 create table ui_skin ( 
    id_ varchar  (50)  not null ,
    createdate_ datetime ,
    description_ varchar  (500) ,
    image_ varchar  (255) ,
    locked_ integer ,
    name_ varchar  (50) ,
    styleclass_ varchar  (255) ,
    title_ varchar  (255) ,
    primary key (id_) 
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


 create table ui_skininstance ( 
    id_ varchar  (50)  not null ,
    actorid_ varchar  (255) ,
    skin_ varchar  (50) ,
    primary key (id_) 
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


 create table ui_userpanel ( 
    id_ varchar  (50)  not null ,
    actorid_ varchar  (50) ,
    createdate_ datetime ,
    layoutname_ varchar  (20) ,
    refreshseconds_ integer ,
    layout_ varchar  (50) ,
    primary key (id_) 
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


 create table ui_userportal ( 
    id_ varchar  (50)  not null ,
    actorid_ varchar  (50) ,
    columnindex_ integer ,
    createdate_ datetime ,
    panelid_ varchar  (50) ,
    position_ integer ,
    primary key (id_) 
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


 create table USERINFO ( 
    USERID varchar  (20)  not null ,
    USERNAME varchar  (20) ,
    PASSWORD varchar  (10) ,
    CTIME datetime ,
    ETIME datetime ,
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
    LASTLOGINTIME datetime ,
    SUPERIORIDS varchar  (250) ,
    TELEPHONE varchar  (50) ,
    UPDATEBY varchar  (50) ,
    UPDATEDATE datetime ,
    USERTYPE integer ,
    LASTLOGINDATE datetime ,
    LOGINIP varchar  (255) ,
    LOGINRETRY integer ,
    TOKEN varchar  (250) ,
    LOCKLOGINTIME datetime ,
    APPID varchar  (200) ,
    APPSECRET varchar  (200) ,
    PASSWORD_HASH varchar  (500) ,
    TOKENTIME datetime ,
    DELETEFLAG integer ,
    DELETETIME datetime ,
    LOGINSECRET_ varchar  (200) ,
    LOGINSECRETUPDATETIME_ datetime ,
    MQLOGINFLAG_ varchar  (1) ,
    SECRETLOGINFLAG_ varchar  (1) ,
    SYNCFLAG integer ,
    SYNCOPERATORTYPE varchar  (50) ,
    SYNCTIME datetime ,
  primary key (USERID) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


 create table USERROLE ( 
    ID varchar  (50)  not null ,
    ROLEID varchar  (255) ,
    USERID varchar  (255) ,
    RSYSID varchar  (50) ,
    RUSERID varchar  (20) ,
    AUTHORIZEFROM varchar  (255) ,
    AUTHORIZED integer ,
    AVAILDATEEND datetime ,
    AVAILDATESTART datetime ,
    CREATEBY varchar  (50) ,
    CREATEDATE datetime ,
    PROCESSDESCRIPTION varchar  (500) ,
  primary key (ID) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


CREATE TABLE SYS_DATABASE (
        ID_ bigint NOT NULL ,
        ACTIVE_ varchar(10) ,
        CODE_ varchar(50) ,
        CREATEBY_ varchar(50) ,
        CREATETIME_ datetime,
        DBNAME_ varchar(50) ,
        HOST_ varchar(100) ,
        INITFLAG_ varchar(10) ,
        KEY_ varchar(1024) ,
        LEVEL_ integer,
        MAPPING_ varchar(50) ,
        NAME_ varchar(200) ,
        NODEID_ bigint,
        OPERATION_ integer,
        PASSWORD_ varchar(100) ,
        PORT_ integer,
        PRIORITY_ integer,
        PROVIDERCLASS_ varchar(100) ,
        REMOTEURL_ varchar(500) ,
        TITLE_ varchar(100) ,
        TYPE_ varchar(50) ,
        UPDATEBY_ varchar(50) ,
        UPDATETIME_ datetime,
        USER_ varchar(50) ,
        VERIFY_ varchar(10) ,
        SECTION_ varchar(50) ,
        DISCRIMINATOR_ varchar(10) ,
        RUNTYPE_ varchar(50) ,
        BUCKET_ varchar(50) ,
        USEYPE_ varchar(50) ,
        CATALOG_ varchar(50) ,
        INFOSERVER_ varchar(50) ,
	SORT_ integer,
        PRIMARY KEY (ID_)
 )ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


CREATE TABLE SYS_DATABASE_ACCESS(
        ID_ BIGINT NOT NULL,
        ACTORID_ VARCHAR(50),
        DATABASEID_ BIGINT,
        PRIMARY KEY (ID_)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


 create table user_online ( 
    id_ bigint  not null ,
    actorid_ varchar  (50) ,
    logindate_ datetime ,
    loginip_ varchar  (100) ,
    name_ varchar  (50) ,
    sessionid_ varchar  (200) ,
    checkdate_ datetime ,
    checkdatems_ bigint ,
    primary key (id_) 
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


create table USER_ONLINE_LOG ( 
    ID_ bigint  not null ,
    ACTORID_ varchar  (50) ,
    DAY_ integer ,
    LOGINDATE_ datetime ,
    LOGINIP_ varchar  (100) ,
    LOGOUTDATE_ datetime ,
    MONTH_ integer ,
    NAME_ varchar  (50) ,
    QUARTER_ integer ,
    SESSIONID_ varchar  (200) ,
    YEAR_ integer ,
  primary key (ID_) 
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE utf8_bin;


CREATE TABLE form_component (
  ID_ BIGINT NOT NULL,
  BASECOMP_ INT  NULL,
  CATEGORY_ VARCHAR(200)  NULL,
  CREATEBY_ VARCHAR(50)  NULL,
  CREATEDATE_ DATETIME NULL,
  DATAROLE_ VARCHAR(50)  NULL,
  DELETEFLAG_ INT NULL,
  DEPLOYMENTID_ VARCHAR(100)  NULL,
  DESC_ LONGTEXT,
  IMAGEFILENAME_ VARCHAR(200)  NULL,
  IMAGEPATH_ VARCHAR(200)  NULL,
  INTEGRAL_ INT NULL,
  JSENGINE_ VARCHAR(50)  NULL,
  JSPATH_ VARCHAR(200)  NULL,
  KENDOCOMPONENT_ VARCHAR(200)  NULL,
  LOCKED_ INT,
  MEDIUMIMAGEFILENAME_ VARCHAR(200)  NULL,
  MEDIUMIMAGEPATH_ VARCHAR(200)  NULL,
  NAME_ VARCHAR(100)  NULL,
  PARENTID_ BIGINT NULL,
  SMALLIMAGEFILENAME_ VARCHAR(200)  NULL,
  SMALLIMAGEPATH_ VARCHAR(200)  NULL,
  TYPE_ VARCHAR(50)  NULL,
  UPDATEBY_ VARCHAR(50)  NULL,
  UPDATEDATE_ DATETIME NULL,
  VERSION_ INT NULL,
  PRIMARY KEY (ID_)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

 CREATE TABLE form_component_property (
  ID_ BIGINT NOT NULL,
  CATEGORY_ VARCHAR(50)  NULL,
  COMPONENTID_ BIGINT NULL,
  CREATEBY_ VARCHAR(50)  NULL,
  CREATEDATE_ DATETIME  NULL,
  DATATYPE_ VARCHAR(50)  NULL,
  DEFVALUE_ LONGTEXT,
  DELETEFLAG_ INT NULL,
  DEPLOYMENTID_ VARCHAR(100)  NULL,
  DESC_ LONGTEXT,
  EDITOR_ VARCHAR(200)  NULL,
  ENUMVALUE_ VARCHAR(200)  NULL,
  ISSORT_ INT NULL,
  KENDOCOMPONENT_ VARCHAR(200)  NULL,
  KENDOMAPPING_ VARCHAR(200)  NULL,
  LEVEL_ VARCHAR(200)  NULL,
  LISTNO_ INT NULL,
  LOCKED_ INT NULL,
  NAME_ VARCHAR(100)  NULL,
  PVALUE_ LONGTEXT,
  PARENTID_ INT NULL,
  TITLE_ VARCHAR(100)  NULL,
  TYPE_ VARCHAR(200)  NULL,
  UPDATEBY_ VARCHAR(50)  NULL,
  UPDATEDATE_ DATETIME NULL,
  VALIDATOR_ VARCHAR(200)  NULL,
  VALUE_ VARCHAR(200)  NULL,
  VERSION_ INT NULL,
  PRIMARY KEY (ID_)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE form_template (
  ID_ INT(11) NOT NULL,
  COMPONENTID_ INT NULL,
  CREATEBY_ VARCHAR(50)  NULL,
  CREATEDATE_ DATETIME NULL,
  DELETEFLAG_ INT NULL,
  IMAGE_ TINYBLOB,
  IMAGE_EXISTS BIGINT NULL,
  NAME_ VARCHAR(50)  NULL,
  TEMPLATE_ LONGTEXT,
  TYPE_ VARCHAR(50) NULL,
  PRIMARY KEY (ID_)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE form_dict_tree (
  ID BIGINT NOT NULL,
  ALLOWEDFILEEXTS VARCHAR(200)  NULL,
  ALLOWEDFIZESIZE INT NULL,
  CODE VARCHAR(50)  NULL,
  CREATEBY VARCHAR(50)  NULL,
  CREATEDATE DATETIME NULL,
  NODEDESC LONGTEXT ,
  DISCRIMINATOR VARCHAR(10)  NULL,
  ICON VARCHAR(50)  NULL,
  ICONCLS VARCHAR(50)  NULL,
  LOCKED INT NULL,
  MOVEABLE VARCHAR(10)  NULL,
  NAME VARCHAR(100)  NULL,
  PARENT BIGINT NULL,
  PROVIDERCLASS VARCHAR(100)  NULL,
  SORT INT NULL,
  TREEID LONGTEXT,
  UPDATEBY VARCHAR(50)  NULL,
  UPDATEDATE DATETIME NULL,
  url LONGTEXT,
  category VARCHAR(200)  NULL,
  PRIMARY KEY (ID)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE form_dictory (
  ID BIGINT NOT NULL,
  BLOCKED INT NULL,
  CATEGORY VARCHAR(50)  NULL,
  CODE VARCHAR(50)  NULL,
  CREATEBY VARCHAR(50)  NULL,
  CREATEDATE DATETIME  NULL,
  DICTDESC LONGTEXT,
  EXT1 VARCHAR(200)  NULL,
  ext10 DATETIME NULL,
  ext11 BIGINT NULL,
  ext12 BIGINT NULL,
  ext13 BIGINT NULL,
  ext14 BIGINT NULL,
  ext15 BIGINT NULL,
  ext16 DOUBLE NULL,
  ext17 DOUBLE NULL,
  ext18 DOUBLE NULL,
  ext19 DOUBLE NULL,
  EXT2 VARCHAR(200) NULL,
  ext20 DOUBLE NULL,
  EXT3 VARCHAR(200)  NULL,
  EXT4 VARCHAR(200)  NULL,
  EXT5 DATETIME NULL,
  EXT6 DATETIME NULL,
  ext7 DATETIME NULL,
  ext8 DATETIME NULL,
  ext9 DATETIME NULL,
  NAME VARCHAR(50)  NULL,
  TYPEID BIGINT(20) NULL,
  SORT INT NULL,
  UPDATEBY VARCHAR(50) NULL,
  UPDATEDATE DATETIME NULL,
  VALUE_ LONGTEXT,
  VALUE VARCHAR(2000) NULL,
  PRIMARY KEY (ID)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;


CREATE TABLE sys_template (
  templateid_ VARCHAR(50) NOT NULL,
  callbackurl_ VARCHAR(200)  NULL,
  content_ LONGTEXT ,
  createby_ VARCHAR(50)  NULL,
  createdate_ DATETIME  NULL,
  datafile_ VARCHAR(200)  NULL,
  description_ VARCHAR(500)  NULL,
  encoding_ VARCHAR(20)  NULL,
  filesize_ BIGINT(20)  NULL,
  filetype_ INT NULL,
  json_ LONGTEXT ,
  language_ VARCHAR(50)  NULL,
  lastmodified_ BIGINT(20)  NULL,
  locked_ INT NULL,
  moduleid_ VARCHAR(50)  NULL,
  modulename_ VARCHAR(50)  NULL,
  name_ VARCHAR(50)  NULL,
  nodeid_ BIGINT(20) DEFAULT NULL,
  objectid_ VARCHAR(50)  NULL,
  objectvalue_ VARCHAR(200)  NULL,
  systype_ VARCHAR(50)  NULL,
  templatetype_ VARCHAR(50)  NULL,
  title_ VARCHAR(200)  NULL,
  PRIMARY KEY (templateid_)
) ENGINE=INNODB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;



create index IDX_TREE_NAME on SYS_TREE (NAME);

create index IDX_TREE_CODE on SYS_TREE (CODE);

create index SYS_DEPT_NAME on SYS_DEPARTMENT (NAME);

create index SYS_DEPT_CODE on SYS_DEPARTMENT (CODE);

create index SYS_DEPT_NODE on SYS_DEPARTMENT (NODEID);

create index SYS_APP_NAME on SYS_APPLICATION (NAME);

create index SYS_APP_CODE on SYS_APPLICATION (CODE);

create index SYS_APP_NODE on SYS_APPLICATION (NODEID);

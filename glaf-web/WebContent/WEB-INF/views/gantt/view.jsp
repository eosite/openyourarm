<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
  request.setAttribute("contextPath", request.getContextPath());
%>
<!DOCTYPE HTML>
<html>
<head>
  <meta http-equiv="X-UA-Compatible" content="IE=9; IE=8; IE=7; IE=EDGE"/>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
  <title>Teamwork</title>

  <link rel=stylesheet href="${contextPath}/scripts/plugins/bootstrap/gantt/platform.css" type="text/css">
  <link rel=stylesheet href="${contextPath}/scripts/plugins/bootstrap/gantt/libs/dateField/jquery.dateField.css" type="text/css">

  <link rel=stylesheet href="${contextPath}/scripts/plugins/bootstrap/gantt/gantt.css" type="text/css">
  <link rel=stylesheet href="${contextPath}/scripts/plugins/bootstrap/gantt/ganttPrint.css" type="text/css" media="print">

  <link rel=stylesheet href="${contextPath}/scripts/plugins/bootstrap/gantt/gantt.extend.css" type="text/css">
  <script type="text/javascript">
  var contextPath = '${contextPath}';
  var gantt_columns = [{
    "fieldName": "name",
    "fieldCode": "name",
    "dType": "string",
    "title": "项目名称",
    "protoCode": 6,
    "isHidden": "",
    "isEditor": "false",
    "columnWidth":"100px"
  }, {
    "fieldName": "start",
    "fieldCode": "start",
    "dType": "datetime",
    "title": "开始时间",
    "protoCode": 8,
    "isHidden": "",
    "isEditor": "false",
    "columnWidth":"100px"
  }, {
    "fieldName": "end",
    "fieldCode": "end",
    "dType": "datetime",
    "title": "结束时间",
    "protoCode": 5,
    "isHidden": "",
    "isEditor": "false",
    "columnWidth":"100px"
  }, {
    "fieldName": "duration",
    "fieldCode": "duration",
    "dType": "i4",
    "title": "持续时间",
    "protoCode": 7,
    "isHidden": "",
    "isEditor": "false",
    "columnWidth":"100px"
  }, {
    "fieldName": "preTask",
    "fieldCode": "preTask",
    "dType": "string",
    "title": "前置任务",
    "protoCode": 9,
    "isHidden": "",
    "isEditor": "false",
    "columnWidth":"100px"
  }, {
    "fieldName": "id",
    "fieldCode": "c1",
    "dType": "string",
    "title": "id",
    "protoCode": 1,
    "isHidden": "√",
    "isEditor": "false",
    "columnWidth":"100px"
  }, {
    "fieldName": "index_id",
    "fieldCode": "index",
    "dType": "integer",
    "title": "index_id",
    "protoCode": 2,
    "isHidden": "√",
    "isEditor": "false",
    "columnWidth":"100px"
  }, {
    "fieldName": "parent_id",
    "fieldCode": "pId",
    "dType": "integer",
    "title": "parent_id",
    "protoCode": 3,
    "isHidden": "√",
    "isEditor": "false",
    "columnWidth":"100px"
  }, {
    "fieldName": "nlevel",
    "fieldCode": "c4",
    "dType": "integer",
    "title": "nlevel",
    "protoCode": 4,
    "isHidden": "√",
    "isEditor": "false",
    "columnWidth":"100px"
  }];
  var parentObj ,options , canWrite = "false";
  if(parent.$){
      parentObj = parent.$("#${comId}");
      options = parentObj.data("iframegantt")?parentObj.data("iframegantt").options:{};
      canWrite = options.canWrite ;
  }
  
  if(parent.IframeGantt){
    if(!parent.IframeGantt.parameters('${comId}', 'asDemo')){
      gantt_columns = parent.IframeGantt.getColumns('${comId}') || [];
    }
  };
  window.isHoly5 = false;
  window.isHoly6 = false;
  window.isHoly7 = false;
  window.dholidays = "";
  </script>
  <script src="${contextPath}/scripts/plugins/bootstrap/gantt/libs/jquery.min.js"></script>
  <script src="${contextPath}/scripts/plugins/bootstrap/gantt/libs/jquery-ui.min.js"></script>

  <script src="${contextPath}/scripts/plugins/bootstrap/gantt/libs/jquery.livequery.min.js"></script>
  <script src="${contextPath}/scripts/plugins/bootstrap/gantt/libs/jquery.timers.js"></script>
  <script src="${contextPath}/scripts/plugins/bootstrap/gantt/libs/platform.js"></script>
  <script src="${contextPath}/scripts/plugins/bootstrap/gantt/libs/date.js"></script>
  <script src="${contextPath}/scripts/plugins/bootstrap/gantt/libs/i18nJs_cn.js"></script>
  <script src="${contextPath}/scripts/plugins/bootstrap/gantt/libs/dateField/jquery.dateField.js"></script>
  <script src="${contextPath}/scripts/plugins/bootstrap/gantt/libs/JST/jquery.JST.js"></script>
  <script src="${contextPath}/scripts/plugins/bootstrap/gantt/js/jquery.template.extends.js"></script>

  <link rel="stylesheet" type="text/css" href="${contextPath}/scripts/plugins/bootstrap/gantt/libs/jquery.svg.css">
  <script type="text/javascript" src="${contextPath}/scripts/plugins/bootstrap/gantt/libs/jquery.svg.min.js"></script>

  <!--In case of jquery 1.7-->
  <!--<script type="text/javascript" src="libs/jquery.svgdom.pack.js"></script>-->

  <!--In case of jquery 1.8-->
  <script type="text/javascript" src="${contextPath}/scripts/plugins/bootstrap/gantt/libs/jquery.svgdom.1.8.js"></script>

  <script src="${contextPath}/scripts/kendo/utils.js"></script>

  <script src="${contextPath}/scripts/plugins/bootstrap/gantt/ganttUtilities.js"></script>
  <script src="${contextPath}/scripts/plugins/bootstrap/gantt/ganttTask.js"></script>
  <script src="${contextPath}/scripts/plugins/bootstrap/gantt/ganttDrawerSVG.js"></script>
  <!--<script src="ganttDrawer.js"></script>-->
  <script src="${contextPath}/scripts/plugins/bootstrap/gantt/ganttGridEditor.js"></script>
  <script src="${contextPath}/scripts/plugins/bootstrap/gantt/ganttMaster.js"></script>

  <script src="${contextPath}/scripts/plugins/bootstrap/jquery.core.extends.js" type="text/javascript"></script>
  <script src="${contextPath}/scripts/plugins/bootstrap/gantt/js/iframeGantt.js" type="text/javascript"></script>
  <script src="${contextPath}/scripts/plugins/bootstrap/gantt/js/gantt.extend.js" type="text/javascript"></script>
</head>
<body style="background-color: #fff;">
<div id="workSpace" style="padding:0px; overflow-y:auto; overflow-x:hidden;border:1px solid #e5e5e5;position:relative;margin:0 5px"></div>

<div id="taZone" style="display:none;" class="noprint">
   <textarea rows="8" cols="150" id="ta">
     {
        "tasks": [{
          "id": -1,
          "name": "Gantt editor",
          "code": "",
          "level": 0,
          "index": 1,
          "parent": -1,
          "status": "STATUS_ACTIVE",
          "canWrite": true,
          "start": 1396994400000,
          "duration": 21,
          "startIsMilestone": true,
          "endIsMilestone": false,
          "collapsed": false,
          "assigs": [],
          "hasChild": true
        }, {
          "id": -2,
          "name": "coding",
          "code": "",
          "level": 1,
          "index": 2,
          "parent": 1,
          "status": "STATUS_ACTIVE",
          "canWrite": true,
          "start": 1396994400000,
          "duration": 10,
          "startIsMilestone": false,
          "endIsMilestone": false,
          "collapsed": false,
          "assigs": [],
          "description": "",
          "progress": 0,
          "hasChild": true
        }, {
          "id": -3,
          "name": "gantt part",
          "code": "",
          "level": 2,
          "index": 3,
          "parent": 2,
          "status": "STATUS_ACTIVE",
          "canWrite": true,
          "start": 1396994400000,
          "duration": 2,
          "startIsMilestone": false,
          "endIsMilestone": false,
          "collapsed": false,
          "assigs": [],
          "depends": "",
          "hasChild": false
        }, {
          "id": -4,
          "name": "editor part",
          "code": "",
          "level": 2,
          "index": 4,
          "parent": 2,
          "status": "STATUS_SUSPENDED",
          "canWrite": true,
          "start": 1397167200000,
          "duration": 4,
          "startIsMilestone": false,
          "endIsMilestone": false,
          "collapsed": false,
          "assigs": [],
          "depends": "3",
          "hasChild": false
        }, {
          "id": -5,
          "name": "testing",
          "code": "",
          "level": 1,
          "index": 5,
          "parent": 1,
          "status": "STATUS_SUSPENDED",
          "canWrite": true,
          "start": 1398981600000,
          "duration": 6,
          "startIsMilestone": false,
          "endIsMilestone": false,
          "collapsed": false,
          "assigs": [],
          "depends": "2:5",
          "description": "",
          "progress": 0,
          "hasChild": true
        }, {
          "id": -6,
          "name": "test on safari",
          "code": "",
          "level": 2,
          "index": 6,
          "parent": 5,
          "status": "STATUS_SUSPENDED",
          "canWrite": true,
          "start": 1398981600000,
          "duration": 2,
          "startIsMilestone": false,
          "endIsMilestone": false,
          "collapsed": false,
          "assigs": [],
          "depends": "",
          "hasChild": false
        }, {
          "id": -7,
          "name": "test on ie",
          "code": "",
          "level": 2,
          "index": 7,
          "parent": 5,
          "status": "STATUS_SUSPENDED",
          "canWrite": true,
          "start": 1399327200000,
          "duration": 3,
          "startIsMilestone": false,
          "endIsMilestone": false,
          "collapsed": false,
          "assigs": [],
          "depends": "6",
          "hasChild": false
        }, {
          "id": -8,
          "name": "test on chrome",
          "code": "",
          "level": 2,
          "index": 8,
          "parent": 5,
          "status": "STATUS_SUSPENDED",
          "canWrite": true,
          "start": 1399327200000,
          "duration": 2,
          "startIsMilestone": false,
          "endIsMilestone": false,
          "collapsed": false,
          "assigs": [],
          "depends": "6",
          "hasChild": false
        }],
        "selectedRow": 0,
        "canWrite": true,
        "canWriteOnParent": true
      }
   </textarea>

  <button onclick="loadGanttFromServer();">load</button>
</div>

<style>
  .resEdit {
    padding: 15px;
  }

  .resLine {
    width: 95%;
    padding: 3px;
    margin: 5px;
    border: 1px solid #d0d0d0;
  }

  body {
    overflow: hidden;
  }

  .ganttButtonBar h1{
    color: #000000;
    font-weight: bold;
    font-size: 28px;
    margin-left: 10px;
  }

 .loading{
    background-image:url("${contextPath}/scripts/kendoui/styles/BlueOpal/loading.gif");
  }

</style>

<form id="gimmeBack" style="display:none;" action="../gimmeBack.jsp" method="post" target="_blank"><input type="hidden" name="prj" id="gimBaPrj"></form>

<script type="text/javascript">

var ge;  //this is the hugly but very friendly global var for the gantt editor
$(function() {

  //load templates
  //$("#ganttemplates").loadTemplates();
  $('#gantEditorTemplates').loadGanttTemplates();

  // here starts gantt initialization
  ge = new GanttMaster();
  var workSpace = $("#workSpace");
  workSpace.css({width:$(window).width() - 20,height:$(window).height() - 100});
  ge.init(workSpace);

  //inject some buttons (for this demo only)
  //$(".ganttButtonBar div").append("<button onclick='clearGantt();' class='button'>清空</button>")
  //        .append("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
  /*$(".ganttButtonBar h1").html("<a href='http://twproject.com' title='Twproject the friendly project and work management tool' target='_blank'><img width='80%' src='${contextPath}/scripts/plugins/bootstrap/gantt/res/twBanner.jpg'></a>");*/
  //$(".ganttButtonBar div").addClass('buttons');
  //overwrite with localized ones
  loadI18n();

  //simulate a data load from a server.
  loadGanttFromServer();


  //fill default Teamwork roles if any
  if (!ge.roles || ge.roles.length == 0) {
    setRoles();
  }

  //fill default Resources roles if any
  if (!ge.resources || ge.resources.length == 0) {
    setResource();
  }


  /*/debug time scale
  $(".splitBox2").mousemove(function(e){
    var x=e.clientX-$(this).offset().left;
    var mill=Math.round(x/(ge.gantt.fx) + ge.gantt.startMillis)
    $("#ndo").html(x+" "+new Date(mill))
  });*/

  $(window).resize(function(){
    workSpace.css({width:$(window).width() - 1,height:$(window).height() - workSpace.position().top});
    workSpace.trigger("resize.gantt");
  }).oneTime(150,"resize",function(){$(this).trigger("resize")});

});

function convert(datas, dataRole){
  if(!dataRole)
    return;
  $.ajax(ArtTemplateDataUtils.ajax(dataRole))
  .done(function(result) {
    if(result.template){
      datas.cols = gantt_columns;
      //var ret = ArtTemplateDataUtils.convertOne(datas, result.template);
      options.template = result.template;
      options.colsTemplate = gantt_columns ;
      ge.loadProject(/*$.parseJSON(ret)*//*eval("("+ret+")")*/IframeGantt.convertDatas(datas,options),options);
      ge.checkpoint(); //empty the undo stack
      
    }

  })
  .fail(function(XHR, TS) {
    console.log(TS);
  })
  .always(function() {
    parent.IframeGantt.initEvents('${comId}', '${frameId}');
  });
}

function loadGanttFromServer(taskId, callback) {
  var datas;
  if(parent.IframeGantt){
    datas = parent.IframeGantt.parameters('${comId}', 'datas');
  }
  if(datas){
    convert(datas, '${dataRole}');
  }else{
    //this is a simulation: load data from the local storage if you have already played with the demo or a textarea with starting demo data
    loadFromLocalStorage();
  }

  //this is the real implementation
  /*
  //var taskId = $("#taskSelector").val();
  var prof = new Profiler("loadServerSide");
  prof.reset();

  $.getJSON("ganttAjaxController.jsp", {CM:"LOADPROJECT",taskId:taskId}, function(response) {
    //console.debug(response);
    if (response.ok) {
      prof.stop();

      ge.loadProject(response.project);
      ge.checkpoint(); //empty the undo stack

      if (typeof(callback)=="function") {
        callback(response);
      }
    } else {
      jsonErrorHandling(response);
    }
  });
  */
}

function getTasks(){
  var prj = ge.saveProject();
  return prj.tasks;
}

function saveGanttOnServer() {
  if(!ge.canWrite)
    return;


  //this is a simulation: save data to the local storage or to the textarea
  //saveInLocalStorage();

  //var tasks = getTasks();
  var ganttTasks = ge.saveGantt(true,true);
  if (parent.IframeGantt) {
    parent.IframeGantt.updateData('${comId}', ganttTasks);
  }

  /*
  var prj = ge.saveProject();

  delete prj.resources;
  delete prj.roles;

  var prof = new Profiler("saveServerSide");
  prof.reset();

  if (ge.deletedTaskIds.length>0) {
    if (!confirm("TASK_THAT_WILL_BE_REMOVED\n"+ge.deletedTaskIds.length)) {
      return;
    }
  }

  $.ajax("ganttAjaxController.jsp", {
    dataType:"json",
    data: {CM:"SVPROJECT",prj:JSON.stringify(prj)},
    type:"POST",

    success: function(response) {
      if (response.ok) {
        prof.stop();
        if (response.project) {
          ge.loadProject(response.project); //must reload as "tmp_" ids are now the good ones
        } else {
          ge.reset();
        }
      } else {
        var errMsg="Errors saving project\n";
        if (response.message) {
          errMsg=errMsg+response.message+"\n";
        }

        if (response.errorMessages.length) {
          errMsg += response.errorMessages.join("\n");
        }

        alert(errMsg);
      }
    }

  });
  */
}

function deleteCurrentTask(){
  if(parent.IframeGantt){
    //Delete On Server
    parent.IframeGantt.deleteCurrentTask('${comId}', ge.currentTask);
  }
  removeCurrentTask();
}
function removeCurrentTask(){
  $('#workSpace').trigger('deleteCurrentTask.gantt');
}
function removeTasks(tasks){

}


//-------------------------------------------  Create some demo data ------------------------------------------------------
function setRoles() {
  ge.roles = [
    {
      id:"tmp_1",
      name:"Project Manager"
    },
    {
      id:"tmp_2",
      name:"Worker"
    },
    {
      id:"tmp_3",
      name:"Stakeholder/Customer"
    }
  ];
}

function setResource() {
  var res = [];
  for (var i = 1; i <= 10; i++) {
    res.push({id:"tmp_" + i,name:"Resource " + i});
  }
  ge.resources = res;
}


function editResources(){

}

function clearGantt() {
  if(parent.IframeGantt){
    parent.IframeGantt.deleteAll('${comId}', getTasks());
  }
  ge.reset();
}

function loadI18n() {
  GanttMaster.messages = {
    "CANNOT_WRITE":                  "不能写入",
    "CHANGE_OUT_OF_SCOPE":"无权限编辑父节点",
    "START_IS_MILESTONE":"里程碑开始",
    "END_IS_MILESTONE":"里程碑结束",
    "TASK_HAS_CONSTRAINTS":"约束限制",
    "GANTT_ERROR_DEPENDS_ON_OPEN_TASK":"打开任务依赖错误",
    "GANTT_ERROR_DESCENDANT_OF_CLOSED_TASK":"关闭任务依赖错误",
    "TASK_HAS_EXTERNAL_DEPS":"任务有外部依赖",
    "GANTT_ERROR_LOADING_DATA_TASK_REMOVED":"加载数据错误",
    "ERROR_SETTING_DATES":"错误日期",
    "CIRCULAR_REFERENCE":"循环引用",
    "CANNOT_DEPENDS_ON_ANCESTORS":"不能依赖父节点",
    "CANNOT_DEPENDS_ON_DESCENDANTS":"不能依赖子节点",
    "INVALID_DATE_FORMAT":"无效日期类型",
    "TASK_MOVE_INCONSISTENT_LEVEL":"任务移动级别不一致",

    "GANTT_QUARTER_SHORT":"trim.",
    "GANTT_SEMESTER_SHORT":"sem."
  };
}



//-------------------------------------------  Get project file as JSON (used for migrate project from gantt to Teamwork) ------------------------------------------------------
function getFile() {
  $("#gimBaPrj").val(JSON.stringify(ge.saveProject()));
  $("#gimmeBack").submit();
  $("#gimBaPrj").val("");

  /*  var uriContent = "data:text/html;charset=utf-8," + encodeURIComponent(JSON.stringify(prj));
   neww=window.open(uriContent,"dl");*/
}


//-------------------------------------------  LOCAL STORAGE MANAGEMENT (for this demo only) ------------------------------------------------------
Storage.prototype.setObject = function(key, value) {
  this.setItem(key, JSON.stringify(value));
};


Storage.prototype.getObject = function(key) {
  return this.getItem(key) && JSON.parse(this.getItem(key));
};


function loadFromLocalStorage() {
  var ret;
  if (localStorage) {
    if (localStorage.getObject("teamworkGantDemo")) {
      //ret = localStorage.getObject("teamworkGantDemo");
    }
  } else {
    $("#taZone").show();
  }
  if (!ret || !ret.tasks || ret.tasks.length == 0){
    ret = JSON.parse($("#ta").val());


    //actualiza data
    var offset=new Date().getTime()-ret.tasks[0].start;
    for (var i=0;i<ret.tasks.length;i++)
      ret.tasks[i].start=ret.tasks[i].start+offset;


  }
  ge.loadProject(ret);
  ge.checkpoint(); //empty the undo stack
}


function saveInLocalStorage() {
  var prj = ge.saveProject();
  if (localStorage) {
    localStorage.setObject("teamworkGantDemo", prj);
  } else {
    $("#ta").val(JSON.stringify(prj));
  }
}


//-------------------------------------------  Open a black popup for managing resources. This is only an axample of implementation (usually resources come from server) ------------------------------------------------------

function editResources(){

  //make resource editor
  var resourceEditor = $.JST.createFromTemplate({}, "RESOURCE_EDITOR");
  var resTbl=resourceEditor.find("#resourcesTable");

  for (var i=0;i<ge.resources.length;i++){
    var res=ge.resources[i];
    resTbl.append($.JST.createFromTemplate(res, "RESOURCE_ROW"))
  }


  //bind add resource
  resourceEditor.find("#addResource").click(function(){
    resTbl.append($.JST.createFromTemplate({id:"new",name:"resource"}, "RESOURCE_ROW"))
  });

  //bind save event
  resourceEditor.find("#resSaveButton").click(function(){
    var newRes=[];
    //find for deleted res
    for (var i=0;i<ge.resources.length;i++){
      var res=ge.resources[i];
      var row = resourceEditor.find("[resId="+res.id+"]");
      if (row.size()>0){
        //if still there save it
        var name = row.find("input[name]").val();
        if (name && name!="")
          res.name=name;
        newRes.push(res);
      } else {
        //remove assignments
        for (var j=0;j<ge.tasks.length;j++){
          var task=ge.tasks[j];
          var newAss=[];
          for (var k=0;k<task.assigs.length;k++){
            var ass=task.assigs[k];
            if (ass.resourceId!=res.id)
              newAss.push(ass);
          }
          task.assigs=newAss;
        }
      }
    }

    //loop on new rows
    resourceEditor.find("[resId=new]").each(function(){
      var row = $(this);
      var name = row.find("input[name]").val();
      if (name && name!="")
        newRes.push (new Resource("tmp_"+new Date().getTime(),name));
    });

    ge.resources=newRes;

    closeBlackPopup();
    ge.redraw();
  });


  var ndo = createBlackPage(400, 500).append(resourceEditor);
}


</script>


<div id="gantEditorTemplates" style="display:none;">
  <div class="__template__" type="${param.showSysMenu=="true"?"GANTBUTTONS":""}"><!--
  <div class="ganttButtonBar noprint">
    <h1 style="float:left"></h1>
    <div class="buttons">
    <button onclick="$('#workSpace').trigger('undo.gantt');" class="button textual" title="前一步"><span class="teamworkIcon">&#39;</span></button>
    <button onclick="$('#workSpace').trigger('redo.gantt');" class="button textual" title="后一步"><span class="teamworkIcon">&middot;</span></button>
    <span class="ganttButtonSeparator"></span>
    <button onclick="$('#workSpace').trigger('addAboveCurrentTask.gantt');" class="button textual" title="上方插入一行"><span class="teamworkIcon">l</span></button>
    <button onclick="$('#workSpace').trigger('addBelowCurrentTask.gantt');" class="button textual" title="下方插入一行"><span class="teamworkIcon">X</span></button>
    <span class="ganttButtonSeparator"></span>
    <button onclick="$('#workSpace').trigger('indentCurrentTask.gantt');" class="button textual" title="缩进"><span class="teamworkIcon">.</span></button>
    <button onclick="$('#workSpace').trigger('outdentCurrentTask.gantt');" class="button textual" title="后退"><span class="teamworkIcon">:</span></button>
    <span class="ganttButtonSeparator"></span>
    <button onclick="$('#workSpace').trigger('moveUpCurrentTask.gantt');" class="button textual" title="上移"><span class="teamworkIcon">k</span></button>
    <button onclick="$('#workSpace').trigger('moveDownCurrentTask.gantt');" class="button textual" title="下移"><span class="teamworkIcon">j</span></button>
    <span class="ganttButtonSeparator"></span>
    <button onclick="$('#workSpace').trigger('zoomMinus.gantt');" class="button textual" title="缩小"><span class="teamworkIcon">)</span></button>
    <button onclick="$('#workSpace').trigger('zoomPlus.gantt');" class="button textual" title="放大"><span class="teamworkIcon">(</span></button>
    <span class="ganttButtonSeparator"></span>
    <button onclick="deleteCurrentTask();" class="button textual" title="删除行"><span class="teamworkIcon">&cent;</span></button>
    <button onclick="saveGanttOnServer();" class="button textual" title="保存"><span class="teamworkIcon">a</span></button>
    <span class="ganttButtonSeparator"></span>
    <button onclick="print();" class="button textual" title="打印"><span class="teamworkIcon">p</span></button>
    <span class="ganttButtonSeparator"></span>
    <button onclick="ge.gantt.showCriticalPath=!ge.gantt.showCriticalPath; ge.redraw();" class="button textual" title="关键路径"><span class="teamworkIcon">&pound;</span></button>
    &nbsp; &nbsp; &nbsp; &nbsp;
    </div></div>
  --></div>

  <div class="__template__" type="TASKSEDITHEAD"><!--
  <table class="gdfTable" cellspacing="0" cellpadding="0">
    <thead>
    <tr style="height:40px">
      <th class="gdfColHeader" style="width:35px;"></th>
      <th class="gdfColHeader" style="width:25px;"></th>

      {{each columns as col i}}
      {{if col.isHidden==''}}
      <th class="gdfColHeader gdfResizable" style="width:{{col.columnWidth}};">{{col.title}}</th>
      {{/if}}
      {{/each}}

    </tr>
    </thead>
  </table>
  --></div>
  <!-- 
  <th class="gdfCell edit" align="right" style="cursor:pointer;"><span class="taskRowIndex">(#=obj.getRow()+1#)</span> <span class="teamworkIcon" style="font-size:12px;" >e</span></th>
  -->

  <!--
    <td class="gdfCell"  style="width:{{col.columnWidth}};" align="{{col.alignment}}">
        {{if canWrite == "true" && col.isEditor == "true"}}
          <input type="text" name="{{col.fieldCode}}" value="(#=obj.{{col.fieldCode}}?obj.{{col.fieldCode}}:''#)" class="{{col.dType=='datetime'?'date':''}}">(#=parent.IframeGantt?parent.IframeGantt.initButton(obj.{{col.field}}_eventObject,obj.{{col.fieldCode}}):''#)
        {{else if col.dType=='datetime'}}
            <input type="text" name="{{col.fieldCode}}" style='text-align: {{col.alignment}};' value="(#=obj.{{col.fieldCode}}?obj.{{col.fieldCode}}:''#)" (#=canWrite&&{{col.isEditor}}?'':'disabled'#) class="{{col.dType=='datetime'?'date':''}}">(#=parent.IframeGantt?parent.IframeGantt.initButton(obj.{{col.field}}_eventObject,obj.{{col.fieldCode}}):''#)
        {{else}}
            (#=obj.{{col.fieldCode}}?obj.{{col.fieldCode}}:''#)
        {{/if}}
      </td>
  
   -->

  <div class="__template__" type="TASKROW"><!--
  <tr taskId="(#=obj.id#)" class="taskEditRow" level="(#=level#)" taskIndex="(#=index#)" taskParent="(#=parent#)">
    <th class="gdfCell" align="right" style="cursor:pointer;"><span class="taskRowIndex">(#=obj.getRow()+1#)</span> </th>
    <td class="gdfCell noClip" align="center"><div class="taskStatus cvcColorSquare" status="(#=obj.status#)"></div></td>

    {{each columns as col i}}
    {{if col.isHidden != ''}}

    {{else if col.fieldCode=='name'}}

      <td class="gdfCell indentCell" style="padding-left:(#=obj.level*10#)px;width:{{col.columnWidth}};">
        <div class="(#=obj.isParent()?'exp-controller expcoll ':'exp-controller'#)" align="center"></div>
        {{if canWrite == "true" && col.isEditor == "true"}}
          <input type="text" name="name" value="(#=obj.name#)">
        {{else}}
            (#=obj.name#)
        {{/if}}
      </td>

    {{else if col.fieldCode=='preTask'}}
      <td class="gdfCell" style="width:{{col.columnWidth}};">
        <input type="text" name="depends" value="(#=obj.depends#)" (#=obj.hasExternalDep?"readonly":""#)>
      </td>
    {{else}}
      <td class="gdfCell"  style="width:{{col.columnWidth}};" align="{{col.alignment}}">
        {{if canWrite == "true" && col.isEditor == "true"}}
          <input type="text" name="{{col.fieldCode}}" value="(#=obj.{{col.fieldCode}}?{{col.dType=='datetime'?'true':'false'}}?hmtdUtils.format('{0:yyyy-MM-dd}',hmtdUtils.parseDate(obj.{{col.fieldCode}})):obj.{{col.fieldCode}}:''#)" class="{{col.dType=='datetime'?'date':''}}" formatValue='{{col.formatValue}}'>
        {{else if col.dType=='datetime'}}
            <input type="text" name="{{col.fieldCode}}" style='text-align: {{col.alignment}};' value="(#=obj.{{col.fieldCode}}?obj.{{col.fieldCode}}:''#)" (#=canWrite&&{{col.isEditor}}?'':'disabled'#) class="{{col.dType=='datetime'?'date':''}}">
        {{else}}
            (#=obj.{{col.fieldCode}}?obj.{{col.fieldCode}}:''#)
        {{/if}}
      </td>
    {{/if}}
    {{/each}}
  </tr>
  --></div>

  <div class="__template__" type="TASKEMPTYROW"><!--
  <tr class="taskEditRow emptyRow" >
    <th class="gdfCell" align="right"></th>
    <td class="gdfCell noClip" align="center"></td>
    {{each columns as col i}}
        <td class="gdfCell"></td>
    {{/each}}
  </tr>
  --></div>

  <div class="__template__" type="TASKBAR"><!--
  <div class="taskBox taskBoxDiv" taskId="(#=obj.id#)" >
    <div class="layout (#=obj.hasExternalDep?'extDep':''#)">
      <div class="taskStatus" status="(#=obj.status#)"></div>
      <div class="taskProgress" style="width:(#=obj.progress>100?100:obj.progress#)%; background-color:(#=obj.progress>100?'red':'rgb(153,255,51);'#);"></div>
      <div class="milestone (#=obj.startIsMilestone?'active':''#)" ></div>

      <div class="taskLabel"></div>
      <div class="milestone end (#=obj.endIsMilestone?'active':''#)" ></div>
    </div>
  </div>
  --></div>

  <div class="__template__" type="CHANGE_STATUS"><!--
    <div class="taskStatusBox">
      <div class="taskStatus cvcColorSquare" status="STATUS_ACTIVE" title="active"></div>
      <div class="taskStatus cvcColorSquare" status="STATUS_DONE" title="completed"></div>
      <div class="taskStatus cvcColorSquare" status="STATUS_FAILED" title="failed"></div>
      <div class="taskStatus cvcColorSquare" status="STATUS_SUSPENDED" title="suspended"></div>
      <div class="taskStatus cvcColorSquare" status="STATUS_UNDEFINED" title="undefined"></div>
    </div>
  --></div>


  <div class="__template__" type="TASK_EDITOR"><!--
  <div class="ganttTaskEditor">
  <table width="100%">
    <tr>
      <td>
        <table cellpadding="5">
          <tr>
            <td><label for="code">代码</label><br><input type="text" name="code" id="code" value="" class="formElements"></td>
           </tr><tr>
            <td><label for="name">名称</label><br><input type="text" name="name" id="name" value=""  size="35" class="formElements"></td>
          </tr>
          <tr></tr>
            <td>
              <label for="description">描述</label><br>
              <textarea rows="5" cols="30" id="description" name="description" class="formElements"></textarea>
            </td>
          </tr>
        </table>
      </td>
      <td valign="top">
        <table cellpadding="5">
          <tr>
          <td colspan="2"><label for="status">状态</label><br><div id="status" class="taskStatus" status=""></div></td>
          <tr>
          <td colspan="2"><label for="progress">进度</label><br><input type="text" name="progress" id="progress" value="" size="3" class="formElements"></td>
          </tr>
          <tr>
          <td><label for="start">开始</label><br><input type="text" name="start" id="start"  value="" class="date" size="10" class="formElements"><input type="checkbox" id="startIsMilestone"> </td>
          <td rowspan="2" class="graph" style="padding-left:50px"><label for="duration">dur.</label><br><input type="text" name="duration" id="duration" value=""  size="5" class="formElements"></td>
        </tr><tr>
          <td><label for="end">结束</label><br><input type="text" name="end" id="end" value="" class="date"  size="10" class="formElements"><input type="checkbox" id="endIsMilestone"></td>
        </table>
      </td>
    </tr>
    </table>

  <h2>任务分派</h2>
  <table  cellspacing="1" cellpadding="0" width="100%" id="assigsTable">
    <tr>
      <th style="width:100px;">名称</th>
      <th style="width:70px;">角色</th>
      <th style="width:30px;">est.wklg.</th>
      <th style="width:30px;" id="addAssig"><span class="teamworkIcon" style="cursor: pointer">+</span></th>
    </tr>
  </table>

  <div style="text-align: right; padding-top: 20px"><button id="saveButton" class="button big">save</button></div>
  </div>
  --></div>


  <div class="__template__" type="ASSIGNMENT_ROW"><!--
  <tr taskId="(#=obj.task.id#)" assigId="(#=obj.assig.id#)" class="assigEditRow" >
    <td ><select name="resourceId"  class="formElements" (#=obj.assig.id.indexOf("tmp_")==0?"":"disabled"#) ></select></td>
    <td ><select type="select" name="roleId"  class="formElements"></select></td>
    <td ><input type="text" name="effort" value="(#=getMillisInHoursMinutes(obj.assig.effort)#)" size="5" class="formElements"></td>
    <td align="center"><span class="teamworkIcon delAssig" style="cursor: pointer">d</span></td>
  </tr>
  --></div>


  <div class="__template__" type="RESOURCE_EDITOR"><!--
  <div class="resourceEditor" style="padding: 5px;">

    <h2>Project team</h2>
    <table  cellspacing="1" cellpadding="0" width="100%" id="resourcesTable">
      <tr>
        <th style="width:100px;">name</th>
        <th style="width:30px;" id="addResource"><span class="teamworkIcon" style="cursor: pointer">+</span></th>
      </tr>
    </table>

    <div style="text-align: right; padding-top: 20px"><button id="resSaveButton" class="button big">save</button></div>
  </div>
  --></div>


  <div class="__template__" type="RESOURCE_ROW"><!--
  <tr resId="(#=obj.id#)" class="resRow" >
    <td ><input type="text" name="name" value="(#=obj.name#)" style="width:100%;" class="formElements"></td>
    <td align="center"><span class="teamworkIcon delRes" style="cursor: pointer">d</span></td>
  </tr>
  --></div>

  <div class="__template__" type="CUSTOM_ROW"><!--
  <tr cusId="(#=obj.id#)">
    <td>我自己来</td>
  </tr>
  --></div>
</div>


<script type="text/javascript">
  $.JST.loadDecorator("ASSIGNMENT_ROW", function(assigTr, taskAssig) {

    var resEl = assigTr.find("[name=resourceId]");
    for (var i in taskAssig.task.master.resources) {
      var res = taskAssig.task.master.resources[i];
      var opt = $("<option>");
      opt.val(res.id).html(res.name);
      if (taskAssig.assig.resourceId == res.id)
        opt.attr("selected", "true");
      resEl.append(opt);
    }


    var roleEl = assigTr.find("[name=roleId]");
    for (var i in taskAssig.task.master.roles) {
      var role = taskAssig.task.master.roles[i];
      var optr = $("<option>");
      optr.val(role.id).html(role.name);
      if (taskAssig.assig.roleId == role.id)
        optr.attr("selected", "true");
      roleEl.append(optr);
    }

    if(taskAssig.task.master.canWrite && taskAssig.task.canWrite){
      assigTr.find(".delAssig").click(function() {
        var tr = $(this).closest("[assigId]").fadeOut(200, function() {
          $(this).remove();
        });
      });
    }


  });
  $(function(){
    if(parent.IframeGantt){
      parent.IframeGantt.resize('${frameId}',0,parentObj.height(),($.isEmptyObject(options)||options.asDemo)?"10px":"0px");
    }
  });
  function getGanttMasterInst(){
    return ge;
  }
</script>
<script src="${contextPath}/scripts/artTemplate/dist/template.js"></script>
<script src="${contextPath}/scripts/artTemplate/dataUtils.js"></script>
</body>
</html>
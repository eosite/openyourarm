<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib uri="http://www.kendoui.com/jsp/tags" prefix="kendo"%>
<%@ page import="com.glaf.base.utils.*"%>
<%
  String context = request.getContextPath();
  com.glaf.base.utils.ContextUtil.getInstance().setContextPath(
      context);
  pageContext.setAttribute("contextPath", context);
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>编辑转换任务信息</title>
  <%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
  <script type="text/javascript" src="${contextPath}/scripts/glaf-base.js"></script>
  <style scoped>

   .k-textbox {
        width: 18.8em;
    }

    .main-section {
        width: 800px;
        padding: 0;
     }

    label {
        display: inline-block;
        width: 100px;
        text-align: right;
        padding-right: 10px;
    }

    .required {
        font-weight: bold;
    }

    .accept, .status {
        padding-left: 90px;
    }
    .confirm {
        text-align: right;
    }

    .valid {
        color: green;
    }

    .invalid {
        color: red;
    }
    span.k-tooltip {
        margin-left: 6px;
    }
</style>
  <script type="text/javascript">
                
  jQuery(function() {


    var viewModel = kendo.observable({
        "transId_": "${task.transId_}",
        "taskName_": "${task.taskName_}",
        "taskDesc_": "${task.taskDesc_}",
        "commitInterval_": "${task.commitInterval_}",
        "rollbackTransFlag_": "${task.rollbackTransFlag_}",
        "retryFlag_": "${task.retryFlag_}",
        "retryTimes_": "${task.retryTimes_}",
        "onPrePostErrorStop_": "${task.onPrePostErrorStop_}",
        "sendMailFlag_": "${task.sendMailFlag_}",
        "emailAddress_": "${task.emailAddress_}",
        "deleteFlag_": "${task.deleteFlag_}",
        "lastStartTime_": "<fmt:formatDate value='${task.lastStartTime_}' pattern='yyyy-MM-dd HH:mm:ss'/>",
        "lastEndTime_": "<fmt:formatDate value='${task.lastEndTime_}' pattern='yyyy-MM-dd HH:mm:ss'/>",
        "succeed_": "${task.succeed_}",
        "errorStopAutoRun_": "${task.errorStopAutoRun_}",
        "locked_": "${task.locked_}",
         
        "transName":"${transfer.transName}",

        "srcDatabase":"${taskSrc.taskConnId_}",
        "spreSQL_":"${taskSrc.preSQL_}",
        "spostSQL_":"${taskSrc.postSQL_}",
        "querySQL_":"${taskSrc.querySQL_}",
        
        "targetDatabase":"${taskTarget.taskConnId_}",
        "tpreSQL_":"${taskTarget.preSQL_}",
        "tpostSQL_":"${taskTarget.postSQL_}",
        "preTuncateFlag_":"${taskTarget.preTuncateFlag_}",

    });

    kendo.bind(jQuery("#iForm"), viewModel);

  });

  jQuery(document).ready(function() {
        jQuery("#iconButton").kendoButton({
              spriteCssClass: "k-icon"
        });           
  });



  // jQuery(function () {
  //     var container = jQuery("#iForm");
  //     kendo.init(container);
  //     container.kendoValidator({
  //         rules: {
  //               greaterdate: function (input) {
  //                 if (input.is("[data-greaterdate-msg]") && input.val() != "") {                                    
  //                    var date = kendo.parseDate(input.val()),
  //                    otherDate = kendo.parseDate(jQuery("[name='" + input.data("greaterdateField") + "']").val());
  //                    return otherDate == null || otherDate.getTime() < date.getTime();
  //                  }
  //                  return true;
  //               }
  //         }
  //     });
  // });


  function transferParameter(str){
    var params = {};
    if(str && str.length > 0){
      str = str.split("&");
      var s;
      $.each(str, function(i, v){
        s = v.split("=");
        params[s[0]] = s[1];
      });
    }
    return params;
  }



 function save(){
     var form = document.getElementById("iForm");
     var validator = jQuery("#iForm").kendoValidator().data("kendoValidator");
     if (validator.validate()) {

       var link = "<%=request.getContextPath()%>/mx/transferTask/saveTask";
       var str = jQuery("#iForm").formSerialize();
       str = decodeURIComponent(str,true);
       var params = transferParameter(str);

       params.transObj = $("#transName").data("transObj");

       var srcDatabase = $("#srcDatabase").data("kendoDropDownList").dataItem();
       if(srcDatabase){
           srcDatabase.preSQL_ = $("#spreSQL_").val();
           srcDatabase.postSQL_ = $("#spostSQL_").val();
           srcDatabase.querySQL_ = $("#querySQL_").val();
           srcDatabase.srcId_ = $("#srcId_").val();
           
           srcDatabase.taskConnId_ = srcDatabase.id;
           srcDatabase.taskDatabaseName_ = srcDatabase.text;
           if(srcDatabase.text=="默认"){
              srcDatabase.taskConnId_ = 14;
              srcDatabase.taskDatabaseName_ = "glafDB";
           }
           srcDatabase.orderMapping_ = 0;
       }
       params.taskSrc =  srcDatabase;

       var targetDatabase = $("#targetDatabase").data("kendoDropDownList").dataItem();
       if(targetDatabase){
           targetDatabase.preSQL_ = $("#tpreSQL_").val();
           targetDatabase.postSQL_ = $("#tpostSQL_").val();
           targetDatabase.preTuncateFlag_ = $("#preTuncateFlag_").val();
           targetDatabase.taskConnId_ = targetDatabase.id;
           targetDatabase.taskDatabaseName_ = targetDatabase.text;
           if(targetDatabase.text=="默认"){
              targetDatabase.taskConnId_ = 14;
              targetDatabase.taskDatabaseName_ = "glafDB";
           }
       }
       params.taskTraget = targetDatabase;

       str = JSON.stringify(params);

       jQuery.ajax({
           type: "POST",
           url: link,
           dataType:  'json',
           contentType: "application/x-www-form-urlencoded",
           data: {params:str},
           error: function(data){
             alert('服务器处理错误！');
           },
           success: function(data){
             if(data != null && data.message != null){
                  if(data.status = "200"){
                    alert(data.message);
                    window.parent.location.reload();
                  }else{
                    alert(data.message);
                  }
             }
           }
       });
     }
 }


  function openQx(){
        var link = '${contextPath}/mx/etl/model/transfer_task_edit_selecttrans?elementId=transId_&elementName=transName';
        var x=100;
        var y=100;
        if(is_ie) {
          x=document.body.scrollLeft+event.clientX-event.offsetX-200;
          y=document.body.scrollTop+event.clientY-event.offsetY-200;
        }
        openWindow(link,self,x, y, 695, 480);
    }

  var formatDateTime = function (date) {  
    var y = date.getFullYear();  
    var m = date.getMonth() + 1;  
    m = m < 10 ? ('0' + m) : m;  
    var d = date.getDate();  
    d = d < 10 ? ('0' + d) : d;  
    var h = date.getHours();  
    var minute = date.getMinutes();  
    minute = minute < 10 ? ('0' + minute) : minute;  
    var s = date.getSeconds();
    s = s < 10 ?('0' + s):s;
    return y + '-' + m + '-' + d+' '+h+':'+minute+':'+s;  
  };  
 </script>
</head>
<body style="margin-top:0px;">
  <div id="main_content" class="k-content">
    <br>
    <div class="x_content_title">
      <img src="<%=request.getContextPath()%>/images/window.png" alt="编辑转换任务信息">&nbsp;编辑转换任务信息
    </div>
    <br>
    <form id="iForm" name="iForm" method="post" data-role="validator" novalidate="novalidate">
      <input type="hidden" id="id_" name="id_" value="${task.id_}"/>
      

      <table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
        <tr>
          <td width="2%" align="left">&nbsp;</td>
          <td align="left">
            <label for="taskName_" class="required">任务名称&nbsp;</label>
            <input id="taskName_" name="taskName_" type="text" class="k-textbox" data-bind="value: taskName_"   value="${task.taskName_}" validationMessage="请输入任务名称" style="width:380px;" required/>
            <span class="k-invalid-msg" data-for="taskName_"></span>
          </td>
        </tr>
        <tr>
          <td width="2%" align="left">&nbsp;</td>
          <td align="left">
            <label for="taskDesc_" class="required">任务描述&nbsp;</label>
            <textarea id="taskDesc_" name="taskDesc_" rows="12" cols="68" class="k-textbox" style="width:380px;height:80px;" data-bind="value: taskDesc_" value="${task.taskDesc_}" validationMessage="请输入任务描述"></textarea>
            <span class="k-invalid-msg" data-for="taskDesc_"></span>
          </td>
        </tr>
        <tr>
          <td width="2%" align="left">&nbsp;</td>
          <td align="left">
            <label for="transId_" class="required">请选择转换配置&nbsp;</label>

            <input type="hidden" id="transId_" name="transId_" value="${task.transId_}"/>
            <input type="hidden" id="srcId_" name="srcId_"/>
            <input id="transName" name="transName" type="text" class="k-textbox" data-bind="value: transName"
                required validationMessage="请选择转换配置" onclick = "javascript:openQx();" style="width:380px;"/>
            <a href="#" onclick="javascript:openQx();">
              <img src="/glaf/images/search_results.gif" border="0">
            </a>

            <span class="k-invalid-msg" data-for="transId_"></span>
          </td>
        </tr>
      </table>

      <fieldset >
      <legend>来源信息</legend>
      <table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
       <tr>
          <td width="2%" align="left">&nbsp;</td>
          <td align="left">
            <label for="srcDatabase" class="required">源数据库&nbsp;</label>
            <select id="srcDatabase" name="srcDatabase" data-bind="value: srcDatabase" validationMessage="请选择源数据库" required></select>
             <script type="text/javascript">
                  var $srcDatabase = $("#srcDatabase").kendoDropDownList({
                              dataTextField: "text",
                              dataValueField: "id",
                              dataSource: {
                                  transport: {
                                      read: {
                                          dataType: "json",
                                          url:'${contextPath}/rs/isdp/global/databaseJson'
                                      }
                                  }
                              },
                              index: 0,
                              change: function(e){
                                console.log(e.sender.dataItem());
                              }
                  }).data("kendoDropDownList");
              </script>
            <span class="k-invalid-msg" data-for="srcDatabase"></span>
          </td>
        </tr>
        <tr>
          <td width="2%" align="left">&nbsp;</td>
          <td align="left">
            <label for="spreSQL_" class="required">前置SQL&nbsp;</label>
            <textarea id="spreSQL_" name="spreSQL_" rows="12" cols="68" class="k-textbox" style="width:380px;height:40px;" data-bind="value: spreSQL_" validationMessage="请输入前置SQL"></textarea>
            <span class="k-invalid-msg" data-for="spreSQL_"></span>
          </td>
        </tr>
        <tr>
          <td width="2%" align="left">&nbsp;</td>
          <td align="left">
            <label for="spostSQL_" class="required">后置SQL&nbsp;</label>
            <textarea id="spostSQL_" name="spostSQL_" rows="12" cols="68" class="k-textbox" style="width:380px;height:40px;" data-bind="value: spostSQL_" validationMessage="请输入后置SQL"></textarea>
            <span class="k-invalid-msg" data-for="spostSQL_"></span>
          </td>
        </tr>
        <tr>
          <td width="2%" align="left">&nbsp;</td>
          <td align="left">
            <label for="querySQL_" class="required">查询SQL&nbsp;</label>
            <textarea id="querySQL_" name="querySQL_" rows="12" cols="68" class="k-textbox" style="width:380px;height:40px;" data-bind="value: querySQL_" validationMessage="请输入查询SQL"></textarea>
            <span class="k-invalid-msg" data-for="querySQL_"></span>
          </td>
        </tr>
      </table>
      </fieldset>

      <fieldset >
        <legend>目标信息</legend>
        <table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
          <tr>
          <td width="2%" align="left">&nbsp;</td>
          <td align="left">
            <label for="targetDatabase" class="required">目标数据库&nbsp;</label>
            <select id="targetDatabase" name="targetDatabase" data-bind="value: targetDatabase" validationMessage="请选择目标数据库" required></select>
             <script type="text/javascript">
                  var $targetDatabase = $("#targetDatabase").kendoDropDownList({
                              dataTextField: "text",
                              dataValueField: "id",
                              dataSource: {
                                  transport: {
                                      read: {
                                          dataType: "json",
                                          url:'${contextPath}/rs/isdp/global/databaseJson'
                                      }
                                  }
                              },
                              index: 0,
                              change: function(e){
                                console.log(e.sender.dataItem());
                              }
                  }).data("kendoDropDownList");
              </script>
            <span class="k-invalid-msg" data-for="targetDatabase"></span>
          </td>
        </tr>

        <tr>
          <td width="2%" align="left">&nbsp;</td>
          <td align="left">
            <label for="tpreSQL_" class="required">前置SQL&nbsp;</label>
            <textarea id="tpreSQL_" name="tpreSQL_" rows="12" cols="68" class="k-textbox" style="width:380px;height:40px;" data-bind="value: tpreSQL_" validationMessage="请输入前置SQL"></textarea>
            <span class="k-invalid-msg" data-for="tpreSQL_"></span>
          </td>
        </tr>
        <tr>
          <td width="2%" align="left">&nbsp;</td>
          <td align="left">
            <label for="tpostSQL_" class="required">后置SQL&nbsp;</label>
            <textarea id="tpostSQL_" name="tpostSQL_" rows="12" cols="68" class="k-textbox" style="width:380px;height:40px;" data-bind="value: tpostSQL_" validationMessage="请输入后置SQL"></textarea>
            <span class="k-invalid-msg" data-for="tpostSQL_"></span>
          </td>
        </tr>
        <tr>
          <td width="2%" align="left">&nbsp;</td>
          <td align="left">
            <label for="preTuncateFlag_" class="required">执行前清空目标表&nbsp;</label>
            <select id="preTuncateFlag_" name="preTuncateFlag_" class="k-valid" data-bind="value: preTuncateFlag_" validationMessage="执行前清空目标表">
              <option value="">----请选择----</option>
              <option value="1">是</option>
              <option value="0">否</option>
            </select>
            <span class="k-invalid-msg" data-for="preTuncateFlag_"></span>
          </td>
        </tr>
        </table>
      </fieldset>

      
      <table width="95%" align="center" border="0" cellspacing="0" cellpadding="5">
        <tr>
          <td width="2%" align="left">&nbsp;</td>
          <td align="left">
            <label for="commitInterval_" class="required">批量提交数量&nbsp;</label>
            <input id="commitInterval_" name="commitInterval_" type="text" class="k-textbox" data-bind="value: commitInterval_" style="width:380px;" value="${task.commitInterval_}" pattern="^[0-9]+$" validationMessage="请输入数值"/>
            <span class="k-invalid-msg" data-for="commitInterval_"></span>
            <!--<div style="margin-top:5px;">
                <span style="color:red; margin-left:110px;">
                （提示：为了保证系统安全，目标表只能以useradd_、etl_、sync_、tmp_开头。）
                </span>
            </div>-->
          </td>
        </tr>

         <tr>
          <td width="2%" align="left">&nbsp;</td>
          <td align="left">
            <label for="deleteFlag_" class="required">是否有效&nbsp;</label>
            <select id="deleteFlag_" name="deleteFlag_" class="k-valid" data-bind="value: deleteFlag_" value="${task.deleteFlag_}" validationMessage="删除标识" >
              <option value="">----请选择----</option>
              <option value="0">是</option>
              <option value="1">否</option>
            </select>
            <span class="k-invalid-msg" data-for="deleteFlag_"></span>
          </td>
        </tr>
        <tr>
          <td width="2%" align="left">&nbsp;</td>
          <td align="left">
            <label for="rollbackTransFlag_" class="required">启用事务&nbsp;</label>
            <select id="rollbackTransFlag_" name="rollbackTransFlag_" class="k-valid" data-bind="value: rollbackTransFlag_" value="${task.rollbackTransFlag_}" validationMessage="启用事务">
              <option value="">----请选择----</option>
              <option value="1">是</option>
              <option value="0">否</option>
            </select>
            <!--&nbsp;（提示：0 不启用  1 启用  ）-->
            <span class="k-invalid-msg" data-for="rollbackTransFlag_"></span>
          </td>
        </tr>
        
        <tr>
          <td width="2%" align="left">&nbsp;</td>
          <td align="left">
            <label for="onPrePostErrorStop_" class="required">发送错误是否继续&nbsp;</label>
            <select id="onPrePostErrorStop_" name="onPrePostErrorStop_" class="k-valid" data-bind="value: onPrePostErrorStop_" value="${task.onPrePostErrorStop_}" validationMessage="发送错误是否继续">
              <option value="">----请选择----</option>
              <option value="0">是</option>
              <option value="1">否</option>
            </select>
            <span class="k-invalid-msg" data-for="onPrePostErrorStop_"></span>
          </td>
        </tr>
         <tr>
          <td width="2%" align="left">&nbsp;</td>
          <td align="left">
            <label for="errorStopAutoRun_" class="required">出错时停止自动运行&nbsp;</label>
            <select id="errorStopAutoRun_" name="errorStopAutoRun_" class="k-valid" data-bind="value: errorStopAutoRun_" value="${task.errorStopAutoRun_}" validationMessage="出错时停止自动运行">
              <option value="">----请选择----</option>
              <option value="0">否</option>
              <option value="1">是</option>
            </select>
            <span class="k-invalid-msg" data-for="errorStopAutoRun_"></span>
          </td>
        </tr>
        <tr>
          <td width="2%" align="left">&nbsp;</td>
          <td align="left">
            <label for="sendMailFlag_" class="required">发送邮件设置&nbsp;</label>
            <select id="sendMailFlag_" name="sendMailFlag_" class="k-valid" data-bind="value: sendMailFlag_" value="${task.sendMailFlag_}" validationMessage="请输入发送邮件标识">
              <option value="">----请选择----</option>
              <option value="0">不发送</option>
              <option value="1">失败时发送</option>
              <option value="2">成功失败都发送</option>
            </select>
            <span class="k-invalid-msg" data-for="sendMailFlag_"></span>
          </td>
        </tr>
        <tr>
          <td width="2%" align="left">&nbsp;</td>
          <td align="left">
            <label for="emailAddress_" class="required">邮件地址&nbsp;</label>
            <input id="emailAddress_" name="emailAddress_" type="text" class="k-textbox" data-bind="value: emailAddress_" style="width:380px;" value="${task.emailAddress_}" data-email-msg="请输入邮件地址"/>
            <span class="k-invalid-msg" data-for="emailAddress_"></span>
          </td>
        </tr>
  
       <tr>
          <td width="2%" align="left">&nbsp;</td>
          <td align="left">
            <label for="retryFlag_" class="required">是否重试&nbsp;</label>
            <select id="retryFlag_" name="retryFlag_" class="k-valid" data-bind="value: retryFlag_" value="${task.retryFlag_}" validationMessage="请输入重试标识">
              <option value="">----请选择----</option>
              <option value="1">是</option>
              <option value="0">否</option>
            </select>
            <span class="k-invalid-msg" data-for="retryFlag_"></span>
          </td>
        </tr>
        <tr>
          <td width="2%" align="left">&nbsp;</td>
          <td align="left">
            <label for="retryTimes_" class="required">重试次数&nbsp;</label>
            <input id="retryTimes_" name="retryTimes_" type="text" class="k-textbox" data-bind="value: retryTimes_" value="${task.retryTimes_}" pattern="^[0-9]+$" validationMessage="请输入数值" style="width:380px;"/>
            <span class="k-invalid-msg" data-for="retryTimes_"></span>
          </td>
        </tr>
        <tr>
          <td width="2%" align="left">&nbsp;</td>
          <td align="left">
            <label for="lastStartTime_" class="required">上次开始时间&nbsp;</label>
            <input id="lastStartTime_" name="lastStartTime_" type="text" class="k-textbox" data-bind="value: lastStartTime_" style="width:380px;" value="${task.lastStartTime_}" validationMessage="上次开始时间" readonly/>
            <!--
            <input id="lastStartTime_" name="lastStartTime_" data-bind="value: lastStartTime_" type="text" class="k-input" data-role="datetimepicker" data-format="yyyy-MM-dd HH:mm:ss" style="width:380px;" role="combobox" aria-expanded="false" aria-disabled="false" aria-readonly="false" value="<fmt:formatDate value="${task.lastStartTime_}" pattern="yyyy-MM-dd"/>" validationMessage="请输入上次开始时间">
            -->
            <span class="k-invalid-msg" data-for="lastStartTime_"></span>
          </td>
        </tr>
        <tr>
          <td width="2%" align="left">&nbsp;</td>
          <td align="left">
            <label for="lastEndTime_" class="required">上次结束时间&nbsp;</label>
            <input id="lastEndTime_" name="lastEndTime_" type="text" class="k-textbox" data-bind="value: lastEndTime_" style="width:380px;" value="${task.lastEndTime_}" validationMessage="上次结束时间" readonly/>
            <!--
            <input id="lastEndTime_" name="lastEndTime_" data-bind="value: lastEndTime_" type="text" class="k-input" data-role="datetimepicker" data-format="yyyy-MM-dd HH:mm:ss" style="width:380px;" role="combobox" aria-expanded="false" aria-disabled="false" aria-readonly="false" value="<fmt:formatDate value="${task.lastEndTime_}" pattern="yyyy-MM-dd"/>" validationMessage="请输入上次结束时间">
            -->
            <span class="k-invalid-msg" data-for="lastEndTime_"></span>
          </td>
        </tr>
        <tr>
          <td width="2%" align="left">&nbsp;</td>
          <td align="left">
            <label for="succeed_" class="required">是否成功&nbsp;</label>
            <input type="hidden" id="succeed_" name="succeed_" data-bind="value: succeed_" value="${task.succeed_}"/>
            <span id="succeed_span"></span>
            <script>
            if($("#succeed_").val()){
              var value = $("#succeed_").val()==0?"否":"是";
              $("#succeed_span").html(value);
            }
            </script>
            <!--
            <select id="succeed_" name="succeed_" class="k-valid" data-bind="value: succeed_" value="${task.succeed_}" validationMessage="成功标识">
              <option value="0">否</option>
              <option value="1">是</option>
            </select>
            -->
            <span class="k-invalid-msg" data-for="succeed_"></span>
          </td>
        </tr>
       
        <tr>
          <td width="2%" align="left">&nbsp;</td>
          <td align="left">
            <label for="locked_" class="required">是否已锁定&nbsp;</label>
            <input type="hidden" id="locked_" name="locked_" data-bind="value: locked_" value="${task.locked_}"/>
            <span id="locked_span"></span>
            <script>
            if($("#locked_").val()){
              var value = $("#locked_").val()==0?"否":"是";
              $("#locked_span").html(value);
            }
            </script>
            <!--
            <select id="locked_" name="locked_" class="k-valid" data-bind="value: locked_" value="${task.locked_}" validationMessage="锁定标识" readonly>
              <option value="0">否</option>
              <option value="1">是</option>
            </select>
            -->
            <span class="k-invalid-msg" data-for="locked_"></span>
          </td>
        </tr>

        <!-- <tr>
        <td class="input-box2" valign="top">是否有效</td>
        <td>
          <input type="radio" name="status" id="engine1" class="k-radio" value="0" >
          <label class="k-radio-label" for="engine1">有效</label>
          &nbsp;&nbsp;
          <input type="radio" name="status" id="engine2" class="k-radio" value="1" >
          <label class="k-radio-label" for="engine2">无效</label>
        </td>
      </tr>
      -->
      <tr>
        <td colspan="2" align="center" valign="bottom" height="30">
          &nbsp;
          <div>
            <button type="button" id="iconButton"  class="k-button k-primary" style="width: 90px" onclick="javascript:save();">保存</button>
          </div>
        </td>
      </tr>
    </table>
  </form>
</div>
<script>
    jQuery(document).ready(function() {
       //jQuery("#commitInterval_").kendoNumericTextBox();       
       //jQuery("#cREATETIME").kendoDateTimePicker();
       //jQuery("#uPDATETIME").kendoDateTimePicker();
       // jQuery("#lastStartTime_").kendoDateTimePicker();
       // jQuery("#lastEndTime_").kendoDateTimePicker();
          // kendo.bind($("#lastStartTime_"), kendo.observable({
          //     selectedDate: function() {
          //         return kendo.toString(new Date(), "yyyy-MM-dd HH:mm:ss");            
          //     },
          //     isEnabled: true,
          //     isVisible: true,
          //     onChange: function() {}
          //   })
          // );
    });
</script>
</body>
</html>
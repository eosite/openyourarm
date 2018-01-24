<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Document</title>

    <link href="${pageContext.request.contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="${pageContext.request.contextPath}/scripts/metronic/4.5.2/theme/assets/global/css/components.min.css" rel="stylesheet" id="style_components" type="text/css" />
     <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
    
    <!-- select2 -->
    <link href="/glaf/scripts/metronic/4.5.2/theme/assets/global/plugins/select2/css/select2.min.css" rel="stylesheet" type="text/css" />
    <link href="/glaf/scripts/metronic/4.5.2/theme/assets/global/plugins/select2/css/select2-bootstrap.min.css" rel="stylesheet" type="text/css" />

    <!-- iCheck -->
    <link href="/glaf/scripts/metronic/4.5.2/theme/assets/global/plugins/icheck/skins/all.css" rel="stylesheet" type="text/css" />



    <style type="text/css">
        th{
            text-align: center;
        }
    </style>
</head>
<body style='padding:15px !important;'>
    <div class="row form-group">
        <div class="col-xs-3 pull-right" style="text-align:center;">
            <button class="btn btn-sm btn-success" id="sureBtn" style="display:none;"><i class="fa fa-check"></i>确认</button>
        </div>
    </div>
    <form class="form-horizontal" id="baseRuleForm" >
        <div class="row form-group">
            <div class="col-xs-4" title="是否返回并输出错误信息">
                <label class="control-label">显示错误信息：<input type="checkbox" class="isBackError" name="isBackError" ></label>
            </div> 
            <div class="col-xs-4" title="容错可使2,2,3,1的数据在规则2,2,3,3中插入">
                <label class="control-label">允许容错：<input type="checkbox" class="isCanFault" name="isCanFault" ></label>
            </div> 
            <div class="col-xs-4">
                <label class="control-label">允许重复数据：<input type="checkbox" class="isCanRepeat" name="isCanRepeat" ></label>
            </div> 
        </div>   
        <div class="row form-group" >
            <div class="col-xs-4" title="">
                <label class="control-label">树表导入：<input type="checkbox" class="isTree" name="isTree" value="on"></label>
            </div> 
            <div class="icheck-inline col-xs-6 treeRulePanle" style="padding-left: 15px;">
                <label class="">字符串规则<input type="radio" class="ruleType" name="ruleType" value="1" checked></label>
                <label class="">表达式规则<input type="radio" class="ruleType" name="ruleType" value="2"></label>
            </div> 
        </div>  
    </form>
    <div class="treeRulePanle">
        <form id="strRuleForm"> 
            <div class="portlet box strruleportlet" style="background: #F5F5F5;border:1px solid #ccc;">
                <div class="portlet-title" style="min-height: 0px;">
                    <div class="" style="line-height: 30px;color:black">
                        字符串规则
                    </div>
                </div>
                <div class="portlet-body">
                    <div class="form-group row">
                        <label class="control-label col-xs-3">编码层数：</label>
                        <div class="col-xs-8">
                            <input name="idruleGroup" class="idruleGroup form-control" type="number" >
                        </div>
                    </div> 
                    <div class="form-group row has-error">
                        <label class="control-label col-xs-3">编码规则：</label>
                        <div class="col-xs-8">
                            <input title="编码规则使用,(字母)分割,代表编码的层级,如2,2,3,3" name="idruleDivide"  class="form-control idruleDivide" placeholder="编码规则使用,(字母)分割,代表编码的层级,如2,2,3,3">
                            （多组逗号隔开,如0101028028分4组,根据2,2,3,3分,可知父层级01-01-028）
                        </div>
                    </div> 
                </div>
            </div>
        </form>
        <form id="expRuleForm">
            <div class="portlet box expressruleportlet" style="background: #F5F5F5;border:1px solid #ccc;display:none;">
                <div class="portlet-title" style="min-height: 0px;">
                    <div class="" style="line-height: 30px;color:black">
                        表达式规则
                    </div>
                </div>
                <div class="portlet-body">
                    <table class="table table-hover" id="combinedRuleTable">
                        <caption style="border:1px solid #e7ecf1;padding-left:8px"> 
                            <button type="button" id="addButton" class="btn btn-info btn-sm" style=""><i class="glyphicon glyphicon-plus"></i>添加</button>
                        </caption>
                        <thead>
                            <tr>
                                <th style="text-align:center;">层级</th>
                                <th style="text-align:center;">层级规则</th>
                                <th style="text-align:center;">父层级规则</th>
                                <th style="text-align:center;">操作</th>
                            </tr>
                        </thead>
                        <tbody>
                        </tbody>
                    </table>
                </div>
            </div>
        </form>
    </div>
    <button id="sureBtn" type="button" style="display:none">确认</button>

</body>
</html>
<script src="${pageContext.request.contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/scripts/plugins/bootstrap/jquery.core.extends.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<!--validate-->
<script type="text/javascript" src="${pageContext.request.contextPath}/scripts/plugins/bootstrap/login/js/jquery.validate.min.js"></script>
<!-- select2 -->
<script src="${pageContext.request.contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/select2/js/select2.full.min.js" type="text/javascript"></script>

<!-- iCheck -->
<script src="${pageContext.request.contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/icheck/icheck.js" type="text/javascript"></script>

<script type="text/javascript">
    var contextPath = '${contextPath}';

    //数据返回位置
    var nameElementId = '${param.nameElementId}';   //返回的名称
    var objelementId = '${param.objelementId}'; //隐藏域

    var gobal_page_param_ = {
        maxlevel : 0,
    }

    //模板
    var tr_tmpl = '<tr>' 
                + '<td width="20%"> <input name="level" class="level form-control" style="width:100%" readonly> </td>'
                + '<td width="30%"> <input name="levelExp" class="levelExp form-control" style="width:100%" readonly></td>'
                + '<td width="30%"> <input name="parentLevelExp" class="parentLevelExp form-control" style="width:100%" readonly></td>'
                + '<td width="20%" style="text-align:center;">' 
                    + '<button type="button" class="removeRuleBtn btn btn-danger btn-sm" style=""><i class="glyphicon glyphicon-remove"></i>删除</button> '
                + '</td>'
            + '</tr>';

    var $expInput = null;   //点击的表达式所在的Input

    function retExpression(data){
        if($expInput){
            $expInput.data("expression",data);
            $expInput.val(data.expVal);
        }
    }
    function getExpression(){
        var expressionData = [];
        if($expInput){
            var level = $expInput.closest("tr").find(".level").val();
            var express = {};
            express.id = level;
            express.name = "单元格ID值";
            express.dType = "string";
            express.code = "~F{cellIdValue}";
            express.value = "~F{" + express.name + "}";
            express.isFn = true;
            expressionData.push(express);
            if(level > 1){
                var express = {};
                express.id = level;
                express.name = "父单元格ID值";
                express.dType = "string";
                express.code = "~F{parentCellIdValue}";
                express.value = "~F{" + express.name + "}";
                express.isFn = true;
                expressionData.push(express);
            }
        }
        return expressionData;
    }
    /**
     * 初始化表达式
     * @return {[type]} [description]
     */
    function initExpressionFn(){
        if($expInput){
            return $expInput.data("expression");
        }
        return [];
    }

    function getExcelUploadRule(){
        var $baseform = $('#baseRuleForm');
        var $strRuleForm = $('#strRuleForm');
        var $expRuleForm = $('#expRuleForm');
        var strRuleparams = {};   //字符串规则
        var baseparams = {};  //基础规则
        var expRuleparams = [];   //表达式规则
        var flag = true;
        var ruleType = $("input[name='ruleType']:checked").val();
        // if(ruleType == '1'){
            //字符串解析规则
            if(strRuleFormValidate.form()){
                var strRuleFormArray = $strRuleForm.serializeArray();
                $.each(strRuleFormArray,function(i,item){
                    strRuleparams[item.name] = item.value;
                })
            }else{
                return "";
            }
            
        // }else if(ruleType == '2'){
            //表达式解析规则
            expRuleparams = [];
            $.each($("#expRuleForm tbody").find("tr"),function(i,tr){
                var $tr = $(tr);
                var levelExp = $tr.find(".levelExp").data("expression");
                var parentLevelExp = $tr.find(".parentLevelExp").data("expression");
                var level = $tr.find(".level").val();
                if(levelExp && (parentLevelExp || level == 1)){
                    var param = {
                        level : level,
                        levelExp : levelExp,
                        parentLevelExp : parentLevelExp,
                    }
                }else{
                    alert("表达式规则中必须全部定义表达式！");
                    flag = false;
                    return '';
                }
                expRuleparams.push(param);
            })
        // }else{
        if(ruleType != '1' && ruleType != '2')    
            flag = false;
        // }
        if(!flag){
            return "";
        }
        var baseformArray = $baseform.serializeArray();
        $.each(baseformArray,function(i,item){
            baseparams[item.name] = item.value;
        })
        var allRule = [{
            baseRule : baseparams,
            strRule : strRuleparams,
            expRule : expRuleparams,
            ruleType : ruleType,
            name : 'Excel解析规则'
        }];
        return allRule;
    }

    function initData(excelUploadRuleData_){
        if(excelUploadRuleData_){
            var baseRule = excelUploadRuleData_.baseRule || {};
            var strRule = excelUploadRuleData_.strRule || {};
            var expRule = excelUploadRuleData_.expRule || [];
            var ruleType = excelUploadRuleData_.ruleType;

            $.each(baseRule,function(key,value){
                var $icheck = $("."+key);
                $.each($icheck,function(i,k){
                    if($(k).val() == value){
                        $(k).iCheck("check");
                        // $(k).attr("checked","checked");
                    }
                })
            })

            $.each(strRule,function(key,value){
                $("."+key).val(value);
            })

            $.each(expRule,function(key,value){
                addExpRule(value);
            })

            var $ruleTypeIchecks = $("input[name='ruleType']");
        }
    }

    function addExpRule(item){
        var $tr_tmpl = $(tr_tmpl);
        var $tbody = $("#expRuleForm tbody");
        $tbody.append($tr_tmpl);
        if(item){
            $tr_tmpl.find(".level").val(item.level);
            $tr_tmpl.find(".levelExp").val(item.levelExp.expVal);
            $tr_tmpl.find(".levelExp").data("expression",item.levelExp);
            if(item.parentLevelExp){
                $tr_tmpl.find(".parentLevelExp").val(item.parentLevelExp.expVal);
                $tr_tmpl.find(".parentLevelExp").data("expression",item.parentLevelExp);
            }

            if(gobal_page_param_.maxlevel < item.level){
                gobal_page_param_.maxlevel = item.level;
            }
        }else{
            $tr_tmpl.find(".level").val(++gobal_page_param_.maxlevel);
        }
        if(gobal_page_param_.maxlevel == 1){
            $tr_tmpl.find(".parentLevelExp").hide();
        }
    }

    function initBody(){
        var $bodyContent = $('body');
        $bodyContent.find(".treeRulePanle").hide();
        $bodyContent.find('input[type="checkbox"]').iCheck({
            checkboxClass: 'icheckbox_square-blue',
        });
        $bodyContent.find('input[type="radio"]').iCheck({
            radioClass: 'iradio_square-blue',
        });
        $bodyContent.on("ifChanged",'.ruleType',function(event){
            var ruleType = $bodyContent.find(".ruleType:checked").val();
            if(ruleType == '1'){
                //字符串解析规则
                $bodyContent.find(".expressruleportlet").hide();
                $bodyContent.find(".strruleportlet").show();
            }else if(ruleType == '2'){
                //表达式解析规则
                $bodyContent.find(".strruleportlet").hide();
                $bodyContent.find(".expressruleportlet").show();
            }
        })

        $bodyContent.on("ifChanged",'.isTree',function(event){
            if($(this).prop("checked")){
                $bodyContent.find(".treeRulePanle").show();
            }else{
                $bodyContent.find(".treeRulePanle").hide();
            }
        });

        var url = contextPath + "/mx/expression/defined/view?category=db&retFn=retExpression&getFn=getExpression&initExpFn=initExpressionFn&notValidate=true";

        var $tbody = $bodyContent.find("tbody");

        //添加规则
        $bodyContent.find("#addButton").click(function(event){
            addExpRule();
            // var $tr_tmpl = $(tr_tmpl);
            // $tr_tmpl.find(".level").val(++gobal_page_param_.maxlevel);
            // $tbody.append($tr_tmpl);
        })

        $bodyContent.on('click',".removeRuleBtn",function(event){
            var $tr = $(this).closest("tr");
            var level = $tr.find(".level").val();
            if(level < gobal_page_param_.maxlevel){
                alert("需要删除下层节点，才能删除上层节点！");
                return;
            }
            gobal_page_param_.maxlevel--;
            $tr.remove();
        })

        $bodyContent.on('click',".levelExp",function(event){
            $expInput = $(this);
            window.open(url);
        });
        $bodyContent.on('click',".parentLevelExp",function(event){
            $expInput = $(this);
            window.open(url);
        });
    }

   $(function(){
        var excelUploadRuleData_ = JSON.parse(window.parent.$('#' + objelementId).val() || '[]')[0];

        initBody();
        initValidate();
      
        if(nameElementId){
            $("#sureBtn").show();
            $("#sureBtn").click(function(event){
                var results = getExcelUploadRule();
                if(results == ''){
                    return;
                }
                window.parent.$('#' + nameElementId).val('Excel解析规则');

                window.parent.$('#' + objelementId).val(JSON.stringify(results));
                parent.layer.close(parent.layer.getFrameIndex());
            })
        }
        

        if(excelUploadRuleData_){
           
            initData(excelUploadRuleData_);
        }
        
   })

   function initValidate(){
        var $strRuleForm = $('#strRuleForm');

        jQuery.validator.addMethod("idruleDivideValid", function (value, element) {
            //获取分组数
            var groups = $strRuleForm.find("input[name='idruleGroup']").val();
            //获取组栅格数
            var groupdivide = $strRuleForm.find("input[name='idruleDivide']").val();
            if (groupdivide && groupdivide != '') {
                if (groups != groupdivide.split(",").length) {
                    return false;
                } else {
                    var totals = 0;
                    $.each(groupdivide.split(","), function (i, item) {
                        totals += parseInt(item);
                    });
                }
            }
            return true;
        });

        window.strRuleFormValidate = $strRuleForm.validate({
            messages: {
                idruleDivide: {
                    "required": "<span class=\"help-block\"> 请输入组栅格数 </span>",
                    "idruleDivideValid": "<span class=\"help-block\"> 栅格数与分组数不正确 </span>"
                },
            },
            rules: {
                idruleDivide: {
                    "required": true,
                    "idruleDivideValid":true
                },
            },
        });
   }


</script>
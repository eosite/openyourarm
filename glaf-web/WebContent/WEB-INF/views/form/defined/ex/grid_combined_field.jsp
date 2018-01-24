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

    <style type="text/css">
        th{
            text-align: center;
        }
    </style>
</head>
<body>
    <!-- <div class="form-horizontal" style="margin:60px 15px">
        <div class="form-group">
            <label for="" class="col-xs-3 control-label col-xs-offset-2" style="text-align:right;padding-top: 4px;">开始字段</label>
            <div class="col-xs-5">
                <select name="startField" class="form-control" id="startField"></select>
            </div>
        </div>
        <div class="form-group">
            <label for="" class="col-xs-3 control-label col-xs-offset-2" style="text-align:right;padding-top: 4px;">结束字段</label>
            <div class="col-xs-5">
                <select name="endField" class="form-control" id="endField"></select>
            </div>
        </div>
        <div class="form-actions" style="text-align:center;">
            <button type="button" id="sureButton" class="btn btn-success" style=""><i class="glyphicon glyphicon-ok"></i>确认</button>
        </div>
    </div> -->
    <div style="padding:5px 5px;">
        <table class="table table-hover" id="combinedRuleTable">
            <caption style="border:1px solid #e7ecf1;padding-left:8px"> 
                <button type="button" id="addButton" class="btn btn-info btn-sm" style=""><i class="glyphicon glyphicon-plus"></i>新增</button>
                <button type="button" id="sureButton" class="btn btn-success pull-right btn-sm" style=""><i class="glyphicon glyphicon-ok"></i>确认</button>
            </caption>
            <thead>
                <tr>
                    <th>开始字段</th>
                    <th>合并类型</th>
                    <th>结束字段</th>
                    <th>操作</th>
                </tr>
            </thead>
                <tbody>
                <!-- <tr>
                    <td width="30%"> <select name="startField" class="startField" style="width:100%"></select> </td>
                    <td width="20%"> <select name="combinedType" class="combinedType" style="width:100%"></select> </td>
                    <td width="30%"><div> <select name="endField" class="endField" style="width:100%"></select> </div></td>
                    <td width="20%" style="text-align:center;"> 
                        <button type="button" id="sureButton" class="btn btn-danger btn-sm" style=""><i class="glyphicon glyphicon-remove"></i>删除</button> 
                    </td>
                </tr> -->
            </tbody>
        </table>
    </div>
</body>
</html>
<script src="${pageContext.request.contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/scripts/plugins/bootstrap/jquery.core.extends.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<!-- select2 -->
<script src="/glaf/scripts/metronic/4.5.2/theme/assets/global/plugins/select2/js/select2.full.min.js" type="text/javascript"></script>
<script type="text/javascript">
    //模板
    var tr_tmpl = '<tr>' 
                    + '<td width="30%"> <select name="startField" class="startField" style="width:100%"></select> </td>'
                    + '<td width="20%"> <select name="combinedType" class="combinedType" style="width:100%"></select> </td>'
                    + '<td width="30%"><div> <select name="endField" class="endField" style="width:100%"></select> </div></td>'
                    + '<td width="20%" style="text-align:center;">' 
                        + '<button type="button" class="removeRuleBtn btn btn-danger btn-sm" style=""><i class="glyphicon glyphicon-remove"></i>删除</button> '
                    + '</td>'
                + '</tr>';

   $(function(){
        //该功能名称
        var operatorName_ = 'combinedField';

        //数据返回位置
        var nameElementId_ = '${param.nameElementId}';  //显示域
        var objelementId_ = '${param.objelementId}';    //隐藏域
        var hideFieldDatas_ = JSON.parse(window.parent.$('#' + objelementId_).val()||"[]")[0];   //信息

        //表头信息隐藏域
        var columnElementId_ = '${param.paramElementId}';   //表头隐藏域

        //JQUERY对象
        var $table = $("#combinedRuleTable");   //合并规则表格
        var $tbody = $table.find("tbody");  //合并规则表格的表体

        //合并类型
        var combined_types_ = [
            {text:"上下合并",id:"upDown"},
            {text:"左右合并",id:"leftRight"},
            {text:"全合并",id:"all"}
        ]
        //表头字段信息
        var columns_ = null;    //全局变量
        if(columnElementId_){
            columns_ = JSON.parse(window.parent.$("#" + columnElementId_).val());
        }

        $.each(columns_,function(i,item){
            item.value = item.columnName;
            item.text = item.name;
        })

        /**
         * 增加合并规则
         * @param {[type]} startValue 开始字段的ID
         * @param {[type]} endValue   结束字段的id
         */
        var AddCombinedRule = function(startFieldId,combinedType,endFieldId){
            var $tr_tmpl = $(tr_tmpl);
            var $endField = $tr_tmpl.find(".endField"); //结束字段
            var $startField = $tr_tmpl.find(".startField");//开始字段
            var $combinedType = $tr_tmpl.find(".combinedType"); //合并规则
            //结束字段下拉框初始化
            $endField.select2({
                minimumResultsForSearch: Infinity,
                data : columns_
            });
            if(endFieldId){
                $endField.val(endFieldId).trigger('change');
            }

            //开始字段下拉框初始化
            $startField.select2({
                minimumResultsForSearch: Infinity,
                data : columns_
            });
            if(startFieldId){
                $startField.val(startFieldId).trigger('change');
            }

            //合并类型
            $combinedType.select2({
                minimumResultsForSearch: Infinity,
                data : combined_types_
            })
            
            if(combinedType){
                $combinedType.val(combinedType).trigger("change");
            }else{
                $tr_tmpl.find(".endField").parent().hide();
            }
            if(combinedType == 'upDown'){
                $tr_tmpl.find(".endField").parent().hide();
            }

            $tbody.append($tr_tmpl);
        }

        if(hideFieldDatas_){
            $.each(hideFieldDatas_.combinedRule,function(i,item){
                //遍历对应的数据，生成对应的表格
                AddCombinedRule(item.startField.id,item.combinedType.id,item.endField.id);
            });
        }

        //增加合并字段规则
        $("#addButton").click(function(event){
            AddCombinedRule();
        })

        //开始字段值变化事件
        $tbody.on("change",".startField",function(event){
            //结束字段变化
            var data = $(this).select2('data')[0];
            if(data){
                var n = 0;
                $.each(columns_,function(i,item){
                    if(data.value == item.value){
                        n = i;
                        return false;   //推出循环
                    }
                })
                var $endField = $(this).closest("tr").find(".endField");

                $endField.empty();
                $endField.select2({
                    minimumResultsForSearch: Infinity,
                    data : columns_.slice(n)
                })    
            }
        })

        //合并类型值变化事件
        $tbody.on("change",".combinedType",function(event){
            var $endField = $(this).closest("tr").find(".endField");
            var typeValue = $(this).select2("data")[0];
            if(typeValue.id == 'upDown'){
                //上下合并隐藏结束字段
                $endField.parent().hide();
            }else{
                //需要显示结束字段
                $endField.parent().show();
            }

        })

        //删除合并规则事件
        $tbody.on("click",".removeRuleBtn",function(event){
            $(this).closest("tr").remove();
        })

        

        $("#sureButton").click(function(event){
            var paramList = [];
            //遍历所有行，生成新的合并规则信息
            $.each($tbody.find("tr"),function(i,item){
                var $tr = $(this);
                var startFieldData = $tr.find(".startField").select2('data')[0];
                var endFieldData = $tr.find(".endField").select2('data')[0];
                var combinedType = $tr.find(".combinedType").select2('data')[0];

                var param = {
                    'startField' : startFieldData,
                    'endField' : endFieldData,
                    'combinedType' : combinedType,
                }
                paramList.push(param);
            });

            var result = [{
                name : "grid列合并规则",
                combinedRule : paramList
            }]

            if(paramList.length > 0 ){

                window.parent.$('#' + nameElementId_).val("grid列合并规则");
                window.parent.$('#' + objelementId_).val(JSON.stringify(result));
                parent.layer.close(parent.layer.getFrameIndex());
            }else{
                window.parent.$('#' + nameElementId_).val("");
                window.parent.$('#' + objelementId_).val("[]");
                parent.layer.close(parent.layer.getFrameIndex());
            }
        })

   })


</script>
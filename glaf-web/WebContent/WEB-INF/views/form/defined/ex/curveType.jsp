<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Document</title>

    <link href="${pageContext.request.contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <!-- grid -->
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/scripts/plugins/bootstrap/grid/css/grid.css">
    <link href="${pageContext.request.contextPath}/scripts/metronic/4.5.2/theme/assets/global/css/components.min.css" rel="stylesheet" id="style_components" type="text/css" />
    <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-select/css/bootstrap-select.min.css" rel="stylesheet" type="text/css" />
     <link href="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css" />
    
    <style type="text/css">
        .parentPanel{
            /*border:1px solid black;*/
            height: 450px;
            background-color:#E1E5EC;
            padding:5px 5px;
        }
        .contentPanel{
            background-color: #FFF;
            border: 1px solid #b3b1b1;
            padding-left: 5px;
            padding-right: 5px;
            height: 100%;
        }
        .gridClass{
            height: 330px;
        }

    </style>

</head>
<body>
    <div class="row" style="padding:15px 25px 0px 25px">
        <div id="header" class="form-horizontal">
            <div class="form-group">
                <label class="control-label col-xs-2">
                    <span class="caption-subject font-blue-sharp bold uppercase" style="font-size: 16px">曲线类型</span>
                </label>
                <div class="col-xs-7">
                    <select id="curveType">
                        <option value="1" selected>正态分布</option>
                        <option value="2">X-BAR曲线</option>
                        <option value="3">R曲线</option>
                        <option value="4">cp曲线</option>
                    </select>
                    
                </div>
                <div class="pull-right" style="margin-right: 40px;">
                    <button id="sureBtn" type="submit" class="btn btn-success">
                        <i class="fa fa-check"></i> 确认
                    </button>
                </div>
                <div class="pull-right" style="margin-right:10px">
                    <button id="deleteBtn" type="submit" class="btn btn-danger">
                        <i class="fa fa-ban"></i> 清空
                    </button>
                </div>
            </div>
        </div>
    </div>
    <div class="row" style="padding:0px 25px">
        <div class="col-xs-4 parentPanel">
            <div id="leftPanel" class="contentPanel form-horizontal">
                <div style="text-align:center;padding-top:10px;margin-bottom:10px"><span class="caption-subject font-blue-sharp bold uppercase" style="font-size: 16px">选择字段</span></div>
                <div>
                    <div id="columnsGrid" class="gridClass"></div>    
                </div>
            </div>
        </div>
        <div class="col-xs-4 parentPanel">
            <div id="middlePanel" class="contentPanel form-horizontal">
                <div style="text-align:center;padding-top:10px;margin-bottom:10px"><span class="caption-subject font-blue-sharp bold uppercase" style="font-size: 16px">属性配置</span></div>
                <div id="content" style="margin-top:20px;height:383px;overflow-y:scroll;">
                    <form id="normalDistributionForm" class="form-horizontal">
                    </form>

                    <form id="qualityControlForm" class="form-horizontal">
                    </form>
                </div>
            </div>
        </div>
        <div class="col-xs-4 parentPanel">
            <div id="rightPanel" class="contentPanel form-horizontal">
                <div style="text-align:center;padding-top:10px;margin-bottom:10px"><span class="caption-subject font-blue-sharp bold uppercase" style="font-size: 16px">字段配置</span></div>
                <div>
                    <div id="propotyColumnsGrid" class="gridClass"></div>    
                </div>
            </div>
        </div>
    </div>
</body>
</html>
<script src="${pageContext.request.contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/scripts/plugins/bootstrap/jquery.core.extends.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/scripts/plugins/bootstrap/grid/js/jquery.grid.extends.js" type="text/javascript"></script>

<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-select/js/bootstrap-select.min.js" type="text/javascript"></script>
<script src="${contextPath}/scripts/metronic/4.5.2/theme/assets/pages/scripts/components-bootstrap-select.min.js" type="text/javascript"></script>

<script type="text/javascript">
    //该功能名称
    var controlName = 'normalDistribution';

    //数据返回位置
    var nameElementId = '${param.nameElementId}';
    var objelementId = '${param.objelementId}';
    //获取曲线类型信息
    var curveTypeData_ = JSON.parse(window.parent.$('#' + objelementId).val() || '[]')[0];

    //获取数据集信息
    var dataSourceSetId = '${param.dataSourceSetId}';
    var datasourceSet = window.parent.$('#' + dataSourceSetId).val();
    if (datasourceSet == null || datasourceSet == '') {
        alert("请选择数据集！");
        parent.layer.close(parent.layer.getFrameIndex());
    }
    var datasourceSetObj = JSON.parse(datasourceSet || '[]')[0];
    //左边grid数据集
    var leftColumns = datasourceSetObj.selectColumns;
    //选中的数据集字段
    var selectedColumns = [];
    //右面板grid数据集
    var rightColumns = [];
    //用于说明当前选择的是什么曲线类型
    var currentRightColumns;
    var curveType_rightColumns_ = {
        '1': [{
            'title': '分组',
            "columnName": "groupName",
            'cn': '分组',
            "en": "groupName"
        }, {
            'title': '频度',
            "columnName": "frequentNum",
            'cn': '频度',
            "en": "frequentNum"
        }, {
            'title': '正态分布数值',
            "columnName": "valueName",
            'cn': '正态分布数值',
            "en": "valueName"
        }],
        '2': [{
            'title': '序号',
            "columnName": "indexCol",
            'cn': '序号',
            "en": "indexCol"
        }, {
            'title': '前X次平均值',
            "columnName": "xbar",
            'cn': '前X次平均值',
            "en": "xbar"
        }, {
            'title': 'Y次平均的平均值',
            "columnName": "x2bar",
            'cn': 'Y次平均的平均值',
            "en": "x2bar"
        }, {
            'title': 'UCL',
            "columnName": "ucl",
            'cn': 'UCL',
            "en": "ucl"
        }, {
            'title': 'LCL',
            "columnName": "lcl",
            'cn': 'LCL',
            "en": "lcl"
        }, {
            'title': '容许上限',
            "columnName": "uplimit",
            'cn': '容许上限',
            "en": "uplimit"
        }, {
            'title': '容许下限',
            "columnName": "downlimit",
            'cn': '容许下限',
            "en": "downlimit"
        }],
        '3': [{
            'title': '序号',
            "columnName": "indexCol",
            'cn': '序号',
            "en": "indexCol",
        }, {
            'title': '前X次极差',
            "columnName": "r",
            'cn': '前X次极差',
            "en": "r"
        }, {
            'title': 'Y次平均的平均值',
            "columnName": "x2bar",
            'cn': 'Y次平均的平均值',
            "en": "x2bar"
        }, {
            'title': 'Y次极差的平均',
            "columnName": "rbar",
            'cn': 'Y次极差的平均',
            "en": "rbar",
        }, {
            'title': 'UCL',
            "columnName": "ucl",
            'cn': 'UCL',
            "en": "ucl"
        }, {
            'title': 'LCL',
            "columnName": "lcl",
            'cn': 'LCL',
            "en": "lcl"
        }, {
            'title': '容许上限',
            "columnName": "uplimit",
            'cn': '容许上限',
            "en": "uplimit"
        }, {
            'title': '容许下限',
            "columnName": "downlimit",
            'cn': '容许下限',
            "en": "downlimit"
        }],
        '4': [{
            'title': '阶段',
            "columnName": "indexCol",
            'cn': '阶段',
            "en": "indexCol"
        }, {
            'title': 'cp',
            "columnName": "cp",
            'cn': '基准线（1.67）',
            "en": "cp",
        }, {
            'title': 'cpk',
            "columnName": "cpk",
            'cn': '基准线（2）',
            "en": "cpk"
        }, ],
    }
    
    
    var normalDistributionColumns = [{'title':'分组',"columnName":"groupName"},{'title':'频度',"columnName":"frequentNum"},{'title':'正态分布数值',"columnName":"valueName"}];
    //seq数组，保存有多少个数据集信息
    var seqJsonList = [];
    //seq数组
    var seqList = curveTypeData_ ? curveTypeData_.seqList || [] : [];   //原本的seqList
    //右边下拉框选择项
    var rightSelectOptions;
    //左边下拉框选择项
    var leftSelectOptions;
    //中间属性值
    var middleProtsColumns;

    //所有曲线的左边下拉框选择项
    var curveType_leftSelect_ = {
        //正态分布曲线的左边下拉框选择项,normalDistribution
        '1' : {
            name : 'calculeType',
            values : [
                {
                    'name':'calculeType',   //属性名称
                    'title' : '计算该列',   //下拉框显示值
                    'value' : 'dataCol',   //属性值
                }
            ]
        },
        // qualityControl
        '2' : {
            name : "calculeType",
            values : [
                {
                    'name' : 'calculeType', //属性名称
                    'title' : '数据',   //数据
                    'value' : 'dataCol',    //数据列
                },{
                    'name' : 'calculeType', //属性名称
                    'title' : '序号',   //序号
                    'value' : 'indexCol',    //序号列
                },
            ]
        },
        '3' : {
            name : "calculeType",
            values : [
                {
                    'name' : 'calculeType', //属性名称
                    'title' : '数据',   //数据
                    'value' : 'dataCol',    //数据列
                },{
                    'name' : 'calculeType', //属性名称
                    'title' : '序号',   //序号
                    'value' : 'indexCol',    //序号列
                },
            ]
        },
        '4' : {
            name : "calculeType",
            values : [
                {
                    'name' : 'calculeType', //属性名称
                    'title' : '数据',   //数据
                    'value' : 'dataCol',    //数据列
                },{
                    'name' : 'calculeType', //属性名称
                    'title' : '序号',   //序号
                    'value' : 'indexCol',    //序号列
                },
            ]
        }
    }
    //所有曲线的中间属性项 
    var curveType_protsColumns_ = {
        //正态分布曲线的左边下拉框选择项,normalDistribution
        '1' : [{
            title : "组数",
            name : "groupNum",
            type : 'number',
        }],
        // qualityControl
        '2' : [{
            title : "组数",
            name : "dataNum",
            type : 'number',
        },{
            title : "平均组数",
            name : "avgNum",
            type : 'number',
        },{
            title : "A2",
            name : "a2",
            type : 'number',
        },{
            title : "容许上限",
            name : "uplimit",
            type : 'number',
        },{
            title : "容许下限",
            name : "downlimit",
            type : 'number',
        }],
        '3' : [{
            title : "组数",
            name : "dataNum",
            type : 'number',
        },{
            title : "平均组数",
            name : "avgNum",
            type : 'number',
        },{
            title : "D3",
            name : "d3",
            type : 'number',
        },{
            title : "D4",
            name : "d4",
            type : 'number',
        },{
            title : "容许上限",
            name : "uplimit",
            type : 'number',
        },{
            title : "容许下限",
            name : "downlimit",
            type : 'number',
        }],
        '4' : [{
            title : "组数",
            name : "dataNum",
            type : 'number',
        },{
            title : "平均组数",
            name : "avgNum",
            type : 'number',
        },{
            title : "A2",
            name : "a2",
            type : 'number',
        },{
            title : "容许上限",
            name : "uplimit",
            type : 'number',
        },{
            title : "容许下限",
            name : "downlimit",
            type : 'number',
        },{
            title : "基准线1",
            name : "datum1",
            type : 'number',
        },{
            title : "基准线2",
            name : "datum2",
            type : 'number',
        },{
            title : "基准线3",
            name : "datum3",
            type : 'number',
        },{
            title : "基准线4",
            name : "datum4",
            type : 'number',
        },{
            title : "基准线5",
            name : "datum5",
            type : 'number',
        }],
    }

    //左边面板
    var leftPanelObj;
    //中间面板
    var middlePanelObj;
    //右边面板
    var rightPanelObj;

    $(function() {
        //曲线类型
        var $curveSelect = $('#curveType');
        var curveTypeNum = curveTypeData_ ? curveTypeData_.type : 1;
        $('#curveType').find('option[value = "'+curveTypeNum+'"]')[0].selected = true;

        //默认选择正态分布
        currentRightColumns = normalDistributionColumns;
        //右边下拉框选择项
        rightSelectOptions = {
            name : 'ctype',
            values :[
                {
                    'name':'ctype',   //属性名称
                    'title' : '名称(x轴)',   //下拉框显示值
                    'value' : 'xAxisName',   //属性值
                },
                {
                    'name':'ctype',   //属性名称
                    'title' : '系列(y轴)',   //下拉框显示值
                    'value' : 'yAxisName',   //属性值
                },
                {
                    'name':'ctype',   //属性名称
                    'title' : '数值',   //下拉框显示值
                    'value' : 'axisName',   //属性值
                }

            ]
        }
        //左边下拉框选择列
        leftSelectOptions = curveType_leftSelect_[curveTypeNum];
        if(curveTypeData_ && curveTypeData_.rightColumns){
            rightColumns = curveTypeData_.rightColumns
        }

        middleProtsColumns = curveType_protsColumns_[curveTypeNum];

        currentRightColumns = curveType_rightColumns_[curveTypeNum];

        //初始化左边数据和seqList
        initLeftColumns();

        //初始化左边面板
        leftPanelObj = new LeftPanel($('#leftPanel'));

        //初始化中间面板
        middlePanelObj = new MiddlePanel($('#middlePanel'), curveTypeNum);

        //初始化右边面板
        rightPanelObj = new RightPanel($('#rightPanel'));
        
        bindEvent();
    })
    
    function bindEvent(){
        //下拉框格式
        $('#curveType').selectpicker({
            style: 'btn-default',
            size: 'auto',
            width : 'auto',
            noneSelectedText:'请选择'
        })

        $('#curveType').change(function(){
            var curveTypeNum = $(this).val();

            //重载左边信息
            leftSelectOptions = curveType_leftSelect_[curveTypeNum];
            leftPanelObj.destroyGrid();
            leftPanelObj.loadGrid();
            middleProtsColumns = curveType_protsColumns_[curveTypeNum];
            if(curveTypeNum == '1'){
                middlePanelObj.loadNormalDistribution();
            }else if(curveTypeNum == '2' || curveTypeNum == '3' || curveTypeNum == '4'){
                middlePanelObj.loadQualityControl();
            }

            //重载右边信息
            rightColumns = [];
            currentRightColumns = curveType_rightColumns_[curveTypeNum];
            rightPanelObj.destroyGrid();
            rightPanelObj.loadGrid();
            
        });

        //确认按钮，返回数据
        $("#sureBtn").click(function(event){
            //曲线类型
            var curveType = $('#curveType').val();
            var results = [];
            var leftSelectColumns = [];
            var result = {
                name : controlName,
                type : curveType,
                'leftColumns' : leftSelectColumns,
                'rightColumns' : rightColumns,
                'seqList' : seqJsonList,
            }
            result.pros = middlePanelObj.getData(curveType);

            //检验必要属性是否编写
            var message = middlePanelObj.validateData(curveType,result.pros);
            if(message != ''){
                alert(message);
                return;
            }
            if(curveType == 1){
                //正态分布校验
                var colContains = [];
                for(var i=0; i<leftColumns.length;i++){
                    var leftColumn = leftColumns[i];
                    if(leftColumn.calculeType && leftColumn.calculeType != ''){
                        if(colContains.indexOf(leftColumn.seq + leftColumn.columnName) != -1){
                            alert('正态分布计算列每个数据集只能选择一列！错误数据集：'+leftColumn.seq);
                            return;
                        }else{
                            leftSelectColumns.push(leftColumn);
                            colContains.push(leftColumn.seq + leftColumn.columnName);
                        }
                    }
                }
                if(colContains.length != seqJsonList.length){
                    alert('正态分布计算列每个数据集必须选择一列！');
                    return;
                }
                colContains = [];
                for(var i=0; i<rightColumns.length; i++){
                    var rightColumn = rightColumns[i];
                    if(rightColumn.chartStack){
                        if(colContains.indexOf(leftColumn.seq + leftColumn.chartStack)!=-1){
                            alert('堆叠分组字段一个数据集只能设置一个！');
                            return;
                        }else{
                            colContains.push(leftColumn.seq + leftColumn.chartStack);
                        }
                    }
                }

                //属性配置
                results.push(result);

                window.parent.$('#' + nameElementId).val('正态分布');
                window.parent.$('#' + objelementId).val(JSON.stringify(results));
                parent.layer.close(parent.layer.getFrameIndex());
            }else if(curveType == 2 || curveType == 3 || curveType == 4){
                //正态分布校验
                //leftSelectOptions
                var colContains = [];
                for(var i=0; i<leftColumns.length;i++){
                    var leftColumn = leftColumns[i];
                    if(leftColumn.calculeType && leftColumn.calculeType != ''){
                        if(colContains.indexOf(leftColumn.seq + leftColumn.calculeType) != -1){
                            alert('每个数据集的序号和数据不能有多个！错误数据集：'+leftColumn.seq);
                            return;
                        }else{
                            leftSelectColumns.push(leftColumn);
                            colContains.push(leftColumn.seq + leftColumn.calculeType);
                        }
                    }
                }
                if(colContains.length != seqJsonList.length * leftSelectOptions.values.length){
                    alert('每个数据集必须选择序号和数据！');
                    return;
                }
                colContains = [];
                for(var i=0; i<rightColumns.length; i++){
                    var rightColumn = rightColumns[i];
                    if(rightColumn.chartStack){
                        if(colContains.indexOf(leftColumn.seq + leftColumn.chartStack)!=-1){
                            alert('堆叠分组字段一个数据集只能设置一个！');
                            return;
                        }else{
                            colContains.push(leftColumn.seq + leftColumn.chartStack);
                        }
                    }
                }
                //属性配置
                results.push(result);
                
                window.parent.$('#' + nameElementId).val('正态分布');
                window.parent.$('#' + objelementId).val(JSON.stringify(results));
                parent.layer.close(parent.layer.getFrameIndex());
            }

            return;
        });

        //清空信息，
        $("#deleteBtn").click(function(event){
            var r=confirm("注意是否要清空曲线计算信息！");
            if(r){
                window.parent.$('#' + nameElementId).val('');
                window.parent.$('#' + objelementId).val('');
                parent.layer.close(parent.layer.getFrameIndex());   
            }
        })
    }

    //初始化左边数据
    function initLeftColumns(){
        //初始化数据
        seqJsonList = [];
        $.each(leftColumns,function(i,item){
            if(curveTypeData_ && curveTypeData_.leftColumns){
                $.each(curveTypeData_.leftColumns,function(k,column){
                    if(column.columnName == item.columnName && column.seq == item.seq){
                        item.calculeType = column.calculeType;
                    }
                })
            }
            if(seqJsonList.indexOf(item.seq) == -1){
                seqJsonList.push(item.seq);
            }
        })
    }

    //初始化左边数据集字段选择grid
    function LeftPanel($leftPanel){
        this.$panel = $leftPanel;
        this.$grid = $leftPanel.find("#columnsGrid");
        this.loadGrid();
    }
    LeftPanel.prototype.loadGrid = function(){
        var that = this;

        var gridSetting = {
            selectable:"single",
            scrollable: true,
            sortable: true,
            columns: [
                {
                    field: 'title',
                    title: '名称',
                    "width": "100px",
                    template : function(data){
                        return data.seq +"."+ data.title;
                    }
                },{
                    title: '操作',
                    align: 'center',
                    "width": "60px",
                    template: function(data){
                        var $select = null;
                        $select = $('<select class="leftSelect">');
                        $select.attr("row-index",data["row-index"]);
                        $select.attr("name",leftSelectOptions.name);
                        $select.append($('<option value="">请选择</option>'));
                        
                        $.each(leftSelectOptions.values,function(i,item){
                            var $option = $('<option>');
                            $option.text(item.title);
                            $option.val(item.value);
                            if(data.calculeType != null && data.calculeType != '' && item.value == data.calculeType){
                                $option.attr("selected","selected");
                            }
                            $select.append($option);
                        })
                        if($select == null){
                            return '';
                        }
                        return $select[0].outerHTML;
                    }
                }
            ],
            datas : leftColumns,
            pagination: {
                paging: false,
            },
            events: {
                onLoadSuccess: function(data){
                    that.$grid.find('.leftSelect').change(function(event){
                        var rowIndex = $(this).attr("row-index");
                        var item = leftColumns[rowIndex];
                        var key = $(this).attr("name");
                        item[key] = $(this).val();
                    })
                }
            },
        };
        this.$grid.grid(gridSetting);
    }
    LeftPanel.prototype.destroyGrid = function(){
        var $gridParent = this.$grid.parent();
        $gridParent.empty();
        this.$grid = $('<div id="columnsGrid" class="gridClass"></div>');
        $gridParent.append(this.$grid);
    }
    LeftPanel.prototype.reloadGrid = function(){
        this.$grid.grid("query");
    }
    
    function MiddlePanel($middlePanel,curveTypeNum){
        this.$panel = $middlePanel;
        //内容面板
        this.$content = $middlePanel.find("#content");
        if(curveTypeNum == 1){
            this.loadNormalDistribution();
        }else if(curveTypeNum == 2 || curveTypeNum == 3 || curveTypeNum == 4){
            this.loadQualityControl();
        }
    }
    MiddlePanel.prototype.loadNormalDistribution = function(){
        //初始化正态分布内容
        var $content = this.$content;
        $content.find("form").hide();
        var $form = $content.find("#normalDistributionForm");
        $form.empty();
        //遍历数据集个数，即seq
        $.each(seqJsonList,function(i,item){
            $.each(middleProtsColumns,function(k,ktiem){
                var $formGroup =  $('<div class="form-group"><label class="control-label col-xs-4" style="overflow:hidden;text-overflow:ellipsis;white-space:nowrap;">'+item+'.'+ktiem.title+'：</label></div>');
                var $input = $('<input class="form-control input-sm" name="'+ktiem.name+'" type="'+ktiem.type+'" placeholder="'+ktiem.title+'">');
                $input.attr("seq",item);
                $formGroup.append($('<div class="col-xs-7">').append($input));
                $formGroup.attr("title",item +'.' + ktiem.title);
                //初始化
                if(curveTypeData_ && curveTypeData_.pros && curveTypeData_.pros[item]){
                    $input.val(curveTypeData_.pros[item][ktiem.name]);
                }
                 $form.append($formGroup);
            })

            //每一个有每一个的formGroup
            // var $formGroup =  $('<div class="form-group"><label class="control-label col-xs-4" style="overflow:hidden;text-overflow:ellipsis;white-space:nowrap;">'+item+'.组数：</label></div>');
            // var $input = $('<input class="form-control input-sm" name="groupNum" type="number" placeholder="组数">');
            // $input.attr("seq",item);
            // $formGroup.append($('<div class="col-xs-7">').append($input));
            // $formGroup.attr("title",item +'.组数');
            // //初始化
            // if(curveTypeData_ && curveTypeData_.pros && curveTypeData_.pros[item]){
            //     $input.val(curveTypeData_.pros[item].groupNum);
            // }

            // $form.append($formGroup);
        });

        $form.show();
    }
    MiddlePanel.prototype.loadQualityControl = function(){
        
        //初始化正态分布内容
        var $content = this.$content;
        $content.find("form").hide();
        var $form = $content.find("#qualityControlForm");
        $form.empty();
        //遍历数据集个数，即seq
        $.each(seqJsonList,function(i,item){

            $.each(middleProtsColumns,function(k,ktiem){
                var $formGroup =  $('<div class="form-group"><label class="control-label col-xs-4" style="overflow:hidden;text-overflow:ellipsis;white-space:nowrap;">'+item+'.'+ktiem.title+'：</label></div>');
                var $input = $('<input class="form-control input-sm" name="'+ktiem.name+'" type="'+ktiem.type+'" placeholder="'+ktiem.title+'">');
                $input.attr("seq",item);
                $formGroup.append($('<div class="col-xs-7">').append($input));
                $formGroup.attr("title",item +'.' + ktiem.title);
                //初始化
                if(curveTypeData_ && curveTypeData_.pros && curveTypeData_.pros[item]){
                    $input.val(curveTypeData_.pros[item][ktiem.name]);
                }
                 $form.append($formGroup);
            })

            //每一个有每一个的formGroup
            //平均组数
            // var $formGroup =  $('<div class="form-group"><label class="control-label col-xs-4" style="overflow:hidden;text-overflow:ellipsis;white-space:nowrap;">'+item+'.组数：</label></div>');
            // var $input = $('<input class="form-control input-sm" name="dataNum" type="number" placeholder="组数">');
            // $input.attr("seq",item);
            // $formGroup.append($('<div class="col-xs-7">').append($input));
            // $formGroup.attr("title",item +'.组数');
            // //初始化
            // if(curveTypeData_ && curveTypeData_.pros && curveTypeData_.pros[item]){
            //     $input.val(curveTypeData_.pros[item].dataNum);
            // }
            //  $form.append($formGroup);

            // //平均值的组数
            // $formGroup =  $('<div class="form-group"><label class="control-label col-xs-4" style="overflow:hidden;text-overflow:ellipsis;white-space:nowrap;">'+item+'.平均组数：</label></div>');
            // $input = $('<input class="form-control input-sm" name="avgNum" type="number" placeholder="平均组数">');
            // $input.attr("seq",item);
            // $formGroup.append($('<div class="col-xs-7">').append($input));
            // $formGroup.attr("title",item +'.平均组数');
            // //初始化
            // if(curveTypeData_ && curveTypeData_.pros && curveTypeData_.pros[item]){
            //     $input.val(curveTypeData_.pros[item].avgNum);
            // }
            // $form.append($formGroup);

            // //A2
            // $formGroup =  $('<div class="form-group"><label class="control-label col-xs-4" style="overflow:hidden;text-overflow:ellipsis;white-space:nowrap;">'+item+'.A2：</label></div>');
            // $input = $('<input class="form-control input-sm" name="a2" type="number" placeholder="A2">');
            // $input.attr("seq",item);
            // $formGroup.append($('<div class="col-xs-7">').append($input));
            // $formGroup.attr("title",item +'.A2');
            // //初始化
            // if(curveTypeData_ && curveTypeData_.pros && curveTypeData_.pros[item]){
            //     $input.val(curveTypeData_.pros[item].a2);
            // }
            // $form.append($formGroup);

            // //容许上限
            // $formGroup =  $('<div class="form-group"><label class="control-label col-xs-4" style="overflow:hidden;text-overflow:ellipsis;white-space:nowrap;">'+item+'.容许上限：</label></div>');
            // $input = $('<input class="form-control input-sm" name="uplimit" type="number" placeholder="容许上限">');
            // $input.attr("seq",item);
            // $formGroup.append($('<div class="col-xs-7">').append($input));
            // $formGroup.attr("title",item +'.容许上限');
            // //初始化
            // if(curveTypeData_ && curveTypeData_.pros && curveTypeData_.pros[item]){
            //     $input.val(curveTypeData_.pros[item].uplimit);
            // }
            // $form.append($formGroup);

            // //容许下限
            // $formGroup =  $('<div class="form-group"><label class="control-label col-xs-4" style="overflow:hidden;text-overflow:ellipsis;white-space:nowrap;">'+item+'.容许下限：</label></div>');
            // $input = $('<input class="form-control input-sm" name="downlimit" type="number" placeholder="容许下限">');
            // $input.attr("seq",item);
            // $formGroup.append($('<div class="col-xs-7">').append($input));
            // $formGroup.attr("title",item +'.容许下限');
            // //初始化
            // if(curveTypeData_ && curveTypeData_.pros && curveTypeData_.pros[item]){
            //     $input.val(curveTypeData_.pros[item].downlimit);
            // }
            // $form.append($formGroup);
        });

        $form.show();
    }
    MiddlePanel.prototype.getData = function(curveType){
        if(curveType == 1){
            //正态分布
            var params = {};
            $.each(this.$content.find("#normalDistributionForm input"),function(i,input){
                var pro;    //属性
                var seq = $(this).attr("seq");
                if(params[seq] == null){
                    params[seq] = {};
                }
                pro = params[seq];

                pro[$(this).attr("name")] = $(this).val();

            });
            // var paramsArray = this.$content.find("#normalDistributionForm").serializeArray();
            // $.each(paramsArray,function(i,item){
            //     params[item.name] = item.value;
            // })
            return params;
        }else if(curveType == 2 || curveType == 3 || curveType == 4){
            var params = {};
            $.each(this.$content.find("#qualityControlForm input"),function(i,input){
                var pro;    //属性
                var seq = $(this).attr("seq");
                if(params[seq] == null){
                    params[seq] = {};
                }
                pro = params[seq];

                pro[$(this).attr("name")] = $(this).val();

            });
            return params;
        }
    }
    MiddlePanel.prototype.validateData = function(curveType,pros){
        var message = '';
        if(curveType == '1'){
            $.each(seqJsonList,function(i,item){
                if(pros[item] == null || pros[item].groupNum == null || pros[item].groupNum == ''){
                    message += '数据集'+item+'未赋值属性！';
                    return false;   
                }
            });
        }else if(curveType == '2' || curveType == '4'){
            $.each(seqJsonList,function(i,item){
                if(pros[item] == null || pros[item].dataNum == null || pros[item].dataNum == ''){
                    message += '数据集'+item+'未赋值组数！';
                    return false;   
                }
                if(pros[item] == null || pros[item].avgNum == null || pros[item].avgNum == ''){
                    message += '数据集'+item+'未赋值平均组数！';
                    return false;   
                }
                if(pros[item] == null || pros[item].a2 == null || pros[item].a2 == ''){
                    message += '数据集'+item+'未赋值A2！';
                    return false;   
                }
                if(pros[item] == null || pros[item].uplimit == null || pros[item].uplimit == ''){
                    message += '数据集'+item+'未赋值容许上限！';
                    return false;   
                }
                if(pros[item] == null || pros[item].downlimit == null || pros[item].downlimit == ''){
                    message += '数据集'+item+'未赋值容许下限！';
                    return false;   
                }
            });
        }else if(curveType == '3'){
            $.each(seqJsonList,function(i,item){
                if(pros[item] == null || pros[item].dataNum == null || pros[item].dataNum == ''){
                    message += '数据集'+item+'未赋值组数！';
                    return false;   
                }
                if(pros[item] == null || pros[item].avgNum == null || pros[item].avgNum == ''){
                    message += '数据集'+item+'未赋值平均组数！';
                    return false;   
                }
                if(pros[item] == null || pros[item].d3 == null || pros[item].d3 == ''){
                    message += '数据集'+item+'未赋值D3！';
                    return false;   
                }if(pros[item] == null || pros[item].d4 == null || pros[item].d4 == ''){
                    message += '数据集'+item+'未赋值D4！';
                    return false;   
                }
                if(pros[item] == null || pros[item].uplimit == null || pros[item].uplimit == ''){
                    message += '数据集'+item+'未赋值容许上限！';
                    return false;   
                }
                if(pros[item] == null || pros[item].downlimit == null || pros[item].downlimit == ''){
                    message += '数据集'+item+'未赋值容许下限！';
                    return false;   
                }
            });
        }
        return message;
    }

    //初始化右边信息
    function RightPanel($rightPanel){
        this.$panel = $rightPanel;
        this.$grid = $rightPanel.find("#propotyColumnsGrid");
        this.loadGrid();   
    }
    RightPanel.prototype.loadGrid = function(){
        var that = this;
        // 遍历数据集生成对应数据
        if(rightColumns && rightColumns.length > 0 && seqList.length == seqJsonList.length){

        }else{
            rightColumns = [];
            $.each(seqJsonList,function(n,value){
                for(var i = 0 ; i < currentRightColumns.length ; i++){
                    var item = currentRightColumns[i];
                    item.seq = value;
                    rightColumns.push(jQuery.extend(true, {}, item)); 
                }
            });
        }

        var gridSetting = {
            scrollable: true,
            sortable: true,
            selectable:"single",
            columns: [{
                field: 'title',
                title: '名称',
                template : function(data){
                    return data.seq + "." + data.title;
                }
            },{
                title: '类型',
                template: function(data){
                    var $select = null;
                    $select = $('<select>');
                    $select.attr("row-index",data["row-index"]);
                    $select.attr("name",rightSelectOptions.name);
                    $select.append($('<option value="">请选择</option>'));
                    $.each(rightSelectOptions.values,function(i,item){
                        var $option = $('<option>');
                        if(data[item.name] == item.value){
                            $option.attr("selected","selected");
                        }
                        $option.text(item.title);
                        $option.val(item.value);
                        $select.append($option);
                    })
                    if($select == null){
                        return '';
                    }
                    return $select[0].outerHTML;
                    
                }
            },{
                title: '堆叠分组字段',
                width: '70px',
                template: function(data){
                    var $input = $('<input type="checkbox" name="chartStack">');
                    $input.attr("row-index",data["row-index"]);
                    if(data.chartStack){
                        $input.attr("checked","checked");
                    }
                    return $input[0].outerHTML;
                }
            }],
            datas : rightColumns,
            // ajax: that.ajax,
            pagination: {
                paging: false,
            },
            events:{
                onLoadSuccess: function(data){
                    that.$grid.find('select').change(function(event){
                        var rowIndex = $(this).attr("row-index");
                        var item = rightColumns[rowIndex];
                        var key = $(this).attr("name");
                        item[key] = $(this).val();
                    })
                    that.$grid.find('input[type="checkbox"]').change(function(event){
                        var rowIndex = $(this).attr("row-index");
                        var item = rightColumns[rowIndex];
                        item.chartStack = $(this).is(':checked');
                    })
                }
            }
        }
        this.$grid.grid(gridSetting);
    }
    RightPanel.prototype.destroyGrid = function(){
        var $gridParent = this.$grid.parent();
        $gridParent.empty();
        this.$grid = $('<div id="propotyColumnsGrid" class="gridClass"></div>');
        $gridParent.append(this.$grid);
    }
    RightPanel.prototype.reloadGrid = function(){
        this.$grid.grid("query");
    }



</script>
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
    
    <!--文件上传-->
        <link href="/glaf/scripts/designer/plugins/bootstrap-fileinput/css/fileinput.min.css" rel="stylesheet" type="text/css" />


    <link rel='stylesheet' type='text/css' href='${contextPath}/scripts/plugins/bootstrap/grid/css/grid.css'>



    <style type="text/css">
        th{
            text-align: center;
        }
    </style>
</head>
<body style='padding:15px !important;'>
    <div class="row form-group">
        <div class="col-xs-2 pull-right" style="text-align:center;">
            <button class="btn btn-sm btn-success" id="sureBtn" style=""><i class="fa fa-check"></i>确认</button>
        </div>
    </div>
        <div class="row form-group">
            <label class="control-label col-xs-2" style="margin-top: 6px;text-align:right;">
                上传文件
            </label>
            <div class="col-xs-10">
                <form enctype="multipart/form-data">
                    <input id="fileInput" name="file"  type="file">
                </form>
           </div>
           <a href="http://ecomfe.github.io/echarts-map-tool/" target="view_window" style="cursor:pointer;">
               <label class="control-label col-xs-10 col-xs-offset-2" style="cursor:pointer;">点击这里下载最新的地图数据。
                </label>
            </a>
        </div>
        
        

    <div class="portlet box strruleportlet" style="background: #F5F5F5;border:1px solid #ccc;">
        <div class="portlet-title" style="min-height: 0px;">
            <div class="" style="line-height: 30px;color:black">
                地图信息列表
            </div>
        </div>
        <div class="portlet-body">
            <div class="row form-group">
                <label class="control-label col-xs-2" style="margin-top: 6px;text-align:right;">
                    文件名称
                </label>
                <div class="col-xs-7">
                    <input type="text" class="form-control input-sm" name="name" id="namelike">
                </div>
                <div class="col-xs-2">
                    <button class="btn btn-sm btn-info" id="queryBtn" style=""><i class="fa fa-search"></i>查询</button>
                </div>
            </div>
            <div id="mapGrid"></div>
        </div>
    </div>
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

<!-- fileinput -->
<script src="${pageContext.request.contextPath}/scripts/designer/plugins/bootstrap-fileinput/js/fileinput.min.js" type="text/javascript"></script>
<script src="/glaf/scripts/designer/plugins/bootstrap-fileinput/js/locales/zh.js" type="text/javascript"></script>

<script type='text/javascript' src='/glaf/scripts/uuid.js'></script>

<script type="text/javascript" src="/glaf/scripts/plugins/bootstrap/jquery.core.extends.js"></script>
<script type="text/javascript" src="/glaf/scripts/plugins/bootstrap/pagination/js/jquery.pagination.extends.js"></script>
<script src='${contextPath}/scripts/plugins/bootstrap/grid/js/jquery.grid.extends.js' type='text/javascript'></script>

<style type="text/css">
    .kv-upload-progress{
        display: none;
    }

</style>

<script type="text/javascript">
    var contextPath = '${contextPath}';

    //数据返回位置
    var nameElementId = '${param.nameElementId}';   //返回的名称
    var objelementId = '${param.objelementId}'; //隐藏域

    var gobal_page_param_ = {
        maxlevel : 0,
    }

    var setting = {
        resizable: false,
        scrollable: false,
        clickUpdate: false,
        occupy: false,
        sortable: {},
        selectable: '',
        showInLine: true,
        sortDesc: false,
        combineAble: false,
        columns:[
            {title:'序号', field:'startIndex', width:80, sortable:false, 'attributes': {
                'style': 'text-align:center;'
            },},
            {title:'文件名称',field:'fileName', width:120,
                'attributes': {
                    'style': 'text-align:center;'
                },
            },
            {title:'创建人',field:'createBy', width:120,
                'attributes': {
                    'style': 'text-align:center;'
                },
            },
            {title:'创建时间',field:'createDate', width:120,
                'attributes': {
                    'style': 'text-align:center;'
                },
            },
        ],
        ajax: {
            read: {
                url: contextPath + "/mx/form/formfile/json",
                async: true,
                data: {},
                success: function(ret) {
                    if (ret) {
                        return {
                            total: ret.total,
                            rows: ret.data
                        }
                    } else {
                        return ret;
                    }
                }
            },
            parameterMap: function(data) {
                return JSON.stringify(data || {});
            },
            schema: {
                model: {
                    id: "id",
                },
                data: "rows",
                total: "total"
            }
        },
        pagination: {
            paging: true,
            page: 1,
            pageSizes: [],

            pageable: {
                refresh: true,
                buttonCount: 10,
                pageSizes: [],
            },
            pageSize: 3
        }
    }

   $(function(){
        var $mapGrid = $("#mapGrid");
        $mapGrid.grid(setting);
        var $fileInput = $("#fileInput");
        $fileInput .fileinput({
            // 'showUpload':false,
            // 'showPreview':false,
            // 'showUpload':false,
            // 'previewFileType':'any'
            language: 'zh', //设置语言
            uploadUrl: '/glaf/mx/form/formfile?method=upload', //上传的地址
            // allowedFileExtensions: ['json'],//接收的文件后缀
            'showPreview': false,
            maxFileSize: 2000,
            multiple: false,
            maxFileCount : 1,
            progress: '',
            uploadExtraData : function(previewId, index) {   //额外参数的关键点
                var obj = {
                    to:'to_dir',
                    randomParent : new UUID().createUUID(),
                };
                return obj;
            },

        })

        //导入完成后回调方法
        $fileInput.on('fileuploaded', function (event, data, previewId, index) {
            var response = data.response;
            if(response.length>0){
                alert("文件上传成功!");
            }else{
                alert("文件上传失败，请重新上传")
            }
            $fileInput.fileinput('clear');
            $fileInput.fileinput('unlock');
            $mapGrid.grid("query");
        });

        $("#queryBtn").click(function(event){
            $mapGrid.grid("query",{fileNameLike : $("#namelike").val()});
        })

        $("#sureBtn").click(function(event){
            var selectedRows = $mapGrid.grid("getSelectedRows");
            if(selectedRows && selectedRows.length > 0){
                var data = selectedRows[0];
                window.parent.$('#' + nameElementId).val(data.fileName);
                var results = [{
                    name : data.fileName,
                    id : data.id
                }]
                window.parent.$('#' + objelementId).val(JSON.stringify(results));
                parent.layer.close(parent.layer.getFrameIndex());
            }else{
                alert("请选择地图信息");
            }
        })
   })

</script>
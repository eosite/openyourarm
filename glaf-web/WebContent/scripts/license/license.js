$(function(){
    var setting = {
        ajax: {
            read: {
                url: contextPath + "/mx/form/license/read",
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
                    fields: {
                        }
                },
                data: "rows",
                total: "total"
            }
        },
        selectable: "sinagle",
        scrollable: false,
        columns:[
            {title:'名称', field:'name', sortable:false,'isEditor':false, 'attributes': {
                'style': 'text-align:center;width:10%;'
            },},
            {title:'代码', field:'code', sortable:false,'isEditor':false, 'attributes': {
                'style': 'text-align:center;width:10%;'
            },},
            {title:'创建者', field:'createBy', sortable:false,'isEditor':false, 'attributes': {
                'style': 'text-align:center;width:10%;'
            },},
            {title:'创建时间', field:'createTime_datetime', sortable:true,'isEditor':true, 
            'editor':{'component':'datetimepicker',},'format':'yyyy-MM-dd HH:mm:ss',
            'attributes': {
                'style': 'text-align:center;width:10%;'
            },},
            {title:'操作',field:'subject',sortable:true,'isEditor':true, 
            'attributes': {
                'style': 'text-align:center;width:20%;'
            },
            'template':function(data){
                var buttonHtml = '<button type="button" class="changeBtn btn blue btn-sm " >'+
                    '<i class="glyphicon glyphicon-edit"></i>修改</button>';
                buttonHtml += '<button type="button" class="editRuleBtn btn blue btn-sm " >'+
                    '<i class="glyphicon glyphicon-edit"></i>LICENSE规则</button>';
                buttonHtml += '<button type="button" class="createLicenseFileBtn btn red btn-sm " >'+
                    '<i class="glyphicon glyphicon-save"></i>生成LICENSE文件</button>';

                return buttonHtml;
            }
            }
        ],
        pagination: {
            paging: true,
            page: 1,
            pageSize: 10,
            pageSizes: [5, 10, 15, 20, 25, 50, 100, 200, 500], //分页条数选择
            buttonCount: 10,
            previousNext: "true", //是否使能分页翻页按钮
            numeric: "true", //是否使能数字页码按钮
            input: "false", //是否使能输入框翻页
            first: '首页', //首页按钮提示文本
            last: '末页', //末页按钮提示文本
            previous: '上一页', //上一页显示文本
            next: '下一页', //下一页显示文本
            refresh: 'false'
        }
    };

    /**
     * 日期格式化
     * @param  
     * @returns 
     */
    Date.prototype.format = function(format) {
        var args = {
            "M+": this.getMonth() + 1,
            "d+": this.getDate(),
            "H+": this.getHours(),
            "m+": this.getMinutes(),
            "s+": this.getSeconds(),
            "q+": Math.floor((this.getMonth() + 3) / 3), //quarter 
            "S": this.getMilliseconds()
        };
        if (/(y+)/.test(format))
            format = format.replace(RegExp.$1, (this.getFullYear() + "")
                .substr(4 - RegExp.$1.length));
        for (var i in args) {
            var n = args[i];
            if (new RegExp("(" + i + ")").test(format))
                format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? n : ("00" + n).substr(("" + n).length));
        }
        return format;
    };

    //环境信息grid
    var $licenseGrid = $("#licenseGrid");
    $licenseGrid.grid(setting);

    var validate_setting = {
        messages: {
            name: {
                "required": "必填",
            },
            code: {
                "required": "必填",
            },
            serverIp: {
                "required": "必填",
            },
            serverType: {
                "required": "必填",
            },
            publicKey: {
                "required": '必填',
            },
            privateKey: {
                "required": '必填',
            },
        },
        rules: {
            name: {
                "required": true,
            },
            code: {
                "required": true,
            },
            serverIp: {
                "required": true,
            },
            serverType: {
                "required": true,
            },
            publicKey: {
                "required": true,
            },
            privateKey: {
                "required": true,
            },
        },
        errorElement: 'span', //default input error message container
        errorClass: 'help-block help-block-error', // default input error message class
        focusInvalid: false, // do not focus the last invalid input
        ignore: "", // validate all fields including form hidden input
        invalidHandler: function(event, validator) { //display error alert on form submit              
        },
        errorPlacement: function(error, element) {
            element.parent().find(".help-block-error").remove();
            if (element.is(':checkbox')) {
                error.insertAfter(element.closest(".md-checkbox-list, .md-checkbox-inline, .checkbox-list, .checkbox-inline"));
            } else if (element.is(':radio')) {
                error.insertAfter(element.closest(".md-radio-list, .md-radio-inline, .radio-list,.radio-inline"));
            } else {
                error.insertAfter(element); // for other inputs, just perform default behavior
            }
        },
        highlight: function(element) { // hightlight error inputs
            $(element).closest('.form-group').addClass('has-error'); // set error class to the control group
        },

        unhighlight: function(element) { // revert the change done by hightlight
            $(element).closest('.form-group').removeClass('has-error'); // set error class to the control group
        },

        success: function(label) {
            label.closest('.form-group').removeClass('has-error'); // set success class to the control group
            label.remove();
        },
    };

    function converToJson(serializeArray){
        var datas = {};
        $.each(serializeArray,function(i,item){
            datas[item.name] = item.value;
        })
        return datas;
    }

    function _openEditLicenseRuleDialog(title,data){
        var that = this;
        //新增主题
        var width = $("body").width() - 150 +'px';
        var height = ($("body").height() - 130 + 6) + 'px';
        var dialog = BootstrapDialog.show({
            title: title || "License规则信息",
            draggable: false,
            css: {
                width: width,
            },
            onshown: function(dialog) {
                var $dialog = dialog.getModalBody();
                if (!data) {
                    data = data || {};
                }

                var $iframe = $("<iframe name='licenseRule' width='100%' height='"+height+"'></iframe>");
                $iframe.attr("src",contextPath + "/mx/form/license/editRuleView?id="+data.id);

                $dialog.append($iframe);
               
            }
        });
         dialog.getModalHeader().css({
            padding: '4px 4px',
        });
    }

    function _openEditLicenseDialog(title,data){
        var that = this;
        //新增主题
        var dialog = BootstrapDialog.show({
            title: title || "License信息",
            draggable: false,
            css: {
                width: '800px',
                height: '500px'
            },
            onshown: function(dialog) {
                var $dialog = dialog.getModalBody();
                if (!data) {
                    data = data || {};
                }

                var templateHtml = ArtTemplateDataUtils.convertOne(data, $("#editLicenseTemplate").html());
                $dialog.append(templateHtml);

                $dialog.on("click","#createRsaKey",function(){
                    var $thisBtn = $(this);
                    if($thisBtn.attr("disabled")){
                        return;
                    }
                    $thisBtn.attr("disabled","disabled");
                    $.ajax({
                        url: contextPath + '/mx/form/license/getRsaKey',
                        async: true,
                        data: {},
                        type : "POST",
                        success: function(ret) {
                            if(typeof ret == 'string'){
                                ret = JSON.parse(ret);
                            }
                            if(ret && ret.statusCode == '200'){
                                //赋值RSAKEY值
                                $dialog.find('textarea[name="publicKey"]').text(ret.publicKey);
                                $dialog.find('textarea[name="privateKey"]').text(ret.privateKey);    
                            }
                        },
                        complete : function(){
                            $thisBtn.removeAttr("disabled");
                        }
                    })
                })

                
                var saveFormValidator = $dialog.find("form").validate(validate_setting);
                $dialog.find(".saveBtn").click(function(event){
                    //保存操作
                    var $thisBtn = $(this);
                    if($thisBtn.attr("disabled")){
                        return;
                    }
                    //保存主题信息
                    if (!saveFormValidator.form()) {
                        return;
                    }

                    var params = converToJson($dialog.find("form").serializeArray());
                    if(params.locked && (params.locked == 'on' || params.locked == 'yes')){
                        params.locked = 1;
                    }else{
                        params.locked = 0;
                    }

                    $.ajax({
                        url: contextPath + '/mx/form/license/saveLicense',
                        async: true,
                        data: params,
                        type : "POST",
                        success: function(ret) {
                            if(typeof ret == 'string'){
                                ret = JSON.parse(ret);
                            }

                            if (ret && ret.statusCode == '200') {
                                alert("保存成功");
                                $licenseGrid.grid("query");
                                dialog.close();
                            } else {
                                alert("保存失败");
                            }
                        },
                        complete : function(){
                            $thisBtn.removeAttr("disabled");
                        }
                    })
                })

            }
        });
         dialog.getModalHeader().css({
            padding: '4px 4px',
        });
        that.dialog = dialog;
    };


    $("body").on("click",".editRuleBtn",function(event){
        var datas = $licenseGrid.grid("getSelectedRows");
        if(datas && datas.length > 0){
            _openEditLicenseRuleDialog("License规则信息编辑",datas[0]);
        }
    });

    $("body").on("click",".createLicenseFileBtn",function(event){
        //生成license
        var datas = $licenseGrid.grid("getSelectedRows");
        if(datas && datas.length > 0){
            var $iframe = $('<iframe id="down-file-iframe" />');
            var $form = $('<form target="down-file-iframe" method="' + 'post' + '" />');
            $form.attr('action', contextPath + '/mx/form/license/downloadXml');
            var $input = $('<input type="hidden" name="id">');
            $input.val(datas[0].id);
            $form.append($input);
            $iframe.append($form);
            $(document.body).append($iframe);
            $form[0].submit();
            $iframe.remove();
        }
        
    });

    $("#addLicenseBtn").click(function(event){
        //新增license
        _openEditLicenseDialog("创建License");
    })

    $("body").on("click",".changeBtn",function(event){
        //新增license
        var datas = $licenseGrid.grid("getSelectedRows");
        if(datas && datas.length > 0){
            _openEditLicenseDialog("修改License("+datas[0].name+")",datas[0]);
        }
        
    })
})
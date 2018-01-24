



var AppInbox = function () {

    var content = $('.inbox-content');
    var listListing = '';
    var pageNo=1;
	var pageSize=15;
	var type;
	var currBox;
	var totalPages;
	var getUrl=function(type){
		if(type=='inbox'){
			url = contextPath+'/rs/transdata/emailRec/list?orderBy=intOperat asc,listno asc';
		}else if(type=='important'){
			url = contextPath+'/rs/transdata/emailRec/list?orderBy=intOperat asc,listno asc&intOperat=0';
		}else if(type=='draft'){
			url = contextPath+'/rs/transdata/emailSend/list?intOperat=0';
		}else if(type=='sent'){
			url = contextPath+'/rs/transdata/emailSend/list?intOperat=1';
		}else if(type=='trash'){
			url = contextPath+'/rs/transdata/emailRec/list?intOperat=-1';
		}else if(type=='last'){
			url = contextPath+'/rs/transdata/emailRec/news?orderBy=intOperat asc,listno asc';
		}
		return url;
	} 
    var loadInbox = function (el, name) {
		$("#searchCondition").val("");
		$(".inbox-content .table").show();
		$(".inbox-content .table").nextAll().remove();
		currBox=el;
		var title = el.attr('data-title');
		type=el.attr('data-type');
		var searchCondition=$("#searchCondition").val();
		var url = getUrl(type);
		var maildate=$(".maildate").val();
        listListing = name;
        App.blockUI({
            target: content,
            overlayColor: 'none',
            animate: true
        });

        toggleButton(el);

        $.ajax({
            type: "POST",
            cache: false,
            url: url,
            contentType : "application/x-www-form-urlencoded",
			dataType : "json",
			data:{"page":pageNo,"rows":pageSize,"searchCondition":searchCondition,"maildate":maildate},
            success: function(res) 
            {
                toggleButton(el);

                App.unblockUI('.inbox-content');

                $('.inbox-nav > li.active').removeClass('active');
                el.closest('li').addClass('active');
                $('.inbox-header > h1').text(title);
                renderGrid(type,res);
                App.initUniform();
            },
            error: function(xhr, ajaxOptions, thrownError)
            {
                toggleButton(el);
				App.unblockUI('.inbox-content');
            },
            async: false
        });

        // handle group checkbox:
        jQuery('body').on('change', '.mail-group-checkbox', function () {
            var set = jQuery('.mail-checkbox');
            var checked = jQuery(this).is(":checked");
            jQuery(set).each(function () {
                $(this).attr("checked", checked);
            });
            jQuery.uniform.update(set);
        });
    }
	var searchInbox=function(el,name){
		loadInbox(el,name);
	}
	var handleDatePickers = function () {

        $.fn.datepicker.dates['zh-CN'] = {
	    days: ["周日", "周一", "周二", "周三", "周四", "周五", "周六", "周日"],
	    daysShort: ["日", "一", "二", "三", "四", "五", "六", "七"],
	    daysMin: ["日", "一", "二", "三", "四", "五", "六", "七"],
	    months: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
	    monthsShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
	    today: "今天",
	    clear: "清除"
	   };
		if (jQuery().datepicker) {
            $('.date-picker').datepicker({
                rtl: App.isRTL(),
                orientation: "left",
                autoclose: true,
				todayBtn: 'linked',
				language: 'zh-CN',
				todayHighlight:true,
				startDate:"",
				weekStart: 1,
				clearBtn:true
            }).on('changeDate',function(ev){
			      //var dateVal = ev.date;
				  if($(".maildate").val()!="")
				  {
					  $('.maildatemsg').text("["+$(".maildate").val()+"]");
				  }
			      else
				  {
					  $('.maildatemsg').text("");
				  }
				  loadInbox(currBox, listListing);
			   });
            //$('body').removeClass("modal-open"); // fix bug when inline picker is used in modal
        }

        /* Workaround to restrict daterange past date select: http://stackoverflow.com/questions/11933173/how-to-restrict-the-selectable-date-ranges-in-bootstrap-datepicker */
}
    var renderGrid=function (type,json){
	var pageNo=json.start==undefined?0:Math.ceil(json.start/pageSize)+1;
	var count=json.total;
	totalPages=Math.ceil(count/pageSize);
	$(".pagination-info").html(pageNo+"-"+totalPages+" of "+count);
    $(".inbox-content .table tbody").empty();
	//生成数据行
	var rows=json.rows;
	var rowdata;
	if(rows!=null){
	for(var i=0;i<rows.length;i++){
		rowdata=rows[i];
		var row=$("<tr></tr>");
		row.attr("data-messageid",rowdata.id);
		if(rowdata.intOperat==0){
			row.addClass("unread");
		}
		//checkbox列
		var column=$("<td></td>");
		column.addClass("inbox-small-cells");
		var check=$("<input class=\"mail-checkbox\" type=\"checkbox\">");
		column.append(check);
		row.append(column);
		//状态列
		column=$("<td></td>");
		column.addClass("inbox-small-cells");
		if(rowdata.intOperat==1){
			column.append("<i class=\"fa fa-envelope-o font-grey-silver\"></i>");
		}else{
			column.append("<i class=\"fa fa-envelope font-yellow-lemon\"></i>");
		}
		row.append(column);
		if(type=='inbox'||type=='last'||type=='important'||type=='trash'){
			//发件人
			column=$("<td></td>");
			column.addClass("view-message");
			var from=rowdata.from;
			if(from!=undefined){
				from=from.replace("<","");
				from=from.replace(">","");
				column.append(from);
			}
			row.append(column);
		}else{
			//收件人
			column=$("<td></td>");
			column.addClass("view-message");
			var to=rowdata.to;
			to=to.replace("<","");
			to=to.replace(">","");
			column.append(to);
			row.append(column);
		}
		//主题
		column=$("<td></td>");
		column.addClass("view-message");
		column.append(rowdata.subject);
		row.append(column);
		
		//附件
		column=$("<td></td>");
		if(rowdata.atts!=undefined&&rowdata.atts>0){
		   column.addClass("downloadAtt inbox-small-cells");
		   column.append("<i class=\"fa fa-paperclip\"></i>");
		}else{
		    column.addClass("inbox-small-cells");
		}
		row.append(column);
		//时间
		column=$("<td></td>");
		column.addClass("view-message text-right");
		column.append(rowdata.date_date);
		row.append(column);
		$(".inbox-content .table tbody").append(row);
	 }
	}
	//补充空行
	for(var j=0;j<pageSize-(rows==null?0:rows.length);j++){
		var row=$("<tr></tr>");
		row.attr("data-messageid",rows.length+j+1);
		//checkbox列
		var column=$("<td></td>");
		column.addClass("inbox-small-cells");
		column.css("height","36");
		row.append(column);
		//状态列
		column=$("<td></td>");
		column.addClass("inbox-small-cells");
		row.append(column);
		//发件人
		column=$("<td></td>");
		column.addClass("");
		row.append(column);
		
		//主题
		column=$("<td></td>");
		column.addClass("");
		row.append(column);
		
		//附件
		column=$("<td></td>");
		column.addClass("inbox-small-cells");
		row.append(column);
		//时间
		column=$("<td></td>");
		column.addClass("text-right");
		row.append(column);
		$(".inbox-content .table tbody").append(row);
	}
}
    var loadMessage = function (el, name, resetMenu) {
        var url;
        if(type=='inbox'||type=='last'||type=='important'||type=='trash'){
			url = contextPath+'/rs/transdata/emailRec/view';
		}else{
			url = contextPath+'/rs/transdata/emailSend/view';
		}
        App.blockUI({
            target: content,
            overlayColor: 'none',
            animate: true
        });

        toggleButton(el);

        var message_id = el.parent('tr').attr("data-messageid");  
        
        $.ajax({
            type: "GET",
            cache: false,
            url: url,
		    contentType : "application/x-www-form-urlencoded",
            dataType: "html",
            data: {'mailId': message_id},
            success: function(res) 
            {
                App.unblockUI(content);

                toggleButton(el);

                if (resetMenu) {
                    $('.inbox-nav > li.active').removeClass('active');
                }
                $('.inbox-header > h1').text('查看邮件');

                //content.html(res);
				$(".inbox-content .table").hide();
				content.append(res);
                App.initUniform();
            },
            error: function(xhr, ajaxOptions, thrownError)
            {
                toggleButton(el);
				App.unblockUI('.inbox-content');
            },
            async: false
        });
    }
    var downloadAtt=function(mailId){
		if(type=='inbox'||type=='last'||type=='important'||type=='trash'){
		  window.location = contextPath+"/rs/transdata/emailRecAtt/downloadAtt?mailId="+mailId;
		}
		else{
		  window.location = contextPath+"/rs/transdata/emailSendAtt/downloadAtt?mailId="+mailId;	
		}
	}
	var downloadAttFile=function(fileId){
		if(type=='inbox'||type=='last'||type=='important'||type=='trash'){
		  window.location = contextPath+"/rs/transdata/emailRecAtt/downloadAtt?fileId="+fileId;
		}
		else{
		  window.location = contextPath+"/rs/transdata/emailSendAtt/downloadAtt?fileId="+fileId;	
		}
	}
	//接收邮件
	var receive=function(el){
		 var url= contextPath+'/rs/transdata/emailRec/receive';
		
        App.blockUI({
            target: content,
            overlayColor: 'gray',
			opacity: 0.5,
			message: '<h2 style="color:#659be0;">正在收取邮件，请稍后...</h2>'
        });

        toggleButton(el);

        var message_id = el.parent('tr').attr("data-messageid"); 
        $.ajax({
            type: "GET",
            url: url,
		    contentType : "application/x-www-form-urlencoded",
            dataType: "json",
            data: {'mailId': message_id},
            success: function(res) 
            {
                App.unblockUI(content);
                toggleButton(el);
				//content.append(res);
				loadInbox(currBox, 'inbox');
                App.initUniform();
            },
            error: function(xhr, ajaxOptions, thrownError)
            {
                toggleButton(el);
				App.unblockUI('.inbox-content');
            },
            async: true
        });
	}
    var loadCompose = function (el) {
        var url = 'app_inbox_compose.html';

        App.blockUI({
            target: content,
            overlayColor: 'none',
            animate: true
        });

        toggleButton(el);

        // load the form via ajax
        $.ajax({
            type: "GET",
            cache: false,
            url: url,
            dataType: "html",
            success: function(res) 
            {
                App.unblockUI(content);
                toggleButton(el);

                $('.inbox-nav > li.active').removeClass('active');
                $('.inbox-header > h1').text('Compose');

                content.html(res);

                initFileupload();
                initWysihtml5();

                $('.inbox-wysihtml5').focus();
                App.initUniform();
            },
            error: function(xhr, ajaxOptions, thrownError)
            {
                toggleButton(el);
            },
            async: false
        });
    }

    var loadReply = function (el) {
        var messageid = $(el).attr("data-messageid");
        var url = 'app_inbox_reply.html';
        
        App.blockUI({
            target: content,
            overlayColor: 'none',
            animate: true
        });

        toggleButton(el);

        // load the form via ajax
        $.ajax({
            type: "GET",
            cache: false,
            url: url,
            dataType: "html",
            success: function(res) 
            {
                App.unblockUI(content);
                toggleButton(el);

                $('.inbox-nav > li.active').removeClass('active');
                $('.inbox-header > h1').text('Reply');

                content.html(res);
                $('[name="message"]').val($('#reply_email_content_body').html());

                handleCCInput(); // init "CC" input field

                initFileupload();
                initWysihtml5();
                App.initUniform();
            },
            error: function(xhr, ajaxOptions, thrownError)
            {
                toggleButton(el);
            },
            async: false
        });
    }

    var handleCCInput = function () {
        var the = $('.inbox-compose .mail-to .inbox-cc');
        var input = $('.inbox-compose .input-cc');
        the.hide();
        input.show();
        $('.close', input).click(function () {
            input.hide();
            the.show();
        });
    }

    var handleBCCInput = function () {

        var the = $('.inbox-compose .mail-to .inbox-bcc');
        var input = $('.inbox-compose .input-bcc');
        the.hide();
        input.show();
        $('.close', input).click(function () {
            input.hide();
            the.show();
        });
    }

    var toggleButton = function(el) {
        if (typeof el == 'undefined') {
            return;
        }
        if (el.attr("disabled")) {
            el.attr("disabled", false);
        } else {
            el.attr("disabled", true);
        }
    }
	
	var showlinkApplyModal=function(){
		$("#sendModal").modal('show');
	}
	var hidelinkApplyModal=function(){
		$("#sendModal").modal('hide');
	}
	var linkApply=function(el){
		var address=$("#address").val();
		if(address!=""){
			App.blockUI({
		            target: content,
		            overlayColor: 'gray',
					opacity: 0.5,
					message: '<h2 style="color:#659be0;">正在发送互联申请邮件，请稍后...</h2>'
		        });
			$.ajax({
            type: "POST",
            cache: false,
            url: contextPath+'/rs/transdata/emailSend/linkApply/',
            contentType : "application/x-www-form-urlencoded",
			dataType : "json",
			data:{"address":address},
            success: function(res) 
            {
                App.unblockUI(content);
				$.alert(0,"发送互联申请邮件成功!");
				$("#address").val("");
				hidelinkApplyModal();
				toggleButton(el);
                loadInbox(el, 'inbox');
                //App.initUniform();
            },
            error: function(xhr, ajaxOptions, thrownError)
            {
                toggleButton(el);
				App.unblockUI('.inbox-content');
				$.alert(1,"发送互联申请邮件失败!");
            },
            async: false
        });
		}
	}
    return {
        //main function to initiate the module
        init: function () {
            handleDatePickers();
            // 收件按钮绑定事件
            $('.inbox').on('click', '.receive', function () {
                receive($(this));
            });
			//互联按钮绑定事件
			$('.inbox').on('click', '.linkapply', function () {
                showlinkApplyModal();
            });

            // handle discard btn
            $('.inbox').on('click', '.inbox-discard-btn', function(e) {
                e.preventDefault();
                loadInbox($(this), listListing);
            });
			$('.inbox').on('click', '.mail-list', function(e) {
                e.preventDefault();
                searchInbox(currBox, listListing);
            });
            // handle reply and forward button click
            $('.inbox').on('click', '.reply-btn', function () {
                loadReply($(this));
            });

            // handle view message
            $('.inbox').on('click', '.view-message', function () {
                loadMessage($(this));
            });
			//下载附件按钮绑定
			$('.inbox').on('click', '.downloadAtt', function () {
				var mailId=$(this).parent().attr("data-messageid");
				downloadAtt(mailId);
			});
            $('.inbox').on('click', '.downloadAttFile', function () {
				var fileId=$(this).attr("att-id");
				downloadAttFile(fileId);
			});
            // handle inbox listing
            $('.inbox-nav > li > a').click(function () {
				//清空查询条件
				$(".maildatemsg").text("");
				$(".maildate").val("");
				pageNo=1;
                loadInbox($(this), 'inbox');
            });

            //handle compose/reply cc input toggle
            $('.inbox-content').on('click', '.mail-to .inbox-cc', function () {
                handleCCInput();
            });

            //handle compose/reply bcc input toggle
            $('.inbox-content').on('click', '.mail-to .inbox-bcc', function () {
                handleBCCInput();
            });
			//检索
			$('.search').click(function () {
                searchInbox(currBox, listListing);
            });
            //绑定翻页按钮
			$('.inbox-content').on('click', '.prePage', function () {
				if(pageNo>1){
				pageNo=pageNo-1;
                loadInbox(currBox, listListing);
				}
            });

			$('.inbox-content').on('click', '.nextPage', function () {
				if(pageNo+1<=totalPages){
					pageNo=pageNo+1;
					loadInbox(currBox, listListing);
				}
            });
			
			//互联申请确认按钮绑定事件
            $('.linkApplybt').click(function () {
                linkApply($("[data-type='sent']"));
            });
			
            //handle loading content based on URL parameter
            if (App.getURLParameter("a") === "view") {
                loadMessage();
            } else if (App.getURLParameter("a") === "compose") {
                loadCompose();
            } else {
               $('.inbox-nav > li:first > a').click();
            }

        }

    };

}();
/*
*提示框
*/
$.alert=function(type,msg){
	 var alertDom=$("<div style=\"display:none\" class=\"alert alert-dismissable myalert\"></div>");
	 alertDom.append("<button class=\"close\" aria-hidden=\"true\" type=\"button\" data-dismiss=\"alert\">×</button>");
	 if(type==1){
		 alertDom.addClass("alert-danger");
		
	 }else{
		 alertDom.addClass("alert-success");
	 }
	 alertDom.append("<h4>	<i class=\"icon fa fa-check\"></i> 操作提示!</h4>");
	 alertDom.append(msg);
	 $("body").append(alertDom);
	 $(".myalert").fadeIn(500).delay(2000).fadeOut(1000);;
}
jQuery(document).ready(function() {
    AppInbox.init();
});
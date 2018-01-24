function getTemplate(targetId){
	// var contextPath = getContextPath();
	var options = {"url" : contextPath+"/service/designer/"+dataRole+"/templates",
				   "contextPath" : contextPath};
	var params = {"getTemplates":"true"};
	if (options.url) {
		$.ajax({
			url : options.url,
			type :"post",
			data :params,
			dataType : "json",
			success : function(rdata) {
				if (rdata) {
					var nodes = eval(rdata);
	//				$.Toolbox.prototype.data = nodes;
					if(params.getTemplates=="true"&&nodes.templates&&nodes.templates.length>0){
					   initTemplate(targetId,nodes);
					}
				}
			},
			error : function() {
				console.log("初始化模板失败");
			}
		});
	}
}

function initTemplate(targetId,nodes){
	
	//var contextPath = getContextPath();

	var sb = new StringBuffer();
	var ids = [];
	sb.append('<tr>');
	$.each(nodes.templates, function(i, obj) {
		var imageUrl = contextPath+'/service/designer/'+obj.id_+'/getTemplateImage';
		imageUrl = obj.image_exists?imageUrl:contextPath+'/images/notfound.jpg';	
		ids.push({'id':obj.id,'id_':obj.id_,'imageUrl':imageUrl});

		if(i!=0&&(i+1)%5==0){
			sb.append('<td><li id="'+obj.id+'" class="mymedia" style="float: left;cursor:pointer;">');
			sb.append('<img class="mymedia-object" style="width:100px;height:100px;" src="'+imageUrl+'" alt="..." title="'+obj.title+'"/>');
			sb.append('<div class="mymedia-body" style="text-align:center;">');
			sb.append('<h4 class="mymedia-heading">'+obj.title+'</h4>');
			sb.append('</div>');
			sb.append('</li></td>');
			sb.append('</tr>');
			sb.append("<tr>");
		}else{
			sb.append('<td><li id="'+obj.id+'" class="mymedia" style="float: left;cursor:pointer;">');
			sb.append('<img class="mymedia-object" style="width:100px;height:100px;" src="'+imageUrl+'" alt="..." title="'+obj.title+'"/>');
			sb.append('<div class="mymedia-body" style="text-align:center;">');
			sb.append('<h4 class="mymedia-heading">'+obj.title+'</h4>');
			sb.append('</div>');
			sb.append('</li></td>');
		}
	});
	
	var str = sb.toString();
	if(str.endsWith("<tr>")){
		str = str.substr(0,str.lastIndexOf("<tr>"))
	}else if(str.endsWith("</td>")){
		str = str + "</tr>"; 
	}
	$("#"+targetId).html(str);
	
	$.each(ids, function(i, o) {
		$('#'+o.id).click(function(event) {
			$.ajax({
				url:contextPath+'/service/designer/getTemplate',
				type :"post",
				dataType:'json',
				data:{'id':o.id_},
				success:function(data){
					if(data.template){
						
						var content,id,newcontent;
						newcontent=$(data.template);

						//获取当前元素类型
						var dtStr = new Date().getTime();
						var id = dataRole + "_" + dtStr;
						newcontent.addClass(id);
						newcontent.addClass("nlayout_elem");
						newcontent.attr("id", id);
						newcontent.attr("data-role", dataRole);
						newcontent.attr("cname", id+'_控件');
						newcontent.attr("scope", "bootstrap");
						newcontent.attr("crtltype", "kendo");
						//元素内容可编辑
						newcontent.attr("contenteditable","true");


						var componentId ;
						var $a = $("#sidebar-menu",window.parent.parent.document).find('a[data_role="'+dataRole+'"]');
						if($a.length>0){
							componentId = $a.attr("data_id");
						}
						//控件初始渲染
						$.ajax({
							url: contextPath+'/service/designer/'+componentId+'/getTemplateRender',
							type :"post",
							dataType:'json',
							success:function(data){
								if(data.template){
									
									var js = data.template.replace(/(\$\('#'\+id\))/g,"newcontent");
									js = js.replace(/(\$\("#"\+id\))/g,"newcontent");
									eval(js);
								}
								
								var template = newcontent[0].outerHTML;
								template = $.base64.encode(template);

								editor.execCommand('insert'+dataRole,{
									"id":id,
									"dataRole" : dataRole,
									"imageUrl": o.imageUrl,
									"templateId":o.id_,
									"template" : template
								});
								// dialog.close(true);

							},
							error: function(xhr, ts) {
								console.log('控件渲染失败！');
							}
						});		

					}
				},
				error:function(xhr,ts) {
					console.log('获取模板失败！');
				}
			});
			
			// var dtStr = new Date().getTime();
			// var id = dataRole + "_" + dtStr;
			// editor.execCommand('insertTextboxBt',{
			// 	"id":id,
			// 	"templateId":o.id_,
			// 	"dataRole" : dataRole,
			// 	"imageUrl": o.imageUrl
			// });
			
		});
	});

}

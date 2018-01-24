<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*"%>
<%@ page import="com.glaf.core.util.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib uri="http://www.kendoui.com/jsp/tags" prefix="kendo"%>
<%
    String theme = com.glaf.core.util.RequestUtils.getTheme(request);
    request.setAttribute("theme", theme);
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="content-type" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>数据源配置</title>
<style type="text/css"> 
	.tdCls{
		background-color:#FCFCFC;
		align:left;
		height:16px;
	}
	.selectCls{
		width : 45%;
	}
	.selectTable{
		background-color: yellow;
	}
	.textCls{
		width : 80%;
	}
	
	div.line {  
	    position:absolute;  
	    z-index:2;  
	    width:3px;  
	    height:3px;  
	    font-size:1px;  
	    background-color:#0000FF;  
	    overflow:hidden;  
	}  
</style>
<%@ include file="/WEB-INF/views/inc/init_easyui.jsp"%>
<script type="text/javascript" src="${pageContext.request.contextPath }/webfile/js/datasetLine.js"></script>

<script type="text/javascript">

	
	function createLine(startXY,endXY,fn){
	
		var div = $('<div>',{
			'class' : 'lineCls'
		}).appendTo(document.body).on('click',function(){
			$(this).find('.line').css({
				'background-color':'red'
			});
			$(this).siblings().find('.line').css({
				'background-color':''
			});
			
			var connection = $(this).data('connection');
			
			if(connection){
				$.each(connection.tableNames,function(i,v){
					$('#t' + i).text(v);
					$('#f' + i).text(connection.fields[i]);
				});
			}
			
			$('#option-dialog').dialog({
				closed : false,
				buttons : [
				     {
				    	 text : '确定',
				    	 iconCls : 'icon-ok',
				    	 handler : function(){
				    		 
				    	 }
				     },
				     {
				    	 text : '取消',
				    	 iconCls : 'icon-cancel',
				    	 handler : function(){
				    		 $('#option-dialog').dialog('close');
				    	 }
				     }
				]
			});
			
		}).on('dblclick',function(){
			$(this).removeObj();
		});
		if(startXY.left > endXY.left){//交换卡片位置
			var s = startXY.jq.parents('tr');
			startXY.jq = s.find('.start_cls');
			var e = endXY.jq.parents('tr');
			endXY.jq = e.find('.end_cls');
		}
		var xy = [startXY.jq.attr('id'),endXY.jq.attr('id')],x_y = xy.join('/');
		var connection = {
				tableNames : [],
				fields : []
		};
		$.each([startXY,endXY],function(i,v){
			var item = v.jq,table = item.parents('table'),tableId = table.attr('id');
			
			var pos = item.offset();
			v.left = pos.left;
			var dialog = item.parents('.card-dialog');
			
			v.top = pos.top;
			v.topTmp = pos.top;
			
			var h = dialog.css('height').replace('px','')*1 + dialog.offset().top;

			if(v.top < dialog.offset().top){
				v.topTmp = dialog.offset().top - 15;
			}else if(h < v.top){
				v.topTmp = h - 15;
			}
			
			v.width = item.css("width").replace('px','') * 1;
			v.height = item.css("height").replace('px','') * 1;
			if(!table.data('divs')){
				table.data('divs',new Array()); 
			}
			table.data('divs').push(div[0]);//把线信息存到卡片上
			
			if(isadd){
				if(!lines[tableId]){
					lines[tableId] = new Object();
					lines[tableId].points = new Object();
				}
				lines[tableId].points[x_y] = xy;
			}
			
			if(!div.data('tbls')){
				div.data('tbls',{});
			}
			var tbls = div.data('tbls');
			if(!tbls[tableId])
				tbls[tableId] = x_y;
			
			
			connection.tableNames.push(tableId);
			connection.fields.push(item.parents('tr').find('span').text());
		});
		div.data('connection',connection);
		line(startXY.left + (startXY.width / 2) - 8, startXY.topTmp + (startXY.height / 2) + 2, endXY.left  + (endXY.width / 2) - 8, endXY.topTmp + (endXY.height / 2) + 2, div[0]);
		if(fn)
			fn(div);
	}
	
	var obj = new Object();
	
	var start = true;//是否为第一次点击
	var isadd = true;//是否为新增线
	var lines = {
		
	};
	
	var datas = {
		points : [
		    ['end_table01','start_table12'],
			['end_table01','start_table12'],
			['end_table02','start_table11'],
			['end_table12','start_table23'],
			['end_table11','start_table25'],
			['end_table14','start_table20'],
			['end_table03','start_table10']
		],
		tables : {
			table0 : {
				name : 'table0',
				top : 100,
				left : 200,
				fields : 6
			},
			table1 : {
				name : 'table1',
				top : 300,
				left : 500,
				fields : 17
			},
			table2 : {
				name : 'table2',
				top : 300,
				left : 900,
				fields : 14
			}
		}
	};
	
	$(function(){
		
		initpagepoints(datas);
		
		initEvents($('#dialog00 div'));
		
		$('.table_cls').draggable({
            revert:true,
            proxy:'clone',
			onStopDrag : function(e){
				var id = $(this).text();
				var datas = {tables : {}};
				datas.tables[id]= {
					top : e.pageY,
					left : e.pageX,
					fields : 6
				};
				initpagepoints(datas);
				initEvents($('#dialog' + id));
			}
        });
		
	});
	
	function initpagepoints(datas){
		$.each(datas.tables,function(id,v){
			var table = $('<table>',{
				id : id,
				style : "width:100%;"
			});
			for(var i = 0; i < v.fields; i ++){
				table.append("<tr rowindex='"+i+"' ><td class='start_cls' id='start_"+id + i+"' >o</td><td><a href=\"javascript:void(0)\" ><span field='id" + i + "'  style=\"font-size: 20px\" >id " + i + "</span></a></td><td class='end_cls' id='end_"+id + i+"' >o</td></tr>");
			}
			
			$("<div>",{
				id : 'dialog' + id,
				'class' : 'card-dialog'
			}).css({
				position:'absolute',
				width: 200,
				height : 300,
				left : v.left,
				top : v.top,
				border : "1px solid #000000",
				overflow :'auto'
			}).append(v.name).append(table).appendTo($("#dialog00"));
		});
		
		if(datas.points){
			initPoints(datas.points);
		}
		
	}
	
	function initPoints(points){
		$.each(points,function(i,ls){
			var start = null,end = null;			
			$.each(ls,function(index,v){
				var item = $('#' + v);
				if(!item[0])
					return false;
				var pos = item.offset();
				if(index == 0){
					start = pos;
					start.jq = item;
				}else{
					end = pos;
					end.jq = item;
				}
			});
			if(start && end){
				createLine(start,end);
			}
		});
	}
	
	function initEvents(jq){
		
		jq.find('span').on('mousedown',function(e){
			var $tr = $(this).parents('tr'),table = $(this).parents('table');
			function init(jq,selector,soe){
				var append = jq.find(selector);
				obj[soe] = append.offset();
				obj[soe + 'obj'] = append;
				obj[soe + 'jq'] = jq;
				obj[soe + 'table'] = table;
			}
			if(start){
				init($tr,'.end_cls','start');
			}else{
				if(obj.starttable[0] == table[0]){
					return false;
				}
				init($tr,'.start_cls','end');
				if(obj.start.left > obj.end.left){//反向操作
					
					init(obj.startjq,'.start_cls','start');
					
					init($tr,'.end_cls','end');
				}
				isadd = true;
				createLine({jq:obj.startobj},{jq:obj.endobj});
			}
			start = !start;
		});
		
		jq.find('tr').on('mouseover',function(e){
			$(this).css({
				"background-color" : 'red'
			});
		}).on('mouseout',function(e){
			$(this).css({
				"background-color" : ''
			});
		});
		
		jq.draggable({
			onDrag : function(e){
				removeLine($(this));
			},
			onStopDrag : function(e){
				rebuild($(this));
				$.log(lines);
			}
		}).scroll(function(e){
			timeOutFunc($(this));			
		}).on('click',function(){
			$(this).addClass('selectTable').siblings().removeClass('selectTable');
		});
	}
	
	
	var startTime;
	function timeOutFunc(jq){
		var currDatetime = new Date().getTime();
		if(!startTime)
			startTime = currDatetime;
		setTimeout(function(){
			if(currDatetime - startTime >= 500){
				removeLine(jq);
				rebuild(jq);
			}else{
				timeOutFunc(jq);
			}
		}, 100);
	}
	
	function removeLine(jq){
		var divs = jq.find('table').data('divs');
		if(divs){
			$.each(divs,function(i,v){
				if(v){
					$(this).remove();
				}
			});
		}
	}
	
	$.fn.removeObj = function(fn){
		var tbls = $(this).data('tbls');
		if(tbls){
			$.each(tbls,function(tableId,x_y){
				delete lines[tableId].points[x_y];
			});
			$(this).remove();
		}
	};
	
	function rebuild(jq){//根据卡片重新生成线
		var tableId = jq.find('table').attr('id');
		if(lines[tableId]){
			var points = lines[tableId].points;
			if(points){
				isadd = false;
				initPoints(points);
			}
		}
	}
	
	function removeTable(){
		$('.selectTable').each(function(){
			var table  = $(this).find('table');
			var divs = table.data('divs');
			if(divs){
				$.each(divs,function(i,v){
					if(v){
						$(this).removeObj();
					}
				});
			}
			var id = table.attr("id");
			if(lines[id]){
				delete lines[id];
			}
			$(this).remove();
		});
	}
	
	function savetableset(){
		
		$.each(lines,function(tableId,v){
			
			
		});
		
	}
	
	$.log = function(msg){
		console.log(msg);
	};
</script>
</head>
<body>
	<div>
		<a href="javascript:void(0);" class='table_cls'  >form_rule</a>
		<a href="javascript:void(0);" class='table_cls' >form_rule_property</a>
		<a href="javascript:void(0);" class='table_cls' >form_page</a>
	</div>
	<input value="移除表" type='button' onclick='removeTable();' >
	
	<input value="移除表" type='button' onclick='savetableset();' >
	<div id="dialog00" ></div>
	<div id="option-dialog" class="easyui-dialog" data-options="closed:true,title:'Join Properties'" style="width:400px;height:280px;" >
		
		Operator<hr>
		<table>
			<tr>
				<td>Table:</td>
				<td><span id='t0' >sdfsd</span></td>
				<td><span id='t1' >dsfdsfds</span></td>
			</tr>
			<tr>
				<td>Column:</td>
				<td><span id='f0' >sdfds</span> 
					<select>
						<option selected="selected" >等于</option>
						<option>大于</option>
						<option>小于</option>
					</select>
				</td>
				<td><span id='f1' >sdfsd</span></td>
			</tr>
		</table>
		Join Type<hr>
	</div>
</body>
</html>
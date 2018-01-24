<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; utf-8">
<title>Insert title here</title>
<%@ include file="/WEB-INF/views/inc/init_kendoui.jsp"%>
</head>
<script type="text/javascript">
  $(function(){
	  var nameElementId = '${param.nameElementId}';
		var objelementId = '${param.objelementId}';
		
		that=this;
		$('#btn1').on('click',function(){
			//构造影藏域
			var array=[];
			var NYobj=function(){
				this.NY='';
				this.name='';
				this.p='';
				this.s='';
			};
			var o1=new NYobj();
			var o2=new NYobj();
			//构造显示域
			var name="";
			o1.NY='Y';
			o1.name="勾选节点"
			o2.NY='N';
			o2.name="取消节点";
			name=name+o1.name+",";
			if($('#obj1p')[0].checked){
			   		var p1=$('#obj1p').val();
			   		name=name+p1;
			   		o1.p='p';
			   		name=name+",";
			}
			if($('#obj1s')[0].checked){
					var s1=$('#obj1s').val();
					name=name+s1;
					o1.s='s';
					name=name+",";
			}
		    name=name+o2.name;
			if($('#obj2p')[0].checked){
					var p2=$('#obj2p').val();
					name=name+p2;
					o2.p='p';
					name=name+",";
			}
			if($('#obj2s')[0].checked){
					var s2=$('#obj2s').val(); 
					name=name+s2;
					o2.s='s';
					name=name+",";
			}

			array.push(o1);
			array.push(o2);
			
			window.parent.$('#'+nameElementId).val(name);
			window.parent.$('#'+objelementId).val(JSON.stringify(array));
			parent.layer.close(parent.layer.getFrameIndex());
			
		});
		
		var JsonStr=window.parent.$('#'+objelementId).val();
		var Jsonobj = null;
		if(JsonStr){
			Jsonobj = eval('(' + JsonStr + ')');	
		}else{
			Jsonobj = [{
				p : true,
				s : true
			},{
				p : true,
				s : true
			}]
		}
		
		
		if(""!=Jsonobj[0].p){
			$('#obj1p')[0].checked=true;
		}
		if(""!=Jsonobj[0].s){
			$('#obj1s')[0].checked=true;
		}
		if(""!=Jsonobj[1].p){
			$('#obj2p')[0].checked=true;
		}
		if(""!=Jsonobj[1].s){
			$('#obj2s')[0].checked=true;
		}
		$('#btn2').on('click',function(e){
			parent.layer.close(parent.layer.getFrameIndex());
		});
		
  });
</script>
<body>
		<div align="left">
	   	 	<div>
	   	 		<label>勾选节点</label></br>
	   	 		<div align="center">
		   	 		<label>联动上级</label>:&nbsp;<input id="obj1p" type="checkbox" value="上级"/>
		   	 		&nbsp;&nbsp;&nbsp;
		   	 		<label>联动下级</label>:&nbsp;<input id="obj1s" type="checkbox" value="下级"/>
	   	 		</div>
	   	 	</div>
	   	 	<hr>
	   	 	<br>
	   	 	<div
	   	 		<label>取消节点</label></br>
	   	 		<div align="center" >
	   	 			<label>联动上级</label>:&nbsp;<input id="obj2p" type="checkbox" value="上级"/>
	   	 			&nbsp;&nbsp;&nbsp;
	   	 			<label>联动下级</label>:&nbsp;<input id="obj2s" type="checkbox" value="下级"/>
	   	 		</div>
			</div>
			<hr>
			<br>
			<div align="center">
				<button id="btn1">确定</button>
				&nbsp;&nbsp;&nbsp;&nbsp;
				<button id="btn2">取消</button>
			</div>
		</div>	
</body>
</html>
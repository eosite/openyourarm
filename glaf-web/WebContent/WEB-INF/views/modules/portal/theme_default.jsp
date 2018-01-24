
<%@ page contentType="text/html;charset=UTF-8"%>
<div id="themeDiv" class="themeDiv">
	<div class="tiptop">
		<span>主题切换</span><a></a>
	</div>
	<table class="themeTb">
		<!--
			<tr>
				<td style="width: 100px; text-align: center;">颜色：</td>
				<td class="themeColor" style="background: #066CAC;"
					onclick="chooseColor('default')">蓝色</td>
				<td class="themeColor" style="background: #E4E5E5;"
					onclick="chooseColor('gray')">灰色</td>
				<td class="themeColor" style="background: #FDD717;"
					onclick="chooseColor('sunny')">橙色</td>
			</tr>
			<tr>
				<td class="themeLayoutTitle">布局1：</td>
				<td class="themeLayoutContent" colspan="3"
					onclick="chooseLayout('home')">LOGO菜单</td>
			</tr>
			<tr>
				<td class="themeLayoutTitle">布局2：</td>
				<td colspan="3" class="themeLayoutContent"
					onclick="chooseLayout('home2')">左边折叠菜单</td>
			</tr>
			<tr>
				<td class="themeLayoutTitle">布局3：</td>
				<td colspan="3" class="themeLayoutContent"
					onclick="chooseLayout('home3')">横向菜单</td>
			</tr>
			<tr>
				<td class="themeLayoutTitle">布局4：</td>
				<td colspan="3" class="themeLayoutContent"
					onclick="chooseLayout('home_kendo')">kendo横向菜单</td>
			</tr>
			<tr>
				<td class="themeLayoutTitle">布局5：</td>
				<td colspan="3" class="themeLayoutContent"
					onclick="chooseLayout('kendo')">kendo_outlook菜单</td>
			</tr>
			<tr>
				<td class="themeLayoutTitle">布局6：</td>
				<td colspan="3" class="themeLayoutContent"
					onclick="chooseLayout('kendo2')">kendo_outlook图片菜单</td>
			</tr>
			 
		<tr>
			<td class="themeLayoutTitle">主题一：</td>
			<td colspan="3" class="themeLayoutContent"
				onclick="chooseLayout('main')">左边树形菜单</td>
		</tr>
		-->
		<c:forEach items="${themeList}" var="themeItem" varStatus="i">
			<tr>
				<td class="themeLayoutTitle">主题${i.index+1}：</td>
				<td colspan="3" class="themeLayoutContent"
					onclick="chooseLayout('${themeItem.ext1}', '${themeItem.ext2}','${themeItem.ext3}')">
					${themeItem.name}</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="4" style="height: 50px; text-align: center;"><input
				class="sure" type="button" value="确定" onclick="setMyTheme()"></td>
		</tr>
	</table>
	<input id="background" type="hidden" name="background"
		value="${userTheme.background}" /> <input id="backgroundType"
		name="backgroundType" type="hidden"
		value="${userTheme.backgroundType}" /> <input id="userThemeId"
		name="userThemeId" type="hidden" value="${userTheme.id}" />
</div>
<script type="text/javascript">
    function openThemeDialog(){
       //显示主题选择窗口
       $("#themeDiv").show();
    }
    var theme="${theme}";
    var layoutModel="default_home";
    var homeTheme="${homeTheme}";
    function chooseColor(cl){    
       this.theme=cl;
       $(".themeColor").removeClass("border");
       $(window.event.srcElement).addClass("border");
    }
    function chooseLayout(ly,color,uicolor){
       this.layoutModel=ly;
       this.theme=uicolor;
       this.homeTheme=color;
       $(".themeLayoutContent").css("background","");
       $(window.event.srcElement).css("background","yellow");
    }
    function setMyTheme(){
		   var theme = this.theme;
		   var homeTheme=this.homeTheme;
		   var layoutModel =this.layoutModel;
		   var background = $('#background').val();
		   var userThemeId = $('#userThemeId').val();
		   var backgroundType = $('#backgroundType').val();
		   var param = {'themeStyle':theme,'homeThemeStyle':homeTheme,'layoutModel':layoutModel , 'background':background 
				   ,'userThemeId':userThemeId,'backgroundType':backgroundType} ;
		   jQuery.ajax({
					   type: "POST",
					   url: '<%=request.getContextPath()%>/mx/my/home/setTheme',
			dataType : 'json',
			data : param,
			error : function(data) {
				alert('服务器处理错误！');
			},
			success : function(data) {
				location.reload();
				//document.getElementById("themeui").href=contextPath+"/scripts/easyui/themes/"+theme+"/easyui.css";
			}
		});
	}
	$(".themeLayoutContent").hover(function() {
		$(this).addClass("bgcolor");
	}, function() {
		$(this).removeClass("bgcolor");
	});
	$(".tiptop a").bind("click", function() {
		$("#themeDiv").hide();
	});
	function menu_MM_over(mmObj) {
		$(mmObj).removeClass("menu_bg1");
		$(mmObj).find("a").removeClass("menu_title1");
		$(mmObj).addClass("menu_bg2");
		$(mmObj).find("a").addClass("menu_title2");
	}
	function menu_MM_out(mmObj) {
		$(mmObj).removeClass("menu_bg2");
		$(mmObj).find("a").removeClass("menu_title2");
		$(mmObj).addClass("menu_bg1");
		$(mmObj).find("a").addClass("menu_title1");
	}
</script>
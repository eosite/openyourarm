 <%@ page contentType="text/html;charset=UTF-8"%>
    <!--修改密码窗口-->
    <div id="w" class="easyui-window" title="修改密码" collapsible="false" 
         minimizable="false" closed="true" maximizable="false" icon="icon-save"  
		 style="width: 400px; height: 250px; padding: 5px; background: #fafafa;">
        <div class="easyui-layout" fit="true">
            <div region="center" border="false" style="padding: 10px; background: #fff; border: 1px solid #ccc;">
                <table cellpadding=3>
                    <tr>
                        <td>
                         	 主      题：   
                        </td>
                        <td>
                        <select id="theme" name="theme">
							<option value="default" selected>蓝色</option>
							<option value="gray">灰色</option>
							<option value="sunny">橙色</option>
							<option value="bootstrap">bootstrap</option>
							<option value="metro">metro</option>
							<!-- <option value="metro-blue">metro-blue</option> -->
						</select>
                        </td>
                    </tr>
                    <tr>
                    	<td>布局模式：</td>
                    	<td>
                    	<select id="layoutModel" name="layoutModel">
							<option value="home" selected>LOGO菜单</option>
							<option value="home2">左边折叠菜单</option>
							<option value="home3">横向菜单</option>
							<option value="home_kendo">kendo横向菜单</option>
							<option value="kendo">kendo_outlook菜单</option>
							<option value="kendo2">kendo_outlook图片菜单</option>
							<option value="main">左边树形菜单</option>
						</select>
                    	</td>
                    </tr>
                    <!-- 
                     <tr>
                    	<td>背景图片</td>
                    	<td>
						
                    	</td>
                    </tr>
                     -->
                </table>
               			<input id="background" type="hidden" name="background" value="${userTheme.background}" />       
						<input id="backgroundType" name="backgroundType" type="hidden" value="${userTheme.backgroundType}" />                    	
						<input id="userThemeId" name="userThemeId" type="hidden" value="${userTheme.id}" />                    	
                 <script type="text/javascript">
			    	document.getElementById("layoutModel").value="${userTheme.layoutModel}";
					document.getElementById("theme").value="${userTheme.themeStyle}";
			    </script>
            </div>
            <div region="south" border="false" style="text-align: right; margin-top:2px; height: 32px; line-height: 32px;">
                <input type="button" id="btnEp" class="button" value="确 定" onclick="javascript:setMyTheme();"/> 
            </div>
        </div>
    </div>
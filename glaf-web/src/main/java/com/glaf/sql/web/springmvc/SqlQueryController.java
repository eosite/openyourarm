package com.glaf.sql.web.springmvc;




import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.util.DBUtils;
import com.glaf.form.core.util.MutilDatabaseBean;

/**
 * 数据查询接口
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/sql/query")
public class SqlQueryController {

	
	private static final String Map = null;
	protected MutilDatabaseBean mutilDatabaseBean;
	
	
	@javax.annotation.Resource 
	public void setMutilDatabaseBean(MutilDatabaseBean mutilDatabaseBean) { 
	this.mutilDatabaseBean = mutilDatabaseBean; 
	}
	

	@RequestMapping(value = "/mobileQuery", method = RequestMethod.POST) 
	@ResponseBody
	public byte[] mobileQuery(HttpServletRequest request,@RequestBody JSONObject model) throws Exception{
		
		JSONObject jsonObject = model.getJSONObject("param");
		String tableName=null;
		 String SqlCmd = jsonObject.get("SqlCmd").toString();
		 String SqlParams =jsonObject.get("SqlParams").toString();
		 String SqlType = jsonObject.get("SqlType").toString();
		
		JSONObject jsonObj = new JSONObject();
		JSONArray parseArray = JSON.parseArray(SqlParams);
		for (Object object : parseArray) { 
			jsonObj = (JSONObject) object;
			Set<String> keySet = jsonObj.keySet();
			for (String string : keySet) {
				Object json = jsonObj.get(string);
				SqlCmd =  SqlCmd.replace("@"+string, json.toString());
			}
		}
		String sqlselct=SqlCmd.toLowerCase();
		String[] split = sqlselct.split("\\s+");

		for(int i =0; i< split.length;i++){
			String string = split[i];
			if(string.equals("from") ||string.equals("update")){
				String substring = split[i+1];
				tableName = substring;
			}
		}
		
		JSONObject returnObj = new JSONObject();
		returnObj.put("code", 200);
		
		/*try {
			if(DBUtils.isAllowedTable(tableName)){
				 if(SqlType.equals("Select")){
					//返回数据集
					List<Map<String, Object>> dataListBySql = mutilDatabaseBean.getDataListBySql(SqlCmd,  0L);
					
					returnObj.put("rows", JSON.parseArray(JSONArray.toJSONString(dataListBySql)));
					//return JSONArray.toJSONString(dataListBySql).getBytes("UTF-8");
				}
			}else{
				returnObj.put("code", "没有权限");
			}
		} catch (Exception e) {
			returnObj.put("code", 500);
			returnObj.put("message", "执行错误"+e.getMessage());
		}
		*/
		return returnObj.toJSONString().getBytes("UTF-8");
	}
	
	
}

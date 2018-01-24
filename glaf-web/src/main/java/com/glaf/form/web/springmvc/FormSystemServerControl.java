package com.glaf.form.web.springmvc;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.util.RequestUtils;
import com.glaf.form.systemServer.SystemServerHandle;

@Controller("/form/formSystemServer")
@RequestMapping("/form/formSystemServer")
public class FormSystemServerControl {

	public FormSystemServerControl() {
		// TODO Auto-generated constructor stub
	}
	
	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request) {
		RequestUtils.setRequestParameterToAttribute(request);
		return new ModelAndView("/form/systemServer/newSystemServer");
	}
	
	@ResponseBody
	@RequestMapping("/getDataById")
	public byte[] getDataById(HttpServletRequest request) throws Exception {
		String id = RequestUtils.getString(request, "id"),result="";
		if(StringUtils.isNotEmpty(id)){
			String[] ids = id.split("_");
			String tabid = ids[0];
			String gridid = ids[1];
			
			String defaultDataFile = SystemProperties.getConfigRootPath() + "/views/form/systemServer/newSystemServerData.json";
			Path defaultPath = Paths.get(defaultDataFile);
			if(Files.exists(defaultPath)){
				String dataStr = new String(Files.readAllBytes(defaultPath),"UTF-8");
				JSONObject jsonData = JSON.parseObject(dataStr),tabData,gridData;
				JSONArray gridDataAry;
				String className;
				Class handler;
				SystemServerHandle model;
				for(String key : jsonData.keySet()){
					if(StringUtils.equals(key, tabid)){
						tabData = jsonData.getJSONObject(key);
						className = tabData.getString("className");
						if(StringUtils.isNotEmpty(className)){
							handler = Class.forName(className);
							model = (SystemServerHandle) handler.newInstance();
							result = model.getDefaultDataById(gridid);
						}else{
							gridDataAry = tabData.getJSONArray("gridData");
							for(Object obj : gridDataAry){
								gridData = (JSONObject)obj;
								if(StringUtils.equals(gridid, gridData.getString("key"))){
									result = gridData.toJSONString();
								}
							}
						}
						break;
					}
				}
			}
		}
		if(result != null){
			return result.getBytes("UTF-8");
		}else{
			return "".getBytes("UTF-8");
		}
	}
	
	@ResponseBody
	@RequestMapping("/jsonData")
	public byte[] jsonData(HttpServletRequest request) throws Exception { 
		String defaultDataFile = SystemProperties.getConfigRootPath() + "/views/form/systemServer/newSystemServerData.json";
		
		Path defaultPath = Paths.get(defaultDataFile);
		if(Files.exists(defaultPath)){
			String dataStr = new String(Files.readAllBytes(defaultPath),"UTF-8");
			
			JSONObject jsonData = JSON.parseObject(dataStr);
			JSONObject tabData;
			String className;
			Class handler;
			SystemServerHandle model;
			for(String key : jsonData.keySet()){
				tabData = jsonData.getJSONObject(key);
				className = tabData.getString("className");
				if(StringUtils.isNotEmpty(className)){
					handler = Class.forName(className);
					model = (SystemServerHandle) handler.newInstance();
					tabData.put("gridData",model.execute());
				}
			}
			
			return jsonData.toJSONString().getBytes("UTF-8");
		}
		return "[]".getBytes("UTF-8");
	}
}

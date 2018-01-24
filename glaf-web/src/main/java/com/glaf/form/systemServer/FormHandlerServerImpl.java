package com.glaf.form.systemServer;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Enumeration;
import java.util.Properties;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.SystemProperties;

public class FormHandlerServerImpl implements SystemServerHandle {

	public FormHandlerServerImpl() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String execute() {
		// TODO Auto-generated method stub
		Properties pro = new Properties();
		FileInputStream in;
		String url = "/mx/form/handler/execute?_handler_=";
		try {
			in = new FileInputStream(SystemProperties.getConfigRootPath() + "/conf/form_handler.properties");
			pro.load(in);
			in.close();
			
			JSONArray result = new JSONArray(),paramArray,newParamArray;
			Enumeration en = pro.propertyNames(); //得到配置文件的名字
			String key,value;
			JSONObject dataObj,paramObj,newObj;
			while(en.hasMoreElements()) {
				key = (String) en.nextElement();
				value = pro.getProperty(key);
				if(StringUtils.isNotEmpty(value)){
					dataObj = JSON.parseObject(new String(value.getBytes("ISO8859-1"), "UTF-8"));
					//生成新的格式
					newObj = new JSONObject();
					newObj.put("key", key);
					newObj.put("name", dataObj.getString("title"));
					newObj.put("url", url+key);
					//获取参数信息，生成新的格式
					paramArray = dataObj.getJSONArray("params");
					newParamArray = new JSONArray();
					for(Object obj:paramArray){
						dataObj = (JSONObject)obj;
						paramObj = new JSONObject();
						paramObj.put("code", dataObj.getString("name"));
						paramObj.put("title", dataObj.getString("title"));
						paramObj.put("desc", dataObj.getString("desc"));
						newParamArray.add(paramObj);
					}
					newObj.put("param", newParamArray);
					result.add(newObj);
				}
			}
			return result.toJSONString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public String getDefaultDataById(String id) {
		// TODO Auto-generated method stub
		Properties pro = new Properties();
		FileInputStream in;
		String url = "/mx/form/handler/execute?_handler_=";
		try {
			in = new FileInputStream(SystemProperties.getConfigRootPath() + "/conf/form_handler.properties");
			pro.load(in);
			in.close();
			
			JSONArray paramArray,newParamArray;
			Enumeration en = pro.propertyNames(); //得到配置文件的名字
			String key,value;
			JSONObject dataObj,paramObj,newObj= new JSONObject();
			while(en.hasMoreElements()) {
				key = (String) en.nextElement();
				if(StringUtils.isEquals(id, key)){
					//找到对应的id了
					value = pro.getProperty(key);
					if(StringUtils.isNotEmpty(value)){
						dataObj = JSON.parseObject(new String(value.getBytes("ISO8859-1"), "UTF-8"));
						//生成新的格式
						newObj.put("key", key);
						newObj.put("name", dataObj.getString("title"));
						newObj.put("url", url+key);
						//获取参数信息，生成新的格式
						paramArray = dataObj.getJSONArray("params");
						newParamArray = new JSONArray();
						for(Object obj:paramArray){
							dataObj = (JSONObject)obj;
							paramObj = new JSONObject();
							paramObj.put("code", dataObj.getString("name"));
							paramObj.put("title", dataObj.getString("title"));
							paramObj.put("desc", dataObj.getString("desc"));
							newParamArray.add(paramObj);
						}
						newObj.put("param", newParamArray);
					}
				}
			}
			return newObj.toJSONString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public JSONObject getAllTabData() {
		// TODO Auto-generated method stub
		return null;
	}


}

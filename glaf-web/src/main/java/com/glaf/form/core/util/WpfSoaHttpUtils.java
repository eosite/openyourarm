package com.glaf.form.core.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import com.jcraft.jsch.Logger;

public class WpfSoaHttpUtils {
	
	/**
	 * 在线查看文件（CELL或TIF）
	 * @param uri
	 * @param did
	 * @param sid
	 * @param token
	 * @param timestamp
	 * @param param
	 * @return
	 */
	public static JSONObject getFiledotByIdHttpMethod(String uri,String did,String sid,String token,String timestamp,JSONObject param){
		/*{
			"param": {
			    "sysId":"{7C308846-AD6C-4191-8D8A-F88D5F0C67AF}", 
			    "fileId": "20131210/admin-0000002",    
			    "fileType": "cell",                   　
			    "queryTable": "filedot",              
			    "queryField": "fileID,fname,file_content",　
			    "IdField":    "fileID",　　　　　　　　　　　　
			    "FileField":  "file_content"　　　　　　
			},
			"action": "getfiledotbyid",
			"did": "120",
			"sid": "fae86e1c-a70e-409d-9bcf-a46800b3a9b9",
			"token": "72-6D-06-EF-D9-7F-05-E9-15-11-17-22-03-85-F7-F8-22-43-5A-2C",
			"timestamp": "81-28-EB-2B-93-18-38-FF-CC-06-4A-0C-02-49-51-9B-09-A9-41-0B"
		}*/
		JSONObject obj = new JSONObject();
		obj.put("action", "getfiledotbyid");
		obj.put("did", did);
		obj.put("sid", sid);
		obj.put("token", token);
		obj.put("timestamp", timestamp);
		obj.put("param", param);
		JSONObject retObj = null;
		String retStr = WpfSoaHttpUtils.baseHttpMethod(uri, obj);
		if(StringUtils.isNotEmpty(retStr)){
			retObj = JSONObject.parseObject(retStr);
		}
		return retObj;
	}
	
	/**
	 * 登录授权
	 * @param uri
	 * @param did
	 * @param sid
	 * @param uid
	 * @param pwd
	 * @return
	 */
	public static JSONObject loginHttpMethod(String uri,String did,String sid,String uid,String pwd){
        JSONObject obj = new JSONObject();
    	obj.put("form", "");
    	obj.put("action", "loginv2");
    	// loginv2 匹配的是 userid 和 password_hash 
    	// mobilelogin 匹配的是 userid 和 password 
    	//obj.put("action", "mobileLogin");
    	obj.put("did", did);
    	obj.put("sid", sid);
    	obj.put("pageIndex", "0");
    	obj.put("pageSize", "0");
    	JSONObject param = new JSONObject();
    	param.put("uid", uid);
		param.put("pwd", pwd);
    	obj.put("param", param);
    	JSONObject retObj = null;
        String retStr = WpfSoaHttpUtils.baseHttpMethod(uri, obj);
        if(StringUtils.isNotEmpty(retStr)){
        	retObj = JSONObject.parseObject(retStr);
        }
        return retObj;
	}
	
	/**
	 * 基础 soa服务方法
	 * @param uri
	 * @param obj
	 * @return
	 */
	public static String baseHttpMethod(String uri,JSONObject obj){
		String retStr = null;
		CloseableHttpClient createDefault = HttpClients.createDefault(); 
		HttpPost httppost = new HttpPost(uri); 
        try {  
           /* {
            	"form": "",
            	"action": "mobileLogin",
            	"did": "120",
            	"sid": "fae86e1c-a70e-409d-9bcf-a46800b3a9b9",
            	"pageIndex": 0,
            	"pageSize": 0,
            	"param": {
              		"uid":"admin",  
              		"pwd":"fj87668438"   
                }
            }*/
            StringEntity stringEntity = new StringEntity(obj.toJSONString(),"UTF-8");
            httppost.setEntity(stringEntity);
            CloseableHttpResponse response = createDefault.execute(httppost);
            HttpEntity entity = response.getEntity();  
            if(entity!=null){
            	retStr = EntityUtils.toString(entity, "UTF-8");
            }
        }catch(Exception e){
        	e.printStackTrace();
        }
		return retStr;
	}
}

package com.glaf.form.systemServer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public interface SystemServerHandle {
	
	public String execute();
	
	public String getDefaultDataById(String id);
	
	public JSONObject getAllTabData();
}

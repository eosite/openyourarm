package com.glaf.workflow.core.util;

import java.util.LinkedHashMap;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

public class MyJSONObject extends JSONObject
 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static LinkedHashMap<String, Object> parseData(String data)
    {
         
        try
        {
            return JSON.parseObject(data, new TypeReference< LinkedHashMap<String,Object>>(){});
        } catch (Exception e)
        {
            e.printStackTrace();
        }
         
        return null;
    }
}
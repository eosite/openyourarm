package com.glaf.lanxin.utils;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class JSONFindUtils {
	/**
	 * 获取层级JSON数据
	 * 
	 * @param data
	 * @param json
	 * @param path
	 */
	public static void getJSONArrayByPath(JSONArray data, JSONObject json, String path) {
		if (path.equals("root")) {
		
		} else {
			String[] pathArr = StringUtils.split(path, ".");
			String doc = null;
			String nextPath = path.substring(path.indexOf(".") + 1, path.length());
			Object obj = null;
			JSONObject nextJson = null;
			if (pathArr.length > 0) {
				doc = pathArr[0];
				obj = json.get(doc);
				if (obj instanceof JSONObject) {
					if (pathArr.length == 1) {
						data.add((JSONObject) obj);
					} else {
						getJSONArrayByPath(data, (JSONObject) obj, nextPath);
					}
				} else if (obj instanceof JSONArray) {
					if (pathArr.length == 1) {
						data.addAll((JSONArray) obj);
					}
					for (int i = 0; i < ((JSONArray) obj).size(); i++) {
						nextJson = ((JSONArray) obj).getJSONObject(i);
						getJSONArrayByPath(data, nextJson, nextPath);
					}
				}
			}
		}
	}
}

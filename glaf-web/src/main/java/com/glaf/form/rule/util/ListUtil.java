package com.glaf.form.rule.util;

import java.util.List;

import com.alibaba.fastjson.JSONArray;

/**
 * 数组集合工具类
 * @author J
 *
 */
public class ListUtil {
	
	/**
	 * 数组拼接 
	 * @param o
	 * @param flag 拼接字符串
	 * @return
	 */
	public static String join(Object[] o, String flag) {
		return join(o, flag, "","");
	}
	/**
	 * 数组拼接
	 * @param o
	 * @param flag 拼接字符串
	 * @param fix  前后缀
	 * @return
	 */
	public static String join(Object[] o, String flag,String fix) {
		return join(o, flag, fix,fix);
	}
	/**
	 * 数组拼接 
	 * @param o
	 * @param flag 拼接字符串
	 * @param prefix 前缀
	 * @param suffix 后缀
	 * @return
	 */
	public static String join(Object[] o, String flag,String prefix,String suffix) {
		StringBuffer str_buff = new StringBuffer();
		for (int i = 0, len = o.length; i < len; i++) {
			str_buff.append(prefix+String.valueOf(o[i])+suffix);
			if (i < len - 1)
				str_buff.append(flag);
		}
		
		return str_buff.toString();
	}

	/**
	 * 集合拼接
	 * @param o
	 * @param flag 拼接字符串
	 * @return
	 */
	public static String joinList(List<String> o, String flag) {
		return joinList(o, flag, "", "");
	}
	/**
	 * 集合拼接
	 * @param o
	 * @param flag 拼接字符串
	 * @param fix  前后缀
	 * @return
	 */
	public static String joinList(List<String> o, String flag,String fix) {
		return joinList(o, flag, fix, fix);
	}
	/**
	 * 集合拼接
	 * @param o
	 * @param flag 拼接字符串
	 * @param prefix 前缀
	 * @param suffix 后缀
	 * @return
	 */
	public static String joinList(List<String> o, String flag,String prefix,String suffix) {
		StringBuffer str_buff = new StringBuffer();
		int i = 0;
		int len = o.size();
		for (String object : o) {
			str_buff.append(prefix+String.valueOf(object)+suffix);
			if (i < len - 1)
				str_buff.append(flag);
			i++;
		}
		return str_buff.toString();
	}
	
	/**
	 * JSON数组拼接
	 * @param o
	 * @param flag 拼接字符串
	 * @return
	 */
	public static String joinJSONArray(JSONArray o, String flag) {
		return joinJSONArray(o, flag, "","");
	}
	/**
	 * JSON数组拼接
	 * @param o
	 * @param flag 拼接字符串
	 * @param fix  前后缀
	 * @return
	 */
	public static String joinJSONArray(JSONArray o, String flag,String fix) {
		return joinJSONArray(o, flag, fix, fix);
	}
	/**
	 * JSON数组拼接
	 * @param o
	 * @param flag 拼接字符串
	 * @param prefix 前缀
	 * @param suffix 后缀
	 * @return
	 */
	public static String joinJSONArray(JSONArray o, String flag,String prefix,String suffix) {
		StringBuffer str_buff = new StringBuffer();
		int i = 0;
		int len = o.size();
		for (Object object : o) {
			str_buff.append(prefix+String.valueOf(object)+suffix);
			if (i < len - 1)
				str_buff.append(flag);
			i++;
		}
		return str_buff.toString();
	}
	
	
}

package com.glaf.form.rule.util;

import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.web.util.HtmlUtils;

import com.alibaba.fastjson.JSONObject;

/**
 * 防注入工具类
 * 
 * @author J
 *
 */
public class InjectUtils {
	/**
	 * sql注入
	 * 
	 * @param str
	 * @return
	 */
	public static String escapeSql(String str) {
		return StringEscapeUtils.escapeSql(str);
	}

	/**
	 * sql注入
	 * 
	 * @param obj
	 * @return
	 */
	public static void escapeSql(JSONObject obj) {
		Set<String> keySet = obj.keySet();
		if (!keySet.isEmpty()) {
			for (String key : keySet) {
				obj.put(key, StringEscapeUtils.escapeSql(obj.getString(key)));
			}
		}
	}

	/**
	 * xss注入
	 * 
	 * @param obj
	 * @return
	 */
	public static void escapeHtml(JSONObject obj) {
		Set<String> keySet = obj.keySet();
		if (!keySet.isEmpty()) {
			for (String key : keySet) {
				// obj.put(key,
				// HtmlUtils.htmlEscape(obj.getString(key),"UTF-8"));
				obj.put(key, stripXSSAndSql(obj.getString(key)));
			}
		}
	}

	/**
	 * 简单处理
	 * 
	 * @param value
	 * @return
	 */
	private static String xssEncode(String value) {
		if (value == null || value.isEmpty()) {
			return value;
		}
		value = value.replaceAll("eval\\((.*)\\)", "");
		value = value.replaceAll("[\\\"\\\'][\\s]*javascript:(.*)[\\\"\\\']", "\"\"");
		value = value.replaceAll("(?i)<script.*?>.*?<script.*?>", "");
		value = value.replaceAll("(?i)<script.*?>.*?</script.*?>", "");
		value = value.replaceAll("(?i)<.*?javascript:.*?>.*?</.*?>", "");
		value = value.replaceAll("(?i)<.*?\\s+on.*?>.*?</.*?>", "");
		return value;
	}

	public static String stripXSSAndSql(String value) {
		if (value != null) {
			// NOTE: It's highly recommended to use the ESAPI library and
			// uncomment the following line to
			// avoid encoded attacks.
			// value = ESAPI.encoder().canonicalize(value);
			// Avoid null characters
			// Avoid anything between script tags
			Pattern scriptPattern = Pattern.compile("<[\r\n| | ]*script[\r\n| | ]*>(.*?)</[\r\n| | ]*script[\r\n| | ]*>", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			// Avoid anything in a
			// src="http://www.yihaomen.com/article/java/..." type of
			// e-xpression
			//scriptPattern = Pattern.compile("src[\r\n| | ]*=[\r\n| | ]*[\\\"|\\\'](.*?)[\\\"|\\\']", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			scriptPattern = Pattern.compile("([a|iframe])(\\s+(\\w*=(\"|\')\\w*(\"|\'))*\\s*)src[\r\n| | ]*=[\r\n| | ]*[\\\"|\\\'](.*?)[\\\"|\\\']", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("$1$2");
			// Remove any lonesome </script> tag
			scriptPattern = Pattern.compile("</[\r\n| | ]*script[\r\n| | ]*>", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			// Remove any lonesome <script ...> tag
			scriptPattern = Pattern.compile("<[\r\n| | ]*script(.*?)>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			// Avoid eval(...) expressions
			scriptPattern = Pattern.compile("eval\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			// Avoid e-xpression(...) expressions
			scriptPattern = Pattern.compile("e-xpression\\((.*?)\\)", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
			// Avoid javascript:... expressions
			scriptPattern = Pattern.compile("javascript[\r\n| | ]*:[\r\n| | ]*", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			// Avoid vbscript:... expressions
			scriptPattern = Pattern.compile("vbscript[\r\n| | ]*:[\r\n| | ]*", Pattern.CASE_INSENSITIVE);
			value = scriptPattern.matcher(value).replaceAll("");
			// Avoid onload= expressions
			scriptPattern = Pattern.compile("onload(.*?)=", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE | Pattern.DOTALL);
			value = scriptPattern.matcher(value).replaceAll("");
		}
		return value;
	}
}

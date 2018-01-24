package com.glaf.expression.core.util;

import java.text.DecimalFormat;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public class StringFunc {
	/**
	 * 字符串拼接
	 * 
	 * @param a1
	 * @param a2
	 * @return
	 */
	public static String concatenate(String s1, String s2) {
		return (s1 != null ? s1 : "") + (s2 != null ? s2 : "");
	}

	/**
	 * 返回字符串字符个数
	 * 
	 * @param s1
	 * @return
	 */
	public static int len(String s1) {
		return s1 != null ? s1.length() : 0;
	}
	
	/**
	 * 获取字符串中子串的数量
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static int getOccurSize(String s1,String s2){
		int o = 0;
		int index=-1;
		while((index=s1.indexOf(s2,index))>-1){
			++index;
			++o;
		}
		return o;
	 }
	
	/**
	 * 返回字符串匹配的起始位置
	 * 
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static int find(String s1, String s2) {
		if (s1 == null || s2 == null) {
			return -1;
		}
		return s1.indexOf(s2);
	}

	/**
	 * 返回字符串匹配的起始位置 不区分大小写
	 * 
	 * @param s1
	 * @param s2
	 * @return
	 */
	public static int search(String s1, String s2) {
		if (s1 == null || s2 == null) {
			return -1;
		}
		return s1.toUpperCase().indexOf(s2.toUpperCase());
	}

	/**
	 * 转小写
	 * 
	 * @param s1
	 * @return
	 */
	public static String lower(String s1) {
		return s1 != null ? s1.toLowerCase() : "";
	}

	/**
	 * 转大写
	 * 
	 * @param s1
	 * @return
	 */
	public static String upper(String s1) {
		return s1 != null ? s1.toUpperCase() : "";
	}

	/**
	 * 字符串截取
	 * 
	 * @param s1
	 * @param startIndex
	 * @param length
	 * @return
	 */
	public static String subString(String s1, int startIndex, int length) {
		return s1 != null ? s1.substring(startIndex, startIndex + length) : "";
	}

	/**
	 * 去前后空格
	 * 
	 * @param s1
	 * @return
	 */
	public static String trim(String s1) {
		return s1 != null ? s1.trim() : "";
	}

	/**
	 * 替换第一个
	 * 
	 * @param s
	 * @param ostr
	 * @param nstr
	 * @return
	 */
	public static String replace(String s, String ostr, String nstr) {
		return s != null ? s.replaceFirst(ostr, nstr) : "";
	}

	/**
	 * 替换所有
	 * 
	 * @param s
	 * @param ostr
	 * @param nstr
	 * @return
	 */
	public static String replaceAll(String s, String ostr, String nstr) {
		return s != null ? s.replaceAll(ostr, nstr) : "";
	}

	/**
	 * 包含数组中的某一元素
	 * 
	 * @param s
	 * @param variables
	 * @return
	 */
	public static boolean arrayContains(String s, String containTexts) {
		if (s != null && containTexts != null) {
			String[] texts = containTexts.split(",");
			for (int i = 0; i < texts.length; i++) {
				if (s.contains(texts[i])) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 分割
	 * 
	 * @param s
	 * @param regex
	 * @return
	 */
	public static String splitStr(String s, String regex, String flag) {
		if (s == null || "".equals(s.trim())) {
			return null;
		}
		String retStr = "";
		String[] strings = s.split(regex);
		int count = 0;
		for (String string : strings) {
			count++;
			if (!string.startsWith(flag))
				string = flag + string;
			if (!string.endsWith(flag))
				string = string + flag;
			retStr += string + (strings.length == count ? "" : ",");
		}
		return retStr;
	}
	
	public static String format(Object value, String formatter) {
		if (StringUtils.isBlank(formatter) || value == null) {
			return "";
		}
		DecimalFormat df = new DecimalFormat(formatter);
		return df.format(NumberUtils.toLong(value.toString()));
	}

}

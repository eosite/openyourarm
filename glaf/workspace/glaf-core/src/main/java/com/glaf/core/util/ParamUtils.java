/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.core.util;

import java.io.IOException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;

public class ParamUtils {

	public static Object get(Map<String, Object> dataMap, String key) {
		if (dataMap == null || key == null) {
			return null;
		}
		Object value = dataMap.get(key);
		if (value == null) {
			value = dataMap.get(key.toLowerCase());
		}
		if (value == null) {
			value = dataMap.get(key.toUpperCase());
		}
		return value;
	}

	public static Boolean getBoolean(Map<String, Object> dataMap, String key) {
		Boolean result = null;
		if (dataMap == null || key == null) {
			return result;
		}
		Object value = dataMap.get(key);
		if (value == null) {
			value = dataMap.get(key.toLowerCase());
		}
		if (value == null) {
			value = dataMap.get(key.toUpperCase());
		}
		if (value != null) {
			if (value instanceof Boolean) {
				result = (Boolean) value;
			} else if (value instanceof String) {
				result = Boolean.valueOf(value.toString());
			} else {
				result = Boolean.valueOf(value.toString());
			}
		}
		return result;
	}

	public static Date getDate(Map<String, Object> dataMap, String key) {
		Date result = null;
		if (dataMap == null || key == null) {
			return null;
		}
		Object value = dataMap.get(key);
		if (value == null) {
			value = dataMap.get(key.toLowerCase());
		}
		if (value == null) {
			value = dataMap.get(key.toUpperCase());
		}
		if (value != null && StringUtils.isNotEmpty(value.toString())) {
			if (value instanceof String) {
				result = DateUtils.toTimestamp(value.toString());
			} else if (value instanceof Date) {
				result = (Date) value;
			} else if (value instanceof byte[]) {
				byte[] x = (byte[]) value;
				try {
					value = com.glaf.core.util.serializer.SerializationUtils.deserialize(x);
					if (value instanceof Date) {
						result = (Date) value;
					}
				} catch (IOException ex) {
				}
			}
		}
		return result;
	}

	public static double getDouble(Map<String, Object> dataMap, String key) {
		double result = 0.0D;
		Object value = dataMap.get(key);
		if (value == null) {
			value = dataMap.get(key.toLowerCase());
		}
		if (value == null) {
			value = dataMap.get(key.toUpperCase());
		}

		if (value != null && StringUtils.isNotEmpty(value.toString())) {
			if (value instanceof String) {
				String tmp = (String) value;
				tmp = StringTools.replace(tmp, ",", "");
				result = Double.parseDouble(tmp);
			} else if (value instanceof Integer) {
				Integer x = (Integer) value;
				result = x.doubleValue();
			} else if (value instanceof Long) {
				Long x = (Long) value;
				result = x.doubleValue();
			} else if (value instanceof Double) {
				Double x = (Double) value;
				result = x.doubleValue();
			} else if (value instanceof byte[]) {
				byte[] x = (byte[]) value;
				try {
					value = com.glaf.core.util.serializer.SerializationUtils.deserialize(x);
					result = Double.parseDouble(value.toString());
				} catch (IOException ex) {
				}
			} else {
				String tmp = value.toString();
				tmp = StringTools.replace(tmp, ",", "");
				result = Double.parseDouble(tmp);
			}
		} else {
			result = MapUtils.getDoubleValue(dataMap, key);
		}

		return result;
	}

	public static Double getDoubleValue(Map<String, Object> dataMap, String key) {
		Double result = null;
		Object value = dataMap.get(key);
		if (value == null) {
			value = dataMap.get(key.toLowerCase());
		}
		if (value == null) {
			value = dataMap.get(key.toUpperCase());
		}
		if (value != null && StringUtils.isNotEmpty(value.toString())) {
			if (value instanceof String) {
				String tmp = (String) value;
				tmp = StringTools.replace(tmp, ",", "");
				result = Double.parseDouble(tmp);
			} else if (value instanceof Integer) {
				Integer x = (Integer) value;
				result = x.doubleValue();
			} else if (value instanceof Long) {
				Long x = (Long) value;
				result = x.doubleValue();
			} else if (value instanceof Double) {
				Double x = (Double) value;
				result = x.doubleValue();
			} else if (value instanceof byte[]) {
				byte[] x = (byte[]) value;
				try {
					value = com.glaf.core.util.serializer.SerializationUtils.deserialize(x);
					result = Double.parseDouble(value.toString());
				} catch (IOException ex) {
				}
			} else {
				String tmp = value.toString();
				tmp = StringTools.replace(tmp, ",", "");
				result = Double.parseDouble(tmp);
			}
		} else {
			result = MapUtils.getDouble(dataMap, key);
		}
		return result;
	}

	public static int getInt(Map<String, Object> dataMap, String key) {
		int result = 0;
		Object value = dataMap.get(key);
		if (value == null) {
			value = dataMap.get(key.toLowerCase());
		}
		if (value == null) {
			value = dataMap.get(key.toUpperCase());
		}
		if (value != null && StringUtils.isNotEmpty(value.toString())) {
			if (value instanceof String) {
				String tmp = (String) value;
				tmp = StringTools.replace(tmp, ",", "");
				if (tmp.indexOf(".") != -1) {
					tmp = tmp.substring(0, tmp.indexOf("."));
				}
				result = Integer.parseInt(tmp);
			} else if (value instanceof Integer) {
				Integer x = (Integer) value;
				result = x.intValue();
			} else if (value instanceof Long) {
				Long x = (Long) value;
				result = x.intValue();
			} else if (value instanceof Double) {
				Double x = (Double) value;
				result = x.intValue();
			} else if (value instanceof byte[]) {
				byte[] x = (byte[]) value;
				try {
					value = com.glaf.core.util.serializer.SerializationUtils.deserialize(x);
					result = Integer.parseInt(value.toString());
				} catch (IOException ex) {
				}
			} else {
				String tmp = value.toString();
				tmp = StringTools.replace(tmp, ",", "");
				if (tmp.indexOf(".") != -1) {
					tmp = tmp.substring(0, tmp.indexOf("."));
				}
				result = Integer.parseInt(tmp);
			}
		}
		return result;
	}

	public static Integer getIntValue(Map<String, Object> dataMap, String key) {
		Integer result = null;
		Object value = dataMap.get(key);
		if (value == null) {
			value = dataMap.get(key.toLowerCase());
		}
		if (value == null) {
			value = dataMap.get(key.toUpperCase());
		}
		if (value != null && StringUtils.isNotEmpty(value.toString())) {
			if (value instanceof String) {
				String tmp = (String) value;
				tmp = StringTools.replace(tmp, ",", "");
				if (tmp.indexOf(".") != -1) {
					tmp = tmp.substring(0, tmp.indexOf("."));
				}
				result = Integer.parseInt(tmp);
			} else if (value instanceof Integer) {
				Integer x = (Integer) value;
				result = x.intValue();
			} else if (value instanceof Long) {
				Long x = (Long) value;
				result = x.intValue();
			} else if (value instanceof Double) {
				Double x = (Double) value;
				result = x.intValue();
			} else if (value instanceof byte[]) {
				byte[] x = (byte[]) value;
				try {
					value = com.glaf.core.util.serializer.SerializationUtils.deserialize(x);
					result = Integer.parseInt(value.toString());
				} catch (IOException ex) {
				}
			} else {
				String tmp = value.toString();
				tmp = StringTools.replace(tmp, ",", "");
				if (tmp.indexOf(".") != -1) {
					tmp = tmp.substring(0, tmp.indexOf("."));
				}
				result = Integer.parseInt(tmp);
			}
		}
		return result;
	}

	public static long getLong(Map<String, Object> dataMap, String key) {
		long result = 0;
		Object value = dataMap.get(key);
		if (value == null) {
			value = dataMap.get(key.toLowerCase());
		}
		if (value == null) {
			value = dataMap.get(key.toUpperCase());
		}
		if (value != null && StringUtils.isNotEmpty(value.toString())) {
			if (value instanceof String) {
				String tmp = (String) value;
				tmp = StringTools.replace(tmp, ",", "");
				if (tmp.indexOf(".") != -1) {
					tmp = tmp.substring(0, tmp.indexOf("."));
				}
				result = Long.parseLong(tmp);
			} else if (value instanceof Integer) {
				Integer x = (Integer) value;
				result = Long.valueOf(x.intValue());
			} else if (value instanceof Long) {
				Long x = (Long) value;
				result = x.longValue();
			} else if (value instanceof Double) {
				Double x = (Double) value;
				result = x.longValue();
			} else if (value instanceof byte[]) {
				byte[] x = (byte[]) value;
				try {
					value = com.glaf.core.util.serializer.SerializationUtils.deserialize(x);
					result = Long.parseLong(value.toString());
				} catch (IOException ex) {
				}
			} else {
				String tmp = value.toString();
				tmp = StringTools.replace(tmp, ",", "");
				if (tmp.indexOf(".") != -1) {
					tmp = tmp.substring(0, tmp.indexOf("."));
				}
				result = Long.parseLong(tmp);
			}
		}
		return result;
	}

	public static Long getLongValue(Map<String, Object> dataMap, String key) {
		Long result = null;
		Object value = dataMap.get(key);
		if (value == null) {
			value = dataMap.get(key.toLowerCase());
		}
		if (value == null) {
			value = dataMap.get(key.toUpperCase());
		}
		if (value != null && StringUtils.isNotEmpty(value.toString())) {
			if (value instanceof String) {
				String tmp = (String) value;
				tmp = StringTools.replace(tmp, ",", "");
				if (tmp.indexOf(".") != -1) {
					tmp = tmp.substring(0, tmp.indexOf("."));
				}
				result = Long.parseLong(tmp);
			} else if (value instanceof Integer) {
				Integer x = (Integer) value;
				result = Long.valueOf(x.intValue());
			} else if (value instanceof Long) {
				Long x = (Long) value;
				result = x.longValue();
			} else if (value instanceof Double) {
				Double x = (Double) value;
				result = x.longValue();
			} else if (value instanceof byte[]) {
				byte[] x = (byte[]) value;
				try {
					value = com.glaf.core.util.serializer.SerializationUtils.deserialize(x);
					result = Long.parseLong(value.toString());
				} catch (IOException ex) {
				}
			} else {
				String tmp = value.toString();
				tmp = StringTools.replace(tmp, ",", "");
				if (tmp.indexOf(".") != -1) {
					tmp = tmp.substring(0, tmp.indexOf("."));
				}
				result = Long.parseLong(tmp);
			}
		}
		return result;
	}

	public static Object getObject(Map<String, Object> dataMap, String key) {
		if (dataMap == null || key == null) {
			return null;
		}
		Object result = dataMap.get(key);
		if (result == null) {
			result = dataMap.get(key.toLowerCase());
		}
		if (result == null) {
			result = dataMap.get(key.toUpperCase());
		}
		return result;
	}

	public static String getString(Map<String, Object> dataMap, String key) {
		String result = null;
		if (dataMap == null || key == null) {
			return null;
		}
		Object value = dataMap.get(key);

		/**
		 * 增加 oracle blob and clob字段支持
		 */
		try {
			Method method = null;
			if ("oracle.sql.CLOB".equals(value.getClass().getName())) {
				Class<?> clobName = ClassUtils.classForName("oracle.sql.CLOB");
				method = clobName.getMethod("stringValue", null);
				return (String) method.invoke(value, null);
			} else if ("oracle.sql.BLOB".equals(value.getClass().getName())) {
				Class<?> blobName = ClassUtils.classForName("oracle.sql.BLOB");
				method = blobName.getMethod("stringValue", null);
			}
			if (method != null) {
				return (String) method.invoke(value, null);
			}
		} catch (Exception e) {
		}

		if (value == null) {
			value = dataMap.get(key.toLowerCase());
		}
		if (value == null) {
			value = dataMap.get(key.toUpperCase());
		}
		if (value != null) {
			if (value instanceof String) {
				result = (String) value;
			} else if (value instanceof Date) {
				result = DateUtils.getDate((Date) value);
			} else if (value instanceof byte[]) {
				byte[] x = (byte[]) value;
				try {
					value = com.glaf.core.util.serializer.SerializationUtils.deserialize(x);
					result = value.toString();
				} catch (IOException ex) {
				}
			} else {
				result = value.toString();
			}
		}
		return result;
	}

	public static String getString(Map<String, Object> dataMap, String key, String defaultValue) {
		String result = defaultValue;
		if (dataMap == null || key == null) {
			return result;
		}
		Object value = dataMap.get(key);
		if (value == null) {
			value = dataMap.get(key.toLowerCase());
		}
		if (value == null) {
			value = dataMap.get(key.toUpperCase());
		}
		if (value != null) {
			if (value instanceof String) {
				result = (String) value;
			} else if (value instanceof byte[]) {
				byte[] x = (byte[]) value;
				try {
					value = com.glaf.core.util.serializer.SerializationUtils.deserialize(x);
					result = value.toString();
				} catch (IOException ex) {
				}
			} else {
				result = value.toString();
			}
		}
		return result;
	}

	public static Timestamp getTimestamp(Map<String, Object> dataMap, String key) {
		Timestamp result = null;
		if (dataMap == null || key == null) {
			return null;
		}
		Object value = dataMap.get(key);
		if (value == null) {
			value = dataMap.get(key.toLowerCase());
		}
		if (value == null) {
			value = dataMap.get(key.toUpperCase());
		}
		if (value != null && StringUtils.isNotEmpty(value.toString())) {
			if (value instanceof String) {
				result = DateUtils.toTimestamp(value.toString());
			} else if (value instanceof Date) {
				Date date = (Date) value;
				result = new Timestamp(date.getTime());
			} else if (value instanceof Timestamp) {
				result = (Timestamp) value;
			}else{
				try {
					Class<?> className = Class.forName("oracle.sql.TIMESTAMP");
					if(value.getClass() == className){
						Class<? extends Object> clz = value.getClass();
						Method method = clz.getMethod("timestampValue", null);
						result = (Timestamp) method.invoke(value, null);
					}
				} catch (Exception e) {
				}
			}
		}
		return result;
	}

	public static boolean isNotEmpty(Map<String, Object> paramMap, String name) {
		if (paramMap != null && paramMap.get(name) != null) {
			Object obj = paramMap.get(name);
			if (obj instanceof Collection<?>) {
				Collection<?> rows = (Collection<?>) obj;
				if (rows != null && rows.size() > 0) {
					return true;
				}
			}
		}
		return false;
	}

	public static void main(String[] args) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("value", "234,101.101");
		System.out.println(ParamUtils.getInt(params, "value"));
		System.out.println(ParamUtils.getIntValue(params, "value"));
		System.out.println(ParamUtils.getLong(params, "value"));
		System.out.println(ParamUtils.getLongValue(params, "value"));
		System.out.println(ParamUtils.getDouble(params, "value"));
		 Date date = new Date();
		try {
			Class<?> classs = Class.forName("java.util.Date");
			
			System.out.println(date.getClass() == classs);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
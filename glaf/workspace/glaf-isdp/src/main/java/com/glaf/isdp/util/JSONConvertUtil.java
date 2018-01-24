package com.glaf.isdp.util;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.codec.binary.Base64;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.RequestUtils;

public class JSONConvertUtil {
	private static String parttern = "yyyy-MM-dd";

	public static JSONObject toJSONObject(Object obj) throws Exception {
		JSONObject jobject = new JSONObject();
		Class<?> clazz = obj.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			Object value = field.get(obj);
			// 如果是字节流，则不输出
			if (field.getType().getSimpleName().equals("byte[]")) {
				continue;
			}
			// 如果是日期类型，则格式化日期类型
			if (field.getType().getSimpleName().equals("Date")) {
				value = DateUtils.getDateTime(parttern, (java.util.Date) field.get(obj));
			}
			jobject.put(field.getName(), value);
			if (field.get(obj) != null && field.get(obj) instanceof String) {
				jobject.put(field.getName() + "_enc", RequestUtils.encodeString(field.get(obj).toString()));
				jobject.put(field.getName() + "_base64",
						Base64.encodeBase64String(field.get(obj).toString().getBytes("UTF-8")));
			}
		}

		return jobject;
	}

	public static JSONArray toJSONArray(List<?> list) throws Exception {
		JSONArray jArray = new JSONArray();

		for (Object obj : list) {
			JSONObject jobject = toJSONObject(obj);
			jArray.add(jobject);
		}

		return jArray;
	}

	public static JSONArray mapListToJSONArray(List<Map<String, Object>> list) throws Exception {
		JSONArray jArray = new JSONArray();

		if (list != null && list.size() > 0) {
			for (Map<String, Object> map : list) {
				JSONObject jobject = mapToJSONObject(map);
				jArray.add(jobject);
			}
		}

		return jArray;
	}

	public static JSONObject mapToJSONObject(Map<String, Object> map) throws Exception {
		JSONObject jobject = new JSONObject();

		Set<Entry<String, Object>> entrySet = map.entrySet();
		Iterator<Entry<String, Object>> it = entrySet.iterator();
		while (it.hasNext()) {
			Entry<String, Object> entry = it.next();
			if (entry.getValue() instanceof java.util.Date) {
				jobject.put((String) entry.getKey(),
						DateUtils.getDateTime(parttern, (java.util.Date) entry.getValue()));
			} else {
				jobject.put((String) entry.getKey(), entry.getValue() == null ? "" : entry.getValue());
			}

			if (entry.getValue() != null && entry.getValue() instanceof String) {
				jobject.put(entry.getKey() + "_enc", RequestUtils.encodeString(entry.getValue().toString()));
				jobject.put(entry.getKey() + "_base64",
						Base64.encodeBase64String(entry.getValue().toString().getBytes("UTF-8")));
			}
		}

		return jobject;
	}

}

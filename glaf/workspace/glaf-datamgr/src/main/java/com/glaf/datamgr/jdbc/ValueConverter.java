package com.glaf.datamgr.jdbc;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.TypeUtils;

public class ValueConverter {

	static Map<String, Converter> converters = new HashMap<>();

	static boolean inited = false;

	static {

		converters.put("oracle.sql.CLOB", new OracleClob());

		converters.put("oracle.sql.NCLOB", new OracleNClob());

		try {
			Iterator<Entry<String, Converter>> it = converters.entrySet().iterator();
			while (it.hasNext()) {
				Entry<String, Converter> entry = it.next();
				ConvertUtils.register(entry.getValue(), Class.forName(entry.getKey()));
			}
			inited = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {

		}

	}

	public static Object convert(Object value) {
		return convert(value, String.class);
	}

	public static Object convert(Object value, Class<?> clazz) {

		if (value == null || !inited) {
			return value;
		}

		if (!converters.containsKey(value.getClass().getName())) {
			return value;
		}

		return ConvertUtils.convert(value, clazz);
	}

	private static class OracleClob implements Converter {

		public <T> T convert(Class<T> clazz, Object value) {

			if (value == null) {

				return null;
			}

			Class<?> cls = value.getClass();
			try {
				Method method = cls.getDeclaredMethod("stringValue", //
						new Class<?>[] {});
				value = method.invoke(value, new Object[] {});
				return TypeUtils.cast(value, clazz, ParserConfig.getGlobalInstance());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

	}

	private static class OracleNClob extends OracleClob implements Converter {
	}

}

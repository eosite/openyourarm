package com.glaf.datamgr.jdbc;

import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.expression.core.util.ExpressionConvertUtil;

/**
 * 条件拼接 数据集、更新集、连接条件 统一由whereClaus.js 组成的树结构规则
 */
public class BuilderUtils {

	private static final String collectionKey = "collection"//
			, childrenKey = "children", outerTreeKey = "outerTree";

	public static boolean buildCondition(String link, StringBuffer SQL, JSONObject whereClaus, ConditionBuild cb,
			Map<String, Object> parameter) {
		boolean rst = false;
		if (whereClaus != null && whereClaus.containsKey(collectionKey)) {
			JSONArray collection = whereClaus.getJSONArray(collectionKey);
			if (rst = CollectionUtils.isNotEmpty(collection)) {
				int i, size;
				JSONObject map = new JSONObject(), json;
				SQL.append(link).append(" ");
				size = collection.size();
				for (i = 0; i < size; i++) {
					json = collection.getJSONObject(i);
					map.put(json.getString("ordinal"), json);
				}
				JSONArray outerTree = whereClaus.getJSONArray(outerTreeKey);
				if (CollectionUtils.isNotEmpty(outerTree)) {
					iterator(SQL, outerTree, map, "", cb, parameter);
				}
			}
		}
		return rst;
	}

	/**
	 * 递归
	 * 
	 * @param SQL
	 * @param jar
	 * @param json
	 * @param parameter
	 */
	protected static void iterator(StringBuffer SQL, JSONArray jar, JSONObject json, String connector,
			ConditionBuild cb, Map<String, Object> parameter) {
		int i, size;
		if (CollectionUtils.isNotEmpty(jar)) {
			size = jar.size();
			JSONObject j, j2;
			String expression, operator, expressionr;
			for (i = 0; i < size; i++) {
				j = jar.getJSONObject(i);
				if (i > 0) {
					SQL.append(" ").append(connector).append(" ");
				}
				if (json.containsKey(j.getString("id"))) {
					j2 = json.getJSONObject(j.getString("id"));
					if (j2 != null) {

						expression = ExpressionConvertUtil.expressionConvert(j2.getString("expressionl"),
								ExpressionConvertUtil.DATABASE_TYPE, new JSONObject());
						operator = j2.getString("operator");
						// logger.debug(operator);
						if (StringUtils.equalsIgnoreCase(operator, "rLike") //
								|| StringUtils.equalsIgnoreCase(operator, "lLike")) {
							operator = "like";
						}
						expressionr = ExpressionConvertUtil.expressionConvert(j2.getString("expression"),
								ExpressionConvertUtil.DATABASE_TYPE, parameter);
						if (cb == null || cb.exec(expression, operator, expressionr) == null) {
							SQL.append(expression).append(" ").append(operator).append(" ");

							SQL.append("${").append(expressionr).append("} ");
						}

					}
				}

				if (j.containsKey(childrenKey)) {
					Boolean root = StringUtils.isBlank(connector);
					iterator(root ? SQL : SQL.append("("), j.getJSONArray(childrenKey), json, j.getString("connector"),
							cb, parameter);
					if (!root)
						SQL.append(")");
				}

			}
		}
	}

	interface ConditionBuild {
		Object exec(String expressionl, String operator, String expressionr);
	}
}

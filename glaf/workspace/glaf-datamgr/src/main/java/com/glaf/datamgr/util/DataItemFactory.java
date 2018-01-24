package com.glaf.datamgr.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.jdbc.QueryHelper;
import com.glaf.core.util.ParamUtils;
import com.glaf.datamgr.domain.DataItemDefinition;
import com.glaf.datamgr.service.DataItemDefinitionService;

public class DataItemFactory {

	protected static DataItemDefinitionService dataItemDefinitionService;

	public static DataItemDefinitionService getDataItemDefinitionService() {
		if (dataItemDefinitionService == null) {
			dataItemDefinitionService = ContextFactory.getBean("dataItemDefinitionService");
		}
		return dataItemDefinitionService;
	}

	public static JSONArray getJSONArray(String code) {
		JSONArray array = new JSONArray();
		DataItemDefinition bean = getDataItemDefinitionService().getDataItemDefinitionByCode(code);
		if (bean != null) {
			Map<String, Object> paramMap = new HashMap<String, Object>();
			QueryHelper helper = new QueryHelper();
			List<Map<String, Object>> dataList = helper.getResultList(bean.getSql(), paramMap);
			if (dataList != null && !dataList.isEmpty()) {
				for (Map<String, Object> dataMap : dataList) {
					JSONObject json = new JSONObject();
					json.put("key", ParamUtils.getString(dataMap, bean.getKeyColumn()));
					json.put("value", ParamUtils.getString(dataMap, bean.getValueColumn()));
					array.add(json);
				}
			}
		}
		return array;
	}

	private DataItemFactory() {

	}
}

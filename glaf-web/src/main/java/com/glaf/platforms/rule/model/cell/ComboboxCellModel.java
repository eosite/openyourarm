package com.glaf.platforms.rule.model.cell;

import org.apache.commons.collections.CollectionUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class ComboboxCellModel extends CommonCellModel implements ICellModel {

	private static final String dictCode = "A001-2-006";
	private static final String customDatasCode = "A001-7-001";

	@Override
	public JSONObject convert(JSONObject rule) {
		JSONObject ret = super.convert(rule);

		if (rule.containsKey(dictCode) && !rule.containsKey(customDatasCode)) { // 获取字典代码(自定义数据源优先级高)
			JSONArray jars = JSONArray.parseArray(rule.getString(dictCode));
			if (CollectionUtils.isNotEmpty(jars)) {
				JSONObject datas = jars.getJSONObject(0).getJSONObject("datas");
				if (datas != null) {
					String code;
					for (String key : datas.keySet()) {
						code = datas.getJSONArray(key).getJSONObject(0)
								.getString("value");
						ret.put("code", code);
					}
				}
			}
		}
		
		if (rule.containsKey(customDatasCode)) { // 获取自定义数据源
			JSONArray jars = JSONArray.parseArray(rule.getString(customDatasCode));
			if (CollectionUtils.isNotEmpty(jars)) {
				JSONObject datas = jars.getJSONObject(0).getJSONObject("datas");
				if (datas != null) {
					JSONArray retDatas = new JSONArray();
					JSONObject retData ;
					JSONArray customDatas ;
					JSONObject customData ;
					for (String key : datas.keySet()) {
						customDatas = datas.getJSONArray(key).getJSONObject(0).getJSONArray("val");
						for (Object object : customDatas) {
							customData = (JSONObject) object ;
							retData = new JSONObject();
							retData.put("value", customData.getString("param"));
							retData.put("text", customData.getString("name"));
							retDatas.add(retData);
						}
						ret.put("customDatas", retDatas);
					}
				}
			}
		}

		return ret;
	}

}

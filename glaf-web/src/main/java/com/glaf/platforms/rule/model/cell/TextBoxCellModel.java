package com.glaf.platforms.rule.model.cell;

import org.apache.commons.collections.CollectionUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class TextBoxCellModel extends CommonCellModel implements ICellModel {
	
	private static final String dictCode = "A001-2-006";
	
	@Override
	public JSONObject convert(JSONObject rule) {
		JSONObject ret = super.convert(rule);

		if (rule.containsKey(dictCode)) { // 获取字典代码
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

		return ret;
	}

}

package com.glaf.platforms.rule.model.cell;

import com.alibaba.fastjson.JSONObject;

public class FileUploadCellModel extends CommonCellModel implements ICellModel {
	private static final String MAX_FILE_SIZE_CODE = "A001-3-009"; //附件上传限制大小

	@Override
	public JSONObject convert(JSONObject rule) {
		JSONObject ret = super.convert(rule);

		if (rule.containsKey(MAX_FILE_SIZE_CODE)) { // 获取自定义数据源
			Double maxFileSize = rule.getDouble(MAX_FILE_SIZE_CODE);
			maxFileSize = maxFileSize == null ? 5 : maxFileSize ;
			ret.put("maxFileSize", maxFileSize*1024*1024);
		}

		return ret;
	}
}

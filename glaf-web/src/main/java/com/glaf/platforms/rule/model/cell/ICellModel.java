package com.glaf.platforms.rule.model.cell;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.glaf.dep.base.domain.DepBaseProp;

public interface ICellModel {

	final static Map<String, Class<? extends ICellModel>> MODELS = new HashMap<String, Class<? extends ICellModel>>() {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		{
			put("textbox", TextBoxCellModel.class);
			put("combobox", ComboboxCellModel.class);
			put("page", PageCellModel.class);
			put("button", ButtonCellModel.class);
			put("radio", RadioCellModel.class);
			put("checkbox", CheckboxCellModel.class);
			put("image", ImageCellModel.class);
			put("datebox", DateboxCellModel.class);
			put("select2", Select2CellModel.class);
			put("fileUpload",FileUploadCellModel.class);
		}
	};

	JSONObject convert(JSONObject rule);

	void setDepBaseProps(List<DepBaseProp> list);
	
	void setQuoteProps(List<DepBaseProp> quoteProps);

}

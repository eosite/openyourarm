package com.glaf.platforms.rule.model.cell;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.dep.base.domain.DepBaseProp;
import com.glaf.form.rule.Global;

public class CommonCellModel {

	private List<DepBaseProp> depBaseProps;
	private List<DepBaseProp> quoteProps;

	public JSONObject convert(JSONObject rule) {
		JSONObject ret = new JSONObject();
		ret.put("name", rule.get(Global.NAME));
		ret.put("type", rule.get(Global.TYPE));

		this.initCommonProperties(rule, ret);
		
		this.initQuoteProps(rule,ret);
		
		return ret;
	}
	
	/**
	 * 初始化引用
	 * @param rule
	 * @param ret
	 */
	private void initQuoteProps(JSONObject rule, JSONObject ret) {
		if (CollectionUtils.isNotEmpty(quoteProps)) {
			JSONArray ruleAry = null;
			for (DepBaseProp dbp : quoteProps) {
				String quoteRule = rule.getString(dbp.getRuleCode());
				if(StringUtils.isNotEmpty(quoteRule) && !"A001-2-006".equals(dbp.getRuleCode())){ //不等于字典CODE
					ruleAry =  JSON.parseArray(quoteRule);
					JSONObject ruleObj = ruleAry.getJSONObject(0);
					JSONObject datasObj = ruleObj.getJSONObject("datas");
					String dynamicId = ruleObj.getString("dynamicId");
					JSONObject obj = new JSONObject();
					obj.put("dynamicId", dynamicId);
					JSONArray quoteAry = new JSONArray();
					for (String dataKey : datasObj.keySet()) {
						JSONArray dataAry = datasObj.getJSONArray(dataKey);
						String value = null ;
						if(CollectionUtils.isNotEmpty(dataAry)){
							JSONObject dataObj = dataAry.getJSONObject(0);
							JSONObject quoteObj = new JSONObject();
							value = dataObj.getString("value");
							if(value != null){
								quoteObj.put("value", value);
							}else{
								quoteObj.put("inid", dataObj.get("inid"));
							}
							quoteObj.put("outid", dataObj.get("outid"));
							quoteAry.add(quoteObj);
						}
					}
					obj.put("params", quoteAry);
					ret.put("quote",obj);
					break;
				}
			}
		}
	}

	private void initCommonProperties(JSONObject rule, JSONObject ret) {
		/**
		 * 获取控件属性(验证、类型<长度...>)
		 */
		List<Object> keysList = new ArrayList<Object>();

		if (CollectionUtils.isNotEmpty(depBaseProps)) {
			for (DepBaseProp dbp : depBaseProps) {
				keysList.add(dbp.getRuleCode());
			}
			this.a2b(rule, ret, keysList.toArray());
		}
	}

	protected void a2b(JSONObject a, JSONObject b, Object... keys) {
		if (a != null && b != null) {
			if (keys == null || keys.length == 0) {
				keys = a.keySet().toArray();
			}
			for (Object key : keys) {
				b.put(key.toString(), a.get(key) != null ? a.get(key)
						.toString() : null);
			}
		}
	}

	public void setDepBaseProps(List<DepBaseProp> depBaseProps) {
		this.depBaseProps = depBaseProps;
	}

	public void setQuoteProps(List<DepBaseProp> quoteProps) {
		this.quoteProps = quoteProps;
	}
	
	
}

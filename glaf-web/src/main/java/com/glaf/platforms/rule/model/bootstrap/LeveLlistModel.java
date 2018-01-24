package com.glaf.platforms.rule.model.bootstrap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.form.core.domain.FormComponent;
import com.glaf.form.core.domain.FormRule;
import com.glaf.platforms.rule.HTMLExpressionConvertUtil;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;
import com.glaf.platforms.rule.model.CommonModel;

public class LeveLlistModel extends CommonModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String templateScript = "";

	// 增加模板
	public String getTemplateScript() {
		String script = "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/plugins/bootstrap/levellist/ext/levellist.extend.css' />"
				+ "<script type='text/javascript' src='${contextPath}/scripts/plugins/bootstrap/levellist/ext/jquery.levellist.extend.js'></script>";

		scriptMap.put("levellist", script);
		
		scriptMap.put("mui", "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/mui/css/mui.min.css' />"
				+ "<script  type='text/javascript' src='${contextPath}/scripts/mui/js/mui.min.js' />");
		return templateScript;
	}

	public void addTemplate(String subTemplate) {
		this.templateScript += subTemplate;
	}

	public String getBind() {

		return null;
	}

	@Override
	public String getWidth() {
		return source.get("width");
	}

	@Override
	public String getHeight() {

		return source.get("height");
	}

	@Override
	public void setSource(Map<String, String> source) {
		this.source = source;
	}

	@Override
	public String getElementTagName() {
		// return "ul";
		return null;
	}

	public String getElementHtml() {
		String formHtml = formPage.getFormHtml();
		Document doc = Jsoup.parse(formHtml); 
		Element ele = doc.getElementById(this.getId());
		Elements styles = ele.getElementsByTag("style").clone();
		
		ele.empty().append(styles.outerHtml());
		
		return ele.outerHtml();
	}

	@Override
	public String getId() {
		return source.get("id");
	}

	@Override
	public String getName() {
		return source.get("name");
	}

	@Override
	public String getValue() {

		return null;
	}

	@Override
	public boolean isReadable() {
		return false;
	}

	@Override
	public boolean isRequired() {
		return false;
	}

	@Override
	public boolean isWritable() {
		return false;
	}

	@Override
	public String getRuleId() {
		return source.get("ruleId");
	}


	/**
	 * 获取值
	 * 
	 * @param columnType
	 * @return
	 */
	private String columnsValue(String columnType) {
		String columns = source.get("columns");
		JSONArray columnsArray = JSON.parseArray(columns);
		JSONObject jo = null;
		String str = null;
		if(null!=columnsArray){
			for (Object object : columnsArray) {
				jo = (JSONObject) object;
				if (columnType.equalsIgnoreCase(jo.getString("columnType"))) {
					str = jo.getString("ColumnName");
				}
		  }
		}
		return str;
	}
	/**
	 * 获取父节点信息
	 * @return
	 */
	public String getParentKey(){
		return columnsValue("pIdKey");
	}

	/**
	 * 简单模式 显示名称
	 * 
	 * @return
	 */
	public String getNameKey() {
		return columnsValue("nameKey");
	}

	/**
	 * 简单模式 idkey (treeid)
	 * 
	 * @return
	 */
	public String getIdKey() {
		// return "treeid";
		return columnsValue("idKey");
	}

	/**
	 * 简单模式 indexKey
	 * 
	 * @return
	 */
	public String getIndexKey() {
		// return "index_id";
		return columnsValue("indexKey");
	}

	// 表名 + _0_
	public String getIdTable() {
		return columnsValue("indexKey").split("_0_")[0] + "_0_id";
	}


	/**
	 * 是否异步
	 * 
	 * @return
	 */
	public String getIsSync() {
		String isSync = source.get("isSync");
		return isSync == null ? "false" : isSync;
	}
	
	public String getStyleRule() {
		String dataLinkPage = source.get("dataLinkPage");
		StringBuffer styleSb = new StringBuffer();
		JSONObject retObj = new JSONObject();
		if (!isNullOrEmpty(dataLinkPage)) {
			JSONArray dataLinkPageAry = JSONArray.parseArray(dataLinkPage);
			if (!dataLinkPageAry.isEmpty()) {
				JSONObject dataLinkPageObj = dataLinkPageAry.getJSONObject(0);
				JSONObject valuesObj = dataLinkPageObj.getJSONObject("values");
				
				//开关选中
				JSONArray checkAry = valuesObj.getJSONArray("check");
				if (!checkAry.isEmpty()) {
					JSONObject checkObj;
					JSONObject check ;
					List<JSONObject> list = new ArrayList<>();
					String type;
					for (Object object : checkAry) {
						checkObj = (JSONObject) object;
						check = new JSONObject();
						this.convertObj(checkObj, check,"type");
						String expdata = checkObj.getString("expdata");
						if(!isNullOrEmpty(expdata)){
							JSONObject expdataObj = JSON.parseObject(expdata);
							String es = HTMLExpressionConvertUtil.htmlConvertForZtree(expdataObj.getString("expActVal"));
							check.put("expression", es);
						}
						list.add(check);
					}
					JSONArray jo = (JSONArray) JSON.toJSON(list);
					retObj.put("check", jo);
				}
			}
		}
		return retObj.toJSONString();
	}
	
	/**
	 * 拷贝属性
	 * @param fromObj
	 * @param toObj
	 * @param arys
	 */
	private void convertObj(JSONObject fromObj,JSONObject toObj,String... arys){
		if(arys.length>0){
			for (String key : arys) {
				toObj.put(key, fromObj.get(key));
			}
		}
	}
	
}

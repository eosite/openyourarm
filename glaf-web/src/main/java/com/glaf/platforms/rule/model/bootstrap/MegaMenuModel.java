package com.glaf.platforms.rule.model.bootstrap;

import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;
import com.glaf.platforms.rule.model.CommonModel;

public class MegaMenuModel extends CommonModel implements IRule, CssRule, AttrRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getBind() {
		return null;
	}

	public String getElementTagName() {
		return null;
	}

	/**
	 * 工具条样式
	 * @return
	 */
	public String getHandleType() {
		return super.getSource("handleType", "hover");
	}
	
	/**
	 * 显示 隐藏
	 * @return
	 */
	@Override
	public boolean isEnabled() {
		return Boolean.parseBoolean(this.source.get("enabled"));
	}
	
	@Override
	public String getElementHtml() {
//		String rid = super.getRuleId();
		
		String formHtml = formPage.getFormHtml();

		Document doc = Jsoup.parse(formHtml);

		Element ele = doc.getElementById(this.getId());
		
//		ele.attr("rid",rid);	
//		ele.removeAttr("contenteditable");
		if ("false".equals(this.isVisible())) {
			ele.attr("style", "display:none;"+ele.attr("style"));
		}
		ele.getElementsByClass("hor-menu").empty();
		String html = ele.outerHtml();

		return html;
	}
	
	//获取数据集字段名称
	public String getColumns(){
		if("true".equalsIgnoreCase(source.get("sysmenu"))){
			/*{
				"sortNo": 0,
				"code": "app_15",
				"level": 0,
				"name": "模块管理",
				"icon": "/images/application_view_list.png",
				"_id_": 15,
				"description": "模块管理",
				"_oid_": 15,
				"id": 15,
				"locked": 0,
				"parentId": 10,
				"url": "/mx/sys/application/showFrame"
			}*/
			//{"idKey":"t0_0_c0","pIdKey":"t0_0_c5","nameKey":"t0_0_c3","indexKey":"t0_0_c4","urlKey":"t0_0_c3"}
			//idKey,indexKey,pIdKey,nameKey,urlKey,icon
			JSONObject sysmeau = new JSONObject();
			sysmeau.put("idKey", "");
			sysmeau.put("indexKey", "id");
			sysmeau.put("pIdKey", "parentId");
			sysmeau.put("nameKey", "name");
			sysmeau.put("urlKey", "url");
			//sysmeau.put("icon", "icon");
			return sysmeau.toJSONString();
		}
		JSONObject coordInfo = new JSONObject() ;
		String dataset = source.get("columns");
		if(notEmpty(dataset)){
			JSONArray columns = JSON.parseArray(dataset);
			if(columns!=null && !columns.isEmpty()){
				JSONObject colunm = null ;
				String ctype = null ;
				for (Object object : columns) {
					colunm = (JSONObject) object ;
					ctype = colunm.getString("columnType") ;
					if(notEmpty(ctype)){
						coordInfo.put(ctype, colunm.getString("ColumnName"));
					}
				}
			}
		}
		return coordInfo.toJSONString();
	}
	
	//获取固定数据集
	public String getFixDataSource(){
		if("true".equalsIgnoreCase(source.get("sysmenu"))){
			return null;
		}
		String dataset = source.get("fixDataSource");
		return dataset;
	}
	
	private boolean notEmpty(String str){
		if(str!=null && !"".equals(str.trim())){
			return true ;
		}
		return false ;
	}
	
//	 @Override
//    public void setScriptMap(Map<String, String> scriptMap) {
//    	this.scriptMap = scriptMap;
//		StringBuffer SB = new StringBuffer();
//		SB.append("<link href=\"${contextPath}/scripts/plugins/bootstrap/menu/ext/megamenu.extends.css\" rel=\"stylesheet\" type=\"text/css\" />");	
//		SB.append("<script type=\"text/javascript\" src=\"${contextPath}/scripts/plugins/bootstrap/menu/ext/jquery.megamenu.extends.js\"></script>");
//		
//		this.scriptMap.put("megamenu", SB.toString());
//    }

}

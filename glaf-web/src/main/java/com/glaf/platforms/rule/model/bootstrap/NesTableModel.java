package com.glaf.platforms.rule.model.bootstrap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.expression.core.util.ExpressionConvertUtil;
import com.glaf.form.core.domain.FormComponent;
import com.glaf.form.core.domain.FormRule;
import com.glaf.platforms.rule.HTMLExpressionConvertUtil;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;
import com.glaf.platforms.rule.model.CommonModel;

public class NesTableModel extends CommonModel implements IRule, CssRule, AttrRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String templateScript = "";

	// 增加模板
	public String getTemplateScript() {
		
		String script = /*"<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/bootstrap/css/font-awesome.min.css'>"
				+ */"<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/plugins/bootstrap/nestable/css/ace.min.css'>"
				+ "<link rel='stylesheet' type='text/css' href='${contextPath}/static/scripts/layer/skin/layer.css' />"
				+ "<script type='text/javascript' src='${contextPath}/scripts/plugins/bootstrap/nestable/js/jquery.nestable.min.js'></script>";

		scriptMap.put("nestable", script);
		return templateScript;
	}

	public void addTemplate(String subTemplate) {
		this.templateScript += subTemplate;
	}

	@Override
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
		StringBuilder sb = new StringBuilder();
		String display = "display:none;";
		if ("true".equals(this.isVisible())) {
			display = "";
		}
		sb.append("<div class='portlet-body' rid='"+this.getRuleId()+"' normal='"+this.getNormal()+"' id='" + this.getId() + "' style='width:" + this.getWidth() + ";height:" + this.getHeight() + ";overflow-y:auto;overflow-x:auto; " + display + "' >");
		// "toolbar":"[{name:'create',text:'新增'},{name:'save',text:'保存'},{name:'cancel',text:'取消'}]",//工具栏
		String toolbar = source.get("toolbar");
		if (StringUtils.isNotEmpty(toolbar)) {
			JSONArray toolbarArray = JSON.parseArray(toolbar);
			for (Object object : toolbarArray) {
				JSONObject jo = (JSONObject) object;
				sb.append("<button id='ztree_" + this.getId() + "_" + jo.getString("name") + "' type='button'>" + jo.getString("text") + "</button>");
			}
		}

		return sb.toString();
	}
	/**
	 * 普通表
	 * 
	 * @return
	 */
	public String getNormal() {
		if ("true".equalsIgnoreCase(source.get("normal"))) {
			return "true";
		}
		return "false";
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
	 * 显示线条
	 * 
	 * @return
	 */
	public String getShowLine() {
		return source.get("showLine");
	}

	/**
	 * 是否显示图标
	 * 
	 * @return
	 */
	public String getShowIcon() {
		return source.get("showIcon");
	}
	/**
	 * 层级
	 * 
	 * @return
	 */
	public String getGradeNest() {
		return source.get("gradeNest");
	}
	/**
	 * 图标样式
	 * 
	 * @return
	 */
	public String getIcon() {
		return columnsValue("icon");
	}

	/**
	 * 展开图标样式
	 * 
	 * @return
	 */
	public String getIconOpen() {
		return columnsValue("iconOpen");
	}

	/**
	 * 关闭图标
	 * 
	 * @return
	 */
	public String getIconClose() {
		return columnsValue("iconClose");
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
		for (Object object : columnsArray) {
			jo = (JSONObject) object;
			if (columnType.equalsIgnoreCase(jo.getString("columnType"))) {
				str = jo.getString("ColumnName");
			}
		}
		return str;
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

	/**
	 * 简单模式 父id
	 * 
	 * @return
	 */
	public String getPIdKey() {
		// return "parent_id";
		return columnsValue("pIdKey");
	}

	// 表名 + _0_
	public String getIdTable() {
		return columnsValue("indexKey").split("_0_")[0] + "_0_id";
	}

	/**
	 * 启用简单模式
	 * 
	 * @return
	 */
	public String getSimpleDataEnable() {
		// return source.get("simpleDataEnable");
		return "true";
	}

	/**
	 * 动画效果
	 * 
	 * @return
	 */
	public String getExpandSpeed() {
		return source.get("expandSpeed");
	}

	/**
	 * 是否显示选择框
	 * 
	 * @return
	 */
	public String getCheckEnable() {
		String checkEnable = source.get("checkEnable");
		if (StringUtils.isNotEmpty(checkEnable)) {
			return source.get("checkEnable");
		} else {
			return "false";
		}
	}

	/**
	 * 选择框样式
	 * 
	 * @return
	 */
	public String getChkStyle() {
		String checkEnable = source.get("chkStyle");
		if (StringUtils.isNotEmpty(checkEnable)) {
			return source.get("chkStyle");
		} else {
			return "checkbox";
		}
	}

	/**
	 * 双击展开节点
	 * 
	 * @return
	 */
	public String getDblClickExpand() {
		return source.get("dblClickExpand");
	}

	/**
	 * 是否启用树编辑功能
	 * 
	 * @return
	 */
	public String getEditEnable() {
		return source.get("editEnable");
	}

	/**
	 * 显示删除按钮
	 * 
	 * @return
	 */
	public String getShowRemoveBtn() {
		if ("true".equalsIgnoreCase(source.get("showRemoveBtn"))) {
			return "";
		}
		return "false";
	}

	/**
	 * 显示编辑名称按钮
	 * 
	 * @return
	 */
	public String getShowRenameBtn() {
		if ("true".equalsIgnoreCase(source.get("showRenameBtn"))) {
			return "";
		}
		return "false";
	}

	/**
	 * 是否启用拖拽
	 * 
	 * @return
	 */
	public String getEditDrag() {
		if ("true".equalsIgnoreCase(source.get("editDrag"))) {
			return "true";
		}
		return "false";
	}

	/**
	 * 单击事件
	 * 
	 * @return
	 */
	public String getClick() {
		// return source.get("click") ;
		// 方法类型
		String type = StringUtils.isNotEmpty(source.get("click")) ? source.get("click").toString() : "";
		// 获取规则 -->输入表达式定义
		String paraType = source.get("paraType");
		if (StringUtils.isNotEmpty(paraType)) {
			// 联动页面需要的参数
			String linkPage = source.get("linkPage"); // 联动页面
			JSONObject params = new JSONObject();
			if (StringUtils.isNotEmpty(linkPage)) {
				JSONArray linkPages = JSON.parseArray(linkPage);
				if (linkPages != null && !linkPages.isEmpty()) {
					String linkstr = linkPages.getJSONObject(0).toJSONString();
					params.put("link", linkstr);
				}
				String jumpType = source.get("jumpType"); // 跳转类型
				params.put("jumpType", jumpType);
				String title = source.get("windowTitle"); // 窗口名称
				params.put("title", title);
				String model = source.get("windowModal"); // 是否为模态窗口
				params.put("model", model);
				String width = source.get("windowWidth"); // 窗口宽度
				params.put("width", width);
				String height = source.get("windowHeight");// 窗口高度
				params.put("height", height);
				String maximize = source.get("maximize");
				params.put("maximize", maximize);
			}

			JSONArray paraTypeArray = JSON.parseArray(paraType);
			JSONObject paraTypeObject = paraTypeArray.getJSONObject(0);
			return "pubsub.pub('" + type + "'," + paraTypeObject.getString("datas") + "," + params.toJSONString() + ",treeNode);";
		} else {
			return "";
		}
	}

	/**
	 * 联动控件（输出控件）
	 * 
	 * @return
	 */
	public String getLinkageControl() {
		// return (source.get("linkageControl")!=null &&
		// StringUtils.isNotEmpty(source.get("linkageControl").toString())) ?
		// source.get("linkageControl") :"''";
		return null;
	}
	/**
	 * 输入形参
	 * 
	 * @return
	 */
	public String getInParamDefined() {
		// return (source.get("linkageControl")!=null &&
		// StringUtils.isNotEmpty(source.get("linkageControl").toString())) ?
		// source.get("linkageControl") :"''";
		String s = source.get("inParamDefined");
		return source.get("inParamDefined");
	}
	/**
	 * 编辑窗口自动关闭
	 * 
	 * @return
	 */
	public String getEditPanleAutoClose() {
		if (StringUtils.isNotEmpty(source.get("editPanleAutoClose")) && "true".equalsIgnoreCase(source.get("editPanleAutoClose"))) {
			return "true";
		}
		return "false";
	}

	@Override
	public void setFormRules(List<FormRule> formRules) {
	}

	@Override
	public void setFormComponents(List<FormComponent> formComponents) {

	}
	/**
	 * 数据关联
	 * 
	 * @return
	 */
    public String getDataAbort(){
    	return source.get("dataAbort");
    }
	public String getExpandChilds() {
		return source.getOrDefault("expandChilds", "0");
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

	public String getStyleRule() {
		String dataLinkPage = source.get("dataLinkPage");
		StringBuffer styleSb = new StringBuffer();
		JSONObject retObj = new JSONObject();
		if (!isNullOrEmpty(dataLinkPage)) {
			JSONArray dataLinkPageAry = JSONArray.parseArray(dataLinkPage);
			if (!dataLinkPageAry.isEmpty()) {
				JSONObject dataLinkPageObj = dataLinkPageAry.getJSONObject(0);
				JSONObject valuesObj = dataLinkPageObj.getJSONObject("values");
				// 图标数组
				JSONArray iconAry = valuesObj.getJSONArray("icon");
				if (!iconAry.isEmpty()) {
					JSONObject iconObj;
					JSONObject icon ;
					List<JSONObject> list = new ArrayList<>();
					String model,iconstr;
					for (Object object : iconAry) {
						iconObj = (JSONObject) object;
						icon = new JSONObject();
						model = iconObj.getString("model");
						if("level".equalsIgnoreCase(model)){
							iconstr = iconObj.getString("type");
							// ico_docu  ico_open ico_close
							switch (iconstr) {
							case "iconOpen":
								iconstr = "button.ico_open" ;
								break;
							case "iconClose":
								iconstr = "button.ico_close" ;
								break;
							case "icon":
								iconstr = "button" ;
								break;
							default:
								iconstr = "button.ico_docu" ;
								break;
							}
							styleSb.append("#"+this.getId()+" a.level"+(iconObj.getIntValue("expression")-1)+" span."+iconstr);
							styleSb.append("{");
							styleSb.append("margin-right:2px; background: url(${contextPath}"+iconObj.getString("icon")+") no-repeat scroll 0 0 transparent; vertical-align:top; *vertical-align:middle");
							styleSb.append("}");
						}else if("custom".equalsIgnoreCase(model)){
							this.convertObj(iconObj, icon,"type","icon");
							String expdata = iconObj.getString("expdata");
							if(!isNullOrEmpty(expdata)){
								JSONObject expdataObj = JSON.parseObject(expdata);
								String es = HTMLExpressionConvertUtil.htmlConvertForZtree(expdataObj.getString("expActVal"));
								icon.put("expression", es);
							}
							list.add(icon);
						}
					}
					JSONArray jo = (JSONArray) JSON.toJSON(list);
					retObj.put("icon", jo);
				}
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
				
				JSONArray textAry = valuesObj.getJSONArray("text");
				if (!textAry.isEmpty()) {
					JSONObject textObj;
					JSONObject text ;
					List<JSONObject> list = new ArrayList<>();
					String model,expdata,htmldata;
					for (Object object : textAry) {
						textObj = (JSONObject) object;
						text = new JSONObject();
						model = textObj.getString("model");
						if("custom".equalsIgnoreCase(model)){
							expdata = textObj.getString("expdata");
							if(!isNullOrEmpty(expdata)){
								JSONObject expdataObj = JSON.parseObject(expdata);
								String es = HTMLExpressionConvertUtil.htmlConvertForZtree(expdataObj.getString("expActVal"));
								text.put("expression", es);
							}
							htmldata = textObj.getString("htmldata");
							if(!isNullOrEmpty(htmldata)){
								JSONObject htmldataObj = JSON.parseObject(htmldata);
								String es = HTMLExpressionConvertUtil.htmlConvertForZtree(htmldataObj.getString("htmlVal"),"##+","+##");
								text.put("htmlVal", es.replaceAll("<p>", "").replaceAll("</p>", "").replaceAll("</br>", ""));
							}
							list.add(text);
						}
					}
					JSONArray jo = (JSONArray) JSON.toJSON(list);
					retObj.put("text", jo);
				}
			}
		}
		if(styleSb.length()>0){
			this.addTemplate("<style>"+styleSb.toString()+"</style>");
		}
		return retObj.toJSONString();
	}
	
	public String getFontCss() {
		String dataLinkPage = source.get("dataLinkPage");
		StringBuffer sb = new StringBuffer();
		if (!isNullOrEmpty(dataLinkPage)) {
			JSONArray dataLinkPageAry = JSONArray.parseArray(dataLinkPage);
			if (!dataLinkPageAry.isEmpty()) {
				JSONObject dataLinkPageObj = dataLinkPageAry.getJSONObject(0);
				JSONObject valuesObj = dataLinkPageObj.getJSONObject("values");
				
				JSONArray textAry = valuesObj.getJSONArray("text");
				if (!textAry.isEmpty()) {
					sb.append("function(treeId, treeNode){");
					JSONObject textObj;
					String model;
					boolean isLevel = false;
					for (Object object : textAry) {
						textObj = (JSONObject) object;
						model = textObj.getString("model");
						if("level".equalsIgnoreCase(model)){
							isLevel = true;
							sb.append("if(treeNode.level=="+(textObj.getIntValue("expression")-1)+"){return {color:\""+textObj.getString("color")+"\"};};");
						}
					}
					sb.append("return {};}");
					if(isLevel){
						return sb.toString();
					}
				}
			}
		}
		return null;
	}
	/**
	 * 获取输入形参规则中的对象规则
	 * @return
	 */
	public String getInParamObjRule(){
		String inPamranDefinedStr = this.source.get("inParamDefined");
		JSONObject ret = new JSONObject();
		if(StringUtils.isNotEmpty(inPamranDefinedStr)){
			JSONArray inPamranDefinedArray = JSON.parseArray(inPamranDefinedStr);
			if(inPamranDefinedArray!= null && !inPamranDefinedArray.isEmpty()){
				JSONObject obj = inPamranDefinedArray.getJSONObject(0);
				ret.put("objSource", obj.getJSONArray("objSource"));
				JSONArray arySource = obj.getJSONArray("arySource");
				if(arySource!= null && !arySource.isEmpty()){
					for(Object aryObj : arySource){
						JSONObject aryJson = (JSONObject)aryObj;
						JSONArray aryChildrenArray = aryJson.getJSONArray("child");
						for(Object aryChildObj : aryChildrenArray){
							JSONObject aryChildJson = (JSONObject)aryChildObj;
							aryChildJson.remove("child");
						}
						aryJson.put("child", aryChildrenArray);
					}
					ret.put("arySource", arySource);
				}
				return ret.toJSONString();
			}
		}
		return "[]";
	}
}

package com.glaf.platforms.rule.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.util.http.HttpClientUtils;
import com.glaf.form.core.domain.FormComponent;
import com.glaf.form.core.domain.FormPage;
import com.glaf.form.core.domain.FormRule;
import com.glaf.platforms.rule.HTMLExpressionConvertUtil;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;

public class JSGisModel implements IRule, CssRule, AttrRule {

	private static final long serialVersionUID = 1L;
	protected Map<String, String> source;
	protected FormPage formPage;
	protected Map<String, String> scriptMap;
	protected String templateScript = "";

	public void setFormPage(FormPage formPage) {
		this.formPage = formPage;
	}

	// 点击事件
	public String getClick() {
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
			}

			JSONArray paraTypeArray = JSON.parseArray(paraType);
			JSONObject paraTypeObject = paraTypeArray.getJSONObject(0);
			return "pubsub.pub('" + type + "'," + paraTypeObject.getString("datas") + "," + params.toJSONString() + ",treeNode);";
		} else {
			return "";
		}
	}

	// 判断条件
	public String getDataLinkPage() {
		JSONObject coordInfo = new JSONObject();
		String dataset = source.get("dataSourceSet");
		if (notEmpty(dataset)) {
			JSONArray dataSetAry = JSON.parseArray(dataset);
			if (dataSetAry != null && !dataSetAry.isEmpty()) {
				JSONObject dataSetObj = dataSetAry.getJSONObject(0);
				JSONArray columns = dataSetObj.getJSONArray("selectColumns");
				if (columns != null && !columns.isEmpty()) {
					JSONObject colunm = null;
					String ctype = null;
					for (Object object : columns) {
						colunm = (JSONObject) object;
						ctype = colunm.getString("ctype");
						if (notEmpty(ctype)) {
							coordInfo.put(ctype, colunm.getString("columnName"));
						}
					}
				}
			}
		}

		String dataStyle = source.get("dataLinkPage"); // 样式定义
		JSONArray retAry = null;
		if (notEmpty(dataStyle)) {
			JSONArray dataStyleAry = JSON.parseArray(dataStyle);
			if (dataStyleAry != null && !dataStyleAry.isEmpty()) {
				JSONObject valueObj = null;
				retAry = new JSONArray();
				JSONObject retObj = null;
				JSONArray paramAry = null;
				// 弹窗偏移量
				int offset = 0;
				String offsetstr = source.get("offset");
				if (notEmpty(offsetstr)) {
					try {
						offset = Integer.parseInt(offsetstr);
					} catch (Exception e) {
					}
				}
				for (Object object : dataStyleAry) {
					valueObj = (JSONObject) object;
					retObj = new JSONObject();

					retObj.put("name", valueObj.getString("name"));
					retObj.put("icon", valueObj.getString("icon"));
					retObj.put("iconWidth", valueObj.getString("iconWidth"));
					retObj.put("iconHeight", valueObj.getString("iconHeight"));
					retObj.put("lineWidth", valueObj.getString("lineWidth"));
					retObj.put("lineColor", valueObj.getString("lineColor"));
					retObj.put("highlightWidth", valueObj.getString("highlightWidth"));
					retObj.put("highlightColor", valueObj.getString("highlightColor"));
					retObj.put("flicker", valueObj.getString("flicker"));
					retObj.put("urlId", valueObj.getString("urlId"));
					retObj.put("windowName", valueObj.getString("windowName"));
					retObj.put("windowWidth", valueObj.getString("windowWidth"));
					retObj.put("windowHeight", valueObj.getString("windowHeight"));
					retObj.put("column", valueObj.getString("column"));
					retObj.put("isShow", valueObj.getString("isShow"));
					retObj.put("isSort", valueObj.getString("isSort"));
					retObj.put("showColumn", valueObj.getString("showColumn"));
					retObj.put("sortColor", valueObj.getString("sortColor"));
					retObj.put("sortSize", valueObj.getString("sortSize"));
					retObj.put("sortOffset", valueObj.getString("sortOffset"));
					retObj.put("toffset", valueObj.getString("toffset"));

					retObj.put("offset", offset);
					// 输入输出参数
					String paramStr = valueObj.getString("param");
					if (notEmpty(paramStr)) {
						paramAry = JSON.parseArray(paramStr);
						if (paramAry != null && !paramAry.isEmpty()) {
							retObj.put("rules", paramAry.getJSONObject(0).getJSONObject("datas").toJSONString());
						}
					}

					JSONObject expdata = JSON.parseObject(valueObj.getString("expdata"));
					String expVal = expdata.getString("expActVal");
					retObj.put("exp", HTMLExpressionConvertUtil.imageConvert(expVal));

					retAry.add(retObj);
				}
				return "jsGisFunc.initDataAndClick(map," + retAry.toJSONString() + ",'" + this.getRuleId() + "'," + coordInfo + ",'" + this.getJsgisUrl() + "')";
			}
		}
		return null;
	}

	// 工具条事件
	public String getToolbarClick() {

		String toolbar = source.get("toolbar"); // 工具条为空 以下直接无效
		if (!notEmpty(toolbar)) {
			return null;
		}
		JSONArray toolbarArray = JSON.parseArray(toolbar);
		if (toolbarArray == null || toolbarArray.isEmpty()) {
			return null;
		}

		String toolbarStr = source.get("toolbarClick");
		if (notEmpty(toolbarStr) && "yes".equalsIgnoreCase(toolbarStr)) {
			JSONArray paramsAry = new JSONArray();
			JSONObject paramobj = new JSONObject();
			String rules = "";
			// 关联页面
			String toolbarLinkPageStr = source.get("toolbarLinkPage");
			if (notEmpty(toolbarLinkPageStr)) {
				JSONArray linkPages = JSON.parseArray(toolbarLinkPageStr);
				JSONObject linkPageObj = null;
				if (linkPages != null && !linkPages.isEmpty()) {
					for (Object object : linkPages) {
						linkPageObj = (JSONObject) object;
						paramobj = new JSONObject();
						paramobj.put("urlId", linkPageObj.getString("id"));
						paramobj.put("type", linkPageObj.getString("ctype"));
						paramobj.put("title", linkPageObj.getString("name"));
						paramobj.put("width", linkPageObj.getString("windowWidth"));
						paramobj.put("height", linkPageObj.getString("windowheight"));
						paramsAry.add(paramobj);
					}
				}
			}

			// 参数关系定义
			JSONArray paramRules = new JSONArray();
			String toolbarParaTypeStr = source.get("toolbarParaType");
			if (notEmpty(toolbarParaTypeStr)) {
				JSONArray paraTypes = JSON.parseArray(toolbarParaTypeStr);
				if (paraTypes != null && !paraTypes.isEmpty()) {
					JSONObject jo = paraTypes.getJSONObject(0).getJSONObject("datas");
					rules = jo.toJSONString();
					Set<String> keys = jo.keySet();
					JSONArray ja = null;
					JSONObject jc = null;
					JSONObject paramRule = null;
					for (String key : keys) {
						ja = jo.getJSONArray(key);
						for (Object object : ja) {
							jc = (JSONObject) object;
							paramRule = new JSONObject();
							paramRule.put("outid", jc.getString("outid"));
							paramRule.put("param", jc.getString("param"));
							paramRules.add(paramRule);
						}
					}
				}
			}
			
			String retVal;
			if(notEmpty(rules)){
				retVal = "pubsub.pub('onDrawEnd'," + (notEmpty(rules) ? rules : "[]") + ",map," + paramsAry.toJSONString() + "," + paramRules.toJSONString() + ");";
			}else{
				retVal = "jsGisFunc._jsGisDraw.call(null,map,[],[]);";
			}

			return retVal;
		}
		return null;
	}

	public String getJsgisUrl() {
		return source.get("jsgisUrl");
	}

	public String getLayer() {
		String url = source.get("jsgisUrl");
		try {
			String response = HttpClientUtils.doGet(url);
			if (StringUtils.isNotEmpty(response)) {
				// //System.out.println(response);
				Document doc = Jsoup.parse(response);
				Elements eles = doc.select("b:contains(Min Scale:)");
				if (eles.size() > 0) {
					Node node = null;
					for (Element element : eles) {
						node = element.nextSibling();
						if ("0".equals(node.toString().trim())) {
							return "ArcGISDynamicMapServiceLayer";
						}
					}
				}
			}
		} catch (Exception e) {

		}
		return "ArcGISTiledMapServiceLayer";
	}

	// 增加模板
	public String getTemplateScript() {
		/*
		 * String linkPage = source.get("linkPage"); //联动页面 JSONArray linkPages
		 * = JSON.parseArray(linkPage); JSONObject params = new JSONObject() ;
		 * if(linkPages!=null && !linkPages.isEmpty()){ String linkstr =
		 * linkPages.getJSONObject(0).toJSONString(); params.put("link",
		 * linkstr); }
		 * 
		 * String jumpType = source.get("jumpType"); //跳转类型
		 * params.put("jumpType", jumpType); String title =
		 * source.get("windowTitle"); //窗口名称 params.put("title", title); String
		 * model = source.get("windowModal"); //是否为模态窗口 params.put("model",
		 * model); String width = source.get("windowWidth"); //窗口宽度
		 * params.put("width", width); String height =
		 * source.get("windowHeight");//窗口高度 params.put("height", height);
		 * 
		 * 
		 * String paraType = source.get("paraType"); //规则 JSONArray paraTypes =
		 * JSON.parseArray(paraType); String rules = null ; if(paraTypes!=null
		 * && !paraTypes.isEmpty()){ rules =
		 * paraTypes.getJSONObject(0).getJSONObject("datas").toJSONString(); }
		 * 
		 * String clickType = source.get("click");// 事件类型
		 * 
		 * StringBuffer sb = new StringBuffer("<script >") ;
		 * sb.append("function ShowPopup(index_id){");
		 * sb.append("pubsub.pub('"+clickType
		 * +"',"+rules+","+params.toJSONString()+",index_id)"); sb.append("}");
		 * sb.append("</script>"); this.addTemplate(sb.toString());
		 */
		scriptMap
				.put("jsgis",
						"<link rel='stylesheet' href='${contextPath}/scripts/arcgis/claro.css'/><link rel='stylesheet' href='${contextPath}/scripts/arcgis/js/esri/css/esri.css'/><link rel='stylesheet' href='${contextPath}/scripts/arcgis/InfoWindow.css'/><script src='${contextPath}/scripts/arcgis/init.js'></script>");
		String script = "<script>var " + this.getId() + "Map, jsgisttb ," + this.getId() + "Bool;</script>";
		script += this.templateScript;
		return script;
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
	public void setFormRules(List<FormRule> formRules) {

	}

	@Override
	public void setFormComponents(List<FormComponent> formComponents) {

	}

	@Override
	public String getElementTagName() {
		return null;
	}

	@Override
	public String getElementHtml() {
		// <button class="k-button"
		// onclick="tb.activate(esri.toolbars.Draw.POINT);">点</button>
		// <button class="k-button"
		// onclick="tb.activate(esri.toolbars.Draw.MULTI_POINT);">多点</button>
		// <button class="k-button"
		// onclick="tb.activate(esri.toolbars.Draw.EXTENT);">面</button>
		// <button class="k-button"
		// onclick="tb.activate(esri.toolbars.Draw.POLYLINE);">线</button>
		// <button class="k-button"
		// onclick="tb.activate(esri.toolbars.Draw.FREEHAND_POLYLINE);">自由线</button>
		// <button class="k-button"
		// onclick="tb.activate(esri.toolbars.Draw.POLYGON);">多边形</button>
		// <button class="k-button"
		// onclick="tb.activate(esri.toolbars.Draw.FREEHAND_POLYGON);">自由多边形</button>
		// <button class="k-button" onclick="tb.deactivate();">移动</button>

		StringBuffer sb = new StringBuffer();
		String toolbar = source.get("toolbar");
		sb.append("<div style='width:" + this.getWidth() + ";height:" + this.getHeight() + "' >");

		if (notEmpty(toolbar)) {
			JSONArray toolbarArray = JSON.parseArray(toolbar);
			if (toolbarArray != null && !toolbarArray.isEmpty()) {
				JSONObject toolbarObj = null;
				for (Object object : toolbarArray) {
					toolbarObj = (JSONObject) object;
					sb.append("<button class='k-button' onclick='jsgistb.activate(" + toolbarObj.getString("code") + ");'>" + toolbarObj.getString("text") + "</button>");
				}
				sb.append("<button class='k-button' onclick='jsgistb.deactivate();'>移动</button>");
			}
		}

		sb.append("<div id='" + this.getId() + "' style='width:100%;height:100%' ></div>");
		sb.append("</div>");
		return sb.toString();
	}

	@Override
	public String getRuleId() {
		return source.get("ruleId");
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
		return source.get("value");
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

	private boolean notEmpty(String str) {
		if (str != null && !"".equals(str.trim())) {
			return true;
		}
		return false;
	}

	public void setScriptMap(Map<String, String> scriptMap) {
		this.scriptMap = scriptMap;
	}

	/**
	 * 弹出窗口是否选用新窗口
	 * 
	 * @return
	 */
	public String isNewWin() {
		String win = source.get("newWin");
		if ("true".equalsIgnoreCase(win)) {
			return "true";
		}
		return "false";
	}

	/**
	 * 多地图切换功能
	 * 
	 * @return
	 */
	public String getMultiMap() {
		String multiMap = source.get("multiMap");
		return multiMap;
	}

	/**
	 * 多地图切换按钮位置
	 * 
	 * @return
	 */
	public String getMultiMapPosition() {
		String positionStr = source.get("multiMapPosition");
		String retPosiTion = "";
		if (notEmpty(positionStr)) {
			JSONArray positionAry = JSON.parseArray(positionStr);
			retPosiTion = positionAry.getJSONObject(0).getJSONObject("position").toJSONString();
		}
		return retPosiTion;
	}

	public String getJsgisDefaultView() {
		String viewStr = source.get("jsgisDefaultView");
		if (notEmpty(viewStr)) {
			JSONArray viewAry = JSON.parseArray(viewStr);
			return this.getId() + "Map.initExtent = " + viewAry.getJSONObject(0).getJSONObject("extent").toJSONString() + ";" + this.getId()
					+ "Map.setExtent(new esri.geometry.Extent(" + this.getId() + "Map.initExtent));";
		}
		return null;
	}

	public String getFlicker() {
		return source.get("flicker") == null ? "500" : source.get("flicker");
	}
	
	
	public String getMaxZoom(){
		return source.getOrDefault("maxZoom", null);
	}
	public String getMinZoom(){
		return source.getOrDefault("minZoom", null);
	}
}

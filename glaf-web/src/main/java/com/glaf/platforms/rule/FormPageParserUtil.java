package com.glaf.platforms.rule;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.util.ReflectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.context.ContextFactory;
import com.glaf.form.core.domain.FormComponent;
import com.glaf.form.core.domain.FormComponentProperty;
import com.glaf.form.core.domain.FormPage;
import com.glaf.form.core.domain.FormRule;
import com.glaf.form.core.domain.FormTemplate;
import com.glaf.form.core.service.FormComponentPropertyService;
import com.glaf.form.core.service.FormPageService;
import com.glaf.form.core.service.FormRuleService;
import com.glaf.form.rule.Global;
import com.glaf.platforms.rule.app.TemplateApp;
import com.glaf.platforms.rule.util.EventUtil;
import com.glaf.platforms.rule.util.EventUtil2;
import com.glaf.platforms.rule.util.PermissionUtil;
import com.glaf.platforms.rule.util.PublishUtil;
import com.mchange.io.FileUtils;

public class FormPageParserUtil {

	protected static final Log logger = LogFactory.getLog(FormPageParserUtil.class);

	public FormPageParserUtil() {
	}

	public static void publish(FormPage formPage, List<FormRule> formRules, List<FormComponent> formComponents, String path, FormPageService formPageService) {
		Document doc = Jsoup.parse(formPage.getFormHtml());
		StringBuffer templateScript = new StringBuffer(); // 模板脚本
		// 布局初始化
		Elements layouts = doc.getElementsByAttributeValue("crtltype", "layout");
		StringBuffer layoutJs = null;
		if (layouts != null && layouts.size() > 0) {
			layoutJs = new StringBuffer(" <script>  $(document).ready(function() { ");
			while (true) {
				layouts = doc.getElementsByAttributeValue("crtltype", "layout");
				if (layouts != null && layouts.size() > 0) {
					Element layout = layouts.get(0);
					initLayout(layout, doc, layoutJs, formRules, formComponents);
				} else {
					break;
				}
			}
			layoutJs.append("  }) </script>   ");
		}
		
		Map<String,Object> otherMap = new HashMap<>();
		//存储页面规则变量的中文名称对象
		JSONObject varObj = new JSONObject();
		otherMap.put("_var_", varObj);
		
		Map<String, String> pageMap = null;
		Map<String, String> initScriptMap = new HashMap<>();
		// 页面初始化
		try {
			StringBuffer pageJs = new StringBuffer();
			pageMap = getPageMap(formPage.getId(), formRules, formComponents);
			
			if (pageMap.size() > 0) {
				// 屏幕初始事件
				PublishUtil.screenInit(pageMap, pageJs);
				
				//如果图形事件定义了 优先图形事件
				if(!EventUtil2.eventInit(pageMap, pageJs, initScriptMap,formPage.getId(),otherMap)){
					// 事件定义器
					PublishUtil.eventInit(pageMap, pageJs, initScriptMap,otherMap);
				}
				
				// 验证定义器
				PublishUtil.validateInit(pageMap, pageJs);
				if (pageJs.length() > 0) {
					templateScript.append(pageJs);
				}
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			logger.debug(" page rule error !");
		}
		Map<String, String> scriptMap = new HashMap();
		// 获取包含role属性的元素
		Elements elements = doc.getElementsByAttribute("data-role");
		// 创建相对应的脚本文件
		Element scriptElement = doc.createElement("script");
		StringBuffer scriptStr = new StringBuffer();
		scriptStr.append("\n jQuery(function() { ");
		for (Element element : elements) {
			if (!element.attr("crtltype").startsWith("layout")) { // 不是layout
																	// 布局的元素
				// 替换元素
				initElement(element, doc, formRules, formComponents, scriptStr, templateScript, formPage, scriptMap, initScriptMap,varObj);
			}
		}
		// 页面元素其他属性初始化
		initElement(doc.select("body").get(0), doc, formRules, formComponents, scriptStr, templateScript, formPage, scriptMap, initScriptMap,varObj);
		scriptStr.append("pageInit();});\n");
		/*
		 * scriptElement.append(scriptStr.toString());
		 * doc.appendChild(scriptElement);
		 */

		// 增加layout相关的javascript脚本
		if (layoutJs != null) {
			templateScript.append(layoutJs.toString());
		}
		// 动态加载的js脚本
		String scriptMapStr = "";
		if (!scriptMap.isEmpty()) {
			Set<String> keys = scriptMap.keySet();
			for (String key : keys) {
				scriptMapStr += scriptMap.get(key);
			}
		}
		try {
			String initStr = "var _param_;" ;
			if(initScriptMap.size()>0){
				initStr += "var __gobalInit__ = " +JSON.toJSON(initScriptMap).toString() +";";
			}
			
			String outPutStr = "<html><head>{{templatescript}}{{script}}</head>{{body}}</html>";
			// 正则匹配
			String regex = "\\{\\{script\\}\\}";
			outPutStr = replaceByRegex(outPutStr, regex,
					"<script>" +(initStr+ /*doc.select ( "script") .text()*/scriptStr.toString()).replaceAll("\n|\t|\r", "") + "</script>");
			regex = "\\{\\{body\\}\\}";
			outPutStr = replaceByRegex(outPutStr, regex, doc.body().select("body>:not(script)").outerHtml());
			regex = "\\{\\{templatescript\\}\\}";
			outPutStr = replaceByRegex(outPutStr, regex, getDynamicScript(formPage.getUiType()) + scriptMapStr + (templateScript != null ? templateScript.toString() : ""));

			if (pageMap.size() > 0 && pageMap.get("permissionDefined") != null) {
				// 权限处理
				outPutStr = PermissionUtil.permissionInit(pageMap, outPutStr);
			}
			formPage.setOutputHtml(outPutStr);
			
			//查询所有方法中文名
			pushVar2DebuggerVar(varObj);
			
			formPage.setDebuggerVar("<script type=\"text/javascript\">var debuggerVar_ = "+varObj.toJSONString()+";</script>");
			try {
				FormRuleService formRuleService = ContextFactory.getBean("formRuleService");
				// 获取页面主表
				String tableName = formRuleService.getTableNameByPageId(formPage.getId());
				formPage.setBusinessTable(tableName);

				// 获取页面主 参数
				String variable = formRuleService.getIdVariable(formPage.getId(), "id");
				formPage.setPrimaryKeyColumn(variable);
			} catch (Exception tableNotFound) {
				logger.error(tableNotFound.getMessage());
			}

			formPageService.save(formPage);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("FormPageParserUtil : " + e.getMessage());
		}
	}

	// 获取动态其他js文件
	private static String getDynamicScript(String uiType) {
		String script = "";
		Boolean isDebug = SystemConfig.getBoolean("dynamicScript");
		if (isDebug != null && isDebug) {
			String path = "";
			if (uiType == null || "".equalsIgnoreCase(uiType) || "kendo".equalsIgnoreCase(uiType)) {
				path = SystemProperties.getConfigRootPath() + "/conf/templates/form/dynamicScript.tmp";
			} else {
				path = SystemProperties.getConfigRootPath() + "/conf/templates/form/dynamicBootstrapScript.tmp";
			}
			File file = new File(path);
			try {
				script = FileUtils.getContentsAsString(file, "UTF-8").replaceAll("[\\t\\n\\r]", "");
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			if (uiType == null || "".equalsIgnoreCase(uiType) || "kendo".equalsIgnoreCase(uiType)) {
				script = "<script type='text/javascript' src='${contextPath}/scripts/defineKendoui.js'></script>";
			} else {
				//script = "<script type='text/javascript' src='${contextPath}/scripts/exec/js/bootstrap.extend.all.min.js'></script>";
			}
		}
		return script;
	}

	/**
	 * 循环遍历布局元素
	 * 
	 * @param childNodes
	 *            //元素下的子节点
	 * @param layoutStr
	 *            //生成的新的html
	 * @param id
	 *            //元素id
	 * @param layoutJs
	 *            //生成相应的javascript
	 * @throws Exception
	 */
	private static void traverseNodes(List<Node> childNodes, StringBuffer layoutStr, String id, StringBuffer layoutJs, List<FormRule> formRules, List<FormComponent> formComponents)
			throws Exception {
		String nodeName = "";
		StringBuffer js = new StringBuffer("$('#" + id + "').kendoSplitter(");
		StringBuffer panesJs = new StringBuffer();
		int count = 0;
		int index = 0;
		String _id = "";
		for (Node node : childNodes) {
			if (!"#text".equals(node.nodeName()) && ("tr".equalsIgnoreCase(node.nodeName()) || "td".equalsIgnoreCase(node.nodeName()))) {
				Element ele = (Element) node;
				nodeName = node.nodeName();// HTML标记名称
				_id = node.attr("id");
				if (StringUtils.isEmpty(_id)) {
					_id = id + "_" + index;
				}
				// style='width:"+(StringUtils.isNotEmpty(node.attr("width"))?node.attr("width"):"100%")
				// +";height:"+(StringUtils.isNotEmpty(node.attr("height"))?node.attr("height"):"100%")+"'
				layoutStr.append("<div id='" + _id + "' style='width:99.8%" + ";height:99.8%' >");
				// 循环遍历节点
				List<Node> childs = node.childNodes();
				if ("tr".equalsIgnoreCase(nodeName) && childs != null && childs.size() > 0) {// 当前元素为tr
					traverseNodes(childs, layoutStr, _id, layoutJs, formRules, formComponents);
					// 如果是tr节点其配置的规则信息在 其设置的div中存放
					ele.attr("id", ele.attr("id") + "_div");
				} else if ("td".equalsIgnoreCase(nodeName)) {// 当前元素为td的时候
					layoutStr.append(ele.html());
				}
				layoutStr.append("</div>");
				// TODO 这里要实现数据库定义的一些规则
				// tr标签获取高度，td标签获取宽度
				panesJs.append("{");
				Map<String, String> source = getPropertyMap(ele, formRules, formComponents);
				String size = ("tr".equalsIgnoreCase(nodeName) ? node.attr("height") : node.attr("width"));
				if (source != null && source.size() > 0) {
					if (StringUtils.isNotEmpty(source.get("size"))) {// 尺寸（如果有配置按照配置尺寸，如果没配置则按照设计页面尺寸）
						size = source.get("size");
					}
					if (StringUtils.isNotEmpty(source.get("collapsible")) && "true".equalsIgnoreCase(source.get("collapsible"))) {// 可折叠
																																	// 默认false
						panesJs.append("collapsible: true ,");
					}
					if (StringUtils.isNotEmpty(source.get("collapsed")) && "true".equalsIgnoreCase(source.get("collapsed"))) {// 默认收缩
																																// 默认
																																// false
						panesJs.append("collapsed:" + source.get("collapsed") + ",");
					}
					if (StringUtils.isNotEmpty(source.get("scrollable")) && !"true".equalsIgnoreCase(source.get("scrollable"))) {// 滚动条
						panesJs.append("scrollable:" + source.get("scrollable") + ",");
					}
					if (StringUtils.isNotEmpty(source.get("max"))) {// 最大扩展宽度
						panesJs.append("max:'" + source.get("max") + "',");
					}
					if (StringUtils.isNotEmpty(source.get("min"))) {// 最小扩展宽度
						panesJs.append("min:'" + source.get("min") + "',");
					}
					if (StringUtils.isNotEmpty(source.get("resizable")) && !"true".equalsIgnoreCase(source.get("resizable"))) {// 拖拽大小
																																// 默认true
						panesJs.append("resizable:" + source.get("resizable") + ",");
					}
					if (StringUtils.isNotEmpty(source.get("collapsedSize"))) {// 收缩预留尺寸
						panesJs.append("collapsedSize:'" + source.get("collapsedSize") + "',");
					}
					if (StringUtils.isNotEmpty(source.get("linkPage"))) {// 关联页面
						String linkPage = source.get("linkPage");
						JSONArray jsonArray = JSON.parseArray(linkPage);
						JSONObject jsonObject = (JSONObject) jsonArray.get(0);
						// http://localhost:8080/glaf/mx/form/page/viewPage?isPublish=true&id=06ba3d1fc5fc4a258e56d9d30129ad7c
						// 必须要带http://ip:port/
						// String link = "getUrlToSplitter()+
						// contextPath+'/mx/form/page/viewPage?id="+jsonObject.getString("id")+"'
						// " ;
						String link = "getUrlToSplitter()+ contextPath+'" + jsonObject.getString("url") + "' ";
						panesJs.append("contentUrl:" + link + ",");
					}
				}
				panesJs.append("size:'" + size + "',");
				panesJs.append("},");
				count++;
				index++;
			}
		}
		String orientation = ("tr".equalsIgnoreCase(nodeName) ? "vertical" : "horizontal");// 方向
		js.append("{orientation: '" + orientation + "',panes: [");
		js.append(panesJs.toString());
		js.append("] });");
		if (count > 1) { // panes大于1的时候才需要渲染
			layoutJs.append(js.toString());
		}
	}

	/**
	 * 初始化布局模版
	 * 
	 * @param layout
	 * @param doc
	 * @param templateScript
	 */
	private static void initLayout(Element layout, Document doc, StringBuffer layoutJs, List<FormRule> formRules, List<FormComponent> formComponents) {
		String id = layout.attr("id");
		String layoutWidth = layout.attr("width");
		String layoutHeight = layout.attr("height");
		StringBuffer layoutStrs = new StringBuffer(
				"<div id='" + id + "' style='width:" + (StringUtils.isNotEmpty(layoutWidth) && !"100%".equalsIgnoreCase(layoutWidth) ? layoutWidth : "99.8%") + ";height:"
						+ (StringUtils.isNotEmpty(layoutHeight) && !"100%".equalsIgnoreCase(layoutHeight) ? layoutHeight : "99.7%") + "' >");
		List<Node> childNodes = layout.getElementsByTag("tbody").get(0).childNodes();

		try {
			traverseNodes(childNodes, layoutStrs, id, layoutJs, formRules, formComponents);
		} catch (Exception e) {
			e.printStackTrace();
		}

		layoutStrs.append("</div>");

		// 替换元素
		Document document = Jsoup.parseBodyFragment(layoutStrs.toString());
		Element body = document.body();// 获取需要替换的元素
		layout.replaceWith(body.child(0));
	}

	public static String replaceByRegex(String outputStr, String regex, String replacement) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(outputStr);
		outputStr = matcher.replaceAll(java.util.regex.Matcher.quoteReplacement(replacement));
		return outputStr;
	}

	/**
	 * 角色转换
	 * 
	 * @param role
	 * @return
	 */
	private static String convertRole(String role) {
		if (role.replaceAll("\\d|\\_|\\-", "").equalsIgnoreCase("colmd")) {
			role = "col-md-12";
		}
		return role;
	}

	/**
	 * 初始化元素GridModel
	 * 
	 * @param element
	 * @param doc
	 * @param formRules
	 * @param formComponents
	 * @param scriptStr
	 */
	public static void initElement(Element element, Document doc, List<FormRule> formRules, List<FormComponent> formComponents, StringBuffer scriptStr, StringBuffer templateScript,
			FormPage formPage, Map<String, String> scriptMap, Map<String, String> initScriptMap,JSONObject varObj) {
		Object model = null;
		String className = null;
		String dataRole = element.attr("data-role").toString().toLowerCase();
		if ("body".equalsIgnoreCase(element.tagName())) {
			dataRole = "page";
		}
		dataRole = convertRole(dataRole);

		String uiType = formPage.getUiType() == null ? "kendo" : formPage.getUiType(); // 获取页面UI类型
		FormComponent formComponent = getFormComponentByElemRole(element, formComponents); // 获取当前控件使用范围
		String category = "kendo";
		if (formComponent != null) {
			category = formComponent.getCategory();
		}
		String type = "";
		if (uiType.equalsIgnoreCase(category) && !"kendo".equalsIgnoreCase(uiType)) {
			type = uiType + ".";
		}
		try {
			// 根据data-role反射加载Model类 并注入source
			className = ControlsEnum.getClassName(dataRole);
			if (className != null) {
				Class<?> clazz = (Class<?>) Class.forName(Global.PLATFORMS_PATH + "model." + type + className);
				model = clazz.newInstance();
				Map<String, String> source = getPropertyMap(element, formRules, formComponents);
				if (source == null || source.size() == 0) {
					return;
				}
				varObj.put(source.get("ruleId"), source.get("html"));
				Method method = clazz.getMethod("setSource", Map.class);
				method.invoke(model, source);
				try {

					Method[] methods = clazz.getMethods();
					List<String> methodNameList = new ArrayList<>();
					for (Method method2 : methods) {
						methodNameList.add(method2.getName());
					}
					if (methodNameList.contains("setElement")) {
						method = clazz.getMethod("setElement", Element.class);
						if (method != null) {
							method.invoke(model, element);
						}
					}

					if (methodNameList.contains("setFormPage")) {
						method = clazz.getMethod("setFormPage", FormPage.class);
						if (method != null) {
							method.invoke(model, formPage);
						}
					}

					if (methodNameList.contains("setScriptMap")) {
						method = clazz.getMethod("setScriptMap", Map.class);
						if (method != null) {
							method.invoke(model, scriptMap);
						}
					}

					if (methodNameList.contains("setFormRules")) {
						method = clazz.getMethod("setFormRules", List.class);
						if (method != null) {
							method.invoke(model, formRules);
						}
					}

					if (methodNameList.contains("setFormComponents")) {
						method = clazz.getMethod("setFormComponents", List.class);
						if (method != null) {
							method.invoke(model, formComponents);
						}
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}

				Object[] args = null;
				// Method tagName = c.getMethod("getElementTagName", null);
				Method tagName = org.springframework.util.ReflectionUtils.findMethod(clazz, "getElementTagName");
				Element targetElement = null;
				Element e = null;
				boolean flag = (tagName.invoke(model, args) != null) ? true : false;
				if (flag) { // 根据标签生成元素
					targetElement = doc.createElement(tagName.invoke(model, args).toString());
					targetElement.attr("id", element.attr("id"));
					targetElement.attr("data-role", element.attr("data-role"));
				} else { // 根据元素HTML
					Method elementHtml = org.springframework.util.ReflectionUtils.findMethod(clazz, "getElementHtml");
					if (elementHtml.invoke(model, args) != null) {
						Document document = Jsoup.parseBodyFragment(elementHtml.invoke(model, args).toString());
						Element body = document.body();// 获取body
						e = body.child(0);// 获取body内的所有元素
						targetElement = e.getElementById(element.attr("id"));
					}
				}
				// 标签元素大小
				// TODO 由属性配置
				// targetElement.attr("style","width:"+element.attr("width")+"px;height:"+element.attr("height")+"px;"+
				// targetElement.attr("style"));

				Method[] methods = clazz.getMethods();
				StringBuffer attrStr = new StringBuffer("");
				StringBuffer dataStr = new StringBuffer("");
				TemplateApp.getTempStr(model, methods, getTemplate("attr", element, formComponents), getTemplate("data", element, formComponents), attrStr, dataStr);
				JSONObject jsonObject = JSON.parseObject(attrStr.toString());
				if (targetElement != null) {
					attrs(targetElement, jsonObject);
					element.replaceWith(flag ? targetElement : e);
				} else {
					attrs(element, jsonObject);
				}
				if (initScriptMap.containsKey(element.attr("id"))) {
					initScriptMap.put(element.attr("id"), wrapInitScript(dataStr));
				} else {
					scriptStr.append(dataStr);
				}

				Method getTemplateScript = ReflectionUtils.findMethod(clazz, "getTemplateScript");
				if (getTemplateScript != null) {
					templateScript.append(ReflectionUtils.invokeMethod(getTemplateScript, model));
				}
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			logger.error("FormPageParserUtil initElement 类加载错误 ：" + e.getMessage());
		} catch (InstantiationException e) {
			e.printStackTrace();
			logger.error("FormPageParserUtil initElement 类实例化错误 ：" + e.getMessage());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			logger.error("FormPageParserUtil initElement 指针异常非法错误 ：" + e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("FormPageParserUtil initElement 异常错误 ：" + e.getMessage());
		}
	}

	private static String wrapInitScript(StringBuffer dataStr) {
		return "function(_param_){" + dataStr.toString().replaceAll("\n|\t|\r", "") + "}";
	}

	/**
	 * 查找控件的属性模版
	 * 
	 * @param type
	 * @param element
	 * @param formComponents
	 * @return
	 * @throws Exception
	 */
	private static String getTemplate(String type, Element element, List<FormComponent> formComponents) throws Exception {
		FormComponent formComponent = null;
		String role = element.attr("data-role").toString();
		if ("".equals(role) && "body".equalsIgnoreCase(element.tagName())) {
			role = "page";
		}
		role = convertRole(role);
		for (FormComponent fc : formComponents) {
			if (fc.getDataRole() != null && fc.getDataRole().equalsIgnoreCase(role)) {
				formComponent = fc;
			}
		}
		if (formComponent == null) {
			throw new Exception("FormPageParserUtil : not find the element's <"+element.attr("cname")+"<->"+element.attr("id")+"> formComponent");
		}
		List<FormTemplate> templates = formComponent.getTemplates();
		for (FormTemplate formTemplate : templates) {
			if (type.equals(formTemplate.getType())) {
				return formTemplate.getTemplate() == null ? "" : formTemplate.getTemplate();
			}
		}
		return "";
	}

	/**
	 * 根据元素角色 获取控件信息
	 * 
	 * @param element
	 * @param formComponents
	 * @return
	 */
	public static FormComponent getFormComponentByElemRole(Element element, List<FormComponent> formComponents) {
		FormComponent formComponent = null;
		String role = element.attr("data-role").toString();
		if ("".equals(role) && "body".equalsIgnoreCase(element.tagName())) {
			role = "page";
		}
		role = convertRole(role);
		for (FormComponent fc : formComponents) {
			if (fc.getDataRole() != null && fc.getDataRole().equalsIgnoreCase(role)) {
				formComponent = fc;
			}
		}
		return formComponent;
	}

	/**
	 * 获取定义的规则对应的属性集合
	 * 
	 * @param element
	 * @param formRules
	 * @param formComponents
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> getPropertyMap(Element element, List<FormRule> formRules, List<FormComponent> formComponents) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		FormComponent formComponent = getFormComponentByElemRole(element, formComponents);
		String id = element.attr("id").toString();
		if ("".equals(id)) {
			if (formRules != null && !formRules.isEmpty()) {
				id = formRules.get(0).getPageId();
			} else {
				logger.debug("FormPageParserUtil : not page element's rule ");
				return map;
			}
		}
		FormRule formRule = null;
		if (formComponent == null) {
			logger.debug("FormPageParserUtil : not find the element's <"+element.attr("cname")+"<->"+element.attr("id")+"> formComponent");
			return map;
		}
		for (FormRule fr : formRules) {
			if (formComponent.getId() == fr.getComponentId() && fr.getName().equals(id)) {
				formRule = fr;
			}
		}
		if (formRule == null) {
			// throw new
			// Exception("FormPageParserUtil : not find the element's
			// formRule");
			logger.debug("FormPageParserUtil : not find the element's <"+element.attr("cname")+"<->"+element.attr("id")+"> formRule");
			return map;
		}
		JSONObject jsonObject = (JSONObject) JSON.parse(formRule.getValue());
		for (FormComponentProperty formComponentProperty : formComponent.getProperties()) {
			map.put(formComponentProperty.getName(),
					jsonObject.get(formComponentProperty.getId() + "") != null ? jsonObject.get(formComponentProperty.getId() + "").toString() : null);
		}
		map.put("ruleId", formRule.getId() + ""); // 控件ID
		map.put("pageId", formRule.getPageId() + ""); // 页面ID
		map.put("dataRole", element.attr("data-role").toString()); // 页面ID
		return map;
	}

	/**
	 * 获取页面规则
	 * 
	 * @param element
	 * @param formRules
	 * @param formComponents
	 * @return
	 * @throws Exception
	 */
	public static Map<String, String> getPageMap(String pageId, List<FormRule> formRules, List<FormComponent> formComponents) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		FormComponent formComponent = null;
		FormRule formRule = null;
		for (FormComponent fc : formComponents) {
			if (fc.getDataRole() != null && fc.getDataRole().equalsIgnoreCase("page")) {
				formComponent = fc;
			}
		}
		if (formComponent == null) {
			logger.debug("FormPageParserUtil : not find the page's <"+pageId+"> formComponent");
			return map;
		}
		for (FormRule fr : formRules) {
			if (formComponent.getId() == fr.getComponentId() && fr.getName().equals(pageId)) {
				formRule = fr;
			}
		}
		if (formRule == null) {
			// throw new
			// Exception("FormPageParserUtil : not find the element's
			// formRule");
			logger.debug("FormPageParserUtil : not find the page's <"+pageId+"> formRule");
			return map;
		}
		JSONObject jsonObject = (JSONObject) JSON.parse(formRule.getValue());
		for (FormComponentProperty formComponentProperty : formComponent.getProperties()) {
			map.put(formComponentProperty.getName(),
					jsonObject.get(formComponentProperty.getId() + "") != null ? jsonObject.get(formComponentProperty.getId() + "").toString() : null);
		}
		map.put("ruleId", formRule.getId() + ""); // 控件ID
		map.put("pageId", formRule.getPageId() + ""); // 页面ID
		map.put("dataRole", pageId); // 页面ID
		return map;
	}

	/**
	 * 批量添加节点属性
	 * 
	 * @param element
	 *            节点元素
	 * @param jsonStr
	 *            json字符串
	 */
	public static void attrs(Element element, JSONObject jsonObject) {
		if (jsonObject == null) {
			return;
		}
		Set<String> keySet = jsonObject.keySet();
		Iterator<String> iterator = keySet.iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			String value = (String) jsonObject.get(key);
			element.attr(key, value);
		}
	}
	
	private static void pushVar2DebuggerVar(JSONObject varObj){
		//查询所有方法中文名
		FormComponentPropertyService formComponentPropertyService = ContextFactory.getBean("formComponentPropertyService");
		List<Map<String, Object>> list = formComponentPropertyService.getEventsProperty(null);
		for (Map<String, Object> map : list) {
			varObj.put(map.get("NAME_").toString(), map.get("TITLE_"));
		}
		
		
	}

	public static void main(String[] args) {
		/*
		 * String a = "c$f()dd<d&ff22"; String outPutStr = "haha{{body}}hoho";
		 * String regex = "\\{\\{body\\}\\}"; a =
		 * java.util.regex.Matcher.quoteReplacement(a); outPutStr =
		 * replaceByRegex(outPutStr, regex, a); //System.out.println(outPutStr);
		 */
		String str = "<html><body><div><span></div><div></div><a/><script src='xxx' ></script></body></html>";
		Document doc = Jsoup.parse(str);
		Elements es = doc.body().select("body>:not(script)");

		ListIterator<Element> iter = es.listIterator();
		// System.out.println(es.outerHtml());
		// System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		// System.out.println(es.toString());
		while (iter.hasNext()) {
			Element e = iter.next();
			// System.out.println("===================");
			// System.out.println(e.toString());
		}

	}
}

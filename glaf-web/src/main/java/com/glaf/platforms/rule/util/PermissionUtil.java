package com.glaf.platforms.rule.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.form.rule.util.ListUtil;
import com.glaf.form.rule.util.PermissionFreemarkerFunction;
import com.glaf.template.util.StringTemplateLoader;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class PermissionUtil {
	private static final String TO_HIDE = "toHide" ;
	private static final String TO_DISABLED = "toDisable" ;
	
	public PermissionUtil() {
		
	}

	/**
	 * 处理
	 * 
	 * @param pageMap
	 */
	public static String permissionInit(Map<String, String> pageMap, String pageHtml) {
		String permissionDefined = pageMap.get("permissionDefined");
		if (permissionDefined != null && !"".equals(permissionDefined)) {
			JSONArray parseArray = JSON.parseArray(permissionDefined);
			if (parseArray != null && !parseArray.isEmpty()) {
				Document document = Jsoup.parse(pageHtml);
				JSONObject obj = parseArray.getJSONObject(0);
				JSONArray nodeArys = obj.getJSONArray("nodes");
				if (nodeArys != null && !nodeArys.isEmpty()) {
					JSONObject nodeObj = null;
					JSONObject execObj = null;
					String type = null ;
					JSONArray contentAry = null ;
					JSONArray execNodesAry = null ;
					JSONObject execNodeObj = null ;
					JSONObject execConfAry = null ;
					for (Object object : nodeArys) {
						nodeObj = (JSONObject) object;
						type = nodeObj.getString("input"); // 获取 是角色、部门、用户等类型
						contentAry = nodeObj.getJSONArray("contentData"); // 获取 内容
						execObj = nodeObj.getJSONObject("execFnData"); // 获取 内容
						execNodesAry = execObj.getJSONArray("nodes");
						execConfAry = execObj.getJSONObject("conf");
						for (Object o : execNodesAry) {
							execNodeObj = (JSONObject) o;
							permissionParse(execNodeObj, document, type, contentAry,execConfAry);
						}
					}
					pageHtml = document.html();
				}
			}
		}
		return pageHtml;
	}
	
	private static void toHide(Element element,String type,JSONArray contentAry){
		element.before("[#if mtpk("+type+"Arys,\""+ListUtil.joinJSONArray(contentAry,",")+"\")]");
		element.after("[/#if]");
	}
	private static void toHide(Elements elements,String type,JSONArray contentAry){
		elements.before("[#if mtpk("+type+"Arys,\""+ListUtil.joinJSONArray(contentAry,",")+"\")]");
		elements.after("[/#if]");
	}
	private static void toDisabled(Element element,String type,JSONArray contentAry){
		element.before("[#if !mtpk("+type+"Arys,\""+ListUtil.joinJSONArray(contentAry,",")+"\")]");
		element.after("[/#if]");
		element.after(element.outerHtml());
		element.after("[#else]");
		element.attr("disabled","disabled");
		element.attr("sdisabled","disabled");
	}
	private static void toDisabled(Elements elements,String type,JSONArray contentAry){
		elements.before("[#if !mtpk("+type+"Arys,\""+ListUtil.joinJSONArray(contentAry,",")+"\")]");
		elements.after("[/#if]");
		Elements colneElements = elements.clone();
		elements.after(colneElements.outerHtml());
		elements.after("[#else]");
		elements.attr("disabled","disabled");
		elements.attr("sdisabled","disabled");
	}
	
	/**
	 * 权限解析
	 */
	private static void permissionParse(JSONObject execNodeObj,Document document,String type,JSONArray contentAry,JSONObject execConfAry){
		String eleId = execNodeObj.getString("pId");
		String eName = execNodeObj.getString("eName");
		String role = execNodeObj.getString("role");
		Element element = document.getElementById(eleId);
		if(element!=null){
			switch (role) {
			case "tabstrip":
				tabstripFn(eName, role, type, contentAry, execConfAry, element);
				break;
			default:
				if(TO_HIDE.equalsIgnoreCase(eName)){
					toHide(element, type, contentAry);
				}else if(TO_DISABLED.equalsIgnoreCase(eName)){
					toDisabled(element, type, contentAry);
				}
				break;
			}
		}
	}
	
	/**
	 * 选卡特殊处理
	 * @param eName
	 * @param role
	 * @param type
	 * @param contentAry
	 * @param execConfAry
	 * @param element
	 */
	private static void tabstripFn(String eName,String role,String type,JSONArray contentAry,JSONObject execConfAry,Element element){
		if(TO_HIDE.equalsIgnoreCase(eName)){
			String conf = getRoleConf(execConfAry, role, eName);
			if(conf!=null){
				String [] confs = conf.split(",");
				int index = 0 ;
				for (String string : confs) {
					index = Integer.parseInt(string);
					Elements liElements = element.select("ul li:eq("+(index-1)+")");
					/*liElements.before("[#if mtpk("+type+"Arys,\""+ListUtil.joinJSONArray(contentAry,",")+"\")]");
					liElements.after("[/#if]");*/
					toHide(liElements, type, contentAry);
					
					Elements divElements = element.select("div:eq("+(index)+")");
					toHide(divElements, type, contentAry);
					/*divElements.before("[#if mtpk("+type+"Arys,\""+ListUtil.joinJSONArray(contentAry,",")+"\")]");
					divElements.after("[/#if]");*/
				}
			}else{
				/*element.before("[#if mtpk("+type+"Arys,\""+ListUtil.joinJSONArray(contentAry,",")+"\")]");
				element.after("[/#if]");*/
				toHide(element, type, contentAry);
			}
		}else if(TO_DISABLED.equalsIgnoreCase(eName)){
			//element.attr("disabled","true");
			toDisabled(element, type, contentAry);
		}
	}
	
	/**
	 * 获取详细配置信息
	 */
	private static String getRoleConf(JSONObject execConfAry,String role,String eName){
		JSONObject confObj = execConfAry.getJSONObject(role) ;
		if(confObj!=null){
			return confObj.getString(eName);
		}
		return null ;
	}
	
	public static void main(String[] args) {
		Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
		cfg.setSharedVariable("mtpk", new PermissionFreemarkerFunction());
		String content = "[#if mtpk(lis,cont) ]我的[/#if]你的";
		String result = "";
		try {
			Map<String, Object> context = new HashMap<String, Object>();
			List<String> lis = new ArrayList<String>();
			lis.add("b");
			context.put("lis", ListUtil.joinList(lis, ","));
			List<String> cont = new ArrayList<String>();
			cont.add("a");
			cont.add("b");
			cont.add("c");
			context.put("cont", ListUtil.joinList(cont, ","));

			cfg.setTemplateLoader(new StringTemplateLoader(content));
			cfg.setDefaultEncoding("UTF-8");
			cfg.setTagSyntax(2);
			Template template = cfg.getTemplate("");
			StringWriter writer = new StringWriter();
			template.process(context, writer);
			writer.flush();
			result = writer.toString();

			//System.out.println(result);
		} catch (TemplateException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}

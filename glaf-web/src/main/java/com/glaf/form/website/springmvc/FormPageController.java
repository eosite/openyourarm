/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.glaf.form.website.springmvc;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.base.modules.sys.model.SysUser;
import com.glaf.base.modules.sys.service.SysUserService;
import com.glaf.base.utils.ContextUtil;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.identity.User;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.ClassUtils;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.web.callback.CallbackProperties;
import com.glaf.core.web.callback.LoginCallback;
import com.glaf.datamgr.domain.DataSet;
import com.glaf.datamgr.jdbc.DataSetBuilder;
import com.glaf.datamgr.service.DataSetService;
import com.glaf.expression.core.util.ExpressionConvertUtil;
import com.glaf.form.core.domain.FormPage;
import com.glaf.form.core.domain.FormRule;
import com.glaf.form.core.service.FormComponentService;
import com.glaf.form.core.service.FormPageService;
import com.glaf.form.core.service.FormRulePropertyService;
import com.glaf.form.core.service.FormRuleService;
import com.glaf.form.core.service.FormTemplateService;
import com.glaf.form.rule.ParamsSqlHelper;
import com.glaf.form.rule.util.InjectUtils;
import com.glaf.form.rule.util.ListUtil;
import com.glaf.form.rule.util.TemplateExtUtils;
import com.glaf.template.Template;
import com.glaf.template.TemplateContainer;
import com.glaf.template.util.TemplateUtils;

/**
 * 
 * SpringMVC控制器
 * 
 */

@Controller("/public/form/page")
@RequestMapping("/public/form/page")
public class FormPageController {
	protected static final Log logger = LogFactory.getLog(FormPageController.class);
	protected FormPageService formPageService;
	protected FormRuleService formRuleService;
	protected FormComponentService formComponentService;
	protected FormTemplateService formTemplateService;
	protected SysUserService sysUserService;

	@Autowired
	protected DataSetService dataSetService;
	@Autowired
	protected FormRulePropertyService formRulePropertyService;

	public FormPageController() {

	}

	protected void doLogin(HttpServletRequest request, HttpServletResponse response) {
		String actorId = RequestUtils.getActorId(request);
		if (StringUtils.isEmpty(actorId)) {
			SysUser bean = sysUserService.findByAccount("anyone");
			if (bean != null && bean.getLocked() == 0) {
				HttpSession session = request.getSession(true);// 重写Session
				logger.debug("sessionid:" + session.getId());
				String ipAddr = RequestUtils.getIPAddress(request);
				// 登录成功，修改最近一次登录时间
				bean.setLastLoginDate(new Date());
				bean.setLastLoginIP(ipAddr);
				bean.setLockLoginTime(new Date());
				bean.setLoginRetry(0);
				sysUserService.updateUser(bean);

				ContextUtil.put(bean.getAccount(), bean);// 传入全局变量
				RequestUtils.setRequestParameterToAttribute(request);

				RequestUtils.setLoginUser(request, response, "default", bean.getAccount());

				Properties props = CallbackProperties.getProperties();
				if (props != null && props.keys().hasMoreElements()) {
					Enumeration<?> e = props.keys();
					while (e.hasMoreElements()) {
						String className = (String) e.nextElement();
						try {
							Object obj = ClassUtils.instantiateObject(className);
							if (obj instanceof LoginCallback) {
								LoginCallback callback = (LoginCallback) obj;
								logger.debug("-------------------------callback--------------------");
								logger.debug(callback.getClass().getName());
								callback.afterLogin(bean.getAccount(), request, response);
							}
						} catch (Exception ex) {
							ex.printStackTrace();
							logger.error(ex);
						}
					}
				}

			}
		}
	}

	@RequestMapping("/preview")
	public ModelAndView preview(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");

		FormPage formPage = formPageService.getFormPage(request.getParameter("id"));
		if (formPage != null) {
			request.setAttribute("formPage", formPage);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("formPage.preview");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/form/page/preview", modelMap);
	}

	@javax.annotation.Resource
	public void setFormComponentService(FormComponentService formComponentService) {
		this.formComponentService = formComponentService;
	}

	@javax.annotation.Resource
	public void setFormPageService(FormPageService formPageService) {
		this.formPageService = formPageService;
	}

	@javax.annotation.Resource
	public void setFormRuleService(FormRuleService formRuleService) {
		this.formRuleService = formRuleService;
	}

	@javax.annotation.Resource
	public void setFormTemplateService(FormTemplateService formTemplateService) {
		this.formTemplateService = formTemplateService;
	}

	@javax.annotation.Resource
	public void setSysUserService(SysUserService sysUserService) {
		this.sysUserService = sysUserService;
	}

	@ResponseBody
	@RequestMapping("/viewPage")
	public void viewPage(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws IOException {
		this.doLogin(request, response);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		// 防止 xss 攻击
		Set<String> pkeys = params.keySet();
		Object value = null;
		for (String pkey : pkeys) {
			value = params.get(pkey);
			params.put(pkey, HtmlUtils.htmlEscape(value.toString()));
		}
		
		String mtKey = "__mt__";
		if(params.containsKey(mtKey)){
			try {
				String encodeStr = (String) params.get(mtKey);
				String decodeStr = new String(Base64.decodeBase64(encodeStr),"UTF-8");
				if(StringUtils.isNotEmpty(decodeStr)){
					JSONObject decodeObj = JSON.parseObject(decodeStr);
					params.putAll(decodeObj);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		User user = loginContext.getUser();
		params.put("actorId", user.getActorId()); // 角色账号
		params.put("sys_date", new Date().getTime());
		String id = RequestUtils.getString(request, "id");
		id = InjectUtils.escapeSql(id);

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		// response.setHeader("Cache-control", "public");
		// response.setHeader("last-modified", "Thu, 01 Dec 1994 16:00:00 GMT");
		// response.setHeader("Expires", "Thu, 01 Dec 1994 16:00:00 GMT");
		PrintWriter writer = null;
		Writer sw = null;
		String errorStr = "<h1 style='color:red;'>未找到对应页面</h1>";

		try {
			FormPage formPage = formPageService.getFormPage(id);
			writer = response.getWriter();
			if (formPage != null) {
				Template template = TemplateContainer.getContainer().getTemplate(formPage.getUiType() == null ? "kendo" : formPage.getUiType());
				/**
				 * 如果是私有的页面
				 */
				if (StringUtils.equals(formPage.getAccessType(), "PRV")) {
					/**
					 * 定义了权限，需要检查是否有权限
					 */
					if (StringUtils.isNotEmpty(formPage.getPerms())) {
						boolean hasPerm = false;
						Collection<String> permList = StringTools.split(formPage.getPerms());
						Collection<String> perms = loginContext.getPermissions();
						for (String perm : permList) {
							if (perms.contains(perm)) {
								hasPerm = true;
								break;
							}
						}
						/**
						 * 如果没有访问该页面的权限，跳转至未授权页面
						 */
						if (!loginContext.isSystemAdministrator() && !hasPerm) {
							request.getRequestDispatcher("/unauthorized.jsp").forward(request, response);
							return;
						}
					}
				}

				try {
					JSONObject detailObj = initPageDetail(request,params);
					params.put("detail", detailObj);
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e.getMessage());
				}

				Document doc = Jsoup.parse(formPage.getOutputHtml());
				Map<String, Object> context = new HashMap<String, Object>();
				
				context.put("res_system_name", SystemConfig.getString("res_system_name"));// 系统名称
				context.put("res_version", SystemConfig.getString("res_version"));// 系统版本

				context.put("script", doc.select("style").outerHtml() + doc.select("link").outerHtml() + doc.select("script").outerHtml());
				context.put("body", doc.body().html());

				context.put("contextPath", request.getContextPath());
				context.put("pageParameters", JSON.toJSONString(params));
				context.put("serviceUrl", RequestUtils.getServiceUrl(request));
				context.put("scriptTag", "script");
				context.put("title", formPage.getName());

				context.put("actorId", user.getActorId()); // 角色账号
				
				context.put("isDebug", false);
				context.put("isDynamicJs", SystemConfig.getBoolean("dynamicScript"));//动态js调式
				context.put("isEncode", SystemConfig.getBoolean("isEncode"));
				
				String templateContent = TemplateUtils.process(context, template.getContent());

				sw = new java.io.StringWriter();
				TemplateExtUtils.process(context, templateContent, sw);
				sw.flush();

				String output = sw.toString().replaceAll("script-tag", "script");

				writer.write(output);
				writer.flush();
			} else {
				writer.write(errorStr);
			}
		} catch (Exception e) {
			if (writer != null) {
				writer.write(errorStr);
			}
			e.printStackTrace();
			logger.error("FormPageController / viewPage 错误：" + e.getMessage());
		} finally {
			if (writer != null) {
				writer.flush();
				writer.close();
			}
		}
	}

	/**
	 * 页面初始化赋值
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	private JSONObject initPageDetail(HttpServletRequest request,Map<String, Object> params) throws IOException {
		String pageId = ParamUtils.getString(params, "id");// pageId
		if (pageId == null)
			throw new RuntimeException("pageId is null");
		// copy对象
		Map<String, Object> newParams = new HashMap();
		newParams.putAll(params);
		JSONObject result = new JSONObject(newParams);
		Map<String, String> ruleMap = this.getRuleMapByPageIdAndEleId(pageId, pageId);

		this.paramsFilter(params, ruleMap);
		if (ruleMap != null) {
			result.put("pageRuleId", ruleMap.get("__pageRuleId"));
			JSONArray jsonArray = null;
			JSONObject controlRule = null;
			// 页面输入输出参数对应
			String param = null;
			JSONObject jsonObject = null;
			Map<String, Object> datas = new HashMap<String, Object>();// 存储参数值,查询结果
			String inParamDefinedStr = ruleMap.get("inParamDefined");// 页面级参数
			if (!isNullOrEmpty(inParamDefinedStr)) {
				jsonArray = JSONArray.parseArray(inParamDefinedStr);
				if (jsonArray != null && !jsonArray.isEmpty()) {
					jsonObject = jsonArray.getJSONObject(0);
					if (jsonObject.containsKey("type")) {
						jsonArray = jsonObject.getJSONArray("source");
						for (int i = 0; i < jsonArray.size(); i++) {
							jsonObject = jsonArray.getJSONObject(i);
							param = jsonObject.getString("param");
							if (params.get(param) != null) {
								datas.put(param, "" + params.get(param) + "");
							}
						}
					} else {
						for (int i = 0; i < jsonArray.size(); i++) {
							jsonObject = jsonArray.getJSONObject(i);
							param = jsonObject.getString("param");
							if (params.get(param) != null) {
								datas.put(param, "" + params.get(param) + "");
							}
						}
					}
				}
			}

			// 已经对页面进行处理的 Pattern.matches("("+excludeRoleStr+").*",key)
			String[] excludeRole = new String[] { "definedTable", "grid", "ztree", "video", "treelist", "charts", "metroselect" };
			String excludeRoleStr = ListUtil.join(excludeRole, "|");

			String str = ruleMap.get("paraType");
			if (!isNullOrEmpty(str)) {
				jsonArray = JSONArray.parseArray(str);
				str = jsonArray.getJSONObject(0).getString("datas");
				controlRule = JSON.parseObject(str);
				Set<String> controlRuleSet = controlRule.keySet();
				JSONObject controlRuleObj = new JSONObject();
				for (String key : controlRuleSet) {
					if (!Pattern.matches("^(" + excludeRoleStr + ").*",
							key)/* !key.startsWith("definedTable") */) {
						controlRuleObj.put(key, controlRule.get(key));
					}
				}
				result.put("controlRule", controlRuleObj.isEmpty() ? null : controlRuleObj);
			}

			String dataSetId = null;
			str = ruleMap.get("dataSourceSet"); // 页面数据源
			if (!isNullOrEmpty(str)) {
				JSONArray tmp = JSONArray.parseArray(str);
				if (tmp != null && !tmp.isEmpty()) {
					JSONObject dataSource = JSONArray.parseArray(str).getJSONObject(0);
					String dataSourceStr = dataSource.getString("datasource");
					if (StringUtils.isNotBlank(dataSourceStr)) {
						jsonArray = JSONArray.parseArray(dataSourceStr);
					} else {
						/**
						 * 单个数据集
						 */
						jsonArray = JSONArray.parseArray(str);
					}
					List<JSONObject> batchRules = new ArrayList<JSONObject>();
					if (jsonArray != null && !jsonArray.isEmpty()) {
						JSONArray idValues = new JSONArray();
						JSONObject dataSources = new JSONObject();
						result.put("__idValues", idValues);
						result.put("dataSources", dataSources);
						for (int i = 0; i < jsonArray.size(); i++) {
							jsonObject = jsonArray.getJSONObject(i);
							String batchRule = jsonObject.getString("batchRules");
							if (!isNullOrEmpty(batchRule)) {
								batchRules.add(JSON.parseObject(batchRule));
								continue;
							}

							dataSetId = jsonObject.getString("datasetId");
							String inParameters = ruleMap.get("inParameters");
							// 获取页面传递参数
							String psql = ParamsSqlHelper.getParamSql(JSON.toJSONString(datas), inParameters, dataSetId);
							logger.info(psql);
							boolean ok = true;
							if (!isNullOrEmpty(inParameters)) {
								ok = this.initParameters(inParameters, datas, result, dataSetId);
							}
							if (StringUtils.isNotBlank(dataSetId) && (ok || !"".equals(psql))) {
								// 构建sql start
								try {
									DataSetBuilder builder = new DataSetBuilder();
									JSONObject json = builder.getJson(dataSetId, psql, null, 0, 1, datas);
									if (json != null) {
										JSONArray arr = json.getJSONArray("rows");
										JSONObject job;
										if (arr != null && arr.size() > 0) {
											params.putAll(job = arr.getJSONObject(0));
											dataSources.put(dataSetId, convertObj(job));
											DataSet ds = dataSetService.getDataSet(dataSetId);
											if (ds != null) {
												if (StringUtils.isNotEmpty(ds.getPrimaryKeys())) { // 获取主键
													if (CollectionUtils.isNotEmpty(ds.getFromSegments())) {
														String tn = ds.getFromSegments().get(0).getTableName();
														String keys[] = StringUtils.split(ds.getPrimaryKeys(), ",");
														for (String key : keys) {
															Map<String, Object> map = new HashMap<String, Object>();
															map.put("tn", tn);
															map.put("value", params.get(tn + "_0_" + key));
															map.put("cn", key);
															idValues.add(map);
															if (StringUtils.startsWithIgnoreCase(key, "id")) {
																params.put(tn + "_0_id", map.get("value"));
															}
														}
													}

												}
											}
										}
									}
								} catch (Exception e) {
									e.printStackTrace();
									logger.debug(e.getMessage());
									throw e;
								}
							}
						}
					}
					result.put("batchRules", batchRules);
				}
			}
			String idValue = null;
			if (controlRule != null) {
				Map<String, Object> data = new HashMap<String, Object>();// 页面控件id对应值
				for (String key : controlRule.keySet()) {
					jsonArray = JSONArray.parseArray(controlRule.getString(key));
					for (int k = 0; k < jsonArray.size(); k++) {
						JSONObject j = jsonArray.getJSONObject(k);
						String columnName = j.getString("columnName");
						if (StringUtils.isBlank(columnName)) {
							columnName = j.getString("inparam");
						}
						if (columnName != null) {
							j.put("idValue", params.get(columnName.split("_0_")[0] + "_0_id"));
							if (key.startsWith("editor")) {
								j.put("value", params.get(columnName) != null ? params.get(columnName).toString().replace("\"", "\\\"") : "");
							} else if (key.startsWith("cell_")) {
								j.put("value", params.get(columnName) != null ? params.get(columnName).toString().replace("\\\"", "\\\\\"").replace("\"", "\\\"") : "");
							} else {
								// 防止注入
								Object obj = params.get(columnName);
								if (obj instanceof String) {
									j.put("value", params.get(columnName) != null ? HtmlUtils.htmlEscape(params.get(columnName).toString()) : "");
								} else {
									j.put("value", params.get(columnName));
								}
							}
						}
						if (idValue == null)
							idValue = j.getString("idValue");
					}
					data.put(key, JSON.toJSON(jsonArray));
				}
				result.put("data", data);
			}
			result.put("__idValue", idValue);

			str = ruleMap.get("saveSourceSet"); // 保存设置

			try {
				str = str == null ? ruleMap.get("linkageControlIn") : str;
				if (StringUtils.isNotBlank(str)) {

					result.put("handleColumns", JSON.parseArray(str));
				}

			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}

		}

		return result;
	}

	/**
	 * 转换
	 * 
	 * @param oldObj
	 * @return
	 */
	private JSONObject convertObj(JSONObject oldObj) {
		JSONObject newObj = new JSONObject();
		Set<String> keys = oldObj.keySet();
		Object obj = null;
		for (String key : keys) {
			obj = oldObj.get(key);
			if (obj instanceof String) {
				newObj.put(key, obj != null ? obj.toString().replace("\\\"", "\\\\\"").replaceAll("\"", "\\\\\"") : obj);
			} else {
				newObj.put(key, obj);
			}
		}

		return newObj;
	}

	private Map<String, String> getRuleMapByPageIdAndEleId(String pageId, String eleId) {
		Map<String, String> ruleMap = null;
		List<FormRule> frs = formRuleService.getRulesByEleId(pageId, eleId);
		if (frs != null && frs.size() > 0) {
			FormRule fr = frs.get(0);
			ruleMap = this.getRuleMap(fr.getId());
			ruleMap.put("__pageRuleId", fr.getId());
		}
		return ruleMap;
	}

	private static boolean isNullOrEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	private Map<String, String> getRuleMap(String rid) {
		return this.formRulePropertyService.getRuleMap(rid);
	}

	private boolean initParameters(String inParameters, Map<String, Object> datas, JSONObject result, String datasetId) {
		boolean rst = false;
		if (!isNullOrEmpty(inParameters)) {
			Map<String, List<Map<String, Object>>> tables = new HashMap<String, List<Map<String, Object>>>();
			JSONArray jsonArray0 = JSONArray.parseArray(inParameters);
			if (jsonArray0 != null && !jsonArray0.isEmpty()) {
				for (int i = 0; i < jsonArray0.size(); i++) {
					String tabId = jsonArray0.getJSONObject(i).getString("tabId");
					if (!datasetId.equals(tabId)) {
						continue;
					}
					JSONArray jsonArray = jsonArray0.getJSONObject(i).getJSONArray("collection");
					if (jsonArray.isEmpty()) {
						rst = true;
					}
					for (int ii = 0; ii < jsonArray.size(); ii++) {
						JSONObject jsonObject = jsonArray.getJSONObject(ii);
						String strs = jsonObject.getString("paramData");
						String expActVal = JSON.parseObject(strs).getString("expActVal");
						expActVal = ExpressionConvertUtil.expressionConvert(expActVal, ExpressionConvertUtil.DATABASE_TYPE);

						if (!isNullOrEmpty(expActVal))
							expActVal = StringUtils.split(expActVal, ".")[0];

						Object val = datas.get(expActVal);
						if (val == null) {
							continue;
						}

						strs = jsonObject.getString("fieldData");
						expActVal = JSON.parseObject(strs).getString("expActVal");
						expActVal = ExpressionConvertUtil.expressionConvert(expActVal, ExpressionConvertUtil.DATABASE_TYPE);
						if (!isNullOrEmpty(expActVal)) {
							String fieldName = StringUtils.split(expActVal, ".")[1];
							// if (fieldName.equalsIgnoreCase("id"))
							// continue;
							rst = true;
							String tableName = StringUtils.split(expActVal, ".")[0];
							Map<String, Object> m = new HashMap<String, Object>();
							if (!tables.containsKey(tableName))
								tables.put(tableName, new ArrayList<Map<String, Object>>());
							// String key = tableName + "_0_" + fieldName;
							m.put("fieldName", fieldName);
							m.put("value", val);
							tables.get(tableName).add(m);
						}
					}
				}
			}
			result.put("tables", tables);
		}
		return rst;
	}

	/**
	 * 过滤 zj 特殊参数
	 * 
	 * @param params
	 * @param ruleMap
	 */
	private void paramsFilter(Map<String, Object> params, Map<String, String> ruleMap) {

		if (params.get("flow-param") != null) { // pageId_0_id
			// String flowParam = params.get("flow-param").toString();
			// if (flowParam.contains("_0_")) {
			String inParameters = ruleMap.get("inParameters"), param = null;
			if (!isNullOrEmpty(inParameters)) {
				JSONArray jsonArray0 = JSONArray.parseArray(inParameters);
				if (jsonArray0 != null && !jsonArray0.isEmpty()) {
					for (int i = 0; i < jsonArray0.size(); i++) {
						JSONArray jsonArray = jsonArray0.getJSONObject(i).getJSONArray("collection");
						for (int ii = 0; ii < jsonArray.size(); ii++) {
							JSONObject jsonObject = jsonArray.getJSONObject(ii);
							String strs = jsonObject.getString("paramData");
							String expActVal = JSON.parseObject(strs).getString("expActVal");
							expActVal = ExpressionConvertUtil.expressionConvert(expActVal, ExpressionConvertUtil.DATABASE_TYPE);

							if (!isNullOrEmpty(expActVal))
								param = StringUtils.split(expActVal, ".")[0];

							strs = jsonObject.getString("fieldData");
							expActVal = JSON.parseObject(strs).getString("expActVal");
							expActVal = ExpressionConvertUtil.expressionConvert(expActVal, ExpressionConvertUtil.DATABASE_TYPE);
							if (!isNullOrEmpty(expActVal)) {
								String fieldName = StringUtils.split(expActVal, ".")[1];
								if (fieldName.equalsIgnoreCase("id") && param != null) {
									params.put(param, params.get("flow-param"));
								}
							}
						}
						// }
					}
				}
			}
		}
	}

}

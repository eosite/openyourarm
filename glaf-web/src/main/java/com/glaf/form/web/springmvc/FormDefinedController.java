package com.glaf.form.web.springmvc;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.activiti.model.TaskItem;
import com.glaf.base.modules.BaseDataManager;
import com.glaf.base.modules.sys.model.Dictory;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.query.SysTreeQuery;
import com.glaf.core.config.DBConfiguration;
import com.glaf.core.config.Environment;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.Database;
import com.glaf.core.factory.TableFactory;
import com.glaf.core.identity.User;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.jdbc.QueryHelper;
import com.glaf.core.security.LoginContext;
import com.glaf.core.security.SecurityUtils;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.ClassUtils;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.FileUtils;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;
import com.glaf.core.util.http.HttpClientUtils;
import com.glaf.datamgr.domain.DataSet;
import com.glaf.datamgr.domain.DataSetAudit;
import com.glaf.datamgr.domain.DynamicSqlTree;
import com.glaf.datamgr.domain.FromSegment;
import com.glaf.datamgr.domain.SelectSegment;
import com.glaf.datamgr.domain.TaskTable;
import com.glaf.datamgr.query.DataSetQuery;
import com.glaf.datamgr.query.DynamicSqlTreeQuery;
import com.glaf.datamgr.service.DataSetAuditService;
import com.glaf.datamgr.service.DataSetService;
import com.glaf.datamgr.service.DynamicSqlTreeService;
import com.glaf.datamgr.service.TaskTableService;
import com.glaf.dep.base.domain.DepBaseWdataSet;
import com.glaf.dep.base.service.DepBaseWdataSetService;
import com.glaf.dep.report.domain.DepReportCell;
import com.glaf.dep.report.domain.DepReportTemplate;
import com.glaf.dep.report.query.DepReportCellQuery;
import com.glaf.dep.report.service.DepReportCellService;
import com.glaf.dep.report.service.DepReportTemplateService;
import com.glaf.form.core.domain.FormComponent;
import com.glaf.form.core.domain.FormComponentProperty;
import com.glaf.form.core.domain.FormDictTree;
import com.glaf.form.core.domain.FormDictory;
import com.glaf.form.core.domain.FormPage;
import com.glaf.form.core.domain.FormRule;
import com.glaf.form.core.domain.FormRuleProperty;
import com.glaf.form.core.domain.FormTask;
import com.glaf.form.core.domain.FormTaskmain;
import com.glaf.form.core.domain.FormVideo;
import com.glaf.form.core.domain.FormWorkFlowRule;
import com.glaf.form.core.domain.FormWorkflowPlan;
import com.glaf.form.core.domain.WdatasetSqllite;
import com.glaf.form.core.query.FormComponentPropertyQuery;
import com.glaf.form.core.query.FormComponentQuery;
import com.glaf.form.core.query.FormDictTreeQuery;
import com.glaf.form.core.query.FormPageQuery;
import com.glaf.form.core.query.FormRuleQuery;
import com.glaf.form.core.query.FormTaskmainQuery;
import com.glaf.form.core.query.FormWorkFlowRuleQuery;
import com.glaf.form.core.query.FormWorkflowPlanQuery;
import com.glaf.form.core.service.FormComponentPropertyService;
import com.glaf.form.core.service.FormComponentService;
import com.glaf.form.core.service.FormPageService;
import com.glaf.form.core.service.FormRulePropertyService;
import com.glaf.form.core.service.FormRuleService;
import com.glaf.form.core.service.FormTaskService;
import com.glaf.form.core.service.FormTaskmainService;
import com.glaf.form.core.service.FormVideoService;
import com.glaf.form.core.service.FormWorkFlowRuleService;
import com.glaf.form.core.service.FormWorkflowPlanService;
import com.glaf.form.core.service.WdatasetSqlliteService;
import com.glaf.form.core.util.FormDictoryFactory;
import com.glaf.form.core.util.MutilDatabaseBean;
import com.glaf.form.rule.Global;
import com.glaf.isdp.config.RConstant;
import com.glaf.isdp.domain.CellDataTable;
import com.glaf.isdp.domain.RdataTable;
import com.glaf.isdp.service.CellDataFieldService;
import com.glaf.isdp.service.CellDataTableService;
import com.glaf.isdp.service.RdataTableService;
import com.glaf.theme.domain.SysThemeTmp;
import com.glaf.theme.query.SysThemeTmpQuery;
import com.glaf.theme.service.SysThemeTmpService;
import com.glaf.util.RefreshDBMetadata;

/**
 * 
 * SpringMVC控制器
 * 
 */

@Controller("/form/defined")
@RequestMapping("/form/defined")
public class FormDefinedController {
	protected static final Log logger = LogFactory.getLog(FormDefinedController.class);
	protected FormPageService formPageService;
	protected FormRuleService formRuleService;
	protected FormComponentService formComponentService;
	protected FormComponentPropertyService formComponentPropertyService;
	protected FormRulePropertyService formRulePropertyService;
	protected IDatabaseService databaseService;
	protected DataSetService dataSetService;
	protected DataSetAuditService dataSetAuditService;
	protected CellDataTableService cellDataTableService;
	@Autowired
	protected CellDataFieldService cellDataFieldService;

	protected MutilDatabaseBean mutilDatabaseBean;

	protected FormVideoService formVideoService;

	protected DepReportCellService depReportCellService;
	protected FormWorkflowPlanService formWorkflowPlanService;
	@Autowired
	protected FormWorkFlowRuleService formWorkFlowRuleService;

	protected FormTaskmainService formTaskmainService;

	protected FormTaskService formTaskService;

	@Autowired
	protected DepReportTemplateService depReportTemplateService;

	@Autowired
	protected RdataTableService rdataTableService;

	@Autowired
	protected TaskTableService taskTableService;

	@Autowired
	DynamicSqlTreeService dynamicDqlTreeService;
	// 主题管理
	@Autowired
	protected SysThemeTmpService sysThemeTmpService;

	public FormDefinedController() {

	}

	@RequestMapping("param/{category}")
	public ModelAndView definedParam(HttpServletRequest request, @PathVariable String category) {
		return gotoView(request, "param/" + category);
	}

	@RequestMapping("outExp/{category}")
	public ModelAndView definedOutExp(HttpServletRequest request, @PathVariable String category) {
		return gotoView(request, "outExp/" + category);
	}

	@RequestMapping("ex/{category}")
	public ModelAndView definedEx(HttpServletRequest request, @PathVariable String category) {
		return gotoView(request, "ex/" + category);
	}

	@RequestMapping("table/{category}")
	public ModelAndView definedTable(HttpServletRequest request, @PathVariable String category) {
		return gotoView(request, "table/" + category);
	}

	@RequestMapping("workflow/{category}")
	public ModelAndView definedWorkflow(HttpServletRequest request, @PathVariable String category) {
		return gotoView(request, "workflow/" + category);
	}

	@RequestMapping("spreadjs/{category}")
	public ModelAndView definedSpreadjs(HttpServletRequest request, @PathVariable String category) {
		// 默认为工程项目 A000
		request.setAttribute("systemType", RequestUtils.getParameter(request, "type", "A000"));
		return gotoView(request, "spreadjs/" + category);
	}

	@RequestMapping("video/{category}")
	public ModelAndView definedVideo(HttpServletRequest request, @PathVariable String category) {
		return gotoView(request, "video/" + category);
	}

	@RequestMapping("ztree/{category}")
	public ModelAndView definedZtree(HttpServletRequest request, @PathVariable String category) {
		return gotoView(request, "ztree/" + category);
	}

	@RequestMapping("charts/{category}")
	public ModelAndView definedCharts(HttpServletRequest request, @PathVariable String category) {
		return gotoView(request, "charts/" + category);
	}

	@RequestMapping("{view}")
	private ModelAndView gotoView(HttpServletRequest request, @PathVariable String view) {
		RequestUtils.setRequestParameterToAttribute(request);
		if ("jsgis".equals(view)) {
			String url = SystemConfig.getString("gisServiceUrl");
			String isDynamic = "tiled";
			try {
				String response = HttpClientUtils.doGet(url);
				if (StringUtils.isNotEmpty(response)) {
					//// System.out.println(response);
					Document doc = Jsoup.parse(response);
					Elements eles = doc.select("b:contains(Min Scale:)");
					if (eles.size() > 0) {
						Node node = null;
						for (Element element : eles) {
							node = element.nextSibling();
							if ("0".equals(node.toString().trim())) {
								isDynamic = "dynamic";
							}
						}
					}
				}
			} catch (Exception e) {

			}
			request.setAttribute("isDynamic", isDynamic);
		}

		LoginContext loginContext = RequestUtils.getLoginContext(request);

		// 如果未登录跳转到登录页面
		if (loginContext == null) {
			ServletRequestAttributes sra = (ServletRequestAttributes) //
			RequestContextHolder.getRequestAttributes();
			try {
				request.getRequestDispatcher("/unauthorized.jsp").forward(request, sra.getResponse());
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		return new ModelAndView("/form/defined/" + view);
	}

	@ResponseBody
	@RequestMapping("/checkDynamicMap")
	public byte[] checkDynamicMap(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String gisUrl = RequestUtils.getString(request, "gisUrl", null);
		String isDynamic = "tiled";
		if (gisUrl != null) {
			try {
				String responseStr = HttpClientUtils.doGet(gisUrl);
				if (StringUtils.isNotEmpty(responseStr)) {
					//// System.out.println(responseStr);
					Document doc = Jsoup.parse(responseStr);
					Elements eles = doc.select("b:contains(Min Scale:)");
					if (eles.size() > 0) {
						Node node = null;
						for (Element element : eles) {
							node = element.nextSibling();
							if ("0".equals(node.toString().trim())) {
								isDynamic = "dynamic";
							}
						}
					}
				}
			} catch (Exception e) {

			}
		}
		JSONObject result = new JSONObject();
		result.put("isDynamic", isDynamic);
		return result.toJSONString().getBytes("UTF-8");
	}

	@ResponseBody
	@RequestMapping("/getVideoSetById")
	public byte[] getVideoSetById(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Long videoId = RequestUtils.getLong(request, "id");
		JSONObject video = this.getVideoSet(videoId);
		if (video != null) {
			return video.toJSONString().getBytes("UTF-8");
		} else {
			video = new JSONObject();
			/*
			 * video.put("szDevIp", "140.224.94.94"); video.put("szDevPort", "8002");
			 * video.put("szDevUser", "admin"); video.put("szDevPwd", "qazwsx12345");
			 */ }
		video = new JSONObject();
		video.put("szDevIp", "218.106.148.186");
		video.put("szDevPort", "8000");
		video.put("szDevUser", "admin");
		video.put("szDevPwd", "monkey0818");
		return video.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 获取摄像头 配置
	 * 
	 * @param videoId
	 * @return
	 */
	private JSONObject getVideoSet(Long videoId) {
		if (videoId != null) {
			FormVideo video = this.formVideoService.getFormVideo(videoId);
			if (video != null && video.getStatus() == 0) {
				JSONObject v = new JSONObject();
				v.put("szDevIp", video.getIp());
				v.put("szDevPort", video.getPort());
				v.put("szDevUser", video.getUserName());
				v.put("szDevPwd", video.getPwd());
				return v;
			}
		}
		return null;
	}

	/**
	 * 下载模板
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	@ResponseBody
	@RequestMapping("/download")
	public byte[] download(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String pageId = RequestUtils.getString(request, "pageId", "");

		FormPage fp = null;// formPageService.getFormPage(pageId);

		FormPageQuery query = new FormPageQuery();
		List<String> ids = new ArrayList<String>();
		ids.add(pageId);
		query.setIds(ids);
		List<FormPage> list = formPageService.list(query);
		if (CollectionUtils.isNotEmpty(list)) {
			fp = list.get(0);
		}

		if (fp != null && fp.getFormHtml() != null) {
			ResponseUtils.download(request, response, fp.getFormHtml().getBytes("UTF-8"), fp.getName() + ".html");
		}
		return null;
	}

	@ResponseBody
	@RequestMapping("/getColumns")
	public byte[] getColumns(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String tableNames = RequestUtils.getString(request, "tableNames", "");

		Map<String, ColumnDefinition> tableMap = new HashMap<String, ColumnDefinition>();

		if (tableNames.trim().length() > 0) {
			for (String tableName : tableNames.split(",")) {
				List<ColumnDefinition> columns = TableFactory.getColumnDefinitions(tableName);
				for (ColumnDefinition cdf : columns) {
					if (!tableMap.containsKey(cdf.getColumnName()))
						tableMap.put(cdf.getColumnName(), cdf);
				}
			}
		}
		return JSON.toJSONString(tableMap.values()).getBytes("UTF-8");
	}

	// 根据pageId 控件id 获取数据源
	@ResponseBody
	@RequestMapping("/getDataSourceSet")
	public byte[] getDataSourceSet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String pageId = RequestUtils.getString(request, "pageId");
		String name = RequestUtils.getString(request, "name");

		FormRuleQuery query = new FormRuleQuery();
		query.setPageId(pageId);
		query.setName(name);
		JSONArray array = new JSONArray();
		List<FormRule> list = formRuleService.list(query);
		if (list != null && list.size() > 0) {
			FormRule fr = list.get(0);
			Map<String, String> map = this.getRuleMap(fr.getId());
			if (map != null) {
				String str;// dataSourceService
				if (StringUtils.isNotBlank(str = MapUtils.getString(map, "dataSourceSet"))) {
					array = JSONArray.parseArray(str);
				} else if (StringUtils.isNotBlank(str = MapUtils.getString(map, "dataSourceService"))) {
					array = JSONArray.parseArray(str);
				}
			}

			if (map != null && map.containsKey("dataSourceSet")) {
				array = JSONArray.parseArray(map.get("dataSourceSet"));
			}
		}

		return JSON.toJSONString(array).getBytes("UTF-8");
	}

	@ResponseBody
	@RequestMapping("/getDictByCode")
	public byte[] getDictByCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String code = RequestUtils.getString(request, "code");
		Object object = null;
		if (StringUtils.isNotEmpty(code)) {
			JSONArray array = new JSONArray();
			// SysTree tree =
			// BaseDataManager.getInstance().getSysTreeService().getSysTreeByCode(code);
			FormDictTree tree = FormDictoryFactory.getInstance().getFormDictTreeService().getFormDictTreeByCode(code);
			if (tree != null) {
				int index = 0;

				/*
				 * List<Dictory> list = BaseDataManager.getInstance() .getDictoryService()
				 * .getAvailableDictoryList(tree.getId());
				 * 
				 * ok
				 * 
				 * for (Dictory item : list) { index++; JSONObject json = item.toJsonObject();
				 * json.put("index", index); json.put("listno", index); array.add(json); }
				 */

				List<FormDictory> dicts = FormDictoryFactory.getInstance().getFormDictoryService()
						.getAvailableDictoryList(tree.getId());

				for (FormDictory item : dicts) {
					index++;
					JSONObject json = item.toJsonObject();
					json.put("index", index);
					json.put("listno", index);
					array.add(json);
				}
			}
			object = array;

		} else {

			/*
			 * SysTreeQuery query = new SysTreeQuery(); query.setLocked(0);
			 * 
			 * ok
			 * 
			 * List<SysTree> trees = BaseDataManager.getInstance()
			 * .getSysTreeService().getDictorySysTrees(query); //System.out.println(trees);
			 */

			FormDictTreeQuery query0 = new FormDictTreeQuery();
			query0.setLocked(0);
			List<FormDictTree> trees = FormDictoryFactory.getInstance().getFormDictTreeService()
					.getDictoryFormDictTrees(query0);

			List<JSONObject> list = new ArrayList<JSONObject>();
			JSONObject json;
			for (FormDictTree tree : trees) {
				json = tree.toJsonObject();
				json.put("text", tree.getName());
				json.put("value", tree.getCode());
				// json.put("id", tree.getCode());
				list.add(json);
			}
			object = list;
		}

		return JSON.toJSONString(object).getBytes("UTF-8");
	}

	@ResponseBody
	@RequestMapping("/getDictByCodeOld")
	public byte[] getDictByCodeOld(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String code = RequestUtils.getString(request, "code");
		Object object = null;
		if (StringUtils.isNotEmpty(code)) {
			JSONArray array = new JSONArray();
			SysTree tree = BaseDataManager.getInstance().getSysTreeService().getSysTreeByCode(code);
			if (tree != null) {
				int index = 0;

				List<Dictory> list = BaseDataManager.getInstance().getDictoryService()
						.getAvailableDictoryList(tree.getId());
				for (Dictory item : list) {
					index++;
					JSONObject json = item.toJsonObject();
					json.put("index", index);
					json.put("listno", index);
					array.add(json);
				}

				/*
				 * List<FormDictory> dicts = FormDictoryFactory.getInstance()
				 * .getFormDictoryService() .getAvailableDictoryList(tree.getId());
				 * 
				 * for (FormDictory item : dicts) { index++; JSONObject json =
				 * item.toJsonObject(); json.put("index", index); json.put("listno", index);
				 * array.add(json); }
				 */
			}
			object = array;

		} else {

			SysTreeQuery query = new SysTreeQuery();
			query.setLocked(0);
			List<SysTree> trees = BaseDataManager.getInstance().getSysTreeService().getDictorySysTrees(query);
			// //System.out.println(trees);

			// FormDictTreeQuery query0 = new FormDictTreeQuery();
			// query0.setLocked(0);
			// List<FormDictTree> trees = FormDictoryFactory.getInstance()
			// .getFormDictTreeService().getDictoryFormDictTrees(query0);

			List<JSONObject> list = new ArrayList<JSONObject>();
			JSONObject json;
			for (SysTree tree : trees) {
				json = tree.toJsonObject();
				json.put("text", tree.getName());
				json.put("value", tree.getCode());
				// json.put("id", tree.getCode());
				list.add(json);
			}
			object = list;
		}

		return JSON.toJSONString(object).getBytes("UTF-8");
	}

	/**
	 * (枚举值) 根据父节点id 获取字典数据
	 * 
	 * @param parentId
	 * @return
	 */
	public Object getDictsByPid(String parentId) {

		JSONObject obj;
		List<JSONObject> list = new ArrayList<JSONObject>();
		if (StringUtils.isNotEmpty(parentId)) {

			List<FormDictory> dicts = FormDictoryFactory.getInstance().getFormDictoryService()
					.getAvailableDictoryList(Long.parseLong(parentId));

			/*
			 * ok List<Dictory> dicts = BaseDataManager.getInstance().getDictoryService()
			 * .getAvailableDictoryList(Long.parseLong(parentId));
			 */

			if (dicts != null) {
				for (FormDictory dict : dicts) {
					obj = dict.toJsonObject();
					obj.put("text", dict.getName());
					obj.put("value", dict.getCode());
					obj.put("id", dict.getCode());
					list.add(obj);
				}
			}
			return list;
		}

		return getText("text");
	}

	/**
	 * 获取控件属性
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/getFormCompoentProperties")
	public byte[] getFormCompoentProperties(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		// //System.out.println(request.getPathInfo());
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String id = RequestUtils.getString(request, "id");
		String dataRole = RequestUtils.getString(request, "dataRole");
		String pageId = RequestUtils.getString(request, "pageId");
		String formRuleId = null;
		FormComponentQuery query = new FormComponentQuery();

		// 转换 各种栅格统一读取 col-md-12 栅格属性
		dataRole = convertRole(dataRole);

		query.setDataRole(dataRole);
		List<FormComponent> fcList = formComponentService.list(query);
		List<JSONObject> jsonList = new ArrayList<JSONObject>();
		JSONObject ret = new JSONObject();
		ret.put("rows", new ArrayList<Object>());
		ret.put("total", 0);
		if (fcList != null && fcList.size() > 0) {
			FormComponent fc = fcList.get(0);
			if (fc != null) {
				FormComponentPropertyQuery fcpQuery = new FormComponentPropertyQuery();
				fcpQuery.setLocked(0);
				fcpQuery.setDeleteFlag(0);
				fcpQuery.setComponentId(fc.getId());
				List<FormComponentProperty> fpcList = formComponentPropertyService.list(fcpQuery);
				if (fpcList != null) {
					FormRuleQuery frQuery = new FormRuleQuery();
					frQuery.setName(id);
					frQuery.setPageId(pageId);
					List<FormRule> frList = formRuleService.list(frQuery);
					JSONObject frJson = null;
					if (frList != null && frList.size() > 0) {
						FormRule fr = frList.get(0);
						//// System.out.println(fr);
						if (fr != null && StringUtils.isNotEmpty(fr.getValue())) {
							formRuleId = fr.getId();
							/*
							 * Map<String, String> ruleMap = getRuleMap(formRuleId); if
							 * (ruleMap.get("buttonType") != null) {
							 * query.setDataRole(ruleMap.get("buttonType")); fcList =
							 * formComponentService.list(query); if (fcList != null) { FormComponent fc0 =
							 * fcList.get(0); fcpQuery.setComponentId(fc0.getId());
							 * List<FormComponentProperty> c = formComponentPropertyService .list(fcpQuery);
							 * fpcList.addAll(c); } }
							 */
							frJson = JSON.parseObject(fr.getValue());
							ret.put("value", frJson);
						}
					}
					JSONObject job;
					Map<String, FormDictory> bdiMap = new HashMap<String, FormDictory>();

					// Map<String, BaseDataInfo> bdiMap = new HashMap<String,
					// BaseDataInfo>();
					/*
					 * List<BaseDataInfo> bdis = BaseDataManager.getInstance()
					 * .getBaseData("form_editor");
					 * 
					 * ok
					 * 
					 * if (bdis != null) { for (BaseDataInfo bdi : bdis) { bdiMap.put(bdi.getId() +
					 * "", bdi); } }
					 */
					List<FormDictory> fds = FormDictoryFactory.getInstance()
							.getFormDictoryListByTreeCode("form_editor");
					if (fds != null) {
						for (FormDictory fd : fds) {
							bdiMap.put(fd.getId() + "", fd);
						}
					}
					String editor, val;
					for (FormComponentProperty fcp : fpcList) {
						editor = "";
						// fcp.setName(fcp.getTitle());
						job = fcp.toJsonObject();
						ret.put("componentId", fc.getId());
						ret.put("formRuleId", formRuleId);
						if (frJson != null) {
							val = frJson.getString(fcp.getId() + "");
							val = StringUtils.isBlank(val) ? fcp.getDefValue() : val;
							job.put("value", val);
						} else {
							job.put("value", fcp.getDefValue());
						}
						job.put("value_", fcp.getValue());
						job.put("group", fcp.getType());

						if (StringUtils.isNotEmpty(fcp.getEditor())) {
							// BaseDataInfo bdi = bdiMap.get(fcp.getEditor());
							FormDictory bdi = bdiMap.get(fcp.getEditor());
							if (bdi != null)
								editor = bdi.getCode();
							if (StringUtils.isNotEmpty(editor)) {
								editor = editor.toLowerCase();
								switch (editor) {
								case "select":
									job.put("editor", this.getSelect(editor, fcp, pageId));
									break;
								case "multiselect":
									job.put("editor", this.getMultiSelect(editor, fcp));
									break;
								case "checkbox":
									job.put("editor", this.getChecBox(editor));
									break;
								case "dialog":
									job.put("editor", this.getDialog(editor, fcp.getValue()));
									break;
								default:
									job.put("editor", this.getText(editor));
								}
							}
						} else
							job.put("editor", this.getText("text"));

						jsonList.add(job);
					}
					ret.put("rows", jsonList);
					ret.put("total", jsonList.size());
				}
			}
		}
		return ret.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 根据pageId 获取对应htmls
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/getFormPageHtmlById")
	public byte[] getFormPageHtmlById(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		FormPage formPage = formPageService.getFormPage(id);
		if (formPage != null) {
			return formPage.getFormHtml().getBytes("UTF-8");
		}
		return null;
	}

	/**
	 * 获取 formPage 树
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/getFormPageTree")
	@ResponseBody
	public byte[] getFormPageTree(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
		String systemName = Environment.getCurrentSystemName();
		Long databaseId = RequestUtils.getLong(request, "databaseId");
		Integer pageCategory = RequestUtils.getInteger(request, "pageCategory", 1);
		String type = RequestUtils.getString(request, "type", null);
		boolean isShowLocked = RequestUtils.getBoolean(request, "locked");
		String showLocked = " WHERE LOCKED_ = 0  and DELETEFLAG_ = 0 ";
		if (isShowLocked) {
			showLocked = "";
		}
		String pageCategorySql = " PAGECATEGORY_ = " + pageCategory.toString();
		if ("".equals(showLocked)) {
			pageCategorySql = " WHERE " + pageCategorySql;
		} else {
			pageCategorySql = " AND " + pageCategorySql;
		}

		if (StringUtils.isNotEmpty(type)) {
			pageCategorySql += " AND TYPE_ = 1 ";
		}
		String sql = " select ID_ as id, PARENTID_ as parentId, NAME_ as name, NAME_ as text, SORTNO_ as sort,LOCKED_ as locked,FORMTYPE_ as formType,UITYPE_ as uiType,TYPE_ as type from  FORM_PAGE "
				+ showLocked + pageCategorySql + " order by SORTNO_ asc ";
		JSONArray array = new JSONArray();
		QueryHelper helper = new QueryHelper();
		try {
			if (databaseId != null && databaseId > 0) {
				Database database = databaseService.getDatabaseById(databaseId);
				if (database != null) {
					Environment.setCurrentSystemName(database.getName());
				}
			}
			List<Map<String, Object>> resultList = helper.getResultList(Environment.getCurrentSystemName(), sql,
					paramMap);
			if (resultList != null && !resultList.isEmpty()) {
				List<FormPage> treeList = new ArrayList<FormPage>();
				for (Map<String, Object> dataMap : resultList) {
					FormPage tree = new FormPage();
					tree.setId(ParamUtils.getString(dataMap, "id"));
					tree.setTitle(ParamUtils.getString(dataMap, "name"));
					tree.setName(ParamUtils.getString(dataMap, "name"));
					String parentId = ParamUtils.getString(dataMap, "parentId");
					tree.setLocked(ParamUtils.getInt(dataMap, "locked"));
					tree.setFormType(ParamUtils.getString(dataMap, "formType"));
					tree.setParentId(parentId);
					tree.setUiType(ParamUtils.getString(dataMap, "uiType"));
					tree.setType(ParamUtils.getString(dataMap, "type"));
					treeList.add(tree);
				}
				String treesJSONStr = JSON.toJSONString(treeList);
				array = JSON.parseArray(treesJSONStr);
				logger.debug("trees->" + treeList.size());
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		} finally {
			Environment.setCurrentSystemName(systemName);
		}
		return array.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 获取 formPage 树
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/getFormPageTree2")
	public byte[] getFormPageTree2(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		FormPageQuery query = new FormPageQuery();
		// query.setNodeParentId(parentId);
		List<FormPage> list = formPageService.getFormPageTree(query);
		if (list != null) {
			JSONObject json;
			List<JSONObject> array = new ArrayList<JSONObject>();
			for (FormPage page : list) {
				json = page.toJsonObject();
				if (page.getParentId() != null && page.getParentId().equalsIgnoreCase("-1")) {
					json.put("isParent", true);
				}
				array.add(json);
			}
			return JSON.toJSONString(array).getBytes("UTF-8");
		}
		return null;
	}

	@ResponseBody
	@RequestMapping("/getFormRule")
	public byte[] getFormRule(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String name = RequestUtils.getString(request, "name", "");
		String pageId = RequestUtils.getString(request, "pageId");

		List<Map<String, String>> retList = this.getFormRuleMap(pageId, name.split(","));

		return JSON.toJSONString(retList).getBytes("UTF-8");
	}

	@ResponseBody
	@RequestMapping("/getFormRuleById")
	public byte[] getFormRuleById(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String rid = RequestUtils.getString(request, "rid", "");
		return JSON.toJSONString(this.getRuleMap(rid)).getBytes("UTF-8");
	}

	private List<Map<String, String>> getFormRuleMap(String pageId, String[] names) {
		FormRuleQuery query = new FormRuleQuery();
		query.setPageId(pageId);
		List<FormRule> list = formRuleService.list(query);
		if (list != null && !list.isEmpty()) {
			List<Map<String, String>> retList = new ArrayList<Map<String, String>>();
			Map<String, FormRule> frMap = new HashMap<String, FormRule>();
			for (FormRule fr : list) {
				frMap.put(fr.getName(), fr);
			}
			if (names != null) {
				for (String key : names) {
					if (frMap.get(key) != null) {
						String id = frMap.get(key).getId();
						Map<String, String> ruleMap = this.getRuleMap(id);
						ruleMap.put("ruleId", id);
						retList.add(ruleMap);
					}
				}
			}
			return retList;
		}
		return null;
	}

	private Object getDialog(String type, String value) {
		JSONObject obj = new JSONObject();
		obj.put("type", type);
		obj.put("data", value);
		return obj;
	}

	private Object getChecBox(String type) {
		JSONObject obj = new JSONObject();
		obj.put("type", type);
		return obj;
	}

	private Object getMultiSelect(String type, FormComponentProperty fcp) {
		JSONObject o = new JSONObject();
		o.put("type", type);
		o.put("data", fcp.getValue());
		JSONObject o1 = new JSONObject();

		o1.put("data", this.getDictsByPid(fcp.getEnumValue()));
		o1.put("valueField", "value");
		o1.put("textField", "text");
		o.put("options", o1);
		return o;
	}

	private Object getText(String type) {
		JSONObject obj = new JSONObject();
		obj.put("type", type);
		return obj;
	}

	@ResponseBody
	@RequestMapping("/getParametersByPageId")
	public byte[] getParametersByPageId(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String pageId = RequestUtils.getString(request, "pageId");

		List<Map<String, Object>> list = formRulePropertyService.getParametersByPageId(pageId);

		if (list != null) {
			return JSON.toJSONString(list).getBytes("UTF-8");
		}

		return null;
	}

	/**
	 * 获取下发数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/getParametersByAttrId")
	public byte[] getParametersByAttrId(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String pageId = RequestUtils.getString(request, "pageId");
		String attrId = RequestUtils.getString(request, "attrId", "25338");
		List<FormRuleProperty> list = formRulePropertyService.getAttributeDatasByPageId(pageId, attrId);
		String retValue = "[]";
		if (list != null && !list.isEmpty()) {
			retValue = list.get(0).getValue();
		}
		return retValue.getBytes("UTF-8");
	}

	@ResponseBody
	@RequestMapping("/getCellParams")
	public byte[] getCellParams(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String pageId = RequestUtils.getString(request, "pageId");
		long cellId = RequestUtils.getLong(request, "cellId");
		String eleId = RequestUtils.getString(request, "eleId");

		JSONObject retAry = new JSONObject();

		DepReportTemplate depReportTemplate = depReportTemplateService.getDepReportTemplate(cellId);
		String ruleJson = depReportTemplate.getRuleJson();
		if (ruleJson != null && !ruleJson.isEmpty()) {
			JSONObject ruleObj = JSON.parseObject(ruleJson);
			Set<String> keys = ruleObj.keySet();
			String inParamCode = "A001-6-001";
			String inParamRuleId = "5d5ae66bc360498ca9744a156a1386e9";
			String pageInParamCode = "A000-1-1-003";
			String pageInParamRuleId = "72916ca7b6a74e089818e69c08c3781f";
			String nameCode = "A001-1-002";
			String roleCode = "A001-1-001";
			JSONObject rlObj;
			String inParamStr = null;
			int count = 0;
			JSONArray inParamsAry;
			JSONObject inParamObj;
			JSONObject datasObj;
			JSONArray valAry;

			for (String key : keys) {
				rlObj = ruleObj.getJSONObject(key);
				inParamStr = rlObj.getString("page".equalsIgnoreCase(key) ? pageInParamCode : inParamCode);
				if (StringUtils.isNotEmpty(inParamStr)) {
					inParamsAry = JSON.parseArray(inParamStr);
					if (inParamsAry != null && !inParamsAry.isEmpty()) {
						JSONObject retObj = new JSONObject();
						inParamObj = inParamsAry.getJSONObject(0);
						datasObj = inParamObj.getJSONObject("datas");
						valAry = datasObj.getJSONArray("page".equalsIgnoreCase(key) ? pageInParamRuleId : inParamRuleId)
								.getJSONObject(0).getJSONArray("val");
						retObj.put("NAME_", "page".equalsIgnoreCase(key) ? eleId : key);
						retObj.put("VALUE_", valAry.toJSONString());
						retObj.put("TITLE_", "page".equalsIgnoreCase(key) ? "数据集参数" : rlObj.getString(nameCode));
						retObj.put("PAGEID_", pageId);
						retObj.put("PARAMTYPE_", "inParamDefined");
						retObj.put("text", "page".equalsIgnoreCase(key) ? "数据集参数" : rlObj.getString(nameCode));
						JSONArray itemsAry = new JSONArray();
						JSONObject valObj = null;
						JSONObject itemsObj = null;
						for (Object object : valAry) {
							itemsObj = new JSONObject();
							valObj = (JSONObject) object;
							itemsObj.put("name", valObj.getString("name"));
							itemsObj.put("param", valObj.getString("param"));
							itemsObj.put("text", valObj.getString("name"));
							itemsObj.put("id", "page".equalsIgnoreCase(key) ? eleId : key);
							itemsObj.put("title", valObj.getString("name"));

							itemsAry.add(itemsObj);
						}
						if ("page".equalsIgnoreCase(key)) {
							itemsObj = new JSONObject();
							itemsObj.put("name", "标段");
							itemsObj.put("param", "dataSetId");
							itemsObj.put("text", "标段");
							itemsObj.put("id", eleId);
							itemsObj.put("title", "标段");
							itemsAry.add(itemsObj);
						}
						retObj.put("items", itemsAry);

						retAry.put("page".equalsIgnoreCase(key) ? eleId : key, retObj);
					}
				}
			}
		}
		return retAry.toJSONString().getBytes("UTF-8");
	}

	@ResponseBody
	@RequestMapping("/getOtypeParams")
	public byte[] getOtypeParams(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String pageId = RequestUtils.getString(request, "pageId");
		String eleId = RequestUtils.getString(request, "eleId");
		String role = RequestUtils.getString(request, "role");

		FormRuleProperty rp = formRulePropertyService.getPageComponentPropertyRule(pageId, role, "dataSourceSet",
				eleId);

		JSONObject ret = new JSONObject();

		if (rp != null) {
			String ruleValue = rp.getValue();
			Integer id = null;
			JSONArray columnsAry = null;
			if (ruleValue != null && !ruleValue.isEmpty()) {
				JSONArray rAry = JSON.parseArray(ruleValue);
				JSONObject rObj = rAry.getJSONObject(0);
				columnsAry = rObj.getJSONArray("columns");
				if (columnsAry != null && !columnsAry.isEmpty()) {
					JSONObject columnObj = null;
					JSONObject retObj = new JSONObject();
					for (Object object : columnsAry) {
						columnObj = (JSONObject) object;
						retObj = new JSONObject();
						String columnName = columnObj.getString("columnName");
						String name = columnObj.getString("title");
						retObj.put("NAME_", name);
						retObj.put("VALUE_", columnName);
						retObj.put("TITLE_", name);
						retObj.put("PAGEID_", pageId);
						retObj.put("PARAMTYPE_", "inParamDefined");
						retObj.put("text", name);
						String paramsStr = columnObj.getString("params");
						if (StringUtils.isNotEmpty(paramsStr)) {
							JSONArray paramsAry = JSON.parseArray(paramsStr);
							JSONArray itemsAry = new JSONArray();
							JSONObject paramObj = null;
							JSONObject itemsObj = null;
							for (Object obj : paramsAry) {
								paramObj = (JSONObject) obj;
								if (paramObj.getString("type") != null) {
									JSONArray sourceAry = paramObj.getJSONArray("source");
									JSONObject sourceObj = null;
									for (Object object2 : sourceAry) {
										sourceObj = (JSONObject) object2;
										itemsObj = new JSONObject();
										itemsObj.put("name", sourceObj.getString("name"));
										itemsObj.put("param", sourceObj.getString("param"));
										itemsObj.put("text", sourceObj.getString("name"));
										itemsObj.put("id", columnName);
										itemsObj.put("title", sourceObj.getString("name"));
										itemsAry.add(itemsObj);
									}
								} else {
									itemsObj = new JSONObject();
									itemsObj.put("name", paramObj.getString("name"));
									itemsObj.put("param", paramObj.getString("param"));
									itemsObj.put("text", paramObj.getString("name"));
									itemsObj.put("id", columnName);
									itemsObj.put("title", paramObj.getString("name"));
									itemsAry.add(itemsObj);
								}
							}
							retObj.put("items", itemsAry);
							ret.put(columnName, retObj);
						}
					}
				}
			}
		}
		return ret.toJSONString().getBytes("UTF-8");
	}

	protected Map<String, String> getRuleMap(String rid) {

		return this.formRulePropertyService.getRuleMap(rid);

	}

	private Object getSelect(String type, FormComponentProperty fcp, String pageId) {
		JSONObject o = new JSONObject();
		o.put("type", type);
		JSONObject o1 = new JSONObject();

		Object obj = null;
		if (StringUtils.isNotEmpty(fcp.getEnumValue())) {
			obj = getDictsByPid(fcp.getEnumValue());
		} /*
			 * else if (fcp.getValue() != null &&
			 * fcp.getValue().equalsIgnoreCase("parameterIn")) { List<Map<String, String>>
			 * retList = this.getFormRuleMap(pageId, pageId.split(",")); if (retList != null
			 * && !retList.isEmpty()) { Map<String, String> map = retList.get(0); if (map !=
			 * null) { String pageParametersStr = map.get("pageParameters"); if
			 * (pageParametersStr != null) { obj = JSON.parseArray(pageParametersStr); } } }
			 * }
			 */else {

			SysTreeQuery query = new SysTreeQuery();
			query.setLocked(0);
			List<SysTree> trees = BaseDataManager.getInstance().getSysTreeService().getDictorySysTrees(query);

			List<JSONObject> list = new ArrayList<JSONObject>();
			JSONObject json;
			for (SysTree tree : trees) {
				json = tree.toJsonObject();
				json.put("text", tree.getName());
				json.put("value", tree.getCode());
				json.put("id", tree.getCode());
				list.add(json);
			}
			obj = list;
		}

		o1.put("data", obj);
		o1.put("valueField", "value");
		o1.put("textField", "text");
		o.put("options", o1);
		return o;
	}

	@ResponseBody
	@RequestMapping("/getStuffByPageId")
	public byte[] getStuffByPageId(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String pageId = RequestUtils.getString(request, "pageId");

		List<Map<String, Object>> list = formRulePropertyService.getStuffByPageId(pageId);

		if (list != null) {
			return JSON.toJSONString(list).getBytes("UTF-8");
		}

		return null;
	}

	@RequestMapping
	public ModelAndView list(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String x_query = request.getParameter("x_query");
		if (StringUtils.equals(x_query, "true")) {
			Map<String, Object> paramMap = RequestUtils.getParameterMap(request);
			String x_complex_query = JsonUtils.encode(paramMap);
			x_complex_query = RequestUtils.encodeString(x_complex_query);
			request.setAttribute("x_complex_query", x_complex_query);
		} else {
			request.setAttribute("x_complex_query", "");
		}

		String requestURI = request.getRequestURI();
		if (request.getQueryString() != null) {
			request.setAttribute("fromUrl", RequestUtils.encodeURL(requestURI + "?" + request.getQueryString()));
		} else {
			request.setAttribute("fromUrl", RequestUtils.encodeURL(requestURI));
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/form/defined/list", modelMap);
	}

	/**
	 * 导入
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/saveFormPage")
	public byte[] saveFormPage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		User user = RequestUtils.getUser(request);
		String parentId = RequestUtils.getString(request, "id");
		String type = RequestUtils.getString(request, "type");
		String formType = RequestUtils.getString(request, "formType");
		String name = RequestUtils.getString(request, "name");
		String uiType = RequestUtils.getString(request, "uiType");
		String stype = RequestUtils.getString(request, "sortType", null);
		Integer pageCategory = RequestUtils.getInteger(request, "pageCategory", 1);

		String actorId = user.getActorId();

		// 将当前上下文初始化给 CommonsMutipartResolver（多部分解析器）
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest req = (MultipartHttpServletRequest) request;
			Map<String, MultipartFile> fileMap = req.getFileMap();
			Set<Entry<String, MultipartFile>> entrySet = fileMap.entrySet();
			Map<String, Object> params = RequestUtils.getParameterMap(req);
			params.remove("status");
			params.remove("wfStatus");

			FormPage formPage;

			for (Entry<String, MultipartFile> entry : entrySet) {

				MultipartFile mFile = entry.getValue();

				formPage = new FormPage();

				formPage.setName(mFile.getOriginalFilename()
						.replace("." + FileUtils.getFileExt(mFile.getOriginalFilename()), ""));
				String formHtml = IOUtils.toString(mFile.getInputStream(), "UTF-8");
				formPage.setFormHtml(formHtml);
				try {
					formPage.setFormType(formType);
					if (StringUtils.isNotBlank(type) && type.equalsIgnoreCase("update")) {
						formPage.setId(parentId);
					} else {
						formPage.setUiType("kendo");
						formPage.setParentId(parentId);
						formPage.setCreateBy(actorId);
						formPage.setCreateDate(new Date());
						formPage.setType(stype);
						formPage.setPageCategory(pageCategory);
					}
					formPageService.save(formPage);
				} catch (Exception ex) {
					ex.printStackTrace();
					return ResponseUtils.responseJsonResult(false);
				}
			}
		} else {
			// 查询出默认主题，为页面加上默认主题
			SysThemeTmpQuery sysThemeTmpQuery = new SysThemeTmpQuery();
			sysThemeTmpQuery.setDefaultFlag(1);
			sysThemeTmpQuery.setUi(uiType);
			String themeId = "";
			List<SysThemeTmp> sysThemeTmpList = this.sysThemeTmpService.list(sysThemeTmpQuery);
			if (sysThemeTmpList != null && sysThemeTmpList.size() > 0) {
				SysThemeTmp sysThemeTmp = sysThemeTmpList.get(0);
				themeId = sysThemeTmp.getThemeTmpId();
			}

			FormPage formPage = new FormPage();
			try {
				formPage.setName(name);
				formPage.setFormHtml(name);
				formPage.setFormType(formType);
				formPage.setParentId(parentId);
				formPage.setCreateBy(actorId);
				formPage.setCreateDate(new Date());
				formPage.setUiType(uiType);
				formPage.setPageCategory(pageCategory);
				formPage.setType(stype);
				formPage.setThemeTmpId(themeId); // 加上默认主题
				formPageService.save(formPage);
			} catch (Exception ex) {
				ex.printStackTrace();
				return ResponseUtils.responseJsonResult(false);
			}
		}
		return ResponseUtils.responseJsonResult(true);
	}

	/**
	 * 更新预览模式
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/updateUiType")
	public byte[] updateUiType(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// User user = RequestUtils.getUser(request);
		String id = RequestUtils.getString(request, "id");
		String uiType = RequestUtils.getString(request, "uiType");

		FormPage formPage = new FormPage();
		try {
			formPage.setId(id);
			formPage.setUiType(uiType);
			formPageService.save(formPage);
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseUtils.responseJsonResult(false);
		}

		return ResponseUtils.responseJsonResult(true);
	}

	/**
	 * 上传页面
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/uploadFiles")
	public ModelAndView uploadFiles(HttpServletRequest request, ModelMap modelMap) {

		RequestUtils.setRequestParameterToAttribute(request);

		return new ModelAndView("/form/defined/uploadFiles", modelMap);
	}

	/**
	 * 保存配置
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/saveOrUpdateFormRule")
	public byte[] saveOrUpdateFormRule(HttpServletRequest request, HttpServletResponse response,
			@RequestBody Map<String, Object> paramMap) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Map<String, Object> params = paramMap;
		// RequestUtils.getParameterMap(request);
		User user = RequestUtils.getUser(request);

		// platform_defined admin之外的用户 需要配置角色才可以操作
		LoginContext loginContext = RequestUtils.getLoginContext(request);

		if (loginContext == null) {
			return ResponseUtils.responseJsonResult(-999, "Session 掉了, 请刷新登录!");
		}

		Collection<String> roles = loginContext.getRoles();
		if (!loginContext.isSystemAdministrator() && !roles.contains("platform_defined")) {
			return ResponseUtils.responseJsonResult(-999, "没有权限");
		}

		FormRule formRule = new FormRule();
		Tools.populate(formRule, params);
		if (formRule.getId() == null) {
			formRule.setCreateBy(user.getActorId());
			formRule.setCreateDate(new Date());
		} else {
			formRule.setCreateBy(user.getActorId());
			formRule.setCreateDate(new Date());
		}
		try {
			formRuleService.save(formRule);
			formRulePropertyService.isRuleProperties(formRule);
			formRulePropertyService.saveComExt(formRule);
		} catch (Exception e) {
			logger.error(e.getMessage());
			// throw e;
		}
		return formRule.toJsonObject().toString().getBytes("UTF-8");
	}

	/**
	 * 生成更新集
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/createUpdateSource")
	public byte[] createUpdateSource(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// Map<String, Object> params = RequestUtils.getParameterMap(request);
		User user = RequestUtils.getUser(request);

		String tableMsgArr = RequestUtils.getString(request, "tableMsgArr");
		JSONArray jsons = new JSONArray();
		JSONArray columns = null;
		String cell = RequestUtils.getString(request, "cell", "");
		Boolean cover = RequestUtils.getBoolean(request, "cover");
		if (StringUtils.isNotBlank(tableMsgArr)) {
			jsons = JSONArray.parseArray(tableMsgArr);
			if (jsons != null) {
				JSONObject job, table, value = null;
				String pageId = RequestUtils.getString(request, "pageId");

				FormRule fr = null;

				if (StringUtils.isNotBlank(pageId)) {
					FormRuleQuery frQuery = new FormRuleQuery();
					frQuery.setName(pageId);
					frQuery.setPageId(pageId);
					List<FormRule> frList = formRuleService.list(frQuery);
					if (frList != null && frList.size() > 0) {
						fr = frList.get(0);
						if (fr.getValue() != null)
							value = JSON.parseObject(fr.getValue());
					}
				}

				if (fr == null) {
					fr = new FormRule();
					fr.setName(pageId);
					fr.setPageId(pageId);
					fr.setCreateBy(user.getActorId());
					fr.setComponentId((long) 38);
				}

				if (value == null) {
					value = new JSONObject();
				}
				String paraTypeKey = "311";
				String paraType = value.getString(paraTypeKey);
				JSONArray paraTypeArr = null;
				if (StringUtils.isBlank(paraType)) {
					JSONObject paraJson = new JSONObject();
					paraJson.put("name", "输入输出关系");
					paraJson.put("datas", new JSONObject());
					paraTypeArr = new JSONArray();
					paraTypeArr.add(paraJson);
				} else {
					paraTypeArr = JSONArray.parseArray(paraType);
				}
				JSONObject datas = paraTypeArr.getJSONObject(0).getJSONObject("datas");

				String dataSourceKey = "296";
				// String dataSourceStr = value.getString(dataSourceKey);
				String dataSourceStr = null;
				if (StringUtils.isBlank(dataSourceStr)) {
					dataSourceStr = "[]";
				}

				JSONArray dataSources = JSONArray.parseArray(dataSourceStr);

				JSONObject c, sobj;
				SelectSegment segment;
				Set<String> fields = new HashSet<String>();
				fields.add("id");
				fields.add("topid");
				fields.add("treeid");
				Map<String, JSONObject> relation = new HashMap<String, JSONObject>();

				for (int i = 0; i < jsons.size(); i++) {
					job = jsons.getJSONObject(i);
					table = job.getJSONObject("table");
					columns = job.getJSONArray("columns");
					String tableName = table.getString("tableName");
					if (StringUtils.isEmpty(tableName))
						continue;
					Set<String> cs = new HashSet<String>();
					// List<ColumnDefinition> cds =
					// DBUtils.getColumnDefinitions(tableName);

					String systemName = table.getString("systemName");
					if (StringUtils.isBlank(systemName)) {
						systemName = Environment.DEFAULT_SYSTEM_NAME;
					}
					List<ColumnDefinition> cds = DBUtils.getColumnDefinitions(systemName, tableName);
					if (CollectionUtils.isNotEmpty(cds)) {
						for (ColumnDefinition cd : cds) {
							cs.add(cd.getColumnName().toLowerCase());
						}
					}

					table.put("topTableName", job.getString("topTableName"));
					relation.put(tableName, table);

					table.put("user", user.getActorId());

					String dataSetId = job.getString("dataSetId");
					DataSet dataset = null;
					DataSetAudit audit = null;

					if (dataSetId != null) {
						dataset = dataSetService.getDataSet(dataSetId);
					}

					String tnString = table.getString("name") + "[" + tableName + "]" + cell + "系统生成";

					if (dataset == null && cover) {// 覆盖
						DataSetQuery query = new DataSetQuery();
						// query.setName(tnString);
						// query.setNameLike(tableName);
						query.setTitleLike(tnString);
						List<DataSet> dsDataSets = dataSetService.list(query);
						if (dsDataSets != null && dsDataSets.size() > 0) {
							dataset = dsDataSets.get(0);
						}
					}

					if (dataset == null) {
						dataset = new DataSet();
						dataset.setCreateBy(user.getActorId());
						tnString += "|" + new SimpleDateFormat("HH:mm:ss").format(new Date());
						dataset.setName(table.getString("name") + " 系统生成");
						dataset.setTitle(tnString);
						dataset.setDatabaseId(table.getLong("databaseId"));
						dataset.setDescription("系统生成查询数据集");
					} else {
						dataset.setSelectSegments(new ArrayList<SelectSegment>());
						dataset.setFromSegments(new ArrayList<FromSegment>());
						audit = this.dataSetAuditService.getLastestDataSetAudit(dataset.getId());
					}

					if (audit == null) {
						audit = new DataSetAudit();
					}
					JSONObject auditContent;
					if (StringUtils.isNotBlank(audit.getContent())) {
						auditContent = JSON.parseObject(audit.getContent());
					} else {
						auditContent = new JSONObject();
					}

					JSONObject metadata = auditContent.getJSONObject("metadata");// new
																					// JSONObject();
					if (metadata == null) {
						metadata = new JSONObject();
						metadata.put("alias", 0);
						metadata.put("label", 0);
						metadata.put("table", new JSONObject());
						metadata.put("column", new JSONObject());
						auditContent.put("metadata", metadata);
					}
					if (!RConstant.isOracle()) {
						metadata = null;
					}

					/**
					 * 保存单表主键
					 */
					String primaryKeys = table.getString("primaryKeys");
					if (StringUtils.isNotEmpty(primaryKeys)) {
						String keys[] = StringUtils.split(primaryKeys, ",");
						for (String key : keys) {
							fields.add(key);
						}
						dataset.setPrimaryKeys(primaryKeys);
					}

					FromSegment fromSegment = new FromSegment();
					fromSegment.setCreateBy(user.getActorId());
					fromSegment.setCreateTime(new Date());
					fromSegment.setTableName(tableName);
					fromSegment.setTableCN(table.getString("name"));
					fromSegment.setTableId(table.getString("id"));
					fromSegment.setOrdinal(i);
					fromSegment.setPosition("468.359375,116.515625");
					dataset.addFrom(fromSegment);
					List<JSONObject> SelectSegments = new ArrayList<JSONObject>();
					List<JSONObject> FromSegments = new ArrayList<JSONObject>();

					List<JSONObject> colList = new ArrayList<JSONObject>();
					for (String f : fields) {// 默认必须字段
						if (!cs.contains(f.toLowerCase())) {// 判断该字段有咩有在数据库
							continue;
						}
						segment = this.getSelectSegment(metadata, table, f);
						dataset.addSelect(segment);
						sobj = segment.toJsonObject();
						sobj.put("tableNameCN", table.getString("name"));
						SelectSegments.add(sobj);

						c = this.getColumn(table, f);
						colList.add(c);
					}

					/*
					 * segment = this.getSelectSegment(table,"topid"); dataset.addSelect(segment);
					 * sobj = segment.toJsonObject(); sobj.put("tableNameCN",
					 * table.getString("name")); SelectSegments.add(sobj);
					 * 
					 * c = this.getColumn(table,"id"); colList.add(c); c =
					 * this.getColumn(table,"topid"); colList.add(c);
					 */

					String batchRules = job.getString("batchRules");

					JSONObject dataSource = new JSONObject();
					dataSource.put("databaseId", table.getLong("databaseId"));
					dataSource.put("name", dataset.getName());
					dataSource.put("title", dataset.getTitle());
					dataSource.put("createTime", dataset.getCreateTime());
					dataSource.put("tables", tableName);

					if (columns != null) {
						// JSONObject columnObj = new JSONObject();
						for (int x = 0; x < columns.size(); x++) {

							JSONObject col = columns.getJSONObject(x);

							String fieldName = col.getString("fieldName");

							if (StringUtils.isBlank(fieldName) || //
									!cs.contains(fieldName.toLowerCase())) {// 判断该字段有咩有在数据库
								continue;
							}

							if (fields.contains(fieldName.toLowerCase())) { // 不能重复
								continue;
							} else {
								// columnObj.put(fieldName, col);
								fields.add(fieldName.toLowerCase());
							}
							String id = col.getString("id");
							JSONObject data = new JSONObject();
							data.put("inid", pageId);
							data.put("inname", "数据源-->" + table.getString("name") + "(" + col.getString("text") + ")");

							String columnLabel = this.Metadata(metadata, tableName, "table") + "_0_" + //
									this.Metadata(metadata, col.getString("fieldName"), "column");

							// data.put("columnName", tableName + "_0_" +
							// col.getString("fieldName"));
							data.put("columnName", columnLabel);

							data.put("outname", col.getString("text") + " 系统参数 1");
							data.put("outid", id);
							data.put("type", "getRow");
							data.put("param", "sys" + (System.currentTimeMillis() + x));

							JSONArray dataArray0 = new JSONArray();
							if (StringUtils.isBlank(batchRules)) {
								dataArray0.add(data);
								datas.put(id, dataArray0);
							}
							if (datas.containsKey(id)) {// 系统自动生成关系对应
								JSONArray dataArray = datas.getJSONArray(id);
								if (dataArray != null) {
									for (int i0 = 0; i0 < dataArray.size(); i0++) {
										JSONObject j = dataArray.getJSONObject(i0);
										if (j != null) {
											String param = j.getString("param");
											if (param != null && !param.startsWith("sys")) {
												dataArray0.add(j);
											} else if (param != null && param.startsWith("sys")) {
											}
										}
									}
								}
							}

							/**
							 * 生成 dataSet
							 */
							segment = new SelectSegment();
							segment.setCreateBy(user.getActorId());
							segment.setCreateTime(new Date());
							segment.setTitle(col.getString("text"));
							segment.setColumnName(col.getString("fieldName"));
							segment.setTableName(tableName);
							segment.setOrdinal(x);
							segment.setAggregate("");
							segment.setOutput("true");

							// segment.setColumnLabel(tableName + "_0_" +
							// col.getString("fieldName"));
							segment.setColumnLabel(columnLabel);

							JSONObject expressionObj = new JSONObject();

							// expressionObj.put("code",
							// "~F{default." + tableName + "." +
							// col.getString("fieldName") + "}");

							expressionObj.put("code",
									"~F{default." + this.Metadata(metadata, tableName, "table") + "." + //
											this.Metadata(metadata, col.getString("fieldName"), "column") + "}");

							expressionObj.put("value",
									"~F{default." + table.getString("name") + "." + col.getString("text") + "}");
							expressionObj.put("FieldLength", col.getInteger("strlen"));
							expressionObj.put("FieldType", col.getString("dType"));
							expressionObj.put("tableNameCN", table.getString("name"));
							segment.setExpression(expressionObj.toString());

							dataset.addSelect(segment);
							sobj = segment.toJsonObject();
							sobj.put("tableNameCN", table.getString("name"));
							SelectSegments.add(sobj);

							/**
							 * 生成 页面的数据源并且选上
							 */
							c = new JSONObject();
							c.put("title", col.getString("text"));
							c.put("tableName", tableName);
							c.put("columnName", tableName + "_0_" + col.getString("fieldName"));

							// c.put("columnLabel", tableName + "_0_" +
							// col.getString("fieldName"));
							c.put("columnLabel", columnLabel);

							c.put("code", "~F{default." + tableName + "." + col.getString("fieldName") + "}");
							c.put("value", "~F{default." + table.getString("name") + "." + col.getString("text") + "}");
							c.put("FieldLength", col.getInteger("strlen"));
							c.put("FieldType", col.getString("dType"));
							c.put("tableNameCn", table.getString("name"));
							colList.add(c);
						}
						dataSource.put("columns", colList);
						/*
						 * value.put(paraTypeKey, paraTypeArr.toJSONString());
						 * fr.setValue(value.toJSONString()); formRuleService.save(fr);
						 */
					}

					// //System.out.println(dataset);
					dataSetService.save(dataset);

					if (!StringUtils.isBlank(batchRules)) {
						// JSONArray batchRulesArray =
						// JSONArray.parseArray(batchRules);
						JSONObject rule = JSONObject.parseObject(batchRules);
						rule.put("dataSetId", dataset.getId());
						dataSource.put("batchRules", rule.toJSONString());
					}

					dataSources.add(dataSource);

					// DataSetAudit audit =
					// this.dataSetAuditService.getLastestDataSetAudit(dataset.getId());
					// if (audit == null) {
					// audit = new DataSetAudit();
					// }
					// JSONObject auditContent;
					// if (StringUtils.isNotBlank(audit.getContent())) {
					// auditContent = JSON.parseObject(audit.getContent());
					// } else {
					// auditContent = new JSONObject();
					// }
					audit.setId(null);
					auditContent.put("id", dataset.getId());
					auditContent.put("selectSegments", SelectSegments);
					// String systemName = table.getString("systemName");
					if (StringUtils.isBlank(systemName)) {
						systemName = Environment.DEFAULT_SYSTEM_NAME;
					}
					JSONObject fJson = fromSegment.toJsonObject(), datasJsonObject = new JSONObject();
					fJson.put("tableType", table.get("tableType"));
					datasJsonObject.put("text", systemName);
					datasJsonObject.put("code", systemName);
					fJson.put("dataSource", datasJsonObject);
					FromSegments.add(fJson);
					auditContent.put("fromSegments", FromSegments);
					auditContent.put("systemName", systemName);
					audit.setContent(auditContent.toJSONString());
					if (StringUtils.isEmpty(dataSetId)) {
						audit.setCreateBy(dataset.getCreateBy());
						if (dataset.getSaveFlag() == null) {
							audit.setSaveFlag("C");
						}
					} else {
						if (dataset.getSaveFlag() == null) {
							audit.setSaveFlag("U");
						}
						if (dataset.getDeleteFlag() == 1) {
							audit.setSaveFlag("D");
						}
						audit.setCreateBy(dataset.getUpdateBy());
					}
					audit.setDatasetId(dataset.getId());
					dataSetAuditService.save(audit);

					// dataSource.put("databaseId",
					// dataset.getId());//systemName
					dataSource.put("dataset", dataset.toJsonObject());

					job.put("dataSetId", dataset.getId());
				}

				/*
				 * for (String key : relation.keySet()) { JSONObject r = relation.get(key);
				 * CellDataTable cellDataTable = new CellDataTable();
				 * cellDataTable.setId(r.getString("id")); cellDataTable.setTopId(""); String
				 * topTableName = r.getString("topTableName"); if
				 * (StringUtils.isNotBlank(topTableName)) { JSONObject pTable =
				 * relation.get(topTableName); if (pTable != null) {
				 * cellDataTable.setTopId(pTable.getString("id")); } }
				 * cellDataTableService.save(cellDataTable); }
				 */
				this.saveRelation(relation);

				/**
				 * 
				 */
				value.put(paraTypeKey, paraTypeArr.toJSONString());

				dataSources = this.rebuildDataSource(dataSources);

				value.put(dataSourceKey, dataSources.toJSONString());
				fr.setValue(value.toJSONString());
				if (StringUtils.isNotBlank(pageId)) {
					formRuleService.save(fr);
					formRulePropertyService.isRuleProperties(fr);
					formRulePropertyService.saveComExt(fr);
				}
			}

		}
		return jsons.toJSONString().getBytes("UTF-8");
	}

	private String Metadata(JSONObject metadata, String string, String type) {
		if (metadata == null || StringUtils.isEmpty(string)) {
			return string;
		}
		JSONObject json = metadata.getJSONObject(type);
		String key = "alias", t = "t";
		int no = 0;
		if (type.equals("column")) {
			key = "label";
			t = "c";
		}
		// string = string.toLowerCase();
		if (!json.containsKey(string)) {
			no = metadata.getIntValue(key);
			json.put(string, t + (no++));
			metadata.put(key, no);
		}
		return json.getString(string);
	}

	/**
	 * 保存主从表关系
	 * 
	 * @param relation
	 */
	protected void saveRelation(Map<String, JSONObject> relation) {
		if (relation != null && relation.size() > 0) {

			String id, topTableName;
			for (String key : relation.keySet()) {
				JSONObject r = relation.get(key);
				id = r.getString("id");
				topTableName = r.getString("topTableName");
				if (r.getBooleanValue("R")) {// R平台
					RdataTable dataTable = new RdataTable();
					dataTable.setId(id);
					dataTable.setTopid("");
					if (StringUtils.isNotBlank(topTableName)) {
						JSONObject pTable = relation.get(topTableName);
						if (pTable != null) {
							dataTable.setTopid(pTable.getString("id"));
						}
					}
					rdataTableService.save(dataTable);
				} else { // 黄工平台
					CellDataTable dataTable = new CellDataTable();
					dataTable.setId(id);
					dataTable.setTopId("");
					if (StringUtils.isNotBlank(topTableName)) {
						JSONObject pTable = relation.get(topTableName);
						if (pTable != null) {
							dataTable.setTopId(pTable.getString("id"));
						}
					}
					cellDataTableService.save(dataTable);
				}
			}
		}
	}

	private JSONArray rebuildDataSource(JSONArray jsonarray) {
		if (jsonarray != null && !jsonarray.isEmpty()) {
			JSONObject jsonObject, dataset, json = new JSONObject();
			JSONArray dataSource = new JSONArray(), tables = new JSONArray();
			List<JSONObject> columns = new ArrayList<JSONObject>();
			for (int i = 0; i < jsonarray.size(); i++) {
				jsonObject = jsonarray.getJSONObject(i);
				if (i == 0) {
					String[] f = new String[] { "databaseId", "title", "datasetId", "name" };
					for (String key : f) {
						json.put(key, jsonObject.get(key));
					}
				}
				dataset = jsonObject.getJSONObject("dataset");
				dataset.put("datasetId", dataset.get("id"));
				dataset.put("batchRules", jsonObject.get("batchRules"));
				dataSource.add(dataset);
				tables.add(jsonObject.getString("tables"));
				if (jsonObject.containsKey("columns")) {
					JSONArray cols = jsonObject.getJSONArray("columns");
					for (int c = 0; c < cols.size(); c++) {
						JSONObject j = cols.getJSONObject(c);
						j.put("datasetId", dataset.get("id"));
						//// System.out.println(j);
						columns.add(j);
					}
				}
			}
			json.put("columns", columns);
			json.put("datasource", dataSource);
			json.put("tables", tables);
			jsonarray = new JSONArray();
			jsonarray.add(json);
		}
		return jsonarray;
	}

	private SelectSegment getSelectSegment(JSONObject metadata, JSONObject table, String field) {
		SelectSegment segment = new SelectSegment();
		String tableName = table.getString("tableName");
		segment.setCreateBy(table.getString("user"));
		segment.setCreateTime(new Date());
		segment.setTitle(field);
		segment.setColumnName(field);
		segment.setTableName(tableName);
		segment.setOrdinal(0);
		segment.setOutput("true");

		// segment.setColumnLabel(tableName + "_0_" + field);
		segment.setColumnLabel(this.Metadata(metadata, tableName, "table") + //
				"_0_" + this.Metadata(metadata, field, "column"));
		JSONObject expressionObj = new JSONObject();
		expressionObj.put("code", "~F{default." + tableName + "." + field + "}");
		expressionObj.put("value", "~F{default." + table.getString("name") + "." + field + "}");
		expressionObj.put("FieldLength", 100);
		expressionObj.put("FieldType", "string");
		expressionObj.put("tableNameCN", table.getString("name"));
		segment.setExpression(expressionObj.toString());

		return segment;
	}

	private JSONObject getColumn(JSONObject table, String field) {
		JSONObject c = new JSONObject();
		String tableName = table.getString("tableName");
		c.put("title", field);
		c.put("tableName", tableName);
		c.put("columnName", tableName + "_0_" + field);
		c.put("columnLabel", tableName + "_0_" + field);
		c.put("code", "~F{default." + tableName + "." + field + "}");
		c.put("value", "~F{default." + table.getString("name") + "." + field + "}");
		c.put("FieldLength", 100);
		c.put("FieldType", "string");
		c.put("tableNameCn", table.getString("name"));
		return c;
	}

	@Autowired
	private DepBaseWdataSetService depBaseWdataSetService;

	@Autowired
	private WdatasetSqlliteService wdatasetSqlliteService;

	@ResponseBody
	@RequestMapping("getPageElementsById")
	public byte[] getPageElementsById(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		JSONObject result = new JSONObject();
		String pageId = RequestUtils.getString(request, "pageId");
		if (StringUtils.isNotEmpty(pageId)) {

			List<Map<String, Object>> list = formPageService.getPageElementsById(pageId);

			Object rid, name;
			Map<String, Object> rmMap;
			List<Map<String, Object>> list0 = new ArrayList<Map<String, Object>>();
			int id = 10000;
			for (Map<String, Object> m : list) {
				rid = m.get("rid");
				m.put("id", ++id);
				params.put(id + "", id);
				if ((name = m.get("name")) != null && StringUtils.contains(name.toString(), "cell")) {
					Map<String, String> map = this.getRuleMap(rid + "");
					if (map != null) {
						String linkPageKey = "linkPage";
						if (map.containsKey(linkPageKey)) {
							JSONObject linkPage = JSONArray.parseArray(map.get(linkPageKey)).getJSONObject(0);
							if (linkPage != null) {
								Long templateId = linkPage.getLong("id");
								List<DepReportCell> list2 = this.getCellElementsById(templateId);
								if (CollectionUtils.isNotEmpty(list2)) {
									int cellId = 119999;
									m.put("isParent", true);
									for (DepReportCell drc : list2) {
										rmMap = new HashMap<String, Object>();
										rmMap.put("pname", m.get("value"));
										rmMap.put("name", drc.getCode());
										rmMap.put("rid", drc.getCode());
										rmMap.put("value", drc.getName());
										rmMap.put("container", name);
										rmMap.put("pid", id);
										rmMap.put("id", cellId++);

										list0.add(rmMap);
									}
								}
							}
						}
					}
				}
			}
			list.addAll(list0);

			List<Map<String, Object>> rows = this.populateDefinedRule(list, params);
			result.put("rows", rows);

			Map<String, String> ruleMap = formRuleService.getRuleByPageId(pageId);
			String str = ruleMap.get("saveSourceSet"); // 保存设置
			try {
				// str = str == null ? ruleMap.get("linkageControlIn") : str;
				if (StringUtils.isNotBlank(str)) {
					JSONObject wdataSet = JSON.parseArray(str).getJSONObject(0);
					if (wdataSet.containsKey("wdataSet")) {
						Long wdataSetId = wdataSet.getJSONObject("wdataSet").getLong("id");

						DepBaseWdataSet dws = depBaseWdataSetService.getDepBaseWdataSet(wdataSetId);

						result.put("wdataSet", dws.getRuleJson());
						result.put("wdataSetName", dws.getDataSetName());
					}

				}
			} catch (Exception ex) {
				logger.error(ex.getMessage());
			}

			result.put("total", rows.size());
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	private List<DepReportCell> getCellElementsById(Long id) {
		DepReportCellQuery query = new DepReportCellQuery();
		query.setRepTemplateId(id);
		query.setDelflag("0");
		return this.depReportCellService.list(query);
	}

	/**
	 * 組裝map 並且把流程定義的屬性賦上
	 * 
	 * @param list
	 * @param params
	 * @return
	 */
	private List<Map<String, Object>> populateDefinedRule(List<Map<String, Object>> list, Map<String, Object> params) {
		List<Map<String, Object>> rows = new ArrayList<>();
		if (list != null && !list.isEmpty()) {
			Object name, parentId;
			Map<String, Object> parent;
			for (Map<String, Object> m : list) {
				parentId = m.get("pid");
				name = m.get("name");
				if (!params.containsKey(parentId + "")) {
					parent = new HashMap<String, Object>();
					parent.put("pid", null);
					parent.put("id", parentId);
					parent.put("name", m.get("pname"));
					rows.add(parent);
					params.put(parentId + "", m);
				}
				m.put("t", name);
				m.put("name", m.get("value") == null ? name : m.get("value"));

				m.put("read", true);
				m.put("write", true);
				m.put("show", true);

				rows.add(m);
			}
		}
		return rows;
	}

	/**
	 * 遍历输出和下级节点所有弹出页面
	 * 
	 * @param eventsMap
	 *            事件
	 * @param widgetEventAry
	 *            输出事件
	 * @param widgetAry
	 *            触发控件
	 */
	private void getEventNewWindowByWidgetEventAry(Map<String, List<Map<String, Object>>> eventsMap,
			JSONArray widgetEventAry, JSONArray widgetAry) {
		List<Map<String, Object>> eventsList;
		Map<String, Object> eventMap;
		JSONObject widgetObj;
		JSONObject widgetPObj;
		JSONObject widgetEventObj;
		JSONArray outWidgetAry; // 输出控件
		JSONArray childsAry; // 下级事件
		JSONObject outWidgetObj;
		if (widgetEventAry != null && widgetEventAry.toJSONString().indexOf("newWindow") > -1
				&& widgetEventAry.toJSONString().indexOf("ignoreWindow") == -1) { // 表示有弹出窗口
			for (Object object3 : widgetEventAry) {
				widgetEventObj = (JSONObject) object3;
				outWidgetAry = JSON.parseArray(widgetEventObj.getString("outWidget"));
				for (Object object4 : outWidgetAry) {
					outWidgetObj = (JSONObject) object4;
					if (outWidgetObj.toJSONString().indexOf("newWindow") > -1) {
						// 根据触发控件来生成多个
						for (Object object5 : widgetAry) {
							widgetObj = (JSONObject) object5;
							widgetPObj = JSON.parseObject(widgetObj.getString("pObj"));
							String widgetPageId = widgetPObj.getString("pageId");
							eventMap = new HashMap<String, Object>();

							// 封装value字符串对象
							JSONArray value_Ary = new JSONArray();
							JSONObject value_Obj = new JSONObject();
							value_Obj.put("node", outWidgetObj);
							value_Obj.put("id", outWidgetObj.getString("id"));
							value_Obj.put("name", outWidgetObj.getString("name"));
							value_Obj.put("url", outWidgetObj.getString("srcUrl"));
							value_Ary.add(value_Obj);

							eventMap.put("PAGEID_", outWidgetObj.getString("id"));
							eventMap.put("NAME_", widgetPObj.getString("eleId"));// 触发控件role
							eventMap.put("VALUE_", value_Ary.toJSONString());
							eventMap.put("PARAMTYPE_", "linkPage");
							eventMap.put("TITLE_", widgetPObj.getString("name"));
							eventMap.put("PAGENAME_", outWidgetObj.getString("title"));
							if (eventsMap.containsKey(widgetPageId)) {
								eventsList = eventsMap.get(widgetPageId);
								eventsList.add(eventMap);
							} else {
								eventsList = new ArrayList<Map<String, Object>>();
								eventsList.add(eventMap);
								eventsMap.put(widgetPageId, eventsList);
							}
						}
					}
				}
				String childStr = widgetEventObj.getString("childs");
				if (StringUtils.isNotEmpty(childStr)) {
					childsAry = JSON.parseArray(widgetEventObj.getString("childs"));
					if (!childsAry.isEmpty()) {
						getEventNewWindowByWidgetEventAry(eventsMap, childsAry, widgetAry);
					}
				}
			}
		}
	}

	/**
	 * 获取事件定义器中弹出页面的规则参数
	 * 
	 * @param pageId
	 */
	private Map<String, List<Map<String, Object>>> getEventNewWindow(String pageId) {
		Map<String/* 页面id */, List<Map<String, Object>>> eventsMap = new HashMap<String, List<Map<String, Object>>>();
		try {
			List<FormRuleProperty> eventsRule = formRulePropertyService.getAttributeDatasByPageId(pageId, "1438");
			if (eventsRule != null && eventsRule.size() > 0) {
				FormRuleProperty eventRule = eventsRule.get(0);
				if (eventRule != null) {
					String eventRuleValue = eventRule.getValue();
					if (StringUtils.isNotEmpty(eventRuleValue)) {
						JSONArray eventsAry = JSON.parseArray(eventRuleValue);
						if (eventsAry != null && !eventsAry.isEmpty()) {
							JSONObject eventsObj;
							for (Object object : eventsAry) {
								eventsObj = (JSONObject) object;
								JSONArray valuesAry = eventsObj.getJSONArray("values");
								JSONObject valueObj;
								JSONArray widgetAry; // 触发控件
								JSONArray widgetEventAry; // 输出控件
								for (Object object2 : valuesAry) {
									valueObj = (JSONObject) object2;
									widgetAry = valueObj.getJSONArray("widget");
									widgetEventAry = valueObj.getJSONArray("widgetEvent");
									getEventNewWindowByWidgetEventAry(eventsMap, widgetEventAry, widgetAry);
								}
							}
						}
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info(e.getMessage());
		}
		return eventsMap;
	}

	/**
	 * 事件定义器 获取当前页面的所有下级页面控件元素
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/getPageHierarchicalAssembly")
	public byte[] getPageHierarchicalAssembly(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String pageId = RequestUtils.getString(request, "pageId");
		Boolean showEvent = RequestUtils.getBoolean(request, "showEvent");
		// 是否为触发事件
		Boolean isTrigger = RequestUtils.getBoolean(request, "isTrigger");
		// 是否根据控件类型分组显示
		Boolean isGroup = RequestUtils.getBoolean(request, "isGroup");
		JSONArray retAry = new JSONArray();
		JSONObject retObj = new JSONObject();

		Map<String, List<Map<String, Object>>> propertyMap = getPropertys(isTrigger);
		// isGroup = true;
		Map<String, String> formComponentMap = null;
		if (isGroup) {
			List<FormComponent> formComponents = formComponentService.list(new FormComponentQuery());
			formComponentMap = new HashMap<>();
			for (FormComponent formComponent : formComponents) {
				if (formComponent.getDataRole() != null)
					formComponentMap.put(formComponent.getDataRole(), formComponent.getName());
			}
		}

		// 获取根页面的html 根节点
		int level = 1;
		FormPage formPage = formPageService.getFormPage(pageId);
		retObj = new JSONObject();
		retObj.put("id", pageId + level);
		retObj.put("pId", level);
		retObj.put("level", level);
		retObj.put("klevel", level);
		retObj.put("eleId", pageId);
		retObj.put("pageId", pageId);
		retObj.put("name", formPage.getName());
		retObj.put("isPage", true);
		retObj.put("drole", "page");
		retAry.add(retObj);
		if (showEvent) {
			this.addEventsTo(propertyMap, "page", retAry, retObj, null);
		}

		// iframe 页面
		List<Map<String, Object>> list = formRulePropertyService.getStuffByPageId(pageId);

		//
		Map<String/* 页面id */, List<Map<String, Object>>> eventsMap = getEventNewWindow(pageId);

		if (eventsMap.containsKey(pageId)) {
			list.addAll(eventsMap.get(pageId));
		}

		// 获取所有data-role的元素 元素节点
		Document doc = Jsoup.parse(formPage.getFormHtml());
		if (list != null) {
			this.assemble(list, retObj, retAry, level + 1, propertyMap, showEvent, "", eventsMap, doc, isGroup,
					formComponentMap);
		}

		Elements elements = doc.getElementsByAttribute("data-role");
		if (isGroup) {
			buildGroupElement(elements, "", level, retObj, pageId, retAry, showEvent, propertyMap, formComponentMap);
		} else {
			// buildElement(elements, "", level, retObj, pageId, retAry,
			// showEvent, propertyMap);
			String role = null;
			String name;
			for (Element element : elements) {
				if (element.attr("crtltype").startsWith("kendo")) { // 不是layout
					role = element.attr("data-role");
					// 布局的元素
					// 页面中的 元素
					retObj = new JSONObject();
					retObj.put("id", element.attr("id") + level);
					retObj.put("pId", pageId + level);
					retObj.put("level", level);
					retObj.put("klevel", level);
					retObj.put("eleId", element.attr("id"));
					retObj.put("icon", "/images/cog.png");
					retObj.put("pageId", pageId);
					retObj.put("drole", role);
					retObj.put("isEle", true);
					name = org.apache.commons.lang.StringUtils.isNotEmpty(element.attr("cname")) ? element.attr("cname")
							: (org.apache.commons.lang.StringUtils.isNotEmpty(element.attr("title"))
									? element.attr("title")
									: element.attr("name"));
					if (org.apache.commons.lang.StringUtils.isEmpty(name)) {
						continue;
					}
					retObj.put("name", name);
					retAry.add(retObj);
					// cell表定义
					if ("cell".equalsIgnoreCase(role)) {
						addCellNode(retAry, retObj, showEvent, role, null);
					} else if ("gridbt".equalsIgnoreCase(role) || "treelistbt".equalsIgnoreCase(role)) {
						addGridBtNode(retAry, retObj, showEvent, role, propertyMap, null);
					}
					if (showEvent) {
						this.addEventsTo(propertyMap, role, retAry, retObj, null);
					}
				}
			}
		}

		return retAry.toJSONString().getBytes("UTF-8");
	}

	private void buildGroupElement(List<Element> elements, String subEventsName, int level, JSONObject pObj,
			String pageId, JSONArray retAry, boolean showEvent, Map<String, List<Map<String, Object>>> propertyMap,
			Map<String, String> formComponentMap) {
		Map<String, List<Element>> groupEles = new HashMap<>();
		String role;
		List<Element> eles;
		for (Element element : elements) {
			role = element.attr("data-role");
			if (groupEles.containsKey(role)) {
				eles = groupEles.get(role);
				eles.add(element);
			} else {
				eles = new ArrayList<>();
				eles.add(element);
				groupEles.put(role, eles);
			}
		}
		Set<String> keySet = groupEles.keySet();
		JSONObject rrObj;
		for (String key : keySet) {
			eles = groupEles.get(key);
			if (eles.size() > 0) {
				rrObj = new JSONObject();
				rrObj.put("id", pObj.getString("id") + key + level);
				rrObj.put("pId", pObj.getString("id"));
				rrObj.put("level", level);
				rrObj.put("klevel", level);
				rrObj.put("pageId", pageId);
				rrObj.put("drole", key);
				rrObj.put("name", formComponentMap.get(key));
				rrObj.put("icon", "/images/system-settings.png");
				rrObj.put("isGroup", true);
				retAry.add(rrObj);
				buildElement(eles, "", level, rrObj, pageId, retAry, showEvent, propertyMap);
			}
		}
	}

	private void buildElement(List<Element> elements, String subEventsName, int level, JSONObject pObj, String pageId,
			JSONArray retAry, boolean showEvent, Map<String, List<Map<String, Object>>> propertyMap) {
		String role = null;
		JSONObject retObj;
		String name;
		for (Element element : elements) {
			if (element.attr("crtltype").startsWith("kendo")) { // 不是layout布局的元素
				role = element.attr("data-role");
				// eleObj.put("id", subObj.get("id") + subEventsName + index +
				// element.attr("id") + level);
				// 页面中的 元素
				retObj = new JSONObject();
				retObj.put("id", pObj.getString("id") + subEventsName + element.attr("id") + level);
				retObj.put("pId", pObj.get("id"));
				retObj.put("ppId", pObj.get("pId"));
				retObj.put("level", level);
				retObj.put("klevel", level);
				retObj.put("eleId", element.attr("id"));
				retObj.put("icon", "/images/cog.png");
				retObj.put("pageId", pageId);
				retObj.put("isEle", true);
				retObj.put("drole", role);
				name = org.apache.commons.lang.StringUtils.isNotEmpty(element.attr("cname")) ? element.attr("cname")
						: (org.apache.commons.lang.StringUtils.isNotEmpty(element.attr("title")) ? element.attr("title")
								: element.attr("name"));
				if (org.apache.commons.lang.StringUtils.isEmpty(name)) {
					continue;
				}
				retObj.put("name", name);
				retAry.add(retObj);
				// cell表定义
				if ("cell".equalsIgnoreCase(role)) {
					addCellNode(retAry, retObj, showEvent, role, null);
				} else if ("gridbt".equalsIgnoreCase(role) || "treelistbt".equalsIgnoreCase(role)) {
					addGridBtNode(retAry, retObj, showEvent, role, propertyMap, pObj);
				}
				if (showEvent) {
					this.addEventsTo(propertyMap, role, retAry, retObj, pObj);
				}
			}
		}
	}

	private JSONObject getCellProperty() {
		String rootPath = SystemProperties.getConfigRootPath();
		String path = rootPath + "/conf/templates/form/spreadjs.tmp";
		File file = new File(path);
		String ruleStr = null;
		try {
			ruleStr = com.mchange.io.FileUtils.getContentsAsString(file, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (ruleStr != null && !ruleStr.isEmpty()) {
			return JSON.parseObject(ruleStr);
		}
		return null;
	}

	/**
	 * spread js 事件定义器节点
	 * 
	 * @param retAry
	 * @param retObj
	 * @param showEvent
	 * @param role
	 */
	private void addCellNode(JSONArray retAry, JSONObject retObj, boolean showEvent, String role, JSONObject pObj) {
		retObj.put("icon", "/images/table_tab.png");
		retObj.put("isEle", true);
		String pageId = retObj.getString("pageId");
		FormRuleProperty rp = formRulePropertyService.getPageComponentPropertyRule(pageId, role, "linkPage",
				retObj.getString("eleId"));
		if (rp != null) {
			String ruleValue = rp.getValue();
			Integer id = null;
			if (ruleValue != null && !ruleValue.isEmpty()) {
				JSONArray rAry = JSON.parseArray(ruleValue);
				JSONObject rObj = rAry.getJSONObject(0);
				id = rObj.getInteger("id");
				if (id != null) {
					JSONObject rootObj = new JSONObject();
					String rootId = retObj.getString("id") + "root" + id;
					rootObj.put("level", retObj.get("level"));
					rootObj.put("klevel", retObj.get("level"));
					rootObj.put("id", rootId);
					rootObj.put("pId", retObj.getString("id"));
					rootObj.put("name", retObj.getString("name") + "(根节点)");
					rootObj.put("icon", "/images/table_tab.png");
					rootObj.put("eName", retObj.getString("name") + "(根节点)");
					rootObj.put("pObj", pObj != null ? pObj.toJSONString() : retObj.toJSONString());
					rootObj.put("eleId", retObj.getString("eleId"));
					rootObj.put("pageId", retObj.getString("pageId"));
					rootObj.put("isEle", true);
					rootObj.put("cellId", id);
					rootObj.put("otype", "cell");
					retAry.add(rootObj);
					if (showEvent) {
						JSONObject rootEventObj = new JSONObject();
						rootEventObj.put("id", retObj.getString("id") + "getValue" + "root");
						rootEventObj.put("pId", rootId);
						rootEventObj.put("name", "获取值");
						rootEventObj.put("icon", "/images/event.png");
						rootEventObj.put("eName", "getValue");
						rootEventObj.put("pObj", JSON.toJSONString(rootObj));
						rootEventObj.put("isEvn", true);
						rootEventObj.put("otype", "cell");
						rootEventObj.put("cellId", id);
						retAry.add(rootEventObj);

						rootEventObj = new JSONObject();
						rootEventObj.put("id", retObj.getString("id") + "setValue" + "root");
						rootEventObj.put("pId", rootId);
						rootEventObj.put("name", "赋值");
						rootEventObj.put("icon", "/images/event.png");
						rootEventObj.put("eName", "setValue");
						rootEventObj.put("pObj", JSON.toJSONString(rootObj));
						rootEventObj.put("isEvn", true);
						rootEventObj.put("otype", "cell");
						rootEventObj.put("cellId", id);
						retAry.add(rootEventObj);

						rootEventObj = new JSONObject();
						rootEventObj.put("id", retObj.getString("id") + "prevPage" + "root");
						rootEventObj.put("pId", rootId);
						rootEventObj.put("name", "上一页");
						rootEventObj.put("icon", "/images/event.png");
						rootEventObj.put("eName", "prevPage");
						rootEventObj.put("pObj", JSON.toJSONString(rootObj));
						rootEventObj.put("isEvn", true);
						rootEventObj.put("otype", "cell");
						rootEventObj.put("cellId", id);
						retAry.add(rootEventObj);

						rootEventObj = new JSONObject();
						rootEventObj.put("id", retObj.getString("id") + "nextPage" + "root");
						rootEventObj.put("pId", rootId);
						rootEventObj.put("name", "下一页");
						rootEventObj.put("icon", "/images/event.png");
						rootEventObj.put("eName", "nextPage");
						rootEventObj.put("pObj", JSON.toJSONString(rootObj));
						rootEventObj.put("isEvn", true);
						rootEventObj.put("otype", "cell");
						rootEventObj.put("cellId", id);
						retAry.add(rootEventObj);

						rootEventObj = new JSONObject();
						rootEventObj.put("id", retObj.getString("id") + "linkAge" + "root");
						rootEventObj.put("pId", rootId);
						rootEventObj.put("name", "联动事件");
						rootEventObj.put("icon", "/images/event.png");
						rootEventObj.put("eName", "linkAge");
						rootEventObj.put("pObj", JSON.toJSONString(rootObj));
						rootEventObj.put("isEvn", true);
						rootEventObj.put("otype", "cell");
						rootEventObj.put("cellId", id);
						retAry.add(rootEventObj);

						rootEventObj = new JSONObject();
						rootEventObj.put("id", retObj.getString("id") + "initEnd" + "root");
						rootEventObj.put("pId", rootId);
						rootEventObj.put("name", "初始化完成事件");
						rootEventObj.put("icon", "/images/event.png");
						rootEventObj.put("eName", "initEnd");
						rootEventObj.put("pObj", JSON.toJSONString(rootObj));
						rootEventObj.put("isEvn", true);
						rootEventObj.put("otype", "cell");
						rootEventObj.put("cellId", id);
						retAry.add(rootEventObj);
					}
					DepReportTemplate depReportTemplate = depReportTemplateService.getDepReportTemplate(id.longValue());
					String ruleJson = depReportTemplate.getRuleJson();
					if (ruleJson != null && !ruleJson.isEmpty()) {
						JSONObject ruleObj = JSON.parseObject(ruleJson);
						Set<String> keys = ruleObj.keySet();
						String pageParams = "A000-1-1-003";
						String nameCode = "A001-1-002";
						String roleCode = "A001-1-001";
						JSONObject rlObj = null;
						JSONObject propertyObj = getCellProperty();
						JSONArray proRules;
						JSONObject proRule;
						int count = 0;
						for (String key : keys) {
							if (!"page".equals(key)) {
								rlObj = ruleObj.getJSONObject(key);
								JSONObject eleObj = new JSONObject();
								eleObj.put("level", retObj.get("level"));
								eleObj.put("klevel", retObj.get("level"));
								eleObj.put("id", retObj.getString("id") + key + id);
								eleObj.put("pId", retObj.getString("id"));
								eleObj.put("name", rlObj.getString(nameCode));
								eleObj.put("icon", "/images/cog.png");
								eleObj.put("eName", rlObj.getString(nameCode));
								eleObj.put("pObj", retObj.toJSONString());
								eleObj.put("eleId", key);
								eleObj.put("pageId", retObj.getString("pageId"));
								eleObj.put("isEle", true);
								eleObj.put("cellId", id);
								eleObj.put("otype", "cell");
								retAry.add(eleObj);
								if (showEvent) {
									String role2 = rlObj.getString(roleCode);
									proRules = propertyObj.getJSONArray(role2);
									if (proRules != null && !proRules.isEmpty()) {
										for (Object object : proRules) {
											proRule = (JSONObject) object;
											JSONObject eventObj = new JSONObject();
											String name = proRule.getString("name");
											String title = proRule.getString("title");
											eventObj.put("id", retObj.getString("id") + name + key + count++);
											eventObj.put("pId", eleObj.getString("id"));
											eventObj.put("name", title);
											eventObj.put("icon", "/images/event.png");
											eventObj.put("eName", name);
											eventObj.put("pObj", eleObj.toJSONString());
											eventObj.put("isEvn", true);
											eventObj.put("otype", "cell");
											eventObj.put("cellId", id);
											retAry.add(eventObj);
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 角色转换
	 * 
	 * @param role
	 * @return
	 */
	private String convertRole(String role) {
		if (role.replaceAll("\\d|\\_|\\-", "").equalsIgnoreCase("colmd")) {
			role = "col-md-12";
		}
		return role;
	}

	/**
	 * 页面控件参数
	 * 
	 * @param propertyMap
	 * @param role
	 * @param retAry
	 * @param retObj
	 */
	private void addEventsTo(Map<String, List<Map<String, Object>>> propertyMap, String role, JSONArray retAry,
			JSONObject retObj, JSONObject pObj) {
		role = convertRole(role);
		List<Map<String, Object>> mapList = propertyMap.get(role);
		if (mapList != null) {
			JSONObject idVal = new JSONObject();
			String title = null;
			String name = null;
			String parentId = null;
			JSONObject eleObj = null;
			for (Map<String, Object> map : mapList) {
				title = map.get("TITLE_").toString();
				name = map.get("NAME_").toString();
				parentId = map.get("PARENTID_") != null ? map.get("PARENTID_").toString() : null;
				eleObj = new JSONObject();
				eleObj.put("id", retObj.getString("id") + name);
				idVal.put(map.get("ID_").toString(), eleObj.get("id"));
				if (parentId != null && idVal.containsKey(parentId)) {
					eleObj.put("pId", idVal.getString(parentId));
				} else {
					eleObj.put("pId", retObj.getString("id"));
				}
				eleObj.put("name", title);
				eleObj.put("icon", "/images/event.png");
				eleObj.put("eName", name);
				eleObj.put("pObj", /* pObj!=null?pObj.toJSONString(): */retObj.toJSONString());
				eleObj.put("isEvn", true);

				retAry.add(eleObj);
			}
		}
	}

	private List<Map<String, Object>> jdbcGetStuffByPageIds(List<String> subPageIds) {
		return jdbcGetStuffByPageIds(subPageIds, "linkPage");
	}

	private List<Map<String, Object>> jdbcGetStuffByPageIds(List<String> subPageIds, String type) {
		List<Map<String, Object>> stuffByPageIds = new ArrayList<>();
		try {
			Connection connection = DBConnectionFactory.getConnection();
			boolean isOracle = StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType());
			String conFun = isOracle ? "to_char(fcr.ID_)" : "CONVERT(VARCHAR,fcr.ID_)";
			String sql = "SELECT A.PAGEID_, A.NAME_ AS NAME_, A.VALUE_, A.PARAMTYPE_ AS PARAMTYPE_, B.VALUE_ AS TITLE_ , P.NAME_  AS PAGENAME_ FROM ( "
					+ "SELECT  fr.PAGEID_,fr.NAME_,frp.VALUE_,fcr.NAME_ AS PARAMTYPE_ FROM FORM_RULE fr LEFT JOIN  FORM_RULE_PROPERTY frp ON fr.ID_ = frp.RULEID_"
					+ " LEFT JOIN FORM_COMPONENT_PROPERTY fcr ON  " + conFun + " = frp.NAME_ WHERE fcr.NAME_ LIKE '%"
					+ type + "%' ) A LEFT JOIN ( "
					+ " SELECT fr.PAGEID_,fr.NAME_,frp.VALUE_ FROM FORM_RULE fr LEFT JOIN FORM_RULE_PROPERTY frp ON fr.ID_ = frp.RULEID_  LEFT JOIN FORM_COMPONENT_PROPERTY fcr "
					+ " ON " + conFun
					+ " = frp.NAME_ WHERE  fcr.name_ = 'html' ) B ON A.PAGEID_=B.PAGEID_ AND A.NAME_ = B.NAME_ LEFT JOIN FORM_PAGE P ON "
					+ "    P.ID_ = A.PAGEID_ WHERE     A.PAGEID_ in  ( ";
			StringBuilder queryBuilder = new StringBuilder(sql);
			for (int i = 0; i < subPageIds.size(); i++) {
				queryBuilder.append(" ?");
				if (i != subPageIds.size() - 1)
					queryBuilder.append(",");
			}
			queryBuilder.append(")");

			PreparedStatement ps = connection.prepareStatement(queryBuilder.toString());
			for (int i = 1; i <= subPageIds.size(); i++) {
				ps.setString(i, subPageIds.get(i - 1));
			}

			ResultSet rs = ps.executeQuery();
			Map<String, Object> map = new HashMap<>();
			while (rs.next()) {
				map = new HashMap<>();
				map.put("PAGEID_", rs.getString(1));
				map.put("NAME_", rs.getString(2));
				map.put("VALUE_", rs.getString(3));
				map.put("PARAMTYPE_", rs.getString(4));
				map.put("TITLE_", rs.getString(5));
				map.put("PAGENAME_", rs.getString(6));
				stuffByPageIds.add(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stuffByPageIds;
	}

	private void assemble(List<Map<String, Object>> list, /* String pageId */
			JSONObject pageObj, JSONArray retAry, Integer level, Map<String, List<Map<String, Object>>> propertyMap,
			Boolean showEvent, String eventsName, Map<String/* 页面id */, List<Map<String, Object>>> eventsMap,
			Document document, boolean isGroup, Map<String, String> formComponentMap) {
		// 最高限制层数，避免重复引用页面引起的死循环
		if (level == 18) {
			return;
		}
		List<Map<String, Object>> subList = null;
		JSONArray valueArray = null;
		JSONObject valueObj = null;
		JSONObject subObj = null;
		JSONObject eleObj = null;
		String subPageId = null;
		String role = null;
		Object valueObject;
		Element selement;
		int index = 0; // 用来判断同一页面调用多次
		String databaseType = DBConnectionFactory.getDatabaseType();
		Class<?> dmdbClobName = null;
		Class<?> clobName = null;
		if (DBUtils.DM_DBMS.equalsIgnoreCase(databaseType)) {
			dmdbClobName = ClassUtils.classForName("dm.jdbc.driver.DmdbClob");
		} else if (DBUtils.ORACLE.equalsIgnoreCase(databaseType)) {
			clobName = ClassUtils.classForName("oracle.sql.CLOB");
		}
		List<String> subPageIds = new ArrayList<>();
		for (Map<String, Object> map : list) {
			valueObject = map.get("VALUE_");
			String value = null;
			if (dmdbClobName != null && valueObject.getClass() == dmdbClobName) {
				try {
					Method method = dmdbClobName.getMethod("getCharacterStream", null);
					Reader reader = (Reader) method.invoke(valueObject, null);
					BufferedReader r = new BufferedReader(reader);
					StringBuilder b = new StringBuilder();
					String line;
					while ((line = r.readLine()) != null) {
						b.append(line);
					}
					value = b.toString();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (clobName != null && valueObject.getClass() == clobName) {
				try {
					Method method = clobName.getMethod("stringValue", null);
					String valstr = (String) method.invoke(valueObject, null);
					value = valstr;
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				value = (String) map.get("VALUE_"); // 获取子页面
			}
			valueArray = JSON.parseArray(value);
			for (Object object : valueArray) {
				valueObj = (JSONObject) object;
				subPageId = valueObj.getString("id"); // 子页面 id
				subPageIds.add(subPageId);
			}
		}
		Map<String, FormPage> formPageMaps = new HashMap<>();
		List<Map<String, Object>> stuffByPageIds = new ArrayList<>();
		if (subPageIds != null && subPageIds.size() > 0) {
			FormPageQuery query = new FormPageQuery();
			query.setIds(subPageIds);
			List<FormPage> formPages = formPageService.list(query);
			if (formPages != null && formPages.size() > 0) {
				for (FormPage formPage : formPages) {
					formPageMaps.put(formPage.getId(), formPage);
				}
			}

			// stuffByPageIds =
			// formRulePropertyService.getStuffByPageIds(subPageIds);
			stuffByPageIds = jdbcGetStuffByPageIds(subPageIds);
		}

		Map<String, List<Map<String, Object>>> subListMaps = new HashMap<>();
		if (stuffByPageIds != null && stuffByPageIds.size() > 0) {
			List<Map<String, Object>> tempList = null;
			String key = null;
			for (Map<String, Object> stuffMap : stuffByPageIds) {
				key = stuffMap.get("PAGEID_").toString();
				if (subListMaps.containsKey(key)) {
					subListMaps.get(key).add(stuffMap);
				} else {
					tempList = new ArrayList<>();
					subListMaps.put(key, tempList);
				}
			}
		}
		for (Map<String, Object> map : list) {
			valueObject = map.get("VALUE_");
			String value = null;

			if (dmdbClobName != null && valueObject.getClass() == dmdbClobName) {
				try {
					Method method = dmdbClobName.getMethod("getCharacterStream", null);
					Reader reader = (Reader) method.invoke(valueObject, null);
					BufferedReader r = new BufferedReader(reader);
					StringBuilder b = new StringBuilder();
					String line;
					while ((line = r.readLine()) != null) {
						b.append(line);
					}
					value = b.toString();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (clobName != null && valueObject.getClass() == clobName) {
				try {
					Method method = clobName.getMethod("stringValue", null);
					String valstr = (String) method.invoke(valueObject, null);
					value = valstr;
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				value = (String) map.get("VALUE_"); // 获取子页面
				/**
				 * 循环分别拿出子页面
				 */
				valueArray = JSON.parseArray(value);
			}
			String subEventsName = (String) map.get("NAME_"); // 控件name

			String subEventsTitle = null; // 控件名称
			Object titleObject = map.get("TITLE_");
			if (dmdbClobName != null && titleObject.getClass() == dmdbClobName) {
				try {
					Method method = dmdbClobName.getMethod("getCharacterStream", null);
					Reader reader = (Reader) method.invoke(titleObject, null);
					BufferedReader r = new BufferedReader(reader);
					StringBuilder b = new StringBuilder();
					String line;
					while ((line = r.readLine()) != null) {
						b.append(line);
					}
					subEventsTitle = b.toString();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else if (clobName != null && titleObject.getClass() == clobName) {
				try {
					Method method = clobName.getMethod("stringValue", null);
					String valstr = (String) method.invoke(titleObject, null);
					subEventsTitle = valstr;
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				subEventsTitle = (String) map.get("TITLE_"); // 获取子页面
			}

			selement = document.getElementById(subEventsName);
			if (selement == null) {
				if (StringUtils.contains(subEventsName, "_0_")) {// 如果此id为列,继续执行//TODO
					System.out.println(subEventsName);
				} else

					continue;
			}

			for (Object object : valueArray) {
				index = valueArray.indexOf(object);
				valueObj = (JSONObject) object;
				subPageId = valueObj.getString("id"); // 子页面 id
				// 获取子页面
				// FormPage formPage = formPageService.getFormPage(subPageId);
				FormPage formPage = formPageMaps.get(subPageId);
				if (formPage != null) {
					Document doc = Jsoup.parse(formPage.getFormHtml());
					subObj = new JSONObject();
					subObj.put("id",
							pageObj.getString("id") + subEventsName + (isGroup ? "" : index) + subPageId + level);
					subObj.put("pId", pageObj.getString("id"));
					subObj.put("level", level);
					subObj.put("klevel", level);
					subObj.put("eleId", subPageId);
					subObj.put("pageId", subPageId);
					subObj.put("name", subEventsTitle + "->" + formPage.getName());
					subObj.put("isPage", true);
					subObj.put("drole", "page");
					retAry.add(subObj);
					if (showEvent) {
						this.addEventsTo(propertyMap, "page", retAry, subObj, null);
					}

					// 获取参数
					subList = subListMaps.get(subPageId);
					// formRulePropertyService.getStuffByPageId(subPageId);

					/*
					 * if (eventsMap.containsKey(subPageId)) {
					 * list.addAll(eventsMap.get(subPageId)); }
					 */

					if (subList != null && subList.size() > 0) {
						// 循环执行
						this.assemble(subList, subObj, retAry, level + 1, propertyMap, showEvent,
								subEventsName + (isGroup ? "" : index), eventsMap, doc, isGroup, formComponentMap);
					}

					// 获取子页面的所有data-role的元素
					Elements elements = doc.getElementsByAttribute("data-role");
					// 更改算法
					if (isGroup) {
						buildGroupElement(elements, subEventsName, level, subObj, subPageId, retAry, showEvent,
								propertyMap, formComponentMap);
					} else {
						for (Element element : elements) {
							if (element.attr("crtltype").startsWith("kendo")) { // 不是layout
																				// 布局的元素
								String name = org.apache.commons.lang.StringUtils.isNotEmpty(element.attr("cname"))
										? element.attr("cname")
										: (org.apache.commons.lang.StringUtils.isNotEmpty(element.attr("title"))
												? element.attr("title")
												: element.attr("name"));

								if (StringUtils.isEmpty(name)) {
									continue;
								}

								// 页面中的 元素
								eleObj = new JSONObject();
								eleObj.put("id", subObj.get("id") + subEventsName + index + element.attr("id") + level);
								eleObj.put("pId", subObj.get("id"));
								eleObj.put("level", level);
								eleObj.put("klevel", level);
								eleObj.put("eleId", element.attr("id"));
								eleObj.put("icon", "/images/cog.png");
								eleObj.put("pageId", subPageId);
								eleObj.put("isEle", true);
								eleObj.put("name", name);

								retAry.add(eleObj);
								role = element.attr("data-role");
								// cell表定义
								if ("cell".equalsIgnoreCase(role)) {
									addCellNode(retAry, eleObj, showEvent, role, null);
								} else if ("gridbt".equalsIgnoreCase(role) || "treelistbt".equalsIgnoreCase(role)) {
									addGridBtNode(retAry, eleObj, showEvent, role, propertyMap, null);
								}
								if (showEvent) {
									this.addEventsTo(propertyMap, role, retAry, eleObj, null);
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 添加gridbt内控件联动功能
	 * 
	 * @param retAry
	 * @param retObj
	 * @param showEvent
	 * @param role
	 */
	private void addGridBtNode(JSONArray retAry, JSONObject retObj, boolean showEvent, String role,
			Map<String, List<Map<String, Object>>> propertyMap, JSONObject pObj) {
		// retObj.put("icon", "/images/table_tab.png");
		retObj.put("isEle", true);
		String pageId = retObj.getString("pageId");
		FormRuleProperty rp = formRulePropertyService.getPageComponentPropertyRule(pageId, role, "dataSourceSet",
				retObj.getString("eleId"));
		if (rp != null) {
			String ruleValue = rp.getValue();
			Integer id = null;
			JSONArray columnsAry = null;
			if (ruleValue != null && !ruleValue.isEmpty()) {
				JSONArray rAry = JSON.parseArray(ruleValue);
				if (rAry != null && !rAry.isEmpty()) {
					JSONObject rObj = rAry.getJSONObject(0);
					if (rObj != null && !rObj.isEmpty()) {
						columnsAry = rObj.getJSONArray("columns");
						if (columnsAry != null && !columnsAry.isEmpty()) {
							JSONObject rootObj = new JSONObject();
							String rootId = retObj.getString("id") + "root";
							rootObj.put("level", retObj.get("level"));
							rootObj.put("klevel", retObj.get("level"));
							rootObj.put("id", rootId);
							rootObj.put("pId", retObj.getString("id"));
							rootObj.put("name", retObj.getString("name") + "(字段)");
							rootObj.put("icon", "/images/table_tab.png");
							rootObj.put("eName", retObj.getString("name") + "(字段)");
							rootObj.put("pObj", pObj != null ? pObj.toJSONString() : retObj.toJSONString());
							rootObj.put("eleId", retObj.getString("eleId"));
							rootObj.put("pageId", retObj.getString("pageId"));
							rootObj.put("isEle", true);
							rootObj.put("oEleId", retObj.getString("eleId"));
							rootObj.put("otype", role);
							retAry.add(rootObj);
							JSONObject columnObj = null;
							String[] editors = { "dropdownlist", "maskedtextbox", "numerictextbox", "checkbox",
									"autocomplete", "timepicker", "datetimepicker", "datepicker", "multiselect",
									"fileupload", "radio" };
							String[] editorRoles = { "metroselect", "textboxbt", "touchspin", "icheckbox",
									"autocomplete", "datepickerbt", "datepickerbt", "datepickerbt", "metroselect_m",
									"jqfileupload2", "icheckradio" };
							List<String> editorArys = Arrays.asList(editors);
							for (Object object : columnsAry) {
								columnObj = (JSONObject) object;

								String subRole = editorRoles[editorArys.indexOf(columnObj.getString("editor"))];

								JSONObject rootEleObj = new JSONObject();
								rootEleObj.put("level", retObj.get("level"));
								rootEleObj.put("klevel", retObj.get("level"));
								rootEleObj.put("id",
										retObj.getString("id") + columnObj.getString("columnName") + "root");
								rootEleObj.put("pId", rootId);
								rootEleObj.put("name", columnObj.getString("title"));
								rootEleObj.put("icon", "/images/cog.png");
								rootEleObj.put("eName", columnObj.getString("title"));
								rootEleObj.put("pObj", JSON.toJSONString(rootObj));
								rootEleObj.put("eleId",
										columnObj.getString("columnName")/*
																			 * retObj.getString( "eleId")
																			 */);
								rootEleObj.put("pageId", retObj.getString("pageId"));
								rootEleObj.put("isEle", true);
								rootEleObj.put("oEleId", retObj.getString("eleId"));
								rootEleObj.put("oEditor", subRole);
								rootEleObj.put("otype", role);
								retAry.add(rootEleObj);

								if (showEvent) {
									// rootEventObj.put("otype", "cell");
									this.addPermissionEvent(propertyMap, subRole, retAry, rootEleObj, role);
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 事件定义器 获取参数列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/getEventsParametersByPageId")
	public byte[] getEventsParametersByPageId(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String pageId = RequestUtils.getString(request, "pageId");
		String inPageIds = RequestUtils.getString(request, "inPages");
		JSONArray retAry = new JSONArray();
		// JSONObject retObj = new JSONObject();
		// 当前页面的控件参数
		List<String> pageIdList = new ArrayList<String>();
		pageIdList.add(pageId);
		List<Map<String, Object>> paramList = jdbcGetStuffByPageIds(pageIdList, "Param");
		// formRulePropertyService.getParametersByPageId(pageId);
		if (paramList != null) {
			retAry.addAll(paramList);
		}

		if (StringUtils.isNotEmpty(inPageIds)) {
			String[] pageIds = inPageIds.split(",");
			pageIdList = new ArrayList<String>();
			for (String string : pageIds) {
				pageIdList.add(string);
			}

			paramList = jdbcGetStuffByPageIds(pageIdList, "Param");
			// formRulePropertyService.getParametersByPageIds(pageIdList);
			if (paramList != null) {
				retAry.addAll(paramList);
			}
			/*
			 * for (String inPageId : pageIds) { paramList =
			 * formRulePropertyService.getParametersByPageId(pageId); if (paramList != null)
			 * { retAry.addAll(paramList); } }
			 */
		}

		/*
		 * List<Map<String, Object>> subPageList = formRulePropertyService
		 * .getStuffByPageId(pageId); if (subPageList != null) { // 联动页面
		 * getEventsParam(subPageList, retAry); }
		 */

		return retAry.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 获取联动页面事件参数
	 * 
	 * @param subPageList
	 * @param retAry
	 */
	private void getEventsParam(List<Map<String, Object>> subPageList, JSONArray retAry) {
		List<Map<String, Object>> paramList = null;
		List<Map<String, Object>> subSubPageList = null;
		JSONArray valueArray = null;
		JSONObject valueObj = null;
		String subPageId = null;

		List<String> subPageIds = new ArrayList<>();
		for (Map<String, Object> subMap : subPageList) {
			String value = (String) subMap.get("VALUE_");
			valueArray = JSON.parseArray(value);
			for (Object object : valueArray) {
				valueObj = (JSONObject) object;
				subPageId = valueObj.getString("id");
				subPageIds.add(subPageId);
			}
		}
		List<Map<String, Object>> parametersByPageIds = jdbcGetStuffByPageIds(subPageIds, "Param");
		// formRulePropertyService.getParametersByPageIds(subPageIds);
		Map<String, List<Map<String, Object>>> paramsPageIds = new HashMap<>();
		if (parametersByPageIds != null && parametersByPageIds.size() > 0) {
			String key = null;
			List<Map<String, Object>> temp = new ArrayList<>();
			for (Map<String, Object> map : parametersByPageIds) {
				key = map.get("PAGEID_").toString();
				if (paramsPageIds.containsKey(key)) {
					paramsPageIds.get(key).add(map);
				} else {
					temp = new ArrayList<>();
					temp.add(map);
					paramsPageIds.put(key, temp);
				}
			}
		}
		List<Map<String, Object>> stuffByPageIds = jdbcGetStuffByPageIds(subPageIds);
		// formRulePropertyService.getStuffByPageIds(subPageIds);
		Map<String, List<Map<String, Object>>> stuffPageIds = new HashMap<>();
		if (stuffByPageIds != null && stuffByPageIds.size() > 0) {
			String key = null;
			List<Map<String, Object>> temp = new ArrayList<>();
			for (Map<String, Object> map : stuffByPageIds) {
				key = map.get("PAGEID_").toString();
				if (stuffPageIds.containsKey(key)) {
					stuffPageIds.get(key).add(map);
				} else {
					temp = new ArrayList<>();
					temp.add(map);
					stuffPageIds.put(key, temp);
				}
			}
		}
		for (Map<String, Object> map : subPageList) {
			String value = (String) map.get("VALUE_");
			valueArray = JSON.parseArray(value);
			for (Object object : valueArray) {
				valueObj = (JSONObject) object;
				subPageId = valueObj.getString("id"); // 子页面 id
				paramList = paramsPageIds.get(subPageId);
				// formRulePropertyService.getParametersByPageId(subPageId);
				if (paramList != null) {
					retAry.addAll(paramList);
				}
				// 再查询页面下的页面
				subSubPageList = stuffPageIds.get(subPageId);
				// formRulePropertyService.getStuffByPageId(subPageId);
				if (subSubPageList != null) {
					// 联动页面
					getEventsParam(subSubPageList, retAry);
				}
			}
		}
	}

	/**
	 * 获取事件属性
	 * 
	 * @return
	 */
	private Map<String, List<Map<String, Object>>> getPropertys(Boolean isTrigger) {
		List<Map<String, Object>> proList = formComponentPropertyService.getEventsProperty(isTrigger ? 1 : 0);
		String role = null;
		List<Map<String, Object>> mapList = null;
		Map<String, List<Map<String, Object>>> retMap = new HashMap<>();
		for (Map<String, Object> map : proList) {
			role = map.get("DATAROLE_").toString();
			if (retMap.containsKey(role)) {
				retMap.get(role).add(map);
			} else {
				mapList = new ArrayList<>();
				mapList.add(map);
				retMap.put(role, mapList);
			}
		}

		return retMap;
	}

	@Resource
	public void setCellDataTableService(CellDataTableService cellDataTableService) {
		this.cellDataTableService = cellDataTableService;
	}

	/**
	 * 权限定义器中 获取当前页面的 所有元素的控制权限
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/getPageControlPermissionDefined")
	public byte[] getPageControlPermissionDefined(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String pageId = RequestUtils.getString(request, "pageId");
		Boolean showEvent = RequestUtils.getBoolean(request, "showEvent");
		JSONArray retAry = new JSONArray();
		JSONObject retObj = new JSONObject();

		// 获取各个控件的权限
		Map<String, List<Map<String, Object>>> propertyMap = getPermissions();

		// 获取根页面的html 根节点
		FormPage formPage = formPageService.getFormPage(pageId);
		retObj = new JSONObject();
		retObj.put("id", pageId);
		retObj.put("pId", "0");
		retObj.put("name", formPage.getName());
		retObj.put("isPage", true);
		retAry.add(retObj);

		// 获取所有data-role的元素 元素节点
		Document doc = Jsoup.parse(formPage.getFormHtml());
		Elements elements = doc.getElementsByAttribute("data-role");
		String role = null;

		for (Element element : elements) {
			if (element.attr("crtltype").startsWith("kendo")) { // 排除layout布局的元素
				// 页面中的 元素
				retObj = new JSONObject();
				retObj.put("id", element.attr("id"));
				retObj.put("pId", pageId);
				retObj.put("icon", "/images/cog.png");
				retObj.put("isEle", true);
				retObj.put("role", element.attr("data-role"));
				retObj.put("name",
						org.apache.commons.lang.StringUtils.isNotEmpty(element.attr("cname")) ? element.attr("cname")
								: (org.apache.commons.lang.StringUtils.isNotEmpty(element.attr("title"))
										? element.attr("title")
										: element.attr("name")));
				retAry.add(retObj);
				if (showEvent) {
					role = element.attr("data-role");
					this.addPermissionEvent(propertyMap, role, retAry, retObj, null);
				}
			}
		}
		return retAry.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 页面控件能控制的权限
	 * 
	 * @param propertyMap
	 * @param role
	 * @param retAry
	 * @param retObj
	 */
	private void addPermissionEvent(Map<String, List<Map<String, Object>>> propertyMap, String role, JSONArray retAry,
			JSONObject retObj, String otype) {
		role = convertRole(role);
		List<Map<String, Object>> mapList = propertyMap.get(role);
		if (mapList == null) {
			mapList = propertyMap.get("default");
		}
		if (mapList != null) {
			String title = null;
			String name = null;
			String callback = null;
			JSONObject eleObj = null;
			for (Map<String, Object> map : mapList) {
				title = map.get("TITLE_").toString();
				name = map.get("NAME_").toString();
				callback = map.get("CALLBACK_") == null ? null : map.get("CALLBACK_").toString();
				eleObj = new JSONObject();
				eleObj.put("id", retObj.getString("id") + name);
				eleObj.put("pId", retObj.getString("id"));
				eleObj.put("name", title);
				eleObj.put("sname", title);
				eleObj.put("role", retObj.getString("role"));
				eleObj.put("icon", "/images/event.png");
				eleObj.put("eName", name);
				eleObj.put("callback", callback);
				eleObj.put("pObj", retObj.toJSONString());
				if (otype != null) {
					eleObj.put("otype", otype);
				}
				eleObj.put("isEvn", true);
				retAry.add(eleObj);
			}
		}
	}

	private Map<String, List<Map<String, Object>>> getPermissions() {
		// 需要配置到数据库中
		Map<String, List<Map<String, Object>>> map = new HashMap<String, List<Map<String, Object>>>();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> subMap = new HashMap<String, Object>();
		subMap.put("NAME_", "toHide");
		subMap.put("TITLE_", "隐藏");
		list.add(subMap);
		/*
		 * subMap = new HashMap<String, Object>(); subMap.put("NAME_", "toShow");
		 * subMap.put("TITLE_", "显示"); list.add(subMap);
		 */
		subMap = new HashMap<String, Object>();
		subMap.put("NAME_", "toDisable");
		subMap.put("TITLE_", "禁用");
		list.add(subMap);
		/*
		 * subMap = new HashMap<String, Object>(); subMap.put("NAME_", "toEnable");
		 * subMap.put("TITLE_", "启用"); list.add(subMap);
		 */
		map.put("default", list);
		// 选卡特殊模式
		list = new ArrayList<Map<String, Object>>();
		subMap = new HashMap<String, Object>();
		subMap.put("NAME_", "toHide");
		// subMap.put("selectSub", true); //
		subMap.put("CALLBACK_", "tabstripToHide"); // 是否使用回调函数。
		subMap.put("TITLE_", "隐藏");
		list.add(subMap);
		subMap = new HashMap<String, Object>();
		subMap.put("NAME_", "toDisable");
		subMap.put("TITLE_", "禁用");
		list.add(subMap);
		map.put("tabstrip", list);
		return map;
	}

	/**
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/searchBySql")
	public byte[] searchBySql(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String sql = RequestUtils.getString(request, "sql");
		if (StringUtils.isBlank(sql)) {
			return null;
		}
		String systemName = RequestUtils.getString(request, "systemName", Environment.DEFAULT_SYSTEM_NAME);
		Integer page = RequestUtils.getInteger(request, "page", 1);
		Integer rows = RequestUtils.getInteger(request, "rows", 0);
		// JSONObject result = this.mutilDatabaseBean.queryBySqlCriteria(sql,
		// page, rows, systemName);

		JSONObject result = this.mutilDatabaseBean.queryBySqlCriteria(sql, page, rows, systemName, //
				RequestUtils.getString(request, "orderBy", ""), RequestUtils.getParameterMap(request));

		PageResult pr = new PageResult();

		pr.setCurrentPageNo(page);
		pr.setPageSize(rows);
		pr.setResults(result.getJSONArray("rows"));
		pr.setTotalRecordCount(result.getIntValue("total"));

		String jsonStr = JSON.toJSONString(pr);

		return jsonStr.getBytes("UTF-8");
	}

	/**
	 * 表管理，预览数据
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/ex/preview")
	public ModelAndView preview(HttpServletRequest request, ModelMap modelMap) {
		String tableName = RequestUtils.getString(request, "tableName");
		if (StringUtils.isEmpty(tableName)) {
			throw new RuntimeException("tableName 不能为空!");
		}
		String systemName = RequestUtils.getString(request, "systemName", Environment.DEFAULT_SYSTEM_NAME);
		String tableId = RequestUtils.getString(request, "tableId");
		Boolean R = RequestUtils.getBoolean(request, "R");

		JSONArray array = cellDataFieldService.getInterfaceColumnDefinitions(systemName, tableName, tableId, R);

		request.setAttribute("columns", array);

		return gotoView(request, "ex/preview");
	}

	@Autowired
	protected RepositoryService repositoryService;

	@RequestMapping("/ex/multiFormStart")
	public ModelAndView multiForm(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap)
			throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String defId = RequestUtils.getString(request, "defId");
		String processId = RequestUtils.getString(request, "processId");
		Map<String, JSONObject> maps = new HashMap<String, JSONObject>();
		if (StringUtils.isNotBlank(processId)) {
			FormTaskmainQuery query = new FormTaskmainQuery();
			query.setProcessId(processId);
			List<FormTaskmain> list = this.formTaskmainService.list(query);
			if (CollectionUtils.isNotEmpty(list)) {
				FormTaskmain main = list.get(0);
				List<FormTask> tasks = main.getFormTasks();

				/**
				 * 判断是否为汇合流程, 并取出所有汇合前的表单数据
				 */
				if (StringUtils.isNotBlank(main.getP_processId())) {
					//
					query.setProcessId(null);
					query.setP_processId(main.getP_processId());
					list = this.formTaskmainService.list(query);
					if (CollectionUtils.isNotEmpty(list)) {
						list.forEach(e -> {
							if (StringUtils.equalsIgnoreCase(//
									e.getProcessId(), processId)) {
								return;
							}
							tasks.addAll(e.getFormTasks());
						});
					}
				}

				if (CollectionUtils.isNotEmpty(tasks)) {
					for (FormTask task : tasks) {
						maps.put(task.getPageId(), task.toJsonObject());
					}
				}
			}
		}

		if (StringUtils.isNotBlank(defId)) {
//			Integer version = this.formWorkflowPlanService.getNextVersionByDefId(defId);
//			FormWorkflowPlanQuery query = new FormWorkflowPlanQuery();
//			query.setDefId(defId);
//			query.setVersion(version - 1);
//			List<FormWorkflowPlan> list = this.formWorkflowPlanService.list(query);
			
			List<FormWorkflowPlan> list = formWorkflowPlanService.getPlans(defId);
			
			Map<String, Set<String>> relation = this.formWorkflowPlanService.getRelationPage(list);
			if (MapUtils.isNotEmpty(maps)) {
				Map<String, JSONObject> maps_ = new HashMap<>();
				for (String key : maps.keySet()) {
					if (relation.containsKey(key)) {
						Set<String> set = relation.get(key);
						JSONObject o = maps.get(key), json;
						for (String p : set) {
							if (!p.equals(key)) {
								json = new JSONObject();
								json.putAll(o);
								json.put("pageId", p);
								maps_.put(p, json);
							}
						}
					}
				}
				maps.putAll(maps_);
			}

			JSONArray pages = new JSONArray();
			FormWorkflowPlan plan;
			String flowDefinedKey = "flowDefined";
			if (CollectionUtils.isNotEmpty(list)) {
				JSONObject jsonObject, json;
//				FormWorkFlowRuleQuery ruleQuery = new FormWorkFlowRuleQuery();
//				ruleQuery.setDefId(defId);
//				ruleQuery.setVersion(version - 1);
//				List<FormWorkFlowRule> rules = formWorkFlowRuleService.list(ruleQuery);
				
				List<FormWorkFlowRule> rules = list.get(0).getRules();
				
				Map<String, Object> params = Global.getGlobalVariables();

				Map<String, JSONObject> pp = new HashMap<>();
				for (FormWorkflowPlan p : list) {
					if (StringUtils.isEmpty(p.getBytes()))
						continue;
					pp.put(p.getPageId(), JSONArray.parseArray(p.getBytes()).getJSONObject(0));
				}

				plan = list.get(0);
				if (CollectionUtils.isNotEmpty(rules)) {

					/**
					 * 没有实例时，取出开始节点的配置信息
					 */
					Map<String, String> tasksMap = new HashMap<String, String>();
					if (StringUtils.isEmpty(processId)) {
						ProcessDefinitionEntity def = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
								.getDeployedProcessDefinition(plan.getProcessDefId());
						List<ActivityImpl> impls = def.getActivities();
						if (CollectionUtils.isNotEmpty(impls)) {
							for (ActivityImpl ai : impls) {
								Map<String, Object> properties = ai.getProperties();
								// System.out.println(properties);
								if (properties != null) {
									if (properties.containsKey("type"))
										tasksMap.put(ai.getId(), properties.get("type").toString());
								}
							}
						}
					}
					Boolean start = false;
					for (FormWorkFlowRule rule : rules) {

						json = maps.get(rule.getPageId());
						if (json != null) {
							json.put("rules", rule.getBytes());
						}

						start = StringUtils.isEmpty(processId)
								&& StringUtils.startsWithIgnoreCase(tasksMap.get(rule.getActTaskId()), "start");

						if (start) {
							JSONObject collection = new JSONObject();
							// JSONObject processDefined = new JSONObject();
							JSONObject processDefined = pp.get(rule.getPageId());// JSONArray.parseArray(plan.getBytes()).getJSONObject(0);

							TaskItem ti = new TaskItem();

							ti.setTaskName(rule.getActTaskName());
							ti.setTaskDefinitionKey(rule.getActTaskId());

							FormDataController.populateFlow(rule, processDefined, defId, ti, params);

							collection.put(flowDefinedKey, processDefined);

							maps.put(rule.getPageId(), collection);
						}
					}
				}

				for (FormWorkflowPlan p : list) {
					jsonObject = p.toJsonObject();
					json = maps.get(p.getPageId());
					if (json != null) {
						jsonObject.putAll(json);
					}
					if (!jsonObject.containsKey(flowDefinedKey)) {

						JSONObject processDefined = pp.get(p.getPageId());
						if (processDefined != null) {
							processDefined.put("kkkkkk", true);
						}

						jsonObject.put(flowDefinedKey, processDefined);
					}
					pages.add(jsonObject);
				}
			}

			modelMap.put("pages", pages);
		}
		return new ModelAndView("/form/defined/ex/MultiForm", modelMap);
	}

	@Resource
	public void setMutilDatabaseBean(MutilDatabaseBean mutilDatabaseBean) {
		this.mutilDatabaseBean = mutilDatabaseBean;
	}

	@Resource
	public void setDepReportCellService(DepReportCellService depReportCellService) {
		this.depReportCellService = depReportCellService;
	}

	@javax.annotation.Resource
	public void setDatabaseService(IDatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	@Resource
	public void setFormComponentPropertyService(FormComponentPropertyService formComponentPropertyService) {
		this.formComponentPropertyService = formComponentPropertyService;
	}

	@Resource
	public void setFormComponentService(FormComponentService formComponentService) {
		this.formComponentService = formComponentService;
	}

	@Resource
	public void setFormPageService(FormPageService formPageService) {
		this.formPageService = formPageService;
	}

	@Resource
	public void setFormRulePropertyService(FormRulePropertyService formRulePropertyService) {
		this.formRulePropertyService = formRulePropertyService;
	}

	@Resource
	public void setFormRuleService(FormRuleService formRuleService) {
		this.formRuleService = formRuleService;
	}

	@Resource
	public void setDataSetService(DataSetService dataSetService) {
		this.dataSetService = dataSetService;
	}

	@Resource
	public void setDataSetAuditService(DataSetAuditService dataSetAuditService) {
		this.dataSetAuditService = dataSetAuditService;
	}

	@Resource
	public void setFormVideoService(FormVideoService formVideoService) {
		this.formVideoService = formVideoService;
	}

	@Resource
	public void setFormWorkflowPlanService(FormWorkflowPlanService formWorkflowPlanService) {
		this.formWorkflowPlanService = formWorkflowPlanService;
	}

	@Resource
	public void setFormTaskmainService(FormTaskmainService formTaskmainService) {
		this.formTaskmainService = formTaskmainService;
	}

	@Resource
	public void setFormTaskService(FormTaskService formTaskService) {
		this.formTaskService = formTaskService;
	}

	/**
	 * 根据数据库code 获取数据库信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/getDatabaseInfo")
	public byte[] getDatabaseInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String code = request.getParameter("databaseCode");
		Database database = null;
		JSONObject retObj = new JSONObject();
		JSONObject obj = new JSONObject();
		if (code != null) {
			// database = databaseService.getDatabaseByCode(code);
			database = databaseService.getDatabaseById(Long.parseLong(code));
		}
		if (database != null) {
			obj.put("ip", database.getHost());
			obj.put("dbname", database.getDbname());
			obj.put("dbtype", database.getType());
			obj.put("dbu", database.getUser());
			obj.put("dbp", database.getPassword());
			obj.put("key", database.getKey());
		} else {
			Properties props = DBConfiguration.getDefaultDataSourceProperties();
			String url = props.getProperty("jdbc.url");
			String[] strs = url.split(":");
			obj.put("ip", strs[2].replace("//", ""));
			obj.put("dbname", strs[3].split("=")[1]);
			obj.put("dbtype", strs[1]);
			obj.put("dbu", props.getProperty("jdbc.user"));
			String password = props.getProperty("jdbc.password");
			String key = SecurityUtils.genKey();
			obj.put("dbp", SecurityUtils.encode(key, password));
			obj.put("key", key);
		}
		String key = SecurityUtils.genKey();
		String content = SecurityUtils.encode(key, obj.toJSONString());
		retObj.put("key", key);
		retObj.put("content", content);
		return retObj.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 获取输入表达式
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/getParametersByExprCalc")
	public byte[] getParametersByExprCalc(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Long id = RequestUtils.getLong(request, "id", 0);
		Boolean isCallback = RequestUtils.getBoolean(request, "isCallback");

		JSONArray retAry = new JSONArray();
		DepReportTemplate depReportTemplate = depReportTemplateService.getDepReportTemplate(id);
		String ruleJson = depReportTemplate.getRuleJson();
		if (ruleJson != null && !ruleJson.isEmpty()) {
			JSONObject ruleObj = JSON.parseObject(ruleJson);
			Set<String> keys = ruleObj.keySet();
			String inParamCode = "A000-1-1-003";
			String inParamRuleId = "72916ca7b6a74e089818e69c08c3781f";
			String paramsDefined = "params-defined";
			String nameCode = "A001-1-002";
			String roleCode = "A001-1-001";
			JSONObject rlObj;
			String inParamStr = null;

			JSONArray ary;
			JSONObject inParamObj;
			JSONObject datasObj;
			JSONArray valAry;
			JSONObject valObj = null;

			JSONObject retObj;
			rlObj = ruleObj.getJSONObject("page");
			inParamStr = rlObj.getString(inParamCode);
			if (StringUtils.isNotEmpty(inParamStr) && !isCallback) {
				ary = JSON.parseArray(inParamStr);
				if (ary != null && !ary.isEmpty()) {
					retObj = new JSONObject();
					inParamObj = ary.getJSONObject(0);
					datasObj = inParamObj.getJSONObject("datas");
					valAry = datasObj.getJSONArray(inParamRuleId).getJSONObject(0).getJSONArray("val");
					for (Object object : valAry) {
						retObj = new JSONObject();
						valObj = (JSONObject) object;
						retObj.put("name", valObj.getString("name"));
						retObj.put("param", valObj.getString("param"));
						retObj.put("datasetId", valObj.getString("datasetId"));

						retAry.add(retObj);
					}
				}
			}

			String paramsDefinedStr = rlObj.getString(paramsDefined);
			if (StringUtils.isNotEmpty(paramsDefinedStr)) {
				ary = JSON.parseArray(paramsDefinedStr);
				JSONObject col2Obj = null;
				JSONObject col3Obj = null;
				String linkageControlStr = null;
				if (ary != null && !ary.isEmpty()) {
					for (Object object : ary) {
						valObj = (JSONObject) object;
						col2Obj = valObj.getJSONObject("col-2");
						col3Obj = valObj.getJSONObject("col-3");
						if ((isCallback ? "output" : "input").equalsIgnoreCase(col3Obj.getString("name"))) {
							linkageControlStr = col2Obj.getJSONObject("data").getString("linkageControl");
							if (StringUtils.isNotEmpty(linkageControlStr)) {
								JSONArray lcAry = JSON.parseArray(linkageControlStr);
								for (Object object2 : lcAry) {
									JSONObject lcObj = (JSONObject) object2;

									retObj = new JSONObject();
									retObj.put("name", lcObj.getString("text"));
									retObj.put("param", lcObj.getString("id"));

									retAry.add(retObj);
								}
							}
						}
					}

				}
			}

		}
		return retAry.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 获取输入表达式
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/getParametersByCrud")
	public byte[] getParametersByCrud(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Long id = RequestUtils.getLong(request, "id", 0);
		Boolean isCallback = RequestUtils.getBoolean(request, "isCallback");
		if (!isCallback) {
			// retAry = depBaseWdataSetService.getColumnsBySelected(id);
			return depBaseWdataSetService.getWdataSetCud(id).toJSONString().getBytes("UTF-8");
		} else {
			return depBaseWdataSetService.getColumns(id).toJSONString().getBytes("UTF-8");
		}
	}

	/**
	 * 获取输入表达式
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/getParametersBySqliteRule")
	public byte[] getParametersBySqliteRule(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Long id = RequestUtils.getLong(request, "id", 0);
		Boolean isCallback = RequestUtils.getBoolean(request, "isCallback");
		if (!isCallback) {
			// retAry = depBaseWdataSetService.getColumnsBySelected(id);
			WdatasetSqllite wdatasetSqllite = wdatasetSqlliteService.getWdatasetSqllite(request.getParameter("id"));
			String ruleStr = wdatasetSqllite.getRuleJson();
			DataSetAudit dataSetAudit;
			JSONArray params = new JSONArray();
			if (ruleStr != null && !ruleStr.isEmpty()) {
				JSONArray ruleJsonAry = JSON.parseArray(ruleStr);
				for (Object ruleObj : ruleJsonAry) {
					JSONObject ruleJson = (JSONObject) ruleObj;
					String datasetId = ruleJson.getString("dataSetId");
					dataSetAudit = dataSetAuditService.getLastestDataSetAudit(datasetId);
					if (dataSetAudit != null) {
						String content = dataSetAudit.getContent();
						if (StringUtils.isNotBlank(content)) {
							JSONObject jsonObject = JSON.parseObject(content);
							if (jsonObject.getString("params") != null) {
								JSONArray paramsAry = jsonObject.getJSONArray("params");
								// 给params增加 datasetId 标识
								JSONObject paramsObj = null;
								for (Object object : paramsAry) {
									paramsObj = (JSONObject) object;
									paramsObj.put("datasetId", datasetId);
								}
								params.addAll(paramsAry);
							}
						}
					}
				}
				return params.toJSONString().getBytes("UTF-8");
			}
			return "".getBytes("UTF-8");
		} else {
			return "".getBytes("UTF-8");
			// return
			// wdatasetSqlliteService.getColumns(id).toJSONString().getBytes("UTF-8");
		}
	}

	@ResponseBody
	@RequestMapping("/refreshMetadata")
	public byte[] refreshMetadata(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String p = RequestUtils.getString(request, "p", "*");
		List<String> packages = null;
		if (p.equalsIgnoreCase("*")) {
			packages = null;
		} else {
			packages = Arrays.asList(StringUtils.split(p, ","));
		}
		RefreshDBMetadata.refresh(packages);
		return ResponseUtils.responseJsonResult(true);
	}

	/**
	 * 初始化页面控件
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/initPageComponent")
	public byte[] initPageComponent(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		User user = RequestUtils.getUser(request);

		// 页面Id
		String pageId = RequestUtils.getString(request, "id");

		// 根据页面id获取页面信息
		FormPage formPage = formPageService.getFormPage(pageId);
		if (formPage != null) {
			try {
				// 获取页面HTML信息
				Document doc = Jsoup.parse(formPage.getFormHtml());
				/**
				 * 首先检测模板控件 start
				 */
				Elements tempEles = doc.getElementsByAttribute("data-templateId");
				FormRuleQuery formRuleQuery = new FormRuleQuery();
				List<FormRule> formRules = null;
				Map<String, FormRule> ruleMaps = new HashMap<>();
				Map<String, FormRule> pageRuleMaps = new HashMap<>();
				if (!tempEles.isEmpty()) {
					List<String> tempIds = new ArrayList<>();
					for (Element element : tempEles) {
						tempIds.add(element.attr("data-templateId"));
					}
					formRuleQuery.setPageIds(tempIds);
					formRuleQuery.setLocked(0);// 有效
					formRuleQuery.deleteFlag(0);// 无删除
					formRules = formRuleService.list(formRuleQuery);
					List<FormRule> list = new ArrayList<>();
					for (FormRule formRule : formRules) {
						if (formRule.getPageId().equalsIgnoreCase(formRule.getName())) {
							pageRuleMaps.put(formRule.getName(), formRule);
						} else {
							ruleMaps.put(formRule.getName(), formRule);
						}
					}
				}
				/**
				 * 首先检测模板控件 end
				 */

				// 获取包含role属性的元素
				Elements elements = doc.getElementsByAttribute("data-role");
				// 获取页面对应的所有规则信息，包括控件
				formRuleQuery = new FormRuleQuery();
				formRuleQuery.setPageId(formPage.getId());
				formRuleQuery.setLocked(0);// 有效
				formRuleQuery.deleteFlag(0);// 无删除
				formRules = formRuleService.list(formRuleQuery);

				String realPath = request.getSession().getServletContext().getRealPath("/");
				String filePath = realPath + "/WEB-INF/conf/templates/component/componentInit.json";
				// 获取规则模板
				String componentRuleTmpl = "";
				java.nio.file.Path pathFrom = java.nio.file.Paths.get(filePath);
				JSONObject componentRuleJson = null;

				if (java.nio.file.Files.exists(pathFrom)) {
					componentRuleTmpl = new String(java.nio.file.Files.readAllBytes(pathFrom), "utf-8");
				}
				if (!componentRuleTmpl.isEmpty()) {
					componentRuleJson = JSONObject.parseObject(componentRuleTmpl);
				}
				if (componentRuleJson == null) {
					return ResponseUtils.responseJsonResult(false, "无法获取控件初始化模板!");
				}

				// 获取所有控件信息
				FormComponentQuery query = new FormComponentQuery();
				List<FormComponent> fcList = formComponentService.list(query);

				Pattern pattern = Pattern.compile("\\{\\{\\w+\\}\\}");

				List<String> ruleIdList = new ArrayList<>();
				// 遍历规则删除无效的控件规则
				boolean flag = true; // 是否是已删除的控件
				FormRule pageRule = null;
				for (FormRule formrule : formRules) {
					flag = true;
					if (pageId.equalsIgnoreCase(formrule.getName())) {
						pageRule = formrule;
					}
					// 首先判断是否存在模板规则信息
					if (ruleMaps.containsKey(formrule.getName())) {
						flag = false;
						String tempPageId = ruleMaps.get(formrule.getName()).getPageId();
						// 移除已经存在的页面规则信息
						if (pageRuleMaps.containsKey(tempPageId)) {
							pageRuleMaps.remove(tempPageId);
						}
						// 移除已经存在的控件信息
						ruleMaps.remove(formrule.getName());

						Elements select = doc.select("#" + formrule.getName());
						if (elements.contains(select.get(0))) {
							elements.remove(select.get(0));
						}
						continue;
					}

					// 判断是否已经存在对应的规则信息
					for (int i = 0; i < elements.size();) {
						Element element = elements.get(i);
						String elementId = element.id();
						if (elementId != null
								&& (elementId.equals(formrule.getName()) || pageId.equals(formrule.getName()))) {
							elements.remove(i);
							flag = false;
							break;
						} else {
							i++;
						}
					}

					if (flag) {
						ruleIdList.add(formrule.getId());
					}
				}

				// 该控件已删除，删除已存在的规则
				formRuleService.deleteByRuleIds(ruleIdList);
				formRulePropertyService.deleteByRuleIds(ruleIdList);

				/**
				 * 模板规则 插入 start
				 */
				// 遍历模板控件规则
				Set<String> keySet = ruleMaps.keySet();
				for (String key : keySet) {
					FormRule formrule = ruleMaps.get(key);
					formrule.setId(null);
					formrule.setPageId(pageId);
					formrule.setCreateBy("sys_tmp_" + formrule.getCreateBy());
					formrule.setCreateDate(new Date());
					formRuleService.save(formrule);
					formRulePropertyService.isRuleProperties(formrule);
					formRulePropertyService.saveComExt(formrule);

					Elements select = doc.select("#" + formrule.getName());
					if (elements.contains(select.get(0))) {
						elements.remove(select.get(0));
					}
				}

				if (!pageRuleMaps.isEmpty()) {
					if (pageRule == null) {
						pageRule = new FormRule();
						pageRule.setName(pageId);
						pageRule.setPageId(pageId);
						pageRule.setComponentId(38l);
						pageRule.setCreateBy("sys_tmp_system");
						pageRule.setCreateDate(new Date());
						pageRule.setValue("{\"296\": \"\",\"301\": \"" + formPage.getName()
								+ "\",\"310\": \"\",\"311\": \"\",\"312\": \"\",\"409\": \"\",\"455\": \"\",\"1438\": \"\",\"8538\": \"\",\"15041\": \"\",\"17540\": \"\",\"18738\": \"false\",\"22238\": \"1\",\"22239\": \"3\",\"22240\": \"\",\"25338\": \"\"}");
					}
					String pageValStr = pageRule.getValue();
					JSONObject pageValObj = JSON.parseObject(pageValStr);

					// 输入形参
					String inParamDefined = "310";
					String inParamDefinedStr = pageValObj.getString(inParamDefined);
					JSONArray inParamDefinedAry = StringUtils.isNotEmpty(inParamDefinedStr)
							? JSON.parseArray(inParamDefinedStr)
							: JSON.parseArray(
									"[{\"name\": \"参数已定义\",\"type\": \"mutil\",\"source\": [],\"objSource\": [],\"arySource\": []}]");
					pageValObj.put(inParamDefined, inParamDefinedAry);
					// 事件定义器
					String eventsDefined = "1438";
					String eventsDefinedStr = pageValObj.getString(eventsDefined);
					JSONArray eventsDefinedAry = StringUtils.isNotEmpty(eventsDefinedStr)
							? JSON.parseArray(eventsDefinedStr)
							: JSON.parseArray("[{\"name\":\"事件已定义\",\"values\":[]}]");
					pageValObj.put(eventsDefined, eventsDefinedAry);
					// 数据集
					String dataset = "296";
					String datasetStr = pageValObj.getString(dataset);
					JSONArray datasetAry = StringUtils.isNotEmpty(datasetStr) ? JSON.parseArray(datasetStr)
							: JSON.parseArray(
									"[{\"name\": \"数据集\",\"title\": \"数据集\",\"columns\": [],\"datasource\": [],\"tables\": []}]");
					pageValObj.put(dataset, datasetAry);
					// 输入输出关系
					String paraType = "311";
					String paraTypeStr = pageValObj.getString(paraType);
					JSONArray paraTypeAry = StringUtils.isNotEmpty(paraTypeStr) ? JSON.parseArray(paraTypeStr)
							: JSON.parseArray(
									"[{\"name\": \"输入输出关系\",\"isDynamic\": \"\",\"dynamicName\": \"\",\"eleId\": \"\",\"datas\": {},\"sort\": false}]");
					pageValObj.put(paraType, paraTypeAry);
					// 输入输出关系
					String validate = "8538";
					String validateStr = pageValObj.getString(validate);
					JSONArray validateAry = StringUtils.isNotEmpty(validateStr) ? JSON.parseArray(validateStr)
							: JSON.parseArray("[{\"name\": \"验证器已定义\",\"nodes\": []}]");
					pageValObj.put(validate, validateAry);
					// 保存设置
					String saveSourceSet = "17540";
					String saveSourceSetStr = pageValObj.getString(saveSourceSet);
					JSONArray saveSourceSetAry = StringUtils.isNotEmpty(saveSourceSetStr)
							? JSON.parseArray(saveSourceSetStr)
							: JSON.parseArray("[]");
					pageValObj.put(saveSourceSet, saveSourceSetAry);

					FormRule pageTempRule = null;
					String pageTempValStr = null;
					JSONObject pageTempRuleObj = null;
					for (String pageRuleKey : pageRuleMaps.keySet()) {
						pageTempRule = pageRuleMaps.get(pageRuleKey);
						// 统一转换原来的页面ID为新的当前页面的页面ID
						pageTempValStr = pageTempRule.getValue().replaceAll(pageRuleKey, pageId);
						pageTempRuleObj = JSONObject.parseObject(pageTempValStr);
						/*
						 * 数据集
						 */
						String datasetTempStr = pageTempRuleObj.getString(dataset);
						if (StringUtils.isNotEmpty(datasetTempStr)) {
							JSONArray datasetTempAry = JSON.parseArray(datasetTempStr);
							JSONObject sourceObj = datasetAry.getJSONObject(0);
							JSONObject targetObj = datasetTempAry.getJSONObject(0);
							sourceObj.getJSONArray("columns").addAll(targetObj.getJSONArray("columns"));
							sourceObj.getJSONArray("datasource").addAll(targetObj.getJSONArray("datasource"));
							sourceObj.getJSONArray("tables").addAll(targetObj.getJSONArray("tables"));
						}
						/*
						 * 输入形参
						 */
						String inParamDefinedTempStr = pageTempRuleObj.getString(inParamDefined);
						if (StringUtils.isNotEmpty(inParamDefinedTempStr)) {
							JSONArray inParamDefinedTempAry = JSON.parseArray(inParamDefinedTempStr);
							JSONObject sourceObj = inParamDefinedAry.getJSONObject(0);
							JSONObject targetObj = inParamDefinedTempAry.getJSONObject(0);
							if (sourceObj.containsKey("source")) {
								sourceObj.getJSONArray("source").addAll(targetObj.getJSONArray("source"));
								sourceObj.getJSONArray("objSource").addAll(targetObj.getJSONArray("objSource"));
								sourceObj.getJSONArray("arySource").addAll(targetObj.getJSONArray("arySource"));
							}
						}
						/*
						 * 验证定义器
						 */
						String validateTempStr = pageTempRuleObj.getString(validate);
						if (StringUtils.isNotEmpty(validateTempStr)) {
							JSONArray validateTempAry = JSON.parseArray(validateTempStr);
							validateAry.getJSONObject(0).getJSONArray("nodes")
									.addAll(validateTempAry.getJSONObject(0).getJSONArray("nodes"));
						}
						/*
						 * 输入输出关系
						 */
						String paraTypeTempStr = pageTempRuleObj.getString(paraType);
						if (StringUtils.isNotEmpty(paraTypeTempStr)) {
							JSONArray paraTypeTempAry = JSON.parseArray(paraTypeTempStr);
							paraTypeAry.getJSONObject(0).getJSONObject("datas")
									.putAll(paraTypeTempAry.getJSONObject(0).getJSONObject("datas"));
						}
						/*
						 * 事件定义器
						 */
						String eventsDefinedTempStr = pageTempRuleObj.getString(eventsDefined);
						if (StringUtils.isNotEmpty(eventsDefinedTempStr)) {
							JSONArray eventsDefinedTempAry = JSON.parseArray(eventsDefinedTempStr);
							eventsDefinedAry.getJSONObject(0).getJSONArray("values")
									.addAll(eventsDefinedTempAry.getJSONObject(0).getJSONArray("values"));
						}
						/*
						 * 保存设置
						 */
						String saveSourceSetTempStr = pageTempRuleObj.getString(saveSourceSet);
						if (StringUtils.isNotEmpty(saveSourceSetTempStr)) {
							saveSourceSetAry.addAll(JSON.parseArray(saveSourceSetTempStr));
						}
					}
					pageRule.setValue(pageValObj.toJSONString());
					formRuleService.save(pageRule);
					formRulePropertyService.isRuleProperties(pageRule);
					formRulePropertyService.saveComExt(pageRule);
				}
				/**
				 * 模板规则 插入 end
				 */

				// 遍历包含role属性的元素，找到对应的规则信息，没有的时候初始化
				for (Element element : elements) {
					flag = true;
					String elementId = element.id();
					// 没有任何规则信息，进行初始化操作。
					String dataRole = element.attr("data-role");
					String elementRuleStr = componentRuleJson.getString(dataRole);
					if (elementRuleStr != null && !elementRuleStr.isEmpty()) {
						Matcher matcher = pattern.matcher(elementRuleStr);
						StringBuffer sbuf = new StringBuffer();
						while (matcher.find()) {
							String str = matcher.group();
							String replace = "";
							if (str.equals("{{ramdom}}")) {
								replace = "col" + System.currentTimeMillis();
							} else {
								str = str.substring(2, str.length() - 2);
								replace = element.attr(str);
							}
							if (replace != null && !replace.isEmpty()) {
								matcher.appendReplacement(sbuf, replace);
							} else if (StringUtils.equals(str, "cname")) {
								matcher.appendReplacement(sbuf, "");
							}
						}
						matcher.appendTail(sbuf);
						// System.out.println(sbuf);
						FormRule formRule = new FormRule();
						formRule.setCreateBy(user.getActorId());
						formRule.setCreateDate(new Date());
						formRule.setPageId(pageId);
						formRule.setValue(sbuf.toString());
						formRule.setName(element.id());
						// 遍历组件列表，获取对应的组件信息
						for (FormComponent formComponent : fcList) {
							if (formComponent.getDataRole() != null && formComponent.getDataRole().equals(dataRole)) {
								formRule.setComponentId(formComponent.getId());
								break;
							}
						}
						formRuleService.save(formRule);
						formRulePropertyService.isRuleProperties(formRule);
						formRulePropertyService.saveComExt(formRule);

					}
				}
				return ResponseUtils.responseResult(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return ResponseUtils.responseResult(false);
	}

	/**
	 * 复制页面控件规则
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/copyPageComponentRule")
	public byte[] copyPageComponentRule(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String fromPageId = RequestUtils.getString(request, "fromPageId");
		String fromComponentName = RequestUtils.getString(request, "fromComponentName");
		String toComponentName = RequestUtils.getString(request, "toComponentName");
		String toRuleId = RequestUtils.getString(request, "toRuleId");

		FormRule fromComponentRule = null;
		FormRule toComponentRule = null;
		// 获取源规则信息
		FormRuleQuery formRuleQuery = new FormRuleQuery();
		formRuleQuery.setPageId(fromPageId);
		formRuleQuery.setName(fromComponentName);
		List<FormRule> list = formRuleService.list(formRuleQuery);
		if (list != null && list.size() > 0) {
			fromComponentRule = list.get(0);
		}
		if (fromComponentRule != null) {
			// 获取目标规则信息
			toComponentRule = formRuleService.getFormRule(toRuleId);
			if (toComponentRule != null) {
				if (fromComponentRule.getId().equals(toComponentRule.getId())) {
					return ResponseUtils.responseJsonResult(false, "源表与目标表一样，不可复制！");
				}
				FormRuleProperty test = new FormRuleProperty();

				String fromComponentRuleValue = fromComponentRule.getValue();
				JSONObject fromComponentRuleValueJSON = null;
				JSONObject toComponentRuleValueJSON = JSON.parseObject(toComponentRule.getValue());
				// 替换ID
				fromComponentRuleValue = fromComponentRuleValue.replaceAll(fromComponentName, toComponentName);
				fromComponentRuleValueJSON = JSON.parseObject(fromComponentRuleValue);
				// 找到目标规则对应的控件属性（name）
				FormComponentPropertyQuery query = new FormComponentPropertyQuery();
				query.setComponentId(toComponentRule.getComponentId());
				query.setName("html");
				List<FormComponentProperty> formComponentPropertyList = formComponentPropertyService.list(query);
				if (formComponentPropertyList != null && formComponentPropertyList.size() > 0) {
					FormComponentProperty formComponentProperty = formComponentPropertyList.get(0);
					String propertyId = String.valueOf(formComponentProperty.getId());
					// 替换对应的规则名称
					fromComponentRuleValueJSON.put(propertyId, toComponentRuleValueJSON.getString(propertyId));
					fromComponentRuleValue = fromComponentRuleValueJSON.toJSONString();
				} else {
					return ResponseUtils.responseJsonResult(false, "无法找到对应的控件属性！");
				}
				// 拷贝到对应的规则去
				toComponentRule.setValue(fromComponentRuleValue);
				formRuleService.save(toComponentRule);
				// 删除对应的规则
				formRulePropertyService.deleteByRuleId(toRuleId);
				formRulePropertyService.isRuleProperties(toComponentRule);
				formRulePropertyService.saveComExt(toComponentRule);
				return ResponseUtils.responseJsonResult(true);
			} else {
				return ResponseUtils.responseJsonResult(false, "无法获取目标控件规则");
			}
		} else {
			return ResponseUtils.responseJsonResult(false, "无法获取源控件规则");
		}
	}

	/**
	 * 复制页面控件规则
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/bulckCopyPageComponentRule")
	public byte[] bulckCopyPageComponentRule(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		return ResponseUtils.responseJsonResult(false, "无法获取源控件规则");
	}

	/**
	 * 获取批量任务参数列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/getParametersByTaskTable")
	public byte[] getParametersByTaskTable(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		Long id = RequestUtils.getLong(request, "id", 0);
		Boolean isCallback = RequestUtils.getBoolean(request, "isCallback");
		TaskTable taskTable = taskTableService.getTaskTable(id);
		String tableName = taskTable.getTableName();

		List<ColumnDefinition> columns = TableFactory.getColumnDefinitions(0, tableName);
		JSONArray retAry = new JSONArray();
		JSONObject retObj = null;
		for (ColumnDefinition columnDefinition : columns) {
			retObj = new JSONObject();
			retObj.put("columnName", columnDefinition.getColumnName());
			retObj.put("columnLabel", columnDefinition.getColumnName());
			retAry.add(retObj);
		}

		return retAry.toJSONString().getBytes("UTF-8");
	}

	/**
	 * 获取逐级汇总参数
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/getParametersByTreeAggregate")
	public byte[] getParametersByTreeAggregate(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String id = RequestUtils.getString(request, "id", null);
		Boolean isCallback = RequestUtils.getBoolean(request, "isCallback");
		// SYS_DYNAMIC_SQL_TREE
		List<DynamicSqlTree> list;
		JSONArray retAry = new JSONArray();
		if (id != null) {
			DynamicSqlTreeQuery query = new DynamicSqlTreeQuery();
			query.setBusinessKey(id);
			query.setModuleId("treetable_aggregate");
			list = dynamicDqlTreeService.list(query);
			JSONObject retObj = null;
			for (DynamicSqlTree dynamicSqlTree : list) {
				retObj = new JSONObject();
				retObj.put("columnName", dynamicSqlTree.getParamTitle());
				retObj.put("columnLabel", dynamicSqlTree.getParamName());
				retAry.add(retObj);
			}
		}
		return retAry.toJSONString().getBytes("UTF-8");
	}

	private Map<String, List<Map<String, Object>>> getEventGroup(List<Map<String, Object>> events) {
		String role = null;
		List<Map<String, Object>> mapList = null;
		Map<String, List<Map<String, Object>>> retMap = new HashMap<>();
		for (Map<String, Object> map : events) {
			role = map.get("DATAROLE_").toString();
			if (retMap.containsKey(role)) {
				retMap.get(role).add(map);
			} else {
				mapList = new ArrayList<>();
				mapList.add(map);
				retMap.put(role, mapList);
			}
		}
		return retMap;
	}

	/**
	 * 获取事件
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/getEvent")
	public byte[] getEvent(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> retMap = new HashMap<>();
		List<Map<String, Object>> triggerEvents = formComponentPropertyService.getEventsProperty(1);
		Map<String, List<Map<String, Object>>> triggerMap = getEventGroup(triggerEvents);
		retMap.put("trigger", triggerMap);
		List<Map<String, Object>> execEvents = formComponentPropertyService.getEventsProperty(0);
		Map<String, List<Map<String, Object>>> execMap = getEventGroup(execEvents);
		retMap.put("exec", execMap);
		try {
			return JSON.toJSONString(retMap).getBytes("UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		}
	}

	@ResponseBody
	@RequestMapping("/getFormDesignHtmlById")
	public byte[] getFormDesignHtmlById(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		FormPage formPage = formPageService.getFormPage(id);
		if (formPage != null) {
			return formPage.getDesignerHtml().getBytes("UTF-8");
		}
		return null;
	}

	/**
	 * 获取
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/getDatasetTables")
	public byte[] getDatasetTables(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String pageId = request.getParameter("pageId");
		String widgetId = request.getParameter("widgetId");
		FormRuleProperty formRuleProperty = formRulePropertyService.getRuleByName(pageId, widgetId, "dataSourceSet");
		if (formRuleProperty != null) {
			String valStr = formRuleProperty.getValue();
			if (StringUtils.isNotEmpty(valStr)) {
				JSONArray valAry = JSON.parseArray(valStr);
				JSONObject valObj = valAry.getJSONObject(0);
				String[] keys = { "datasource" };
				if (!valObj.isEmpty()) {
					JSONArray tables = null;
					for (String key : keys) {
						if (valObj.containsKey(key)) {
							tables = valObj.getJSONArray(key);
							break;
						}
					}
					return tables.toJSONString().getBytes("UTF-8");
				}
			}
		}
		return null;
	}

	/**
	 * 获取事件定义器规则串
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping("/getEventDefinedStr")
	public byte[] getEventDefinedStr(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		String pageId = request.getParameter("pageId");
		FormRuleProperty formRuleProperty = formRulePropertyService.getRuleByName(pageId, pageId, "eventsDefined");
		if (formRuleProperty != null) {
			String valStr = formRuleProperty.getValue();
			if (StringUtils.isNotEmpty(valStr)) {
				JSONArray valAry = JSON.parseArray(valStr);
				if (!valAry.isEmpty()) {
					return valAry.toJSONString().getBytes("UTF-8");
				}
			}
		}
		return null;
	}

	@ResponseBody
	@RequestMapping("/definedGisMap")
	public byte[] definedGisMap(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		request.getParameter("f");
		// {"features":[],"fields":[]}
		JSONObject obj = new JSONObject();
		obj.put("features", new JSONArray());
		obj.put("fields", new JSONArray());
		return obj.toJSONString().getBytes();
	}

	@RequestMapping(params = "method=selectMediaListSource")
	public ModelAndView selectMediaListSource(HttpServletRequest request, HttpServletResponse response) {
		// 保存字段的HTML标签的ID
		String fieldIdElementId = RequestUtils.getString(request, "fieldIdElementId", "");
		request.setAttribute("fieldIdElementId", fieldIdElementId);
		// 保存字段中文名称的HTML标签的ID
		String fieldNameElementId = RequestUtils.getString(request, "fieldNameElementId", "");
		request.setAttribute("fieldNameElementId", fieldNameElementId);
		// 保存字段JSON对象的HTML标签的ID
		String fieldObjElementId = RequestUtils.getString(request, "fieldObjElementId", "");
		request.setAttribute("fieldObjElementId", fieldObjElementId);

		String tableJson = RequestUtils.getString(request, "tableJson", "");
		String fieldJson = RequestUtils.getString(request, "fieldJson", "[]");
		try {
			if (StringUtils.isNotEmpty(tableJson)) {
				tableJson = new String(Base64.decodeBase64(tableJson.getBytes()), "GBK");
			}
			if (StringUtils.isNotEmpty(fieldJson)) {
				fieldJson = new String(Base64.decodeBase64(fieldJson.getBytes()), "GBK");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		request.setAttribute("tableJson", tableJson);
		request.setAttribute("fieldJson", fieldJson);

		String currentSystemName = Environment.getCurrentSystemName();
		String databaseCode = RequestUtils.getString(request, "databaseCode", currentSystemName);
		request.setAttribute("databaseCode", databaseCode);

		String view = request.getParameter("view");

		return new ModelAndView("/form/defined/ex/selectMediaListSource");
	}

	@RequestMapping(params = "method=modelbim")
	public ModelAndView selectModelBim(HttpServletRequest request, HttpServletResponse response) {

		return new ModelAndView("/modelbim/view");
	}
}

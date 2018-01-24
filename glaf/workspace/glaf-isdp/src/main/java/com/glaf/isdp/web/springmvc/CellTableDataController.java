package com.glaf.isdp.web.springmvc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONArray;
import com.glaf.core.config.Environment;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.Database;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.entity.SqlExecutor;
import com.glaf.core.jdbc.QueryHelper;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.service.ITablePageService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.datamgr.domain.DataSet;
import com.glaf.datamgr.domain.FromSegment;
import com.glaf.datamgr.domain.SynchronizationRule;
import com.glaf.datamgr.service.DataSetService;
import com.glaf.datamgr.service.SynchronizationRuleService;
import com.glaf.isdp.bean.TableDataBean;

@Controller("/isdp/table/data")
@RequestMapping("/isdp/table/data")
public class CellTableDataController {

	protected static final Log logger = LogFactory.getLog(CellTableDataController.class);

	protected DataSetService dataSetService;

	protected IDatabaseService databaseService;

	protected ITablePageService tablePageService;

	protected SynchronizationRuleService synchronizationRuleService;

	@ResponseBody
	@RequestMapping("/ajaxTreepinfoJson")
	public byte[] ajaxTreepinfoJson(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json; charset=UTF-8");
		logger.debug("params:" + params);
		TableDataBean bean = new TableDataBean();
		long databaseId = ParamUtils.getLong(params, "databaseId");
		int index_id = ParamUtils.getInt(params, "indexId");
		JSONArray result = bean.getTreepinfoJson(databaseId, index_id);
		return result.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/detail")
	public ModelAndView detail(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String tablename = request.getParameter("tablename");
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		if (StringUtils.isNotEmpty(tablename)) {
			long databaseId = RequestUtils.getLong(request, "databaseId");
			String currSystemName = Environment.getCurrentSystemName();
			try {
				Database database = databaseService.getDatabaseById(databaseId);
				if (database != null) {
					Environment.setCurrentSystemName(database.getName());
				}
				params.put("tablenameLike", tablename + "%");
				String sql = " select * from cell_repinfo where dname like #{tablenameLike} order by listno ";
				List<Map<String, Object>> fieldList = tablePageService.getListData(sql, params);
				if (fieldList != null && !fieldList.isEmpty()) {
					int width = 150;
					List<ColumnDefinition> columns = new ArrayList<ColumnDefinition>();
					for (Map<String, Object> map : fieldList) {
						ColumnDefinition col = new ColumnDefinition();
						col.setName(ParamUtils.getString(map, "dname").toLowerCase());
						col.setColumnName(ParamUtils.getString(map, "dname"));
						col.setTitle(ParamUtils.getString(map, "fname"));
						col.setLength(ParamUtils.getInt(map, "strlen"));
						col.setSortNo(ParamUtils.getInt(map, "listno"));
						String dataType = ParamUtils.getString(map, "dtype");
						switch (dataType) {
						case "r8":
							col.setJavaType("Double");
							if (col.getLength() <= 0) {
								col.setLength(65);
							}
							break;
						case "double":
							col.setJavaType("Double");
							if (col.getLength() <= 0) {
								col.setLength(65);
							}
							break;
						case "float":
							col.setJavaType("Double");
							if (col.getLength() <= 0) {
								col.setLength(65);
							}
							break;
						case "int":
							col.setJavaType("Integer");
							break;
						case "int4":
							col.setJavaType("Integer");
							if (col.getLength() <= 0) {
								col.setLength(65);
							}
							break;
						case "int8":
							col.setJavaType("Long");
							if (col.getLength() <= 0) {
								col.setLength(65);
							}
							break;
						case "long":
							col.setJavaType("Long");
							if (col.getLength() <= 0) {
								col.setLength(65);
							}
							break;
						case "date":
							col.setJavaType("Date");
							if (col.getLength() <= 0) {
								col.setLength(95);
							}
							break;
						case "datetime":
							col.setJavaType("Date");
							if (col.getLength() <= 0) {
								col.setLength(95);
							}
							break;
						default:
							col.setJavaType("String");
							if (col.getLength() <= 0) {
								col.setLength(215);
							}
							break;
						}
						width += col.getLength();
						columns.add(col);
					}
					request.setAttribute("width", width);
					request.setAttribute("columns", columns);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				Environment.setCurrentSystemName(currSystemName);
			}
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/isdp/table/detail", modelMap);
	}

	@RequestMapping
	public ModelAndView list(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String keywordsLike = request.getParameter("keywordsLike");
		if (StringUtils.isNotEmpty(keywordsLike)) {
			request.setAttribute("keywordsLike_base64", Base64.encodeBase64String(keywordsLike.getBytes()));
		}

		DataSet dataSet = dataSetService.getDataSet(request.getParameter("datasetId"));
		List<FromSegment> fromSegments = dataSet.getFromSegments();
		if (fromSegments != null && fromSegments.size() == 1) {
			FromSegment seg = fromSegments.get(0);
			SynchronizationRule rule = synchronizationRuleService
					.getSynchronizationRuleByTargetTable(seg.getTableName());
			if (rule != null) {
				request.setAttribute("rule", rule);
				List<String> tables = rule.getSouceTables();
				List<String> tables2 = new ArrayList<String>();
				QueryHelper helper = new QueryHelper();
				Iterator<String> iterator = tables.iterator();
				while (iterator.hasNext()) {
					String tablename = iterator.next();
					if (DBUtils.tableExists(tablename)) {
						SqlExecutor sqlExecutor = new SqlExecutor();
						sqlExecutor.setSql(" select count(*) from " + tablename);
						sqlExecutor.setParameter(params);
						int total = helper.getTotal(sqlExecutor);
						if (total > 0) {
							tables2.add(tablename);
						}
					}
				}

				if (tables2.size() > 0) {
					StringBuilder buffer = new StringBuilder();
					StringBuilder buffer2 = new StringBuilder();
					buffer.append("   select * from cell_data_table where tablename in ( ");
					buffer2.append(
							"   select * from cell_repinfo where frmtype in ( select id from cell_data_table where tablename in ( ");
					iterator = tables2.iterator();
					while (iterator.hasNext()) {
						String tablename = iterator.next();
						buffer.append("'").append(tablename).append("'");
						buffer2.append("'").append(tablename).append("'");
						if (iterator.hasNext()) {
							buffer.append(", ");
							buffer2.append(", ");
						}
					}
					buffer.append(" ) ");
					buffer2.append("   ) ");
					buffer2.append(" ) ");

					List<Map<String, Object>> tableList = helper.getResultList(buffer.toString(), params);
					List<Map<String, Object>> fieldList = helper.getResultList(buffer2.toString(), params);
					if (tableList != null && !tableList.isEmpty()) {
						List<TableDefinition> tableDefinitions = new ArrayList<TableDefinition>();
						for (Map<String, Object> dataMap : tableList) {
							TableDefinition tableDefinition = new TableDefinition();
							tableDefinition.setName(ParamUtils.getString(dataMap, "name"));
							tableDefinition.setTableName(ParamUtils.getString(dataMap, "tablename"));
							String tableid = ParamUtils.getString(dataMap, "id");
							if (fieldList != null && !fieldList.isEmpty()) {
								for (Map<String, Object> map : fieldList) {
									String frmtype = ParamUtils.getString(map, "frmtype");
									if (StringUtils.equalsIgnoreCase(tableid, frmtype)) {
										ColumnDefinition col = new ColumnDefinition();
										col.setName(ParamUtils.getString(map, "fname"));
										col.setColumnName(ParamUtils.getString(map, "dname"));
										col.setLength(ParamUtils.getInt(map, "strlen"));
										col.setSortNo(ParamUtils.getInt(map, "listno"));
										String dataType = ParamUtils.getString(map, "dtype");
										switch (dataType) {
										case "r8":
											col.setJavaType("Double");
											break;
										case "double":
											col.setJavaType("Double");
											break;
										case "float":
											col.setJavaType("Double");
											break;
										case "int":
											col.setJavaType("Integer");
											break;
										case "int4":
											col.setJavaType("Integer");
											break;
										case "int8":
											col.setJavaType("Long");
											break;
										case "long":
											col.setJavaType("Long");
											break;
										case "date":
											col.setJavaType("Date");
											break;
										case "datetime":
											col.setJavaType("Date");
											break;
										default:
											col.setJavaType("String");
											break;
										}
										tableDefinition.addColumn(col);
									}
								}
							}
							tableDefinitions.add(tableDefinition);
						}
						request.setAttribute("tableDefinitions", tableDefinitions);
					}
				}
			}
		}
		request.setAttribute("dataSet", dataSet);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/isdp/table/datalist", modelMap);
	}

	@RequestMapping("/selectSingleWBSNode")
	public ModelAndView selectSingleWBSNode(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		String keywordsLike = request.getParameter("keywordsLike");
		if (StringUtils.isNotEmpty(keywordsLike)) {
			request.setAttribute("keywordsLike_base64", Base64.encodeBase64String(keywordsLike.getBytes()));
		}

		return new ModelAndView("/isdp/table/select_wbs_radio", modelMap);
	}

	@javax.annotation.Resource
	public void setDatabaseService(IDatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	@javax.annotation.Resource
	public void setDataSetService(DataSetService dataSetService) {
		this.dataSetService = dataSetService;
	}

	@javax.annotation.Resource
	public void setSynchronizationRuleService(SynchronizationRuleService synchronizationRuleService) {
		this.synchronizationRuleService = synchronizationRuleService;
	}

	@javax.annotation.Resource
	public void setTablePageService(ITablePageService tablePageService) {
		this.tablePageService = tablePageService;
	}

	@ResponseBody
	@RequestMapping("/treeJson")
	public byte[] treeJson(HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		TableDataBean bean = new TableDataBean();
		JSONArray result = bean.getTreeJson(params);
		return result.toString().getBytes("UTF-8");
	}

	@RequestMapping("/treelist")
	public ModelAndView treelist(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/isdp/table/treelist", modelMap);
	}

	@ResponseBody
	@RequestMapping("/treepinfoJson")
	public byte[] treepinfoJson(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json; charset=UTF-8");
		logger.debug("params:" + params);
		TableDataBean bean = new TableDataBean();
		JSONArray result = bean.getTreepinfoJson(params);
		return result.toJSONString().getBytes("UTF-8");
	}

	@ResponseBody
	@RequestMapping("/wbsTreeJson")
	public byte[] wbsTreeJson(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json; charset=UTF-8");
		logger.debug("params:" + params);
		TableDataBean bean = new TableDataBean();
		JSONArray result = bean.getWbsTreeJson(params);
		return result.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping("/wbstreelist")
	public ModelAndView wbstreelist(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/isdp/table/wbstreelist", modelMap);
	}

}

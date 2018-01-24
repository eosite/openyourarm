package com.glaf.datamgr.web.springmvc;

import java.io.IOException;
import java.sql.Connection;
import java.util.*;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import com.alibaba.fastjson.*;
import com.glaf.core.base.TreeModel;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.Database;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.identity.*;
import com.glaf.core.jdbc.BulkInsertBean;
import com.glaf.core.security.*;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.service.ITreeModelService;
import com.glaf.core.util.*;
import com.glaf.datamgr.bean.SqliteDBHelper;
import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.jdbc.DataSetBuilder;
import com.glaf.datamgr.query.*;
import com.glaf.datamgr.service.*;
import com.zaxxer.hikari.HikariDataSource;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/datamgr/dataSetExport")
@RequestMapping("/datamgr/dataSetExport")
public class DataSetExportController {
	protected static final Log logger = LogFactory.getLog(DataSetExportController.class);

	protected DataSetService dataSetService;

	protected DataSetExportService dataSetExportService;

	protected IDatabaseService databaseService;

	protected ITreeModelService treeModelService;

	public DataSetExportController() {

	}

	@javax.annotation.Resource
	public void setDatabaseService(IDatabaseService databaseService) {
		this.databaseService = databaseService;
	}

	@RequestMapping("/datasetList")
	public ModelAndView datasetList(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		logger.debug("params:" + params);

		String xtype = request.getParameter("xtype");

		String category = request.getParameter("category");
		if (StringUtils.isEmpty(category)) {
			category = "report_category";
		}
		TreeModel treeModel = treeModelService.getTreeModelByCode(category);
		if (treeModel != null) {
			treeModel = treeModelService.getTreeModelWithAllChildren(treeModel.getId());
			List<TreeModel> treeModels = treeModel.getChildren();
			request.setAttribute("treeModels", treeModels);
		}

		String keywordsLike = request.getParameter("keywordsLike");
		DataSetQuery query = new DataSetQuery();
		query.locked(0);
		Tools.populate(query, params);
		if (keywordsLike != null && keywordsLike.length() > 0) {
			query.setKeywordsLike(keywordsLike);
		}

		DataSetExport dataSetExport = dataSetExportService.getDataSetExport(request.getParameter("id"));
		if (dataSetExport != null) {
			request.setAttribute("dataSetExport", dataSetExport);
		}

		List<DataSet> list = dataSetService.chartList(query);
		if (list != null && !list.isEmpty()) {
			if (StringUtils.equals(xtype, "1")) {
				if (dataSetExport != null && StringUtils.isNotEmpty(dataSetExport.getDatasetIds())) {
					List<String> datasetIds = StringTools.split(dataSetExport.getDatasetIds());
					for (DataSet dataSet : list) {
						if (datasetIds.contains(dataSet.getId())) {
							dataSet.setChecked(true);
						}
					}
				}
			}
		}

		request.setAttribute("list", list);

		String x_view = ViewProperties.getString("dataSetExport.datasetList");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/dataSetExport/datasetList", modelMap);
	}

	@ResponseBody
	@RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		String id = RequestUtils.getString(request, "id");
		String ids = request.getParameter("ids");
		if (StringUtils.isNotEmpty(ids)) {
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					DataSetExport dataSetExport = dataSetExportService.getDataSetExport(String.valueOf(x));
					if (dataSetExport != null
							&& (StringUtils.equals(dataSetExport.getCreateBy(), loginContext.getActorId())
									|| loginContext.isSystemAdministrator())) {
						dataSetExportService.deleteById(dataSetExport.getId());
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			DataSetExport dataSetExport = dataSetExportService.getDataSetExport(String.valueOf(id));
			if (dataSetExport != null && (StringUtils.equals(dataSetExport.getCreateBy(), loginContext.getActorId())
					|| loginContext.isSystemAdministrator())) {
				dataSetExportService.deleteById(dataSetExport.getId());
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		DataSetExport dataSetExport = dataSetExportService.getDataSetExport(request.getParameter("id"));
		if (dataSetExport != null) {
			request.setAttribute("dataSetExport", dataSetExport);

			if (StringUtils.isNotEmpty(dataSetExport.getDatasetIds())) {
				List<String> datasetIds = StringTools.split(dataSetExport.getDatasetIds());
				StringBuffer sb01 = new StringBuffer();
				StringBuffer sb02 = new StringBuffer();
				for (String datasetId : datasetIds) {
					DataSet dataset = dataSetService.getDataSet(datasetId);
					if (dataset != null) {
						sb01.append(dataset.getId()).append(",");
						sb02.append(dataset.getTitle()).append("[").append(dataset.getName()).append("],");
					}
				}
				if (sb01.toString().endsWith(",")) {
					sb01.delete(sb01.length() - 1, sb01.length());
				}
				if (sb02.toString().endsWith(",")) {
					sb02.delete(sb02.length() - 1, sb02.length());
				}
				request.setAttribute("datasetIds", sb01.toString());
				request.setAttribute("datasetNames", sb02.toString());
			}

		}

		String category = request.getParameter("category");
		if (StringUtils.isEmpty(category)) {
			category = "report_category";
		}
		TreeModel treeModel = treeModelService.getTreeModelByCode(category);
		if (treeModel != null) {
			treeModel = treeModelService.getTreeModelWithAllChildren(treeModel.getId());
			List<TreeModel> treeModels = treeModel.getChildren();
			request.setAttribute("treeModels", treeModels);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("dataSetExport.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/datamgr/dataSetExport/edit", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DataSetExportQuery query = new DataSetExportQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);

		if (!loginContext.isSystemAdministrator()) {
			String actorId = loginContext.getActorId();
			query.createBy(actorId);
		}

		int start = 0;
		int limit = 10;
		String orderName = null;
		String order = null;

		int pageNo = ParamUtils.getInt(params, "page");
		limit = ParamUtils.getInt(params, "rows");
		start = (pageNo - 1) * limit;
		orderName = ParamUtils.getString(params, "sortName");
		order = ParamUtils.getString(params, "sortOrder");

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0) {
			limit = Paging.DEFAULT_PAGE_SIZE;
		}

		JSONObject result = new JSONObject();
		int total = dataSetExportService.getDataSetExportCountByQueryCriteria(query);
		if (total > 0) {
			result.put("total", total);
			result.put("totalCount", total);
			result.put("totalRecords", total);
			result.put("start", start);
			result.put("startIndex", start);
			result.put("limit", limit);
			result.put("pageSize", limit);

			if (StringUtils.isNotEmpty(orderName)) {
				query.setSortOrder(orderName);
				if (StringUtils.equals(order, "desc")) {
					query.setSortOrder(" desc ");
				}
			}

			List<DataSetExport> list = dataSetExportService.getDataSetExportsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (DataSetExport dataSetExport : list) {
					JSONObject rowJSON = dataSetExport.toJsonObject();
					rowJSON.put("id", dataSetExport.getId());
					rowJSON.put("rowId", dataSetExport.getId());
					rowJSON.put("dataSetExportId", dataSetExport.getId());
					rowJSON.put("startIndex", ++start);
					rowsJSON.add(rowJSON);
				}

			}
		} else {
			JSONArray rowsJSON = new JSONArray();
			result.put("rows", rowsJSON);
			result.put("total", total);
		}
		return result.toJSONString().getBytes("UTF-8");
	}

	@RequestMapping
	public ModelAndView list(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		return new ModelAndView("/datamgr/dataSetExport/list", modelMap);
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("dataSetExport.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/datamgr/dataSetExport/query", modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveDataSetExport")
	public byte[] saveDataSetExport(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DataSetExport dataSetExport = new DataSetExport();
		try {
			Tools.populate(dataSetExport, params);
			dataSetExport.setName(request.getParameter("name"));
			dataSetExport.setTitle(request.getParameter("title"));
			dataSetExport.setType(request.getParameter("type"));
			dataSetExport.setExportDBName(request.getParameter("exportDBName"));
			dataSetExport.setLastExportStatus(RequestUtils.getInt(request, "lastExportStatus"));
			dataSetExport.setLastExportTime(RequestUtils.getDate(request, "lastExportTime"));
			dataSetExport.setServiceKey(request.getParameter("serviceKey"));
			dataSetExport.setUserId(request.getParameter("userId"));
			dataSetExport.setDatasetIds(request.getParameter("datasetIds"));
			dataSetExport.setScheduleFlag(request.getParameter("scheduleFlag"));
			dataSetExport.setDeleteFetch(request.getParameter("deleteFetch"));
			dataSetExport.setPublicFlag(request.getParameter("publicFlag"));
			dataSetExport.setCreateTableFlag(request.getParameter("createTableFlag"));
			dataSetExport.setLocked(RequestUtils.getInt(request, "locked"));
			dataSetExport.setCreateBy(actorId);
			dataSetExport.setUpdateBy(actorId);
			this.dataSetExportService.save(dataSetExport);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@javax.annotation.Resource(name = "com.glaf.datamgr.service.dataSetExportService")
	public void setDataSetExportService(DataSetExportService dataSetExportService) {
		this.dataSetExportService = dataSetExportService;
	}

	@javax.annotation.Resource
	public void setDataSetService(DataSetService dataSetService) {
		this.dataSetService = dataSetService;
	}

	@javax.annotation.Resource
	public void setTreeModelService(ITreeModelService treeModelService) {
		this.treeModelService = treeModelService;
	}

	@ResponseBody
	@RequestMapping("/sqlite")
	public void sqlite(HttpServletRequest request, HttpServletResponse response) throws IOException {
		DataSetBuilder dataSetBuilder = new DataSetBuilder();
		SqliteDBHelper sqliteDBHelper = new SqliteDBHelper();
		HikariDataSource dataSource = null;
		Connection conn = null;
		DataSet dataSet = null;
		try {
			String dataSetExportId = request.getParameter("exportId");
			if (StringUtils.isNotEmpty(dataSetExportId)) {
				DataSetExport dataSetExport = dataSetExportService.getDataSetExport(dataSetExportId);
				if (dataSetExport != null && StringUtils.isNotEmpty(dataSetExport.getDatasetIds())) {
					String sqliteDB = dataSetExport.getId() + ".db";
					String dbpath = SystemProperties.getConfigRootPath() + "/temp/" + sqliteDB;
					FileUtils.deleteFile(dbpath);
					List<String> datasetIds = StringTools.split(dataSetExport.getDatasetIds());
					for (String datasetId : datasetIds) {
						dataSet = dataSetService.getDataSet(datasetId);
						if (dataSet != null && StringUtils.isNotEmpty(dataSet.getExportTableName())) {
							logger.debug("dataSet.getDatabaseId():" + dataSet.getDatabaseId());
							Map<String, Object> params = RequestUtils.getParameterMap(request);

							if (!params.containsKey("om")) {
								params.put("om", true);// 只获取mapping字段
							}
							int pageNo = RequestUtils.getInt(request, "page", 1);
							int limit = RequestUtils.getInt(request, "rows", 500000);
							int start = (pageNo - 1) * limit;
							if (start <= 0) {
								start = 0;
							}
							if (limit <= 0) {
								limit = 500000;
							}
							limit = pageNo * limit;
							JSONObject result = dataSetBuilder.getJson(datasetId, start, limit, params);
							if (result != null && result.getJSONArray("rows") != null) {
								JSONArray array = result.getJSONArray("rows");
								TableDefinition tableDefinition = new TableDefinition();
								tableDefinition.setTableName(dataSet.getExportTableName());
								tableDefinition.setName(dataSet.getExportTableName());

								List<String> columnNames = new ArrayList<String>();
								List<SelectSegment> segments = dataSet.getSelectSegments();
								for (SelectSegment segment : segments) {
									if (StringUtils.isNotEmpty(segment.getColumnMapping())
											&& StringUtils.equals(segment.getOutput(), "true")) {
										ColumnDefinition col = new ColumnDefinition();
										col.setName(segment.getColumnMapping());
										col.setColumnName(segment.getColumnMapping());
										col.setJavaType("String");
										col.setLength(500);
										if (StringUtils.equalsIgnoreCase(segment.getColumnMapping(), "id")) {
											tableDefinition.setIdColumn(col);
										} else {
											tableDefinition.addColumn(col);
										}
										columnNames.add(segment.getColumnMapping().toLowerCase());
									}
								}
								boolean myIdGen = false;
								if (tableDefinition.getIdColumn() == null) {
									ColumnDefinition idColumn = new ColumnDefinition();
									idColumn.setName("id");
									idColumn.setColumnName("id");
									idColumn.setJavaType("Long");
									tableDefinition.setIdColumn(idColumn);
									myIdGen = true;
								}

								boolean databaseid = false;
								boolean databasemapping = false;
								Database database = null;
								if (!columnNames.contains("databaseid_")) {
									ColumnDefinition databaseidColumn = new ColumnDefinition();
									databaseidColumn.setName("databaseid_");
									databaseidColumn.setColumnName("DATABASEID_");
									databaseidColumn.setJavaType("Long");
									tableDefinition.addColumn(databaseidColumn);
									databaseid = true;
									if (dataSet.getDatabaseId() != null && dataSet.getDatabaseId() > 0) {
										database = databaseService.getDatabaseById(dataSet.getDatabaseId());
									}
								}

								if (!columnNames.contains("databasemapping_")) {
									ColumnDefinition databaseMappingColumn = new ColumnDefinition();
									databaseMappingColumn.setName("databasemapping_");
									databaseMappingColumn.setColumnName("DATABASEMAPPING_");
									databaseMappingColumn.setJavaType("String");
									databaseMappingColumn.setLength(50);
									tableDefinition.addColumn(databaseMappingColumn);
									databasemapping = true;
									if (database == null && dataSet.getDatabaseId() != null
											&& dataSet.getDatabaseId() > 0) {
										database = databaseService.getDatabaseById(dataSet.getDatabaseId());
									}
								}

								dataSource = sqliteDBHelper.getTempDataSource(sqliteDB);
								conn = dataSource.getConnection();
								conn.setAutoCommit(false);
								DBUtils.createTable(conn, tableDefinition);
								conn.commit();

								int len = array.size();
								JSONObject json = null;
								long id = 1;
								List<Map<String, Object>> datalist = new ArrayList<Map<String, Object>>();
								for (int i = 0; i < len; i++) {
									json = array.getJSONObject(i);
									Map<String, Object> dataMap = new HashMap<String, Object>();
									Iterator<Entry<String, Object>> iterator = json.entrySet().iterator();
									while (iterator.hasNext()) {
										Entry<String, Object> entry = iterator.next();
										String key = (String) entry.getKey();
										Object value = entry.getValue();
										if (value != null) {
											dataMap.put(key, value);
										}
									}
									if (myIdGen) {
										dataMap.put("id", id++);
									}
									if (database != null) {
										if (databaseid) {
											dataMap.put("databasemapping_", database.getId());
										}
										if (databasemapping) {
											dataMap.put("databasemapping_", database.getMapping());
										}
									}
									datalist.add(dataMap);
								}

								conn.setAutoCommit(false);
								BulkInsertBean bean = new BulkInsertBean();
								bean.bulkInsert(conn, tableDefinition, datalist);
								conn.commit();
								JdbcUtils.close(conn);
								dataSource.close();
							}
						}
					}

					byte[] bytes = FileUtils.getBytes(dbpath);
					FileUtils.save(SystemProperties.getAppPath() + "/export/" + dataSetExport.getId(), bytes);
					FileUtils.save(SystemProperties.getAppPath() + "/export/" + dataSetExport.getExportDBName(), bytes);
					ResponseUtils.download(request, response, bytes, dataSetExport.getExportDBName());

				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("export error", ex);
		} finally {
			JdbcUtils.close(conn);
			if (dataSource != null) {
				dataSource.close();
			}
		}
	}

}

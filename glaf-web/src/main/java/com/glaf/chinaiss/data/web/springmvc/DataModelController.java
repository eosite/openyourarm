package com.glaf.chinaiss.data.web.springmvc;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.chinaiss.data.domain.DataModel;
import com.glaf.chinaiss.data.domain.DataModelMetadata;
import com.glaf.chinaiss.data.query.DataModelQuery;
import com.glaf.chinaiss.data.service.DataModelService;
import com.glaf.chinaiss.data.util.DataModelDomainFactory;
import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.config.Environment;
import com.glaf.core.config.ViewProperties;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.identity.User;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.JsonUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.Paging;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.Tools;
import com.glaf.isdp.config.RConstant;
import com.glaf.isdp.domain.RdataField;
import com.glaf.isdp.domain.RdataTable;
import com.glaf.isdp.domain.Rinterface;
import com.glaf.isdp.query.RdataTableQuery;
import com.glaf.isdp.query.RinterfaceQuery;
import com.glaf.isdp.service.RdataFieldService;
import com.glaf.isdp.service.RdataTableService;
import com.glaf.isdp.service.RinterfaceService;

/**
 * 
 * SpringMVC控制器
 *
 */

@Controller("/data/model")
@RequestMapping("/data/model")
public class DataModelController {
	protected static final Log logger = LogFactory.getLog(DataModelController.class);

	protected DataModelService dataModelService;

	protected RdataTableService rdataTableService;

	protected RdataFieldService rdataFieldService;

	protected RinterfaceService rinterfaceService;

	public DataModelController() {

	}

	@javax.annotation.Resource(name = "com.glaf.chinaiss.data.service.dataModelService")
	public void setDataModelService(DataModelService dataModelService) {
		this.dataModelService = dataModelService;
	}

	@javax.annotation.Resource
	public void setRdataTableService(RdataTableService rdataTableService) {
		this.rdataTableService = rdataTableService;
	}

	@javax.annotation.Resource
	public void setRdataFieldService(RdataFieldService rdataFieldService) {
		this.rdataFieldService = rdataFieldService;
	}

	@javax.annotation.Resource
	public void setRinterfaceService(RinterfaceService rinterfaceService) {
		this.rinterfaceService = rinterfaceService;
	}

	@RequestMapping("/save")
	public ModelAndView save(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		DataModel dataModel = new DataModel();
		Tools.populate(dataModel, params);

		dataModel.setName(request.getParameter("name"));
		dataModel.setDescription(request.getParameter("description"));
		dataModel.setJson(request.getParameter("json"));
		dataModel.setTopId(request.getParameter("topId"));
		dataModel.setParentId(request.getParameter("parentId"));
		dataModel.setCreateBy(request.getParameter("createBy"));
		dataModel.setCreateDate(RequestUtils.getDate(request, "createDate"));
		dataModel.setUpdateBy(request.getParameter("updateBy"));
		dataModel.setUpdateDate(RequestUtils.getDate(request, "updateDate"));

		// dataModel.setCreateBy(actorId);

		dataModelService.save(dataModel);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/saveDataModel")
	public byte[] saveDataModel(HttpServletRequest request) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DataModel dataModel = new DataModel();
		try {
			Tools.populate(dataModel, params);
			dataModel.setName(request.getParameter("name"));
			dataModel.setDescription(request.getParameter("description"));
			dataModel.setJson(request.getParameter("json"));
			dataModel.setTopId(request.getParameter("topId"));
			dataModel.setParentId(request.getParameter("parentId"));
			dataModel.setCreateBy(request.getParameter("createBy"));
			dataModel.setCreateDate(RequestUtils.getDate(request, "createDate"));
			dataModel.setUpdateBy(request.getParameter("updateBy"));
			dataModel.setUpdateDate(RequestUtils.getDate(request, "updateDate"));
			// dataModel.setCreateBy(actorId);
			this.dataModelService.save(dataModel);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
	public @ResponseBody DataModel saveOrUpdate(HttpServletRequest request, @RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		DataModel dataModel = new DataModel();
		try {
			Tools.populate(dataModel, model);
			dataModel.setName(ParamUtils.getString(model, "name"));
			dataModel.setDescription(ParamUtils.getString(model, "description"));
			dataModel.setJson(ParamUtils.getString(model, "json"));
			dataModel.setTopId(ParamUtils.getString(model, "topId"));
			dataModel.setParentId(ParamUtils.getString(model, "parentId"));
			dataModel.setCreateBy(ParamUtils.getString(model, "createBy"));
			dataModel.setCreateDate(ParamUtils.getDate(model, "createDate"));
			dataModel.setUpdateBy(ParamUtils.getString(model, "updateBy"));
			dataModel.setUpdateDate(ParamUtils.getDate(model, "updateDate"));
			dataModel.setCreateBy(actorId);
			this.dataModelService.save(dataModel);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return dataModel;
	}

	@RequestMapping("/update")
	public ModelAndView update(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		params.remove("status");
		params.remove("wfStatus");

		DataModel dataModel = dataModelService.getDataModel(request.getParameter("id"));

		Tools.populate(dataModel, params);

		dataModel.setName(request.getParameter("name"));
		dataModel.setDescription(request.getParameter("description"));
		dataModel.setJson(request.getParameter("json"));
		dataModel.setTopId(request.getParameter("topId"));
		dataModel.setParentId(request.getParameter("parentId"));
		dataModel.setCreateBy(request.getParameter("createBy"));
		dataModel.setCreateDate(RequestUtils.getDate(request, "createDate"));
		dataModel.setUpdateBy(request.getParameter("updateBy"));
		dataModel.setUpdateDate(RequestUtils.getDate(request, "updateDate"));

		dataModelService.save(dataModel);

		return this.list(request, modelMap);
	}

	@ResponseBody
	@RequestMapping("/delete")
	public byte[] delete(HttpServletRequest request, ModelMap modelMap) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		String id = RequestUtils.getString(request, "id");
		String ids = request.getParameter("ids");
		if (StringUtils.isNotEmpty(ids)) {
			StringTokenizer token = new StringTokenizer(ids, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					DataModel dataModel = dataModelService.getDataModel(String.valueOf(x));
					/**
					 * 此处业务逻辑需自行调整
					 */
					// TODO
					if (dataModel != null && (StringUtils.equals(dataModel.getCreateBy(), loginContext.getActorId())
							|| loginContext.isSystemAdministrator())) {
						// dataModel.setDeleteFlag(1);
						dataModelService.save(dataModel);
					}
				}
			}
			return ResponseUtils.responseResult(true);
		} else if (id != null) {
			DataModel dataModel = dataModelService.getDataModel(String.valueOf(id));
			/**
			 * 此处业务逻辑需自行调整
			 */
			// TODO
			if (dataModel != null && (StringUtils.equals(dataModel.getCreateBy(), loginContext.getActorId())
					|| loginContext.isSystemAdministrator())) {
				// dataModel.setDeleteFlag(1);
				// dataModelService.save(dataModel);
				dataModelService.deleteById(id);
				return ResponseUtils.responseResult(true);
			}
		}
		return ResponseUtils.responseResult(false);
	}

	@RequestMapping("/edit")
	public ModelAndView edit(HttpServletRequest request, ModelMap modelMap) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		RequestUtils.setRequestParameterToAttribute(request);
		request.removeAttribute("canSubmit");
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DataModel dataModel = dataModelService.getDataModel(request.getParameter("id"));
		if (dataModel != null) {
			request.setAttribute("dataModel", dataModel);
		}

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}

		String x_view = ViewProperties.getString("dataModel.edit");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}

		return new ModelAndView("/data/model/dataModel/edit", modelMap);
	}

	@RequestMapping("/view")
	public ModelAndView view(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DataModel dataModel = dataModelService.getDataModel(request.getParameter("id"));
		request.setAttribute("dataModel", dataModel);

		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view);
		}

		String x_view = ViewProperties.getString("dataModel.view");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view);
		}

		return new ModelAndView("/data/model/dataModel/view");
	}

	@RequestMapping("/query")
	public ModelAndView query(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		String x_view = ViewProperties.getString("dataModel.query");
		if (StringUtils.isNotEmpty(x_view)) {
			return new ModelAndView(x_view, modelMap);
		}
		return new ModelAndView("/data/model/dataModel/query", modelMap);
	}

	@RequestMapping("/json")
	@ResponseBody
	public byte[] json(HttpServletRequest request, ModelMap modelMap) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DataModelQuery query = new DataModelQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		/**
		 * 此处业务逻辑需自行调整
		 */
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
		int total = dataModelService.getDataModelCountByQueryCriteria(query);
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

			Map<String, User> userMap = IdentityFactory.getUserMap();
			List<DataModel> list = dataModelService.getDataModelsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (DataModel dataModel : list) {
					JSONObject rowJSON = dataModel.toJsonObject();
					rowJSON.put("id", dataModel.getId());
					rowJSON.put("rowId", dataModel.getId());
					rowJSON.put("dataModelId", dataModel.getId());
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

	@RequestMapping("/read")
	@ResponseBody
	public byte[] read(HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DataModelQuery query = new DataModelQuery();
		Tools.populate(query, params);
		query.deleteFlag(0);
		query.setActorId(loginContext.getActorId());
		query.setLoginContext(loginContext);
		query.setDataRequest(dataRequest);
		DataModelDomainFactory.processDataRequest(dataRequest);

		/**
		 * 此处业务逻辑需自行调整
		 */
		if (!loginContext.isSystemAdministrator()) {
			String actorId = loginContext.getActorId();
			query.createBy(actorId);
		}

		int start = 0;
		int limit = PageResult.DEFAULT_PAGE_SIZE;

		int pageNo = dataRequest.getPage();
		limit = dataRequest.getPageSize();

		start = (pageNo - 1) * limit;

		if (start < 0) {
			start = 0;
		}

		if (limit <= 0) {
			limit = PageResult.DEFAULT_PAGE_SIZE;
		}

		JSONObject result = new JSONObject();
		int total = dataModelService.getDataModelCountByQueryCriteria(query);
		if (total > 0) {
			result.put("total", total);
			result.put("totalCount", total);
			result.put("totalRecords", total);
			result.put("start", start);
			result.put("startIndex", start);
			result.put("limit", limit);
			result.put("pageSize", limit);

			String orderName = null;
			String order = null;

			if (dataRequest.getSort() != null && !dataRequest.getSort().isEmpty()) {
				SortDescriptor sort = dataRequest.getSort().get(0);
				orderName = sort.getField();
				order = sort.getDir();
				logger.debug("orderName:" + orderName);
				logger.debug("order:" + order);
			}

			if (StringUtils.isNotEmpty(orderName)) {
				query.setSortColumn(orderName);
				if (StringUtils.equals(order, "desc")) {
					query.setSortOrder(" desc ");
				}
			}

			Map<String, User> userMap = IdentityFactory.getUserMap();
			List<DataModel> list = dataModelService.getDataModelsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();

				result.put("rows", rowsJSON);

				for (DataModel dataModel : list) {
					JSONObject rowJSON = dataModel.toJsonObject();
					rowJSON.put("id", dataModel.getId());
					rowJSON.put("dataModelId", dataModel.getId());
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

		return new ModelAndView("/data/model/dataModel/list", modelMap);
	}

	@RequestMapping("/getDataModelById")
	@ResponseBody
	public byte[] getDataModelById(HttpServletRequest request, ModelMap modelMap) throws IOException {
		String id = RequestUtils.getString(request, "id");
		DataModel dm = this.dataModelService.getDataModel(id);
		if (dm != null) {
			return dm.toJsonObject().toJSONString().getBytes("UTF-8");
		}
		return null;
	}

	@RequestMapping("/getDataModelByIdAmouse")
	@ResponseBody
	public byte[] getDataModelByIdAmouse(HttpServletRequest request, ModelMap modelMap) throws IOException {
		String idAmouse = request.getParameter("idAmouse");
		JSONObject result = new JSONObject();
		if (StringUtils.isNotEmpty(idAmouse)) {
			StringTokenizer token = new StringTokenizer(idAmouse, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				if (StringUtils.isNotEmpty(x)) {
					DataModel dm = this.dataModelService.getDataModel(x);
					if (dm != null) {
						if (dm.getJson() != null) {
							JSONObject json = JSON.parseObject(dm.getJson());
							String str = String.valueOf(json.get("nodes"));
							if (str.length() > 2) {
								result.put("istable", true);
								return result.toJSONString().getBytes("UTF-8");
							}
						}
					}
				}
			}
		}
		result.put("istable", false);
		return result.toJSONString().getBytes("UTF-8");
	}

	@ResponseBody
	@RequestMapping("/getDataModels")
	public byte[] getDataModels(HttpServletRequest request) {
		try {

			List list = this.dataModelService.getDataModelsWithTree(RequestUtils.getParameterMap(request));

			return new JSONArray(list).toJSONString().getBytes("UTF-8");
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@ResponseBody
	@RequestMapping("/getDataModelColumns")
	public byte[] getDataModelColumns(HttpServletRequest request) throws IOException {

		JSONObject result = new JSONObject();

		String treeId = RequestUtils.getString(request, "treeId");

		String tableName = RequestUtils.getString(request, "tableName");

		List list = this.dataModelService.getDataModelColumns(treeId, tableName);

		result.put("columns", list);

		return result.toJSONString().getBytes("UTF-8");
	}

	@ResponseBody
	@RequestMapping("/getDataModelTablesByTreeId")
	public byte[] getDataModelTablesByTreeId(HttpServletRequest request) {
		try {
			List list = this.dataModelService//
					.getDataModelTablesByTreeId(RequestUtils.getString(request, "treeId"));
			return new JSONArray(list).toJSONString().getBytes("UTF-8");
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return ResponseUtils.responseJsonResult(false);
	}

	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request, ModelMap modelMap) {
		RequestUtils.setRequestParameterToAttribute(request);
		String view = request.getParameter("view");
		if (StringUtils.isNotEmpty(view)) {
			return new ModelAndView(view, modelMap);
		}
		return new ModelAndView("/data/model/index", modelMap);
	}

	@ResponseBody
	@RequestMapping("/preview")
	public byte[] preview(HttpServletRequest request, ModelMap modelMap) throws IOException {

		JSONObject result = new JSONObject();

		String tableDefinitionStr = RequestUtils.getString(request, "tableDefinition");

		String dbType = RequestUtils.getString(request, "dbType", "sqlserver");

		if (StringUtils.isNotBlank(tableDefinitionStr)) {

			TableDefinition tableDefinition = JSON.parseObject(tableDefinitionStr, TableDefinition.class);

			String sql = DBUtils.getCreateTableScript(dbType, tableDefinition);

			result.put("SQL", sql);
		}

		return result.toJSONString().getBytes("UTF-8");
	}

	@ResponseBody
	@RequestMapping("/model2db")
	public byte[] model2db(HttpServletRequest request, ModelMap modelMap) throws IOException {
		return this.saveModel2db(RequestUtils.getString(request, "id"), RequestUtils.getString(request, "code"));
	}

	/**
	 * 根据模型建表
	 * 
	 * @param id
	 * @throws IOException
	 */
	public byte[] saveModel2db(String id, String code) throws IOException {
		List<Map<String, Object>> list = null;
		JSONArray jsonArray = new JSONArray();
		try {
			boolean isNull;
			String tableName, tbMapping = null,
					sql = "SELECT id_ 'id', TABLENAME_ 'tableName', NAME_ 'name' FROM DATA_MODEL_TABLES WHERE PARENTID_ = ?";
			list = this.dataModelService.getList(sql, Arrays.asList(id));
			Map<String, DataModelMetadata> mapping;
			if (CollectionUtils.isNotEmpty(list)) {
				DataModelMetadata dmm;
				for (Map<String, Object> map : list) {
					mapping = this.dataModelService.getMappingByTableName( // 数据量大，最好每次查
							tableName = MapUtils.getString(map, "tableName").toLowerCase());
					RdataTable rdataTable = new RdataTable();
					dmm = mapping.get(tableName);
					if (isNull = (dmm == null || StringUtils.isBlank(tbMapping = dmm.getMapping()))) {
						rdataTable.setAddtype(1);
					} else {
						if (dmm.getMappingId() != null) {
							rdataTable = rdataTableService.getRdataTable(dmm.getMappingId());
						} else {
							RdataTableQuery query = new RdataTableQuery();
							query.setTablename(tbMapping);
							List<RdataTable> tbs = this.rdataTableService.list(query);
							if (CollectionUtils.isNotEmpty(tbs)) {
								rdataTable = tbs.get(0);
							}
						}
					}

					if (dmm != null && dmm.getMappingId() != null
							&& !DBUtils.tableExists(Environment.getCurrentSystemName(), dmm.getMapping())) {
						/**
						 * 表被删了，清空元数据
						 */
						rdataTableService.deleteTable(dmm.getMappingId());
						rdataTable = null;
					}

					if (rdataTable == null) {
						rdataTable = new RdataTable();
						rdataTable.setAddtype(1);
						isNull = true;
					}

					rdataTable.setIndexId(1000001);
					rdataTable.setName(MapUtils.getString(map, "name"));

					rdataTable.setCodeName(code + "_");

					rdataTableService.save(rdataTable);
					if (isNull) {
						this.dataModelService.insertMapping(tableName, //
								tbMapping = rdataTable.getTablename(), "table", rdataTable.getId());
						rdataTable.setTablename(rdataTable.getTablename());
						rdataTableService.save(rdataTable);
					}
					JSONObject json = new JSONObject();
					this.saveModel2dbColumn(MapUtils.getString(map, "id")//
							, mapping, rdataTable);
					json.put("code", rdataTable.getTablename());
					json.put("tableName", tableName);
					jsonArray.add(json);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.debug(e.getMessage());
		} finally {
			list = null;
		}
		return jsonArray.toJSONString().getBytes("UTF-8");
	}

	private static Map<String, String> typeMap;

	static {
		typeMap = new HashMap<>();
		typeMap.put("varchar", "string");
		typeMap.put("integer", "i4");
		typeMap.put("date", "datetime");
	}

	/**
	 * 保存列信息
	 * 
	 * @param connection
	 * @param id
	 * @throws SQLException
	 */
	protected void saveModel2dbColumn(String id, //
			Map<String, DataModelMetadata> mapping, RdataTable table) throws SQLException {
		String sql = "SELECT ID_ 'id', COLUMNNAME_ 'columnName', NAME_ 'name', TABLENAME_ 'tableName', TYPE_ 'columnType' " //
				+ " , LENGTH_ 'length' FROM DATA_MODEL_COLUMNS WHERE PARENTID_ = ?";
		List<Map<String, Object>> list = this.dataModelService.getList(sql, Arrays.asList(id));
		if (CollectionUtils.isNotEmpty(list)) {
			String tbMapping = table.getTablename(), columnName, columnMapping, columnType, //
					tableid = table.getId(), dtype;
			DataModelMetadata dmm;
			RdataField rdataField;
			Rinterface rinterface = null;

			if (StringUtils.isBlank(table.getTablename())) {
				return;
			}

			List<ColumnDefinition> cdfs = DBUtils.getColumnDefinitions(Environment.getCurrentSystemName(),
					table.getTablename());
			/*
			 * if(CollectionUtils.isEmpty(cdfs)){
			 * rdataTableService.deleteTable(table.getId()); return; }
			 */
			Set<String> sets = new HashSet<String>();
			for (ColumnDefinition cdf : cdfs) {
				sets.add(cdf.getColumnName().toUpperCase());
			}

			for (Map<String, Object> map : list) {
				columnName = MapUtils.getString(map, "columnName").toLowerCase();
				columnType = MapUtils.getString(map, "columnType");

				dtype = MapUtils.getString(typeMap, columnType, columnType);

				Integer length = MapUtils.getInteger(map, "length");

				String code = (MapUtils.getString(map, "tableName") + "_0_" + columnName).toLowerCase();
				dmm = mapping.get(code);
				rdataField = null;
				if (dmm != null) {
					rdataField = rdataFieldService.getRdataField(dmm.getMappingId());
					if (rdataField != null && !rdataField.getTableid().equalsIgnoreCase(tableid)) {// 表格被删了，但是元数据表还在
						rdataField = null;
					}
				}

				if (rdataField != null) { // 更新字段
					RinterfaceQuery query = new RinterfaceQuery();
					query.setFrmtype(tableid);
					query.setDname(dmm.getMapping());
					List<Rinterface> iis = rinterfaceService.list(query);
					if (CollectionUtils.isNotEmpty(iis)) {
						rinterface = iis.get(0);
					}
					if (rinterface != null) {
						rinterface.setFname(StringUtils.trim(MapUtils.getString(map, "name")));
						rinterface.setDtype(dtype);
						rinterface.setStrlen(length);
					}
					this.rdataTableService.modifyColumn(tbMapping, columnType, rdataField, rinterface);
				} else {

					int maxUser = rdataFieldService.getNextMaxUser(tableid);

					columnMapping = RConstant.getColumnName(table.getTablename(), maxUser);

					if (sets.contains(MapUtils.getString(map, "columnName").toUpperCase())) {
						columnMapping = MapUtils.getString(map, "columnName");
						// continue;
					}

					rdataField = new RdataField();
					rdataField.setMaxuser(maxUser);
					rdataField.setTableid(tableid);
					rdataField.setCtime(new Date());
					rdataField.setFieldname(columnMapping);
					rdataField.setUserindex("");
					rdataField.setTablename("");
					// rdataField.setUserid(loginContext.getActorId());
					rdataField.setFormula("");
					rdataField.setDname("");

					rinterface = new Rinterface();
					rinterface.setDatapoint(3);
					rinterface.setDname(columnMapping);
					rinterface.setFname(StringUtils.trim(MapUtils.getString(map, "name")));
					rinterface.setIssystem("1");
					rinterface.setDtype(dtype);
					rinterface.setStrlen(length);
					rinterface.setFrmtype(table.getId());

					this.rdataTableService.addColumn(tbMapping, columnMapping, columnType, rdataField, rinterface);

					this.dataModelService.insertMapping(MapUtils.getString(map, "tableName") + "_0_" + columnName,
							columnMapping, "column", rdataField.getId());

				}
			}
		}
		list = null;
	}

	/**
	 * 
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/saveOrUpdateDetails", method = RequestMethod.POST)
	public @ResponseBody DataModel saveOrUpdateDetails(HttpServletRequest request,
			@RequestBody Map<String, Object> model) {
		User user = RequestUtils.getUser(request);
		String actorId = user.getActorId();
		DataModel dataModel = new DataModel();
		try {
			Tools.populate(dataModel, model);
			dataModel.setName(ParamUtils.getString(model, "name"));
			dataModel.setCode(ParamUtils.getString(model, "code"));
			dataModel.setDescription(ParamUtils.getString(model, "description"));
			dataModel.setJson(ParamUtils.getString(model, "json"));
			dataModel.setTopId(ParamUtils.getString(model, "topId"));
			dataModel.setParentId(ParamUtils.getString(model, "parentId"));
			dataModel.setCreateBy(ParamUtils.getString(model, "createBy"));
			dataModel.setCreateDate(ParamUtils.getDate(model, "createDate"));
			dataModel.setUpdateBy(ParamUtils.getString(model, "updateBy"));
			dataModel.setUpdateDate(ParamUtils.getDate(model, "updateDate"));
			dataModel.setCreateBy(actorId);
			dataModel.setTreeId(ParamUtils.getString(model, "treeId"));
			this.dataModelService.saveOrUpdateDetails(dataModel);
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(ex);
		}
		return dataModel;
	}

}

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

package com.glaf.datamgr.web.rest;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.metamodel.query.Query;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.base.TreeModel;
import com.glaf.core.domain.QueryDefinition;
import com.glaf.core.identity.User;
import com.glaf.core.security.IdentityFactory;
import com.glaf.core.service.IQueryDefinitionService;
import com.glaf.core.service.ITableDefinitionService;
import com.glaf.core.service.ITreeModelService;
import com.glaf.core.tree.component.TreeComponent;
import com.glaf.core.tree.helper.TreeHelper;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.PageResult;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.core.util.StringTools;
import com.glaf.core.util.Tools;
import com.glaf.datamgr.domain.DataSet;
import com.glaf.datamgr.domain.DataSetAudit;
import com.glaf.datamgr.domain.SelectSegment;
import com.glaf.datamgr.jdbc.DataSetBuilder;
import com.glaf.datamgr.jdbc.SQL;
import com.glaf.datamgr.jdbc.SqlQueryManager;
import com.glaf.datamgr.query.DataSetQuery;
import com.glaf.datamgr.service.DataSetAuditService;
import com.glaf.datamgr.service.DataSetService;
import com.glaf.datamgr.util.DataSetDomainFactory;
import com.glaf.datamgr.util.DataSetJsonFactory;
import com.glaf.datamgr.util.SelectSegmentJsonFactory;

@Controller
@Path("/rs/dataset")
public class DataSetResource {
	private static Log logger = LogFactory.getLog(DataSetResource.class);

	protected ITableDefinitionService tableDefinitionService;

	protected IQueryDefinitionService queryDefinitionService;

	protected DataSetAuditService dataSetAuditService;

	protected DataSetService dataSetService;

	protected ITreeModelService treeModelService;

	@GET
	@POST
	@Path("/columns")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] columns(@Context HttpServletRequest request) throws IOException {
		logger.debug(RequestUtils.getParameterMap(request));
		JSONArray result = new JSONArray();
		String datasetId = request.getParameter("datasetId");
		DataSet dataSet = dataSetService.getDataSet(datasetId);
		if (dataSet != null && dataSet.getSelectSegments() != null) {
			for (SelectSegment seg : dataSet.getSelectSegments()) {
				JSONObject json = seg.toJsonObject();
				if (StringUtils.startsWith(seg.getColumnLabel(), "field_")) {
					json.put("column", seg.getColumnLabel());
					json.put("column_lowercase", seg.getColumnLabel().toLowerCase());
				} else {
					if (StringUtils.isNotEmpty(seg.getColumnName())) {
						json.put("column", seg.getColumnName());
						json.put("column_lowercase", seg.getColumnName().toLowerCase());
					}
				}
				result.add(json);
			}
		}
		logger.debug(result.toJSONString());
		return result.toJSONString().getBytes("UTF-8");
	}

	@POST
	@Path("/data")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] data(@Context HttpServletRequest request, @RequestBody DataRequest dataRequest) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		logger.debug("params:" + params);
		DataSetQuery query = new DataSetQuery();
		Tools.populate(query, params);
		query.setDeleteFlag(0);
		query.setDataRequest(dataRequest);
		DataSetDomainFactory.processDataRequest(dataRequest);

		String keywordsLike_base64 = request.getParameter("keywordsLike_base64");
		if (StringUtils.isNotEmpty(keywordsLike_base64)) {
			String keywordsLike = new String(Base64.decodeBase64(keywordsLike_base64));
			query.setKeywordsLike(keywordsLike);
		}

		String gridType = ParamUtils.getString(params, "gridType");
		if (gridType == null) {
			gridType = "kendoui";
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
		int total = dataSetService.getDataSetCountByQueryCriteria(query);
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
			List<DataSet> list = dataSetService.getDataSetsByQueryCriteria(start, limit, query);

			if (list != null && !list.isEmpty()) {
				JSONArray rowsJSON = new JSONArray();
				result.put("rows", rowsJSON);
				for (DataSet dataSet : list) {
					JSONObject rowJSON = dataSet.toJsonObject();
					rowJSON.put("id", dataSet.getId());
					rowJSON.put("datasetId", dataSet.getId());
					rowJSON.put("dataSetId", dataSet.getId());
					rowJSON.put("startIndex", ++start);
					User createBy = userMap.get(dataSet.getCreateBy());
					if (createBy != null) {
						rowJSON.put("createByName", createBy.getName());
					}
					User updateBy = userMap.get(dataSet.getUpdateBy());
					if (updateBy != null) {
						rowJSON.put("updateByName", updateBy.getName());
					}
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

	@GET
	@POST
	@Path("/getDataSetParams")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] getDataSetParams(@Context HttpServletRequest request) throws IOException {

		String datasetIds = request.getParameter("datasetIds");
		if (StringUtils.isNotBlank(datasetIds)) {
			String ids[] = StringUtils.split(datasetIds, ",");
			JSONArray params = new JSONArray();
			DataSetAudit dataSetAudit;
			for (String datasetId : ids) {
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

		String datasetId = request.getParameter("datasetId");
		DataSetAudit dataSetAudit = dataSetAuditService.getLastestDataSetAudit(datasetId);
		if (dataSetAudit != null) {
			String content = dataSetAudit.getContent();
			if (StringUtils.isNotBlank(content)) {
				JSONObject jsonObject = JSON.parseObject(content);
				if (jsonObject.getString("params") != null)
					return jsonObject.getString("params").getBytes("UTF-8");
			}
		}
		return null;
	}

	@GET
	@POST
	@Path("/getLastestJson")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] getLastestJson(@Context HttpServletRequest request) throws IOException {
		String datasetId = request.getParameter("datasetId");
		DataSetAudit dataSetAudit = dataSetAuditService.getLastestDataSetAudit(datasetId);
		if (dataSetAudit != null) {
			return dataSetAudit.getContent().getBytes("UTF-8");
		}
		return "".getBytes("UTF-8");
	}

	@GET
	@POST
	@Path("/getSelectJson")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] getSelectJson(@Context HttpServletRequest request) throws IOException {
		String datasetId = request.getParameter("datasetId");
		DataSet dataSet = dataSetService.getDataSet(datasetId);
		if (dataSet != null) {
			return SelectSegmentJsonFactory.listToArray(dataSet.getSelectSegments()).toJSONString().getBytes("UTF-8");
		}
		return "".getBytes("UTF-8");
	}

	@GET
	@POST
	@Path("/list")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] list(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DataSetQuery query = new DataSetQuery();
		Tools.populate(query, params);
		List<DataSet> list = dataSetService.list(query);
		ObjectNode responseJSON = new ObjectMapper().createObjectNode();

		ArrayNode tablesJSON = new ObjectMapper().createArrayNode();
		responseJSON.set("data", tablesJSON);

		for (DataSet dataset : list) {
			ObjectNode objectNode = dataset.toObjectNode();
			tablesJSON.add(objectNode);
		}

		return responseJSON.toString().getBytes("UTF-8");
	}

	@POST
	@Path("/save")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] save(@Context HttpServletRequest request) throws IOException {
		String parentId = request.getParameter("parentId");
		String json = request.getParameter("jsonResult");
		String actorId = RequestUtils.getActorId(request);
		DataSet dataset = null;
		JSONObject jsonObject = null;
		if (StringUtils.isNotEmpty(json)) {
			json = StringTools.replaceIgnoreCase(json, "\"select\"", "\"selectSegments\"");
			json = StringTools.replaceIgnoreCase(json, "\"from\"", "\"fromSegments\"");
			json = StringTools.replaceIgnoreCase(json, "\"join\"", "\"joinSegments\"");
			json = StringTools.replaceIgnoreCase(json, "\"where\"", "\"whereSegments\"");
			json = StringTools.replaceIgnoreCase(json, "\"groupBy\"", "\"groupBySegments\"");
			json = StringTools.replaceIgnoreCase(json, "\"having\"", "\"havingSegments\"");
			json = StringTools.replaceIgnoreCase(json, "\"orderBy\"", "\"orderBySegments\"");

			logger.debug(json);

			// JsonFactory f = new JsonFactory();
			// ObjectMapper mapper = new ObjectMapper(f);
			try {
				jsonObject = JSON.parseObject(json);
				dataset = DataSetJsonFactory.jsonToObject(jsonObject);
				// dataset = mapper.readValue(json, DataSet.class);
			} catch (Exception ex) {
				ex.printStackTrace();
				return ResponseUtils.responseJsonResult(false, "解析JSON字符串失败，格式不正确！");
			}
		}

		if (dataset == null) {
			return ResponseUtils.responseJsonResult(false, "参数格式不正确！");
		}

		/*
		 * SqlExecutor sqlExecutor = SqlSegmentUtils.toSql(dataset, null);
		 * dataset.setSql(sqlExecutor.getSql());
		 */

		DataSetBuilder builder = new DataSetBuilder();
		Map<String, Object> parameter = new HashMap<String, Object>(), parameter2 = new HashMap<String, Object>();
		parameter.put("HttpServletRequest", request);

		DataSet ds0 = DataSetJsonFactory.jsonToObject(jsonObject);
		ds0.setLastestJson(json);

		parameter2.putAll(parameter);
		Query query = builder.buildQuery(ds0, parameter);
		dataset.setSql(query.toSql());

		logger.debug("sql:" + dataset.getSql());

		if (!DBUtils.isLegalQuerySql(dataset.getSql())) {
			return ResponseUtils.responseJsonResult(false, "SQL查询不合法！");
		}

		QueryDefinition queryDefinition = new QueryDefinition();
		queryDefinition.setSql(dataset.getSql());
		queryDefinition.setParentId(parentId);
		queryDefinition.setDatabaseId(dataset.getDatabaseId());
		logger.debug("->currsql:" + dataset.getSql());
		SqlQueryManager manager = new SqlQueryManager();
		try {
			manager.toTableDefinition(queryDefinition, dataset.getSql());
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseUtils.responseJsonResult(false, "查询失败，SQL语句不正确！\n" + ex.getMessage());
		}

		DataSetAudit audit = new DataSetAudit();
		audit.setContent(json);
		if (StringUtils.isEmpty(dataset.getId())) {
			dataset.setCreateBy(actorId);
			dataset.setCreateTime(new Date());
			audit.setCreateBy(dataset.getCreateBy());
			if (dataset.getSaveFlag() == null) {
				audit.setSaveFlag("C");
			}
		} else {
			dataset.setUpdateBy(actorId);
			dataset.setUpdateTime(new Date());
			if (dataset.getSaveFlag() == null) {
				audit.setSaveFlag("U");
			}
			if (dataset.getDeleteFlag() == 1) {
				audit.setSaveFlag("D");
			}
			audit.setCreateBy(dataset.getUpdateBy());
		}

		if (StringUtils.isNotEmpty(dataset.getSql())) {

			dataset.setSql(SQL.format(dataset.getSql()));

			dataSetService.save(dataset);

			audit.setDatasetId(dataset.getId());
			dataSetAuditService.save(audit);
		}

		JSONObject result = new JSONObject();
		result.putAll(dataset.toJsonObject());
		long time = DataSetBuilder.validate(dataset, parameter2);
		if (time > DataSetBuilder.VALIDATE_TIME_MILLIS) {// 大于15秒的不能通过
			result.put("time", time / 1000);
		} else {
			DataSetBuilder.cacheDataSet(dataset, true);
		}

		// return ResponseUtils.responseJsonResult(true);
		return result.toJSONString().getBytes("UTF-8");
	}

	@POST
	@Path("/saveHistory")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] saveHistory(@Context HttpServletRequest request) {
		String json = request.getParameter("jsonResult");
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		DataSetAudit datasetAudit = new DataSetAudit();
		Tools.populate(datasetAudit, params);
		datasetAudit.setContent(json);
		dataSetAuditService.save(datasetAudit);
		return ResponseUtils.responseJsonResult(true);
	}

	@javax.annotation.Resource
	public void setDataSetAuditService(DataSetAuditService dataSetAuditService) {
		this.dataSetAuditService = dataSetAuditService;
	}

	@javax.annotation.Resource
	public void setDataSetService(DataSetService dataSetService) {
		this.dataSetService = dataSetService;
	}

	@javax.annotation.Resource
	public void setQueryDefinitionService(IQueryDefinitionService queryDefinitionService) {
		this.queryDefinitionService = queryDefinitionService;
	}

	@javax.annotation.Resource
	public void setTableDefinitionService(ITableDefinitionService tableDefinitionService) {
		this.tableDefinitionService = tableDefinitionService;
	}

	@javax.annotation.Resource
	public void setTreeModelService(ITreeModelService treeModelService) {
		this.treeModelService = treeModelService;
	}

	public void test(DataSet dataset) {

		DataSetBuilder builder = new DataSetBuilder();

		Map<String, Object> parameter = null;

		// JSONObject jsonObject = builder.getJson("default", dataset, 1, 100,
		// parameter );

		// System.out.println(jsonObject.toJSONString());

		Query query = builder.buildQuery(dataset, parameter);

		logger.debug("SelectClause:" + query.getSelectClause());
		logger.debug("FromClause:" + query.getFromClause());
		logger.debug("WhereClause:" + query.getWhereClause());
		logger.debug("OrderByClause:" + query.getOrderByClause());
		logger.debug("toSql:" + query.toSql());
	}

	@GET
	@POST
	@Path("/toSql")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] toSql(@Context HttpServletRequest request) throws IOException {
		Map<String, Object> params = RequestUtils.getParameterMap(request);
		logger.debug(params);
		String parentId = request.getParameter("parentId");
		String json = request.getParameter("jsonResult");
		logger.debug("json=" + json);
		ObjectNode responseJSON = new ObjectMapper().createObjectNode();
		DataSet dataset = null;

		if (StringUtils.isNotEmpty(json)) {
			json = StringTools.replaceIgnoreCase(json, "\"select\"", "\"selectSegments\"");
			json = StringTools.replaceIgnoreCase(json, "\"from\"", "\"fromSegments\"");
			json = StringTools.replaceIgnoreCase(json, "\"join\"", "\"joinSegments\"");
			json = StringTools.replaceIgnoreCase(json, "\"where\"", "\"whereSegments\"");
			json = StringTools.replaceIgnoreCase(json, "\"groupBy\"", "\"groupBySegments\"");
			json = StringTools.replaceIgnoreCase(json, "\"having\"", "\"havingSegments\"");
			json = StringTools.replaceIgnoreCase(json, "\"orderBy\"", "\"orderBySegments\"");

			logger.debug(json);
			// JsonFactory f = new JsonFactory();
			// ObjectMapper mapper = new ObjectMapper(f);
			try {
				JSONObject jsonObject = JSON.parseObject(json);
				dataset = DataSetJsonFactory.jsonToObject(jsonObject);
				// dataset = mapper.readValue(json, DataSet.class);
			} catch (Exception ex) {
				ex.printStackTrace();
				return ResponseUtils.responseJsonResult(false, "解析JSON字符串失败，格式不正确！");
			}
		}

		if (dataset == null) {
			return ResponseUtils.responseJsonResult(false, "参数格式不正确！");
		}

		DataSetBuilder builder = new DataSetBuilder();
		Map<String, Object> parameter = new HashMap<String, Object>(), parameter2 = new HashMap<String, Object>();
		parameter.put("HttpServletRequest", request);
		dataset.setLastestJson(json);

		parameter2.putAll(parameter);
		long time = DataSetBuilder.validate(dataset, parameter2);
		if (time > DataSetBuilder.VALIDATE_TIME_MILLIS) {// 大于15秒的不能通过
			responseJSON.put("time", time / 1000);
			return responseJSON.toString().getBytes("UTF-8");
		}

		Query query = builder.buildQuery(dataset, parameter);
		dataset.setSql(query.toSql());

		/*
		 * SqlExecutor sqlExecutor = SqlSegmentUtils.toSql(dataset, null);
		 * dataset.setSql(sqlExecutor.getSql());
		 */

		logger.debug("sql:" + dataset.getSql());

		QueryDefinition queryDefinition = new QueryDefinition();
		queryDefinition.setSql(dataset.getSql());
		queryDefinition.setParentId(parentId);
		queryDefinition.setDatabaseId(dataset.getDatabaseId());
		SqlQueryManager manager = new SqlQueryManager();
		try {
			manager.toTableDefinition(queryDefinition, dataset.getSql());
		} catch (Exception ex) {
			ex.printStackTrace();
			return ResponseUtils.responseJsonResult(false, "查询失败，SQL语句不正确！\n\n" + dataset.getSql());
		}

		if (StringUtils.isNotEmpty(dataset.getSql())) {
			responseJSON.put("sql", dataset.getSql());
			return responseJSON.toString().getBytes("UTF-8");
		}

		return null;
	}

	@GET
	@POST
	@Path("/treeJson")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_JSON })
	public byte[] treeJson(@Context HttpServletRequest request) throws IOException {
		JSONArray array = new JSONArray();
		Long nodeId = RequestUtils.getLong(request, "nodeId");
		String nodeCode = request.getParameter("nodeCode");
		String selected = request.getParameter("selected");

		logger.debug(RequestUtils.getParameterMap(request));
		List<TreeComponent> components = new java.util.ArrayList<TreeComponent>();
		List<String> chooseList = new java.util.ArrayList<String>();
		if (StringUtils.isNotEmpty(selected)) {
			chooseList = StringTools.split(selected);
		}

		TreeModel treeNode = null;

		if (nodeId != null && nodeId > 0) {
			treeNode = treeModelService.getTreeModel(nodeId);
		} else if (StringUtils.isNotEmpty(nodeCode)) {
			treeNode = treeModelService.getTreeModelByCode(nodeCode);
		}

		if (treeNode != null) {
			DataSetQuery query = new DataSetQuery();
			query.setLocked(0);
			query.setDeleteFlag(0);
			query.setKeywordsLike(request.getParameter("keywordsLike"));

			String keywordsLike_base64 = request.getParameter("keywordsLike_base64");
			if (StringUtils.isNotEmpty(keywordsLike_base64)) {
				String keywordsLike = new String(Base64.decodeBase64(keywordsLike_base64));
				query.setKeywordsLike(keywordsLike);
			}

			List<DataSet> datasets = dataSetService.list(query);
			List<TreeModel> childrenTrees = treeModelService.getChildrenTreeModels(treeNode.getId());
			if (childrenTrees != null && !childrenTrees.isEmpty()) {
				for (TreeModel tree : childrenTrees) {
					TreeComponent c = new TreeComponent();
					c.setId(String.valueOf(tree.getId()));
					c.setCode(String.valueOf(tree.getId()));
					c.setParentId(String.valueOf(tree.getParentId()));
					c.setTitle(tree.getName());
					c.setCls("folder");
					c.getDataMap().put("level", "0");
					c.getDataMap().put("nocheck", "true");
					c.getDataMap().put("iconSkin", "tree_folder");
					c.getDataMap().put("isParent", "true");
					components.add(c);

					for (DataSet dataset : datasets) {
						if (dataset.getNodeId() != null && tree.getId() == dataset.getNodeId()) {
							TreeComponent t = new TreeComponent();
							t.setId(dataset.getId());
							t.setParentId(String.valueOf(tree.getId()));
							t.setTitle(dataset.getName());
							t.setCode(dataset.getId());
							t.setCls("leaf");
							t.getDataMap().put("iconSkin", "tree_leaf");
							t.getDataMap().put("level", "9");
							if (chooseList.contains(dataset.getId())) {
								t.setChecked(true);
							}
							components.add(t);
						}
					}
				}
			}
			logger.debug("components size:" + components.size());
			TreeHelper treeHelper = new TreeHelper();
			array = treeHelper.getJSONArray(components, 0);
		}
		logger.debug(array.toJSONString());
		return array.toJSONString().getBytes("UTF-8");
	}

	@GET
	@POST
	@Path("/view/{datasetId}")
	@ResponseBody
	@Produces({ MediaType.APPLICATION_OCTET_STREAM })
	public byte[] view(@PathParam("datasetId") String datasetId, @Context HttpServletRequest request)
			throws IOException {
		DataSet dataset = dataSetService.getDataSet(datasetId);
		return DataSetJsonFactory.toJSON(dataset).getBytes("UTF-8");
	}

}
